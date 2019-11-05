package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.model.Choice;
import com.journaldev.spring.model.Game;
import com.journaldev.spring.model.MatrixVariant;

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
