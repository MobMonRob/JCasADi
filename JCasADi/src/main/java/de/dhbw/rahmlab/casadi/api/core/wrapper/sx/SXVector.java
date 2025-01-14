package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

public class SXVector implements Vector {

    private StdVectorSX stdVectorSX;

    public SXVector(SXWrapper... initialElements) {
        this.stdVectorSX = new StdVectorSX();
        for (SXWrapper element : initialElements) {
            this.stdVectorSX.add(element.getCasADiObject());
        }
    }

    public StdVectorSX getCasADiObject() {
        return this.stdVectorSX;
    }

}
