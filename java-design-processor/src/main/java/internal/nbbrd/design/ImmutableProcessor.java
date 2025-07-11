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
import nbbrd.design.Immutable;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;
import java.util.stream.Stream;

import static internal.nbbrd.design.proc.Elements2.fieldsIn;
import static internal.nbbrd.design.proc.Rule.is;
import static internal.nbbrd.design.proc.Rule.it;
import static javax.lang.model.element.Modifier.*;

/**
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.Immutable")
public final class ImmutableProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_IMMUTABLE).process(annotations, roundEnv, processingEnv);
    }

    private static boolean hasFieldsFinalOrLazy(TypeElement type) {
        boolean lazy = type.getAnnotation(Immutable.class).lazy();
        return getNonStaticFields(type).allMatch(field -> field.getModifiers().contains(FINAL) || (lazy && field.getModifiers().contains(VOLATILE)));
    }

    private static boolean hasFieldsPrivate(TypeElement type) {
        return getNonStaticFields(type).allMatch(field -> field.getModifiers().contains(PRIVATE));
    }

    private static boolean hasLazyFieldIfLazy(TypeElement type) {
        boolean lazy = type.getAnnotation(Immutable.class).lazy();
        return !lazy || getNonStaticFields(type).anyMatch(field -> field.getModifiers().contains(VOLATILE));
    }

    private static Stream<VariableElement> getNonStaticFields(TypeElement type) {
        return fieldsIn(type).filter(field -> !field.getModifiers().contains(STATIC));
    }

    private static final Rule<TypeElement> IS_IMMUTABLE = Rule.on(TypeElement.class)
            .and(is(FINAL))
            .and(it(ImmutableProcessor::hasFieldsFinalOrLazy, "Fields of '%s' must be final or lazy"))
            .and(it(ImmutableProcessor::hasFieldsPrivate, "Fields of '%s' must be private"))
            .and(it(ImmutableProcessor::hasLazyFieldIfLazy, "'%s' must have at least one lazy field"));
}
