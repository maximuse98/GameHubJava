package com.gamehub;

import com.gamehub.dao.GameDAO;
import com.gamehub.model.Game;
import com.gamehub.model.Scene;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DatabaseTest {
    private GameDAO gameDAO;
    private List<Game> games;

    @Autowired
    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @Test
    @Transactional
    public void getGames(){
        games = gameDAO.listGames();
        for (Game game:games) {
            System.out.println(game);
        }
    }

    @Test
    @Transactional
    public void addGame(){

    }
}
