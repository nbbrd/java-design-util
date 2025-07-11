package internal.nbbrd.design;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

import static org.assertj.core.api.Assertions.assertThat;

class TraitProcessorTest {

    @Test
    public void testValid() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TraitValid.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testBadName() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TraitBadName.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        CompilationSubject.assertThat(compilation)
                .hadErrorContaining("must must end with 'ble' or start with 'Has'")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testNotInterface() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TraitNotInterface.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        CompilationSubject.assertThat(compilation)
                .hadErrorContaining("must be INTERFACE")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testNotMethod() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/TraitNoMethod.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();

        CompilationSubject.assertThat(compilation)
                .hadErrorContaining("must have at least one method")
                .inFile(file)
                .onLine(8);
    }

    @Test
    public void testIsValidName() {
        assertThat(TraitProcessor.isValidName("HasFeature")).isTrue();
        assertThat(TraitProcessor.isValidName("Movable")).isTrue();
        assertThat(TraitProcessor.isValidName("Reproducible")).isTrue();
        assertThat(TraitProcessor.isValidName("Has")).isTrue();
        assertThat(TraitProcessor.isValidName("ble")).isTrue();
        assertThat(TraitProcessor.isValidName("Feature")).isFalse();
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new TraitProcessor())
                .compile(file);
    }
}