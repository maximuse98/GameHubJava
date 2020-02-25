package com.gamehub.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "scenes")
public class SceneEntity{

    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "backgroundId")
    private ImageResourceEntity background;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "nextSceneId")
    private SceneEntity nextScene;

    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<SpriteEntity> sprites;

    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<MatrixVariantEntity> matrixVariantList;

    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<ChoiceEntity> choices;

    private String text;
    private String type;
    private String speaker;

    @Transient
    private int jsonId;
    @Transient
    private int nextSceneJsonId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageResourceEntity getBackground() {
        return background;
    }

    public void setBackground(ImageResourceEntity background) {
        this.background = background;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMatrixVariantList(Set<MatrixVariantEntity> matrixVariantList) {
        this.matrixVariantList = matrixVariantList;
    }

    public Set<MatrixVariantEntity> getMatrixVariantList() {
        return matrixVariantList;
    }

    public Set<ChoiceEntity> getChoices() {
        return choices;
    }

    public void setChoices(Set<ChoiceEntity> choices) {
        this.choices = choices;
    }

    public Set<SpriteEntity> getSprites() {
        return sprites;
    }

    public void setSprites(Set<SpriteEntity> sprites) {
        this.sprites = sprites;
    }

    public int getJsonId() {
        return jsonId;
    }

    public void setJsonId(int jsonId) {
        this.jsonId = jsonId;
    }

    public SceneEntity getNextScene() {
        return nextScene;
    }

    public void setNextScene(SceneEntity nextScene) {
        this.nextScene = nextScene;
    }

    public int getNextSceneJsonId() {
        return nextSceneJsonId;
    }

    public void setNextSceneJsonId(int nextSceneJsonId) {
        this.nextSceneJsonId = nextSceneJsonId;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "id=" + id +
                ", background=" + background +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
