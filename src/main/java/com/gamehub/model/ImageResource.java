package com.gamehub.model;

import com.gamehub.entity.ImageResourceEntity;

import javax.persistence.*;
import java.sql.Blob;

public class ImageResource {

    private int id;
    private String path;
    private Blob image;

    @Transient
    private int jsonId;

    public ImageResource(ImageResourceEntity entity) {
        this.id = entity.getId();
        this.path = entity.getPath();
        this.image = entity.getImage();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public int getJsonId() {
        return jsonId;
    }

    public void setJsonId(int jsonId) {
        this.jsonId = jsonId;
    }

    @Override
    public String toString() {
        return "ImageResource{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
