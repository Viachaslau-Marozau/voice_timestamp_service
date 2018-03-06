package com.clarabridge.voice.timestamp.service.interfaces;

import com.clarabridge.voice.timestamp.model.Sentence;
import com.clarabridge.voice.timestamp.model.SentenceTimestamp;

import java.util.List;
import java.util.Map;

public interface VoiceTimestampService {

    List<SentenceTimestamp> service(String lang, Map<String, Object> metadataJSON, List<Sentence> sentences);
}
