package de.dhbw.rahmlab.casadi.api;

import static de.dhbw.rahmlab.casadi.api.Util.toLongArr;
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
     * Creates a new SX column vector with the length of the given count of indizes and
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
        this.sx = new SX(new StdVectorCasadiSXElem(colElements));
    }
    /**
     * Create a new sparse SX column vector with the given count of rows and sets the
     * elements of the given sx into the the elements of the created one. 
     * 
     * @param rows count of rows of the new one
     * @param values values to set into the new one
     * @param indizes indizes where to set the given values
     */
    public SXColVec(int rows, SXElem[] values, int[] indizes){
        Sparsity sparsity = createColVecSparsity(rows, indizes);
        StdVectorCasadiSXElem elements = new StdVectorCasadiSXElem(values);
        this.sx = new SX(sparsity, elements, true);
    }
    
    /**
     * Get the element of the column vector at the given index as a scalar.
     * 
     * @param index
     * @return 
     */
    public SXScalar get(int index){
        if (index >= sx.sparsity().rows()) throw new IllegalArgumentException("index "+String.valueOf(index)+" >= sx.rows()!");
        return new SXScalar(sx.at(index));
    }
    
    private static Sparsity createColVecSparsity(int rows, int[] nonZeroIndizes) {
        //System.out.println("create col vec sparsity: rows="+String.valueOf(rows)+
        //        " nonZeroIndizes.length="+String.valueOf(nonZeroIndizes.length));
        StdVectorCasadiInt colind = new StdVectorCasadiInt(new long[]{0, nonZeroIndizes.length});
        StdVectorCasadiInt row = new StdVectorCasadiInt(toLongArr(nonZeroIndizes)); 
        Sparsity result = new Sparsity((long) rows, 1l, colind, row);
        //result.spy();
        return result;
    }
}