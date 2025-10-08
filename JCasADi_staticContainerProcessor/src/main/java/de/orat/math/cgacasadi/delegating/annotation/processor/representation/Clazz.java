package de.orat.math.cgacasadi.delegating.annotation.processor.representation;

import de.orat.math.cgacasadi.delegating.annotation.api.GenerateDelegate;
import de.orat.math.cgacasadi.delegating.annotation.processor.GenerateDelegatingProcessor.Utils;
import de.orat.math.cgacasadi.delegating.annotation.processor.common.ErrorException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

/**
 * Convention: representation of target structure, not source structure. With other words, being directly
 * usable by generation classes.
 */
public class Clazz {

    public final TypeElement correspondingElement;
    public final String qualifiedName;
    public final String simpleName;
    public final String enclosingQualifiedName;
    /**
     * Unmodifiable
     */
    public final List<Method> methods;
    public final DeclaredType annotatedTo;

    /**
     * Unmodifiable
     */
    public final Collection<DeclaredType> commonSuperTypes;

    public Clazz(TypeElement correspondingElement, Utils utils) throws ErrorException, Exception {
        this.correspondingElement = correspondingElement;
        this.simpleName = correspondingElement.getSimpleName().toString();
        this.enclosingQualifiedName = ((QualifiedNameable) correspondingElement.getEnclosingElement()).getQualifiedName().toString();
        this.qualifiedName = correspondingElement.getQualifiedName().toString();

        ElementKind kind = correspondingElement.getKind();
        if (kind != ElementKind.CLASS) {
            throw ErrorException.create(correspondingElement,
                "Expected \"%s\" to be a class, but was \"%s\".",
                this.qualifiedName, kind);
        }

        DeclaredType to;
        try {
            GenerateDelegate annotation = correspondingElement.getAnnotation(GenerateDelegate.class);
            annotation.to().getClass();
            throw new AssertionError("Should have thrown a MirroredTypeException before this.");
        } catch (MirroredTypeException mte) {
            // Save assumption because classes are DeclaredTypes.
            to = (DeclaredType) mte.getTypeMirror();
        }
        this.annotatedTo = to;

        var toSuperTypeElements = computeSuperTypes(to, utils).values().stream().map(tm -> ((DeclaredType) tm).asElement()).collect(Collectors.toSet());
        Map<String, DeclaredType> commonSuperTypes = computeSuperTypes((DeclaredType) correspondingElement.asType(), utils);
        var commonSuperTypeElemenents = commonSuperTypes.entrySet().stream().filter(e -> !toSuperTypeElements.contains(((DeclaredType) e.getValue()).asElement())).toList();
        commonSuperTypes.entrySet().removeAll(commonSuperTypeElemenents);
        // commonSuperTypes.keySet().forEach(k -> System.out.println(k));
        this.commonSuperTypes = Collections.unmodifiableCollection(commonSuperTypes.values());

        this.methods = Collections.unmodifiableList(Clazz.computeMethods(commonSuperTypes, correspondingElement, utils));
    }

    private static Map<String, DeclaredType> computeSuperTypes(DeclaredType baseType, Utils utils) {
        // Compute all recursive super types
        Map<String, DeclaredType> allSuperTypes = new LinkedHashMap<>();
        {
            List<DeclaredType> currentSubTypes = List.of(baseType);
            while (!currentSubTypes.isEmpty()) {
                List<DeclaredType> nextSubTypes = new ArrayList<>();
                for (var currentSubType : currentSubTypes) {
                    var previousEntry = allSuperTypes.putIfAbsent(currentSubType.toString(), currentSubType);
                    if (previousEntry != null) {
                        continue;
                    }

                    // Includes substitutions.
                    var currentSuperTypes = (List<DeclaredType>) utils.typeUtils().directSupertypes(currentSubType);
                    nextSubTypes.addAll(currentSuperTypes);
                }
                currentSubTypes = nextSubTypes;
            }
        }
        allSuperTypes.remove(baseType.toString());
        allSuperTypes.remove("java.lang.Object");
        // allSuperTypes.keySet().forEach(s -> System.out.println("superTypes: " + s.toString() + s.hashCode()));
        return allSuperTypes;
    }

    private static List<Method> computeMethods(Map<String, DeclaredType> superTypes, TypeElement baseTypeElement, Utils utils) throws ErrorException {
        List<Method> allMethods = new ArrayList<>();

        // Don't generate methods which are already contained in the annotated class.
        Set<String> previousMethodElementsNames = baseTypeElement.getEnclosedElements().stream()
            .filter(el -> el.getKind() == ElementKind.METHOD)
            .map(m -> (ExecutableElement) m)
            .map(e -> e.getSimpleName().toString())
            .collect(Collectors.toCollection(HashSet::new));

        for (DeclaredType superInterface : superTypes.values()) {
            List<ExecutableElement> interfaceDefaultMethodElements = ((TypeElement) superInterface.asElement()).getEnclosedElements().stream()
                .filter(el -> el.getKind() == ElementKind.METHOD)
                .map(m -> (ExecutableElement) m)
                // Remove overrides
                .filter(m -> !previousMethodElementsNames.contains(m.getSimpleName().toString()))
                .toList();

            List<String> methodElementsNames = interfaceDefaultMethodElements.stream()
                .map(me -> me.getSimpleName().toString())
                .toList();
            previousMethodElementsNames.addAll(methodElementsNames);

            TypeParametersToArguments typeParametersToArguments = new TypeParametersToArguments(superInterface);
            List<Method> containedMethods = checkCreateMethods(interfaceDefaultMethodElements, utils, typeParametersToArguments);
            allMethods.addAll(containedMethods);
        }

        return allMethods;
    }

    private static List<Method> checkCreateMethods(List<ExecutableElement> methodElements, Utils utils, TypeParametersToArguments typeParametersToArguments) {
        List<Method> methods = new ArrayList<>(methodElements.size());
        Set<String> methodNames = new HashSet<>(methodElements.size());

        for (ExecutableElement methodElement : methodElements) {
            utils.exceptionHandler().handle(() -> {
                Method methodRepr = new Method(methodElement, typeParametersToArguments, utils);

                if (methodNames.contains(methodRepr.name)) {
                    throw ErrorException.create(methodElement,
                        "Forbidden overloaded method: \"%s\".",
                        methodElement.getSimpleName());
                }

                methods.add(methodRepr);
                methodNames.add(methodRepr.name);
            });
        }

        return methods;
    }
}
