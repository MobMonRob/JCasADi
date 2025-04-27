package de.dhbw.rahmlab.casadi.api.jonas_experiments.course;

import de.dhbw.rahmlab.casadi.api.core.builder.ConstraintBuilder;
import de.dhbw.rahmlab.casadi.api.core.constraints.Comparison;
import de.dhbw.rahmlab.casadi.api.core.constraints.Constraint;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
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

        MX x = MX.sym("x");
        MX y = MX.sym("y");
        MX expression = MX.times(x, y);

        Function f = new Function("f", new StdVectorMX(new MX[]{x, y}), new StdVectorMX(new MX[]{expression}));

        StdVectorDM input = new StdVectorDM(new DM[]{new DM(1), new DM(2)});
        StdVectorDM output = new StdVectorDM();

        f.call(input, output);

        System.out.println(output.get(0));

    }

    public static void test2() {

        var x = MX.sym("x");
        var v = MX.sym("v");
        var z = MX.vertcat(new StdVectorMX(new MX[]{x, v}));

        var xdot = MX.vertcat(new StdVectorMX(new MX[]{x, new MX(0)}));
        var alg = MX.minus(MX.plus(x, v), new MX(1));

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

    public static void test4() {

        var values = new StdVectorDouble(new double[]{1, 2, 3});
        var vector = new DM(values);

        var adjustedElement = DM.minus(vector.at(0), new DM(2));
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

}
