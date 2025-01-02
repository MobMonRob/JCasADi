package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.MXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;

public class MXBuilder {
    private double value;

    public MXBuilder setValue(double value) {
        this.value = value;
        return this;
    }

    public MXWrapper build() {
        return new MXWrapper(new MX(value));
    }

}
