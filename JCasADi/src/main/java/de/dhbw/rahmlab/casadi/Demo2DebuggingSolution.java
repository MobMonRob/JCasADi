package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo2DebuggingSolution {
    
    public static void main(String[] args) {
        
        // # Ex 1.1

        SX x = SX.sym("x");
        SX y = SX.sin(x);
        SX z = SX.plus(SX.mrdivide(y, x), y);// //SX.plus(x, y)); //y/x+y;

        Function f = new Function("f",new StdVectorSX(new SX[]{x}),new StdVectorSX(new SX[]{z}));

        //print(f(0.01)) # 1.00998
        StdVectorDM arg = new StdVectorDM(new DM[]{new DM(0.01)});
        StdVectorDM res = new StdVectorDM();
	f.call(arg, res);
        System.out.println("f(0.01)="+res.toString()); 
        
        
        // # Ex 1.2

        y = SX.sin(x).printme(new SX(0));
        z = SX.plus(SX.mrdivide(y,x), y); // SX.plus(x, y));
        f = new Function("f",new StdVectorSX(new SX[]{x}),new StdVectorSX(new SX[]{z}));

        //f(0.01) # |> 0 : 9.9998333341666645e-03
        //# The zero is the number you passed on to printme.
        arg = new StdVectorDM(new DM[]{new DM(0.01)});
        res = new StdVectorDM();
	f.call(arg, res);
        System.out.println("f(0.01)="+res.toString()); 
        
        
        //# Ex 1.3

        MX xx = MX.sym("x");

        // attachAssert(y) If y does not evaluate to 1, a runtime error is raised
        //MX yassert = MX. // x>=0
        MX yy = MX.sqrt(xx.attachAssert(MX.ge(xx, new MX(0d)) , "That's not allowed!"));
        StdVectorDM result = new StdVectorDM();
	Function f1 = new Function("f1", new StdVectorMX(new MX[]{xx}),
                                             new StdVectorMX(new MX[]{yy}));
        StdVectorDM argument = new StdVectorDM(new DM[]{new DM(-3)});
        try {
            f1.call(argument, result);
            System.out.println("f1(-3)="+result.toString()); // f(-3)=[1.73205]
            // Exception in thread "main" java.lang.RuntimeException: Error in Function::call for 'f1' [MXFunction] at .../casadi/core/function.cpp:330:
            // .../casadi/core/assertion.cpp:70: Assertion error: That's not allowed
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        /*try:
          f(-3)
        except Exception as e:
          print(e)
          */

        
        //# Ex 1.4

        f = new Function("f",new StdVectorMX(new MX[]{xx}),new StdVectorMX(new MX[]{MX.jacobian(yy,xx)}));

        /*try:
          f(-3)
        except Exception as e:
          print(e)
        */
        try {
            f.call(argument, result);
            System.out.println(" neu f(-3)="+result.toString()); // f(-3)=[1.73205]
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        
        //# Error is still raised

        
        //# Ex 1.4

        /*xx = MX.sym("x");

        yy = if_else(x<=1,x**2,-0.5*x**2+3*x-1.5)

        f = Function("f",[xx],[yy])
        xs = np.linspace(-4,5)
        ys = f(xs)

        plot(xs,ys)

        show()*/
                
                

        //# Ex 1.6

        /*x = MX.sym("x")

        y = if_else(x<=1,(x**2).monitor("true-clause"),(-0.5*x**2+3*x-1.5).monitor("false-clause"))

        f = Function('f',[x],[y])

        f(3) # both the true and false clause are evaluated*/

        //# Ex 1.7

        /*x = MX.sym("x")

        y = if_else(x<=1,(x**2).monitor("true-clause"),(-0.5*x**2+3*x-1.5).monitor("false-clause"),True)

        f = Function('f',[x],[y])

        f(3) //# Only the false-clause is evaluated
        f(0) //# Only the true-clause is evaluated

        */
    }
}
