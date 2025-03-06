package de.dhbw.rahmlab.casadi.api.core.solver;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Linsol;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

public class LinearSolver {

    private final Linsol linsol;

    public LinearSolver() {
        this.linsol = new Linsol();
    }

    public LinearSolver(String name, String solver, Sparsity sp, Dict opts) {
        this.linsol = new Linsol(name, solver, sp, opts);
    }

    public LinearSolver(String name, String solver, Sparsity sp) {
        this.linsol = new Linsol(name, solver, sp);
    }

    public LinearSolver(Linsol other) {
        this.linsol = new Linsol(other);
    }

    public LinearSolver(LinearSolver other) {
        this.linsol = new Linsol(other.getCasADiObject());
    }

    public static String typeName() {
        return Linsol.type_name();
    }

    public static void loadPlugin(String name) {
        Linsol.load_plugin(name);
    }

    public static String doc(String name) {
        return Linsol.doc(name);
    }

    public String pluginName() {
        return this.linsol.plugin_name();
    }

    public Sparsity getSparsityPattern() {
        return this.linsol.sparsity();
    }

    public void performSymbolicFactorization(DMWrapper A) {
        this.linsol.sfact(A.getCasADiObject());
    }

    public void performNumericFactorization(DMWrapper A) {
        this.linsol.nfact(A.getCasADiObject());
    }

    public DMWrapper solve(DMWrapper A, DMWrapper B, boolean tr) {
        return new DMWrapper(this.linsol.solve(A.getCasADiObject(), B.getCasADiObject(), tr));
    }

    public DMWrapper solve(DMWrapper A, DMWrapper B) {
        return new DMWrapper(this.linsol.solve(A.getCasADiObject(), B.getCasADiObject()));
    }

    public MXWrapper solve(MXWrapper A, MXWrapper B, boolean tr) {
        return new MXWrapper(this.linsol.solve(A.getCasADiObject(), B.getCasADiObject(), tr));
    }

    public MXWrapper solve(MXWrapper A, MXWrapper B) {
        return new MXWrapper(this.linsol.solve(A.getCasADiObject(), B.getCasADiObject()));
    }

    public long countNegativeEigenvalues(DMWrapper A) {
        return this.linsol.neig(A.getCasADiObject());
    }

    public long calculateMatrixRank(DMWrapper A) {
        return this.linsol.rank(A.getCasADiObject());
    }

    public Dict getEvaluationStatisticsWithMemory(int mem) {
        return this.linsol.stats(mem);
    }

    public Dict getEvaluationStatistics() {
        return this.linsol.stats();
    }

    public String className() {
        return this.linsol.class_name();
    }

    public String toString(boolean more) {
        return this.linsol.toString(more);
    }

    public String toString() {
        return this.linsol.toString();
    }

    public boolean isNull() {
        return this.linsol.is_null();
    }

    public long getHash() {
        return this.linsol.__hash__();
    }

    public Linsol getCasADiObject() {
        return this.linsol;
    }

}
