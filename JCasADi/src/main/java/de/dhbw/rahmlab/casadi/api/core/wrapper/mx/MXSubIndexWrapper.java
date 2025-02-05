package de.dhbw.rahmlab.casadi.api.core.wrapper.mx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.SubIndex;
import de.dhbw.rahmlab.casadi.impl.casadi.MxSubIndex;

/**
 * Wrapper class for MxSubIndex to provide a more user-friendly interface for CasADi operations.
 * This class extends MXWrapper and allows for operations on sub-indices of matrices.
 */
public class MXSubIndexWrapper extends MXWrapper implements SubIndex {

    private final MxSubIndex mxSubIndex;

    /**
     * Constructs a new MXSubIndexWrapper from a given MXWrapper and an index.
     *
     * @param mat The MXWrapper object representing the matrix.
     * @param i The index for the sub-index.
     */
    public MXSubIndexWrapper(MXWrapper mat, int i) {
        super(mat.getCasADiObject());
        this.mxSubIndex = new MxSubIndex(mat.getCasADiObject(), i);
    }

    /**
     * Constructs a new MXSubIndexWrapper from an existing MxSubIndex object.
     *
     * @param y The MxSubIndex object to wrap.
     */
    public MXSubIndexWrapper(MxSubIndex y) {
        super(y);
        this.mxSubIndex = new MxSubIndex(y);
    }

    /**
     * Copy constructor for MXSubIndexWrapper.
     *
     * @param other The MXSubIndexWrapper object to copy.
     */
    public MXSubIndexWrapper(MXSubIndexWrapper other) {
        super(other.getCasADiObject());
        this.mxSubIndex = new MxSubIndex(other.getCasADiObject());
    }

    /**
     * Assigns the value of another MxSubIndex to this MXSubIndexWrapper.
     *
     * @param y The MxSubIndex object whose value is to be assigned.
     */
    public void assign(MxSubIndex y) {
        this.mxSubIndex.assign(y);
    }

    /**
     * Assigns the value of another MXSubIndexWrapper to this MXSubIndexWrapper.
     *
     * @param other The MXSubIndexWrapper object whose value is to be assigned.
     */
    public void assign(MXSubIndexWrapper other) {
        this.mxSubIndex.assign(other.getCasADiObject());
    }

    /**
     * Assigns the value of another MXWrapper to this MXSubIndexWrapper.
     *
     * @param other The MXWrapper object whose value is to be assigned.
     */
    public void assign(MXWrapper other) {
        this.mxSubIndex.assign(other.getCasADiObject());
    }

    /**
     * Returns the underlying MxSubIndex object.
     *
     * @return The MxSubIndex object wrapped by this MXSubIndexWrapper.
     */
    public MxSubIndex getCasADiObject() {
        return this.mxSubIndex;
    }

}
