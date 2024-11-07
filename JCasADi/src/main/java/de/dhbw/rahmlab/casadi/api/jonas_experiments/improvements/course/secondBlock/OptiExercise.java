package de.dhbw.rahmlab.casadi.api.jonas_experiments.improvements.course.secondBlock;

import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

import static de.dhbw.rahmlab.casadi.impl.casadi.MX.sum1;

public class OptiExercise {

    static long N_numerical = 25;
    static MX N = new MX(25);
    static MX m = MX.rdivide(new MX(40), N); // mass [kg]
    static MX D = MX.times(new MX(70), N); // spring constant [J/m^2]
    static MX g = new MX(9.81); // gravitational constant [m/s^2]
    static MX L = MX.rdivide(new MX(5), N); // reference length [m]

    public static void introductionExample() {
        var opti = new Opti();
        var y = opti.variable();
        var x = opti.variable();

        // // Although Opti exists, it is still difficult and confusing to define an expression
        // Confusing/difficult to create
        var expression = MX.plus(MX.pow(MX.minus(new MX(1), x), new MX(2)), MX.pow(MX.minus(y, MX.pow(x, new MX(2))), new MX(2)));
        var constraint = MX.eq(MX.plus(MX.pow(x, new MX(2)), MX.pow(y, new MX(2))), new MX(1));

        opti.minimize(expression);
        opti.subject_to(constraint);

        var dict = new Dict();
        dict.put("error_on_fail", new GenericType(false));

        opti.solver("qrsqp", dict);
        var sol = opti.solve();

        System.out.println(sol.value(MX.vertcat(new StdVectorMX(new MX[]{x, y}))));
    }
    // Output:
    // This is casadi::QRQP
    //Number of variables:                               2
    //Number of constraints:                             1
    //Number of nonzeros in H:                           4
    //Number of nonzeros in A:                           2
    //Number of nonzeros in KKT:                         9
    //Number of nonzeros in QR(V):                       6
    //Number of nonzeros in QR(R):                       6
    //-------------------------------------------
    //This is casadi::Qrsqp.
    //Using exact Hessian
    //Number of variables:                               2
    //Number of constraints:                             1
    //Number of nonzeros in constraint Jacobian:         2
    //Number of nonzeros in Lagrangian Hessian:          4
    //
    //iter      objective    inf_pr    inf_du     ||d||  lg(rg) ls
    //   0   1.000000e+00  1.00e+00  2.00e+00  0.00e+00       -  0
    //Function qpsol (0x7fb3a0352ff0)
    //Input 0 (h):
    //[[2, -0],
    // [-0, 2]]
    //Input 1 (g): [0, -2]
    //Input 2 (a): [[0, 0]]
    //Input 3 (lba): 1
    //Input 4 (uba): 1
    //Input 5 (lbx): [-inf, -inf]
    //Input 6 (ubx): [inf, inf]
    //Input 7 (x0): [0, 0]
    //Input 8 (lam_x0): [0, 0]
    //Input 9 (lam_a0): 2.22507e-308
    //Input 10 (q): NULL
    //Input 11 (p): NULL
    // Iter  Sing        fk      |pr|   con      |du|   var     min_R   con  last_tau  Note
    //    0     1         0         1     2         2     1         0     2         0
    //Exception in thread "main" java.lang.RuntimeException: Error in Opti::solve [OptiNode] at .../casadi/core/optistack.cpp:157:
    //Error in Function::call for 'solver' [Qrsqp] at .../casadi/core/function.cpp:1401:
    //Error in Function::call for 'solver' [Qrsqp] at .../casadi/core/function.cpp:330:
    //Error in Function::operator() for 'qpsol' [Qrqp] at .../casadi/core/function.cpp:1482:
    //.../casadi/core/conic.cpp:537: conic process failed. Set 'error_on_fail' option to false to ignore this error.
    //	at de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Opti_solve(Native Method)
    //	at de.dhbw.rahmlab.casadi.impl.casadi.Opti.solve(Opti.java:397)
    //	at de.dhbw.rahmlab.casadi.api.jonas_experiments.improvements.course.secondBlock.OptiExercise.main(OptiExercise.java:27)

    // But should be ans = 0.8082   0.5889

    public static void presentation() {
        var opti = new Opti();

        var x = opti.variable(10);
        var y = opti.variable();

        opti.subject_to(MX.eq(y, new MX(3)));
        // opti.subject_to(MX.ge(MX.vertcat(new StdVectorMX(new MX[]{x, y})), MX.vertcat(new StdVectorMX(new MX[]{new MX(0), new MX(2)}))));

        opti.minimize(MX.cos(sum1(MX.times(new MX(10), x))));

        System.out.println(opti);
    }

    public static void presentation1() {
        var opti = new Opti();

        var x = opti.variable();
        var p = opti.parameter();

        opti.subject_to();
        opti.subject_to(MX.eq(MX.sin(x), p));

        opti.set_value(p, new DM(0.2));
        opti.solver("qrsqp");
        var sol = opti.solve();

        System.out.println(sol.value(x)); // correct values
    }

    public static void exercise1_1and1_2() {
        var opti = new Opti();
        var x = opti.variable(N_numerical);
        var y = opti.variable(N_numerical);

        var sum = MX.sum1(MX.plus(MX.pow(MX.diff(x), new MX(2)), MX.pow(MX.diff(y), new MX(2))));
        var V = MX.times(MX.times(new MX(0.5), D), sum);
        V = MX.plus(V, MX.times(g, MX.sum1(MX.times(m, y))));

        opti.minimize(V);
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(0), y.at(0)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(-2), new MX(0)}))));
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(-1), y.at(-1)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(2), new MX(0)}))));
        opti.solver("qrsqp");
        var sol = opti.solve();
        System.out.println("1.1: " + sol.value(V)); // -> correct value

        System.out.println("1.2: ");
        MX.hessian(opti.f(), opti.x()).at(0).sparsity().spy(); // -> different output as python (only 1 star)
        // We observe a band structure: there is only coupling between immediately neighbouring points in the objective.
        // We also note that there is no coupling between x and y.

        var lag = MX.diag(MX.vertcat(new StdVectorMX(new MX[]{MX.plus(opti.f(), opti.lam_g().T()), opti.g()})));
        System.out.println(lag);
    }

    public static void main(String[] args) {
        exercise1_1and1_2();
    }

    // Notes:
    // - clearing constraints of a problem: opti.subject_to();

}
