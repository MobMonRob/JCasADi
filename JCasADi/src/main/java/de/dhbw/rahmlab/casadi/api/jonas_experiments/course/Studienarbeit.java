package de.dhbw.rahmlab.casadi.api.jonas_experiments.course;

import de.dhbw.rahmlab.casadi.api.core.constraints.*;
import de.dhbw.rahmlab.casadi.api.core.problem.NLPProblem;
import de.dhbw.rahmlab.casadi.api.core.problem.NLPProblemBuilder;
import de.dhbw.rahmlab.casadi.api.core.solver.CasADiSolver;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MapStringToMXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.Slice;
import de.dhbw.rahmlab.casadi.impl.core__;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToMX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

public class Studienarbeit {

    public static void main(String[] args) {
        constraintTest();
    }

    public static void test1() {

        MX x = MxStatic.sym("x");
        MX y = MxStatic.sym("y");
        MX expression = MxStatic.times(x, y);

        Function f = new Function("f", new StdVectorMX(new MX[]{x, y}), new StdVectorMX(new MX[]{expression}));

        StdVectorDM input = new StdVectorDM(new DM[]{new DM(1), new DM(2)});
        StdVectorDM output = new StdVectorDM();

        f.call(input, output);

        System.out.println(output.get(0));

    }

    public static void test2() {

        var x = MxStatic.sym("x");
        var v = MxStatic.sym("v");
        var z = MxStatic.vertcat(new StdVectorMX(new MX[]{x, v}));

        var xdot = MxStatic.vertcat(new StdVectorMX(new MX[]{x, new MX(0)}));
        var alg = MxStatic.minus(MxStatic.plus(x, v), new MX(1));

        StdMapStringToMX dae = new StdMapStringToMX();
        dae.put("x", z);
        dae.put("ode", xdot);
        dae.put("alg", alg);

        var F = core__.integrator("f", "idas", dae);

    }

    public static void test3() {

        var matrix = new MX(4, 4);

        var rowSlice = new Slice(1, 2);
        var colSlice = new Slice(2, 4);

        MX submatrix = new MX();

        matrix.get(submatrix, true, rowSlice, colSlice);

        System.out.println(matrix);
        System.out.println(submatrix);

    }

    public static void solverTest3() {

        var matrix = new MXWrapper(4, 4);

        matrix.get("1:2", "2:4");

        MapStringToMXWrapper s = new MapStringToMXWrapper();
        s.put("1:2", new MXWrapper(4, 4));
        s.put("2:4", new MXWrapper(4, 4));

    }

    public static void test4() {

        var values = new StdVectorDouble(new double[]{1, 2, 3});
        DM vector = new DM(values);
        ;
        var adjustedElement = DmStatic.minus(vector.at(0), new DM(2));
        vector.set(adjustedElement, true, new Slice(0));

    }

    public static void constraintTest() {

        var x = new MXWrapper();
        var y = new MXWrapper();
        Constraint constraint = ConstraintBuilder.of(x.multiply(x))
                .cmp(Comparison.LE)
                .rhs(y.add(1))
                .build();
        System.out.println(constraint);

    }

    public static void constraintTest2() {


        var x1 = MXWrapper.sym("x1");
        var x2 = MXWrapper.sym("x2");

        NLPProblem problem = new NLPProblemBuilder()
                .addVariable(x1)
                .addVariable(x2)
                .minimize(x1.subtract(1).pow(2).add(x2.sin()))
                .addConstraint(ExpressionParser.parseConstraint("x1^2 - x2 = 1"))
                .addConstraint(ExpressionParser.parseConstraint("x1^2 + x2 <= 4"))
                .setInitialDecisionVariable(x1, 1.5)
                .setInitialDecisionVariable(x2, 1.25)
                .setSolver(CasADiSolver.CONIC)
                .build();

    }

    public static void addbeispiel() {

//        DM x = new DM();
//        DM result = DmStatic.plus(x, new DM(2));

//        DMWrapper x = new DMWrapper();
//        DMWrapper result = x.add(2);

        // x * y + 2 - y
        // vor :
//        var x = MxStatic.sym("x");
//        var y = MxStatic.sym("y");
//        var expression = MxStatic.minus(MxStatic.plus(MxStatic.times(x, y), new MX(2)), y);

        // Nachher:
        var x = MXWrapper.sym("x");
        var y = MXWrapper.sym("y");
        var expression = x.multiply(y).add(2).subtract(y);

        // Python:
//        x = ca.MX.sym('x')
//        y = ca.MX.sym('y')
//        expression = x * y + 2 - y

    }

    public static void solverTest() {

        var x = MXWrapper.sym("x");
        ScalarConstraint c = new ScalarConstraint(x.multiply(x), Comparison.LE, new MXWrapper(1));

        NLPProblem problem = new NLPProblemBuilder()
                .addVariable(x)
                .addConstraint(c)
                .setSolver(CasADiSolver.CONIC)
                .build();

    }

    public static void solverTest2() {

        Constraint c = ConstraintBuilder
                .of(MXWrapper.sym("x").pow(2))
                .cmp(Comparison.LE)
                .rhs(MXWrapper.sym("y").add(1))
                .build();


    }

    public static void abschlussMitExpressionParser() {

        var x1 = MXWrapper.sym("x1");
        var x2 = MXWrapper.sym("x2");

        NLPProblem nlp = new NLPProblemBuilder()
                .addVariable(x1)
                .addVariable(x2)
                .minimize(x1.subtract(1).pow(2).add(x2.sin()))
                .addConstraint(ExpressionParser.parseConstraint("x1^2 - x2 = 1"))
                .addConstraint(ExpressionParser.parseConstraint("x1^2 + x2 <= 4"))
                .setInitialDecisionVariable(x1, 1.5)
                .setInitialDecisionVariable(x2, 1.25)
                .setSolver(CasADiSolver.CONIC)
                .build();

        var result = nlp.solve();

        System.out.println(result.value(x1));
        System.out.println(result.value(x2));

    }

    public static void abschlussOhnexpressionParser() {

        var x1 = MXWrapper.sym("x1");
        var x2 = MXWrapper.sym("x2");

        NLPProblem nlp = new NLPProblemBuilder()
                .addVariable(x1)
                .addVariable(x2)
                .minimize(x1.subtract(1).pow(2).add(x2.sin()))
                .addConstraint(x1.pow(2).subtract(x2), Comparison.EQ, new MXWrapper(1))
                .addConstraint(x1.pow(2).add(x2), Comparison.LE, new MXWrapper(4))
                .setInitialDecisionVariable(x1, 1.5)
                .setInitialDecisionVariable(x2, 1.25)
                .setSolver(CasADiSolver.CONIC)
                .build();

        var result = nlp.solve();

        System.out.println(result.value(x1));
        System.out.println(result.value(x2));

    }

    public static void test223() {

        var vector = new DMWrapper(1, 2, 3);
        vector.set(vector.at(0).subtract(2), new NumberWrapper(2));

    }

}
