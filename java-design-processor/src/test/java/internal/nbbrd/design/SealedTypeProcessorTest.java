package internal.nbbrd.design;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;

public class SealedTypeProcessorTest {

    @Test
    public void testInterfaceSealedType() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/InterfaceSealedType.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testAbstractClassSealedType() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/AbstractClassSealedType.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testNonSubclassSealedType() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonSubclassSealedType.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must have subclasses")
                .inFile(file)
                .onLine(6);
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new SealedTypeProcessor())
                .compile(file);
    }
}
