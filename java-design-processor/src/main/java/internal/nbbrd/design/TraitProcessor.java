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
import internal.nbbrd.design.proc.Rule;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static internal.nbbrd.design.proc.Rule.*;
import static javax.lang.model.element.ElementKind.INTERFACE;

/**
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.Trait")
public final class TraitProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_TRAIT).process(annotations, roundEnv, processingEnv);
    }

    private static boolean hasAtLeastOneMethod(TypeElement type) {
        return Elements2.methodsIn(type).findAny().isPresent();
    }

    static boolean isValidName(String name) {
        return name.endsWith("ble") || name.startsWith("Has");
    }

    private static final Rule<TypeElement> IS_TRAIT = Rule.on(TypeElement.class)
            .and(is(INTERFACE))
            .and(isNamedTesting(TraitProcessor::isValidName, "must end with 'ble' or start with 'Has'"))
            .and(it(TraitProcessor::hasAtLeastOneMethod, "'%s' must have at least one method"));
}
