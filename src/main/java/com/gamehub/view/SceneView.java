package com.gamehub.view;

import java.util.HashSet;
import java.util.Set;

public class SceneView {
    private String ids;
    private String speakers;
    private String sprites;
    private String text;
    private String type;
    private String backgrounds;
    private String lastSceneType;
    private Set<ChoiceView> choices;

    public SceneView() {
        choices = new HashSet<>();
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getSpeakers() {
        return speakers;
    }

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<ChoiceView> getChoices() {
        return choices;
    }

    public void setChoices(Set<ChoiceView> choices) {
        this.choices = choices;
    }

    public String getSprites() {
        return sprites;
    }

    public void setSprites(String sprite) {
        this.sprites = sprite;
    }

    public void addChoice(ChoiceView choiceView){
        choices.add(choiceView);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(String backgrounds) {
        this.backgrounds = backgrounds;
    }

    public String getLastSceneType() {
        return lastSceneType;
    }

    public void setLastSceneType(String lastSceneType) {
        this.lastSceneType = lastSceneType;
    }
}
