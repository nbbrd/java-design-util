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

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.ElementFilter;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static javax.lang.model.element.Modifier.STATIC;

/**
 * @author Philippe Charles
 */
@lombok.experimental.UtilityClass
public class Processors {

    public Stream<Element> streamOf(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Set::stream);
    }

    public boolean isMethod(Element e) {
        return e.getKind().equals(ElementKind.METHOD);
    }

    public boolean hasName(Element e, String name) {
        return e.getSimpleName().toString().equals(name);
    }

    public boolean isMethodWithName(Element e, String name) {
        return isMethod(e) && hasName(e, name);
    }

    public boolean isMethodWithoutParameter(Element e) {
        return isMethod(e) && ((ExecutableElement) e).getParameters().isEmpty();
    }

    public boolean isMethodWithReturnInstanceOf(Element e, Supplier<Class<?>> type) {
        return isMethod(e) && isAssignableFrom(((ExecutableElement) e).getReturnType(), extractResultType(type));
    }

    // see http://hauchee.blogspot.be/2015/12/compile-time-annotation-processing-getting-class-value.html
    public TypeMirror extractResultType(Supplier<Class<?>> type) {
        try {
            type.get();
            throw new RuntimeException("Expecting exeption to be raised");
        } catch (MirroredTypeException ex) {
            return ex.getTypeMirror();
        }
    }

    public List<? extends TypeMirror> extractResultTypes(Supplier<Class<?>[]> types) {
        try {
            types.get();
            throw new RuntimeException("Expecting exeption to be raised");
        } catch (MirroredTypesException ex) {
            return ex.getTypeMirrors();
        }
    }

    public boolean isAssignableFrom(TypeMirror from, TypeMirror to) {
        TypeMirror current = from.getKind().equals(TypeKind.TYPEVAR) ? ((TypeVariable) from).getUpperBound() : from;
        while (current instanceof DeclaredType && !isCompatible(to, current)) {
            current = ((DeclaredType) current).getEnclosingType();
        }
        return isCompatible(to, current);
    }

    private boolean isCompatible(TypeMirror to, TypeMirror current) {
        if (to instanceof DeclaredType && current instanceof DeclaredType) {
            // Generic problem such as:
            // demetra.regarima.internal.RegArmaSsqFunction
            // demetra.regarima.internal.RegArmaSsqFunction<S>
            return current.toString().contains(to.toString());
        }
        // FIXME: bug if same prefix
        return to.equals(current);
    }

    public static Stream<VariableElement> getNonStaticFields(TypeElement type) {
        return ElementFilter
                .fieldsIn(type.getEnclosedElements())
                .stream()
                .filter(field -> !field.getModifiers().contains(STATIC));
    }
}
