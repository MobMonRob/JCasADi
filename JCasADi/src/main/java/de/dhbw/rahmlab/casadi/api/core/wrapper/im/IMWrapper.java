package de.dhbw.rahmlab.casadi.api.core.wrapper.im;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.bool.BooleanVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.index.IndexSlice;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.str.StringVector;
import de.dhbw.rahmlab.casadi.impl.casadi.IM;

import java.util.Arrays;
import java.util.List;

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
        this.id = m.getId();
    }

    public IMWrapper(long nrow, long ncol) {
        this.im = new IM(nrow, ncol);
    }

    public IMWrapper(SparsityWrapper sp) {
        this.im = new IM(sp.getCasADiObject());
    }

    public IMWrapper(SparsityWrapper sp, IMWrapper d) {
        this.im = new IM(sp.getCasADiObject(), d.getCasADiObject());
    }

    public IMWrapper(double value) {
        this.im = new IM(value);
    }

    public IMWrapper(Number value) {
        this.im = new IM(value.longValue());
    }

    public IMWrapper(NumberWrapper value) {
        this.im = new IM(value.getDoubleValue());
    }

    public IMWrapper(DoubleVectorCollection m) {
        this.im = new IM(m.getCasADiObject());
    }

    public IMWrapper(DoubleVector... m) {
        DoubleVectorCollection collection = new DoubleVectorCollection();
        collection.addAll(Arrays.asList(m));
        this.im = new IM(collection.getCasADiObject());
    }

    public IMWrapper(Iterable<DoubleVector> m) {
        DoubleVectorCollection collection = new DoubleVectorCollection();
        m.forEach(collection::add);
        this.im = new IM(collection.getCasADiObject());
    }

    public IMWrapper(CasADiIntVector x) {
        this.im = new IM(x.getCasADiObject());
    }

    public IMWrapper(Long... x) {
        CasADiIntVector vector = new CasADiIntVector();
        vector.addAll(List.of(x));
        this.im = new IM(vector.getCasADiObject());
    }

    public IMWrapper(Number... x) {
        CasADiIntVector vector = new CasADiIntVector();
        Arrays.stream(x).forEach(element -> vector.add(element.longValue()));
        this.im = new IM(vector.getCasADiObject());
    }

    public IMWrapper(NumberWrapper... x) {
        CasADiIntVector vector = new CasADiIntVector();
        Arrays.stream(x).forEach(element -> vector.add(element.getLongValue()));
        this.im = new IM(vector.getCasADiObject());
    }

    public IMWrapper(SparsityWrapper sp, long val, boolean dummy) {
        this.im = new IM(sp.getCasADiObject(), val, dummy);
    }

    public IMWrapper(SparsityWrapper sp, CasADiIntVector val, boolean dummy) {
        this.im = new IM(sp.getCasADiObject(), val.getCasADiObject(), dummy);
    }

    public static IMWrapper inf(SparsityWrapper sp) {
        return new IMWrapper(ImStatic.inf(sp.getCasADiObject()));
    }

    public static IMWrapper inf(long nrow, long ncol) {
        return new IMWrapper(ImStatic.inf(nrow, ncol));
    }

    public static IMWrapper inf(long nrow) {
        return new IMWrapper(ImStatic.inf(nrow));
    }

    public static IMWrapper inf() {
        return new IMWrapper(ImStatic.inf());
    }

    public static IMWrapper nan(SparsityWrapper sp) {
        return new IMWrapper(ImStatic.nan(sp.getCasADiObject()));
    }

    public static IMWrapper nan(long nrow, long ncol) {
        return new IMWrapper(ImStatic.nan(nrow, ncol));
    }

    public static IMWrapper nan(long nrow) {
        return new IMWrapper(ImStatic.nan(nrow));
    }

    public static IMWrapper nan() {
        return new IMWrapper(ImStatic.nan());
    }

    public static IMWrapper eye(long n) {
        return new IMWrapper(ImStatic.eye(n));
    }

    public static IMWrapper rand(long nrow, long ncol) {
        return new IMWrapper(ImStatic.rand(nrow, ncol));
    }

    public static IMWrapper rand(long nrow) {
        return new IMWrapper(ImStatic.rand(nrow));
    }

    public static IMWrapper rand() {
        return new IMWrapper(ImStatic.rand());
    }

    public static IMWrapper rand(SparsityWrapper sp) {
        return new IMWrapper(ImStatic.rand(sp.getCasADiObject()));
    }

    public static IMWrapper sym(String name, long nrow, long ncol) {
        return new IMWrapper(ImStatic.sym(name, nrow, ncol));
    }

    public static IMWrapper sym(String name, long nrow) {
        return new IMWrapper(ImStatic.sym(name, nrow));
    }

    public static IMWrapper sym(String name) {
        return new IMWrapper(ImStatic.sym(name));
    }

    public static IMWrapper sym(String name, SparsityWrapper sp) {
        return new IMWrapper(ImStatic.sym(name, sp.getCasADiObject()));
    }

    public static IMWrapper zeros(long nrow, long ncol) {
        return new IMWrapper(ImStatic.zeros(nrow, ncol));
    }

    public static IMWrapper zeros(long nrow) {
        return new IMWrapper(ImStatic.zeros(nrow));
    }

    public static IMWrapper zeros() {
        return new IMWrapper(ImStatic.zeros());
    }

    public static IMWrapper zeros(SparsityWrapper sp) {
        return new IMWrapper(ImStatic.zeros(sp.getCasADiObject()));
    }

    public static IMWrapper ones(long nrow, long ncol) {
        return new IMWrapper(ImStatic.ones(nrow, ncol));
    }

    public static IMWrapper ones(long nrow) {
        return new IMWrapper(ImStatic.ones(nrow));
    }

    public static IMWrapper ones() {
        return new IMWrapper(ImStatic.ones());
    }

    public static IMWrapper ones(SparsityWrapper sp) {
        return new IMWrapper(ImStatic.ones(sp.getCasADiObject()));
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
    public IMWrapper get(boolean ind1, IndexSlice rr) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, IMWrapper rr) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, SparsityWrapper sp) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, sp.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(String sliceDefinitionRR, String sliceDefinitionCC) {
        IndexSlice indexSliceRR = IndexSlice.slicingSyntax(sliceDefinitionRR);
        IndexSlice indexSliceCC = IndexSlice.slicingSyntax(sliceDefinitionCC);
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), true, indexSliceRR.getCasADiObject(), indexSliceCC.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, IndexSlice rr, IndexSlice cc) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, IndexSlice rr, IMWrapper cc) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper get(boolean ind1, IMWrapper rr, IndexSlice cc) {
        IMWrapper output = new IMWrapper();
        this.im.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
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
    public void set(IMWrapper m, boolean ind1, IndexSlice rr) {
        this.im.set(m.getCasADiObject(), ind1, rr.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, boolean ind1, IMWrapper rr) {
        this.im.set(m.getCasADiObject(), ind1, rr.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, boolean ind1, SparsityWrapper sp) {
        this.im.set(m.getCasADiObject(), ind1, sp.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, String sliceDefinitionRR, String sliceDefinitionCC) {
        IndexSlice indexSliceRR = IndexSlice.slicingSyntax(sliceDefinitionRR);
        IndexSlice indexSliceCC = IndexSlice.slicingSyntax(sliceDefinitionCC);
        this.im.set(m.getCasADiObject(), true, indexSliceRR.getCasADiObject(), indexSliceCC.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, boolean ind1, IndexSlice rr, IndexSlice cc) {
        this.im.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, boolean ind1, IndexSlice rr, IMWrapper cc) {
        this.im.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void set(IMWrapper m, boolean ind1, IMWrapper rr, IndexSlice cc) {
        this.im.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
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
    public IMWrapper getNZ(boolean ind1, IndexSlice k) {
        IMWrapper output = new IMWrapper();
        this.im.get_nz(output.getCasADiObject(), ind1, k.getCasADiObject());
        return output;
    }

    @Override
    public IMWrapper getNZ(boolean ind1, IMWrapper k) {
        IMWrapper output = new IMWrapper();
        this.im.get_nz(output.getCasADiObject(), ind1, k.getCasADiObject());
        return output;
    }

    @Override
    public void setNZ(IMWrapper m, boolean ind1, IndexSlice k) {
        this.im.set_nz(m.getCasADiObject(), ind1, k.getCasADiObject());
    }

    @Override
    public void setNZ(IMWrapper m, boolean ind1, IMWrapper k) {
        this.im.set_nz(m.getCasADiObject(), ind1, k.getCasADiObject());
    }

    @Override
    public IMWrapper binary(long op, IMWrapper y) {
        return new IMWrapper(ImStatic.binary(op, this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper unary(long op) {
        return new IMWrapper(ImStatic.unary(op, this.im));
    }

    @Override
    public boolean isEqual(IMWrapper other, long depth) {
        return ImStatic.is_equal(this.im, other.getCasADiObject(), depth);
    }

    @Override
    public boolean isEqual(IMWrapper other) {
        return ImStatic.is_equal(this.im, other.getCasADiObject());
    }

    @Override
    public IMWrapper mmin() {
        return new IMWrapper(ImStatic.mmin(this.im));
    }

    @Override
    public IMWrapper mmax() {
        return new IMWrapper(ImStatic.mmax(this.im));
    }

    @Override
    public IMWrapper simplify() {
        return new IMWrapper(ImStatic.simplify(this.im));
    }

    @Override
    public IMWrapper jacobian(IMWrapper x, Dictionary opts) {
        return new IMWrapper(ImStatic.jacobian(this.im, x.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public IMWrapper jacobian(IMWrapper x) {
        return new IMWrapper(ImStatic.jacobian(this.im, x.getCasADiObject()));
    }

    @Override
    public SparsityWrapper jacobianSparsity(IMWrapper x) {
        return new SparsityWrapper(ImStatic.jacobian_sparsity(this.im, x.getCasADiObject()));
    }

    @Override
    public IMWrapper hessian(IMWrapper x, Dictionary opts) {
        return new IMWrapper(ImStatic.hessian(this.im, x.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public IMWrapper hessian(IMWrapper x) {
        return new IMWrapper(ImStatic.hessian(this.im, x.getCasADiObject()));
    }

    @Override
    public IMWrapper hessian(IMWrapper x, IMWrapper g, Dictionary opts) {
        return new IMWrapper(ImStatic.hessian(this.im, x.getCasADiObject(), g.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public IMWrapper hessian(IMWrapper x, IMWrapper g) {
        return new IMWrapper(ImStatic.hessian(this.im, x.getCasADiObject()));
    }

    @Override
    public IMWrapper substitute(IMWrapper v, IMWrapper vdef) {
        return new IMWrapper(ImStatic.substitute(this.im, v.getCasADiObject(), vdef.getCasADiObject()));
    }

    @Override
    public IMWrapper pinv() {
        return new IMWrapper(ImStatic.pinv(this.im));
    }

    @Override
    public IMWrapper pinv(String lsolver, Dictionary dict) {
        return new IMWrapper(ImStatic.pinv(this.im, lsolver, dict.getCasADiObject()));
    }

    @Override
    public IMWrapper expmConst(IMWrapper imWrapper) {
        return new IMWrapper(ImStatic.expm_const(this.im, imWrapper.getCasADiObject()));
    }

    @Override
    public IMWrapper expm() {
        return new IMWrapper(ImStatic.expm(this.im));
    }

    @Override
    public IMWrapper solve(IMWrapper b) {
        return new IMWrapper(ImStatic.solve(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper solve(IMWrapper b, String lsolver, Dictionary dict) {
        return new IMWrapper(ImStatic.solve(this.im, b.getCasADiObject(), lsolver, dict.getCasADiObject()));
    }

    @Override
    public IMWrapper inv() {
        return new IMWrapper(ImStatic.inv(this.im));
    }

    @Override
    public IMWrapper inv(String lsolver, Dictionary dict) {
        return new IMWrapper(ImStatic.inv(this.im, lsolver, dict.getCasADiObject()));
    }

    @Override
    public long nNodes() {
        return ImStatic.n_nodes(this.im);
    }

    @Override
    public String printOperator(StringVector args) {
        return ImStatic.print_operator(this.im, args.getCasADiObject());
    }

    @Override
    public boolean dependsOn(IMWrapper arg) {
        return ImStatic.depends_on(this.im, arg.getCasADiObject());
    }

    @Override
    public IMWrapper mrdivide(IMWrapper b) {
        return new IMWrapper(ImStatic.mrdivide(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper mldivide(IMWrapper b) {
        return new IMWrapper(ImStatic.mldivide(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper det() {
        return new IMWrapper(ImStatic.det(this.im));
    }

    @Override
    public IMWrapper invMinor() {
        return new IMWrapper(ImStatic.inv_minor(this.im));
    }

    @Override
    public IMWrapper trace() {
        return new IMWrapper(ImStatic.trace(this.im));
    }

    @Override
    public IMWrapper norm1() {
        return new IMWrapper(ImStatic.norm_1(this.im));
    }

    @Override
    public IMWrapper norm2() {
        return new IMWrapper(ImStatic.norm_2(this.im));
    }

    @Override
    public IMWrapper normFro() {
        return new IMWrapper(ImStatic.norm_fro(this.im));
    }

    @Override
    public IMWrapper normInf() {
        return new IMWrapper(ImStatic.norm_inf(this.im));
    }

    @Override
    public IMWrapper sum2() {
        return new IMWrapper(ImStatic.sum2(this.im));
    }

    @Override
    public IMWrapper sum1() {
        return new IMWrapper(ImStatic.sum1(this.im));
    }

    @Override
    public IMWrapper dot(IMWrapper y) {
        return new IMWrapper(ImStatic.dot(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper nullspace() {
        return new IMWrapper(ImStatic.nullspace(this.im));
    }

    @Override
    public IMWrapper diag() {
        return new IMWrapper(ImStatic.diag(this.im));
    }

    @Override
    public IMWrapper unite(IMWrapper B) {
        return new IMWrapper(ImStatic.unite(this.im, B.getCasADiObject()));
    }


    @Override
    public IMWrapper project(SparsityWrapper sp, boolean intersect) {
        return new IMWrapper(ImStatic.project(this.im, sp.getCasADiObject(), intersect));
    }

    @Override
    public IMWrapper project(SparsityWrapper sp) {
        return new IMWrapper(ImStatic.project(this.im, sp.getCasADiObject()));
    }

    @Override
    public IMWrapper polyval(IMWrapper x) {
        return new IMWrapper(ImStatic.polyval(this.im, x.getCasADiObject()));
    }

    @Override
    public IMWrapper densify(IMWrapper val) {
        return new IMWrapper(ImStatic.densify(this.im, val.getCasADiObject()));
    }

    @Override
    public IMWrapper densify() {
        return new IMWrapper(ImStatic.densify(this.im));
    }

    @Override
    public IMWrapper einstein(IMWrapper other, IMWrapper C, CasADiIntVector dim_a, CasADiIntVector dim_b,
                              CasADiIntVector dim_c, CasADiIntVector a, CasADiIntVector b, CasADiIntVector c) {
        return new IMWrapper(ImStatic.einstein(this.im, other.getCasADiObject(), C.getCasADiObject(),
                dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public IMWrapper einstein(IMWrapper other, CasADiIntVector dim_a, CasADiIntVector dim_b, CasADiIntVector dim_c,
                              CasADiIntVector a, CasADiIntVector b, CasADiIntVector c) {
        return new IMWrapper(ImStatic.einstein(this.im, other.getCasADiObject(), dim_a.getCasADiObject(),
                dim_b.getCasADiObject(), dim_c.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public IMWrapper cumsum(long axis) {
        return new IMWrapper(ImStatic.cumsum(this.im, axis));
    }

    @Override
    public IMWrapper cumsum() {
        return new IMWrapper(ImStatic.cumsum(this.im));
    }

    @Override
    public IMWrapper reshape(long nrow, long ncol) {
        return new IMWrapper(ImStatic.reshape(this.im, nrow, ncol));
    }

    @Override
    public IMWrapper reshape(SparsityWrapper sp) {
        return new IMWrapper(ImStatic.reshape(this.im, sp.getCasADiObject()));
    }

    @Override
    public IMWrapper sparsityCast(SparsityWrapper sp) {
        return new IMWrapper(ImStatic.sparsity_cast(this.im, sp.getCasADiObject()));
    }

    @Override
    public IMWrapper kron(IMWrapper b) {
        return new IMWrapper(ImStatic.kron(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper mtimes(IMWrapper other) {
        return new IMWrapper(ImStatic.mtimes(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper mac(IMWrapper y, IMWrapper z) {
        return new IMWrapper(ImStatic.mac(this.im, y.getCasADiObject(), z.getCasADiObject()));
    }

    public IMWrapper sparsify(double tol) {
        return new IMWrapper(ImStatic.sparsify(this.im, tol));
    }

    public IMWrapper sparsify() {
        return new IMWrapper(ImStatic.sparsify(this.im));
    }

    public void expand(IMWrapper weights, IMWrapper terms) {
        ImStatic.expand(this.im, weights.getCasADiObject(), terms.getCasADiObject());
    }

    public IMWrapper pwConst(IMWrapper tval, IMWrapper val) {
        return new IMWrapper(ImStatic.pw_const(this.im, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public IMWrapper pwLin(IMWrapper tval, IMWrapper val) {
        return new IMWrapper(ImStatic.pw_lin(this.im, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public IMWrapper heaviside() {
        return new IMWrapper(ImStatic.heaviside(this.im));
    }

    public IMWrapper rectangle() {
        return new IMWrapper(ImStatic.rectangle(this.im));
    }

    public IMWrapper triangle() {
        return new IMWrapper(ImStatic.triangle(this.im));
    }

    public IMWrapper ramp() {
        return new IMWrapper(ImStatic.ramp(this.im));
    }

    public IMWrapper gaussQuadrate(IMWrapper x, IMWrapper a, IMWrapper b, long order) {
        return new IMWrapper(ImStatic.gauss_quadrature(this.im, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order));
    }

    public IMWrapper gaussQuadrate(IMWrapper x, IMWrapper a, IMWrapper b) {
        return new IMWrapper(ImStatic.gauss_quadrature(this.im, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject()));
    }

    public IMWrapper gaussQuadrate(IMWrapper x, IMWrapper a, IMWrapper b, long order, IMWrapper w) {
        return new IMWrapper(ImStatic.gauss_quadrature(this.im, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order, w.getCasADiObject()));
    }

    @Override
    public BooleanVector whichDepends(IMWrapper var, long order, boolean tr) {
        return new BooleanVector(ImStatic.which_depends(this.im, var.getCasADiObject(), order, tr));
    }

    @Override
    public BooleanVector whichDepends(IMWrapper var, long order) {
        return new BooleanVector(ImStatic.which_depends(this.im, var.getCasADiObject(), order));
    }

    @Override
    public BooleanVector whichDepends(IMWrapper var) {
        return new BooleanVector(ImStatic.which_depends(this.im, var.getCasADiObject()));
    }

    public IMWrapper taylor(IMWrapper x, IMWrapper a, long order) {
        return new IMWrapper(ImStatic.taylor(this.im, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public IMWrapper mtaylor(IMWrapper x, IMWrapper a, long order) {
        return new IMWrapper(ImStatic.mtaylor(this.im, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public IMWrapper mtaylor(IMWrapper x, IMWrapper a, long order, CasADiIntVector orderContributions) {
        return new IMWrapper(ImStatic.mtaylor(this.im, x.getCasADiObject(), a.getCasADiObject(), order, orderContributions.getCasADiObject()));
    }

    public IMWrapper polyCoeff(IMWrapper x) {
        return new IMWrapper(ImStatic.poly_coeff(this.im, x.getCasADiObject()));
    }

    public IMWrapper polyRoots() {
        return new IMWrapper(ImStatic.poly_roots(this.im));
    }

    public IMWrapper eigSymbolic() {
        return new IMWrapper(ImStatic.eig_symbolic(this.im));
    }

    @Override
    public DMWrapper evalf() {
        return new DMWrapper(ImStatic.evalf(this.im));
    }

    public void qrSparse(IMWrapper V, IMWrapper R, IMWrapper beta, CasADiIntVector prinv, CasADiIntVector pc, boolean amd) {
        ImStatic.qr_sparse(this.im, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(),
                prinv.getCasADiObject(), pc.getCasADiObject(), amd);
    }

    public void qrSparse(IMWrapper V, IMWrapper R, IMWrapper beta, CasADiIntVector prinv, CasADiIntVector pc) {
        ImStatic.qr_sparse(this.im, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(),
                prinv.getCasADiObject(), pc.getCasADiObject());
    }

    public IMWrapper qrSolve(IMWrapper v, IMWrapper r, IMWrapper beta, CasADiIntVector prinv, CasADiIntVector pc, boolean tr) {
        return new IMWrapper(ImStatic.qr_solve(this.im, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(),
                prinv.getCasADiObject(), pc.getCasADiObject(), tr));
    }

    public IMWrapper qrSolve(IMWrapper v, IMWrapper r, IMWrapper beta, CasADiIntVector prinv, CasADiIntVector pc) {
        return new IMWrapper(ImStatic.qr_solve(this.im, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(),
                prinv.getCasADiObject(), pc.getCasADiObject()));
    }

    public void qr(IMWrapper Q, IMWrapper R) {
        ImStatic.qr(this.im, Q.getCasADiObject(), R.getCasADiObject());
    }

    public void ldl(IMWrapper D, IMWrapper LT, CasADiIntVector p, boolean amd) {
        ImStatic.ldl(this.im, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject(), amd);
    }

    public void ldl(IMWrapper D, IMWrapper LT, CasADiIntVector p) {
        ImStatic.ldl(this.im, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject());
    }

    public IMWrapper ldlSolve(IMWrapper D, IMWrapper LT, CasADiIntVector p) {
        return new IMWrapper(ImStatic.ldl_solve(this.im, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject()));
    }

    public IMWrapper all() {
        return new IMWrapper(ImStatic.all(this.im));
    }

    public IMWrapper any() {
        return new IMWrapper(ImStatic.any(this.im));
    }

    public IMWrapper adj() {
        return new IMWrapper(ImStatic.adj(this.im));
    }

    public IMWrapper minor(long i, long j) {
        return new IMWrapper(ImStatic.minor(this.im, i, j));
    }

    public IMWrapper cofactor(long i, long j) {
        return new IMWrapper(ImStatic.cofactor(this.im, i, j));
    }

    public IMWrapper chol() {
        return new IMWrapper(ImStatic.chol(this.im));
    }

    public IMWrapper normInfMul(IMWrapper y) {
        return new IMWrapper(ImStatic.norm_inf_mul(this.im, y.getCasADiObject()));
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
        ImStatic.set_max_depth(eq_depth);
    }

    @Override
    public void setMaxDepth() {
        ImStatic.set_max_depth();
    }

    @Override
    public long getMaxDepth() {
        return ImStatic.get_max_depth();
    }

    @Override
    public String typeName() {
        return ImStatic.type_name();
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
    public void erase(CasADiIntVector rr, CasADiIntVector cc, boolean ind1) {
        this.im.erase(rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void erase(CasADiIntVector rr, CasADiIntVector cc) {
        this.im.erase(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void erase(CasADiIntVector rr, boolean ind1) {
        this.im.erase(rr.getCasADiObject(), ind1);
    }

    @Override
    public void erase(CasADiIntVector rr) {
        this.im.erase(rr.getCasADiObject());
    }

    public void remove(CasADiIntVector rr, CasADiIntVector cc) {
        this.im.remove(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc, boolean ind1) {
        this.im.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc) {
        this.im.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject());
    }

    public CasADiIntVector nonzeros() {
        return new CasADiIntVector(this.im.nonzeros());
    }

    @Override
    public SparsityWrapper sparsity() {
        return new SparsityWrapper(this.im.sparsity());
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

    public CasADiIntVector getNonzeros() {
        return new CasADiIntVector(this.im.get_nonzeros());
    }

    public CasADiIntVector getElements() {
        return new CasADiIntVector(this.im.get_elements());
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
    public Dictionary info() {
        return new Dictionary(this.im.info());
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
        return new IMWrapper(ImStatic.vec(this.im));
    }

    public IMWrapper repmat(long n, long m) {
        return new IMWrapper(ImStatic.repmat(this.im, n, m));
    }

    public IMWrapper repmant(long n) {
        return new IMWrapper(ImStatic.repmat(this.im, n));
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
    public CasADiIntVector getRow() {
        return new CasADiIntVector(this.im.get_row());
    }

    @Override
    public CasADiIntVector getColInd() {
        return new CasADiIntVector(this.im.get_colind());
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
        return new IMWrapper(ImStatic.interp1d(x.getCasADiObject(), this.im, xq.getCasADiObject(), mode, equidistant));
    }

    @Override
    public long sprank() {
        return ImStatic.sprank(this.im);
    }

    @Override
    public long norm0Mul(IMWrapper y) {
        return ImStatic.norm_0_mul(this.im, y.getCasADiObject());
    }

    @Override
    public IMWrapper tril(boolean includeDiagonal) {
        return new IMWrapper(ImStatic.tril(this.im, includeDiagonal));
    }

    @Override
    public IMWrapper tril() {
        return new IMWrapper(ImStatic.tril(this.im));
    }

    @Override
    public IMWrapper triu(boolean includeDiagonal) {
        return new IMWrapper(ImStatic.triu(this.im, includeDiagonal));
    }

    @Override
    public IMWrapper triu() {
        return new IMWrapper(ImStatic.triu(this.im));
    }

    @Override
    public IMWrapper sumsqr() {
        return new IMWrapper(ImStatic.sumsqr(this.im));
    }

    public IMWrapper linspace(IMWrapper b, long nsteps) {
        return new IMWrapper(ImStatic.linspace(this.im, b.getCasADiObject(), nsteps));
    }

    @Override
    public IMWrapper cross(IMWrapper b, long dim) {
        return new IMWrapper(ImStatic.cross(this.im, b.getCasADiObject(), dim));
    }

    @Override
    public IMWrapper cross(IMWrapper b) {
        return new IMWrapper(ImStatic.cross(this.im, b.getCasADiObject()));
    }

    @Override
    public IMWrapper skew() {
        return new IMWrapper(ImStatic.skew(this.im));
    }

    @Override
    public IMWrapper invSkew() {
        return new IMWrapper(ImStatic.inv_skew(this.im));
    }

    @Override
    public IMWrapper tril2symm() {
        return new IMWrapper(ImStatic.tril2symm(this.im));
    }

    @Override
    public IMWrapper triu2symm() {
        return new IMWrapper(ImStatic.triu2symm(this.im));
    }

    @Override
    public IMWrapper repsum(long n, long m) {
        return new IMWrapper(ImStatic.repsum(this.im, n, m));
    }

    @Override
    public IMWrapper repsum(long n) {
        return new IMWrapper(ImStatic.repsum(this.im, n));
    }

    @Override
    public IMWrapper diff(long n, long axis) {
        return new IMWrapper(ImStatic.diff(this.im, n, axis));
    }

    @Override
    public IMWrapper diff(long n) {
        return new IMWrapper(ImStatic.diff(this.im, n));
    }

    @Override
    public IMWrapper diff() {
        return new IMWrapper(ImStatic.diff(this.im));
    }

    @Override
    public boolean isLinear(IMWrapper var) {
        return ImStatic.is_linear(this.im, var.getCasADiObject());
    }

    @Override
    public boolean isQuadratic(IMWrapper var) {
        return ImStatic.is_quadratic(this.im, var.getCasADiObject());
    }

    @Override
    public void quadraticCoeff(IMWrapper var, IMWrapper A, IMWrapper b, IMWrapper c, boolean check) {
        ImStatic.quadratic_coeff(this.im, var.getCasADiObject(), A.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject(), check);
    }

    @Override
    public void linearCoeff(IMWrapper var, IMWrapper A, IMWrapper b, boolean check) {
        ImStatic.linear_coeff(this.im, var.getCasADiObject(), A.getCasADiObject(), b.getCasADiObject(), check);
    }

    @Override
    public IMWrapper bilin(IMWrapper x, IMWrapper y) {
        return new IMWrapper(ImStatic.bilin(this.im, x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public IMWrapper rank1(IMWrapper alpha, IMWrapper x, IMWrapper y) {
        return new IMWrapper(ImStatic.rank1(this.im, alpha.getCasADiObject(), x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public IMWrapper logsumexp() {
        return new IMWrapper(ImStatic.logsumexp(this.im));
    }

    @Override
    public IMWrapper jtimes(IMWrapper arg, IMWrapper v, boolean tr, Dictionary opts) {
        return new IMWrapper(ImStatic.jtimes_(this.im, arg.getCasADiObject(), v.getCasADiObject(), tr, opts.getCasADiObject()));
    }

    @Override
    public IMWrapper jtimes(IMWrapper arg, IMWrapper v, boolean tr) {
        return new IMWrapper(ImStatic.jtimes_(this.im, arg.getCasADiObject(), v.getCasADiObject(), tr));
    }

    @Override
    public IMWrapper jtimes(IMWrapper arg, IMWrapper v) {
        return new IMWrapper(ImStatic.jtimes_(this.im, arg.getCasADiObject(), v.getCasADiObject()));
    }

    @Override
    public IMWrapper gradient(IMWrapper arg, Dictionary opts) {
        return new IMWrapper(ImStatic.gradient(this.im, arg.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public IMWrapper gradient(IMWrapper arg) {
        return new IMWrapper(ImStatic.gradient(this.im, arg.getCasADiObject()));
    }

    @Override
    public IMWrapper tangent(IMWrapper arg, Dictionary opts) {
        return new IMWrapper(ImStatic.tangent(this.im, arg.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public IMWrapper tangent(IMWrapper arg) {
        return new IMWrapper(ImStatic.tangent(this.im, arg.getCasADiObject()));
    }

    @Override
    public IMWrapper linearize(IMWrapper x, IMWrapper x0, Dictionary opts) {
        return new IMWrapper(ImStatic.linearize(this.im, x.getCasADiObject(), x0.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public IMWrapper linearize(IMWrapper x, IMWrapper x0) {
        return new IMWrapper(ImStatic.linearize(this.im, x.getCasADiObject(), x0.getCasADiObject()));
    }

    @Override
    public IMWrapper mpower(IMWrapper y) {
        return new IMWrapper(ImStatic.mpower(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper soc(IMWrapper y) {
        return new IMWrapper(ImStatic.soc(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper add(IMWrapper other) {
        return new IMWrapper(ImStatic.plus(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper add(N number) {
        double other = number.doubleValue();
        IMWrapper summand = new IMWrapper(other);
        IM result = ImStatic.plus(this.im, summand.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper subtract(IMWrapper other) {
        return new IMWrapper(ImStatic.minus(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper subtract(N number) {
        double other = number.doubleValue();
        IMWrapper subtrahend = new IMWrapper(other);
        IM result = ImStatic.minus(this.im, subtrahend.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper multiply(IMWrapper other) {
        return new IMWrapper(ImStatic.times(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper multiply(N number) {
        double other = number.doubleValue();
        IMWrapper multiplicand = new IMWrapper(other);
        IM result = ImStatic.times(this.im, multiplicand.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper divide(IMWrapper other) {
        return new IMWrapper(ImStatic.rdivide(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper divide(N number) {
        double other = number.doubleValue();
        if (other == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        IMWrapper divisor = new IMWrapper(other);
        IM result = ImStatic.rdivide(this.im, divisor.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper lt(IMWrapper y) {
        return new IMWrapper(ImStatic.lt(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper lt(N number) {
        double other = number.doubleValue();
        IMWrapper threshold = new IMWrapper(other);
        IM result = ImStatic.lt(this.im, threshold.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper le(IMWrapper y) {
        return new IMWrapper(ImStatic.le(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper le(N number) {
        double other = number.doubleValue();
        IMWrapper threshold = new IMWrapper(other);
        IM result = ImStatic.le(this.im, threshold.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper gt(IMWrapper y) {
        return new IMWrapper(ImStatic.gt(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper gt(N number) {
        double other = number.doubleValue();
        IMWrapper threshold = new IMWrapper(other);
        IM result = ImStatic.gt(this.im, threshold.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper ge(IMWrapper y) {
        return new IMWrapper(ImStatic.ge(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper ge(N number) {
        double other = number.doubleValue();
        IMWrapper threshold = new IMWrapper(other);
        IM result = ImStatic.ge(this.im, threshold.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper eq(IMWrapper y) {
        return new IMWrapper(ImStatic.eq(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper eq(N number) {
        double other = number.doubleValue();
        IMWrapper value = new IMWrapper(other);
        IM result = ImStatic.eq(this.im, value.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper ne(IMWrapper y) {
        return new IMWrapper(ImStatic.ne(this.im, y.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper ne(N number) {
        double other = number.doubleValue();
        IMWrapper value = new IMWrapper(other);
        IM result = ImStatic.ne(this.im, value.getCasADiObject());
        return new IMWrapper(result);
    }

    @Override
    public IMWrapper logicAnd(IMWrapper y) {
        return new IMWrapper(ImStatic.logic_and(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper logicOr(IMWrapper y) {
        return new IMWrapper(ImStatic.logic_or(this.im, y.getCasADiObject()));
    }

    @Override
    public IMWrapper logicNot() {
        return new IMWrapper(ImStatic.logic_not(this.im));
    }

    @Override
    public IMWrapper abs() {
        return new IMWrapper(ImStatic.abs(this.im));
    }

    @Override
    public IMWrapper sqrt() {
        return new IMWrapper(ImStatic.sqrt(this.im));
    }

    @Override
    public IMWrapper sq() {
        return new IMWrapper(ImStatic.sq(this.im));
    }

    @Override
    public IMWrapper sin() {
        return new IMWrapper(ImStatic.sin(this.im));
    }

    @Override
    public IMWrapper cos() {
        return new IMWrapper(ImStatic.cos(this.im));
    }

    @Override
    public IMWrapper tan() {
        return new IMWrapper(ImStatic.tan(this.im));
    }

    @Override
    public IMWrapper atan() {
        return new IMWrapper(ImStatic.atan(this.im));
    }

    @Override
    public IMWrapper asin() {
        return new IMWrapper(ImStatic.asin(this.im));
    }

    @Override
    public IMWrapper acos() {
        return new IMWrapper(ImStatic.acos(this.im));
    }

    @Override
    public IMWrapper tanh() {
        return new IMWrapper(ImStatic.tanh(this.im));
    }

    @Override
    public IMWrapper sinh() {
        return new IMWrapper(ImStatic.sinh(this.im));
    }

    @Override
    public IMWrapper cosh() {
        return new IMWrapper(ImStatic.cosh(this.im));
    }

    @Override
    public IMWrapper atanh() {
        return new IMWrapper(ImStatic.atanh(this.im));
    }

    @Override
    public IMWrapper asinh() {
        return new IMWrapper(ImStatic.asinh(this.im));
    }

    @Override
    public IMWrapper acosh() {
        return new IMWrapper(ImStatic.acosh(this.im));
    }

    @Override
    public IMWrapper exp() {
        return new IMWrapper(ImStatic.exp(this.im));
    }

    @Override
    public IMWrapper log() {
        return new IMWrapper(ImStatic.log(this.im));
    }

    @Override
    public IMWrapper log10() {
        return new IMWrapper(ImStatic.log10(this.im));
    }

    @Override
    public IMWrapper log1p() {
        return new IMWrapper(ImStatic.log1p(this.im));
    }

    @Override
    public IMWrapper expm1() {
        return new IMWrapper(ImStatic.expm1(this.im));
    }

    @Override
    public IMWrapper floor() {
        return new IMWrapper(ImStatic.floor(this.im));
    }

    @Override
    public IMWrapper ceil() {
        return new IMWrapper(ImStatic.ceil(this.im));
    }

    @Override
    public IMWrapper erf() {
        return new IMWrapper(ImStatic.erf(this.im));
    }

    @Override
    public IMWrapper erfinv() {
        return new IMWrapper(ImStatic.erfinv(this.im));
    }

    @Override
    public IMWrapper sign() {
        return new IMWrapper(ImStatic.sign(this.im));
    }

    @Override
    public IMWrapper pow(IMWrapper other) {
        return new IMWrapper(ImStatic.pow(this.im, other.getCasADiObject()));
    }

    @Override
    public <N extends Number> IMWrapper pow(N exponent) {
        double exp = exponent.doubleValue();
        IM exponentIM = new IM(exp);
        return new IMWrapper(ImStatic.pow(this.im, exponentIM));
    }

    @Override
    public IMWrapper mod(IMWrapper other) {
        return new IMWrapper(ImStatic.mod(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper remainder(IMWrapper other) {
        return new IMWrapper(ImStatic.remainder(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper atan2(IMWrapper other) {
        return new IMWrapper(ImStatic.atan2(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper ifElseZero(IMWrapper other) {
        return new IMWrapper(ImStatic.if_else_zero(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper fmin(IMWrapper other) {
        return new IMWrapper(ImStatic.fmin(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper fmax(IMWrapper other) {
        return new IMWrapper(ImStatic.fmax(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper copysign(IMWrapper other) {
        return new IMWrapper(ImStatic.copysign(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper constpow(IMWrapper other) {
        return new IMWrapper(ImStatic.constpow(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper hypot(IMWrapper other) {
        return new IMWrapper(ImStatic.hypot(this.im, other.getCasADiObject()));
    }

    @Override
    public IMWrapper negate() {
        IMWrapper sign = new IMWrapper(-1);
        return new IMWrapper(ImStatic.times(this.im, sign.getCasADiObject()));
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

    public IMVector toVector() {
        return new IMVector(this);
    }

}
