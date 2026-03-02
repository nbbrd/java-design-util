package internal.nbbrd.design;

import nbbrd.design.BuilderPattern;
import org.jspecify.annotations.NonNull;

@BuilderPattern(value = Byte.class, buildMethodName = "make")
public class CustomNamedBuilder {

    @NonNull
    public Byte make() {
        return null;
    }
}