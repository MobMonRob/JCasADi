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

public class StdVectorVectorMX extends java.util.AbstractList<de.dhbw.rahmlab.casadi.impl.std.StdVectorMX> implements java.util.RandomAccess {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  public StdVectorVectorMX(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
	if (cMemoryOwn) {
		REGISTER_DELETION(this, this.swigCPtr, StdVectorVectorMX::delete);
	}
  }

  /**
  * <pre>
  * In C++, deleting a pointer twice is undefined behavior!
  * In C++, deleting an object polymorphically is undefined behavior if the base class does not declare it's constructor as virtual!
  * Using this baseclass constructor for subtypes prevents that.
  * </pre>
  */
  protected StdVectorVectorMX(long cPtr, boolean cMemoryOwn, long subtype_cPtr, LongConsumer subtype_deleteFunction) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
	if (cMemoryOwn) {
		REGISTER_DELETION(this, subtype_cPtr, subtype_deleteFunction);
	}
  }

  public static long getCPtr(StdVectorVectorMX obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        StdVectorVectorMX.delete(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  protected void finalize() {
  }

  private static void delete(long swigCPtr) {
	synchronized (GLOBAL_DESTRUCTOR_LOCK) {
        de.dhbw.rahmlab.casadi.impl.core__JNI.delete_std_StdVectorVectorMX(swigCPtr);
	}
}

  public StdVectorVectorMX(de.dhbw.rahmlab.casadi.impl.std.StdVectorMX[] initialElements) {
    this();
    reserve(initialElements.length);

    for (de.dhbw.rahmlab.casadi.impl.std.StdVectorMX element : initialElements) {
      add(element);
    }
  }

  public StdVectorVectorMX(Iterable<de.dhbw.rahmlab.casadi.impl.std.StdVectorMX> initialElements) {
    this();
    for (de.dhbw.rahmlab.casadi.impl.std.StdVectorMX element : initialElements) {
      add(element);
    }
  }

  public de.dhbw.rahmlab.casadi.impl.std.StdVectorMX get(int index) {
    return doGet(index);
  }

  public de.dhbw.rahmlab.casadi.impl.std.StdVectorMX set(int index, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX e) {
    return doSet(index, e);
  }

  public boolean add(de.dhbw.rahmlab.casadi.impl.std.StdVectorMX e) {
    modCount++;
    doAdd(e);
    return true;
  }

  public void add(int index, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX e) {
    modCount++;
    doAdd(index, e);
  }

  public de.dhbw.rahmlab.casadi.impl.std.StdVectorMX remove(int index) {
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

  public StdVectorVectorMX() {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorVectorMX__SWIG_0(), true);
  }

  public StdVectorVectorMX(de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorMX other) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorVectorMX__SWIG_1(de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorMX.getCPtr(other), other), true);
  }

  public long capacity() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_capacity(swigCPtr, this);
  }

  public void reserve(long n) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_reserve(swigCPtr, this, n);
  }

  public boolean isEmpty() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_isEmpty(swigCPtr, this);
  }

  public void clear() {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_clear(swigCPtr, this);
  }

  public StdVectorVectorMX(int count, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX value) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_std_StdVectorVectorMX__SWIG_2(count, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX.getCPtr(value), value), true);
  }

  private int doSize() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_doSize(swigCPtr, this);
  }

  private void doAdd(de.dhbw.rahmlab.casadi.impl.std.StdVectorMX x) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_doAdd__SWIG_0(swigCPtr, this, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX.getCPtr(x), x);
  }

  private void doAdd(int index, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX x) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_doAdd__SWIG_1(swigCPtr, this, index, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX.getCPtr(x), x);
  }

  private de.dhbw.rahmlab.casadi.impl.std.StdVectorMX doRemove(int index) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorMX(de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_doRemove(swigCPtr, this, index), true);
  }

  private de.dhbw.rahmlab.casadi.impl.std.StdVectorMX doGet(int index) {
	final long cPtr = de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_doGet(swigCPtr, this, index);
	if (cPtr == 0) return null;
	// false here indicates no ownership transfer to java
	de.dhbw.rahmlab.casadi.impl.std.StdVectorMX proxy = new de.dhbw.rahmlab.casadi.impl.std.StdVectorMX(cPtr, false);
	// public void extend(final Object toBeExtendedLifeTime, final Object extendedToLifeTime)
	LIFE_TIME_EXTENDER.extend(this, proxy);
	return proxy;
}

  private de.dhbw.rahmlab.casadi.impl.std.StdVectorMX doSet(int index, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX val) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorMX(de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_doSet(swigCPtr, this, index, de.dhbw.rahmlab.casadi.impl.std.StdVectorMX.getCPtr(val), val), true);
  }

  private void doRemoveRange(int fromIndex, int toIndex) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.std_StdVectorVectorMX_doRemoveRange(swigCPtr, this, fromIndex, toIndex);
  }

}