package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.api.core.constraints.AbstractConstraint;
import de.dhbw.rahmlab.casadi.api.core.constraints.Comparison;
import de.dhbw.rahmlab.casadi.api.core.constraints.ConstraintBuilder;
import de.dhbw.rahmlab.casadi.api.core.solver.CasADiSolver;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for creating NLPProblem objects.
 * <p>
 * This builder provides a fluent API to configure non-linear optimization problems
 * in a way that closely resembles mathematical notation. It allows the addition of decision variables,
 * parameters, an objective function, constraints, and solver settings.
 * <p>
 * Example usage:
 * <pre>
 *     NLPProblem nlp = new NLPProblemBuilder()
 *                        .withProblemType("my_problem")
 *                        .addVariable(variable1)
 *                        .addParameter(parameter1)
 *                        .minimize(objective)
 *                        .addConstraint(constraint1)
 *                        .setSolver(CasADiSolver.IPOPT)
 *                        .build();
 * </pre>
 */
public class NLPProblemBuilder {

    private String problemType = null;
    private final List<MXWrapper> decisionVariables = new ArrayList<>();
    private final List<MXWrapper> parameters = new ArrayList<>();
    private final List<AbstractConstraint> abstractConstraints = new ArrayList<>();
    private MXWrapper objective = null;
    private CasADiSolver solver = null;
    private Dictionary pluginOptions = null;
    private Dictionary solverOptions = null;
    private final List<InitialAssignment> initialAssignments = new ArrayList<>();

    /**
     * Represents an initial assignment for a decision variable in the NLPProblem.
     * This class is used to store the variable and its initial values for configuration during the problem building process.
     */
    private static class InitialAssignment {
        MXWrapper variable;
        Number[] values;

        /**
         * Constructs an InitialAssignment with a specified variable and its initial values.
         *
         * @param variable the MXWrapper representing the decision variable.
         * @param values   the initial values for the variable.
         */
        InitialAssignment(MXWrapper variable, Number... values) {
            this.variable = variable;
            this.values = values;
        }
    }

    /**
     * Specifies the type of the optimization problem.
     *
     * @param problemType the problem type as a string.
     * @return the current builder instance.
     */
    public NLPProblemBuilder withProblemType(String problemType) {
        this.problemType = problemType;
        return this;
    }

    /**
     * Adds a decision variable to the optimization problem.
     *
     * @param variable an MXWrapper representing a decision variable.
     * @return the current builder instance.
     */
    public NLPProblemBuilder addVariable(MXWrapper variable) {
        this.decisionVariables.add(variable);
        return this;
    }

    /**
     * Adds a parameter to the optimization problem.
     *
     * @param parameter an MXWrapper representing a parameter.
     * @return the current builder instance.
     */
    public NLPProblemBuilder addParameter(MXWrapper parameter) {
        this.parameters.add(parameter);
        return this;
    }

    /**
     * Sets the objective function (to be minimized) for the optimization problem.
     *
     * @param f an MXWrapper representing the objective function.
     * @return the current builder instance.
     */
    public NLPProblemBuilder minimize(MXWrapper f) {
        this.objective = f;
        return this;
    }

    /**
     * Adds a constraint to the optimization problem.
     *
     * @param abstractConstraint an AbstractConstraint object representing a constraint.
     * @return the current builder instance.
     */
    public NLPProblemBuilder addConstraint(AbstractConstraint abstractConstraint) {
        this.abstractConstraints.add(abstractConstraint);
        return this;
    }

    /**
     * Adds a binary constraint of the form lhs (cmp) rhs.
     *
     * @param lhs left-hand side expression
     * @param cmp comparison operator (EQ, LE, GE, LT, GT)
     * @param rhs right-hand side expression
     * @return the current builder instance.
     */
    public NLPProblemBuilder addConstraint(MXWrapper lhs,
                                           Comparison cmp,
                                           MXWrapper rhs) {
        AbstractConstraint c = (AbstractConstraint) ConstraintBuilder
                .of(lhs)
                .cmp(cmp)
                .rhs(rhs)
                .build();
        this.abstractConstraints.add(c);
        return this;
    }

    /**
     * Specifies the solver to be used.
     *
     * @param solver the CasADiSolver enumeration value.
     * @return the current builder instance.
     */
    public NLPProblemBuilder setSolver(CasADiSolver solver) {
        this.solver = solver;
        return this;
    }

    /**
     * Specifies the solver along with plugin and solver options.
     *
     * @param solver        the CasADiSolver enumeration value.
     * @param pluginOptions the plugin options as a Dictionary.
     * @param solverOptions the solver options as a Dictionary.
     * @return the current builder instance.
     */
    public NLPProblemBuilder setSolver(CasADiSolver solver, Dictionary pluginOptions, Dictionary solverOptions) {
        this.solver = solver;
        this.pluginOptions = pluginOptions;
        this.solverOptions = solverOptions;
        return this;
    }

    /**
     * Sets initial values for a decision variable.
     *
     * @param x the MXWrapper representing the decision variable.
     * @param v the initial values for the variable.
     * @return the current builder instance.
     */
    public NLPProblemBuilder setInitialDecisionVariable(MXWrapper x, Number... v) {
        initialAssignments.add(new InitialAssignment(x, v));
        return this;
    }

    /**
     * Sets initial values for multiple decision variables.
     *
     * @param assignments the MXWrapper objects representing the decision variables.
     * @return the current builder instance.
     */
    public NLPProblemBuilder setInitialDecisionVariable(MXWrapper... assignments) {
        for (MXWrapper assignment : assignments) {
            initialAssignments.add(new InitialAssignment(assignment));
        }
        return this;
    }

    /**
     * Builds the NLPProblem using the current configuration.
     *
     * @return an NLPProblem object representing the constructed non-linear optimization problem.
     */
    public NLPProblem build() {
        NLPProblem problem;
        if (problemType != null && !problemType.isEmpty()) {
            problem = new NLPProblem(problemType);
        } else {
            problem = new NLPProblem();
        }
        // Add decision variables
        for (MXWrapper var : decisionVariables) {
            problem.addVariable(var);
        }
        // Add parameters
        for (MXWrapper param : parameters) {
            problem.addParameter(param);
        }
        // Set the objective function
        if (objective != null) {
            problem.minimize(objective);
        }
        // Add all constraints
        for (AbstractConstraint c : abstractConstraints) {
            problem.addConstraint(c);
        }
        // Set solver if defined
        if (solver != null) {
            if (pluginOptions != null && solverOptions != null) {
                problem.setSolver(solver, pluginOptions, solverOptions);
            } else if (pluginOptions != null) {
                problem.setSolver(solver, pluginOptions);
            } else {
                problem.setSolver(solver);
            }
        }
        // Set initial values for decision variables
        for (InitialAssignment assignment : initialAssignments) {
            if (assignment.values != null && assignment.values.length > 0) {
                problem.setInitialDecisionVariable(assignment.variable, assignment.values);
            } else {
                problem.setInitialDecisionVariables(assignment.variable);
            }
        }
        return problem;
    }
}
