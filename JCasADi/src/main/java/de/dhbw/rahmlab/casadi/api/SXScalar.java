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
        this.sx = new SX(Sparsity.dense(1));
    }
    public SXScalar(SX sx){
        this.sx = sx;
    }
    public static SXScalar mul(SXScalar s1, SXScalar s2){
        return s1.mul(s2);
    }
    public SXScalar(double value){
        this.sx = new SX(value);
    }
    
    public SXScalar div(SXScalar s){
        return new SXScalar(SX.times(sx, SX.inv(s.sx)));
    }
    public SXScalar div(double s){
        return new SXScalar(SX.times(sx, new SX(1d/s)));
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
        if (a.length != b.length) throw new IllegalArgumentException("a.length!=b.length");
        SXScalar result = new SXScalar();
        for (int i=0;i<a.length;i++){
            result.sx = SX.plus(result.sx, SX.times(vec.sx.at(a[i]),vec.sx.at(b[i])));
        }
        return result;
    }
    
    public static SXScalar sumProd(SXScalar[] scalors, SXColVec vec, int[] ind){
        if (scalors.length != ind.length) throw new IllegalArgumentException("scalors.length!=ind.length");
        //if (scalors.length != vec.length) throw new IllegalArgumentException("scalors.length!=vec.length");
        SXScalar result = new SXScalar();
        for (int i=0;i<ind.length;i++){
            result.sx = SX.plus(result.sx, SX.times(vec.sx.at(ind[i]),scalors[i].sx));
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
    public SXScalar sqrt(){
        return new SXScalar(SX.sqrt(sx));
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
    public SXScalar abs(){
        return new SXScalar(SX.abs(sx));
    }
    // > than: 1, else 0
    public SXScalar gt(SXScalar y){
        return new SXScalar(SX.gt(sx, y.sx));
    }
    // > than: ifele, else elseele
    public SXScalar gt(SXScalar y, SXScalar ifele, SXScalar elseele){
        SX gt = SX.gt(sx, y.sx);
        SX le = SX.le(sx, y.sx);
        // TODO statt times SX.if_else verwenden
        return (new SXScalar(SX.mtimes(gt, ifele.sx)).add(new SXScalar(SX.mtimes(le, elseele.sx))));
    }
    // > than: ifele, else elseele
    public SXScalar gt(double y, SXScalar ifele, SXScalar elseele){
        SX gt = SX.gt(sx, new SXScalar(y).sx);
        SX le = SX.le(sx, new SXScalar(y).sx);
        // TODO statt times SX.if_else verwenden
        return (new SXScalar(SX.mtimes(gt, ifele.sx)).add(new SXScalar(SX.mtimes(le, elseele.sx))));
    }
    public SXScalar[] gt(SXScalar y, SXScalar[] ifele, SXScalar[] elseele){
        if (ifele.length != elseele.length) throw new IllegalArgumentException("efele.length != elseele.length not allwoed!");
        SX gt = SX.gt(sx, y.sx);
        SX le = SX.le(sx, y.sx);
        // TODO statt times SX.if_else verwenden
        SXScalar[] result = new SXScalar[ifele.length];
        for (int i=0;i<ifele.length;i++){
            result[i] = (new SXScalar(SX.mtimes(gt, ifele[i].sx)).
                    add(new SXScalar(SX.mtimes(le, elseele[i].sx))));
        }
        return result;
    }
    
    public SXScalar eq(double y, SXScalar ifele, SXScalar elseele){
        return eq(new SXScalar(y), ifele, elseele);
    }
    public SXScalar eq(SXScalar y, SXScalar ifele, SXScalar elseele){
        SX eq = SX.eq(sx, y.sx);
        SX ne = SX.ne(sx, y.sx);
        // TODO statt times SX.if_else verwenden
        return (new SXScalar(SX.mtimes(ifele.sx, eq))).add(new SXScalar(SX.mtimes(elseele.sx, ne)));
    }
    public SXScalar[] eq(double y, SXScalar[] ifele, SXScalar[] elseele){
        return eq(new SXScalar(y), ifele, elseele);
    }
    
    //TODO
    // eleganter wäre es statt SXScalar[] SXColVec zur Verfügung zu stellen...
    // dann sollten auch die Implementierungen effizienter sein
    
    public SXScalar[] eq(SXScalar y, SXScalar[] ifele, SXScalar[] elseele){
        if (ifele.length != elseele.length) throw new IllegalArgumentException("ifele.length != elseele.length!");
        SX eq = SX.eq(sx, y.sx);
        SX ne = SX.ne(sx, y.sx);
        // TODO statt times SX.if_else verwenden
        SXScalar[] result = new SXScalar[ifele.length];
        for (int i=0;i<ifele.length;i++){
            result[i] = (new SXScalar(SX.mtimes(eq, ifele[i].sx)).
                    add(new SXScalar(SX.mtimes(ne, elseele[i].sx))));
        }
        return result;
    }
    // efele if x1==y1 && x2==y2 else elseele
    public static SXScalar[] eq(SXScalar x1, SXScalar y1, SXScalar x2, SXScalar y2, SXScalar[] ifele, SXScalar[] elseele){
        if (ifele.length != elseele.length) throw new IllegalArgumentException("ifele.length != elseele.length!");
        SX eq = SX.times(SX.eq(x1.sx, y1.sx),(SX.eq(x2.sx, y2.sx)));
        SX ne = SX.gt(SX.plus(SX.ne(x1.sx, y1.sx),SX.ne(x2.sx, y2.sx)), new SX(0d));
                 
        // TODO statt times SX.if_else verwenden
        SXScalar[] result = new SXScalar[ifele.length];
        for (int i=0;i<ifele.length;i++){
            result[i] = (new SXScalar(SX.mtimes(eq, ifele[i].sx)).
                    add(new SXScalar(SX.mtimes(ne, elseele[i].sx))));
        }
        return result;
    }
    // >= than: 1, else 0
    public SXScalar ge(SXScalar y){
        return new SXScalar(SX.ge(sx, y.sx));
    }
    // < than: 1, else 0
    public SXScalar lt(SXScalar y){
        return new SXScalar(SX.lt(sx, y.sx));
    }
    public SXScalar lt(SXScalar y, SXScalar ifele, SXScalar elseele){
        SX lt = SX.lt(sx, y.sx);
        SX ge = SX.ge(sx, y.sx);
        // TODO statt times SX.if_else verwenden
        return (new SXScalar(SX.mtimes(lt, ifele.sx)).add(new SXScalar(SX.mtimes(ge, elseele.sx))));
    }
    public SXScalar lt(double y, SXScalar ifele, SXScalar elseele){
        SX lt = SX.lt(sx, new SXScalar(y).sx);
        SX ge = SX.ge(sx, new SXScalar(y).sx);
        // TODO statt times SX.if_else verwenden
        return (new SXScalar(SX.mtimes(lt, ifele.sx)).add(new SXScalar(SX.mtimes(ge, elseele.sx))));
    }
    public SXScalar[] lt(SXScalar y, SXScalar[] ifele, SXScalar[] elseele){
        if (ifele.length != elseele.length) throw new IllegalArgumentException("efele.length != elseele.length not allwoed!");
        SX lt = SX.lt(sx, y.sx);
        SX ge = SX.ge(sx, y.sx);
        // TODO statt times SX.if_else verwenden
        SXScalar[] result = new SXScalar[ifele.length];
        for (int i=0;i<ifele.length;i++){
            result[i] = (new SXScalar(SX.mtimes(lt, ifele[i].sx)).
                    add(new SXScalar(SX.mtimes(ge, elseele[i].sx))));
        }
        return result;
    }
    
    public SXScalar cos(){
        return new SXScalar(SX.cos(sx));
    }
    public SXScalar sin(){
        return new SXScalar(SX.sin(sx));
    }
    public SXScalar cosh(){
        return new SXScalar(SX.cosh(sx));
    }
    public SXScalar sinh(){
        return new SXScalar(SX.sinh(sx));
    }
    public SXScalar atanh(){
        return new SXScalar(SX.atanh(sx));
    }
    public static SXScalar atan2(SXScalar y, SXScalar x){
        return new SXScalar(SX.atan2(y.sx, x.sx));
    }
    public SXScalar asin(){
        return new SXScalar(SX.asin(sx));
    }
}
