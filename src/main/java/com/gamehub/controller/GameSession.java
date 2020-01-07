package com.gamehub.controller;

import com.gamehub.model.*;

import java.util.*;

/**
 *
 * GameSession - сессия игры, объеденяет игроков, играющих в одну игру
 *
 * id - уникальный id сессии
 * game - текущая игра
 * gameSize - сколько пользователей всего может подключиться к игре
 * answeredUsers - сколько пользователей прислало ответ
 * creator - создатель игры
 * exitUsersCount - сколько пользователей успешно завершили игру
 * users - участники игры
 * answeredUsers - пользователи, что дали ответ
 *
 * Логика реализована только на двух игроков
 *
 * Поток Future пользователя из GameController ждет пока значение переменной hasNewScene у пользователя не станет true,
 * затем GameController забирает следующую сцену каждого юзера и ставит hasNewScene = false, currentFuture = null
 *
 * Каждый пользователь может иметь только один поток Future. Если пользователь, который ожидает ответ другого игрока,
 * перезагрузил страницу и сделал иной выбор, то будет засчитан последний выбор.
 *
 *
 * @author maximuse98
 *
 **/

public class GameSession {

    private int id;
    private Game game;
    private int gameSize;
    private User creator;
    private int exitUsersCount;

    private Set<User> users = new HashSet<>();
    private Set<User> answeredUsers = new HashSet<>();

    public GameSession(Game game, User user) {
        this.id = this.hashCode();
        this.game = game;
        this.exitUsersCount = 0;
        this.creator = user;
        users.add(user);

        gameSize = game.getPlayersCount();
    }

    public void addUser(User user){
        if(users.size() <= gameSize){
            user.setCurrentSession(this);
            user.setCurrentScene(game.getStartScene2());

            Iterator<Role> roleIterator = game.getRoles().iterator();
            roleIterator.next();

            user.setCurrentRole(roleIterator.next().getName());
            users.add(user);
        }
    }

    public void removeUser(User user){
        if(user.getCurrentChoice()!=null){
            answeredUsers.remove(user);
        }
        users.remove(user);
        user.clearSession();
    }

    public void addChoice(User user, int choiceId){
        Set<Choice> choices = user.getCurrentScene().getChoices();
        for (Choice choice:choices) {
            if(choiceId==choice.getId()){
                user.setCurrentChoice(choice);
            }
        }
        answeredUsers.add(user);
        if(answeredUsers.size() == gameSize) {
            this.generateNextScenes();
            answeredUsers.clear();
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

        Iterator<User> iterator = users.iterator();
        User user1 = iterator.next();
        Set<MatrixVariant> matrixVariants = user1.getCurrentScene().getMatrixVariantList();
        for (MatrixVariant matrixVariant: matrixVariants){
            if(matrixVariant.getMatrixPosition().equals(matrixPosition)){
                user1.setCurrentScene(matrixVariant.getNextScene1());
                iterator.next().setCurrentScene(matrixVariant.getNextScene2());
            }
        }

        //this.generateNextScene(iterator.next(), matrixPosition, true);

        //String[] split = matrixPosition.split("-");
        //matrixPosition = split[1]+"-"+split[0];

        //System.out.println("Generated next position:"+matrixPosition);
        //this.generateNextScene(iterator.next(), matrixPosition, false);
    }

    private void generateNextScene(User user, String matrixPosition, boolean isFirst){
        Set<MatrixVariant> matrixVariants = user.getCurrentScene().getMatrixVariantList();
        for (MatrixVariant matrixVariant: matrixVariants){
            if(matrixVariant.getMatrixPosition().equals(matrixPosition)){
                if (isFirst){
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

    public User getCreator() {
        return creator;
    }

    public int getUsersCount() {
        return users.size();
    }

    public int getGameSize() {
        return gameSize;
    }

    public int getId() {
        return id;
    }

    public int getExitUsersCount() {
        return exitUsersCount;
    }

    public void setExitUsersCount(int exitUsersCount) {
        this.exitUsersCount = exitUsersCount;
    }
}
