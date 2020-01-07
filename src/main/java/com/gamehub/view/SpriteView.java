package com.gamehub.view;

import com.gamehub.model.Sprite;

public class SpriteView {
    private int id;

    public SpriteView(Sprite sprite) {
        this.id = sprite.getId();
    }

    public int getId() {
        return id;
    }
}
