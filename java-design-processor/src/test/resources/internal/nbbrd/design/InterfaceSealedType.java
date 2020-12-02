package internal.nbbrd.design;

import nbbrd.design.SealedType;

@SealedType(InterfaceSealedType.Impl.class)
public interface InterfaceSealedType<T extends InterfaceSealedType<T>> {

    public static final class Impl implements InterfaceSealedType<Impl> {
    }
}