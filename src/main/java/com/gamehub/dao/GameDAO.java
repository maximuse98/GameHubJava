package com.gamehub.dao;

import com.gamehub.entity.ChoiceEntity;
import com.gamehub.entity.GameEntity;
import com.gamehub.entity.MatrixVariantEntity;
import com.gamehub.model.Choice;
import com.gamehub.model.Game;
import com.gamehub.model.MatrixVariant;

import java.util.List;

public interface GameDAO {
    void addGame(GameEntity p);
    void updateGame(GameEntity p);
    List<GameEntity> listGames();
    GameEntity getGameById(int id);
    void removeGame(int id);
    void removeGame(GameEntity game);
    ChoiceEntity getChoiceById(int id);
    MatrixVariantEntity getVariantByPosition(String matrixPosition);
}
