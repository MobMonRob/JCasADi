package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.*;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;

public class FunctionUtils {

    public static FunctionWrapper jit(String name, String body, StringVector nameIn, StringVector nameOut, Dictionary opts) {
        return new FunctionWrapper(Function.jit(name, body, nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper jit(String name, String body, StringVector nameIn, StringVector nameOut) {
        return new FunctionWrapper(Function.jit(name, body, nameIn.getCasADiObject(), nameOut.getCasADiObject()));
    }

    public static FunctionWrapper jit(String name, String body, StringVector nameIn, StringVector nameOut, SparsityVector sparsityIn, SparsityVector sparsityOut, Dictionary opts) {
        return new FunctionWrapper(Function.jit(name, body, nameIn.getCasADiObject(), nameOut.getCasADiObject(), sparsityIn.getCasADiObject(), sparsityOut.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper jit(String name, String body, StringVector nameIn, StringVector nameOut, SparsityVector sparsityIn, SparsityVector sparsityOut) {
        return new FunctionWrapper(Function.jit(name, body, nameIn.getCasADiObject(), nameOut.getCasADiObject(), sparsityIn.getCasADiObject(), sparsityOut.getCasADiObject()));
    }

    public static FunctionWrapper conditional(String name, FunctionVector f, FunctionWrapper fDef, Dictionary opts) {
        return new FunctionWrapper(Function.conditional(name, f.getCasADiObject(), fDef.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper conditional(String name, FunctionVector f, FunctionWrapper fDef) {
        return new FunctionWrapper(Function.conditional(name, f.getCasADiObject(), fDef.getCasADiObject()));
    }

    public static FunctionWrapper conditional(String name, FunctionWrapper f, Dictionary opts) {
        return new FunctionWrapper(Function.conditional(name, f.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper conditional(String name, FunctionWrapper f) {
        return new FunctionWrapper(Function.conditional(name, f.getCasADiObject()));
    }

    public static FunctionWrapper bspline(String name, DoubleVectorCollection knots, DoubleVector coeffs, IntegerVector degree, long m, Dictionary opts) {
        return new FunctionWrapper(Function.bspline(name, knots.getCasADiObject(), coeffs.getCasADiObject(), degree.getCasADiObject(), m, opts.getCasADiObject()));
    }

    public static FunctionWrapper bspline(String name, DoubleVectorCollection knots, DoubleVector coeffs, IntegerVector degree, long m) {
        return new FunctionWrapper(Function.bspline(name, knots.getCasADiObject(), coeffs.getCasADiObject(), degree.getCasADiObject(), m));
    }

    public static FunctionWrapper bspline(String name, DoubleVectorCollection knots, DoubleVector coeffs, IntegerVector degree) {
        return new FunctionWrapper(Function.bspline(name, knots.getCasADiObject(), coeffs.getCasADiObject(), degree.getCasADiObject()));
    }

    public static FunctionWrapper ifElse(String name, FunctionWrapper fTrue, FunctionWrapper fFalse, Dictionary opts) {
        return new FunctionWrapper(Function.if_else(name, fTrue.getCasADiObject(), fFalse.getCasADiObject(), opts.getCasADiObject()));
    }

    public static FunctionWrapper ifElse(String name, FunctionWrapper fTrue, FunctionWrapper fFalse) {
        return new FunctionWrapper(Function.if_else(name, fTrue.getCasADiObject(), fFalse.getCasADiObject()));
    }

    public static boolean checkName(String name) {
        return Function.check_name(name);
    }

    public static String fixName(String name) {
        return Function.fix_name(name);
    }

    public static FunctionWrapper deserialize(String s) {
        return new FunctionWrapper(Function.deserialize(s));
    }

    public static FunctionWrapper load(String filename) {
        return new FunctionWrapper(Function.load(filename));
    }



}
