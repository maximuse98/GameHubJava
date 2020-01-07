package com.gamehub.view;

import com.gamehub.model.Choice;
import com.gamehub.model.Scene;
import com.gamehub.model.Sprite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SceneView {
    private Set<SpriteView> sprites;
    private Set<ChoiceView> choices;
    private List<PhraseView> phrases;
    private String type;
    private String speaker;

    public SceneView(Scene scene){
        sprites = new HashSet<>();
        choices = new HashSet<>();
        phrases = new ArrayList<>();
        this.type = scene.getType();
        this.speaker = scene.getSpeaker();
        for (Sprite sprite:scene.getSprites()) {
            sprites.add(new SpriteView(sprite));
        }
        for (Choice choice:scene.getChoices()) {
            choices.add(new ChoiceView(choice));
        }
        String[] text = scene.getText().split("[|]");
        for(int i=0;i<text.length;){
            try {
                phrases.add(new PhraseView(text[i++]));
            } catch (NullPointerException ignored){}
        }
    }

    public Set<SpriteView> getSprites() {
        return sprites;
    }

    public Set<ChoiceView> getChoices() {
        return choices;
    }

    public List<PhraseView> getPhrases() {
        return phrases;
    }

    public String getType() {
        return type;
    }

    public String getSpeaker() {
        return speaker;
    }
}
