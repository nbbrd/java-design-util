package internal.nbbrd.design;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;

public class StringValueProcessorTest {

    @Test
    public void testValid() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/ValidSV.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testAlternateParser() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/AlternateParserSV.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testAlternateFormatter() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/AlternateFormatterSV.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testNonStatic() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonStaticSV.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must have a parser")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testNonCharSequence() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonCharSequenceSV.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must have a parser")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testInvalidReturnType() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/InvalidReturnTypeSV.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must have a parser")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testCheckedException() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/CheckedExceptionSV.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must have a parser")
                .inFile(file)
                .onLine(8);
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new StringValueProcessor())
                .compile(file);
    }
}
