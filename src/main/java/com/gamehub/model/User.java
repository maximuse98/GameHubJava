package com.gamehub.model;

import com.gamehub.controller.GameSession;
import com.gamehub.exception.ExceptionType;
import com.gamehub.exception.NotFoundException;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

public class User {
    private String username;
    private String password;

    private GameSession currentSession;
    private Scene currentScene;
    private Choice currentChoice;
    private boolean hasNewScene;

    private Future<String> currentFuture;

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
        this.currentFuture = null;
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
        if(currentFuture != null){
            currentFuture.cancel(true);
        }
        this.currentScene = null;
        this.currentSession = null;
        this.hasNewScene = false;
        this.currentChoice = null;
        this.currentFuture = null;
    }

    public boolean hasNewScene() {
        return hasNewScene;
    }

    public void setHasNewScene(boolean hasNewScene) {
        this.hasNewScene = hasNewScene;
    }

    public Future<String> getCurrentFuture() {
        return currentFuture;
    }

    public void setCurrentFuture(Future<String> currentFuture) {
        this.currentFuture = currentFuture;
    }

    public Future<String> doAsync(int choiceId, String successUrl){
        try {
            currentSession.addChoice(this, choiceId);
            while(!hasNewScene){
                //this.getCurrentSession();
                Thread.sleep(1000);
            }
            //System.out.println("Finish method - " + Thread.currentThread().getName());
            return new AsyncResult<>(successUrl);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new AsyncResult<>(null);
        }
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
