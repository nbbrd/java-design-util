/*
 * Copyright 2020 National Bank of Belgium
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
package nbbrd.design;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a class can be represented unambiguously by a {@link String}.
 * <p>
 * The annotated class must satisfy the following contracts:
 * <ul>
 * <li><b>Parser:</b> It must have a {@code public static} factory method (named by {@link #parseMethodName()})
 * that accepts a single {@link CharSequence} parameter, returns the enclosing type,
 * and does not throw any checked exception.
 * An invalid input should raise an {@link IllegalArgumentException} (or a subclass thereof).</li>
 * <li><b>Formatter:</b> It must have a {@code public} non-static, no-arg method (named by {@link #formatMethodName()})
 * that returns a {@link String} and does not throw any checked exception.</li>
 * <li><b>Short formatter (optional):</b> When {@link #hasShortFormat()} is {@code true}, the class must also have
 * a {@code public} non-static, no-arg method (named by {@link #shortFormatMethodName()})
 * that returns a {@link String} and does not throw any checked exception.</li>
 * </ul>
 * <p>
 * The string representation should be consistent: formatting an object to a string and
 * parsing that string back should yield an equal object.
 *
 * @author Philippe Charles
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface RepresentableAsString {

    /**
     * Name of the static factory method used to parse a {@link CharSequence} into an instance of the annotated class.
     * <p>
     * The method must be {@code public static}, accept a single {@link CharSequence} parameter,
     * return the annotated type, and not throw any checked exception.
     *
     * @return the parse method name; defaults to {@code "parse"}
     */
    String parseMethodName() default "parse";

    /**
     * Name of the instance method used to format the object as a {@link String}.
     * <p>
     * The method must be {@code public}, non-static, accept no parameters,
     * return a {@link String}, and not throw any checked exception.
     *
     * @return the format method name; defaults to {@code "toString"}
     */
    String formatMethodName() default "toString";

    /**
     * Whether to enforce the presence of a short format method.
     * <p>
     * When {@code true}, the annotated class must also have a {@code public}, non-static,
     * no-arg method named {@link #shortFormatMethodName()} that returns a {@link String}
     * and does not throw any checked exception. The method may be inherited from a parent class.
     *
     * @return {@code true} to enforce a short format method; defaults to {@code false}
     */
    boolean hasShortFormat() default false;

    /**
     * Name of the short format method used to produce an abbreviated string representation.
     * <p>
     * This property is only effective when {@link #hasShortFormat()} is {@code true}.
     * The referenced method must be {@code public}, non-static, accept no parameters,
     * return a {@link String}, and not throw any checked exception.
     * The method may be inherited from a parent class.
     *
     * @return the short format method name; defaults to {@code "toShortString"}
     */
    String shortFormatMethodName() default "toShortString";
}
