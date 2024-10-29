package de.dhbw.rahmlab.casadi.api;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MX {
    
    private de.dhbw.rahmlab.casadi.impl.casadi.MX mx;
    
    // row vector, corresponding to vertcat
    public MX(double[] values){
        mx = new de.dhbw.rahmlab.casadi.impl.casadi.MX(new StdVectorDouble(values));
    }
}
