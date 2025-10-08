package de.orat.math.cgacasadi.delegating.annotation.processor.representation;

import de.orat.math.cgacasadi.delegating.annotation.processor.GenerateDelegatingProcessor.Utils;
import de.orat.math.cgacasadi.delegating.annotation.processor.common.WarningException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Convention: representation of target structure, not source structure. With other words, being directly
 * usable by generation classes.
 */
public final class Method {

    public final String name;
    public final TypeMirror returnType;
    public final TypeElement enclosingType;
    /**
     * Unmodifiable
     */
    public final Set<Modifier> modifiers;

    /**
     * Unmodifiable
     */
    public final List<Parameter> parameters;

    protected Method(ExecutableElement correspondingElement, TypeParametersToArguments typeParametersToArguments, Utils utils) throws WarningException {
        assert correspondingElement.getKind() == ElementKind.METHOD : String.format(
            "Expected \"%s\" to be a method, but was \"%s\".",
            correspondingElement.getSimpleName(), correspondingElement.getKind());

        this.name = correspondingElement.getSimpleName().toString();
        this.returnType = typeParametersToArguments.clearTypeParameterIfPresent(correspondingElement.getReturnType());
        this.enclosingType = (TypeElement) correspondingElement.getEnclosingElement();

        {
            var modifiers = new HashSet<>(correspondingElement.getModifiers());
            modifiers.remove(Modifier.DEFAULT);
            modifiers.remove(Modifier.ABSTRACT);
            this.modifiers = Collections.unmodifiableSet(modifiers);

            if (modifiers.contains(Modifier.PRIVATE)) {
                throw WarningException.create(correspondingElement,
                    "\"%s\": private method will not be delegated.", this.name);
            }
            if (modifiers.contains(Modifier.STATIC)) {
                throw WarningException.create(correspondingElement,
                    "\"%s\": static method will not be delegated.", this.name);
            }
        }

        this.parameters = computeParameters(correspondingElement, typeParametersToArguments, utils);
    }

    private static List<Parameter> computeParameters(ExecutableElement correspondingElement, TypeParametersToArguments typeParametersToArguments, Utils utils) {
        List<VariableElement> parameterElements = (List<VariableElement>) correspondingElement.getParameters();
        List<Parameter> parameters = new ArrayList<>(parameterElements.size());
        for (VariableElement parameterElement : parameterElements) {
            utils.exceptionHandler().handle(() -> {
                Parameter parameter = new Parameter(parameterElement, typeParametersToArguments, utils);
                parameters.add(parameter);
            });
        }

        return Collections.unmodifiableList(parameters);
    }
}
