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
            RepresentableProcessorTest.this.testValid("internal/nbbrd/design/StringValueValid.java");
        }

        @Test
        public void testAlternateParser() {
            RepresentableProcessorTest.this.testAlternateParser("internal/nbbrd/design/StringValueAlternateParser.java");
        }

        @Test
        public void testAlternateFormatter() {
            RepresentableProcessorTest.this.testAlternateFormatter("internal/nbbrd/design/StringValueAlternateFormatter.java");
        }

        @Test
        public void testNonStatic() {
            RepresentableProcessorTest.this.testNonStatic("internal/nbbrd/design/StringValueNonStatic.java");
        }

        @Test
        public void testInvalidParamType() {
            RepresentableProcessorTest.this.testInvalidParamType("internal/nbbrd/design/StringValueInvalidParamType.java");
        }

        @Test
        public void testInvalidReturnType() {
            RepresentableProcessorTest.this.testInvalidReturnType("internal/nbbrd/design/StringValueInvalidReturnType.java");
        }

        @Test
        public void testCheckedException() {
            RepresentableProcessorTest.this.testCheckedException("internal/nbbrd/design/StringValueCheckedException.java");
        }
    }

    @Nested
    class OnIntTest {

        @Test
        public void testValid() {
            RepresentableProcessorTest.this.testValid("internal/nbbrd/design/RIntValid.java");
        }

        @Test
        public void testAlternateParser() {
            RepresentableProcessorTest.this.testAlternateParser("internal/nbbrd/design/RIntAlternateParser.java");
        }

        @Test
        public void testAlternateFormatter() {
            RepresentableProcessorTest.this.testAlternateFormatter("internal/nbbrd/design/RIntAlternateFormatter.java");
        }

        @Test
        public void testNonStatic() {
            RepresentableProcessorTest.this.testNonStatic("internal/nbbrd/design/RIntNonStatic.java");
        }

        @Test
        public void testInvalidParamType() {
            RepresentableProcessorTest.this.testInvalidParamType("internal/nbbrd/design/RIntInvalidParamType.java");
        }

        @Test
        public void testInvalidReturnType() {
            RepresentableProcessorTest.this.testInvalidReturnType("internal/nbbrd/design/RIntInvalidReturnType.java");
        }

        @Test
        public void testCheckedException() {
            RepresentableProcessorTest.this.testCheckedException("internal/nbbrd/design/RIntCheckedException.java");
        }
    }

    @Nested
    class OnStringTest {

        @Test
        public void testValid() {
            RepresentableProcessorTest.this.testValid("internal/nbbrd/design/RStringValid.java");
        }

        @Test
        public void testAlternateParser() {
            RepresentableProcessorTest.this.testAlternateParser("internal/nbbrd/design/RStringAlternateParser.java");
        }

        @Test
        public void testAlternateFormatter() {
            RepresentableProcessorTest.this.testAlternateFormatter("internal/nbbrd/design/RStringAlternateFormatter.java");
        }

        @Test
        public void testNonStatic() {
            RepresentableProcessorTest.this.testNonStatic("internal/nbbrd/design/RStringNonStatic.java");
        }

        @Test
        public void testInvalidParamType() {
            RepresentableProcessorTest.this.testInvalidParamType("internal/nbbrd/design/RStringInvalidParamType.java");
        }

        @Test
        public void testInvalidReturnType() {
            RepresentableProcessorTest.this.testInvalidReturnType("internal/nbbrd/design/RStringInvalidReturnType.java");
        }

        @Test
        public void testCheckedException() {
            RepresentableProcessorTest.this.testCheckedException("internal/nbbrd/design/RStringCheckedException.java");
        }
    }

    @Nested
    class OnObjectTest {

        @Test
        public void testValid() {
            RepresentableProcessorTest.this.testValid("internal/nbbrd/design/RObjectValid.java");
        }

        @Test
        public void testAlternateParser() {
            RepresentableProcessorTest.this.testAlternateParser("internal/nbbrd/design/RObjectAlternateParser.java");
        }

        @Test
        public void testAlternateFormatter() {
            RepresentableProcessorTest.this.testAlternateFormatter("internal/nbbrd/design/RObjectAlternateFormatter.java");
        }

        @Test
        public void testNonStatic() {
            RepresentableProcessorTest.this.testNonStatic("internal/nbbrd/design/RObjectNonStatic.java");
        }

        @Test
        public void testInvalidParamType() {
            RepresentableProcessorTest.this.testInvalidParamType("internal/nbbrd/design/RObjectInvalidParamType.java");
        }

        @Test
        public void testInvalidReturnType() {
            RepresentableProcessorTest.this.testInvalidReturnType("internal/nbbrd/design/RObjectInvalidReturnType.java");
        }

        @Test
        public void testCheckedException() {
            RepresentableProcessorTest.this.testCheckedException("internal/nbbrd/design/RObjectCheckedException.java");
        }
    }

    private void testValid(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    private void testAlternateParser(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    private void testAlternateFormatter(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    private void testNonStatic(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be static")
                .inFile(file)
                .onLine(6);
    }

    private void testInvalidParamType(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be the type")
                .inFile(file)
                .onLine(6);
    }

    private void testInvalidReturnType(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("return type must extends enclosing type")
                .inFile(file)
                .onLine(6);
    }

    private void testCheckedException(String resourceName) {
        JavaFileObject file = JavaFileObjects.forResource(resourceName);
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must not have checked exceptions")
                .inFile(file)
                .onLine(8);
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new RepresentableProcessor())
                .compile(file);
    }
}
