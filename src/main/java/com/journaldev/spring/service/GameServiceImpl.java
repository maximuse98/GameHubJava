package com.journaldev.spring.service;

import java.util.ArrayList;
import java.util.List;

import com.journaldev.spring.model.Choice;
import com.journaldev.spring.model.Game;
import com.journaldev.spring.model.MatrixVariant;
import com.journaldev.spring.view.GameView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.GameDAO;

@Service
public class GameServiceImpl implements GameService {
	
	private GameDAO gameDAO;

	public void setGameDAO(GameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

	@Override
	@Transactional
	public void addGame(Game p) {
		this.gameDAO.addGame(p);
	}

	@Override
	@Transactional
	public void updateGame(Game p) {
		this.gameDAO.updateGame(p);
	}

	@Override
	@Transactional
	public List<Game> listGames() {
		return this.gameDAO.listGames();
	}

	@Override
	@Transactional
	public List<GameView> getGameViews(){
		List<Game> games = this.listGames();
		List<GameView> gameViews = new ArrayList<>(games.size());
		for (Game game:games) {
			gameViews.add(new GameView(game));
		}
		return gameViews;
	}

	@Override
	@Transactional
	public Game getGame(int id) {
		return this.gameDAO.getGameById(id);
	}

	@Override
	@Transactional
	public void removeGame(int id) {
		this.gameDAO.removeGame(id);
	}

	@Override
	@Transactional
	public void removeGame(Game game) {
		this.gameDAO.removeGame(game);
	}

	@Override
	@Transactional
    public Choice getChoice(int id) {
        return this.gameDAO.getChoiceById(id);
    }

    @Override
	@Transactional
    public MatrixVariant getVariant(String matrixPosition) {
        return this.gameDAO.getVariantByPosition(matrixPosition);
    }
}
