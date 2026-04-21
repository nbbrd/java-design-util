package internal.nbbrd.design;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;

public class RepresentableProcessorTest {

    @Nested
    class OnStringValueTest {

        @Test
        public void testValid() {
            assertValid("internal/nbbrd/design/StringValueValid.java");
        }

        @Test
        public void testAlternateParser() {
            assertAlternateParser("internal/nbbrd/design/StringValueAlternateParser.java");
        }

        @Test
        public void testAlternateFormatter() {
            assertAlternateFormatter("internal/nbbrd/design/StringValueAlternateFormatter.java");
        }

        @Test
        public void testNonStatic() {
            assertNonStatic("internal/nbbrd/design/StringValueNonStatic.java");
        }

        @Test
        public void testInvalidParamType() {
            assertInvalidParamType("internal/nbbrd/design/StringValueInvalidParamType.java");
        }

        @Test
        public void testInvalidReturnType() {
            assertInvalidReturnType("internal/nbbrd/design/StringValueInvalidReturnType.java");
        }

        @Test
        public void testCheckedException() {
            assertCheckedException("internal/nbbrd/design/StringValueCheckedException.java");
        }
    }

    @Nested
    class OnIntTest {

        @Test
        public void testValid() {
            assertValid("internal/nbbrd/design/RIntValid.java");
        }

        @Test
        public void testAlternateParser() {
            assertAlternateParser("internal/nbbrd/design/RIntAlternateParser.java");
        }

        @Test
        public void testAlternateFormatter() {
            assertAlternateFormatter("internal/nbbrd/design/RIntAlternateFormatter.java");
        }

        @Test
        public void testNonStatic() {
            assertNonStatic("internal/nbbrd/design/RIntNonStatic.java");
        }

        @Test
        public void testInvalidParamType() {
            assertInvalidParamType("internal/nbbrd/design/RIntInvalidParamType.java");
        }

        @Test
        public void testInvalidReturnType() {
            assertInvalidReturnType("internal/nbbrd/design/RIntInvalidReturnType.java");
        }

        @Test
        public void testCheckedException() {
            assertCheckedException("internal/nbbrd/design/RIntCheckedException.java");
        }

        @Test
        public void testInheritedFormatter() {
            assertValid("internal/nbbrd/design/RIntInheritedFormatter.java");
        }

        @Test
        public void testOverrideFormatter() {
            assertValid("internal/nbbrd/design/RIntOverrideFormatter.java");
        }
    }

    @Nested
    class OnStringTest {

        @Test
        public void testValid() {
            assertValid("internal/nbbrd/design/RStringValid.java");
        }

        @Test
        public void testAlternateParser() {
            assertAlternateParser("internal/nbbrd/design/RStringAlternateParser.java");
        }

        @Test
        public void testAlternateFormatter() {
            assertAlternateFormatter("internal/nbbrd/design/RStringAlternateFormatter.java");
        }

        @Test
        public void testNonStatic() {
            assertNonStatic("internal/nbbrd/design/RStringNonStatic.java");
        }

        @Test
        public void testInvalidParamType() {
            assertInvalidParamType("internal/nbbrd/design/RStringInvalidParamType.java");
        }

        @Test
        public void testInvalidReturnType() {
            assertInvalidReturnType("internal/nbbrd/design/RStringInvalidReturnType.java");
        }

        @Test
        public void testCheckedException() {
            assertCheckedException("internal/nbbrd/design/RStringCheckedException.java");
        }

        @Test
        public void testShortFormatValid() {
            assertValid("internal/nbbrd/design/RStringShortFormatValid.java");
        }

        @Test
        public void testShortFormatMissing() {
            JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/RStringShortFormatMissing.java");
            Compilation compilation = compile(file);

            CompilationSubject.assertThat(compilation)
                    .failed();

            assertThat(compilation)
                    .hadErrorContaining("must have a short formatter")
                    .inFile(file)
                    .onLine(8);
        }

        @Test
        public void testShortFormatStatic() {
            JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/RStringShortFormatStatic.java");
            Compilation compilation = compile(file);

            CompilationSubject.assertThat(compilation)
                    .failed();

            assertThat(compilation)
                    .hadErrorContaining("must not be static")
                    .inFile(file)
                    .onLine(8);
        }

        @Test
        public void testInheritedFormatter() {
            assertValid("internal/nbbrd/design/RStringInheritedFormatter.java");
        }

        @Test
        public void testOverrideFormatter() {
            assertValid("internal/nbbrd/design/RStringOverrideFormatter.java");
        }

        @Test
        public void testInheritedShortFormat() {
            assertValid("internal/nbbrd/design/RStringInheritedShortFormat.java");
        }

        @Test
        public void testOverrideShortFormat() {
            assertValid("internal/nbbrd/design/RStringOverrideShortFormat.java");
        }
    }

    @Nested
    class OnObjectTest {

        @Test
        public void testValid() {
            assertValid("internal/nbbrd/design/RObjectValid.java");
        }

        @Test
        public void testAlternateParser() {
            assertAlternateParser("internal/nbbrd/design/RObjectAlternateParser.java");
        }

        @Test
        public void testAlternateFormatter() {
            assertAlternateFormatter("internal/nbbrd/design/RObjectAlternateFormatter.java");
        }

        @Test
        public void testNonStatic() {
            assertNonStatic("internal/nbbrd/design/RObjectNonStatic.java");
        }

        @Test
        public void testInvalidParamType() {
            assertInvalidParamType("internal/nbbrd/design/RObjectInvalidParamType.java");
        }

        @Test
        public void testInvalidReturnType() {
            assertInvalidReturnType("internal/nbbrd/design/RObjectInvalidReturnType.java");
        }

        @Test
        public void testCheckedException() {
            assertCheckedException("internal/nbbrd/design/RObjectCheckedException.java");
        }

        @Test
        public void testInheritedFormatter() {
            assertValid("internal/nbbrd/design/RObjectInheritedFormatter.java");
        }

        @Test
        public void testInheritedFormatterFromInterface() {
            assertValid("internal/nbbrd/design/RObjectInheritedFormatterFromInterface.java");
        }

        @Test
        public void testOverrideFormatter() {
            assertValid("internal/nbbrd/design/RObjectOverrideFormatter.java");
        }
    }

    @Test
    public void testMultipleRepresentations() {
        assertValid("internal/nbbrd/design/RMultiValid.java");
    }

    private static void assertValid(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    private static void assertAlternateParser(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    private static void assertAlternateFormatter(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    private static void assertNonStatic(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be static")
                .inFile(file)
                .onLine(6);
    }

    private static void assertInvalidParamType(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be the type")
                .inFile(file)
                .onLine(6);
    }

    private static void assertInvalidReturnType(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("return type must extends enclosing type")
                .inFile(file)
                .onLine(6);
    }

    private static void assertCheckedException(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must not have checked exceptions")
                .inFile(file)
                .onLine(8);
    }

    private static Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new RepresentableProcessor())
                .compile(file);
    }
}
