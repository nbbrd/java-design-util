package internal.nbbrd.design.proc;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.function.Function;

import static internal.nbbrd.design.proc.Rule.of;

@lombok.experimental.UtilityClass
public class ExecutableRules {

    public static Rule<ExecutableElement> hasNoParameter() {
        return of(m -> m.getParameters().isEmpty(), "'%s' must not have parameters");
    }

    public static Rule<ExecutableElement> hasParametersThat(Rule<? super VariableElement>... rules) {
        return (env, m) -> hasParametersThat(env, m, rules);
    }

    public static Rule<ExecutableElement> hasParametersThat3(Rule<? super TypeMirror>... rules) {
        return (env, m) -> hasParametersThat3(env, m, rules);
    }

    public static Rule<ExecutableElement> hasAllParametersThat(Rule<? super VariableElement> rule) {
        return (env, m) -> hasAllParametersThat(env, m, rule);
    }

    public static Rule<ExecutableElement> hasNoCheckedException() {
        return of(ExecutableRules::hasNoCheckedException, "'%s' must not have checked exceptions");
    }

    public static Rule<ExecutableElement> returnsEnclosing() {
        return of((env, m) -> isReturningEnclosingType(env, m), "'%s' return type must extends enclosing type");
    }

    public static Rule<ExecutableElement> returns(Function<ExecutableElement, TypeMirror> extractor) {
        return of((env, m) -> isReturningType(env, m, extractor.apply(m)), "'%s' return type is not valid");
    }

    public static Rule<ExecutableElement> returnsTypeThat(Rule<? super TypeElement> rule) {
        return (env, m) -> returnsTypeThat(env, m, rule);
    }

    public static Rule<ExecutableElement> returnsTypeThat2(Rule<? super TypeMirror> rule) {
        return (env, m) -> returnsTypeThat2(env, m, rule);
    }

    private static String hasParametersThat(ProcessingEnvironment env, ExecutableElement method, Rule<? super VariableElement>... rules) {
        if (method.getParameters().size() != rules.length) {
            return "'%s' must have " + rules.length + " parameters";
        }
        List<? extends VariableElement> params = method.getParameters();
        for (int i = 0; i < params.size(); i++) {
            String result = rules[i].check(env, params.get(i));
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static String hasParametersThat3(ProcessingEnvironment env, ExecutableElement method, Rule<? super TypeMirror>... rules) {
        if (method.getParameters().size() != rules.length) {
            return "'%s' must have " + rules.length + " parameters";
        }
        List<? extends VariableElement> params = method.getParameters();
        for (int i = 0; i < params.size(); i++) {
            String result = rules[i].check(env, params.get(i).asType());
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static String hasAllParametersThat(ProcessingEnvironment env, ExecutableElement method, Rule<? super VariableElement> rule) {
        List<? extends VariableElement> params = method.getParameters();
        for (int i = 0; i < params.size(); i++) {
            String result = rule.check(env, params.get(i));
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static boolean hasNoCheckedException(ProcessingEnvironment env, ExecutableElement method) {
        TypeElement runtimeException = Processors.getTypeElement(env, RuntimeException.class);
        return method
                .getThrownTypes()
                .stream()
                .allMatch(o -> env.getTypeUtils().isAssignable(o, runtimeException.asType()));
    }

    private static boolean isReturningEnclosingType(ProcessingEnvironment env, ExecutableElement executable) {
        TypeMirror expected = executable.getEnclosingElement().asType();
        TypeMirror found = executable.getReturnType();
        Types types = env.getTypeUtils();
        return types.isSameType(types.erasure(expected), types.erasure(found));
    }

    private static boolean isReturningType(ProcessingEnvironment env, ExecutableElement executable, TypeMirror type) {
        TypeMirror found = executable.getReturnType();
        Types types = env.getTypeUtils();
        return types.isSameType(types.erasure(type), types.erasure(found));
    }

    private static String returnsTypeThat(ProcessingEnvironment env, ExecutableElement type, Rule<? super TypeElement> rule) {
        Element element = env.getTypeUtils().asElement(type.getReturnType());
        return element instanceof TypeElement
                ? rule.check(env, (TypeElement) element)
                : "'%s' doesn't return a type element";
    }

    private static String returnsTypeThat2(ProcessingEnvironment env, ExecutableElement type, Rule<? super TypeMirror> rule) {
        return rule.check(env, type.getReturnType());
    }
}
