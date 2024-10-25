package de.dhbw.rahmlab.casadi.api.course.fristBlock;

import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

import static de.dhbw.rahmlab.casadi.impl.core__.integrator;
import static de.dhbw.rahmlab.casadi.impl.core__.nlpsol;

public class Nlpsol {

    // Exercise: NLP (Presentation)
    // minimize x^2 + 100 z^2
    //  x, y, z
    //    s.t. z + (1-x)^2 - y = 0
    // Corresponding Python-Code:
    // x = MX.sym('x');
    // y = MX.sym('y');
    // z = MX.sym('z');
    // v = [x;y;z];
    // f = x^2 + 100*z^2;
    // g = z + (1-x)^2 - y;
    // nlp = struct('x', v, 'f', f, 'g', g); -> no solution to pass struct to solver
    // solver = nlpsol('solver', 'ipopt', nlp);
    // res = solver('x0' , [2.5 3.0 0.75],... % solution guess
    //              'lbg',    0,...           % lower bound on g
    //              'ubg',    0);             % upper bound on g
    // x_opt = full(res.x)

//    static MX x = MX.sym("x");
//    static MX y = MX.sym("y");
//    static MX z = MX.sym("z");
//    static MX[] v = new MX[]{x, y, z};
//    static MX f = MX.plus(MX.pow(x, new MX(2)), MX.times(new MX(100), MX.pow(z, new MX(2))));
//    static MX g = MX.minus(MX.plus(z, MX.pow(MX.minus(new MX(1), x), new MX(2))), y);
//    static Function nlp = new Function("nlp", new StdVectorMX(v), new StdVectorMX(new MX[]{f, g}));

    // Exercise sheet
    static double m = 0.04593; // ball mass [kg]
    static double gravity = 9.81; // gravity [m/s^2]
    static double c = 17.5e-5; // friction [kg/m]
    static MX p = MX.sym("p", 2);
    static MX v = MX.sym("v", 2);
    static MX states = MX.vertcat(new StdVectorMX(new MX[]{p, v}));
    static MX speed = MX.norm_2(v);
    static MX rhs = MX.vertcat(new StdVectorMX(new MX[]{v, MX.times(MX.times(new MX(-c), speed), MX.rdivide(v, new MX(m)))}));

    public static void exercise1_1() {
        DM.set_precision(6);
        rhs.set(MX.minus(rhs.at(3), new MX(gravity)), true, new IM(3)); // -> triggers wrong values
        var f = new Function("rhs", new StdVectorMX(new MX[]{MX.vertcat(new StdVectorMX(new MX[]{p, v}))}), new StdVectorMX(new MX[]{rhs}));
        var result = new StdVectorMX();
        f.call(new StdVectorMX(new MX[]{MX.vertcat(new StdVectorMX(new MX[]{new MX(0.0), new MX(0.0), new MX(35.0), new MX(30.0)}))}), result);
        System.out.println("rhs Dim: " + rhs.dim_());
        var re = MX.vertcat(result);
        System.out.print("[");
        for (int i = 0; i < re.rows(); i++) {
            if (i < 3)
                System.out.print(re.at(i, 0) + ", ");
            else
                System.out.print(re.at(i, 0));
        }
        System.out.println("]");
        // -> Wrong values at last (second & third) positions in the result MX
        // My Result: [35, 30, -15.0792, -5.26917]
        // Solution: [35, 30, -6.14737, -15.0792]
    }

    public static void exercise1_2() {
        var ode = new Function("ode", new StdVectorMX(new MX[]{states}), new StdVectorMX(new MX[]{rhs}));
        var options = new Dict();
        options.put("tf", new GenericType(1));
        System.out.println(states.dim_());
        System.out.println(rhs.dim_());
        var intg = integrator("intg", "cvodes", ode, options); // -> triggers error "DAE has wrong number of inputs"; Why?
        System.out.println(intg);
        var result = new StdVectorMX();
        intg.call(new StdVectorMX(new MX[]{MX.vertcat(new StdVectorMX(new MX[]{new MX(0.0), new MX(0.0), new MX(35.0), new MX(30.0)}))}), result);
        System.out.println(result);
    }

    public static void exercise2_1() {
        var X0 = MX.sym("X0", 4);
        var fly1sec = new Function("fly1sec", new StdVectorMX(new MX[]{X0}), new StdVectorMX());
    }

    public static void exercise2_2() {

    }

    public static void exercise2_3() {

    }

    public static void exercise2_4() {

    }

    public static void exercise2_5() {

    }

    public static void exercise3_1() {

    }

    public static void exercise3_2() {

    }

    public static void main(String[] args) {
        // Function solver = nlpsol("solver", "ipopt", nlp);
        // var result = new StdVectorMX();
        // solver.call(new StdVectorMX(new MX[]{MX.vertcat(new StdVectorMX(new MX[]{new MX(2.5), new MX(3.0), new MX(0.75)}))}, new MX(0), new MX(0)), result);
        // System.out.println(result);
        // ---------------------------
        // Dict options = new Dict();
        // option.put("expand", new GenericType(true));
        // option.put("print_level", new GenericType(0));
        // result = new StdVectorMX();
        // solver.call(new StdVectorMX(new MX[]{MX.vertcat(new StdVectorMX(new MX[]{new MX(2.5), new MX(3.0), new MX(0.75)}))}, new MX(0), new MX(0)), result);
        // System.out.println(result);
        // exercise1_1();
        exercise1_2();
    }

}
