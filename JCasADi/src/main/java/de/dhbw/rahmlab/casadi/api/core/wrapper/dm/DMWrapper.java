package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

public class DMWrapper {
    private DM dm;

    public DMWrapper(DM dm) {
        this.dm = dm;
    }

    public static DMWrapper fromValue(double value) {
        return new DMWrapper(new DM(value));
    }

    public static DMWrapper fromArray(StdVectorDouble values) {
        return new DMWrapper(new DM(values));
    }

    public DMWrapper add(DMWrapper other) {
        DM result = DM.plus(this.dm, other.dm);
        return new DMWrapper(result);
    }

    public DMWrapper subtract(DMWrapper other) {
        DM result = DM.minus(this.dm, other.dm);
        return new DMWrapper(result);
    }

    public DMWrapper multiply(DMWrapper other) {
        DM result = DM.times(this.dm, other.dm);
        return new DMWrapper(result);
    }

    public DMWrapper transpose() {
        DM result = this.dm.T();
        return new DMWrapper(result);
    }

    // Weitere Operationen (Inverse, Determinante etc.) können hier hinzugefügt werden

    public DM getCasADiObject() {
        return this.dm;
    }
}
