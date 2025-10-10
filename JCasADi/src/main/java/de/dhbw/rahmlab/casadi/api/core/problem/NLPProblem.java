package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.api.core.constraints.AbstractConstraint;
import de.dhbw.rahmlab.casadi.api.core.interfaces.NumericValue;
import de.dhbw.rahmlab.casadi.api.core.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.solver.CasADiSolver;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MapStringToMXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.str.StringVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Opti;
import de.dhbw.rahmlab.casadi.impl.casadi.OptiAdvanced;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;

import java.util.Arrays;

/**
 * Represents a non-linear programming (NLP) problem, providing methods to define and solve optimization problems.
 * This class allows the addition of variables, parameters, constraints, and objective functions, as well as solver configuration.
 */
public class NLPProblem {

    private final Opti nlpProblem;

    /**
     * Constructs an NLPProblem with a specified problem type.
     *
     * @param problem_type the type of the optimization problem.
     */
    public NLPProblem(String problem_type) {
        this.nlpProblem = new Opti(problem_type);
    }

    /**
     * Constructs an NLPProblem with default settings.
     */
    public NLPProblem() {
        this.nlpProblem = new Opti();
    }

    /**
     * Constructs an NLPProblem using an existing Opti object.
     *
     * @param other the Opti object used to initialize the problem.
     */
    public NLPProblem(Opti other) {
        this.nlpProblem = other;
    }

    /**
     * Constructs an NLPProblem using another NLPProblem object.
     *
     * @param other the NLPProblem object used to initialize the problem.
     */
    public NLPProblem(NLPProblem other) {
        this.nlpProblem = other.getCasADiObject();
    }

    /**
     * Adds a decision variable to the optimization problem.
     *
     * @param variable an MXWrapper representing the decision variable.
     * @return an MXWrapper representing the added variable.
     */
    public MXWrapper addVariable(MXWrapper variable) {
        long nrows = variable.rows();
        long mcols = variable.columns();
        return new MXWrapper(this.nlpProblem.variable(nrows, mcols));
    }

    /**
     * Adds a decision variable with specified dimensions and an attribute.
     *
     * @param nrows     the number of rows for the variable.
     * @param mcols     the number of columns for the variable.
     * @param attribute the attribute for the variable.
     * @return an MXWrapper representing the added variable.
     */
    public MXWrapper addVariable(long nrows, long mcols, String attribute) {
        return new MXWrapper(this.nlpProblem.variable(nrows, mcols, attribute));
    }

    /**
     * Adds a decision variable with specified dimensions.
     *
     * @param nrows the number of rows for the variable.
     * @param mcols the number of columns for the variable.
     * @return an MXWrapper representing the added variable.
     */
    public MXWrapper addVariable(long nrows, long mcols) {
        return new MXWrapper(this.nlpProblem.variable(nrows, mcols));
    }

    /**
     * Adds a decision variable with a specified number of rows.
     *
     * @param nrows the number of rows for the variable.
     * @return an MXWrapper representing the added variable.
     */
    public MXWrapper addVariable(long nrows) {
        return new MXWrapper(this.nlpProblem.variable(nrows));
    }

    /**
     * Adds a decision variable with default dimensions.
     *
     * @return an MXWrapper representing the added variable.
     */
    public MXWrapper addVariable() {
        return new MXWrapper(this.nlpProblem.variable());
    }

    /**
     * Adds a parameter to the optimization problem.
     *
     * @param parameter an MXWrapper representing the parameter.
     * @return an MXWrapper representing the added parameter.
     */
    public MXWrapper addParameter(MXWrapper parameter) {
        long nrows = parameter.rows();
        long mcols = parameter.columns();
        return new MXWrapper(this.nlpProblem.parameter(nrows, mcols));
    }

    /**
     * Adds a parameter with specified dimensions and an attribute.
     *
     * @param nrows     the number of rows for the parameter.
     * @param mcols     the number of columns for the parameter.
     * @param attribute the attribute for the parameter.
     * @return an MXWrapper representing the added parameter.
     */
    public MXWrapper addParameter(long nrows, long mcols, String attribute) {
        return new MXWrapper(this.nlpProblem.parameter(nrows, mcols, attribute));
    }

    /**
     * Adds a parameter with specified dimensions.
     *
     * @param nrows the number of rows for the parameter.
     * @param mcols the number of columns for the parameter.
     * @return an MXWrapper representing the added parameter.
     */
    public MXWrapper addParameter(long nrows, long mcols) {
        return new MXWrapper(this.nlpProblem.parameter(nrows, mcols));
    }

    /**
     * Adds a parameter with a specified number of rows.
     *
     * @param nrows the number of rows for the parameter.
     * @return an MXWrapper representing the added parameter.
     */
    public MXWrapper addParameter(long nrows) {
        return new MXWrapper(this.nlpProblem.parameter(nrows));
    }

    /**
     * Adds a parameter with default dimensions.
     *
     * @return an MXWrapper representing the added parameter.
     */
    public MXWrapper addParameter() {
        return new MXWrapper(this.nlpProblem.parameter());
    }

    /**
     * Sets the objective function to be minimized in the optimization problem.
     *
     * @param f an MXWrapper representing the objective function.
     */
    public void minimize(MXWrapper f) {
        this.nlpProblem.minimize(f.getCasADiObject());
    }

    /**
     * Adds a constraint to the optimization problem.
     *
     * @param abstractConstraint an AbstractConstraint representing the constraint.
     * @throws IllegalArgumentException if the constraint expression is null.
     */
    public void addConstraint(AbstractConstraint abstractConstraint) {
        if (abstractConstraint.getExpression() != null) {
            // ConstraintType swigType = SwigConstraintTypeAdapter.toSwigType(abstractConstraint);
            this.nlpProblem.subject_to(abstractConstraint.getExpression().getCasADiObject());
        } else {
            throw new IllegalArgumentException("The constraint must be either of type MXWrapper or SXWrapper.");
        }
    }

    /**
     * Adds multiple constraints to the optimization problem.
     *
     * @param constraints an array of MXWrapper representing the constraints.
     */
    public void addConstraints(MXWrapper... constraints) {
        MXVector vector = new MXVector(constraints);
        this.nlpProblem.subject_to(vector.getCasADiObject());
    }

    /**
     * Adds multiple abstract constraints to the optimization problem.
     *
     * @param abstractConstraints an array of AbstractConstraint representing the constraints.
     */
    public void addConstraints(AbstractConstraint... abstractConstraints) {
        MXVector vector = new MXVector(
                Arrays.stream(abstractConstraints)
                        .map(AbstractConstraint::getExpression)
                        .toList()
        );
        this.nlpProblem.subject_to(vector.getCasADiObject());

//        Arrays.stream(abstractConstraints)
//                .map(SwigConstraintTypeAdapter::toSwigType)
//                .forEach(type -> System.out.println("Added constraint with type: " + type));
    }

    /**
     * Clears all constraints from the optimization problem.
     */
    public void clearConstraints() {
        this.nlpProblem.subject_to();
    }

//    public void setSolver(String solver, Dictionary pluginOptions, Dictionary solverOptions) {
//        this.nlpProblem.solver(solver, pluginOptions.getCasADiObject(), solverOptions.getCasADiObject());
//    }
//
//    public void setSolver(String solver, Dictionary pluginOptions) {
//        this.nlpProblem.solver(solver, pluginOptions.getCasADiObject());
//    }
//
//    public void setSolver(String solver) {
//        this.nlpProblem.solver(solver);
//    }

    /**
     * Sets the solver for the optimization problem with specified plugin and solver options.
     *
     * @param solver        the CasADiSolver enumeration value.
     * @param pluginOptions the plugin options as a Dictionary.
     * @param solverOptions the solver options as a Dictionary.
     */
    public void setSolver(CasADiSolver solver, Dictionary pluginOptions, Dictionary solverOptions) {
        this.nlpProblem.solver(solver.getSolverName(), pluginOptions.getCasADiObject(), solverOptions.getCasADiObject());
    }

    /**
     * Sets the solver for the optimization problem with specified plugin options.
     *
     * @param solver        the CasADiSolver enumeration value.
     * @param pluginOptions the plugin options as a Dictionary.
     */
    public void setSolver(CasADiSolver solver, Dictionary pluginOptions) {
        this.nlpProblem.solver(solver.getSolverName(), pluginOptions.getCasADiObject());
    }

    /**
     * Sets the solver for the optimization problem.
     *
     * @param solver the CasADiSolver enumeration value.
     */
    public void setSolver(CasADiSolver solver) {
        this.nlpProblem.solver(solver.getSolverName());
    }

    /**
     * Sets the initial value for a decision variable.
     *
     * @param x an MXWrapper representing the decision variable.
     * @param v a NumericValue representing the initial value.
     * @throws IllegalArgumentException if the numeric type is unsupported.
     */
    public void setInitialDecisionVariable(MXWrapper x, NumericValue v) {
        if (v instanceof DMWrapper) {
            this.nlpProblem.set_initial(x.getCasADiObject(), ((DMWrapper) v).getCasADiObject());
        } else if (v instanceof NumberWrapper) {
            this.nlpProblem.set_initial(x.getCasADiObject(), new DM(((NumberWrapper) v).getDoubleValue()));
        } else {
            throw new IllegalArgumentException("Unsupported numeric type " + v.getClass());
        }
    }

    /**
     * Sets the initial values for a decision variable.
     *
     * @param x an MXWrapper representing the decision variable.
     * @param v an array of Number representing the initial values.
     */
    public void setInitialDecisionVariable(MXWrapper x, Number... v) {
        StdVectorDM dmVector = new StdVectorDM();
        Arrays.stream(v).forEach(value -> {
            dmVector.add(new DM(value.doubleValue()));
        });
        this.nlpProblem.set_initial(x.getCasADiObject(), DmStatic.vertcat(dmVector));
    }

    /**
     * Sets the initial values for multiple decision variables.
     *
     * @param assignments an array of MXWrapper representing the decision variables.
     */
    public void setInitialDecisionVariables(MXWrapper... assignments) {
        MXVector vector = new MXVector(assignments);
        this.nlpProblem.set_initial(vector.getCasADiObject());
    }

    /**
     * Sets the value for a parameter.
     *
     * @param x an MXWrapper representing the parameter.
     * @param v a NumericValue representing the value.
     * @throws IllegalArgumentException if the numeric type is unsupported.
     */
    public void setParameterValue(MXWrapper x, NumericValue v) {
        if (v instanceof DMWrapper) {
            this.nlpProblem.set_value(x.getCasADiObject(), ((DMWrapper) v).getCasADiObject());
        } else if (v instanceof NumberWrapper) {
            this.nlpProblem.set_value(x.getCasADiObject(), new DM(((NumberWrapper) v).getDoubleValue()));
        } else {
            throw new IllegalArgumentException("Unsupported numeric type " + v.getClass());
        }
    }

    /**
     * Sets the values for multiple parameters.
     *
     * @param assignments an array of MXWrapper representing the parameters.
     */
    public void setParameterValues(MXWrapper... assignments) {
        MXVector vector = new MXVector(assignments);
        this.nlpProblem.set_initial(vector.getCasADiObject());
    }

    /**
     * Solves the optimization problem and returns the result.
     *
     * @return an NLPResult representing the solution.
     */
    public NLPResult solve() {
        return new NLPResult(this.nlpProblem.solve());
    }

    /**
     * Solves the optimization problem with limited resources and returns the result.
     *
     * @return an NLPResult representing the solution.
     */
    public NLPResult solveWithLimits() {
        return new NLPResult(this.nlpProblem.solve_limited());
    }

    /**
     * Retrieves the value of a variable or expression.
     *
     * @param x      a Wrapper representing the variable or expression.
     * @param values optional MXWrapper values for the expression.
     * @return a DMWrapper representing the value.
     * @throws IllegalArgumentException if the wrapper object is unsupported.
     */
    public DMWrapper getValue(Wrapper x, MXWrapper... values) {
        if (values == null || values.length == 0) {
            return handleGetValueWithoutParameters(x);
        } else {
            MXVector vector = new MXVector(values);
            return handleGetValueWithParameters(x, vector);
        }
    }

    /**
     * Retrieves the value of a variable or expression with specified values.
     *
     * @param x      a Wrapper representing the variable or expression.
     * @param values an MXVector representing the values for the expression.
     * @return a DMWrapper representing the value.
     * @throws IllegalArgumentException if the wrapper object is unsupported.
     */
    public DMWrapper getValue(Wrapper x, MXVector values) {
        if (values == null) {
            return handleGetValueWithoutParameters(x);
        } else {
            return handleGetValueWithParameters(x, values);
        }
    }

    /**
     * Handles the retrieval of value without parameters for a given wrapper.
     *
     * @param x a Wrapper representing the variable or expression.
     * @return a DMWrapper representing the value.
     * @throws IllegalArgumentException if the wrapper object is unsupported.
     */
    private DMWrapper handleGetValueWithoutParameters(Wrapper x) {
        if (x instanceof MXWrapper mxWrapper) {
            return new DMWrapper(this.nlpProblem.value((mxWrapper.getCasADiObject())));
        } else if (x instanceof DMWrapper dmWrapper) {
            return new DMWrapper(this.nlpProblem.value(dmWrapper.getCasADiObject()));
        } else if (x instanceof SXWrapper sxWrapper) {
            return new DMWrapper(this.nlpProblem.value(sxWrapper.getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported wrapper object " + x.getClass());
        }
    }

    /**
     * Handles the retrieval of value with parameters for a given wrapper.
     *
     * @param x      a Wrapper representing the variable or expression.
     * @param values an MXVector representing the values for the expression.
     * @return a DMWrapper representing the value.
     * @throws IllegalArgumentException if the wrapper object is unsupported.
     */
    private DMWrapper handleGetValueWithParameters(Wrapper x, MXVector values) {
        if (x instanceof MXWrapper mxWrapper) {
            return new DMWrapper(this.nlpProblem.value(mxWrapper.getCasADiObject(), values.getCasADiObject()));
        } else if (x instanceof DMWrapper dmWrapper) {
            return new DMWrapper(this.nlpProblem.value(dmWrapper.getCasADiObject(), values.getCasADiObject()));
        } else if (x instanceof SXWrapper sxWrapper) {
            return new DMWrapper(this.nlpProblem.value(sxWrapper.getCasADiObject(), values.getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported wrapper object: " + x.getClass());
        }
    }

    /**
     * Retrieves the statistics of the optimization problem.
     *
     * @return a Dictionary representing the statistics.
     */
    public Dictionary getStatistics() {
        return new Dictionary(this.nlpProblem.stats());
    }

    /**
     * Retrieves the status of the solver.
     *
     * @return a string representing the solver status.
     */
    public String getSolverStatus() {
        return this.nlpProblem.return_status();
    }

    /**
     * Retrieves the initial assignments of the optimization problem.
     *
     * @return an MXVector representing the initial assignments.
     */
    public MXVector getInitialAssignments() {
        return new MXVector(this.nlpProblem.initial());
    }

    /**
     * Retrieves the latest values of the decision variables.
     *
     * @return an MXVector representing the latest variable values.
     */
    public MXVector getLatestVariableValues() {
        return new MXVector(this.nlpProblem.value_variables());
    }

    /**
     * Retrieves the latest values of the parameters.
     *
     * @return an MXVector representing the latest parameter values.
     */
    public MXVector getLatestParameterValues() {
        return new MXVector(this.nlpProblem.value_parameters());
    }

    /**
     * Retrieves the dual variable associated with a constraint.
     *
     * @param constraint an MXWrapper representing the constraint.
     * @return an MXWrapper representing the dual variable.
     */
    public MXWrapper getDualVariable(MXWrapper constraint) {
        return new MXWrapper(this.nlpProblem.dual(constraint.getCasADiObject()));
    }

    /**
     * Retrieves the number of decision variables in the optimization problem.
     *
     * @return the number of decision variables.
     */
    public long getNumberOfDecisionVariables() {
        return this.nlpProblem.nx();
    }

    /**
     * Retrieves the number of parameters in the optimization problem.
     *
     * @return the number of parameters.
     */
    public long getNumberOfParameters() {
        return this.nlpProblem.np();
    }

    /**
     * Retrieves the number of constraints in the optimization problem.
     *
     * @return the number of constraints.
     */
    public long getNumberOfConstraints() {
        return this.nlpProblem.ng();
    }

    /**
     * Retrieves the decision variables of the optimization problem.
     *
     * @return an MXWrapper representing the decision variables.
     */
    public MXWrapper getDecisionVariables() {
        return new MXWrapper(this.nlpProblem.x());
    }

    /**
     * Retrieves the parameters of the optimization problem.
     *
     * @return an MXWrapper representing the parameters.
     */
    public MXWrapper getParameters() {
        return new MXWrapper(this.nlpProblem.p());
    }

    /**
     * Retrieves the constraints of the optimization problem.
     *
     * @return an MXWrapper representing the constraints.
     */
    public MXWrapper getConstraints() {
        return new MXWrapper(this.nlpProblem.g());
    }

    /**
     * Retrieves the objective function of the optimization problem.
     *
     * @return an MXWrapper representing the objective function.
     */
    public MXWrapper getObjectiveFunction() {
        return new MXWrapper(this.nlpProblem.f());
    }

    /**
     * Retrieves the lower bounds of the constraints.
     *
     * @return an MXWrapper representing the lower bounds.
     */
    public MXWrapper getLowerBounds() {
        return new MXWrapper(this.nlpProblem.lbg());
    }

    /**
     * Retrieves the upper bounds of the constraints.
     *
     * @return an MXWrapper representing the upper bounds.
     */
    public MXWrapper getUpperBounds() {
        return new MXWrapper(this.nlpProblem.ubg());
    }

    /**
     * Retrieves the dual variables associated with the constraints.
     *
     * @return an MXWrapper representing the dual variables.
     */
    public MXWrapper getDualVariables() {
        return new MXWrapper(this.nlpProblem.lam_g());
    }

    /**
     * Creates a function from the optimization problem with specified arguments, results, and options.
     *
     * @param name   the name of the function.
     * @param args   an MXVector representing the function arguments.
     * @param res    an MXVector representing the function results.
     * @param opts   a Dictionary representing the function options.
     * @return a FunctionWrapper representing the created function.
     */
    public FunctionWrapper createFunction(String name, MXVector args, MXVector res, Dictionary opts) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, args.getCasADiObject(), res.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Creates a function from the optimization problem with specified arguments and results.
     *
     * @param name the name of the function.
     * @param args an MXVector representing the function arguments.
     * @param res  an MXVector representing the function results.
     * @return a FunctionWrapper representing the created function.
     */
    public FunctionWrapper createFunction(String name, MXVector args, MXVector res) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, args.getCasADiObject(), res.getCasADiObject()));
    }

    /**
     * Creates a function from the optimization problem with specified arguments, results, input names, output names, and options.
     *
     * @param name     the name of the function.
     * @param args     an MXVector representing the function arguments.
     * @param res      an MXVector representing the function results.
     * @param nameIn   a StringVector representing the input names.
     * @param nameOut  a StringVector representing the output names.
     * @param opts     a Dictionary representing the function options.
     * @return a FunctionWrapper representing the created function.
     */
    public FunctionWrapper createFunction(String name, MXVector args, MXVector res, StringVector nameIn, StringVector nameOut, Dictionary opts) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, args.getCasADiObject(), res.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Creates a function from the optimization problem with specified arguments, results, input names, and output names.
     *
     * @param name     the name of the function.
     * @param args     an MXVector representing the function arguments.
     * @param res      an MXVector representing the function results.
     * @param nameIn   a StringVector representing the input names.
     * @param nameOut  a StringVector representing the output names.
     * @return a FunctionWrapper representing the created function.
     */
    public FunctionWrapper createFunction(String name, MXVector args, MXVector res, StringVector nameIn, StringVector nameOut) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, args.getCasADiObject(), res.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject()));
    }

    /**
     * Creates a function from the optimization problem with specified dictionary, input names, output names, and options.
     *
     * @param name     the name of the function.
     * @param dict     a MapStringToMXWrapper representing the function dictionary.
     * @param nameIn   a StringVector representing the input names.
     * @param nameOut  a StringVector representing the output names.
     * @param opts     a Dictionary representing the function options.
     * @return a FunctionWrapper representing the created function.
     */
    public FunctionWrapper createFunction(String name, MapStringToMXWrapper dict, StringVector nameIn, StringVector nameOut, Dictionary opts) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, dict.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts.getCasADiObject()));
    }

    /**
     * Creates a function from the optimization problem with specified dictionary, input names, and output names.
     *
     * @param name     the name of the function.
     * @param dict     a MapStringToMXWrapper representing the function dictionary.
     * @param nameIn   a StringVector representing the input names.
     * @param nameOut  a StringVector representing the output names.
     * @return a FunctionWrapper representing the created function.
     */
    public FunctionWrapper createFunction(String name, MapStringToMXWrapper dict, StringVector nameIn, StringVector nameOut) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, dict.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject()));
    }

    /**
     * Creates a bounded constraint with specified lower bound, expression, and upper bound.
     *
     * @param lowerBound  an MXWrapper representing the lower bound.
     * @param expression  an MXWrapper representing the expression.
     * @param upperBound  an MXWrapper representing the upper bound.
     * @return an MXWrapper representing the bounded constraint.
     */
    public MXWrapper createBoundedConstraint(MXWrapper lowerBound, MXWrapper expression, MXWrapper upperBound) {
        return new MXWrapper(Opti.bounded(lowerBound.getCasADiObject(), expression.getCasADiObject(), upperBound.getCasADiObject()));
    }

    /**
     * Retrieves a debug copy of the optimization problem.
     *
     * @return an OptiAdvanced representing the debug copy.
     */
    public OptiAdvanced getDebugCopy() {
        return this.nlpProblem.debug();
    }

    /**
     * Retrieves an advanced copy of the optimization problem.
     *
     * @return an OptiAdvanced representing the advanced copy.
     */
    public OptiAdvanced getAdvancedCopy() {
        return this.nlpProblem.advanced();
    }

    /**
     * Creates a copy of the optimization problem.
     *
     * @return a new NLPProblem representing the copied problem.
     */
    public NLPProblem copy() {
        return new NLPProblem(this.nlpProblem.copy());
    }

    /**
     * Adds user data to the optimization problem for a specified symbol.
     *
     * @param symbol an MXWrapper representing the symbol.
     * @param meta   a Dictionary representing the metadata.
     */
    public void addUserData(MXWrapper symbol, Dictionary meta) {
        this.nlpProblem.update_user_dict(symbol.getCasADiObject(), meta.getCasADiObject());
    }

    /**
     * Adds user data to the optimization problem for specified symbols.
     *
     * @param symbol    an MXVector representing the symbols.
     * @param metadata  a Dictionary representing the metadata.
     */
    public void addUserData(MXVector symbol, Dictionary metadata) {
        this.nlpProblem.update_user_dict(symbol.getCasADiObject(), metadata.getCasADiObject());
    }

    /**
     * Retrieves user data associated with a specified symbol.
     *
     * @param symbol an MXWrapper representing the symbol.
     * @return a Dictionary representing the user data.
     */
    public Dictionary getUserData(MXWrapper symbol) {
        return new Dictionary(this.nlpProblem.user_dict(symbol.getCasADiObject()));
    }

    /**
     * Retrieves the type name of the optimization problem.
     *
     * @return a string representing the type name.
     */
    public String getTypeName() {
        return this.nlpProblem.type_name();
    }

    /**
     * Converts the optimization problem to a string representation with additional details.
     *
     * @param more a flag indicating whether to include more details.
     * @return a string representation of the optimization problem.
     */
    public String toString(boolean more) {
        return this.nlpProblem.toString(more);
    }

    /**
     * Converts the optimization problem to a string representation.
     *
     * @return a string representation of the optimization problem.
     */
    public String toString() {
        return this.nlpProblem.toString();
    }

    /**
     * Registers a callback for the optimization problem.
     *
     * @param callback an NLPCallback representing the callback.
     */
    private void registerCallback(NLPCallback callback) {
        this.nlpProblem.callback_class(callback.getCasADiObject());
    }

    /**
     * Registers a default callback for the optimization problem.
     */
    private void registerCallback() {
        this.nlpProblem.callback_class();
    }

    /**
     * Retrieves the underlying CasADi object.
     *
     * @return the Opti object representing the optimization problem.
     */
    public Opti getCasADiObject() {
        return nlpProblem;
    }
}