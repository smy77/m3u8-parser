package io.lindstrom.m3u8.model;

import org.immutables.value.Value;
import io.lindstrom.m3u8.model.MediaPlaylistBuilder;

@Value.Immutable
public interface StandardMediaPlaylist extends MediaPlaylist {
    static StandardMediaPlaylistBuilder builder() {
        return new StandardMediaPlaylistBuilder();
    }

    abstract class Builder extends MediaPlaylistBuilder {
    }

}
