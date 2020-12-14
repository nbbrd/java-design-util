package internal.nbbrd.design;

import nbbrd.design.Immutable;
import nbbrd.design.StaticFactoryMethod;

public class PublicConstructorSFM {

    @StaticFactoryMethod
    public static PublicConstructorSFM make() {
        return new PublicConstructorSFM();
    }

    public PublicConstructorSFM() {
    }
}