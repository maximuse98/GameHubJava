package com.journaldev.spring.service;

import com.journaldev.spring.controller.GameSession;
import com.journaldev.spring.exception.NotFoundException;
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

    ResponseEntity<byte[]> getUserBackground(String username) throws SQLException, NotFoundException;
    ResponseEntity<byte[]> getUserSprite(String username, int spriteId) throws SQLException, NotFoundException;

    void createSession(String creator, Game game) throws NotFoundException ;
    void addUserToSession(String username, int sessionId) throws NotFoundException;
    void leaveSession(String username) throws NotFoundException;
    void removeUser(String username) throws NotFoundException;

    User getUser(String username) throws NotFoundException;
    Blob getImage(int id, String username) throws NotFoundException;
    GameSession getSession(int id) throws NotFoundException;

    List<User> getUsers();
    List<UserView> getUserViews();
    List<GameSession> getSessions();
    List<SessionView> getSessionViews();
}
