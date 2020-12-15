package internal.nbbrd.design;

import nbbrd.design.BuilderPattern;

@BuilderPattern(Byte.class)
public class MissingBuilder {

    public Byte buildX() {
        return null;
    }
}