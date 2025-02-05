package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.SymbolicExpression;

public class Constraint<T extends SymbolicExpression> {

    private final T expression;

    public Constraint(T expression) {
        this.expression = expression;
    }

    public T getExpression() {
        return this.expression;
    }

}
