package com.journaldev.spring.view;

import com.journaldev.spring.model.Choice;
import com.journaldev.spring.model.Scene;
import com.journaldev.spring.model.Sprite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SceneView {
    private Set<SpriteView> sprites;
    private Set<ChoiceView> choices;
    private List<PhraseView> phrases;
    private String type;

    public SceneView(Scene scene){
        sprites = new HashSet<>();
        choices = new HashSet<>();
        phrases = new ArrayList<>();
        this.type = scene.getType();
        for (Sprite sprite:scene.getSprites()) {
            sprites.add(new SpriteView(sprite));
        }
        for (Choice choice:scene.getChoices()) {
            choices.add(new ChoiceView(choice));
        }
        String[] text = scene.getText().split("[|]");
        for(int i=0;i<text.length;){
            try {
                phrases.add(new PhraseView(text[i++],text[i++]));
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
}
