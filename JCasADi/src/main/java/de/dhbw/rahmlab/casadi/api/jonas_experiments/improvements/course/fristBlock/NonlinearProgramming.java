package de.dhbw.rahmlab.casadi.api.jonas_experiments.improvements.course.fristBlock;

import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToMX;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToSparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.dhbw.rahmlab.casadi.impl.core__.conic;
import static de.dhbw.rahmlab.casadi.impl.core__.rootfinder;

public class NonlinearProgramming {

    static List<MX> mx = new ArrayList<>();
    // 1.1
    static MX x = MX.sym("x", 2);
    // Functions as expressions:
    static MX f_ex = MX.plus(MX.pow(x.at(0), new MX(2)), MX.pow(MX.tanh(x.at(1)), new MX(2)));
    static MX g_ex = MX.plus(MX.cos(MX.plus(x.at(0), x.at(1))), new MX(0.5));
    static MX h_ex = MX.plus(MX.sin(x.at(0)), new MX(0.5));

    // 1.2
    public static MX solution() {
        MX lambd = MX.sym("lambd");
        MX x0 = MX.vertcat(new StdVectorMX(new MX[]{new MX(-0.5), new MX(-1.8)}));
        MX nu = MX.sym("nu");
        MX lag = MX.plus(MX.plus(f_ex, MX.times(lambd, g_ex)), MX.times(nu, h_ex));
        Function lagf = new Function("lagf", new StdVectorMX(new MX[]{x, lambd, nu}), new StdVectorMX(new MX[]{lag}));
        StdVectorMX result = new StdVectorMX(new MX[]{});
        lagf.call(new StdVectorMX(new MX[]{x0, new MX(2), new MX(3)}), result);
        return result.get(0).at(0,0);
    }

    // 1.3
    public static MX solution1_3() {
        MX lambd = MX.sym("lambd");
        MX x0 = MX.vertcat(new StdVectorMX(new MX[]{new MX(-0.5), new MX(-1.8)}));
        MX lambda0 = new MX(0.);
        MX nu0 = new MX(0);
        MX nu = MX.sym("nu");
        MX lag = MX.plus(MX.plus(f_ex, MX.times(lambd, g_ex)), MX.times(nu, h_ex));
        Function QPF = new Function("QPF", new StdVectorMX(new MX[]{x, lambd, nu}), new StdVectorMX(new MX[]{f_ex, g_ex, h_ex, MX.gradient(f_ex, x), MX.hessian(lag, x), MX.jacobian(g_ex, x), MX.jacobian(h_ex, x)}));
        // 1.4
        StdVectorMX result = new StdVectorMX(new MX[]{});
        QPF.call(new StdVectorMX(new MX[]{x0, lambda0, nu0}), result);
        DM.set_precision(16);
        mx = result.stream().toList();
        return result.stream().toList().get(4);
    }

    public static void main(String[] args) {
        // 1.2
        MX x0 = MX.vertcat(new StdVectorMX(new MX[]{new MX(-0.5), new MX(-1.8)}));
        Function l = new Function("l", new StdVectorMX(new MX[]{x}), new StdVectorMX(new MX[]{MX.plus(MX.plus(f_ex, MX.times(new MX(2), g_ex)), MX.times(new MX(3), h_ex))}));
        StdVectorMX result = new StdVectorMX(new MX[]{});
        l.call(new StdVectorMX(new MX[]{x0}), result);
        System.out.println(result.get(0).at(0,0));
        System.out.println("-----");
        System.out.println("----------- 1.4 -----------");

        // 1.4
        for (int i = 0; i < solution1_3().columns(); i++) {
            for (int j = 0; j < solution1_3().rows(); j++) {
                System.out.print(solution1_3().at(j,i) + " ");
            }
            System.out.println();
        }
        System.out.println("-----");
        System.out.println("----------- 1.5 -----------");
//        for (int i = 0; i < mx.size(); i++) {
//            System.out.println(mx.get(i).dim_());
//            if (i == 3) {
//                System.out.println(mx.get(i).at(0,0));
//                System.out.println(mx.get(i).at(1,0));
//            } else if (i == 4) {
//                System.out.print(mx.get(i).at(0,0) + " ");
//                System.out.println(mx.get(i).at(0,1));
//                System.out.print(mx.get(i).at(1,0) + " ");
//                System.out.println(mx.get(i).at(1,1));
//            } else if (i == 5 || i == 6) {
//                System.out.print(mx.get(i).at(0,0) + " ");
//                System.out.println(mx.get(i).at(0,1));
//            } else {
//                System.out.println(mx.get(i).at(0,0));
//            }
//        }
        /**
         * Output:
         * nlp_f
         * 1x1
         * 1.14644
         *
         * nlp_g
         * 1x1
         * -0.166276
         *
         * nlp_h
         * 1x1
         * 0.0205745
         *
         * nlp_grad_f
         * 2x1
         * -1
         * -0.196099
         *
         * nlp_hess_l
         * 2x2
         * 2 0
         * 0 -0.349887
         *
         * nlp_jac_g
         * 1x2
         * 0.745705 0.745705
         *
         * nlp_jac_h
         * 1x2
         * 0.877583 00
         */

        // 1.5
        MX H = mx.get(4);
        MX G = mx.get(3);
        MX A = MX.vertcat(new StdVectorMX(new MX[]{mx.get(5), mx.get(6)}));
        MX lba = MX.vertcat(new StdVectorMX(new MX[]{MX.times(new MX(-1), mx.get(1)), MX.times(new MX(-1), MX.inf())}));
        MX uba = MX.vertcat(new StdVectorMX(new MX[]{MX.times(new MX(-1), mx.get(1)), MX.times(new MX(-1), mx.get(2))}));
        System.out.println("---- Dim:");
        System.out.println(H.dim_());
        System.out.println(G.dim_());
        System.out.println(A.dim_());
        System.out.println(lba.dim_());
        System.out.println(uba.dim_());
        System.out.println("----- Test: ");
        System.out.println(H);
        System.out.println(G);
        System.out.println(A);
        System.out.println(lba);
        System.out.println(uba);
        System.out.println("---- Formatiert: ");
        System.out.print("[");
        for (int i = 0; i < H.columns(); i++) {
            System.out.print("[");
            for (int j = 0; j < H.rows(); j++) {
                System.out.print(H.at(i,j) + " ");
            }
            System.out.println("]");
        }
        System.out.println("]");
        System.out.print("[");
        for (int i = 0; i < G.rows(); i++) {
            System.out.print(G.at(i,0) + " ");
        }
        System.out.println("]");
        System.out.print("[");
        for (int i = 0; i < A.columns(); i++) {
            System.out.print("[");
            for (int j = 0; j < A.rows(); j++) {
                System.out.print(A.at(i,j) + " ");
            }
            System.out.println("]");
        }
        System.out.println("]");
        System.out.print("[");
        for (int i = 0; i < lba.rows(); i++) {
            System.out.print(lba.at(i,0) + " ");
        }
        System.out.println("]");
        System.out.print("[");
        for (int i = 0; i < uba.rows(); i++) {
            System.out.print(uba.at(i,0) + " ");
        }
        System.out.println("]");
        System.out.println("----------- 1.6 -----------");

        // 1.6
        System.out.println("H: ");
        System.out.println(H.sparsity());
        H.sparsity().spy();
        System.out.println("A: ");
        System.out.println(A.sparsity());
        System.out.println("----------- 1.7 -----------");

        // 1.7
        Map<String, Sparsity> qp_map = new HashMap<>();
        qp_map.put("h", H.sparsity());
        qp_map.put("a", A.sparsity());
        var qp_struct = new StdMapStringToSparsity(qp_map);
        Function solver = conic("solver", "qrqp", qp_struct);
        System.out.println(solver.getClass().getName());
        System.out.println("----------- 1.8 -----------");

        // 1.8
        System.out.println(solver);
        var re = new StdMapStringToMX();

        Map<String, MX> input_map = new HashMap<>();
        input_map.put("h", H);
        input_map.put("g", G);
        input_map.put("a", A);
        input_map.put("lba", lba);
        input_map.put("uba", uba);
        var input = new StdMapStringToMX(input_map);

        // var in = new StdVectorMX(new MX[]{H, G, A, lba, uba});
        solver.call(input, re);
        System.out.println("Res: " + re);
        var dx = re.get("x");
        var lambd = re.get("lam_a").at(0);
        var nu = re.get("lam_a").at(1);
        System.out.println(dx);
        System.out.println(lambd);
        System.out.println(nu);
        System.out.println("----------- 1.9 -----------");

        // 1.9
        var lambd2 = MX.sym("lambd");
        var nu2 = MX.sym("nu");
        MX x01 = MX.vertcat(new StdVectorMX(new MX[]{new MX(-0.5), new MX(-1.8)}));
        MX lambda0 = new MX(0);
        MX nu0 = new MX(0);
        MX lag = MX.plus(MX.plus(f_ex, MX.times(lambd2, g_ex)), MX.times(nu2, h_ex));
        Function QPF = new Function("QPF", new StdVectorMX(new MX[]{x, lambd2, nu2}), new StdVectorMX(new MX[]{f_ex, g_ex, h_ex, MX.gradient(f_ex, x), MX.hessian(lag, x).at(0), MX.jacobian(g_ex, x), MX.jacobian(h_ex, x)}));
        Dict dict = new Dict();
        dict.put("print_iter", new GenericType(false));
        solver = conic("solver", "qrqp", qp_struct, dict);
        for (int i = 0; i < 4; i++) {
            StdVectorMX result1 = new StdVectorMX(new MX[]{});
            QPF.call(new StdVectorMX(new MX[]{x0, lambda0, nu0}), result1);
            MX H1 = result1.get(4);
            MX G1 = result1.get(3);
            MX A1 = MX.vertcat(new StdVectorMX(new MX[]{result1.get(5), result1.get(6)}));
            MX lba1 = MX.vertcat(new StdVectorMX(new MX[]{MX.times(new MX(-1), result1.get(1)), MX.times(new MX(-1), MX.inf())}));
            MX uba1 = MX.vertcat(new StdVectorMX(new MX[]{MX.times(new MX(-1), result1.get(1)), MX.times(new MX(-1), result1.get(2))}));

            var re1 = new StdMapStringToMX();

            Map<String, MX> input_map1 = new HashMap<>();
            input_map.put("h", H1);
            input_map.put("g", G1);
            input_map.put("a", A1);
            input_map.put("lba", lba1);
            input_map.put("uba", uba1);
            var input1 = new StdMapStringToMX(input_map);

            var in1 = new StdVectorMX(new MX[]{H1, G1, A1, lba1, uba1});
            solver.call(input1, re1);
            var dx1 = re1.get("x");
            lambda0 = re1.get("lam_a").at(0);
            nu0 = re1.get("lam_a").at(1);
            x01 = MX.plus(x01, dx1);
            System.out.println("x = " + x01);
        }
        System.out.println("----------- 2.1 -----------");

        // 2.1
        System.out.println("4 equations, 4 unknowns");
        System.out.println("rootfinder x:  [x;lambda;nu]");
        System.out.println("rootfinder p: tau (something we may tune)");
        System.out.println("rootfinder g: nu*h+tau");
        System.out.println("----------- 2.2 -----------");

        // 2.2
        MX x = MX.sym("x", 2);
        MX f = MX.plus(MX.pow(x.at(0), new MX(2)), MX.pow(MX.tanh(x.at(1)), new MX(2)));
        MX g = MX.plus(MX.cos(MX.plus(x.at(0), x.at(1))), new MX(0.5));
        MX h = MX.plus(MX.sin(x.at(0)), new MX(0.5));
        var lambd1 = MX.sym("lambd");
        var nu1 = MX.sym("nu");
        var lag1 = MX.plus(MX.plus(f, MX.times(lambd1, g)), MX.times(nu1, h));
        var tau = MX.sym("tau");
        Map<String, MX> g_map = new HashMap<>();
        g_map.put("x", MX.vertcat(new StdVectorMX(new MX[]{x, lambd1, nu1})));
        g_map.put("p", tau);
        g_map.put("g", MX.vertcat(new StdVectorMX(new MX[]{MX.gradient(lag1, x), g, MX.plus(MX.times(nu1, h), tau)})));
        var G1 = new StdMapStringToMX(g_map);
        Function rf = rootfinder("rf", "newton", G1);
        System.out.println(rf);
        System.out.println("----------- 2.3 -----------");

        // 2.3
        MX new_x0 = MX.vertcat(new StdVectorMX(new MX[]{new MX(-0.5), new MX(-1.8)}));
        MX new_lambda0 = new MX(0.1);
        MX new_nu0 = new MX(0.1);
        var resul = new StdMapStringToMX();
        Map<String, MX> mxMap = new HashMap<>();
        mxMap.put("x0", MX.vertcat(new StdVectorMX(new MX[]{new_x0, new_lambda0, new_nu0})));
        mxMap.put("p", new MX(1e-2));
        rf.call(new StdMapStringToMX(mxMap), resul);
        for (int i = 0; i < 2; i++) {
            System.out.println(resul.get("x").at(i));
        }
        System.out.println("----------- 2.4 -----------");

        // 2.4
        resul = new StdMapStringToMX();
        mxMap.remove("p");
        mxMap.put("p", new MX(1e-6));
        rf.call(new StdMapStringToMX(mxMap), resul);
        for (int i = 0; i < 2; i++) {
            System.out.println(resul.get("x").at(i));
        }
    }

}
