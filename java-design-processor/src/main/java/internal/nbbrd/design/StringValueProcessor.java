package internal.nbbrd.design;

import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Rule;
import nbbrd.design.StringValue;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static internal.nbbrd.design.proc.Elements2.methodsIn;
import static internal.nbbrd.design.proc.ExecutableRules.*;
import static internal.nbbrd.design.proc.Rule.*;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.StringValue")
public class StringValueProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_STRING_VALUE).process(annotations, roundEnv, processingEnv);
    }

    private static final Rule<TypeElement> HAS_PARSE_METHOD = of(StringValueProcessor::hasParseMethod, "'%s' must have a parser");

    private static boolean hasParseMethod(ProcessingEnvironment env, TypeElement type) {
        Rule<ExecutableElement> isParseMethod = getIsParseMethod(type.getAnnotation(StringValue.class));
        return methodsIn(type).anyMatch(isParseMethod.asPredicate(env));
    }

    private static Rule<ExecutableElement> getIsParseMethod(StringValue annotation) {
        return Rule.on(ExecutableElement.class)
                .and(isNamed(annotation.parseMethodName()))
                .and(is(PUBLIC))
                .and(is(STATIC))
                .and(returnsEnclosing())
                .and(hasParametersThat(is(CharSequence.class)))
                .and(hasNoCheckedException());
    }

    private static final Rule<TypeElement> HAS_FORMAT_METHOD = of(StringValueProcessor::hasFormatMethod, "'%s' must have a formatter");

    private static boolean hasFormatMethod(ProcessingEnvironment env, TypeElement type) {
        Rule<ExecutableElement> isFormatMethod = getIsFormatMethod(type.getAnnotation(StringValue.class));
        return methodsIn(type).anyMatch(isFormatMethod.asPredicate(env));
    }

    private static Rule<ExecutableElement> getIsFormatMethod(StringValue annotation) {
        return Rule.on(ExecutableElement.class)
                .and(isNamed(annotation.formatMethodName()))
                .and(is(PUBLIC))
                .and(isNot(STATIC))
                .and(returnsTypeThat(is(String.class)))
                .and(hasNoParameter())
                .and(hasNoCheckedException());
    }

    private static final Rule<TypeElement> IS_STRING_VALUE = Rule.on(TypeElement.class)
            .and(HAS_PARSE_METHOD)
            .and(HAS_FORMAT_METHOD);
}
