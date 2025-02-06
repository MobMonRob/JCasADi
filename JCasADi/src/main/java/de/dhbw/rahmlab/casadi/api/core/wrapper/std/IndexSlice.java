package de.dhbw.rahmlab.casadi.api.core.wrapper.std;

import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Slice;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

/**
 * Represents a range of indices for slicing operations.
 *
 * <p>The {@code IndexRange} class provides functionality to define and manipulate
 * a range of indices, allowing for operations such as slicing and accessing
 * elements within a specified range. It supports various constructors to create
 * ranges based on single indices, start and stop values, and step sizes.</p>
 *
 * <p>This class is particularly useful for working with data structures that
 * require indexing, such as arrays or matrices, and provides methods to retrieve
 * information about the range, check its properties, and apply transformations.</p>
 *
 * <p>Examples of usage:</p>
 * <pre>
 * IndexRange range1 = new IndexRange(0, 10); // Creates a range from 0 to 10
 * IndexRange range2 = new IndexRange(0, 10, 2); // Creates a range from 0 to 10 with a step of 2
 * </pre>
 */
public class IndexSlice {

    private final Slice slice;

    /**
     * Constructs an {@code IndexSlice} object representing all elements.
     */
    public IndexSlice() {
        this.slice = new Slice();
    }

    /**
     * Constructs an {@code IndexSlice} object representing a single index.
     *
     * @param i the index to represent.
     */
    private IndexSlice(long i) {
        this.slice = new Slice(i);
    }

    /**
     * Constructs an {@code IndexSlice} object representing a range with a specified step.
     *
     * @param start the starting index of the range.
     * @param stop the ending index of the range.
     * @param step the step size for the range.
     */
    private IndexSlice(long start, long stop, long step) {
        this.slice = new Slice(start, stop, step);
    }

    /**
     * Constructs an {@code IndexSlice} object representing a range.
     *
     * @param start the starting index of the range.
     * @param stop the ending index of the range.
     */
    private IndexSlice(long start, long stop) {
        this.slice = new Slice(start, stop);
    }

    /**
     * Constructs an {@code IndexSlice} object representing a range with a specified step using integer values.
     *
     * @param start the starting index of the range.
     * @param stop the ending index of the range.
     * @param step the step size for the range.
     */
    private IndexSlice(int start, int stop, int step) {
        this.slice = new Slice(start, stop, step);
    }

    /**
     * Constructs an {@code IndexSlice} object representing a range using integer values.
     *
     * @param start the starting index of the range.
     * @param stop the ending index of the range.
     */
    private IndexSlice(int start, int stop) {
        this.slice = new Slice(start, stop);
    }

    /**
     * Constructs an {@code IndexSlice} object representing a range with a specified step using mixed integer and long values.
     *
     * @param start the starting index of the range.
     * @param stop the ending index of the range.
     * @param step the step size for the range.
     */
    private IndexSlice(int start, long stop, int step) {
        this.slice = new Slice(start, stop, step);
    }

    /**
     * Constructs an {@code IndexSlice} object representing a range using mixed integer and long values.
     *
     * @param start the starting index of the range.
     * @param stop the ending index of the range.
     */
    private IndexSlice(int start, long stop) {
        this.slice = new Slice(start, stop);
    }

    /**
     * Constructs an {@code IndexSlice} object representing a range with a specified step using mixed long and integer values.
     *
     * @param start the starting index of the range.
     * @param stop the ending index of the range.
     * @param step the step size for the range.
     */
    private IndexSlice(long start, int stop, int step) {
        this.slice = new Slice(start, stop, step);
    }

    /**
     * Constructs an {@code IndexSlice} object representing a range using mixed long and integer values.
     *
     * @param start the starting index of the range.
     * @param stop the ending index of the range.
     */
    private IndexSlice(long start, int stop) {
        this.slice = new Slice(start, stop);
    }

    /**
     * Constructs an {@code IndexSlice} object using the specified values.
     *
     * <p>This constructor accepts a variable number of {@link NumberWrapper} arguments.
     * It creates a slice based on the number of provided values:</p>
     *
     * <ul>
     *   <li>If one value is provided, it creates a slice representing that single index.</li>
     *   <li>If two values are provided, it creates a slice representing a range from the first value to the second value.</li>
     *   <li>If three values are provided, it creates a slice representing a range with a specified step.</li>
     * </ul>
     *
     * @param values an array of {@link NumberWrapper} objects representing the indices or range.
     *               Must not be null or empty, and must contain exactly 1 to 3 elements.
     * @throws IllegalArgumentException if {@code values} is null, empty, or contains more than 3 elements.
     */
    public IndexSlice(NumberWrapper... values) {
        if (values == null || values.length == 0 || values.length > 3) {
            throw new IllegalArgumentException("Input values must not be null or empty and must contain exactly 1 to 3 elements.");
        } else {
            if (values.length == 1) {
                this.slice = new Slice(values[0].getLongValue());
            } else if (values.length == 2) {
                this.slice = new Slice(values[0].getLongValue(), values[1].getLongValue());
            } else {
                this.slice = new Slice(values[0].getLongValue(), values[1].getLongValue(), values[2].getLongValue());
            }
        }
    }

    /**
     * Constructs an {@code IndexSlice} object from another {@link Slice} object.
     *
     * @param other the {@link Slice} object to copy.
     */
    public IndexSlice(Slice other) {
        this.slice = new Slice(other);
    }

    /**
     * Constructs an {@code IndexSlice} object from another {@code IndexSlice} object.
     *
     * @param other the {@code IndexSlice} object to copy.
     */
    public IndexSlice(IndexSlice other) {
        this.slice = new Slice(other.getCasADiObject());
    }

    /**
     * Returns the starting index of the slice.
     *
     * @return the starting index.
     */
    public long getStart() {
        return this.slice.getStart();
    }

    /**
     * Returns the ending index of the slice.
     *
     * @return the ending index.
     */
    public long getStop() {
        return this.slice.getStop();
    }

    /**
     * Returns the step size of the slice.
     *
     * @return the step size.
     */
    public long getStep() {
        return this.slice.getStep();
    }

    /**
     * Returns all indices represented by this slice as an {@link IntegerVector}.
     *
     * @return an {@link IntegerVector} containing all indices.
     */
    public IntegerVector all() {
        return new IntegerVector(this.slice.all());
    }

    /**
     * Returns all indices represented by this slice as an {@link IntegerVector}
     * with a specified length and index type.
     *
     * @param len the length to apply.
     * @param ind1 whether to use 1-based indexing.
     * @return an {@link IntegerVector} containing all indices.
     */
    public IntegerVector all(long len, boolean ind1) {
        return new IntegerVector(this.slice.all(len, ind1));
    }

    /**
     * Returns all indices represented by this slice as an {@link IntegerVector}
     * with a specified length.
     *
     * @param len the length to apply.
     * @return an {@link IntegerVector} containing all indices.
     */
    public IntegerVector all(long len) {
        return new IntegerVector(this.slice.all(len));
    }

    /**
     * Returns all indices represented by this slice as an {@link IntegerVector}
     * based on an outer {@code IndexSlice} and a specified length.
     *
     * @param outer the outer {@code IndexSlice} to use.
     * @param len the length to apply.
     * @return an {@link IntegerVector} containing all indices.
     */
    public IntegerVector all(IndexSlice outer, long len) {
        return new IntegerVector(this.slice.all(outer.getCasADiObject(), len));
    }

    /**
     * Returns the number of elements in the slice.
     *
     * @return the size of the slice.
     */
    public long size() {
        return this.slice.size();
    }

    /**
     * Checks if the slice is empty.
     *
     * @return {@code true} if the slice is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return this.slice.is_empty();
    }

    /**
     * Checks if the slice represents a scalar value.
     *
     * @param len the length to check against.
     * @return {@code true} if the slice is scalar, {@code false} otherwise.
     */
    public boolean isScalar(long len) {
        return this.slice.is_scalar(len);
    }

    /**
     * Returns the scalar value of the slice if it is scalar.
     *
     * @param len the length to check against.
     * @return the scalar value.
     * @throws IllegalArgumentException if the slice is not scalar.
     */
    public long scalar(long len) {
        return this.slice.scalar(len);
    }

    /**
     * Applies a concrete length to the slice and returns a new {@code IndexSlice}.
     *
     * @param len the length to apply.
     * @param ind1 whether to use 1-based indexing.
     * @return a new {@code IndexSlice} with the applied length.
     */
    public IndexSlice apply(long len, boolean ind1) {
        return new IndexSlice(this.slice.apply(len, ind1));
    }

    /**
     * Applies a concrete length to the slice and returns a new {@code IndexSlice}.
     *
     * @param len the length to apply.
     * @return a new {@code IndexSlice} with the applied length.
     */
    public IndexSlice apply(long len) {
        return new IndexSlice(this.slice.apply(len));
    }

    /**
     * Returns the type name of the slice.
     *
     * @return the type name as a {@code String}.
     */
    public String typeName() {
        return this.slice.type_name();
    }

    /**
     * Returns a string representation of the slice with additional details.
     *
     * @param more whether to include more details in the string representation.
     * @return a string representation of the slice.
     */
    public String toString(boolean more) {
        return this.slice.toString(more);
    }

    /**
     * Returns a string representation of the slice.
     *
     * @return a string representation of the slice.
     */
    public String toString() {
        return this.slice.toString();
    }

    /**
     * Returns information about the slice as a {@link Dict}.
     *
     * @return a {@link Dict} containing information about the slice.
     */
    public Dict info() {
        return this.slice.info();
    }

    /**
     * Returns the underlying {@link Slice} object.
     *
     * @return the underlying {@link Slice} object.
     */
    public Slice getCasADiObject() {
        return this.slice;
    }

}
