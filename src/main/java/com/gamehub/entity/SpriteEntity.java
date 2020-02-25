package com.gamehub.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "sprite")
public class SpriteEntity {

    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "resourceId")
    private ImageResourceEntity imageResource;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "sceneId")
    private SceneEntity scene;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageResourceEntity getImageResource() {
        return imageResource;
    }

    public void setImageResource(ImageResourceEntity imageResource) {
        this.imageResource = imageResource;
    }

    public SceneEntity getScene() {
        return scene;
    }

    public void setScene(SceneEntity scene) {
        this.scene = scene;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "id=" + id +
                ", imageResource=" + imageResource +
                ", scene=" + scene +
                '}';
    }
}
