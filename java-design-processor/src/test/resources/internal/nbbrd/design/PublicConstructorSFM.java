package internal.nbbrd.design;

import nbbrd.design.Immutable;
import nbbrd.design.StaticFactoryMethod;

public class PublicConstructorSFM {

    @StaticFactoryMethod
    static PublicConstructorSFM make() {
        return new PublicConstructorSFM();
    }

    public PublicConstructorSFM() {
    }
}