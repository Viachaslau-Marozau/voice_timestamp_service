package com.clarabridge.voice.timestamp.service.impl;

import com.clarabridge.voice.timestamp.model.Sentence;
import com.clarabridge.voice.timestamp.model.SentenceTimestamp;
import com.clarabridge.voice.timestamp.service.interfaces.VoiceTimestampService;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTextVoiceTimestampServiceImpl implements VoiceTimestampService {

    private final Pattern pattern = Pattern.compile(Constants.REGEXP);

    @Override
    public List<SentenceTimestamp> service(String lang, Map<String, Object> metadataJSON, List<Sentence> sentences) {

        String preparedMetadata = prepareMetadata(metadataJSON);
        String preparedSentences = prepareSentences(sentences);
        String timestampsAsString = populateTimestampsAsText(preparedMetadata, preparedSentences);
        List<SentenceTimestamp> timestamps = getTimestampsAsMap(timestampsAsString);
        return timestamps;
    }

    private String prepareMetadata(Map<String, Object> metadataJSON) {

        StringBuilder resultBuilder = new StringBuilder();
        List<Map<String, Object>> utterances = (List<Map<String, Object>>) metadataJSON.get(Constants.JSON_UTTERANCES);
        boolean setTimestamp = true;
        for (Map<String, Object> utterance : utterances) {
            List<Map<String, Object>> events = (List<Map<String, Object>>) utterance.get(Constants.JSON_EVENTS);
            for (Map<String, Object> event : events) {
                String word = (String) event.get(Constants.JSON_WORD);
                if (setTimestamp) {
                    Double start = (Double) event.get(Constants.JSON_START);
                    resultBuilder.append(Constants.METADATA_PREFIX).append(start).append(Constants.METADATA_POSTFIX);
                    setTimestamp = false;
                }
                resultBuilder.append(word);

                if (word.matches(Constants.DELIMITER)) {
                    setTimestamp = true;
                } else {
                    resultBuilder.append(Constants.SPACE);
                }
            }
        }
        resultBuilder.append(Constants.METADATA_ENDLINE);
        // remove first prefix
        resultBuilder.replace(0, 5, Constants.EMPTY_STRING);
        return resultBuilder.toString();
    }

    private String prepareSentences(List<Sentence> sentences) {

        Iterator<Sentence> iterator = sentences.iterator();
        if (!iterator.hasNext())
            return Constants.EMPTY_STRING;

        StringBuilder builder = new StringBuilder();
        for (; ; ) {
            Sentence sentence = iterator.next();
            builder.append(Constants.SENTENCE_PREFIX);
            builder.append(sentence.getWords());
            builder.append(Constants.SENTENCE_DELIMITER);
            builder.append(sentence.getId());
            builder.append(Constants.SENTENCE_POSTFIX);
            if (!iterator.hasNext())
                return builder.toString();
            builder.append(Constants.NEW_LINE);
        }
    }

    private String populateTimestampsAsText(String preparedMetadata, String preparedSentences) {

        StringsComparator cmp = new StringsComparator(preparedMetadata, preparedSentences);
        EditScript<Character> script = cmp.getScript();
        final StringBuilder resultBuilder = new StringBuilder();
        script.visit(new CommandVisitor<Character>() {

            boolean isTimestamp = false;

            @Override
            public void visitInsertCommand(Character object) {
                resultBuilder.append(object);
            }

            @Override
            public void visitKeepCommand(Character object) {
                if (Constants.TIMESTAMP_START_SYMBOL.equals(object)) {
                    isTimestamp = true;
                }
                if (Constants.TIMESTAMP_END_SYMBOL.equals(object)) {
                    isTimestamp = false;
                }
                resultBuilder.append(object);
            }

            @Override
            public void visitDeleteCommand(Character object) {
                if (isTimestamp) {
                    resultBuilder.append(object);
                }
            }
        });
        return resultBuilder.toString();
    }

    private List<SentenceTimestamp> getTimestampsAsMap(String timestampsAsString) {

        String[] timestampsAsStringArray = timestampsAsString.split(Constants.NEW_LINE);
        List<SentenceTimestamp> timestamps = new ArrayList<>(timestampsAsStringArray.length);
        Double timestamp = 0d;
        for (String timestampAsString : timestampsAsStringArray) {
            Matcher matcher = pattern.matcher(timestampAsString);
            if (matcher.find()) {
                String timestampStr = matcher.group(Constants.TIMESTAMP_GROUP_NUMBER);
                if (!timestampStr.isEmpty()) {
                    timestamp = Double.valueOf(timestampStr);
                }
                String sentenceId = matcher.group(Constants.SENTENCE_ID_GROUP_NUMBER);
                SentenceTimestamp sentenceTimestamp = new SentenceTimestamp(sentenceId, timestamp);
                timestamps.add(sentenceTimestamp);
            }
        }
        return timestamps;
    }

    public static final class Constants {


        public static final String REGEXP = "^\\[(.*)\\]\\{\\{(.*)\\}:\\{(.*)\\}\\}";
        public static final String DELIMITER = ".*[.!?]";
        public static final String EMPTY_STRING = "";
        public static final String SPACE = " ";
        public static final String NEW_LINE = "\n";

        public static final String METADATA_PREFIX = "}:{}}[";
        public static final String METADATA_POSTFIX = "]{{";
        public static final String METADATA_ENDLINE = "}:{}}";

        public static final String SENTENCE_PREFIX = "[]{{";
        public static final String SENTENCE_DELIMITER = "}:{";
        public static final String SENTENCE_POSTFIX = "}}";

        public static final String JSON_UTTERANCES = "utterances";
        public static final String JSON_EVENTS = "events";
        public static final String JSON_METADATA = "metadata";
        public static final String JSON_START = "start";
        public static final String JSON_CONFIDENCE = "confidence";
        public static final String JSON_END = "end";
        public static final String JSON_WORD = "word";
        public static final String JSON_CHANNEL = "channel";

        public static final Character TIMESTAMP_START_SYMBOL = '[';
        public static final Character TIMESTAMP_END_SYMBOL = ']';

        public static final int TIMESTAMP_GROUP_NUMBER = 1;
        public static final int SENTENCE_ID_GROUP_NUMBER = 3;

        private Constants() {
            super();
        }
    }
}
