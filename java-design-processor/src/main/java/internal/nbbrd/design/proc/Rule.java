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
import javax.lang.model.type.TypeMirror;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * @param <T>
 * @author Philippe Charles
 */
@FunctionalInterface
public interface Rule<T> {

    String check(ProcessingEnvironment env, T element);

    default boolean isValid(ProcessingEnvironment env, T element) {
        return check(env, element) == null;
    }

    default Rule<T> or(Rule<? super T> other) {
        return (env, e) -> {
            String first = this.check(env, e);
            if (first == NO_ERROR) {
                return NO_ERROR;
            }
            String second = other.check(env, e);
            if (second == NO_ERROR) {
                return NO_ERROR;
            }
            return first + " or " + second;
        };
    }

    default Rule<T> and(Rule<? super T> other) {
        return (env, e) -> {
            String result = this.check(env, e);
            return result != NO_ERROR ? result : other.check(env, e);
        };
    }

    default Predicate<T> asPredicate(ProcessingEnvironment env) {
        return element -> isValid(env, element);
    }

    String NO_ERROR = null;

    static <T extends Element> Rule<T> on(Class<T> type) {
        return (env, e) -> NO_ERROR;
    }

    static <T extends Element> Rule<T> of(Predicate<? super T> condition, String formattedMessage) {
        return (env, e) -> !condition.test(e) ? String.format(formattedMessage, e) : NO_ERROR;
    }

    static <T> Rule<T> of(BiPredicate<ProcessingEnvironment, ? super T> condition, String formattedMessage) {
        return (env, e) -> !condition.test(env, e) ? String.format(formattedMessage, e) : NO_ERROR;
    }

    static <T extends Element> Rule<T> is(Modifier modifier) {
        return of(e -> Elements2.is(e, modifier), "'%s' must be " + modifier);
    }

    static <T extends Element> Rule<T> isNot(Modifier modifier) {
        return of(e -> !Elements2.is(e, modifier), "'%s' must not be " + modifier);
    }

    static <T extends Element> Rule<T> is(ElementKind kind) {
        return of(e -> Elements2.is(e, kind), "'%s' must be " + kind);
    }

    static <T extends Element> Rule<T> isNamed(String name) {
        return of(e -> Elements2.isNamed(e, name), "'%s' must be named " + name);
    }

    static <T extends Element> Rule<T> is(Element expected) {
        return of((env, e) -> env.getTypeUtils().isSameType(e.asType(), expected.asType()), "'%s' must be the type " + expected);
    }

    static <T extends Element> Rule<T> is(Class<?> expected) {
        return of((env, e) -> env.getTypeUtils().isSameType(e.asType(), Processors.getTypeMirror(env, expected)), "'%s' must be the type " + expected);
    }

    static <T extends TypeMirror> Rule<T> is2(Class<?> expected) {
        return of((env, e) -> env.getTypeUtils().isSameType(e, Processors.getTypeMirror(env, expected)), "'%s' must be the type " + expected);
    }

    static <T extends TypeMirror> Rule<T> is3(TypeMirror expected) {
        return of((env, e) -> env.getTypeUtils().isSameType(e, expected), "'%s' must be the type " + expected);
    }
}
