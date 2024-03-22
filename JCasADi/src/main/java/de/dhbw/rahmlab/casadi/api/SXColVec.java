package de.dhbw.rahmlab.casadi.api;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SXColVec {
    
    public SX sx;
    
    public SXColVec(SX sx){
        if (sx.sparsity().columns() != 1) throw new IllegalArgumentException("sx is no column vector");
        this.sx = sx;
    }
}
