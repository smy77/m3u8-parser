package io.lindstrom.m3u8.parser;

public class RawPlaylistParser /*extends AbstractPlaylistParser<RawPlaylist, RawPlaylist.Builder>*/ {

    /*
    public RawPlaylistParser(Class mapperClass) {
        super(mapperClass);
    }

    @Override
    RawPlaylist.Builder newBuilder() {
        return new RawPlaylist.Builder();
    }

    @Override
    void onTag(RawPlaylist.Builder builder, String prefix, String attributeList, Iterator<String> lineIterator) {
        Matcher matcher = DefaultTagParser.ATTRIBUTE_LIST_PATTERN.matcher(attributeList);
        List<RawAttribute> attributes = new ArrayList<>();
        while (matcher.find()) {
            boolean hasQuotes = matcher.group(2) != null;
            String value = hasQuotes ? matcher.group(2) : matcher.group(3);
            attributes.add(new RawAttribute(matcher.group(1), value, hasQuotes));
        }

        attributes.sort(Comparator.comparing(attribute -> attribute.name));

        if (!attributes.isEmpty()) {
            builder.addTag(prefix, attributes);
        }
    }

    @Override
    void onURI(RawPlaylist.Builder builder, String uri) {
        // ignore
    }

    @Override
    RawPlaylist build(RawPlaylist.Builder builder) {
        return builder.build();
    }

    @Override
    void write(RawPlaylist playlist, StringBuilder stringBuilder) {
        throw new UnsupportedOperationException("not implemented");
    }
    */
}