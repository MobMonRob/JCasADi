package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

public class DMBuilder {
    private StdVectorDouble values;

    public DMBuilder() {
        this.values = new StdVectorDouble();
    }

    public DMBuilder setValues(StdVectorDouble values) {
        this.values = values;
        return this;
    }

    public DMBuilder addValue(double value) {
        this.values.add(value);
        return this;
    }

    public DMWrapper build() {
        return new DMWrapper(new de.dhbw.rahmlab.casadi.impl.casadi.DM(values));
    }
}

