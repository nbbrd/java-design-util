package internal.nbbrd.design.proc;

import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import java.lang.annotation.Annotation;
import java.util.function.Predicate;
import java.util.stream.Stream;

@lombok.experimental.UtilityClass
public class Elements2 {

    public static boolean isNamed(Element e, String name) {
        return isNamedTesting(e, name::equals);
    }

    public static boolean isNamedTesting(Element e, Predicate<? super String> namePredicate) {
        return namePredicate.test(e.getSimpleName().toString());
    }

    public static boolean is(Element e, Modifier modifier) {
        return e.getModifiers().contains(modifier);
    }

    public static boolean is(Element e, ElementKind kind) {
        return e.getKind().equals(kind);
    }

    public static boolean hasAnnotation(Element e, Class<? extends Annotation> annotationType) {
        return e.getAnnotation(annotationType) != null;
    }

    public static Stream<ExecutableElement> constructorsIn(TypeElement type) {
        return ElementFilter.constructorsIn(type.getEnclosedElements()).stream();
    }

    public static Stream<ExecutableElement> methodsIn(TypeElement type) {
        return ElementFilter.methodsIn(type.getEnclosedElements()).stream();
    }

    public static Stream<VariableElement> fieldsIn(TypeElement type) {
        return ElementFilter.fieldsIn(type.getEnclosedElements()).stream();
    }
}
