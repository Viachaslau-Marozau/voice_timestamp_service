package com.clarabridge.voice.timestamp.model;

import java.util.List;

public class VoiceTimestampResponse {

    private Status status;
    private String statusDetails;
    private List<SentenceTimestamp> timestamps;

    public VoiceTimestampResponse() {
        super();
    }

    public VoiceTimestampResponse(Status status, String statusDetails, List<SentenceTimestamp> timestamps) {
        this.status = status;
        this.statusDetails = statusDetails;
        this.timestamps = timestamps;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public List<SentenceTimestamp> getTimestamps() {
        return timestamps;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }

    public void setTimestamps(List<SentenceTimestamp> timestamps) {
        this.timestamps = timestamps;
    }
}
