package internal.nbbrd.design;

import nbbrd.design.Immutable;

@Immutable
public final class NonPrivateFieldImmutable {

    final Object field = 123;
}