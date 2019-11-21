package com.gamehub.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name="name", updatable = false)
    private String login;
    private String password;

    @Transient
    private UserRoles userRole;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRoles getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoles userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "login='" + login + '\'' +
                ", password='" + password +
                '}';
    }
}
