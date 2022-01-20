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
package internal.nbbrd.design.proc;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.*;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Philippe Charles
 */
@lombok.experimental.UtilityClass
public class Processors {

    public static Stream<Element> streamOf(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Set::stream);
    }

    // see http://hauchee.blogspot.be/2015/12/compile-time-annotation-processing-getting-class-value.html
    public static TypeMirror extractResultType(Supplier<Class<?>> type) {
        try {
            type.get();
            throw new RuntimeException("Expecting exception to be raised");
        } catch (MirroredTypeException ex) {
            return ex.getTypeMirror();
        }
    }

    public static List<? extends TypeMirror> extractResultTypes(Supplier<Class<?>[]> types) {
        try {
            types.get();
            throw new RuntimeException("Expecting exception to be raised");
        } catch (MirroredTypesException ex) {
            return ex.getTypeMirrors();
        }
    }

    public static boolean isAssignableFrom(TypeMirror from, TypeMirror to) {
        TypeMirror current = from.getKind().equals(TypeKind.TYPEVAR) ? ((TypeVariable) from).getUpperBound() : from;
        while (current instanceof DeclaredType && !isCompatible(to, current)) {
            current = ((DeclaredType) current).getEnclosingType();
        }
        return isCompatible(to, current);
    }

    private static boolean isCompatible(TypeMirror to, TypeMirror current) {
        if (to instanceof DeclaredType && current instanceof DeclaredType) {
            // Generic problem such as:
            // demetra.regarima.internal.RegArmaSsqFunction
            // demetra.regarima.internal.RegArmaSsqFunction<S>
            return current.toString().contains(to.toString());
        }
        // FIXME: bug if same prefix
        return to.equals(current);
    }

    public static TypeElement getTypeElement(ProcessingEnvironment env, Class<?> type) {
        return env.getElementUtils().getTypeElement(type.getName());
    }

    public static TypeMirror getTypeMirror(ProcessingEnvironment env, Class<?> type) {
        return type.isPrimitive()
                ? env.getTypeUtils().getPrimitiveType(TypeKind.valueOf(type.getSimpleName().toUpperCase()))
                : env.getElementUtils().getTypeElement(type.getName()).asType();
    }
}
