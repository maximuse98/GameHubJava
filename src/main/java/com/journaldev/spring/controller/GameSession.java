package com.journaldev.spring.controller;

import com.journaldev.spring.model.Choice;
import com.journaldev.spring.model.Game;
import com.journaldev.spring.model.MatrixVariant;
import com.journaldev.spring.model.User;

import java.util.*;

/**
 *
 * GameSession - сессия игры, объеденяет игроков, играющих в одну игру
 *
 * id - уникальный id сессии
 * game - текущая игра
 * usersCount -  сколько пользователей в данный момент присоеденились к сессии
 * gameSize - сколько пользователей всего может подключиться к игре (как правило - 2)
 * answeredUsers - сколько пользователей прислали ответ
 * creator - создатель игры
 *
 * Логика реализована только на двух игроков
 *
 * Поток пользователя из GameController ждет пока значение переменной hasNewScene у пользователя не станет true,
 * затем GameController забирает следующую сцену каждого юзера и ставит hasNewScene = false
 *
 * @author maximuse98
 *
 **/

public class GameSession {

    private int id;
    private Game game;
    private int usersCount;
    private int gameSize;
    private int answeredUsers;
    private User creator;

    private List<User> users = new LinkedList<User>();

    public GameSession(Game game, User user) {
        this.id = this.hashCode();
        this.game = game;
        this.usersCount = 1;
        this.creator = user;
        users.add(user);

        gameSize = game.getPlayersCount();
    }

    public void addUser(User user){
        if(usersCount <= gameSize){
            user.setCurrentSession(this);
            user.setCurrentScene(game.getStartScene2());
            users.add(user);
            usersCount++;
        }
    }

    public void removeUser(User user){
        user.clearSession();
        users.remove(user);
        usersCount--;
    }

    public void addChoice(User user, int choiceId){
        Set<Choice> choices = user.getCurrentScene().getChoices();
        for (Choice choice:choices) {
            if(choiceId==choice.getId()){
                user.setCurrentChoice(choice);
            }
        }
        answeredUsers++;
        if(answeredUsers == gameSize) {
            this.generateNextScenes();
            answeredUsers = 0;
        }
    }

    private void generateNextScenes(){
        String matrixPosition = "";
        for (User user:users) {
            matrixPosition = matrixPosition.concat(Integer.toString(user.getCurrentChoice().getMatrixNum()));
            matrixPosition = matrixPosition.concat("-");
        }
        matrixPosition = matrixPosition.substring(0, matrixPosition.length() - 1);
        System.out.println("Generated position:"+matrixPosition);

        this.generateNextScene(0, matrixPosition);

        String[] split = matrixPosition.split("-");
        matrixPosition = split[1]+"-"+split[0];

        System.out.println("Generated next position:"+matrixPosition);
        this.generateNextScene(1, matrixPosition);
    }

    private void generateNextScene(int userNum, String matrixPosition){
        User user = users.get(userNum);
        Set<MatrixVariant> matrixVariants = user.getCurrentScene().getMatrixVariantList();
        for (MatrixVariant matrixVariant: matrixVariants){
            if(matrixVariant.getMatrixPosition().equals(matrixPosition)){
                if (userNum==0){
                    user.setCurrentScene(matrixVariant.getNextScene1());
                }
                else {
                    user.setCurrentScene(matrixVariant.getNextScene2());
                }
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getAnsweredUsers() {
        return answeredUsers;
    }

    public User getCreator() {
        return creator;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public int getGameSize() {
        return gameSize;
    }

    public int getId() {
        return id;
    }
}
