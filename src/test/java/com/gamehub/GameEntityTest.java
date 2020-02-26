package com.gamehub;

import com.gamehub.dao.GameDAO;
import com.gamehub.entity.GameEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class GameEntityTest {
    private GameDAO gameDAO;

    @Autowired
    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @Test
    @Transactional
    public void getGames(){
        List<GameEntity> games = gameDAO.listGames();
        for (GameEntity game:games) {
            System.out.println(game);
        }
    }

    @Test
    @Transactional
    public void createGameViews(){
        List<GameEntity> games = gameDAO.listGames();
        for (GameEntity game:games) {
            System.out.println(game.createView());
        }
    }
}
