package com.gamehub.model;

public class User {
    private String name;
    private Scene scene;
    private GameSession gameSession;
    private Choice choice;

    private boolean isSceneReady = false;

    public User(String name) {
        this.name = name;
    }

    public User(String name, Scene scene) {
        this.name = name;
        this.scene = scene;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scene getScene() {
        this.isSceneReady = false;
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public void sceneReady (){
        this.isSceneReady = true;
    }

    public boolean isSceneReady() {
        return isSceneReady;
    }
}
