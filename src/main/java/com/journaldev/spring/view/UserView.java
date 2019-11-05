package com.journaldev.spring.view;

import com.journaldev.spring.model.User;

public class UserView {
    private String username;
    private String currentGameName;

    public UserView(User user) {
        this.username = user.getUsername();
        try {
            this.currentGameName = user.getCurrentSession().getGame().getName();
        }catch (NullPointerException e){
            this.currentGameName = null;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getCurrentGameName() {
        return currentGameName;
    }
}
