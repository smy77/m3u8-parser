package io.lindstrom.m3u8.model;

import org.immutables.value.Value;
import io.lindstrom.m3u8.model.CueInBuilder;

@Value.Immutable
public interface CueIn {

    String id();

    static Builder builder() {
        return new Builder();
    }
    class Builder extends CueInBuilder {}
}
