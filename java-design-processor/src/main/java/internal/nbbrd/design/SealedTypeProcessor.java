package internal.nbbrd.design;

import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Processors;
import internal.nbbrd.design.proc.Rule;
import nbbrd.design.SealedType;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import java.util.Set;

import static internal.nbbrd.design.proc.Rule.is;
import static internal.nbbrd.design.proc.Rule.isNot;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.INTERFACE;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.FINAL;

@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.SealedType")
public class SealedTypeProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return Processing.of(IS_SEALED_TYPE).process(annotations, roundEnv, processingEnv);
    }

    private static boolean hasSubclasses(ProcessingEnvironment env, TypeElement type) {
        Types types = env.getTypeUtils();
        return Processors.extractResultTypes(type.getAnnotation(SealedType.class)::value)
                .stream()
                .allMatch(subclass -> types.isAssignable(subclass, types.erasure(type.asType())));
    }

    private static final Rule<TypeElement> HAS_SUBCLASSES = Rule.of(SealedTypeProcessor::hasSubclasses, "'%s' must have subclasses");

    private static final Rule<TypeElement> IS_SEALED_TYPE = Rule.on(TypeElement.class)
            .and(isNot(FINAL))
            .and(is(INTERFACE).or(is(CLASS).and(is(ABSTRACT))))
            .and(HAS_SUBCLASSES);
}
