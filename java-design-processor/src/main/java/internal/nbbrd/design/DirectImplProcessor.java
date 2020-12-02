/*
 * Copyright 2016 National Bank of Belgium
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

import internal.nbbrd.design.proc.Check;
import internal.nbbrd.design.proc.Processing;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;

import static internal.nbbrd.design.proc.Check.is;
import static javax.lang.model.element.Modifier.FINAL;

/**
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.DirectImpl")
public final class DirectImplProcessor extends AbstractProcessor {

    private final Processing<TypeElement> processing = Processing
            .onType()
            .check(is(FINAL))
            .check(DO_NOT_EXTEND_CLASS)
            .check(DO_NOT_CONTAIN_PUBLIC_VARS)
            .check(EXTEND_AT_LEAST_ONE_INTERFACE)
            .build();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processing.process(annotations, roundEnv, processingEnv);
    }

    private static final Check<TypeElement> DO_NOT_EXTEND_CLASS = Check.of(DirectImplProcessor::doNotExtendClass, "'%s' may not extend another class");
    private static final Check<TypeElement> DO_NOT_CONTAIN_PUBLIC_VARS = Check.of(DirectImplProcessor::doNotContainPublicVars, "'%s' may not contain public vars");

    private static final Check EXTEND_AT_LEAST_ONE_INTERFACE = Check.of(
            DirectImplProcessor::extendAtLeastOneInterface,
            "'%s' must extend at least one interface"
    );

    private static boolean extendAtLeastOneInterface(TypeElement type) {
        return !type.getInterfaces().isEmpty();
    }

    private static boolean doNotExtendClass(TypeElement type) {
        return type.getSuperclass().toString().equals(Object.class.getName());
    }

    private static boolean doNotContainPublicVars(TypeElement type) {
        return type.getEnclosedElements().stream().noneMatch(DirectImplProcessor::isVariableNotStaticButPublic);
    }

    private static boolean isVariableNotStaticButPublic(Element e) {
        Set<Modifier> modifiers = e.getModifiers();
        return e instanceof VariableElement
                && !modifiers.contains(Modifier.STATIC)
                && modifiers.contains(Modifier.PUBLIC);
    }
}
