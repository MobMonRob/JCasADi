package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.OptiSol;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

public class NLPResult {

    private final OptiSol optiSol;

    public NLPResult(OptiSol other) {
        this.optiSol = new OptiSol(other);
    }

    public NLPResult(NLPResult other) {
        this.optiSol = new OptiSol(other.getCasADiObject());
    }

    public String typeName() {
        return this.optiSol.type_name();
    }

    public String toString(boolean more) {
        return this.optiSol.toString(more);
    }

    public String toString() {
        return this.optiSol.toString();
    }

    public DMWrapper value(Wrapper x, MXWrapper... values) {
        if (values == null || values.length == 0) {
            return handleValueWithoutParameters(x);
        } else {
            MXVector vector = new MXVector(values);
            return handleValueWithParameters(x, vector);
        }
    }

    public DMWrapper value(Wrapper x, MXVector values) {
        if (values == null) {
            return handleValueWithoutParameters(x);
        } else {
            return handleValueWithParameters(x, values);
        }
    }

    public MXVector valueVariables() {
        return new MXVector(this.optiSol.value_variables());
    }

    public MXVector valueParameters() {
        return new MXVector(this.optiSol.value_parameters());
    }

    public Dict getStats() {
        return this.optiSol.stats();
    }

    public NLPProblem getProblem() {
        return new NLPProblem(this.optiSol.opti());
    }

    public OptiSol getCasADiObject() {
        return this.optiSol;
    }

    private DMWrapper value(MXWrapper x, MXVector values) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject(), values.getCasADiObject()));
    }

    private DMWrapper value(MXWrapper x) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject()));
    }

    private DMWrapper value(DMWrapper x, MXVector values) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject(), values.getCasADiObject()));
    }

    private DMWrapper value(DMWrapper x) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject()));
    }

    private DMWrapper value(SXWrapper x, MXVector values) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject(), values.getCasADiObject()));
    }

    private DMWrapper value(SXWrapper x) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject()));
    }

    private DMWrapper handleValueWithoutParameters(Wrapper x) {
        if (x instanceof MXWrapper mxWrapper) {
            return new DMWrapper(this.optiSol.value(mxWrapper.getCasADiObject()));
        } else if (x instanceof DMWrapper dmWrapper) {
            return new DMWrapper(this.optiSol.value(dmWrapper.getCasADiObject()));
        } else if (x instanceof SXWrapper sxWrapper) {
            return new DMWrapper(this.optiSol.value(sxWrapper.getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported wrapper type: " + x.getClass().getName());
        }
    }

    private DMWrapper handleValueWithParameters(Wrapper x, MXVector values) {
        if (x instanceof MXWrapper mxWrapper) {
            return new DMWrapper(this.optiSol.value(mxWrapper.getCasADiObject(), values.getCasADiObject()));
        } else if (x instanceof DMWrapper dmWrapper) {
            return new DMWrapper(this.optiSol.value(dmWrapper.getCasADiObject(), values.getCasADiObject()));
        } else if (x instanceof SXWrapper sxWrapper) {
            return new DMWrapper(this.optiSol.value(sxWrapper.getCasADiObject(), values.getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported wrapper type: " + x.getClass().getName());
        }
    }

}
