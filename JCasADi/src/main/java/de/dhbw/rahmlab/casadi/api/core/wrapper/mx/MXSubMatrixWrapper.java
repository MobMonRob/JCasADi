package de.dhbw.rahmlab.casadi.api.core.wrapper.mx;

import de.dhbw.rahmlab.casadi.api.core.interfaces.SubMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.MxSubMatrix;

/**
 * Wrapper class for MxSubMatrix to provide a more user-friendly interface for CasADi operations.
 * This class extends MXWrapper and allows for operations on sub-matrices of matrices.
 */
public class MXSubMatrixWrapper extends MXWrapper implements SubMatrix {

    private final MxSubMatrix mxSubMatrix;

    /**
     * Constructs a new MXSubMatrixWrapper from a given MXWrapper and indices.
     *
     * @param mat The MXWrapper object representing the matrix.
     * @param i The row index for the sub-matrix.
     * @param j The column index for the sub-matrix.
     */
    public MXSubMatrixWrapper(MXWrapper mat, int i, int j) {
        super(mat.getCasADiObject());
        this.mxSubMatrix = new MxSubMatrix(mat.getCasADiObject(), i, j);
    }

    /**
     * Constructs a new MXSubMatrixWrapper from an existing MxSubMatrix object.
     *
     * @param y The MxSubMatrix object to wrap.
     */
    public MXSubMatrixWrapper(MxSubMatrix y) {
        super(y);
        this.mxSubMatrix = new MxSubMatrix(y);
    }

    /**
     * Copy constructor for MXSubMatrixWrapper.
     *
     * @param other The MXSubMatrixWrapper object to copy.
     */
    public MXSubMatrixWrapper(MXSubMatrixWrapper other) {
        super(other.getCasADiObject());
        this.mxSubMatrix = new MxSubMatrix(other.getCasADiObject());
    }

    /**
     * Assigns the value of another MxSubMatrix to this MXSubMatrixWrapper.
     *
     * @param other The MxSubMatrix object whose value is to be assigned.
     */
    public void assign(MxSubMatrix other) {
        this.mxSubMatrix.assign(other);
    }

    /**
     * Assigns the value of another MXSubIndexWrapper to this MXSubMatrixWrapper.
     *
     * @param other The MXSubIndexWrapper object whose value is to be assigned.
     */
    public void assign(MXSubIndexWrapper other) {
        this.mxSubMatrix.assign(other.getCasADiObject());
    }

    /**
     * Assigns the value of another MXWrapper to this MXSubMatrixWrapper.
     *
     * @param other The MXWrapper object whose value is to be assigned.
     */
    public void assign(MXWrapper other) {
        this.mxSubMatrix.assign(other.getCasADiObject());
    }

    /**
     * Returns the underlying MxSubMatrix object.
     *
     * @return The MxSubMatrix object wrapped by this MXSubMatrixWrapper.
     */
    public MxSubMatrix getCasADiObject() {
        return this.mxSubMatrix;
    }

}
