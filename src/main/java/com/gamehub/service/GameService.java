package com.gamehub.service;

import java.util.List;

import com.gamehub.model.Choice;
import com.gamehub.model.MatrixVariant;
import com.gamehub.model.Game;
import com.gamehub.view.GameView;

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
