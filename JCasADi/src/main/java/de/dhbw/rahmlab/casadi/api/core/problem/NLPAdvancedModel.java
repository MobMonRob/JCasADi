package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.MapStringToDMWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.*;

public class NLPAdvancedModel extends NLPProblem {

    private final OptiAdvanced optiAdvanced;

    public NLPAdvancedModel(Opti x) {
        this.optiAdvanced = new OptiAdvanced(x);
    }

    public NLPAdvancedModel(NLPProblem x) {
        this.optiAdvanced = new OptiAdvanced(x.getCasADiObject());
    }

    public NLPAdvancedModel(OptiAdvanced other) {
        this.optiAdvanced = new OptiAdvanced(other);
    }

    public NLPAdvancedModel(NLPAdvancedModel other) {
        this.optiAdvanced = new OptiAdvanced(other.getCasADiObject());
    }

    public FunctionWrapper getCasADiSolver() {
        return new FunctionWrapper(this.optiAdvanced.casadi_solver());
    }

    public boolean isParametric(MXWrapper expression) {
        return this.optiAdvanced.is_parametric(expression.getCasADiObject());
    }

    public MXVector getSymbolicVariables() {
        return new MXVector(this.optiAdvanced.symvar());
    }

    public MXVector getSymbolicVariables(MXWrapper expression) {
        return new MXVector(this.optiAdvanced.symvar(expression.getCasADiObject()));
    }

    public MXVector getSymbolicVariables(MXWrapper expression, VariableType type) {
        return new MXVector(this.optiAdvanced.symvar(expression.getCasADiObject(), type));
    }

    public void setMetaData(MXWrapper m, MetaVar meta) {
        this.optiAdvanced.set_meta(m.getCasADiObject(), meta);
    }

    public void setMetaData(MXWrapper m, MetaCon meta) {
        this.optiAdvanced.set_meta_con(m.getCasADiObject(), meta);
    }

    public void assertActiveSymbol(MXWrapper m) {
        this.optiAdvanced.assert_active_symbol(m.getCasADiObject());
    }

    public MXVector activeSymbolicVariable(VariableType type) {
        return new MXVector(this.optiAdvanced.active_symvar(type));
    }

    public DMVector activeValues(VariableType type) {
        return new DMVector(this.optiAdvanced.active_values(type));
    }

    public MXWrapper getVariableByIndex(long index) {
        return new MXWrapper(this.optiAdvanced.x_lookup(index));
    }

    public MXWrapper getGradientByIndex(long index) {
        return new MXWrapper(this.optiAdvanced.g_lookup(index));
    }

    public String describeVariable(long i) {
        return this.optiAdvanced.x_describe(i);
    }

    public String describeGradient(long i) {
        return this.optiAdvanced.g_describe(i);
    }

    public String describeExpressionWithIndentation(MXWrapper x, long indent) {
        return this.optiAdvanced.describe(x.getCasADiObject(), indent);
    }

    public String describeExpression(MXWrapper x) {
        return this.optiAdvanced.describe(x.getCasADiObject());
    }

    public void displayInfeasibilitiesWithTolerance(double tol) {
        this.optiAdvanced.show_infeasibilities(tol);
    }

    public void displayInfeasibilities() {
        this.optiAdvanced.show_infeasibilities();
    }

    public void prepareForSolving() {
        this.optiAdvanced.solve_prepare();
    }

    public MapStringToDMWrapper executeSolveWithArguments(MapStringToDMWrapper args) {
        return new MapStringToDMWrapper(this.optiAdvanced.solve_actual(args.getCasADiObject()));
    }

    public MapStringToDMWrapper arg() {
        return new MapStringToDMWrapper(this.optiAdvanced.arg());
    }

    public void res(MapStringToDMWrapper res) {
        this.optiAdvanced.res(res.getCasADiObject());
    }

    public MapStringToDMWrapper res() {
        return new MapStringToDMWrapper(this.optiAdvanced.res());
    }

    public MXVector retrieveConstraints() {
        return new MXVector(this.optiAdvanced.constraints());
    }

    public MXWrapper objective() {
        return new MXWrapper(this.optiAdvanced.objective());
    }

    public NLPAdvancedModel createBakedCopy() {
        return new NLPAdvancedModel(this.optiAdvanced.baked_copy());
    }

    public void validateEmpty() {
        this.optiAdvanced.assert_empty();
    }

    public void finalizeStructure() {
        this.optiAdvanced.bake();
    }

    public boolean getProblemDirty() {
        return this.optiAdvanced.getProblem_dirty_();
    }

    public void markProblemDirty(boolean flag) {
        this.optiAdvanced.mark_problem_dirty(flag);
    }

    public void markProblemDirty() {
        this.optiAdvanced.mark_problem_dirty();
    }

    public boolean isProblemDirty() {
        return this.optiAdvanced.problem_dirty();
    }

    public boolean getSolverDirty() {
        return optiAdvanced.getSolver_dirty_();
    }

    public void markSolverDirty(boolean flag) {
        this.optiAdvanced.mark_solver_dirty(flag);
    }

    public void markSolverDirty() {
        this.optiAdvanced.mark_solver_dirty();
    }

    public boolean isSolverDirty() {
        return this.optiAdvanced.solver_dirty();
    }

    public boolean getSolved() {
        return this.optiAdvanced.getSolved_();
    }

    public void setSolvedStatus(boolean flag) {
        this.optiAdvanced.mark_solved(flag);
    }

    public void markAsSolved() {
        this.optiAdvanced.mark_solved();
    }

    public boolean isSolved() {
        return this.optiAdvanced.solved();
    }

    public void validateSolved() {
        this.optiAdvanced.assert_solved();
    }

    public void validateBaked() {
        this.optiAdvanced.assert_baked();
    }

    public long getInstanceNumber() {
        return this.optiAdvanced.instance_number();
    }

    public OptiAdvanced getCasADiObject() {
        return this.optiAdvanced;
    }

    private MetaCon canonicalizeExpression(MXWrapper expression) {
        return this.optiAdvanced.canon_expr(expression.getCasADiObject());
    }

    private MetaVar getMetaData(MXWrapper m) {
        return this.optiAdvanced.get_meta(m.getCasADiObject());
    }

    private MetaCon getSymbolMetaDataInternal(MXWrapper m) {
        return this.optiAdvanced.get_meta_con(m.getCasADiObject());
    }

}
