package internal.nbbrd.design.proc;

import javax.lang.model.element.TypeElement;

import static internal.nbbrd.design.proc.Elements2.constructorsIn;
import static internal.nbbrd.design.proc.Elements2.is;
import static internal.nbbrd.design.proc.Rule.of;
import static javax.lang.model.element.Modifier.PUBLIC;

@lombok.experimental.UtilityClass
public class TypeRules {

    public static Rule<TypeElement> hasNoInterface() {
        return of(type -> type.getInterfaces().isEmpty(), "'%s' must not implement interfaces");
    }

    public static Rule<TypeElement> hasNoPublicConstructor() {
        return of(type -> constructorsIn(type).noneMatch(method -> is(method, PUBLIC)), "'%s' cannot have public constructors");
    }
}
