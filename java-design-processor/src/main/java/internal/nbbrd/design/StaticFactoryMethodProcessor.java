package internal.nbbrd.design;

import internal.nbbrd.design.proc.Check;
import internal.nbbrd.design.proc.Processing;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Set;

import static internal.nbbrd.design.proc.Check.is;
import static internal.nbbrd.design.proc.Check.of;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.StaticFactoryMethod")
public class StaticFactoryMethodProcessor extends AbstractProcessor {

    private final Processing<ExecutableElement> processing = Processing
            .onExecutable()
            .check(is(STATIC))
            .check(is(PUBLIC))
            .check(of(StaticFactoryMethodProcessor::hasValidReturnType, "'%s' return type must extends enclosing type"))
            .check(of(StaticFactoryMethodProcessor::hasNoPublicConstructor, "'%s' return type cannot have public constructors"))
            .build();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processing.process(annotations, roundEnv, processingEnv);
    }

    private static boolean hasNoPublicConstructor(ProcessingEnvironment env, ExecutableElement type) {
        Types types = env.getTypeUtils();
        TypeMirror returnType = type.getReturnType();
        return !types.asElement(returnType)
                .getEnclosedElements()
                .stream()
                .filter(o -> o.getKind().equals(ElementKind.CONSTRUCTOR))
                .map(ExecutableElement.class::cast)
                .filter(o -> o.getModifiers().contains(PUBLIC))
                .findFirst()
                .isPresent();
    }

    private static boolean hasValidReturnType(ProcessingEnvironment env, ExecutableElement type) {
        Types types = env.getTypeUtils();
        TypeMirror expected = type.getEnclosingElement().asType();
        TypeMirror found = type.getReturnType();
        return types.isSameType(expected, found);
    }
}
