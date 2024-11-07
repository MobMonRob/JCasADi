package de.dhbw.rahmlab.casadi.api.jonas_experiments.improvements.course.fristBlock;

import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.*;

import java.util.HashMap;
import java.util.Map;

import static de.dhbw.rahmlab.casadi.impl.casadi.MX.mtimes;
import static de.dhbw.rahmlab.casadi.impl.core__.*;

public class Nlpsol {

    // Exercise sheet
    static double m = 0.04593; // ball mass [kg]
    static double gravity = 9.81; // gravity [m/s^2]
    static double c = 17.5e-5; // friction [kg/m]
    static MX p = MX.sym("p", 2);
    static MX v = MX.sym("v", 2);
    static MX states = MX.vertcat(new StdVectorMX(new MX[]{p, v}));
    static MX speed = MX.norm_2(v);
    static MX rhs = MX.vertcat(new StdVectorMX(new MX[]{v, MX.times(MX.times(new MX(-c), speed), MX.rdivide(v, new MX(m)))}));
    static MX T = MX.sym("T");
    static MX X0 = MX.sym("X0", 4);

    static MX theta = MX.sym("theta");
    static MX v1 = MX.sym("v");
    static MX theta_rad = MX.times(MX.rdivide(theta, new MX(180)), new MX(Math.PI));

    // integrator of 1.2
    public static Function integrator1_2() {
        Map<String, MX> inputMap = new StdMapStringToMX();
        inputMap.put("x", states);
        inputMap.put("ode", rhs);
        var ode = new StdMapStringToMX(inputMap);
        var options = new Dict();
        options.put("tf", new GenericType(1));
        var intg = integrator("intg", "cvodes", ode, options);
        return intg;
    }

    // function of 2.2
    public static Function fly2_2() {
        Map<String, MX> ode_T = new HashMap<>();
        ode_T.put("x", states);
        ode_T.put("ode", MX.times(T, rhs));
        ode_T.put("p", T);
        var function_input = new StdMapStringToMX(ode_T);
        var dict = new Dict();
        dict.put("tf", new GenericType(1));
        var intg = integrator("intg", "cvodes", function_input, dict);

        System.out.println(intg);

        Map<String, MX> outputMap = new StdMapStringToMX();
        outputMap.put("x0", X0);
        outputMap.put("p", T);
        var result = new StdMapStringToMX();

        intg.call(new StdMapStringToMX(outputMap), result);

        var fly = new Function("fly", new StdVectorMX(new MX[]{X0, T}), new StdVectorMX(new MX[]{result.get("xf")}));

        System.out.println(fly);

        return fly;
    }

    // function of 2.3
    public static Function shoot2_3(Function fly) {
        var res = new StdVectorMX();
        fly.call(new StdVectorMX(new MX[]{MX.vertcat(new StdVectorMX(new MX[]{new MX(0), new MX(0), MX.times(v1, MX.cos(theta_rad)), MX.times(v1, MX.sin(theta_rad))})), T}), res);
        var shoot = new Function("shoot", new StdVectorMX(new MX[]{v1, theta, T}), res);
        System.out.println("shoot: " + shoot);
        return shoot;
    }

    // function of 2.5
    public static Function shoot_distance2_5() {
        var fly = fly2_2();
        var x = shoot2_3(fly);
        var result = new StdVectorMX();
        x.call(new StdVectorMX(new MX[]{v1, theta, T}), result);

        var height = MX.vertcat(result).at(1); // -> wrong size, see below/above
        // corresponding python-code: height = x[1]

        System.out.println("Height 2: " + result.size()); // -> wrong size
        // Python = (4, 1)
        // Java = 1

        StdMapStringToMX inputMap = new StdMapStringToMX();
        inputMap.put("x", T);
        inputMap.put("p", MX.vertcat(new StdVectorMX(new MX[]{v1, theta})));
        inputMap.put("g", height);
        var rf = rootfinder("rf", "newton", inputMap);
        System.out.println(rf);

        var result1 = new StdMapStringToMX();

        StdMapStringToMX outputMap = new StdMapStringToMX();
        outputMap.put("x0", new MX(5));
        outputMap.put("p", MX.vertcat(new StdVectorMX(new MX[]{v1, theta})));

        rf.call(outputMap, result1);
        var T_landing = result1.get("x");
        var res = new StdVectorMX();

        x.call(new StdVectorMX(new MX[]{v1, theta, T_landing}), res);
        System.out.println(res);

        var shoot_distance = new Function("shoot_distance", new StdVectorMX(new MX[]{v1, theta}), new StdVectorMX(new MX[]{res.get(0)}));

        return shoot_distance;
    }

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
    public static void exercise_presentation() {
        MX x = MX.sym("x");
        MX y = MX.sym("y");
        MX z = MX.sym("z");
        MX[] v = new MX[]{x, y, z};
        MX f = MX.plus(MX.pow(x, new MX(2)), MX.times(new MX(100), MX.pow(z, new MX(2))));
        MX g = MX.minus(MX.plus(z, MX.pow(MX.minus(new MX(1), x), new MX(2))), y);
        Map<String, MX> inputMap = new StdMapStringToMX();
        inputMap.put("x", MX.vertcat(new StdVectorMX(v)));
        inputMap.put("f", f);
        inputMap.put("g", g);
        StdMapStringToMX mp = new StdMapStringToMX(inputMap);
        var solver = nlpsol("solver", "qrsqp", mp);
        inputMap.clear();
        inputMap.put("x0", MX.vertcat(new StdVectorMX(new MX[]{new MX(2.5), new MX(3.0), new MX(0.75)})));
        inputMap.put("lbg", new MX(0));
        inputMap.put("ubg", new MX(0));
        var res = new StdMapStringToMX();
        solver.call(new StdMapStringToMX(inputMap), res);
        System.out.println(res);
    }

    // Exercise sheet
    public static void exercise1_1() {
        System.out.println(rhs.dim_());
        DM.set_precision(6);
        rhs.set(MX.minus(rhs.at(3), new MX(gravity)), true, new IM(3)); // -> triggers wrong values
        var f = new Function("rhs", new StdVectorMX(new MX[]{MX.vertcat(new StdVectorMX(new MX[]{p, v}))}), new StdVectorMX(new MX[]{rhs}));
        System.out.println(f);
        var result = new StdVectorDM();
        f.call(new StdVectorDM(new DM[]{DM.vertcat(new StdVectorDM(new DM[]{new DM(0.0), new DM(0.0), new DM(35.0), new DM(30.0)}))}), result);
        System.out.println("rhs Dim: " + rhs.dim_());
        System.out.println(DM.vertcat(result));
        // -> Wrong values at second & third position in the result MX
        // My Result: [35, 30, -15.0792, -5.26917]
        // Solution: [35, 30, -6.14737, -15.0792]
    }

    public static void exercise1_2() {
        var intg = integrator1_2();
        System.out.println(intg);
        var result = new StdMapStringToDM();
        Map<String, DM> outputMap = new StdMapStringToDM();
        outputMap.put("x0", DM.vertcat(new StdVectorDM(new DM[]{new DM(0), new DM(0), new DM(35), new DM(30)})));
        intg.call(new StdMapStringToDM(outputMap), result);
        System.out.println("(1.2) xf: " + result.get("xf")); // -> Wrong values
    }

    public static void exercise2_1() {
        var intg = integrator1_2();

        var outputMap = new StdMapStringToMX();
        outputMap.put("x0", X0);

        var result = new StdMapStringToMX();
        intg.call(outputMap, result);

        System.out.println(result);
        // Output of result
        // Python: {'adj_p': MX(0x0), 'adj_u': MX(0x0), 'adj_x0': MX(0x0), 'adj_z0': MX(0x0), 'qf': MX(0x1), 'xf': MX(@1=0x0, intg(X0, 0x1, 0x1, 0x1, @1, @1, @1){0}), 'zf': MX(0x1)}
        // Java:   {adj_x0=0x0, adj_p=0x0, adj_u=0x0, xf=@1=0x0, intg(X0, 0x1, 0x1, 0x1, @1, @1, @1){0}, qf=0x1, zf=0x1, adj_z0=0x0}

        System.out.println(result.get("xf"));
        // Output of xf
        // Python res["xf"]:      @1=0x0, intg(X0, 0x1, 0x1, 0x1, @1, @1, @1){0}
        // Java result.get("xf"): @1=0x0, intg(X0, 0x1, 0x1, 0x1, @1, @1, @1){0}

        var fly1sec = new Function("fly1sec", new StdVectorMX(new MX[]{X0}), new StdVectorMX(new MX[]{result.get("xf")}));
        System.out.println(fly1sec);

        var re = new StdVectorDM();

        fly1sec.call(new StdVectorDM(new DM[]{DM.vertcat(new StdVectorDM(new DM[]{new DM(0.0), new DM(0.0), new DM(35.0), new DM(30.0)}))}), re);
        System.out.println("2.1: " + re); // -> Wrong values
    }

    public static void exercise2_2() {
        var fly = fly2_2();
        var input1 = new StdVectorDM(new DM[]{DM.vertcat(new StdVectorDM(new DM[]{new DM(0), new DM(0), new DM(35), new DM(30)})), new DM(5)});
        var output = new StdVectorDM();
        fly.call(input1, output);
        System.out.println(" 2.2: " + output);// -> Wrong values
    }

    public static void exercise2_3() {
        var fly = fly2_2();
        var shoot = shoot2_3(fly);
        var output = new StdVectorDM(new DM[]{new DM(50), new DM(30), new DM(5)});
        var result = new StdVectorDM();
        shoot.call(output, result);
        System.out.println("2.3: " + result); // -> Wrong values
    }

    public static void exercise2_4() {
        var fly = fly2_2();
        var x = shoot2_3(fly);
        var result = new StdVectorMX();

        x.call(new StdVectorMX(new MX[]{new MX(50), new MX(30), T}), result);

        var height = MX.vertcat(result).at(1); // -> wrong size, see below
        // corresponding python-code: height = x[1]

        System.out.println("Shape: " + height.dim_()); // -> wrong size
        // Python = (4, 1)
        // Java = 1x1

        Map<String, MX> inputMap = new HashMap<>();
        inputMap.put("x", T);
        inputMap.put("g", height);

        var rf = rootfinder("rf", "newton", new StdMapStringToMX(inputMap));

        var re = new StdMapStringToDM();

        Map<String, DM> inputMp = new HashMap<>();
        inputMp.put("x0", new DM(5));

        rf.call(new StdMapStringToDM(inputMp), re);
        System.out.println("2.4: " + re.get("x")); // T = 4.49773s; How to get value x? (res["x"])
        // -> wrong value: -1.28631e-14
    }

    public static void exercise2_5() {
        var shoot_distance = shoot_distance2_5();
        var end_result = new StdVectorDM();
        shoot_distance.call(new StdVectorDM(new DM[]{new DM(50), new DM(30)}), end_result);
        System.out.println("2.5: " + end_result); // 143.533
        // -> wrong value: [[-5.5699e-13, -3.21578e-13, 43.3013, 25]]
    }

    public static void exercise3_1() {
        var shoot_distance = shoot_distance2_5();

        var result_sd = new StdVectorMX();
        shoot_distance.call(new StdVectorMX(new MX[]{new MX(30), theta}), result_sd);

        StdMapStringToMX nlp = new StdMapStringToMX();
        nlp.put("x", theta);
        nlp.put("f", MX.times(new MX(-1), MX.vertcat(result_sd)));

        var solver = nlpsol("solver", "ipopt", nlp);

        var result = new StdMapStringToDM();
        var outputMap = new StdMapStringToDM();
        outputMap.put("x0", new DM(30));

        solver.call(outputMap, result);
        System.out.println(result.get("x")); // 43.2223
    }

    public static void exercise3_2() {
        var shoot_distance = shoot_distance2_5();

        var cov_vtheta = MX.diag(MX.vertcat(new StdVectorMX(new MX[]{MX.pow(new MX(1), new MX(2)), MX.pow(new MX(1.2), new MX(2))})));

        var result = new StdVectorMX();
        shoot_distance.call(new StdVectorMX(new MX[]{v1, theta}), result);

        var J = MX.jacobian(MX.vertcat(result), MX.vertcat(new StdVectorMX(new MX[]{v1, theta})));

        var sigma_shoot_distance = mtimes(mtimes(J, cov_vtheta), J.T());

        StdMapStringToMX nlp = new StdMapStringToMX();
        nlp.put("x", MX.vertcat(new StdVectorMX(new MX[]{v1, theta})));
        nlp.put("g", MX.vertcat(result));
        nlp.put("f", sigma_shoot_distance);

        var solver = nlpsol("solver", "ipopt", nlp);

        StdMapStringToDM outputMap = new StdMapStringToDM();
        outputMap.put("x0", DM.vertcat(new StdVectorDM(new DM[]{new DM(30), new DM(45)})));
        outputMap.put("lbg", new DM(80));
        outputMap.put("ubg", new DM(80));
        var result1 = new StdMapStringToDM();

        solver.call(outputMap, result1);

        System.out.println(result1.get("x"));
    }

    public static void main(String[] args) {
//        exercise_presentation();
//        exercise1_1();
//        exercise1_2();
//        exercise2_1();
//        exercise2_2();
//        exercise2_3();
//        exercise2_4();
        exercise2_5();
//        exercise3_1();
//        exercise3_2();
    }

}
