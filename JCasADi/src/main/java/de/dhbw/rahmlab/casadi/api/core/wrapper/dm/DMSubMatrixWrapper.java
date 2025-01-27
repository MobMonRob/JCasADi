package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.SubMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.DmSubMatrix;

public class DMSubMatrixWrapper extends DMWrapper implements SubMatrix {

    private DmSubMatrix subMatrix;

    public DMSubMatrixWrapper(DmSubMatrix y) {
        super(y);
        this.subMatrix = new DmSubMatrix(y);
    }

    public DMSubMatrixWrapper(DMSubMatrixWrapper other) {
        super(other.getCasADiObject());
        this.subMatrix = new DmSubMatrix(other.getCasADiObject());
    }

    public DMSubMatrixWrapper(DMWrapper mat, int i, int j) {
        super(mat.getCasADiObject());
        this.subMatrix = new DmSubMatrix(mat.getCasADiObject(), i, j);
    }

    public void assign(DmSubMatrix y) {
        this.subMatrix.assign(y);
    }

    public void assign(DMWrapper other) {
        this.subMatrix.assign(other.getCasADiObject());
    }

    public void assign(DMSubMatrixWrapper other) {
        this.subMatrix.assign(other.getCasADiObject());
    }

    public DmSubMatrix getCasADiObject() {
        return this.subMatrix;
    }

}
