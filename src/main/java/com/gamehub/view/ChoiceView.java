package com.gamehub.view;

import com.gamehub.model.Choice;

public class ChoiceView {
    private int id;
    private String caption;

    public ChoiceView(Choice choice) {
        this.id = choice.getId();
        this.caption = choice.getCaption();
    }

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }
}
