package com.gamehub.model;

import com.gamehub.entity.MatrixVariantEntity;

public class MatrixVariant {

    private int id;
    private String matrixPosition;
    private int value;
    private Scene nextScene1;
    private Scene nextScene2;

    public MatrixVariant(MatrixVariantEntity entity) {
        this.id = entity.getId();
        this.matrixPosition = entity.getMatrixPosition();
        this.value = entity.getValue();
        this.nextScene1 = new Scene(entity.getNextScene1());
        this.nextScene2 = new Scene(entity.getNextScene2());
    }


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

    @Override
    public String toString() {
        return "MatrixVariant{" +
                "id=" + id +
                ", matrixPosition='" + matrixPosition + '\'' +
                ", value=" + value +
                ", nextScene1=" + nextScene1 +
                ", nextScene2=" + nextScene2 +
                '}';
    }
}
