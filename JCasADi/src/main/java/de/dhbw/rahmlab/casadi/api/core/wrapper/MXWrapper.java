package de.dhbw.rahmlab.casadi.api.core.wrapper;

import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorMX;

import java.util.List;

public class MXWrapper {

    private MX mx;

    /**
     * default constructor
     */
    public MXWrapper() {
        mx = new MX();
    }

    /**
     * MX constructor
     *
     * @param mx
     */
    public MXWrapper(MX mx) {
        this.mx = mx;
    }

    /**
     * DM constructor
     *
     * @param dm
     */
    public MXWrapper(DMWrapper dm) {
        this.mx = new MX(dm.getCasadiObject());
    }

    /**
     * static values constructor
     *
     * @param value
     * @return MXWrapper
     */
    public static MXWrapper fromValue(double value) {
        return new MXWrapper(new MX(value));
    }

    /**
     * double array constructor
     *
     * @param values
     * @return MXWrapper
     */
    public static MXWrapper fromArray(double[] values) {
        return new MXWrapper(new MX(new StdVectorDouble(values)));
    }

    /**
     * iterable constructor
     *
     * @param values
     * @return MXWrapper
     */
    public static MXWrapper fromIterable(Iterable<Double> values) {
        return new MXWrapper(new MX(new StdVectorDouble(values)));
    }

    /**
     * sparsity constructor
     *
     * @param sparsity
     * @return MXWrapper
     */
    public static MXWrapper fromSparsity(Sparsity sparsity) {
        return new MXWrapper(new MX(sparsity));
    }

    /**
     * Construct matrix with a given sparsity and a file with nonzeros
     *
     * @param sparsity
     * @param filename
     * @return MXWrapper
     */
    public static MXWrapper fromSparsityAndFile(Sparsity sparsity, String filename) {
        return new MXWrapper(new MX(sparsity, filename));
    }

    /**
     * Construct matrix with a given sparsity and nonzeros
     *
     * @param sparsity
     * @param values
     * @return MXWrapper
     */
    public static MXWrapper fromSparsityAndValues(Sparsity sparsity, MXWrapper values) {
        return new MXWrapper(new MX(sparsity, values.mx));
    }

    /**
     * Create a sparse matrix with all structural zeros
     *
     * @param rows
     * @param cols
     * @return MXWrapper
     */
    public static MXWrapper fromSize(long rows, long cols) {
        return new MXWrapper(new MX(rows, cols));
    }

    /**
     * Returns the sparsity pattern of the MX matrix.
     * Returns null if no sparsity pattern exists.
     *
     * @return sparsity
     */
    public Sparsity sparsity() {
        return this.mx.sparsity();
    }

    /**
     * Returns the truth value of the MX expression.
     *
     * @return boolean
     */
    public boolean nonzero() {
        return this.mx.__nonzero__();
    }

    /**
     * Returns an owning reference to the sparsity pattern.
     *
     * @return Sparsity
     */
    public Sparsity getSparsity() {
        return this.mx.get_sparsity();
    }

    public MXWrapper add(MXWrapper other) {
        MX result = MX.plus(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public String getName() {
        return this.mx.name();
    }

    public MXWrapper subtract(MXWrapper other) {
        MX result = MX.minus(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper multiply(MXWrapper other) {
        MX result = MX.times(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper divide(MXWrapper other) {
        MX result = MX.rdivide(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper pow(MXWrapper other) {
        MX result = MX.pow(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper lt(MXWrapper other) {
        MX result = MX.lt(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper eq(MXWrapper other) {
        MX result = MX.eq(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper abs() {
        MX result = MX.abs(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sqrt() {
        MX result = MX.sqrt(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sin() {
        MX result = MX.sin(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper cos() {
        MX result = MX.cos(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper exp() {
        MX result = MX.exp(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper tan() {
        MX result = MX.tan(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper atan() {
        MX result = MX.atan(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper asin() {
        MX result = MX.asin(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper acos() {
        MX result = MX.acos(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sinh() {
        MX result = MX.sinh(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper cosh() {
        MX result = MX.cosh(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper tanh() {
        MX result = MX.tanh(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper expm1() {
        MX result = MX.expm1(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper log1p() {
        MX result = MX.log1p(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper inv() {
        MX result = MX.inv(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper pinv() {
        MX result = MX.pinv(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper det() {
        MX result = MX.det(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper trace() {
        MX result = MX.trace(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper kron(MXWrapper other) {
        MX result = MX.kron(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper reshape(long rows, long cols) {
        MX result = MX.reshape(this.mx, rows, cols);
        return new MXWrapper(result);
    }

    public MXWrapper densify() {
        MX result = MX.densify(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper diag() {
        MX result = MX.diag(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper eye(long size) {
        MX result = MX.eye(size);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper blockcat(MXWrapper other) {
        MX result = MX.blockcat(new StdVectorVectorMX());
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper horzcat(MXWrapper other) {
        MX result = MX.horzcat(new StdVectorMX());
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper vertcat(MXWrapper other) {
        MX result = MX.vertcat(new StdVectorMX());
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper blocksplit() {
        MX result = MX.blockcat(new StdVectorVectorMX());
        return new MXWrapper(result);
    }

    public MXWrapper norm_2() {
        MX result = MX.norm_2(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper norm_fro() {
        MX result = MX.norm_fro(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper norm_1() {
        MX result = MX.norm_1(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper norm_inf() {
        MX result = MX.norm_inf(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper gradient(MXWrapper other) {
        MX result = MX.gradient(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper jacobian(MXWrapper other) {
        MX result = MX.jacobian(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper hessian(MXWrapper other) {
        MX result = MX.hessian(this.mx, other.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper forward() {
        // MX result = MX.forward();
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change to StdVectorMX
    public MXWrapper reverse() {
        // MX result = MX.reverse(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Add MX Objectes
    public MXWrapper linearize() {
        // MX result = MX.linearize(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Revise logic
    public MXWrapper if_else(MXWrapper condition, MXWrapper trueValue, MXWrapper falseValue) {
        MX result = MX.if_else(condition.mx, trueValue.mx, falseValue.mx);
        return new MXWrapper(result);
    }

    public MXWrapper simplify() {
        MX result = MX.simplify(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper substitute(MXWrapper oldValue, MXWrapper newValue) {
        MX result = MX.substitute(this.mx, oldValue.mx, newValue.mx);
        return new MXWrapper(result);
    }

    public MXWrapper solve(MXWrapper b) {
        MX result = MX.solve(this.mx, b.mx);
        return new MXWrapper(result);
    }

    public MXWrapper expm() {
        MX result = MX.expm(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper zeros(long size) {
        MX result = MX.zeros(size);
        return new MXWrapper(result);
    }

    public MXWrapper ones(long size) {
        MX result = MX.ones(size);
        return new MXWrapper(result);
    }

    // Matrixoperationen
    public MXWrapper mldivide(MXWrapper other) {
        MX result = MX.mldivide(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper mrdivide(MXWrapper other) {
        MX result = MX.mrdivide(this.mx, other.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper vertsplit(StdVectorCasadiInt offset) {
        // MX result = MX.vertsplit(this.mx, offset);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change to StdVectorMX
    public MXWrapper horzsplit(int cols) {
        // MX result = MX.horzsplit(this.mx, cols);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change
    public MXWrapper repmat(int rows, int cols) {
        // MX result = MX.repmat(this.mx, rows, cols);
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper tril() {
        MX result = MX.tril(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper triu() {
        MX result = MX.triu(this.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to A, Aplha, x, y
    public MXWrapper rank1(MXWrapper v) {
        // MX result = MX.rank1(this.mx, v.mx);
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper bilin(MXWrapper x, MXWrapper y) {
        MX result = MX.bilin(this.mx, x.mx, y.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sumsqr() {
        MX result = MX.sumsqr(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper nullspace() {
        MX result = MX.nullspace(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper inv_minor() {
        MX result = MX.inv_minor(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper inv_node() {
        MX result = MX.inv_node(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper cross(MXWrapper other) {
        MX result = MX.cross(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper dot(MXWrapper other) {
        MX result = MX.dot(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper linspace(MXWrapper other, int steps) {
        MX result = MX.linspace(this.mx, other.mx, steps);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper ad_forward() {
        // MX result = MX.ad_forward(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Change to StdVectorMX
    public MXWrapper ad_reverse() {
        // MX result = MX.ad_reverse(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Add MX
    public MXWrapper linearize(MXWrapper point) {
        // MX result = MX.linearize(this.mx, point.mx);
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper if_else_zero(MXWrapper condition) {
        MX result = MX.if_else_zero(this.mx, condition.mx);
        return new MXWrapper(result);
    }

    public MXWrapper convexify() {
        MX result = MX.convexify(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper find() {
        MX result = MX.find(this.mx);
        return new MXWrapper(result);
    }

    // TODO: Change to StdVectorMX
    public MXWrapper symvar() {
        // StdVectorMX result = de.dhbw.rahmlab.casadi.impl.casadi.MX.symvar(this.mx);
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper logic_not() {
        MX result = MX.logic_not(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper logic_and(MXWrapper other) {
        MX result = MX.logic_and(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper logic_or(MXWrapper other) {
        MX result = MX.logic_or(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper fmin(MXWrapper other) {
        MX result = MX.fmin(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper fmax(MXWrapper other) {
        MX result = MX.fmax(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper hypot(MXWrapper other) {
        MX result = MX.hypot(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper sign() {
        MX result = MX.sign(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper copysign(MXWrapper other) {
        MX result = MX.copysign(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper constpow(MXWrapper other) {
        MX result = MX.constpow(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper atan2(MXWrapper other) {
        MX result = MX.atan2(this.mx, other.mx);
        return new MXWrapper(result);
    }

    public MXWrapper erf() {
        MX result = MX.erf(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper erfinv() {
        MX result = MX.erfinv(this.mx);
        return new MXWrapper(result);
    }

    public MXWrapper tangent(MXWrapper other) {
        MX result = MX.tangent(this.mx, other.mx);
        return new MXWrapper(result);
    }

    // TODO: Check Parameters
    public MXWrapper bspline(MXWrapper other) {
        // MX result = MX.bspline();
        // return new MXWrapper(result);
        return null;
    }

    // TODO: Check Parameters
    public MXWrapper bspline_dual() {
        // MX result = MX.bspline_dual();
        // return new MXWrapper(result);
        return null;
    }

    public MXWrapper logsumexp() {
        MX result = MX.logsumexp(this.mx);
        return new MXWrapper(result);
    }

    public MX getCasADiObject() {
        return this.mx;
    }
}
