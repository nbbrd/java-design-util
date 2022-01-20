package internal.nbbrd.design;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import internal.nbbrd.design.proc.Elements2;
import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Processors;
import internal.nbbrd.design.proc.Rule;
import nbbrd.design.RepresentableAs;
import nbbrd.design.RepresentableAsInt;
import nbbrd.design.RepresentableAsString;
import nbbrd.design.StringValue;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static internal.nbbrd.design.proc.Elements2.methodsIn;
import static internal.nbbrd.design.proc.ExecutableRules.*;
import static internal.nbbrd.design.proc.Rule.*;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@ServiceProvider(Processor.class)
@SupportedAnnotationTypes({
        "nbbrd.design.StringValue",
        "nbbrd.design.RepresentableAs",
        "nbbrd.design.RepresentableAsInt",
        "nbbrd.design.RepresentableAsString"
})
public final class RepresentableProcessor extends AbstractProcessor {

    private static final Rule<TypeElement> IS_STRING_VALUE = RepresentableRule
            .builder(StringValue.class)
            .parseType(o -> CharSequence.class)
            .parseMethodName(StringValue::parseMethodName)
            .formatType(o -> String.class)
            .formatMethodName(StringValue::formatMethodName)
            .build();

    private static final Rule<TypeElement> IS_INT = RepresentableRule
            .builder(RepresentableAsInt.class)
            .parseType(o -> int.class)
            .parseMethodName(RepresentableAsInt::parseMethodName)
            .formatType(o -> int.class)
            .formatMethodName(RepresentableAsInt::formatMethodName)
            .build();

    private static final Rule<TypeElement> IS_STRING = RepresentableRule
            .builder(RepresentableAsString.class)
            .parseType(o -> CharSequence.class)
            .parseMethodName(RepresentableAsString::parseMethodName)
            .formatType(o -> String.class)
            .formatMethodName(RepresentableAsString::formatMethodName)
            .build();

    private static final Rule<TypeElement> IS_TYPE = RepresentableRule
            .builder(RepresentableAs.class)
            .parseType(RepresentableAs::value)
            .parseMethodName(RepresentableAs::parseMethodName)
            .formatType(RepresentableAs::value)
            .formatMethodName(RepresentableAs::formatMethodName)
            .build();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing
                .of(IS_STRING_VALUE, IS_INT, IS_STRING, IS_TYPE)
                .process(annotations, roundEnv, processingEnv);
    }

    @lombok.Builder
    public static final class RepresentableRule<A extends Annotation> implements Rule<TypeElement> {

        public static <A extends Annotation> Builder<A> builder(Class<A> annotationType) {
            return new Builder<A>().annotationType(annotationType);
        }

        private final Class<A> annotationType;
        private final Function<A, Class<?>> parseType;
        private final Function<A, Class<?>> formatType;
        private final Function<A, String> parseMethodName;
        private final Function<A, String> formatMethodName;

        private boolean isNullOrEmpty(String name) {
            return name == null || name.isEmpty();
        }

        private TypeMirror getTypeMirror(ProcessingEnvironment env, A annotation, Function<A, Class<?>> extractor) {
            try {
                Class<?> result = extractor.apply(annotation);
                return Processors.getTypeMirror(env, result);
            } catch (MirroredTypeException ex) {
                return ex.getTypeMirror();
            }
        }

        private String getParseMethodName(A annotation) {
            String result = parseMethodName.apply(annotation);
            return isNullOrEmpty(result) ? "parse" : result;
        }

        private String hasParseMethod(ProcessingEnvironment env, TypeElement type) {
            A annotation = type.getAnnotation(annotationType);

            String methodName = getParseMethodName(annotation);

            List<ExecutableElement> methods = methodsIn(type)
                    .filter(isNamed(methodName).asPredicate(env))
                    .collect(Collectors.toList());

            Rule<ExecutableElement> isParseMethod = getIsParseMethod(getTypeMirror(env, annotation, parseType));

            switch (methods.size()) {
                case 1:
                    return isParseMethod.check(env, methods.get(0));
                default:
                    return String.format("'%s' must have a parser", type);
            }
        }

        private Rule<ExecutableElement> getIsParseMethod(TypeMirror parseType) {
            return Rule.on(ExecutableElement.class)
                    .and(is(PUBLIC))
                    .and(is(STATIC))
                    .and(returnsEnclosing())
                    .and(hasParametersThat3(is3(parseType)))
                    .and(hasNoCheckedException());
        }

        private String getFormatMethodName(TypeMirror type, A annotation) {
            String result = formatMethodName.apply(annotation);
            if (!isNullOrEmpty(result)) {
                return result;
            }
            TypeName typeName = TypeName.get(type);
            return "to" + (typeName instanceof ClassName ? ((ClassName) typeName).simpleName() : typeName.toString());
        }

        private String hasFormatMethod(ProcessingEnvironment env, TypeElement type) {
            A annotation = type.getAnnotation(annotationType);

            TypeMirror formatTypeMirror = getTypeMirror(env, annotation, formatType);
            String methodName = getFormatMethodName(formatTypeMirror, annotation);

            List<ExecutableElement> methods = methodsIn(type)
                    .filter(isNamed(methodName).asPredicate(env))
                    .collect(Collectors.toList());

            Rule<ExecutableElement> isFormatMethod = getIsFormatMethod(formatTypeMirror);

            switch (methods.size()) {
                case 1:
                    return isFormatMethod.check(env, methods.get(0));
                default:
                    return String.format("'%s' must have a formatter", type);
            }
        }

        private Rule<ExecutableElement> getIsFormatMethod(TypeMirror formatType) {
            return Rule.on(ExecutableElement.class)
                    .and(is(PUBLIC))
                    .and(isNot(STATIC))
                    .and(returnsTypeThat2(is3(formatType)))
                    .and(hasNoParameter())
                    .and(hasNoCheckedException());
        }

        private final Rule<TypeElement> isRepresentableRule = Rule.on(TypeElement.class)
                .and(this::hasParseMethod)
                .and(this::hasFormatMethod);

        @Override
        public String check(ProcessingEnvironment env, TypeElement element) {
            return Elements2.hasAnnotation(element, annotationType) ? isRepresentableRule.check(env, element) : NO_ERROR;
        }
    }
}
