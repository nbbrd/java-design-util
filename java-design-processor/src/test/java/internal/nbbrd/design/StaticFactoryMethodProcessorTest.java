package internal.nbbrd.design;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;

public class StaticFactoryMethodProcessorTest {

    @Test
    public void testValid() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/ValidSFM.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testNonStatic() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonStaticMethodSFM.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be static")
                .inFile(file)
                .onLine(9);
    }

    @Test
    public void testNonPublic() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonPublicSFM.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be public")
                .inFile(file)
                .onLine(9);
    }

    @Test
    public void testPublicConstructor() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/PublicConstructorSFM.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("return type cannot have public constructors")
                .inFile(file)
                .onLine(9);
    }

    @Test
    public void testNonEnclosing() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonEnclosingSFM.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("return type must extends enclosing type")
                .inFile(file)
                .onLine(9);
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new StaticFactoryMethodProcessor())
                .compile(file);
    }
}
