package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Collection;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDM;

public class DMVectorCollection implements Collection {

    StdVectorVectorDM stdVectorVectorDM;

    public DMVectorCollection(StdVectorVectorDM stdVectorVectorDM) {
        this.stdVectorVectorDM = new StdVectorVectorDM(stdVectorVectorDM);
    }

    public StdVectorVectorDM getCasADiObject() {
        return this.stdVectorVectorDM;
    }

}
