package com.clarabridge.voice.timestamp.model;

import java.util.List;
import java.util.Map;

public class VoiceTimestampRequest {
    private String lang;
    private Map<String, Object> metadataJSON;
    private List<Sentence> sentences;

    public VoiceTimestampRequest() {
        super();
    }

    public VoiceTimestampRequest(String lang, Map<String, Object> metadataJSON, List<Sentence> sentences) {
        this.lang = lang;
        this.metadataJSON = metadataJSON;
        this.sentences = sentences;
    }

    public String getLang() {
        return lang;
    }

    public Map<String, Object> getMetadataJSON() {
        return metadataJSON;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setMetadataJSON(Map<String, Object> metadataJSON) {
        this.metadataJSON = metadataJSON;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }
}
