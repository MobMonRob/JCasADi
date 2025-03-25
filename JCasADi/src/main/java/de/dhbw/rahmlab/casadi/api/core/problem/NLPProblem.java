package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.api.core.constraints.Constraint;
import de.dhbw.rahmlab.casadi.api.core.solver.CasADiSolver;
import de.dhbw.rahmlab.casadi.api.core.utils.MXUtils;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.NumericValue;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.MapStringToMXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.StringVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;

import java.util.Arrays;

public class NLPProblem {

    private final Opti nlpProblem;

    public NLPProblem(String problem_type) {
        this.nlpProblem = new Opti(problem_type);
    }

    public NLPProblem() {
        this.nlpProblem = new Opti();
    }

    public NLPProblem(Opti other) {
        this.nlpProblem = other;
    }

    public NLPProblem(NLPProblem other) {
        this.nlpProblem = other.getCasADiObject();
    }

    public MXWrapper addVariable(MXWrapper variable) {
        long nrows = variable.rows();
        long mcols = variable.columns();
        return new MXWrapper(this.nlpProblem.variable(nrows, mcols));
    }

    public MXWrapper addVariable(long nrows, long mcols, String attribute) {
        return new MXWrapper(this.nlpProblem.variable(nrows, mcols, attribute));
    }

    public MXWrapper addVariable(long nrows, long mcols) {
        return new MXWrapper(this.nlpProblem.variable(nrows, mcols));
    }

    public MXWrapper addVariable(long nrows) {
        return new MXWrapper(this.nlpProblem.variable(nrows));
    }

    public MXWrapper addVariable() {
        return new MXWrapper(this.nlpProblem.variable());
    }

    public MXWrapper addParameter(MXWrapper parameter) {
        long nrows = parameter.rows();
        long mcols = parameter.columns();
        return new MXWrapper(this.nlpProblem.parameter(nrows, mcols));
    }

    public MXWrapper addParameter(long nrows, long mcols, String attribute) {
        return new MXWrapper(this.nlpProblem.parameter(nrows, mcols, attribute));
    }

    public MXWrapper addParameter(long nrows, long mcols) {
        return new MXWrapper(this.nlpProblem.parameter(nrows, mcols));
    }

    public MXWrapper addParameter(long nrows) {
        return new MXWrapper(this.nlpProblem.parameter(nrows));
    }

    public MXWrapper addParameter() {
        return new MXWrapper(this.nlpProblem.parameter());
    }

    public void minimize(MXWrapper f) {
        this.nlpProblem.minimize(f.getCasADiObject());
    }

    public void addConstraint(Constraint constraint) {
        if (constraint.getExpression() instanceof MXWrapper) {
            this.nlpProblem.subject_to(((MXWrapper) constraint.getExpression()).getCasADiObject());
        } else if (constraint.getExpression() instanceof SXWrapper) {
            MXWrapper mxWrapper = MXUtils.convertSXWrapperToMXWrapper((SXWrapper) constraint.getExpression());
            this.nlpProblem.subject_to(mxWrapper.getCasADiObject());
        } else {
            throw new IllegalArgumentException("The constraint must be either of type MXWrapper or SXWrapper.");
        }
    }

    public void addConstraints(MXWrapper... constraints) {
        MXVector vector = new MXVector(constraints);
        this.nlpProblem.subject_to(vector.getCasADiObject());
    }

    public void clearConstraints() {
        this.nlpProblem.subject_to();
    }

//    public void setSolver(String solver, Dict pluginOptions, Dict solverOptions) {
//        this.nlpProblem.solver(solver, pluginOptions, solverOptions);
//    }
//
//    public void setSolver(String solver, Dict pluginOptions) {
//        this.nlpProblem.solver(solver, pluginOptions);
//    }
//
//    public void setSolver(String solver) {
//        this.nlpProblem.solver(solver);
//    }

    public void setSolver(CasADiSolver solver, Dict pluginOptions, Dict solverOptions) {
        this.nlpProblem.solver(solver.getSolverName(), pluginOptions, solverOptions);
    }

    public void setSolver(CasADiSolver solver, Dict pluginOptions) {
        this.nlpProblem.solver(solver.getSolverName(), pluginOptions);
    }

    public void setSolver(CasADiSolver solver) {
        this.nlpProblem.solver(solver.getSolverName());
    }

    public void setInitialDecisionVariable(MXWrapper x, NumericValue v) {
        if (v instanceof DMWrapper) {
            this.nlpProblem.set_initial(x.getCasADiObject(), ((DMWrapper) v).getCasADiObject());
        } else if (v instanceof NumberWrapper) {
            this.nlpProblem.set_initial(x.getCasADiObject(), new DM(((NumberWrapper) v).getDoubleValue()));
        } else {
            throw new IllegalArgumentException("Unsupported numeric type " + v.getClass());
        }
    }

    public void setInitialDecisionVariable(MXWrapper x, Number... v) {
        StdVectorDM dmVector = new StdVectorDM();
        Arrays.stream(v).forEach(value -> {
            dmVector.add(new DM(value.doubleValue()));
        });
        this.nlpProblem.set_initial(x.getCasADiObject(), DM.vertcat(dmVector));
    }

    public void setInitialDecisionVariable(MXWrapper... assignments) {
        MXVector vector = new MXVector(assignments);
        this.nlpProblem.set_initial(vector.getCasADiObject());
    }

    public void setParameterValue(MXWrapper x, NumericValue v) {
        if (v instanceof DMWrapper) {
            this.nlpProblem.set_value(x.getCasADiObject(), ((DMWrapper) v).getCasADiObject());
        } else if (v instanceof NumberWrapper) {
            this.nlpProblem.set_value(x.getCasADiObject(), new DM(((NumberWrapper) v).getDoubleValue()));
        } else {
            throw new IllegalArgumentException("Unsupported numeric type " + v.getClass());
        }
    }

    public void setParameterValues(MXWrapper... assignments) {
        MXVector vector = new MXVector(assignments);
        this.nlpProblem.set_initial(vector.getCasADiObject());
    }

    public NLPResult solve() {
        return new NLPResult(this.nlpProblem.solve());
    }

    public NLPResult solveWithLimits() {
        return new NLPResult(this.nlpProblem.solve_limited());
    }

    public DMWrapper getValue(Wrapper x, MXWrapper... values) {
        if (values == null || values.length == 0) {
            return handleGetValueWithoutParameters(x);
        } else {
            MXVector vector = new MXVector(values);
            return handleGetValueWithParameters(x, vector);
        }
    }

    public DMWrapper getValue(Wrapper x, MXVector values) {
        if (values == null) {
            return handleGetValueWithoutParameters(x);
        } else {
            return handleGetValueWithParameters(x, values);
        }
    }

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

    public Dict getStatistics() {
        return this.nlpProblem.stats();
    }

    public String getSolverStatus() {
        return this.nlpProblem.return_status();
    }

    public MXVector getInitialAssignments() {
        return new MXVector(this.nlpProblem.initial());
    }

    public MXVector getLatestVariableValues() {
        return new MXVector(this.nlpProblem.value_variables());
    }

    public MXVector getLatestParameterValues() {
        return new MXVector(this.nlpProblem.value_parameters());
    }

    public MXWrapper getDualVariable(MXWrapper constraint) {
        return new MXWrapper(this.nlpProblem.dual(constraint.getCasADiObject()));
    }

    public long getNumberOfDecisionVariables() {
        return this.nlpProblem.nx();
    }

    public long getNumberOfParameters() {
        return this.nlpProblem.np();
    }

    public long getNumberOfConstraints() {
        return this.nlpProblem.ng();
    }

    public MXWrapper getDecisionVariables() {
        return new MXWrapper(this.nlpProblem.x());
    }

    public MXWrapper getParameters() {
        return new MXWrapper(this.nlpProblem.p());
    }

    public MXWrapper getConstraints() {
        return new MXWrapper(this.nlpProblem.g());
    }

    public MXWrapper getObjectiveFunction() {
        return new MXWrapper(this.nlpProblem.f());
    }

    public MXWrapper getLowerBounds() {
        return new MXWrapper(this.nlpProblem.lbg());
    }

    public MXWrapper getUpperBounds() {
        return new MXWrapper(this.nlpProblem.ubg());
    }

    public MXWrapper getDualVariables() {
        return new MXWrapper(this.nlpProblem.lam_g());
    }

    public FunctionWrapper createFunction(String name, MXVector args, MXVector res, Dict opts) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, args.getCasADiObject(), res.getCasADiObject(), opts));
    }

    public FunctionWrapper createFunction(String name, MXVector args, MXVector res) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, args.getCasADiObject(), res.getCasADiObject()));
    }

    public FunctionWrapper createFunction(String name, MXVector args, MXVector res, StringVector nameIn, StringVector nameOut, Dict opts) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, args.getCasADiObject(), res.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts));
    }

    public FunctionWrapper createFunction(String name, MXVector args, MXVector res, StringVector nameIn, StringVector nameOut) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, args.getCasADiObject(), res.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject()));
    }

    public FunctionWrapper createFunction(String name, MapStringToMXWrapper dict, StringVector nameIn, StringVector nameOut, Dict opts) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, dict.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject(), opts));
    }

    public FunctionWrapper createFunction(String name, MapStringToMXWrapper dict, StringVector nameIn, StringVector nameOut) {
        return new FunctionWrapper(this.nlpProblem.to_function(name, dict.getCasADiObject(), nameIn.getCasADiObject(), nameOut.getCasADiObject()));
    }

    public MXWrapper createBoundedConstraint(MXWrapper lowerBound, MXWrapper expression, MXWrapper upperBound) {
        return new MXWrapper(Opti.bounded(lowerBound.getCasADiObject(), expression.getCasADiObject(), upperBound.getCasADiObject()));
    }

    public OptiAdvanced getDebugCopy() {
        return this.nlpProblem.debug();
    }

    public OptiAdvanced getAdvancedCopy() {
        return this.nlpProblem.advanced();
    }

    public NLPProblem copy() {
        return new NLPProblem(this.nlpProblem.copy());
    }

    public void addUserData(MXWrapper symbol, Dict meta) {
        this.nlpProblem.update_user_dict(symbol.getCasADiObject(), meta);
    }

    public void addUserData(MXVector symbol, Dict metadata) {
        this.nlpProblem.update_user_dict(symbol.getCasADiObject(), metadata);
    }

    public Dict getUserData(MXWrapper symbol) {
        return this.nlpProblem.user_dict(symbol.getCasADiObject());
    }

    public String getTypeName() {
        return this.nlpProblem.type_name();
    }

    public String toString(boolean more) {
        return this.nlpProblem.toString(more);
    }

    public String toString() {
        return this.nlpProblem.toString();
    }

    private void registerCallback(NLPCallback callback) {
        this.nlpProblem.callback_class(callback.getCasADiObject());
    }

    private void registerCallback() {
        this.nlpProblem.callback_class();
    }

    public String getClassname() {
        return this.nlpProblem.class_name();
    }

    public boolean isNull() {
        return this.nlpProblem.is_null();
    }

    public long hash() {
        return this.nlpProblem.__hash__();
    }

    public Opti getCasADiObject() {
        return nlpProblem;
    }

}
