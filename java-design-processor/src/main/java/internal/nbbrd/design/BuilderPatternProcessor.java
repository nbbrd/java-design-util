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
import nbbrd.design.BuilderPattern;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static internal.nbbrd.design.proc.Elements2.methodsIn;
import static internal.nbbrd.design.proc.ExecutableRules.hasNoParameter;
import static internal.nbbrd.design.proc.Processors.extractResultType;
import static internal.nbbrd.design.proc.Rule.isNamed;

/**
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.BuilderPattern")
public final class BuilderPatternProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_BUILDER_PATTERN).process(annotations, roundEnv, processingEnv);
    }

    private static boolean hasBuildMethod(ProcessingEnvironment env, TypeElement type) {
        Rule<ExecutableElement> isBuildMethod = getIsBuildMethodRule(type.getAnnotation(BuilderPattern.class));
        return methodsIn(type).anyMatch(isBuildMethod.asPredicate(env));
    }

    private static Rule<ExecutableElement> getIsBuildMethodRule(BuilderPattern annotation) {
        return Rule.on(ExecutableElement.class)
                .and(isNamed(annotation.buildMethodName()))
                .and(hasNoParameter())
                .and(Rule.of((env, method) -> env.getTypeUtils().isAssignable(method.getReturnType(), extractResultType(annotation::value)), ""));
    }

    private static final Rule<TypeElement> HAS_BUILD_METHOD = Rule.of(BuilderPatternProcessor::hasBuildMethod, "Cannot find build method in '%s'");

    private static final Rule<TypeElement> IS_BUILDER_PATTERN = Rule.on(TypeElement.class).and(HAS_BUILD_METHOD);
}
