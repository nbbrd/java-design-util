/*
* Copyright 2017 National Bank of Belgium
*
* Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
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
 * Identifies a class whose global (static) resources are thread-safe Different
 * objects of this class can be safely accessed/modified from different threads.
 * However, a given object can't be safely safely accessed/modified from
 * different threads.
 *
 * This is the default thread property of the classes of JD+
 *
 * @author Jean Palate
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface NotThreadSafe {
}
