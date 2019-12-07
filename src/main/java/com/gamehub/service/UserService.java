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
import java.util.Optional;

public interface UserService {
    void saveUser(String login, String password);
    void createUser(String username);

    ResponseEntity<byte[]> getUserBackground(String username) throws SQLException;
    ResponseEntity<byte[]> getUserSprite(String username, int spriteId) throws SQLException;

    void createSession(String creator, Game game);
    void addUserToSession(String username, int sessionId);
    void leaveSession(String username);
    void removeUser(String username);

    User getUser(String username);
    Blob getImage(int id, String username);
    Optional<GameSession> getSession(int id);

    List<User> getUsers();
    List<UserView> getUserViews();
    List<GameSession> getSessions();
    List<SessionView> getSessionViews();

    UserEntity getUserEntity(String username);

    void restoreConnection(String username) throws NotFoundException;
}
