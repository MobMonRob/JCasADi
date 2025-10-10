package de.dhbw.rahmlab.casadi.api.core.wrapper.im;

import de.dhbw.rahmlab.casadi.ImStatic;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;

public class IMUtils {

    public static IMWrapper scalarMatrix(long op, IMWrapper x, IMWrapper y) {
        return new IMWrapper(ImStatic.scalar_matrix(op, x.getCasADiObject(), y.getCasADiObject()));
    }

    public static IMWrapper matrixScalar(long op, IMWrapper x, IMWrapper y) {
        return new IMWrapper(ImStatic.matrix_scalar(op, x.getCasADiObject(), y.getCasADiObject()));
    }

    public static IMWrapper matrixMatrix(long op, IMWrapper x, IMWrapper y) {
        return new IMWrapper(ImStatic.matrix_matrix(op, x.getCasADiObject(), y.getCasADiObject()));
    }

    public static IMWrapper ifElse(IMWrapper x, IMWrapper ifTrue, IMWrapper ifFalse, boolean shortCircuit) {
        return new IMWrapper(ImStatic.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject(), shortCircuit));
    }

    public static IMWrapper ifElse(IMWrapper x, IMWrapper ifTrue, IMWrapper ifFalse) {
        return new IMWrapper(ImStatic.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject()));
    }

    public static IMWrapper triplet(CasADiIntVector row, CasADiIntVector col, IMWrapper d) {
        return new IMWrapper(ImStatic.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject()));
    }

    public static IMWrapper triplet(CasADiIntVector row, CasADiIntVector col, IMWrapper d, long nrow, long ncol) {
        return new IMWrapper(ImStatic.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject(), nrow, ncol));
    }

    public static void setPrecision(long precision) {
        ImStatic.set_precision(precision);
    }

    public static void setWidth(long width) {
        ImStatic.set_width(width);
    }

    public static void setScientific(boolean scientific) {
        ImStatic.set_scientific(scientific);
    }

    public static void rng(long seed) {
        ImStatic.rng(seed);
    }

    public static IMWrapper deserialize(String s) {
        return new IMWrapper(ImStatic.deserialize(s));
    }

    public static DMWrapper fromFile(String filename, String formatHint) {
        return new DMWrapper(ImStatic.from_file(filename, formatHint));
    }

    public static DMWrapper fromFile(String filename) {
        return new DMWrapper(ImStatic.from_file(filename));
    }

}
