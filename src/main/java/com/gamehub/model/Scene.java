package com.gamehub.model;

import com.gamehub.view.ChoiceView;
import com.gamehub.view.SceneView;
import com.gamehub.view.View;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "scenes")
public class Scene implements Model {

    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "backgroundId")
    private ImageResource background;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "nextSceneId")
    private Scene nextScene;

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

    public Scene getNextScene() {
        return nextScene;
    }

    public void setNextScene(Scene nextScene) {
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

    @Override
    public View createView() {
        SceneView sceneView = new SceneView();

        String textView = "";
        String speakerView = "";
        String spriteIds = "";
        String sceneIds = "";
        String backgroundIds = "";
        String lastSceneType;

        Scene scene = this;
        do {
            textView = textView.concat(scene.getText().concat("|"));
            speakerView = speakerView.concat(scene.getSpeaker() == null ? ".|" : scene.getSpeaker().concat("|"));
            sceneIds = sceneIds.concat(scene.getId() + "|");
            backgroundIds = backgroundIds.concat(scene.getBackground().getId() + "|");

            for(Sprite s: scene.getSprites()){
                spriteIds = spriteIds.concat(s.getId() + ",");
            }
            spriteIds = spriteIds.substring(0, spriteIds.length() - 1);
            spriteIds = spriteIds.concat("|");
            if (scene.getType().equals("Quest")){
                for (Choice choice:scene.getChoices()) {
                    sceneView.addChoice(new ChoiceView(choice));
                }
            }
            sceneView.setType(scene.getType());
            lastSceneType = scene.getType();
            scene = scene.getNextScene();
        } while(scene != null);

        sceneView.setText(textView.substring(0, textView.length()-1));
        sceneView.setSpeakers(speakerView.substring(0, speakerView.length()-1));
        sceneView.setSprites(spriteIds.substring(0, spriteIds.length()-1));
        sceneView.setIds(sceneIds.substring(0, sceneIds.length()-1));
        sceneView.setBackgrounds(backgroundIds.substring(0, backgroundIds.length()-1));
        sceneView.setLastSceneType(lastSceneType);

        return sceneView;
    }
}
