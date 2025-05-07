package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.IntegerVector;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;

public class DMUtils {

    public static DMVector substitute(DMVector ex, DMVector v, DMVector vdef) {
        return new DMVector(DM.substitute(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject()));
    }

    public static void substituteInplace(DMVector v, DMVector vdef, DMVector ex, boolean revers) {
        DM.substitute_inplace(v.getCasADiObject(), vdef.getCasADiObject(), ex.getCasADiObject(), revers);
    }

    public static void extract(DMVector ex, DMVector v, DMVector vdef, Dictionary opts) {
        DM.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), opts.getCasADiObject());
    }

    public static void extract(DMVector ex, DMVector v, DMVector vdef) {
        DM.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject());
    }

    public static void shared(DMVector ex, DMVector v, DMVector vdef, String vPrefix, String vSuffix) {
        DM.shared(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), vPrefix, vSuffix);
    }

    public static DMWrapper ifElse(DMWrapper x, DMWrapper ifTrue, DMWrapper ifFalse, boolean shortCircuit) {
        return new DMWrapper(DM.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject(), shortCircuit));
    }

    public static DMWrapper ifElse(DMWrapper x, DMWrapper ifTrue, DMWrapper ifFalse) {
        return new DMWrapper(DM.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject()));
    }

    public static DMWrapper conditional(DMWrapper ind, DMVector x, DMWrapper xDefault, boolean shortCircuit) {
        return new DMWrapper(DM.conditional(ind.getCasADiObject(), x.getCasADiObject(), xDefault.getCasADiObject(), shortCircuit));
    }

    public static DMWrapper conditional(DMWrapper ind, DMVector x, DMWrapper xDefault) {
        return new DMWrapper(DM.conditional(ind.getCasADiObject(), x.getCasADiObject(), xDefault.getCasADiObject()));
    }

    public static DMVector cse(DMVector e) {
        return new DMVector(DM.cse(e.getCasADiObject()));
    }

    public static DMVectorCollection forward(DMVector ex, DMVector arg, DMVectorCollection v, Dictionary opts) {
        return new DMVectorCollection(DM.forward(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject(), opts.getCasADiObject()));
    }

    public static DMVectorCollection forward(DMVector ex, DMVector arg, DMVectorCollection v) {
        return new DMVectorCollection(DM.forward(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject()));
    }

    public static DMVectorCollection reverse(DMVector ex, DMVector arg, DMVectorCollection v, Dictionary opts) {
        return new DMVectorCollection(DM.reverse(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject(), opts.getCasADiObject()));
    }

    public static DMVectorCollection reverse(DMVector ex, DMVector arg, DMVectorCollection v) {
        return new DMVectorCollection(DM.reverse(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject()));
    }

    public static DMVector getInput(FunctionWrapper f) {
        return new DMVector(DM.get_input(f.getCasADiObject()));
    }

    public static DMVector getFree(FunctionWrapper f) {
        return new DMVector(DM.get_free(f.getCasADiObject()));
    }

    public static DMWrapper triplet(IntegerVector row, IntegerVector col, DMWrapper d) {
        return new DMWrapper(DM.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject()));
    }

    public static DMWrapper triplet(IntegerVector row, IntegerVector col, DMWrapper d, long nrow, long ncol) {
        return new DMWrapper(DM.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject(), nrow, ncol));
    }

    public static void setPrecision(long precision) {
        DM.set_precision(precision);
    }

    public static void setWidth(long width) {
        DM.set_width(width);
    }

    public static void set_scientific(boolean scientific) {
        DM.set_scientific(scientific);
    }

    public static void rng(long seed) {
        DM.rng(seed);
    }

    public static DMWrapper fromFile(String filename, String formatHint) {
        return new DMWrapper(DM.from_file(filename, formatHint));
    }

    public static DMWrapper fromFile(String filename) {
        return new DMWrapper(DM.from_file(filename));
    }

    public static IntegerVector offset(DMVector v, boolean vert) {
        return new IntegerVector(DM.offset(v.getCasADiObject(), vert));
    }

    public static IntegerVector offset(DMVector v) {
        return new IntegerVector(DM.offset(v.getCasADiObject()));
    }

    public static DMWrapper scalarMatrix(long op, DMWrapper x, DMWrapper y) {
        DM result = DM.scalar_matrix(op, x.getCasADiObject(), y.getCasADiObject());
        return new DMWrapper(result);
    }

    public static DMWrapper matrixScalar(long op, DMWrapper x, DMWrapper y) {
        DM result = DM.matrix_scalar(op, x.getCasADiObject(), y.getCasADiObject());
        return new DMWrapper(result);
    }

    public static DMWrapper matrixMatrix(long op, DMWrapper x, DMWrapper y) {
        DM result = DM.matrix_matrix(op, x.getCasADiObject(), y.getCasADiObject());
        return new DMWrapper(result);
    }

}
