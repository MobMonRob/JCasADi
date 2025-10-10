package de.dhbw.rahmlab.casadi.api.core.wrapper.mx;

import de.dhbw.rahmlab.casadi.MxStatic;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;

public class MXUtils {

    /**
     * Computes the offsets for the given MXVector based on the specified orientation.
     *
     * @param v The MXVector representing the vector.
     * @param vert A boolean indicating whether to compute vertical offsets.
     * @return IntegerVectorCollection. A new IntegerVectorCollection containing the computed offsets.
     */
    public static CasADiIntVector offset(MXVector v, boolean vert) {
        return new CasADiIntVector(MxStatic.offset(v.getCasADiObject(), vert));
    }

    /**
     * Computes the offsets for the given MXVector.
     *
     * @param v The MXVector representing the vector.
     * @return IntegerVectorCollection. A new IntegerVectorCollection containing the computed offsets.
     */
    public static CasADiIntVector offset(MXVector v) {
        return new CasADiIntVector(MxStatic.offset(v.getCasADiObject()));
    }

    /**
     * Performs common subexpression elimination on the given expressions.
     *
     * @param e The MXVector representing the expressions.
     * @return MXVector. A new MXVector containing the optimized expressions.
     */
    public static MXVector cse(MXVector e) {
        return new MXVector(MxStatic.cse(e.getCasADiObject()));
    }

    /**
     * Computes the forward mode of automatic differentiation for the given expression.
     *
     * @param ex The MXVector representing the expression to differentiate.
     * @param arg The MXVector representing the arguments of the expression.
     * @param v The VectorCollection representing the values for the forward mode.
     * @param opts Options for the forward computation.
     * @return VectorCollection. A new VectorCollection containing the result of the forward mode.
     */
    public static MXVectorCollection forward(MXVector ex, MXVector arg, MXVectorCollection v, Dictionary opts) {
        return new MXVectorCollection(MxStatic.forward(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Computes the forward mode of automatic differentiation for the given expression.
     *
     * @param ex The MXVector representing the expression to differentiate.
     * @param arg The MXVector representing the arguments of the expression.
     * @param v The VectorCollection representing the values for the forward mode.
     * @return VectorCollection. A new VectorCollection containing the result of the forward mode.
     */
    public static MXVectorCollection forward(MXVector ex, MXVector arg, MXVectorCollection v) {
        return new MXVectorCollection(MxStatic.forward(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject()));
    }

    /**
     * Computes the reverse mode of automatic differentiation for the given expression.
     *
     * @param ex The MXVector representing the expression to differentiate.
     * @param arg The MXVector representing the arguments of the expression.
     * @param v The VectorCollection representing the values for the reverse mode.
     * @param opts Options for the reverse computation.
     * @return VectorCollection. A new VectorCollection containing the result of the reverse mode.
     */
    public static MXVectorCollection reverse(MXVector ex, MXVector arg, MXVectorCollection v, Dictionary opts) {
        return new MXVectorCollection(MxStatic.reverse(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Computes the reverse mode of automatic differentiation for the given expression.
     *
     * @param ex The MXVector representing the expression to differentiate.
     * @param arg The MXVector representing the arguments of the expression.
     * @param v The VectorCollection representing the values for the reverse mode.
     * @return VectorCollection. A new VectorCollection containing the result of the reverse mode.
     */
    public static MXVectorCollection reverse(MXVector ex, MXVector arg, MXVectorCollection v) {
        return new MXVectorCollection(MxStatic.reverse(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject()));
    }

    /**
     * Substitutes a vector of variables in a vector of expressions with given values.
     *
     * @param ex The MXVector representing the expressions to substitute in.
     * @param v The MXVector representing the variables to substitute.
     * @param vdef The MXVector representing the values to substitute in.
     * @return MXVector. A new MXVector containing the results of the substitutions.
     */
    public static MXVector substitute(MXVector ex, MXVector v, MXVector vdef) {
        return new MXVector(MxStatic.substitute(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject()));
    }

    /**
     * Substitutes variables in the given expressions in place with the specified values.
     *
     * This method modifies the expressions directly by substituting the variables
     * with the provided values. The substitution can be performed in reverse order
     * if specified.
     *
     * @param variables The MXVector representing the variables to substitute.
     * @param values The MXVector representing the values to substitute in.
     * @param expressions The MXVector representing the expressions to modify.
     * @param reverse A boolean indicating whether to perform the substitution in reverse order.
     */
    public static void substituteInPlace(MXVector variables, MXVector values, MXVector expressions, boolean reverse) {
        MxStatic.substitute_inplace(variables.getCasADiObject(), values.getCasADiObject(), expressions.getCasADiObject(), reverse);
    }

    /**
     * Extracts values from the expressions represented by the given vectors.
     *
     * This method uses the provided options dictionary for extraction.
     *
     * @param ex The MXVector representing the expressions to extract from.
     * @param v The MXVector representing the variables.
     * @param vdef The MXVector representing the values to substitute.
     * @param opts The dictionary containing options for extraction.
     */
    public static void extract(MXVector ex, MXVector v, MXVector vdef, Dictionary opts) {
        MxStatic.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), opts.getCasADiObject());
    }

    /**
     * Extracts values from the expressions represented by the given vectors.
     *
     * This method does not use an options dictionary for extraction.
     *
     * @param ex The MXVector representing the expressions to extract from.
     * @param v The MXVector representing the variables.
     * @param vdef The MXVector representing the values to substitute.
     */
    public static void extract(MXVector ex, MXVector v, MXVector vdef) {
        MxStatic.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject());
    }

    /**
     * Shares variables in the context of the given expressions.
     *
     * @param ex The MXVector representing the expressions.
     * @param v The MXVector representing the variables.
     * @param vdef The MXVector representing the default values.
     * @param v_prefix The prefix for variable names.
     * @param v_suffix The suffix for variable names.
     */
    public static void shared(MXVector ex, MXVector v, MXVector vdef, String v_prefix, String v_suffix) {
        MxStatic.shared(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), v_prefix, v_suffix);
    }

    /**
     * Evaluates a conditional expression based on the provided condition.
     *
     * @param cond The MXWrapper representing the condition.
     * @param if_true The MXWrapper representing the expression if the condition is true.
     * @param if_false The MXWrapper representing the expression if the condition is false.
     * @param short_circuit A boolean indicating whether to use short-circuit evaluation.
     * @return MXWrapper. A new MXWrapper containing the result of the conditional expression.
     */
    public static MXWrapper ifElse(MXWrapper cond, MXWrapper if_true, MXWrapper if_false, boolean short_circuit) {
        MX result = MxStatic.if_else(cond.getCasADiObject(), if_true.getCasADiObject(), if_false.getCasADiObject(), short_circuit);
        return new MXWrapper(result);
    }

    /**
     * Evaluates a conditional expression based on the provided condition.
     *
     * @param cond The MXWrapper representing the condition.
     * @param if_true The MXWrapper representing the expression if the condition is true.
     * @param if_false The MXWrapper representing the expression if the condition is false.
     * @return MXWrapper. A new MXWrapper containing the result of the conditional expression.
     */
    public static MXWrapper ifElse(MXWrapper cond, MXWrapper if_true, MXWrapper if_false) {
        MX result = MxStatic.if_else(cond.getCasADiObject(), if_true.getCasADiObject(), if_false.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Evaluates a conditional expression based on the provided index.
     *
     * @param ind The MXWrapper representing the index.
     * @param x The MXVector representing the expressions.
     * @param x_default The MXWrapper representing the default expression.
     * @param short_circuit A boolean indicating whether to use short-circuit evaluation.
     * @return MXWrapper. A new MXWrapper containing the result of the conditional expression.
     */
    public static MXWrapper conditional(MXWrapper ind, MXVector x, MXWrapper x_default, boolean short_circuit) {
        MX result = MxStatic.conditional(ind.getCasADiObject(), x.getCasADiObject(), x_default.getCasADiObject(), short_circuit);
        return new MXWrapper(result);
    }

    /**
     * Evaluates a conditional expression based on the provided index.
     *
     * @param ind The MXWrapper representing the index.
     * @param x The MXVector representing the expressions.
     * @param x_default The MXWrapper representing the default expression.
     * @return MXWrapper. A new MXWrapper containing the result of the conditional expression.
     */
    public static MXWrapper conditional(MXWrapper ind, MXVector x, MXWrapper x_default) {
        MX result = MxStatic.conditional(ind.getCasADiObject(), x.getCasADiObject(), x_default.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Substitutes expressions in the graph.
     *
     * @param ex The MXVector representing the expressions to substitute.
     * @param expr The MXVector representing the expressions.
     * @param exprs The MXVector representing the expressions to substitute with.
     * @return MXVector. A new MXVector containing the substituted expressions.
     */
    public static MXVector graphSubstitute(MXVector ex, MXVector expr, MXVector exprs) {
        return new MXVector(MxStatic.graph_substitute(ex.getCasADiObject(), expr.getCasADiObject(), exprs.getCasADiObject()));
    }

    /**
     * Gets the function inputs for the specified function.
     *
     * @param f The Function object for which to get the inputs.
     * @return MXVector. A new MXVector containing the function inputs.
     */
    public static MXVector getInput(FunctionWrapper f) {
        return new MXVector(MxStatic.get_input(f.getCasADiObject()));
    }

    /**
     * Gets the free variables for the specified function.
     *
     * @param f The Function object for which to get the free variables.
     * @return MXVector. A new MXVector containing the free variables.
     */
    public static MXVector getFree(FunctionWrapper f) {
        return new MXVector(MxStatic.get_free(f.getCasADiObject()));
    }

    /**
     * Computes the dual B-spline for the given input and specified knots, degree, and options.
     *
     * @param x The DoubleVector representing the input values.
     * @param knots The DoubleVectorCollection representing the knots.
     * @param degree The IntegerVectorCollection representing the degree of the B-spline.
     * @param opts The dictionary containing options for the B-spline computation.
     * @return DmStatic. A new DM containing the dual B-spline result.
     */
    public static DMWrapper bsplineDual(DoubleVector x, DoubleVectorCollection knots, CasADiIntVector degree, Dictionary opts) {
        return new DMWrapper(MxStatic.bspline_dual(x.getCasADiObject(), knots.getCasADiObject(), degree.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Computes the dual B-spline for the given input and specified knots and degree.
     *
     * @param x The DoubleVector representing the input values.
     * @param knots The DoubleVectorCollection representing the knots.
     * @param degree The IntegerVectorCollection representing the degree of the B-spline.
     * @return DmStatic. A new DM containing the dual B-spline result.
     */
    public static DMWrapper bsplineDual(DoubleVector x, DoubleVectorCollection knots, CasADiIntVector degree) {
        return new DMWrapper(MxStatic.bspline_dual(x.getCasADiObject(), knots.getCasADiObject(), degree.getCasADiObject()));
    }

    /**
     * Converts an instance of {@link SXWrapper} to an instance of {@link MXWrapper}.
     *
     * This method creates a new {@link MXWrapper} object by using the dimensions
     * of the provided {@link SXWrapper}. The resulting {@link MXWrapper} will
     * have the same number of rows and columns as the input {@link SXWrapper}.
     *
     * @param sxWrapper the {@link SXWrapper} instance to be converted
     * @return a new {@link MXWrapper} instance with the same dimensions as the input {@link SXWrapper}
     */
    public static MXWrapper convertSXWrapperToMXWrapper(SXWrapper sxWrapper) {
        return MXWrapper.sym("x", sxWrapper.rows(), sxWrapper.columns());
    }

}
