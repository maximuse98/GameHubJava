package com.gamehub.dao;

import com.gamehub.entity.UserEntity;

public interface UserDAO {
    void addUser(UserEntity user);
    UserEntity getUser(String username);
    void deleteUser(UserEntity user);
}
