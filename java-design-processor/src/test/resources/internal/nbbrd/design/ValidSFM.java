package internal.nbbrd.design;

import nbbrd.design.Immutable;
import nbbrd.design.StaticFactoryMethod;

public class ValidSFM {

    @StaticFactoryMethod
    public static ValidSFM make() {
        return new ValidSFM();
    }

    private ValidSFM() {
    }
}