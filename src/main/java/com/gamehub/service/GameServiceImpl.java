package com.gamehub.service;

import java.util.ArrayList;
import java.util.List;

import com.gamehub.dao.GameDAO;
import com.gamehub.entity.GameEntity;
import com.gamehub.entity.MatrixVariantEntity;
import com.gamehub.model.Choice;
import com.gamehub.model.Game;
import com.gamehub.model.Scene;
import com.gamehub.view.View;
import org.springframework.transaction.annotation.Transactional;


public class GameServiceImpl implements GameService {
	private GameDAO gameDAO;

	public void setGameDAO(GameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

	@Transactional
	public void addGame(GameEntity p) {
		this.gameDAO.addGame(p);
	}

	@Transactional
	public void updateGame(GameEntity p) {
		this.gameDAO.updateGame(p);
	}

	@Transactional
	public GameEntity getGame(int id) {
		return this.gameDAO.getGameById(id);
	}

	@Transactional
	public void removeGame(int id) {
		this.gameDAO.removeGame(id);
	}

	@Transactional
	public void removeGame(GameEntity game) {
		this.gameDAO.removeGame(game);
	}

    public Choice getChoice(Scene scene, int id) {
        while(!scene.getType().equals("Quest")){
        	scene = scene.getNextScene();
        	if (scene == null) throw new NullPointerException();
		}
		for (Choice choice:scene.getChoices()) {
			if (choice.getId() == id) return choice;
		}
		throw new NullPointerException();
    }

	@Transactional
    public MatrixVariantEntity getVariant(String matrixPosition) {
        return this.gameDAO.getVariantByPosition(matrixPosition);
    }

	@Transactional
	public Game getGameModel(int id) {
		return new Game(this.getGame(id));
	}

	@Transactional
    public List<View> getGameViews(){
		List<GameEntity> games = this.gameDAO.listGames();
		List<View> views = new ArrayList<>(games.size());
		for (GameEntity game:games) {
			views.add(game.createView());
		}
		return views;
	}
}
