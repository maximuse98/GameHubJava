package com.gamehub.view;

public class GameView {
    private int id;
    private String name;
    private int playersCount;
    private String color;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public String getColor() {
        return color;
    }

    public void setColour(String colour) {
        this.color = colour;
    }
}
