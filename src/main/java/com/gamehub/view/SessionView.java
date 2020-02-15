package com.gamehub.view;

public class SessionView {
    private int id;
    private String gameName;
    private String creatorUsername;
    private int gameSize;
    private int usersCount;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }
}
