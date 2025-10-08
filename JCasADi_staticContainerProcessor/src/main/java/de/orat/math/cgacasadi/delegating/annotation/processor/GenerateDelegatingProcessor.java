package de.orat.math.cgacasadi.delegating.annotation.processor;

import com.google.auto.service.AutoService;
import de.orat.math.cgacasadi.delegating.annotation.processor.common.ExceptionHandler;
import de.orat.math.cgacasadi.delegating.annotation.processor.generation.ClassesGenerator;
import de.orat.math.cgacasadi.delegating.annotation.processor.representation.Clazz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import de.orat.math.cgacasadi.delegating.annotation.api.GenerateDelegate;

@AutoService(Processor.class)
public final class GenerateDelegatingProcessor extends AbstractProcessor {

    public static record Utils(ExceptionHandler exceptionHandler, Elements elementUtils, Types typeUtils) {

    }

    private volatile Utils utils;

    private volatile Filer filer;

    private volatile boolean initialized = false;

    protected static final Set<String> supportedAnnotationTypes = Set.of(GenerateDelegate.class.getCanonicalName());

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_17;
    }

    @Override
    protected synchronized boolean isInitialized() {
        return super.isInitialized() && this.initialized;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();

        var exceptionHandler = new ExceptionHandler(processingEnv.getMessager());
        var elementUtils = processingEnv.getElementUtils();
        var typeUtils = processingEnv.getTypeUtils();
        this.utils = new Utils(exceptionHandler, elementUtils, typeUtils);

        this.initialized = true;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!isInitialized()) {
            throw new IllegalStateException("Can't proccess if not initialized properly.");
        }

        this.utils.exceptionHandler().handle(() -> {
            List<Clazz> classes = GenerateDelegatingProcessor.computeClasses(roundEnv, this.utils);
            ClassesGenerator.generate(classes, this.filer);
        });

        // The return boolean value should be true if your annotation processor has processed all the passed annotations, and you don't want them to be passed to other annotation processors down the list.
        return true;
    }

    private static List<Clazz> computeClasses(RoundEnvironment roundEnv, Utils utils) {
        // Safe cast because "@Target(ElementType.TYPE)" of Annotation.
        Set<TypeElement> annotatedTypes = (Set<TypeElement>) roundEnv.getElementsAnnotatedWith(GenerateDelegate.class);

        List<Clazz> classes = new ArrayList<>(annotatedTypes.size());
        for (TypeElement annotatedType : annotatedTypes) {
            Utils adjustedUtils = new Utils(new ExceptionHandler(utils.exceptionHandler()),
                utils.elementUtils(),
                utils.typeUtils());

            adjustedUtils.exceptionHandler().handle(() -> {
                Clazz classRepr = new Clazz(annotatedType, adjustedUtils);
                classes.add(classRepr);
            });
        }

        return Collections.unmodifiableList(classes);
    }
}
