package de.dhbw.rahmlab.casadi.api.jonas_experiments.improvements.course.fristBlock;

import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

import static de.dhbw.rahmlab.casadi.impl.casadi.DM.*;
import static de.dhbw.rahmlab.casadi.impl.core__.rootfinder;

/***
 * @author jebbingh
 */
public class RootfindingExercise {

    public static final MX X = MX.sym("X", 2);

    public static DM gDM(DM w) {
        DM x = new DM(w.get_elements().get(0));
        DM y = new DM(w.get_elements().get(1));

        DM g1 = new DM(tanh(minus(rdivide(times(plus(x, new DM(2)), times(y, y)), new DM(25)), new DM(0.5))));
        DM g2 = new DM(plus(plus(sin(x), times(new DM(-0.5), y)), new DM(1)));

        return vertcat(new StdVectorDM(new DM[]{g1, g2}));
    }

    public static MX gMX(MX w) {
        MX x = w.at(0, 0);
        MX y = w.at(1, 0);
        MX g1 = MX.tanh(MX.minus(MX.rdivide(MX.times(MX.plus(x, new MX(2)), MX.times(y, y)), new MX(25)), new MX(0.5)));
        MX g2 = MX.plus(MX.plus(MX.sin(x), MX.times(new MX(-0.5), y)), new MX(1));
        return MX.vertcat(new StdVectorMX(new MX[]{g1, g2}));
    }

    public static DM gMX1(MX w) {
        return null;
    }

    public static DM computeJacobian(DM w) {
        DM resultOfG = gDM(w);
        int numberOfRows = (int) w.rows();
        DM epsilon = new DM(1e-8);
        DM[] jacobian = new DM[numberOfRows];
        for (int i = 0; i < numberOfRows; i++) {
            DM s = initForwardSeed(numberOfRows, i);
            jacobian[i] = rdivide(minus(gDM(plus(w, times(s, epsilon))), resultOfG), epsilon);
        }
        return horzcat(new StdVectorDM(jacobian));
    }

    private static DM initForwardSeed(int numberOfRows, int i) {
        DM[] forwardSeeds = new DM[numberOfRows];
        for (int j = 0; j < numberOfRows; j++) {
            if (j == i) {
                forwardSeeds[j] = new DM(1);
            } else {
                forwardSeeds[j] = new DM(0);
            }
        }
        return vertcat(new StdVectorDM(forwardSeeds));
    }

    public static DM newtonMethod(int steps, DM w) {
        DM result = w;
        for (int i = 0; i < steps; i++) {
            result = minus(result, solve(computeJacobian(result), gDM(result)));
        }
        return result;
    }

    public static Function jf(MX w) {
        return new Function("f", new StdVectorMX(new MX[]{X}), new StdVectorMX(new MX[]{MX.jacobian(gMX(X), X)}));
    }

    public static void main(String[] args) {
        DM x = new DM(-0.8);
        DM y = new DM(2);
        // 1.1
//        System.out.println("Result: " + g(vertcat(new StdVectorDM(new DM[]{x, y}))));
//        System.out.println("Shape: " + g(vertcat(new StdVectorDM(new DM[]{x, y}))).dim_());
        // 1.2
//        System.out.println("Jacobian: " + computeJacobian(vertcat(new StdVectorDM(new DM[]{x, y}))));
        // 1.3
//        System.out.println(newtonMethod(5, vertcat(new StdVectorDM(new DM[]{x, y}))));

        // 2.1
        System.out.println("Shape: " + X.dim_());
        System.out.println("Result: " + gMX(X));
        System.out.println("Type: " + gMX(X).getClass().getName());
        System.out.println(MX.jacobian(gMX(X), X).getClass().getName());
        System.out.println(MX.jacobian(gMX(X), X).dim_());
        // 2.2
        System.out.println(jf(X));
        // Output: f:(i0[2])->(o0[2x2]) MXFunction
        // [2] means a 2-vector
        // [2x2] means a 2-by-2 matrix
        //2.3
        Function f = new Function("f", new StdVectorMX(new MX[]{X}), new StdVectorMX(new MX[]{MX.jacobian(gMX(X), X)}));
        StdVectorDM result = new StdVectorDM();
        StdVectorDM arg = new StdVectorDM(new DM[]{vertcat(new StdVectorDM(new DM[]{x, y}))});
        f.call(arg, result);
        System.out.println(vertcat(result));
        // 2.4
        DM r = vertcat(new StdVectorDM(new DM[]{x, y}));
        StdVectorDM arg2 = new StdVectorDM(new DM[]{vertcat(new StdVectorDM(new DM[]{x, y}))});
        for (int i = 0; i < 5; i++) {
            StdVectorDM e = new StdVectorDM();
            f.call(arg2, e);
            r = minus(r, solve(vertcat(e), gDM(r)));
            arg2 = new StdVectorDM(new DM[]{r});
        }
        System.out.println(r);
        // 3.1
        Function rfp = new Function("rfp", new StdVectorMX(new MX[]{X}), new StdVectorMX(new MX[]{gMX(X)}));
//        Map<String, MX> map = new HashMap<>();
//        map.put("x", X);
//        map.put("g", gMX(X));
        // Finde keine Methode, um Map Objekt zu konvertieren oder SWIGTYPE_p_std__mapT_std__string_casadi__MX_t zu befüllen
//        SWIGTYPE_p_std__mapT_std__string_casadi__MX_t rfp = new SWIGTYPE_p_std__mapT_std__string_casadi__MX_t();
        Function rf = rootfinder("rf", "newton", rfp);
        System.out.println(rf.getClass().getName());
        System.out.println(rf);
        // 3.2
        StdVectorDM re = new StdVectorDM();
        rf.call(arg, re);
        System.out.println(re);
        // 3.3
        System.out.println(rf.stats());
        // 3.4
        Dict opt = new Dict();
        opt.put("print_iteration", new GenericType(true));
        rf = rootfinder("rf", "newton", rfp, opt);
        rf.call(arg, re);
        System.out.println(re);
    }


}
