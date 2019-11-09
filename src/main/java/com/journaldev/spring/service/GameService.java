package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.model.Choice;
import com.journaldev.spring.model.Game;
import com.journaldev.spring.model.MatrixVariant;
import com.journaldev.spring.view.GameView;

public interface GameService {

	void addGame(Game p);
	void updateGame(Game p);
	List<Game> listGames();
	List<GameView> getGameViews();
	Game getGame(int id);
	void removeGame(int id);
	void removeGame(Game game);
	Choice getChoice(int id);
	MatrixVariant getVariant(String matrixPosition);
}
