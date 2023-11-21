package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo1RecapSolution {
    
    public static void main(String[] args) {
        // Ex 1.1
        MX x = MX.sym("x");
        MX y = MX.sym("y");
        MX z = MX.sin(MX.dot(x, y));
        
        Function f = new Function("f",new StdVectorMx(new MX[]{x,y}, new StdVectorMx(new MX{z}, 
                new Std))
        
    }
       
}
