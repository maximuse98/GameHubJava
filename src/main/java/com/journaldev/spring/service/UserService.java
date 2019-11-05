package com.journaldev.spring.service;

import com.journaldev.spring.controller.GameSession;
import com.journaldev.spring.model.Game;
import com.journaldev.spring.view.SessionView;
import com.journaldev.spring.model.User;
import com.journaldev.spring.view.UserView;
import org.springframework.http.ResponseEntity;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public void createUser(User user);
    public void deleteUser(User user);

    public ResponseEntity<byte[]> getUserBackground(String username) throws SQLException;
    public ResponseEntity<byte[]> getUserSprite(String username, int spriteId) throws SQLException;

    public void createSession(User creator, Game game);
    public void addUserToSession(User user, GameSession gameSession);
    public void deleteSession(User user);
    public void removeUser(String username);

    public User findUserByUsername(String username);
    public Blob findImageBySpriteId(int id, String username);
    public GameSession findSessionById(int id);

    public List<User> getUsers();
    public List<UserView> getUserViews();
    public List<GameSession> getSessions();
    public List<SessionView> getSessionViews();
}
