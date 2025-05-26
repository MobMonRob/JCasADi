package de.dhbw.rahmlab.casadi.api.core.wrapper.mx;

import de.dhbw.rahmlab.casadi.api.core.constraints.ExpressionParser;
import de.dhbw.rahmlab.casadi.api.core.wrapper.bool.BooleanVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.im.IMWrapper;
import de.dhbw.rahmlab.casadi.api.core.interfaces.SymbolicExpression;
import de.dhbw.rahmlab.casadi.api.core.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.index.IndexSlice;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.str.StringVector;
import de.dhbw.rahmlab.casadi.impl.casadi.*;
import java.util.Arrays;

/**
 * A wrapper class for the MX object, representing a variable in the constraint model.
 */
public class MXWrapper implements Wrapper<MXWrapper>, SymbolicExpression {

    private final MX mx;
    private String id;

    /**
     * Default constructor that initializes the MX object with default settings.
     */
    public MXWrapper() {
        this.mx = new MX();
    }

    /**
     * Constructs an MXWrapper with specified number of rows and columns.
     *
     * @param nrow The number of rows.
     * @param ncol The number of columns.
     */
    public MXWrapper(long nrow, long ncol) {
        this.mx = new MX(nrow, ncol);
    }

    /**
     * Constructs an MXWrapper using a given sparsity pattern.
     *
     * @param sparsity The SparsityWrapper object representing the sparsity pattern.
     */
    public MXWrapper(SparsityWrapper sparsity) {
        this.mx = new MX(sparsity.getCasADiObject());
    }

    /**
     * Constructs an MXWrapper with a given sparsity pattern and initial value.
     *
     * @param sparsity The SparsityWrapper object representing the sparsity pattern.
     * @param val The initial value for the matrix.
     */
    public MXWrapper(SparsityWrapper sparsity, MXWrapper val) {
        this.mx = new MX(sparsity.getCasADiObject(), val.getCasADiObject());
    }

    /**
     * Constructs an MXWrapper with a given sparsity pattern and function name.
     *
     * @param sparsity The SparsityWrapper object representing the sparsity pattern.
     * @param fname The function name associated with the matrix.
     */
    public MXWrapper(SparsityWrapper sparsity, String fname) {
        this.mx = new MX(sparsity.getCasADiObject(), fname);
    }

    /**
     * Constructs an MXWrapper with a single double value.
     *
     * @param x The double value to initialize the matrix.
     */
    public MXWrapper(double x) {
        this.mx = new MX(x);
    }

    /**
     * Constructs an MXWrapper using a vector of double values.
     *
     * @param x The DoubleVector containing the values to initialize the matrix.
     */
    public MXWrapper(DoubleVector x) {
        this.mx = new MX(x.getCasADiObject());
    }

    /**
     * Constructs an MXWrapper using an array of NumberWrapper objects.
     *
     * @param x The array of NumberWrapper objects to initialize the matrix.
     */
    public MXWrapper(NumberWrapper... x) {
        DoubleVector doubleVector = new DoubleVector();
        Arrays.stream(x).forEach(value -> doubleVector.add(value.getDoubleValue()));
        this.mx = new MX(doubleVector.getCasADiObject());
    }

    /**
     * Constructs an MXWrapper using an array of double values.
     *
     * @param x The array of double values to initialize the matrix.
     */
    public MXWrapper(double... x) {
        DoubleVector doubleVector = new DoubleVector(x);
        this.mx = new MX(doubleVector.getCasADiObject());
    }

    /**
     * Constructs an MXWrapper using a varargs of numbers.
     *
     * @param x The varargs of numbers to initialize the matrix.
     * @param <T> The type of numbers, extending Number.
     */
    @SafeVarargs
    public <T extends Number> MXWrapper(T... x) {
        DoubleVector doubleVector = new DoubleVector();
        Arrays.stream(x).forEach(value -> doubleVector.add(value.doubleValue()));
        this.mx = new MX(doubleVector.getCasADiObject());
    }

    /**
     * Constructs an MXWrapper using an iterable collection of double values.
     *
     * @param values The iterable collection of double values to initialize the matrix.
     */
    public MXWrapper(Iterable<Double> values) {
        DoubleVector doubleVector = new DoubleVector(values);
        this.mx = new MX(doubleVector.getCasADiObject());
    }

    /**
     * Copy constructor that creates a new MXWrapper from another MXWrapper.
     *
     * @param other The MXWrapper to copy.
     */
    public MXWrapper(MXWrapper other) {
        this.mx = new MX(other.getCasADiObject());
    }

    /**
     * Constructs an MXWrapper directly from an MX object.
     *
     * @param mx The MX object to wrap.
     */
    public MXWrapper(MX mx) {
        this.mx = mx;
    }

    /**
     * Constructs an MXWrapper using a DMWrapper object.
     *
     * @param dm The DMWrapper object to initialize the matrix.
     */
    public MXWrapper(DMWrapper dm) {
        this.mx = new MX(dm.getCasADiObject());
    }

    /**
     * Constructs a constant matrix with a given sparsity and values.
     *
     * @param sp The Sparsity object representing the sparsity pattern.
     * @param val The constant value to fill the matrix.
     * @param dummy A boolean parameter used internally.
     */
    public MXWrapper(SparsityWrapper sp, double val, boolean dummy) {
        this.mx = new MX(sp.getCasADiObject(), val, dummy);
    }

     /**
     * Constructs an MXWrapper object by parsing a Maxima-style expression string.
     * This constructor utilizes your existing {@link de.dhbw.rahmlab.casadi.api.core.constraints.ExpressionParser},
     * which transforms infix strings into MX trees.
     *
     * Note: This constructor is currently experimental and may exhibit unexpected behavior.
     * It is designed to explore new functionalities and improvements, and feedback is encouraged
     * to enhance its robustness and reliability.
     *
     * @param expr an expression like "2*x^2 + 3*x + 1"
     */
    public MXWrapper(String expr) {
        this.mx = new MX(ExpressionParser.parse(expr).getCasADiObject());
    }

    /**
     * Returns the type name of the current MX instance.
     *
     * This method retrieves the type name associated with the MX object,
     * which can be useful for identifying the specific type of the
     * optimization problem or operation being handled by the MXWrapper.
     *
     * @return A string representing the type name of the MX instance.
     */
    @Override
    public String typeName() {
        return MX.type_name();
    }

    /**
     * Returns the sparsity pattern of the MX matrix.
     * Returns null if no sparsity pattern exists.
     *
     * @return sparsity
     */
    @Override
    public SparsityWrapper sparsity() {
        return new SparsityWrapper(this.mx.sparsity());
    }

    /**
     * Returns the truth value of the MX expression.
     *
     * @return boolean
     */
    @Override
    public boolean nonzero() {
        return this.mx.__nonzero__();
    }

    /**
     * Returns an owning reference to the sparsity pattern.
     *
     * @return Sparsity
     */
    public SparsityWrapper getSparsity() {
        return new SparsityWrapper(this.mx.get_sparsity());
    }

    /**
     * Erases elements from the matrix based on the specified row and column indices.
     *
     * @param rr A collection of row indices to be erased.
     * @param cc A collection of column indices to be erased.
     * @param ind1 A boolean flag that determines the behavior of the erase operation.
     */
    @Override
    public void erase(CasADiIntVector rr, CasADiIntVector cc, boolean ind1) {
        this.mx.erase(rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    /**
     * Erases elements from the matrix based on the specified row and column indices.
     *
     * @param rr A collection of row indices to be erased.
     * @param cc A collection of column indices to be erased.
     */
    @Override
    public void erase(CasADiIntVector rr, CasADiIntVector cc) {
        this.mx.erase(rr.getCasADiObject(), cc.getCasADiObject());
    }

    /**
     * Erases elements from the matrix based on the specified row indices.
     *
     * @param rr A collection of row indices to be erased.
     * @param ind1 A boolean flag that determines the behavior of the erase operation.
     */
    @Override
    public void erase(CasADiIntVector rr, boolean ind1) {
        this.mx.erase(rr.getCasADiObject(), ind1);
    }

    /**
     * Erases elements from the matrix based on the specified row indices.
     *
     * @param rr A collection of row indices to be erased.
     */
    @Override
    public void erase(CasADiIntVector rr) {
        this.mx.erase(rr.getCasADiObject());
    }

    /**
     * Enlarges the matrix to accommodate additional rows and columns.
     *
     * @param nrow The number of rows to be added.
     * @param ncol The number of columns to be added.
     * @param rr A collection of row indices that will be used in the enlargement.
     * @param cc A collection of column indices that will be used in the enlargement.
     * @param ind1 A boolean flag that determines the behavior of the enlargement operation.
     */
    @Override
    public void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc, boolean ind1) {
        this.mx.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    /**
     * Enlarges the matrix to accommodate additional rows and columns.
     *
     * @param nrow The number of rows to be added.
     * @param ncol The number of columns to be added.
     * @param rr A collection of row indices that will be used in the enlargement.
     * @param cc A collection of column indices that will be used in the enlargement.
     */
    @Override
    public void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc) {
        this.mx.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject());
    }

    /**
     * Creates a new MXWrapper instance that is dependent on the specified variable.
     *
     * @param ch The index of the variable to create a dependency on.
     * @return A new MXWrapper instance that is dependent on the specified variable.
     */
    @Override
    public MXWrapper dep(long ch) {
        return new MXWrapper(this.mx.dep(ch));
    }

    /**
     * Creates a new MXWrapper instance that is dependent on the last variable.
     *
     * @return A new MXWrapper instance that is dependent on the last variable.
     */
    @Override
    public MXWrapper dep() {
        return new MXWrapper(this.mx.dep());
    }

    /**
     * Method to get the number of outputs (outputs)
     *
     * @return long
     */
    public long nOut() {
        return this.mx.n_out();
    }

    /**
     * Retrieves the output corresponding to the given index from the MX object.
     * <p>
     * This method wraps the functionality of the CasADi `get_output(long oind)` method,
     * which returns the output at the specified index.
     * </p>
     *
     * @param oind The index of the desired output.
     * @return A new `MXWrapper` instance containing the requested output.
     */
    public MXWrapper getOutput(long oind) {
        return new MXWrapper(this.mx.get_output(oind));
    }

    /**
     * Gets the number of dependencies of the binary SXElem in the MX object.
     * This method is used to query the number of dependencies that are involved
     * in the symbolic expression represented by the MX object.
     *
     * @return long. The number of dependencies as a long.
     */
    @Override
    public long nDep() {
        return this.mx.n_dep();
    }

    /**
     * Gets the name of the MX object.
     * This method returns the name of the symbolic expression or variable.
     *
     * @return String. The name of the MX object as a String.
     */
    @Override
    public String getName() {
        return this.mx.name();
    }

    /**
     * Checks if the MX object represents a symbolic expression.
     * Symbolic objects are used to represent mathematical expressions that can be
     * evaluated later.
     *
     * @return boolean. true if the object is symbolic, false otherwise.
     */
    @Override
    public boolean isSymbolic() {
        return this.mx.is_symbolic();
    }

    /**
     * Checks if the MX object is constant.
     * A constant object represents a fixed value that cannot change during optimization.
     *
     * @return boolean. true if the object is constant, false otherwise.
     */
    @Override
    public boolean isConstant() {
        return this.mx.is_constant();
    }

    /**
     * Checks if the MX object represents a function call.
     * This is typically used when the object is part of a larger optimization problem
     * or a symbolic expression involving function calls.
     *
     * @return boolean. true if the object is a function call, false otherwise.
     */
    public boolean isCall() {
        return this.mx.is_call();
    }

    /**
     * Retrieves the function associated with this MX object.
     * This method is only valid when the MX object represents a function call
     * (i.e., when `is_call()` returns true).
     *
     * @return Function. The `Function` associated with the MX object, or null if not applicable.
     */
    public FunctionWrapper whichFunction() {
        return new FunctionWrapper(this.mx.which_function());
    }

    /**
     * Checks if the MX object represents an evaluation output.
     *
     * This method determines whether the MX object is the result of an evaluation,
     * i.e., if it is an output of some computation or function.
     *
     * @return boolean. true if the object is an evaluation output, false otherwise.
     */
    public boolean isOutput() {
        return this.mx.is_output();
    }

    /**
     * Retrieves the index of the evaluation output.
     * This method is only valid when `isOutput()` returns true.
     *
     * @return long. The index of the evaluation output as a long.
     * @throws IllegalStateException if called when `isOutput()` is false.
     */
    public long whichOutput() {
        if (this.mx.is_output()) {
            return this.mx.which_output();
        } else {
            throw new IllegalStateException("Output is not valid unless isOutput() is true.");
        }
    }

    /**
     * Checks if the MX object represents a specific operation.
     *
     * This method is used to determine if the MX object corresponds to a certain
     * type of operation, identified by the provided operation code.
     *
     * @param op The operation code to check.
     * @return boolean. true if the object represents the given operation, false otherwise.
     */
    @Override
    public boolean isOp(long op) {
        return this.mx.is_op(op);
    }

    /**
     * Checks if the MX object represents a multiplication operation.
     *
     * This method is used to determine if the MX object corresponds to a multiplication
     * operation (e.g., matrix multiplication, scalar multiplication, etc.).
     *
     * @return boolean. true if the object is a multiplication, false otherwise.
     */
    public boolean isMultiplication() {
        return this.mx.is_multiplication();
    }

    /**
     * Checks if the MX object represents a commutative operation.
     *
     * This method checks if the operation represented by the MX object is commutative,
     * meaning that the order of operands does not affect the result.
     *
     * @return boolean. true if the operation is commutative, false otherwise.
     */
    @Override
    public boolean isCommutative() {
        return this.mx.is_commutative();
    }

    /**
     * Checks if the MX object represents a norm operation.
     *
     * This method is used to determine if the MX object corresponds to a norm operation,
     * such as the L2 norm, or another type of mathematical norm.
     *
     * @return boolean. true if the object is a norm, false otherwise.
     */
    public boolean isNorm() {
        return this.mx.is_norm();
    }

    /**
     * Checks if the MX object can be used to define function inputs.
     *
     * This method checks if the MX object is a valid input for a CasADi function.
     * Valid inputs include combinations of operations like Reshape, concatenations,
     * and symbolic MX objects.
     *
     * @return boolean. true if the object can be used as a function input, false otherwise.
     */
    @Override
    public boolean isValidInput() {
        return this.mx.is_valid_input();
    }

    /**
     * Gets the number of primitives for MX function inputs/outputs.
     *
     * This method returns the total number of symbolic primitives (basic components) used
     * in the MX expression. This can help understand the complexity or structure of the
     * symbolic expression.
     *
     * @return long. The number of primitives as a long.
     */
    public long nPrimitives() {
        return this.mx.n_primitives();
    }

    /**
     * Retrieves the symbolic primitives used in the MX expression.
     *
     * This method returns a vector containing the symbolic primitives (basic components) of
     * the MX expression. These are the building blocks of the symbolic expression.
     *
     * @return MXVector. A vector of MX primitives.
     */
    public MXVector primitives() {
        return new MXVector(this.mx.primitives());
    }

    /**
     * Splits an MX expression into its symbolic primitives.
     *
     * This method decomposes the given MX expression into its constituent symbolic primitives
     * (basic symbolic components) and returns them as a vector.
     *
     * @param x The MX expression to split into primitives.
     * @return MXVector. A vector of MX primitives representing the split expression.
     */
    public MXVector splitPrimitives(MXWrapper x) {
        return new MXVector(this.mx.split_primitives(x.getCasADiObject()));
    }

    /**
     * Joins symbolic primitives into a single MX expression.
     *
     * This method combines a set of symbolic primitives (MX expressions) into a single
     * unified MX expression.
     *
     * @param v A vector of MX primitives to join into a single expression.
     * @return MXWrapper
     */
    public MXWrapper joinPrimitives(MXVector v) {
        return new MXWrapper(this.mx.join_primitives(v.getCasADiObject()));
    }

    /**
     * Checks if there are duplicate symbolic expressions in the MX object.
     *
     * This method detects if any symbolic primitives appear more than once in the expression.
     * If duplicates are found, a warning is issued with the names of the duplicate expressions.
     *
     * @return boolean. true if duplicate symbolic expressions are found, false otherwise.
     */
    @Override
    public boolean hasDuplicates() {
        return this.mx.has_duplicates();
    }

    /**
     * Resets the marker for an input expression.
     *
     * This method clears the temporary input flag for the expression, which can be used
     * to mark or unmark an expression during symbolic manipulation.
     */
    @Override
    public void resetInput() {
        this.mx.reset_input();
    }

    /**
     * Checks if the expression represents an identity matrix.
     *
     * This method returns true if the MX expression represents an identity matrix (eye),
     * otherwise, it returns false.
     *
     * @return boolean. true if the expression is an identity matrix, false otherwise.
     */
    @Override
    public boolean isEye() {
        return this.mx.is_eye();
    }

    /**
     * Checks if the expression is equal to zero.
     *
     * This method checks whether the MX expression is equivalent to zero. Be aware that
     * false negatives are possible due to numerical inaccuracies.
     *
     * @return boolean. true if the expression is zero, false otherwise.
     */
    @Override
    public boolean isZero() {
        return this.mx.is_zero();
    }

    /**
     * Checks if the expression is equal to one.
     *
     * This method checks whether the MX expression is equivalent to one. Be aware that
     * false negatives are possible due to numerical inaccuracies.
     *
     * @return boolean. true if the expression is one, false otherwise.
     */
    @Override
    public boolean isOne() {
        return this.mx.is_one();
    }

    /**
     * Checks if the expression is equal to minus one.
     *
     * This method checks whether the MX expression is equivalent to -1. Be aware that
     * false negatives are possible due to numerical inaccuracies.
     *
     * @return boolean. true if the expression is -1, false otherwise.
     */
    @Override
    public boolean isMinusOne() {
        return this.mx.is_minus_one();
    }

    /**
     * Checks if the expression represents a transpose.
     *
     * This method checks whether the MX expression is a transpose of another expression.
     *
     * @return boolean. true if the expression is a transpose, false otherwise.
     */
    public boolean isTranspose() {
        return this.mx.is_transpose();
    }

    /**
     * Checks if the expression is regular (i.e., does not contain NaN or Inf).
     *
     * This method checks whether the MX expression contains any NaN or Inf values. If it
     * does, the expression is considered irregular.
     *
     * @return boolean. true if the expression is regular (no NaN or Inf), false otherwise.
     */
    @Override
    public boolean isRegular() {
        return this.mx.is_regular();
    }

    /**
     * Checks if the expression represents a binary operation.
     *
     * This method checks whether the MX expression is a binary operation, i.e., it involves
     * two operands. If the expression is binary, it returns true.
     *
     * @return boolean. true if the expression is binary, false otherwise.
     */
    public boolean isBinary() {
        return this.mx.is_binary();
    }

    /**
     * Checks if the expression represents a unary operation.
     *
     * This method checks whether the MX expression is a unary operation, i.e., it involves
     * only one operand. If the expression is unary, it returns true.
     *
     * @return boolean. true if the expression is unary, false otherwise.
     */
    public boolean isUnary() {
        return this.mx.is_unary();
    }

    /**
     * Gets the operation type of the expression.
     *
     * This method returns a numerical code representing the type of operation that the MX
     * expression represents. This can be used to classify the expression into a specific
     * operation type, such as addition, multiplication, etc.
     *
     * @return long. The operation type code as a long value.
     */
    @Override
    public long op() {
        return this.mx.op();
    }

    /**
     * Obtains information about the MX node.
     *
     * This method returns a dictionary containing detailed information about the current MX
     * expression, such as its structure, properties, or additional metadata.
     *
     * @return Dict. A dictionary containing the information of the MX expression.
     */
    @Override
    public Dictionary info() {
        return new Dictionary(this.mx.info());
    }

    /**
     * Get the temporary variable associated with the current MX object.
     *
     * This method retrieves the temporary variable that has been set or allocated
     * for the current expression. The temporary variable can be used internally
     * to store intermediate results or other temporary states in symbolic computations.
     *
     * @return long. The current temporary variable's value.
     */
    public long getTemp() {
        return this.mx.get_temp();
    }

    /**
     * Set the temporary variable for the current MX object.
     *
     * This method sets the temporary variable for the current expression. The temporary
     * variable can be used internally to store intermediate results or other temporary
     * states during symbolic operations.
     *
     * @param temp The value to set as the temporary variable.
     */
    public void setTemp(long temp) {
        this.mx.set_temp(temp);
    }

    /**
     * Creates a binary operation node that represents a symbolic expression
     * for a mathematical operation between this MX object and another MX object.
     * The operation type is specified by the provided operation ID.
     *
     * @param op The operation type ID (e.g., {@code ADD} for addition, {@code MUL} for multiplication, etc.).
     * @param y The right operand, which is another MXWrapper object.
     * @return A new MXWrapper representing the result of the binary operation.
     */
    @Override
    public MXWrapper binary(long op, MXWrapper y) {
        MX result = MX.binary(op, this.mx, y.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Creates a unary operation node that represents a symbolic expression
     * for a mathematical operation on this MX object.
     * The operation type is specified by the provided operation ID.
     *
     * @param op The operation type ID (e.g., {@code NEG} for negation, {@code SQRT} for square root, etc.).
     * @return A new MXWrapper representing the result of the unary operation.
     */
    @Override
    public MXWrapper unary(long op) {
        MX result = MX.unary(op, this.mx);
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with all entries set to positive infinity, defined by the given sparsity pattern.
     *
     * This method creates an MX matrix where all elements are initialized to positive infinity.
     * The structure of the matrix is defined by the sparsity pattern passed as an argument.
     *
     * @param sp The sparsity pattern that defines the matrix structure.
     * @return MXWrapper
     */
    public static MXWrapper inf(SparsityWrapper sp) {
        MX result = MX.inf(sp.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with all entries set to positive infinity with the specified dimensions.
     *
     * This method creates an MX matrix of the specified dimensions (nrow x ncol),
     * where all elements are initialized to positive infinity.
     *
     * @param nrow The number of rows of the matrix.
     * @param ncol The number of columns of the matrix.
     * @return MXWrapper
     */
    public static MXWrapper inf(long nrow, long ncol) {
        MX result = MX.inf(nrow, ncol);
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with all entries set to positive infinity with the specified number of rows.
     *
     * This method creates an MX matrix with the given number of rows and a default number of columns,
     * where all elements are initialized to positive infinity.
     *
     * @param nrow The number of rows of the matrix.
     * @return MXWrapper
     */
    public static MXWrapper inf(long nrow) {
        MX result = MX.inf(nrow);
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with all entries set to positive infinity with default dimensions.
     *
     * This method creates an MX matrix with a default size (usually 1x1) where all elements are initialized to positive infinity.
     *
     * @return MXWrapper
     */
    public static MXWrapper inf() {
        MX result = MX.inf();
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with all entries set to NaN, defined by the given sparsity pattern.
     *
     * This method creates an MX matrix where all elements are initialized to NaN.
     * The structure of the matrix is defined by the sparsity pattern passed as an argument.
     *
     * @param sp The sparsity pattern that defines the matrix structure.
     * @return MXWrapper
     */
    public static MXWrapper nan(SparsityWrapper sp) {
        MX result = MX.nan(sp.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with all entries set to NaN with the specified dimensions.
     *
     * This method creates an MX matrix of the specified dimensions (nrow x ncol),
     * where all elements are initialized to NaN.
     *
     * @param nrow The number of rows of the matrix.
     * @param ncol The number of columns of the matrix.
     * @return MXWrapper
     */
    public static MXWrapper nan(long nrow, long ncol) {
        MX result = MX.nan(nrow, ncol);
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with all entries set to NaN with the specified number of rows.
     *
     * This method creates an MX matrix with the given number of rows and a default number of columns,
     * where all elements are initialized to NaN.
     *
     * @param nrow The number of rows of the matrix.
     * @return MXWrapper
     */
    public static MXWrapper nan(long nrow) {
        MX result = MX.nan(nrow);
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with all entries set to NaN with default dimensions.
     *
     * This method creates an MX matrix with a default size (usually 1x1) where all elements are initialized to NaN.
     *
     * @return MXWrapper
     */
    public static MXWrapper nan() {
        MX result = MX.nan();
        return new MXWrapper(result);
    }

    /**
     * Create a matrix with an identity matrix structure of the given size.
     *
     * This method creates an MX matrix that represents an identity matrix with the specified
     * size. The matrix will have ones on the diagonal and zeros elsewhere.
     *
     * @param size The size of the identity matrix (number of rows and columns).
     * @return MXWrapperg
     */
    public static MXWrapper eye(long size) {
        MX result = MX.eye(size);
        return new MXWrapper(result);
    }

    // ----- Get a submatrix, single argument -----
    /**
     * Retrieves a slice of the MX object based on the specified slice definition.
     *
     * This method parses the given slice definition string to create an
     * IndexSlice object, which is then used to extract a portion of the
     * MX object. The extracted slice is stored in a new MX instance
     * that is returned by this method.
     *
     * <p>Note: The parameter 'ind1' in the underlying MX.get method is
     * always set to true, indicating that the slice should include the
     * starting index.</p>
     *
     * @param sliceDefinition A string representing the slice definition
     *                       in the format 'start:stop:step'.
     *                       For example, '0:10:2' retrieves every second
     *                       element from index 0 to 9.
     * @return An MX object containing the sliced portion of the original
     *         MX object.
     * @throws IllegalArgumentException if the slice definition is invalid
     *                                  or cannot be parsed.
     */
    @Override
    public MXWrapper get(String sliceDefinition) {
        IndexSlice indexSlice = IndexSlice.slicingSyntax(sliceDefinition);
        MXWrapper output = new MXWrapper();
        this.mx.get(output.getCasADiObject(), true, indexSlice.getCasADiObject());
        return output;
    }

    /**
     * Gets a submatrix using a single argument, of type Slice.
     *
     * This method extracts a submatrix from the current matrix using the given slice
     * along the row index.
     *
     * @param ind1 A boolean indicating whether the indices are inclusive or not.
     * @param rr The slice used to extract the submatrix along the row dimension.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    @Override
    public MXWrapper get(boolean ind1, IndexSlice rr) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a single argument of type IM.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The IM used to define the rows of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    @Override
    public MXWrapper get(boolean ind1, IMWrapper rr) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a single argument of type Sparsity.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param sp The Sparsity used to define the rows of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    @Override
    public MXWrapper get(boolean ind1, SparsityWrapper sp) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, sp.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a single argument of type MXWrapper.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The MXWrapper used to define the rows of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    public MXWrapper get(boolean ind1, MXWrapper rr) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.mx);
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a single argument of type long.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The long value used to define the rows of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    public MXWrapper get(boolean ind1, long rr) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr);
        return output;
    }

    // ----- Get a submatrix, two arguments -----
    /**
     * Retrieves a slice of the MX object based on the specified row and column slice definitions.
     *
     * This method parses the given slice definition strings to create IndexSlice objects for both
     * rows and columns, which are then used to extract a portion of the MX object. The extracted
     * slice is stored in a new MXWrapper instance that is returned by this method.
     *
     * <p>Note: The parameter 'ind1' in the underlying MX.get method is always set to true, indicating
     * that the slice should include the starting index.</p>
     *
     * @param sliceDefinitionRR A string representing the row slice definition in the format 'start:stop:step'.
     *                          For example, '0:10:2' retrieves every second row from index 0 to 9.
     * @param sliceDefinitionCC A string representing the column slice definition in the format 'start:stop:step'.
     *                          For example, '0:5:1' retrieves every column from index 0 to 4.
     * @return An MXWrapper object containing the sliced portion of the original MX object.
     * @throws IllegalArgumentException if either slice definition is invalid or cannot be parsed.
     */
    @Override
    public MXWrapper get(String sliceDefinitionRR, String sliceDefinitionCC) {
        IndexSlice indexSliceRR = IndexSlice.slicingSyntax(sliceDefinitionRR);
        IndexSlice indexSliceCC = IndexSlice.slicingSyntax(sliceDefinitionCC);
        MXWrapper output = new MXWrapper();
        this.mx.get(output.getCasADiObject(), true, indexSliceRR.getCasADiObject(), indexSliceCC.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using two arguments of type Slice.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The slice used to define the rows of the submatrix.
     * @param cc The slice used to define the columns of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    @Override
    public MXWrapper get(boolean ind1, IndexSlice rr, IndexSlice cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a Slice for rows and an IM for columns.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The slice used to define the rows of the submatrix.
     * @param cc The IM used to define the columns of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    @Override
    public MXWrapper get(boolean ind1, IndexSlice rr, IMWrapper cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a Slice for rows and a long for columns.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The slice used to define the rows of the submatrix.
     * @param cc The long value representing the column index of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    public MXWrapper get(boolean ind1, IndexSlice rr, long cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.getCasADiObject(), cc);
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using an IM for rows and a Slice for columns.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The IM used to define the rows of the submatrix.
     * @param cc The slice used to define the columns of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    @Override
    public MXWrapper get(boolean ind1, IMWrapper rr, IndexSlice cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a long for rows and a Slice for columns.
     *
     * @param ind1 A boolean indicating whether the row index is inclusive.
     * @param rr The long value representing the row index of the submatrix.
     * @param cc The slice used to define the columns of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    public MXWrapper get(boolean ind1, long rr, IndexSlice cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr, cc.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using two IM arguments.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The IM used to define the rows of the submatrix.
     * @param cc The IM used to define the columns of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    @Override
    public MXWrapper get(boolean ind1, IMWrapper rr, IMWrapper cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using two long arguments.
     *
     * @param ind1 A boolean indicating whether the row index is inclusive.
     * @param rr The long value representing the row index of the submatrix.
     * @param cc The long value representing the column index of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    public MXWrapper get(boolean ind1, long rr, long cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr, cc);
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a MXWrapper for rows and a Slice for columns.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The MXWrapper used to define the rows of the submatrix.
     * @param cc The slice used to define the columns of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    public MXWrapper get(boolean ind1, MXWrapper rr, IndexSlice cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.mx, cc.getCasADiObject());
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using a Slice for rows and a MXWrapper for columns.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The slice used to define the rows of the submatrix.
     * @param cc The MXWrapper used to define the columns of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    public MXWrapper get(boolean ind1, IndexSlice rr, MXWrapper cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.getCasADiObject(), cc.mx);
        return output;
    }

    /**
     * Extracts a submatrix from the current matrix using two MXWrapper arguments.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The MXWrapper used to define the rows of the submatrix.
     * @param cc The MXWrapper used to define the columns of the submatrix.
     * @return A new MXWrapper containing the extracted submatrix.
     */
    public MXWrapper get(boolean ind1, MXWrapper rr, MXWrapper cc) {
        MXWrapper output = new MXWrapper();
        this.mx.get(output.mx, ind1, rr.mx, cc.mx);
        return output;
    }

    /**
     * Retrieves a slice of the MX object using the specified NumberWrapper values.
     *
     * This method creates an {@code IndexSlice} object based on the provided
     * {@link NumberWrapper} arguments, which represent the indices or ranges
     * for the slice. The extracted slice is stored in a new MXWrapper instance
     * that is returned by this method.
     *
     * <p>Note: The parameter 'ind1' in the underlying MX.get method is
     * always set to true, indicating that the slice should include the
     * starting index.</p>
     *
     * @param slice A variable number of {@link NumberWrapper} arguments
     *              representing the indices or ranges for the slice.
     *              Must contain between 1 and 3 elements:
     *              <ul>
     *                <li>1 element: Represents a single index.</li>
     *                <li>2 elements: Represents a range from the first
     *                    value to the second value.</li>
     *                <li>3 elements: Represents a range with a specified step.</li>
     *              </ul>
     * @return An MXWrapper instance containing the sliced portion of the
     *         original MX object.
     * @throws IllegalArgumentException if {@code slice} is null, empty,
     *                                  or contains more than 3 elements.
     */
    @Override
    public MXWrapper get(NumberWrapper... slice) {
        IndexSlice indexSlice = new IndexSlice(slice);
        MXWrapper output = new MXWrapper();
        this.mx.get(output.getCasADiObject(), true, indexSlice.getCasADiObject());
        return output;
    }

    // ----- Set a submatrix, single argument -----
    /**
     * Sets a slice of the MX object based on the specified slice definition.
     *
     * This method parses the given slice definition string to create an
     * IndexSlice object, which is then used to determine the portion of
     * the MX object that will be updated. The provided MXWrapper instance
     * is used to set the values in the specified slice of the original
     * MX object.
     *
     * <p>Note: The parameter 'ind1' in the underlying set method is
     * always set to true, indicating that the slice should include the
     * starting index.</p>
     *
     * @param mx             An MXWrapper instance containing the values
     *                       to be set in the specified slice of the MX object.
     * @param sliceDefinition A string representing the slice definition
     *                       in the format 'start:stop:step'.
     *                       For example, '0:10:2' specifies that every
     *                       second element from index 0 to 9 should be updated.
     * @throws IllegalArgumentException if the slice definition is invalid
     *                                  or cannot be parsed.
     */
    @Override
    public void set(MXWrapper mx, String sliceDefinition) {
        IndexSlice indexSlice = IndexSlice.slicingSyntax(sliceDefinition);
        this.mx.set(mx.getCasADiObject(), true, indexSlice.getCasADiObject());
    }

    /**
     * Sets a submatrix in the current matrix using a single argument of type Slice.
     * The specified slice defines the rows to be set in the current matrix.
     *
     * @param m The matrix containing the values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The slice used to define the rows to be set.
     */
    @Override
    public void set(MXWrapper m, boolean ind1, IndexSlice rr) {
        this.mx.set(m.mx, ind1, rr.getCasADiObject());
    }

    /**
     * Sets a submatrix in the current matrix using a single argument of type IM.
     * The specified IM defines the rows to be set in the current matrix.
     *
     * @param m The matrix containing the values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The IM used to define the rows to be set.
     */
    @Override
    public void set(MXWrapper m, boolean ind1, IMWrapper rr) {
        this.mx.set(m.mx, ind1, rr.getCasADiObject());
    }

    /**
     * Sets a submatrix in the current matrix using a single argument of type Sparsity.
     * The specified Sparsity defines the rows to be set in the current matrix.
     *
     * @param m The matrix containing the values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param sp The Sparsity used to define the rows to be set.
     */
    @Override
    public void set(MXWrapper m, boolean ind1, SparsityWrapper sp) {
        this.mx.set(m.mx, ind1, sp.getCasADiObject());
    }

    // ----- Set a submatrix, two arguments -----
    /**
     * Sets a portion of the MX object based on the specified row and column slice definitions,
     * using the provided MXWrapper object as the source for the data to be set.
     *
     * This method parses the given slice definition strings to create IndexSlice objects for both
     * rows and columns, which are then used to determine the portion of the MX object to be replaced.
     * The data from the provided MXWrapper object is inserted into the specified slice of the MX object.
     *
     * <p>Note: The parameter 'ind1' in the underlying MX.set method is always set to true, indicating
     * that the operation should include the starting index.</p>
     *
     * @param m The MXWrapper object containing the data to be set into the MX object.
     * @param sliceDefinitionRR A string representing the row slice definition in the format 'start:stop:step'.
     *                          For example, '0:10:2' specifies every second row from index 0 to 9.
     * @param sliceDefinitionCC A string representing the column slice definition in the format 'start:stop:step'.
     *                          For example, '0:5:1' specifies every column from index 0 to 4.
     * @throws IllegalArgumentException if either slice definition is invalid or cannot be parsed.
     */
    public void set(MXWrapper m, String sliceDefinitionRR, String sliceDefinitionCC) {
        IndexSlice indexSliceRR = IndexSlice.slicingSyntax(sliceDefinitionRR);
        IndexSlice indexSliceCC = IndexSlice.slicingSyntax(sliceDefinitionCC);
        this.mx.set(m.mx, true, indexSliceRR.getCasADiObject(), indexSliceCC.getCasADiObject());
    }

    /**
     * Sets a submatrix in the current matrix using two arguments of type Slice.
     * The specified slices define the rows and columns to be set in the current matrix.
     *
     * @param m The matrix containing the values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The slice used to define the rows to be set.
     * @param cc The slice used to define the columns to be set.
     */
    @Override
    public void set(MXWrapper m, boolean ind1, IndexSlice rr, IndexSlice cc) {
        this.mx.set(m.mx, ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    /**
     * Sets a submatrix in the current matrix using a Slice for rows and an IM for columns.
     * The specified Slice and IM define the rows and columns to be set in the current matrix.
     *
     * @param m The matrix containing the values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The slice used to define the rows to be set.
     * @param cc The IM used to define the columns to be set.
     */
    @Override
    public void set(MXWrapper m, boolean ind1, IndexSlice rr, IMWrapper cc) {
        this.mx.set(m.mx, ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    /**
     * Sets a submatrix in the current matrix using an IM for rows and a Slice for columns.
     * The specified IM and Slice define the rows and columns to be set in the current matrix.
     *
     * @param m The matrix containing the values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The IM used to define the rows to be set.
     * @param cc The slice used to define the columns to be set.
     */
    @Override
    public void set(MXWrapper m, boolean ind1, IMWrapper rr, IndexSlice cc) {
        this.mx.set(m.mx, ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    /**
     * Sets a submatrix in the current matrix using two IM arguments.
     * The specified IMs define the rows and columns to be set in the current matrix.
     *
     * @param m The matrix containing the values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param rr The IM used to define the rows to be set.
     * @param cc The IM used to define the columns to be set.
     */
    @Override
    public void set(MXWrapper m, boolean ind1, IMWrapper rr, IMWrapper cc) {
        this.mx.set(m.mx, ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }


    /**
     * Sets a slice of the MX object using the specified NumberWrapper values.
     *
     * This method creates an {@code IndexSlice} object based on the provided
     * {@link NumberWrapper} arguments, which represent the indices or ranges
     * for the slice. The values are then used to update the specified slice
     * of the MX object with the provided MXWrapper instance.
     *
     * <p>Note: The parameter 'ind1' in the underlying set method is
     * always set to true, indicating that the slice should include the
     * starting index.</p>
     *
     * @param m      An MXWrapper instance containing the values to be set
     *               in the specified slice of the MX object.
     * @param slice   A variable number of {@link NumberWrapper} arguments
     *                representing the indices or ranges for the slice.
     *                Must contain between 1 and 3 elements:
     *                <ul>
     *                  <li>1 element: Represents a single index.</li>
     *                  <li>2 elements: Represents a range from the first
     *                      value to the second value.</li>
     *                  <li>3 elements: Represents a range with a specified step.</li>
     *                </ul>
     * @throws IllegalArgumentException if {@code slice} is null, empty,
     *                                  or contains more than 3 elements.
     */
    @Override
    public void set(MXWrapper m, NumberWrapper... slice) {
        IndexSlice indexSlice = new IndexSlice(slice);
        this.mx.set(m.getCasADiObject(), true, indexSlice.getCasADiObject());
    }

    // ----- Get a set of nonzeros -----
    /**
     * Retrieves a set of non-zero elements from the current matrix using a single argument of type Slice.
     * The specified slice defines the rows to be considered for non-zero extraction.
     * The result is returned as a new MXWrapper containing the non-zero elements.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param k The slice used to define the rows for non-zero extraction.
     * @return A new MXWrapper containing the resulting non-zero elements.
     */
    @Override
    public MXWrapper getNZ(boolean ind1, IndexSlice k) {
        MXWrapper output = new MXWrapper();
        this.mx.get_nz(output.getCasADiObject(), ind1, k.getCasADiObject());
        return output;
    }

    /**
     * Retrieves a set of non-zero elements from the current matrix using a single argument of type IM.
     * The specified IM defines the rows to be considered for non-zero extraction.
     * The result is returned as a new MXWrapper containing the non-zero elements.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param k The IM used to define the rows for non-zero extraction.
     * @return A new MXWrapper containing the resulting non-zero elements.
     */
    @Override
    public MXWrapper getNZ(boolean ind1, IMWrapper k) {
        MXWrapper output = new MXWrapper();
        this.mx.get_nz(output.getCasADiObject(), ind1, k.getCasADiObject());
        return output;
    }

    /**
     * Retrieves a set of non-zero elements from the current matrix using a single argument of type MXWrapper.
     * The specified MXWrapper defines the rows to be considered for non-zero extraction.
     * The result is returned as a new MXWrapper containing the non-zero elements.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param kk The MXWrapper used to define the rows for non-zero extraction.
     * @return A new MXWrapper containing the resulting non-zero elements.
     */
    public MXWrapper getNZ(boolean ind1, MXWrapper kk) {
        MXWrapper output = new MXWrapper();
        this.mx.get_nz(output.getCasADiObject(), ind1, kk.mx);
        return output;
    }

    /**
     * Retrieves a set of non-zero elements from the current matrix using a single argument of type long.
     * The specified long defines the row index to be considered for non-zero extraction.
     * The result is returned as a new MXWrapper containing the non-zero elements.
     *
     * @param ind1 A boolean indicating whether the row index is inclusive.
     * @param kk The long value representing the row index for non-zero extraction.
     * @return A new MXWrapper containing the resulting non-zero elements.
     */
    public MXWrapper getNZ(boolean ind1, long kk) {
        MXWrapper output = new MXWrapper();
        this.mx.get_nz(output.getCasADiObject(), ind1, kk);
        return output;
    }

    /**
     * Retrieves a set of non-zero elements from the current matrix using an inner MXWrapper and an outer Slice.
     * The specified MXWrapper and Slice define the rows and columns to be considered for non-zero extraction.
     * The result is returned as a new MXWrapper containing the non-zero elements.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param inner The MXWrapper used to define the rows for non-zero extraction.
     * @param outer The slice used to define the columns for non-zero extraction.
     * @return A new MXWrapper containing the resulting non-zero elements.
     */
    public MXWrapper getNZ(boolean ind1, MXWrapper inner, IndexSlice outer) {
        MXWrapper output = new MXWrapper();
        this.mx.get_nz(output.getCasADiObject(), ind1, inner.mx, outer.getCasADiObject());
        return output;
    }

    /**
     * Retrieves a set of non-zero elements from the current matrix using an inner Slice and an outer MXWrapper.
     * The specified Slice and MXWrapper define the rows and columns to be considered for non-zero extraction.
     * The result is returned as a new MXWrapper containing the non-zero elements.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param inner The slice used to define the rows for non-zero extraction.
     * @param outer The MXWrapper used to define the columns for non-zero extraction.
     * @return A new MXWrapper containing the resulting non-zero elements.
     */
    public MXWrapper getNZ(boolean ind1, IndexSlice inner, MXWrapper outer) {
        MXWrapper output = new MXWrapper();
        this.mx.get_nz(output.getCasADiObject(), ind1, inner.getCasADiObject(), outer.mx);
        return output;
    }

    /**
     * Retrieves a set of non-zero elements from the current matrix using two MXWrapper arguments.
     * The specified MXWrappers define the rows and columns to be considered for non-zero extraction.
     * The result is returned as a new MXWrapper containing the non-zero elements.
     *
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param inner The MXWrapper used to define the rows for non-zero extraction.
     * @param outer The MXWrapper used to define the columns for non-zero extraction.
     * @return A new MXWrapper containing the resulting non-zero elements.
     */
    public MXWrapper getNZ(boolean ind1, MXWrapper inner, MXWrapper outer) {
        MXWrapper output = new MXWrapper();
        this.mx.get_nz(output.getCasADiObject(), ind1, inner.mx, outer.mx);
        return output;
    }

    // ----- Set a set of nonzeors -----
    /**
     * Sets a set of non-zero elements in the current matrix using a single argument of type Slice.
     * The specified slice defines the rows to be set in the current matrix.
     *
     * @param m The matrix containing the non-zero values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param k The slice used to define the rows to be set.
     */
    @Override
    public void setNZ(MXWrapper m, boolean ind1, IndexSlice k) {
        this.mx.set_nz(m.mx, ind1, k.getCasADiObject());
    }

    /**
     * Sets a set of non-zero elements in the current matrix using a single argument of type IM.
     * The specified IM defines the rows to be set in the current matrix.
     *
     * @param m The matrix containing the non-zero values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param k The IM used to define the rows to be set.
     */
    @Override
    public void setNZ(MXWrapper m, boolean ind1, IMWrapper k) {
        this.mx.set_nz(m.mx, ind1, k.getCasADiObject());
    }

    /**
     * Sets a set of non-zero elements in the current matrix using a single argument of type MXWrapper.
     * The specified MXWrapper defines the rows to be set in the current matrix.
     *
     * @param m The matrix containing the non-zero values to set.
     * @param ind1 A boolean indicating whether the row indices are inclusive.
     * @param kk The MXWrapper used to define the rows to be set.
     */
    public void setNZ(MXWrapper m, boolean ind1, MXWrapper kk) {
        this.mx.set_nz(m.mx, ind1, kk.mx);
    }

    /**
     * Sets a set of non-zero elements in the current matrix using a single argument of type long.
     * The specified long defines the row index to be set in the current matrix.
     *
     * @param m The matrix containing the non-zero values to set.
     * @param ind1 A boolean indicating whether the row index is inclusive.
     * @param kk The long value representing the row index to be set.
     */
    public void setNZ(MXWrapper m, boolean ind1, long kk) {
        this.mx.set_nz(m.mx, ind1, kk);
    }

    /**
     * Computes an Einstein dense tensor contraction between this MX object and another MX object.
     * <br>
     * This method computes the product:
     * <br>
     * C_c = A_a + B_b
     * <br>
     * where a, b, c are index/einstein notation in an encoded form.
     * <br>
     * For example, a matrix-matrix product may be written as:
     * <br>
     * C_ij = A_ik B_kj
     * <br>
     * The encoded form uses strictly negative numbers to indicate labels.
     * <br>
     * For the above example, we would have:
     * <br>
     * a {-1, -3} b {-3, -2} c {-1 -2}
     *
     * @param other The MXWrapper to contract with.
     * @param C The MXWrapper to store the resulting contraction.
     * @param dim_a Dimensions of this MX object.
     * @param dim_b Dimensions of the other MX object.
     * @param dim_c Dimensions of the resulting MX object.
     * @param a Einstein notation for this MX object.
     * @param b Einstein notation for the other MX object.
     * @param c Einstein notation for the resulting MX object.
     * @return MXWrapper. The MXWrapper containing the result of the contraction.
     */
    @Override
    public MXWrapper einstein(MXWrapper other, MXWrapper C,
                              CasADiIntVector dim_a, CasADiIntVector dim_b,
                              CasADiIntVector dim_c, CasADiIntVector a,
                              CasADiIntVector b, CasADiIntVector c) {
        MX.einstein(this.mx, other.mx, C.mx,
                dim_a.getCasADiObject(), dim_b.getCasADiObject(),
                dim_c.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), c.getCasADiObject());
        return C;
    }

    /**
     * Computes an Einstein dense tensor contraction between this MX object and another MX object.
     * <br>
     * This method computes the product:
     * <br>
     * C_c = A_a + B_b
     * <br>
     * where a, b, c are index/einstein notation in an encoded form.
     * <br>
     * For example, a matrix-matrix product may be written as:
     * <br>
     * C_ij = A_ik B_kj
     * <br>
     * The encoded form uses strictly negative numbers to indicate labels.
     * <br>
     * For the above example, we would have:
     * <br>
     * a {-1, -3} b {-3, -2} c {-1 -2}
     *
     * @param other The MXWrapper to contract with.
     * @param dim_a Dimensions of this MX object.
     * @param dim_b Dimensions of the other MX object.
     * @param dim_c Dimensions of the resulting MX object.
     * @param a Einstein notation for this MX object.
     * @param b Einstein notation for the other MX object.
     * @param c Einstein notation for the resulting MX object.
     * @return MXWrapper. A new MXWrapper containing the result of the contraction.
     */
    @Override
    public MXWrapper einstein(MXWrapper other,
                              CasADiIntVector dim_a, CasADiIntVector dim_b,
                              CasADiIntVector dim_c, CasADiIntVector a,
                              CasADiIntVector b, CasADiIntVector c) {
        return new MXWrapper(MX.einstein(this.mx, other.getCasADiObject(), dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(), a.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject()));
    }

    /**
     * Checks if this MX object is equal to another MX object up to a specified depth.
     *
     * @param other The MXWrapper to compare with.
     * @param depth The depth to which the comparison should be performed.
     * @return boolean. true if the two MX objects are equal, false otherwise.
     */
    @Override
    public boolean isEqual(MXWrapper other, long depth) {
        return MX.is_equal(this.mx, other.getCasADiObject(), depth);
    }

    /**
     * Checks if this MX object is equal to another MX object.
     *
     * @param other The MXWrapper to compare with.
     * @return boolean. true if the two MX objects are equal, false otherwise.
     */
    @Override
    public boolean isEqual(MXWrapper other) {
        return MX.is_equal(this.mx, other.getCasADiObject());
    }

    /**
     * Computes the minimum value of this MX object.
     *
     * @return MXWrapper. A new MXWrapper containing the minimum value of the MX object.
     */
    @Override
    public MXWrapper mmin() {
        return new MXWrapper(MX.mmin(this.mx));
    }

    /**
     * Computes the maximum value of this MX object.
     *
     * @return MXWrapper. A new MXWrapper containing the maximum value of the MX object.
     */
    @Override
    public MXWrapper mmax() {
        return new MXWrapper(MX.mmax(this.mx));
    }

    /**
     * Horizontally concatenates this MX object with a vector of MX objects.
     *
     * @param mxVector The vector of MXWrapper objects to concatenate with.
     * @return MXWrapper. A new MXWrapper containing the result of the horizontal concatenation.
     */
    @Deprecated
    private MXWrapper horzcat(MXVector mxVector) {
        return new MXWrapper(MX.horzcat(mxVector.getCasADiObject()));
    }

    /**
     * Creates a diagonal concatenation of a vector of MX objects.
     *
     * @param mxVector The vector of MXWrapper objects to concatenate diagonally.
     * @return MXWrapper. A new MXWrapper containing the result of the diagonal concatenation.
     */
    @Deprecated
    private MXWrapper diagcat(MXVector mxVector) {
        return new MXWrapper(MX.diagcat(mxVector.getCasADiObject()));
    }

    /**
     * Vertically concatenates this MX object with a vector of MX objects.
     *
     * @param mxVector The vector of MXWrapper objects to concatenate with.
     * @return MXWrapper. A new MXWrapper containing the result of the vertical concatenation.
     */
    @Deprecated
    private MXWrapper vertcat(MXVector mxVector) {
        return new MXWrapper(MX.vertcat(mxVector.getCasADiObject()));
    }

    /**
     * Horizontally splits this MX object at the specified offset.
     *
     * @param offset The offset at which to split the MX object.
     * @return MXVector. A vector of MXWrapper objects resulting from the horizontal split.
     */
    public MXVector horzsplit(CasADiIntVector offset) {
        return new MXVector(MX.horzsplit(this.mx, offset.getCasADiObject()));
    }

    /**
     * Diagonally splits this MX object at the specified offsets.
     *
     * @param offset1 The first offset for the diagonal split.
     * @param offset2 The second offset for the diagonal split.
     * @return MXVector. A vector of MXWrapper objects resulting from the diagonal split.
     */
    public MXVector diagsplit(CasADiIntVector offset1, CasADiIntVector offset2) {
        return new MXVector(MX.diagsplit(this.mx, offset1.getCasADiObject(), offset2.getCasADiObject()));
    }

    /**
     * Vertically splits this MX object at the specified offset.
     *
     * @param offset The offset at which to split the MX object.
     * @return MXVector. A vector of MXWrapper objects resulting from the vertical split.
     */
    public MXVector vertsplit(CasADiIntVector offset) {
        return new MXVector(MX.vertsplit(this.mx, offset.getCasADiObject()));
    }

    /**
     * Block concatenates a vector of vectors of MX objects.
     *
     * @param mxVector The vector of vectors of MXWrapper objects to concatenate.
     * @return MXWrapper. A new MXWrapper containing the result of the block concatenation.
     */
    public MXWrapper blockcat(MXVectorCollection mxVector) {
        return new MXWrapper(MX.blockcat(mxVector.getCasADiObject()));
    }

    /**
     * Multiplies this MX object with another MX object.
     *
     * @param other The MXWrapper to multiply with.
     * @return MXWrapper. A new MXWrapper containing the result of the multiplication.
     */
    @Override
    public MXWrapper mtimes(MXWrapper other) {
        return new MXWrapper(MX.mtimes(this.mx, other.mx));
    }

    /**
     * Computes the multiply-accumulate operation: z = x + y * z.
     *
     * @param y The MXWrapper to multiply with.
     * @param z The MXWrapper to accumulate into.
     * @return MXWrapper. A new MXWrapper containing the result of the multiply-accumulate operation.
     */
    @Override
    public MXWrapper mac(MXWrapper y, MXWrapper z) {
        return new MXWrapper(MX.mac(this.mx, y.mx, z.mx));
    }

    /**
     * Reshapes this MX object to the specified number of rows and columns.
     *
     * @param nrow The number of rows for the reshaped MX object.
     * @param ncol The number of columns for the reshaped MX object.
     * @return MXWrapper. A new MXWrapper containing the reshaped MX object.
     */
    @Override
    public MXWrapper reshape(long nrow, long ncol) {
        return new MXWrapper(MX.reshape(this.mx, nrow, ncol));
    }

    /**
     * Reshapes this MX object according to the specified sparsity pattern.
     *
     * @param sp The Sparsity object defining the new sparsity pattern.
     * @return MXWrapper. A new MXWrapper containing the reshaped MX object.
     */
    @Override
    public MXWrapper reshape(SparsityWrapper sp) {
        return new MXWrapper(MX.reshape(this.mx, sp.getCasADiObject()));
    }

    /**
     * Casts this MX object to a new sparsity pattern.
     *
     * @param sp The Sparsity object defining the new sparsity pattern.
     * @return MXWrapper. A new MXWrapper containing the sparsity-cast MX object.
     */
    @Override
    public MXWrapper sparsityCast(SparsityWrapper sp) {
        return new MXWrapper(MX.sparsity_cast(this.mx, sp.getCasADiObject()));
    }

    /**
     * Computes the Kronecker product of this MX object with another MX object.
     *
     * @param b The MXWrapper to compute the Kronecker product with.
     * @return MXWrapper. A new MXWrapper containing the result of the Kronecker product.
     */
    @Override
    public MXWrapper kron(MXWrapper b) {
        return new MXWrapper(MX.kron(this.mx, b.mx));
    }

    /**
     * Computes the Jacobian matrix of this MX object with respect to a variable.
     *
     * @param x The MXWrapper representing the variable with respect to which to compute the Jacobian.
     * @param opts Options for the Jacobian computation.
     * @return MXWrapper. A new MXWrapper containing the Jacobian matrix.
     */
    @Override
    public MXWrapper jacobian(MXWrapper x, Dictionary opts) {
        return new MXWrapper(MX.jacobian(this.mx, x.mx, opts.getCasADiObject()));
    }

    /**
     * Computes the Jacobian matrix of this MX object with respect to a variable.
     *
     * @param x The MXWrapper representing the variable with respect to which to compute the Jacobian.
     * @return MXWrapper. A new MXWrapper containing the Jacobian matrix.
     */
    @Override
    public MXWrapper jacobian(MXWrapper x) {
        return new MXWrapper(MX.jacobian(this.mx, x.mx));
    }

    /**
     * Computes the Hessian matrix of this MX object with respect to a variable.
     *
     * @param x The MXWrapper representing the variable with respect to which to compute the Hessian.
     * @param opts Options for the Hessian computation.
     * @return MXWrapper. A new MXWrapper containing the Hessian matrix.
     */
    @Override
    public MXWrapper hessian(MXWrapper x, Dictionary opts) {
        return new MXWrapper(MX.hessian(this.mx, x.mx, opts.getCasADiObject()));
    }

    /**
     * Computes the Hessian matrix of this MX object with respect to a variable.
     *
     * @param x The MXWrapper representing the variable with respect to which to compute the Hessian.
     * @return MXWrapper. A new MXWrapper containing the Hessian matrix.
     */
    @Override
    public MXWrapper hessian(MXWrapper x) {
        return new MXWrapper(MX.hessian(this.mx, x.mx));
    }

    /**
     * Computes the Hessian matrix of this MX object with respect to two variables.
     *
     * @param x The MXWrapper representing the first variable.
     * @param g The MXWrapper representing the second variable.
     * @param opts Options for the Hessian computation.
     * @return MXWrapper. A new MXWrapper containing the Hessian matrix.
     */
    @Override
    public MXWrapper hessian(MXWrapper x, MXWrapper g, Dictionary opts) {
        return new MXWrapper(MX.hessian(this.mx, x.mx, g.mx, opts.getCasADiObject()));
    }

    /**
     * Computes the Hessian matrix of this MX object with respect to two variables.
     *
     * @param x The MXWrapper representing the first variable.
     * @param g The MXWrapper representing the second variable.
     * @return MXWrapper. A new MXWrapper containing the Hessian matrix.
     */
    @Override
    public MXWrapper hessian(MXWrapper x, MXWrapper g) {
        return new MXWrapper(MX.hessian(this.mx, x.mx, g.mx));
    }

    /**
     * Determines which variables depend on the given expression.
     *
     * @param var The MXWrapper representing the variable.
     * @param order The order of dependency to check.
     * @param tr A boolean flag for additional options.
     * @return BooleanVectorCollection. A new BooleanVectorCollection containing the dependency information.
     */
    @Override
    public BooleanVector whichDepends(MXWrapper var, long order, boolean tr) {
        return new BooleanVector(MX.which_depends(this.mx, var.mx, order, tr));
    }

    /**
     * Determines which variables depend on the given expression.
     *
     * @param var The MXWrapper representing the variable.
     * @param order The order of dependency to check.
     * @return BooleanVectorCollection. A new BooleanVectorCollection containing the dependency information.
     */
    @Override
    public BooleanVector whichDepends(MXWrapper var, long order) {
        return new BooleanVector(MX.which_depends(this.mx, var.mx, order));
    }

    /**
     * Determines which variables depend on the given expression.
     *
     * @param var The MXWrapper representing the variable.
     * @return BooleanVectorCollection. A new BooleanVectorCollection containing the dependency information.
     */
    @Override
    public BooleanVector whichDepends(MXWrapper var) {
        return new BooleanVector(MX.which_depends(this.mx, var.mx));
    }

    /**
     * Computes the sparsity pattern of the Jacobian matrix of this MX object with respect to a variable.
     *
     * @param x The MXWrapper representing the variable with respect to which to compute the Jacobian sparsity.
     * @return Sparsity. A new Sparsity object containing the sparsity pattern of the Jacobian matrix.
     */
    @Override
    public SparsityWrapper jacobianSparsity(MXWrapper x) {
        return new SparsityWrapper(MX.jacobian_sparsity(this.mx, x.mx));
    }

    /**
     * Substitutes a variable in this MX object with a given value.
     *
     * @param v The MXWrapper representing the variable to substitute.
     * @param vdef The MXWrapper representing the value to substitute in.
     * @return MXWrapper. A new MXWrapper containing the result of the substitution.
     */
    @Override
    public MXWrapper substitute(MXWrapper v, MXWrapper vdef) {
        return new MXWrapper(MX.substitute(this.mx, v.mx, vdef.mx));
    }

    /**
     * Solves the linear equation system Ax = b.
     *
     * This method solves the equation system represented by the matrix A (this) and the vector b.
     *
     * @param b The MXWrapper representing the right-hand side vector b.
     * @return MXWrapper. A new MXWrapper containing the solution vector x.
     */
    @Override
    public MXWrapper solve(MXWrapper b) {
        MX result = MX.solve(this.mx, b.mx);
        return new MXWrapper(result);
    }

    /**
     * Solves the linear equation system Ax = b using a specified linear solver.
     *
     * This method allows the user to specify a linear solver and additional options
     * through a dictionary.
     *
     * @param b The MXWrapper representing the right-hand side vector b.
     * @param lsolver The name of the linear solver to use.
     * @param dict The dictionary containing additional options for the solver.
     * @return MXWrapper. A new MXWrapper containing the solution vector x.
     */
    @Override
    public MXWrapper solve(MXWrapper b, String lsolver, Dictionary dict) {
        MX result = MX.solve(this.mx, b.mx, lsolver, dict.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Solves the linear equation system Ax = b using a specified linear solver.
     *
     * This method allows the user to specify a linear solver without additional options.
     *
     * @param b The MXWrapper representing the right-hand side vector b.
     * @param lsolver The name of the linear solver to use.
     * @return MXWrapper. A new MXWrapper containing the solution vector x.
     */
    public MXWrapper solve(MXWrapper b, String lsolver) {
        MX result = MX.solve(this.mx, b.mx, lsolver);
        return new MXWrapper(result);
    }

    /**
     * Computes the inverse minor of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the inverse minor of the matrix.
     */
    @Override
    public MXWrapper invMinor() {
        MX result = MX.inv_minor(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the inverse node of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the inverse node of the matrix.
     */
    public MXWrapper invNode() {
        MX result = MX.inv_node(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the inverse of the matrix represented by this MXWrapper using a specified linear solver.
     *
     * This method allows the user to specify a linear solver and additional options
     * through a dictionary.
     *
     * @param lsolver The name of the linear solver to use.
     * @param dict The dictionary containing additional options for the solver.
     * @return MXWrapper. A new MXWrapper containing the inverse of the matrix.
     */
    @Override
    public MXWrapper inv(String lsolver, Dictionary dict) {
        MX result = MX.inv(this.mx, lsolver, dict.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Computes the inverse of the matrix represented by this MXWrapper using a specified linear solver.
     *
     * This method allows the user to specify a linear solver without additional options.
     *
     * @param lsolver The name of the linear solver to use.
     * @return MXWrapper. A new MXWrapper containing the inverse of the matrix.
     */
    public MXWrapper inv(String lsolver) {
        MX result = MX.inv(this.mx, lsolver);
        return new MXWrapper(result);
    }

    /**
     * Computes the inverse of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the inverse of the matrix.
     */
    @Override
    public MXWrapper inv() {
        MX result = MX.inv(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the pseudo-inverse of the matrix represented by this MXWrapper using a specified linear solver.
     *
     * This method allows the user to specify a linear solver and additional options
     * through a dictionary.
     *
     * @param lsolver The name of the linear solver to use.
     * @param dict The dictionary containing additional options for the solver.
     * @return MXWrapper. A new MXWrapper containing the pseudo-inverse of the matrix.
     */
    @Override
    public MXWrapper pinv(String lsolver, Dictionary dict) {
        MX result = MX.pinv(this.mx, lsolver, dict.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Computes the pseudo-inverse of the matrix represented by this MXWrapper using a specified linear solver.
     *
     * This method allows the user to specify a linear solver without additional options.
     *
     * @param lsolver The name of the linear solver to use.
     * @return MXWrapper. A new MXWrapper containing the pseudo-inverse of the matrix.
     */
    public MXWrapper pinv(String lsolver) {
        MX result = MX.pinv(this.mx, lsolver);
        return new MXWrapper(result);
    }

    /**
     * Computes the pseudo-inverse of the matrix represented by this MXWrapper.
     *
     * This method calculates the pseudo-inverse of the matrix A.
     *
     * @return MXWrapper. A new MXWrapper containing the pseudo-inverse of the matrix.
     */
    @Override
    public MXWrapper pinv() {
        MX result = MX.pinv(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the matrix exponential of the matrix represented by this MXWrapper with a constant time parameter.
     *
     * @param t The MXWrapper representing the time parameter.
     * @return MXWrapper. A new MXWrapper containing the matrix exponential of A at time t.
     */
    @Override
    public MXWrapper expmConst(MXWrapper t) {
        MX result = MX.expm_const(this.mx, t.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the matrix exponential of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the matrix exponential of A.
     */
    @Override
    public MXWrapper expm() {
        MX result = MX.expm(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Returns the number of nodes in the expression represented by this MXWrapper.
     *
     * @return long. The number of nodes in the expression.
     */
    @Override
    public long nNodes() {
        return MX.n_nodes(this.mx);
    }

    /**
     * Prints the operator represented by this MXWrapper.
     *
     * @param args The StringVectorCollection containing additional arguments for printing.
     * @return String. The string representation of the operator.
     */
    @Override
    public String printOperator(StringVector args) {
        return MX.print_operator(this.mx, args.getCasADiObject());
    }

    /**
     * Checks if the expression represented by this MXWrapper depends on the given argument.
     *
     * @param arg The MXWrapper representing the argument.
     * @return boolean. True if this expression depends on the argument, false otherwise.
     */
    @Override
    public boolean dependsOn(MXWrapper arg) {
        return MX.depends_on(this.mx, arg.mx);
    }

    /**
     * Simplifies the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the simplified expression.
     */
    @Override
    public MXWrapper simplify() {
        MX result = MX.simplify(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the dot product of two expressions.
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the dot product.
     */
    @Override
    public MXWrapper dot(MXWrapper y) {
        MX result = MX.dot(this.mx, y.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the right matrix division of two expressions.
     *
     * @param b The MXWrapper representing the divisor.
     * @return MXWrapper. A new MXWrapper containing the result of the right division.
     */
    @Override
    public MXWrapper mrdivide(MXWrapper b) {
        MX result = MX.mrdivide(this.mx, b.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the left matrix division of two expressions.
     *
     * @param b The MXWrapper representing the divisor.
     * @return MXWrapper. A new MXWrapper containing the result of the left division.
     */
    @Override
    public MXWrapper mldivide(MXWrapper b) {
        MX result = MX.mldivide(this.mx, b.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the 2-norm of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the 2-norm of the expression.
     */
    @Override
    public MXWrapper norm2() {
        MX result = MX.norm_2(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the Frobenius norm of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the Frobenius norm of the expression.
     */
    @Override
    public MXWrapper normFro() {
        MX result = MX.norm_fro(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the 1-norm of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the 1-norm of the expression.
     */
    @Override
    public MXWrapper norm1() {
        MX result = MX.norm_1(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the infinity norm of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the infinity norm of the expression.
     */
    @Override
    public MXWrapper normInf() {
        MX result = MX.norm_inf(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Unites two expressions into a single expression.
     *
     * @param B The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the united expression.
     */
    @Override
    public MXWrapper unite(MXWrapper B) {
        MX result = MX.unite(this.mx, B.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the trace of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the trace of the expression.
     */
    @Override
    public MXWrapper trace() {
        MX result = MX.trace(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the diagonal of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the diagonal of the expression.
     */
    @Override
    public MXWrapper diag() {
        MX result = MX.diag(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the sum of all elements in the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the sum of the elements.
     */
    @Override
    public MXWrapper sum2() {
        MX result = MX.sum2(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the sum of elements along the first dimension of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the sum of the elements along the first dimension.
     */
    @Override
    public MXWrapper sum1() {
        MX result = MX.sum1(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Evaluates a polynomial at the given point.
     *
     * @param x The MXWrapper representing the point at which to evaluate the polynomial.
     * @return MXWrapper. A new MXWrapper containing the result of the polynomial evaluation.
     */
    @Override
    public MXWrapper polyval(MXWrapper x) {
        MX result = MX.polyval(this.mx, x.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the determinant of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the determinant of the expression.
     */
    @Override
    public MXWrapper det() {
        MX result = MX.det(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Returns the symbolic variables in the expression represented by this MXWrapper.
     *
     * @return MXVector. A new MXVector containing the symbolic variables.
     */
    public MXVector symvar() {
        return new MXVector(MX.symvar(this.mx));
    }

    /**
     * Computes the null space of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the null space of the matrix.
     */
    @Override
    public MXWrapper nullspace() {
        MX result = MX.nullspace(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the repeated sum of the elements in the expression represented by this MXWrapper.
     *
     * @param n The number of repetitions.
     * @param m The dimension along which to sum.
     * @return MXWrapper. A new MXWrapper containing the result of the repeated sum.
     */
    @Override
    public MXWrapper repsum(long n, long m) {
        MX result = MX.repsum(this.mx, n, m);
        return new MXWrapper(result);
    }

    /**
     * Computes the repeated sum of the elements in the expression represented by this MXWrapper.
     *
     * @param n The number of repetitions.
     * @return MXWrapper. A new MXWrapper containing the result of the repeated sum.
     */
    @Override
    public MXWrapper repsum(long n) {
        MX result = MX.repsum(this.mx, n);
        return new MXWrapper(result);
    }

    /**
     * Densifies the sparse matrix represented by this MXWrapper.
     *
     * @param val The value to fill in the dense matrix.
     * @return MXWrapper. A new MXWrapper containing the densified matrix.
     */
    @Override
    public MXWrapper densify(MXWrapper val) {
        MX result = MX.densify(this.mx, val.mx);
        return new MXWrapper(result);
    }

    /**
     * Densifies the sparse matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the densified matrix.
     */
    @Override
    public MXWrapper densify() {
        MX result = MX.densify(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Projects the expression represented by this MXWrapper onto the given sparsity structure.
     *
     * @param sp The Sparsity structure to project onto.
     * @param intersect A boolean indicating whether to intersect the sparsity.
     * @return MXWrapper. A new MXWrapper containing the projected expression.
     */
    @Override
    public MXWrapper project(SparsityWrapper sp, boolean intersect) {
        MX result = MX.project(this.mx, sp.getCasADiObject(), intersect);
        return new MXWrapper(result);
    }

    /**
     * Projects the expression represented by this MXWrapper onto the given sparsity structure.
     *
     * @param sp The Sparsity structure to project onto.
     * @return MXWrapper. A new MXWrapper containing the projected expression.
     */
    @Override
    public MXWrapper project(SparsityWrapper sp) {
        MX result = MX.project(this.mx, sp.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Computes the cumulative sum of the elements in the expression represented by this MXWrapper along the specified axis.
     *
     * @param axis The axis along which to compute the cumulative sum.
     * @return MXWrapper. A new MXWrapper containing the cumulative sum.
     */
    @Override
    public MXWrapper cumsum(long axis) {
        MX result = MX.cumsum(this.mx, axis);
        return new MXWrapper(result);
    }

    /**
     * Computes the cumulative sum of the elements in the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the cumulative sum.
     */
    @Override
    public MXWrapper cumsum() {
        MX result = MX.cumsum(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Finds the specified expression in the context of this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the found expression.
     */
    public MXWrapper find() {
        MX result = MX.find(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the low-level representation of the given expression with options.
     *
     * @param p The MXWrapper representing the parameter.
     * @param options The dictionary containing options for the computation.
     * @return MXWrapper. A new MXWrapper containing the low-level representation.
     */
    public MXWrapper low(MXWrapper p, Dictionary options) {
        MX result = MX.low(this.mx, p.mx, options.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Computes the low-level representation of the given expression.
     *
     * @param p The MXWrapper representing the parameter.
     * @return MXWrapper. A new MXWrapper containing the low-level representation.
     */
    public MXWrapper low(MXWrapper p) {
        MX result = MX.low(this.mx, p.mx);
        return new MXWrapper(result);
    }

    /**
     * Substitutes variables in the graph of the given expression.
     *
     * @param v The MXVector representing the variables.
     * @param vdef The MXVector representing the default values.
     * @return MXWrapper. A new MXWrapper containing the substituted expression.
     */
    public MXWrapper graphSubstitute(MXVector v, MXVector vdef) {
        MX result = MX.graph_substitute(this.mx, v.getCasADiObject(), vdef.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Expands the given matrix expression with boundary conditions and options.
     *
     * @param boundary The MXVector representing the boundary conditions.
     * @param options The dictionary containing options for the expansion.
     * @return MXWrapper. A new MXWrapper containing the expanded expression.
     */
    public MXWrapper matrixExpand(MXVector boundary, Dictionary options) {
        MX result = MX.matrix_expand(this.mx, boundary.getCasADiObject(), options.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Expands the given vector expression with specified boundary conditions and options.
     *
     * @param e The MXVector representing the vector expression to expand.
     * @param boundary The MXVector representing the boundary conditions.
     * @param options The dictionary containing options for the expansion.
     * @return MXVector. A new MXVector containing the expanded expressions.
     */
    public MXVector matrixExpand(MXVector e, MXVector boundary, Dictionary options) {
        return new MXVector(MX.matrix_expand(e.getCasADiObject(), boundary.getCasADiObject(), options.getCasADiObject()));
    }

    /**
     * Expands the given expression using the specified guess.
     *
     * @param x_guess The MXWrapper representing the initial guess.
     * @return MXWrapper. A new MXWrapper containing the lifted expression.
     */
    public MXWrapper lift(MXWrapper x_guess) {
        MX result = MX.lift(this.mx, x_guess.mx);
        return new MXWrapper(result);
    }

    /**
     * Evaluates the expression numerically.
     *
     * @return DM. A new DM containing the evaluated result.
     */
    @Override
    public DMWrapper evalf() {
        return new DMWrapper(MX.evalf(this.mx));
    }

    /**
     * Computes the B-spline of the given expression with specified coefficients and knots.
     *
     * @param coeffs The DM representing the coefficients.
     * @param knots The DoubleVectorCollection representing the knots.
     * @param degree The IntegerVectorCollection representing the degree.
     * @param m The number of repetitions.
     * @param opts The dictionary containing options for the B-spline.
     * @return MXWrapper. A new MXWrapper containing the B-spline result.
     */
    public MXWrapper bspline(DM coeffs, DoubleVectorCollection knots, CasADiIntVector degree, long m, Dictionary opts) {
        MX result = MX.bspline(this.mx, coeffs, knots.getCasADiObject(), degree.getCasADiObject(), m, opts.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Computes the B-spline of the given expression with specified coefficients and knots.
     *
     * @param coeffs The DM representing the coefficients.
     * @param knots The DoubleVectorCollection representing the knots.
     * @param degree The IntegerVectorCollection representing the degree.
     * @param m The number of repetitions.
     * @return MXWrapper. A new MXWrapper containing the B-spline result.
     */
    public MXWrapper bspline(DM coeffs, DoubleVectorCollection knots, CasADiIntVector degree, long m) {
        MX result = MX.bspline(this.mx, coeffs, knots.getCasADiObject(), degree.getCasADiObject(), m);
        return new MXWrapper(result);
    }

    /**
     * Computes the B-spline of the given expression with specified coefficients and knots.
     *
     * @param coeffs The MXWrapper representing the coefficients.
     * @param knots The DoubleVectorCollection representing the knots.
     * @param degree The IntegerVectorCollection representing the degree.
     * @param m The number of repetitions.
     * @param opts The dictionary containing options for the B-spline.
     * @return MXWrapper. A new MXWrapper containing the B-spline result.
     */
    public MXWrapper bspline(MXWrapper coeffs, DoubleVectorCollection knots, CasADiIntVector degree, long m, Dictionary opts) {
        MX result = MX.bspline(this.mx, coeffs.mx, knots.getCasADiObject(), degree.getCasADiObject(), m, opts.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Computes the B-spline of the given expression with specified coefficients and knots.
     *
     * @param coeffs The MXWrapper representing the coefficients.
     * @param knots The DoubleVectorCollection representing the knots.
     * @param degree The IntegerVectorCollection representing the degree.
     * @param m The number of repetitions.
     * @return MXWrapper. A new MXWrapper containing the B-spline result.
     */
    public MXWrapper bspline(MXWrapper coeffs, DoubleVectorCollection knots, CasADiIntVector degree, long m) {
        MX result = MX.bspline(this.mx, coeffs.mx, knots.getCasADiObject(), degree.getCasADiObject(), m);
        return new MXWrapper(result);
    }

    /**
     * Convexifies the given expression using the specified options.
     *
     * @param opts The dictionary containing options for the convexification.
     * @return MXWrapper. A new MXWrapper containing the convexified expression.
     */
    public MXWrapper convexify(Dictionary opts) {
        MX result = MX.convexify(this.mx, opts.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Convexifies the given expression.
     *
     * @return MXWrapper. A new MXWrapper containing the convexified expression.
     */
    public MXWrapper convexify() {
        MX result = MX.convexify(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Stops the differentiation of the given expression at the specified order.
     *
     * @param order The order at which to stop differentiation.
     * @return MXWrapper. A new MXWrapper containing the expression with stopped differentiation.
     */
    public MXWrapper stopDiff(long order) {
        MX result = MX.stop_diff(this.mx, order);
        return new MXWrapper(result);
    }

    /**
     * Stops the differentiation of the given expression with respect to the specified variable at the specified order.
     *
     * @param var The MXWrapper representing the variable.
     * @param order The order at which to stop differentiation.
     * @return MXWrapper. A new MXWrapper containing the expression with stopped differentiation.
     */
    public MXWrapper stopDiff(MXWrapper var, long order) {
        MX result = MX.stop_diff(this.mx, var.mx, order);
        return new MXWrapper(result);
    }

    /**
     * Performs low-level access to inlined linear interpolation.
     *
     * Usually, you want to be using 'interpolant' instead.
     * Accepts lookup_mode option.
     *
     * @param x The MXVector representing the input values.
     * @param xq The MXVector representing the query points.
     * @param opts The dictionary containing options for the interpolation.
     * @return MXWrapper. A new MXWrapper containing the result of the interpolation.
     */
    public MXWrapper interpnLinear(MXVector x, MXVector xq, Dictionary opts) {
        MX result = MX.interpn_linear(x.getCasADiObject(), this.mx, xq.getCasADiObject(), opts.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Performs low-level access to inlined linear interpolation.
     *
     * Usually, you want to be using 'interpolant' instead.
     *
     * @param x MXVector representing the input values.
     * @param xq The MXVector representing the query points.
     * @return MXWrapper. A new MXWrapper containing the result of the interpolation.
     */
    public MXWrapper interpnLinear(MXVector x, MXVector xq) {
        MX result = MX.interpn_linear(x.getCasADiObject(), this.mx, xq.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Prints the current MX expression represented by this MXWrapper alongside the provided MX expression.
     *
     * @param b The MXWrapper representing the expression to print alongside this one.
     * @return MXWrapper. A new MXWrapper containing the result of the print operation.
     */
    @Override
    public MXWrapper printMe(MXWrapper b) {
        return new MXWrapper(this.mx.printme(b.mx));
    }

    /**
     * Returns itself, but with an assertion attached.
     *
     * If y does not evaluate to 1, a runtime error is raised.
     *
     * @param y The MXWrapper representing the expression to assert.
     * @param fail_message The message to display if the assertion fails.
     * @return MXWrapper. A new MXWrapper containing the result of the assertion.
     */
    public MXWrapper attachAssert(MXWrapper y, String fail_message) {
        return new MXWrapper(this.mx.attachAssert(y.mx, fail_message));
    }

    /**
     * Returns itself, but with an assertion attached.
     *
     * If y does not evaluate to 1, a runtime error is raised.
     *
     * @param y The MXWrapper representing the expression to assert.
     * @return MXWrapper. A new MXWrapper containing the result of the assertion.
     */
    public MXWrapper attachAssert(MXWrapper y) {
        return new MXWrapper(this.mx.attachAssert(y.mx));
    }

    /**
     * Monitors an expression.
     *
     * Returns itself, but with the side effect of printing the nonzeros along with a comment.
     *
     * @param comment The comment to print alongside the nonzeros.
     * @return MXWrapper. A new MXWrapper containing the monitored expression.
     */
    public MXWrapper monitor(String comment) {
        return new MXWrapper(this.mx.monitor(comment));
    }

    /**
     * Transposes the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the transposed matrix.
     */
    public MXWrapper T() {
        return new MXWrapper(this.mx.T());
    }

    /**
     * Retrieves the IM representation of a GetNonzeros or SetNonzeros node.
     *
     * This method provides access to the internal representation of the nonzero elements
     * in the matrix associated with this MXWrapper. It can be useful for understanding
     * the structure of the matrix or for debugging purposes.
     *
     * @return IM. A new IM containing the representation of the nonzero elements.
     */
    public IM mapping() {
        return new IM(this.mx.mapping());
    }

    /**
     * Sets or resets the depth to which equalities are being checked for simplifications.
     *
     * @param eq_depth The maximum depth for equality checks.
     */
    @Override
    public void setMaxDepth(long eq_depth) {
        MX.set_max_depth(eq_depth);
    }

    /**
     * Sets or resets the depth to which equalities are being checked for simplifications
     * to the default value.
     */
    @Override
    public void setMaxDepth() {
        MX.set_max_depth();
    }

    /**
     * Gets the depth to which equalities are being checked for simplifications.
     *
     * @return long. The current maximum depth for equality checks.
     */
    @Override
    public long getMaxDepth() {
        return MX.get_max_depth();
    }

    /**
     * Called from MXFunction to perform forward automatic differentiation.
     *
     * @param fseed The VectorCollection representing the seed for forward mode.
     * @param fsens The VectorCollection to store the sensitivity results.
     */
    public void adForward(MXVectorCollection fseed, MXVectorCollection fsens) {
        this.mx.ad_forward(fseed.getCasADiObject(), fsens.getCasADiObject());
    }

    /**
     * Called from MXFunction to perform reverse automatic differentiation.
     *
     * @param aseed The VectorCollection representing the seed for reverse mode.
     * @param asens The VectorCollection to store the sensitivity results.
     */
    public void adReverse(MXVectorCollection aseed, MXVectorCollection asens) {
        this.mx.ad_reverse(aseed.getCasADiObject(), asens.getCasADiObject());
    }

    /**
     * Accesses a specific row of the matrix represented by this MXWrapper.
     *
     * @param rr The row index to access.
     * @return MXSubIndexWrapper. An object representing the specified row of the matrix.
     */
    @Override
    public MXSubIndexWrapper at(int rr) {
        return new MXSubIndexWrapper(this.mx.at(rr));
    }

    /**
     * Accesses a specific element in the matrix represented by this MXWrapper.
     *
     * @param rr The row index of the element.
     * @param cc The column index of the element.
     * @return MXSubMatrixWrapper. An object representing the specified element of the matrix.
     */
    @Override
    public MXSubMatrixWrapper at(int rr, int cc) {
        return new MXSubMatrixWrapper(this.mx.at(rr, cc));
    }

    /**
     * Assigns the values from another MXWrapper to this MXWrapper.
     *
     * @param other The MXWrapper containing the values to assign.
     */
    @Override
    public void assign(MXWrapper other) {
        this.mx.assign(other.mx);
    }

    /**
     * Splits the given matrix into blocks based on specified vertical and horizontal offsets.
     *
     * @param vert_offset The IntegerVectorCollection representing vertical offsets.
     * @param horz_offset The IntegerVectorCollection representing horizontal offsets.
     * @return VectorCollection. A new VectorCollection containing the split blocks.
     */
    public MXVectorCollection blocksplit(CasADiIntVector vert_offset, CasADiIntVector horz_offset) {
        return new MXVectorCollection(MX.blocksplit(this.mx, vert_offset.getCasADiObject(), horz_offset.getCasADiObject()));
    }

    /**
     * Splits the given matrix into blocks based on specified increments.
     *
     * @param vert_incr The vertical increment for splitting.
     * @param horz_incr The horizontal increment for splitting.
     * @return VectorCollection. A new VectorCollection containing the split blocks.
     */
    public MXVectorCollection blocksplit(long vert_incr, long horz_incr) {
        return new MXVectorCollection(MX.blocksplit(this.mx, vert_incr, horz_incr));
    }

    /**
     * Concatenates the given vector expressions into a single vector.
     *
     * @param x The MXVector representing the vector to concatenate.
     * @return MXWrapper. A new MXWrapper containing the concatenated vector.
     */
    @Deprecated
    private MXWrapper veccat(MXVector x) {
        return new MXWrapper(MX.veccat(x.getCasADiObject()));
    }

    /**
     * Converts the given matrix expression into a vector.
     *
     * @return MXWrapper. A new MXWrapper containing the vector representation.
     */
    @Override
    public MXWrapper vec() {
        return new MXWrapper(MX.vec(this.mx));
    }

    /**
     * Vertically splits the given matrix into n parts.
     *
     * @param n The number of parts to split into.
     * @return MXVector. A new MXVector containing the split parts.
     */
    public MXVector vertsplit_n(long n) {
        return new MXVector(MX.vertsplit_n(this.mx, n));
    }

    /**
     * Gets the string representation of the MX object represented by this MXWrapper.
     *
     * @param more A boolean indicating whether to include additional information.
     * @return String. The string representation of the MX object.
     */
    @Override
    public String toString(boolean more) {
        return this.mx.toString(more);
    }

    /**
     * Gets the string representation of the MX object represented by this MXWrapper.
     *
     * @return String. The string representation of the MX object.
     */
    @Override
    public String toString() {
        return this.mx.toString();
    }

    /**
     * Gets the number of (structural) non-zero elements in the matrix represented by this MXWrapper.
     *
     * @return long. The number of non-zero elements.
     */
    @Override
    public long nnz() {
        return mx.nnz_();
    }

    /**
     * Gets the number of non-zeros in the lower triangular half of the matrix represented by this MXWrapper.
     *
     * @return long. The number of non-zero elements in the lower triangular half.
     */
    @Override
    public long nnzLower() {
        return mx.nnz_lower_();
    }

    /**
     * Gets the number of non-zeros in the upper triangular half of the matrix represented by this MXWrapper.
     *
     * @return long. The number of non-zero elements in the upper triangular half.
     */
    @Override
    public long nnzUpper() {
        return mx.nnz_upper_();
    }

    /**
     * Gets the number of non-zeros on the diagonal of the matrix represented by this MXWrapper.
     *
     * @return long. The number of non-zero elements on the diagonal.
     */
    @Override
    public long nnzDiag() {
        return mx.nnz_diag();
    }

    /**
     * Gets the total number of elements in the matrix represented by this MXWrapper.
     *
     * @return long. The total number of elements.
     */
    @Override
    public long numel() {
        return mx.numel_();
    }

    /**
     * Gets the first dimension (i.e., number of rows) of the matrix represented by this MXWrapper.
     *
     * @return long. The number of rows in the matrix.
     */
    @Override
    public long size1() {
        return mx.size1_();
    }

    /**
     * Gets the number of rows in the matrix represented by this MXWrapper, using Octave-style syntax.
     *
     * @return long. The number of rows in the matrix.
     */
    @Override
    public long rows() {
        return mx.rows();
    }

    /**
     * Gets the second dimension (i.e., number of columns) of the matrix represented by this MXWrapper.
     *
     * @return long. The number of columns in the matrix.
     */
    @Override
    public long size2() {
        return mx.size2_();
    }

    /**
     * Gets the number of columns in the matrix represented by this MXWrapper, using Octave-style syntax.
     *
     * @return long. The number of columns in the matrix.
     */
    @Override
    public long columns() {
        return mx.columns();
    }

    /**
     * Gets the string representation of the dimensions of the matrix represented by this MXWrapper.
     *
     * The representation is e.g. "4x5" or "4x5,10nz".
     *
     * @param withNz A boolean indicating whether to include the number of non-zero elements.
     * @return String. The string representation of the dimensions.
     */
    @Override
    public String dim(boolean withNz) {
        return this.mx.dim_(withNz);
    }

    /**
     * Gets the string representation of the dimensions of the matrix represented by this MXWrapper.
     *
     * The representation is e.g. "4x5" or "4x5,10nz".
     *
     * @return String. The string representation of the dimensions.
     */
    @Override
    public String dim() {
        return this.mx.dim_();
    }

    /**
     * Gets the size along a particular dimension of the matrix represented by this MXWrapper.
     *
     * @param axis The dimension along which to get the size.
     * @return long. The size along the specified dimension.
     */
    @Override
    public long size(long axis) {
        return this.mx.size_(axis);
    }

    /**
     * Checks if the sparsity of the matrix represented by this MXWrapper is empty,
     * i.e., if one of the dimensions is zero (or optionally both dimensions).
     *
     * @param both A boolean indicating whether to check both dimensions.
     * @return boolean. True if the sparsity is empty, false otherwise.
     */
    @Override
    public boolean isEmpty(boolean both) {
        return this.mx.is_empty_(both);
    }

    /**
     * Checks if the sparsity of the matrix represented by this MXWrapper is empty,
     * i.e., if one of the dimensions is zero (or optionally both dimensions).
     *
     * @return boolean. True if the sparsity is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.mx.is_empty_();
    }

    /**
     * Checks if the matrix expression represented by this MXWrapper is dense.
     *
     * @return boolean. True if the matrix is dense, false otherwise.
     */
    @Override
    public boolean isDense() {
        return this.mx.is_dense_();
    }

    /**
     * Checks if the matrix expression represented by this MXWrapper is scalar.
     *
     * @param scalarAndDense A boolean indicating whether to check for both scalar and dense.
     * @return boolean. True if the matrix is scalar, false otherwise.
     */
    @Override
    public boolean isScalar(boolean scalarAndDense) {
        return this.mx.is_scalar_(scalarAndDense);
    }

    /**
     * Checks if the matrix expression represented by this MXWrapper is scalar.
     *
     * @return boolean. True if the matrix is scalar, false otherwise.
     */
    @Override
    public boolean isScalar() {
        return this.mx.is_scalar_();
    }

    /**
     * Checks if the matrix expression represented by this MXWrapper is square.
     *
     * @return boolean. True if the matrix is square, false otherwise.
     */
    @Override
    public boolean isSquare() {
        return this.mx.is_square();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is a row or column vector.
     *
     * @return boolean. True if the matrix is a vector, false otherwise.
     */
    @Override
    public boolean isVector() {
        return this.mx.is_vector_();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is a row vector (i.e., size1() == 1).
     *
     * @return boolean. True if the matrix is a row vector, false otherwise.
     */
    @Override
    public boolean isRow() {
        return this.mx.is_row_();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is a column vector (i.e., size2() == 1).
     *
     * @return boolean. True if the matrix is a column vector, false otherwise.
     */
    @Override
    public boolean isColumn() {
        return this.mx.is_column_();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is upper triangular.
     *
     * @return boolean. True if the matrix is upper triangular, false otherwise.
     */
    @Override
    public boolean isTriu() {
        return this.mx.is_triu_();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is lower triangular.
     *
     * @return boolean. True if the matrix is lower triangular, false otherwise.
     */
    @Override
    public boolean isTril() {
        return this.mx.is_tril_();
    }

    /**
     * Gets the sparsity pattern of the matrix represented by this MXWrapper.
     * See the Sparsity class for details.
     *
     * @return IntegerVectorCollection. A new IntegerVectorCollection containing the row indices of the sparsity pattern.
     */
    @Override
    public CasADiIntVector getRow() {
        return new CasADiIntVector(this.mx.get_row());
    }

    /**
     * Gets the column indices of the sparsity pattern of the matrix represented by this MXWrapper.
     *
     * @return IntegerVectorCollection. A new IntegerVectorCollection containing the column indices of the sparsity pattern.
     */
    @Override
    public CasADiIntVector getColInd() {
        return new CasADiIntVector(this.mx.get_colind());
    }

    /**
     * Gets the row index of the specified element in the matrix represented by this MXWrapper.
     *
     * @param el The element index for which to retrieve the row.
     * @return long. The row index of the specified element.
     */
    @Override
    public long row(long el) {
        return this.mx.row_(el);
    }

    /**
     * Gets the column index of the specified column in the matrix represented by this MXWrapper.
     *
     * @param col The column index for which to retrieve the column index.
     * @return long. The column index of the specified column.
     */
    @Override
    public long colind(long col) {
        return this.mx.colind_(col);
    }

    /**
     * Performs linear interpolation for the given input and specified knots, degree, and options.
     *
     * @param x The DoubleVector representing the input values.
     * @param xq The DoubleVector representing the query points.
     * @param mode The mode of interpolation.
     * @param equidistant A boolean indicating whether the input values are equidistant.
     * @return MXWrapper. A new MXWrapper containing the result of the interpolation.
     */
    @Override
    public MXWrapper interp1d(DoubleVector x, DoubleVector xq, String mode, boolean equidistant) {
        return new MXWrapper(MX.interp1d(x.getCasADiObject(), this.mx, xq.getCasADiObject(), mode, equidistant));
    }

    /**
     * Gets the rank of the sparse matrix represented by this MXWrapper.
     *
     * @return long. The rank of the matrix.
     */
    @Override
    public long sprank() {
        return MX.sprank(this.mx);
    }

    /**
     * Computes the 0-norm of the product of two matrices.
     *
     * @param y The MXWrapper representing the second matrix.
     * @return long. The 0-norm of the product.
     */
    @Override
    public long norm0Mul(MXWrapper y) {
        return MX.norm_0_mul(this.mx, y.mx);
    }

    /**
     * Gets the lower triangular part of the matrix represented by this MXWrapper.
     *
     * @param includeDiagonal A boolean indicating whether to include the diagonal.
     * @return MXWrapper. A new MXWrapper containing the lower triangular part.
     */
    @Override
    public MXWrapper tril(boolean includeDiagonal) {
        return new MXWrapper(MX.tril(this.mx, includeDiagonal));
    }

    /**
     * Gets the lower triangular part of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the lower triangular part.
     */
    @Override
    public MXWrapper tril() {
        return new MXWrapper(MX.tril(this.mx));
    }

    /**
     * Gets the upper triangular part of the matrix represented by this MXWrapper.
     *
     * @param includeDiagonal A boolean indicating whether to include the diagonal.
     * @return MXWrapper. A new MXWrapper containing the upper triangular part.
     */
    public MXWrapper triu(boolean includeDiagonal) {
        return new MXWrapper(MX.triu(this.mx, includeDiagonal));
    }

    /**
     * Gets the upper triangular part of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the upper triangular part.
     */
    @Override
    public MXWrapper triu() {
        return new MXWrapper(MX.triu(this.mx));
    }

    /**
     * Computes the sum of squares of the elements in the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the sum of squares.
     */
    @Override
    public MXWrapper sumsqr() {
        return new MXWrapper(MX.sumsqr(this.mx));
    }

    /**
     * Creates a linearly spaced vector between two points.
     *
     * @param b The ending point.
     * @param nsteps The number of steps.
     * @return MXWrapper. A new MXWrapper containing the linearly spaced vector.
     */
    public MXWrapper linspace(MXWrapper b, long nsteps) {
        return new MXWrapper(MX.linspace(this.mx, b.mx, nsteps));
    }

    /**
     * Computes the cross product of two matrices.
     *
     * @param b The MXWrapper representing the second matrix.
     * @param dim The dimension along which to compute the cross product.
     * @return MXWrapper. A new MXWrapper containing the result of the cross product.
     */
    @Override
    public MXWrapper cross(MXWrapper b, long dim) {
        return new MXWrapper(MX.cross(this.mx, b.mx, dim));
    }

    /**
     * Computes the cross product of two matrices.
     *
     * @param b The MXWrapper representing the second matrix.
     * @return MXWrapper. A new MXWrapper containing the result of the cross product.
     */
    @Override
    public MXWrapper cross(MXWrapper b) {
        return new MXWrapper(MX.cross(this.mx, b.mx));
    }

    /**
     * Computes the skew-symmetric matrix of this matrix.
     *
     * @return MXWrapper. A new MXWrapper containing the skew-symmetric matrix.
     */
    @Override
    public MXWrapper skew() {
        return new MXWrapper(MX.skew(this.mx));
    }

    /**
     * Computes the inverse of the skew-symmetric matrix of this matrix.
     *
     * @return MXWrapper. A new MXWrapper containing the inverse skew-symmetric matrix.
     */
    @Override
    public MXWrapper invSkew() {
        return new MXWrapper(MX.inv_skew(this.mx));
    }

    /**
     * Converts the lower triangular matrix represented by this MXWrapper to a symmetric matrix.
     *
     * @return MXWrapper. A new MXWrapper containing the symmetric matrix.
     */
    @Override
    public MXWrapper tril2symm() {
        return new MXWrapper(MX.tril2symm(this.mx));
    }

    /**
     * Converts the upper triangular matrix represented by this MXWrapper to a symmetric matrix.
     *
     * @return MXWrapper. A new MXWrapper containing the symmetric matrix.
     */
    @Override
    public MXWrapper triu2symm() {
        return new MXWrapper(MX.triu2symm(this.mx));
    }

    /**
     * Computes the difference of the matrix represented by this MXWrapper along the specified axis.
     *
     * @param n The number of times to compute the difference.
     * @param axis The axis along which to compute the difference.
     * @return MXWrapper. A new MXWrapper containing the result of the difference.
     */
    @Override
    public MXWrapper diff(long n, long axis) {
        return new MXWrapper(MX.diff(this.mx, n, axis));
    }

    /**
     * Computes the difference of the matrix represented by this MXWrapper.
     *
     * @param n The number of times to compute the difference.
     * @return MXWrapper. A new MXWrapper containing the result of the difference.
     */
    @Override
    public MXWrapper diff(long n) {
        return new MXWrapper(MX.diff(this.mx, n));
    }

    /**
     * Computes the difference of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the result of the difference.
     */
    @Override
    public MXWrapper diff() {
        return new MXWrapper(MX.diff(this.mx));
    }

    /**
     * Checks if the expression represented by this MXWrapper is linear with respect to the specified variable.
     *
     * @param var The MXWrapper representing the variable.
     * @return boolean. True if the expression is linear, false otherwise.
     */
    @Override
    public boolean isLinear(MXWrapper var) {
        return MX.is_linear(this.mx, var.mx);
    }

    /**
     * Checks if the expression represented by this MXWrapper is quadratic with respect to the specified variable.
     *
     * @param var The MXWrapper representing the variable.
     * @return boolean. True if the expression is quadratic, false otherwise.
     */
    @Override
    public boolean isQuadratic(MXWrapper var) {
        return MX.is_quadratic(this.mx, var.mx);
    }

    /**
     * Retrieves the quadratic coefficients of the expression represented by this MXWrapper.
     *
     * @param var The MXWrapper representing the variable.
     * @param A The MXWrapper to store the quadratic coefficients.
     * @param b The MXWrapper to store the linear coefficients.
     * @param c The MXWrapper to store the constant term.
     * @param check A boolean indicating whether to check the coefficients.
     */
    @Override
    public void quadraticCoeff(MXWrapper var, MXWrapper A, MXWrapper b, MXWrapper c, boolean check) {
        MX.quadratic_coeff(this.mx, var.mx, A.mx, b.mx, c.mx, check);
    }

    /**
     * Retrieves the linear coefficients of the expression represented by this MXWrapper.
     *
     * @param var The MXWrapper representing the variable.
     * @param A The MXWrapper to store the linear coefficients.
     * @param b The MXWrapper to store the constant term.
     * @param check A boolean indicating whether to check the coefficients.
     */
    @Override
    public void linearCoeff(MXWrapper var, MXWrapper A, MXWrapper b, boolean check) {
        MX.linear_coeff(this.mx, var.mx, A.mx, b.mx, check);
    }

    /**
     * Computes the bilinear form of the given matrices.
     *
     * @param x The MXWrapper representing the first matrix.
     * @param y The MXWrapper representing the second matrix.
     * @return MXWrapper. A new MXWrapper containing the result of the bilinear form.
     */
    @Override
    public MXWrapper bilin(MXWrapper x, MXWrapper y) {
        return new MXWrapper(MX.bilin(this.mx, x.mx, y.mx));
    }

    /**
     * Computes the rank-1 update of the given matrices.
     *
     * @param alpha The MXWrapper representing the scalar multiplier.
     * @param x The MXWrapper representing the first matrix.
     * @param y The MXWrapper representing the second matrix.
     * @return MXWrapper. A new MXWrapper containing the result of the rank-1 update.
     */
    @Override
    public MXWrapper rank1(MXWrapper alpha, MXWrapper x, MXWrapper y) {
        return new MXWrapper(MX.rank1(this.mx, alpha.mx, x.mx, y.mx));
    }

    /**
     * Computes the log-sum-exponential of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the result of the log-sum-exponential.
     */
    @Override
    public MXWrapper logsumexp() {
        return new MXWrapper(MX.logsumexp(this.mx));
    }

    /**
     * Computes the Jacobian times a vector operation for the current expression.
     *
     * @param arg The MXWrapper representing the argument for the Jacobian computation.
     * @param v The MXWrapper representing the vector to multiply with the Jacobian.
     * @param tr A boolean indicating whether to transpose the result.
     * @param opts The dictionary containing options for the operation.
     * @return MXWrapper. A new MXWrapper containing the result of the Jacobian times vector operation.
     */
    @Override
    public MXWrapper jtimes(MXWrapper arg, MXWrapper v, boolean tr, Dictionary opts) {
        return new MXWrapper(MX.jtimes_(this.mx, arg.getCasADiObject(), v.getCasADiObject(), tr, opts.getCasADiObject()));
    }

    /**
     * Computes the Jacobian times a vector operation for the current expression.
     *
     * @param arg The MXWrapper representing the argument for the Jacobian computation.
     * @param v The MXWrapper representing the vector to multiply with the Jacobian.
     * @param tr A boolean indicating whether to transpose the result.
     * @return MXWrapper. A new MXWrapper containing the result of the Jacobian times vector operation.
     */
    @Override
    public MXWrapper jtimes(MXWrapper arg, MXWrapper v, boolean tr) {
        return new MXWrapper(MX.jtimes_(this.mx, arg.getCasADiObject(), v.getCasADiObject(), tr));
    }

    /**
     * Computes the Jacobian times a vector operation for the current expression.
     *
     * @param arg The MXWrapper representing the argument for the Jacobian computation.
     * @param v The MXWrapper representing the vector to multiply with the Jacobian.
     * @return MXWrapper. A new MXWrapper containing the result of the Jacobian times vector operation.
     */
    @Override
    public MXWrapper jtimes(MXWrapper arg, MXWrapper v) {
        return new MXWrapper(MX.jtimes_(this.mx, arg.getCasADiObject(), v.getCasADiObject()));
    }

    /**
     * Computes the gradient of the expression represented by this MXWrapper with respect to the specified argument.
     *
     * @param arg The MXWrapper representing the argument for the gradient computation.
     * @param opts The dictionary containing options for the gradient computation.
     * @return MXWrapper. A new MXWrapper containing the gradient of the expression.
     */
    @Override
    public MXWrapper gradient(MXWrapper arg, Dictionary opts) {
        return new MXWrapper(MX.gradient(this.mx, arg.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Computes the gradient of the expression represented by this MXWrapper with respect to the specified argument.
     *
     * @param arg The MXWrapper representing the argument for the gradient computation.
     * @return MXWrapper. A new MXWrapper containing the gradient of the expression.
     */
    @Override
    public MXWrapper gradient(MXWrapper arg) {
        return new MXWrapper(MX.gradient(this.mx, arg.getCasADiObject()));
    }

    /**
     * Computes the tangent of the expression represented by this MXWrapper with respect to the specified argument.
     *
     * @param arg The MXWrapper representing the argument for the tangent computation.
     * @param opts The dictionary containing options for the tangent computation.
     * @return MXWrapper. A new MXWrapper containing the tangent of the expression.
     */
    @Override
    public MXWrapper tangent(MXWrapper arg, Dictionary opts) {
        return new MXWrapper(MX.tangent(this.mx, arg.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Computes the tangent of the expression represented by this MXWrapper with respect to the specified argument.
     *
     * @param arg The MXWrapper representing the argument for the tangent computation.
     * @return MXWrapper. A new MXWrapper containing the tangent of the expression.
     */
    @Override
    public MXWrapper tangent(MXWrapper arg) {
        return new MXWrapper(MX.tangent(this.mx, arg.getCasADiObject()));
    }

    /**
     * Computes the linearization of the given function around the specified point.
     *
     * @param x The MXWrapper representing the point around which to linearize.
     * @param x0 The MXWrapper representing the initial guess.
     * @param opts The dictionary containing options for the linearization.
     * @return MXWrapper. A new MXWrapper containing the linearized function.
     */
    @Override
    public MXWrapper linearize(MXWrapper x, MXWrapper x0, Dictionary opts) {
        return new MXWrapper(MX.linearize(this.mx, x.getCasADiObject(), x0.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Computes the linearization of the given function around the specified point.
     *
     * @param x The MXWrapper representing the point around which to linearize.
     * @param x0 The MXWrapper representing the initial guess.
     * @return MXWrapper. A new MXWrapper containing the linearized function.
     */
    @Override
    public MXWrapper linearize(MXWrapper x, MXWrapper x0) {
        return new MXWrapper(MX.linearize(this.mx, x.getCasADiObject(), x0.getCasADiObject()));
    }

    /**
     * Computes the matrix power of the given matrices.
     *
     * @param y The MXWrapper representing the exponent matrix.
     * @return MXWrapper. A new MXWrapper containing the result of the matrix power.
     */
    @Override
    public MXWrapper mpower(MXWrapper y) {
        return new MXWrapper(MX.mpower(this.mx, y.getCasADiObject()));
    }

    /**
     * Computes the second-order cone representation of the given matrices.
     *
     * @param y The MXWrapper representing the second matrix.
     * @return MXWrapper. A new MXWrapper containing the second-order cone representation.
     */
    @Override
    public MXWrapper soc(MXWrapper y) {
        return new MXWrapper(MX.soc(this.mx, y.getCasADiObject()));
    }

    /**
     * Creates an nrow-by-ncol symbolic primitive.
     *
     * @param name The name of the symbolic variable.
     * @param nrow The number of rows for the symbolic primitive.
     * @param ncol The number of columns for the symbolic primitive.
     * @return MXWrapper. A new MXWrapper containing the created symbolic primitive.
     */
    public static MXWrapper sym(String name, long nrow, long ncol) {
        return new MXWrapper(MX.sym(name, nrow, ncol));
    }

    /**
     * Creates an nrow-by-ncol symbolic primitive.
     *
     * @param name The name of the symbolic variable.
     * @param nrow The number of rows for the symbolic primitive.
     * @return MXWrapper. A new MXWrapper containing the created symbolic primitive.
     */
    public static MXWrapper sym(String name, long nrow) {
        return new MXWrapper(MX.sym(name, nrow));
    }

    /**
     * Creates a symbolic primitive with the given name.
     *
     * @param name The name of the symbolic variable.
     * @return MXWrapper. A new MXWrapper containing the created symbolic primitive.
     */
    public static MXWrapper sym(String name) {
        return new MXWrapper(MX.sym(name));
    }

    /**
     * Creates a symbolic primitive with a given sparsity pattern.
     *
     * @param name The name of the symbolic variable.
     * @param sp The Sparsity object representing the sparsity pattern.
     * @return MXWrapper. A new MXWrapper containing the created symbolic primitive.
     */
    public static MXWrapper sym(String name, SparsityWrapper sp) {
        return new MXWrapper(MX.sym(name, sp.getCasADiObject()));
    }

    /**
     * Creates a vector of length p with matrices that are symbolic primitives of given sparsity.
     *
     * @param name The name of the symbolic variable.
     * @param sp The Sparsity object representing the sparsity pattern.
     * @param p The length of the vector.
     * @return MXVector. A new MXVector containing the symbolic primitives.
     */
    public static MXVector sym(String name, SparsityWrapper sp, long p) {
        return new MXVector(MX.sym(name, sp.getCasADiObject(), p));
    }

    /**
     * Creates a vector of length p with nrow-by-ncol symbolic primitives.
     *
     * @param name The name of the symbolic variable.
     * @param nrow The number of rows for the symbolic primitives.
     * @param ncol The number of columns for the symbolic primitives.
     * @param p The length of the vector.
     * @return MXVector. A new MXVector containing the symbolic primitives.
     */
    public static MXVector sym(String name, long nrow, long ncol, long p) {
        return new MXVector(MX.sym(name, nrow, ncol, p));
    }

    /**
     * Creates a vector of length r of vectors of length p with symbolic primitives of given sparsity.
     *
     * @param name The name of the symbolic variable.
     * @param sp The Sparsity object representing the sparsity pattern.
     * @param p The length of the inner vectors.
     * @param r The length of the outer vector.
     * @return VectorCollection. A new VectorCollection containing the symbolic primitives.
     */
    public static MXVectorCollection sym(String name, SparsityWrapper sp, long p, long r) {
        return new MXVectorCollection(MX.sym(name, sp.getCasADiObject(), p, r));
    }

    /**
     * Creates a vector of length r of vectors of length p with nrow-by-ncol symbolic primitives.
     *
     * @param name The name of the symbolic variable.
     * @param nrow The number of rows for the symbolic primitives.
     * @param ncol The number of columns for the symbolic primitives.
     * @param p The length of the inner vectors.
     * @param r The length of the outer vector.
     * @return VectorCollection. A new VectorCollection containing the symbolic primitives.
     */
    public static MXVectorCollection sym(String name, long nrow, long ncol, long p, long r) {
        return new MXVectorCollection(MX.sym(name, nrow, ncol, p, r));
    }

    /**
     * Creates a dense matrix or a matrix with specified sparsity with all entries set to zero.
     *
     * @param nrow The number of rows for the matrix.
     * @param ncol The number of columns for the matrix.
     * @return MXWrapper. A new MXWrapper containing the created matrix.
     */
    public static MXWrapper zeros(long nrow, long ncol) {
        return new MXWrapper(MX.zeros(nrow, ncol));
    }

    /**
     * Creates a dense matrix or a matrix with specified sparsity with all entries set to zero.
     *
     * @param nrow The number of rows for the matrix.
     * @return MXWrapper. A new MXWrapper containing the created matrix.
     */
    public static MXWrapper zeros(long nrow) {
        return new MXWrapper(MX.zeros(nrow));
    }

    /**
     * Creates a dense matrix or a matrix with specified sparsity with all entries set to zero.
     *
     * @return MXWrapper. A new MXWrapper containing the created matrix.
     */
    public static MXWrapper zeros() {
        return new MXWrapper(MX.zeros());
    }

    /**
     * Creates a dense matrix or a matrix with specified sparsity with all entries set to zero.
     *
     * @param sp The Sparsity object representing the sparsity pattern.
     * @return MXWrapper. A new MXWrapper containing the created matrix.
     */
    public static MXWrapper zeros(SparsityWrapper sp) {
        return new MXWrapper(MX.zeros(sp.getCasADiObject()));
    }

    /**
     * Creates a dense matrix or a matrix with specified sparsity with all entries set to one.
     *
     * @param nrow The number of rows for the matrix.
     * @param ncol The number of columns for the matrix.
     * @return MXWrapper. A new MXWrapper containing the created matrix.
     */
    public static MXWrapper ones(long nrow, long ncol) {
        return new MXWrapper(MX.ones(nrow, ncol));
    }

    /**
     * Creates a dense matrix or a matrix with specified sparsity with all entries set to one.
     *
     * @param nrow The number of rows for the matrix.
     * @return MXWrapper. A new MXWrapper containing the created matrix.
     */
    public static MXWrapper ones(long nrow) {
        return new MXWrapper(MX.ones(nrow));
    }

    /**
     * Creates a dense matrix or a matrix with specified sparsity with all entries set to one.
     *
     * @return MXWrapper. A new MXWrapper containing the created matrix.
     */
    public static MXWrapper ones() {
        return new MXWrapper(MX.ones());
    }

    /**
     * Creates a dense matrix or a matrix with specified sparsity with all entries set to one.
     *
     * @param sp The Sparsity object representing the sparsity pattern.
     * @return MXWrapper. A new MXWrapper containing the created matrix.
     */
    public static MXWrapper ones(SparsityWrapper sp) {
        return new MXWrapper(MX.ones(sp.getCasADiObject()));
    }

    /**
     * Adds the matrix represented by this MXWrapper to another matrix.
     *
     * Addition: (other); this.mx + other
     *
     * @param other The MXWrapper representing the matrix to add.
     * @return MXWrapper. A new MXWrapper containing the result of the addition.
     */
    @Override
    public MXWrapper add(MXWrapper other) {
        MX result = MX.plus(this.mx, other.mx);
        return new MXWrapper(result);
    }

    /**
     * Adds the matrix represented by this MXWrapper to a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the addition is returned as a new MXWrapper instance.
     *
     * @param number The Number to add (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the addition.
     */
    @Override
    public <T extends Number> MXWrapper add(T number) {
        double other = number.doubleValue();
        MXWrapper summand = new MXWrapper(other);
        MX result = MX.plus(this.mx, summand.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Subtracts another matrix from the matrix represented by this MXWrapper.
     *
     * Subtraction: (other); this.mx - other
     *
     * @param other The MXWrapper representing the matrix to subtract.
     * @return MXWrapper. A new MXWrapper containing the result of the subtraction.
     */
    @Override
    public MXWrapper subtract(MXWrapper other) {
        MX result = MX.minus(this.mx, other.mx);
        return new MXWrapper(result);
    }

    /**
     * Subtracts a specified Number (Integer, Double, etc.) from the matrix represented by this MXWrapper.
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the subtraction is returned as a new MXWrapper instance.
     *
     * @param number The Number to subtract (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the subtraction.
     */
    @Override
    public <T extends Number> MXWrapper subtract(T number) {
        double other = number.doubleValue();
        MXWrapper subtrahend = new MXWrapper(other);
        MX result = MX.minus(this.mx, subtrahend.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Multiplies the matrix represented by this MXWrapper with another matrix.
     *
     * Elementwise multiplication: (other); this.mx .* other
     *
     * @param other The MXWrapper representing the matrix to multiply with.
     * @return MXWrapper. A new MXWrapper containing the result of the multiplication.
     */
    @Override
    public MXWrapper multiply(MXWrapper other) {
        MX result = MX.times(this.mx, other.mx);
        return new MXWrapper(result);
    }

    /**
     * Multiplies the matrix represented by this MXWrapper by a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the multiplication is returned as a new MXWrapper instance.
     *
     * @param number The Number to multiply by (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the multiplication.
     */
    @Override
    public <T extends Number> MXWrapper multiply(T number) {
        double other = number.doubleValue();
        MXWrapper multiplicand = new MXWrapper(other);
        MX result = MX.times(this.mx, multiplicand.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Divides the matrix represented by this MXWrapper by another matrix.
     *
     * Elementwise division: (other); this.mx ./ other
     *
     * @param other The MXWrapper representing the matrix to divide by.
     * @return MXWrapper. A new MXWrapper containing the result of the division.
     */
    @Override
    public MXWrapper divide(MXWrapper other) {
        MX result = MX.rdivide(this.mx, other.mx);
        return new MXWrapper(result);
    }

    /**
     * Divides the matrix represented by this MXWrapper by a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the division is returned as a new MXWrapper instance.
     *
     * @param number The Number to divide by (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the division.
     * @throws ArithmeticException if the specified number is zero, as division by zero is not allowed.
     */
    @Override
    public <T extends Number> MXWrapper divide(T number) {
        double other = number.doubleValue();
        if (other == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        MXWrapper divisor = new MXWrapper(other);
        MX result = MX.rdivide(this.mx, divisor.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Performs a logical less than operation between the current MX expression and another MX expression.
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper lt(MXWrapper y) {
        return new MXWrapper(MX.lt(this.mx, y.mx));
    }

    /**
     * Checks if the matrix represented by this MXWrapper is less than a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the comparison is returned as a new MXWrapper instance
     * representing a logical condition.
     *
     * @param number The Number to compare against (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the comparison.
     */
    @Override
    public <T extends Number> MXWrapper lt(T number) {
        double other = number.doubleValue();
        MXWrapper threshold = new MXWrapper(other);
        MX result = MX.lt(this.mx, threshold.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Performs a logical less than or equal to operation between the current MX expression and another MX expression.
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper le(MXWrapper y) {
        return new MXWrapper(MX.le(this.mx, y.mx));
    }

    /**
     * Checks if the matrix represented by this MXWrapper is less than or equal to a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the comparison is returned as a new MXWrapper instance
     * representing a logical condition.
     *
     * @param number The Number to compare against (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the comparison.
     */
    @Override
    public <T extends Number> MXWrapper le(T number) {
        double other = number.doubleValue();
        MXWrapper threshold = new MXWrapper(other);
        MX result = MX.le(this.mx, threshold.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Performs a logical greater than operation between the current MX expression and another MX expression.
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper gt(MXWrapper y) {
        return new MXWrapper(MX.gt(this.mx, y.mx));
    }

    /**
     * Checks if the matrix represented by this MXWrapper is greater than a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the comparison is returned as a new MXWrapper instance
     * representing a logical condition.
     *
     * @param number The Number to compare against (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the comparison.
     */
    @Override
    public <T extends Number> MXWrapper gt(T number) {
        double other = number.doubleValue();
        MXWrapper threshold = new MXWrapper(other);
        MX result = MX.gt(this.mx, threshold.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Performs a logical greater than or equal to operation between the current MX expression and another MX expression.
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper ge(MXWrapper y) {
        return new MXWrapper(MX.ge(this.mx, y.mx));
    }

    /**
     * Checks if the matrix represented by this MXWrapper is greater than or equal to a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the comparison is returned as a new MXWrapper instance
     * representing a logical condition.
     *
     * @param number The Number to compare against (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the comparison.
     */
    @Override
    public <T extends Number> MXWrapper ge(T number) {
        double other = number.doubleValue();
        MXWrapper threshold = new MXWrapper(other);
        MX result = MX.ge(this.mx, threshold.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Performs a logical equality check between the current MX expression and another MX expression.
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper eq(MXWrapper y) {
        return new MXWrapper(MX.eq(this.mx, y.mx));
    }

    /**
     * Checks if the matrix represented by this MXWrapper is equal to a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the comparison is returned as a new MXWrapper instance
     * representing a logical condition.
     *
     * @param number The Number to compare against (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the comparison.
     */
    @Override
    public <T extends Number> MXWrapper eq(T number) {
        double other = number.doubleValue();
        MXWrapper value = new MXWrapper(other);
        MX result = MX.eq(this.mx, value.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Performs a logical not equal to check between the current MX expression and another MX expression.
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper ne(MXWrapper y) {
        return new MXWrapper(MX.ne(this.mx, y.mx));
    }

    /**
     * Checks if the matrix represented by this MXWrapper is not equal to a specified Number (Integer, Double, etc.).
     *
     * This method converts the provided Number to a double value using {@link Number#doubleValue()},
     * which may involve rounding. The result of the comparison is returned as a new MXWrapper instance
     * representing a logical condition.
     *
     * @param number The Number to compare against (can be Integer, Double, etc.).
     * @return MXWrapper A new MXWrapper containing the result of the comparison.
     */
    @Override
    public <T extends Number> MXWrapper ne(T number) {
        double other = number.doubleValue();
        MXWrapper value = new MXWrapper(other);
        MX result = MX.ne(this.mx, value.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Performs a logical AND operation between the current MX expression and another MX expression.
     *
     * Returns (an expression evaluating to) 1 if both
     * expressions are nonzero and 0 otherwise
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper logicAnd(MXWrapper y) {
        return new MXWrapper(MX.logic_and(this.mx, y.mx));
    }

    /**
     * Performs a logical OR operation between the current MX expression and another MX expression.
     *
     * Returns (an expression evaluating to) 1 if at
     * least one expression is nonzero and 0 otherwise
     *
     * @param y The MXWrapper representing the second expression.
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper logicOr(MXWrapper y) {
        return new MXWrapper(MX.logic_or(this.mx, y.mx));
    }

    /**
     * Performs a logical NOT operation on the current MX expression.
     *
     * Returns (an expression evaluating to) 1 if
     * expression is zero and 0 otherwise
     *
     * @return MXWrapper. A new MXWrapper containing the result of the logical operation.
     */
    @Override
    public MXWrapper logicNot() {
        return new MXWrapper(MX.logic_not(this.mx));
    }

    /**
     * Computes the absolute value of the wrapped MX object.
     *
     * @return A new MXWrapper containing the absolute value.
     */
    @Override
    public MXWrapper abs() {
        return new MXWrapper(MX.abs(this.mx));
    }

    /**
     * Computes the square root of the wrapped MX object.
     *
     * @return A new MXWrapper containing the square root.
     */
    @Override
    public MXWrapper sqrt() {
        return new MXWrapper(MX.sqrt(this.mx));
    }


    /**
     * Computes the square of the wrapped MX object.
     *
     * @return A new MXWrapper containing the square.
     */
    @Override
    public MXWrapper sq() {
        return new MXWrapper(MX.sq(this.mx));
    }

    /**
     * Computes the sine of the wrapped MX object.
     *
     * @return A new MXWrapper containing the sine value.
     */
    @Override
    public MXWrapper sin() {
        return new MXWrapper(MX.sin(this.mx));
    }

    /**
     * Computes the cosine of the wrapped MX object.
     *
     * @return A new MXWrapper containing the cosine value.
     */
    @Override
    public MXWrapper cos() {
        return new MXWrapper(MX.cos(this.mx));
    }

    /**
     * Computes the tangent of the wrapped MX object.
     *
     * @return A new MXWrapper containing the tangent value.
     */
    @Override
    public MXWrapper tan() {
        return new MXWrapper(MX.tan(this.mx));
    }

    /**
     * Computes the arc tangent of the wrapped MX object.
     *
     * @return A new MXWrapper containing the arc tangent value.
     */
    @Override
    public MXWrapper atan() {
        return new MXWrapper(MX.atan(this.mx));
    }

    /**
     * Computes the arc sine of the wrapped MX object.
     *
     * @return A new MXWrapper containing the arc sine value.
     */
    @Override
    public MXWrapper asin() {
        return new MXWrapper(MX.asin(this.mx));
    }

    /**
     * Computes the arc cosine of the wrapped MX object.
     *
     * @return A new MXWrapper containing the arc cosine value.
     */
    @Override
    public MXWrapper acos() {
        return new MXWrapper(MX.acos(this.mx));
    }

    /**
     * Computes the hyperbolic tangent of the wrapped MX object.
     *
     * @return A new MXWrapper containing the hyperbolic tangent value.
     */
    @Override
    public MXWrapper tanh() {
        return new MXWrapper(MX.tanh(this.mx));
    }

    /**
     * Computes the hyperbolic sine of the wrapped MX object.
     *
     * @return A new MXWrapper containing the hyperbolic sine value.
     */
    @Override
    public MXWrapper sinh() {
        return new MXWrapper(MX.sinh(this.mx));
    }

    /**
     * Computes the hyperbolic cosine of the wrapped MX object.
     *
     * @return A new MXWrapper containing the hyperbolic cosine value.
     */
    @Override
    public MXWrapper cosh() {
        return new MXWrapper(MX.cosh(this.mx));
    }

    /**
     * Computes the inverse hyperbolic tangent of the wrapped MX object.
     *
     * @return A new MXWrapper containing the inverse hyperbolic tangent value.
     */
    @Override
    public MXWrapper atanh() {
        return new MXWrapper(MX.atanh(this.mx));
    }

    /**
     * Computes the inverse hyperbolic sine of the wrapped MX object.
     *
     * @return A new MXWrapper containing the inverse hyperbolic sine value.
     */
    @Override
    public MXWrapper asinh() {
        return new MXWrapper(MX.asinh(this.mx));
    }

    /**
     * Computes the inverse hyperbolic cosine of the wrapped MX object.
     *
     * @return A new MXWrapper containing the inverse hyperbolic cosine value.
     */
    @Override
    public MXWrapper acosh() {
        return new MXWrapper(MX.acosh(this.mx));
    }

    /**
     * Computes the elementwise exponential of the wrapped MX object.
     *
     * @return A new MXWrapper containing the exponential value.
     */
    @Override
    public MXWrapper exp() {
        return new MXWrapper(MX.exp(this.mx));
    }

    /**
     * Computes the natural logarithm of the wrapped MX object.
     *
     * @return A new MXWrapper containing the natural logarithm value.
     */
    @Override
    public MXWrapper log() {
        return new MXWrapper(MX.log(this.mx));
    }

    /**
     * Computes the base-10 logarithm of the wrapped MX object.
     *
     * @return A new MXWrapper containing the base-10 logarithm value.
     */
    @Override
    public MXWrapper log10() {
        return new MXWrapper(MX.log10(this.mx));
    }

    /**
     * Computes the natural logarithm of (1 + wrapped MX object).
     *
     * @return A new MXWrapper containing the logarithm value.
     */
    @Override
    public MXWrapper log1p() {
        return new MXWrapper(MX.log1p(this.mx));
    }

    /**
     * Computes the elementwise exponential minus one of the wrapped MX object.
     *
     * @return A new MXWrapper containing the result of exp(x) - 1.
     */
    @Override
    public MXWrapper expm1() {
        return new MXWrapper(MX.expm1(this.mx));
    }

    /**
     * Computes the largest integer less than or equal to the wrapped MX object.
     *
     * @return A new MXWrapper containing the floor value.
     */
    @Override
    public MXWrapper floor() {
        return new MXWrapper(MX.floor(this.mx));
    }

    /**
     * Computes the smallest integer greater than or equal to the wrapped MX object.
     *
     * @return A new MXWrapper containing the ceiling value.
     */
    @Override
    public MXWrapper ceil() {
        return new MXWrapper(MX.ceil(this.mx));
    }

    /**
     * Computes the error function of the wrapped MX object.
     *
     * @return A new MXWrapper containing the error function value.
     */
    @Override
    public MXWrapper erf() {
        return new MXWrapper(MX.erf(this.mx));
    }

    /**
     * Computes the inverse error function of the wrapped MX object.
     *
     * @return A new MXWrapper containing the inverse error function value.
     */
    @Override
    public MXWrapper erfinv() {
        return new MXWrapper(MX.erfinv(this.mx));
    }

    /**
     * Computes the sign of the wrapped MX object.
     *
     * @return A new MXWrapper containing the sign value.
     */
    @Override
    public MXWrapper sign() {
        return new MXWrapper(MX.sign(this.mx));
    }

    /**
     * Computes the elementwise power of the wrapped MX object raised to another MXWrapper.
     *
     * @param other The MXWrapper containing the exponent.
     * @return A new MXWrapper containing the result of x^y.
     */
    @Override
    public MXWrapper pow(MXWrapper other) {
        return new MXWrapper(MX.pow(this.mx, other.mx));
    }

    /**
     * Raises the current MX object to the power of the given exponent.
     * This method accepts any subclass of {@link Number}, converting it to a double value.
     * It then creates a new MX object with this double value and computes the power operation.
     * The result is wrapped in a new {@code MXWrapper} instance and returned.
     *
     * @param exponent the exponent to which the current MX object is to be raised.
     *                 This can be any object that extends from {@link Number} (e.g., Integer, Double, Float, Long).
     * @return a new {@code MXWrapper} instance representing the result of the power operation.
     * @param <T> a type parameter extending {@link Number}, allowing various numeric types to be used as the exponent.
     */
    @Override
    public <T extends Number> MXWrapper pow(T exponent) {
        double exp = exponent.doubleValue();
        MX exponentMX = new MX(exp);
        return new MXWrapper(MX.pow(this.mx, exponentMX));
    }

    /**
     * Computes the remainder of the division of the wrapped MX object by another MXWrapper.
     *
     * @param other The MXWrapper containing the divisor.
     * @return A new MXWrapper containing the remainder.
     */
    @Override
    public MXWrapper mod(MXWrapper other) {
        return new MXWrapper(MX.mod(this.mx, other.mx));
    }

    /**
     * Computes the remainder after division of the wrapped MX object by another MXWrapper.
     *
     * @param other The MXWrapper containing the divisor.
     * @return A new MXWrapper containing the remainder.
     */
    @Override
    public MXWrapper remainder(MXWrapper other) {
        return new MXWrapper(MX.remainder(this.mx, other.mx));
    }

    /**
     * Computes the two-argument arc tangent of the wrapped MX object and another MXWrapper.
     *
     * @param other The MXWrapper containing the x-coordinate.
     * @return A new MXWrapper containing the arc tangent value.
     */
    @Override
    public MXWrapper atan2(MXWrapper other) {
        return new MXWrapper(MX.atan2(this.mx, other.mx));
    }

    /**
     * Computes a conditional assignment of the wrapped MX object and another MXWrapper.
     *
     * @param other The MXWrapper containing the value to assign if the condition is true.
     * @return A new MXWrapper containing the result of the conditional assignment.
     */
    @Override
    public MXWrapper ifElseZero(MXWrapper other) {
        return new MXWrapper(MX.if_else_zero(this.mx, other.mx));
    }

    /**
     * Computes the minimum of the wrapped MX object and another MXWrapper.
     *
     * @param other The MXWrapper containing the value to compare.
     * @return A new MXWrapper containing the minimum value.
     */
    @Override
    public MXWrapper fmin(MXWrapper other) {
        return new MXWrapper(MX.fmin(this.mx, other.mx));
    }

    /**
     * Computes the maximum of the wrapped MX object and another MXWrapper.
     *
     * @param other The MXWrapper containing the value to compare.
     * @return A new MXWrapper containing the maximum value.
     */
    @Override
    public MXWrapper fmax(MXWrapper other) {
        return new MXWrapper(MX.fmax(this.mx, other.mx));
    }

    /**
     * Copies the sign of one wrapped MX object to another.
     *
     * @param other The MXWrapper containing the value whose sign is to be copied.
     * @return A new MXWrapper containing the result of the sign copy.
     */
    @Override
    public MXWrapper copysign(MXWrapper other) {
        return new MXWrapper(MX.copysign(this.mx, other.mx));
    }

    /**
     * Computes the elementwise power of the wrapped MX object raised to a constant power.
     *
     * @param other The MXWrapper containing the constant exponent.
     * @return A new MXWrapper containing the result of x^y.
     */
    @Override
    public MXWrapper constpow(MXWrapper other) {
        return new MXWrapper(MX.constpow(this.mx, other.mx));
    }

    /**
     * Computes the hypotenuse of the wrapped MX object and another MXWrapper.
     *
     * @param other The MXWrapper containing the other side of the triangle.
     * @return A new MXWrapper containing the hypotenuse value.
     */
    @Override
    public MXWrapper hypot(MXWrapper other) {
        return new MXWrapper(MX.hypot(this.mx, other.mx));
    }

    /**
     * Negates the current MXWrapper object.
     *
     * <p>This method creates a new MXWrapper that represents the negation of the current
     * MXWrapper. It multiplies the current CasADi object by -1 to achieve this.</p>
     *
     * @return A new MXWrapper containing the negated value of the current object.
     */
    @Override
    public MXWrapper negate() {
        MXWrapper sign = new MXWrapper(-1);
        return new MXWrapper(MX.times(this.mx, sign.getCasADiObject()));
    }

    /**
     * Returns the associated CasADi object.
     *
     * This method retrieves the instance of type {@link MX}
     * that is stored in this class. The CasADi object
     * can be used for mathematical operations or further
     * processing.
     *
     * @return the CasADi object of type {@link MX}
     */
    public MX getCasADiObject() {
        return this.mx;
    }

    /**
     * Returns the unique identifier for this constrained variable.
     * The ID can be used to distinguish this variable from others
     * within the optimization problem or constraint model.
     *
     * @return The ID of the constrained variable.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier for this constrained variable.
     * This ID can be used to reference the variable in constraints
     * and for debugging purposes.
     *
     * @param id The unique identifier to set for this variable.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Converts this object to an MXVector.
     *
     * This method creates a new instance of MXVector using the current object as an argument.
     * It is useful for converting the object's data into a vector format, which might be
     * necessary for operations that require vector input.
     *
     * @return A new MXVector instance containing the data from this object.
     */
    public MXVector toVector() {
        return new MXVector(this);
    }

}
