package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Collection;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorSX;

public class SXVectorCollection implements Collection {

    StdVectorVectorSX stdVectorVectorSX;

    public StdVectorVectorSX getCasADiObject() {
        return this.stdVectorVectorSX;
    }

}
