package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.MapStringToDMWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.*;

/**
 * Represents an advanced NLP (Non-Linear Programming) model that extends the capabilities of an NLPProblem.
 * This class provides various methods to interact with and manipulate the underlying optimization problem.
 */
public class NLPAdvancedModel extends NLPProblem {

    private final OptiAdvanced optiAdvanced;

    /**
     * Constructs an NLPAdvancedModel using an Opti object.
     *
     * @param x the Opti object used to initialize the model.
     */
    public NLPAdvancedModel(Opti x) {
        this.optiAdvanced = new OptiAdvanced(x);
    }

    /**
     * Constructs an NLPAdvancedModel using an NLPProblem object.
     *
     * @param x the NLPProblem object used to initialize the model.
     */
    public NLPAdvancedModel(NLPProblem x) {
        this.optiAdvanced = new OptiAdvanced(x.getCasADiObject());
    }

    /**
     * Constructs an NLPAdvancedModel using another OptiAdvanced object.
     *
     * @param other the OptiAdvanced object used to initialize the model.
     */
    public NLPAdvancedModel(OptiAdvanced other) {
        this.optiAdvanced = new OptiAdvanced(other);
    }

    /**
     * Constructs an NLPAdvancedModel using another NLPAdvancedModel object.
     *
     * @param other the NLPAdvancedModel object used to initialize the model.
     */
    public NLPAdvancedModel(NLPAdvancedModel other) {
        this.optiAdvanced = new OptiAdvanced(other.getCasADiObject());
    }

    /**
     * Retrieves the CasADi solver wrapped in a FunctionWrapper.
     *
     * @return the FunctionWrapper containing the CasADi solver.
     */
    public FunctionWrapper getCasADiSolver() {
        return new FunctionWrapper(this.optiAdvanced.casadi_solver());
    }

    /**
     * Checks if the given expression is parametric.
     *
     * @param expression the expression to check.
     * @return true if the expression is parametric, false otherwise.
     */
    public boolean isParametric(MXWrapper expression) {
        return this.optiAdvanced.is_parametric(expression.getCasADiObject());
    }

    /**
     * Retrieves the symbolic variables of the model.
     *
     * @return an MXVector containing the symbolic variables.
     */
    public MXVector getSymbolicVariables() {
        return new MXVector(this.optiAdvanced.symvar());
    }

    /**
     * Retrieves the symbolic variables associated with a specific expression.
     *
     * @param expression the expression to retrieve symbolic variables for.
     * @return an MXVector containing the symbolic variables.
     */
    public MXVector getSymbolicVariables(MXWrapper expression) {
        return new MXVector(this.optiAdvanced.symvar(expression.getCasADiObject()));
    }

    /**
     * Retrieves the symbolic variables associated with a specific expression and variable type.
     *
     * @param expression the expression to retrieve symbolic variables for.
     * @param type       the type of variables to retrieve.
     * @return an MXVector containing the symbolic variables.
     */
    public MXVector getSymbolicVariables(MXWrapper expression, VariableType type) {
        return new MXVector(this.optiAdvanced.symvar(expression.getCasADiObject(), type));
    }

    /**
     * Sets metadata for a given expression.
     *
     * @param m    the expression to set metadata for.
     * @param meta the metadata to set.
     */
    public void setMetaData(MXWrapper m, MetaVar meta) {
        this.optiAdvanced.set_meta(m.getCasADiObject(), meta);
    }

    /**
     * Sets constraint metadata for a given expression.
     *
     * @param m    the expression to set constraint metadata for.
     * @param meta the constraint metadata to set.
     */
    public void setMetaData(MXWrapper m, MetaCon meta) {
        this.optiAdvanced.set_meta_con(m.getCasADiObject(), meta);
    }

    /**
     * Asserts that a given symbol is active.
     *
     * @param m the symbol to assert as active.
     */
    public void assertActiveSymbol(MXWrapper m) {
        this.optiAdvanced.assert_active_symbol(m.getCasADiObject());
    }

    /**
     * Retrieves active symbolic variables of a specific type.
     *
     * @param type the type of variables to retrieve.
     * @return an MXVector containing the active symbolic variables.
     */
    public MXVector activeSymbolicVariable(VariableType type) {
        return new MXVector(this.optiAdvanced.active_symvar(type));
    }

    /**
     * Retrieves active values of a specific type.
     *
     * @param type the type of values to retrieve.
     * @return a DMVector containing the active values.
     */
    public DMVector activeValues(VariableType type) {
        return new DMVector(this.optiAdvanced.active_values(type));
    }

    /**
     * Retrieves a variable by its index.
     *
     * @param index the index of the variable to retrieve.
     * @return an MXWrapper containing the variable.
     */
    public MXWrapper getVariableByIndex(long index) {
        return new MXWrapper(this.optiAdvanced.x_lookup(index));
    }

    /**
     * Retrieves a gradient by its index.
     *
     * @param index the index of the gradient to retrieve.
     * @return an MXWrapper containing the gradient.
     */
    public MXWrapper getGradientByIndex(long index) {
        return new MXWrapper(this.optiAdvanced.g_lookup(index));
    }

    /**
     * Describes a variable by its index.
     *
     * @param i the index of the variable to describe.
     * @return a string description of the variable.
     */
    public String describeVariable(long i) {
        return this.optiAdvanced.x_describe(i);
    }

    /**
     * Describes a gradient by its index.
     *
     * @param i the index of the gradient to describe.
     * @return a string description of the gradient.
     */
    public String describeGradient(long i) {
        return this.optiAdvanced.g_describe(i);
    }

    /**
     * Describes an expression with a specified level of indentation.
     *
     * @param x      the expression to describe.
     * @param indent the level of indentation.
     * @return a string description of the expression.
     */
    public String describeExpressionWithIndentation(MXWrapper x, long indent) {
        return this.optiAdvanced.describe(x.getCasADiObject(), indent);
    }

    /**
     * Describes an expression.
     *
     * @param x the expression to describe.
     * @return a string description of the expression.
     */
    public String describeExpression(MXWrapper x) {
        return this.optiAdvanced.describe(x.getCasADiObject());
    }

    /**
     * Displays infeasibilities with a specified tolerance.
     *
     * @param tol the tolerance level for displaying infeasibilities.
     */
    public void displayInfeasibilitiesWithTolerance(double tol) {
        this.optiAdvanced.show_infeasibilities(tol);
    }

    /**
     * Displays infeasibilities.
     */
    public void displayInfeasibilities() {
        this.optiAdvanced.show_infeasibilities();
    }

    /**
     * Prepares the model for solving.
     */
    public void prepareForSolving() {
        this.optiAdvanced.solve_prepare();
    }

    /**
     * Executes the solve process with specified arguments.
     *
     * @param args the arguments for solving.
     * @return a MapStringToDMWrapper containing the results.
     */
    public MapStringToDMWrapper executeSolveWithArguments(MapStringToDMWrapper args) {
        return new MapStringToDMWrapper(this.optiAdvanced.solve_actual(args.getCasADiObject()));
    }

    /**
     * Retrieves the arguments for solving.
     *
     * @return a MapStringToDMWrapper containing the arguments.
     */
    public MapStringToDMWrapper arg() {
        return new MapStringToDMWrapper(this.optiAdvanced.arg());
    }

    /**
     * Sets the result of solving.
     *
     * @param res the result to set.
     */
    public void res(MapStringToDMWrapper res) {
        this.optiAdvanced.res(res.getCasADiObject());
    }

    /**
     * Retrieves the result of solving.
     *
     * @return a MapStringToDMWrapper containing the result.
     */
    public MapStringToDMWrapper res() {
        return new MapStringToDMWrapper(this.optiAdvanced.res());
    }

    /**
     * Retrieves the constraints of the model.
     *
     * @return an MXVector containing the constraints.
     */
    public MXVector retrieveConstraints() {
        return new MXVector(this.optiAdvanced.constraints());
    }

    /**
     * Retrieves the objective of the model.
     *
     * @return an MXWrapper containing the objective.
     */
    public MXWrapper objective() {
        return new MXWrapper(this.optiAdvanced.objective());
    }

    /**
     * Creates a baked copy of the current model.
     *
     * @return a new NLPAdvancedModel that is a baked copy of the current model.
     */
    public NLPAdvancedModel createBakedCopy() {
        return new NLPAdvancedModel(this.optiAdvanced.baked_copy());
    }

    /**
     * Validates that the model is empty.
     */
    public void validateEmpty() {
        this.optiAdvanced.assert_empty();
    }

    /**
     * Finalizes the structure of the model.
     */
    public void finalizeStructure() {
        this.optiAdvanced.bake();
    }

    /**
     * Checks if the problem is marked as dirty.
     *
     * @return true if the problem is dirty, false otherwise.
     */
    public boolean getProblemDirty() {
        return this.optiAdvanced.getProblem_dirty_();
    }

    /**
     * Marks the problem as dirty with a specified flag.
     *
     * @param flag the flag indicating whether the problem is dirty.
     */
    public void markProblemDirty(boolean flag) {
        this.optiAdvanced.mark_problem_dirty(flag);
    }

    /**
     * Marks the problem as dirty.
     */
    public void markProblemDirty() {
        this.optiAdvanced.mark_problem_dirty();
    }

    /**
     * Checks if the problem is dirty.
     *
     * @return true if the problem is dirty, false otherwise.
     */
    public boolean isProblemDirty() {
        return this.optiAdvanced.problem_dirty();
    }

    /**
     * Checks if the solver is marked as dirty.
     *
     * @return true if the solver is dirty, false otherwise.
     */
    public boolean getSolverDirty() {
        return optiAdvanced.getSolver_dirty_();
    }

    /**
     * Marks the solver as dirty with a specified flag.
     *
     * @param flag the flag indicating whether the solver is dirty.
     */
    public void markSolverDirty(boolean flag) {
        this.optiAdvanced.mark_solver_dirty(flag);
    }

    /**
     * Marks the solver as dirty.
     */
    public void markSolverDirty() {
        this.optiAdvanced.mark_solver_dirty();
    }

    /**
     * Checks if the solver is dirty.
     *
     * @return true if the solver is dirty, false otherwise.
     */
    public boolean isSolverDirty() {
        return this.optiAdvanced.solver_dirty();
    }

    /**
     * Checks if the model is solved.
     *
     * @return true if the model is solved, false otherwise.
     */
    public boolean getSolved() {
        return this.optiAdvanced.getSolved_();
    }

    /**
     * Sets the solved status of the model with a specified flag.
     *
     * @param flag the flag indicating whether the model is solved.
     */
    public void setSolvedStatus(boolean flag) {
        this.optiAdvanced.mark_solved(flag);
    }

    /**
     * Marks the model as solved.
     */
    public void markAsSolved() {
        this.optiAdvanced.mark_solved();
    }

    /**
     * Checks if the model is solved.
     *
     * @return true if the model is solved, false otherwise.
     */
    public boolean isSolved() {
        return this.optiAdvanced.solved();
    }

    /**
     * Validates that the model is solved.
     */
    public void validateSolved() {
        this.optiAdvanced.assert_solved();
    }

    /**
     * Validates that the model is baked.
     */
    public void validateBaked() {
        this.optiAdvanced.assert_baked();
    }

    /**
     * Retrieves the instance number of the model.
     *
     * @return the instance number.
     */
    public long getInstanceNumber() {
        return this.optiAdvanced.instance_number();
    }

    /**
     * Retrieves the underlying CasADi object.
     *
     * @return the OptiAdvanced object.
     */
    public OptiAdvanced getCasADiObject() {
        return this.optiAdvanced;
    }

    /**
     * Canonicalizes an expression.
     *
     * @param expression the expression to canonicalize.
     * @return the canonicalized MetaCon object.
     */
    private MetaCon canonicalizeExpression(MXWrapper expression) {
        return this.optiAdvanced.canon_expr(expression.getCasADiObject());
    }

    /**
     * Retrieves metadata for a given expression.
     *
     * @param m the expression to retrieve metadata for.
     * @return the MetaVar object containing the metadata.
     */
    private MetaVar getMetaData(MXWrapper m) {
        return this.optiAdvanced.get_meta(m.getCasADiObject());
    }

    /**
     * Retrieves internal symbol metadata for a given expression.
     *
     * @param m the expression to retrieve symbol metadata for.
     * @return the MetaCon object containing the symbol metadata.
     */
    private MetaCon getSymbolMetaDataInternal(MXWrapper m) {
        return this.optiAdvanced.get_meta_con(m.getCasADiObject());
    }

}
