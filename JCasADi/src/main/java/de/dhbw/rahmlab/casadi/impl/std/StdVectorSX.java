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

public class StdVectorSX extends java.util.AbstractList<de.dhbw.rahmlab.casadi.impl.casadi.SX> implements java.util.RandomAccess {
  private final long swigCPtr;

  public StdVectorSX(long cPtr, boolean cMemoryOwn) {
    this.swigCPtr = cPtr;
	  if (cMemoryOwn) {
		  REGISTER_DELETION(this, cPtr, StdVectorSX::delete);
  	}
  }

  /**
  * <pre>
  * In C++, deleting a pointer twice is undefined behavior!
  * In C++, deleting an object polymorphically is undefined behavior if the base class does not declare it's constructor as virtual!
  * Using this baseclass constructor for subtypes prevents that.
  * </pre>
  */
  protected StdVectorSX(long cPtr, boolean cMemoryOwn, long subtype_cPtr, LongConsumer subtype_deleteFunction) {
    this.swigCPtr = cPtr;
	  if (cMemoryOwn) {
		  REGISTER_DELETION(this, subtype_cPtr, subtype_deleteFunction);
	  }
  }

  public static long getCPtr(StdVectorSX obj) {
    return obj.swigCPtr;
  }

  private static void delete(long swigCPtr) {
  de.dhbw.rahmlab.casadi.impl.core__JNI.delete_std_StdVectorSX(swigCPtr);
}

  public StdVectorSX(de.dhbw.rahmlab.casadi.impl.casadi.SX[] initialElements) {
    this();
    reserve(initialElements.length);

    for (de.dhbw.rahmlab.casadi.impl.casadi.SX element : initialElements) {
      add(element);
    }
  }

  public StdVectorSX(Iterable<de.dhbw.rahmlab.casadi.impl.casadi.SX> initialElements) {
    this();
    for (de.dhbw.rahmlab.casadi.impl.casadi.SX element : initialElements) {
      add(element);
    }
  }

  public de.dhbw.rahmlab.casadi.impl.casadi.SX get(int index) {
    return doGet(index);
  }

  public de.dhbw.rahmlab.casadi.impl.casadi.SX set(int index, de.dhbw.rahmlab.casadi.impl.casadi.SX e) {
    return doSet(index, e);
  }

  public boolean add(de.dhbw.rahmlab.casadi.impl.casadi.SX e) {
    modCount++;
    doAdd(e);
    return true;
  }

  public void add(int index, de.dhbw.rahmlab.casadi.impl.casadi.SX e) {
    modCount++;
    doAdd(index, e);
  }

  public de.dhbw.rahmlab.casadi.impl.casadi.SX remove(int index) {
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

  public StdVectorSX() {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorSX__SWIG_0(), true);
  }

  public StdVectorSX(de.dhbw.rahmlab.casadi.impl.std.StdVectorSX other) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorSX__SWIG_1(de.dhbw.rahmlab.casadi.impl.std.StdVectorSX.getCPtr(other), other), true);
  }

  public long capacity() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_isEmpty(swigCPtr, this);
  }

  public void clear() {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_clear(swigCPtr, this);
  }

  public StdVectorSX(int count, de.dhbw.rahmlab.casadi.impl.casadi.SX value) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorSX__SWIG_2(count, de.dhbw.rahmlab.casadi.impl.casadi.SX.getCPtr(value), value), true);
  }

  private int doSize() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_doSize(swigCPtr, this);
  }

  private void doAdd(de.dhbw.rahmlab.casadi.impl.casadi.SX x) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_doAdd__SWIG_0(swigCPtr, this, de.dhbw.rahmlab.casadi.impl.casadi.SX.getCPtr(x), x);
  }

  private void doAdd(int index, de.dhbw.rahmlab.casadi.impl.casadi.SX x) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_doAdd__SWIG_1(swigCPtr, this, index, de.dhbw.rahmlab.casadi.impl.casadi.SX.getCPtr(x), x);
  }

  private de.dhbw.rahmlab.casadi.impl.casadi.SX doRemove(int index) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.SX(de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_doRemove(swigCPtr, this, index), true);
  }

  private de.dhbw.rahmlab.casadi.impl.casadi.SX doGet(int index) {
	final long cPtr = de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_doGet(swigCPtr, this, index);
	if (cPtr == 0) return null;
	// false here indicates no ownership transfer to java
	de.dhbw.rahmlab.casadi.impl.casadi.SX proxy = new de.dhbw.rahmlab.casadi.impl.casadi.SX(cPtr, false);
	// public void extend(final Object toBeExtendedLifeTime, final Object extendedToLifeTime)
	LIFE_TIME_EXTENDER.extend(this, proxy);
	return proxy;
}

  private de.dhbw.rahmlab.casadi.impl.casadi.SX doSet(int index, de.dhbw.rahmlab.casadi.impl.casadi.SX val) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.SX(de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_doSet(swigCPtr, this, index, de.dhbw.rahmlab.casadi.impl.casadi.SX.getCPtr(val), val), true);
  }

  private void doRemoveRange(int fromIndex, int toIndex) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorSX_doRemoveRange(swigCPtr, this, fromIndex, toIndex);
  }

}