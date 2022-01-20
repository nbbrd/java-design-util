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
 * Class that can be represented unambiguously by an <code>int</code>.
 * Such class must have a static factory method that accepts unique <code>int</code> parameter
 * and that doesn't throw a checked exception.
 *
 * @author Philippe Charles
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface RepresentableAsInt {

    String parseMethodName() default "parse";

    String formatMethodName() default "toInt";
}
