package io.lindstrom.m3u8.parser;

import io.lindstrom.m3u8.model.*;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Supplier;

import static io.lindstrom.m3u8.parser.Tags.*;

public class AbstractMediaPlaylistParser<P extends MediaPlaylist> extends AbstractPlaylistParser<P, MediaPlaylistCreator<P>> {
    private final ByteRangeParser byteRangeParser = new ByteRangeParser();
    private final SegmentMapParser segmentMapParser = new SegmentMapParser(byteRangeParser);
    private final SegmentKeyParser segmentKeyParser = new SegmentKeyParser();
    private final List<TagsSupport> optionalTagsSupport = new ArrayList<>();


    protected Supplier<MediaPlaylistCreator<P>> builderSupplier;
    ParsingMode parsingMode;

    public AbstractMediaPlaylistParser(Supplier<MediaPlaylistCreator<P>> supplier) {
        this.builderSupplier = supplier;
    }
    public AbstractMediaPlaylistParser(ParsingMode parsingMode) {
        this.parsingMode = parsingMode;
    }
    public AbstractMediaPlaylistParser(Supplier<MediaPlaylistCreator<P>> supplier, ParsingMode parsingMode) {
        this.builderSupplier = supplier;
        this.parsingMode = parsingMode;
    }

    @Override
    MediaPlaylistCreator<P> newBuilder() {
        return builderSupplier.get();
    }

    @Override
    void onTag(MediaPlaylistCreator<P> builderWrapper, String prefix, String attributes, Iterator<String> lineIterator) throws PlaylistParserException {
        if (MediaSegmentTag.EXT_X_PART.tag().equals(prefix)) {
            builderWrapper.playlistBuilder().partialSegments(Collections.singleton(PartialSegmentAttribute.parse(attributes, parsingMode)));
        } else if (MediaPlaylistTag.tags.containsKey(prefix)) {
            MediaPlaylistTag.tags.get(prefix).read(builderWrapper.playlistBuilder(), attributes, parsingMode);
        } else if (MediaSegmentTag.tags.containsKey(prefix)) {
            MediaSegmentTag.tags.get(prefix).read(builderWrapper.segmentBuilder(), attributes, parsingMode);
        } else if (MediaPlaylistEndTag.tags.containsKey(prefix)){
            MediaPlaylistEndTag.tags.get(prefix).read(builderWrapper.playlistBuilder(), attributes, parsingMode);
        } else if (parsingMode.failOnUnknownTags()) {
            throw new PlaylistParserException("Tag not implemented: " + prefix);
        }
    }

    @Override
    void onURI(MediaPlaylistCreator<P> builderWrapper, String uri) {
        builderWrapper.segmentBuilder().uri(uri);
        builderWrapper.playlistBuilder().addMediaSegments(builderWrapper.segmentBuilder().build());
        builderWrapper.newSegmentBuilder(MediaSegment.builder());
    }

    @Override
    void onComment(MediaPlaylistCreator<P> builder, String value) {
        builder.playlistBuilder().addComments(value);
    }

    @Override
    P build(MediaPlaylistCreator<P> builder) {
        return builder.create();
    }

    public void addOptionalTagsSupport(TagsSupport tagsSupport) {
        this.optionalTagsSupport.add(tagsSupport);
    }

    @Override
    void write(MediaPlaylist playlist, StringBuilder stringBuilder) {
        TextBuilder textBuilder = new TextBuilder(stringBuilder);
        for (MediaPlaylistTag tag : MediaPlaylistTag.tags.values()) {
            tag.write(playlist, textBuilder);
        }

        playlist.mediaSegments().forEach(mediaSegment -> {
            for (MediaSegmentTag tag : MediaSegmentTag.tags.values()) {
                tag.write(mediaSegment, textBuilder);
            }
            textBuilder.add(mediaSegment.uri()).add('\n');
        });

        for (MediaPlaylistEndTag tag : MediaPlaylistEndTag.tags.values()) {
            tag.write(playlist, textBuilder);
        }
    }
}
