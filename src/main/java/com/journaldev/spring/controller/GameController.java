package com.journaldev.spring.controller;

import com.journaldev.spring.model.Game;
import com.journaldev.spring.service.GameParserService;
import com.journaldev.spring.view.SceneView;
import com.journaldev.spring.model.User;
import com.journaldev.spring.service.UserService;
import com.journaldev.spring.view.UserView;
import com.journaldev.spring.service.GameService;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
public class GameController {
	
	private GameService gameService;
	private UserService userService;
	private GameParserService gameParserService;

	@Autowired()
	@Qualifier(value="userService")
	public void setUserService(UserService us){this.userService = us;}
	
	@Autowired()
	@Qualifier(value="gameService")
	public void setGameService(GameService gs){
		this.gameService = gs;
	}

    @Autowired()
    @Qualifier(value="gameParserService")
    public void setGameParserService(GameParserService ps){this.gameParserService = ps;}

	@RequestMapping(value = "/register")
	public String reg(Model model){
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/games", method = RequestMethod.POST)
	public String register(@ModelAttribute User user){
		userService.createUser(user);
		return "redirect:/games/" + user.getUsername();
	}

	@RequestMapping(value = "/games/{username}", method = RequestMethod.GET)
	public String backToGames(@PathVariable("username") String username, Model model){
		User user = this.userService.getUser(username);

		userService.leaveSession(user);

		model.addAttribute("user", new UserView(user));
		model.addAttribute("game", new Game());

		model.addAttribute("listGames", gameService.getGameViews());
		model.addAttribute("listSessions",userService.getSessionViews());
		model.addAttribute("listUsers", userService.getUserViews());

        return "game";
	}
	
	@RequestMapping(value = "/game/{username}/{gameId}", method = RequestMethod.GET)
    public String startNewSession(@PathVariable("gameId") int id, @PathVariable("username") String username, Model model){
		Game game = this.gameService.getGame(id);
		User user = this.userService.getUser(username);

		if (user == null) {
            return "redirect:/register";
        }

		if(user.getCurrentSession()==null){
		    userService.createSession(user, game);
		}

		model.addAttribute("scene", new SceneView(user.getCurrentScene()));
		model.addAttribute("username", user.getUsername());
		model.addAttribute("gameName", game.getName());

		return "session";
    }

    @RequestMapping(value = "/leave/{username}", method = RequestMethod.GET)
    public String leaveSession(@PathVariable("username") String username){
        User user = this.userService.getUser(username);
        userService.leaveSession(user);

        return "redirect:/games/"+username;
    }

    @RequestMapping(value = "/exit/{username}", method = RequestMethod.GET)
    public String exitUser(@PathVariable("username") String username){
        userService.removeUser(username);
        return "redirect:/register";
    }

	@RequestMapping(value = "/connect/{username}/{sessionId}", method = RequestMethod.GET)
	public String addUserToSession(@PathVariable("sessionId") int sessionId, @PathVariable("username") String username){
		User user = userService.getUser(username);
        if (user == null) {
            return "redirect:/register";
        }
		GameSession session = userService.getSession(sessionId);
        if(session.getGameSize()<=session.getUsersCount()){
            return "redirect:/games/{username}";
        }
        userService.addUserToSession(user, session);
        return "redirect:/game/"+username+"/"+session.getGame().getId();
	}

	@RequestMapping(value = "/send/{username}/{choiceId}", method = RequestMethod.GET)
	public String sendAnswer(@PathVariable("choiceId") int choiceId, @PathVariable("username") String username) throws ExecutionException, InterruptedException {
		User user = userService.getUser(username);
		if (user == null) {
			return "redirect:/register";
		}
		if(user.getCurrentSession() == null){
            return "redirect:/games/{username}";
        }
		//System.out.println("Invoking an asynchronous method. " + Thread.currentThread().getName());
		Future<String> future = this.asyncMethodWithReturnType(user,choiceId);

		while (true) {
			if (future.isDone()) {
				//System.out.println("Result from asynchronous process - " + future.get());
				return future.get();
			}
		}
	}

	@RequestMapping(value = "/image/sprite/{username}/{spriteId}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getSprite(@PathVariable("username") String username, @PathVariable("spriteId") int id) throws SQLException {
		return userService.getUserSprite(username, id);
	}

	@RequestMapping(value = "/image/background/{username}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getBackground(@PathVariable("username") String username) throws SQLException {
		return userService.getUserBackground(username);
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
		GameSession currentSession = user.getCurrentSession();
		currentSession.addChoice(user, choiceId);

		//System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
		try {
			while(!user.hasNewScene()){
			    if(user.getCurrentSession() == null){
			        return new AsyncResult<>(null);
                }
				Thread.sleep(1000);
			}
			//System.out.println("Finish method - " + Thread.currentThread().getName());
			return new AsyncResult<>("redirect:/game/"+user.getUsername()+"/"+currentSession.getGame().getId());
		} catch (InterruptedException e) {
			e.printStackTrace();
            return new AsyncResult<>(null);
		}
	}
}
