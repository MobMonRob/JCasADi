package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.im.IMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.IntegerVector;
import de.dhbw.rahmlab.casadi.impl.casadi.IM;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

public class IMUtils {

    public static IMWrapper scalarMatrix(long op, IMWrapper x, IMWrapper y) {
        return new IMWrapper(IM.scalar_matrix(op, x.getCasADiObject(), y.getCasADiObject()));
    }

    public static IMWrapper matrixScalar(long op, IMWrapper x, IMWrapper y) {
        return new IMWrapper(IM.matrix_scalar(op, x.getCasADiObject(), y.getCasADiObject()));
    }

    public static IMWrapper matrixMatrix(long op, IMWrapper x, IMWrapper y) {
        return new IMWrapper(IM.matrix_matrix(op, x.getCasADiObject(), y.getCasADiObject()));
    }

    public static IMWrapper ifElse(IMWrapper x, IMWrapper ifTrue, IMWrapper ifFalse, boolean shortCircuit) {
        return new IMWrapper(IM.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject(), shortCircuit));
    }

    public static IMWrapper ifElse(IMWrapper x, IMWrapper ifTrue, IMWrapper ifFalse) {
        return new IMWrapper(IM.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject()));
    }

    public static IMWrapper triplet(IntegerVector row, IntegerVector col, IMWrapper d) {
        return new IMWrapper(IM.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject()));
    }

    public static IMWrapper triplet(IntegerVector row, IntegerVector col, IMWrapper d, long nrow, long ncol) {
        return new IMWrapper(IM.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject(), nrow, ncol));
    }

    public static IMWrapper inf(Sparsity sp) {
        return new IMWrapper(IM.inf(sp));
    }

    public static IMWrapper inf(long nrow, long ncol) {
        return new IMWrapper(IM.inf(nrow, ncol));
    }

    public static IMWrapper inf(long nrow) {
        return new IMWrapper(IM.inf(nrow));
    }

    public static IMWrapper inf() {
        return new IMWrapper(IM.inf());
    }

    public static IMWrapper nan(Sparsity sp) {
        return new IMWrapper(IM.nan(sp));
    }

    public static IMWrapper nan(long nrow, long ncol) {
        return new IMWrapper(IM.nan(nrow, ncol));
    }

    public static IMWrapper nan(long nrow) {
        return new IMWrapper(IM.nan(nrow));
    }

    public static IMWrapper nan() {
        return new IMWrapper(IM.nan());
    }

    public static IMWrapper eye(long n) {
        return new IMWrapper(IM.eye(n));
    }

    public static void setPrecision(long precision) {
        IM.set_precision(precision);
    }

    public static void setWidth(long width) {
        IM.set_width(width);
    }

    public static void setScientific(boolean scientific) {
        IM.set_scientific(scientific);
    }

    public static void rng(long seed) {
        IM.rng(seed);
    }

    public static IMWrapper rand(long nrow, long ncol) {
        return new IMWrapper(IM.rand(nrow, ncol));
    }

    public static IMWrapper rand(long nrow) {
        return new IMWrapper(IM.rand(nrow));
    }

    public static IMWrapper rand() {
        return new IMWrapper(IM.rand());
    }

    public static IMWrapper rand(Sparsity sp) {
        return new IMWrapper(IM.rand(sp));
    }

    public static IMWrapper deserialize(String s) {
        return new IMWrapper(IM.deserialize(s));
    }

    public static DMWrapper fromFile(String filename, String formatHint) {
        return new DMWrapper(IM.from_file(filename, formatHint));
    }

    public static DMWrapper fromFile(String filename) {
        return new DMWrapper(IM.from_file(filename));
    }

    public static IMWrapper sym(String name, long nrow, long ncol) {
        return new IMWrapper(IM.sym(name, nrow, ncol));
    }

    public static IMWrapper sym(String name, long nrow) {
        return new IMWrapper(IM.sym(name, nrow));
    }

    public static IMWrapper sym(String name) {
        return new IMWrapper(IM.sym(name));
    }

    public static IMWrapper sym(String name, Sparsity sp) {
        return new IMWrapper(IM.sym(name, sp));
    }

    public static IMWrapper zeros(long nrow, long ncol) {
        return new IMWrapper(IM.zeros(nrow, ncol));
    }

    public static IMWrapper zeros(long nrow) {
        return new IMWrapper(IM.zeros(nrow));
    }

    public static IMWrapper zeros() {
        return new IMWrapper(IM.zeros());
    }

    public static IMWrapper zeros(Sparsity sp) {
        return new IMWrapper(IM.zeros(sp));
    }

    public static IMWrapper ones(long nrow, long ncol) {
        return new IMWrapper(IM.ones(nrow, ncol));
    }

    public static IMWrapper ones(long nrow) {
        return new IMWrapper(IM.ones(nrow));
    }

    public static IMWrapper ones() {
        return new IMWrapper(IM.ones());
    }

    public static IMWrapper ones(Sparsity sp) {
        return new IMWrapper(IM.ones(sp));
    }

}
