package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

/**
  * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo11AlgorithmicDifferentiationSolution {
    
    public static void main(String[] args) {
        
        // # Ex1.1
        SX x = SxStatic.sym("x");

        SX a = SxStatic.dot(x, SxStatic.sin(x));

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

        SX xdot = SxStatic.sym("xdot");
        System.out.println(SxStatic.jtimes_(a, x, xdot, false));
        SX abar = SxStatic.sym("abar");
        System.out.println(SxStatic.jtimes_(a, x, abar, true));

        
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
        
        
        // # Ex 2.1
        
        x = SxStatic.sym("x");
        SX y = SxStatic.sym("y");
        SX z = SxStatic.sym("z");
        //args = vertcat(x,y,z)
        arg = new StdVectorSX(new SX[]{x,y,z});
        a = SxStatic.dot(x, y);
        //b = cos(z*a)*z
        SX b = SxStatic.dot(SxStatic.cos(SxStatic.dot(z, a)),z);
        //res = vertcat(a,b)
        StdVectorSX res = new StdVectorSX(new SX[]{a,b});
        
        f = new Function("f",arg, res);
        count_f = f.n_instructions()-f.nnz_in()-f.nnz_out();

        System.out.println("#true operations in f:"+String.valueOf(count_f)); // # 4

        // # Ex 2.2

        fwd1_f=f.forward(1);
        adj1_f=f.reverse(1);

        count_fwd1_f = fwd1_f.n_instructions()-fwd1_f.nnz_in()-fwd1_f.nnz_out();
        count_adj1_f = adj1_f.n_instructions()-adj1_f.nnz_in()-adj1_f.nnz_out();
        System.out.println(String.valueOf(count_fwd1_f));
        System.out.println(String.valueOf(count_adj1_f));// # 14 and 13 -- a factor 3.5 and 3.25
        // # This is fairly atypical: usually forward mode is cheaper than reverse mode

        
        // # Ex 2.3

        // args_num = vertcat(2,3,4)
        StdVectorDouble args_num1 = new StdVectorDouble(new double[]{2,3,4});
        StdVectorDM args_num = new StdVectorDM(new DM[]{new DM(args_num1)});
        
        StdVectorDouble einsA = new StdVectorDouble(new double[]{1,0,0}); // vertcat(1,0,0)
        StdVectorDM eins = new StdVectorDM(new DM[]{new DM(einsA)});
        
        StdVectorDouble zweiA = new StdVectorDouble(new double[]{0,1,0}); // vertcat(0,1,0)
        StdVectorDM zwei = new StdVectorDM(new DM[]{new DM(zweiA)});
        
        StdVectorDouble dreiA = new StdVectorDouble(new double[]{0,0,1}); // vertcat(0,0,1)
        StdVectorDM drei = new StdVectorDM(new DM[]{new DM(dreiA)});
        
        //StdVectorDouble dummy = new StdVectorDouble(new double[]{});
        StdVectorDM dummy = new StdVectorDM(new DM[]{});
        
		StdVectorDM res1 = new StdVectorDM();

		StdVectorDM args_num__eins = new StdVectorDM(new DM[]{new DM(args_num1), new DM(), new DM(einsA)});
		fwd1_f.call(args_num__eins, res1);
		System.out.println(res1);

		// System.out.println(fwd1_f.call(args_num, eins, res1));
        //System.out.println(fwd1_f(args_num,dummy,zwei));
        //System.out.println(fwd1_f(args_num,dummy,drei));
        //TODO
        
        // # Cost: 3*14=42 (forward)

        //print(adj1_f(args_num,[],vertcat(1,0)))
        //print(adj1_f(args_num,[],vertcat(0,1)))

        // # Cost 2*13=26 (reverse)

        
        // # Ex 2.4

        //fwd3_f = f.forward(3)
        //adj2_f = f.reverse(2)

        //# All 3 forward sweeps at once
        //print(fwd3_f(args_num,[],DM.eye(3)))

        //print("cost forward jac", fwd3_f.n_instructions()-fwd3_f.nnz_in()-fwd3_f.nnz_out()) # 34

        //# All 2 reverse sweeps at once
        //print(adj2_f(args_num,[],DM.eye(2)).T)

        //print("cost reverse jac", adj2_f.n_instructions()-adj2_f.nnz_in()-adj2_f.nnz_out()) # 22

        //# Ex 2.5

        //Jf = fwd3_f(args,[],DM.eye(3))
        //print(Jf)
        //Jf = Function('Jf',[args],[Jf])
        //print("cost forward jac", Jf.n_instructions()-Jf.nnz_in()-Jf.nnz_out()) # 16
        //Jr = adj2_f(args,[],DM.eye(2))
        //Jr = Function('Jr',[args],[Jr])
        //print("cost reverse jac", Jr.n_instructions()-Jr.nnz_in()-Jr.nnz_out()) # 13


        //# Ex 3.1

        //x = SxStatic.sym("x")
        //y = SxStatic.sym("y")
        //z = SxStatic.sym("z")
        //args = vertcat(x,y,z)

        //a = x*y
        //b = cos(z*a)*z

        //res = vertcat(a,b)
        //Je = jacobian(res,args)
        //J = Function('J',[args],[Je])
        //print("cost forward jac",J.n_instructions()-J.nnz_in()-J.nnz_out()) # 15

        //print("ad_weight default")
        //Je = jacobian(res,args,{"helper_options": {"verbose":True}})

        //# Printout says "Calculating forward derivatives"

        //# Ex 3.2
        //print("ad_weight 0.5")

        //Je = jacobian(res,args,{"helper_options": {"ad_weight":0.5, "verbose":True}})
        //J = Function('J',[args],[Je])
        //print(J.n_instructions()-J.nnz_in()-J.nnz_out()) # 12

        //# Ex 3.3

        //N = 20
        //x = SxStatic.sym("x",N)

        //sp = Sparsity.diag(N)+Sparsity.rowcol([N-1],range(N),N,N)

        //sp.spy() # Dense row - would need full forward seeding to recover each column of this dense row.

        //A = SxStatic.sym("A",sp)

        //jacobian(A @ x,x,{"helper_options": {"verbose":True}}) # 2 reverse sweeps taken

        //print("After transpose")
        //A = SxStatic.sym("A",sp.T)

        //jacobian(A @ x,x,{"helper_options": {"verbose":True}}) # 2 forward sweeps taken

     }
}
