package de.dhbw.rahmlab.casadi.api;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.SXElem;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.casadi.SxSubIndex;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt;
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
    /**
     * Creates a new SX column vector with the lenght of the given indizes and
     * sets the elements of the given sx into the elements of the created one.
     * 
     * @param sx
     * @param indizes 
     */
    public SXColVec(SX sx, int[] indizes){
        if (sx.sparsity().columns() != 1) throw new IllegalArgumentException("sx is no column vector");
        SXElem[] colElements = new SXElem[indizes.length];
        for (int i=0;i<indizes.length;i++){
            colElements[i] = sx.at(indizes[i]).scalar();
        }
        StdVectorCasadiSXElem x = new 
            StdVectorCasadiSXElem(colElements);
        sx = new SX(x);
    }
    public SXColVec(int rows, SXElem[] values, int[] indizes){
        StdVectorCasadiSXElem elements = new StdVectorCasadiSXElem(values);
        Sparsity sparsity = createColVecSparsity(rows, indizes);
        sx = new SX(sparsity, elements, true);
    }
    
    private static Sparsity createColVecSparsity(int rows, int[] nonZeroIndizes) {
        /*StdVectorCasadiInt row = new StdVectorCasadiInt(toLongArr(nonZeroIndizes));
        StdVectorCasadiInt colind = new StdVectorCasadiInt(new long[]{0, nonZeroIndizes.length});
        Sparsity result = new Sparsity(rows, sparsity.getn_col(), colind, row);*/
        //result.spy();
        //return result;
        throw new RuntimeException("not yet implemented!");
    }
}
