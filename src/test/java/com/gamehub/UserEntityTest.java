package com.gamehub;

import com.gamehub.dao.UserDAO;
import com.gamehub.entity.UserEntity;
import com.gamehub.entity.UserRoles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/spring-security-config.xml"})
public class UserEntityTest {
    private UserDAO userDAO;
    private BCryptPasswordEncoder encoder;

    private final String TEST_LOGIN = "test";
    private final String TEST_PASSWORD = "12345";

    @Autowired
    public void prepare(UserDAO userDAO, BCryptPasswordEncoder encoder) {
        this.userDAO = userDAO;
        this.encoder = encoder;
    }

    @Test
    @Transactional
    public void addUser(){
        UserEntity user = new UserEntity();
        user.setLogin(TEST_LOGIN);
        user.setPassword(encoder.encode(TEST_PASSWORD));
        user.setUserRole(UserRoles.ROLE_USER);

        userDAO.addUser(user);
    }

    @Test
    @Transactional
    public void getUser(){
        UserEntity testUser = userDAO.getUser(TEST_LOGIN);
        Assert.assertTrue(encoder.matches(TEST_PASSWORD, testUser.getPassword()));
    }

    @Test
    @Transactional
    public void removeUser(){
        userDAO.deleteUser(TEST_LOGIN);
    }
}
