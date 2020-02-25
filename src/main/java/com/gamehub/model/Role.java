package com.gamehub.model;

import com.gamehub.entity.RoleEntity;

public class Role {

    private int id;
    private String name;

    public Role(RoleEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
