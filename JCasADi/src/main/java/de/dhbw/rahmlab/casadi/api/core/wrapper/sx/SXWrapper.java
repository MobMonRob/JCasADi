package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.interfaces.SymbolicExpression;
import de.dhbw.rahmlab.casadi.api.core.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.bool.BooleanVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.DoubleVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.im.IMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.index.IndexSlice;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.str.StringVector;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;

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

    public SXWrapper(SparsityWrapper sp) {
        this.sx = new SX(sp.getCasADiObject());
    }

    public SXWrapper(SparsityWrapper sp, SXWrapper d) {
        this.sx = new SX(sp.getCasADiObject(), d.getCasADiObject());
    }

    public SXWrapper(Number value) {
        this.sx = new SX(value.doubleValue());
    }

    public SXWrapper(Number... numbers) {
        DoubleVector vec = new DoubleVector();
        Arrays.stream(numbers).forEach(number -> vec.add(number.doubleValue()));
        this.sx = new SX(new DoubleVectorCollection(vec).getCasADiObject());
    }

    public SXWrapper(DoubleVectorCollection m) {
        this.sx = new SX(m.getCasADiObject());
    }

    public SXWrapper(DoubleVector... m) {
        DoubleVectorCollection collection = new DoubleVectorCollection();
        collection.addAll(Arrays.asList(m));
        this.sx = new SX(collection.getCasADiObject());
    }

    public SXWrapper(SXComponentVector x) {
        this.sx = new SX(x.getCasADiObject());
    }

    public SXWrapper(SparsityWrapper sp, SXComponent val, boolean dummy) {
        this.sx = new SX(sp.getCasADiObject(), val.getCasADiObject(), dummy);
    }

    public SXWrapper(SparsityWrapper sp, SXComponentVector d, boolean dummy) {
        this.sx = new SX(sp.getCasADiObject(), d.getCasADiObject(), dummy);
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
    public SXWrapper get(String sliceDefinition) {
        IndexSlice indexSlice = IndexSlice.slicingSyntax(sliceDefinition);
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), true, indexSlice.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IndexSlice rr) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IMWrapper rr) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, SparsityWrapper sp) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, sp.getCasADiObject());
        return output;
    }

    // ----- Get a submatrix, two arguments -----
    @Override
    public SXWrapper get(String sliceDefinitionRR, String sliceDefinitionCC) {
        IndexSlice indexSliceRR = IndexSlice.slicingSyntax(sliceDefinitionRR);
        IndexSlice indexSliceCC = IndexSlice.slicingSyntax(sliceDefinitionCC);
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), true, indexSliceRR.getCasADiObject(), indexSliceCC.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IndexSlice rr, IndexSlice cc) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IndexSlice rr, IMWrapper cc) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IMWrapper rr, IndexSlice cc) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper get(boolean ind1, IMWrapper rr, IMWrapper cc) {
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper get(NumberWrapper... slice) {
        IndexSlice indexSlice = new IndexSlice(slice);
        SXWrapper output = new SXWrapper();
        this.sx.get(output.getCasADiObject(), true, indexSlice.getCasADiObject());
        return output;
    }

    // ----- Set a submatrix, single argument -----
    @Override
    public void set(SXWrapper m, String sliceDefinition) {
        IndexSlice indexSlice = IndexSlice.slicingSyntax(sliceDefinition);
        this.sx.set(m.getCasADiObject(), true, indexSlice.getCasADiObject());
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IndexSlice rr) {
        this.sx.set(m.getCasADiObject(), ind1, rr.getCasADiObject());
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IMWrapper rr) {
        this.sx.set(m.getCasADiObject(), ind1, rr.getCasADiObject());
    }

    @Override
    public void set(SXWrapper m, boolean ind1, SparsityWrapper sp) {
        this.sx.set(m.getCasADiObject(), ind1, sp.getCasADiObject());
    }

    // ----- Set a submatrix, two arguments -----
    @Override
    public void set(SXWrapper m, String sliceDefinition, String sliceDefinitionCC) {
        IndexSlice indexSliceRR = IndexSlice.slicingSyntax(sliceDefinition);
        IndexSlice indexSliceCC = IndexSlice.slicingSyntax(sliceDefinitionCC);
        this.sx.set(m.getCasADiObject(), true, indexSliceRR.getCasADiObject(), indexSliceCC.getCasADiObject());
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IndexSlice rr, IndexSlice cc) {
        this.sx.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IndexSlice rr, IMWrapper cc) {
        this.sx.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IMWrapper rr, IndexSlice cc) {
        this.sx.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void set(SXWrapper m, boolean ind1, IMWrapper rr, IMWrapper cc) {
        this.sx.set(m.getCasADiObject(), ind1, rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void set(SXWrapper m, NumberWrapper... slice) {
        IndexSlice indexSlice = new IndexSlice(slice);
        this.sx.set(m.getCasADiObject(), true, indexSlice.getCasADiObject());
    }

    @Override
    public SXWrapper getNZ(boolean ind1, IndexSlice k) {
        SXWrapper output = new SXWrapper();
        this.sx.get_nz(output.getCasADiObject(), ind1, k.getCasADiObject());
        return output;
    }

    @Override
    public SXWrapper getNZ(boolean ind1, IMWrapper k) {
        SXWrapper output = new SXWrapper();
        this.sx.get_nz(output.getCasADiObject(), ind1, k.getCasADiObject());
        return output;
    }

    @Override
    public void setNZ(SXWrapper m, boolean ind1, IndexSlice k) {
        this.sx.set_nz(m.getCasADiObject(), ind1, k.getCasADiObject());
    }

    @Override
    public void setNZ(SXWrapper m, boolean ind1, IMWrapper k) {
        this.sx.set_nz(m.getCasADiObject(), ind1, k.getCasADiObject());
    }

    @Override
    public SXWrapper binary(long op, SXWrapper y) {
        return new SXWrapper(SxStatic.binary(op, this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper unary(long op) {
        return new SXWrapper(SxStatic.unary(op, this.sx));
    }

    @Override
    public boolean isEqual(SXWrapper other, long depth) {
        return SxStatic.is_equal(this.sx, other.getCasADiObject(), depth);
    }

    @Override
    public boolean isEqual(SXWrapper other) {
        return SxStatic.is_equal(this.sx, other.getCasADiObject());
    }

    @Override
    public SXWrapper mmin() {
        return new SXWrapper(SxStatic.mmin(this.sx));
    }

    @Override
    public SXWrapper mmax() {
        return new SXWrapper(SxStatic.mmax(this.sx));
    }

    @Override
    public SXWrapper simplify() {
        return new SXWrapper(SxStatic.simplify(this.sx));
    }

    @Override
    public SXWrapper jacobian(SXWrapper x, Dictionary opts) {
        return new SXWrapper(SxStatic.jacobian(this.sx, x.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public SXWrapper jacobian(SXWrapper x) {
        return new SXWrapper(SxStatic.jacobian(this.sx, x.getCasADiObject()));
    }

    @Override
    public SparsityWrapper jacobianSparsity(SXWrapper x) {
        return new SparsityWrapper(SxStatic.jacobian_sparsity(this.sx, x.getCasADiObject()));
    }

    @Override
    public SXWrapper hessian(SXWrapper x, Dictionary opts) {
        return new SXWrapper(SxStatic.hessian(this.sx, x.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public SXWrapper hessian(SXWrapper x) {
        return new SXWrapper(SxStatic.hessian(this.sx, x.getCasADiObject()));
    }

    @Override
    public SXWrapper hessian(SXWrapper x, SXWrapper g, Dictionary opts) {
        return new SXWrapper(SxStatic.hessian(this.sx, x.getCasADiObject(), g.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public SXWrapper hessian(SXWrapper x, SXWrapper g) {
        return new SXWrapper(SxStatic.hessian(this.sx, x.getCasADiObject(), g.getCasADiObject()));
    }

    @Override
    public SXWrapper substitute(SXWrapper v, SXWrapper vdef) {
        return new SXWrapper(SxStatic.substitute(this.sx, v.getCasADiObject(), vdef.getCasADiObject()));
    }

    @Override
    public SXWrapper pinv() {
        return new SXWrapper(SxStatic.pinv(this.sx));
    }

    @Override
    public SXWrapper pinv(String lsolver, Dictionary dict) {
        return new SXWrapper(SxStatic.pinv(this.sx, lsolver, dict.getCasADiObject()));
    }

    @Override
    public SXWrapper expmConst(SXWrapper sxWrapper) {
        return new SXWrapper(SxStatic.expm_const(this.sx, sxWrapper.getCasADiObject()));
    }

    @Override
    public SXWrapper expm() {
        return new SXWrapper(SxStatic.expm(this.sx));
    }

    @Override
    public SXWrapper solve(SXWrapper b) {
        return new SXWrapper(SxStatic.solve(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXWrapper solve(SXWrapper b, String lsolver, Dictionary dict) {
        return new SXWrapper(SxStatic.solve(this.sx, b.getCasADiObject(), lsolver, dict.getCasADiObject()));
    }

    @Override
    public SXWrapper inv() {
        return new SXWrapper(SxStatic.inv(this.sx));
    }

    @Override
    public SXWrapper inv(String lsolver, Dictionary dict) {
        return new SXWrapper(SxStatic.inv(this.sx, lsolver, dict.getCasADiObject()));
    }

    @Override
    public long nNodes() {
        return SxStatic.n_nodes(this.sx);
    }

    @Override
    public String printOperator(StringVector args) {
        return SxStatic.print_operator(this.sx, args.getCasADiObject());
    }

    @Override
    public boolean dependsOn(SXWrapper arg) {
        return SxStatic.depends_on(this.sx, arg.getCasADiObject());
    }

    @Override
    public SXWrapper mrdivide(SXWrapper b) {
        return new SXWrapper(SxStatic.mrdivide(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXWrapper mldivide(SXWrapper b) {
        return new SXWrapper(SxStatic.mldivide(this.sx, b.getCasADiObject()));
    }

    public SXVector symvar() {
        return new SXVector(SxStatic.symvar(this.sx));
    }

    @Override
    public SXWrapper det() {
        return new SXWrapper(SxStatic.det(this.sx));
    }

    @Override
    public SXWrapper invMinor() {
        return new SXWrapper(SxStatic.inv_minor(this.sx));
    }

    @Override
    public SXWrapper trace() {
        return new SXWrapper(SxStatic.trace(this.sx));
    }

    @Override
    public SXWrapper norm1() {
        return new SXWrapper(SxStatic.norm_1(this.sx));
    }

    @Override
    public SXWrapper norm2() {
        return new SXWrapper(SxStatic.norm_2(this.sx));
    }

    @Override
    public SXWrapper normFro() {
        return new SXWrapper(SxStatic.norm_fro(this.sx));
    }

    @Override
    public SXWrapper normInf() {
        return new SXWrapper(SxStatic.norm_inf(this.sx));
    }

    @Override
    public SXWrapper sum2() {
        return new SXWrapper(SxStatic.sum2(this.sx));
    }

    @Override
    public SXWrapper sum1() {
        return new SXWrapper(SxStatic.sum1(this.sx));
    }

    @Override
    public SXWrapper dot(SXWrapper y) {
        return new SXWrapper(SxStatic.dot(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper nullspace() {
        return new SXWrapper(SxStatic.nullspace(this.sx));
    }

    @Override
    public SXWrapper diag() {
        return new SXWrapper(SxStatic.diag(this.sx));
    }

    @Override
    public SXWrapper unite(SXWrapper B) {
        return new SXWrapper(SxStatic.unite(this.sx, B.getCasADiObject()));
    }

    @Override
    public SXWrapper project(SparsityWrapper sp, boolean intersect) {
        return new SXWrapper(SxStatic.project(this.sx, sp.getCasADiObject(), intersect));
    }

    @Override
    public SXWrapper project(SparsityWrapper sp) {
        return new SXWrapper(SxStatic.project(this.sx, sp.getCasADiObject()));
    }

    @Override
    public SXWrapper polyval(SXWrapper x) {
        return new SXWrapper(SxStatic.polyval(this.sx, x.getCasADiObject()));
    }

    @Override
    public SXWrapper densify(SXWrapper val) {
        return new SXWrapper(SxStatic.densify(this.sx, val.getCasADiObject()));
    }

    @Override
    public SXWrapper densify() {
        return new SXWrapper(SxStatic.densify(this.sx));
    }

    @Override
    public SXWrapper einstein(SXWrapper other, SXWrapper C,
                              CasADiIntVector dim_a, CasADiIntVector dim_b, CasADiIntVector dim_c,
                              CasADiIntVector a, CasADiIntVector b, CasADiIntVector c) {
        return new SXWrapper(SxStatic.einstein(this.sx, other.getCasADiObject(), C.getCasADiObject(),
                dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(),
                a.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public SXWrapper einstein(SXWrapper other, CasADiIntVector dim_a, CasADiIntVector dim_b, CasADiIntVector dim_c,
                              CasADiIntVector a, CasADiIntVector b, CasADiIntVector c) {
        return new SXWrapper(SxStatic.einstein(this.sx, other.getCasADiObject(),
                dim_a.getCasADiObject(), dim_b.getCasADiObject(), dim_c.getCasADiObject(),
                a.getCasADiObject(), b.getCasADiObject(), c.getCasADiObject()));
    }

    @Override
    public SXWrapper cumsum(long axis) {
        return new SXWrapper(SxStatic.cumsum(this.sx, axis));
    }

    @Override
    public SXWrapper cumsum() {
        return new SXWrapper(SxStatic.cumsum(this.sx));
    }

    public SXVector horzsplit(CasADiIntVector offset) {
        return new SXVector(SxStatic.horzsplit(this.sx, offset.getCasADiObject()));
    }

    public SXVector vertsplit(CasADiIntVector offset) {
        return new SXVector(SxStatic.vertsplit(this.sx, offset.getCasADiObject()));
    }

    public SXVector diagsplit(CasADiIntVector offset1, CasADiIntVector offset2) {
        return new SXVector(SxStatic.diagsplit(this.sx, offset1.getCasADiObject(), offset2.getCasADiObject()));
    }

    @Override
    public SXWrapper reshape(long nrow, long ncol) {
        return new SXWrapper(SxStatic.reshape(this.sx, nrow, ncol));
    }

    @Override
    public SXWrapper reshape(SparsityWrapper sp) {
        return new SXWrapper(SxStatic.reshape(this.sx, sp.getCasADiObject()));
    }

    @Override
    public SXWrapper sparsityCast(SparsityWrapper sp) {
        return new SXWrapper(SxStatic.sparsity_cast(this.sx, sp.getCasADiObject()));
    }

    @Override
    public SXWrapper kron(SXWrapper b) {
        return new SXWrapper(SxStatic.kron(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXWrapper mtimes(SXWrapper other) {
        return new SXWrapper(SxStatic.mtimes(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper mac(SXWrapper y, SXWrapper z) {
        return new SXWrapper(SxStatic.mac(this.sx, y.getCasADiObject(), z.getCasADiObject()));
    }

    public SXWrapper sparsify(double tol) {
        return new SXWrapper(SxStatic.sparsify(this.sx, tol));
    }

    public SXWrapper sparsify() {
        return new SXWrapper(SxStatic.sparsify(this.sx));
    }

    public void expand(SXWrapper weights, SXWrapper terms) {
        SxStatic.expand(this.sx, weights.getCasADiObject(), terms.getCasADiObject());
    }

    public SXWrapper pwConst(SXWrapper tval, SXWrapper val) {
        return new SXWrapper(SxStatic.pw_const(this.sx, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public SXWrapper pwLin(SXWrapper tval, SXWrapper val) {
        return new SXWrapper(SxStatic.pw_lin(this.sx, tval.getCasADiObject(), val.getCasADiObject()));
    }

    public SXWrapper heaviside() {
        return new SXWrapper(SxStatic.heaviside(this.sx));
    }

    public SXWrapper rectangle() {
        return new SXWrapper(SxStatic.rectangle(this.sx));
    }

    public SXWrapper triangle() {
        return new SXWrapper(SxStatic.triangle(this.sx));
    }

    public SXWrapper ramp() {
        return new SXWrapper(SxStatic.ramp(this.sx));
    }

    public SXWrapper gaussQuadrate(SXWrapper x, SXWrapper a, SXWrapper b, long order) {
        return new SXWrapper(SxStatic.gauss_quadrature(this.sx, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order));
    }

    public SXWrapper gaussQuadrate(SXWrapper x, SXWrapper a, SXWrapper b) {
        return new SXWrapper(SxStatic.gauss_quadrature(this.sx, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject()));
    }

    public SXWrapper gaussQuadrate(SXWrapper x, SXWrapper a, SXWrapper b, long order, SXWrapper w) {
        return new SXWrapper(SxStatic.gauss_quadrature(this.sx, x.getCasADiObject(), a.getCasADiObject(),
                b.getCasADiObject(), order, w.getCasADiObject()));
    }

    @Override
    public BooleanVector whichDepends(SXWrapper var, long order, boolean tr) {
        return new BooleanVector(SxStatic.which_depends(this.sx, var.getCasADiObject(), order, tr));
    }

    @Override
    public BooleanVector whichDepends(SXWrapper var, long order) {
        return new BooleanVector(SxStatic.which_depends(this.sx, var.getCasADiObject(), order));
    }

    @Override
    public BooleanVector whichDepends(SXWrapper var) {
        return new BooleanVector(SxStatic.which_depends(this.sx, var.getCasADiObject()));
    }

    public SXWrapper taylor(SXWrapper x, SXWrapper a, long order) {
        return new SXWrapper(SxStatic.taylor(this.sx, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public SXWrapper mtaylor(SXWrapper x, SXWrapper a, long order) {
        return new SXWrapper(SxStatic.mtaylor(this.sx, x.getCasADiObject(), a.getCasADiObject(), order));
    }

    public SXWrapper mtaylor(SXWrapper x, SXWrapper a, long order, CasADiIntVector orderContributions) {
        return new SXWrapper(SxStatic.mtaylor(this.sx, x.getCasADiObject(), a.getCasADiObject(), order, orderContributions.getCasADiObject()));
    }

    public SXWrapper polyCoeff(SXWrapper x) {
        return new SXWrapper(SxStatic.poly_coeff(this.sx, x.getCasADiObject()));
    }

    public SXWrapper polyRoots() {
        return new SXWrapper(SxStatic.poly_roots(this.sx));
    }

    public SXWrapper eigSymbolic() {
        return new SXWrapper(SxStatic.eig_symbolic(this.sx));
    }

    @Override
    public DMWrapper evalf() {
        return new DMWrapper(SxStatic.evalf(this.sx));
    }

    public void qrSparse(SXWrapper V, SXWrapper R, SXWrapper beta, CasADiIntVector prinv, CasADiIntVector pc, boolean amd) {
        SxStatic.qr_sparse(this.sx, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject(), amd);
    }

    public void qrSparse(SXWrapper V, SXWrapper R, SXWrapper beta, CasADiIntVector prinv, CasADiIntVector pc) {
        SxStatic.qr_sparse(this.sx, V.getCasADiObject(), R.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject());
    }

    public SXWrapper qrSolve(SXWrapper v, SXWrapper r, SXWrapper beta, CasADiIntVector prinv, CasADiIntVector pc, boolean tr) {
        return new SXWrapper(SxStatic.qr_solve(this.sx, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject(), tr));
    }

    public SXWrapper qrSolve(SXWrapper v, SXWrapper r, SXWrapper beta, CasADiIntVector prinv, CasADiIntVector pc) {
        return new SXWrapper(SxStatic.qr_solve(this.sx, v.getCasADiObject(), r.getCasADiObject(), beta.getCasADiObject(), prinv.getCasADiObject(), pc.getCasADiObject()));
    }

    public void qr(SXWrapper Q, SXWrapper R) {
        SxStatic.qr(this.sx, Q.getCasADiObject(), R.getCasADiObject());
    }

    public void ldl(SXWrapper D, SXWrapper LT, CasADiIntVector p, boolean amd) {
        SxStatic.ldl(this.sx, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject(), amd);
    }

    public void ldl(SXWrapper D, SXWrapper LT, CasADiIntVector p) {
        SxStatic.ldl(this.sx, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject());
    }

    public SXWrapper ldlSolve(SXWrapper D, SXWrapper LT, CasADiIntVector p) {
        return new SXWrapper(SxStatic.ldl_solve(this.sx, D.getCasADiObject(), LT.getCasADiObject(), p.getCasADiObject()));
    }

    public SXWrapper all() {
        return new SXWrapper(SxStatic.all(this.sx));
    }

    public SXWrapper any() {
        return new SXWrapper(SxStatic.any(this.sx));
    }

    public SXWrapper adj() {
        return new SXWrapper(SxStatic.adj(this.sx));
    }

    public SXWrapper minor(long i, long j) {
        return new SXWrapper(SxStatic.minor(this.sx, i, j));
    }

    public SXWrapper cofactor(long i, long j) {
        return new SXWrapper(SxStatic.cofactor(this.sx, i, j));
    }

    public SXWrapper chol() {
        return new SXWrapper(SxStatic.chol(this.sx));
    }

    public SXWrapper normInfMul(SXWrapper y) {
        return new SXWrapper(SxStatic.norm_inf_mul(this.sx, y.getCasADiObject()));
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
        SxStatic.set_max_depth(eq_depth);
    }

    @Override
    public void setMaxDepth() {
        SxStatic.set_max_depth();
    }

    @Override
    public long getMaxDepth() {
        return SxStatic.get_max_depth();
    }

    @Override
    public String typeName() {
        return SxStatic.type_name();
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
    public void erase(CasADiIntVector rr, CasADiIntVector cc, boolean ind1) {
        this.sx.erase(rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void erase(CasADiIntVector rr, CasADiIntVector cc) {
        this.sx.erase(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void erase(CasADiIntVector rr, boolean ind1) {
        this.sx.erase(rr.getCasADiObject(), ind1);
    }

    @Override
    public void erase(CasADiIntVector rr) {
        this.sx.erase(rr.getCasADiObject());
    }

    public void remove(CasADiIntVector rr, CasADiIntVector cc) {
        this.sx.remove(rr.getCasADiObject(), cc.getCasADiObject());
    }

    @Override
    public void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc, boolean ind1) {
        this.sx.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    @Override
    public void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc) {
        this.sx.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject());
    }

    public SXComponentVector nonzeros() {
        return new SXComponentVector(this.sx.nonzeros());
    }

    @Override
    public SparsityWrapper sparsity() {
        return new SparsityWrapper(this.sx.sparsity());
    }

    public static SXWrapper inf(SparsityWrapper sp) {
        return new SXWrapper(SxStatic.inf(sp.getCasADiObject()));
    }

    public static SXWrapper inf(long nrow, long ncol) {
        return new SXWrapper(SxStatic.inf(nrow, ncol));
    }

    public static SXWrapper inf(long nrow) {
        return new SXWrapper(SxStatic.inf(nrow));
    }

    public static SXWrapper inf() {
        return new SXWrapper(SxStatic.inf());
    }

    public static SXWrapper nan(SparsityWrapper sp) {
        return new SXWrapper(SxStatic.nan(sp.getCasADiObject()));
    }

    public static SXWrapper nan(long nrow, long ncol) {
        return new SXWrapper(SxStatic.nan(nrow, ncol));
    }

    public static SXWrapper nan(long nrow) {
        return new SXWrapper(SxStatic.nan(nrow));
    }

    public static SXWrapper nan() {
        return new SXWrapper(SxStatic.nan());
    }

    public static SXWrapper eye(long size) {
        return new SXWrapper(SxStatic.eye(size));
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
        return new SXWrapper(SxStatic.rand(nrow, ncol));
    }

    public static SXWrapper rand(long nrow) {
        return new SXWrapper(SxStatic.rand(nrow));
    }

    public static SXWrapper rand() {
        return new SXWrapper(SxStatic.rand());
    }

    public static SXWrapper rand(SparsityWrapper sp) {
        return new SXWrapper(SxStatic.rand(sp.getCasADiObject()));
    }

    public void exportCode(String lang) {
        this.sx.export_code(lang);
    }

    @Override
    public Dictionary info() {
        return new Dictionary(this.sx.info());
    }

    public String serialize() {
        return this.sx.serialize();
    }

    public static SXWrapper deserialize(String s) {
        return new SXWrapper(SxStatic.deserialize(s));
    }

    public void toFile(String filename, String format) {
        this.sx.to_file(filename, format);
    }

    public void toFile(String filename) {
        this.sx.to_file(filename);
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

    public SXVectorCollection blocksplit(CasADiIntVector vert_offset, CasADiIntVector horz_offset) {
        return new SXVectorCollection(SxStatic.blocksplit(this.sx, vert_offset.getCasADiObject(), horz_offset.getCasADiObject()));
    }

    public SXVectorCollection blocksplit(long vert_incr, long horz_incr) {
        return new SXVectorCollection(SxStatic.blocksplit(this.sx, vert_incr, horz_incr));
    }

    @Override
    public SXWrapper vec() {
        return new SXWrapper(SxStatic.vec(this.sx));
    }

    public SXWrapper repmat(long n, long m) {
        return new SXWrapper(SxStatic.repmat(this.sx, n, m));
    }

    public SXWrapper repmat(long n) {
        return new SXWrapper(SxStatic.repmat(this.sx, n));
    }

    public SXVector vertsplit_n(long n) {
        return new SXVector(SxStatic.vertsplit_n(this.sx, n));
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
    public CasADiIntVector getRow() {
        return new CasADiIntVector(this.sx.get_row());
    }

    @Override
    public CasADiIntVector getColInd() {
        return new CasADiIntVector(this.sx.get_colind());
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
        return new SXWrapper(SxStatic.interp1d(x.getCasADiObject(), this.sx, xq.getCasADiObject(), mode, equidistant));
    }

    @Override
    public long sprank() {
        return SxStatic.sprank(this.sx);
    }

    @Override
    public long norm0Mul(SXWrapper y) {
        return SxStatic.norm_0_mul(this.sx, y.getCasADiObject());
    }

    @Override
    public SXWrapper tril(boolean includeDiagonal) {
        return new SXWrapper(SxStatic.tril(this.sx, includeDiagonal));
    }

    @Override
    public SXWrapper tril() {
        return new SXWrapper(SxStatic.tril(this.sx));
    }

    @Override
    public SXWrapper triu(boolean includeDiagonal) {
        return new SXWrapper(SxStatic.triu(this.sx, includeDiagonal));
    }

    @Override
    public SXWrapper triu() {
        return new SXWrapper(SxStatic.triu(this.sx));
    }

    @Override
    public SXWrapper sumsqr() {
        return new SXWrapper(SxStatic.sumsqr(this.sx));
    }

    public SXWrapper linspace(SXWrapper b, long nsteps) {
        return new SXWrapper(SxStatic.linspace(this.sx, b.getCasADiObject(), nsteps));
    }

    @Override
    public SXWrapper cross(SXWrapper b, long dim) {
        return new SXWrapper(SxStatic.cross(this.sx, b.getCasADiObject(), dim));
    }

    @Override
    public SXWrapper cross(SXWrapper b) {
        return new SXWrapper(SxStatic.cross(this.sx, b.getCasADiObject()));
    }

    @Override
    public SXWrapper skew() {
        return new SXWrapper(SxStatic.skew(this.sx));
    }

    @Override
    public SXWrapper invSkew() {
        return new SXWrapper(SxStatic.inv_skew(this.sx));
    }

    @Override
    public SXWrapper tril2symm() {
        return new SXWrapper(SxStatic.tril2symm(this.sx));
    }

    @Override
    public SXWrapper triu2symm() {
        return new SXWrapper(SxStatic.triu2symm(this.sx));
    }

    public SXWrapper repsum(long n, long m) {
        return new SXWrapper(SxStatic.repsum(this.sx, n, m));
    }

    public SXWrapper repsum(long n) {
        return new SXWrapper(SxStatic.repsum(this.sx, n));
    }

    @Override
    public SXWrapper diff(long n, long axis) {
        return new SXWrapper(SxStatic.diff(this.sx, n, axis));
    }

    @Override
    public SXWrapper diff(long n) {
        return new SXWrapper(SxStatic.diff(this.sx, n));
    }

    @Override
    public SXWrapper diff() {
        return new SXWrapper(SxStatic.diff(this.sx));
    }

    @Override
    public boolean isLinear(SXWrapper var) {
        return SxStatic.is_linear(this.sx, var.getCasADiObject());
    }

    @Override
    public boolean isQuadratic(SXWrapper var) {
        return SxStatic.is_quadratic(this.sx, var.getCasADiObject());
    }

    @Override
    public void quadraticCoeff(SXWrapper var, SXWrapper A, SXWrapper b, SXWrapper c, boolean check) {
        SxStatic.quadratic_coeff(this.sx, var.getCasADiObject(), A.getCasADiObject(),
                b.getCasADiObject(), c.getCasADiObject(), check);
    }

    @Override
    public void linearCoeff(SXWrapper var, SXWrapper A, SXWrapper b, boolean check) {
        SxStatic.linear_coeff(this.sx, var.getCasADiObject(), A.getCasADiObject(), b.getCasADiObject(), check);
    }

    @Override
    public SXWrapper bilin(SXWrapper x, SXWrapper y) {
        return new SXWrapper(SxStatic.bilin(this.sx, x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public SXWrapper rank1(SXWrapper alpha, SXWrapper x, SXWrapper y) {
        return new SXWrapper(SxStatic.rank1(this.sx, alpha.getCasADiObject(), x.getCasADiObject(), y.getCasADiObject()));
    }

    @Override
    public SXWrapper logsumexp() {
        return new SXWrapper(SxStatic.logsumexp(this.sx));
    }

    @Override
    public SXWrapper jtimes(SXWrapper arg, SXWrapper v, boolean tr, Dictionary opts) {
        return new SXWrapper(SxStatic.jtimes_(this.sx, arg.getCasADiObject(), v.getCasADiObject(), tr, opts.getCasADiObject()));
    }

    @Override
    public SXWrapper jtimes(SXWrapper arg, SXWrapper v, boolean tr) {
        return new SXWrapper(SxStatic.jtimes_(this.sx, arg.getCasADiObject(), v.getCasADiObject(), tr));
    }

    @Override
    public SXWrapper jtimes(SXWrapper arg, SXWrapper v) {
        return new SXWrapper(SxStatic.jtimes_(this.sx, arg.getCasADiObject(), v.getCasADiObject()));
    }

    @Override
    public SXWrapper gradient(SXWrapper arg, Dictionary opts) {
        return new SXWrapper(SxStatic.gradient(this.sx, arg.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public SXWrapper gradient(SXWrapper arg) {
        return new SXWrapper(SxStatic.gradient(this.sx, arg.getCasADiObject()));
    }

    @Override
    public SXWrapper tangent(SXWrapper arg, Dictionary opts) {
        return new SXWrapper(SxStatic.tangent(this.sx, arg.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public SXWrapper tangent(SXWrapper arg) {
        return new SXWrapper(SxStatic.tangent(this.sx, arg.getCasADiObject()));
    }

    @Override
    public SXWrapper linearize(SXWrapper x, SXWrapper x0, Dictionary opts) {
        return new SXWrapper(SxStatic.linearize(this.sx, x.getCasADiObject(), x0.getCasADiObject(), opts.getCasADiObject()));
    }

    @Override
    public SXWrapper linearize(SXWrapper x, SXWrapper x0) {
        return new SXWrapper(SxStatic.linearize(this.sx, x.getCasADiObject(), x0.getCasADiObject()));
    }

    @Override
    public SXWrapper mpower(SXWrapper y) {
        return new SXWrapper(SxStatic.mpower(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper soc(SXWrapper y) {
        return new SXWrapper(SxStatic.soc(this.sx, y.getCasADiObject()));
    }

    public static SXWrapper sym(String name, long nrow, long ncol) {
        return new SXWrapper(SxStatic.sym(name, nrow, ncol));
    }

    public static SXWrapper sym(String name, long nrow) {
        return new SXWrapper(SxStatic.sym(name, nrow));
    }

    public static SXWrapper sym(String name) {
        return new SXWrapper(SxStatic.sym(name));
    }

    public static SXWrapper sym(String name, SparsityWrapper sp) {
        return new SXWrapper(SxStatic.sym(name, sp.getCasADiObject()));
    }

    public static SXVector sym(String name, SparsityWrapper sp, long p) {
        return new SXVector(SxStatic.sym(name, sp.getCasADiObject(), p));
    }

    public static SXVector sym(String name, long nrow, long ncol, long p) {
        return new SXVector(SxStatic.sym(name, nrow, ncol, p));
    }

    public static SXVectorCollection sym(String name, SparsityWrapper sp, long p, long r) {
        return new SXVectorCollection(SxStatic.sym(name, sp.getCasADiObject(), p, r));
    }

    public static SXVectorCollection sym(String name, long nrow, long ncol, long p, long r) {
        return new SXVectorCollection(SxStatic.sym(name, nrow, ncol, p, r));
    }

    public static SXWrapper zeros(long nrow, long ncol) {
        return new SXWrapper(SxStatic.zeros(nrow, ncol));
    }

    public static SXWrapper zeros(long nrow) {
        return new SXWrapper(SxStatic.zeros(nrow));
    }

    public static SXWrapper zeros() {
        return new SXWrapper(SxStatic.zeros());
    }

    public static SXWrapper zeros(SparsityWrapper sp) {
        return new SXWrapper(SxStatic.zeros(sp.getCasADiObject()));
    }

    public static SXWrapper ones(long nrow, long ncol) {
        return new SXWrapper(SxStatic.ones(nrow, ncol));
    }

    public static SXWrapper ones(long nrow) {
        return new SXWrapper(SxStatic.ones(nrow));
    }

    public static SXWrapper ones() {
        return new SXWrapper(SxStatic.ones());
    }

    public static SXWrapper ones(SparsityWrapper sp) {
        return new SXWrapper(SxStatic.ones(sp.getCasADiObject()));
    }

    @Override
    public SXWrapper add(SXWrapper other) {
        SX result = SxStatic.plus(this.sx, other.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public <T extends Number> SXWrapper add(T number) {
        double other = number.doubleValue();
        SXWrapper summand = new SXWrapper(other);
        SX result = SxStatic.plus(this.sx, summand.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper subtract(SXWrapper other) {
        SX result = SxStatic.minus(this.sx, other.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public <T extends Number> SXWrapper subtract(T number) {
        double other = number.doubleValue();
        SXWrapper subtrahend = new SXWrapper(other);
        SX result = SxStatic.minus(this.sx, subtrahend.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper multiply(SXWrapper other) {
        SX result = SxStatic.times(this.sx, other.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public <T extends Number> SXWrapper multiply(T number) {
        double other = number.doubleValue();
        SXWrapper multiplicand = new SXWrapper(other);
        SX result = SxStatic.times(this.sx, multiplicand.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper divide(SXWrapper other) {
        SX result = SxStatic.rdivide(this.sx, other.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public <T extends Number> SXWrapper divide(T number) {
        double other = number.doubleValue();
        if (other == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        SXWrapper divisor = new SXWrapper(other);
        SX result = SxStatic.rdivide(this.sx, divisor.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper lt(SXWrapper y) {
        return new SXWrapper(SxStatic.lt(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper lt(T number) {
        double other = number.doubleValue();
        SXWrapper threshold = new SXWrapper(other);
        SX result = SxStatic.lt(this.sx, threshold.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper le(SXWrapper y) {
        return new SXWrapper(SxStatic.le(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper le(T number) {
        double other = number.doubleValue();
        SXWrapper threshold = new SXWrapper(other);
        SX result = SxStatic.le(this.sx, threshold.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper gt(SXWrapper y) {
        return new SXWrapper(SxStatic.gt(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper gt(T number) {
        double other = number.doubleValue();
        SXWrapper threshold = new SXWrapper(other);
        SX result = SxStatic.gt(this.sx, threshold.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper ge(SXWrapper y) {
        return new SXWrapper(SxStatic.ge(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper ge(T number) {
        double other = number.doubleValue();
        SXWrapper threshold = new SXWrapper(other);
        SX result = SxStatic.ge(this.sx, threshold.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper eq(SXWrapper y) {
        return new SXWrapper(SxStatic.eq(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper eq(T number) {
        double other = number.doubleValue();
        SXWrapper value = new SXWrapper(other);
        SX result = SxStatic.eq(this.sx, value.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper ne(SXWrapper y) {
        return new SXWrapper(SxStatic.ne(this.sx, y.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper ne(T number) {
        double other = number.doubleValue();
        SXWrapper value = new SXWrapper(other);
        SX result = SxStatic.ne(this.sx, value.getCasADiObject());
        return new SXWrapper(result);
    }

    @Override
    public SXWrapper logicAnd(SXWrapper y) {
        return new SXWrapper(SxStatic.logic_and(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper logicOr(SXWrapper y) {
        return new SXWrapper(SxStatic.logic_or(this.sx, y.getCasADiObject()));
    }

    @Override
    public SXWrapper logicNot() {
        return new SXWrapper(SxStatic.logic_not(this.sx));
    }

    @Override
    public SXWrapper abs() {
        return new SXWrapper(SxStatic.abs(this.sx));
    }

    @Override
    public SXWrapper sqrt() {
        return new SXWrapper(SxStatic.sqrt(this.sx));
    }

    @Override
    public SXWrapper sq() {
        return new SXWrapper(SxStatic.sq(this.sx));
    }

    @Override
    public SXWrapper sin() {
        return new SXWrapper(SxStatic.sin(this.sx));
    }

    @Override
    public SXWrapper cos() {
        return new SXWrapper(SxStatic.cos(this.sx));
    }

    @Override
    public SXWrapper tan() {
        return new SXWrapper(SxStatic.tan(this.sx));
    }

    @Override
    public SXWrapper atan() {
        return new SXWrapper(SxStatic.atan(this.sx));
    }

    @Override
    public SXWrapper asin() {
        return new SXWrapper(SxStatic.asin(this.sx));
    }

    @Override
    public SXWrapper acos() {
        return new SXWrapper(SxStatic.acos(this.sx));
    }

    @Override
    public SXWrapper tanh() {
        return new SXWrapper(SxStatic.tanh(this.sx));
    }

    @Override
    public SXWrapper sinh() {
        return new SXWrapper(SxStatic.sinh(this.sx));
    }

    @Override
    public SXWrapper cosh() {
        return new SXWrapper(SxStatic.cosh(this.sx));
    }

    @Override
    public SXWrapper atanh() {
        return new SXWrapper(SxStatic.atanh(this.sx));
    }

    @Override
    public SXWrapper asinh() {
        return new SXWrapper(SxStatic.asinh(this.sx));
    }

    @Override
    public SXWrapper acosh() {
        return new SXWrapper(SxStatic.acosh(this.sx));
    }

    @Override
    public SXWrapper exp() {
        return new SXWrapper(SxStatic.exp(this.sx));
    }

    @Override
    public SXWrapper log() {
        return new SXWrapper(SxStatic.log(this.sx));
    }

    @Override
    public SXWrapper log10() {
        return new SXWrapper(SxStatic.log10(this.sx));
    }

    @Override
    public SXWrapper log1p() {
        return new SXWrapper(SxStatic.log1p(this.sx));
    }

    @Override
    public SXWrapper expm1() {
        return new SXWrapper(SxStatic.expm1(this.sx));
    }

    @Override
    public SXWrapper floor() {
        return new SXWrapper(SxStatic.floor(this.sx));
    }

    @Override
    public SXWrapper ceil() {
        return new SXWrapper(SxStatic.ceil(this.sx));
    }

    @Override
    public SXWrapper erf() {
        return new SXWrapper(SxStatic.erf(this.sx));
    }

    @Override
    public SXWrapper erfinv() {
        return new SXWrapper(SxStatic.erfinv(this.sx));
    }

    @Override
    public SXWrapper sign() {
        return new SXWrapper(SxStatic.sign(this.sx));
    }

    @Override
    public SXWrapper pow(SXWrapper other) {
        return new SXWrapper(SxStatic.pow(this.sx, other.getCasADiObject()));
    }

    @Override
    public <T extends Number> SXWrapper pow(T exponent) {
        double exp = exponent.doubleValue();
        SX exponentSX = new SX(exp);
        return new SXWrapper(SxStatic.pow(this.sx, exponentSX));
    }

    @Override
    public SXWrapper mod(SXWrapper other) {
        return new SXWrapper(SxStatic.mod(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper remainder(SXWrapper other) {
        return new SXWrapper(SxStatic.remainder(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper atan2(SXWrapper other) {
        return new SXWrapper(SxStatic.atan2(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper ifElseZero(SXWrapper other) {
        return new SXWrapper(SxStatic.if_else_zero(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper fmin(SXWrapper other) {
        return new SXWrapper(SxStatic.fmin(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper fmax(SXWrapper other) {
        return new SXWrapper(SxStatic.fmax(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper copysign(SXWrapper other) {
        return new SXWrapper(SxStatic.copysign(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper constpow(SXWrapper other) {
        return new SXWrapper(SxStatic.constpow(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper hypot(SXWrapper other) {
        return new SXWrapper(SxStatic.hypot(this.sx, other.getCasADiObject()));
    }

    @Override
    public SXWrapper negate() {
        SXWrapper sign = new SXWrapper(-1);
        return new SXWrapper(SxStatic.times(this.sx, sign.getCasADiObject()));
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

    public SXVector toVector() {
        return new SXVector(this);
    }

}
