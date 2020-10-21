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
import nbbrd.design.MightBePromoted;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 *
 * @author Philippe Charles
 */
@ServiceProvider(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("nbbrd.design.MightBePromoted")
public final class MightBePromotedProcessor extends AbstractProcessor {

    private final Processing<Element> processing = Processing
            .builder()
            .check(INTERNAL_OR_NOT_PUBLIC)
            .build();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processing.process(annotations, roundEnv, processingEnv);
    }

    private static final Check<Element> INTERNAL_OR_NOT_PUBLIC = Check.of(
            MightBePromotedProcessor::isInternalOrNotPublic,
            "'%s' must be in internal package or not public"
    );

    private static boolean isInternalOrNotPublic(ProcessingEnvironment env, Element element) {
        return isTypeInInternalPackage(env, element) || isNotPublic(env, element);
    }

    private static boolean isTypeInInternalPackage(ProcessingEnvironment env, Element element) {
        if (element instanceof TypeElement) {
            TypeElement type = (TypeElement) element;
            PackageElement pkg = env.getElementUtils().getPackageOf(type);
            String fullname = pkg.getQualifiedName().toString();
            String packagePattern = type.getAnnotation(MightBePromoted.class).packagePattern();
            return fullname.matches(packagePattern);
        }
        return false;
    }

    public static boolean isNotPublic(ProcessingEnvironment env, Element element) {
        return !element.getModifiers().contains(Modifier.PUBLIC);
    }
}
