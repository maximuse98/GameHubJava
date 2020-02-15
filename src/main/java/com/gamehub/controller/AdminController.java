package com.gamehub.controller;

import com.gamehub.service.GameParserService;
import com.gamehub.service.GameService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;

@Controller
public class AdminController {
    private GameService gameService;
    private GameParserService gameParserService;

    private static final String ADMIN_URL = "redirect:/upload";

    @Autowired
    @Qualifier(value="gameService")
    public void setGameService(GameService gs){
        this.gameService = gs;
    }

    @Autowired
    @Qualifier(value="gameParserService")
    public void setGameParserService(GameParserService ps){this.gameParserService = ps;}


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("game") MultipartFile file, @RequestParam("images") MultipartFile[] images) throws IOException, ParseException, SQLException {
        gameParserService.parse(file,images);
        gameService.addGame(gameParserService.getGame());
        return ADMIN_URL;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getUploadPage(Model model, Principal principal){
        model.addAttribute("listGames", gameService.getGameViews() );
        model.addAttribute("username", principal.getName());
        return "load";
    }

    @RequestMapping(value = "/delete/{gameId}", method = RequestMethod.GET)
    public String deleteGame(@PathVariable("gameId") int gameId){
        gameService.removeGame(gameId);
        return ADMIN_URL;
    }
}
