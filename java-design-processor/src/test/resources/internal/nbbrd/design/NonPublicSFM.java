package internal.nbbrd.design;

import nbbrd.design.Immutable;
import nbbrd.design.StaticFactoryMethod;

public class NonPublicSFM {

    @StaticFactoryMethod
    static NonPublicSFM make() {
        return new NonPublicSFM();
    }

    private NonPublicSFM() {
    }
}