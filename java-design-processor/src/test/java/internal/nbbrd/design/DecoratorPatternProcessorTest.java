package internal.nbbrd.design;


import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;

class DecoratorPatternProcessorTest {

    @Test
    public void testValid() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TestDecoratorValid.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testConstructorMissing() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TestDecoratorConstructorMissing.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();
    }

    @Test
    public void testConstructorNoArg() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TestDecoratorConstructorNoArg.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();
    }

    @Test
    public void testConstructorNoDecorated() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TestDecoratorConstructorNoDecorated.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();
    }

    @Test
    public void testConstructorSuper() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TestDecoratorConstructorSuper.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new DecoratorPatternProcessor())
                .compile(file);
    }
}