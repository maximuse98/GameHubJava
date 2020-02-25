package com.gamehub.model;

import com.gamehub.entity.ChoiceEntity;
import com.gamehub.entity.MatrixVariantEntity;
import com.gamehub.entity.SceneEntity;
import com.gamehub.entity.SpriteEntity;
import com.gamehub.view.ChoiceView;
import com.gamehub.view.SceneView;
import com.gamehub.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Scene implements Model {

    private int id;
    private ImageResource background;
    private Scene nextScene;
    private List<Sprite> sprites;
    private List<MatrixVariant> matrixVariantList;
    private List<Choice> choices;

    private String text;
    private String type;
    private String speaker;

    public Scene(SceneEntity entity) {
        this.id = entity.getId();
        this.background = new ImageResource(entity.getBackground());
        this.type = entity.getType();
        this.text = entity.getText();
        this.speaker = entity.getSpeaker();

        setSprites(entity.getSprites());

        if(type.equals("Normal")){
            this.nextScene = new Scene(entity.getNextScene());
        }
        if(type.equals("Quest")){
            setMatrixVariantList(entity.getMatrixVariantList());
            setChoices(entity.getChoices());
        }
    }

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

    public void setMatrixVariantList(Set<MatrixVariantEntity> entityMatrixVariantList) {
        matrixVariantList = new ArrayList<>(entityMatrixVariantList.size());
        for (MatrixVariantEntity entityMatrixVariant: entityMatrixVariantList) {
            matrixVariantList.add(new MatrixVariant(entityMatrixVariant));
        }
    }

    public List<MatrixVariant> getMatrixVariantList() {
        return matrixVariantList;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<ChoiceEntity> entityChoices) {
        choices = new ArrayList<>(entityChoices.size());
        for (ChoiceEntity choiceEntity: entityChoices) {
            Choice choice = new Choice(choiceEntity);
            choice.setScene(this);
            choices.add(choice);
        }
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public void setSprites(Set<SpriteEntity> entitySprites) {
        sprites = new ArrayList<>(entitySprites.size());
        for (SpriteEntity entitySprite:entitySprites) {
            sprites.add(new Sprite(entitySprite));
        }
    }

    public Scene getNextScene() {
        return nextScene;
    }

    public void setNextScene(Scene nextScene) {
        this.nextScene = nextScene;
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
