/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package de.dhbw.rahmlab.casadi.impl.casadi;

import de.dhbw.rahmlab.casadi.impl.*;
import static de.dhbw.rahmlab.casadi.impl.core__.*;
import java.util.function.LongConsumer;
import static de.dhbw.rahmlab.casadi.implUtil.WrapUtil.*;

/**
 *  Sparsity interface class<br>
 * <br>
 *       This is a common base class for GenericMatrix (i.e. MX and Matrix&lt;&gt;) and Sparsity, introducing a<br>
 *       uniform syntax and implementing common functionality using the curiously recurring template pattern<br>
 *       (CRTP) idiom.<br>
 * <br>
 * <br>
 *       @author Joel Andersson<br>
 *       2014<br>
 * <br>
 *       
 */
public class DmSparsityInterface implements IDmSparsityInterface {
  private final long swigCPtr;

  public DmSparsityInterface(long cPtr, boolean cMemoryOwn) {
    this.swigCPtr = cPtr;
	  if (cMemoryOwn) {
		  REGISTER_DELETION(this, cPtr, DmSparsityInterface::delete);
  	}
  }

  /**
  * <pre>
  * In C++, deleting a pointer twice is undefined behavior!
  * In C++, deleting an object polymorphically is undefined behavior if the base class does not declare it's constructor as virtual!
  * Using this baseclass constructor for subtypes prevents that.
  * </pre>
  */
  protected DmSparsityInterface(long cPtr, boolean cMemoryOwn, long subtype_cPtr, LongConsumer subtype_deleteFunction) {
    this.swigCPtr = cPtr;
	  if (cMemoryOwn) {
		  REGISTER_DELETION(this, subtype_cPtr, subtype_deleteFunction);
	  }
  }

  public static long getCPtr(DmSparsityInterface obj) {
    return obj.swigCPtr;
  }

  private static void delete(long swigCPtr) {
  de.dhbw.rahmlab.casadi.impl.core__JNI.delete_casadi_DmSparsityInterface(swigCPtr);
}

  public long IDmSparsityInterface_GetInterfaceCPtr() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_IDmSparsityInterface_GetInterfaceCPtr(swigCPtr);
  }

  /**
   *  Conditional comment: CLUTTER
   */
  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDM blocksplit(de.dhbw.rahmlab.casadi.impl.casadi.DM x, de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt vert_offset, de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt horz_offset) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_blocksplit__SWIG_0(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt.getCPtr(vert_offset), vert_offset, de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt.getCPtr(horz_offset), horz_offset), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDM blocksplit(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long vert_incr, long horz_incr) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_blocksplit__SWIG_1(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, vert_incr, horz_incr), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.casadi.DM veccat(de.dhbw.rahmlab.casadi.impl.std.StdVectorDM x) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.DM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_veccat(de.dhbw.rahmlab.casadi.impl.std.StdVectorDM.getCPtr(x), x), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.casadi.DM vec(de.dhbw.rahmlab.casadi.impl.casadi.DM x) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.DM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_vec(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.casadi.DM repmat(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long n, long m) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.DM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_repmat__SWIG_0(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, n, m), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.casadi.DM repmat(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long n) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.DM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_repmat__SWIG_1(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, n), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt offset(de.dhbw.rahmlab.casadi.impl.std.StdVectorDM v, boolean vert) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_offset__SWIG_0(de.dhbw.rahmlab.casadi.impl.std.StdVectorDM.getCPtr(v), v, vert), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt offset(de.dhbw.rahmlab.casadi.impl.std.StdVectorDM v) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_offset__SWIG_1(de.dhbw.rahmlab.casadi.impl.std.StdVectorDM.getCPtr(v), v), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorDM diagsplit_(de.dhbw.rahmlab.casadi.impl.casadi.DM x, de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt output_offset) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_diagsplit___SWIG_0(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt.getCPtr(output_offset), output_offset), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorDM diagsplit_(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long incr) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_diagsplit___SWIG_1(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, incr), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorDM diagsplit_(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long incr1, long incr2) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_diagsplit___SWIG_2(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, incr1, incr2), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.casadi.DM mtimes_(de.dhbw.rahmlab.casadi.impl.std.StdVectorDM args) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.DM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_mtimes_(de.dhbw.rahmlab.casadi.impl.std.StdVectorDM.getCPtr(args), args), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorDM horzsplit_(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long incr) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_horzsplit_(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, incr), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorDM vertsplit_(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long incr) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_vertsplit_(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, incr), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorDM horzsplit_n(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long n) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_horzsplit_n(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, n), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.std.StdVectorDM vertsplit_n(de.dhbw.rahmlab.casadi.impl.casadi.DM x, long n) {
    return new de.dhbw.rahmlab.casadi.impl.std.StdVectorDM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_vertsplit_n(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(x), x, n), true);
  }

  public static de.dhbw.rahmlab.casadi.impl.casadi.DM repmat(de.dhbw.rahmlab.casadi.impl.casadi.DM A, SWIGTYPE_p_std__pairT_long_long_long_long_t rc) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.DM(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_DmSparsityInterface_repmat__SWIG_2(de.dhbw.rahmlab.casadi.impl.casadi.DM.getCPtr(A), A, SWIGTYPE_p_std__pairT_long_long_long_long_t.getCPtr(rc)), true);
  }

  public DmSparsityInterface() {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_DmSparsityInterface__SWIG_0(), true);
  }

  public DmSparsityInterface(de.dhbw.rahmlab.casadi.impl.casadi.IDmSparsityInterface other) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_DmSparsityInterface__SWIG_1(other.IDmSparsityInterface_GetInterfaceCPtr(), other), true);
  }

}