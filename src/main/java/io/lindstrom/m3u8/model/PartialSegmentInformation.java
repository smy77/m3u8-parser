package io.lindstrom.m3u8.model;

import org.immutables.value.Value;
import io.lindstrom.m3u8.model.PartialSegmentBuilder;

@Value.Immutable
public interface PartialSegmentInformation {

    double partTargetDuration();

    static Builder builder() {
        return new Builder();
    }

    class Builder extends PartialSegmentInformationBuilder {

    }

    static PartialSegmentInformation of(double partTargetDuration) {
        return builder().partTargetDuration(partTargetDuration).build();
    }
}
