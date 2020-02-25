package com.gamehub.view;

import com.gamehub.model.User;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 *
 * Используется в двух направлениях: в виде формы для регистрации вместе с Hibernate Validator или
 * в качестве информации о пользователе, возвращаемое клиенту при успешной авторизации, созданой
 * через Spring Security и com.gamehub.security.User
 *
 * @author maximuse98
 *
 **/

public class UserView implements View {

    @NotEmpty(message = "Login is required")
    private String username;

    @Size(min = 4, message = "Password should contain at least 4 symbols")
    private String password;

    @NotEmpty(message = "Password confirm is required")
    private String passwordConfirm;

    private String currentGameName;

    public String getUsername() {
        return username;
    }

    public String getCurrentGameName() {
        return currentGameName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setCurrentGameName(String currentGameName) {
        this.currentGameName = currentGameName;
    }

    @Override
    public View createByUser(User user) {
        return user.createView();
    }
}
