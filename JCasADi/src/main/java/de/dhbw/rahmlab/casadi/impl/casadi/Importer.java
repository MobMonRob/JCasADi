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

/**
 *  Importer<br>
 * <br>
 *       Just-in-time compilation of code<br>
 * <br>
 *       <br>
 *       <br>
 * <br>
 *       @author Joris Gillis<br>
 *       2015<br>
 * <br>
 *       
 */
public class Importer implements ISharedObject {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  public Importer(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
	if (cMemoryOwn) {
		REGISTER_DELETION(this, this.swigCPtr, Importer::delete);
	}
  }

  /**
  * <pre>
  * In C++, deleting a pointer twice is undefined behavior!
  * In C++, deleting an object polymorphically is undefined behavior if the base class does not declare it's constructor as virtual!
  * Using this baseclass constructor for subtypes prevents that.
  * </pre>
  */
  protected Importer(long cPtr, boolean cMemoryOwn, long subtype_cPtr, LongConsumer subtype_deleteFunction) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
	if (cMemoryOwn) {
		REGISTER_DELETION(this, subtype_cPtr, subtype_deleteFunction);
	}
  }

  public static long getCPtr(Importer obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        Importer.delete(swigCPtr);
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
        de.dhbw.rahmlab.casadi.impl.core__JNI.delete_casadi_Importer(swigCPtr);
	}
}

  public long ISharedObject_GetInterfaceCPtr() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_ISharedObject_GetInterfaceCPtr(swigCPtr);
  }

  /**
   *  Get type name<br>
   * <br>
   *         
   */
  public static String type_name() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_type_name();
  }

  /**
   *  Default constructor
   */
  public Importer() {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_Importer__SWIG_0(), true);
  }

  /**
   *  Importer factory
   */
  public Importer(String name, String compiler, de.dhbw.rahmlab.casadi.impl.std.Dict opts) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_Importer__SWIG_1(name, compiler, de.dhbw.rahmlab.casadi.impl.std.Dict.getCPtr(opts), opts), true);
  }

  /**
   *  Importer factory
   */
  public Importer(String name, String compiler) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_Importer__SWIG_2(name, compiler), true);
  }

  /**
   *  Check if a particular cast is allowed
   */
  public static boolean test_cast(SWIGTYPE_p_casadi__SharedObjectInternal ptr) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_test_cast(SWIGTYPE_p_casadi__SharedObjectInternal.getCPtr(ptr));
  }

  /**
   *  Check if a plugin is available
   */
  public static boolean has_plugin(String name) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_has_plugin(name);
  }

  /**
   *  Explicitly load a plugin dynamically
   */
  public static void load_plugin(String name) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_load_plugin(name);
  }

  /**
   *  Get solver specific documentation
   */
  public static String doc(String name) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_doc(name);
  }

  /**
   *  Query plugin name
   */
  public String plugin_name() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_plugin_name(swigCPtr, this);
  }

  public boolean has_function(String symname) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_has_function(swigCPtr, this, symname);
  }

  /**
   *  Conditional comment: INTERNAL End of conditional comment.Does a meta entry exist?<br>
   * <br>
   *         
   */
  public boolean has_meta(String cmd, long ind) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_has_meta__SWIG_0(swigCPtr, this, cmd, ind);
  }

  /**
   *  Conditional comment: INTERNAL End of conditional comment.Does a meta entry exist?<br>
   * <br>
   *         
   */
  public boolean has_meta(String cmd) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_has_meta__SWIG_1(swigCPtr, this, cmd);
  }

  /**
   *  Get entry as a text<br>
   * <br>
   *         
   */
  public String get_meta(String cmd, long ind) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_get_meta__SWIG_0(swigCPtr, this, cmd, ind);
  }

  /**
   *  Get entry as a text<br>
   * <br>
   *         
   */
  public String get_meta(String cmd) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_get_meta__SWIG_1(swigCPtr, this, cmd);
  }

  /**
   *  Check if a function is inlined
   */
  public boolean inlined(String symname) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_inlined(swigCPtr, this, symname);
  }

  /**
   *  Get the function body, if inlined
   */
  public String body(String symname) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_body(swigCPtr, this, symname);
  }

  /**
   *  Get library name
   */
  public String library() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_library(swigCPtr, this);
  }

  /**
   *  Serialize an object<br>
   * <br>
   *         
   */
  public void serialize(SWIGTYPE_p_casadi__SerializingStream s) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_serialize(swigCPtr, this, SWIGTYPE_p_casadi__SerializingStream.getCPtr(s));
  }

  /**
   *  Deserialize with type disambiguation<br>
   * <br>
   *         
   */
  public static de.dhbw.rahmlab.casadi.impl.casadi.Importer deserialize(SWIGTYPE_p_casadi__DeserializingStream s) {
    return new de.dhbw.rahmlab.casadi.impl.casadi.Importer(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_deserialize(SWIGTYPE_p_casadi__DeserializingStream.getCPtr(s)), true);
  }

  /**
   *  Get class name<br>
   * <br>
   *         
   */
  public String class_name() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_class_name(swigCPtr, this);
  }

  /**
   *  Print a description of the object
   */
  public void disp(SWIGTYPE_p_std__ostream stream, boolean more) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_disp__SWIG_0(swigCPtr, this, SWIGTYPE_p_std__ostream.getCPtr(stream), more);
  }

  /**
   *  Print a description of the object
   */
  public void disp(SWIGTYPE_p_std__ostream stream) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_disp__SWIG_1(swigCPtr, this, SWIGTYPE_p_std__ostream.getCPtr(stream));
  }

  /**
   *  Get string representation
   */
  public String toString(boolean more) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_toString__SWIG_0(swigCPtr, this, more);
  }

  /**
   *  Get string representation
   */
  public String toString() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_toString__SWIG_1(swigCPtr, this);
  }

  /**
   *  Conditional comment: INTERNAL Print the pointer to the internal class
   */
  public void print_ptr(SWIGTYPE_p_std__ostream stream) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_print_ptr__SWIG_0(swigCPtr, this, SWIGTYPE_p_std__ostream.getCPtr(stream));
  }

  /**
   *  Conditional comment: INTERNAL Print the pointer to the internal class
   */
  public void print_ptr() {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_print_ptr__SWIG_1(swigCPtr, this);
  }

  /**
   *  End of conditional comment.Is a null pointer?
   */
  public boolean is_null() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer_is_null(swigCPtr, this);
  }

  /**
   *  Returns a number that is unique for a given Node.<br>
   * <br>
   * If the Object does not point to any node, "0" is returned.<br>
   * <br>
   *         
   */
  public long __hash__() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_Importer___hash__(swigCPtr, this);
  }

  public Importer(de.dhbw.rahmlab.casadi.impl.casadi.Importer other) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_Importer__SWIG_3(de.dhbw.rahmlab.casadi.impl.casadi.Importer.getCPtr(other), other), true);
  }

}