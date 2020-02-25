package com.gamehub.controller;

import com.gamehub.entity.UserEntity;
import com.gamehub.service.GameService;
import com.gamehub.service.SessionService;
import com.gamehub.service.UserService;
import com.gamehub.view.GameView;
import com.gamehub.view.SceneView;
import com.gamehub.view.UserView;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

@Controller
public class GameController {
	
	private GameService gameService;
	private UserService userService;
	private SessionService sessionService;

	private static final String LOGIN_URL = "redirect:/login";
	private static final String USER_MAIN_URL = "redirect:/games";
	private static final String USER_SESSION_URL = "redirect:/game";

	@Autowired
	public void setUserService(UserService us){
		this.userService = us;
	}
	
	@Autowired
	public void setGameService(GameService gs){
		this.gameService = gs;
	}

	@Autowired
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}


	@RequestMapping(value = {"/login", "/"})
	public String loginPage(@RequestParam(value = "status", required = false) String status, Model model,Principal principal){
	    if(principal != null) return USER_MAIN_URL;
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
	public String registerPage(Model model, Principal principal){
        if(principal != null) return USER_MAIN_URL;
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
	public String mainPage(Model model, Principal principal){
		if (sessionService.isUserExist(principal.getName())) return USER_SESSION_URL;

	    model.addAttribute("username", principal.getName());
		model.addAttribute("listGames", gameService.getGameViews());
        model.addAttribute("listSessions", sessionService.getSessionViews());
        model.addAttribute("listUsers", sessionService.getUserViews());
		return "game";
	}
	
	@RequestMapping(value = "/start/{gameId}", method = RequestMethod.GET)
    public String startNewSession(@PathVariable("gameId") int id, Principal principal){
		sessionService.createSession(principal.getName(), gameService.getGame(id));
		return USER_SESSION_URL;
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String getScene(Model model, Principal principal){
		if(!sessionService.isUserExist(principal.getName())) return USER_MAIN_URL;
        GameView gameView = (GameView) sessionService.getViewByUser(principal.getName(), new GameView());

        model.addAttribute("scene", sessionService.getViewByUser(principal.getName(), new SceneView()));
        model.addAttribute("gameName", gameView.getName());
        model.addAttribute("gameColor",gameView.getColor());
        //model.addAttribute("userRole", "RoleExample");

        return "session";
    }

    @RequestMapping(value = "/leave", method = RequestMethod.GET)
    public String leaveSession(Principal principal, Model model){
		sessionService.leaveSession(principal.getName());
        model.addAttribute("logout", true);
        return USER_MAIN_URL;
    }

	@RequestMapping(value = "/connect/{sessionId}", method = RequestMethod.GET)
	public String addUserToSession(@PathVariable("sessionId") int sessionId, Principal principal){
	    sessionService.addUserToSession(principal.getName(), sessionId);
        return USER_SESSION_URL;
	}

	@RequestMapping(value = "/send/{choiceId}", method = RequestMethod.GET)
	public String sendAnswer(@PathVariable("choiceId") int choiceId, Principal principal) throws ExecutionException, InterruptedException {
		if(!sessionService.isUserExist(principal.getName())) return USER_MAIN_URL;
		if(sessionService.addChoice(principal.getName(),choiceId).get()){
            return USER_SESSION_URL;
        }
        throw new NullPointerException();
	}

	@RequestMapping(value = "/image/sprite", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getSprite(@RequestParam int scene_id, @RequestParam int id, Principal principal) throws SQLException {
	    return sessionService.getUserSprite(principal.getName(),id,scene_id);
	}

	@RequestMapping(value = "/image/background", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getBackground(Principal principal, @RequestParam int scene_id) throws SQLException {
		return sessionService.getUserBackground(principal.getName(),scene_id);
    }
}
