package com.gamehub.dao;

import java.util.List;

import com.gamehub.model.Choice;
import com.gamehub.model.Game;
import com.gamehub.model.MatrixVariant;

public interface GameDAO {

	void addGame(Game p);
	void updateGame(Game p);
	List<Game> listGames();
	Game getGameById(int id);
	void removeGame(int id);
	void removeGame(Game game);
	Choice getChoiceById(int id);
	MatrixVariant getVariantByPosition(String matrixPosition);
}
