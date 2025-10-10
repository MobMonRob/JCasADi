package de.dhbw.rahmlab.casadi.api.jonas_experiments.course.fristBlock;

import de.dhbw.rahmlab.casadi.MxStatic;
import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorStdString;

import static de.dhbw.rahmlab.casadi.impl.casadi.MX.vertcat;

public class CasADiBasicsExercise {

    static MX x = MxStatic.sym("x");
    static MX y = MxStatic.sym("y");

    public static MX f(MX x, MX y) {
        MX w = MxStatic.plus(x, y);
        MX z = MxStatic.times(w, y);
        z = MxStatic.times(new MX(4), z);
        return z;
    }

    public static void withoutCasADiFunctions() {
        var r = f(new MX(1), MxStatic.sin(x));
        System.out.println(r);
    }

    public static void withCasADiFunctions() {
        MX w = MxStatic.plus(x, y);
        MX z = MxStatic.times(w, y);
        z = MxStatic.times(new MX(4), z);
        var input = new StdVectorMX(new MX[]{x, y});
        var output = new StdVectorMX(new MX[]{z});
        var f = new Function("f", input, output);
        var result = new StdVectorMX();
        f.call(new StdVectorMX(new MX[]{new MX(1), MxStatic.sin(x)}), result);
        System.out.println(result);
        System.out.println("------------------------------------------------");
        // -------------------------When does CasADi Function conserve memory--------------------------------------
//        for (int i = 1; i <= 10; i++) {
//            f.call(new StdVectorMX(new MX[]{new MX(1), MxStatic.sin(MxStatic.times(x, new MX(i)))}), result);
//            System.out.println(result);
//        }
        var r = new StdVectorMX(new MX[]{new MX(0)});
        for (int i = 1; i <= 10; i++) {
            f.call(new StdVectorMX(new MX[]{new MX(1), MxStatic.sin(MxStatic.times(x, new MX(i)))}), result);
            r = new StdVectorMX(new MX[]{MX.plus(r.get(0), result.get(0))});
            // Has calls to f appended (f ten times)
            System.out.println(r);
        }
        // By Contrast with the Java Function, no f-calls and everything will be expanded
        // (Java Function will be 10 times in the memory)
        System.out.println("------------------------------------------------");
        var r2 = new MX(0);
        for (int i = 1; i <= 10; i++) {
            r2 = MxStatic.plus(r2, f(new MX(1), MxStatic.sin(MxStatic.times(new MX(i), x))));
            System.out.println(r2);
        }
    }

    public static Double F(double x, double y) {
        return Math.sin(x)*y;
    }

    public static MX F(MX x, MX y) {
        return MxStatic.times(MxStatic.sin(x), y);
    }

    public static void exerciseSheet() {
        MX x = MxStatic.sym("x");
        MX y = MxStatic.sym("y");
        MX p = MxStatic.sym("p", 2);
        MX A = MxStatic.sym("A", 4, 5);
        Function f = new Function("f", new StdVectorMX(new MX[]{x, y}), new StdVectorMX(new MX[]{MX.times(MxStatic.sin(x), y)}));
        Function g = new Function("g", new StdVectorMX(new MX[]{x}), new StdVectorMX(new MX[]{MX.sqrt(x), MxStatic.times(x, x)}));
        Function h = new Function("h", new StdVectorMX(new MX[]{x, y}), new StdVectorMX(new MX[]{MX.times(MxStatic.sin(x), y)})); // Does not work as in the video
        // Cannot find a way to implement lambda-Function F in Java
        // -------
        System.out.println(MxStatic.plus(new MX(5), x).getClass().getName()); // 2
        System.out.println(MxStatic.minus(x, x).getClass().getName()); // 3
        System.out.println(MxStatic.eq(x, x).getClass().getName()); // 4
        System.out.println(MxStatic.eq(x, y).getClass().getName()); // 5
        System.out.println(MxStatic.times(A, x).getClass().getName()); // 6
        System.out.println(MxStatic.times(A, new MX(0)).getClass().getName()); // 7
        System.out.println(MxStatic.gradient(MxStatic.sin(x), x).getClass().getName()); // 8
        System.out.println(MxStatic.gradient(MxStatic.sin(x), x).getClass().getName()); // 9
        System.out.println(p.at(1).getClass().getName()); // 10
        MX[] elementsOfA = new MX[A.get_row().size()];
        for (int i = 0; i < 4; i++) {
            elementsOfA[i] = A.at(i, 0);
        }
        System.out.println(elementsOfA.getClass().getName()); // 11
        StdVectorMX input = new StdVectorMX(new MX[]{new MX(1), new MX(2)});
        StdVectorMX output = new StdVectorMX();
        f.call(input, output);
        System.out.println(output.getClass().getName()); // 12
        input = new StdVectorMX(new MX[]{new MX(1), y});
        f.call(input, output);
        System.out.println(output.getClass().getName()); // 13
        input = new StdVectorMX(new MX[]{new MX(9)});
        g.call(input, output);
        System.out.println(output.getClass().getName()); // 14
        input = new StdVectorMX(new MX[]{new MX(9)});
        g.call(input, output);
        System.out.println(output.get(0).getClass().getName()); // 15
        input = new StdVectorMX(new MX[]{x});
        g.call(input, output);
        System.out.println(output.getClass().getName()); // 16
        input = new StdVectorMX(new MX[]{x});
        g.call(input, output);
        System.out.println(output.get(0).getClass().getName()); // 17
        input = new StdVectorMX(new MX[]{x});
        g.call(input, output);
        System.out.println(output.get(0).at(0).getClass().getName()); // 18
        input = new StdVectorMX(new MX[]{x});
        // h.call(input, output);
        // System.out.println(output.getClass().getName());// 19
        System.out.println(f.expand().getClass().getName()); // 20
        // System.out.println(F().getClass().getName()); // 21
        System.out.println(F(x, y).getClass().getName());// 22
        System.out.println(F(1, 2).getClass().getName());// 23
        // -------
        Function m = new Function("m", new StdVectorMX(new MX[]{new MX(x)}), new StdVectorMX(new MX[]{MX.sin(x)})); // Goal 1
        p.at(0); // Goal 2
        p.at(1); // Goal 3
        MX M = vertcat(new StdVectorMX(new MX[]{x, y})); // Goal 4
        StdVectorDM result = new StdVectorDM();
        f.call(new StdVectorDM(new DM[]{new DM(1), new DM(2)}), result); // Goal 5
        StdVectorMX result1 = new StdVectorMX();
        f.call(new StdVectorMX(new MX[]{p.at(0), p.at(1)}), result1); // Goal 6
        Function square = new Function("square", new StdVectorMX(new MX[]{x, y}), new StdVectorMX(new MX[]{MX.times(MxStatic.times(MxStatic.sin(x), y), MxStatic.times(MxStatic.sin(x), y))})); // Goal 7
        StdVectorMX result2 = new StdVectorMX();
        //h.call(new StdVectorMX(new MX[]{new MX(5)}), result2); // Goal 8
        Function m1 = new Function("m1", new StdVectorMX(new MX[]{x}), new StdVectorMX(new MX[]{new MX(5)})); // Goal 9
        Function m2 = new Function("m2", new StdVectorMX(new MX[]{x}), new StdVectorMX(new MX[]{new MX(MxStatic.sin(x))})); // Goal 10
        Function m3 = new Function("m3", new StdVectorMX(new MX[]{x, new MX()}), new StdVectorMX(new MX[]{new MX(x)})); // Goal 11
        // -------
        SX a = SxStatic.sym("a");
        SX b = SxStatic.sym("b");
        StdVectorSX re = new StdVectorSX();
        Function f2 = new Function("f2", new StdVectorSX(new SX[]{a, b}), new StdVectorSX(new SX[]{new SX(SxStatic.plus(SxStatic.sin(a), b))}), new StdVectorStdString(new String[]{"x", "y"}), new StdVectorStdString(new String[]{"z"}));
        Function g2 = new Function("g2", new StdVectorSX(new SX[]{a}), new StdVectorSX(new SX[]{SX.sqrt(a), SxStatic.pow(a, new SX(2))}), new StdVectorStdString(new String[]{"x"}), new StdVectorStdString(new String[]{"p0", "p1"}));
        // Now functions have labels for input and output
        // Cannot find option to use labels in the code
        //f2.call(new StdVectorSX(new SX[]{new SX(3)}), re);
        //System.out.println(re); // 1 Error
        re = new StdVectorSX();
        f2.call(new StdVectorSX(new SX[]{new SX(3), new SX(4)}), re);
        System.out.println(re.getClass().getName()); // 2
        // 3
        // 4
        // 5
        // 6
        // 7
        // re = new StdVectorSX();
        // f2.call(new StdVectorSX(new SX[]{new SX(3)}), re);
        // System.out.println(re); // 8 Error
        re = new StdVectorSX();
        f2.call(new StdVectorSX(new SX[]{new SX(), new SX()}), re);
        System.out.println(re.getClass().getName()); // 9
        // 10
        re = new StdVectorSX();
        g2.call(new StdVectorSX(new SX[]{new SX(a)}), re);
        System.out.println(re.getClass().getName()); // 11
        // 12
        re = new StdVectorSX();
        g2.call(new StdVectorSX(new SX[]{new SX(3)}), re);
        System.out.println(re.getClass().getName()); // 13
        // 14
        // 15
        // 16
    }

    public static void main(String[] args) {
        // version without CasADi Functions:
        // withoutCasADiFunctions();
        // version with CasADi Functions:
        // withCasADiFunctions();
        exerciseSheet();
    }

}
