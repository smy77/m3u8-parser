package io.lindstrom.m3u8.parser;

import io.lindstrom.m3u8.model.MasterPlaylist;
import io.lindstrom.m3u8.model.MediaPlaylist;

import java.util.Collections;
import java.util.Iterator;

import static io.lindstrom.m3u8.parser.Tags.*;

/**
 * MasterPlaylistParser can read and write Master Playlists according to RFC 8216 (HTTP Live Streaming).
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * MasterPlaylistParser parser = new MasterPlaylistParser();
 *
 * // Parse playlist
 * MasterPlaylist playlist = parser.readPlaylist(Paths.get("path/to/master.m3u8"));
 *
 * // Update playlist version
 * MasterPlaylist updated = MasterPlaylist.builder()
 *                                        .from(playlist)
 *                                        .version(2)
 *                                        .build();
 *
 * // Write playlist to standard out
 * System.out.println(parser.writePlaylistAsString(updated));
 * }
 * </pre>
 *
 * This implementation is reusable and thread safe.
 */
public class MasterPlaylistParser extends AbstractPlaylistParser<MasterPlaylist, MasterPlaylist.Builder> {
    private final ParsingMode parsingMode;

    public MasterPlaylistParser() {
        this(ParsingMode.STRICT);
    }

    public MasterPlaylistParser(ParsingMode parsingMode) {
        this.parsingMode = parsingMode;
    }

    @Override
    MasterPlaylist.Builder newBuilder() {
        return MasterPlaylist.builder();
    }


    @Override
    void onTag(MasterPlaylist.Builder builder, String prefix, String attributes, Iterator<String> lineIterator) throws PlaylistParserException {
        MasterPlaylistTag tag = MasterPlaylistTag.tags.get(prefix);

        if (tag == MasterPlaylistTag.EXT_X_STREAM_INF) {
            String uriLine = lineIterator.next();
            if (uriLine == null || uriLine.startsWith("#")) {
                throw new PlaylistParserException("Expected URI, got " + uriLine);
            }
            builder.addVariants(VariantAttribute.parse(attributes, uriLine, parsingMode));
        } else if (tag != null) {
            tag.read(builder, attributes, parsingMode);
        } else if (parsingMode.failOnUnknownTags()) {
            throw new PlaylistParserException("Tag not implemented: " + prefix);
        }
    }

    @Override
    void onURI(MasterPlaylist.Builder builder, String uri) throws PlaylistParserException {
        throw new PlaylistParserException("Unexpected URI in master playlist");
    }

    @Override
    void write(MasterPlaylist playlist, StringBuilder stringBuilder) {
        for (MasterPlaylistTag tag : MasterPlaylistTag.tags.values()) {
            TextBuilder textBuilder = new TextBuilder(stringBuilder);
            tag.write(playlist, textBuilder);
        }
    }

    @Override
    void onComment(MasterPlaylist.Builder builder, String value) {
        builder.addComments(
                value
        );
    }

    @Override
    MasterPlaylist build(MasterPlaylist.Builder builder) {
        return builder.build();
    }
}
