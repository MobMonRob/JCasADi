package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.SubIndex;
import de.dhbw.rahmlab.casadi.impl.casadi.DmSubIndex;

public class DMSubIndexWrapper implements SubIndex {

    private DmSubIndex dmSubIndex;

    public DMSubIndexWrapper(DmSubIndex dmSubIndex) {
        this.dmSubIndex = dmSubIndex;
    }

    public DmSubIndex getCasADiObject() {
        return this.dmSubIndex;
    }

}
