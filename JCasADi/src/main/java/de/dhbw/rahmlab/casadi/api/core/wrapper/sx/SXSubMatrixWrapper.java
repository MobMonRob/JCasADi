package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.interfaces.SubMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.SxSubMatrix;

public class SXSubMatrixWrapper extends SXWrapper implements SubMatrix {

    private final SxSubMatrix subMatrix;

    public SXSubMatrixWrapper(SXWrapper mat, int i, int j) {
        super(mat.getCasADiObject());
        this.subMatrix = new SxSubMatrix(mat.getCasADiObject(), i, j);
    }

    public SXSubMatrixWrapper(SxSubMatrix y) {
        super(y);
        this.subMatrix = new SxSubMatrix(y);
    }

    public SXSubMatrixWrapper(SXSubMatrixWrapper other) {
        super(other.getCasADiObject());
        this.subMatrix = new SxSubMatrix(other.getCasADiObject());
    }

    public void assign(SxSubMatrix other) {
        this.subMatrix.assign(other);
    }

    public void assign(SXSubMatrixWrapper other) {
        this.subMatrix.assign(other.getCasADiObject());
    }

    public void assign(SXWrapper other) {
        this.subMatrix.assign(other.getCasADiObject());
    }

    public SxSubMatrix getCasADiObject() {
        return this.subMatrix;
    }

}
