package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.interfaces.SubIndex;
import de.dhbw.rahmlab.casadi.impl.casadi.SxSubIndex;

public class SXSubIndexWrapper extends SXWrapper implements SubIndex {

    private final SxSubIndex subIndex;

    public SXSubIndexWrapper(SXWrapper mat, int i) {
        super(mat.getCasADiObject());
        this.subIndex = new SxSubIndex(mat.getCasADiObject(), i);
    }

    public SXSubIndexWrapper(SxSubIndex y) {
        super(y);
        this.subIndex = new SxSubIndex(y);
    }

    public SXSubIndexWrapper(SXSubIndexWrapper other) {
        super(other.getCasADiObject());
        this.subIndex = new SxSubIndex(other.getCasADiObject());
    }

    public void assign(SxSubIndex y) {
        this.subIndex.assign(y);
    }

    public void assign(SXSubIndexWrapper other) {
        this.subIndex.assign(other.getCasADiObject());
    }

    public void assign(SXWrapper other) {
        this.subIndex.assign(other.getCasADiObject());
    }

    public SxSubIndex getCasADiObject() {
        return this.subIndex;
    }

}
