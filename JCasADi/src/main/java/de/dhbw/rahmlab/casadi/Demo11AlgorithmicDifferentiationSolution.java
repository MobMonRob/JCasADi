package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

/**
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
        
        //print(f.n_instructions()-f.nnz_in()-f.nnz_out()) # 2
        System.out.println(String.valueOf(f.n_instructions()-f.nnz_in()-f.nnz_out()));
        
        
        // # Ex1.4

        //# a_dot = sin(x) x_dot + x (cos(x) x_dot)
        //# x_bar = sin(x) a_bar + cos(x) (x a_bar)

        SX xdot = SX.sym("xdot");
        System.out.println(SX.jtimes_(a, x, xdot, false));
        SX abar = SX.sym("abar");
        System.out.println(SX.jtimes_(a, x, abar, true));

        
        //# Ex1.5

        Function fwd1_f=f.forward(1);
        Function adj1_f=f.reverse(1);

        // usprünglich disp(true)
        System.out.println(fwd1_f.toString(true));
        System.out.println(adj1_f.toString(true));
        
        long count_f = f.n_instructions()-f.nnz_in()-f.nnz_out();
        long count_fwd1_f = fwd1_f.n_instructions()-fwd1_f.nnz_in()-fwd1_f.nnz_out();
        long count_adj1_f = adj1_f.n_instructions()-adj1_f.nnz_in()-adj1_f.nnz_out();

        System.out.println(String.valueOf(count_f));
        System.out.println(String.valueOf(count_fwd1_f));
        System.out.println(String.valueOf(count_adj1_f));
     }
}
