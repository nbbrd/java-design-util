package internal.nbbrd.design;

import nbbrd.design.DecoratorPattern;

import java.io.Reader;

@DecoratorPattern
public abstract class TestDecoratorConstructorNoDecorated extends Reader {

    TestDecoratorConstructorNoDecorated(boolean stuff) {}
}