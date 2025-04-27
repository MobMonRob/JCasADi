package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo1RootfindingSolution {
    
    public static void main(String[] args) {
        
        // Ex 1.1
        
        MX x = MX.sym("x");
        MX y = MX.sym("y");
        // TODO plus() sollte direkt doubles auch als Argument ermmöglichen
        // TODO MX-Objekte sollten direkt Methoden haben um damit weiterrechnen zu können
        MX g1 = MX.tanh(MX.minus(MX.dot(MX.plus(x, new MX(2.0)), MX.dot(MX.sq(y),new MX(1d/25d))), new MX(0.5)));
        MX g2 = MX.plus(MX.minus(MX.sin(x),MX.dot(new MX(0.5d),y)), new MX(1d));
        MX[] z = new MX[]{g1, g2};
        Dict options = new Dict();
        options.put("always_inline", new GenericType(true));
        //TODO possibly syntactic shugar: MX[] direkt als Argument von Function 
        // ermögichen
        Function f = new Function("f",new StdVectorMX(new MX[]{x,y}),
                new StdVectorMX(z), options);
        
        // Funktionsaufruf mit konstaten Argumenten
        StdVectorMX args1b = new StdVectorMX(new MX[]{new MX(-0.8),new MX(2d)});
        StdVectorMX result = new StdVectorMX();
        // Funktionaufruf mit symbolischem Argument
	f.call(args1b, result);
        // .name() führt zu einer Exception, vermutlich da der name des result-values
        // nicht gesetzt ist. Ist das bei Python/Matlab genauso?
        //FIXME
        //System.out.println("f(2*x,y)=" + result.get(0).name()); // sin(((2.*x)*y)
       
        // [-0.2986,-0.7174]
        // TODO in Python wird auf 4 Nachkommastellen gerundet, hier auf 6
        System.out.println("f(-0.8,2)=["+result.get(0)+","+result.get(1)+"]");
        
        //FIXME
        // toString() liefert nur den Pointer
        // DM.str() liefert einen String, vielleicht fehlt eine vergleichbare Methode
        // für MX? oder sollte die toString()-Methode den symbolischen Ausdruck liefern?
        //System.out.println("f(2*x,y)=" + result.get(0).toString()); // sin(((2.*x)*y)
       
        StdVectorDouble args1 = new StdVectorDouble(new double[]{-0.8,2d});
        MX test = new MX(args1);
        // cols = 1
        System.out.println("cols = "+String.valueOf(test.get_sparsity().columns()));
        // rows = 2
        System.out.println("rows = "+String.valueOf(test.get_sparsity().rows()));
        
        // Ex 1.2
        
        // x0 ist args1 von vorher 
        MX x0 = new MX(args1); //new MX(-0.8),new MX(2d)
        // nominal ist result von vorher
        MX eps = new MX(1e-8);
        
        //MX perturbed_x = 
        
                
                
        // Ex 1.3
        
        MX A_mx = MX.sym("A",2,2);
        MX b_mx = MX.sym("b",2);
       
        // z = A_mx@b_mx
        //z = MX.times(A_mx, b_mx);
        
        StdVectorMX args2 = new StdVectorMX(new MX[]{A_mx, b_mx});
        
        //Function f_mx = new Function("f", args2, new StdVectorMX(new MX[]{z}));
        
        SX A_sx = SX.sym("A",2,2);
        SX b_sx = SX.sym("b",2);
        
        // z = A_sx@b_sx
        SX z1 = SX.times(A_sx, b_sx);
        
        // Warum gibts die Methode disp() nicht für MX?
        //A_sx.disp(stream);
        System.out.println(z1.toString());
        
        
    } 
}
