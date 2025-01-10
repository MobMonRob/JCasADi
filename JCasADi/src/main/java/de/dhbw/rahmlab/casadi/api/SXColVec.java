package de.dhbw.rahmlab.casadi.api;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.SXElem;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiSXElem;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SXColVec {
    
    public SX sx;
    
    public SXColVec(SX sx){
        if (sx.sparsity().columns() != 1) throw new IllegalArgumentException("sx is no column vector");
        this.sx = sx;
    }
    // not yet tested
    public SXColVec(SX sx, int[] indizes){
        SXElem[] colElements = new SXElem[indizes.length];
        for (int i=0;i<indizes.length;i++){
            colElements[i] = sx.at(indizes[i]).scalar();
        }
        StdVectorCasadiSXElem x = new 
            StdVectorCasadiSXElem(colElements);
        sx = new SX(x);
    }
}
