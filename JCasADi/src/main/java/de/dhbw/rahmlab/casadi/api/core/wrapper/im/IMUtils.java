package de.dhbw.rahmlab.casadi.api.core.wrapper.im;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.IntegerVector;
import de.dhbw.rahmlab.casadi.impl.casadi.IM;

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

    public static IMWrapper deserialize(String s) {
        return new IMWrapper(IM.deserialize(s));
    }

    public static DMWrapper fromFile(String filename, String formatHint) {
        return new DMWrapper(IM.from_file(filename, formatHint));
    }

    public static DMWrapper fromFile(String filename) {
        return new DMWrapper(IM.from_file(filename));
    }

}
