package de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.bool.BooleanVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.integer.CasADiIntVector;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

public class SparsityWrapper {

    public final Sparsity sparsity;

    public SparsityWrapper() {
        this.sparsity = new Sparsity();
    }

    public SparsityWrapper(long dummy) {
        this.sparsity = new Sparsity(dummy);
    }

    public SparsityWrapper(long nrow, long ncol) {
        this.sparsity = new Sparsity(nrow, ncol);
    }

    public SparsityWrapper(long nrow, long ncol, CasADiIntVector colind, CasADiIntVector row, boolean orderRows) {
        this.sparsity = new Sparsity(nrow, ncol, colind.getCasADiObject(), row.getCasADiObject(), orderRows);
    }

    public SparsityWrapper(long nrow, long ncol, CasADiIntVector colind, CasADiIntVector row) {
        this.sparsity = new Sparsity(nrow, ncol, colind.getCasADiObject(), row.getCasADiObject());
    }

    public SparsityWrapper(Sparsity sparsity) {
        this.sparsity = new Sparsity(sparsity);
    }

    public SparsityWrapper(SparsityWrapper other) {
        this.sparsity = new Sparsity(other.getCasADiObject());
    }

    public static SparsityWrapper dense(long nrow, long ncol) {
        return new SparsityWrapper(Sparsity.dense(nrow, ncol));
    }

    public static SparsityWrapper dense(long nrow) {
        return new SparsityWrapper(Sparsity.dense(nrow));
    }

    public static SparsityWrapper unit(long n, long el) {
        return new SparsityWrapper(Sparsity.unit(n, el));
    }

    public static SparsityWrapper upper(long n) {
        return new SparsityWrapper(Sparsity.upper(n));
    }

    public static SparsityWrapper lower(long n) {
        return new SparsityWrapper(Sparsity.lower(n));
    }

    public static SparsityWrapper diag(long nrow) {
        return new SparsityWrapper(Sparsity.diag(nrow));
    }

    public static SparsityWrapper diag(long nrow, long ncol) {
        return new SparsityWrapper(Sparsity.diag(nrow, ncol));
    }

    public static SparsityWrapper band(long n, long p) {
        return new SparsityWrapper(Sparsity.band(n, p));
    }

    public static SparsityWrapper banded(long n, long p) {
        return new SparsityWrapper(Sparsity.banded(n, p));
    }


    public CasADiIntVector permutationVector(boolean invert) {
        return new CasADiIntVector(this.sparsity.permutation_vector(invert));
    }

    public CasADiIntVector permutationVector() {
        return new CasADiIntVector(this.sparsity.permutation_vector());
    }

    public SparsityWrapper getDiag(CasADiIntVector output) {
        return new SparsityWrapper(this.sparsity.get_diag(output.getCasADiObject()));
    }

    public CasADiIntVector compress() {
        return new CasADiIntVector(this.sparsity.compress());
    }

    public boolean isEqual(SparsityWrapper y) {
        return this.sparsity.is_equal(y.getCasADiObject());
    }

    public boolean isEqual(long nrow, long ncol, CasADiIntVector colind, CasADiIntVector row) {
        return this.sparsity.is_equal(nrow, ncol, colind.getCasADiObject(), row.getCasADiObject());
    }

    public boolean isStacked(SparsityWrapper y, long n) {
        return this.sparsity.is_stacked(y.getCasADiObject(), n);
    }

    public long size1() {
        return this.sparsity.size1();
    }

    public long rows() {
        return this.sparsity.rows();
    }

    public long size2() {
        return this.sparsity.size2();
    }

    public long columns() {
        return this.sparsity.columns();
    }

    public long numel() {
        return this.sparsity.numel();
    }

    public double density() {
        return this.sparsity.density();
    }

    public boolean isEmpty(boolean both) {
        return this.sparsity.is_empty(both);
    }

    public boolean isEmpty() {
        return this.sparsity.is_empty();
    }

    public long nnz() {
        return this.sparsity.nnz();
    }

    public long nnzUpper(boolean strictly) {
        return this.sparsity.nnz_upper(strictly);
    }

    public long nnzUpper() {
        return this.sparsity.nnz_upper();
    }

    public long nnzLower(boolean strictly) {
        return this.sparsity.nnz_lower(strictly);
    }

    public long nnzLower() {
        return this.sparsity.nnz_lower();
    }

    public long nnzDiag() {
        return this.sparsity.nnz_diag();
    }

    public long bwUpper() {
        return this.sparsity.bw_upper();
    }

    public long bwLower() {
        return this.sparsity.bw_lower();
    }

    public long size(long axis) {
        return this.sparsity.size(axis);
    }

    public Dictionary info() {
        return new Dictionary(this.sparsity.info());
    }

    public void toFile(String filename, String formatHint) {
        this.sparsity.to_file(filename, formatHint);
    }

    public void toFile(String filename) {
        this.sparsity.to_file(filename);
    }

    public String serialize() {
        return this.sparsity.serialize();
    }

    public CasADiIntVector getRow() {
        return new CasADiIntVector(this.sparsity.get_row());
    }

    public CasADiIntVector getColind() {
        return new CasADiIntVector(this.sparsity.get_colind());
    }

    public long colind(long cc) {
        return this.sparsity.colind(cc);
    }

    public long row(long el) {
        return this.sparsity.row(el);
    }

    public CasADiIntVector getCol() {
        return new CasADiIntVector(this.sparsity.get_col());
    }

    public void resize(long nrow, long ncol) {
        this.sparsity.resize(nrow, ncol);
    }

    public long addNz(long rr, long cc) {
        return this.sparsity.add_nz(rr, cc);
    }

    public long getNz(long rr, long cc) {
        return this.sparsity.get_nz(rr, cc);
    }

    public boolean hasNz(long rr, long cc) {
        return this.sparsity.has_nz(rr, cc);
    }

    public CasADiIntVector getNz(CasADiIntVector rr, CasADiIntVector cc) {
        return new CasADiIntVector(this.sparsity.get_nz(rr.getCasADiObject(), cc.getCasADiObject()));
    }

    public void getNz(CasADiIntVector inout) {
        this.sparsity.get_nz(inout.getCasADiObject());
    }

    public CasADiIntVector getLower() {
        return new CasADiIntVector(this.sparsity.get_lower());
    }

    public CasADiIntVector getUpper() {
        return new CasADiIntVector(this.sparsity.get_upper());
    }

    public void getCCS(CasADiIntVector arg0, CasADiIntVector arg1) {
        this.sparsity.get_ccs(arg0.getCasADiObject(), arg1.getCasADiObject());
    }

    public void getCRS(CasADiIntVector arg0, CasADiIntVector arg1) {
        this.sparsity.get_crs(arg0.getCasADiObject(), arg1.getCasADiObject());
    }

    public void getTriplet(CasADiIntVector arg0, CasADiIntVector arg1) {
        this.sparsity.get_triplet(arg0.getCasADiObject(), arg1.getCasADiObject());
    }

    public SparsityWrapper sub(CasADiIntVector rr, CasADiIntVector cc, CasADiIntVector output, boolean ind1) {
        return new SparsityWrapper(this.sparsity.sub(rr.getCasADiObject(), cc.getCasADiObject(), output.getCasADiObject(), ind1));
    }

    public SparsityWrapper sub(CasADiIntVector rr, CasADiIntVector cc, CasADiIntVector output) {
        return new SparsityWrapper(this.sparsity.sub(rr.getCasADiObject(), cc.getCasADiObject(), output.getCasADiObject()));
    }

    public SparsityWrapper sub(CasADiIntVector rr, SparsityWrapper sp, CasADiIntVector output, boolean ind1) {
        return new SparsityWrapper(this.sparsity.sub(rr.getCasADiObject(), sp.getCasADiObject(), output.getCasADiObject(), ind1));
    }

    public SparsityWrapper sub(CasADiIntVector rr, SparsityWrapper sp, CasADiIntVector output) {
        return new SparsityWrapper(this.sparsity.sub(rr.getCasADiObject(), sp.getCasADiObject(), output.getCasADiObject()));
    }

    public SparsityWrapper T() {
        return new SparsityWrapper(this.sparsity.T());
    }

    public SparsityWrapper transpose(CasADiIntVector output, boolean invertMapping) {
        return new SparsityWrapper(this.sparsity.transpose(output.getCasADiObject(), invertMapping));
    }

    public SparsityWrapper transpose(CasADiIntVector output) {
        return new SparsityWrapper(this.sparsity.transpose(output.getCasADiObject()));
    }

    public boolean isTranspose(SparsityWrapper y) {
        return this.sparsity.is_transpose(y.getCasADiObject());
    }

    public boolean isReshape(SparsityWrapper y) {
        return this.sparsity.is_reshape(y.getCasADiObject());
    }

    public SparsityWrapper combine(SparsityWrapper y, boolean f0xIsZero, boolean function0IsZero) {
        return new SparsityWrapper(this.sparsity.combine(y.getCasADiObject(), f0xIsZero, function0IsZero));
    }

    public SparsityWrapper unite(SparsityWrapper y) {
        return new SparsityWrapper(this.sparsity.unite(y.getCasADiObject()));
    }

    public SparsityWrapper intersect(SparsityWrapper y) {
        return new SparsityWrapper(this.sparsity.intersect(y.getCasADiObject()));
    }

    public boolean isSubset(SparsityWrapper rhs) {
        return this.sparsity.is_subset(rhs.getCasADiObject());
    }

    public SparsityWrapper sparsityCastMod(SparsityWrapper X, SparsityWrapper Y) {
        return new SparsityWrapper(this.sparsity.sparsity_cast_mod(X.getCasADiObject(), Y.getCasADiObject()));
    }

    public SparsityWrapper patternInverse() {
        return new SparsityWrapper(this.sparsity.pattern_inverse());
    }

    public SparsityVector horzsplit(CasADiIntVector offset) {
        return new SparsityVector(Sparsity.horzsplit(this.sparsity, offset.getCasADiObject()));
    }

    public SparsityVector vertsplit(CasADiIntVector offset) {
        return new SparsityVector(Sparsity.vertsplit(this.sparsity, offset.getCasADiObject()));
    }

    public SparsityVector diagsplit(CasADiIntVector offset1, CasADiIntVector offset2) {
        return new SparsityVector(Sparsity.diagsplit(this.sparsity, offset1.getCasADiObject(), offset2.getCasADiObject()));
    }

    public SparsityWrapper mtimes(SparsityWrapper y) {
        return new SparsityWrapper(Sparsity.mtimes(this.sparsity, y.getCasADiObject()));
    }

    public SparsityWrapper mac(SparsityWrapper y, SparsityWrapper z) {
        return new SparsityWrapper(Sparsity.mac(this.sparsity, y.getCasADiObject(), z.getCasADiObject()));
    }

    public SparsityWrapper reshape(long nrow, long ncol) {
        return new SparsityWrapper(Sparsity.reshape(this.sparsity, nrow, ncol));
    }

    public SparsityWrapper reshape(SparsityWrapper y) {
        return new SparsityWrapper(Sparsity.reshape(this.sparsity, y.getCasADiObject()));
    }

    public SparsityWrapper sparsityCast(SparsityWrapper sp) {
        return new SparsityWrapper(Sparsity.sparsity_cast(this.sparsity, sp.getCasADiObject()));
    }

    public long sprank() {
        return Sparsity.sprank(this.sparsity);
    }

    public long norm0Mul(SparsityWrapper A) {
        return Sparsity.norm_0_mul(this.sparsity, A.getCasADiObject());
    }

    public SparsityWrapper kron(SparsityWrapper b) {
        return new SparsityWrapper(Sparsity.kron(this.sparsity, b.getCasADiObject()));
    }

    public SparsityWrapper triu(boolean includeDiagonal) {
        return new SparsityWrapper(Sparsity.triu(this.sparsity, includeDiagonal));
    }

    public SparsityWrapper triu() {
        return new SparsityWrapper(Sparsity.triu(this.sparsity));
    }

    public SparsityWrapper tril(boolean includeDiagonal) {
        return new SparsityWrapper(Sparsity.tril(this.sparsity, includeDiagonal));
    }

    public SparsityWrapper tril() {
        return new SparsityWrapper(Sparsity.tril(this.sparsity));
    }

    public SparsityWrapper sum2() {
        return new SparsityWrapper(Sparsity.sum2(this.sparsity));
    }

    public SparsityWrapper sum1() {
        return new SparsityWrapper(Sparsity.sum1(this.sparsity));
    }

    public void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc, boolean ind1) {
        this.sparsity.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject(), ind1);
    }

    public void enlarge(long nrow, long ncol, CasADiIntVector rr, CasADiIntVector cc) {
        this.sparsity.enlarge(nrow, ncol, rr.getCasADiObject(), cc.getCasADiObject());
    }

    public void enlargeRows(long nrow, CasADiIntVector rr, boolean ind1) {
        this.sparsity.enlargeRows(nrow, rr.getCasADiObject(), ind1);
    }

    public void enlargeRows(long nrow, CasADiIntVector rr) {
        this.sparsity.enlargeRows(nrow, rr.getCasADiObject());
    }

    public void enlargeColumns(long ncol, CasADiIntVector cc, boolean ind1) {
        this.sparsity.enlargeColumns(ncol, cc.getCasADiObject(), ind1);
    }

    public void enlargeColumns(long ncol, CasADiIntVector cc) {
        this.sparsity.enlargeColumns(ncol, cc.getCasADiObject());
    }

    public SparsityWrapper makeDense(CasADiIntVector output) {
        return new SparsityWrapper(this.sparsity.makeDense(output.getCasADiObject()));
    }

    public CasADiIntVector erase(CasADiIntVector rr, CasADiIntVector cc, boolean ind1) {
        return new CasADiIntVector(this.sparsity.erase(rr.getCasADiObject(), cc.getCasADiObject(), ind1));
    }

    public CasADiIntVector erase(CasADiIntVector rr, CasADiIntVector cc) {
        return new CasADiIntVector(this.sparsity.erase(rr.getCasADiObject(), cc.getCasADiObject()));
    }

    public CasADiIntVector erase(CasADiIntVector rr, boolean ind1) {
        return new CasADiIntVector(this.sparsity.erase(rr.getCasADiObject(), ind1));
    }

    public CasADiIntVector erase(CasADiIntVector rr) {
        return new CasADiIntVector(this.sparsity.erase(rr.getCasADiObject()));
    }

    public void append(SparsityWrapper sp) {
        this.sparsity.append(sp.getCasADiObject());
    }

    public void appendColumns(SparsityWrapper sp) {
        this.sparsity.appendColumns(sp.getCasADiObject());
    }

    public boolean isScalar(boolean scalarAndDense) {
        return this.sparsity.is_scalar(scalarAndDense);
    }

    public boolean isScalar() {
        return this.sparsity.is_scalar();
    }

    public boolean isDense() {
        return this.sparsity.is_dense();
    }

    public boolean isRow() {
        return this.sparsity.is_row();
    }

    public boolean isColumn() {
        return this.sparsity.is_column();
    }

    public boolean isVector() {
        return this.sparsity.is_vector();
    }

    public boolean isDiag() {
        return this.sparsity.is_diag();
    }

    public boolean isSquare() {
        return this.sparsity.is_square();
    }

    public boolean isSymmetric() {
        return this.sparsity.is_symmetric();
    }

    public boolean isTriu(boolean strictly) {
        return this.sparsity.is_triu(strictly);
    }

    public boolean isTriu() {
        return this.sparsity.is_triu();
    }

    public boolean isTril(boolean strictly) {
        return this.sparsity.is_tril(strictly);
    }

    public boolean isTril() {
        return this.sparsity.is_tril();
    }

    public boolean isSingular() {
        return this.sparsity.is_singular();
    }

    public boolean isPermutation() {
        return this.sparsity.is_permutation();
    }

    public boolean isSelection(boolean allowEmpty) {
        return this.sparsity.is_selection(allowEmpty);
    }

    public boolean isSelection() {
        return this.sparsity.is_selection();
    }

    public boolean isOrthonormal(boolean allowEmpty) {
        return this.sparsity.is_orthonormal(allowEmpty);
    }

    public boolean isOrthonormal() {
        return this.sparsity.is_orthonormal();
    }

    public boolean isOrthonormalRows(boolean allowEmpty) {
        return this.sparsity.is_orthonormal_rows(allowEmpty);
    }

    public boolean isOrthonormalRows() {
        return this.sparsity.is_orthonormal_rows();
    }

    public boolean isOrthonormalColumns(boolean allowEmpty) {
        return this.sparsity.is_orthonormal_columns(allowEmpty);
    }

    public boolean isOrthonormalColumns() {
        return this.sparsity.is_orthonormal_columns();
    }

    public boolean rowsSequential(boolean strictly) {
        return this.sparsity.rowsSequential(strictly);
    }

    public boolean rowsSequential() {
        return this.sparsity.rowsSequential();
    }

    public void removeDuplicates(CasADiIntVector inout) {
        this.sparsity.removeDuplicates(inout.getCasADiObject());
    }

    public CasADiIntVector etree(boolean ata) {
        return new CasADiIntVector(this.sparsity.etree(ata));
    }

    public CasADiIntVector etree() {
        return new CasADiIntVector(this.sparsity.etree());
    }

    public SparsityWrapper ldl(CasADiIntVector output, boolean amd) {
        return new SparsityWrapper(this.sparsity.ldl(output.getCasADiObject(), amd));
    }

    public SparsityWrapper ldl(CasADiIntVector output) {
        return new SparsityWrapper(this.sparsity.ldl(output.getCasADiObject()));
    }

    public void qrSparse(SparsityWrapper arg0, SparsityWrapper arg1, CasADiIntVector arg2, CasADiIntVector arg3, boolean amd) {
        this.sparsity.qr_sparse(arg0.getCasADiObject(), arg1.getCasADiObject(), arg2.getCasADiObject(), arg3.getCasADiObject(), amd);
    }

    public void qrSparse(SparsityWrapper arg0, SparsityWrapper arg1, CasADiIntVector arg2, CasADiIntVector arg3) {
        this.sparsity.qr_sparse(arg0.getCasADiObject(), arg1.getCasADiObject(), arg2.getCasADiObject(), arg3.getCasADiObject());
    }

    public long dfs(long j, long top, CasADiIntVector arg2, CasADiIntVector arg3, CasADiIntVector pinv, BooleanVector arg5) {
        return this.sparsity.dfs(j, top, arg2.getCasADiObject(), arg3.getCasADiObject(), pinv.getCasADiObject(), arg5.getCasADiObject());
    }

    public long scc(CasADiIntVector arg0, CasADiIntVector arg1) {
        return this.sparsity.scc(arg0.getCasADiObject(), arg1.getCasADiObject());
    }

    public long btf(CasADiIntVector arg0, CasADiIntVector arg1, CasADiIntVector arg2, CasADiIntVector arg3, CasADiIntVector arg4, CasADiIntVector arg5) {
        return this.sparsity.btf(arg0.getCasADiObject(), arg1.getCasADiObject(), arg2.getCasADiObject(), arg3.getCasADiObject(), arg4.getCasADiObject(), arg5.getCasADiObject());
    }

    public CasADiIntVector amd() {
        return new CasADiIntVector(this.sparsity.amd());
    }

    public CasADiIntVector find(boolean ind1) {
        return new CasADiIntVector(this.sparsity.find(ind1));
    }

    public CasADiIntVector find() {
        return new CasADiIntVector(this.sparsity.find());
    }

    public void find(CasADiIntVector loc, boolean ind1) {
        this.sparsity.find(loc.getCasADiObject(), ind1);
    }

    public void find(CasADiIntVector loc) {
        this.sparsity.find(loc.getCasADiObject());
    }

    public SparsityWrapper uniColoring(SparsityWrapper AT, long cutoff) {
        return new SparsityWrapper(this.sparsity.uni_coloring(AT.getCasADiObject(), cutoff));
    }

    public SparsityWrapper uniColoring(SparsityWrapper AT) {
        return new SparsityWrapper(this.sparsity.uni_coloring(AT.getCasADiObject()));
    }

    public SparsityWrapper uniColoring() {
        return new SparsityWrapper(this.sparsity.uni_coloring());
    }

    public SparsityWrapper starColoring(long ordering, long cutoff) {
        return new SparsityWrapper(this.sparsity.star_coloring(ordering, cutoff));
    }

    public SparsityWrapper starColoring(long ordering) {
        return new SparsityWrapper(this.sparsity.star_coloring(ordering));
    }

    public SparsityWrapper starColoring() {
        return new SparsityWrapper(this.sparsity.star_coloring());
    }

    public SparsityWrapper starColoring2(long ordering, long cutoff) {
        return new SparsityWrapper(this.sparsity.star_coloring2(ordering, cutoff));
    }

    public SparsityWrapper starColoring2(long ordering) {
        return new SparsityWrapper(this.sparsity.star_coloring2(ordering));
    }

    public SparsityWrapper starColoring2() {
        return new SparsityWrapper(this.sparsity.star_coloring2());
    }

    public CasADiIntVector largestFirst() {
        return new CasADiIntVector(this.sparsity.largest_first());
    }

    public SparsityWrapper pmult(CasADiIntVector p, boolean permuteRows, boolean permuteColumns, boolean invertPermutation) {
        return new SparsityWrapper(this.sparsity.pmult(p.getCasADiObject(), permuteRows, permuteColumns, invertPermutation));
    }

    public SparsityWrapper pmult(CasADiIntVector p, boolean permuteRows, boolean permuteColumns) {
        return new SparsityWrapper(this.sparsity.pmult(p.getCasADiObject(), permuteRows, permuteColumns));
    }

    public SparsityWrapper pmult(CasADiIntVector p, boolean permuteRows) {
        return new SparsityWrapper(this.sparsity.pmult(p.getCasADiObject(), permuteRows));
    }

    public SparsityWrapper pmult(CasADiIntVector p) {
        return new SparsityWrapper(this.sparsity.pmult(p.getCasADiObject()));
    }

    public String dim(boolean withNz) {
        return this.sparsity.dim(withNz);
    }

    public String dim() {
        return this.sparsity.dim();
    }

    public String postfixDim() {
        return this.sparsity.postfix_dim();
    }

    public String reprEl(long k) {
        return this.sparsity.repr_el(k);
    }

    public void spy() {
        this.sparsity.spy();
    }

    public void spyMatlab(String mfile) {
        this.sparsity.spy_matlab(mfile);
    }

    public void exportCode(String lang) {
        this.sparsity.export_code(lang);
    }

    public String typeName() {
        return Sparsity.type_name();
    }

    public long hash() {
        return this.sparsity.hash();
    }

    public SparsityWrapper kkt(SparsityWrapper J, boolean withXDiag, boolean withLamGDiag) {
        return new SparsityWrapper(Sparsity.kkt(this.sparsity, J.getCasADiObject(), withXDiag, withLamGDiag));
    }

    public SparsityWrapper kkt(SparsityWrapper J, boolean withXDiag) {
        return new SparsityWrapper(Sparsity.kkt(this.sparsity, J.getCasADiObject(), withXDiag));
    }

    public SparsityWrapper kkt(SparsityWrapper J) {
        return new SparsityWrapper(Sparsity.kkt(this.sparsity, J.getCasADiObject()));
    }

    public SparsityVectorCollection blocksplit(CasADiIntVector vertOffset, CasADiIntVector horzOffset) {
        return new SparsityVectorCollection(Sparsity.blocksplit(this.sparsity, vertOffset.getCasADiObject(), horzOffset.getCasADiObject()));
    }

    public SparsityVectorCollection blocksplit(long vertIncr, long horzIncr) {
        return new SparsityVectorCollection(Sparsity.blocksplit(this.sparsity, vertIncr, horzIncr));
    }

    public SparsityWrapper vec() {
        return new SparsityWrapper(Sparsity.vec(this.sparsity));
    }

    public SparsityWrapper repmat(long n, long m) {
        return new SparsityWrapper(Sparsity.repmat(this.sparsity, n, m));
    }

    public SparsityWrapper repmat(long n) {
        return new SparsityWrapper(Sparsity.repmat(this.sparsity, n));
    }

    public SparsityVector vertsplitN(long n) {
        return new SparsityVector(Sparsity.vertsplit_n(this.sparsity, n));
    }

    public String className() {
        return this.sparsity.class_name();
    }

    public String toString(boolean more) {
        return this.sparsity.toString(more);
    }

    public String toString() {
        return this.sparsity.toString();
    }

    public boolean isNull() {
        return this.sparsity.is_null();
    }

    public long __hash__() {
        return this.sparsity.__hash__();
    }

    public Sparsity getCasADiObject() {
        return this.sparsity;
    }

    public SparsityVector toVector() {
        return new SparsityVector(this);
    }

}
