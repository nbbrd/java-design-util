package internal.nbbrd.design;

import nbbrd.design.BuilderPattern;

@BuilderPattern(Byte.class)
public class InvalidTypeBuilder {

    public String build() {
        return null;
    }
}