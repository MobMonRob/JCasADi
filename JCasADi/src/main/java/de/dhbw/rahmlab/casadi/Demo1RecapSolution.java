package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo1RecapSolution {
    
    public static void main(String[] args) {
        
        // Ex 1.1
        
        MX x = MxStatic.sym("x");
        MX y = MxStatic.sym("y");
        MX z = MxStatic.sin(MxStatic.dot(x, y));
        
        Dict options = new Dict();
        options.put("always_inline", new GenericType(true));
        //TODO possibly syntactic shugar: MX[] direkt als Argument von Function 
        // ermögichen
        Function f = new Function("f",new StdVectorMX(new MX[]{x,y}),
                new StdVectorMX(new MX[]{z}), options);
        StdVectorMX args1 = new StdVectorMX(new MX[]{MX.dot(new MX(2d), x), y});
        StdVectorMX result = new StdVectorMX();
        // Funktionaufruf mit symbolischem Argument
	f.call(args1, result);
        // .name() führt zu einer Exception, vermutlich da der name des result-values
        // nicht gesetzt ist. Ist das bei Python/Matlab genauso?
        //FIXME
        //System.out.println("f(2*x,y)=" + result.get(0).name()); // sin(((2.*x)*y)
       
        //FIXME
        // toString() liefert nur den Pointer
        // DmStatic.str() liefert einen String, vielleicht fehlt eine vergleichbare Methode
        // für MX? oder sollte die toString()-Methode den symbolischen Ausdruck liefern?
        System.out.println("f(2*x,y)=" + result.get(0).toString()); // sin(((2.*x)*y)
       
        
        // Ex 1.2
        
        // print(substitute(z,x,2*x)
        MX res = MxStatic.substitute(z, x, MxStatic.dot(new MX(2d),x)); // sin(((2.*x)*y)
        System.out.println(res.toString());
        
        
        // Ex 1.3
        
        MX A_mx = MxStatic.sym("A",2,2);
        MX b_mx = MxStatic.sym("b",2);
       
        // z = A_mx@b_mx
        z = MxStatic.times(A_mx, b_mx);
        
        StdVectorMX args2 = new StdVectorMX(new MX[]{A_mx, b_mx});
        
        Function f_mx = new Function("f", args2, new StdVectorMX(new MX[]{z}));
        
        SX A_sx = SxStatic.sym("A",2,2);
        SX b_sx = SxStatic.sym("b",2);
        
        // z = A_sx@b_sx
        SX z1 = SxStatic.times(A_sx, b_sx);
        
        // Warum gibts die Methode disp() nicht für MX?
        //A_sx.disp(stream);
        System.out.println(z1.toString());
        
        
    } 
}
