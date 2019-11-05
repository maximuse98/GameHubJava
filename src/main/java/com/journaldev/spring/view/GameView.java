package com.journaldev.spring.view;

import com.journaldev.spring.model.Game;

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
