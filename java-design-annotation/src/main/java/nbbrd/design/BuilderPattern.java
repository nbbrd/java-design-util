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
package nbbrd.design;

import java.lang.annotation.*;

/**
 * The builder pattern is a design pattern that provides a flexible solution to various object creation problems in object-oriented programming.
 * The builder pattern separates the construction of a complex object from its representation.
 *
 * @author Philippe Charles
 * @see <a href="https://en.wikipedia.org/wiki/Builder_pattern">Wikipedia</a>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface BuilderPattern {

    /**
     * The type of the object that will be built by the builder.
     *
     * @return a non-null type
     */
    Class<?> value();

    /**
     * The name of the method that builds the object.
     *
     * @return a non-null method name
     */
    String buildMethodName() default "build";
}
