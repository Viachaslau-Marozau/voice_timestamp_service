package com.clarabridge.voice.timestamp.model;

import java.util.Objects;

public class SentenceTimestamp {

    private String sentenceId;
    private Double timestamp;

    public SentenceTimestamp() {
        super();
    }

    public SentenceTimestamp(String sentenceId, Double timestamp) {
        this.sentenceId = sentenceId;
        this.timestamp = timestamp;
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SentenceTimestamp)) return false;
        SentenceTimestamp that = (SentenceTimestamp) o;
        return Objects.equals(sentenceId, that.sentenceId) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sentenceId, timestamp);
    }

    @Override
    public String toString() {
        return "{sentenceId:'" + sentenceId + "\', timestamp:" + timestamp + '}';
    }
}
