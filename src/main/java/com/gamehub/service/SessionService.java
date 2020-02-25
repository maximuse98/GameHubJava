package com.gamehub.service;

import com.gamehub.model.*;
import com.gamehub.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;

import javax.inject.Provider;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Future;

public class SessionService {
    private List<GameSession> sessions = new LinkedList<>();
    private List<User> users = new LinkedList<>();

    private GameService gameService;
    private Provider<User> userProvider;

    @Autowired
    public void prepare(GameService gameService, Provider<User> userProvider){
        this.gameService = gameService;
        this.userProvider = userProvider;
    }

    public void createSession(String username, Game game){
        if(isUserExist(username)) return;

        User user = userProvider.get();
        user.setName(username);
        user.setScene(game.getStartScene1());
        users.add(user);

        GameSession session = new GameSession(game, user);
        sessions.add(session);
    }

    public void addUserToSession(String username, int sessionId){
        if(isUserExist(username)) return;

        User user = userProvider.get();
        user.setName(username);
        GameSession session = findSession(sessionId);

        if(session.isAvailable()) {
            session.addUser(user);
            users.add(user);
        }
    }

    public Future<Boolean> addChoice(int choiceId){
        User user = userProvider.get();
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

        User user = userProvider.get();
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

    public ResponseEntity<byte[]> getUserBackground(int sceneId) throws SQLException{
        User user = userProvider.get();
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
    public ResponseEntity<byte[]> getUserSprite(int spriteId, int sceneId) throws SQLException {
        Blob image = getImage(spriteId, sceneId);
        if(image == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        byte[] imageContent = image.getBytes(1, (int) image.length());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    private Blob getImage(int id, int sceneId){
        User user = userProvider.get();
        Scene currentScene = user.getScene();
        List<Sprite> sprites = new LinkedList<>();
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
    private void findUser(String username){
        for (User user: users){
            if(user.getName().equals(username)) return;
        }
        throw new NullPointerException();
    }

    public List<View> getSessionViews() {
        return getViews(sessions);
    }
    public List<View> getUserViews() {
        return getViews(users);
    }

    public View getViewByUser(View view){
        return view.createByUser(userProvider.get());
    }
    public List<View> getViews(List<? extends Model> models){
        List<View> views = new ArrayList<>(models.size());
        for (Model model:models) {
            views.add(model.createView());
        }
        return views;
    }
}
