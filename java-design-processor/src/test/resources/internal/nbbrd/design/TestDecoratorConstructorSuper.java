package internal.nbbrd.design;

import nbbrd.design.DecoratorPattern;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.Reader;

@DecoratorPattern
public abstract class TestDecoratorConstructorSuper extends FilterInputStream {

    TestDecoratorConstructorSuper(InputStream delegate) { super(delegate); }
}