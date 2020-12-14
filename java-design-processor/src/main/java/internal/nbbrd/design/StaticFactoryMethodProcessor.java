package internal.nbbrd.design;

import internal.nbbrd.design.proc.Processing;
import internal.nbbrd.design.proc.Rule;
import nbbrd.service.ServiceProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static internal.nbbrd.design.proc.ExecutableRules.returnsEnclosing;
import static internal.nbbrd.design.proc.ExecutableRules.returnsTypeThat;
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

    private static final Rule<ExecutableElement> IS_STATIC_FACTORY_METHOD = Rule.on(ExecutableElement.class)
            .and(is(STATIC))
            .and(is(PUBLIC))
            .and(returnsEnclosing())
            .and(returnsTypeThat(hasNoPublicConstructor()));
}
