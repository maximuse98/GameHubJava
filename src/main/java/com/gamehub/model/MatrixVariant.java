package com.gamehub.model;

import javax.persistence.*;

@Entity
@Table(name = "matrixvariant")
public class MatrixVariant {

    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String matrixPosition;
    private int value;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "nextSceneId1")
    private Scene nextScene1;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "nextSceneId2")
    private Scene nextScene2;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sceneId")
    private Scene scene;

    @Transient
    private int nextSceneId1;
    @Transient
    private int nextSceneId2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatrixPosition() {
        return matrixPosition;
    }

    public void setMatrixPosition(String matrixPosition) {
        this.matrixPosition = matrixPosition;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Scene getNextScene1() {
        return nextScene1;
    }

    public void setNextScene1(Scene nextScene1) {
        this.nextScene1 = nextScene1;
    }

    public Scene getNextScene2() {
        return nextScene2;
    }

    public void setNextScene2(Scene nextScene2) {
        this.nextScene2 = nextScene2;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public int getNextSceneId1() {
        return nextSceneId1;
    }

    public void setNextSceneId1(int nextSceneId1) {
        this.nextSceneId1 = nextSceneId1;
    }

    public int getNextSceneId2() {
        return nextSceneId2;
    }

    public void setNextSceneId2(int nextSceneId2) {
        this.nextSceneId2 = nextSceneId2;
    }

    @Override
    public String toString() {
        return "MatrixVariant{" +
                "id=" + id +
                ", matrixPosition='" + matrixPosition + '\'' +
                ", value=" + value +
                ", nextScene1=" + nextScene1 +
                ", nextScene2=" + nextScene2 +
                ", scene=" + scene +
                '}';
    }
}
