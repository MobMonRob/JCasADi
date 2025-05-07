package de.dhbw.rahmlab.casadi.api.core.wrapper.im;

import de.dhbw.rahmlab.casadi.api.core.interfaces.SubIndex;
import de.dhbw.rahmlab.casadi.impl.casadi.ImSubIndex;

public class IMSubIndexWrapper extends IMWrapper implements SubIndex {

    private final ImSubIndex imSubIndex;

    public IMSubIndexWrapper(IMWrapper mat, int i) {
        super(mat.getCasADiObject());
        this.imSubIndex = new ImSubIndex(mat.getCasADiObject(), i);
    }

    public IMSubIndexWrapper(ImSubIndex y) {
        super(y);
        this.imSubIndex = new ImSubIndex(y);
    }

    public IMSubIndexWrapper(IMSubIndexWrapper other) {
        super(other.getCasADiObject());
        this.imSubIndex = new ImSubIndex(other.getCasADiObject());
    }

    public void assign(ImSubIndex y) {
        this.imSubIndex.assign(y);
    }

    public void assign(IMSubIndexWrapper other) {
        this.imSubIndex.assign(other.getCasADiObject());
    }

    public void assign(IMWrapper other) {
        this.imSubIndex.assign(other.getCasADiObject());
    }

    public ImSubIndex getCasADiObject() {
        return this.imSubIndex;
    }

}
