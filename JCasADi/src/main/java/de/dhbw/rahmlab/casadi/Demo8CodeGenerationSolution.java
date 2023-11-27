package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo8CodeGenerationSolution {
    
    // Ex 1.1
    
    SX x = SX.sym("x", 2);
    SX y = SX.sym("y");
    
    Function f = new Function("f", new StdVectorSX(new SX[]{x,y}), 
                                       new StdVectorSX(new SX[]{SX.minus(SX.sqrt(x),y)}));
    f.generate("fun");

    
    // Ex 1.2
    
    Dict options = new Dict();
    options.put("with_header", new GenericType(true));
       
    f.generate("fun2",options);
}