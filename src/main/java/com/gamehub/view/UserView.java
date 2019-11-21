package com.gamehub.view;

import com.gamehub.exception.NotFoundException;
import com.gamehub.model.User;

public class UserView {
    private String username;
    private String currentGameName;

    public UserView(User user) {
        this.username = user.getUsername();
        try {
            this.currentGameName = user.getCurrentSession().getGame().getName();
        }catch (NullPointerException | NotFoundException e){
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
