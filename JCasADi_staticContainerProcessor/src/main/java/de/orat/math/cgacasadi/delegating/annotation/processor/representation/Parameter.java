package de.orat.math.cgacasadi.delegating.annotation.processor.representation;

import de.orat.math.cgacasadi.delegating.annotation.processor.GenerateDelegatingProcessor.Utils;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Convention: representation of target structure, not source structure. With other words, being directly
 * usable by generation classes.
 */
public final class Parameter {

    public final TypeMirror type;
    public final String identifier;

    protected Parameter(VariableElement correspondingElement, TypeParametersToArguments typeParametersToArguments, Utils utils) {
        this.type = typeParametersToArguments.clearTypeParameterIfPresent(correspondingElement.asType());
        this.identifier = correspondingElement.getSimpleName().toString();
    }
}
