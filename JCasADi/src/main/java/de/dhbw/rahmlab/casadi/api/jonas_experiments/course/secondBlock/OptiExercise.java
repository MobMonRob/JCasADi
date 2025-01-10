package de.dhbw.rahmlab.casadi.api.jonas_experiments.course.secondBlock;

import de.dhbw.rahmlab.casadi.api.core.builder.MXBuilder;
import de.dhbw.rahmlab.casadi.api.core.wrapper.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.MXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

import static de.dhbw.rahmlab.casadi.impl.casadi.MX.*;

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

    public static void exercise1_1and1_2and1_3() {
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

        System.out.println("1.3: ");
        var lag = MX.plus(opti.f(), MX.mtimes(opti.lam_g().T(), opti.g()));
        System.out.println(lag); // -> same output as Python

        var grad_lag = sol.value(gradient(lag, opti.x()));
        System.out.println(DM.norm_2(grad_lag)); // -> minimal difference
        // Python: 1.833083207755712e-12
        // Java:   1.03145e-09
    }

    public static void exercise2_1() {
        var opti = new Opti();

        var x = opti.variable(N_numerical);
        var y = opti.variable(N_numerical);

        var sum = MX.sum1(MX.pow(MX.minus(MX.sqrt(MX.plus(MX.pow(diff(x), new MX(2)), MX.pow(diff(y), new MX(2)))), L), new MX(2)));
        var V = MX.times(MX.times(new MX(0.5), D), sum);
        V = MX.plus(V, MX.times(g, MX.sum1(MX.times(m, y))));

        opti.minimize(V);
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(0), y.at(0)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(-2), new MX(0)}))));
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(-1), y.at(-1)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(2), new MX(0)}))));
        opti.solver("qrsqp");

        try {
            var sol = opti.solve();
            System.out.println("2.1: ");
            System.out.println(sol);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Throws exception, but dont know if its the same one as in the python code
        // maybe because through qrsqp
    }

    public static void exercise2_1band2_2() {
        var opti = new Opti();

        var x = opti.variable(N_numerical);
        var y = opti.variable(N_numerical);

        var sum = MX.sum1(MX.pow(MX.minus(MX.sqrt(MX.plus(MX.pow(diff(x), new MX(2)), MX.pow(diff(y), new MX(2)))), L), new MX(2)));
        var V = MX.times(MX.times(new MX(0.5), D), sum);
        V = MX.plus(V, MX.times(g, MX.sum1(MX.times(m, y))));

        opti.minimize(V);
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(0), y.at(0)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(-2), new MX(0)}))));
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(-1), y.at(-1)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(2), new MX(0)}))));
        opti.solver("qrsqp");

        opti.set_initial(x, lineSpace(-2.0, 2.0));
        opti.set_initial(y, DM.zeros(N_numerical)); // test

        var sol = opti.solve();

        DM xValues = sol.value(x);
        DM yValues = sol.value(y);

        System.out.println("2.1: ");
        System.out.println(xValues); // -> different values
        System.out.println(yValues); // -> different values

        System.out.println("---- 2.2 ----");
        hessian(opti.f(), opti.x()).at(0).sparsity().spy(); // -> different output as python (only 1 star)
    }

    public static void exercise2_2() {
        var opti = new Opti();

        var z = opti.variable(2*N_numerical, 1);
        var x = extractEverySecondElement(z, 0); // Elemente mit geraden Indizes
        var y = extractEverySecondElement(z, 1);

        var sum = MX.sum1(MX.pow(MX.minus(MX.sqrt(MX.plus(MX.pow(diff(x), new MX(2)), MX.pow(diff(y), new MX(2)))), L), new MX(2)));
        var V = MX.times(MX.times(new MX(0.5), D), sum);
        V = MX.plus(V, MX.times(g, MX.sum1(MX.times(m, y))));

        opti.minimize(V);
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(0), y.at(0)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(-2), new MX(0)}))));
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(-1), y.at(-1)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(2), new MX(0)}))));
        opti.solver("ipopt");

        opti.set_initial(x, lineSpace(-2.0, 2.0));

        var sol = opti.solve();

        DM xValues = sol.value(x);
        DM yValues = sol.value(y);
        System.out.println("2.2: ");
        System.out.println(xValues); // -> different values
        System.out.println(yValues); // -> different values

        hessian(opti.f(), opti.x()).at(0).sparsity().spy(); // -> different output as python (only 1 star)
    }

    // No implementation of task 2.3 because task focus on visualization

    public static void exercise3_1() {
        System.out.println("3.1: ");
        var opti = new Opti();

        var x = opti.variable(N_numerical);
        var y = opti.variable(N_numerical);

        var sum = MX.sum1(MX.times(m, y));
        opti.minimize(MX.times(g, sum));

        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{MX.plus(MX.pow(diff(x), new MX(2)), MX.pow(diff(y), new MX(2)))})), MX.vertcat(new StdVectorMX(new MX[]{MX.pow(L, new MX(2))}))));
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(0), y.at(0)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(-2), new MX(0)}))));
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(-1), y.at(-1)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(2), new MX(0)}))));

        opti.set_initial(x, lineSpace(-2.0, 2.0));
        opti.set_initial(y, DM.times(new DM(-1), DM.sin(lineSpace(0, Math.PI))));

        opti.solver("qrsqp");

        var sol = opti.solve();

        var J = jacobian(opti.g(), opti.x());

        // -> different output in Python
        System.out.println(opti.debug().value(J));
        DM JValues = sol.value(J);
        System.out.println(JValues);
        // double[][] JArray = JValues.toString(); -> Two Array method is missing
        // RealMatrix JMatrix = new Array2DRowRealMatrix(JArray);
        // int rank = new SingularValueDecomposition(JMatrix).getRank();

        // System.out.println("Rank of Jacobian Matrix: " + rank);
    }

    public static void exercise3_2() {
        System.out.println("3.2: ");
        var opti = new Opti();

        var x = opti.variable(N_numerical);
        var y = opti.variable(N_numerical);

        var sum = MX.sum1(MX.times(m, y));
        opti.minimize(MX.times(g, sum));

        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{MX.plus(MX.pow(diff(x), new MX(2)), MX.pow(diff(y), new MX(2)))})), MX.vertcat(new StdVectorMX(new MX[]{MX.pow(L, new MX(2))}))));
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(0), y.at(0)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(-2), new MX(0)}))));
        opti.subject_to(MX.eq(MX.vertcat(new StdVectorMX(new MX[]{x.at(-1), y.at(-1)})), MX.vertcat(new StdVectorMX(new MX[]{new MX(2), new MX(0)}))));

        opti.set_initial(x, lineSpace(-2.0, 2.0));

        opti.solver("qrsqp");

        try {
            var sol = opti.solve();
            var J = jacobian(opti.g(), opti.x());
            System.out.println(J.dim_());

            System.out.println(opti.debug().value(J));
            DM JValues = sol.value(J);
            System.out.println(JValues);

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        // -> different output in python

        var J = jacobian(opti.g(), opti.x());
        System.out.println(J.dim_()); // -> Same shape as python

        // double[][] JArray = JValues.toString(); -> Two Array method is missing
        // RealMatrix JMatrix = new Array2DRowRealMatrix(JArray);
        // int rank = new SingularValueDecomposition(JMatrix).getRank();

        // System.out.println("Rank of Jacobian Matrix: " + rank);
    }

    private static MX extractEverySecondElement(MX z, int startIndex) {
        int length = (int) z.size1_();
        MX[] result = new MX[(length - startIndex + 1) / 2];

        for (int i = startIndex, j = 0; i < length; i += 2, j++) {
            result[j] = z.at(i);
        }

        return MX.vertcat(new StdVectorMX(result));
    }

    private static DM lineSpace(double start, double end) {
        DM[] linspaceValues = new DM[(int) N_numerical];
        for (int i = 0; i < N_numerical; i++) {
            linspaceValues[i] = DM.plus(new DM(start), DM.times(new DM(i), DM.rdivide(DM.minus(new DM(end), new DM(start)), DM.minus(new DM(N_numerical), new DM(1)))));
        }

        return DM.vertcat(new StdVectorDM(linspaceValues));
    }

    public static void main(String[] args) {
//        exercise1_1and1_2and1_3();
//        exercise2_1();
//        exercise2_1band2_2();
//        exercise2_2();
 //       exercise3_1();
        //exercise3_2();
        MX a = new MX(2);
        MX b = new MX(2);
        MX c = MX.plus(a, b);
        System.out.println(c);
        MXWrapper wrapper = new MXBuilder().setValue(0.2).build();
        MXWrapper wrapper2 = new MXBuilder().setValue(0.2).build();
        MXWrapper sum = wrapper.add(wrapper2);
        System.out.println(sum.getCasADiObject());

        MXWrapper x = new MXBuilder().setName("X").setDimensions(2, 2).build();
        MXWrapper y = new MXBuilder().setName("Y").setDimensions(2, 2).build();

        MXWrapper sum1 = x.add(y);

        System.out.println(sum1.getCasADiObject());

        MXWrapper z = new MXBuilder().setName("Z").setDimensions(3, 1).build();
        MXWrapper objective = z.T().mtimes(z);

        System.out.println(objective.getCasADiObject());

        MX z1 = MX.sym("Z", 3);
        MX result = MX.mtimes(z1.T(), z1);
        System.out.println(result);

        MXWrapper matrix = new MXBuilder().setValues(new double[]{1.0, 2.0, 3.0, 4.0}).buildFromValues();
        System.out.println(matrix.getCasADiObject());

        MX matrix2 = new MX(new StdVectorDouble(new double[]{1.0, 2.0, 3.0, 4.0}));
        System.out.println(matrix2);

        MXWrapper x1 = new MXBuilder().setName("X").setDimensions(2, 2).build();
        MXWrapper y1 = new MXBuilder().setName("Y").setDimensions(2, 2).build();

        MXVector vectorMX = new MXVector().add(x1).add(y1);

        for(int i = 0; i < vectorMX.size(); i++) {
            System.out.println(vectorMX.get(i).getCasADiObject());
        }

        StdVectorMX stdVectorMX = new StdVectorMX(new MX[]{x1.getCasADiObject(), y1.getCasADiObject()});

        for(int i = 0; i < stdVectorMX.size(); i++) {
            System.out.println(stdVectorMX.get(i));
        }
    }

    // Notes:
    // - clearing constraints of a problem: opti.subject_to();

}
