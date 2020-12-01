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
import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.tools.JavaFileObject;

/**
 * @author Philippe Charles
 */
public class MightBePromotedProcessorTest {

    @Test
    public void testInternalOrNotPublic1() {
        JavaFileObject file = JavaFileObjects.forResource("internal/abc/InternalOrNotPublic1.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testInternalOrNotPublic2() {
        JavaFileObject file = JavaFileObjects.forResource("abc/internal/InternalOrNotPublic2.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testInternalOrNotPublic3() {
        JavaFileObject file = JavaFileObjects.forResource("impl/abc/InternalOrNotPublic3.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testInternalOrNotPublic4() {
        JavaFileObject file = JavaFileObjects.forResource("abc/impl/InternalOrNotPublic4.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testInternalOrNotPublic5() {
        JavaFileObject file = JavaFileObjects.forResource("abc/InternalOrNotPublic5.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    public void testInternalOrNotPublic6() {
        JavaFileObject file = JavaFileObjects.forResource("abc/InternalOrNotPublic6.java");
        Compilation compilation = compile(file);

        CompilationSubject.assertThat(compilation)
                .failed();
    }

    private Compilation compile(JavaFileObject file) {
        return com.google.testing.compile.Compiler.javac()
                .withProcessors(new MightBePromotedProcessor())
                .compile(file);
    }
}
