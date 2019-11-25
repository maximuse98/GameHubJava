package com.gamehub.service;

import com.gamehub.controller.GameSession;
import com.gamehub.dao.UserDAO;
import com.gamehub.entity.UserEntity;
import com.gamehub.exception.ExceptionType;
import com.gamehub.exception.NotFoundException;
import com.gamehub.model.Game;
import com.gamehub.model.Scene;
import com.gamehub.model.Sprite;
import com.gamehub.view.SessionView;
import com.gamehub.model.User;
import com.gamehub.view.UserView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //private static final Logger logger = LoggerFactory.getLogger(GameDAOImpl.class);

    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void createUser(String username) {
        User user = new User(username);
        try {
            this.getUser(username);
        } catch (NotFoundException e){
            users.add(user);
        }
    }

    @Override
    @Transactional
    public void saveUser(String login, String password) {
        UserEntity user = new UserEntity();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setLogin(login);

        userDAO.addUser(user);
    }

    @Override
    public ResponseEntity<byte[]> getUserBackground(String username) throws SQLException, NotFoundException {
        User user = this.getUser(username);
        Blob image = user.getCurrentScene().getBackground().getImage();

        byte[] imageContent = image.getBytes(1, (int) image.length());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getUserSprite(String username, int spriteId) throws SQLException, NotFoundException {
        Blob image = this.getImage(spriteId, username);

        byte[] imageContent = image.getBytes(1, (int) image.length());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    public void removeUser(String username) throws NotFoundException {
        User user = this.getUser(username);
        try{
            GameSession currentSession = user.getCurrentSession();
            currentSession.removeUser(user);
        }catch (NotFoundException e){
            user.clearSession();
        }
        users.remove(user);
    }

    @Override
    public void createSession(String username, Game game) throws NotFoundException {
        User user = this.getUser(username);
        GameSession session = new GameSession(game, user);

        Scene startScene = game.getStartScene1();

        user.setCurrentSession(session);
        user.setCurrentScene(startScene);
        user.setHasNewScene(false);

        sessions.add(session);
    }

    @Override
    public void addUserToSession(String username, int sessionId) throws NotFoundException {
        GameSession session = this.getSession(sessionId);
        User user = this.getUser(username);
        session.addUser(user);
    }

    @Override
    public void leaveSession(String username) throws NotFoundException {
        User user = this.getUser(username);
        GameSession currentSession = user.getCurrentSession();
        if (user.getCurrentScene().getType().equals("Result")){
            currentSession.setExitUsersCount(currentSession.getExitUsersCount()+1);
        }
        if(currentSession.getUsersCount() - currentSession.getExitUsersCount() == 1){
            user.clearSession();
            sessions.remove(currentSession);
        } else{
            currentSession.removeUser(user);
        }
    }

    @Override
    public User getUser(String username) throws NotFoundException {
        for (User user:users) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        throw new NotFoundException(ExceptionType.USER);
    }

    @Override
    public Blob getImage(int id, String username) throws NotFoundException {
        User user = this.getUser(username);
        Set<Sprite> sprites = user.getCurrentScene().getSprites();
        for (Sprite sprite:sprites) {
            if(sprite.getId() == id){
                return sprite.getImageResource().getImage();
            }
        }
        throw new NotFoundException(ExceptionType.IMAGE);
    }

    @Override
    public GameSession getSession(int id) throws NotFoundException {
        for (GameSession session:sessions) {
            if(session.getId()==id){
                return session;
            }
        }
        throw new NotFoundException(ExceptionType.SESSION);
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

    @Override
    @Transactional
    public UserEntity getUserEntity(String username) {
        return userDAO.getUser(username);
    }

    @Override
    public void restoreConnection(String username) throws NotFoundException {
        this.createUser(username);

        User user = this.getUser(username);
        user.getCurrentSession();
    }
}
