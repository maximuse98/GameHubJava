package com.gamehub.service;

import com.gamehub.model.*;
import com.gamehub.view.GameView;
import com.gamehub.view.SceneView;
import com.gamehub.view.SessionView;
import com.gamehub.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public class SessionService {
    private List<GameSession> sessions = new LinkedList<>();
    private List<User> users = new LinkedList<>();

    private ViewService viewService;
    private GameService gameService;

    @Autowired
    @Qualifier(value = "viewService")
    public void setViewService(ViewService viewService) {
        this.viewService = viewService;
    }

    @Autowired
    @Qualifier(value = "gameService")
    public void setGameService(GameService gameService){
        this.gameService = gameService;
    }


    public void createSession(String username, Game game){
        if(isUserExist(username)) return;
        User user = new User(username, game.getStartScene1());
        users.add(user);

        GameSession session = new GameSession(game, user);
        sessions.add(session);
    }

    public void addUserToSession(String username, int sessionId){
        if(isUserExist(username)) return;
        User user = new User(username);
        GameSession session = findSession(sessionId);

        if(session.isAvailable()) {
            session.addUser(user);
            users.add(user);
        }
    }

    public Future<Boolean> addChoice(String username, int choiceId){
        User user = findUser(username);
        Choice choice = gameService.getChoice(user.getScene(), choiceId);

        user.getGameSession().addAnswer(user, choice);

        try {
            while (!user.isSceneReady()) {
                Thread.sleep(1000);
            }
            return new AsyncResult<>(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new AsyncResult<>(false);
        }
    }

    public void leaveSession(String username){
        if(!isUserExist(username)) return;

        User user = findUser(username);
        GameSession session = user.getGameSession();

        session.removeUser(user);
        users.remove(user);

        if(session.isEmpty())
            sessions.remove(session);
    }

    public boolean isUserExist(String username){
        try{
            findUser(username);
            return true;
        }catch (NullPointerException e){
            return false;
        }
    }

    public ResponseEntity<byte[]> getUserBackground(String username, int sceneId) throws SQLException{
        User user = findUser(username);
        Scene currentScene = user.getScene();
        Blob image = null;
        while(currentScene != null){
            if(currentScene.getId() == sceneId){
                image = currentScene.getBackground().getImage();
                break;
            }
            currentScene = currentScene.getNextScene();
        }
        if (image == null){
            return null;
        }
        byte[] imageContent = image.getBytes(1, (int) image.length());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }
    public ResponseEntity<byte[]> getUserSprite(String username, int spriteId, int sceneId) throws SQLException {
        Blob image = getImage(spriteId, username, sceneId);
        if(image == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        byte[] imageContent = image.getBytes(1, (int) image.length());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    private Blob getImage(int id, String username, int sceneId){
        User user = findUser(username);
        Scene currentScene = user.getScene();
        Set<Sprite> sprites = new HashSet<>();
        while(currentScene != null){
            if(currentScene.getId() == sceneId){
                sprites = currentScene.getSprites();
                break;
            }
            currentScene = currentScene.getNextScene();
        }
        for (Sprite sprite:sprites) {
            if(sprite.getId() == id){
                return sprite.getImageResource().getImage();
            }
        }
        return null;
    }

    private GameSession findSession(int sessionId){
        for (GameSession session: sessions) {
            if(session.getId() == sessionId) return session;
        }
        throw new NullPointerException();
    }
    private User findUser(String username){
        for (User user: users){
            if(user.getName().equals(username)) return user;
        }
        throw new NullPointerException();
    }

    public List<SessionView> getSessionViews() {
        return viewService.createSessionViews(sessions);
    }
    public List<UserView> getUserViews() {
        return viewService.createUserViews(users);
    }
    public SceneView getSceneView(String username){
        return viewService.createSceneView(findUser(username).getScene());
    }
    public GameView getGameView(String username){
        return viewService.createGameView(findUser(username).getGameSession().getGame());
    }
}
