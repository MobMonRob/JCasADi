package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.IntegerVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityVector;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

public class SparsityUtils {

    public static SparsityWrapper scalar(boolean denseScalar) {
        return new SparsityWrapper(Sparsity.scalar(denseScalar));
    }

    public static SparsityWrapper scalar() {
        return new SparsityWrapper(Sparsity.scalar());
    }

    public static SparsityWrapper dense(long nrow, long ncol) {
        return new SparsityWrapper(Sparsity.dense(nrow, ncol));
    }

    public static SparsityWrapper dense(long nrow) {
        return new SparsityWrapper(Sparsity.dense(nrow));
    }

    public static SparsityWrapper unit(long n, long el) {
        return new SparsityWrapper(Sparsity.unit(n, el));
    }

    public static SparsityWrapper upper(long n) {
        return new SparsityWrapper(Sparsity.upper(n));
    }

    public static SparsityWrapper lower(long n) {
        return new SparsityWrapper(Sparsity.lower(n));
    }

    public static SparsityWrapper diag(long nrow) {
        return new SparsityWrapper(Sparsity.diag(nrow));
    }

    public static SparsityWrapper diag(long nrow, long ncol) {
        return new SparsityWrapper(Sparsity.diag(nrow, ncol));
    }

    public static SparsityWrapper band(long n, long p) {
        return new SparsityWrapper(Sparsity.band(n, p));
    }

    public static SparsityWrapper banded(long n, long p) {
        return new SparsityWrapper(Sparsity.banded(n, p));
    }

    public static SparsityWrapper rowcol(IntegerVector row, IntegerVector col, long nrow, long ncol) {
        return new SparsityWrapper(Sparsity.rowcol(row.getCasADiObject(), col.getCasADiObject(), nrow, ncol));
    }

    public static SparsityWrapper triplet(long nrow, long ncol, IntegerVector row, IntegerVector col, IntegerVector OUTPUT, boolean invertMapping) {
        return new SparsityWrapper(Sparsity.triplet(nrow, ncol, row.getCasADiObject(), col.getCasADiObject(), OUTPUT.getCasADiObject(), invertMapping));
    }

    public static SparsityWrapper triplet(long nrow, long ncol, IntegerVector row, IntegerVector col) {
        return new SparsityWrapper(Sparsity.triplet(nrow, ncol, row.getCasADiObject(), col.getCasADiObject()));
    }

    public static SparsityWrapper nonzeros(long nrow, long ncol, IntegerVector nz, boolean ind1) {
        return new SparsityWrapper(Sparsity.nonzeros(nrow, ncol, nz.getCasADiObject(), ind1));
    }

    public static SparsityWrapper nonzeros(long nrow, long ncol, IntegerVector nz) {
        return new SparsityWrapper(Sparsity.nonzeros(nrow, ncol, nz.getCasADiObject()));
    }

    public static SparsityWrapper compressed(IntegerVector v, boolean orderRows) {
        return new SparsityWrapper(Sparsity.compressed(v.getCasADiObject(), orderRows));
    }

    public static SparsityWrapper compressed(IntegerVector v) {
        return new SparsityWrapper(Sparsity.compressed(v.getCasADiObject()));
    }

    public static SparsityWrapper permutation(IntegerVector p, boolean invert) {
        return new SparsityWrapper(Sparsity.permutation(p.getCasADiObject(), invert));
    }

    public static SparsityWrapper permutation(IntegerVector p) {
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

    public static IntegerVector offset(SparsityVector v, boolean vert) {
        return new IntegerVector(Sparsity.offset(v.getCasADiObject(), vert));
    }

    public static IntegerVector offset(SparsityVector v) {
        return new IntegerVector(Sparsity.offset(v.getCasADiObject()));
    }

}
