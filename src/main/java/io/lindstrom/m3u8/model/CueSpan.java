package io.lindstrom.m3u8.model;

import org.immutables.value.Value;
import io.lindstrom.m3u8.model.CueSpanBuilder;

@Value.Immutable
public interface CueSpan {

    String timeFromSignal();
    Double id();


    static Builder builder() {
        return new Builder();
    }
    class Builder extends CueSpanBuilder {}
}
