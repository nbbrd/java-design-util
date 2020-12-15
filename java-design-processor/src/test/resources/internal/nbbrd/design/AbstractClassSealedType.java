package internal.nbbrd.design;

import nbbrd.design.SealedType;

@SealedType(AbstractClassSealedType.Impl.class)
public abstract class AbstractClassSealedType<T extends AbstractClassSealedType<T>> {

    public static final class Impl extends AbstractClassSealedType<Impl> {
    }
}