package internal.nbbrd.design;

import nbbrd.design.Immutable;

@Immutable(lazy = true)
public final class LazyFieldImmutable {

    private volatile Object field = 123;
}