package de.dhbw.rahmlab.casadi.api.core.wrapper.dict;

import de.dhbw.rahmlab.casadi.api.core.wrapper.generictype.CasADiGenericWrapper;

public record KeyValue(String key, CasADiGenericWrapper value) {
    public KeyValue {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
    }

}