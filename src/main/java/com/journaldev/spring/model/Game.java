package com.journaldev.spring.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="games")
public class Game {
    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private int playersCount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "startSceneId1")
    private Scene startScene1;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "startSceneId2")
    private Scene startScene2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public Scene getStartScene1() {
        return startScene1;
    }

    public void setStartScene1(Scene startScene1) {
        this.startScene1 = startScene1;
    }

    public Scene getStartScene2() {
        return startScene2;
    }

    public void setStartScene2(Scene startScene2) {
        this.startScene2 = startScene2;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playersCount=" + playersCount +
                ", startScene1=" + startScene1 +
                ", startScene2=" + startScene2 +
                '}';
    }
}
