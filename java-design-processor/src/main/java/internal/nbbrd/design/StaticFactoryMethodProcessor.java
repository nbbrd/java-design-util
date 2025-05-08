package internal.nbbrd.design;

import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Rule;
import nbbrd.design.StaticFactoryMethod;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

import static internal.nbbrd.design.proc.ExecutableRules.*;
import static internal.nbbrd.design.proc.Processors.extractResultType;
import static internal.nbbrd.design.proc.Processors.getTypeMirror;
import static internal.nbbrd.design.proc.Rule.ifThenElse;
import static internal.nbbrd.design.proc.Rule.is;
import static internal.nbbrd.design.proc.TypeRules.hasNoPublicConstructor;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.StaticFactoryMethod")
public class StaticFactoryMethodProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_STATIC_FACTORY_METHOD).process(annotations, roundEnv, processingEnv);
    }

    private static final Rule<ExecutableElement> ENCLOSED = returnsEnclosing().and(returnsTypeThat(hasNoPublicConstructor()));
    private static final Rule<ExecutableElement> EXTERNAL = returns(StaticFactoryMethodProcessor::getAnnotationValue);

    private static final Rule<ExecutableElement> IS_STATIC_FACTORY_METHOD = Rule.on(ExecutableElement.class)
            .and(is(STATIC))
            .and(is(PUBLIC))
            .and(ifThenElse(StaticFactoryMethodProcessor::isEnclosedType, ENCLOSED, EXTERNAL));

    private static TypeMirror getAnnotationValue(ExecutableElement x) {
        return extractResultType(x.getAnnotation(StaticFactoryMethod.class)::value);
    }

    private static boolean isEnclosedType(ProcessingEnvironment env, ExecutableElement x) {
        TypeMirror expected = getTypeMirror(env, Void.class);
        TypeMirror found = getAnnotationValue(x);
        return env.getTypeUtils().isSameType(expected, found);
    }
}
