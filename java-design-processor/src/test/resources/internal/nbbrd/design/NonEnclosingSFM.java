package internal.nbbrd.design;

import nbbrd.design.Immutable;
import nbbrd.design.StaticFactoryMethod;

public class NonEnclosingSFM {

    @StaticFactoryMethod
    public static String make() {
        return "";
    }

    private NonEnclosingSFM() {
    }
}