package de.dhbw.rahmlab.casadi.api;

import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDouble;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Util {
    
    //TODO
    // vertcat und horzcat ähnlich wie in Python zur Verfügung stellen mit unterschiedlichen
    // Argumenten
    // vielleicht brauche ich aber auch SX und DM als result types?
    // Eventuell eigene classes MX, SX und DM zur Verfügung stellen und dort die
    // passenden vertcat/horzcat Methodne ansiedeln?
    // sind derartige Methoden als Abkürzung überhaupt sinnvoll?
    // tauchen in den Beispielen des Kurses auf, aber ich kann durch
    // MX(StdDouble...) das gleiche erreichen und dann statt horzcat einfach transpose verwenden
    @Deprecated
    public static MX vertcat(double[] values){
        return null;
    }
    @Deprecated
    public static MX horzcat(double[] values){
        return null;
    }
    
    public static long[] toLongArr(int[] values) {
        long[] result = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i];
        }
        return result;
    }

    public static StdVectorDouble toStdVectorDouble(double[] values) {
        StdVectorDouble result = new StdVectorDouble();
        for (int i = 0; i < values.length; i++) {
            result.add(values[i]);
        }
        return result;
    }

    public static int[] toIntArr(StdVectorCasadiInt values) {
        int[] result = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i).intValue();
        }
        return result;
    }

    public static int[] toIntArr(long[] values) {
        int[] result = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = (int) values[i];
        }
        return result;
    }
    
    /**
     * Create a dense column vector from the given values.
     * 
     * @param values dense column values
     * @return 
     */
    public static SX toSX(double[] values) {
        // Warum diese Verschachtelung? Ich brauche hier einen Vector von Vectors, da hier alle dense matrix
        // values übergeben werden müssen.
        return new SX(new StdVectorVectorDouble(new StdVectorDouble[]{toStdVectorDouble(values)}));
    }

}
