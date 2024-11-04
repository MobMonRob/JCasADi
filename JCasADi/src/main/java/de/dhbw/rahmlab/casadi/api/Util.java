package de.dhbw.rahmlab.casadi.api;

import de.dhbw.rahmlab.casadi.impl.casadi.MX;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Util {
    
    //TODO
    // vertcat und horzcat ähnlich wie in Python zur Verfügung stellen mit unterschiedlichen
    // Argumenten
    // vielleicht brauche ich aber auch SX und DM als result types?
    // Eventuell eigenen classes MX, SX und DM zur Verfügung stellen und dort die
    // passenden vertcat/horzcat Methodne ansiedeln?
    // sind derartige Methoden als Abkürzung überhaupt sinnvoll?
    // tauchen in den Beispielen des Kurses auf, aber ich kann durch
    // MX(StdDouble...) das gleiche erreichen und dann statt horzcat einfach transpose verwenden
    @Deprecated
    public static MX vertcat(double[] values){
        return null;
    }
    public static MX horzcat(double[] values){
        return null;
    }
}
