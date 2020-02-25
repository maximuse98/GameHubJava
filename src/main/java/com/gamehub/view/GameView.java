package com.gamehub.view;

import com.gamehub.model.User;

public class GameView implements View{
    private int id;
    private String name;
    private int playersCount;
    private String color;

    public GameView() {
    }

    public GameView(int id, String name, int playersCount, String color) {
        this.id = id;
        this.name = name;
        this.playersCount = playersCount;
        this.color = color;
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

    @Override
    public View createByUser(User user) {
        return user.getGameSession().getGame().createView();
    }
}
