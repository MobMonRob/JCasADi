package de.dhbw.rahmlab.casadi.delegating.annotation.processor.representation;

import de.dhbw.rahmlab.casadi.delegating.annotation.api.GenerateDelegate;
import de.dhbw.rahmlab.casadi.delegating.annotation.processor.GenerateDelegatingProcessor.Utils;
import de.dhbw.rahmlab.casadi.delegating.annotation.processor.common.ErrorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;

/**
 * Convention: representation of target structure, not source structure. With other words, being directly
 * usable by generation classes.
 */
public class Clazz {

    public final String qualifiedName;
    public final String simpleName;
    public final String enclosingQualifiedName;
    /**
     * Unmodifiable
     */
    public final List<Method> methods;

    public final TypeElement annotatedType;

    public Clazz(TypeElement annotatedType, GenerateDelegate annotation, Utils utils) throws ErrorException, Exception {

        this.annotatedType = annotatedType;
        this.enclosingQualifiedName = ((QualifiedNameable) annotatedType.getEnclosingElement()).getQualifiedName().toString();

        ElementKind kind = annotatedType.getKind();
        if (kind != ElementKind.CLASS) {
            throw ErrorException.create(annotatedType,
                "Expected \"%s\" to be a class, but was \"%s\".",
                annotatedType.getQualifiedName().toString(), kind);
        }

        List<DeclaredType> delegateClasses;
        try {
            annotation.of().getClass();
            throw new AssertionError("Should have thrown a MirroredTypesException before this.");
        } catch (MirroredTypesException mte) {
            // Save assumption because classes are DeclaredTypes.
            delegateClasses = (List<DeclaredType>) mte.getTypeMirrors();
        }
        this.simpleName = annotation.name();
        this.qualifiedName = String.format("%s.%s", this.enclosingQualifiedName, this.simpleName);

        this.methods = Collections.unmodifiableList(Clazz.computeMethods(delegateClasses, utils));
    }

    private static final Set<Modifier> publicStatic = Set.of(Modifier.STATIC, Modifier.PUBLIC);

    private static List<Method> computeMethods(List<DeclaredType> delegateClasses, Utils utils) throws ErrorException {
        List<Method> allStaticMethods = new ArrayList<>();

        for (DeclaredType delegateClass : delegateClasses) {
            List<ExecutableElement> staticMethodElements = ((TypeElement) delegateClass.asElement()).
                getEnclosedElements().stream()
                .filter(el -> el.getKind() == ElementKind.METHOD)
                .filter(el -> el.getModifiers().containsAll(publicStatic))
                .map(m -> (ExecutableElement) m)
                .toList();
            List<Method> staticMethods = checkCreateMethods(staticMethodElements, utils);
            allStaticMethods.addAll(staticMethods);
        }

        return allStaticMethods;
    }

    private static List<Method> checkCreateMethods(List<ExecutableElement> methodElements, Utils utils) {
        List<Method> methods = new ArrayList<>(methodElements.size());

        for (ExecutableElement methodElement : methodElements) {
            utils.exceptionHandler().handle(() -> {
                Method methodRepr = new Method(methodElement, utils);
                methods.add(methodRepr);
            });
        }

        return methods;
    }
}
