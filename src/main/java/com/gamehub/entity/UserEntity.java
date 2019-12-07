package com.gamehub.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name="name", updatable = false)
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

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
        return role;
    }

    public void setUserRole(UserRoles userRole) {
        this.role = userRole;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "login='" + login + '\'' +
                ", password='" + password +
                '}';
    }
}
