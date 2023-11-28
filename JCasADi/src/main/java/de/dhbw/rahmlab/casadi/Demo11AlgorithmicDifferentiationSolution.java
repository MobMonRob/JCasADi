package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo11AlgorithmicDifferentiationSolution {
    
    public static void main(String[] args) {
        
        // # Ex1.1
        SX x = SX.sym("x");

        SX a = SX.dot(x, SX.sin(x));

        Dict options = new Dict();
        options.put("live_variables", new GenericType(false));
        
        StdVectorSX arg = new StdVectorSX(new SX[]{x});
        StdVectorSX result = new StdVectorSX(new SX[]{a});
        Function f = new Function("f",arg,result,options);
        //f.disp(true);
        //print("\n")
        System.out.println(f.toString(true));
         /*f:(i0)->(o0) SXFunction
        Algorithm:
        @0 = input[0][0];
        @1 = sin(@0);
        @2 = (@0*@1);
        output[0][0] = @2;*/
         
         
        //# Ex1.2
        
        //print(f.n_instructions()) # 4
        System.out.println(String.valueOf(f.n_instructions()));

       
     }
}
