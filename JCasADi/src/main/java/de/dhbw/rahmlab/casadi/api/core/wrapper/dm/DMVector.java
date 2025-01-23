package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;

public class DMVector implements Vector {

    private StdVectorDM stdVectorDM;

    public DMVector(StdVectorDM stdVectorDM) {
        this.stdVectorDM = new StdVectorDM(stdVectorDM);
    }

    public StdVectorDM getCasADiObject() {
        return this.stdVectorDM;
    }

}
