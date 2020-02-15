package com.gamehub.service;

import com.gamehub.entity.UserEntity;

public interface UserService {
    void saveUser(String login, String password);
    UserEntity getUserEntity(String username);
}
