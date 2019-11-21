package com.gamehub.view;

import com.gamehub.model.Sprite;

public class SpriteView {
    private int id;
    private int positionX;
    private int positionY;

    public SpriteView(Sprite sprite) {
        this.id = sprite.getId();
        this.positionX = sprite.getPositionX();
        this.positionY = sprite.getPositionY();
    }

    public int getId() {
        return id;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
