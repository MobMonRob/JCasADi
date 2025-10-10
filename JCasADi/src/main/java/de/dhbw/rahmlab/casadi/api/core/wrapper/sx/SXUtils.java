package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.str.StringVector;
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
        return new SXWrapper(SxStatic.sym(mxWrapper.getName(), mxWrapper.rows(), mxWrapper.columns()));
    }

    public static SXWrapper convertSXComponentToSXWrapper(SXComponent sxComponent) {
        SXComponentVector vector = new SXComponentVector(sxComponent);
        return new SXWrapper(vector);
    }

    public static SXWrapper scalarMatrix(long op, SXWrapper x, SXWrapper y) {
        SX result = SxStatic.scalar_matrix(op, x.getCasADiObject(), y.getCasADiObject());
        return new SXWrapper(result);
    }

    public static SXWrapper matrixScalar(long op, SXWrapper x, SXWrapper y) {
        SX result = SxStatic.matrix_scalar(op, x.getCasADiObject(), y.getCasADiObject());
        return new SXWrapper(result);
    }

    public static SXWrapper matrixMatrix(long op, SXWrapper x, SXWrapper y) {
        SX result = SxStatic.matrix_matrix(op, x.getCasADiObject(), y.getCasADiObject());
        return new SXWrapper(result);
    }

    public static SXVector substitute(SXVector ex, SXVector v, SXVector vdef) {
        return new SXVector(SxStatic.substitute(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject()));
    }

    public static void substituteInplace(SXVector v, SXVector vdef, SXVector ex, boolean revers) {
        SxStatic.substitute_inplace(v.getCasADiObject(), vdef.getCasADiObject(), ex.getCasADiObject(), revers);
    }

    public static void extract(SXVector ex, SXVector v, SXVector vdef, Dictionary ops) {
        SxStatic.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), ops.getCasADiObject());
    }

    public static void extract(SXVector ex, SXVector v, SXVector vdef) {
        SxStatic.extract(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject());
    }

    public static void shared(SXVector ex, SXVector v, SXVector vdef, String vPrefix, String vSuffix) {
        SxStatic.shared(ex.getCasADiObject(), v.getCasADiObject(), vdef.getCasADiObject(), vPrefix, vSuffix);
    }

    public static SXWrapper ifElse(SXWrapper x, SXWrapper ifTrue, SXWrapper ifFalse, boolean shortCircuit) {
        return new SXWrapper(SxStatic.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject(), shortCircuit));
    }

    public static SXWrapper ifElse(SXWrapper x, SXWrapper ifTrue, SXWrapper ifFalse) {
        return new SXWrapper(SxStatic.if_else(x.getCasADiObject(), ifTrue.getCasADiObject(), ifFalse.getCasADiObject()));
    }

    public static SXWrapper conditional(SXWrapper ind, SXVector x, SXWrapper xDefault, boolean shortCircuit) {
        return new SXWrapper(SxStatic.conditional(ind.getCasADiObject(), x.getCasADiObject(), xDefault.getCasADiObject(), shortCircuit));
    }

    public static SXWrapper conditional(SXWrapper ind, SXVector x, SXWrapper xDefault) {
        return new SXWrapper(SxStatic.conditional(ind.getCasADiObject(), x.getCasADiObject(), xDefault.getCasADiObject()));
    }

    public static SXVector cse(SXVector e) {
        return new SXVector(SxStatic.cse(e.getCasADiObject()));
    }

    public static SXVectorCollection forward(SXVector ex, SXVector arg, SXVectorCollection v, Dictionary opts) {
        return new SXVectorCollection(SxStatic.forward(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject(), opts.getCasADiObject()));
    }

    public static SXVectorCollection forward(SXVector ex, SXVector arg, SXVectorCollection v) {
        return new SXVectorCollection(SxStatic.forward(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject()));
    }

    public static SXVectorCollection reverse(SXVector ex, SXVector arg, SXVectorCollection v, Dictionary opts) {
        return new SXVectorCollection(SxStatic.reverse(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject(), opts.getCasADiObject()));
    }

    public static SXVectorCollection reverse(SXVector ex, SXVector arg, SXVectorCollection v) {
        return new SXVectorCollection(SxStatic.reverse(ex.getCasADiObject(), arg.getCasADiObject(), v.getCasADiObject()));
    }

    public static SXVector getInput(FunctionWrapper f) {
        return new SXVector(SxStatic.get_input(f.getCasADiObject()));
    }

    public static SXVector getFree(FunctionWrapper f) {
        return new SXVector(SxStatic.get_free(f.getCasADiObject()));
    }

    public static void printSplit(long nnz, SXComponent nonzeros, StringVector nz, StringVector inter) {
        SxStatic.print_split(nnz, nonzeros.getCasADiObject(), nz.getCasADiObject(), inter.getCasADiObject());
    }

    public static SXWrapper triplet(CasADiIntVector row, CasADiIntVector col, SXWrapper d) {
        return new SXWrapper(SxStatic.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject()));
    }

    public static SXWrapper triplet(CasADiIntVector row, CasADiIntVector col, SXWrapper d, long nrow, long ncol) {
        return new SXWrapper(SxStatic.triplet(row.getCasADiObject(), col.getCasADiObject(), d.getCasADiObject(), nrow, ncol));
    }

    public static void setPrecision(long precision) {
        SxStatic.set_precision(precision);
    }

    public static void setWidth(long width) {
        SxStatic.set_width(width);
    }

    public static void setScientific(boolean scientific) {
        SxStatic.set_scientific(scientific);
    }

    public static void rng(long seed) {
        SxStatic.rng(seed);
    }

    public static void toFile(String filename, SparsityWrapper sp, SXComponent nonzeros, String format) {
        SxStatic.to_file(filename, sp.getCasADiObject(), nonzeros.getCasADiObject(), format);
    }

    public static void toFile(String filename, SparsityWrapper sp, SXComponent nonzeros) {
        SxStatic.to_file(filename, sp.getCasADiObject(), nonzeros.getCasADiObject());
    }

    public static DMWrapper fromFile(String filename, String formatHint) {
        return new DMWrapper(SxStatic.from_file(filename, formatHint));
    }

    public static DMWrapper fromFile(String filename) {
        return new DMWrapper(SxStatic.from_file(filename));
    }

    public static CasADiIntVector offset(SXVector v, boolean vert) {
        return new CasADiIntVector(SxStatic.offset(v.getCasADiObject(), vert));
    }

    public static CasADiIntVector offset(SXVector v) {
        return new CasADiIntVector(SxStatic.offset(v.getCasADiObject()));
    }

}
