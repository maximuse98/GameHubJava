package com.gamehub.entity;

import com.gamehub.view.GameView;
import com.gamehub.view.View;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="games")
public class GameEntity {
    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private int playersCount;
    private String colorTheme;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "startSceneId1")
    private SceneEntity startScene1;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "startSceneId2")
    private SceneEntity startScene2;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<RoleEntity> roles;

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

    public SceneEntity getStartScene1() {
        return startScene1;
    }

    public void setStartScene1(SceneEntity startScene1) {
        this.startScene1 = startScene1;
    }

    public SceneEntity getStartScene2() {
        return startScene2;
    }

    public void setStartScene2(SceneEntity startScene2) {
        this.startScene2 = startScene2;
    }

    public String getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(String colourTheme) {
        this.colorTheme = colourTheme;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name +
                ", playersCount=" + playersCount +
                ", startScene1=" + startScene1 +
                ", startScene2=" + startScene2 +
                '}';
    }

    public View createView() {
        return new GameView(id,name,playersCount,colorTheme);
    }
}
