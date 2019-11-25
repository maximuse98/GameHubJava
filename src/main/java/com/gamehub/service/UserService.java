package com.gamehub.service;

import com.gamehub.controller.GameSession;
import com.gamehub.entity.UserEntity;
import com.gamehub.exception.NotFoundException;
import com.gamehub.model.Game;
import com.gamehub.view.SessionView;
import com.gamehub.model.User;
import com.gamehub.view.UserView;
import org.springframework.http.ResponseEntity;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void saveUser(String login, String password);
    void createUser(String username);

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

    UserEntity getUserEntity(String username);

    void restoreConnection(String username) throws NotFoundException;
}
