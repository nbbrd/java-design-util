package internal.nbbrd.design;

import nbbrd.design.BuilderPattern;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

@BuilderPattern(List.class)
public class ValidBuilder {

    @NonNull
    public List<Byte> build() {
        return null;
    }
}