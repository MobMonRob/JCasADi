package de.dhbw.rahmlab.casadi.api;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SXScalar {
    
    public de.dhbw.rahmlab.casadi.impl.casadi.SX sx;
    
    public SXScalar(){
        sx = new SX(Sparsity.dense(1));
    }
    
    public static SXScalar sum(SXScalar[] summands){
        SXScalar result = new SXScalar();
        for (int i=0;i<summands.length;i++){
            result.sx = SX.plus(result.sx, summands[i].sx);
        }
        return result;
    }
}
