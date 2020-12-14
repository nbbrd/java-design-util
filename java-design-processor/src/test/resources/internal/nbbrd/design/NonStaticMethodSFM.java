package internal.nbbrd.design;

import nbbrd.design.Immutable;
import nbbrd.design.StaticFactoryMethod;

public class NonStaticMethodSFM {

    @StaticFactoryMethod
    NonStaticMethodSFM make() {
        return new NonStaticMethodSFM();
    }
}