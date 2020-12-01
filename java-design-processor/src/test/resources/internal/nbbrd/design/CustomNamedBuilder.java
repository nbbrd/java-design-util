package internal.nbbrd.design;

import nbbrd.design.BuilderPattern;
import org.checkerframework.checker.nullness.qual.NonNull;

@BuilderPattern(value = Byte.class, buildMethodName = "make")
public class CustomNamedBuilder {

    @NonNull
    public Byte make() {
        return null;
    }
}