package de.dhbw.rahmlab.casadi.api.core.wrapper;

import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.*;

// TODO: Bind StdVectorMX to MXWrapper
public class MXWrapper {

    private MX mx;

    /**
     * default constructor
     */
    public MXWrapper() {
        mx = new MX();
    }

    /**
     * MX constructor
     *
     * @param mx
     */
    public MXWrapper(MX mx) {
        this.mx = mx;
    }

    /**
     * DM constructor
     *
     * @param dm
     */
    public MXWrapper(DMWrapper dm) {
        this.mx = new MX(dm.getCasadiObject());
    }

    /**
     * Constructs a constant matrix with a given sparsity and values.
     *
     * @param sp The Sparsity object representing the sparsity pattern.
     * @param val The constant value to fill the matrix.
     * @param dummy A boolean parameter used internally.
     */
    public MXWrapper(Sparsity sp, double val, boolean dummy) {
        this.mx = new MX(sp, val, dummy);
    }

    /**
     * static values constructor
     *
     * @param value
     * @return MXWrapper
     */
    public static MXWrapper fromValue(double value) {
        return new MXWrapper(new MX(value));
    }

    /**
     * double array constructor
     *
     * @param values
     * @return MXWrapper
     */
    public static MXWrapper fromArray(double[] values) {
        return new MXWrapper(new MX(new StdVectorDouble(values)));
    }

    /**
     * iterable constructor
     *
     * @param values
     * @return MXWrapper
     */
    public static MXWrapper fromIterable(Iterable<Double> values) {
        return new MXWrapper(new MX(new StdVectorDouble(values)));
    }

    /**
     * sparsity constructor
     *
     * @param sparsity
     * @return MXWrapper
     */
    public static MXWrapper fromSparsity(Sparsity sparsity) {
        return new MXWrapper(new MX(sparsity));
    }

    /**
     * Construct matrix with a given sparsity and a file with nonzeros
     *
     * @param sparsity
     * @param filename
     * @return MXWrapper
     */
    public static MXWrapper fromSparsityAndFile(Sparsity sparsity, String filename) {
        return new MXWrapper(new MX(sparsity, filename));
    }

    /**
     * Construct matrix with a given sparsity and nonzeros
     *
     * @param sparsity
     * @param values
     * @return MXWrapper
     */
    public static MXWrapper fromSparsityAndValues(Sparsity sparsity, MXWrapper values) {
        return new MXWrapper(new MX(sparsity, values.mx));
    }

    /**
     * Create a sparse matrix with all structural zeros
     *
     * @param rows
     * @param cols
     * @return MXWrapper
     */
    public static MXWrapper fromSize(long rows, long cols) {
        return new MXWrapper(new MX(rows, cols));
    }

    /**
     * Returns the sparsity pattern of the MX matrix.
     * Returns null if no sparsity pattern exists.
     *
     * @return sparsity
     */
    public Sparsity sparsity() {
        return this.mx.sparsity();
    }

    /**
     * Returns the truth value of the MX expression.
     *
     * @return boolean
     */
    public boolean nonzero() {
        return this.mx.__nonzero__();
    }

    /**
     * Returns an owning reference to the sparsity pattern.
     *
     * @return Sparsity
     */
    public Sparsity getSparsity() {
        return this.mx.get_sparsity();
    }

    /**
     * Erase a submatrix (leaving structural zeros in its place)
     *
     * @param operation
     * @return MXWrapper
     */
    public MXWrapper erase (EraseOperation operation) {
        operation.apply(this.mx);
        return this;
    }

    /**
     * Static method to create an Erase operation
     *
     * @return EraseOperation
     */
    public static EraseOperation erase() {
        return new EraseOperation();
    }

    /**
     * First draft.
     *
     * A helper class to configure and apply an "erase" operation on an MX object.
     * This class provides a builder-like interface to set various parameters for the "erase" operation.
     */
    public static class EraseOperation {
        private StdVectorCasadiInt rows;
        private StdVectorCasadiInt cols;
        private Boolean indicator;

        /**
         * Constructor for EraseOperation.
         * Initializes all parameters to null.
         */
        private EraseOperation() {
            this.rows = null;
            this.cols = null;
            this.indicator = null;
        }

        /**
         * Sets the rows to be erased in the MX object.
         *
         * @param rows A vector of integers representing the rows to be erased.
         * @return The current instance of EraseOperation for method chaining.
         */
        public EraseOperation setRows(StdVectorCasadiInt rows) {
            this.rows = rows;
            return this;
        }

        /**
         * Sets the columns to be erased in the MX object.
         *
         * @param cols A vector of integers representing the columns to be erased.
         * @return The current instance of EraseOperation for method chaining.
         */
        public EraseOperation setCols(StdVectorCasadiInt cols) {
            this.cols = cols;
            return this;
        }

        /**
         * Sets the indicator for the erase operation.
         * The indicator controls how the elements should be erased (e.g., structural zeros).
         *
         * @param indicator A boolean indicating how the elements are erased.
         * @return The current instance of EraseOperation for method chaining.
         */
        public EraseOperation setIndicator(Boolean indicator) {
            this.indicator = indicator;
            return this;
        }

        /**
         * Applies the erase operation on the given MX object using the specified parameters.
         * This method checks the available parameters (rows, cols, and indicator) and calls
         * the appropriate erase method on the MX object.
         *
         * @param mx The MX object on which the erase operation will be applied.
         */
        public void apply(MX mx) {
            if (rows != null && cols != null && indicator != null) {
                mx.erase(rows, cols, indicator);
            } else if (rows != null && cols != null) {
                mx.erase(rows, cols);
            } else if (rows != null && indicator != null) {
                mx.erase(rows, indicator);
            } else if (rows != null) {
                mx.erase(rows);
            }
        }
    }

    /**
     * Method for enlarging the matrix
     *
     * @param operation
     * @return MXWrapper
     */
    public MXWrapper enlarge(EnlargeOperation operation) {
        operation.apply(this.mx);
        return this;
    }

    /**
     * Static method to create an EnlargeOperation builder
     *
     * @return EnlargeOperation
     */
    public static EnlargeOperation enlarge() {
        return new EnlargeOperation();
    }

    /**
     * Inner class to represent the parameters for enlarging the matrix.
     * Provides a fluent API to configure the enlarge operation.
     */
    public static class EnlargeOperation {
        private long nrow;
        private long ncol;
        private StdVectorCasadiInt rows;
        private StdVectorCasadiInt cols;
        private Boolean indicator;

        /**
         * default construtor
         */
        private EnlargeOperation() {
            this.nrow = 0;
            this.ncol = 0;
            this.rows = null;
            this.cols = null;
            this.indicator = null;
        }

        /**
         * Sets the number of rows for the enlarged matrix.
         *
         * @param nrow The number of rows.
         * @return The current instance of EnlargeOperation for method chaining.
         */
        public EnlargeOperation setRows(long nrow) {
            this.nrow = nrow;
            return this;
        }

        /**
         * Sets the number of columns for the enlarged matrix.
         *
         * @param ncol The number of columns.
         * @return The current instance of EnlargeOperation for method chaining.
         */
        public EnlargeOperation setCols(long ncol) {
            this.ncol = ncol;
            return this;
        }

        /**
         * Sets the rows to be inserted into the enlarged matrix.
         *
         * @param rows A vector of integers representing the rows to be inserted.
         * @return The current instance of EnlargeOperation for method chaining.
         */
        public EnlargeOperation setRowIndices(StdVectorCasadiInt rows) {
            this.rows = rows;
            return this;
        }

        /**
         * Sets the columns to be inserted into the enlarged matrix.
         *
         * @param cols A vector of integers representing the columns to be inserted.
         * @return The current instance of EnlargeOperation for method chaining.
         */
        public EnlargeOperation setColIndices(StdVectorCasadiInt cols) {
            this.cols = cols;
            return this;
        }

        /**
         * Sets the indicator for the enlargement operation.
         * The indicator defines how the empty rows/columns should be handled (e.g., structural zeros).
         *
         * @param indicator A boolean indicating how to handle the empty rows and columns.
         * @return The current instance of EnlargeOperation for method chaining.
         */
        public EnlargeOperation setIndicator(Boolean indicator) {
            this.indicator = indicator;
            return this;
        }

        /**
         * Applies the enlarge operation to the given MX object.
         * Depending on the set parameters, it calls the appropriate enlarge method.
         *
         * @param mx The MX object to which the enlarge operation will be applied.
         */
        public void apply(MX mx) {
            if (rows != null && cols != null && indicator != null) {
                mx.enlarge(nrow, ncol, rows, cols, indicator);
            } else if (rows != null && cols != null) {
                mx.enlarge(nrow, ncol, rows, cols);
            }
        }
    }

    /**
     * Method to get the dependency
     *
     * @param operation
     * @return MXWrapper
     */
    public MXWrapper dep(DepOperation operation) {
        operation.apply(mx);
        return this;
    }

    /**
     * Static method to create a DepOperation builder
     *
     * @return DepOperation
     */
    public static DepOperation dep() {
        return new DepOperation();
    }

    /**
     * Inner class to represent the parameters for the dep operation.
     * Provides a fluent API to configure the dep operation.
     */
    public static class DepOperation {
        private Long index;

        /**
         * default constructor
         */
        private DepOperation() {
            this.index = null;
        }

        /**
         * Sets the index for the nth dependency to be retrieved.
         *
         * @param index The index of the desired dependency.
         * @return The current instance of DepOperation for method chaining.
         */
        public DepOperation setIndex(long index) {
            this.index = index;
            return this;
        }

        /**
         * Applies the dep operation to the given MX object.
         * Depending on whether an index is set, it calls the appropriate dep method.
         *
         * @param mx The MX object from which the dependency will be retrieved.
         */
        public void apply(MX mx) {
            if (index != null) {
                mx.dep(index);
            } else {
                mx.dep();
            }
        }
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
    public long nDep() {
        return this.mx.n_dep();
    }

    /**
     * Gets the name of the MX object.
     * This method returns the name of the symbolic expression or variable.
     *
     * @return String. The name of the MX object as a String.
     */
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
    public boolean isSymbolic() {
        return this.mx.is_symbolic();
    }

    /**
     * Checks if the MX object is constant.
     * A constant object represents a fixed value that cannot change during optimization.
     *
     * @return boolean. true if the object is constant, false otherwise.
     */
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
    public Function whichFunction() {
        return this.mx.which_function();
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
     * @return StdVectorMX. A vector of MX primitives.
     */
    public StdVectorMX primitives() {
        return this.mx.primitives();
    }

    /**
     * Splits an MX expression into its symbolic primitives.
     *
     * This method decomposes the given MX expression into its constituent symbolic primitives
     * (basic symbolic components) and returns them as a vector.
     *
     * @param x The MX expression to split into primitives.
     * @return StdVectorMX. A vector of MX primitives representing the split expression.
     */
    public StdVectorMX splitPrimitives(MXWrapper x) {
        return this.mx.split_primitives(x.getCasADiObject());
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
    public MXWrapper joinPrimitives(StdVectorMX v) {
        return new MXWrapper(this.mx.join_primitives(v));
    }

    /**
     * Checks if there are duplicate symbolic expressions in the MX object.
     *
     * This method detects if any symbolic primitives appear more than once in the expression.
     * If duplicates are found, a warning is issued with the names of the duplicate expressions.
     *
     * @return boolean. true if duplicate symbolic expressions are found, false otherwise.
     */
    public boolean hasDuplicates() {
        return this.mx.has_duplicates();
    }

    /**
     * Resets the marker for an input expression.
     *
     * This method clears the temporary input flag for the expression, which can be used
     * to mark or unmark an expression during symbolic manipulation.
     */
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
    public Dict info() {
        return this.mx.info();
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
     * Creates a binary operation node using the provided operands and operation ID.
     *
     * This method creates a binary operation node that represents a symbolic expression
     * for a mathematical operation between two MX objects. The operation type is
     * specified by the provided operation ID.
     *
     * @param op The operation type ID (e.g., addition, multiplication, etc.).
     * @param x The left operand (MX object).
     * @param y The right operand (MX object).
     * @return MXWrapper
     */
    public MXWrapper binary(long op, MXWrapper x, MXWrapper y) {
        MX result = MX.binary(op, x.getCasADiObject(), y.getCasADiObject());
        return new MXWrapper(result);
    }

    /**
     * Creates a unary operation node using the provided operand and operation ID.
     *
     * This method creates a unary operation node that represents a symbolic expression
     * for a mathematical operation on a single MX object. The operation type is
     * specified by the provided operation ID.
     *
     * @param op The operation type ID (e.g., negation, square root, etc.).
     * @param x The operand (MX object).
     * @return MXWrapper
     */
    public MXWrapper unary(long op, MXWrapper x) {
        MX result = MX.unary(op, x.getCasADiObject());
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
    public MXWrapper inf(Sparsity sp) {
        MX result = MX.inf(sp);
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
    public MXWrapper inf(long nrow, long ncol) {
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
    public MXWrapper inf(long nrow) {
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
    public MXWrapper inf() {
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
    public MXWrapper nan(Sparsity sp) {
        MX result = MX.nan(sp);
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
    public MXWrapper nan(long nrow, long ncol) {
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
    public MXWrapper nan(long nrow) {
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
    public MXWrapper nan() {
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
    public MXWrapper eye(long size) {
        MX result = MX.eye(size);
        return new MXWrapper(result);
    }

    // ----- Get a submatrix, single argument -----
    /**
     * Get a submatrix using a single argument, of type Slice.
     *
     * This method extracts a submatrix from the current matrix using the given slice
     * along the row index. The result is stored in the provided OUTPUT matrix.
     *
     * @param OUTPUT The matrix to store the result.
     * @param ind1 A boolean indicating whether the indices are inclusive or not.
     * @param rr The slice used to extract the submatrix along the row dimension.
     */
    public void get(MXWrapper OUTPUT, boolean ind1, Slice rr) {
        this.mx.get(OUTPUT.mx, ind1, rr);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, IM rr) {
        this.mx.get(OUTPUT.mx, ind1, rr);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, Sparsity rr) {
        this.mx.get(OUTPUT.mx, ind1, rr);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, MXWrapper rr) {
        this.mx.get(OUTPUT.mx, ind1, rr.mx);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, long rr) {
        this.mx.get(OUTPUT.mx, ind1, rr);
    }

    // ----- Get a submatrix, two arguments
    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, Slice rr, Slice cc) {
        this.mx.get(OUTPUT.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, Slice rr, IM cc) {
        this.mx.get(OUTPUT.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, Slice rr, long cc) {
        this.mx.get(OUTPUT.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, IM rr, Slice cc) {
        this.mx.get(OUTPUT.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, long rr, Slice cc) {
        this.mx.get(OUTPUT.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, IM rr, IM cc) {
        this.mx.get(OUTPUT.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, long rr, long cc) {
        this.mx.get(OUTPUT.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, MXWrapper rr, Slice cc) {
        this.mx.get(OUTPUT.mx, ind1, rr.mx, cc);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, Slice rr, MXWrapper cc) {
        this.mx.get(OUTPUT.mx, ind1,rr, cc.mx);
    }

    // TODO: Add Java doc
    public void get(MXWrapper OUTPUT, boolean ind1, MXWrapper rr, MXWrapper cc) {
        this.mx.get(OUTPUT.mx, ind1, rr.mx, cc.mx);
    }

    // ----- Set a submatrix, single argument -----
    // TODO: Add Java doc
    public void set(MXWrapper m, boolean ind1, Slice rr) {
        this.mx.set(m.mx, ind1, rr);
    }

    // TODO: Add Java doc
    public void set(MXWrapper m, boolean ind1, IM rr) {
        this.mx.set(m.mx, ind1, rr);
    }

    // TODO: Add Java doc
    public void set(MXWrapper m, boolean ind1, Sparsity sp) {
        this.mx.set(m.mx, ind1, sp);
    }

    // ----- Set a submatrix, two arguments -----
    // TODO: Add Java doc
    public void set(MXWrapper m, boolean ind1, Slice rr, Slice cc) {
        this.mx.set(m.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void set(MXWrapper m, boolean ind1, Slice rr, IM cc) {
        this.mx.set(m.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void set(MXWrapper m, boolean ind1, IM rr, Slice cc) {
        this.mx.set(m.mx, ind1, rr, cc);
    }

    // TODO: Add Java doc
    public void set(MXWrapper m, boolean ind1, IM rr, IM cc) {
        this.mx.set(m.mx, ind1, rr, cc);
    }

    // ----- Get a set of nonzeros -----
    // TODO: Add Java doc
    public void getNZ(MXWrapper OUTPUT, boolean ind1, Slice kk) {
        this.mx.get_nz(OUTPUT.mx, ind1, kk);
    }

    // TODO: Add Java doc
    public void getNZ(MXWrapper OUTPUT, boolean ind1, IM kk) {
        this.mx.get_nz(OUTPUT.mx, ind1, kk);
    }

    // TODO: Add Java doc
    public void getNZ(MXWrapper OUTPUT, boolean ind1, MXWrapper kk) {
        this.mx.get_nz(OUTPUT.mx, ind1, kk.mx);
    }

    // TODO: Add Java doc
    public void getNZ(MXWrapper OUTPUT, boolean ind1, long kk) {
        this.mx.get_nz(OUTPUT.mx, ind1, kk);
    }

    // TODO: Add Java doc
    public void getNZ(MXWrapper OUTPUT, boolean ind1, MXWrapper inner, Slice outer) {
        this.mx.get_nz(OUTPUT.mx, ind1, inner.mx, outer);
    }

    // TODO: Add Java doc
    public void getNZ(MXWrapper OUTPUT, boolean ind1, Slice inner, MXWrapper outer) {
        this.mx.get_nz(OUTPUT.mx, ind1, inner, outer.mx);
    }

    // TODO: Add Java doc
    public void getNZ(MXWrapper OUTPUT, boolean ind1, MXWrapper inner, MXWrapper outer) {
        this.mx.get_nz(OUTPUT.mx, ind1, inner.mx, outer.mx);
    }

    // ----- Set a set of nonzeors -----
    // TODO: Add Java doc
    public void setNZ(MXWrapper m, boolean ind1, Slice kk) {
        this.mx.set_nz(m.mx, ind1, kk);
    }

    // TODO: Add Java doc
    public void setNZ(MXWrapper m, boolean ind1, IM kk) {
        this.mx.set_nz(m.mx, ind1, kk);
    }

    // TODO: Add Java doc
    public void setNZ(MXWrapper m, boolean ind1, MXWrapper kk) {
        this.mx.set_nz(m.mx, ind1, kk.mx);
    }

    // TODO: Add Java doc
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
     * @param C The resulting MXWrapper.
     * @param dim_a Dimensions of this MX object.
     * @param dim_b Dimensions of the other MX object.
     * @param dim_c Dimensions of the resulting MX object.
     * @param a Einstein notation for this MX object.
     * @param b Einstein notation for the other MX object.
     * @param c Einstein notation for the resulting MX object.
     * @return MXWrapper. A new MXWrapper containing the result of the contraction.
     */
    public MXWrapper einstein(MXWrapper other, MXWrapper C,
                              StdVectorCasadiInt dim_a, StdVectorCasadiInt dim_b,
                              StdVectorCasadiInt dim_c, StdVectorCasadiInt a,
                              StdVectorCasadiInt b, StdVectorCasadiInt c) {
        return new MXWrapper(MX.einstein(this.mx, other.mx, C.mx, dim_a, dim_b, dim_c, a, b, c));
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
    public MXWrapper einstein(MXWrapper other,
                              StdVectorCasadiInt dim_a, StdVectorCasadiInt dim_b,
                              StdVectorCasadiInt dim_c, StdVectorCasadiInt a,
                              StdVectorCasadiInt b, StdVectorCasadiInt c) {
        return new MXWrapper(MX.einstein(this.mx, other.mx, dim_a, dim_b, dim_c, a, b, c));
    }

    /**
     * Checks if this MX object is equal to another MX object up to a specified depth.
     *
     * @param other The MXWrapper to compare with.
     * @param depth The depth to which the comparison should be performed.
     * @return boolean. true if the two MX objects are equal, false otherwise.
     */
    public boolean isEqual(MXWrapper other, long depth) {
        return MX.is_equal(this.mx, other.mx, depth);
    }

    /**
     * Checks if this MX object is equal to another MX object.
     *
     * @param other The MXWrapper to compare with.
     * @return boolean. true if the two MX objects are equal, false otherwise.
     */
    public boolean isEqual(MXWrapper other) {
        return MX.is_equal(this.mx, other.mx);
    }

    /**
     * Computes the minimum value of this MX object.
     *
     * @return MXWrapper. A new MXWrapper containing the minimum value of the MX object.
     */
    public MXWrapper mmin() {
        return new MXWrapper(MX.mmin(this.mx));
    }

    /**
     * Computes the maximum value of this MX object.
     *
     * @return MXWrapper. A new MXWrapper containing the maximum value of the MX object.
     */
    public MXWrapper mmax() {
        return new MXWrapper(MX.mmax(this.mx));
    }

    /**
     * Horizontally concatenates this MX object with a vector of MX objects.
     *
     * @param mxVector The vector of MXWrapper objects to concatenate with.
     * @return MXWrapper. A new MXWrapper containing the result of the horizontal concatenation.
     */
    public MXWrapper horzcat(StdVectorMX mxVector) {
        return new MXWrapper(MX.horzcat(mxVector));
    }

    /**
     * Creates a diagonal concatenation of a vector of MX objects.
     *
     * @param mxVector The vector of MXWrapper objects to concatenate diagonally.
     * @return MXWrapper. A new MXWrapper containing the result of the diagonal concatenation.
     */
    public MXWrapper diagcat(StdVectorMX mxVector) {
        return new MXWrapper(MX.diagcat(mxVector));
    }

    /**
     * Vertically concatenates this MX object with a vector of MX objects.
     *
     * @param mxVector The vector of MXWrapper objects to concatenate with.
     * @return MXWrapper. A new MXWrapper containing the result of the vertical concatenation.
     */
    public MXWrapper vertcat(StdVectorMX mxVector) {
        return new MXWrapper(MX.vertcat(mxVector));
    }

    /**
     * Horizontally splits this MX object at the specified offset.
     *
     * @param offset The offset at which to split the MX object.
     * @return StdVectorMX. A vector of MXWrapper objects resulting from the horizontal split.
     */
    public StdVectorMX horzsplit(StdVectorCasadiInt offset) {
        return new StdVectorMX(MX.horzsplit(this.mx, offset));
    }

    /**
     * Diagonally splits this MX object at the specified offsets.
     *
     * @param offset1 The first offset for the diagonal split.
     * @param offset2 The second offset for the diagonal split.
     * @return StdVectorMX. A vector of MXWrapper objects resulting from the diagonal split.
     */
    public StdVectorMX diagsplit(StdVectorCasadiInt offset1, StdVectorCasadiInt offset2) {
        return new StdVectorMX(MX.diagsplit(this.mx, offset1, offset2));
    }

    /**
     * Vertically splits this MX object at the specified offset.
     *
     * @param offset The offset at which to split the MX object.
     * @return StdVectorMX. A vector of MXWrapper objects resulting from the vertical split.
     */
    public StdVectorMX vertsplit(StdVectorCasadiInt offset) {
        return new StdVectorMX(MX.vertsplit(this.mx, offset));
    }

    /**
     * Block concatenates a vector of vectors of MX objects.
     *
     * @param mxVector The vector of vectors of MXWrapper objects to concatenate.
     * @return MXWrapper. A new MXWrapper containing the result of the block concatenation.
     */
    public MXWrapper blockcat(StdVectorVectorMX mxVector) {
        return new MXWrapper(MX.blockcat(mxVector));
    }

    /**
     * Multiplies this MX object with another MX object.
     *
     * @param other The MXWrapper to multiply with.
     * @return MXWrapper. A new MXWrapper containing the result of the multiplication.
     */
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
    public MXWrapper reshape(long nrow, long ncol) {
        return new MXWrapper(MX.reshape(this.mx, nrow, ncol));
    }

    /**
     * Reshapes this MX object according to the specified sparsity pattern.
     *
     * @param sp The Sparsity object defining the new sparsity pattern.
     * @return MXWrapper. A new MXWrapper containing the reshaped MX object.
     */
    public MXWrapper reshape(Sparsity sp) {
        return new MXWrapper(MX.reshape(this.mx, sp));
    }

    /**
     * Casts this MX object to a new sparsity pattern.
     *
     * @param sp The Sparsity object defining the new sparsity pattern.
     * @return MXWrapper. A new MXWrapper containing the sparsity-cast MX object.
     */
    public MXWrapper sparsityCast(Sparsity sp) {
        return new MXWrapper(MX.sparsity_cast(this.mx, sp));
    }

    /**
     * Computes the Kronecker product of this MX object with another MX object.
     *
     * @param b The MXWrapper to compute the Kronecker product with.
     * @return MXWrapper. A new MXWrapper containing the result of the Kronecker product.
     */
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
    public MXWrapper jacobian(MXWrapper x, Dict opts) {
        return new MXWrapper(MX.jacobian(this.mx, x.mx, opts));
    }

    /**
     * Computes the Jacobian matrix of this MX object with respect to a variable.
     *
     * @param x The MXWrapper representing the variable with respect to which to compute the Jacobian.
     * @return MXWrapper. A new MXWrapper containing the Jacobian matrix.
     */
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
    public MXWrapper hessian(MXWrapper x, Dict opts) {
        return new MXWrapper(MX.hessian(this.mx, x.mx, opts));
    }

    /**
     * Computes the Hessian matrix of this MX object with respect to a variable.
     *
     * @param x The MXWrapper representing the variable with respect to which to compute the Hessian.
     * @return MXWrapper. A new MXWrapper containing the Hessian matrix.
     */
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
    public MXWrapper hessian(MXWrapper x, MXWrapper g, Dict opts) {
        return new MXWrapper(MX.hessian(this.mx, x.mx, g.mx, opts));
    }

    /**
     * Computes the Hessian matrix of this MX object with respect to two variables.
     *
     * @param x The MXWrapper representing the first variable.
     * @param g The MXWrapper representing the second variable.
     * @return MXWrapper. A new MXWrapper containing the Hessian matrix.
     */
    public MXWrapper hessian(MXWrapper x, MXWrapper g) {
        return new MXWrapper(MX.hessian(this.mx, x.mx, g.mx));
    }

    /**
     * Computes the forward mode of automatic differentiation for the given expression.
     *
     * @param ex The StdVectorMX representing the expression to differentiate.
     * @param arg The StdVectorMX representing the arguments of the expression.
     * @param v The StdVectorVectorMX representing the values for the forward mode.
     * @param opts Options for the forward computation.
     * @return StdVectorVectorMX. A new StdVectorVectorMX containing the result of the forward mode.
     */
    public StdVectorVectorMX forward(StdVectorMX ex, StdVectorMX arg, StdVectorVectorMX v, Dict opts) {
        return new StdVectorVectorMX(MX.forward(ex, arg, v, opts));
    }

    /**
     * Computes the forward mode of automatic differentiation for the given expression.
     *
     * @param ex The StdVectorMX representing the expression to differentiate.
     * @param arg The StdVectorMX representing the arguments of the expression.
     * @param v The StdVectorVectorMX representing the values for the forward mode.
     * @return StdVectorVectorMX. A new StdVectorVectorMX containing the result of the forward mode.
     */
    public StdVectorVectorMX forward(StdVectorMX ex, StdVectorMX arg, StdVectorVectorMX v) {
        return new StdVectorVectorMX(MX.forward(ex, arg, v));
    }

    /**
     * Computes the reverse mode of automatic differentiation for the given expression.
     *
     * @param ex The StdVectorMX representing the expression to differentiate.
     * @param arg The StdVectorMX representing the arguments of the expression.
     * @param v The StdVectorVectorMX representing the values for the reverse mode.
     * @param opts Options for the reverse computation.
     * @return StdVectorVectorMX. A new StdVectorVectorMX containing the result of the reverse mode.
     */
    public StdVectorVectorMX reverse(StdVectorMX ex, StdVectorMX arg, StdVectorVectorMX v, Dict opts) {
        return new StdVectorVectorMX(MX.reverse(ex, arg, v, opts));
    }

    /**
     * Computes the reverse mode of automatic differentiation for the given expression.
     *
     * @param ex The StdVectorMX representing the expression to differentiate.
     * @param arg The StdVectorMX representing the arguments of the expression.
     * @param v The StdVectorVectorMX representing the values for the reverse mode.
     * @return StdVectorVectorMX. A new StdVectorVectorMX containing the result of the reverse mode.
     */
    public StdVectorVectorMX reverse(StdVectorMX ex, StdVectorMX arg, StdVectorVectorMX v) {
        return new StdVectorVectorMX(MX.reverse(ex, arg, v));
    }

    /**
     * Determines which variables depend on the given expression.
     *
     * @param var The MXWrapper representing the variable.
     * @param order The order of dependency to check.
     * @param tr A boolean flag for additional options.
     * @return StdVectorBool. A new StdVectorBool containing the dependency information.
     */
    public StdVectorBool whichDepends(MXWrapper var, long order, boolean tr) {
        return new StdVectorBool(MX.which_depends(this.mx, var.mx, order, tr));
    }

    /**
     * Determines which variables depend on the given expression.
     *
     * @param var The MXWrapper representing the variable.
     * @param order The order of dependency to check.
     * @return StdVectorBool. A new StdVectorBool containing the dependency information.
     */
    public StdVectorBool whichDepends(MXWrapper var, long order) {
        return new StdVectorBool(MX.which_depends(this.mx, var.mx, order));
    }

    /**
     * Determines which variables depend on the given expression.
     *
     * @param var The MXWrapper representing the variable.
     * @return StdVectorBool. A new StdVectorBool containing the dependency information.
     */
    public StdVectorBool whichDepends(MXWrapper var) {
        return new StdVectorBool(MX.which_depends(this.mx, var.mx));
    }

    /**
     * Computes the sparsity pattern of the Jacobian matrix of this MX object with respect to a variable.
     *
     * @param x The MXWrapper representing the variable with respect to which to compute the Jacobian sparsity.
     * @return Sparsity. A new Sparsity object containing the sparsity pattern of the Jacobian matrix.
     */
    public Sparsity jacobianSparsity(MXWrapper x) {
        return new Sparsity(MX.jacobian_sparsity(this.mx, x.mx));
    }

    /**
     * Substitutes a variable in this MX object with a given value.
     *
     * @param v The MXWrapper representing the variable to substitute.
     * @param vdef The MXWrapper representing the value to substitute in.
     * @return MXWrapper. A new MXWrapper containing the result of the substitution.
     */
    public MXWrapper substitute(MXWrapper v, MXWrapper vdef) {
        return new MXWrapper(MX.substitute(this.mx, v.mx, vdef.mx));
    }

    /**
     * Substitutes a vector of variables in a vector of expressions with given values.
     *
     * @param ex The StdVectorMX representing the expressions to substitute in.
     * @param v The StdVectorMX representing the variables to substitute.
     * @param vdef The StdVectorMX representing the values to substitute in.
     * @return StdVectorMX. A new StdVectorMX containing the results of the substitutions.
     */
    public static StdVectorMX substitute(StdVectorMX ex, StdVectorMX v, StdVectorMX vdef) {
        return new StdVectorMX(MX.substitute(ex, v, vdef));
    }

    /**
     * Substitutes variables in the given expressions in place with the specified values.
     *
     * This method modifies the expressions directly by substituting the variables
     * with the provided values. The substitution can be performed in reverse order
     * if specified.
     *
     * @param variables The StdVectorMX representing the variables to substitute.
     * @param values The StdVectorMX representing the values to substitute in.
     * @param expressions The StdVectorMX representing the expressions to modify.
     * @param reverse A boolean indicating whether to perform the substitution in reverse order.
     */
    public static void substituteInPlace(StdVectorMX variables, StdVectorMX values, StdVectorMX expressions, boolean reverse) {
        MX.substitute_inplace(variables, values, expressions, reverse);
    }

    /**
     * Solves the linear equation system Ax = b.
     *
     * This method solves the equation system represented by the matrix A (this) and the vector b.
     *
     * @param b The MXWrapper representing the right-hand side vector b.
     * @return MXWrapper. A new MXWrapper containing the solution vector x.
     */
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
    public MXWrapper solve(MXWrapper b, String lsolver, Dict dict) {
        MX result = MX.solve(this.mx, b.mx, lsolver, dict);
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
    public MXWrapper inv(String lsolver, Dict dict) {
        MX result = MX.inv(this.mx, lsolver, dict);
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
    public MXWrapper pinv(String lsolver, Dict dict) {
        MX result = MX.pinv(this.mx, lsolver, dict);
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
    public MXWrapper expmConst(MXWrapper t) {
        MX result = MX.expm_const(this.mx, t.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the matrix exponential of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the matrix exponential of A.
     */
    public MXWrapper expm() {
        MX result = MX.expm(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Returns the number of nodes in the expression represented by this MXWrapper.
     *
     * @return long. The number of nodes in the expression.
     */
    public long nNodes() {
        return MX.n_nodes(this.mx);
    }

    /**
     * Prints the operator represented by this MXWrapper.
     *
     * @param args The StdVectorStdString containing additional arguments for printing.
     * @return String. The string representation of the operator.
     */
    public String printOperator(StdVectorStdString args) {
        return MX.print_operator(this.mx, args);
    }

    /**
     * Extracts values from the expressions represented by the given vectors.
     *
     * This method uses the provided options dictionary for extraction.
     *
     * @param ex The StdVectorMX representing the expressions to extract from.
     * @param v The StdVectorMX representing the variables.
     * @param vdef The StdVectorMX representing the values to substitute.
     * @param opts The dictionary containing options for extraction.
     */
    public static void extract(StdVectorMX ex, StdVectorMX v, StdVectorMX vdef, Dict opts) {
        MX.extract(ex, v, vdef, opts);
    }

    /**
     * Extracts values from the expressions represented by the given vectors.
     *
     * This method does not use an options dictionary for extraction.
     *
     * @param ex The StdVectorMX representing the expressions to extract from.
     * @param v The StdVectorMX representing the variables.
     * @param vdef The StdVectorMX representing the values to substitute.
     */
    public static void extract(StdVectorMX ex, StdVectorMX v, StdVectorMX vdef) {
        MX.extract(ex, v, vdef);
    }

    /**
     * Shares variables in the context of the given expressions.
     *
     * @param ex The StdVectorMX representing the expressions.
     * @param v The StdVectorMX representing the variables.
     * @param vdef The StdVectorMX representing the default values.
     * @param v_prefix The prefix for variable names.
     * @param v_suffix The suffix for variable names.
     */
    public static void shared(StdVectorMX ex, StdVectorMX v, StdVectorMX vdef, String v_prefix, String v_suffix) {
        MX.shared(ex, v, vdef, v_prefix, v_suffix);
    }

    // TODO: Should this be static?
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
        MX result = MX.if_else(cond.mx, if_true.mx, if_false.mx, short_circuit);
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
        MX result = MX.if_else(cond.mx, if_true.mx, if_false.mx);
        return new MXWrapper(result);
    }

    /**
     * Evaluates a conditional expression based on the provided index.
     *
     * @param ind The MXWrapper representing the index.
     * @param x The StdVectorMX representing the expressions.
     * @param x_default The MXWrapper representing the default expression.
     * @param short_circuit A boolean indicating whether to use short-circuit evaluation.
     * @return MXWrapper. A new MXWrapper containing the result of the conditional expression.
     */
    public static MXWrapper conditional(MXWrapper ind, StdVectorMX x, MXWrapper x_default, boolean short_circuit) {
        MX result = MX.conditional(ind.mx, x, x_default.mx, short_circuit);
        return new MXWrapper(result);
    }

    /**
     * Evaluates a conditional expression based on the provided index.
     *
     * @param ind The MXWrapper representing the index.
     * @param x The StdVectorMX representing the expressions.
     * @param x_default The MXWrapper representing the default expression.
     * @return MXWrapper. A new MXWrapper containing the result of the conditional expression.
     */
    public static MXWrapper conditional(MXWrapper ind, StdVectorMX x, MXWrapper x_default) {
        MX result = MX.conditional(ind.mx, x, x_default.mx);
        return new MXWrapper(result);
    }
    // End

    /**
     * Checks if the expression represented by this MXWrapper depends on the given argument.
     *
     * @param arg The MXWrapper representing the argument.
     * @return boolean. True if this expression depends on the argument, false otherwise.
     */
    public boolean dependsOn(MXWrapper arg) {
        return MX.depends_on(this.mx, arg.mx);
    }

    /**
     * Simplifies the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the simplified expression.
     */
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
    public MXWrapper mldivide(MXWrapper b) {
        MX result = MX.mldivide(this.mx, b.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the 2-norm of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the 2-norm of the expression.
     */
    public MXWrapper norm2() {
        MX result = MX.norm_2(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the Frobenius norm of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the Frobenius norm of the expression.
     */
    public MXWrapper normFro() {
        MX result = MX.norm_fro(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the 1-norm of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the 1-norm of the expression.
     */
    public MXWrapper norm1() {
        MX result = MX.norm_1(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the infinity norm of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the infinity norm of the expression.
     */
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
    public MXWrapper unite(MXWrapper B) {
        MX result = MX.unite(this.mx, B.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the trace of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the trace of the expression.
     */
    public MXWrapper trace() {
        MX result = MX.trace(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the diagonal of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the diagonal of the expression.
     */
    public MXWrapper diag() {
        MX result = MX.diag(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the sum of all elements in the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the sum of the elements.
     */
    public MXWrapper sum2() {
        MX result = MX.sum2(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the sum of elements along the first dimension of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the sum of the elements along the first dimension.
     */
    public MXWrapper sum1() {
        MX result = MX.sum1(this.mx);
        return new MXWrapper(result);
    }

    // TODO: Should this be static?
    /**
     * Evaluates a polynomial at the given point.
     *
     * @param p The MXWrapper representing the polynomial coefficients.
     * @param x The MXWrapper representing the point at which to evaluate the polynomial.
     * @return MXWrapper. A new MXWrapper containing the result of the polynomial evaluation.
     */
    public static MXWrapper polyval(MXWrapper p, MXWrapper x) {
        MX result = MX.polyval(p.mx, x.mx);
        return new MXWrapper(result);
    }
    // End

    /**
     * Computes the determinant of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the determinant of the expression.
     */
    public MXWrapper det() {
        MX result = MX.det(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Returns the symbolic variables in the expression represented by this MXWrapper.
     *
     * @return StdVectorMX. A new StdVectorMX containing the symbolic variables.
     */
    public StdVectorMX symvar() {
        return MX.symvar(this.mx);
    }

    /**
     * Computes the null space of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the null space of the matrix.
     */
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
    public MXWrapper densify(MXWrapper val) {
        MX result = MX.densify(this.mx, val.mx);
        return new MXWrapper(result);
    }

    /**
     * Densifies the sparse matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the densified matrix.
     */
    public MXWrapper densify() {
        MX result = MX.densify(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the bilinear form of the given matrices.
     *
     * @param x The MXWrapper representing the second matrix.
     * @param y The MXWrapper representing the third matrix.
     * @return MXWrapper. A new MXWrapper containing the result of the bilinear form.
     */
    public MXWrapper _bilin(MXWrapper x, MXWrapper y) {
        MX result = MX._bilin(this.mx, x.mx, y.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the rank-1 update of the given matrices.
     *
     * @param alpha The MXWrapper representing the scalar multiplier.
     * @param x The MXWrapper representing the first matrix.
     * @param y The MXWrapper representing the second matrix.
     * @return MXWrapper. A new MXWrapper containing the result of the rank-1 update.
     */
    public MXWrapper _rank1(MXWrapper alpha, MXWrapper x, MXWrapper y) {
        MX result = MX._rank1(this.mx, alpha.mx, x.mx, y.mx);
        return new MXWrapper(result);
    }

    /**
     * Projects the expression represented by this MXWrapper onto the given sparsity structure.
     *
     * @param sp The Sparsity structure to project onto.
     * @param intersect A boolean indicating whether to intersect the sparsity.
     * @return MXWrapper. A new MXWrapper containing the projected expression.
     */
    public MXWrapper project(Sparsity sp, boolean intersect) {
        MX result = MX.project(this.mx, sp, intersect);
        return new MXWrapper(result);
    }

    /**
     * Projects the expression represented by this MXWrapper onto the given sparsity structure.
     *
     * @param sp The Sparsity structure to project onto.
     * @return MXWrapper. A new MXWrapper containing the projected expression.
     */
    public MXWrapper project(Sparsity sp) {
        MX result = MX.project(this.mx, sp);
        return new MXWrapper(result);
    }

    /**
     * Computes the cumulative sum of the elements in the expression represented by this MXWrapper along the specified axis.
     *
     * @param axis The axis along which to compute the cumulative sum.
     * @return MXWrapper. A new MXWrapper containing the cumulative sum.
     */
    public MXWrapper cumsum(long axis) {
        MX result = MX.cumsum(this.mx, axis);
        return new MXWrapper(result);
    }

    /**
     * Computes the cumulative sum of the elements in the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the cumulative sum.
     */
    public MXWrapper cumsum() {
        MX result = MX.cumsum(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Computes the log-sum-exponential of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the result of the log-sum-exponential.
     */
    public MXWrapper _logsumexp() {
        MX result = MX._logsumexp(this.mx);
        return new MXWrapper(result);
    }

    /**
     * Performs common subexpression elimination on the given expressions.
     *
     * @param e The StdVectorMX representing the expressions.
     * @return StdVectorMX. A new StdVectorMX containing the optimized expressions.
     */
    public static StdVectorMX cse(StdVectorMX e) {
        return MX.cse(e);
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
    public MXWrapper low(MXWrapper p, Dict options) {
        MX result = MX.low(this.mx, p.mx, options);
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
     * @param v The StdVectorMX representing the variables.
     * @param vdef The StdVectorMX representing the default values.
     * @return MXWrapper. A new MXWrapper containing the substituted expression.
     */
    public MXWrapper graphSubstitute(StdVectorMX v, StdVectorMX vdef) {
        MX result = MX.graph_substitute(this.mx, v, vdef);
        return new MXWrapper(result);
    }

    /**
     * Substitutes expressions in the graph.
     *
     * @param ex The StdVectorMX representing the expressions to substitute.
     * @param expr The StdVectorMX representing the expressions.
     * @param exprs The StdVectorMX representing the expressions to substitute with.
     * @return StdVectorMX. A new StdVectorMX containing the substituted expressions.
     */
    public static StdVectorMX graphSubstitute(StdVectorMX ex, StdVectorMX expr, StdVectorMX exprs) {
        return MX.graph_substitute(ex, expr, exprs);
    }

    /**
     * Expands the given matrix expression with boundary conditions and options.
     *
     * @param boundary The StdVectorMX representing the boundary conditions.
     * @param options The dictionary containing options for the expansion.
     * @return MXWrapper. A new MXWrapper containing the expanded expression.
     */
    public MXWrapper matrixExpand(StdVectorMX boundary, Dict options) {
        MX result = MX.matrix_expand(this.mx, boundary, options);
        return new MXWrapper(result);
    }

    /**
     * Expands the given vector expression with specified boundary conditions and options.
     *
     * @param e The StdVectorMX representing the vector expression to expand.
     * @param boundary The StdVectorMX representing the boundary conditions.
     * @param options The dictionary containing options for the expansion.
     * @return StdVectorMX. A new StdVectorMX containing the expanded expressions.
     */
    public StdVectorMX matrixExpand(StdVectorMX e, StdVectorMX boundary, Dict options) {
        return MX.matrix_expand(e, boundary, options);
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
    public DMWrapper evalf() {
        return new DMWrapper(MX.evalf(this.mx));
    }

    /**
     * Computes the B-spline of the given expression with specified coefficients and knots.
     *
     * @param coeffs The DM representing the coefficients.
     * @param knots The StdVectorVectorDouble representing the knots.
     * @param degree The StdVectorCasadiInt representing the degree.
     * @param m The number of repetitions.
     * @param opts The dictionary containing options for the B-spline.
     * @return MXWrapper. A new MXWrapper containing the B-spline result.
     */
    public MXWrapper bspline(DM coeffs, StdVectorVectorDouble knots, StdVectorCasadiInt degree, long m, Dict opts) {
        MX result = MX.bspline(this.mx, coeffs, knots, degree, m, opts);
        return new MXWrapper(result);
    }

    /**
     * Computes the B-spline of the given expression with specified coefficients and knots.
     *
     * @param coeffs The DM representing the coefficients.
     * @param knots The StdVectorVectorDouble representing the knots.
     * @param degree The StdVectorCasadiInt representing the degree.
     * @param m The number of repetitions.
     * @return MXWrapper. A new MXWrapper containing the B-spline result.
     */
    public MXWrapper bspline(DM coeffs, StdVectorVectorDouble knots, StdVectorCasadiInt degree, long m) {
        MX result = MX.bspline(this.mx, coeffs, knots, degree, m);
        return new MXWrapper(result);
    }

    /**
     * Computes the B-spline of the given expression with specified coefficients and knots.
     *
     * @param coeffs The MXWrapper representing the coefficients.
     * @param knots The StdVectorVectorDouble representing the knots.
     * @param degree The StdVectorCasadiInt representing the degree.
     * @param m The number of repetitions.
     * @param opts The dictionary containing options for the B-spline.
     * @return MXWrapper. A new MXWrapper containing the B-spline result.
     */
    public MXWrapper bspline(MXWrapper coeffs, StdVectorVectorDouble knots, StdVectorCasadiInt degree, long m, Dict opts) {
        MX result = MX.bspline(this.mx, coeffs.mx, knots, degree, m, opts);
        return new MXWrapper(result);
    }

    /**
     * Computes the B-spline of the given expression with specified coefficients and knots.
     *
     * @param coeffs The MXWrapper representing the coefficients.
     * @param knots The StdVectorVectorDouble representing the knots.
     * @param degree The StdVectorCasadiInt representing the degree.
     * @param m The number of repetitions.
     * @return MXWrapper. A new MXWrapper containing the B-spline result.
     */
    public MXWrapper bspline(MXWrapper coeffs, StdVectorVectorDouble knots, StdVectorCasadiInt degree, long m) {
        MX result = MX.bspline(this.mx, coeffs.mx, knots, degree, m);
        return new MXWrapper(result);
    }

    /**
     * Convexifies the given expression using the specified options.
     *
     * @param opts The dictionary containing options for the convexification.
     * @return MXWrapper. A new MXWrapper containing the convexified expression.
     */
    public MXWrapper convexify(Dict opts) {
        MX result = MX.convexify(this.mx, opts);
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
     * Computes the dual B-spline for the given input and specified knots, degree, and options.
     *
     * @param x The StdVectorDouble representing the input values.
     * @param knots The StdVectorVectorDouble representing the knots.
     * @param degree The StdVectorCasadiInt representing the degree of the B-spline.
     * @param opts The dictionary containing options for the B-spline computation.
     * @return DM. A new DM containing the dual B-spline result.
     */
    public static DM bsplineDual(StdVectorDouble x, StdVectorVectorDouble knots, StdVectorCasadiInt degree, Dict opts) {
        return MX.bspline_dual(x, knots, degree, opts);
    }

    /**
     * Computes the dual B-spline for the given input and specified knots and degree.
     *
     * @param x The StdVectorDouble representing the input values.
     * @param knots The StdVectorVectorDouble representing the knots.
     * @param degree The StdVectorCasadiInt representing the degree of the B-spline.
     * @return DM. A new DM containing the dual B-spline result.
     */
    public static DM bsplineDual(StdVectorDouble x, StdVectorVectorDouble knots, StdVectorCasadiInt degree) {
        return MX.bspline_dual(x, knots, degree);
    }

    /**
     * Performs low-level access to inlined linear interpolation.
     *
     * Usually, you want to be using 'interpolant' instead.
     * Accepts lookup_mode option.
     *
     * @param x The StdVectorMX representing the input values.
     * @param v The MXWrapper representing the values to interpolate.
     * @param xq The StdVectorMX representing the query points.
     * @param opts The dictionary containing options for the interpolation.
     * @return MXWrapper. A new MXWrapper containing the result of the interpolation.
     */
    public static MXWrapper interpnLinear(StdVectorMX x, MXWrapper v, StdVectorMX xq, Dict opts) {
        MX result = MX.interpn_linear(x, v.mx, xq, opts);
        return new MXWrapper(result);
    }

    /**
     * Performs low-level access to inlined linear interpolation.
     *
     * Usually, you want to be using 'interpolant' instead.
     *
     * @param x The StdVectorMX representing the input values.
     * @param v The MXWrapper representing the values to interpolate.
     * @param xq The StdVectorMX representing the query points.
     * @return MXWrapper. A new MXWrapper containing the result of the interpolation.
     */
    public static MXWrapper interpnLinear(StdVectorMX x, MXWrapper v, StdVectorMX xq) {
        MX result = MX.interpn_linear(x, v.mx, xq);
        return new MXWrapper(result);
    }

    /**
     * Prints the current MX expression represented by this MXWrapper alongside the provided MX expression.
     *
     * @param b The MXWrapper representing the expression to print alongside this one.
     * @return MXWrapper. A new MXWrapper containing the result of the print operation.
     */
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
    public void setMaxDepth(long eq_depth) {
        MX.set_max_depth(eq_depth);
    }

    /**
     * Sets or resets the depth to which equalities are being checked for simplifications
     * to the default value.
     */
    public void setMaxDepth() {
        MX.set_max_depth();
    }

    /**
     * Gets the depth to which equalities are being checked for simplifications.
     *
     * @return long. The current maximum depth for equality checks.
     */
    public long getMaxDepth() {
        return MX.get_max_depth();
    }

    /**
     * Gets the function inputs for the specified function.
     *
     * @param f The Function object for which to get the inputs.
     * @return StdVectorMX. A new StdVectorMX containing the function inputs.
     */
    public StdVectorMX getInput(Function f) {
        return MX.get_input(f);
    }

    /**
     * Gets the free variables for the specified function.
     *
     * @param f The Function object for which to get the free variables.
     * @return StdVectorMX. A new StdVectorMX containing the free variables.
     */
    public StdVectorMX getFree(Function f) {
        return MX.get_free(f);
    }

    /**
     * Evaluates the MX node with new symbolic dependencies.
     *
     * @param arg The StdVectorMX representing the new symbolic dependencies.
     * @param OUTPUT The StdVectorMX to store the output results.
     */
    public void evalMx(StdVectorMX arg, StdVectorMX OUTPUT) {
        this.mx.eval_mx(arg, OUTPUT);
    }

    /**
     * Called from MXFunction to perform forward automatic differentiation.
     *
     * @param fseed The StdVectorVectorMX representing the seed for forward mode.
     * @param fsens The StdVectorVectorMX to store the sensitivity results.
     */
    public void adForward(StdVectorVectorMX fseed, StdVectorVectorMX fsens) {
        this.mx.ad_forward(fseed, fsens);
    }

    /**
     * Called from MXFunction to perform reverse automatic differentiation.
     *
     * @param aseed The StdVectorVectorMX representing the seed for reverse mode.
     * @param asens The StdVectorVectorMX to store the sensitivity results.
     */
    public void adReverse(StdVectorVectorMX aseed, StdVectorVectorMX asens) {
        this.mx.ad_reverse(aseed, asens);
    }

    /**
     * Creates a symbolic variable with the given name and sparsity pattern.
     *
     * @param name The name of the symbolic variable.
     * @param sparsity The Sparsity object representing the sparsity pattern of the variable.
     * @return MXWrapper. A new MXWrapper containing the created symbolic variable.
     */
    public MXWrapper sym(String name, Sparsity sparsity) {
        return new MXWrapper(MX._sym(name, sparsity));
    }

    /**
     * Accesses a specific row of the matrix represented by this MXWrapper.
     *
     * @param rr The row index to access.
     * @return MxSubIndex. An object representing the specified row of the matrix.
     */
    public MxSubIndex at(int rr) {
        return this.mx.at(rr);
    }

    /**
     * Accesses a specific element in the matrix represented by this MXWrapper.
     *
     * @param rr The row index of the element.
     * @param cc The column index of the element.
     * @return MxSubMatrix. An object representing the specified element of the matrix.
     */
    public MxSubMatrix at(int rr, int cc) {
        return this.mx.at(rr, cc);
    }

    /**
     * Assigns the values from another MXWrapper to this MXWrapper.
     *
     * @param other The MXWrapper containing the values to assign.
     */
    public void assign(MXWrapper other) {
        this.mx.assign(other.mx);
    }

    /**
     * Splits the given matrix into blocks based on specified vertical and horizontal offsets.
     *
     * @param vert_offset The StdVectorCasadiInt representing vertical offsets.
     * @param horz_offset The StdVectorCasadiInt representing horizontal offsets.
     * @return StdVectorVectorMX. A new StdVectorVectorMX containing the split blocks.
     */
    public StdVectorVectorMX blocksplit(StdVectorCasadiInt vert_offset, StdVectorCasadiInt horz_offset) {
        return MX.blocksplit(this.mx, vert_offset, horz_offset);
    }

    /**
     * Splits the given matrix into blocks based on specified increments.
     *
     * @param vert_incr The vertical increment for splitting.
     * @param horz_incr The horizontal increment for splitting.
     * @return StdVectorVectorMX. A new StdVectorVectorMX containing the split blocks.
     */
    public StdVectorVectorMX blocksplit(long vert_incr, long horz_incr) {
        return MX.blocksplit(this.mx, vert_incr, horz_incr);
    }

    /**
     * Concatenates the given vector expressions into a single vector.
     *
     * @param x The StdVectorMX representing the vector to concatenate.
     * @return MXWrapper. A new MXWrapper containing the concatenated vector.
     */
    public MXWrapper veccat(StdVectorMX x) {
        return new MXWrapper(MX.veccat(x));
    }

    /**
     * Converts the given matrix expression into a vector.
     *
     * @return MXWrapper. A new MXWrapper containing the vector representation.
     */
    public MXWrapper vec() {
        return new MXWrapper(MX.vec(this.mx));
    }

    /**
     * Computes the offsets for the given vector based on the specified orientation.
     *
     * @param v The StdVectorMX representing the vector.
     * @param vert A boolean indicating whether to compute vertical offsets.
     * @return StdVectorCasadiInt. A new StdVectorCasadiInt containing the computed offsets.
     */
    public static StdVectorCasadiInt offset(StdVectorMX v, boolean vert) {
        return MX.offset(v, vert);
    }

    /**
     * Computes the offsets for the given vector.
     *
     * @param v The StdVectorMX representing the vector.
     * @return StdVectorCasadiInt. A new StdVectorCasadiInt containing the computed offsets.
     */
    public static StdVectorCasadiInt offset(StdVectorMX v) {
        return MX.offset(v);
    }

    /**
     * Vertically splits the given matrix into n parts.
     *
     * @param n The number of parts to split into.
     * @return StdVectorMX. A new StdVectorMX containing the split parts.
     */
    public StdVectorMX vertsplit_n(long n) {
        return MX.vertsplit_n(this.mx, n);
    }

    /**
     * Retrieves the class name of the MX object represented by this MXWrapper.
     *
     * @return String. The class name of the underlying MX object.
     */
    public String className() {
        return this.mx.class_name();
    }

    /**
     * Gets the string representation of the MX object represented by this MXWrapper.
     *
     * @param more A boolean indicating whether to include additional information.
     * @return String. The string representation of the MX object.
     */
    public String toString(boolean more) {
        return this.mx.toString(more);
    }

    /**
     * Gets the string representation of the MX object represented by this MXWrapper.
     *
     * @return String. The string representation of the MX object.
     */
    public String toString() {
        return this.mx.toString();
    }

    /**
     * Prints the pointer to the internal class of the MX object represented by this MXWrapper.
     */
    public void printPtr() {
        this.mx.print_ptr();
    }

    /**
     * Checks if the MX object represented by this MXWrapper is a null pointer.
     *
     * @return boolean. True if the object is a null pointer, false otherwise.
     */
    public boolean isNull() {
        return this.mx.is_null();
    }

    /**
     * Returns a unique number for the MX object represented by this MXWrapper.
     *
     * If the object does not point to any node, "0" is returned.
     *
     * @return long. The unique hash number for the MX object.
     */
    public long hash() {
        return this.mx.__hash__();
    }

    /**
     * Gets the number of (structural) non-zero elements in the matrix represented by this MXWrapper.
     *
     * @return long. The number of non-zero elements.
     */
    public long nnz() {
        return mx.nnz_();
    }

    /**
     * Gets the number of non-zeros in the lower triangular half of the matrix represented by this MXWrapper.
     *
     * @return long. The number of non-zero elements in the lower triangular half.
     */
    public long nnzLower() {
        return mx.nnz_lower_();
    }

    /**
     * Gets the number of non-zeros in the upper triangular half of the matrix represented by this MXWrapper.
     *
     * @return long. The number of non-zero elements in the upper triangular half.
     */
    public long nnzUpper() {
        return mx.nnz_upper_();
    }

    /**
     * Gets the number of non-zeros on the diagonal of the matrix represented by this MXWrapper.
     *
     * @return long. The number of non-zero elements on the diagonal.
     */
    public long nnzDiag() {
        return mx.nnz_diag();
    }

    /**
     * Gets the total number of elements in the matrix represented by this MXWrapper.
     *
     * @return long. The total number of elements.
     */
    public long numel() {
        return mx.numel_();
    }

    /**
     * Gets the first dimension (i.e., number of rows) of the matrix represented by this MXWrapper.
     *
     * @return long. The number of rows in the matrix.
     */
    public long size1() {
        return mx.size1_();
    }

    /**
     * Gets the number of rows in the matrix represented by this MXWrapper, using Octave-style syntax.
     *
     * @return long. The number of rows in the matrix.
     */
    public long rows() {
        return mx.rows();
    }

    /**
     * Gets the second dimension (i.e., number of columns) of the matrix represented by this MXWrapper.
     *
     * @return long. The number of columns in the matrix.
     */
    public long size2() {
        return mx.size2_();
    }

    /**
     * Gets the number of columns in the matrix represented by this MXWrapper, using Octave-style syntax.
     *
     * @return long. The number of columns in the matrix.
     */
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
    public String dim() {
        return this.mx.dim_();
    }

    /**
     * Gets the size along a particular dimension of the matrix represented by this MXWrapper.
     *
     * @param axis The dimension along which to get the size.
     * @return long. The size along the specified dimension.
     */
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
    public boolean isEmpty(boolean both) {
        return this.mx.is_empty_(both);
    }

    /**
     * Checks if the sparsity of the matrix represented by this MXWrapper is empty,
     * i.e., if one of the dimensions is zero (or optionally both dimensions).
     *
     * @return boolean. True if the sparsity is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.mx.is_empty_();
    }

    /**
     * Checks if the matrix expression represented by this MXWrapper is dense.
     *
     * @return boolean. True if the matrix is dense, false otherwise.
     */
    public boolean isDense() {
        return this.mx.is_dense_();
    }

    /**
     * Checks if the matrix expression represented by this MXWrapper is scalar.
     *
     * @param scalarAndDense A boolean indicating whether to check for both scalar and dense.
     * @return boolean. True if the matrix is scalar, false otherwise.
     */
    public boolean isScalar(boolean scalarAndDense) {
        return this.mx.is_scalar_(scalarAndDense);
    }

    /**
     * Checks if the matrix expression represented by this MXWrapper is scalar.
     *
     * @return boolean. True if the matrix is scalar, false otherwise.
     */
    public boolean isScalar() {
        return this.mx.is_scalar_();
    }

    /**
     * Checks if the matrix expression represented by this MXWrapper is square.
     *
     * @return boolean. True if the matrix is square, false otherwise.
     */
    public boolean isSquare() {
        return this.mx.is_square();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is a row or column vector.
     *
     * @return boolean. True if the matrix is a vector, false otherwise.
     */
    public boolean isVector() {
        return this.mx.is_vector_();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is a row vector (i.e., size1() == 1).
     *
     * @return boolean. True if the matrix is a row vector, false otherwise.
     */
    public boolean isRow() {
        return this.mx.is_row_();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is a column vector (i.e., size2() == 1).
     *
     * @return boolean. True if the matrix is a column vector, false otherwise.
     */
    public boolean isColumn() {
        return this.mx.is_column_();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is upper triangular.
     *
     * @return boolean. True if the matrix is upper triangular, false otherwise.
     */
    public boolean isTriu() {
        return this.mx.is_triu_();
    }

    /**
     * Checks if the matrix represented by this MXWrapper is lower triangular.
     *
     * @return boolean. True if the matrix is lower triangular, false otherwise.
     */
    public boolean isTril() {
        return this.mx.is_tril_();
    }

    /**
     * Gets the sparsity pattern of the matrix represented by this MXWrapper.
     * See the Sparsity class for details.
     *
     * @return StdVectorCasadiInt. A new StdVectorCasadiInt containing the row indices of the sparsity pattern.
     */
    public StdVectorCasadiInt getRow() {
        return new StdVectorCasadiInt(this.mx.get_row());
    }

    /**
     * Gets the column indices of the sparsity pattern of the matrix represented by this MXWrapper.
     *
     * @return StdVectorCasadiInt. A new StdVectorCasadiInt containing the column indices of the sparsity pattern.
     */
    public StdVectorCasadiInt getColInd() {
        return new StdVectorCasadiInt(this.mx.get_colind());
    }

    /**
     * Gets the row index of the specified element in the matrix represented by this MXWrapper.
     *
     * @param el The element index for which to retrieve the row.
     * @return long. The row index of the specified element.
     */
    public long row(long el) {
        return this.mx.row_(el);
    }

    /**
     * Gets the column index of the specified column in the matrix represented by this MXWrapper.
     *
     * @param col The column index for which to retrieve the column index.
     * @return long. The column index of the specified column.
     */
    public long colind(long col) {
        return this.mx.colind_(col);
    }

    /**
     * Performs linear interpolation for the given input and specified knots, degree, and options.
     *
     * @param x The StdVectorDouble representing the input values.
     * @param v The MXWrapper representing the values to interpolate.
     * @param xq The StdVectorDouble representing the query points.
     * @param mode The mode of interpolation.
     * @param equidistant A boolean indicating whether the input values are equidistant.
     * @return MXWrapper. A new MXWrapper containing the result of the interpolation.
     */
    public MXWrapper interp1d(StdVectorDouble x, MXWrapper v, StdVectorDouble xq, String mode, boolean equidistant) {
        return new MXWrapper(MX.interp1d(x, v.mx, xq, mode, equidistant));
    }

    /**
     * Gets the rank of the sparse matrix represented by this MXWrapper.
     *
     * @return long. The rank of the matrix.
     */
    public long sprank() {
        return MX.sprank(this.mx);
    }

    /**
     * Computes the 0-norm of the product of two matrices.
     *
     * @param y The MXWrapper representing the second matrix.
     * @return long. The 0-norm of the product.
     */
    public long norm0Mul(MXWrapper y) {
        return MX.norm_0_mul(this.mx, y.mx);
    }

    /**
     * Gets the lower triangular part of the matrix represented by this MXWrapper.
     *
     * @param includeDiagonal A boolean indicating whether to include the diagonal.
     * @return MXWrapper. A new MXWrapper containing the lower triangular part.
     */
    public MXWrapper tril(boolean includeDiagonal) {
        return new MXWrapper(MX.tril(this.mx, includeDiagonal));
    }

    /**
     * Gets the lower triangular part of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the lower triangular part.
     */
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
    public MXWrapper triu() {
        return new MXWrapper(MX.triu(this.mx));
    }

    /**
     * Computes the sum of squares of the elements in the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the sum of squares.
     */
    public MXWrapper sumsqr() {
        return new MXWrapper(MX.sumsqr(this.mx));
    }

    /**
     * Creates a linearly spaced vector between two points.
     *
     * @param a The starting point.
     * @param b The ending point.
     * @param nsteps The number of steps.
     * @return MXWrapper. A new MXWrapper containing the linearly spaced vector.
     */
    public MXWrapper linspace(MXWrapper a, MXWrapper b, long nsteps) {
        return new MXWrapper(MX.linspace(a.mx, b.mx, nsteps));
    }

    /**
     * Computes the cross product of two matrices.
     *
     * @param b The MXWrapper representing the second matrix.
     * @param dim The dimension along which to compute the cross product.
     * @return MXWrapper. A new MXWrapper containing the result of the cross product.
     */
    public MXWrapper cross(MXWrapper b, long dim) {
        return new MXWrapper(MX.cross(this.mx, b.mx, dim));
    }

    /**
     * Computes the cross product of two matrices.
     *
     * @param b The MXWrapper representing the second matrix.
     * @return MXWrapper. A new MXWrapper containing the result of the cross product.
     */
    public MXWrapper cross(MXWrapper b) {
        return new MXWrapper(MX.cross(this.mx, b.mx));
    }

    /**
     * Computes the skew-symmetric matrix of this matrix.
     *
     * @return MXWrapper. A new MXWrapper containing the skew-symmetric matrix.
     */
    public MXWrapper skew() {
        return new MXWrapper(MX.skew(this.mx));
    }

    /**
     * Computes the inverse of the skew-symmetric matrix of this matrix.
     *
     * @return MXWrapper. A new MXWrapper containing the inverse skew-symmetric matrix.
     */
    public MXWrapper inv_skew() {
        return new MXWrapper(MX.inv_skew(this.mx));
    }

    /**
     * Converts the lower triangular matrix represented by this MXWrapper to a symmetric matrix.
     *
     * @return MXWrapper. A new MXWrapper containing the symmetric matrix.
     */
    public MXWrapper tril2symm() {
        return new MXWrapper(MX.tril2symm(this.mx));
    }

    /**
     * Converts the upper triangular matrix represented by this MXWrapper to a symmetric matrix.
     *
     * @return MXWrapper. A new MXWrapper containing the symmetric matrix.
     */
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
    public MXWrapper diff(long n, long axis) {
        return new MXWrapper(MX.diff(this.mx, n, axis));
    }

    /**
     * Computes the difference of the matrix represented by this MXWrapper.
     *
     * @param n The number of times to compute the difference.
     * @return MXWrapper. A new MXWrapper containing the result of the difference.
     */
    public MXWrapper diff(long n) {
        return new MXWrapper(MX.diff(this.mx, n));
    }

    /**
     * Computes the difference of the matrix represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the result of the difference.
     */
    public MXWrapper diff() {
        return new MXWrapper(MX.diff(this.mx));
    }

    /**
     * Checks if the expression represented by this MXWrapper is linear with respect to the specified variable.
     *
     * @param var The MXWrapper representing the variable.
     * @return boolean. True if the expression is linear, false otherwise.
     */
    public boolean isLinear(MXWrapper var) {
        return MX.is_linear(this.mx, var.mx);
    }

    /**
     * Checks if the expression represented by this MXWrapper is quadratic with respect to the specified variable.
     *
     * @param var The MXWrapper representing the variable.
     * @return boolean. True if the expression is quadratic, false otherwise.
     */
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
    public MXWrapper rank1(MXWrapper alpha, MXWrapper x, MXWrapper y) {
        return new MXWrapper(MX.rank1(this.mx, alpha.mx, x.mx, y.mx));
    }

    /**
     * Computes the log-sum-exponential of the expression represented by this MXWrapper.
     *
     * @return MXWrapper. A new MXWrapper containing the result of the log-sum-exponential.
     */
    public MXWrapper logsumexp() {
        return new MXWrapper(MX.logsumexp(this.mx));
    }

    public MXWrapper add(MXWrapper other) {
        MX result = MX.plus(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper subtract(MXWrapper other) {
        MX result = MX.minus(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper multiply(MXWrapper other) {
        MX result = MX.times(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper divide(MXWrapper other) {
        MX result = MX.rdivide(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper pow(MXWrapper other) {
        MX result = MX.pow(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper lt(MXWrapper other) {
        MX result = MX.lt(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper eq(MXWrapper other) {
        MX result = MX.eq(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper abs() {
        MX result = MX.abs(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sqrt() {
        MX result = MX.sqrt(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sin() {
        MX result = MX.sin(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper cos() {
        MX result = MX.cos(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper exp() {
        MX result = MX.exp(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper tan() {
        MX result = MX.tan(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper atan() {
        MX result = MX.atan(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper asin() {
        MX result = MX.asin(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper acos() {
        MX result = MX.acos(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sinh() {
        MX result = MX.sinh(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper cosh() {
        MX result = MX.cosh(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper tanh() {
        MX result = MX.tanh(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper expm1() {
        MX result = MX.expm1(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper log1p() {
        MX result = MX.log1p(this.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper blocksplit() {
        //MX result = MX.blocksplit(new StdVectorVectorMX());
        //return new MXWrapper(result);
        return null;
    }

    public MXWrapper norm_2() {
        MX result = MX.norm_2(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper norm_fro() {
        MX result = MX.norm_fro(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper norm_1() {
        MX result = MX.norm_1(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper norm_inf() {
        MX result = MX.norm_inf(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper gradient(MXWrapper other) {
        MX result = MX.gradient(this.mx, other.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper forward() {
        // MX result = MX.forward();
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change to StdVectorMX
    public MXWrapper reverse() {
        // MX result = MX.reverse(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Add MX Objectes
    public MXWrapper linearize() {
        // MX result = MX.linearize(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Revise logic
    public MXWrapper if_else(MXWrapper condition, MXWrapper trueValue, MXWrapper falseValue) {
        MX result = MX.if_else(condition.mx, trueValue.mx, falseValue.mx);
        return new MXWrapper(result);
    }

    public MXWrapper zeros(long size) {
        MX result = MX.zeros(size);
        return new MXWrapper(result);
    }

    public MXWrapper ones(long size) {
        MX result = MX.ones(size);
        return new MXWrapper(result);
    }

    // TODO: Change
    public MXWrapper repmat(int rows, int cols) {
        // MX result = MX.repmat(this.mx, rows, cols);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change to A, Aplha, x, y
    public MXWrapper rank1(MXWrapper v) {
        // MX result = MX.rank1(this.mx, v.mx);
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper inv_minor() {
        MX result = MX.inv_minor(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper inv_node() {
        MX result = MX.inv_node(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper linspace(MXWrapper other, int steps) {
        MX result = MX.linspace(this.mx, other.mx, steps);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper ad_forward() {
        // MX result = MX.ad_forward(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change to StdVectorMX
    public MXWrapper ad_reverse() {
        // MX result = MX.ad_reverse(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Add MX
    public MXWrapper linearize(MXWrapper point) {
        // MX result = MX.linearize(this.mx, point.mx);
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper if_else_zero(MXWrapper condition) {
        MX result = MX.if_else_zero(this.mx, condition.mx);
        return new MXWrapper(result);
    }

    public MXWrapper logic_not() {
        MX result = MX.logic_not(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper logic_and(MXWrapper other) {
        MX result = MX.logic_and(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper logic_or(MXWrapper other) {
        MX result = MX.logic_or(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper fmin(MXWrapper other) {
        MX result = MX.fmin(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper fmax(MXWrapper other) {
        MX result = MX.fmax(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper hypot(MXWrapper other) {
        MX result = MX.hypot(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sign() {
        MX result = MX.sign(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper copysign(MXWrapper other) {
        MX result = MX.copysign(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper constpow(MXWrapper other) {
        MX result = MX.constpow(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper atan2(MXWrapper other) {
        MX result = MX.atan2(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper erf() {
        MX result = MX.erf(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper erfinv() {
        MX result = MX.erfinv(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper tangent(MXWrapper other) {
        MX result = MX.tangent(this.mx, other.mx);
        return new MXWrapper(result);
    }

    // TODO: Check Parameters
    public MXWrapper bspline(MXWrapper other) {
        // MX result = MX.bspline();
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Check Parameters
    public MXWrapper bspline_dual() {
        // MX result = MX.bspline_dual();
        // return new MXWrapper(result);
        return null;
    }

    public MX getCasADiObject() {
        return this.mx;
    }
}
