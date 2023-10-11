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
public class ClassNameConstantProcessorTest {

    @Test
    public void testFullClassNameConstant() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/FullClassNameConstant.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testEmptyClassNameConstant() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/EmptyClassNameConstant.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("'ID' should represent the full name of its enclosing class")
                .inFile(file)
                .onLine(8);
    }

    @Test
    public void testNullClassNameConstant() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NullClassNameConstant.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("'ID' should represent the full name of its enclosing class")
                .inFile(file)
                .onLine(8);
    }

    @Test
    public void testNonFinalClassNameConstant() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonFinalClassNameConstant.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("'ID' must be final")
                .inFile(file)
                .onLine(8);
    }

    @Test
    public void testNonStaticClassNameConstant() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonStaticClassNameConstant.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("'ID' must be static")
                .inFile(file)
                .onLine(8);
    }

    @Test
    public void testNonStringClassNameConstant() {
        JavaFileObject file = JavaFileObjects.forResource("internal/nbbrd/design/NonStringClassNameConstant.java");
        Compilation compilation = compile(file);

        assertThat(compilation)
                .failed();

        assertThat(compilation)
                .hadErrorContaining("'ID' must be the type class java.lang.String")
                .inFile(file)
                .onLine(8);
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new ClassNameConstantProcessor())
                .compile(file);
    }
}
