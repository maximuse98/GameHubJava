package com.gamehub.service;

import com.gamehub.dao.UserDAO;
import com.gamehub.entity.UserEntity;
import com.gamehub.entity.UserRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {
    //private static final Logger logger = LoggerFactory.getLogger(GameDAOImpl.class);

    private UserDAO userDAO;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Transactional
    public void saveUser(String login, String password) {
        UserEntity user = new UserEntity();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setLogin(login);
        user.setUserRole(UserRoles.ROLE_USER);

        userDAO.addUser(user);
    }

    @Transactional
    public UserEntity getUserEntity(String username) {
        return userDAO.getUser(username);
    }
}
