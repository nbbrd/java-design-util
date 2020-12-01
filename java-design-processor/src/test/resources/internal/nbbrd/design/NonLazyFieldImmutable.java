package internal.nbbrd.design;

import nbbrd.design.Immutable;

@Immutable(lazy = true)
public final class NonLazyFieldImmutable {

    private Object field = 123;
}