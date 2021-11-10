package io.lindstrom.m3u8.parser;

import io.lindstrom.m3u8.model.CueSpan;
import io.lindstrom.m3u8.model.PartialSegment;
import io.lindstrom.m3u8.model.SegmentMap;

import java.util.Map;

public enum CueSpanAttribute implements Attribute<CueSpan, CueSpan.Builder> {
    TIMEFORSIGNAL{
        @Override
        public void read(CueSpan.Builder builder, String value) throws PlaylistParserException {
            builder.timeFromSignal(value);
        }

        @Override
        public void write(CueSpan value, TextBuilder textBuilder) {
            textBuilder.add(name(),value.timeFromSignal());
        }
    },
    ID{
        @Override
        public void read(CueSpan.Builder builder, String value) throws PlaylistParserException {
            builder.id(Double.valueOf(value));
        }

        @Override
        public void write(CueSpan value, TextBuilder textBuilder) {
            textBuilder.add(name(),value.id());
        }
    };

    final static Map<String, CueSpanAttribute> attributeMap = ParserUtils.toMap(values(), Attribute::key);

    static CueSpan parse(String attributes, ParsingMode parsingMode) throws PlaylistParserException {
        CueSpan.Builder builder = CueSpan.builder();
        ParserUtils.readAttributes(attributeMap, attributes, builder, parsingMode);
        return builder.build();
    }
}

