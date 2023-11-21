package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo1RecapSolution {
    
    public static void main(String[] args) {
        // Ex 1.1
        MX x = MX.sym("x");
        MX y = MX.sym("y");
        MX z = MX.sin(MX.dot(x, y));
        
        Dict options = new Dict();
        options.put("always_inline", new GenericType(true));
        Function f = new Function("f",new StdVectorMX(new MX[]{x,y}), 
                new StdVectorMX(new MX[]{z}), options);
        
    }
       
}
