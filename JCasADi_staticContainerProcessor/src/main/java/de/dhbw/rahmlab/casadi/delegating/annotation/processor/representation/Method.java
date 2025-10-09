package de.dhbw.rahmlab.casadi.delegating.annotation.processor.representation;

import de.dhbw.rahmlab.casadi.delegating.annotation.processor.GenerateDelegatingProcessor.Utils;
import de.dhbw.rahmlab.casadi.delegating.annotation.processor.common.WarningException;
import java.util.ArrayList;
import java.util.Collections;
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

    protected Method(ExecutableElement correspondingElement, Utils utils) throws WarningException {
        assert correspondingElement.getKind() == ElementKind.METHOD : String.format(
            "Expected \"%s\" to be a method, but was \"%s\".",
            correspondingElement.getSimpleName(), correspondingElement.getKind());

        this.name = correspondingElement.getSimpleName().toString();
        this.returnType = correspondingElement.getReturnType();
        this.enclosingType = (TypeElement) correspondingElement.getEnclosingElement();
        this.modifiers = Collections.unmodifiableSet(correspondingElement.getModifiers());
        this.parameters = Collections.unmodifiableList(computeParameters(correspondingElement, utils));
    }

    private static List<Parameter> computeParameters(ExecutableElement correspondingElement, Utils utils) {
        List<VariableElement> parameterElements = (List<VariableElement>) correspondingElement.getParameters();
        List<Parameter> parameters = new ArrayList<>(parameterElements.size());

        for (VariableElement parameterElement : parameterElements) {
            utils.exceptionHandler().handle(() -> {
                Parameter parameter = new Parameter(parameterElement, utils);
                parameters.add(parameter);
            });
        }

        return parameters;
    }
}
