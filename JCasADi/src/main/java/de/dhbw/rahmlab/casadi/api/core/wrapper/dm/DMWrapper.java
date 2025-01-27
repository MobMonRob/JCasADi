package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.*;
import de.dhbw.rahmlab.casadi.impl.casadi.*;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

public class DMWrapper implements Wrapper<DMWrapper> {

    private DM dm;

    public DMWrapper() {
        this.dm = new DM();
    }

    public DMWrapper(DM dm) {
        this.dm = dm;
    }

    public DMWrapper(Sparsity sp, double val, boolean dummy) {
        this.dm = new DM(sp, val, dummy);
    }

    public DMWrapper(Sparsity sp, DoubleVector d, boolean dummy) {
        this.dm = new DM(sp, d.getCasADiObject(), dummy);
    }

    public static DMWrapper fromSparsity(Sparsity sp) {
        return new DMWrapper(new DM(sp));
    }

    public static DMWrapper fromValue(double value) {
        return new DMWrapper(new DM(value));
    }

    public static DMWrapper fromArray(StdVectorDouble values) {
        return new DMWrapper(new DM(values));
    }

    public static DMWrapper fromSparsityAndValues(Sparsity sparsity, DMWrapper values) {
        return new DMWrapper(new DM(sparsity, values.dm));
    }

    public static DMWrapper fromSize(long nrow, long ncol) {
        return new DMWrapper(new DM(nrow, ncol));
    }

    public static DMWrapper fromDenseMatrix(DoubleVectorCollection m) {
        return new DMWrapper(new DM(m.getCasADiObject()));
    }

    public static DMWrapper fromVector(DoubleVector x) {
        return new DMWrapper(new DM(x.getCasADiObject()));
    }

    public static DMWrapper fromValues(double... values) {
        return new DMWrapper(new DM(new DoubleVector(values).getCasADiObject()));
    }

    public static DMWrapper fromIterable(Iterable<Double> values) {
        return new DMWrapper(new DM(new DoubleVector(values).getCasADiObject()));
    }

    public static DMWrapper rand(long nrow, long ncol) {
        return new DMWrapper(DM.rand(nrow, ncol));
    }

    public static DMWrapper rand(long nrow) {
        return new DMWrapper(DM.rand(nrow));
    }

    public static DMWrapper rand() {
        return new DMWrapper(DM.rand());
    }

    public static DMWrapper rand(Sparsity sp) {
        return new DMWrapper(DM.rand(sp));
    }

    public double scalar() {
        return this.dm.scalar();
    }

    public boolean hasNZ(long rr, long cc) {
        return this.dm.has_nz(rr, cc);
    }

    public boolean nonzero() {
        return this.dm.__nonzero__();
    }

    // ----- Get a submatrix, single argument -----
    @Override
    public DMWrapper get(boolean ind1, Slice rr) {
        DMWrapper output = new DMWrapper();
        this.dm.get(output.getCasADiObject(), ind1, rr);
        return output;
    }

    @Override
    public DMWrapper get(boolean ind1, IM rr) {
        DMWrapper output = new DMWrapper();
        this.dm.get(output.getCasADiObject(), ind1, rr);
        return output;
    }

    @Override
    public DMWrapper get(boolean ind1, Sparsity sp) {
        DMWrapper output = new DMWrapper();
        this.dm.get(output.getCasADiObject(), ind1, sp);
        return output;
    }

    // ----- Get a submatrix, two arguments -----
    @Override
    public DMWrapper get(boolean ind1, Slice rr, Slice cc) {
        DMWrapper output = new DMWrapper();
        this.dm.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    @Override
    public DMWrapper get(boolean ind1, Slice rr, IM cc) {
        DMWrapper output = new DMWrapper();
        this.dm.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    @Override
    public DMWrapper get(boolean ind1, IM rr, Slice cc) {
        DMWrapper output = new DMWrapper();
        this.dm.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    @Override
    public DMWrapper get(boolean ind1, IM rr, IM cc) {
        DMWrapper output = new DMWrapper();
        this.dm.get(output.getCasADiObject(), ind1, rr, cc);
        return output;
    }

    // ----- Set a submatrix, single arguments -----
    @Override
    public void set(DMWrapper m, boolean ind1, Slice rr) {
        this.dm.set(m.getCasADiObject(), ind1, rr);
    }

    @Override
    public void set(DMWrapper m, boolean ind1, IM rr) {
        this.dm.set(m.getCasADiObject(), ind1, rr);
    }

    @Override
    public void set(DMWrapper m, boolean ind1, Sparsity sp) {
        this.dm.set(m.getCasADiObject(), ind1, sp);
    }

    // ----- Set a submatrix, two arguments -----
    @Override
    public void set(DMWrapper m, boolean ind1, Slice rr, Slice cc) {
       this.dm.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public void set(DMWrapper m, boolean ind1, Slice rr, IM cc) {
        this.dm.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public void set(DMWrapper m, boolean ind1, IM rr, Slice cc) {
        this.dm.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public void set(DMWrapper m, boolean ind1, IM rr, IM cc) {
        this.dm.set(m.getCasADiObject(), ind1, rr, cc);
    }

    @Override
    public DMWrapper getNZ(boolean ind1, Slice k) {
        DMWrapper output = new DMWrapper();
        this.dm.get_nz(output.getCasADiObject(), ind1, k);
        return output;
    }

    @Override
    public DMWrapper getNZ(boolean ind1, IM k) {
        DMWrapper output = new DMWrapper();
        this.dm.get_nz(output.getCasADiObject(), ind1, k);
        return output;
    }

    @Override
    public void setNZ(DMWrapper m, boolean ind1, Slice k) {
        this.dm.set_nz(m.getCasADiObject(), ind1, k);
    }

    @Override
    public void setNZ(DMWrapper m, boolean ind1, IM k) {
        this.dm.set_nz(m.getCasADiObject(), ind1, k);
    }

    @Override
    public DMWrapper binary(long op, DMWrapper x, DMWrapper y) {
        DM result = DM.binary(op, x.getCasADiObject(), y.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper unary(long op, DMWrapper x) {
        DM result = DM.unary(op, x.getCasADiObject());
        return new DMWrapper(result);
    }

    public DMWrapper scalarMatrix(long op, DMWrapper x, DMWrapper y) {
        DM result = DM.scalar_matrix(op, x.getCasADiObject(), y.getCasADiObject());
        return new DMWrapper(result);
    }

    public DMWrapper matrixScalar(long op, DMWrapper x, DMWrapper y) {
        DM result = DM.matrix_scalar(op, x.getCasADiObject(), y.getCasADiObject());
        return new DMWrapper(result);
    }

    public DMWrapper matrixMatrix(long op, DMWrapper x, DMWrapper y) {
        DM result = DM.matrix_matrix(op, x.getCasADiObject(), y.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public boolean isEqual(DMWrapper other, long depth) {
        return DM.is_equal(this.dm, other.getCasADiObject(), depth);
    }

    @Override
    public boolean isEqual(DMWrapper other) {
        return DM.is_equal(this.dm, other.getCasADiObject());
    }

    @Override
    public DMWrapper mmin() {
        return new DMWrapper(DM.mmin(this.dm));
    }

    @Override
    public DMWrapper mmax() {
        return new DMWrapper(DM.mmax(this.dm));
    }

    @Override
    public DMWrapper simplify() {
        return new DMWrapper(DM.simplify(this.dm));
    }

    @Override
    public DMWrapper jacobian(DMWrapper x, Dict opts) {
        return new DMWrapper(DM.jacobian(this.dm, x.getCasADiObject(), opts));
    }

    @Override
    public DMWrapper jacobian(DMWrapper x) {
        return new DMWrapper(DM.jacobian(this.dm, x.getCasADiObject()));
    }

    @Override
    public Sparsity jacobianSparsity(DMWrapper x) {
        return DM.jacobian_sparsity(this.dm, x.getCasADiObject());
    }

    @Override
    public DMWrapper hessian(DMWrapper x, Dict opts) {
        return new DMWrapper(DM.hessian(this.dm, x.getCasADiObject(), opts));
    }

    @Override
    public DMWrapper hessian(DMWrapper x) {
        return new DMWrapper(DM.hessian(this.dm, x.getCasADiObject()));
    }

    @Override
    public DMWrapper hessian(DMWrapper x, DMWrapper g, Dict opts) {
        return new DMWrapper(DM.hessian(this.dm, x.getCasADiObject(), g.getCasADiObject(), opts));
    }

    @Override
    public DMWrapper hessian(DMWrapper x, DMWrapper g) {
        return new DMWrapper(DM.hessian(this.dm, x.getCasADiObject(), g.getCasADiObject()));
    }

    @Override
    public DMWrapper substitute(DMWrapper v, DMWrapper vdef) {
        return new DMWrapper(DM.substitute(this.dm, v.getCasADiObject(), vdef.getCasADiObject()));
    }

    @Override
    public DMWrapper pinv() {
        return new DMWrapper(DM.pinv(this.dm));
    }

    @Override
    public DMWrapper pinv(String lsolver, Dict opts) {
        return new DMWrapper(DM.pinv(this.dm, lsolver, opts));
    }

    @Override
    public DMWrapper expmConst(DMWrapper t) {
        return new DMWrapper(DM.expm_const(this.dm, t.getCasADiObject()));
    }

    @Override
    public DMWrapper expm() {
        return new DMWrapper(DM.expm(this.dm));
    }

    @Override
    public DMWrapper solve(DMWrapper b) {
        return new DMWrapper(DM.solve(this.dm, b.getCasADiObject()));
    }

    @Override
    public DMWrapper solve(DMWrapper b, String lsolver, Dict opts) {
        return new DMWrapper(DM.solve(this.dm, b.getCasADiObject(), lsolver, opts));
    }

    @Override
    public DMWrapper inv() {
        return new DMWrapper(DM.inv(this.dm));
    }

    @Override
    public DMWrapper inv(String lsolver, Dict opts) {
        return new DMWrapper(DM.inv(this.dm, lsolver, opts));
    }

    @Override
    public long nNodes() {
        return DM.n_nodes(this.dm);
    }

    @Override
    public String printOperator(StringVectorCollection args) {
        return DM.print_operator(this.dm, args.getCasADiObject());
    }

    @Override
    public DMWrapper _bilin(DMWrapper x, DMWrapper y) {
        return new DMWrapper(DM._bilin(this.dm, x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public DMWrapper _rank1(DMWrapper alpha, DMWrapper x, DMWrapper y) {
        return new DMWrapper(DM._rank1(this.dm, alpha.getCasADiObject(), x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public boolean dependsOn(DMWrapper arg) {
        return DM.depends_on(this.dm, arg.getCasADiObject());
    }

    @Override
    public DMWrapper mrdivide(DMWrapper y) {
        return new DMWrapper(DM.mrdivide(this.dm, y.getCasADiObject()));
    }

    @Override
    public DMWrapper mldivide(DMWrapper y) {
        return new DMWrapper(DM.mldivide(this.dm, y.getCasADiObject()));
    }

    @Override
    public DMVector symvar() {
        return new DMVector(DM.symvar(this.dm));
    }

    @Override
    public DMWrapper det() {
        return new DMWrapper(DM.det(this.dm));
    }

    @Override
    public DMWrapper invMinor() {
        return new DMWrapper(DM.inv_minor(this.dm));
    }

    @Override
    public DMWrapper trace() {
        return new DMWrapper(DM.trace(this.dm));
    }

    @Override
    public DMWrapper norm1() {
        return new DMWrapper(DM.norm_1(this.dm));
    }

    @Override
    public DMWrapper norm2() {
        return new DMWrapper(DM.norm_2(this.dm));
    }

    @Override
    public DMWrapper normFro() {
        return new DMWrapper(DM.norm_fro(this.dm));
    }

    @Override
    public DMWrapper normInf() {
        return new DMWrapper(DM.norm_inf(this.dm));
    }

    @Override
    public DMWrapper sum2() {
        return new DMWrapper(DM.sum2(this.dm));
    }

    @Override
    public DMWrapper sum1() {
        return new DMWrapper(DM.sum1(this.dm));
    }

    @Override
    public DMWrapper dot(DMWrapper y) {
        return new DMWrapper(DM.dot(this.dm, y.getCasADiObject()));
    }

    @Override
    public DMWrapper nullspace() {
        return new DMWrapper(DM.nullspace(this.dm));
    }

    @Override
    public DMWrapper diag() {
        return new DMWrapper(DM.diag(this.dm));
    }

    @Override
    public DMWrapper unite(DMWrapper B) {
        return new DMWrapper(DM.unite(this.dm, B.getCasADiObject()));
    }

    @Override
    public DMWrapper project(Sparsity sp, boolean intersect) {
        return new DMWrapper(DM.project(this.dm, sp, intersect));
    }

    @Override
    public DMWrapper project(Sparsity sp) {
        return new DMWrapper(DM.project(this.dm, sp));
    }

    @Override
    public DMWrapper polyval(DMWrapper x) {
        return new DMWrapper(DM.polyval(this.dm, x.getCasADiObject()));
    }

    @Override
    public DMWrapper densify(DMWrapper val) {
        return new DMWrapper(DM.densify(this.dm, val.getCasADiObject()));
    }

    @Override
    public DMWrapper densify() {
        return new DMWrapper(DM.densify(this.dm));
    }

    @Override
    public DMWrapper einstein(DMWrapper other, DMWrapper C,
                              IntegerVector dim_a, IntegerVector dim_b,
                              IntegerVector dim_c, IntegerVector a,
                              IntegerVector b, IntegerVector c) {
        return new DMWrapper(DM.einstein(this.dm, other.getCasADiObject(), C.getCasADiObject(),
                dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(),
                a.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public DMWrapper einstein(DMWrapper other,
                              IntegerVector dim_a, IntegerVector dim_b,
                              IntegerVector dim_c, IntegerVector a,
                              IntegerVector b, IntegerVector c) {
        return new DMWrapper(DM.einstein(this.dm, other.getCasADiObject(),
                dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(),
                a.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public DMWrapper cumsum(long axis) {
        return new DMWrapper(DM.cumsum(this.dm, axis));
    }

    @Override
    public DMWrapper cumsum() {
        return new DMWrapper(DM.cumsum(this.dm));
    }

    @Override
    public DMWrapper _logsumexp() {
        return new DMWrapper(DM._logsumexp(this.dm));
    }

    @Override
    public DMVector horzsplit(IntegerVector offset) {
        return new DMVector(DM.horzsplit(this.dm, offset.getCasADiObject()));
    }

    @Override
    public DMVector vertsplit(IntegerVector offset) {
        return new DMVector(DM.vertsplit(this.dm, offset.getCasADiObject()));
    }

    @Override
    public DMVector diagsplit(IntegerVector offset1, IntegerVector offset2) {
        return new DMVector(DM.diagsplit(this.dm, offset1.getCasADiObject(), offset2.getCasADiObject()));
    }

    @Override
    public DMWrapper reshape(long nrow, long ncol) {
        return new DMWrapper(DM.reshape(this.dm, nrow, ncol));
    }

    @Override
    public DMWrapper reshape(Sparsity sp) {
        return new DMWrapper(DM.reshape(this.dm, sp));
    }

    @Override
    public DMWrapper sparsityCast(Sparsity sp) {
        return new DMWrapper(DM.sparsity_cast(this.dm, sp));
    }

    @Override
    public DMWrapper kron(DMWrapper y) {
        return new DMWrapper(DM.kron(this.dm, y.getCasADiObject()));
    }

    @Override
    public DMWrapper mtimes(DMWrapper y) {
        return new DMWrapper(DM.mtimes(this.dm, y.getCasADiObject()));
    }

    @Override
    public DMWrapper mac(DMWrapper y, DMWrapper z) {
        return new DMWrapper(DM.mac(this.dm, y.getCasADiObject(), z.getCasADiObject()));
    }

    public DMWrapper sparsify(double tol) {
        return new DMWrapper(DM.sparsify(this.dm, tol));
    }

    public DMWrapper sparsify() {
        return new DMWrapper(DM.sparsify(this.dm));
    }

    public void expand(DMWrapper weights, DMWrapper terms) {
        DM.expand(this.dm, weights.getCasADiObject(), terms.getCasADiObject());
    }

    public DMWrapper pwConst(DMWrapper tval, DMWrapper val) {
        return new DMWrapper(DM.pw_const(this.dm, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public DMWrapper pwLin(DMWrapper tval, DMWrapper val) {
        return new DMWrapper(DM.pw_lin(this.dm, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public DMWrapper heaviside() {
        return new DMWrapper(DM.heaviside(this.dm));
    }

    public DMWrapper rectangle() {
        return new DMWrapper(DM.rectangle(this.dm));
    }

    public DMWrapper triangle() {
        return new DMWrapper(DM.triangle(this.dm));
    }

    public DMWrapper ramp() {
        return new DMWrapper(DM.ramp(this.dm));
    }

    public DMWrapper gaussQuadrate(DMWrapper x, DMWrapper a, DMWrapper b, long order) {
        return new DMWrapper(DM.gauss_quadrature(this.dm, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order));
    }

    public DMWrapper gaussQuadrate(DMWrapper x, DMWrapper a, DMWrapper b) {
        return new DMWrapper(DM.gauss_quadrature(this.dm, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject()));
    }

    public DMWrapper gaussQuadrate(DMWrapper x, DMWrapper a, DMWrapper b, long order, DMWrapper w) {
        return new DMWrapper(DM.gauss_quadrature(this.dm, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order, w.getCasADiObject()));
    }

    @Override
    public BooleanVector whichDepends(DMWrapper var, long order, boolean tr) {
        return new BooleanVector(DM.which_depends(this.dm, var.getCasADiObject(), order, tr));
    }

    @Override
    public BooleanVector whichDepends(DMWrapper var, long order) {
        return new BooleanVector(DM.which_depends(this.dm, var.getCasADiObject(), order));
    }

    @Override
    public BooleanVector whichDepends(DMWrapper var) {
        return new BooleanVector(DM.which_depends(this.dm, var.getCasADiObject()));
    }

    public DMWrapper taylor(DMWrapper x, DMWrapper a, long order) {
        return new DMWrapper(DM.taylor(this.dm, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public DMWrapper mtaylor(DMWrapper x, DMWrapper a, long order) {
        return new DMWrapper(DM.mtaylor(this.dm, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public DMWrapper mtaylor(DMWrapper x, DMWrapper a, long order, IntegerVector orderContributions) {
        return new DMWrapper(DM.mtaylor(this.dm, x.getCasADiObject(), a.getCasADiObject(), order, orderContributions.getCasADiObject()));
    }

    public DMWrapper polyCoeff(DMWrapper x) {
        return new DMWrapper(DM.poly_coeff(this.dm, x.getCasADiObject()));
    }

    public DMWrapper polyRoots() {
        return new DMWrapper(DM.poly_roots(this.dm));
    }

    public DMWrapper eigSymbolic() {
        return new DMWrapper(DM.eig_symbolic(this.dm));
    }

    @Override
    public DMWrapper evalf() {
        return new DMWrapper(DM.evalf(this.dm));
    }

    public void qrSparse(DMWrapper V, DMWrapper R, DMWrapper beta, IntegerVector prinv, IntegerVector pc, boolean amd) {
        DM.qr_sparse(this.dm, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject(), amd);
    }

    public void qrSparse(DMWrapper V, DMWrapper R, DMWrapper beta, IntegerVector prinv, IntegerVector pc) {
        DM.qr_sparse(this.dm, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject());
    }

    public DMWrapper qrSolve(DMWrapper v, DMWrapper r, DMWrapper beta, IntegerVector prinv, IntegerVector pc, boolean tr) {
        return new DMWrapper(DM.qr_solve(this.dm, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject(), tr));
    }

    public DMWrapper qrSolve(DMWrapper v, DMWrapper r, DMWrapper beta, IntegerVector prinv, IntegerVector pc) {
        return new DMWrapper(DM.qr_solve(this.dm, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject()));
    }

    public void qr(DMWrapper Q, DMWrapper R) {
        DM.qr(this.dm, Q.getCasADiObject(), R.getCasADiObject());
    }

    public void ldl(DMWrapper D, DMWrapper LT, IntegerVector p, boolean amd) {
        DM.ldl(this.dm, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject(), amd);
    }

    public void ldl(DMWrapper D, DMWrapper LT, de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt p) {
        DM.ldl(this.dm, D.getCasADiObject(), LT.getCasADiObject(), p);
    }

    public DMWrapper ldlSolve(DMWrapper D, DMWrapper LT, de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt p) {
        return new DMWrapper(DM.ldl_solve(this.dm, D.getCasADiObject(), LT.getCasADiObject(), p));
    }

    public DMWrapper all() {
        return new DMWrapper(DM.all(this.dm));
    }

    public DMWrapper any() {
        return new DMWrapper(DM.any(this.dm));
    }

    public DMWrapper adj() {
        return new DMWrapper(DM.adj(this.dm));
    }

    public DMWrapper minor(long i, long j) {
        return new DMWrapper(DM.minor(this.dm, i, j));
    }

    public DMWrapper cofactor(long i, long j) {
        return new DMWrapper(DM.cofactor(this.dm, i, j));
    }

    public DMWrapper chol() {
        return new DMWrapper(DM.chol(this.dm));
    }

    public DMWrapper normInfMul(DMWrapper y) {
        return new DMWrapper(DM.norm_inf_mul(this.dm, y.getCasADiObject()));
    }

    @Override
    public DMWrapper printMe(DMWrapper y) {
        return new DMWrapper(this.dm.printme(y.getCasADiObject()));
    }

    @Override
    public DMWrapper T() {
        return new DMWrapper(this.dm.T());
    }

    @Override
    public void setMaxDepth(long eq_depth) {
        DM.set_max_depth(eq_depth);
    }

    @Override
    public void setMaxDepth() {
        DM.set_max_depth();
    }

    @Override
    public long getMaxDepth() {
        return DM.get_max_depth();
    }

    @Override
    public String typeName() {
        return DM.type_name();
    }

    public void printSplit(StringVectorCollection arg0, StringVectorCollection arg1) {
        this.dm.print_split(arg0.getCasADiObject(), arg1.getCasADiObject());
    }

    @Override
    public String toString(boolean more) {
        return this.dm.toString(more);
    }

    @Override
    public String toString() {
        return this.dm.toString();
    }

    public void clear() {
        this.dm.clear();
    }

    public void resize(long nrow, long ncol) {
        this.dm.resize(nrow, ncol);
    }

    public void reserve(long nnz) {
        this.dm.reserve(nnz);
    }

    public void reserve(long nnz, long ncol) {
        this.dm.reserve(nnz, ncol);
    }

    @Override
    public void erase(IntegerVector rr, IntegerVector cc, boolean ind1) {
        this.dm.erase(rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void erase(IntegerVector rr, IntegerVector cc) {
        this.dm.erase(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void erase(IntegerVector rr, boolean ind1) {
        this.dm.erase(rr.getCasADiObject(), ind1);
    }

    @Override
    public void erase(IntegerVector rr) {
        this.dm.erase(rr.getCasADiObject());
    }

    public void remove(IntegerVector rr, IntegerVector cc) {
        this.dm.remove(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void enlarge(long nrow, long ncol, IntegerVector rr, IntegerVector cc, boolean ind1) {
        this.dm.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void enlarge(long nrow, long ncol, IntegerVector rr, IntegerVector cc) {
        this.dm.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject());
    }

    public DoubleVector nonzeros() {
        return new DoubleVector(this.dm.nonzeros());
    }

    @Override
    public Sparsity sparsity() {
        return this.dm.sparsity();
    }

    public static DMWrapper inf(Sparsity sp) {
        return new DMWrapper(DM.inf(sp));
    }

    public static DMWrapper inf(long nrow, long ncol) {
        return new DMWrapper(DM.inf(nrow, ncol));
    }

    public static DMWrapper inf(long nrow) {
        return new DMWrapper(DM.inf(nrow));
    }

    public static DMWrapper inf() {
        return new DMWrapper(DM.inf());
    }

    public static DMWrapper nan(Sparsity sp) {
        return new DMWrapper(DM.nan(sp));
    }

    public static DMWrapper nan(long nrow, long ncol) {
        return new DMWrapper(DM.nan(nrow, ncol));
    }

    public static DMWrapper nan(long nrow) {
        return new DMWrapper(DM.nan(nrow));
    }

    public static DMWrapper nan() {
        return new DMWrapper(DM.nan());
    }

    public static DMWrapper eye(long size) {
        return new DMWrapper(DM.eye(size));
    }

    public long elementHash() {
        return this.dm.element_hash();
    }

    @Override
    public boolean isRegular() {
        return this.dm.is_regular();
    }

    public boolean isSmooth() {
        return this.dm.is_smooth();
    }

    public boolean isLeaf() {
        return this.dm.is_leaf();
    }

    @Override
    public boolean isCommutative() {
        return this.dm.is_commutative();
    }

    @Override
    public boolean isSymbolic() {
        return this.dm.is_symbolic();
    }

    @Override
    public boolean isValidInput() {
        return this.dm.is_valid_input();
    }

    @Override
    public boolean hasDuplicates() {
        return this.dm.has_duplicates();
    }

    @Override
    public void resetInput() {
        this.dm.reset_input();
    }

    @Override
    public boolean isConstant() {
        return this.dm.is_constant();
    }

    public boolean isInteger() {
        return this.dm.is_integer();
    }

    @Override
    public boolean isZero() {
        return this.dm.is_zero();
    }

    @Override
    public boolean isOne() {
        return this.dm.is_one();
    }

    @Override
    public boolean isMinusOne() {
        return this.dm.is_minus_one();
    }

    @Override
    public boolean isEye() {
        return this.dm.is_eye();
    }

    @Override
    public long op() {
        return this.dm.op();
    }

    @Override
    public boolean isOp(long op) {
        return this.dm.is_op(op);
    }

    public boolean hasZeros() {
        return this.dm.has_zeros();
    }

    public DoubleVector getNonzeros() {
        return new DoubleVector(this.dm.get_nonzeros());
    }

    public DoubleVector getElements() {
        return new DoubleVector(this.dm.get_elements());
    }

    @Override
    public String getName() {
        return this.dm.name();
    }

    @Override
    public DMWrapper dep(long ch) {
        return new DMWrapper(this.dm.dep(ch));
    }

    @Override
    public DMWrapper dep() {
        return new DMWrapper(this.dm.dep());
    }

    @Override
    public long nDep() {
        return this.dm.n_dep();
    }

    public void exportCode(String lang) {
        this.dm.export_code(lang);
    }

    @Override
    public Dict info() {
        return this.dm.info();
    }

    public String serialize() {
        return this.dm.serialize();
    }

    public DMWrapper deserialize(String s) {
        return new DMWrapper(DM.deserialize(s));
    }

    public void toFile(String filename, String format) {
        this.dm.to_file(filename, format);
    }

    public void toFile(String filename) {
        this.dm.to_file(filename);
    }

    @Override
    public DMWrapper _sym(String name, Sparsity sp) {
        return new DMWrapper(DM._sym(name, sp));
    }

    @Override
    public DMSubIndexWrapper at(int rr) {
        return new DMSubIndexWrapper(this.dm.at(rr));
    }

    @Override
    public DMSubMatrixWrapper at(int rr, int cc) {
        return new DMSubMatrixWrapper(this.dm.at(rr, cc));
    }

    @Override
    public void assign(DMWrapper other) {
        this.dm.assign(other.getCasADiObject());
    }

    @Override
    public DMVectorCollection blocksplit(IntegerVector vert_offset, IntegerVector horz_offset) {
        return new DMVectorCollection(DM.blocksplit(this.dm, vert_offset.getCasADiObject(), horz_offset.getCasADiObject()));
    }

    @Override
    public DMVectorCollection blocksplit(long vert_incr, long horz_incr) {
        return new DMVectorCollection(DM.blocksplit(this.dm, vert_incr, horz_incr));
    }

    @Override
    public DMWrapper vec() {
        return new DMWrapper(DM.vec(this.dm));
    }

    public DMWrapper repmat(long n, long m) {
        return new DMWrapper(DM.repmat(this.dm, n, m));
    }

    public DMWrapper repmat(long n) {
        return new DMWrapper(DM.repmat(this.dm, n));
    }

    @Override
    public DMVector vertsplit_n(long n) {
        return new DMVector(DM.vertsplit_n(this.dm, n));
    }

    @Override
    public long nnz() {
        return this.dm.nnz_();
    }

    @Override
    public long nnzLower() {
        return this.dm.nnz_lower_();
    }

    @Override
    public long nnzUpper() {
        return this.dm.nnz_upper_();
    }

    @Override
    public long nnzDiag() {
        return this.dm.nnz_diag();
    }

    @Override
    public long numel() {
        return this.dm.numel_();
    }

    @Override
    public long size1() {
        return this.dm.size1_();
    }

    @Override
    public long rows() {
        return this.dm.rows();
    }

    @Override
    public long size2() {
        return this.dm.size2_();
    }

    @Override
    public long columns() {
        return this.dm.columns();
    }

    @Override
    public String dim(boolean withNz) {
        return this.dm.dim_(withNz);
    }

    @Override
    public String dim() {
        return this.dm.dim_();
    }

    @Override
    public long size(long axis) {
        return this.dm.size_(axis);
    }

    @Override
    public boolean isEmpty(boolean both) {
        return this.dm.is_empty_(both);
    }

    @Override
    public boolean isEmpty() {
        return this.dm.is_empty_();
    }

    @Override
    public boolean isDense() {
        return this.dm.is_dense_();
    }

    @Override
    public boolean isScalar(boolean scalarAndDense) {
        return this.dm.is_scalar_(scalarAndDense);
    }

    @Override
    public boolean isScalar() {
        return this.dm.is_scalar_();
    }

    @Override
    public boolean isSquare() {
        return this.dm.is_square();
    }

    @Override
    public boolean isVector() {
        return this.dm.is_vector_();
    }

    @Override
    public boolean isRow() {
        return this.dm.is_row_();
    }

    @Override
    public boolean isColumn() {
        return this.dm.is_column_();
    }

    @Override
    public boolean isTriu() {
        return this.dm.is_triu_();
    }

    @Override
    public boolean isTril() {
        return this.dm.is_tril_();
    }

    @Override
    public IntegerVector getRow() {
        return new IntegerVector(this.dm.get_row());
    }

    @Override
    public IntegerVector getColInd() {
        return new IntegerVector(this.dm.get_colind());
    }

    @Override
    public long row(long el) {
        return this.dm.row_(el);
    }

    @Override
    public long colind(long col) {
        return this.dm.colind_(col);
    }

    @Override
    public DMWrapper interp1d(DoubleVector x, DoubleVector xq, String mode, boolean equidistant) {
        return new DMWrapper(DM.interp1d(x.getCasADiObject(), this.dm, xq.getCasADiObject(), mode, equidistant));
    }

    @Override
    public long sprank() {
        return DM.sprank(this.dm);
    }

    @Override
    public long norm0Mul(DMWrapper y) {
        return DM.norm_0_mul(this.dm, y.getCasADiObject());
    }

    @Override
    public DMWrapper tril(boolean includeDiagonal) {
        return new DMWrapper(DM.tril(this.dm, includeDiagonal));
    }

    @Override
    public DMWrapper tril() {
        return new DMWrapper(DM.tril(this.dm));
    }

    @Override
    public DMWrapper triu(boolean includeDiagonal) {
        return new DMWrapper(DM.triu(this.dm, includeDiagonal));
    }

    @Override
    public DMWrapper triu() {
        return new DMWrapper(DM.triu(this.dm));
    }

    @Override
    public DMWrapper sumsqr() {
        return new DMWrapper(DM.sumsqr(this.dm));
    }

    public DMWrapper linspace(DMWrapper b, long nsteps) {
        return new DMWrapper(DM.linspace(this.dm, b.getCasADiObject(), nsteps));
    }

    @Override
    public DMWrapper cross(DMWrapper b, long dim) {
        return new DMWrapper(DM.cross(this.dm, b.getCasADiObject(), dim));
    }

    @Override
    public DMWrapper cross(DMWrapper b) {
        return new DMWrapper(DM.cross(this.dm, b.getCasADiObject()));
    }

    @Override
    public DMWrapper skew() {
        return new DMWrapper(DM.skew(this.dm));
    }

    @Override
    public DMWrapper invSkew() {
        return new DMWrapper(DM.inv_skew(this.dm));
    }

    @Override
    public DMWrapper tril2symm() {
        return new DMWrapper(DM.tril2symm(this.dm));
    }

    @Override
    public DMWrapper triu2symm() {
        return new DMWrapper(DM.triu2symm(this.dm));
    }

    public DMWrapper repsum(long n, long m) {
        return new DMWrapper(DM.repsum(this.dm, n, m));
    }

    public DMWrapper repsum(long n) {
        return new DMWrapper(DM.repsum(this.dm, n));
    }

    @Override
    public DMWrapper diff(long n, long axis) {
        return new DMWrapper(DM.diff(this.dm, n, axis));
    }

    @Override
    public DMWrapper diff(long n) {
        return new DMWrapper(DM.diff(this.dm, n));
    }

    @Override
    public DMWrapper diff() {
        return new DMWrapper(DM.diff(this.dm));
    }

    @Override
    public boolean isLinear(DMWrapper var) {
        return DM.is_linear(this.dm, var.getCasADiObject());
    }

    @Override
    public boolean isQuadratic(DMWrapper var) {
        return DM.is_quadratic(this.dm, var.getCasADiObject());
    }

    @Override
    public void quadraticCoeff(DMWrapper var, DMWrapper A, DMWrapper b, DMWrapper c, boolean check) {
        DM.quadratic_coeff(this.dm, var.getCasADiObject(), A.getCasADiObject(),
                b.getCasADiObject(), c.getCasADiObject(), check);
    }

    @Override
    public void linearCoeff(DMWrapper var, DMWrapper A, DMWrapper b, boolean check) {
        DM.linear_coeff(this.dm, var.getCasADiObject(), A.getCasADiObject(), b.getCasADiObject(), check);
    }

    @Override
    public DMWrapper bilin(DMWrapper x, DMWrapper y) {
        return new DMWrapper(DM.bilin(this.dm, x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public DMWrapper rank1(DMWrapper alpha, DMWrapper x, DMWrapper y) {
        return new DMWrapper(DM.rank1(this.dm, alpha.getCasADiObject(), x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public DMWrapper logsumexp() {
        return new DMWrapper(DM.logsumexp(this.dm));
    }

    @Override
    public DMWrapper jtimes(DMWrapper arg, DMWrapper v, boolean tr, Dict opts) {
        return new DMWrapper(DM.jtimes_(this.dm, arg.getCasADiObject(), v.getCasADiObject(), tr, opts));
    }

    @Override
    public DMWrapper jtimes(DMWrapper arg, DMWrapper v, boolean tr) {
        return new DMWrapper(DM.jtimes_(this.dm, arg.getCasADiObject(), v.getCasADiObject(), tr));
    }

    @Override
    public DMWrapper jtimes(DMWrapper arg, DMWrapper v) {
        return new DMWrapper(DM.jtimes_(this.dm, arg.getCasADiObject(), v.getCasADiObject()));
    }

    @Override
    public DMWrapper gradient(DMWrapper arg, Dict opts) {
        return new DMWrapper(DM.gradient(this.dm, arg.getCasADiObject(), opts));
    }

    @Override
    public DMWrapper gradient(DMWrapper arg) {
        return new DMWrapper(DM.gradient(this.dm, arg.getCasADiObject()));
    }

    @Override
    public DMWrapper tangent(DMWrapper arg, Dict opts) {
        return new DMWrapper(DM.tangent(this.dm, arg.getCasADiObject(), opts));
    }

    @Override
    public DMWrapper tangent(DMWrapper arg) {
        return new DMWrapper(DM.tangent(this.dm, arg.getCasADiObject()));
    }

    @Override
    public DMWrapper linearize(DMWrapper x, DMWrapper x0, Dict opts) {
        return new DMWrapper(DM.linearize(this.dm, x.getCasADiObject(), x0.getCasADiObject(), opts));
    }

    @Override
    public DMWrapper linearize(DMWrapper x, DMWrapper x0) {
        return new DMWrapper(DM.linearize(this.dm, x.getCasADiObject(), x0.getCasADiObject()));
    }

    @Override
    public DMWrapper mpower(DMWrapper y) {
        return new DMWrapper(DM.mpower(this.dm, y.getCasADiObject()));
    }

    @Override
    public DMWrapper soc(DMWrapper y) {
        return new DMWrapper(DM.soc(this.dm, y.getCasADiObject()));
    }

    public static DMWrapper sym(String name, long nrow, long ncol) {
        return new DMWrapper(DM.sym(name, nrow, ncol));
    }

    public static DMWrapper sym(String name, long nrow) {
        return new DMWrapper(DM.sym(name, nrow));
    }

    public static DMWrapper sym(String name) {
        return new DMWrapper(DM.sym(name));
    }

    public static DMWrapper sym(String name, Sparsity sp) {
        return new DMWrapper(DM.sym(name, sp));
    }

    public static DMVector sym(String name, Sparsity sp, long p) {
        return new DMVector(DM.sym(name, sp, p));
    }

    public static DMVector sym(String name, long nrow, long ncol, long p) {
        return new DMVector(DM.sym(name, nrow, ncol, p));
    }

    public static DMVectorCollection sym(String name, Sparsity sp, long p, long r) {
        return new DMVectorCollection(DM.sym(name, sp, p, r));
    }

    public static DMVectorCollection sym(String name, long nrow, long ncol, long p, long r) {
        return new DMVectorCollection(DM.sym(name, nrow, ncol, p, r));
    }

    public static DMWrapper zeros(long nrow, long ncol) {
        return new DMWrapper(DM.zeros(nrow, ncol));
    }

    public static DMWrapper zeros(long nrow) {
        return new DMWrapper(DM.zeros(nrow));
    }

    public static DMWrapper zeros() {
        return new DMWrapper(DM.zeros());
    }

    public static DMWrapper zeros(Sparsity sp) {
        return new DMWrapper(DM.zeros(sp));
    }

    public static DMWrapper ones(long nrow, long ncol) {
        return new DMWrapper(DM.ones(nrow, ncol));
    }

    public static DMWrapper ones(long nrow) {
        return new DMWrapper(DM.ones(nrow));
    }

    public static DMWrapper ones() {
        return new DMWrapper(DM.ones());
    }

    public static DMWrapper ones(Sparsity sp) {
        return new DMWrapper(DM.ones(sp));
    }

    @Override
    public DMWrapper add(DMWrapper other) {
        DM result = DM.plus(this.dm, other.dm);
        return new DMWrapper(result);
    }

    @Override
    public <T extends Number> DMWrapper add(T number) {
        double other = number.doubleValue();
        DMWrapper summand = DMWrapper.fromValue(other);
        DM result = DM.plus(this.dm, summand.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper subtract(DMWrapper other) {
        DM result = DM.minus(this.dm, other.dm);
        return new DMWrapper(result);
    }

    @Override
    public <T extends Number> DMWrapper subtract(T number) {
        double other = number.doubleValue();
        DMWrapper subtrahend = DMWrapper.fromValue(other);
        DM result = DM.minus(this.dm, subtrahend.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper multiply(DMWrapper other) {
        DM result = DM.times(this.dm, other.dm);
        return new DMWrapper(result);
    }

    @Override
    public <T extends Number> DMWrapper multiply(T number) {
        double other = number.doubleValue();
        DMWrapper multiplicand = DMWrapper.fromValue(other);
        DM result = DM.times(this.dm, multiplicand.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper divide(DMWrapper other) {
        DM result = DM.rdivide(this.dm, other.dm);
        return new DMWrapper(result);
    }

    @Override
    public <T extends Number> DMWrapper divide(T number) {
        double other = number.doubleValue();
        if (other == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        DMWrapper divisor = DMWrapper.fromValue(other);
        DM result = DM.mtimes(this.dm, divisor.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper lt(DMWrapper y) {
        return new DMWrapper(DM.lt(this.dm, y.dm));
    }

    @Override
    public <T extends Number> DMWrapper lt(T number) {
        double other = number.doubleValue();
        DMWrapper threshold = DMWrapper.fromValue(other);
        DM result = DM.lt(this.dm, threshold.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper le(DMWrapper y) {
        return new DMWrapper(DM.le(this.dm, y.dm));
    }

    @Override
    public <T extends Number> DMWrapper le(T number) {
        double other = number.doubleValue();
        DMWrapper threshold = DMWrapper.fromValue(other);
        DM result = DM.le(this.dm, threshold.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper gt(DMWrapper y) {
        return new DMWrapper(DM.gt(this.dm, y.dm));
    }

    @Override
    public <T extends Number> DMWrapper gt(T number) {
        double other = number.doubleValue();
        DMWrapper threshold = DMWrapper.fromValue(other);
        DM result = DM.gt(this.dm, threshold.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper ge(DMWrapper y) {
        return new DMWrapper(DM.ge(this.dm, y.dm));
    }

    @Override
    public <T extends Number> DMWrapper ge(T number) {
        double other = number.doubleValue();
        DMWrapper threshold = DMWrapper.fromValue(other);
        DM result = DM.ge(this.dm, threshold.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper eq(DMWrapper y) {
        return new DMWrapper(DM.eq(this.dm, y.dm));
    }

    @Override
    public <T extends Number> DMWrapper eq(T number) {
        double other = number.doubleValue();
        DMWrapper value = DMWrapper.fromValue(other);
        DM result = DM.eq(this.dm, value.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper ne(DMWrapper y) {
        return new DMWrapper(DM.ne(this.dm, y.dm));
    }

    @Override
    public <T extends Number> DMWrapper ne(T number) {
        double other = number.doubleValue();
        DMWrapper value = DMWrapper.fromValue(other);
        DM result = DM.ne(this.dm, value.getCasADiObject());
        return new DMWrapper(result);
    }

    @Override
    public DMWrapper logicAnd(DMWrapper y) {
        return new DMWrapper(DM.logic_and(this.dm, y.dm));
    }

    @Override
    public DMWrapper logicOr(DMWrapper y) {
        return new DMWrapper(DM.logic_or(this.dm, y.dm));
    }

    @Override
    public DMWrapper logicNot() {
        return new DMWrapper(DM.logic_not(this.dm));
    }

    @Override
    public DMWrapper abs() {
        return new DMWrapper(DM.abs(this.dm));
    }

    @Override
    public DMWrapper sqrt() {
        return new DMWrapper(DM.sqrt(this.dm));
    }

    @Override
    public DMWrapper sq() {
        return new DMWrapper(DM.sq(this.dm));
    }

    @Override
    public DMWrapper sin() {
        return new DMWrapper(DM.sin(this.dm));
    }

    @Override
    public DMWrapper cos() {
        return new DMWrapper(DM.cos(this.dm));
    }

    @Override
    public DMWrapper tan() {
        return new DMWrapper(DM.tan(this.dm));
    }

    @Override
    public DMWrapper atan() {
        return new DMWrapper(DM.atan(this.dm));
    }

    @Override
    public DMWrapper asin() {
        return new DMWrapper(DM.asin(this.dm));
    }

    @Override
    public DMWrapper acos() {
        return new DMWrapper(DM.acos(this.dm));
    }

    @Override
    public DMWrapper tanh() {
        return new DMWrapper(DM.tanh(this.dm));
    }

    @Override
    public DMWrapper sinh() {
        return new DMWrapper(DM.sinh(this.dm));
    }

    @Override
    public DMWrapper cosh() {
        return new DMWrapper(DM.cosh(this.dm));
    }

    @Override
    public DMWrapper atanh() {
        return new DMWrapper(DM.atanh(this.dm));
    }

    @Override
    public DMWrapper asinh() {
        return new DMWrapper(DM.asinh(this.dm));
    }

    @Override
    public DMWrapper acosh() {
        return new DMWrapper(DM.acosh(this.dm));
    }

    @Override
    public DMWrapper exp() {
        return new DMWrapper(DM.exp(this.dm));
    }

    @Override
    public DMWrapper log() {
        return new DMWrapper(DM.log(this.dm));
    }

    @Override
    public DMWrapper log10() {
        return new DMWrapper(DM.log10(this.dm));
    }

    @Override
    public DMWrapper log1p() {
        return new DMWrapper(DM.log1p(this.dm));
    }

    @Override
    public DMWrapper expm1() {
        return new DMWrapper(DM.expm1(this.dm));
    }

    @Override
    public DMWrapper floor() {
        return new DMWrapper(DM.floor(this.dm));
    }

    @Override
    public DMWrapper ceil() {
        return new DMWrapper(DM.ceil(this.dm));
    }

    @Override
    public DMWrapper erf() {
        return new DMWrapper(DM.erf(this.dm));
    }

    @Override
    public DMWrapper erfinv() {
        return new DMWrapper(DM.erfinv(this.dm));
    }

    @Override
    public DMWrapper sign() {
        return new DMWrapper(DM.sign(this.dm));
    }

    @Override
    public DMWrapper pow(DMWrapper other) {
        return new DMWrapper(DM.pow(this.dm, other.dm));
    }

    @Override
    public <T extends Number> DMWrapper pow(T exponent) {
        double exp = exponent.doubleValue();
        DM exponentDM = new DM(exp);
        return new DMWrapper(DM.pow(this.dm, exponentDM));
    }

    @Override
    public DMWrapper mod(DMWrapper other) {
        return new DMWrapper(DM.mod(this.dm, other.dm));
    }

    @Override
    public DMWrapper remainder(DMWrapper other) {
        return new DMWrapper(DM.remainder(this.dm, other.dm));
    }

    @Override
    public DMWrapper atan2(DMWrapper other) {
        return new DMWrapper(DM.atan2(this.dm, other.dm));
    }

    @Override
    public DMWrapper ifElseZero(DMWrapper other) {
        return new DMWrapper(DM.if_else_zero(this.dm, other.dm));
    }

    @Override
    public DMWrapper fmin(DMWrapper other) {
        return new DMWrapper(DM.fmin(this.dm, other.dm));
    }

    @Override
    public DMWrapper fmax(DMWrapper other) {
        return new DMWrapper(DM.fmax(this.dm, other.dm));
    }

    @Override
    public DMWrapper copysign(DMWrapper other) {
        return new DMWrapper(DM.copysign(this.dm, other.dm));
    }

    @Override
    public DMWrapper constpow(DMWrapper other) {
        return new DMWrapper(DM.constpow(this.dm, other.dm));
    }

    @Override
    public DMWrapper hypot(DMWrapper other) {
        return new DMWrapper(DM.hypot(this.dm, other.dm));
    }

    public DM getCasADiObject() {
        return this.dm;
    }

}
