package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.IntegerVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.StringVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.*;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;

public class SXUtils {

    /**
     * Converts an instance of {@link MXWrapper} to an instance of {@link SXWrapper}.
     *
     * This method creates a new {@link SXWrapper} object by using the dimensions
     * of the provided {@link MXWrapper}. The resulting {@link SXWrapper} will
     * have the same number of rows and columns as the input {@link MXWrapper}.
     *
     * @param mxWrapper the {@link MXWrapper} instance to be converted
     * @return a new {@link SXWrapper} instance with the same dimensions as the input {@link MXWrapper}
     */
    public static SXWrapper convertMXWrapperToSXWrapper(MXWrapper mxWrapper) {
        return new SXWrapper(SX.sym(mxWrapper.getName(), mxWrapper.rows(), mxWrapper.columns()));
    }

    public static SXWrapper convertSXComponentToSXWrapper(SXComponent sxComponent) {
        SXComponentVector vector = new SXComponentVector(sxComponent);
        return new SXWrapper(vector);
    }

    public static SXWrapper scalarMatrix(long op, SXWrapper x, SXWrapper y) {
        SX result = SX.scalar_matrix(op, x.getCasADiObject(), y.getCasADiObject());
        return new SXWrapper(result);
    }

    public static SXWrapper matrixScalar(long op, SXWrapper x, SXWrapper y) {
        SX result = SX.matrix_scalar(op, x.getCasADiObject(), y.getCasADiObject());
        return new SXWrapper(result);
    }

    public static SXWrapper matrixMatrix(long op, SXWrapper x, SXWrapper y) {
        SX result = SX.matrix_matrix(op, x.getCasADiObject(), y.getCasADiObject());
        return new SXWrapper(result);
    }

    public static SXVector substitute(SXVector ex, SXVector v, SXVector vdef) {
        return new SXVector(SX.substitute(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject()));
    }

    public static void substituteInplace(SXVector v, SXVector vdef, SXVector ex, boolean revers) {
        SX.substitute_inplace(v.getCasADiObject(), vdef.getCasADiObject(), ex.getCasADiObject(), revers);
    }

    public static void extract(SXVector ex, SXVector v, SXVector vdef, Dictionary ops) {
        SX.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), ops.getCasADiObject());
    }

    public static void extract(SXVector ex, SXVector v, SXVector vdef) {
        SX.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject());
    }

    public static void shared(SXVector ex, SXVector v, SXVector vdef, String vPrefix, String vSuffix) {
        SX.shared(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), vPrefix, vSuffix);
    }

    public static SXWrapper ifElse(SXWrapper x, SXWrapper ifTrue, SXWrapper ifFalse, boolean shortCircuit) {
        return new SXWrapper(SX.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject(), shortCircuit));
    }

    public static SXWrapper ifElse(SXWrapper x, SXWrapper ifTrue, SXWrapper ifFalse) {
        return new SXWrapper(SX.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject()));
    }

    public static SXWrapper conditional(SXWrapper ind, SXVector x, SXWrapper xDefault, boolean shortCircuit) {
        return new SXWrapper(SX.conditional(ind.getCasADiObject(), x.getCasADiObject(), xDefault.getCasADiObject(), shortCircuit));
    }

    public static SXWrapper conditional(SXWrapper ind, SXVector x, SXWrapper xDefault) {
        return new SXWrapper(SX.conditional(ind.getCasADiObject(), x.getCasADiObject(), xDefault.getCasADiObject()));
    }

    public static SXVector cse(SXVector e) {
        return new SXVector(SX.cse(e.getCasADiObject()));
    }

    public static SXVectorCollection forward(SXVector ex, SXVector arg, SXVectorCollection v, Dictionary opts) {
        return new SXVectorCollection(SX.forward(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject(), opts.getCasADiObject()));
    }

    public static SXVectorCollection forward(SXVector ex, SXVector arg, SXVectorCollection v) {
        return new SXVectorCollection(SX.forward(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject()));
    }

    public static SXVectorCollection reverse(SXVector ex, SXVector arg, SXVectorCollection v, Dictionary opts) {
        return new SXVectorCollection(SX.reverse(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject(), opts.getCasADiObject()));
    }

    public static SXVectorCollection reverse(SXVector ex, SXVector arg, SXVectorCollection v) {
        return new SXVectorCollection(SX.reverse(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject()));
    }

    public static SXVector getInput(FunctionWrapper f) {
        return new SXVector(SX.get_input(f.getCasADiObject()));
    }

    public static SXVector getFree(FunctionWrapper f) {
        return new SXVector(SX.get_free(f.getCasADiObject()));
    }

    public static void printSplit(long nnz, SXComponent nonzeros, StringVector nz, StringVector inter) {
        SX.print_split(nnz, nonzeros.getCasADiObject(), nz.getCasADiObject(), inter.getCasADiObject());
    }

    public static SXWrapper triplet(IntegerVector row, IntegerVector col, SXWrapper d) {
        return new SXWrapper(SX.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject()));
    }

    public static SXWrapper triplet(IntegerVector row, IntegerVector col, SXWrapper d, long nrow, long ncol) {
        return new SXWrapper(SX.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject(), nrow, ncol));
    }

    public static void setPrecision(long precision) {
        SX.set_precision(precision);
    }

    public static void setWidth(long width) {
        SX.set_width(width);
    }

    public static void setScientific(boolean scientific) {
        SX.set_scientific(scientific);
    }

    public static void rng(long seed) {
        SX.rng(seed);
    }

    public static void toFile(String filename, SparsityWrapper sp, SXComponent nonzeros, String format) {
        SX.to_file(filename, sp.getCasADiObject(), nonzeros.getCasADiObject(), format);
    }

    public static void toFile(String filename, SparsityWrapper sp, SXComponent nonzeros) {
        SX.to_file(filename, sp.getCasADiObject(), nonzeros.getCasADiObject());
    }

    public static DMWrapper fromFile(String filename, String formatHint) {
        return new DMWrapper(SX.from_file(filename, formatHint));
    }

    public static DMWrapper fromFile(String filename) {
        return new DMWrapper(SX.from_file(filename));
    }

    public static IntegerVector offset(SXVector v, boolean vert) {
        return new IntegerVector(SX.offset(v.getCasADiObject(), vert));
    }

    public static IntegerVector offset(SXVector v) {
        return new IntegerVector(SX.offset(v.getCasADiObject()));
    }

}
