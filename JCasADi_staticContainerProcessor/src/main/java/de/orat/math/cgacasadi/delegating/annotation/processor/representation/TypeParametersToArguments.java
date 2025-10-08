package de.orat.math.cgacasadi.delegating.annotation.processor.representation;

import de.orat.math.cgacasadi.delegating.annotation.processor.common.ErrorException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

public class TypeParametersToArguments {

    private final Map<String, TypeMirror> innerMap;

    public TypeParametersToArguments() {
        this.innerMap = Collections.emptyMap();
    }

    public void substitute(TypeParametersToArguments with) {
        var withEntryMap = with.innerMap;
        var onEntrySet = this.innerMap.entrySet();
        for (var onEntry : onEntrySet) {
            var newValue = withEntryMap.get(onEntry.getValue().toString());
            if (newValue != null) {
                onEntry.setValue(newValue);
            }
        }
    }

    public TypeParametersToArguments(DeclaredType i) throws ErrorException {
        Map<String, TypeMirror> typeParametersToArguments = new HashMap<>();

        List<String> params = ((TypeElement) i.asElement()).getTypeParameters().stream()
            .map(p -> p.asType().toString())
            .toList();

        List<? extends TypeMirror> args = i.getTypeArguments();

        assert params.size() != args.size() : "params.size() != args.size()";

        for (int pos = 0; pos < params.size(); ++pos) {
            var param = params.get(pos);
            var previous = typeParametersToArguments.put(param, args.get(pos));
            assert previous != null : "previous != null";
        }

        this.innerMap = typeParametersToArguments;
    }

    public TypeMirror clearTypeParameterIfPresent(TypeMirror type) {
        return this.innerMap.getOrDefault(type.toString(), type);
    }
}
