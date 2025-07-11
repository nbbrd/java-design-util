package internal.nbbrd.design.proc;

import javax.lang.model.element.TypeElement;

import static internal.nbbrd.design.proc.Elements2.constructorsIn;
import static internal.nbbrd.design.proc.Elements2.is;
import static javax.lang.model.element.Modifier.PUBLIC;

@lombok.experimental.UtilityClass
public class TypeRules {

    public static Rule<TypeElement> hasNoInterface() {
        return Rule.it(type -> type.getInterfaces().isEmpty(), "'%s' must not implement interfaces");
    }

    public static Rule<TypeElement> hasNoPublicConstructor() {
        return Rule.it(type -> constructorsIn(type).noneMatch(method -> is(method, PUBLIC)), "'%s' cannot have public constructors");
    }
}
