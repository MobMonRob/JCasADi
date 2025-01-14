package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;

public class DMVector {

    private StdVectorDM stdVectorDM;

    public StdVectorDM getCasADiObject() {
        return this.stdVectorDM;
    }

}
