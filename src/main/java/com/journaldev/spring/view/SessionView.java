package com.journaldev.spring.view;

import com.journaldev.spring.controller.GameSession;
import com.journaldev.spring.model.Game;

public class SessionView {
    private int id;
    private String gameName;
    private String creatorUsername;
    private int gameSize;
    private int usersCount;

    public SessionView(GameSession gameSession) {
        Game game = gameSession.getGame();
        this.id = gameSession.getId();
        this.gameName = game.getName();
        this.creatorUsername = gameSession.getCreator().getUsername();
        this.gameSize = game.getPlayersCount();
        this.usersCount = gameSession.getUsersCount() + gameSession.getExitUsersCount();
    }

    public int getId() {
        return id;
    }

    public String getGameName() {
        return gameName;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public int getGameSize() {
        return gameSize;
    }

    public int getUsersCount() {
        return usersCount;
    }
}
