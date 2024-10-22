package de.dhbw.rahmlab.casadi.api;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

/**
 * useful to implement normalizeEvenElement() in CGACasadi
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SXScalar {
    
    public de.dhbw.rahmlab.casadi.impl.casadi.SX sx;
    
    public SXScalar(){
        sx = new SX(Sparsity.dense(1));
    }
    public SXScalar(SX sx){
        this.sx = sx;
    }
    public SXScalar(double value){
        this.sx = new SX(value);
    }
    
    public SXScalar div(SXScalar s){
        return new SXScalar(SX.times(sx, SX.inv(s.sx)));
    }
    public SXScalar mul(SXScalar s){
        return new SXScalar(SX.mtimes(sx, s.sx));
    }
    public SXScalar muls(double s){
        return new SXScalar(SX.times(sx, new SX(s)));
    }
    public SXScalar add(SXScalar a){
        return new SXScalar(SX.plus(sx, a.sx));
    }
    public SXScalar sub(SXScalar a){
        return new SXScalar(SX.minus(sx, a.sx));
    }
    
    public static SXScalar sum(SXScalar[] summands){
        SXScalar result = new SXScalar();
        for (int i=0;i<summands.length;i++){
            result.sx = SX.plus(result.sx, summands[i].sx);
        }
        return result;
    }
    public static SXScalar sumSq(SXScalar[] summands){
        SXScalar result = new SXScalar();
        for (int i=0;i<summands.length;i++){
            result.sx = SX.plus(result.sx, SX.sq(summands[i].sx));
        }
        return result;
    }
    public static SXScalar sumSq(SXColVec vec, int[] indizes){
        SXScalar result = new SXScalar();
        for (int i=0;i<indizes.length;i++){
            result.sx = SX.plus(result.sx, SX.sq(vec.sx.at(indizes[i])));
        }
        return result;
    }
    public static SXScalar sumProd(SXColVec vec, int[] a, int[] b){
        if (a.length != b.length) throw new IllegalArgumentException("a!=b");
        SXScalar result = new SXScalar();
        for (int i=0;i<a.length;i++){
            result.sx = SX.plus(result.sx, SX.times(vec.sx.at(a[i]),vec.sx.at(b[i])));
        }
        return result;
    }
    
    public static SXScalar sub(SXScalar[] summands){
        SXScalar result = new SXScalar();
        for (int i=0;i<summands.length;i++){
            result.sx = SX.minus(result.sx, summands[i].sx);
        }
        return result;
    }
    public static SXScalar subSq(SXScalar[] summands){
        SXScalar result = new SXScalar();
        for (int i=0;i<summands.length;i++){
            result.sx = SX.minus(result.sx, SX.sq(summands[i].sx));
        }
        return result;
    }
    public SXScalar sq(){
        return new SXScalar(SX.sq(sx));
    }
    public SXScalar exp(){
        return new SXScalar(SX.exp(sx));
    }
    public SXScalar pow(double exp){
        return new SXScalar(SX.pow(sx, new SX(exp)));
    }
    public SXScalar pow(SXScalar exp){
        return new SXScalar(SX.pow(sx, exp.sx));
    }
    public static SXScalar pow(double base, SXScalar exp){
        return new SXScalar(SX.pow(new SX(base), exp.sx));
    }
    public SXScalar negate(){
        return new SXScalar(SX.times(sx, new SX(-1)));
    }
}
