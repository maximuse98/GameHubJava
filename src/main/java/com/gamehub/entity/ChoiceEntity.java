package com.gamehub.entity;

import javax.persistence.*;

@Entity
@Table(name = "choice")
public class ChoiceEntity {

    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sceneId")
    private SceneEntity scene;

    private String caption;
    private int matrixNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public SceneEntity getScene() {
        return scene;
    }

    public void setScene(SceneEntity scene) {
        this.scene = scene;
    }

    public int getMatrixNum() {
        return matrixNum;
    }

    public void setMatrixNum(int matrixNum) {
        this.matrixNum = matrixNum;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", scene=" + scene +
                ", caption='" + caption + '\'' +
                ", matrixNum='" + matrixNum +
                '}';
    }
}
