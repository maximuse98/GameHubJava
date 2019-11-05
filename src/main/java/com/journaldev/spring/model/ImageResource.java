package com.journaldev.spring.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name="imageresources")
public class ImageResource {
    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String path;
    private Blob image;

    @Transient
    private int jsonId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public int getJsonId() {
        return jsonId;
    }

    public void setJsonId(int jsonId) {
        this.jsonId = jsonId;
    }

    @Override
    public String toString() {
        return "ImageResource{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
