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
public class BuilderPatternProcessorTest {

    @Test
    public void testValidBuilder() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/ValidBuilder.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testMissingBuilder() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/MissingBuilder.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("Cannot find build method")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testInvalidTypeBuilder() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/InvalidTypeBuilder.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("Cannot find build method")
                .inFile(file)
                .onLine(6);
    }

    @Test
    public void testCustomNamedBuilder() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/CustomNamedBuilder.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .succeededWithoutWarnings();
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new BuilderPatternProcessor())
                .compile(file);
    }
}
