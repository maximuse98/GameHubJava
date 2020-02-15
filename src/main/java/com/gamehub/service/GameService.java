package com.gamehub.service;

import com.gamehub.model.Choice;
import com.gamehub.model.Game;
import com.gamehub.model.MatrixVariant;
import com.gamehub.model.Scene;
import com.gamehub.view.GameView;

import java.util.List;

public interface GameService {
    void addGame(Game p);
    void updateGame(Game p);
    List<GameView> getGameViews();
    Game getGame(int id);
    void removeGame(int id);
    void removeGame(Game game);
    Choice getChoice(Scene scene, int id);
    MatrixVariant getVariant(String matrixPosition);
}
