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

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * @param <T>
 * @author Philippe Charles
 */
@FunctionalInterface
public interface Check<T extends Element> {

    String check(ProcessingEnvironment env, T type);

    default Check<T> or(Check<T> other) {
        return (env, type) -> {
            String first = this.check(env, type);
            if (first == NO_ERROR) {
                return NO_ERROR;
            }
            String second = other.check(env, type);
            if (second == NO_ERROR) {
                return NO_ERROR;
            }
            return first + " or " + second;
        };
    }

    default Check<T> and(Check<T> other) {
        return (env, type) -> {
            String result = this.check(env, type);
            return result != NO_ERROR ? result : other.check(env, type);
        };
    }

    String NO_ERROR = null;

    static <T extends Element> Check<T> of(Predicate<? super T> condition, String formattedMessage) {
        return (env, type) -> !condition.test(type) ? String.format(formattedMessage, type) : NO_ERROR;
    }

    static <T extends Element> Check<T> of(BiPredicate<ProcessingEnvironment, ? super T> condition, String formattedMessage) {
        return (env, type) -> !condition.test(env, type) ? String.format(formattedMessage, type) : NO_ERROR;
    }

    static <T extends Element> Check<T> is(Modifier modifier) {
        return of(type -> type.getModifiers().contains(modifier), "'%s' must be " + modifier);
    }

    static <T extends Element> Check<T> isNot(Modifier modifier) {
        return of(type -> !type.getModifiers().contains(modifier), "'%s' must not be " + modifier);
    }

    static <T extends Element> Check<T> is(ElementKind kind) {
        return of(type -> !type.getKind().equals(kind), "'%s' must be " + kind);
    }
}
