package com.gamehub.view;

import com.gamehub.model.Game;

public class GameView {
    private int id;
    private String name;
    private int playersCount;

    public GameView(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.playersCount = game.getPlayersCount();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPlayersCount() {
        return playersCount;
    }
}
