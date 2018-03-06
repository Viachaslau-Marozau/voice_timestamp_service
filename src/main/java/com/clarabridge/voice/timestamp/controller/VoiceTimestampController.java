package com.clarabridge.voice.timestamp.controller;

import com.clarabridge.voice.timestamp.model.SentenceTimestamp;
import com.clarabridge.voice.timestamp.model.VoiceTimestampRequest;
import com.clarabridge.voice.timestamp.model.VoiceTimestampResponse;
import com.clarabridge.voice.timestamp.service.interfaces.VoiceTimestampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clarabridge.voice.timestamp.model.Status.FAILED;
import static com.clarabridge.voice.timestamp.model.Status.SUCCESS;

@RestController
@RequestMapping("/voice-timestamp-service")
public class VoiceTimestampController {

    private static final String SUCCESS_MESSAGE = "Request processed successfully";

    @Autowired
    @Qualifier("voiceTimestampService")
    VoiceTimestampService voiceTimestampService;

    @RequestMapping(value = "/populate-voice-timestamp", method = RequestMethod.POST)
    public @ResponseBody
    VoiceTimestampResponse doPost(@RequestBody VoiceTimestampRequest request) {
        return populateTimestamp(request);
    }

    private VoiceTimestampResponse populateTimestamp(VoiceTimestampRequest request) {
        VoiceTimestampResponse response = new VoiceTimestampResponse();
        try {
            List<SentenceTimestamp> timestamps = voiceTimestampService.service(request.getLang(), request.getMetadataJSON(), request.getSentences());
            response.setStatus(SUCCESS);
            response.setStatusDetails(SUCCESS_MESSAGE);
            response.setTimestamps(timestamps);
        } catch (Exception e) {
            response.setStatus(FAILED);
            response.setStatusDetails(String.format("Execution failed: %s: %s", e.getClass(), e.getMessage()));
        }
        return response;
    }
}
