/*
 * Copyright 2018 National Bank of Belgium
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

import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Rule;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;

import static internal.nbbrd.design.proc.Rule.is;
import static internal.nbbrd.design.proc.Rule.of;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.ClassNameConstant")
public final class ClassNameConstantProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_CLASS_NAME_CONSTANT).process(annotations, roundEnv, processingEnv);
    }

    private static boolean hasClassName(VariableElement field) {
        Object constantValue = field.getConstantValue();
        return constantValue instanceof String
                && field.getEnclosingElement().toString().equals(constantValue);
    }

    private static final Rule<VariableElement> IS_CLASS_NAME_CONSTANT = Rule.on(VariableElement.class)
            .and(is(STATIC))
            .and(is(FINAL))
            .and(is(String.class))
            .and(of(ClassNameConstantProcessor::hasClassName, "'%s' should represent the full name of its enclosing class"));
}
