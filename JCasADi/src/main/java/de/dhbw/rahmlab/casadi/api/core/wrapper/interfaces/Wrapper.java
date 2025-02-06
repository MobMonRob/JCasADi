package de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.BooleanVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.IntegerVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.StringVector;
import de.dhbw.rahmlab.casadi.impl.casadi.IM;
import de.dhbw.rahmlab.casadi.impl.casadi.Slice;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

public interface Wrapper<T extends Wrapper> {

    String typeName();

    Sparsity sparsity();

    boolean nonzero();

    void erase(IntegerVector rr, IntegerVector cc, boolean ind1);

    void erase(IntegerVector rr, IntegerVector cc);

    void erase(IntegerVector rr, boolean ind1);

    void erase(IntegerVector rr);

    void enlarge(long nrow, long ncol, IntegerVector rr, IntegerVector cc, boolean ind1);

    void enlarge(long nrow, long ncol, IntegerVector rr, IntegerVector cc);

    T dep(long ch);

    T dep();

    long nDep();

    String getName();

    boolean isSymbolic();

    boolean isConstant();

    boolean isOp(long op);

    boolean isCommutative();

    boolean isValidInput();

    boolean hasDuplicates();

    void resetInput();

    boolean isEye();

    boolean isZero();

    boolean isOne();

    boolean isMinusOne();

    boolean isRegular();

    long op();

    Dict info();

    T binary(long op, T y);

    T unary(long op);

    T get(String sliceDefinition);

    T get(boolean ind1, Slice rr);

    T get(boolean ind1, IM rr);

    T get(boolean ind1, Sparsity sp);

    T get(boolean ind1, Slice rr, Slice cc);

    T get(boolean ind1, Slice rr, IM cc);

    T get(boolean ind1, IM rr, Slice cc);

    T get(boolean ind1, IM rr, IM cc);

    T get(NumberWrapper... slice);

    void set(T m, String sliceDefinition);

    void set(T m, boolean ind1, Slice rr);

    void set(T m, boolean ind1, IM rr);

    void set(T m, boolean ind1, Sparsity sp);

    void set(T m, boolean ind1, Slice rr, Slice cc);

    void set(T m, boolean ind1, Slice rr, IM cc);

    void set(T m, boolean ind1, IM rr, Slice cc);

    void set(T m, boolean ind1, IM rr, IM cc);

    void set(T m, NumberWrapper... slice);

    T getNZ(boolean ind1, Slice k);

    T getNZ(boolean ind1, IM k);

    void setNZ(T m, boolean ind1, Slice k);

    void setNZ(T m, boolean ind1, IM k);

    T einstein(T other, T C,
               IntegerVector dim_a, IntegerVector dim_b,
               IntegerVector dim_c, IntegerVector a,
               IntegerVector b, IntegerVector c);

    T einstein(T other,
               IntegerVector dim_a, IntegerVector dim_b,
               IntegerVector dim_c, IntegerVector a,
               IntegerVector b, IntegerVector c);

    boolean isEqual(T other, long depth);

    boolean isEqual(T other);

    T mmin();

    T mmax();

    <S extends Vector> S horzsplit(IntegerVector offset);

    <S extends Vector> S diagsplit(IntegerVector offset1, IntegerVector offset2);

    <S extends Vector> S vertsplit(IntegerVector offset);

    T mtimes(T other);

    T mac(T y, T z);

    T reshape(long nrow, long ncol);

    T reshape(Sparsity sp);

    T sparsityCast(Sparsity sp);

    T kron(T b);

    T jacobian(T x, Dict opts);

    T jacobian(T x);

    T hessian(T x, Dict opts);

    T hessian(T x);

    T hessian(T x, T g, Dict opts);

    T hessian(T x, T g);

    BooleanVector whichDepends(T var, long order, boolean tr);

    BooleanVector whichDepends(T var, long order);

    BooleanVector whichDepends(T var);

    Sparsity jacobianSparsity(T x);

    T substitute(T v, T vdef);

    T solve(T b);

    T solve(T b, String lsolver, Dict dict);

    T invMinor();

    T inv();

    T inv(String lsolver, Dict dict);

    T pinv();

    T pinv(String lsolver, Dict dict);

    T expmConst(T t);

    T expm();

    long nNodes();

    String printOperator(StringVector args);

    boolean dependsOn(T arg);

    T simplify();

    T dot(T y);

    T mrdivide(T b);

    T mldivide(T b);

    T norm2();

    T normFro();

    T norm1();

    T normInf();

    T unite(T B);

    T trace();

    T diag();

    T sum2();

    T sum1();

    T polyval(T x);

    T det();

    <S extends Vector> S symvar();

    T nullspace();

    T repsum(long n, long m);

    T repsum(long n);

    T densify(T val);

    T densify();

    T _bilin(T x, T y);

    T _rank1(T alpha, T x, T y);

    T project(Sparsity sp, boolean intersect);

    T project(Sparsity sp);

    T cumsum(long axis);

    T cumsum();

    T _logsumexp();

    DMWrapper evalf();

    T printMe(T y);

    T T();

    void setMaxDepth(long eq_depth);

    void setMaxDepth();

    long getMaxDepth();

    T _sym(String name, Sparsity sp);

    <V extends SubIndex> V at(int rr);

    <W extends SubMatrix> W at(int rr, int cc);

    void assign(T other);

    <U extends Collection> U blocksplit(IntegerVector vert_offset, IntegerVector horz_offset);

    <U extends Collection> U blocksplit(long vert_incr, long horz_incr);

    T vec();

    <S extends Vector> S vertsplit_n(long n);

    String toString(boolean more);

    String toString();

    long nnz();

    long nnzLower();

    long nnzUpper();

    long nnzDiag();

    long numel();

    long size1();

    long rows();

    long size2();

    long columns();

    String dim(boolean withNz);

    String dim();

    long size(long axis);

    boolean isEmpty(boolean both);

    boolean isEmpty();

    boolean isDense();

    boolean isScalar(boolean scalarAndDense);

    boolean isScalar();

    boolean isSquare();

    boolean isVector();

    boolean isRow();

    boolean isColumn();

    boolean isTriu();

    boolean isTril();

    IntegerVector getRow();

    IntegerVector getColInd();

    long row(long el);

    long colind(long col);

    T interp1d(DoubleVector x, DoubleVector xq, String mode, boolean equidistant);

    long sprank();

    long norm0Mul(T y);

    T tril(boolean includeDiagonal);

    T tril();

    T triu(boolean includeDiagonal);

    T triu();

    T sumsqr();

    T cross(T b, long dim);

    T cross(T b);

    T skew();

    T invSkew();

    T tril2symm();

    T triu2symm();

    T diff(long n, long axis);

    T diff(long n);

    T diff();

    boolean isLinear(T var);

    boolean isQuadratic(T var);

    void quadraticCoeff(T var, T A, T b, T c, boolean check);

    void linearCoeff(T var, T A, T b, boolean check);

    T bilin(T x, T y);

    T rank1(T alpha, T x, T y);

    T logsumexp();

    T jtimes(T arg, T v, boolean tr, Dict opts);

    T jtimes(T arg, T v, boolean tr);

    T jtimes(T arg, T v);

    T gradient(T arg, Dict opts);

    T gradient(T arg);

    T tangent(T arg, Dict opts);

    T tangent(T arg);

    T linearize(T x, T x0, Dict opts);

    T linearize(T x, T x0);

    T mpower(T y);

    T soc(T y);

    T add(T other);

    <N extends Number> T add(N number);

    T subtract(T other);

    <N extends Number> T subtract(N number);

    T multiply(T other);

    <N extends Number> T multiply(N number);

    T divide(T other);

    <N extends Number> T divide(N number);

    T lt(T y);

    <N extends Number> T lt(N number);

    T le(T y);

    <N extends Number> T le(N number);

    T gt(T y);

    <N extends Number> T gt(N number);

    T ge(T y);

    <N extends Number> T ge(N number);

    T eq(T y);

    <N extends Number> T eq(N number);

    T ne(T y);

    <N extends Number> T ne(N number);

    T logicAnd(T y);

    T logicOr(T y);

    T logicNot();

    T abs();

    T sqrt();

    T sq();

    T sin();

    T cos();

    T tan();

    T atan();

    T asin();

    T acos();

    T tanh();

    T sinh();

    T cosh();

    T atanh();

    T asinh();

    T acosh();

    T exp();

    T log();

    T log10();

    T log1p();

    T expm1();

    T floor();

    T ceil();

    T erf();

    T erfinv();

    T sign();

    T pow(T other);

    <N extends Number> T pow(N exponent);

    T mod(T other);

    T remainder(T other);

    T atan2(T other);

    T ifElseZero(T other);

    T fmin(T other);

    T fmax(T other);

    T copysign(T other);

    T constpow(T other);

    T hypot(T other);

    T negate();

}
