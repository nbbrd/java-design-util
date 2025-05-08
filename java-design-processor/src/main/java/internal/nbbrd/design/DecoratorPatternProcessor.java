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

import internal.nbbrd.design.proc.Elements2;
import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Processors;
import internal.nbbrd.design.proc.Rule;
import nbbrd.design.DecoratorPattern;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static internal.nbbrd.design.proc.Processors.extractResultType;

/**
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.DecoratorPattern")
public final class DecoratorPatternProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_DECORATOR_PATTERN).process(annotations, roundEnv, processingEnv);
    }

    private static final Rule<TypeElement> IS_DECORATOR_PATTERN =
            Rule.on(TypeElement.class)
                    .and(Rule.is(ElementKind.CLASS))
                    .and(Rule.of(DecoratorPatternProcessor::isExtendingDecoratedType, "'%s' doesn't extend a class"))
                    .and(Rule.of(DecoratorPatternProcessor::hasConstructorWithDecoratedType, "'%s' doesn't have a constructor with the decorated type"));

    private static boolean isExtendingDecoratedType(ProcessingEnvironment env, TypeElement type) {
        return getDecoratedType(env, type)
                .filter(decoratedType -> isExtendingDecoratedType(env, type, decoratedType))
                .isPresent();
    }

    private static boolean isExtendingDecoratedType(ProcessingEnvironment env, TypeElement type, TypeMirror decoratedType) {
        return env.getTypeUtils().isAssignable(type.getSuperclass(), decoratedType) ||
                type.getInterfaces().stream().anyMatch(x -> env.getTypeUtils().isAssignable(x, decoratedType));
    }

    private static boolean hasConstructorWithDecoratedType(ProcessingEnvironment env, TypeElement type) {
        return getDecoratedType(env, type)
                .filter(decoratedType -> hasConstructorWithDecoratedType(env, type, decoratedType))
                .isPresent();
    }

    private static boolean hasConstructorWithDecoratedType(ProcessingEnvironment env, TypeElement type, TypeMirror decoratedType) {
        return Elements2.constructorsIn(type)
                .flatMap(method -> method.getParameters().stream())
                .anyMatch(var -> env.getTypeUtils().isSameType(decoratedType, var.asType()));
    }

    private static boolean isNullType(ProcessingEnvironment env, TypeMirror expected) {
        return env.getTypeUtils().isSameType(Processors.getTypeMirror(env, Void.class), expected);
    }

    private static boolean isDirectSuperClass(TypeMirror found) {
        return !(found instanceof NoType && found.getKind() == TypeKind.NONE) && !(found.toString().equals("java.lang.Object"));
    }

    private static Optional<TypeMirror> inferDecoratedType(TypeElement type) {
        TypeMirror superclass = type.getSuperclass();
        List<? extends TypeMirror> parents = type.getInterfaces();
        return isDirectSuperClass(superclass) && parents.isEmpty()
                ? Optional.of(superclass)
                : (!isDirectSuperClass(superclass) && parents.size() == 1 ? Optional.of(parents.get(0)) : Optional.empty());
    }

    private static Optional<TypeMirror> getDecoratedType(ProcessingEnvironment env, TypeElement type) {
        TypeMirror result = extractResultType(type.getAnnotation(DecoratorPattern.class)::value);
        return isNullType(env, result) ? inferDecoratedType(type) : Optional.of(result);
    }
}
