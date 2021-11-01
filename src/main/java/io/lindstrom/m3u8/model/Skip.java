package io.lindstrom.m3u8.model;

import org.immutables.value.Value;
import io.lindstrom.m3u8.model.SkipBuilder;
import java.util.List;

@Value.Immutable
public interface Skip {

    long skippedSegments();

    List<String> recentlyRemovedDateRanges();

    static Builder builder() {
        return new Builder();
    }

    class Builder extends SkipBuilder {

    }

    static Skip of(int skippedSegments) {
        return builder().skippedSegments(skippedSegments).build();
    }
}
