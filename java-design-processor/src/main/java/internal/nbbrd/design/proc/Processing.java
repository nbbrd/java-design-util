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
package internal.nbbrd.design.proc;

import lombok.AccessLevel;
import nbbrd.design.SkipProcessing;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @param <T>
 * @author Philippe Charles
 */
@lombok.AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Processing<T extends Element> {

    public static <X extends Element> Processing<X> of(Rule<X>... rules) {
        return new Processing<X>(Arrays.asList(rules));
    }

    @lombok.Singular
    private final List<Rule<? super T>> checks;

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment env) {
        if (roundEnv.processingOver()) {
            return false;
        }
        Processors.streamOf(annotations, roundEnv)
                .map(element -> (T) element)
                .filter(element -> !isSkipRequired(element, env))
                .forEach(element -> checkAll(element, env));
        return true;
    }

    private void checkAll(T element, ProcessingEnvironment env) {
        checks.forEach(check -> {
            String result = check.check(env, element);
            if (result != null) {
                env.getMessager().printMessage(Diagnostic.Kind.ERROR, result, element);
            }
        });
    }

    private boolean isSkipRequired(Element element, ProcessingEnvironment env) {
        SkipProcessing skip = element.getAnnotation(SkipProcessing.class);
        if (skip != null) {
            env.getMessager().printMessage(Diagnostic.Kind.WARNING, "Processing skipped on '" + element + "'; reason: '" + skip.reason() + "'", element);
            return true;
            //FIXME: make the following work without throwing exception
//            if (element.getAnnotation(skip.target()) != null) {
//                env.getMessager().printMessage(Diagnostic.Kind.WARNING, "Processing skipped on '" + element + "'", element);
//                return true;
//            }
        }
        return false;
    }
}
