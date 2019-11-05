package com.journaldev.spring.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "scenes")
public class Scene {

    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "backgroundId")
    private ImageResource background;

    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<Sprite> sprites;

    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<MatrixVariant> matrixVariantList;

    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<Choice> choices;

    private String text;
    private String type;

    @Transient
    private int jsonId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageResource getBackground() {
        return background;
    }

    public void setBackground(ImageResource background) {
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

    public void setMatrixVariantList(Set<MatrixVariant> matrixVariantList) {
        this.matrixVariantList = matrixVariantList;
    }

    public Set<MatrixVariant> getMatrixVariantList() {
        return matrixVariantList;
    }

    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = choices;
    }

    public Set<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(Set<Sprite> sprites) {
        this.sprites = sprites;
    }

    public int getJsonId() {
        return jsonId;
    }

    public void setJsonId(int jsonId) {
        this.jsonId = jsonId;
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
