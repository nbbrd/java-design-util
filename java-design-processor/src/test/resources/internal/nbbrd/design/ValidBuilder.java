package internal.nbbrd.design;

import nbbrd.design.BuilderPattern;
import org.checkerframework.checker.nullness.qual.NonNull;

@BuilderPattern(Byte.class)
public class ValidBuilder {

    @NonNull
    public Byte build() {
        return null;
    }
}