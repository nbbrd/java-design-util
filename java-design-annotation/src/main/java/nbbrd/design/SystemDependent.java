/*
 * Copyright 2026 National Bank of Belgium.
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *      https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nbbrd.design;

import java.lang.annotation.*;

/**
 * Indicates that a method's behavior or return value depends on system
 * configuration or environment settings.<br>
 * This includes dependencies on:
 * <ul>
 * <li>{@link java.util.Locale#getDefault()}</li>
 * <li>{@link java.util.TimeZone#getDefault()}</li>
 * <li>{@link java.nio.charset.Charset#defaultCharset()}</li>
 * <li>System properties</li>
 * <li>Environment variables</li>
 * </ul>
 * <p>
 * Methods marked with this annotation may produce different results
 * on different systems or when system configuration changes.
 *
 * @author Philippe Charles
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface SystemDependent {
}
