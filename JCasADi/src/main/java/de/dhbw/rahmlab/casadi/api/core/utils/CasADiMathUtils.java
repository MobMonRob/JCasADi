package de.dhbw.rahmlab.casadi.api.core.utils;

public class CasADiMathUtils {

    public static Double[] linspace(double start, double stop, int num) {
        Double[] result = new Double[num];
        double step = (stop - start) / (num - 1);

        for (int i = 0; i < num; i++) {
            result[i] = start + i * step;
        }

        return result;
    }

}
