package com.journaldev.spring.model;

import com.journaldev.spring.controller.GameSession;

public class User {
    private String username;
    private String password;

    private GameSession currentSession;
    private Scene currentScene;
    private Choice currentChoice;
    private boolean hasNewScene;

    public User() {
    }

    public User(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GameSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(GameSession currentSession) {
        this.currentSession = currentSession;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.hasNewScene = true;
        this.currentScene = currentScene;
    }

    public Choice getCurrentChoice() {
        return currentChoice;
    }

    public void setCurrentChoice(Choice currentChoice) {
        this.currentChoice = currentChoice;
    }

    public void clearSession(){
        this.currentChoice = null;
        this.currentScene = null;
        this.currentSession = null;
        this.hasNewScene = false;
    }

    public boolean hasNewScene() {
        return hasNewScene;
    }

    public void setHasNewScene(boolean hasNewScene) {
        this.hasNewScene = hasNewScene;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", currentSession=" + currentSession +
                ", currentScene=" + currentScene +
                ", currentChoice=" + currentChoice +
                '}';
    }
}
