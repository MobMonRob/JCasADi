package de.dhbw.rahmlab.casadi.api.jonas_experiments.course.fristBlock;

import de.dhbw.rahmlab.casadi.api.core.builder.DMBuilder;
import de.dhbw.rahmlab.casadi.api.core.builder.MXBuilder;
import de.dhbw.rahmlab.casadi.api.core.problem.NLPProblem;
import de.dhbw.rahmlab.casadi.api.core.solver.CasADiSolver;
import de.dhbw.rahmlab.casadi.api.core.utils.CasADiMathUtils;
import de.dhbw.rahmlab.casadi.api.core.utils.ConcatenationUtils;
import de.dhbw.rahmlab.casadi.api.core.utils.DMUtils;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.MapStringToDMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.MapStringToSXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXSubMatrixWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

import java.util.HashMap;

public class NonlinearProgrammingWithAPI {

    public static void main(String[] args) throws Exception {
        var N = 25;
        var m = 40/(float)N;
        var D = 70*N;
        var g = 9.81;
        var L = 5/(float)N;

        var problem = new NLPProblem();

        var x = problem.addVariable(N);
        var y = problem.addVariable(N);

        var dx = x.diff().pow(2);
        var dy = y.diff().pow(2);

        var sumDiffs = dx.add(dy);
        var V = new MXWrapper(0.5).multiply(D).multiply(sumDiffs.sum1()); // V = 0.5 * D * sum1(diff(x)**2 + diff(y)**2)

        // otherTests();
        // test();
        // test2();
        // test4();
        test123();
    }

    public static void otherTests() throws Exception {
        var m = 0.04593;
        var g = 9.81;
        var c = 17.5e-5;

        DMUtils.setPrecision(6);

        var p = new MXBuilder().setName("p").setRows(2).buildSymbolic();
        var v = new MXBuilder().setName("v").setRows(2).buildSymbolic();
        System.out.println(p);
        System.out.println(v);

        var states = ConcatenationUtils.vertcat(p, v);

        var speed = v.norm2();
        System.out.println(speed);

        var newC = new MXWrapper(c).negate();
        System.out.println(newC);
        var rhs = ConcatenationUtils.vertcat(v, newC.multiply(speed).multiply(v).divide(m));
        System.out.println(newC.multiply(speed).multiply(v).divide(m));
        System.out.println(rhs);

        System.out.println(rhs.rows() + " " + rhs.columns());
        //rhs.get(true, 3).subtract(g);
        System.out.println(rhs);

        System.out.println("--------");
        var N = 25;
        var opti = new NLPProblem();
        var z = opti.addVariable(2*N, 1);
        System.out.println("z = " + z);
        var x = z.get("::2");
        System.out.println("x = " + x);
        var y = z.get("1::2");
        System.out.println("y = " + y);
    }

    public static void test() {
        var dm = new DMBuilder().setValues(2, 3, 4, 5, 6, 3, 3).build();

        System.out.println("DM: " + dm);

        var slice = dm.get("0:2:3");
        System.out.println("Slice: " + slice);
    }
    
    public static void test2() {
        var map = new HashMap<String, SXWrapper>();
        map.put("1", new SXWrapper());
        map.put("2", new SXWrapper());
        map.put("3", new SXWrapper());
        map.put("4", new SXSubMatrixWrapper(new SXWrapper(1, 2, 4, 5), 0, 0));
        MapStringToSXWrapper test1 = new MapStringToSXWrapper(map);

        System.out.println(test1.get("1"));
        System.out.println(test1.get("4"));
    }

    public static void test3() {
        MapStringToDMWrapper t = new MapStringToDMWrapper();
        t.put("1", new DMWrapper(2));
    }

    public static void test4() throws Exception {
        var N = 25;
        var m = 40/(float)N;
        var D = 70*N;
        var g = 9.81;
        var L = 5/(float)N;

        var opti = new NLPProblem();
        var x = opti.addVariable(N);
        var y = opti.addVariable(N);

        var dx = x.diff().pow(2);
        var dy = y.diff().pow(2);
        var sumDiffs = dx.add(dy).sqrt().subtract(L).pow(2).sum1();
        var V = sumDiffs.multiply(0.5).multiply(D);
        V = new MXWrapper(V).multiply(y).sum1().multiply(g).add(V);

        opti.minimize(V);

        opti.addConstraints(ConcatenationUtils.vertcat(x.at(0), y.at(0)).eq(ConcatenationUtils.vertcat(new MXWrapper(-2), new MXWrapper(0))));
        opti.addConstraints(ConcatenationUtils.vertcat(x.at(-1), y.at(-1)).eq(ConcatenationUtils.vertcat(new MXWrapper(2), new MXWrapper(0))));

        opti.setSolver(CasADiSolver.QRSQP);

        opti.setInitialDecisionVariable(x, CasADiMathUtils.linspace(-2, 2, N));

        var sol = opti.solve();
        System.out.println("Sol: " + sol.toString());

    }

    public static void test123() {

        MX x = MX.sym("x");
        MX y = MX.sym("y");
        MX expression = MX.times(x, y);

        Function f = new Function("f", new StdVectorMX(new MX[]{x, y}), new StdVectorMX(new MX[]{expression}));

        StdVectorMX input = new StdVectorMX(new MX[]{new MX(1), new MX(2)});
        StdVectorMX output = new StdVectorMX();

        f.call(input, output);

        System.out.println(output);

    }

}
