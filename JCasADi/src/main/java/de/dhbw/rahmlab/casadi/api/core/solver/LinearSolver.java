package de.dhbw.rahmlab.casadi.api.core.solver;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Linsol;

/**
 * Represents a linear solver for solving linear systems of equations.
 * This class provides methods to configure, factorize, and solve linear systems using various solvers and options.
 */
public class LinearSolver {

    private final Linsol linsol;

    /**
     * Constructs a LinearSolver with default settings.
     */
    public LinearSolver() {
        this.linsol = new Linsol();
    }

    /**
     * Constructs a LinearSolver with specified name, solver, sparsity pattern, and options.
     *
     * @param name   the name of the solver.
     * @param solver the solver type.
     * @param sp     the sparsity pattern as a SparsityWrapper.
     * @param opts   the options as a Dictionary.
     */
    public LinearSolver(String name, String solver, SparsityWrapper sp, Dictionary opts) {
        this.linsol = new Linsol(name, solver, sp.getCasADiObject(), opts.getCasADiObject());
    }

    /**
     * Constructs a LinearSolver with specified name, solver, and sparsity pattern.
     *
     * @param name   the name of the solver.
     * @param solver the solver type.
     * @param sp     the sparsity pattern as a SparsityWrapper.
     */
    public LinearSolver(String name, String solver, SparsityWrapper sp) {
        this.linsol = new Linsol(name, solver, sp.getCasADiObject());
    }

    /**
     * Constructs a LinearSolver using an existing Linsol object.
     *
     * @param other the Linsol object used to initialize the solver.
     */
    public LinearSolver(Linsol other) {
        this.linsol = new Linsol(other);
    }

    /**
     * Constructs a LinearSolver using another LinearSolver object.
     *
     * @param other the LinearSolver object used to initialize the solver.
     */
    public LinearSolver(LinearSolver other) {
        this.linsol = new Linsol(other.getCasADiObject());
    }

    /**
     * Retrieves the type name of the linear solver.
     *
     * @return a string representing the type name.
     */
    public static String typeName() {
        return Linsol.type_name();
    }

    /**
     * Loads a plugin for the linear solver.
     *
     * @param name the name of the plugin to load.
     */
    public static void loadPlugin(String name) {
        Linsol.load_plugin(name);
    }

    /**
     * Retrieves documentation for a specified solver.
     *
     * @param name the name of the solver.
     * @return a string representing the documentation.
     */
    public static String doc(String name) {
        return Linsol.doc(name);
    }

    /**
     * Retrieves the name of the plugin used by the solver.
     *
     * @return a string representing the plugin name.
     */
    public String pluginName() {
        return this.linsol.plugin_name();
    }

    /**
     * Retrieves the sparsity pattern of the linear solver.
     *
     * @return a SparsityWrapper representing the sparsity pattern.
     */
    public SparsityWrapper getSparsityPattern() {
        return new SparsityWrapper(this.linsol.sparsity());
    }

    /**
     * Performs symbolic factorization on a given matrix.
     *
     * @param A a DMWrapper representing the matrix to factorize.
     */
    public void performSymbolicFactorization(DMWrapper A) {
        this.linsol.sfact(A.getCasADiObject());
    }

    /**
     * Performs numeric factorization on a given matrix.
     *
     * @param A a DMWrapper representing the matrix to factorize.
     */
    public void performNumericFactorization(DMWrapper A) {
        this.linsol.nfact(A.getCasADiObject());
    }

    /**
     * Solves a linear system with specified matrices and transpose option.
     *
     * @param A  a DMWrapper representing the matrix A.
     * @param B  a DMWrapper representing the matrix B.
     * @param tr a boolean indicating whether to transpose the matrix.
     * @return a DMWrapper representing the solution.
     */
    public DMWrapper solve(DMWrapper A, DMWrapper B, boolean tr) {
        return new DMWrapper(this.linsol.solve(A.getCasADiObject(), B.getCasADiObject(), tr));
    }

    /**
     * Solves a linear system with specified matrices.
     *
     * @param A a DMWrapper representing the matrix A.
     * @param B a DMWrapper representing the matrix B.
     * @return a DMWrapper representing the solution.
     */
    public DMWrapper solve(DMWrapper A, DMWrapper B) {
        return new DMWrapper(this.linsol.solve(A.getCasADiObject(), B.getCasADiObject()));
    }

    /**
     * Solves a linear system with specified matrices and transpose option using MXWrapper.
     *
     * @param A  an MXWrapper representing the matrix A.
     * @param B  an MXWrapper representing the matrix B.
     * @param tr a boolean indicating whether to transpose the matrix.
     * @return an MXWrapper representing the solution.
     */
    public MXWrapper solve(MXWrapper A, MXWrapper B, boolean tr) {
        return new MXWrapper(this.linsol.solve(A.getCasADiObject(), B.getCasADiObject(), tr));
    }

    /**
     * Solves a linear system with specified matrices using MXWrapper.
     *
     * @param A an MXWrapper representing the matrix A.
     * @param B an MXWrapper representing the matrix B.
     * @return an MXWrapper representing the solution.
     */
    public MXWrapper solve(MXWrapper A, MXWrapper B) {
        return new MXWrapper(this.linsol.solve(A.getCasADiObject(), B.getCasADiObject()));
    }

    /**
     * Counts the number of negative eigenvalues in a given matrix.
     *
     * @param A a DMWrapper representing the matrix.
     * @return the number of negative eigenvalues.
     */
    public long countNegativeEigenvalues(DMWrapper A) {
        return this.linsol.neig(A.getCasADiObject());
    }

    /**
     * Calculates the rank of a given matrix.
     *
     * @param A a DMWrapper representing the matrix.
     * @return the rank of the matrix.
     */
    public long calculateMatrixRank(DMWrapper A) {
        return this.linsol.rank(A.getCasADiObject());
    }

    /**
     * Retrieves evaluation statistics with specified memory allocation.
     *
     * @param mem the memory allocation for statistics.
     * @return a Dictionary representing the evaluation statistics.
     */
    public Dictionary getEvaluationStatisticsWithMemory(int mem) {
        return new Dictionary(this.linsol.stats(mem));
    }

    /**
     * Retrieves evaluation statistics.
     *
     * @return a Dictionary representing the evaluation statistics.
     */
    public Dictionary getEvaluationStatistics() {
        return new Dictionary(this.linsol.stats());
    }

    /**
     * Retrieves the class name of the linear solver.
     *
     * @return a string representing the class name.
     */
    public String className() {
        return this.linsol.class_name();
    }

    /**
     * Converts the linear solver to a string representation with additional details.
     *
     * @param more a flag indicating whether to include more details.
     * @return a string representation of the linear solver.
     */
    public String toString(boolean more) {
        return this.linsol.toString(more);
    }

    /**
     * Converts the linear solver to a string representation.
     *
     * @return a string representation of the linear solver.
     */
    public String toString() {
        return this.linsol.toString();
    }

    /**
     * Checks if the linear solver is null.
     *
     * @return true if the solver is null, false otherwise.
     */
    public boolean isNull() {
        return this.linsol.is_null();
    }

    /**
     * Retrieves the hash code of the linear solver.
     *
     * @return the hash code.
     */
    public long getHash() {
        return this.linsol.__hash__();
    }

    /**
     * Retrieves the underlying CasADi object.
     *
     * @return the Linsol object representing the linear solver.
     */
    public Linsol getCasADiObject() {
        return this.linsol;
    }

}
