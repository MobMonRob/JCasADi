package de.dhbw.rahmlab.casadi.api.core.interfaces;

import de.dhbw.rahmlab.casadi.api.core.wrapper.bool.BooleanVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.im.IMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.index.IndexSlice;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.str.StringVector;

/**
 * Interface representing a wrapper for CasADi objects, providing a wide range of operations.
 * This interface defines methods for manipulating and querying CasADi objects, including
 * mathematical operations, matrix manipulations, and symbolic computations.
 *
 * @param <T> The type of the wrapper, allowing for method chaining and fluent interface usage.
 */
public interface Wrapper<T extends Wrapper<?>> {

    /**
     * Returns the type name of the wrapped object.
     *
     * @return The type name as a string.
     */
    String typeName();

    /**
     * Retrieves the sparsity pattern of the wrapped object.
     *
     * @return A SparsityWrapper representing the sparsity pattern.
     */
    SparsityWrapper sparsity();

    /**
     * Checks if the wrapped object has non-zero elements.
     *
     * @return True if there are non-zero elements, false otherwise.
     */
    boolean nonzero();

    /**
     * Erases elements from the wrapped object based on specified row and column indices.
     *
     * @param rr Row indices.
     * @param cc Column indices.
     * @param ind1 Indicator for 1-based indexing.
     */
    void erase(CasADiIntVector rr, CasADiIntVector cc, boolean ind1);

    /**
     * Erases elements from the wrapped object based on specified row and column indices.
     *
     * @param rr Row indices.
     * @param cc Column indices.
     */
    void erase(CasADiIntVector rr, CasADiIntVector cc);

    /**
     * Erases elements from the wrapped object based on specified row indices.
     *
     * @param rr Row indices.
     * @param ind1 Indicator for 1-based indexing.
     */
    void erase(CasADiIntVector rr, boolean ind1);

    /**
     * Erases elements from the wrapped object based on specified row indices.
     *
     * @param rr Row indices.
     */
    void erase(CasADiIntVector rr);

    /**
     * Enlarges the wrapped object to specified dimensions, using row and column indices.
     *
     * @param nrow Number of rows.
     * @param ncol Number of columns.
     * @param rr Row indices.
     * @param cc Column indices.
     * @param ind1 Indicator for 1-based indexing.
     */
    void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc, boolean ind1);

    /**
     * Enlarges the wrapped object to specified dimensions, using row and column indices.
     *
     * @param nrow Number of rows.
     * @param ncol Number of columns.
     * @param rr Row indices.
     * @param cc Column indices.
     */
    void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc);

    /**
     * Retrieves a dependent object based on the specified index.
     *
     * @param ch Index of the dependent object.
     * @return The dependent object.
     */
    T dep(long ch);

    /**
     * Retrieves the default dependent object.
     *
     * @return The default dependent object.
     */
    T dep();

    /**
     * Returns the number of dependent objects.
     *
     * @return The number of dependent objects.
     */
    long nDep();

    /**
     * Retrieves the name of the wrapped object.
     *
     * @return The name as a string.
     */
    String getName();

    /**
     * Checks if the wrapped object is symbolic.
     *
     * @return True if symbolic, false otherwise.
     */
    boolean isSymbolic();

    /**
     * Checks if the wrapped object is constant.
     *
     * @return True if constant, false otherwise.
     */
    boolean isConstant();

    /**
     * Checks if the wrapped object is an operation of the specified type.
     *
     * @param op The operation type.
     * @return True if the object is of the specified operation type, false otherwise.
     */
    boolean isOp(long op);

    /**
     * Checks if the wrapped object is commutative.
     *
     * @return True if commutative, false otherwise.
     */
    boolean isCommutative();

    /**
     * Checks if the wrapped object is a valid input.
     *
     * @return True if valid, false otherwise.
     */
    boolean isValidInput();

    /**
     * Checks if the wrapped object has duplicate elements.
     *
     * @return True if duplicates exist, false otherwise.
     */
    boolean hasDuplicates();

    /**
     * Resets the input state of the wrapped object.
     */
    void resetInput();

    /**
     * Checks if the wrapped object is an identity matrix.
     *
     * @return True if identity matrix, false otherwise.
     */
    boolean isEye();

    /**
     * Checks if the wrapped object is a zero matrix.
     *
     * @return True if zero matrix, false otherwise.
     */
    boolean isZero();

    /**
     * Checks if the wrapped object is a matrix of ones.
     *
     * @return True if matrix of ones, false otherwise.
     */
    boolean isOne();

    /**
     * Checks if the wrapped object is a matrix of minus ones.
     *
     * @return True if matrix of minus ones, false otherwise.
     */
    boolean isMinusOne();

    /**
     * Checks if the wrapped object is regular.
     *
     * @return True if regular, false otherwise.
     */
    boolean isRegular();

    /**
     * Retrieves the operation type of the wrapped object.
     *
     * @return The operation type.
     */
    long op();

    /**
     * Retrieves information about the wrapped object.
     *
     * @return A Dictionary containing information.
     */
    Dictionary info();

    /**
     * Performs a binary operation with another wrapped object.
     *
     * @param op The operation type.
     * @param y The other wrapped object.
     * @return The result of the binary operation.
     */
    T binary(long op, T y);

    /**
     * Performs a unary operation on the wrapped object.
     *
     * @param op The operation type.
     * @return The result of the unary operation.
     */
    T unary(long op);

    /**
     * Retrieves a sub-object based on a slice definition.
     *
     * @param sliceDefinition The slice definition.
     * @return The sub-object.
     */
    T get(String sliceDefinition);

    /**
     * Retrieves a sub-object based on row indices and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @return The sub-object.
     */
    T get(boolean ind1, IndexSlice rr);

    /**
     * Retrieves a sub-object based on row indices and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @return The sub-object.
     */
    T get(boolean ind1, IMWrapper rr);

    /**
     * Retrieves a sub-object based on sparsity pattern and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param sp Sparsity pattern.
     * @return The sub-object.
     */
    T get(boolean ind1, SparsityWrapper sp);

    /**
     * Retrieves a sub-object based on row and column slice definitions.
     *
     * @param sliceDefinitionRR Row slice definition.
     * @param sliceDefinitionCC Column slice definition.
     * @return The sub-object.
     */
    T get(String sliceDefinitionRR, String sliceDefinitionCC);

    /**
     * Retrieves a sub-object based on row and column indices and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @param cc Column indices.
     * @return The sub-object.
     */
    T get(boolean ind1, IndexSlice rr, IndexSlice cc);

    /**
     * Retrieves a sub-object based on row indices and column indices and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @param cc Column indices.
     * @return The sub-object.
     */
    T get(boolean ind1, IndexSlice rr, IMWrapper cc);

    /**
     * Retrieves a sub-object based on row indices and column indices and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @param cc Column indices.
     * @return The sub-object.
     */
    T get(boolean ind1, IMWrapper rr, IndexSlice cc);

    /**
     * Retrieves a sub-object based on row indices and column indices and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @param cc Column indices.
     * @return The sub-object.
     */
    T get(boolean ind1, IMWrapper rr, IMWrapper cc);

    /**
     * Retrieves a sub-object based on a variable number of slice definitions.
     *
     * @param slice Variable number of slice definitions.
     * @return The sub-object.
     */
    T get(NumberWrapper... slice);

    /**
     * Sets a sub-object based on a slice definition.
     *
     * @param m The sub-object to set.
     * @param sliceDefinition The slice definition.
     */
    void set(T m, String sliceDefinition);

    /**
     * Sets a sub-object based on row indices and an indicator for 1-based indexing.
     *
     * @param m The sub-object to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     */
    void set(T m, boolean ind1, IndexSlice rr);

    /**
     * Sets a sub-object based on row indices and an indicator for 1-based indexing.
     *
     * @param m The sub-object to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     */
    void set(T m, boolean ind1, IMWrapper rr);

    /**
     * Sets a sub-object based on sparsity pattern and an indicator for 1-based indexing.
     *
     * @param m The sub-object to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param sp Sparsity pattern.
     */
    void set(T m, boolean ind1, SparsityWrapper sp);

    /**
     * Sets a sub-object based on row and column slice definitions.
     *
     * @param m The sub-object to set.
     * @param sliceDefinitionRR Row slice definition.
     * @param sliceDefinitionCC Column slice definition.
     */
    void set(T m, String sliceDefinitionRR, String sliceDefinitionCC);

    /**
     * Sets a sub-object based on row and column indices and an indicator for 1-based indexing.
     *
     * @param m The sub-object to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @param cc Column indices.
     */
    void set(T m, boolean ind1, IndexSlice rr, IndexSlice cc);

    /**
     * Sets a sub-object based on row indices and column indices and an indicator for 1-based indexing.
     *
     * @param m The sub-object to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @param cc Column indices.
     */
    void set(T m, boolean ind1, IndexSlice rr, IMWrapper cc);

    /**
     * Sets a sub-object based on row indices and column indices and an indicator for 1-based indexing.
     *
     * @param m The sub-object to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @param cc Column indices.
     */
    void set(T m, boolean ind1, IMWrapper rr, IndexSlice cc);

    /**
     * Sets a sub-object based on row indices and column indices and an indicator for 1-based indexing.
     *
     * @param m The sub-object to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param rr Row indices.
     * @param cc Column indices.
     */
    void set(T m, boolean ind1, IMWrapper rr, IMWrapper cc);

    /**
     * Sets a sub-object based on a variable number of slice definitions.
     *
     * @param m The sub-object to set.
     * @param slice Variable number of slice definitions.
     */
    void set(T m, NumberWrapper... slice);

    /**
     * Retrieves non-zero elements based on row indices and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param k Row indices.
     * @return The non-zero elements.
     */
    T getNZ(boolean ind1, IndexSlice k);

    /**
     * Retrieves non-zero elements based on row indices and an indicator for 1-based indexing.
     *
     * @param ind1 Indicator for 1-based indexing.
     * @param k Row indices.
     * @return The non-zero elements.
     */
    T getNZ(boolean ind1, IMWrapper k);

    /**
     * Sets non-zero elements based on row indices and an indicator for 1-based indexing.
     *
     * @param m The non-zero elements to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param k Row indices.
     */
    void setNZ(T m, boolean ind1, IndexSlice k);

    /**
     * Sets non-zero elements based on row indices and an indicator for 1-based indexing.
     *
     * @param m The non-zero elements to set.
     * @param ind1 Indicator for 1-based indexing.
     * @param k Row indices.
     */
    void setNZ(T m, boolean ind1, IMWrapper k);

    /**
     * Performs Einstein summation with another wrapped object.
     *
     * @param other The other wrapped object.
     * @param C The result object.
     * @param dim_a Dimensions of the first object.
     * @param dim_b Dimensions of the second object.
     * @param dim_c Dimensions of the result object.
     * @param a Indices of the first object.
     * @param b Indices of the second object.
     * @param c Indices of the result object.
     * @return The result of the Einstein summation.
     */
    T einstein(T other, T C,
               CasADiIntVector dim_a, CasADiIntVector dim_b,
               CasADiIntVector dim_c, CasADiIntVector a,
               CasADiIntVector b, CasADiIntVector c);

    /**
     * Performs Einstein summation with another wrapped object.
     *
     * @param other The other wrapped object.
     * @param dim_a Dimensions of the first object.
     * @param dim_b Dimensions of the second object.
     * @param dim_c Dimensions of the result object.
     * @param a Indices of the first object.
     * @param b Indices of the second object.
     * @param c Indices of the result object.
     * @return The result of the Einstein summation.
     */
    T einstein(T other,
               CasADiIntVector dim_a, CasADiIntVector dim_b,
               CasADiIntVector dim_c, CasADiIntVector a,
               CasADiIntVector b, CasADiIntVector c);

    /**
     * Checks if the wrapped object is equal to another object up to a specified depth.
     *
     * @param other The other object to compare.
     * @param depth The depth of comparison.
     * @return True if equal, false otherwise.
     */
    boolean isEqual(T other, long depth);

    /**
     * Checks if the wrapped object is equal to another object.
     *
     * @param other The other object to compare.
     * @return True if equal, false otherwise.
     */
    boolean isEqual(T other);

    /**
     * Retrieves the minimum value of the wrapped object.
     *
     * @return The minimum value.
     */
    T mmin();

    /**
     * Retrieves the maximum value of the wrapped object.
     *
     * @return The maximum value.
     */
    T mmax();

    /**
     * Multiplies the wrapped object with another object.
     *
     * @param other The other object to multiply with.
     * @return The result of the multiplication.
     */
    T mtimes(T other);

    /**
     * Performs a multiply-accumulate operation with two other objects.
     *
     * @param y The first object.
     * @param z The second object.
     * @return The result of the multiply-accumulate operation.
     */
    T mac(T y, T z);

    /**
     * Reshapes the wrapped object to specified dimensions.
     *
     * @param nrow Number of rows.
     * @param ncol Number of columns.
     * @return The reshaped object.
     */
    T reshape(long nrow, long ncol);

    /**
     * Reshapes the wrapped object based on a sparsity pattern.
     *
     * @param sp Sparsity pattern.
     * @return The reshaped object.
     */
    T reshape(SparsityWrapper sp);

    /**
     * Casts the wrapped object to a specified sparsity pattern.
     *
     * @param sp Sparsity pattern.
     * @return The casted object.
     */
    T sparsityCast(SparsityWrapper sp);

    /**
     * Performs a Kronecker product with another object.
     *
     * @param b The other object.
     * @return The result of the Kronecker product.
     */
    T kron(T b);

    /**
     * Computes the Jacobian of the wrapped object with respect to another object.
     *
     * @param x The other object.
     * @param opts Options for the computation.
     * @return The Jacobian.
     */
    T jacobian(T x, Dictionary opts);

    /**
     * Computes the Jacobian of the wrapped object with respect to another object.
     *
     * @param x The other object.
     * @return The Jacobian.
     */
    T jacobian(T x);

    /**
     * Computes the Hessian of the wrapped object with respect to another object.
     *
     * @param x The other object.
     * @param opts Options for the computation.
     * @return The Hessian.
     */
    T hessian(T x, Dictionary opts);

    /**
     * Computes the Hessian of the wrapped object with respect to another object.
     *
     * @param x The other object.
     * @return The Hessian.
     */
    T hessian(T x);

    /**
     * Computes the Hessian of the wrapped object with respect to another object and a gradient.
     *
     * @param x The other object.
     * @param g The gradient.
     * @param opts Options for the computation.
     * @return The Hessian.
     */
    T hessian(T x, T g, Dictionary opts);

    /**
     * Computes the Hessian of the wrapped object with respect to another object and a gradient.
     *
     * @param x The other object.
     * @param g The gradient.
     * @return The Hessian.
     */
    T hessian(T x, T g);

    /**
     * Determines which elements of the wrapped object depend on another object.
     *
     * @param var The other object.
     * @param order The order of dependency.
     * @param tr Indicator for transposition.
     * @return A BooleanVector indicating dependencies.
     */
    BooleanVector whichDepends(T var, long order, boolean tr);

    /**
     * Determines which elements of the wrapped object depend on another object.
     *
     * @param var The other object.
     * @param order The order of dependency.
     * @return A BooleanVector indicating dependencies.
     */
    BooleanVector whichDepends(T var, long order);

    /**
     * Determines which elements of the wrapped object depend on another object.
     *
     * @param var The other object.
     * @return A BooleanVector indicating dependencies.
     */
    BooleanVector whichDepends(T var);

    /**
     * Computes the sparsity pattern of the Jacobian with respect to another object.
     *
     * @param x The other object.
     * @return The sparsity pattern.
     */
    SparsityWrapper jacobianSparsity(T x);

    /**
     * Substitutes elements of the wrapped object with another object.
     *
     * @param v The elements to substitute.
     * @param vdef The substitution definition.
     * @return The substituted object.
     */
    T substitute(T v, T vdef);

    /**
     * Solves a linear system with the wrapped object as the coefficient matrix.
     *
     * @param b The right-hand side vector.
     * @return The solution vector.
     */
    T solve(T b);

    /**
     * Solves a linear system with the wrapped object as the coefficient matrix, using a specified solver.
     *
     * @param b The right-hand side vector.
     * @param lsolver The linear solver to use.
     * @param dict Options for the solver.
     * @return The solution vector.
     */
    T solve(T b, String lsolver, Dictionary dict);

    /**
     * Computes the inverse of the minor of the wrapped object.
     *
     * @return The inverse minor.
     */
    T invMinor();

    /**
     * Computes the inverse of the wrapped object.
     *
     * @return The inverse.
     */
    T inv();

    /**
     * Computes the inverse of the wrapped object using a specified solver.
     *
     * @param lsolver The linear solver to use.
     * @param dict Options for the solver.
     * @return The inverse.
     */
    T inv(String lsolver, Dictionary dict);

    /**
     * Computes the pseudo-inverse of the wrapped object.
     *
     * @return The pseudo-inverse.
     */
    T pinv();

    /**
     * Computes the pseudo-inverse of the wrapped object using a specified solver.
     *
     * @param lsolver The linear solver to use.
     * @param dict Options for the solver.
     * @return The pseudo-inverse.
     */
    T pinv(String lsolver, Dictionary dict);

    /**
     * Computes the matrix exponential of the wrapped object multiplied by a constant.
     *
     * @param t The constant.
     * @return The matrix exponential.
     */
    T expmConst(T t);

    /**
     * Computes the matrix exponential of the wrapped object.
     *
     * @return The matrix exponential.
     */
    T expm();

    /**
     * Returns the number of nodes in the wrapped object.
     *
     * @return The number of nodes.
     */
    long nNodes();

    /**
     * Prints the operator representation of the wrapped object with specified arguments.
     *
     * @param args The arguments for the operator.
     * @return The operator representation as a string.
     */
    String printOperator(StringVector args);

    /**
     * Checks if the wrapped object depends on another object.
     *
     * @param arg The other object.
     * @return True if depends, false otherwise.
     */
    boolean dependsOn(T arg);

    /**
     * Simplifies the wrapped object.
     *
     * @return The simplified object.
     */
    T simplify();

    /**
     * Computes the dot product with another object.
     *
     * @param y The other object.
     * @return The dot product.
     */
    T dot(T y);

    /**
     * Performs matrix right division with another object.
     *
     * @param b The other object.
     * @return The result of the division.
     */
    T mrdivide(T b);

    /**
     * Performs matrix left division with another object.
     *
     * @param b The other object.
     * @return The result of the division.
     */
    T mldivide(T b);

    /**
     * Computes the L2 norm of the wrapped object.
     *
     * @return The L2 norm.
     */
    T norm2();

    /**
     * Computes the Frobenius norm of the wrapped object.
     *
     * @return The Frobenius norm.
     */
    T normFro();

    /**
     * Computes the L1 norm of the wrapped object.
     *
     * @return The L1 norm.
     */
    T norm1();

    /**
     * Computes the infinity norm of the wrapped object.
     *
     * @return The infinity norm.
     */
    T normInf();

    /**
     * Unites the wrapped object with another object.
     *
     * @param B The other object.
     * @return The united object.
     */
    T unite(T B);

    /**
     * Computes the trace of the wrapped object.
     *
     * @return The trace.
     */
    T trace();

    /**
     * Computes the diagonal of the wrapped object.
     *
     * @return The diagonal.
     */
    T diag();

    /**
     * Computes the sum of elements along the second dimension.
     *
     * @return The sum.
     */
    T sum2();

    /**
     * Computes the sum of elements along the first dimension.
     *
     * @return The sum.
     */
    T sum1();

    /**
     * Evaluates a polynomial with the wrapped object as the coefficients.
     *
     * @param x The variable for the polynomial.
     * @return The evaluated polynomial.
     */
    T polyval(T x);

    /**
     * Computes the determinant of the wrapped object.
     *
     * @return The determinant.
     */
    T det();

    /**
     * Computes the nullspace of the wrapped object.
     *
     * @return The nullspace.
     */
    T nullspace();

    /**
     * Repeats the sum of the wrapped object along specified dimensions.
     *
     * @param n Number of repetitions along the first dimension.
     * @param m Number of repetitions along the second dimension.
     * @return The repeated sum.
     */
    T repsum(long n, long m);

    /**
     * Repeats the sum of the wrapped object along the first dimension.
     *
     * @param n Number of repetitions.
     * @return The repeated sum.
     */
    T repsum(long n);

    /**
     * Densifies the wrapped object with a specified value.
     *
     * @param val The value to densify with.
     * @return The densified object.
     */
    T densify(T val);

    /**
     * Densifies the wrapped object.
     *
     * @return The densified object.
     */
    T densify();

    /**
     * Projects the wrapped object onto a specified sparsity pattern.
     *
     * @param sp Sparsity pattern.
     * @param intersect Indicator for intersection.
     * @return The projected object.
     */
    T project(SparsityWrapper sp, boolean intersect);

    /**
     * Projects the wrapped object onto a specified sparsity pattern.
     *
     * @param sp Sparsity pattern.
     * @return The projected object.
     */
    T project(SparsityWrapper sp);

    /**
     * Computes the cumulative sum along a specified axis.
     *
     * @param axis The axis for the cumulative sum.
     * @return The cumulative sum.
     */
    T cumsum(long axis);

    /**
     * Computes the cumulative sum along the first axis.
     *
     * @return The cumulative sum.
     */
    T cumsum();

    /**
     * Evaluates the wrapped object numerically.
     *
     * @return A DMWrapper representing the evaluated object.
     */
    DMWrapper evalf();

    /**
     * Prints the wrapped object with another object.
     *
     * @param y The other object.
     * @return The printed representation.
     */
    T printMe(T y);

    /**
     * Transposes the wrapped object.
     *
     * @return The transposed object.
     */
    T T();

    /**
     * Sets the maximum depth for equality comparison.
     *
     * @param eq_depth The maximum depth.
     */
    void setMaxDepth(long eq_depth);

    /**
     * Sets the default maximum depth for equality comparison.
     */
    void setMaxDepth();

    /**
     * Retrieves the maximum depth for equality comparison.
     *
     * @return The maximum depth.
     */
    long getMaxDepth();

    /**
     * Retrieves a sub-index at the specified row.
     *
     * @param rr The row index.
     * @param <V> The type of the sub-index.
     * @return The sub-index.
     */
    <V extends SubIndex> V at(int rr);

    /**
     * Retrieves a sub-matrix at the specified row and column.
     *
     * @param rr The row index.
     * @param cc The column index.
     * @param <W> The type of the sub-matrix.
     * @return The sub-matrix.
     */
    <W extends SubMatrix> W at(int rr, int cc);

    /**
     * Assigns another wrapped object to the current object.
     *
     * @param other The other object to assign.
     */
    void assign(T other);

    /**
     * Converts the wrapped object to a vector representation.
     *
     * @return The vector representation.
     */
    T vec();

    /**
     * Returns a string representation of the wrapped object with additional information.
     *
     * @param more Indicator for additional information.
     * @return The string representation.
     */
    String toString(boolean more);

    /**
     * Returns a string representation of the wrapped object.
     *
     * @return The string representation.
     */
    String toString();

    /**
     * Returns the number of non-zero elements in the wrapped object.
     *
     * @return The number of non-zero elements.
     */
    long nnz();

    /**
     * Returns the number of non-zero elements in the lower triangle of the wrapped object.
     *
     * @return The number of non-zero elements in the lower triangle.
     */
    long nnzLower();

    /**
     * Returns the number of non-zero elements in the upper triangle of the wrapped object.
     *
     * @return The number of non-zero elements in the upper triangle.
     */
    long nnzUpper();

    /**
     * Returns the number of non-zero elements in the diagonal of the wrapped object.
     *
     * @return The number of non-zero elements in the diagonal.
     */
    long nnzDiag();

    /**
     * Returns the number of elements in the wrapped object.
     *
     * @return The number of elements.
     */
    long numel();

    /**
     * Returns the number of rows in the wrapped object.
     *
     * @return The number of rows.
     */
    long size1();

    /**
     * Returns the number of rows in the wrapped object.
     *
     * @return The number of rows.
     */
    long rows();

    /**
     * Returns the number of columns in the wrapped object.
     *
     * @return The number of columns.
     */
    long size2();

    /**
     * Returns the number of columns in the wrapped object.
     *
     * @return The number of columns.
     */
    long columns();

    /**
     * Returns the dimensions of the wrapped object with an option for non-zero elements.
     *
     * @param withNz Indicator for non-zero elements.
     * @return The dimensions as a string.
     */
    String dim(boolean withNz);

    /**
     * Returns the dimensions of the wrapped object.
     *
     * @return The dimensions as a string.
     */
    String dim();

    /**
     * Returns the size of the wrapped object along a specified axis.
     *
     * @param axis The axis.
     * @return The size along the axis.
     */
    long size(long axis);

    /**
     * Checks if the wrapped object is empty with an option for both dimensions.
     *
     * @param both Indicator for both dimensions.
     * @return True if empty, false otherwise.
     */
    boolean isEmpty(boolean both);

    /**
     * Checks if the wrapped object is empty.
     *
     * @return True if empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Checks if the wrapped object is dense.
     *
     * @return True if dense, false otherwise.
     */
    boolean isDense();

    /**
     * Checks if the wrapped object is a scalar with an option for dense representation.
     *
     * @param scalarAndDense Indicator for dense representation.
     * @return True if scalar, false otherwise.
     */
    boolean isScalar(boolean scalarAndDense);

    /**
     * Checks if the wrapped object is a scalar.
     *
     * @return True if scalar, false otherwise.
     */
    boolean isScalar();

    /**
     * Checks if the wrapped object is square.
     *
     * @return True if square, false otherwise.
     */
    boolean isSquare();

    /**
     * Checks if the wrapped object is a vector.
     *
     * @return True if vector, false otherwise.
     */
    boolean isVector();

    /**
     * Checks if the wrapped object is a row vector.
     *
     * @return True if row vector, false otherwise.
     */
    boolean isRow();

    /**
     * Checks if the wrapped object is a column vector.
     *
     * @return True if column vector, false otherwise.
     */
    boolean isColumn();

    /**
     * Checks if the wrapped object is upper triangular.
     *
     * @return True if upper triangular, false otherwise.
     */
    boolean isTriu();

    /**
     * Checks if the wrapped object is lower triangular.
     *
     * @return True if lower triangular, false otherwise.
     */
    boolean isTril();

    /**
     * Retrieves the row indices of the wrapped object.
     *
     * @return The row indices.
     */
    CasADiIntVector getRow();

    /**
     * Retrieves the column indices of the wrapped object.
     *
     * @return The column indices.
     */
    CasADiIntVector getColInd();

    /**
     * Returns the row index of a specified element.
     *
     * @param el The element index.
     * @return The row index.
     */
    long row(long el);

    /**
     * Returns the column index of a specified column.
     *
     * @param col The column index.
     * @return The column index.
     */
    long colind(long col);

    /**
     * Performs 1D interpolation with the wrapped object.
     *
     * @param x The x values.
     * @param xq The query points.
     * @param mode The interpolation mode.
     * @param equidistant Indicator for equidistant points.
     * @return The interpolated object.
     */
    T interp1d(DoubleVector x, DoubleVector xq, String mode, boolean equidistant);

    /**
     * Returns the structural rank of the wrapped object.
     *
     * @return The structural rank.
     */
    long sprank();

    /**
     * Computes the zero norm multiplied by another object.
     *
     * @param y The other object.
     * @return The result of the multiplication.
     */
    long norm0Mul(T y);

    /**
     * Retrieves the lower triangular part of the wrapped object with an option for including the diagonal.
     *
     * @param includeDiagonal Indicator for including the diagonal.
     * @return The lower triangular part.
     */
    T tril(boolean includeDiagonal);

    /**
     * Retrieves the lower triangular part of the wrapped object.
     *
     * @return The lower triangular part.
     */
    T tril();

    /**
     * Retrieves the upper triangular part of the wrapped object with an option for including the diagonal.
     *
     * @param includeDiagonal Indicator for including the diagonal.
     * @return The upper triangular part.
     */
    T triu(boolean includeDiagonal);

    /**
     * Retrieves the upper triangular part of the wrapped object.
     *
     * @return The upper triangular part.
     */
    T triu();

    /**
     * Computes the sum of squares of the wrapped object.
     *
     * @return The sum of squares.
     */
    T sumsqr();

    /**
     * Computes the cross product with another object along a specified dimension.
     *
     * @param b The other object.
     * @param dim The dimension.
     * @return The cross product.
     */
    T cross(T b, long dim);

    /**
     * Computes the cross product with another object.
     *
     * @param b The other object.
     * @return The cross product.
     */
    T cross(T b);

    /**
     * Computes the skew-symmetric matrix of the wrapped object.
     *
     * @return The skew-symmetric matrix.
     */
    T skew();

    /**
     * Computes the inverse skew-symmetric matrix of the wrapped object.
     *
     * @return The inverse skew-symmetric matrix.
     */
    T invSkew();

    /**
     * Converts the lower triangular part of the wrapped object to a symmetric matrix.
     *
     * @return The symmetric matrix.
     */
    T tril2symm();

    /**
     * Converts the upper triangular part of the wrapped object to a symmetric matrix.
     *
     * @return The symmetric matrix.
     */
    T triu2symm();

    /**
     * Computes the difference along a specified axis.
     *
     * @param n The number of differences.
     * @param axis The axis.
     * @return The difference.
     */
    T diff(long n, long axis);

    /**
     * Computes the difference along the first axis.
     *
     * @param n The number of differences.
     * @return The difference.
     */
    T diff(long n);

    /**
     * Computes the difference along the first axis.
     *
     * @return The difference.
     */
    T diff();

    /**
     * Checks if the wrapped object is linear with respect to another object.
     *
     * @param var The other object.
     * @return True if linear, false otherwise.
     */
    boolean isLinear(T var);

    /**
     * Checks if the wrapped object is quadratic with respect to another object.
     *
     * @param var The other object.
     * @return True if quadratic, false otherwise.
     */
    boolean isQuadratic(T var);

    /**
     * Retrieves the quadratic coefficients with respect to another object.
     *
     * @param var The other object.
     * @param A The quadratic matrix.
     * @param b The linear vector.
     * @param c The constant.
     * @param check Indicator for checking validity.
     */
    void quadraticCoeff(T var, T A, T b, T c, boolean check);

    /**
     * Retrieves the linear coefficients with respect to another object.
     *
     * @param var The other object.
     * @param A The linear matrix.
     * @param b The linear vector.
     * @param check Indicator for checking validity.
     */
    void linearCoeff(T var, T A, T b, boolean check);

    /**
     * Computes the bilinear form with two other objects.
     *
     * @param x The first object.
     * @param y The second object.
     * @return The bilinear form.
     */
    T bilin(T x, T y);

    /**
     * Computes the rank-1 update with two other objects.
     *
     * @param alpha The scalar multiplier.
     * @param x The first object.
     * @param y The second object.
     * @return The rank-1 update.
     */
    T rank1(T alpha, T x, T y);

    /**
     * Computes the log-sum-exp of the wrapped object.
     *
     * @return The log-sum-exp.
     */
    T logsumexp();

    /**
     * Computes the Jacobian times a vector with respect to another object.
     *
     * @param arg The other object.
     * @param v The vector.
     * @param tr Indicator for transposition.
     * @param opts Options for the computation.
     * @return The result of the Jacobian times vector.
     */
    T jtimes(T arg, T v, boolean tr, Dictionary opts);

    /**
     * Computes the Jacobian times a vector with respect to another object.
     *
     * @param arg The other object.
     * @param v The vector.
     * @param tr Indicator for transposition.
     * @return The result of the Jacobian times vector.
     */
    T jtimes(T arg, T v, boolean tr);

    /**
     * Computes the Jacobian times a vector with respect to another object.
     *
     * @param arg The other object.
     * @param v The vector.
     * @return The result of the Jacobian times vector.
     */
    T jtimes(T arg, T v);

    /**
     * Computes the gradient with respect to another object.
     *
     * @param arg The other object.
     * @param opts Options for the computation.
     * @return The gradient.
     */
    T gradient(T arg, Dictionary opts);

    /**
     * Computes the gradient with respect to another object.
     *
     * @param arg The other object.
     * @return The gradient.
     */
    T gradient(T arg);

    /**
     * Computes the tangent with respect to another object.
     *
     * @param arg The other object.
     * @param opts Options for the computation.
     * @return The tangent.
     */
    T tangent(T arg, Dictionary opts);

    /**
     * Computes the tangent with respect to another object.
     *
     * @param arg The other object.
     * @return The tangent.
     */
    T tangent(T arg);

    /**
     * Linearizes the wrapped object with respect to another object and a reference point.
     *
     * @param x The other object.
     * @param x0 The reference point.
     * @param opts Options for the computation.
     * @return The linearized object.
     */
    T linearize(T x, T x0, Dictionary opts);

    /**
     * Linearizes the wrapped object with respect to another object and a reference point.
     *
     * @param x The other object.
     * @param x0 The reference point.
     * @return The linearized object.
     */
    T linearize(T x, T x0);

    /**
     * Computes the matrix power with another object.
     *
     * @param y The other object.
     * @return The matrix power.
     */
    T mpower(T y);

    /**
     * Computes the second-order cone with another object.
     *
     * @param y The other object.
     * @return The second-order cone.
     */
    T soc(T y);

    /**
     * Adds another object to the wrapped object.
     *
     * @param other The other object.
     * @return The result of the addition.
     */
    T add(T other);

    /**
     * Adds a number to the wrapped object.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the addition.
     */
    <N extends Number> T add(N number);

    /**
     * Subtracts another object from the wrapped object.
     *
     * @param other The other object.
     * @return The result of the subtraction.
     */
    T subtract(T other);

    /**
     * Subtracts a number from the wrapped object.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the subtraction.
     */
    <N extends Number> T subtract(N number);

    /**
     * Multiplies the wrapped object with another object.
     *
     * @param other The other object.
     * @return The result of the multiplication.
     */
    T multiply(T other);

    /**
     * Multiplies the wrapped object with a number.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the multiplication.
     */
    <N extends Number> T multiply(N number);

    /**
     * Divides the wrapped object by another object.
     *
     * @param other The other object.
     * @return The result of the division.
     */
    T divide(T other);

    /**
     * Divides the wrapped object by a number.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the division.
     */
    <N extends Number> T divide(N number);

    /**
     * Checks if the wrapped object is less than another object.
     *
     * @param y The other object.
     * @return The result of the comparison.
     */
    T lt(T y);

    /**
     * Checks if the wrapped object is less than a number.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the comparison.
     */
    <N extends Number> T lt(N number);

    /**
     * Checks if the wrapped object is less than or equal to another object.
     *
     * @param y The other object.
     * @return The result of the comparison.
     */
    T le(T y);

    /**
     * Checks if the wrapped object is less than or equal to a number.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the comparison.
     */
    <N extends Number> T le(N number);

    /**
     * Checks if the wrapped object is greater than another object.
     *
     * @param y The other object.
     * @return The result of the comparison.
     */
    T gt(T y);

    /**
     * Checks if the wrapped object is greater than a number.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the comparison.
     */
    <N extends Number> T gt(N number);

    /**
     * Checks if the wrapped object is greater than or equal to another object.
     *
     * @param y The other object.
     * @return The result of the comparison.
     */
    T ge(T y);

    /**
     * Checks if the wrapped object is greater than or equal to a number.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the comparison.
     */
    <N extends Number> T ge(N number);

    /**
     * Checks if the wrapped object is equal to another object.
     *
     * @param y The other object.
     * @return The result of the comparison.
     */
    T eq(T y);

    /**
     * Checks if the wrapped object is equal to a number.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the comparison.
     */
    <N extends Number> T eq(N number);

    /**
     * Checks if the wrapped object is not equal to another object.
     *
     * @param y The other object.
     * @return The result of the comparison.
     */
    T ne(T y);

    /**
     * Checks if the wrapped object is not equal to a number.
     *
     * @param number The number.
     * @param <N> The type of the number.
     * @return The result of the comparison.
     */
    <N extends Number> T ne(N number);

    /**
     * Performs logical AND with another object.
     *
     * @param y The other object.
     * @return The result of the logical AND.
     */
    T logicAnd(T y);

    /**
     * Performs logical OR with another object.
     *
     * @param y The other object.
     * @return The result of the logical OR.
     */
    T logicOr(T y);

    /**
     * Performs logical NOT on the wrapped object.
     *
     * @return The result of the logical NOT.
     */
    T logicNot();

    /**
     * Computes the absolute value of the wrapped object.
     *
     * @return The absolute value.
     */
    T abs();

    /**
     * Computes the square root of the wrapped object.
     *
     * @return The square root.
     */
    T sqrt();

    /**
     * Computes the square of the wrapped object.
     *
     * @return The square.
     */
    T sq();

    /**
     * Computes the sine of the wrapped object.
     *
     * @return The sine.
     */
    T sin();

    /**
     * Computes the cosine of the wrapped object.
     *
     * @return The cosine.
     */
    T cos();

    /**
     * Computes the tangent of the wrapped object.
     *
     * @return The tangent.
     */
    T tan();

    /**
     * Computes the arctangent of the wrapped object.
     *
     * @return The arctangent.
     */
    T atan();

    /**
     * Computes the arcsine of the wrapped object.
     *
     * @return The arcsine.
     */
    T asin();

    /**
     * Computes the arccosine of the wrapped object.
     *
     * @return The arccosine.
     */
    T acos();

    /**
     * Computes the hyperbolic tangent of the wrapped object.
     *
     * @return The hyperbolic tangent.
     */
    T tanh();

    /**
     * Computes the hyperbolic sine of the wrapped object.
     *
     * @return The hyperbolic sine.
     */
    T sinh();

    /**
     * Computes the hyperbolic cosine of the wrapped object.
     *
     * @return The hyperbolic cosine.
     */
    T cosh();

    /**
     * Computes the inverse hyperbolic tangent of the wrapped object.
     *
     * @return The inverse hyperbolic tangent.
     */
    T atanh();

    /**
     * Computes the inverse hyperbolic sine of the wrapped object.
     *
     * @return The inverse hyperbolic sine.
     */
    T asinh();

    /**
     * Computes the inverse hyperbolic cosine of the wrapped object.
     *
     * @return The inverse hyperbolic cosine.
     */
    T acosh();

    /**
     * Computes the exponential of the wrapped object.
     *
     * @return The exponential.
     */
    T exp();

    /**
     * Computes the natural logarithm of the wrapped object.
     *
     * @return The natural logarithm.
     */
    T log();

    /**
     * Computes the base-10 logarithm of the wrapped object.
     *
     * @return The base-10 logarithm.
     */
    T log10();

    /**
     * Computes the logarithm of 1 plus the wrapped object.
     *
     * @return The logarithm of 1 plus the object.
     */
    T log1p();

    /**
     * Computes the exponential of the wrapped object minus 1.
     *
     * @return The exponential minus 1.
     */
    T expm1();

    /**
     * Computes the floor of the wrapped object.
     *
     * @return The floor.
     */
    T floor();

    /**
     * Computes the ceiling of the wrapped object.
     *
     * @return The ceiling.
     */
    T ceil();

    /**
     * Computes the error function of the wrapped object.
     *
     * @return The error function.
     */
    T erf();

    /**
     * Computes the inverse error function of the wrapped object.
     *
     * @return The inverse error function.
     */
    T erfinv();

    /**
     * Computes the sign of the wrapped object.
     *
     * @return The sign.
     */
    T sign();

    /**
     * Computes the power of the wrapped object raised to another object.
     *
     * @param other The exponent object.
     * @return The power.
     */
    T pow(T other);

    /**
     * Computes the power of the wrapped object raised to a number.
     *
     * @param exponent The exponent number.
     * @param <N> The type of the number.
     * @return The power.
     */
    <N extends Number> T pow(N exponent);

    /**
     * Computes the modulus of the wrapped object with another object.
     *
     * @param other The other object.
     * @return The modulus.
     */
    T mod(T other);

    /**
     * Computes the remainder of the wrapped object with another object.
     *
     * @param other The other object.
     * @return The remainder.
     */
    T remainder(T other);

    /**
     * Computes the arctangent of the wrapped object divided by another object.
     *
     * @param other The other object.
     * @return The arctangent.
     */
    T atan2(T other);

    /**
     * Computes the wrapped object if another object is zero.
     *
     * @param other The other object.
     * @return The result.
     */
    T ifElseZero(T other);

    /**
     * Computes the minimum of the wrapped object and another object.
     *
     * @param other The other object.
     * @return The minimum.
     */
    T fmin(T other);

    /**
     * Computes the maximum of the wrapped object and another object.
     *
     * @param other The other object.
     * @return The maximum.
     */
    T fmax(T other);

    /**
     * Computes the copy sign of the wrapped object with another object.
     *
     * @param other The other object.
     * @return The copy sign.
     */
    T copysign(T other);

    /**
     * Computes the constant power of the wrapped object with another object.
     *
     * @param other The other object.
     * @return The constant power.
     */
    T constpow(T other);

    /**
     * Computes the hypotenuse of the wrapped object with another object.
     *
     * @param other The other object.
     * @return The hypotenuse.
     */
    T hypot(T other);

    /**
     * Negates the wrapped object.
     *
     * @return The negated object.
     */
    T negate();

}
