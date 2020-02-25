package com.gamehub.service;

import com.gamehub.entity.ChoiceEntity;
import com.gamehub.entity.GameEntity;
import com.gamehub.entity.MatrixVariantEntity;
import com.gamehub.entity.SceneEntity;
import com.gamehub.model.Choice;
import com.gamehub.model.Game;
import com.gamehub.model.Scene;
import com.gamehub.view.View;

import java.util.List;

public interface GameService {
    void addGame(GameEntity p);
    void updateGame(GameEntity p);
    List<View> getGameViews();
    GameEntity getGame(int id);
    void removeGame(int id);
    void removeGame(GameEntity game);
    Choice getChoice(Scene scene, int id);
    MatrixVariantEntity getVariant(String matrixPosition);

    Game getGameModel(int id);
}
