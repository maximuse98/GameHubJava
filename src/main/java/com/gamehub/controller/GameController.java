package com.gamehub.controller;

import com.gamehub.entity.UserEntity;
import com.gamehub.service.GameParserService;
import com.gamehub.view.SceneView;
import com.gamehub.model.User;
import com.gamehub.service.UserService;
import com.gamehub.service.GameService;

import com.gamehub.view.UserView;
import org.hibernate.exception.ConstraintViolationException;
import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.security.Principal;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
public class GameController {
	
	private GameService gameService;
	private UserService userService;
	private GameParserService gameParserService;

	private static final String LOGIN_URL = "redirect:/login";
	private static final String USER_MAIN_URL = "redirect:/games";
	private static final String USER_SESSION_URL = "redirect:/game";
	private static final String ADMIN_URL = "redirect:/upload";

	@Autowired
	@Qualifier(value="userService")
	public void setUserService(UserService us){
		this.userService = us;
	}
	
	@Autowired
	@Qualifier(value="gameService")
	public void setGameService(GameService gs){
		this.gameService = gs;
	}

    @Autowired
    @Qualifier(value="gameParserService")
    public void setGameParserService(GameParserService ps){this.gameParserService = ps;}


	@RequestMapping(value = {"/login", "/"})
	public String loginPage(@RequestParam(value = "status", required = false) String status, Model model){
		if(status != null){
			switch (status) {
				case "created":
					model.addAttribute("message", "Account successfully created");
					break;
				case "logout":
					model.addAttribute("message", "Successfully logout");
					break;
				case "error":
					model.addAttribute("error", "Bad credentials");
					break;
			}
		}
		model.addAttribute("user",new UserEntity());
		return "index";
	}

	@RequestMapping(value = "/registry", method = RequestMethod.GET)
	public String registerPage(Model model){
		model.addAttribute("user",new UserView());
		return "register";
	}

	@RequestMapping(value = "/registry", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") @Valid UserView user, BindingResult result, Model model){
		if (result.hasErrors()) {
			return "register";
		}
		if(!user.getPassword().equals(user.getPasswordConfirm())){
			model.addAttribute("error", "Passwords are not matched");
			return "register";
		}
		try {
			userService.saveUser(user.getUsername(),user.getPassword());
		} catch (ConstraintViolationException e){
			model.addAttribute("error","Username is already used");
			return "register";
		}
		return LOGIN_URL.concat("?status=created");
	}

	@RequestMapping(value = "/games", method = RequestMethod.GET)
	public String mainPage(Model model){
		model.addAttribute("listGames", gameService.getGameViews());
        model.addAttribute("listSessions",userService.getSessionViews());
        model.addAttribute("listUsers", userService.getUserViews());
		return "game";
	}
	
	@RequestMapping(value = "/start/{gameId}", method = RequestMethod.GET)
    public String startNewSession(@PathVariable("gameId") int id, Principal principal){
	    userService.createSession(principal.getName(),gameService.getGame(id));
		return USER_SESSION_URL;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String getScene(Model model, Principal principal){
        User user = userService.getUser(principal.getName());

        model.addAttribute("scene", new SceneView(user.getCurrentScene()));
        model.addAttribute("gameName", user.getCurrentSession().getGame().getName());

        return "session";
    }

    @RequestMapping(value = "/leave", method = RequestMethod.GET)
    public String leaveSession(Principal principal){
	    userService.leaveSession(principal.getName());
		return USER_MAIN_URL;
    }

    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    public String exitUser(Model model, HttpServletRequest request){
	    userService.removeUser(request.getUserPrincipal().getName());
		model.addAttribute("logout",true);
		return "game";
    }

	@RequestMapping(value = "/connect/{sessionId}", method = RequestMethod.GET)
	public String addUserToSession(@PathVariable("sessionId") int sessionId, HttpServletRequest request){
	    GameSession session = userService.getSession(sessionId).orElseThrow(()-> new NotFoundException("Session not found"));
	    if(session.getGameSize()<=session.getUsersCount()){
	        return USER_MAIN_URL;
	    }
	    userService.addUserToSession(request.getUserPrincipal().getName(), sessionId);
        return USER_SESSION_URL;
	}

	@RequestMapping(value = "/send/{choiceId}", method = RequestMethod.GET)
	public String sendAnswer(@PathVariable("choiceId") int choiceId, Principal principal) throws ExecutionException, InterruptedException {
		User user = userService.getUser(principal.getName());
		if(user.hasNewScene()){
			return USER_SESSION_URL;
		}
		user.getChoice(choiceId).orElseThrow(()-> new NotFoundException("Choice not found"));

		Future<String> currentFuture = user.getCurrentFuture();
		if(currentFuture == null){
			Future<String> newFuture = user.doAsync(choiceId,USER_SESSION_URL);
			user.setCurrentFuture(newFuture);
			return newFuture.get();
		} else {
			return currentFuture.get();
		}
	}

	@RequestMapping(value = "/image/sprite/{spriteId}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getSprite(@PathVariable("spriteId") int id, Principal principal) throws SQLException {
	    return userService.getUserSprite(principal.getName(), id);
	}

	@RequestMapping(value = "/image/background", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getBackground(Principal principal) throws SQLException {
		return userService.getUserBackground(principal.getName());
    }

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("game") MultipartFile file, @RequestParam("images") MultipartFile[] images) throws IOException, ParseException, SQLException {
		gameParserService.parse(file,images);
		gameService.addGame(gameParserService.getGame());
		return ADMIN_URL;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getUploadPage(Model model){
        model.addAttribute("listGames", gameService.getGameViews());
		return "load";
	}

    @RequestMapping(value = "/delete/{gameId}", method = RequestMethod.GET)
	public String deleteGame(@PathVariable("gameId") int gameId){
	    gameService.removeGame(gameId);
        return ADMIN_URL;
    }
}
