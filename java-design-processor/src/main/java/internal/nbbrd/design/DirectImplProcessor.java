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

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static internal.nbbrd.design.proc.Check.*;

/**
 *
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("nbbrd.design.DirectImpl")
public final class DirectImplProcessor extends AbstractProcessor {

    private final Processing<TypeElement> processing = Processing
            .<TypeElement>builder()
            .check(IS_FINAL)
            .check(DO_NOT_EXTEND_CLASS)
            .check(DO_NOT_CONTAIN_PUBLIC_VARS)
            .check(EXTEND_AT_LEAST_ONE_INTERFACE)
            .build();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processing.process(annotations, roundEnv, processingEnv);
    }

    private static final Check EXTEND_AT_LEAST_ONE_INTERFACE = Check.of(
            DirectImplProcessor::extendAtLeastOneInterface,
            "'%s' must extend at least one interface"
    );

    private static boolean extendAtLeastOneInterface(TypeElement type) {
        return !type.getInterfaces().isEmpty();
    }
}
