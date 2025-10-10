package de.dhbw.rahmlab.casadi.api.core.wrapper.im;

import de.dhbw.rahmlab.casadi.api.core.interfaces.SubMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.ImSubMatrix;

public class IMSubMatrixWrapper extends IMWrapper implements SubMatrix {

    private final ImSubMatrix imSubMatrix;

    public IMSubMatrixWrapper(IMWrapper mat, int i, int j) {
        super(mat.getCasADiObject());
        this.imSubMatrix = new ImSubMatrix(mat.getCasADiObject(), i, j);
    }

    public IMSubMatrixWrapper(ImSubMatrix y) {
        super(y);
        this.imSubMatrix = new ImSubMatrix(y);
    }

    public IMSubMatrixWrapper(IMSubMatrixWrapper other) {
        super(other.getCasADiObject());
        this.imSubMatrix = new ImSubMatrix(other.getCasADiObject());
    }

    public void assign(ImSubMatrix other) {
        this.imSubMatrix.assign(other);
    }

    public void assign(IMSubMatrixWrapper other) {
        this.imSubMatrix.assign(other.getCasADiObject());
    }

    public void assign(IMWrapper other) {
        this.imSubMatrix.assign(other.getCasADiObject());
    }

    public ImSubMatrix getCasADiObject() {
        return this.imSubMatrix;
    }

}
