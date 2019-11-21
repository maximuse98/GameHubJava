package com.gamehub.model;

import com.gamehub.controller.GameSession;
import com.gamehub.exception.ExceptionType;
import com.gamehub.exception.NotFoundException;

public class User {
    private String username;
    private String password;

    private GameSession currentSession;
    private Scene currentScene;
    private Choice currentChoice;
    private boolean hasNewScene;

    public User() {
        this.hasNewScene = false;
    }

    public User(String username){
        this.username = username;
        this.hasNewScene = false;
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

    public GameSession getCurrentSession() throws NotFoundException {
        if(currentSession == null){
            throw new NotFoundException(ExceptionType.SESSION);
        }
        return currentSession;
    }

    public void setCurrentSession(GameSession currentSession) {
        this.currentSession = currentSession;
    }

    public Scene getCurrentScene() {
        this.hasNewScene = false;
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

    public void clearSession() {
        this.currentScene = null;
        this.currentSession = null;
        this.hasNewScene = false;
        this.currentChoice = null;
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
