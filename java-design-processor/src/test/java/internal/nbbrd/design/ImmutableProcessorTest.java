/*
 * Copyright 2019 National Bank of Belgium
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package internal.nbbrd.design;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

import static com.google.testing.compile.CompilationSubject.assertThat;

/**
 * @author Philippe Charles
 */
public class ImmutableProcessorTest {

    @Test
    public void testEmptyImmutable() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/EmptyImmutable.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testNonFinalImmutable() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonFinalImmutable.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be final")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testFinalFieldImmutable() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/FinalFieldImmutable.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testNonFinalFieldImmutable() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonFinalFieldImmutable.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be final or lazy")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testLazyFieldImmutable() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/LazyFieldImmutable.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testNonLazyFieldImmutable() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonLazyFieldImmutable.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be final or lazy")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testNonPrivateFieldImmutable() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonPrivateFieldImmutable.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must be private")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testLazyImmutable() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/LazyImmutable.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("must have at least one lazy field")
                .inFile(file)
                .onLine(6);
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new ImmutableProcessor())
                .compile(file);
    }
}
