package internal.nbbrd.design;

import internal.nbbrd.design.proc.Check;
import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Processors;
import nbbrd.design.SealedType;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import java.util.Set;

import static internal.nbbrd.design.proc.Check.is;
import static internal.nbbrd.design.proc.Check.isNot;
import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.ElementKind.INTERFACE;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.FINAL;

@ServiceProvider(Processor.class)
@SupportedAnnotationTypes("nbbrd.design.SealedType")
public class SealedTypeProcessor extends AbstractProcessor {

    private final Processing<TypeElement> processing = Processing
            .onType()
            .check(isNot(FINAL))
            .check(is(INTERFACE).or(IS_ABSTRACT_CLASS))
            .check(HAS_SUBCLASSES)
            .build();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return processing.process(annotations, roundEnv, processingEnv);
    }

    private static final Check<Element> IS_ABSTRACT_CLASS = is(CLASS).and(is(ABSTRACT));
    private static final Check<TypeElement> HAS_SUBCLASSES = Check.of(SealedTypeProcessor::hasFinalSubclasses, "'%s' must have subclasses");

    private static boolean hasFinalSubclasses(ProcessingEnvironment env, TypeElement type) {
        Types types = env.getTypeUtils();
        return Processors.extractResultTypes(type.getAnnotation(SealedType.class)::value)
                .stream()
                .allMatch(subclass -> types.isAssignable(subclass, types.erasure(type.asType())));
    }
}
