package de.dhbw.rahmlab.casadi.api.core.wrapper.numeric;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.NumericValue;

/**
 * A wrapper class for numeric values that implements the NumericValue interface.
 * This class provides methods to retrieve the underlying numeric value in various formats.
 */
public class NumberWrapper implements NumericValue {

    private final Number value;

    /**
     * Constructs a NumberWrapper with the specified numeric value.
     *
     * @param value the numeric value to be wrapped
     */
    public NumberWrapper(Number value) {
        this.value = value;
    }

    /**
     * Returns the underlying numeric value as an integer.
     *
     * @return the integer representation of the wrapped numeric value
     */
    public int getIntValue() {
        return this.value.intValue();
    }

    /**
     * Returns the underlying numeric value as a long.
     *
     * @return the long representation of the wrapped numeric value
     */
    public long getLongValue() {
        return this.value.longValue();
    }

    /**
     * Returns the underlying numeric value as a float.
     *
     * @return the float representation of the wrapped numeric value
     */
    public float getFloatValue() {
        return this.value.floatValue();
    }

    /**
     * Returns the underlying numeric value as a double.
     *
     * @return the double representation of the wrapped numeric value
     */
    public double getDoubleValue() {
        return this.value.doubleValue();
    }

    /**
     * Returns the underlying numeric value as a short.
     *
     * @return the short representation of the wrapped numeric value
     */
    public short getShortValue() {
        return this.value.shortValue();
    }

    public Number getUnderlyingNumberObject() {
        return this.value;
    }

    public NumberVector toVector() {
        return new NumberVector(this.value);
    }

}
