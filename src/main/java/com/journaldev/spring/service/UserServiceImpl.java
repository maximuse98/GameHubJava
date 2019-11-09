package com.journaldev.spring.service;

import com.journaldev.spring.controller.GameSession;
import com.journaldev.spring.dao.GameDAOImpl;
import com.journaldev.spring.model.Game;
import com.journaldev.spring.model.Scene;
import com.journaldev.spring.model.Sprite;
import com.journaldev.spring.view.SessionView;
import com.journaldev.spring.model.User;
import com.journaldev.spring.view.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private List<User> users = new LinkedList<>();
    private List<GameSession> sessions = new LinkedList<>();

    private static final Logger logger = LoggerFactory.getLogger(GameDAOImpl.class);

    @Override
    public void createUser(User user) {
        users.add(user);
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }

    @Override
    public ResponseEntity<byte[]> getUserBackground(String username) throws SQLException {
        User user = this.getUser(username);
        Blob image = user.getCurrentScene().getBackground().getImage();

        byte[] imageContent = image.getBytes(1, (int) image.length());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getUserSprite(String username, int spriteId) throws SQLException {
        Blob image = this.getImage(spriteId, username);

        byte[] imageContent = image.getBytes(1, (int) image.length());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    public void removeUser(String username){
        User user = this.getUser(username);
        users.remove(user);
    }

    @Override
    public void createSession(User creator, Game game) {
        GameSession session = new GameSession(game, creator);

        Scene startScene = game.getStartScene1();

        creator.setCurrentSession(session);
        creator.setCurrentScene(startScene);
        creator.setHasNewScene(false);

        sessions.add(session);
    }

    @Override
    public void addUserToSession(User user, GameSession session) {
        session.addUser(user);
    }

    @Override
    public void leaveSession(User user) {
        try {
            GameSession currentSession = user.getCurrentSession();
            if(currentSession.getUsersCount() == 1){
                user.clearSession();
                sessions.remove(currentSession);
            } else{
                currentSession.removeUser(user);
            }
        } catch (NullPointerException e){
            logger.info("Empty session for user:"+user.getUsername());
        }
    }

    @Override
    public User getUser(String username){
        for (User user:users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    @Override
    public Blob getImage(int id, String username) {
        User user = this.getUser(username);
        Set<Sprite> sprites = user.getCurrentScene().getSprites();
        for (Sprite sprite:sprites) {
            if(sprite.getId() == id){
                return sprite.getImageResource().getImage();
            }
        }
        return null;
    }

    @Override
    public GameSession getSession(int id) {
        for (GameSession session:sessions) {
            if(session.getId()==id){
                return session;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public List<UserView> getUserViews(){
        List<User> users = this.getUsers();
        ArrayList<UserView> userViews = new ArrayList<>(users.size());
        for (User user:users) {
            userViews.add(new UserView(user));
        }
        return userViews;
    }

    @Override
    public List<GameSession> getSessions() {
        return sessions;
    }

    @Override
    public List<SessionView> getSessionViews() {
        List<GameSession> sessions = this.getSessions();
        List<SessionView> sessionViews = new ArrayList<>(sessions.size());
        for (GameSession gameSession:sessions) {
            sessionViews.add(new SessionView(gameSession));
        }
        return sessionViews;
    }
}
