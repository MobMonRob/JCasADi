/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package de.dhbw.rahmlab.casadi.impl.std;

import de.dhbw.rahmlab.casadi.impl.*;
import static de.dhbw.rahmlab.casadi.impl.core__.*;
import java.util.function.LongConsumer;
import static de.dhbw.rahmlab.casadi.implUtil.WrapUtil.*;

public class StdVectorCasadiLinsol extends java.util.AbstractList<de.dhbw.rahmlab.casadi.impl.casadi.Linsol> implements java.util.RandomAccess {
  private final long swigCPtr;

  public StdVectorCasadiLinsol(long cPtr, boolean cMemoryOwn) {
    this.swigCPtr = cPtr;
	  if (cMemoryOwn) {
		  REGISTER_DELETION(this, cPtr, StdVectorCasadiLinsol::delete);
  	}
  }

  /**
  * <pre>
  * In C++, deleting a pointer twice is undefined behavior!
  * In C++, deleting an object polymorphically is undefined behavior if the base class does not declare it's constructor as virtual!
  * Using this baseclass constructor for subtypes prevents that.
  * </pre>
  */
  protected StdVectorCasadiLinsol(long cPtr, boolean cMemoryOwn, long subtype_cPtr, LongConsumer subtype_deleteFunction) {
    this.swigCPtr = cPtr;
	  if (cMemoryOwn) {
		  REGISTER_DELETION(this, subtype_cPtr, subtype_deleteFunction);
	  }
  }

  public static long getCPtr(StdVectorCasadiLinsol obj) {
    return obj.swigCPtr;
  }

  private static void delete(long swigCPtr) {
  de.dhbw.rahmlab.casadi.impl.core__JNI.delete_std_StdVectorCasadiLinsol(swigCPtr);
}

  public StdVectorCasadiLinsol(de.dhbw.rahmlab.casadi.impl.casadi.Linsol[] initialElements) {
    this();
    reserve(initialElements.length);

    for (de.dhbw.rahmlab.casadi.impl.casadi.Linsol element : initialElements) {
      add(element);
    }
  }

  public StdVectorCasadiLinsol(Iterable<de.dhbw.rahmlab.casadi.impl.casadi.Linsol> initialElements) {
    this();
    for (de.dhbw.rahmlab.casadi.impl.casadi.Linsol element : initialElements) {
      add(element);
    }
  }

  public de.dhbw.rahmlab.casadi.impl.casadi.Linsol get(int index) {
    return doGet(index);
  }

  public de.dhbw.rahmlab.casadi.impl.casadi.Linsol set(int index, de.dhbw.rahmlab.casadi.impl.casadi.Linsol e) {
    return doSet(index, e);
  }

  public boolean add(de.dhbw.rahmlab.casadi.impl.casadi.Linsol e) {
    modCount++;
    doAdd(e);
    return true;
  }

  public void add(int index, de.dhbw.rahmlab.casadi.impl.casadi.Linsol e) {
    modCount++;
    doAdd(index, e);
  }

  public de.dhbw.rahmlab.casadi.impl.casadi.Linsol remove(int index) {
    modCount++;
    return doRemove(index);
  }

  protected void removeRange(int fromIndex, int toIndex) {
    modCount++;
    doRemoveRange(fromIndex, toIndex);
  }

  public int size() {
    return doSize();
  }

  public StdVectorCasadiLinsol() {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorCasadiLinsol__SWIG_0(), true);
  }

  public StdVectorCasadiLinsol(de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiLinsol other) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorCasadiLinsol__SWIG_1(de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiLinsol.getCPtr(other), other), true);
  }

  public long capacity() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_isEmpty(swigCPtr, this);
  }

  public void clear() {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_clear(swigCPtr, this);
  }

  public StdVectorCasadiLinsol(int count, de.dhbw.rahmlab.casadi.impl.casadi.Linsol value) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorCasadiLinsol__SWIG_2(count, de.dhbw.rahmlab.casadi.impl.casadi.Linsol.getCPtr(value), value), true);
  }

  private int doSize() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_doSize(swigCPtr, this);
  }

  private void doAdd(de.dhbw.rahmlab.casadi.impl.casadi.Linsol x) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_doAdd__SWIG_0(swigCPtr, this, de.dhbw.rahmlab.casadi.impl.casadi.Linsol.getCPtr(x), x);
  }

  private void doAdd(int index, de.dhbw.rahmlab.casadi.impl.casadi.Linsol x) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_doAdd__SWIG_1(swigCPtr, this, index, de.dhbw.rahmlab.casadi.impl.casadi.Linsol.getCPtr(x), x);
  }

  private de.dhbw.rahmlab.casadi.impl.casadi.Linsol doRemove(int index) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.Linsol(de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_doRemove(swigCPtr, this, index), true);
  }

  private de.dhbw.rahmlab.casadi.impl.casadi.Linsol doGet(int index) {
	final long cPtr = de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_doGet(swigCPtr, this, index);
	if (cPtr == 0) return null;
	// false here indicates no ownership transfer to java
	de.dhbw.rahmlab.casadi.impl.casadi.Linsol proxy = new de.dhbw.rahmlab.casadi.impl.casadi.Linsol(cPtr, false);
	// public void extend(final Object toBeExtendedLifeTime, final Object extendedToLifeTime)
	LIFE_TIME_EXTENDER.extend(this, proxy);
	return proxy;
}

  private de.dhbw.rahmlab.casadi.impl.casadi.Linsol doSet(int index, de.dhbw.rahmlab.casadi.impl.casadi.Linsol val) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.Linsol(de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_doSet(swigCPtr, this, index, de.dhbw.rahmlab.casadi.impl.casadi.Linsol.getCPtr(val), val), true);
  }

  private void doRemoveRange(int fromIndex, int toIndex) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorCasadiLinsol_doRemoveRange(swigCPtr, this, fromIndex, toIndex);
  }

}