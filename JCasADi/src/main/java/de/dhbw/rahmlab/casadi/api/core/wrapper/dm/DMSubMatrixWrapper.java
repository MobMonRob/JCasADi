package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.SubMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.DmSubMatrix;

public class DMSubMatrixWrapper implements SubMatrix {

    private DmSubMatrix subMatrix;

    public DMSubMatrixWrapper(DmSubMatrix subMatrix) {
        this.subMatrix = subMatrix;
    }

    public DmSubMatrix getCasADiObject() {
        return this.subMatrix;
    }

}
