package com.gamehub.model;

import com.gamehub.entity.SpriteEntity;

public class Sprite {

    private int id;
    private ImageResource imageResource;

    public Sprite(SpriteEntity entity) {
        this.id = entity.getId();
        this.imageResource = new ImageResource(entity.getImageResource());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageResource getImageResource() {
        return imageResource;
    }

    public void setImageResource(ImageResource imageResource) {
        this.imageResource = imageResource;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "id=" + id +
                ", imageResource=" + imageResource +
                '}';
    }
}
