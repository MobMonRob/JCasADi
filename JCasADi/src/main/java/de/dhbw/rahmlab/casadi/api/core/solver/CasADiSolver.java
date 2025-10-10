package de.dhbw.rahmlab.casadi.api.core.solver;

/**
 * Enum representing the available solvers in CasADi.
 * Each solver is designed for specific types of optimization problems.
 */
public enum CasADiSolver {

    /**
     * IPOPT: Interior Point OPTimizer for large-scale nonlinear optimization problems.
     * Suitable for Nonlinear Programming (NLP) problems.
     */
    IPOPT("ipopt"),

    /**
     * CONIC: A general-purpose solver for convex optimization problems.
     * Solves conic programming problems (e.g., SOCP, LP, SDP).
     */
    CONIC("conic"),

    /**
     * qpOASES: A solver specifically for quadratic programming (QP) problems.
     * Suitable for both online and offline quadratic programs.
     */
    QPOASES("qpOASES"),

    /**
     * OOQP: Object-Oriented Quadratic Programming solver.
     * Specialized for structured QP problems, such as those arising in control applications.
     */
    OOQP("OOQP"),

    /**
     * OSQP: Operator Splitting Quadratic Program solver.
     * Efficient for solving large-scale QP problems with sparse structures.
     */
    OSQP("OSQP"),

    /**
     * QRSQP: Solver for solving quadratic programming problems via reduced space successive quadratic programming.
     * Suitable for optimization problems that require iterative refinement of feasible solutions.
     */
    QRSQP("qrsqp"),

    /**
     * PROXQP: Proximal-based solver for large QP problems.
     * Ideal for high-dimensional quadratic programs.
     */
    PROXQP("PROXQP"),

    /**
     * CPLEX: IBM CPLEX solver for linear programming (LP) and mixed-integer programming (MIP) problems.
     * Often used in industrial applications requiring high performance.
     */
    CPLEX("CPLEX"),

    /**
     * GUROBI: Gurobi solver for linear programming, quadratic programming, and mixed-integer programming.
     * Known for fast solutions in combinatorial optimization and integer programs.
     */
    GUROBI("GUROBI"),

    /**
     * NLPSOL: CasADi's general nonlinear programming (NLP) solver interface.
     * Allows various underlying solvers for nonlinear optimization problems.
     */
    NLPSOL("nlpsol"),

    /**
     * INTEGRATOR: Used for integration problems, including ODEs and DAEs.
     * Commonly used in simulation and control applications.
     */
    INTEGRATOR("integrator"),

    /**
     * LINSOL: Linear solver interface in CasADi.
     * Used for solving sparse or dense linear systems.
     */
    LINSOL("linsol"),

    /**
     * ROOTFINDER: Solver for finding the roots of nonlinear systems of equations.
     * Typically used in implicit system solvers or fixed-point iteration methods.
     */
    ROOTFINDER("rootfinder");

    private final String solverName;

    CasADiSolver(String solverName) {
        this.solverName = solverName;
    }

    /**
     * Returns the solver name as a string.
     *
     * @return the solver name.
     */
    public String getSolverName() {
        return solverName;
    }

}
