package internal.nbbrd.design;

import nbbrd.design.Immutable;
import nbbrd.design.StaticFactoryMethod;

public class ErasureSFM<T> {

    @StaticFactoryMethod
    public static <X> ErasureSFM<X> make() {
        return new ErasureSFM<X>();
    }

    private ErasureSFM() {
    }
}