package de.dhbw.rahmlab.casadimaxima.experiments;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import static de.dhbw.rahmlab.casadi.impl.casadi.SxGenericExpression.*;

public class Casadi_Operators {

    public static void main(String[] args) {
        SX zz = new SX(1e-5);
        System.out.println(zz);

        // 1. Erzeuge symbolische Skalare
        SX a = SxStatic.sym("a");
        SX b = SxStatic.sym("b");

        System.out.println("--- Binäre Operationen (2 Parameter) ---");
        System.out.println("plus:          " + plus(a, b).toString());
        System.out.println("minus:         " + minus(a, b).toString());
        System.out.println("times:         " + times(a, b).toString());
        System.out.println("rdivide:       " + rdivide(a, b).toString());
        System.out.println("pow:           " + pow(a, b).toString());
        System.out.println("mod:           " + mod(a, b).toString());
        System.out.println("remainder:     " + remainder(a, b).toString());
        System.out.println("atan2:         " + atan2(a, b).toString());
        System.out.println("fmin:          " + fmin(a, b).toString());
        System.out.println("fmax:          " + fmax(a, b).toString());
        System.out.println("copysign:      " + copysign(a, b).toString());
        System.out.println("constpow:      " + constpow(a, b).toString());
        System.out.println("hypot:         " + hypot(a, b).toString());
        System.out.println("if_else_zero:  " + if_else_zero(a, b).toString());

        System.out.println("\n--- Logische Operationen ---");
        System.out.println("lt:            " + lt(a, b).toString());
        System.out.println("le:            " + le(a, b).toString());
        System.out.println("gt:            " + gt(a, b).toString());
        System.out.println("ge:            " + ge(a, b).toString());
        System.out.println("eq:            " + eq(a, b).toString());
        System.out.println("ne:            " + ne(a, b).toString());
        System.out.println("logic_and:     " + logic_and(a, b).toString());
        System.out.println("logic_or:      " + logic_or(a, b).toString());
        System.out.println("logic_not:     " + logic_not(a).toString());

        System.out.println("\n--- Unäre Operationen (1 Parameter) ---");
        System.out.println("abs:           " + abs(a).toString());
        System.out.println("sqrt:          " + sqrt(a).toString());
        System.out.println("sq:            " + sq(a).toString());
        System.out.println("sign:          " + sign(a).toString());
        System.out.println("floor:         " + floor(a).toString());
        System.out.println("ceil:          " + ceil(a).toString());
        System.out.println("exp:           " + exp(a).toString());
        System.out.println("expm1:         " + expm1(a).toString());
        System.out.println("log:           " + log(a).toString());
        System.out.println("log10:         " + log10(a).toString());
        System.out.println("log1p:         " + log1p(a).toString());
        System.out.println("erf:           " + erf(a).toString());
        System.out.println("erfinv:        " + erfinv(a).toString());

        System.out.println("\n--- Trigonometrie ---");
        System.out.println("sin:           " + sin(a).toString());
        System.out.println("cos:           " + cos(a).toString());
        System.out.println("tan:           " + tan(a).toString());
        System.out.println("asin:          " + asin(a).toString());
        System.out.println("acos:          " + acos(a).toString());
        System.out.println("atan:          " + atan(a).toString());
        System.out.println("sinh:          " + sinh(a).toString());
        System.out.println("cosh:          " + cosh(a).toString());
        System.out.println("tanh:          " + tanh(a).toString());
        System.out.println("asinh:         " + asinh(a).toString());
        System.out.println("acosh:         " + acosh(a).toString());
        System.out.println("atanh:         " + atanh(a).toString());
    }
}
