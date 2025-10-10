package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.OptiSol;

/**
 * Represents the result of an NLP (Non-Linear Programming) solution.
 * This class provides methods to access solution details, values, and statistics.
 */
public class NLPResult {

    private final OptiSol optiSol;

    /**
     * Constructs an NLPResult using an existing OptiSol object.
     *
     * @param other the OptiSol object used to initialize the result.
     */
    public NLPResult(OptiSol other) {
        this.optiSol = new OptiSol(other);
    }

    /**
     * Constructs an NLPResult using another NLPResult object.
     *
     * @param other the NLPResult object used to initialize the result.
     */
    public NLPResult(NLPResult other) {
        this.optiSol = new OptiSol(other.getCasADiObject());
    }

    /**
     * Retrieves the type name of the solution.
     *
     * @return a string representing the type name.
     */
    public String typeName() {
        return this.optiSol.type_name();
    }

    /**
     * Converts the solution to a string representation with additional details.
     *
     * @param more a flag indicating whether to include more details.
     * @return a string representation of the solution.
     */
    public String toString(boolean more) {
        return this.optiSol.toString(more);
    }

    /**
     * Converts the solution to a string representation.
     *
     * @return a string representation of the solution.
     */
    public String toString() {
        return this.optiSol.toString();
    }

    /**
     * Retrieves the value associated with a given wrapper and optional values.
     *
     * @param x      the wrapper representing the variable or expression.
     * @param values optional values for the expression.
     * @return a DMWrapper containing the value.
     */
    public DMWrapper value(Wrapper<?> x, MXWrapper... values) {
        if (values == null || values.length == 0) {
            return handleValueWithoutParameters(x);
        } else {
            MXVector vector = new MXVector(values);
            return handleValueWithParameters(x, vector);
        }
    }

    /**
     * Retrieves the value associated with a given wrapper and values.
     *
     * @param x      the wrapper representing the variable or expression.
     * @param values the values for the expression.
     * @return a DMWrapper containing the value.
     */
    public DMWrapper value(Wrapper<?> x, MXVector values) {
        if (values == null) {
            return handleValueWithoutParameters(x);
        } else {
            return handleValueWithParameters(x, values);
        }
    }

    /**
     * Retrieves the values of the variables in the solution.
     *
     * @return an MXVector containing the values of the variables.
     */
    public MXVector valueVariables() {
        return new MXVector(this.optiSol.value_variables());
    }

    /**
     * Retrieves the values of the parameters in the solution.
     *
     * @return an MXVector containing the values of the parameters.
     */
    public MXVector valueParameters() {
        return new MXVector(this.optiSol.value_parameters());
    }

    /**
     * Retrieves the statistics of the solution.
     *
     * @return a Dictionary containing the statistics.
     */
    public Dictionary getStats() {
        return new Dictionary(this.optiSol.stats());
    }

    /**
     * Retrieves the NLPProblem associated with the solution.
     *
     * @return an NLPProblem object.
     */
    public NLPProblem getProblem() {
        return new NLPProblem(this.optiSol.opti());
    }

    /**
     * Retrieves the underlying CasADi object.
     *
     * @return the OptiSol object.
     */
    public OptiSol getCasADiObject() {
        return this.optiSol;
    }

    /**
     * Retrieves the value associated with a given MXWrapper and values.
     *
     * @param x      the MXWrapper representing the variable or expression.
     * @param values the values for the expression.
     * @return a DMWrapper containing the value.
     */
    private DMWrapper value(MXWrapper x, MXVector values) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject(), values.getCasADiObject()));
    }

    /**
     * Retrieves the value associated with a given MXWrapper.
     *
     * @param x the MXWrapper representing the variable or expression.
     * @return a DMWrapper containing the value.
     */
    private DMWrapper value(MXWrapper x) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject()));
    }

    /**
     * Retrieves the value associated with a given DMWrapper and values.
     *
     * @param x      the DMWrapper representing the variable or expression.
     * @param values the values for the expression.
     * @return a DMWrapper containing the value.
     */
    private DMWrapper value(DMWrapper x, MXVector values) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject(), values.getCasADiObject()));
    }

    /**
     * Retrieves the value associated with a given DMWrapper.
     *
     * @param x the DMWrapper representing the variable or expression.
     * @return a DMWrapper containing the value.
     */
    private DMWrapper value(DMWrapper x) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject()));
    }

    /**
     * Retrieves the value associated with a given SXWrapper and values.
     *
     * @param x      the SXWrapper representing the variable or expression.
     * @param values the values for the expression.
     * @return a DMWrapper containing the value.
     */
    private DMWrapper value(SXWrapper x, MXVector values) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject(), values.getCasADiObject()));
    }

    /**
     * Retrieves the value associated with a given SXWrapper.
     *
     * @param x the SXWrapper representing the variable or expression.
     * @return a DMWrapper containing the value.
     */
    private DMWrapper value(SXWrapper x) {
        return new DMWrapper(this.optiSol.value(x.getCasADiObject()));
    }

    /**
     * Handles the retrieval of value without parameters for a given wrapper.
     *
     * @param x the wrapper representing the variable or expression.
     * @return a DMWrapper containing the value.
     * @throws IllegalArgumentException if the wrapper type is unsupported.
     */
    private DMWrapper handleValueWithoutParameters(Wrapper<?> x) {
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

    /**
     * Handles the retrieval of value with parameters for a given wrapper.
     *
     * @param x      the wrapper representing the variable or expression.
     * @param values the values for the expression.
     * @return a DMWrapper containing the value.
     * @throws IllegalArgumentException if the wrapper type is unsupported.
     */
    private DMWrapper handleValueWithParameters(Wrapper<?> x, MXVector values) {
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
