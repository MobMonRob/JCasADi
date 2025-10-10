package de.dhbw.rahmlab.casadi.api.core.utils;

/**
 * Utility class providing mathematical functions related to CasADi.
 * This class includes methods for generating sequences and performing common mathematical operations.
 */
public class CasADiMathUtils {

    /**
     * Generates a sequence of evenly spaced values over a specified range.
     * A version of NumPys linspace for Java
     *
     * @param start the starting value of the sequence.
     * @param stop  the ending value of the sequence.
     * @param num   the number of values to generate in the sequence.
     * @return an array of Double containing the generated sequence.
     */
    public static Double[] linspace(double start, double stop, int num) {
        Double[] result = new Double[num];
        double step = (stop - start) / (num - 1);

        for (int i = 0; i < num; i++) {
            result[i] = start + i * step;
        }

        return result;
    }

}
