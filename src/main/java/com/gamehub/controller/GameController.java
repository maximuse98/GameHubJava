package com.gamehub.controller;

import com.gamehub.entity.UserEntity;
import com.gamehub.exception.ExceptionType;
import com.gamehub.exception.NotFoundException;
import com.gamehub.model.Game;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
	@Qualifier(value="userService")
	public void setUserService(UserService us){this.userService = us;}
	
	@Autowired
	@Qualifier(value="gameService")
	public void setGameService(GameService gs){
		this.gameService = gs;
	}

    @Autowired
    @Qualifier(value="gameParserService")
    public void setGameParserService(GameParserService ps){this.gameParserService = ps;}


	@RequestMapping(value = {"/login", "/"})
	public String login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "message", required = false) String message, @RequestParam(value = "logout", required = false) String logout, Model model, Principal principal){
		if (error != null) {
			model.addAttribute("error", "Bad credentials");
		}
		if (logout != null) {
			model.addAttribute("message", "Successfully logout");
		}
		if (message != null){
			model.addAttribute("message","Account successfully created");
		}
		if (principal != null){
			try{
				User user = userService.getUser(principal.getName());
				user.getCurrentSession();
			}catch (NotFoundException e){
				return handleException(e.getType());
			}
			return "redirect:/game";
		}
		model.addAttribute("user",new UserEntity());
		return "index";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Model model){
		model.addAttribute("user",new UserView());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
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
			model.addAttribute("error","Username is already in use");
			return "register";
		}
		return "redirect:/login?message";
	}

	@RequestMapping(value = "/games", method = RequestMethod.GET)
	public String login(Model model, Principal principal){
		userService.createUser(principal.getName());

		model.addAttribute("listGames", gameService.getGameViews());
        model.addAttribute("listSessions",userService.getSessionViews());
        model.addAttribute("listUsers", userService.getUserViews());
		return "game";
	}
	
	@RequestMapping(value = "/start/{gameId}", method = RequestMethod.GET)
    public String startNewSession(@PathVariable("gameId") int id, Principal principal){
		try {
			Game game = gameService.getGame(id);
			userService.createSession(principal.getName(),game);
		} catch (NotFoundException e) {
			return handleException(e.getType());
		}
		return "redirect:/game";
    }

    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public String getScene(Model model, Principal principal){
        User user;
        Game game;
        try {
            user = userService.getUser(principal.getName());
            game = user.getCurrentSession().getGame();
        } catch (NotFoundException e) {
            return handleException(e.getType());
        }

        model.addAttribute("scene", new SceneView(user.getCurrentScene()));
        model.addAttribute("gameName", game.getName());

        return "session";
    }

    @RequestMapping(value = "/leave", method = RequestMethod.GET)
    public String leaveSession(Principal principal){
		try {
			userService.leaveSession(principal.getName());
		} catch (NotFoundException e) {
			return handleException(e.getType());
		}
		return "redirect:/games";
    }

    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    public String exitUser(Model model, Principal principal){
		try {
			userService.removeUser(principal.getName());
		} catch (NotFoundException e) {
			return handleException(e.getType());
		}
		model.addAttribute("logout",true);
		return "game";
    }

	@RequestMapping(value = "/connect/{sessionId}", method = RequestMethod.GET)
	public String addUserToSession(@PathVariable("sessionId") int sessionId, Principal principal){
		GameSession session;
		try {
			session = userService.getSession(sessionId);
			if(session.getGameSize()<=session.getUsersCount()){
				return "redirect:/games";
			}
			userService.addUserToSession(principal.getName(), sessionId);
		} catch (NotFoundException e) {
			return handleException(e.getType());
		}
        return "redirect:/game";
	}

	@RequestMapping(value = "/send/{choiceId}", method = RequestMethod.GET)
	public String sendAnswer(@PathVariable("choiceId") int choiceId, Principal principal) throws ExecutionException, InterruptedException {
		User user;
		try {
			user = userService.getUser(principal.getName());
			user.getCurrentSession();
		} catch (NotFoundException e) {
			return handleException(e.getType());
		}
		Future<String> future = this.asyncMethodWithReturnType(user,choiceId);

		while (true) {
			if (future.isDone()) {
                return future.get();
			}
		}
	}

	@RequestMapping(value = "/image/sprite/{spriteId}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getSprite(@PathVariable("spriteId") int id, Principal principal) throws SQLException {
		try {
			return userService.getUserSprite(principal.getName(), id);
		} catch (NotFoundException e) {
			return null;
		}
	}

	@RequestMapping(value = "/image/background", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getBackground(Principal principal) throws SQLException {
		try {
			return userService.getUserBackground(principal.getName());
		} catch (NotFoundException | NullPointerException e) {
			return null;
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("game") MultipartFile file, @RequestParam("images") MultipartFile[] images) throws IOException, ParseException, SQLException {
		gameParserService.parse(file,images);
		Game game = gameParserService.getGame();

		gameService.addGame(game);
		return "redirect:/upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String getUploadPage(Model model){
        model.addAttribute("listGames", gameService.getGameViews());
		return "load";
	}

    @RequestMapping(value = "/delete/{gameId}", method = RequestMethod.GET)
	public String deleteGame(@PathVariable("gameId") int gameId){
	    gameService.removeGame(gameId);
        return "redirect:/upload";
    }

	@Async
	public Future<String> asyncMethodWithReturnType(User user, int choiceId) {
		try {
			GameSession currentSession = user.getCurrentSession();
			currentSession.addChoice(user, choiceId);
			while(!user.hasNewScene()){
			    user.getCurrentSession();
				Thread.sleep(1000);
			}
			//System.out.println("Finish method - " + Thread.currentThread().getName());
			return new AsyncResult<>("redirect:/game");
		} catch (InterruptedException e) {
			e.printStackTrace();
            return new AsyncResult<>(null);
		} catch (NotFoundException e) {
			return new AsyncResult<>(handleException(e.getType()));
		}
	}

	private String handleException(ExceptionType type){
		switch (type){
			case USER:
				return "redirect:/login";
			case SESSION:
				return "redirect:/games";
			case IMAGE:
				return null;
		}
		return null;
	}
}
