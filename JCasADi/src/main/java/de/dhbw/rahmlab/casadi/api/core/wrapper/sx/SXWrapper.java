package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;

public class SXWrapper implements Wrapper {

    private SX sx;

    public SXWrapper() {
        this.sx = new SX();
    }

    public SXWrapper(SX sx) {
        this.sx = sx;
    }

    public SX getCasADiObject() {
        return this.sx;
    }

}
