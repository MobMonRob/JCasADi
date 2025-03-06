package de.dhbw.rahmlab.casadi.api.core.wrapper.im;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.*;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.*;
import de.dhbw.rahmlab.casadi.impl.casadi.IM;
import de.dhbw.rahmlab.casadi.impl.casadi.Slice;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

public class IMWrapper implements Wrapper<IMWrapper> {

    private final IM im;
    private String id;

    public IMWrapper() {
        this.im = new IM();
    }

    public IMWrapper(IM m) {
        this.im = new IM(m);
    }

    public IMWrapper(IMWrapper m) {
        this.im = new IM(m.getCasADiObject());
    }

    public IMWrapper(long... dimensions) {
        if (dimensions.length == 1) {
            this.im = IM.inf(dimensions[0]);
        } else if (dimensions.length == 2) {
            this.im = new IM(dimensions[0], dimensions[1]);
        } else {
            this.im = new IM();
        }
    }

    public IMWrapper(Sparsity sp) {
        this.im = new IM(sp);
    }

    public IMWrapper(Sparsity sp, IMWrapper d) {
        this.im = new IM(sp, d.getCasADiObject());
    }

    public IMWrapper(double value) {
        this.im = new IM(value);
    }

    public IMWrapper(NumberWrapper value) {
        this.im = new IM(value.getDoubleValue());
    }

    public IMWrapper(DoubleVectorCollection m) {
        this.im = new IM(m.getCasADiObject());
    }

    public IMWrapper(IntegerVector x) {
        this.im = new IM(x.getCasADiObject());
    }

    public IMWrapper(Sparsity sp, long val, boolean dummy) {
        this.im = new IM(sp, val, dummy);
    }

    public IMWrapper(Sparsity sp, IntegerVector val, boolean dummy) {
        this.im = new IM(sp, val.getCasADiObject(), dummy);
    }

    public long scalar() {
        return this.im.scalar();
    }

    public boolean hasNZ(long rr, long cc) {
        return this.im.has_nz(rr, cc);
    }

    @Override
    public boolean nonzero() {
        return this.im.__nonzero__();
    }

    @Override
    public IMWrapper get(String sliceDefinition) {
        IndexSlice indexSlice = IndexSlice.slicingSyntax(sliceDefinition);
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), true, indexSlice.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, Slice rr) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr);
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, IMWrapper rr) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, Sparsity sp) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, sp);
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, Slice rr, Slice cc) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, Slice rr, IMWrapper cc) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr, cc.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, IMWrapper rr, Slice cc) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc);
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, IMWrapper rr, IMWrapper cc) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(NumberWrapper... slice) {
        IndexSlice indexSlice = new IndexSlice(slice);
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), true, indexSlice.getCasADiObject());
        return output;
    }

    @Override
    public void set(IMWrapper m, String sliceDefinition) {
        IndexSlice indexSlice = IndexSlice.slicingSyntax(sliceDefinition);
        this.im.set(m.getCasADiObject(), true, indexSlice.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, boolean ind1, Slice rr) {
        this.im.set(m.getCasADiObject(), ind1, rr);
    }

    @Override
    public void set(IMWrapper m, boolean ind1, IMWrapper rr) {
        this.im.set(m.getCasADiObject(), ind1, rr.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, boolean ind1, Sparsity sp) {
        this.im.set(m.getCasADiObject(), ind1, sp);
    }

    @Override
    public void set(IMWrapper m, boolean ind1, Slice rr, Slice cc) {
        this.im.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public void set(IMWrapper m, boolean ind1, Slice rr, IMWrapper cc) {
        this.im.set(m.getCasADiObject(), ind1, rr, cc.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, boolean ind1, IMWrapper rr, Slice cc) {
        this.im.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc);
    }

    @Override
    public void set(IMWrapper m, boolean ind1, IMWrapper rr, IMWrapper cc) {
        this.im.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, NumberWrapper... slice) {
        IndexSlice indexSlice = new IndexSlice(slice);
        this.im.set(m.getCasADiObject(), true, indexSlice.getCasADiObject());
    }

    @Override
    public IMWrapper getNZ(boolean ind1, Slice k) {
        IMWrapper output = new IMWrapper();
        this.im.get_nz(output.getCasADiObject(), ind1, k);
        return output;
    }

    @Override
    public IMWrapper getNZ(boolean ind1, IMWrapper k) {
        IMWrapper output = new IMWrapper();
        this.im.get_nz(output.getCasADiObject(), ind1, k.getCasADiObject());
        return output;
    }

    @Override
    public void setNZ(IMWrapper m, boolean ind1, Slice k) {
        this.im.set_nz(m.getCasADiObject(), ind1, k);
    }

    @Override
    public void setNZ(IMWrapper m, boolean ind1, IMWrapper k) {
        this.im.set_nz(m.getCasADiObject(), ind1, k.getCasADiObject());
    }

    @Override
    public IMWrapper binary(long op, IMWrapper y) {
        return new IMWrapper(IM.binary(op, this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper unary(long op) {
        return new IMWrapper(IM.unary(op, this.im));
    }

    @Override
    public boolean isEqual(IMWrapper other, long depth) {
        return IM.is_equal(this.im, other.getCasADiObject(), depth);
    }

    @Override
    public boolean isEqual(IMWrapper other) {
        return IM.is_equal(this.im, other.getCasADiObject());
    }

    @Override
    public IMWrapper mmin() {
        return new IMWrapper(IM.mmin(this.im));
    }

    @Override
    public IMWrapper mmax() {
        return new IMWrapper(IM.mmax(this.im));
    }

    @Override
    public IMWrapper simplify() {
        return new IMWrapper(IM.simplify(this.im));
    }

    @Override
    public IMWrapper jacobian(IMWrapper x, Dict opts) {
        return new IMWrapper(IM.jacobian(this.im, x.getCasADiObject(), opts));
    }

    @Override
    public IMWrapper jacobian(IMWrapper x) {
        return new IMWrapper(IM.jacobian(this.im, x.getCasADiObject()));
    }

    @Override
    public Sparsity jacobianSparsity(IMWrapper x) {
        return IM.jacobian_sparsity(this.im, x.getCasADiObject());
    }

    @Override
    public IMWrapper hessian(IMWrapper x, Dict opts) {
        return new IMWrapper(IM.hessian(this.im, x.getCasADiObject(), opts));
    }

    @Override
    public IMWrapper hessian(IMWrapper x) {
        return new IMWrapper(IM.hessian(this.im, x.getCasADiObject()));
    }

    @Override
    public IMWrapper hessian(IMWrapper x, IMWrapper g, Dict opts) {
        return new IMWrapper(IM.hessian(this.im, x.getCasADiObject(), g.getCasADiObject(), opts));
    }

    @Override
    public IMWrapper hessian(IMWrapper x, IMWrapper g) {
        return new IMWrapper(IM.hessian(this.im, x.getCasADiObject()));
    }

    @Override
    public IMWrapper substitute(IMWrapper v, IMWrapper vdef) {
        return new IMWrapper(IM.substitute(this.im, v.getCasADiObject(), vdef.getCasADiObject()));
    }

    @Override
    public IMWrapper pinv() {
        return new IMWrapper(IM.pinv(this.im));
    }

    @Override
    public IMWrapper pinv(String lsolver, Dict dict) {
        return new IMWrapper(IM.pinv(this.im, lsolver, dict));
    }

    @Override
    public IMWrapper expmConst(IMWrapper imWrapper) {
        return new IMWrapper(IM.expm_const(this.im, imWrapper.getCasADiObject()));
    }

    @Override
    public IMWrapper expm() {
        return new IMWrapper(IM.expm(this.im));
    }

    @Override
    public IMWrapper solve(IMWrapper b) {
        return new IMWrapper(IM.solve(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper solve(IMWrapper b, String lsolver, Dict dict) {
        return new IMWrapper(IM.solve(this.im, b.getCasADiObject(), lsolver, dict));
    }

    @Override
    public IMWrapper inv() {
        return new IMWrapper(IM.inv(this.im));
    }

    @Override
    public IMWrapper inv(String lsolver, Dict dict) {
        return new IMWrapper(IM.inv(this.im, lsolver, dict));
    }

    @Override
    public long nNodes() {
        return IM.n_nodes(this.im);
    }

    @Override
    public String printOperator(StringVector args) {
        return IM.print_operator(this.im, args.getCasADiObject());
    }

    @Override
    public boolean dependsOn(IMWrapper arg) {
        return IM.depends_on(this.im, arg.getCasADiObject());
    }

    @Override
    public IMWrapper mrdivide(IMWrapper b) {
        return new IMWrapper(IM.mrdivide(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper mldivide(IMWrapper b) {
        return new IMWrapper(IM.mldivide(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper det() {
        return new IMWrapper(IM.det(this.im));
    }

    @Override
    public IMWrapper invMinor() {
        return new IMWrapper(IM.inv_minor(this.im));
    }

    @Override
    public IMWrapper trace() {
        return new IMWrapper(IM.trace(this.im));
    }

    @Override
    public IMWrapper norm1() {
        return new IMWrapper(IM.norm_1(this.im));
    }

    @Override
    public IMWrapper norm2() {
        return new IMWrapper(IM.norm_2(this.im));
    }

    @Override
    public IMWrapper normFro() {
        return new IMWrapper(IM.norm_fro(this.im));
    }

    @Override
    public IMWrapper normInf() {
        return new IMWrapper(IM.norm_inf(this.im));
    }

    @Override
    public IMWrapper sum2() {
        return new IMWrapper(IM.sum2(this.im));
    }

    @Override
    public IMWrapper sum1() {
        return new IMWrapper(IM.sum1(this.im));
    }

    @Override
    public IMWrapper dot(IMWrapper y) {
        return new IMWrapper(IM.dot(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper nullspace() {
        return new IMWrapper(IM.nullspace(this.im));
    }

    @Override
    public IMWrapper diag() {
        return new IMWrapper(IM.diag(this.im));
    }

    @Override
    public IMWrapper unite(IMWrapper B) {
        return new IMWrapper(IM.unite(this.im, B.getCasADiObject()));
    }


    @Override
    public IMWrapper project(Sparsity sp, boolean intersect) {
        return new IMWrapper(IM.project(this.im, sp, intersect));
    }

    @Override
    public IMWrapper project(Sparsity sp) {
        return new IMWrapper(IM.project(this.im, sp));
    }

    @Override
    public IMWrapper polyval(IMWrapper x) {
        return new IMWrapper(IM.polyval(this.im, x.getCasADiObject()));
    }

    @Override
    public IMWrapper densify(IMWrapper val) {
        return new IMWrapper(IM.densify(this.im, val.getCasADiObject()));
    }

    @Override
    public IMWrapper densify() {
        return new IMWrapper(IM.densify(this.im));
    }

    @Override
    public IMWrapper einstein(IMWrapper other, IMWrapper C, IntegerVector dim_a, IntegerVector dim_b,
                              IntegerVector dim_c, IntegerVector a, IntegerVector b, IntegerVector c) {
        return new IMWrapper(IM.einstein(this.im, other.getCasADiObject(), C.getCasADiObject(),
                dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public IMWrapper einstein(IMWrapper other, IntegerVector dim_a, IntegerVector dim_b, IntegerVector dim_c,
                              IntegerVector a, IntegerVector b, IntegerVector c) {
        return new IMWrapper(IM.einstein(this.im, other.getCasADiObject(), dim_a.getCasADiObject(),
                dim_b.getCasADiObject(), dim_c.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public IMWrapper cumsum(long axis) {
        return new IMWrapper(IM.cumsum(this.im, axis));
    }

    @Override
    public IMWrapper cumsum() {
        return new IMWrapper(IM.cumsum(this.im));
    }

    @Override
    public IMWrapper reshape(long nrow, long ncol) {
        return new IMWrapper(IM.reshape(this.im, nrow, ncol));
    }

    @Override
    public IMWrapper reshape(Sparsity sp) {
        return new IMWrapper(IM.reshape(this.im, sp));
    }

    @Override
    public IMWrapper sparsityCast(Sparsity sp) {
        return new IMWrapper(IM.sparsity_cast(this.im, sp));
    }

    @Override
    public IMWrapper kron(IMWrapper b) {
        return new IMWrapper(IM.kron(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper mtimes(IMWrapper other) {
        return new IMWrapper(IM.mtimes(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper mac(IMWrapper y, IMWrapper z) {
        return new IMWrapper(IM.mac(this.im, y.getCasADiObject(), z.getCasADiObject()));
    }

    public IMWrapper sparsify(double tol) {
        return new IMWrapper(IM.sparsify(this.im, tol));
    }

    public IMWrapper sparsify() {
        return new IMWrapper(IM.sparsify(this.im));
    }

    public void expand(IMWrapper weights, IMWrapper terms) {
        IM.expand(this.im, weights.getCasADiObject(), terms.getCasADiObject());
    }

    public IMWrapper pwConst(IMWrapper tval, IMWrapper val) {
        return new IMWrapper(IM.pw_const(this.im, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public IMWrapper pwLin(IMWrapper tval, IMWrapper val) {
        return new IMWrapper(IM.pw_lin(this.im, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public IMWrapper heaviside() {
        return new IMWrapper(IM.heaviside(this.im));
    }

    public IMWrapper rectangle() {
        return new IMWrapper(IM.rectangle(this.im));
    }

    public IMWrapper triangle() {
        return new IMWrapper(IM.triangle(this.im));
    }

    public IMWrapper ramp() {
        return new IMWrapper(IM.ramp(this.im));
    }

    public IMWrapper gaussQuadrate(IMWrapper x, IMWrapper a, IMWrapper b, long order) {
        return new IMWrapper(IM.gauss_quadrature(this.im, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order));
    }

    public IMWrapper gaussQuadrate(IMWrapper x, IMWrapper a, IMWrapper b) {
        return new IMWrapper(IM.gauss_quadrature(this.im, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject()));
    }

    public IMWrapper gaussQuadrate(IMWrapper x, IMWrapper a, IMWrapper b, long order, IMWrapper w) {
        return new IMWrapper(IM.gauss_quadrature(this.im, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order, w.getCasADiObject()));
    }

    @Override
    public BooleanVector whichDepends(IMWrapper var, long order, boolean tr) {
        return new BooleanVector(IM.which_depends(this.im, var.getCasADiObject(), order, tr));
    }

    @Override
    public BooleanVector whichDepends(IMWrapper var, long order) {
        return new BooleanVector(IM.which_depends(this.im, var.getCasADiObject(), order));
    }

    @Override
    public BooleanVector whichDepends(IMWrapper var) {
        return new BooleanVector(IM.which_depends(this.im, var.getCasADiObject()));
    }

    public IMWrapper taylor(IMWrapper x, IMWrapper a, long order) {
        return new IMWrapper(IM.taylor(this.im, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public IMWrapper mtaylor(IMWrapper x, IMWrapper a, long order) {
        return new IMWrapper(IM.mtaylor(this.im, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public IMWrapper mtaylor(IMWrapper x, IMWrapper a, long order, IntegerVector orderContributions) {
        return new IMWrapper(IM.mtaylor(this.im, x.getCasADiObject(), a.getCasADiObject(), order, orderContributions.getCasADiObject()));
    }

    public IMWrapper polyCoeff(IMWrapper x) {
        return new IMWrapper(IM.poly_coeff(this.im, x.getCasADiObject()));
    }

    public IMWrapper polyRoots() {
        return new IMWrapper(IM.poly_roots(this.im));
    }

    public IMWrapper eigSymbolic() {
        return new IMWrapper(IM.eig_symbolic(this.im));
    }

    @Override
    public DMWrapper evalf() {
        return new DMWrapper(IM.evalf(this.im));
    }

    public void qrSparse(IMWrapper V, IMWrapper R, IMWrapper beta, IntegerVector prinv, IntegerVector pc, boolean amd) {
        IM.qr_sparse(this.im, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(),
                prinv.getCasADiObject(), pc.getCasADiObject(), amd);
    }

    public void qrSparse(IMWrapper V, IMWrapper R, IMWrapper beta, IntegerVector prinv, IntegerVector pc) {
        IM.qr_sparse(this.im, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(),
                prinv.getCasADiObject(), pc.getCasADiObject());
    }

    public IMWrapper qrSolve(IMWrapper v, IMWrapper r, IMWrapper beta, IntegerVector prinv, IntegerVector pc, boolean tr) {
        return new IMWrapper(IM.qr_solve(this.im, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(),
                prinv.getCasADiObject(), pc.getCasADiObject(), tr));
    }

    public IMWrapper qrSolve(IMWrapper v, IMWrapper r, IMWrapper beta, IntegerVector prinv, IntegerVector pc) {
        return new IMWrapper(IM.qr_solve(this.im, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(),
                prinv.getCasADiObject(), pc.getCasADiObject()));
    }

    public void qr(IMWrapper Q, IMWrapper R) {
        IM.qr(this.im, Q.getCasADiObject(), R.getCasADiObject());
    }

    public void ldl(IMWrapper D, IMWrapper LT, IntegerVector p, boolean amd) {
        IM.ldl(this.im, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject(), amd);
    }

    public void ldl(IMWrapper D, IMWrapper LT, IntegerVector p) {
        IM.ldl(this.im, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject());
    }

    public IMWrapper ldlSolve(IMWrapper D, IMWrapper LT, IntegerVector p) {
        return new IMWrapper(IM.ldl_solve(this.im, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject()));
    }

    public IMWrapper all() {
        return new IMWrapper(IM.all(this.im));
    }

    public IMWrapper any() {
        return new IMWrapper(IM.any(this.im));
    }

    public IMWrapper adj() {
        return new IMWrapper(IM.adj(this.im));
    }

    public IMWrapper minor(long i, long j) {
        return new IMWrapper(IM.minor(this.im, i, j));
    }

    public IMWrapper cofactor(long i, long j) {
        return new IMWrapper(IM.cofactor(this.im, i, j));
    }

    public IMWrapper chol() {
        return new IMWrapper(IM.chol(this.im));
    }

    public IMWrapper normInfMul(IMWrapper y) {
        return new IMWrapper(IM.norm_inf_mul(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper printMe(IMWrapper y) {
        return new IMWrapper(this.im.printme(y.getCasADiObject()));
    }

    @Override
    public IMWrapper T() {
        return new IMWrapper(this.im.T());
    }

    @Override
    public void setMaxDepth(long eq_depth) {
        IM.set_max_depth(eq_depth);
    }

    @Override
    public void setMaxDepth() {
        IM.set_max_depth();
    }

    @Override
    public long getMaxDepth() {
        return IM.get_max_depth();
    }

    @Override
    public String typeName() {
        return IM.type_name();
    }

    public void printSplit(StringVector arg0, StringVector arg1) {
        this.im.print_split(arg0.getCasADiObject(), arg1.getCasADiObject());
    }

    @Override
    public String toString(boolean more) {
        return this.im.toString(more);
    }

    public String toString() {
        return this.im.toString();
    }

    public void clear() {
        this.im.clear();
    }

    public void resize(long nrow, long ncol) {
        this.im.resize(nrow, ncol);
    }

    public void reserve(long nnz) {
        this.im.reserve(nnz);
    }

    public void reserve(long nnz, long ncol) {
        this.im.reserve(nnz, ncol);
    }

    @Override
    public void erase(IntegerVector rr, IntegerVector cc, boolean ind1) {
        this.im.erase(rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void erase(IntegerVector rr, IntegerVector cc) {
        this.im.erase(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void erase(IntegerVector rr, boolean ind1) {
        this.im.erase(rr.getCasADiObject(), ind1);
    }

    @Override
    public void erase(IntegerVector rr) {
        this.im.erase(rr.getCasADiObject());
    }

    public void remove(IntegerVector rr, IntegerVector cc) {
        this.im.remove(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void enlarge(long nrow, long ncol, IntegerVector rr, IntegerVector cc, boolean ind1) {
        this.im.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void enlarge(long nrow, long ncol, IntegerVector rr, IntegerVector cc) {
        this.im.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject());
    }

    public IntegerVector nonzeros() {
        return new IntegerVector(this.im.nonzeros());
    }

    @Override
    public Sparsity sparsity() {
        return this.im.sparsity();
    }

    public long elementHash() {
        return this.im.element_hash();
    }

    @Override
    public boolean isRegular() {
        return this.im.is_regular();
    }

    public boolean isSmooth() {
        return this.im.is_smooth();
    }

    public boolean isLeaf() {
        return this.im.is_leaf();
    }

    @Override
    public boolean isCommutative() {
        return this.im.is_commutative();
    }

    @Override
    public boolean isSymbolic() {
        return this.im.is_symbolic();
    }

    @Override
    public boolean isValidInput() {
        return this.im.is_valid_input();
    }

    @Override
    public boolean hasDuplicates() {
        return this.im.has_duplicates();
    }

    @Override
    public void resetInput() {
        this.im.reset_input();
    }

    @Override
    public boolean isConstant() {
        return this.im.is_constant();
    }

    public boolean isInteger() {
        return this.im.is_integer();
    }

    @Override
    public boolean isZero() {
        return this.im.is_zero();
    }

    @Override
    public boolean isOne() {
        return this.im.is_one();
    }

    @Override
    public boolean isMinusOne() {
        return this.im.is_minus_one();
    }

    @Override
    public boolean isEye() {
        return this.im.is_eye();
    }

    @Override
    public long op() {
        return this.im.op();
    }

    @Override
    public boolean isOp(long op) {
        return this.im.is_op(op);
    }

    public boolean hasZeros() {
        return this.im.has_zeros();
    }

    public IntegerVector getNonzeros() {
        return new IntegerVector(this.im.get_nonzeros());
    }

    public IntegerVector getElements() {
        return new IntegerVector(this.im.get_elements());
    }

    @Override
    public String getName() {
        return this.im.name();
    }

    @Override
    public IMWrapper dep(long ch) {
        return new IMWrapper(this.im.dep(ch));
    }

    @Override
    public IMWrapper dep() {
        return new IMWrapper(this.im.dep());
    }

    @Override
    public long nDep() {
        return this.im.n_dep();
    }

    public void exportCode(String lang) {
        this.im.export_code(lang);
    }

    @Override
    public Dict info() {
        return this.im.info();
    }

    public String serialize() {
        return this.im.serialize();
    }

    public void toFile(String filename, String format) {
        this.im.to_file(filename, format);
    }

    public void toFile(String filename) {
        this.im.to_file(filename);
    }

    @Override
    public IMSubIndexWrapper at(int rr) {
        return new IMSubIndexWrapper(this.im.at(rr));
    }

    @Override
    public IMSubMatrixWrapper at(int rr, int cc) {
        return new IMSubMatrixWrapper(this.im.at(rr, cc));
    }

    @Override
    public void assign(IMWrapper other) {
        this.im.assign(other.getCasADiObject());
    }

    @Override
    public IMWrapper vec() {
        return new IMWrapper(IM.vec(this.im));
    }

    public IMWrapper repmat(long n, long m) {
        return new IMWrapper(IM.repmat(this.im, n, m));
    }

    public IMWrapper repmant(long n) {
        return new IMWrapper(IM.repmat(this.im, n));
    }

    @Override
    public long nnz() {
        return this.im.nnz_();
    }

    @Override
    public long nnzLower() {
        return this.im.nnz_lower_();
    }

    @Override
    public long nnzUpper() {
        return this.im.nnz_upper_();
    }

    @Override
    public long nnzDiag() {
        return this.im.nnz_diag();
    }

    @Override
    public long numel() {
        return this.im.numel_();
    }

    @Override
    public long size1() {
        return this.im.size1_();
    }

    @Override
    public long rows() {
        return this.im.rows();
    }

    @Override
    public long size2() {
        return this.im.size2_();
    }

    @Override
    public long columns() {
        return this.im.columns();
    }

    @Override
    public String dim(boolean withNz) {
        return this.im.dim_(withNz);
    }

    @Override
    public String dim() {
        return this.im.dim_();
    }

    @Override
    public long size(long axis) {
        return this.im.size_(axis);
    }

    @Override
    public boolean isEmpty(boolean both) {
        return this.im.is_empty_(both);
    }

    @Override
    public boolean isEmpty() {
        return this.im.is_empty_();
    }

    @Override
    public boolean isDense() {
        return this.im.is_dense_();
    }

    @Override
    public boolean isScalar(boolean scalarAndDense) {
        return this.im.is_scalar_(scalarAndDense);
    }

    @Override
    public boolean isScalar() {
        return this.im.is_scalar_();
    }

    @Override
    public boolean isSquare() {
        return this.im.is_square();
    }

    @Override
    public boolean isVector() {
        return this.im.is_vector_();
    }

    @Override
    public boolean isRow() {
        return this.im.is_row_();
    }

    @Override
    public boolean isColumn() {
        return this.im.is_column_();
    }

    @Override
    public boolean isTriu() {
        return this.im.is_triu_();
    }

    @Override
    public boolean isTril() {
        return this.im.is_tril_();
    }

    @Override
    public IntegerVector getRow() {
        return new IntegerVector(this.im.get_row());
    }

    @Override
    public IntegerVector getColInd() {
        return new IntegerVector(this.im.get_colind());
    }

    @Override
    public long row(long el) {
        return this.im.row_(el);
    }

    @Override
    public long colind(long col) {
        return this.im.colind_(col);
    }

    @Override
    public IMWrapper interp1d(DoubleVector x, DoubleVector xq, String mode, boolean equidistant) {
        return new IMWrapper(IM.interp1d(x.getCasADiObject(), this.im, xq.getCasADiObject(), mode, equidistant));
    }

    @Override
    public long sprank() {
        return IM.sprank(this.im);
    }

    @Override
    public long norm0Mul(IMWrapper y) {
        return IM.norm_0_mul(this.im, y.getCasADiObject());
    }

    @Override
    public IMWrapper tril(boolean includeDiagonal) {
        return new IMWrapper(IM.tril(this.im, includeDiagonal));
    }

    @Override
    public IMWrapper tril() {
        return new IMWrapper(IM.tril(this.im));
    }

    @Override
    public IMWrapper triu(boolean includeDiagonal) {
        return new IMWrapper(IM.triu(this.im, includeDiagonal));
    }

    @Override
    public IMWrapper triu() {
        return new IMWrapper(IM.triu(this.im));
    }

    @Override
    public IMWrapper sumsqr() {
        return new IMWrapper(IM.sumsqr(this.im));
    }

    public IMWrapper linspace(IMWrapper b, long nsteps) {
        return new IMWrapper(IM.linspace(this.im, b.getCasADiObject(), nsteps));
    }

    @Override
    public IMWrapper cross(IMWrapper b, long dim) {
        return new IMWrapper(IM.cross(this.im, b.getCasADiObject(), dim));
    }

    @Override
    public IMWrapper cross(IMWrapper b) {
        return new IMWrapper(IM.cross(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper skew() {
        return new IMWrapper(IM.skew(this.im));
    }

    @Override
    public IMWrapper invSkew() {
        return new IMWrapper(IM.inv_skew(this.im));
    }

    @Override
    public IMWrapper tril2symm() {
        return new IMWrapper(IM.tril2symm(this.im));
    }

    @Override
    public IMWrapper triu2symm() {
        return new IMWrapper(IM.triu2symm(this.im));
    }

    @Override
    public IMWrapper repsum(long n, long m) {
        return new IMWrapper(IM.repsum(this.im, n, m));
    }

    @Override
    public IMWrapper repsum(long n) {
        return new IMWrapper(IM.repsum(this.im, n));
    }

    @Override
    public IMWrapper diff(long n, long axis) {
        return new IMWrapper(IM.diff(this.im, n, axis));
    }

    @Override
    public IMWrapper diff(long n) {
        return new IMWrapper(IM.diff(this.im, n));
    }

    @Override
    public IMWrapper diff() {
        return new IMWrapper(IM.diff(this.im));
    }

    @Override
    public boolean isLinear(IMWrapper var) {
        return IM.is_linear(this.im, var.getCasADiObject());
    }

    @Override
    public boolean isQuadratic(IMWrapper var) {
        return IM.is_quadratic(this.im, var.getCasADiObject());
    }

    @Override
    public void quadraticCoeff(IMWrapper var, IMWrapper A, IMWrapper b, IMWrapper c, boolean check) {
        IM.quadratic_coeff(this.im, var.getCasADiObject(), A.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject(), check);
    }

    @Override
    public void linearCoeff(IMWrapper var, IMWrapper A, IMWrapper b, boolean check) {
        IM.linear_coeff(this.im, var.getCasADiObject(), A.getCasADiObject(), b.getCasADiObject(), check);
    }

    @Override
    public IMWrapper bilin(IMWrapper x, IMWrapper y) {
        return new IMWrapper(IM.bilin(this.im, x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public IMWrapper rank1(IMWrapper alpha, IMWrapper x, IMWrapper y) {
        return new IMWrapper(IM.rank1(this.im, alpha.getCasADiObject(), x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public IMWrapper logsumexp() {
        return new IMWrapper(IM.logsumexp(this.im));
    }

    @Override
    public IMWrapper jtimes(IMWrapper arg, IMWrapper v, boolean tr, Dict opts) {
        return new IMWrapper(IM.jtimes_(this.im, arg.getCasADiObject(), v.getCasADiObject(), tr, opts));
    }

    @Override
    public IMWrapper jtimes(IMWrapper arg, IMWrapper v, boolean tr) {
        return new IMWrapper(IM.jtimes_(this.im, arg.getCasADiObject(), v.getCasADiObject(), tr));
    }

    @Override
    public IMWrapper jtimes(IMWrapper arg, IMWrapper v) {
        return new IMWrapper(IM.jtimes_(this.im, arg.getCasADiObject(), v.getCasADiObject()));
    }

    @Override
    public IMWrapper gradient(IMWrapper arg, Dict opts) {
        return new IMWrapper(IM.gradient(this.im, arg.getCasADiObject(), opts));
    }

    @Override
    public IMWrapper gradient(IMWrapper arg) {
        return new IMWrapper(IM.gradient(this.im, arg.getCasADiObject()));
    }

    @Override
    public IMWrapper tangent(IMWrapper arg, Dict opts) {
        return new IMWrapper(IM.tangent(this.im, arg.getCasADiObject(), opts));
    }

    @Override
    public IMWrapper tangent(IMWrapper arg) {
        return new IMWrapper(IM.tangent(this.im, arg.getCasADiObject()));
    }

    @Override
    public IMWrapper linearize(IMWrapper x, IMWrapper x0, Dict opts) {
        return new IMWrapper(IM.linearize(this.im, x.getCasADiObject(), x0.getCasADiObject(), opts));
    }

    @Override
    public IMWrapper linearize(IMWrapper x, IMWrapper x0) {
        return new IMWrapper(IM.linearize(this.im, x.getCasADiObject(), x0.getCasADiObject()));
    }

    @Override
    public IMWrapper mpower(IMWrapper y) {
        return new IMWrapper(IM.mpower(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper soc(IMWrapper y) {
        return new IMWrapper(IM.soc(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper add(IMWrapper other) {
        return new IMWrapper(IM.plus(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper add(N number) {
        double other = number.doubleValue();
        IMWrapper summand = new IMWrapper(other);
        IM result = IM.plus(this.im, summand.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper subtract(IMWrapper other) {
        return new IMWrapper(IM.minus(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper subtract(N number) {
        double other = number.doubleValue();
        IMWrapper subtrahend = new IMWrapper(other);
        IM result = IM.minus(this.im, subtrahend.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper multiply(IMWrapper other) {
        return new IMWrapper(IM.times(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper multiply(N number) {
        double other = number.doubleValue();
        IMWrapper multiplicand = new IMWrapper(other);
        IM result = IM.times(this.im, multiplicand.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper divide(IMWrapper other) {
        return new IMWrapper(IM.rdivide(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper divide(N number) {
        double other = number.doubleValue();
        if (other == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        IMWrapper divisor = new IMWrapper(other);
        IM result = IM.rdivide(this.im, divisor.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper lt(IMWrapper y) {
        return new IMWrapper(IM.lt(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper lt(N number) {
        double other = number.doubleValue();
        IMWrapper threshold = new IMWrapper(other);
        IM result = IM.lt(this.im, threshold.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper le(IMWrapper y) {
        return new IMWrapper(IM.le(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper le(N number) {
        double other = number.doubleValue();
        IMWrapper threshold = new IMWrapper(other);
        IM result = IM.le(this.im, threshold.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper gt(IMWrapper y) {
        return new IMWrapper(IM.gt(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper gt(N number) {
        double other = number.doubleValue();
        IMWrapper threshold = new IMWrapper(other);
        IM result = IM.gt(this.im, threshold.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper ge(IMWrapper y) {
        return new IMWrapper(IM.ge(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper ge(N number) {
        double other = number.doubleValue();
        IMWrapper threshold = new IMWrapper(other);
        IM result = IM.ge(this.im, threshold.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper eq(IMWrapper y) {
        return new IMWrapper(IM.eq(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper eq(N number) {
        double other = number.doubleValue();
        IMWrapper value = new IMWrapper(other);
        IM result = IM.eq(this.im, value.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper ne(IMWrapper y) {
        return new IMWrapper(IM.ne(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper ne(N number) {
        double other = number.doubleValue();
        IMWrapper value = new IMWrapper(other);
        IM result = IM.ne(this.im, value.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper logicAnd(IMWrapper y) {
        return new IMWrapper(IM.logic_and(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper logicOr(IMWrapper y) {
        return new IMWrapper(IM.logic_or(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper logicNot() {
        return new IMWrapper(IM.logic_not(this.im));
    }

    @Override
    public IMWrapper abs() {
        return new IMWrapper(IM.abs(this.im));
    }

    @Override
    public IMWrapper sqrt() {
        return new IMWrapper(IM.sqrt(this.im));
    }

    @Override
    public IMWrapper sq() {
        return new IMWrapper(IM.sq(this.im));
    }

    @Override
    public IMWrapper sin() {
        return new IMWrapper(IM.sin(this.im));
    }

    @Override
    public IMWrapper cos() {
        return new IMWrapper(IM.cos(this.im));
    }

    @Override
    public IMWrapper tan() {
        return new IMWrapper(IM.tan(this.im));
    }

    @Override
    public IMWrapper atan() {
        return new IMWrapper(IM.atan(this.im));
    }

    @Override
    public IMWrapper asin() {
        return new IMWrapper(IM.asin(this.im));
    }

    @Override
    public IMWrapper acos() {
        return new IMWrapper(IM.acos(this.im));
    }

    @Override
    public IMWrapper tanh() {
        return new IMWrapper(IM.tanh(this.im));
    }

    @Override
    public IMWrapper sinh() {
        return new IMWrapper(IM.sinh(this.im));
    }

    @Override
    public IMWrapper cosh() {
        return new IMWrapper(IM.cosh(this.im));
    }

    @Override
    public IMWrapper atanh() {
        return new IMWrapper(IM.atanh(this.im));
    }

    @Override
    public IMWrapper asinh() {
        return new IMWrapper(IM.asinh(this.im));
    }

    @Override
    public IMWrapper acosh() {
        return new IMWrapper(IM.acosh(this.im));
    }

    @Override
    public IMWrapper exp() {
        return new IMWrapper(IM.exp(this.im));
    }

    @Override
    public IMWrapper log() {
        return new IMWrapper(IM.log(this.im));
    }

    @Override
    public IMWrapper log10() {
        return new IMWrapper(IM.log10(this.im));
    }

    @Override
    public IMWrapper log1p() {
        return new IMWrapper(IM.log1p(this.im));
    }

    @Override
    public IMWrapper expm1() {
        return new IMWrapper(IM.expm1(this.im));
    }

    @Override
    public IMWrapper floor() {
        return new IMWrapper(IM.floor(this.im));
    }

    @Override
    public IMWrapper ceil() {
        return new IMWrapper(IM.ceil(this.im));
    }

    @Override
    public IMWrapper erf() {
        return new IMWrapper(IM.erf(this.im));
    }

    @Override
    public IMWrapper erfinv() {
        return new IMWrapper(IM.erfinv(this.im));
    }

    @Override
    public IMWrapper sign() {
        return new IMWrapper(IM.sign(this.im));
    }

    @Override
    public IMWrapper pow(IMWrapper other) {
        return new IMWrapper(IM.pow(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper pow(N exponent) {
        double exp = exponent.doubleValue();
        IM exponentIM = new IM(exp);
        return new IMWrapper(IM.pow(this.im, exponentIM));
    }

    @Override
    public IMWrapper mod(IMWrapper other) {
        return new IMWrapper(IM.mod(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper remainder(IMWrapper other) {
        return new IMWrapper(IM.remainder(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper atan2(IMWrapper other) {
        return new IMWrapper(IM.atan2(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper ifElseZero(IMWrapper other) {
        return new IMWrapper(IM.if_else_zero(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper fmin(IMWrapper other) {
        return new IMWrapper(IM.fmin(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper fmax(IMWrapper other) {
        return new IMWrapper(IM.fmax(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper copysign(IMWrapper other) {
        return new IMWrapper(IM.copysign(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper constpow(IMWrapper other) {
        return new IMWrapper(IM.constpow(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper hypot(IMWrapper other) {
        return new IMWrapper(IM.hypot(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper negate() {
        IMWrapper sign = new IMWrapper(-1);
        return new IMWrapper(IM.times(this.im, sign.getCasADiObject()));
    }

    public IM getCasADiObject() {
        return this.im;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
