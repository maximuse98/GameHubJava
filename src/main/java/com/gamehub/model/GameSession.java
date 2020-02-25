package com.gamehub.model;

import com.gamehub.view.SessionView;
import com.gamehub.view.View;

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
 * listeners - пользователи, что дали ответ
 *
 * Логика реализована только на двух игроков
 *
 * Поток Future пользователя из GameController ждет пока значение переменной hasNewScene у пользователя не станет true,
 * затем GameController забирает следующую сцену каждого юзера и ставит hasNewScene = false
 *
 * Каждый пользователь может иметь только один поток Future. Если пользователь, который ожидает ответ другого игрока,
 * перезагрузил страницу и сделал иной выбор, то будет засчитан первый выбор.
 *
 *
 * @author maximuse98
 *
 **/

public class GameSession implements Model {

    private int id;
    private Game game;
    private int gameSize;
    private User creator;

    private Set<User> users = new HashSet<>();
    private Set<User> listeners = new HashSet<>();
    private int answeredCount;

    public GameSession(Game game, User user) {
        this.id = this.hashCode();
        this.game = game;
        this.creator = user;

        gameSize = game.getPlayersCount();
        addUser(user);
    }

    public void addUser(User user){
        user.setGameSession(this);
        if(users.isEmpty()) {
            user.setScene(game.getStartScene1());
        } else{
            user.setScene(game.getStartScene2());
        }
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    public void addAnswer(User user, Choice choice){
        user.setChoice(choice);
        listeners.add(user);

        this.answeredCount += 1;
        if (answeredCount == gameSize) {
            generateNextScenes();
            for (User listener: listeners) {
                listener.sceneReady();
            }
            listeners.clear();
        }
    }

    public boolean isAvailable(){
        return users.size() < gameSize;
    }

    public boolean isEmpty(){
        return users.size() == 0;
    }

    private void generateNextScenes(){
        String matrixPosition = "";
        for (User user: users) {
            matrixPosition = matrixPosition.concat(Integer.toString(user.getChoice().getMatrixNum()));
            matrixPosition = matrixPosition.concat("-");
        }
        matrixPosition = matrixPosition.substring(0, matrixPosition.length() - 1);

        Iterator<User> iterator = users.iterator();
        User user1 = iterator.next();
        Set<MatrixVariant> matrixVariants = user1.getChoice().getScene().getMatrixVariantList();

        for (MatrixVariant matrixVariant: matrixVariants){
            if(matrixVariant.getMatrixPosition().equals(matrixPosition)){
                user1.setScene(matrixVariant.getNextScene1());
                iterator.next().setScene(matrixVariant.getNextScene2());
            }
        }
    }

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public User getCreator() {
        return creator;
    }

    @Override
    public View createView() {
        return new SessionView(id,game.getName(),creator.getName(),game.getPlayersCount(),answeredCount);
    }
}
