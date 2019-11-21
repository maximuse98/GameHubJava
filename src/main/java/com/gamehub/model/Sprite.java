package com.gamehub.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "sprite")
public class Sprite {

    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "resourceId")
    private ImageResource imageResource;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "sceneId")
    private Scene scene;

    private int positionX;
    private int positionY;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public ImageResource getImageResource() {
        return imageResource;
    }

    public void setImageResource(ImageResource imageResource) {
        this.imageResource = imageResource;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "id=" + id +
                ", imageResource=" + imageResource +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", scene=" + scene +
                '}';
    }
}