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
    void createUser(User user);
    void deleteUser(User user);

    ResponseEntity<byte[]> getUserBackground(String username) throws SQLException;
    ResponseEntity<byte[]> getUserSprite(String username, int spriteId) throws SQLException;

    void createSession(User creator, Game game);
    void addUserToSession(User user, GameSession gameSession);
    void leaveSession(User user);
    void removeUser(String username);

    User getUser(String username);
    Blob getImage(int id, String username);
    GameSession getSession(int id);

    List<User> getUsers();
    List<UserView> getUserViews();
    List<GameSession> getSessions();
    List<SessionView> getSessionViews();
}
