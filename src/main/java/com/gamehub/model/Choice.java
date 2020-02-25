package com.gamehub.model;

import com.gamehub.entity.ChoiceEntity;

public class Choice {

    private int id;
    private String caption;
    private int matrixNum;
    private Scene scene;

    public Choice(ChoiceEntity entity) {
        this.id = entity.getId();
        this.caption = entity.getCaption();
        this.matrixNum = entity.getMatrixNum();
    }

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

    public int getMatrixNum() {
        return matrixNum;
    }

    public void setMatrixNum(int matrixNum) {
        this.matrixNum = matrixNum;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", matrixNum='" + matrixNum +
                '}';
    }
}
