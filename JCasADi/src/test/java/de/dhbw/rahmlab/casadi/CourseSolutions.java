/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CourseSolutions {
    
    public CourseSolutions() {
    }

    // Ex 1.1
    @Test
    public void hello() {
         MX x = MxStatic.sym("x");
        MX y = MxStatic.sym("y");
        // TODO plus() sollte direkt doubles auch als Argument ermmöglichen
        // TODO MX-Objekte sollten direkt Methoden haben um damit weiterrechnen zu können
        MX g1 = MxStatic.tanh(MxStatic.minus(MxStatic.dot(MxStatic.plus(x, new MX(2.0)), MX.dot(MxStatic.sq(y),new MX(1d/25d))), new MX(0.5)));
        MX g2 = MxStatic.plus(MxStatic.minus(MxStatic.sin(x),MxStatic.dot(new MX(0.5d),y)), new MX(1d));
        MX[] z = new MX[]{g1, g2};
        Dict options = new Dict();
        options.put("always_inline", new GenericType(true));
        //TODO possibly syntactic shugar: MX[] direkt als Argument von Function 
        // ermögichen
        Function f = new Function("f",new StdVectorMX(new MX[]{x,y}),
                new StdVectorMX(z), options);
        
        // Funktionsaufruf mit konstaten Argumenten
        StdVectorMX args1b = new StdVectorMX(new MX[]{new MX(-0.8),new MX(2d)});
        StdVectorDouble args1 = new StdVectorDouble(new double[]{-0.8,2d});
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
        
        //TODO
        //Hier mit assert() und Vergleich mit dem Lösungsvektor überprüfen
    }
}
