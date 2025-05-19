package de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity;

import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

public class SparsityUtils {

    public static SparsityWrapper scalar(boolean denseScalar) {
        return new SparsityWrapper(Sparsity.scalar(denseScalar));
    }

    public static SparsityWrapper scalar() {
        return new SparsityWrapper(Sparsity.scalar());
    }

    public static SparsityWrapper rowcol(CasADiIntVector row, CasADiIntVector col, long nrow, long ncol) {
        return new SparsityWrapper(Sparsity.rowcol(row.getCasADiObject(), col.getCasADiObject(), nrow, ncol));
    }

    public static SparsityWrapper triplet(long nrow, long ncol, CasADiIntVector row, CasADiIntVector col, CasADiIntVector OUTPUT, boolean invertMapping) {
        return new SparsityWrapper(Sparsity.triplet(nrow, ncol, row.getCasADiObject(), col.getCasADiObject(), OUTPUT.getCasADiObject(), invertMapping));
    }

    public static SparsityWrapper triplet(long nrow, long ncol, CasADiIntVector row, CasADiIntVector col) {
        return new SparsityWrapper(Sparsity.triplet(nrow, ncol, row.getCasADiObject(), col.getCasADiObject()));
    }

    public static SparsityWrapper nonzeros(long nrow, long ncol, CasADiIntVector nz, boolean ind1) {
        return new SparsityWrapper(Sparsity.nonzeros(nrow, ncol, nz.getCasADiObject(), ind1));
    }

    public static SparsityWrapper nonzeros(long nrow, long ncol, CasADiIntVector nz) {
        return new SparsityWrapper(Sparsity.nonzeros(nrow, ncol, nz.getCasADiObject()));
    }

    public static SparsityWrapper compressed(CasADiIntVector v, boolean orderRows) {
        return new SparsityWrapper(Sparsity.compressed(v.getCasADiObject(), orderRows));
    }

    public static SparsityWrapper compressed(CasADiIntVector v) {
        return new SparsityWrapper(Sparsity.compressed(v.getCasADiObject()));
    }

    public static SparsityWrapper permutation(CasADiIntVector p, boolean invert) {
        return new SparsityWrapper(Sparsity.permutation(p.getCasADiObject(), invert));
    }

    public static SparsityWrapper permutation(CasADiIntVector p) {
        return new SparsityWrapper(Sparsity.permutation(p.getCasADiObject()));
    }

    public static SparsityWrapper fromFile(String filename, String formatHint) {
        return new SparsityWrapper(Sparsity.from_file(filename, formatHint));
    }

    public static SparsityWrapper fromFile(String filename) {
        return new SparsityWrapper(Sparsity.from_file(filename));
    }

    public static SparsityWrapper deserialize(String s) {
        return new SparsityWrapper(Sparsity.deserialize(s));
    }

    public static CasADiIntVector offset(SparsityVector v, boolean vert) {
        return new CasADiIntVector(Sparsity.offset(v.getCasADiObject(), vert));
    }

    public static CasADiIntVector offset(SparsityVector v) {
        return new CasADiIntVector(Sparsity.offset(v.getCasADiObject()));
    }

}
