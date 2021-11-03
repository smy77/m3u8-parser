package io.lindstrom.m3u8.parser;

import io.lindstrom.m3u8.model.MediaPlaylist;
import io.lindstrom.m3u8.model.StandardMediaPlaylist;
import io.lindstrom.m3u8.model.StandardMediaPlaylistBuilder;

import java.util.Map;

enum MediaPlaylistEndTag implements Tag<MediaPlaylist, StandardMediaPlaylistBuilder> {
    EXT_X_PART {
        @Override
        public void read(StandardMediaPlaylistBuilder builder, String attributes, ParsingMode parsingMode) throws PlaylistParserException {
            // read elsewhere
        }

        @Override
        public void write(MediaPlaylist playlist, TextBuilder textBuilder) {
            playlist.partialSegments().forEach(p -> {
                textBuilder.addTag(tag(), p, PartialSegmentAttribute.attributeMap);
            });
        }
    },

    EXT_X_PRELOAD_HINT {
        @Override
        public void read(StandardMediaPlaylistBuilder builder, String attributes, ParsingMode parsingMode) throws PlaylistParserException {
            builder.preloadHint(PreloadHintAttribute.parse(attributes, parsingMode));
        }

        @Override
        public void write(MediaPlaylist playlist, TextBuilder textBuilder) {
            playlist.preloadHint().ifPresent(v -> textBuilder.addTag(tag(), v, PreloadHintAttribute.attributeMap));
        }
    },

    EXT_X_RENDITION_REPORT {
        @Override
        public void read(StandardMediaPlaylistBuilder builder, String attributes, ParsingMode parsingMode) throws PlaylistParserException {
            builder.addRenditionReports(RenditionReportAttribute.parse(attributes, parsingMode));
        }

        @Override
        public void write(MediaPlaylist playlist, TextBuilder textBuilder) {
            playlist.renditionReports().forEach(r -> textBuilder.addTag(tag(), r, RenditionReportAttribute.attributeMap));
        }
    },

    EXT_X_ENDLIST {
        @Override
        public void read(StandardMediaPlaylistBuilder builder, String attributes, ParsingMode parsingMode) {
            builder.build().ongoing();
        }

        @Override
        public void write(MediaPlaylist playlist, TextBuilder textBuilder) {
            if (!playlist.ongoing()) {
                textBuilder.addTag(tag());
            }
        }
    };

    static final Map<String, MediaPlaylistEndTag> tags = ParserUtils.toMap(values(), Tag::tag);
}
