package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.*;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.*;
import de.dhbw.rahmlab.casadi.impl.casadi.IM;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Slice;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

import javax.constraints.ConstrainedVariable;
import javax.constraints.Problem;
import java.util.Arrays;

public class SXWrapper implements Wrapper<SXWrapper>, SymbolicExpression {

    private final SX sx;
    private String id;

    public SXWrapper() {
        this.sx = new SX();
    }

    public SXWrapper(SX sx) {
        this.sx = sx;
    }

    public SXWrapper(long nrow, long ncol) {
        this.sx = new SX(nrow, ncol);
    }

    public SXWrapper(Sparsity sp) {
        this.sx = new SX(sp);
    }

    public SXWrapper(Sparsity sp, SXWrapper d) {
        this.sx = new SX(sp, d.getCasADiObject());
    }

    public SXWrapper(Number value) {
        this.sx = new SX(value.doubleValue());
    }

    public SXWrapper(DoubleVectorCollection m) {
        this.sx = new SX(m.getCasADiObject());
    }

    public SXWrapper(DoubleVector... m) {
        DoubleVectorCollection collection = new DoubleVectorCollection();
        Arrays.stream(m).forEach(collection::add);
        this.sx = new SX(collection.getCasADiObject());
    }

    public SXWrapper(SXComponentVector x) {
        this.sx = new SX(x.getCasADiObject());
    }

    public SXWrapper(Sparsity sp, SXComponent val, boolean dummy) {
        this.sx = new SX(sp, val.getCasADiObject(), dummy);
    }

    public SXWrapper(Sparsity sp, SXComponentVector d, boolean dummy) {
        this.sx = new SX(sp, d.getCasADiObject(), dummy);
    }

    public SXComponent scalar() {
        return new SXComponent(this.sx.scalar());
    }

    public boolean hasNZ(long rr, long cc) {
        return this.sx.has_nz(rr, cc);
    }

    @Override
    public boolean nonzero() {
        return this.sx.__nonzero__();
    }

    // ----- Get a submatrix, single argument -----
    @Override
    public SXWrapper get(boolean ind1, Slice rr) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr);
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IM rr) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr);
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, Sparsity sp) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, sp);
        return output;
    }

    // ----- Get a submatrix, two arguments -----
    @Override
    public SXWrapper get(boolean ind1, Slice rr, Slice cc) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, Slice rr, IM cc) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IM rr, Slice cc) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IM rr, IM cc) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    // ----- Set a submatrix, single argument -----
    @Override
    public void set(SXWrapper m, boolean ind1, Slice rr) {
        this.sx.set(m.getCasADiObject(), ind1, rr);
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IM rr) {
        this.sx.set(m.getCasADiObject(), ind1, rr);
    }

    @Override
    public void set(SXWrapper m, boolean ind1, Sparsity sp) {
        this.sx.set(m.getCasADiObject(), ind1, sp);
    }

    // ----- Set a submatrix, two arguments -----
    @Override
    public void set(SXWrapper m, boolean ind1, Slice rr, Slice cc) {
        this.sx.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public void set(SXWrapper m, boolean ind1, Slice rr, IM cc) {
        this.sx.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IM rr, Slice cc) {
        this.sx.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IM rr, IM cc) {
        this.sx.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public SXWrapper getNZ(boolean ind1, Slice k) {
        SXWrapper output = new SXWrapper();
        this.sx.get_nz(output.getCasADiObject(), ind1, k);
        return output;
    }

    @Override
    public SXWrapper getNZ(boolean ind1, IM k) {
        SXWrapper output = new SXWrapper();
        this.sx.get_nz(output.getCasADiObject(), ind1, k);
        return output;
    }

    @Override
    public void setNZ(SXWrapper m, boolean ind1, Slice k) {
        this.sx.set_nz(m.getCasADiObject(), ind1, k);
    }

    @Override
    public void setNZ(SXWrapper m, boolean ind1, IM k) {
        this.sx.set_nz(m.getCasADiObject(), ind1, k);
    }

    @Override
    public SXWrapper binary(long op, SXWrapper y) {
        return new SXWrapper(SX.binary(op, this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper unary(long op) {
        return new SXWrapper(SX.unary(op, this.sx));
    }

    @Override
    public boolean isEqual(SXWrapper other, long depth) {
        return SX.is_equal(this.sx, other.getCasADiObject(), depth);
    }

    @Override
    public boolean isEqual(SXWrapper other) {
        return SX.is_equal(this.sx, other.getCasADiObject());
    }

    @Override
    public SXWrapper mmin() {
        return new SXWrapper(SX.mmin(this.sx));
    }

    @Override
    public SXWrapper mmax() {
        return new SXWrapper(SX.mmax(this.sx));
    }

    @Override
    public SXWrapper simplify() {
        return new SXWrapper(SX.simplify(this.sx));
    }

    @Override
    public SXWrapper jacobian(SXWrapper x, Dict opts) {
        return new SXWrapper(SX.jacobian(this.sx, x.getCasADiObject(), opts));
    }

    @Override
    public SXWrapper jacobian(SXWrapper x) {
        return new SXWrapper(SX.jacobian(this.sx, x.getCasADiObject()));
    }

    @Override
    public Sparsity jacobianSparsity(SXWrapper x) {
        return SX.jacobian_sparsity(this.sx, x.getCasADiObject());
    }

    @Override
    public SXWrapper hessian(SXWrapper x, Dict opts) {
        return new SXWrapper(SX.hessian(this.sx, x.getCasADiObject(), opts));
    }

    @Override
    public SXWrapper hessian(SXWrapper x) {
        return new SXWrapper(SX.hessian(this.sx, x.getCasADiObject()));
    }

    @Override
    public SXWrapper hessian(SXWrapper x, SXWrapper g, Dict opts) {
        return new SXWrapper(SX.hessian(this.sx, x.getCasADiObject(), g.getCasADiObject(), opts));
    }

    @Override
    public SXWrapper hessian(SXWrapper x, SXWrapper g) {
        return new SXWrapper(SX.hessian(this.sx, x.getCasADiObject(), g.getCasADiObject()));
    }

    @Override
    public SXWrapper substitute(SXWrapper v, SXWrapper vdef) {
        return new SXWrapper(SX.substitute(this.sx, v.getCasADiObject(), vdef.getCasADiObject()));
    }

    @Override
    public SXWrapper pinv() {
        return new SXWrapper(SX.pinv(this.sx));
    }

    @Override
    public SXWrapper pinv(String lsolver, Dict dict) {
        return new SXWrapper(SX.pinv(this.sx, lsolver, dict));
    }

    @Override
    public SXWrapper expmConst(SXWrapper sxWrapper) {
        return new SXWrapper(SX.expm_const(this.sx, sxWrapper.getCasADiObject()));
    }

    @Override
    public SXWrapper expm() {
        return new SXWrapper(SX.expm(this.sx));
    }

    @Override
    public SXWrapper solve(SXWrapper b) {
        return new SXWrapper(SX.solve(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXWrapper solve(SXWrapper b, String lsolver, Dict dict) {
        return new SXWrapper(SX.solve(this.sx, b.getCasADiObject(), lsolver, dict));
    }

    @Override
    public SXWrapper inv() {
        return new SXWrapper(SX.inv(this.sx));
    }

    @Override
    public SXWrapper inv(String lsolver, Dict dict) {
        return new SXWrapper(SX.inv(this.sx, lsolver, dict));
    }

    @Override
    public long nNodes() {
        return SX.n_nodes(this.sx);
    }

    @Override
    public String printOperator(StringVector args) {
        return SX.print_operator(this.sx, args.getCasADiObject());
    }

    @Override
    public SXWrapper _bilin(SXWrapper x, SXWrapper y) {
        return new SXWrapper(SX._bilin(this.sx, x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public SXWrapper _rank1(SXWrapper alpha, SXWrapper x, SXWrapper y) {
        return new SXWrapper(SX._rank1(this.sx, alpha.getCasADiObject(), x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public boolean dependsOn(SXWrapper arg) {
        return SX.depends_on(this.sx, arg.getCasADiObject());
    }

    @Override
    public SXWrapper mrdivide(SXWrapper b) {
        return new SXWrapper(SX.mrdivide(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXWrapper mldivide(SXWrapper b) {
        return new SXWrapper(SX.mldivide(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXVector symvar() {
        return new SXVector(SX.symvar(this.sx));
    }

    @Override
    public SXWrapper det() {
        return new SXWrapper(SX.det(this.sx));
    }

    @Override
    public SXWrapper invMinor() {
        return new SXWrapper(SX.inv_minor(this.sx));
    }

    @Override
    public SXWrapper trace() {
        return new SXWrapper(SX.trace(this.sx));
    }

    @Override
    public SXWrapper norm1() {
        return new SXWrapper(SX.norm_1(this.sx));
    }

    @Override
    public SXWrapper norm2() {
        return new SXWrapper(SX.norm_2(this.sx));
    }

    @Override
    public SXWrapper normFro() {
        return new SXWrapper(SX.norm_fro(this.sx));
    }

    @Override
    public SXWrapper normInf() {
        return new SXWrapper(SX.norm_inf(this.sx));
    }

    @Override
    public SXWrapper sum2() {
        return new SXWrapper(SX.sum2(this.sx));
    }

    @Override
    public SXWrapper sum1() {
        return new SXWrapper(SX.sum1(this.sx));
    }

    @Override
    public SXWrapper dot(SXWrapper y) {
        return new SXWrapper(SX.dot(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper nullspace() {
        return new SXWrapper(SX.nullspace(this.sx));
    }

    @Override
    public SXWrapper diag() {
        return new SXWrapper(SX.diag(this.sx));
    }

    @Override
    public SXWrapper unite(SXWrapper B) {
        return new SXWrapper(SX.unite(this.sx, B.getCasADiObject()));
    }

    @Override
    public SXWrapper project(Sparsity sp, boolean intersect) {
        return new SXWrapper(SX.project(this.sx, sp, intersect));
    }

    @Override
    public SXWrapper project(Sparsity sp) {
        return new SXWrapper(SX.project(this.sx, sp));
    }

    @Override
    public SXWrapper polyval(SXWrapper x) {
        return new SXWrapper(SX.polyval(this.sx, x.getCasADiObject()));
    }

    @Override
    public SXWrapper densify(SXWrapper val) {
        return new SXWrapper(SX.densify(this.sx, val.getCasADiObject()));
    }

    @Override
    public SXWrapper densify() {
        return new SXWrapper(SX.densify(this.sx));
    }

    @Override
    public SXWrapper einstein(SXWrapper other, SXWrapper C,
                              IntegerVector dim_a, IntegerVector dim_b, IntegerVector dim_c,
                              IntegerVector a, IntegerVector b, IntegerVector c) {
        return new SXWrapper(SX.einstein(this.sx, other.getCasADiObject(), C.getCasADiObject(),
                dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(),
                a.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public SXWrapper einstein(SXWrapper other, IntegerVector dim_a, IntegerVector dim_b, IntegerVector dim_c,
                              IntegerVector a, IntegerVector b, IntegerVector c) {
        return new SXWrapper(SX.einstein(this.sx, other.getCasADiObject(),
                dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(),
                a.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public SXWrapper cumsum(long axis) {
        return new SXWrapper(SX.cumsum(this.sx, axis));
    }

    @Override
    public SXWrapper cumsum() {
        return new SXWrapper(SX.cumsum(this.sx));
    }

    @Override
    public SXWrapper _logsumexp() {
        return new SXWrapper(SX._logsumexp(this.sx));
    }

    @Override
    public SXVector horzsplit(IntegerVector offset) {
        return new SXVector(SX.horzsplit(this.sx, offset.getCasADiObject()));
    }

    @Override
    public SXVector vertsplit(IntegerVector offset) {
        return new SXVector(SX.vertsplit(this.sx, offset.getCasADiObject()));
    }

    @Override
    public SXVector diagsplit(IntegerVector offset1, IntegerVector offset2) {
        return new SXVector(SX.diagsplit(this.sx, offset1.getCasADiObject(), offset2.getCasADiObject()));
    }

    @Override
    public SXWrapper reshape(long nrow, long ncol) {
        return new SXWrapper(SX.reshape(this.sx, nrow, ncol));
    }

    @Override
    public SXWrapper reshape(Sparsity sp) {
        return new SXWrapper(SX.reshape(this.sx, sp));
    }

    @Override
    public SXWrapper sparsityCast(Sparsity sp) {
        return new SXWrapper(SX.sparsity_cast(this.sx, sp));
    }

    @Override
    public SXWrapper kron(SXWrapper b) {
        return new SXWrapper(SX.kron(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXWrapper mtimes(SXWrapper other) {
        return new SXWrapper(SX.mtimes(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper mac(SXWrapper y, SXWrapper z) {
        return new SXWrapper(SX.mac(this.sx, y.getCasADiObject(), z.getCasADiObject()));
    }

    public SXWrapper sparsify(double tol) {
        return new SXWrapper(SX.sparsify(this.sx, tol));
    }

    public SXWrapper sparsify() {
        return new SXWrapper(SX.sparsify(this.sx));
    }

    public void expand(SXWrapper weights, SXWrapper terms) {
        SX.expand(this.sx, weights.getCasADiObject(), terms.getCasADiObject());
    }

    public SXWrapper pwConst(SXWrapper tval, SXWrapper val) {
        return new SXWrapper(SX.pw_const(this.sx, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public SXWrapper pwLin(SXWrapper tval, SXWrapper val) {
        return new SXWrapper(SX.pw_lin(this.sx, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public SXWrapper heaviside() {
        return new SXWrapper(SX.heaviside(this.sx));
    }

    public SXWrapper rectangle() {
        return new SXWrapper(SX.rectangle(this.sx));
    }

    public SXWrapper triangle() {
        return new SXWrapper(SX.triangle(this.sx));
    }

    public SXWrapper ramp() {
        return new SXWrapper(SX.ramp(this.sx));
    }

    public SXWrapper gaussQuadrate(SXWrapper x, SXWrapper a, SXWrapper b, long order) {
        return new SXWrapper(SX.gauss_quadrature(this.sx, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order));
    }

    public SXWrapper gaussQuadrate(SXWrapper x, SXWrapper a, SXWrapper b) {
        return new SXWrapper(SX.gauss_quadrature(this.sx, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject()));
    }

    public SXWrapper gaussQuadrate(SXWrapper x, SXWrapper a, SXWrapper b, long order, SXWrapper w) {
        return new SXWrapper(SX.gauss_quadrature(this.sx, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order, w.getCasADiObject()));
    }

    @Override
    public BooleanVector whichDepends(SXWrapper var, long order, boolean tr) {
        return new BooleanVector(SX.which_depends(this.sx, var.getCasADiObject(), order, tr));
    }

    @Override
    public BooleanVector whichDepends(SXWrapper var, long order) {
        return new BooleanVector(SX.which_depends(this.sx, var.getCasADiObject(), order));
    }

    @Override
    public BooleanVector whichDepends(SXWrapper var) {
        return new BooleanVector(SX.which_depends(this.sx, var.getCasADiObject()));
    }

    public SXWrapper taylor(SXWrapper x, SXWrapper a, long order) {
        return new SXWrapper(SX.taylor(this.sx, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public SXWrapper mtaylor(SXWrapper x, SXWrapper a, long order) {
        return new SXWrapper(SX.mtaylor(this.sx, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public SXWrapper mtaylor(SXWrapper x, SXWrapper a, long order, IntegerVector orderContributions) {
        return new SXWrapper(SX.mtaylor(this.sx, x.getCasADiObject(), a.getCasADiObject(), order, orderContributions.getCasADiObject()));
    }

    public SXWrapper polyCoeff(SXWrapper x) {
        return new SXWrapper(SX.poly_coeff(this.sx, x.getCasADiObject()));
    }

    public SXWrapper polyRoots() {
        return new SXWrapper(SX.poly_roots(this.sx));
    }

    public SXWrapper eigSymbolic() {
        return new SXWrapper(SX.eig_symbolic(this.sx));
    }

    @Override
    public DMWrapper evalf() {
        return new DMWrapper(SX.evalf(this.sx));
    }

    public void qrSparse(SXWrapper V, SXWrapper R, SXWrapper beta, IntegerVector prinv, IntegerVector pc, boolean amd) {
        SX.qr_sparse(this.sx, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject(), amd);
    }

    public void qrSparse(SXWrapper V, SXWrapper R, SXWrapper beta, IntegerVector prinv, IntegerVector pc) {
        SX.qr_sparse(this.sx, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject());
    }

    public SXWrapper qrSolve(SXWrapper v, SXWrapper r, SXWrapper beta, IntegerVector prinv, IntegerVector pc, boolean tr) {
        return new SXWrapper(SX.qr_solve(this.sx, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject(), tr));
    }

    public SXWrapper qrSolve(SXWrapper v, SXWrapper r, SXWrapper beta, IntegerVector prinv, IntegerVector pc) {
        return new SXWrapper(SX.qr_solve(this.sx, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject()));
    }

    public void qr(SXWrapper Q, SXWrapper R) {
        SX.qr(this.sx, Q.getCasADiObject(), R.getCasADiObject());
    }

    public void ldl(SXWrapper D, SXWrapper LT, IntegerVector p, boolean amd) {
        SX.ldl(this.sx, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject(), amd);
    }

    public void ldl(SXWrapper D, SXWrapper LT, IntegerVector p) {
        SX.ldl(this.sx, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject());
    }

    public SXWrapper ldlSolve(SXWrapper D, SXWrapper LT, IntegerVector p) {
        return new SXWrapper(SX.ldl_solve(this.sx, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject()));
    }

    public SXWrapper all() {
        return new SXWrapper(SX.all(this.sx));
    }

    public SXWrapper any() {
        return new SXWrapper(SX.any(this.sx));
    }

    public SXWrapper adj() {
        return new SXWrapper(SX.adj(this.sx));
    }

    public SXWrapper minor(long i, long j) {
        return new SXWrapper(SX.minor(this.sx, i, j));
    }

    public SXWrapper cofactor(long i, long j) {
        return new SXWrapper(SX.cofactor(this.sx, i, j));
    }

    public SXWrapper chol() {
        return new SXWrapper(SX.chol(this.sx));
    }

    public SXWrapper normInfMul(SXWrapper y) {
        return new SXWrapper(SX.norm_inf_mul(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper printMe(SXWrapper y) {
        return new SXWrapper(this.sx.printme(y.getCasADiObject()));
    }

    @Override
    public SXWrapper T() {
        return new SXWrapper(this.sx.T());
    }

    @Override
    public void setMaxDepth(long eq_depth) {
        SX.set_max_depth(eq_depth);
    }

    @Override
    public void setMaxDepth() {
        SX.set_max_depth();
    }

    @Override
    public long getMaxDepth() {
        return SX.get_max_depth();
    }

    @Override
    public String typeName() {
        return SX.type_name();
    }

    public void printSplit(StringVector arg0, StringVector arg1) {
        this.sx.print_split(arg0.getCasADiObject(), arg1.getCasADiObject());
    }

    @Override
    public String toString(boolean more) {
        return this.sx.toString(more);
    }

    @Override
    public String toString() {
        return this.sx.toString();
    }

    public void clear() {
        this.sx.clear();
    }

    public void resize(long nrow, long ncol) {
        this.sx.resize(nrow, ncol);
    }

    public void reserve(long nnz) {
        this.sx.reserve(nnz);
    }

    public void reserve(long nnz, long ncol) {
        this.sx.reserve(nnz, ncol);
    }

    @Override
    public void erase(IntegerVector rr, IntegerVector cc, boolean ind1) {
        this.sx.erase(rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void erase(IntegerVector rr, IntegerVector cc) {
        this.sx.erase(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void erase(IntegerVector rr, boolean ind1) {
        this.sx.erase(rr.getCasADiObject(), ind1);
    }

    @Override
    public void erase(IntegerVector rr) {
        this.sx.erase(rr.getCasADiObject());
    }

    public void remove(IntegerVector rr, IntegerVector cc) {
        this.sx.remove(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void enlarge(long nrow, long ncol, IntegerVector rr, IntegerVector cc, boolean ind1) {
        this.sx.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void enlarge(long nrow, long ncol, IntegerVector rr, IntegerVector cc) {
        this.sx.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject());
    }

    public SXComponentVector nonzeros() {
        return new SXComponentVector(this.sx.nonzeros());
    }

    @Override
    public Sparsity sparsity() {
        return this.sx.sparsity();
    }

    public static SXWrapper inf(Sparsity sp) {
        return new SXWrapper(SX.inf(sp));
    }

    public static SXWrapper inf(long nrow, long ncol) {
        return new SXWrapper(SX.inf(nrow, ncol));
    }

    public static SXWrapper inf(long nrow) {
        return new SXWrapper(SX.inf(nrow));
    }

    public static SXWrapper inf() {
        return new SXWrapper(SX.inf());
    }

    public static SXWrapper nan(Sparsity sp) {
        return new SXWrapper(SX.nan(sp));
    }

    public static SXWrapper nan(long nrow, long ncol) {
        return new SXWrapper(SX.nan(nrow, ncol));
    }

    public static SXWrapper nan(long nrow) {
        return new SXWrapper(SX.nan(nrow));
    }

    public static SXWrapper nan() {
        return new SXWrapper(SX.nan());
    }

    public static SXWrapper eye(long size) {
        return new SXWrapper(SX.eye(size));
    }

    public long elementHash() {
        return this.sx.element_hash();
    }

    @Override
    public boolean isRegular() {
        return this.sx.is_regular();
    }

    public boolean isSmooth() {
        return this.sx.is_smooth();
    }

    public boolean isLeaf() {
        return this.sx.is_leaf();
    }

    @Override
    public boolean isCommutative() {
        return this.sx.is_commutative();
    }

    @Override
    public boolean isSymbolic() {
        return this.sx.is_symbolic();
    }

    @Override
    public boolean isValidInput() {
        return this.sx.is_valid_input();
    }

    @Override
    public boolean hasDuplicates() {
        return this.sx.has_duplicates();
    }

    @Override
    public void resetInput() {
        this.sx.reset_input();
    }

    @Override
    public boolean isConstant() {
        return this.sx.is_constant();
    }

    public boolean isInteger() {
        return this.sx.is_integer();
    }

    @Override
    public boolean isZero() {
        return this.sx.is_zero();
    }

    @Override
    public boolean isOne() {
        return this.sx.is_one();
    }

    @Override
    public boolean isMinusOne() {
        return this.sx.is_minus_one();
    }

    @Override
    public boolean isEye() {
        return this.sx.is_eye();
    }

    @Override
    public long op() {
        return this.sx.op();
    }

    @Override
    public boolean isOp(long op) {
        return this.sx.is_op(op);
    }

    public boolean hasZeros() {
        return this.sx.has_zeros();
    }

    public SXComponentVector getNonzeros() {
        return new SXComponentVector(this.sx.get_nonzeros());
    }

    public SXComponentVector getElements() {
        return new SXComponentVector(this.sx.get_elements());
    }

    @Override
    public String getName() {
        return this.sx.name();
    }

    @Override
    public SXWrapper dep(long ch) {
        return new SXWrapper(this.sx.dep(ch));
    }

    @Override
    public SXWrapper dep() {
        return new SXWrapper(this.sx.dep());
    }

    @Override
    public long nDep() {
        return this.sx.n_dep();
    }

    public static SXWrapper rand(long nrow, long ncol) {
        return new SXWrapper(SX.rand(nrow, ncol));
    }

    public static SXWrapper rand(long nrow) {
        return new SXWrapper(SX.rand(nrow));
    }

    public static SXWrapper rand() {
        return new SXWrapper(SX.rand());
    }

    public static SXWrapper rand(Sparsity sp) {
        return new SXWrapper(SX.rand(sp));
    }

    public void exportCode(String lang) {
        this.sx.export_code(lang);
    }

    @Override
    public Dict info() {
        return this.sx.info();
    }

    public String serialize() {
        return this.sx.serialize();
    }

    public static SXWrapper deserialize(String s) {
        return new SXWrapper(SX.deserialize(s));
    }

    public void toFile(String filename, String format) {
        this.sx.to_file(filename, format);
    }

    public void toFile(String filename) {
        this.sx.to_file(filename);
    }

    @Override
    public SXWrapper _sym(String name, Sparsity sp) {
        return new SXWrapper(SX._sym(name, sp));
    }

    @Override
    public SXSubIndexWrapper at(int rr) {
        return new SXSubIndexWrapper(this.sx.at(rr));
    }

    @Override
    public SXSubMatrixWrapper at(int rr, int cc) {
        return new SXSubMatrixWrapper(this.sx.at(rr, cc));
    }

    @Override
    public void assign(SXWrapper other) {
        this.sx.assign(other.getCasADiObject());
    }

    @Override
    public SXVectorCollection blocksplit(IntegerVector vert_offset, IntegerVector horz_offset) {
        return new SXVectorCollection(SX.blocksplit(this.sx, vert_offset.getCasADiObject(), horz_offset.getCasADiObject()));
    }

    @Override
    public SXVectorCollection blocksplit(long vert_incr, long horz_incr) {
        return new SXVectorCollection(SX.blocksplit(this.sx, vert_incr, horz_incr));
    }

    @Override
    public SXWrapper vec() {
        return new SXWrapper(SX.vec(this.sx));
    }

    public SXWrapper repmat(long n, long m) {
        return new SXWrapper(SX.repmat(this.sx, n, m));
    }

    public SXWrapper repmat(long n) {
        return new SXWrapper(SX.repmat(this.sx, n));
    }

    @Override
    public SXVector vertsplit_n(long n) {
        return new SXVector(SX.vertsplit_n(this.sx, n));
    }

    @Override
    public long nnz() {
        return this.sx.nnz_();
    }

    @Override
    public long nnzLower() {
        return this.sx.nnz_lower_();
    }

    @Override
    public long nnzUpper() {
        return this.sx.nnz_upper_();
    }

    @Override
    public long nnzDiag() {
        return this.sx.nnz_diag();
    }

    @Override
    public long numel() {
        return this.sx.numel_();
    }

    @Override
    public long size1() {
        return this.sx.size1_();
    }

    @Override
    public long rows() {
        return this.sx.rows();
    }

    @Override
    public long size2() {
        return this.sx.size2_();
    }

    @Override
    public long columns() {
        return this.sx.columns();
    }

    @Override
    public String dim(boolean withNz) {
        return this.sx.dim_(withNz);
    }

    @Override
    public String dim() {
        return this.sx.dim_();
    }

    @Override
    public long size(long axis) {
        return this.sx.size_(axis);
    }

    @Override
    public boolean isEmpty(boolean both) {
        return this.sx.is_empty_(both);
    }

    @Override
    public boolean isEmpty() {
        return this.sx.is_empty_();
    }

    @Override
    public boolean isDense() {
        return this.sx.is_dense_();
    }

    @Override
    public boolean isScalar(boolean scalarAndDense) {
        return this.sx.is_scalar_(scalarAndDense);
    }

    @Override
    public boolean isScalar() {
        return this.sx.is_scalar_();
    }

    @Override
    public boolean isSquare() {
        return this.sx.is_square();
    }

    @Override
    public boolean isVector() {
        return this.sx.is_vector_();
    }

    @Override
    public boolean isRow() {
        return this.sx.is_row_();
    }

    @Override
    public boolean isColumn() {
        return this.sx.is_column_();
    }

    @Override
    public boolean isTriu() {
        return this.sx.is_triu_();
    }

    @Override
    public boolean isTril() {
        return this.sx.is_tril_();
    }

    @Override
    public IntegerVector getRow() {
        return new IntegerVector(this.sx.get_row());
    }

    @Override
    public IntegerVector getColInd() {
        return new IntegerVector(this.sx.get_colind());
    }

    @Override
    public long row(long el) {
        return this.sx.row_(el);
    }

    @Override
    public long colind(long col) {
        return this.sx.colind_(col);
    }

    @Override
    public SXWrapper interp1d(DoubleVector x, DoubleVector xq, String mode, boolean equidistant) {
        return new SXWrapper(SX.interp1d(x.getCasADiObject(), this.sx, xq.getCasADiObject(), mode, equidistant));
    }

    @Override
    public long sprank() {
        return SX.sprank(this.sx);
    }

    @Override
    public long norm0Mul(SXWrapper y) {
        return SX.norm_0_mul(this.sx, y.getCasADiObject());
    }

    @Override
    public SXWrapper tril(boolean includeDiagonal) {
        return new SXWrapper(SX.tril(this.sx, includeDiagonal));
    }

    @Override
    public SXWrapper tril() {
        return new SXWrapper(SX.tril(this.sx));
    }

    @Override
    public SXWrapper triu(boolean includeDiagonal) {
        return new SXWrapper(SX.triu(this.sx, includeDiagonal));
    }

    @Override
    public SXWrapper triu() {
        return new SXWrapper(SX.triu(this.sx));
    }

    @Override
    public SXWrapper sumsqr() {
        return new SXWrapper(SX.sumsqr(this.sx));
    }

    public SXWrapper linspace(SXWrapper b, long nsteps) {
        return new SXWrapper(SX.linspace(this.sx, b.getCasADiObject(), nsteps));
    }

    @Override
    public SXWrapper cross(SXWrapper b, long dim) {
        return new SXWrapper(SX.cross(this.sx, b.getCasADiObject(), dim));
    }

    @Override
    public SXWrapper cross(SXWrapper b) {
        return new SXWrapper(SX.cross(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXWrapper skew() {
        return new SXWrapper(SX.skew(this.sx));
    }

    @Override
    public SXWrapper invSkew() {
        return new SXWrapper(SX.inv_skew(this.sx));
    }

    @Override
    public SXWrapper tril2symm() {
        return new SXWrapper(SX.tril2symm(this.sx));
    }

    @Override
    public SXWrapper triu2symm() {
        return new SXWrapper(SX.triu2symm(this.sx));
    }

    public SXWrapper repsum(long n, long m) {
        return new SXWrapper(SX.repsum(this.sx, n, m));
    }

    public SXWrapper repsum(long n) {
        return new SXWrapper(SX.repsum(this.sx, n));
    }

    @Override
    public SXWrapper diff(long n, long axis) {
        return new SXWrapper(SX.diff(this.sx, n, axis));
    }

    @Override
    public SXWrapper diff(long n) {
        return new SXWrapper(SX.diff(this.sx, n));
    }

    @Override
    public SXWrapper diff() {
        return new SXWrapper(SX.diff(this.sx));
    }

    @Override
    public boolean isLinear(SXWrapper var) {
        return SX.is_linear(this.sx, var.getCasADiObject());
    }

    @Override
    public boolean isQuadratic(SXWrapper var) {
        return SX.is_quadratic(this.sx, var.getCasADiObject());
    }

    @Override
    public void quadraticCoeff(SXWrapper var, SXWrapper A, SXWrapper b, SXWrapper c, boolean check) {
        SX.quadratic_coeff(this.sx, var.getCasADiObject(), A.getCasADiObject(),
                b.getCasADiObject(), c.getCasADiObject(), check);
    }

    @Override
    public void linearCoeff(SXWrapper var, SXWrapper A, SXWrapper b, boolean check) {
        SX.linear_coeff(this.sx, var.getCasADiObject(), A.getCasADiObject(), b.getCasADiObject(), check);
    }

    @Override
    public SXWrapper bilin(SXWrapper x, SXWrapper y) {
        return new SXWrapper(SX.bilin(this.sx, x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public SXWrapper rank1(SXWrapper alpha, SXWrapper x, SXWrapper y) {
        return new SXWrapper(SX.rank1(this.sx, alpha.getCasADiObject(), x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public SXWrapper logsumexp() {
        return new SXWrapper(SX.logsumexp(this.sx));
    }

    @Override
    public SXWrapper jtimes(SXWrapper arg, SXWrapper v, boolean tr, Dict opts) {
        return new SXWrapper(SX.jtimes_(this.sx, arg.getCasADiObject(), v.getCasADiObject(), tr, opts));
    }

    @Override
    public SXWrapper jtimes(SXWrapper arg, SXWrapper v, boolean tr) {
        return new SXWrapper(SX.jtimes_(this.sx, arg.getCasADiObject(), v.getCasADiObject(), tr));
    }

    @Override
    public SXWrapper jtimes(SXWrapper arg, SXWrapper v) {
        return new SXWrapper(SX.jtimes_(this.sx, arg.getCasADiObject(), v.getCasADiObject()));
    }

    @Override
    public SXWrapper gradient(SXWrapper arg, Dict opts) {
        return new SXWrapper(SX.gradient(this.sx, arg.getCasADiObject(), opts));
    }

    @Override
    public SXWrapper gradient(SXWrapper arg) {
        return new SXWrapper(SX.gradient(this.sx, arg.getCasADiObject()));
    }

    @Override
    public SXWrapper tangent(SXWrapper arg, Dict opts) {
        return new SXWrapper(SX.tangent(this.sx, arg.getCasADiObject(), opts));
    }

    @Override
    public SXWrapper tangent(SXWrapper arg) {
        return new SXWrapper(SX.tangent(this.sx, arg.getCasADiObject()));
    }

    @Override
    public SXWrapper linearize(SXWrapper x, SXWrapper x0, Dict opts) {
        return new SXWrapper(SX.linearize(this.sx, x.getCasADiObject(), x0.getCasADiObject(), opts));
    }

    @Override
    public SXWrapper linearize(SXWrapper x, SXWrapper x0) {
        return new SXWrapper(SX.linearize(this.sx, x.getCasADiObject(), x0.getCasADiObject()));
    }

    @Override
    public SXWrapper mpower(SXWrapper y) {
        return new SXWrapper(SX.mpower(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper soc(SXWrapper y) {
        return new SXWrapper(SX.soc(this.sx, y.getCasADiObject()));
    }

    public static SXWrapper sym(String name, long nrow, long ncol) {
        return new SXWrapper(SX.sym(name, nrow, ncol));
    }

    public static SXWrapper sym(String name, long nrow) {
        return new SXWrapper(SX.sym(name, nrow));
    }

    public static SXWrapper sym(String name) {
        return new SXWrapper(SX.sym(name));
    }

    public static SXWrapper sym(String name, Sparsity sp) {
        return new SXWrapper(SX.sym(name, sp));
    }

    public static SXVector sym(String name, Sparsity sp, long p) {
        return new SXVector(SX.sym(name, sp, p));
    }

    public static SXVector sym(String name, long nrow, long ncol, long p) {
        return new SXVector(SX.sym(name, nrow, ncol, p));
    }

    public static SXVectorCollection sym(String name, Sparsity sp, long p, long r) {
        return new SXVectorCollection(SX.sym(name, sp, p, r));
    }

    public static SXVectorCollection sym(String name, long nrow, long ncol, long p, long r) {
        return new SXVectorCollection(SX.sym(name, nrow, ncol, p, r));
    }

    public static SXWrapper zeros(long nrow, long ncol) {
        return new SXWrapper(SX.zeros(nrow, ncol));
    }

    public static SXWrapper zeros(long nrow) {
        return new SXWrapper(SX.zeros(nrow));
    }

    public static SXWrapper zeros() {
        return new SXWrapper(SX.zeros());
    }

    public static SXWrapper zeros(Sparsity sp) {
        return new SXWrapper(SX.zeros(sp));
    }

    public static SXWrapper ones(long nrow, long ncol) {
        return new SXWrapper(SX.ones(nrow, ncol));
    }

    public static SXWrapper ones(long nrow) {
        return new SXWrapper(SX.ones(nrow));
    }

    public static SXWrapper ones() {
        return new SXWrapper(SX.ones());
    }

    public static SXWrapper ones(Sparsity sp) {
        return new SXWrapper(SX.ones(sp));
    }

    @Override
    public SXWrapper add(SXWrapper other) {
        SX result = SX.plus(this.sx, other.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public <T extends Number> SXWrapper add(T number) {
        double other = number.doubleValue();
        SXWrapper summand = new SXWrapper(other);
        SX result = SX.plus(this.sx, summand.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper subtract(SXWrapper other) {
        SX result = SX.minus(this.sx, other.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public <T extends Number> SXWrapper subtract(T number) {
        double other = number.doubleValue();
        SXWrapper subtrahend = new SXWrapper(other);
        SX result = SX.minus(this.sx, subtrahend.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper multiply(SXWrapper other) {
        SX result = SX.times(this.sx, other.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public <T extends Number> SXWrapper multiply(T number) {
        double other = number.doubleValue();
        SXWrapper multiplicand = new SXWrapper(other);
        SX result = SX.times(this.sx, multiplicand.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper divide(SXWrapper other) {
        SX result = SX.rdivide(this.sx, other.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public <T extends Number> SXWrapper divide(T number) {
        double other = number.doubleValue();
        if (other == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        SXWrapper divisor = new SXWrapper(other);
        SX result = SX.rdivide(this.sx, divisor.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper lt(SXWrapper y) {
        return new SXWrapper(SX.lt(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper lt(T number) {
        double other = number.doubleValue();
        SXWrapper threshold = new SXWrapper(other);
        SX result = SX.lt(this.sx, threshold.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper le(SXWrapper y) {
        return new SXWrapper(SX.le(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper le(T number) {
        double other = number.doubleValue();
        SXWrapper threshold = new SXWrapper(other);
        SX result = SX.le(this.sx, threshold.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper gt(SXWrapper y) {
        return new SXWrapper(SX.gt(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper gt(T number) {
        double other = number.doubleValue();
        SXWrapper threshold = new SXWrapper(other);
        SX result = SX.gt(this.sx, threshold.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper ge(SXWrapper y) {
        return new SXWrapper(SX.ge(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper ge(T number) {
        double other = number.doubleValue();
        SXWrapper threshold = new SXWrapper(other);
        SX result = SX.ge(this.sx, threshold.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper eq(SXWrapper y) {
        return new SXWrapper(SX.eq(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper eq(T number) {
        double other = number.doubleValue();
        SXWrapper value = new SXWrapper(other);
        SX result = SX.eq(this.sx, value.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper ne(SXWrapper y) {
        return new SXWrapper(SX.ne(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper ne(T number) {
        double other = number.doubleValue();
        SXWrapper value = new SXWrapper(other);
        SX result = SX.ne(this.sx, value.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper logicAnd(SXWrapper y) {
        return new SXWrapper(SX.logic_and(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper logicOr(SXWrapper y) {
        return new SXWrapper(SX.logic_or(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper logicNot() {
        return new SXWrapper(SX.logic_not(this.sx));
    }

    @Override
    public SXWrapper abs() {
        return new SXWrapper(SX.abs(this.sx));
    }

    @Override
    public SXWrapper sqrt() {
        return new SXWrapper(SX.sqrt(this.sx));
    }

    @Override
    public SXWrapper sq() {
        return new SXWrapper(SX.sq(this.sx));
    }

    @Override
    public SXWrapper sin() {
        return new SXWrapper(SX.sin(this.sx));
    }

    @Override
    public SXWrapper cos() {
        return new SXWrapper(SX.cos(this.sx));
    }

    @Override
    public SXWrapper tan() {
        return new SXWrapper(SX.tan(this.sx));
    }

    @Override
    public SXWrapper atan() {
        return new SXWrapper(SX.atan(this.sx));
    }

    @Override
    public SXWrapper asin() {
        return new SXWrapper(SX.asin(this.sx));
    }

    @Override
    public SXWrapper acos() {
        return new SXWrapper(SX.acos(this.sx));
    }

    @Override
    public SXWrapper tanh() {
        return new SXWrapper(SX.tanh(this.sx));
    }

    @Override
    public SXWrapper sinh() {
        return new SXWrapper(SX.sinh(this.sx));
    }

    @Override
    public SXWrapper cosh() {
        return new SXWrapper(SX.cosh(this.sx));
    }

    @Override
    public SXWrapper atanh() {
        return new SXWrapper(SX.atanh(this.sx));
    }

    @Override
    public SXWrapper asinh() {
        return new SXWrapper(SX.asinh(this.sx));
    }

    @Override
    public SXWrapper acosh() {
        return new SXWrapper(SX.acosh(this.sx));
    }

    @Override
    public SXWrapper exp() {
        return new SXWrapper(SX.exp(this.sx));
    }

    @Override
    public SXWrapper log() {
        return new SXWrapper(SX.log(this.sx));
    }

    @Override
    public SXWrapper log10() {
        return new SXWrapper(SX.log10(this.sx));
    }

    @Override
    public SXWrapper log1p() {
        return new SXWrapper(SX.log1p(this.sx));
    }

    @Override
    public SXWrapper expm1() {
        return new SXWrapper(SX.expm1(this.sx));
    }

    @Override
    public SXWrapper floor() {
        return new SXWrapper(SX.floor(this.sx));
    }

    @Override
    public SXWrapper ceil() {
        return new SXWrapper(SX.ceil(this.sx));
    }

    @Override
    public SXWrapper erf() {
        return new SXWrapper(SX.erf(this.sx));
    }

    @Override
    public SXWrapper erfinv() {
        return new SXWrapper(SX.erfinv(this.sx));
    }

    @Override
    public SXWrapper sign() {
        return new SXWrapper(SX.sign(this.sx));
    }

    @Override
    public SXWrapper pow(SXWrapper other) {
        return new SXWrapper(SX.pow(this.sx, other.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper pow(T exponent) {
        double exp = exponent.doubleValue();
        SX exponentSX = new SX(exp);
        return new SXWrapper(SX.pow(this.sx, exponentSX));
    }

    @Override
    public SXWrapper mod(SXWrapper other) {
        return new SXWrapper(SX.mod(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper remainder(SXWrapper other) {
        return new SXWrapper(SX.remainder(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper atan2(SXWrapper other) {
        return new SXWrapper(SX.atan2(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper ifElseZero(SXWrapper other) {
        return new SXWrapper(SX.if_else_zero(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper fmin(SXWrapper other) {
        return new SXWrapper(SX.fmin(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper fmax(SXWrapper other) {
        return new SXWrapper(SX.fmax(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper copysign(SXWrapper other) {
        return new SXWrapper(SX.copysign(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper constpow(SXWrapper other) {
        return new SXWrapper(SX.constpow(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper hypot(SXWrapper other) {
        return new SXWrapper(SX.hypot(this.sx, other.getCasADiObject()));
    }

    public SX getCasADiObject() {
        return this.sx;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String s) {
        this.id = s;
    }

}
