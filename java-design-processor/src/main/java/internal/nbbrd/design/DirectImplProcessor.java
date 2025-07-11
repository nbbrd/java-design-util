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

import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Rule;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Set;

import static internal.nbbrd.design.proc.Elements2.fieldsIn;
import static internal.nbbrd.design.proc.Rule.is;
import static internal.nbbrd.design.proc.Rule.it;
import static javax.lang.model.element.Modifier.FINAL;

/**
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.DirectImpl")
public final class DirectImplProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_DIRECT_IMPL).process(annotations, roundEnv, processingEnv);
    }

    private static boolean extendsAtLeastOneInterface(TypeElement type) {
        return !type.getInterfaces().isEmpty();
    }

    private static boolean doesNotExtendClass(TypeElement type) {
        return type.getSuperclass().toString().equals(Object.class.getName());
    }

    private static boolean doesNotContainPublicVars(TypeElement type) {
        return fieldsIn(type).noneMatch(DirectImplProcessor::isVariableNotStaticButPublic);
    }

    private static boolean isVariableNotStaticButPublic(VariableElement e) {
        Set<Modifier> modifiers = e.getModifiers();
        return !modifiers.contains(Modifier.STATIC)
                && modifiers.contains(Modifier.PUBLIC);
    }

    private static final Rule<TypeElement> IS_DIRECT_IMPL = Rule.on(TypeElement.class)
            .and(is(FINAL))
            .and(it(DirectImplProcessor::doesNotExtendClass, "'%s' may not extend another class"))
            .and(it(DirectImplProcessor::doesNotContainPublicVars, "'%s' may not contain public vars"))
            .and(it(DirectImplProcessor::extendsAtLeastOneInterface, "'%s' must extend at least one interface"));
}
