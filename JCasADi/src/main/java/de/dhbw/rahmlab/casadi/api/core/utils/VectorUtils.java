package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.IntegerVectorCollection;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

/**
 * Utility class for performing operations on vector objects, specifically for computing offsets.
 *
 * This class provides static methods to calculate offsets for instances of MXVector.
 * The methods can compute offsets based on specified orientations, either vertically or horizontally.
 *
 * The utility methods return an IntegerVectorCollection containing the computed offsets.
 */
public class VectorUtils {

    /**
     * Computes the offsets for the given MXVector based on the specified orientation.
     *
     * @param v The MXVector representing the vector.
     * @param vert A boolean indicating whether to compute vertical offsets.
     * @return IntegerVectorCollection. A new IntegerVectorCollection containing the computed offsets.
     */
    public static IntegerVectorCollection computeOffset(MXVector v, boolean vert) {
        return new IntegerVectorCollection(MX.offset(v.getCasADiObject(), vert));
    }

    /**
     * Computes the offsets for the given MXVector.
     *
     * @param v The MXVector representing the vector.
     * @return IntegerVectorCollection. A new IntegerVectorCollection containing the computed offsets.
     */
    public static IntegerVectorCollection computeOffset(MXVector v) {
        return new IntegerVectorCollection(MX.offset(v.getCasADiObject()));
    }

    /**
     * Performs common subexpression elimination on the given expressions.
     *
     * @param e The MXVector representing the expressions.
     * @return MXVector. A new MXVector containing the optimized expressions.
     */
    public static MXVector cse(MXVector e) {
        return new MXVector(MX.cse(e.getCasADiObject()));
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
        return new MXVector(MX.substitute(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject()));
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
        MX.substitute_inplace(variables.getCasADiObject(), values.getCasADiObject(), expressions.getCasADiObject(), reverse);
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
    public static void extract(MXVector ex, MXVector v, MXVector vdef, Dict opts) {
        MX.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), opts);
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
        MX.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject());
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
        MX.shared(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), v_prefix, v_suffix);
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
        return new MXVector(MX.graph_substitute(ex.getCasADiObject(), expr.getCasADiObject(), exprs.getCasADiObject()));
    }

}
