package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.interfaces.SubIndex;
import de.dhbw.rahmlab.casadi.impl.casadi.DmSubIndex;

public class DMSubIndexWrapper extends DMWrapper implements SubIndex {

    private final DmSubIndex dmSubIndex;

    public DMSubIndexWrapper(DmSubIndex y) {
        super(y);
        this.dmSubIndex = new DmSubIndex(y);
    }

    public DMSubIndexWrapper(DMSubIndexWrapper other) {
        super(other.getCasADiObject());
        this.dmSubIndex = new DmSubIndex(other.getCasADiObject());
    }

    public DMSubIndexWrapper(DMWrapper mat, int i) {
        super(mat.getCasADiObject());
        this.dmSubIndex = new DmSubIndex(mat.getCasADiObject(), i);
    }

    public void assign(DMSubIndexWrapper other) {
        this.dmSubIndex.assign(other.getCasADiObject());
    }

    public void assign(DmSubIndex y) {
        this.dmSubIndex.assign(y);
    }

    public void assign(DMWrapper other) {
        this.dmSubIndex.assign(other.getCasADiObject());
    }

    public DmSubIndex getCasADiObject() {
        return this.dmSubIndex;
    }

}
