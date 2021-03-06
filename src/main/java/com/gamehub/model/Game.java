package com.gamehub.model;

import com.gamehub.entity.GameEntity;
import com.gamehub.view.GameView;
import com.gamehub.view.View;

import java.util.Set;


public class Game implements Model {

    private int id;
    private String name;
    private int playersCount;
    private String colorTheme;
    private Scene startScene1;
    private Scene startScene2;
    private Set<Role> roles;

    public Game(GameEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.playersCount = entity.getPlayersCount();
        this.colorTheme = entity.getColorTheme();
        this.startScene1 = new Scene(entity.getStartScene1());
        this.startScene2 = new Scene(entity.getStartScene2());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public Scene getStartScene1() {
        return startScene1;
    }

    public void setStartScene1(Scene startScene1) {
        this.startScene1 = startScene1;
    }

    public Scene getStartScene2() {
        return startScene2;
    }

    public void setStartScene2(Scene startScene2) {
        this.startScene2 = startScene2;
    }

    public String getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(String colourTheme) {
        this.colorTheme = colourTheme;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name +
                ", playersCount=" + playersCount +
                ", startScene1=" + startScene1 +
                ", startScene2=" + startScene2 +
                '}';
    }

    @Override
    public View createView() {
        return new GameView(id,name,playersCount,colorTheme);
    }
}
