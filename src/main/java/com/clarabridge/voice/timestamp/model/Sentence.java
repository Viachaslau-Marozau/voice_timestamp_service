package com.clarabridge.voice.timestamp.model;

import java.util.Objects;

public class Sentence {

    private String id;
    private String words;

    public Sentence() {
        super();
    }

    public Sentence(String id, String words) {
        this.id = id;
        this.words = words;
    }

    public String getId() {
        return id;
    }

    public String getWords() {
        return words;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWords(String words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sentence)) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(id, sentence.id) &&
                Objects.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, words);
    }

    @Override
    public String toString() {
        return "{id:'" + id + "\', words:'" + words + "\'}";
    }
}
