package com.gamehub.dao;

import com.gamehub.entity.UserEntity;

public interface UserDAO {
    void addUser(UserEntity user);
    void deleteUser(String username);
    UserEntity getUser(String username);
}
