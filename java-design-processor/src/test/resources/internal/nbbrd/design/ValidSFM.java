package internal.nbbrd.design;

import nbbrd.design.Immutable;
import nbbrd.design.StaticFactoryMethod;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ValidSFM {

    public static class C1 {

        @StaticFactoryMethod
        public static C1 make() {
            return new C1();
        }

        private C1() {
        }
    }

    public static class StreamUtils  {

        @StaticFactoryMethod(InputStream.class)
        public static InputStream nullInputStream() {
            return new ByteArrayInputStream(new byte[0]);
        }
    }
}