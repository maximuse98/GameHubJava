package com.gamehub.service;

import com.gamehub.model.GameSession;
import com.gamehub.model.*;
import com.gamehub.view.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Сущность для перевода любых данных из типа model в тип view
 * model -> view
 *
 * @author maximuse98
 *
 **/

public class ViewService {

    public List<SessionView> createSessionViews(List<GameSession> sessions) {
        List<SessionView> sessionViews = new ArrayList<>(sessions.size());
        for (GameSession gameSession:sessions) {
            SessionView sessionView = new SessionView();
            sessionView.setId(gameSession.getId());

            Game game = gameSession.getGame();
            sessionView.setGameName(game.getName());
            sessionView.setCreatorUsername(gameSession.getCreator());
            sessionView.setGameSize(game.getPlayersCount());
            sessionView.setUsersCount(gameSession.getUsersCount());

            sessionViews.add(sessionView);
        }
        return sessionViews;
    }

    public List<GameView> createGameViews(List<Game> games) {
        List<GameView> gameViews = new ArrayList<>(games.size());
        for (Game game:games) {
            gameViews.add(createGameView(game));
        }
        return gameViews;
    }

    public List<UserView> createUserViews(List<User> users) {
        ArrayList<UserView> userViews = new ArrayList<>(users.size());
        for (User user : users) {
            UserView userView = new UserView();
            userView.setUsername(user.getName());
            try {
                userView.setCurrentGameName(user.getGameSession().getGame().getName());
            }catch (NullPointerException ignored){ }
            userViews.add(userView);
        }
        return userViews;
    }

    public SceneView createSceneView(Scene scene) {
        SceneView sceneView = new SceneView();
        String textView = "";
        String speakerView = "";
        String spriteIds = "";
        String sceneIds = "";
        String backgroundIds = "";
        String lastSceneType;
        do {
            textView = textView.concat(scene.getText().concat("|"));
            speakerView = speakerView.concat(scene.getSpeaker() == null ? ".|" : scene.getSpeaker().concat("|"));
            sceneIds = sceneIds.concat(scene.getId() + "|");
            backgroundIds = backgroundIds.concat(scene.getBackground().getId() + "|");

            for(Sprite s: scene.getSprites()){
                spriteIds = spriteIds.concat(s.getId() + ",");
            }
            spriteIds = spriteIds.substring(0, spriteIds.length() - 1);
            spriteIds = spriteIds.concat("|");
            if (scene.getType().equals("Quest")){
                for (Choice choice:scene.getChoices()) {
                    sceneView.addChoice(new ChoiceView(choice));
                }
            }
            sceneView.setType(scene.getType());
            lastSceneType = scene.getType();
            scene = scene.getNextScene();
        } while(scene != null);

        sceneView.setText(textView.substring(0, textView.length()-1));
        sceneView.setSpeakers(speakerView.substring(0, speakerView.length()-1));
        sceneView.setSprites(spriteIds.substring(0, spriteIds.length()-1));
        sceneView.setIds(sceneIds.substring(0, sceneIds.length()-1));
        sceneView.setBackgrounds(backgroundIds.substring(0, backgroundIds.length()-1));
        sceneView.setLastSceneType(lastSceneType);

        return sceneView;
    }

    public GameView createGameView(Game game){
        GameView gameView = new GameView();

        gameView.setId(game.getId());
        gameView.setName(game.getName());
        gameView.setPlayersCount(game.getPlayersCount());
        gameView.setColour(game.getColorTheme());

        return gameView;
    }
}
