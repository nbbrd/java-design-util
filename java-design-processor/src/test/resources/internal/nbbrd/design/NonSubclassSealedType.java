package internal.nbbrd.design;

import nbbrd.design.SealedType;

@SealedType(NonSubclassSealedType.Impl.class)
public abstract class NonSubclassSealedType {

    public static final class Impl {
    }
}