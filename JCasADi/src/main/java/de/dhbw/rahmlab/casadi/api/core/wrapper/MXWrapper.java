package de.dhbw.rahmlab.casadi.api.core.wrapper;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.Slice;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.*;

import java.util.List;

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

    public MXWrapper inv() {
        MX result = MX.inv(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper pinv() {
        MX result = MX.pinv(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper det() {
        MX result = MX.det(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper trace() {
        MX result = MX.trace(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper kron(MXWrapper other) {
        MX result = MX.kron(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper reshape(long rows, long cols) {
        MX result = MX.reshape(this.mx, rows, cols);
        return new MXWrapper(result);
    }

    public MXWrapper densify() {
        MX result = MX.densify(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper diag() {
        MX result = MX.diag(this.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper blockcat(MXWrapper other) {
        MX result = MX.blockcat(new StdVectorVectorMX());
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper horzcat(MXWrapper other) {
        MX result = MX.horzcat(new StdVectorMX());
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper vertcat(MXWrapper other) {
        MX result = MX.vertcat(new StdVectorMX());
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper blocksplit() {
        MX result = MX.blockcat(new StdVectorVectorMX());
        return new MXWrapper(result);
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

    public MXWrapper jacobian(MXWrapper other) {
        MX result = MX.jacobian(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper hessian(MXWrapper other) {
        MX result = MX.hessian(this.mx, other.mx);
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

    public MXWrapper simplify() {
        MX result = MX.simplify(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper substitute(MXWrapper oldValue, MXWrapper newValue) {
        MX result = MX.substitute(this.mx, oldValue.mx, newValue.mx);
        return new MXWrapper(result);
    }

    public MXWrapper solve(MXWrapper b) {
        MX result = MX.solve(this.mx, b.mx);
        return new MXWrapper(result);
    }

    public MXWrapper expm() {
        MX result = MX.expm(this.mx);
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

    // Matrixoperationen
    public MXWrapper mldivide(MXWrapper other) {
        MX result = MX.mldivide(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper mrdivide(MXWrapper other) {
        MX result = MX.mrdivide(this.mx, other.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper vertsplit(StdVectorCasadiInt offset) {
        // MX result = MX.vertsplit(this.mx, offset);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change to StdVectorMX
    public MXWrapper horzsplit(int cols) {
        // MX result = MX.horzsplit(this.mx, cols);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change
    public MXWrapper repmat(int rows, int cols) {
        // MX result = MX.repmat(this.mx, rows, cols);
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper tril() {
        MX result = MX.tril(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper triu() {
        MX result = MX.triu(this.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to A, Aplha, x, y
    public MXWrapper rank1(MXWrapper v) {
        // MX result = MX.rank1(this.mx, v.mx);
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper bilin(MXWrapper x, MXWrapper y) {
        MX result = MX.bilin(this.mx, x.mx, y.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sumsqr() {
        MX result = MX.sumsqr(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper nullspace() {
        MX result = MX.nullspace(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper inv_minor() {
        MX result = MX.inv_minor(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper inv_node() {
        MX result = MX.inv_node(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper cross(MXWrapper other) {
        MX result = MX.cross(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper dot(MXWrapper other) {
        MX result = MX.dot(this.mx, other.mx);
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

    public MXWrapper convexify() {
        MX result = MX.convexify(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper find() {
        MX result = MX.find(this.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper symvar() {
        // StdVectorMX result = de.dhbw.rahmlab.casadi.impl.casadi.MX.symvar(this.mx);
        // return new MXWrapper(result);
        return null;
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

    public MXWrapper logsumexp() {
        MX result = MX.logsumexp(this.mx);
        return new MXWrapper(result);
    }

    public MX getCasADiObject() {
        return this.mx;
    }
}
