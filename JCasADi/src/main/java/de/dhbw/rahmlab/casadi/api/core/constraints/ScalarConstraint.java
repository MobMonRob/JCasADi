package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

public class ScalarConstraint extends AbstractConstraint {

    public ScalarConstraint(MXWrapper lhs, Comparison cmp, MXWrapper rhs) {
        super(lhs, cmp, rhs);
    }

    @Override
    protected Constraint create(MXWrapper newLhs, Comparison cmp, MXWrapper newRhs) {
        return new ScalarConstraint(newLhs, cmp, newRhs);
    }

}
