package com.gamehub.view;

public class PhraseView {
    private String speaker;
    private String speech;

    public PhraseView(String speaker, String speech) {
        this.speaker = speaker;
        this.speech = speech;
    }

    public PhraseView(String speech) {
        this.speech = speech;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getSpeech() {
        return speech;
    }
}
