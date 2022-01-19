package internal.nbbrd.design;

import internal.nbbrd.design.proc.Elements2;
import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Rule;
import nbbrd.design.RepresentableAsInt;
import nbbrd.design.RepresentableAsString;
import nbbrd.design.StringValue;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.Function;

import static internal.nbbrd.design.proc.Elements2.methodsIn;
import static internal.nbbrd.design.proc.ExecutableRules.*;
import static internal.nbbrd.design.proc.Rule.*;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@ServiceProvider(Processor.class)
@SupportedAnnotationTypes({
        "nbbrd.design.StringValue",
        "nbbrd.design.RepresentableAsInt",
        "nbbrd.design.RepresentableAsString"
})
public final class RepresentableProcessor extends AbstractProcessor {

    private static final Rule<TypeElement> IS_STRING_VALUE = RepresentableRule
            .builder(StringValue.class)
            .parseType(CharSequence.class)
            .parseMethod(StringValue::parseMethodName)
            .formatType(String.class)
            .formatMethod(StringValue::formatMethodName)
            .build();

    private static final Rule<TypeElement> IS_INT = RepresentableRule
            .builder(RepresentableAsInt.class)
            .parseType(int.class)
            .parseMethod(RepresentableAsInt::parseMethodName)
            .formatType(int.class)
            .formatMethod(RepresentableAsInt::formatMethodName)
            .build();

    private static final Rule<TypeElement> IS_STRING = RepresentableRule
            .builder(RepresentableAsString.class)
            .parseType(CharSequence.class)
            .parseMethod(RepresentableAsString::parseMethodName)
            .formatType(String.class)
            .formatMethod(RepresentableAsString::formatMethodName)
            .build();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing
                .of(IS_STRING_VALUE, IS_INT, IS_STRING)
                .process(annotations, roundEnv, processingEnv);
    }

    @lombok.Builder
    public static final class RepresentableRule<A extends Annotation> implements Rule<TypeElement> {

        public static <A extends Annotation> Builder<A> builder(Class<A> annotationType) {
            return new Builder<A>().annotationType(annotationType);
        }

        private final Class<A> annotationType;
        private final Class<?> parseType;
        private final Class<?> formatType;
        private final Function<A, String> parseMethod;
        private final Function<A, String> formatMethod;

        private boolean hasParseMethod(ProcessingEnvironment env, TypeElement type) {
            A annotation = type.getAnnotation(annotationType);
            Rule<ExecutableElement> isParseMethod = getIsParseMethod(parseMethod.apply(annotation));
            return methodsIn(type).anyMatch(isParseMethod.asPredicate(env));
        }

        private Rule<ExecutableElement> getIsParseMethod(String parseMethodName) {
            return Rule.on(ExecutableElement.class)
                    .and(isNamed(parseMethodName))
                    .and(is(PUBLIC))
                    .and(is(STATIC))
                    .and(returnsEnclosing())
                    .and(hasParametersThat(is(parseType)))
                    .and(hasNoCheckedException());
        }

        private boolean hasFormatMethod(ProcessingEnvironment env, TypeElement type) {
            A annotation = type.getAnnotation(annotationType);
            Rule<ExecutableElement> isFormatMethod = getIsFormatMethod(formatMethod.apply(annotation));
            return methodsIn(type).anyMatch(isFormatMethod.asPredicate(env));
        }

        private Rule<ExecutableElement> getIsFormatMethod(String formatMethodName) {
            return Rule.on(ExecutableElement.class)
                    .and(isNamed(formatMethodName))
                    .and(is(PUBLIC))
                    .and(isNot(STATIC))
                    .and(returnsTypeThat2(is2(formatType)))
                    .and(hasNoParameter())
                    .and(hasNoCheckedException());
        }

        private final Rule<TypeElement> hasParseMethodRule = Rule.of(this::hasParseMethod, "'%s' must have a parser");

        private final Rule<TypeElement> hasFormatMethodRule = Rule.of(this::hasFormatMethod, "'%s' must have a formatter");

        private final Rule<TypeElement> isRepresentableRule = Rule.on(TypeElement.class)
                .and(hasParseMethodRule)
                .and(hasFormatMethodRule);

        @Override
        public String check(ProcessingEnvironment env, TypeElement element) {
            return Elements2.hasAnnotation(element, annotationType) ? isRepresentableRule.check(env, element) : NO_ERROR;
        }
    }
}
