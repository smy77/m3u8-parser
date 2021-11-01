package io.lindstrom.m3u8.model;

import org.immutables.value.Value;
import io.lindstrom.m3u8.model.CueOutBuilder;

@Value.Immutable
public interface CueOut {
    Double duration();
    String id();
    String cue();


    static Builder builder() {
        return new Builder();
    }
    class Builder extends CueOutBuilder {}
}
