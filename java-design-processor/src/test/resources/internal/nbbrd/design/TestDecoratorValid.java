package internal.nbbrd.design;

import nbbrd.design.DecoratorPattern;

import java.io.*;

public class TestDecoratorValid {

    @DecoratorPattern
    public abstract static class V1 extends Reader {

        V1(Reader delegate) {}
    }

    @DecoratorPattern
    public abstract static class V2 implements Closeable {

        V2(Closeable delegate) {}
    }

    @DecoratorPattern(Closeable.class)
    public abstract static class V3 extends Reader implements Closeable, Flushable {

        V3(Closeable delegate) {}
    }

    @DecoratorPattern
    public abstract static class V4 implements Closeable {

        V4(boolean stuff, Closeable delegate) {}
    }

    @DecoratorPattern(InputStream.class)
    public abstract static class V5 extends FilterInputStream {

        V5(InputStream delegate) { super(delegate); }
    }
}