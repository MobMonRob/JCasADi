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

/**
 *  Helper class for C code generation<br>
 * <br>
 *       @author Joel Andersson<br>
 *       2016<br>
 * <br>
 *       
 */
public class CodeGenerator {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  public CodeGenerator(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr(CodeGenerator obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        de.dhbw.rahmlab.casadi.impl.core__JNI.delete_casadi_CodeGenerator(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  /**
   *  Constructor
   */
  public CodeGenerator(String name, de.dhbw.rahmlab.casadi.impl.std.Dict opts) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_CodeGenerator__SWIG_0(name, de.dhbw.rahmlab.casadi.impl.std.Dict.getCPtr(opts), opts), true);
  }

  /**
   *  Constructor
   */
  public CodeGenerator(String name) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_CodeGenerator__SWIG_1(name), true);
  }

  /**
   *  Add a function (name generated)
   */
  public void add(de.dhbw.rahmlab.casadi.impl.casadi.Function f, boolean with_jac_sparsity) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_CodeGenerator_add__SWIG_0(swigCPtr, this, de.dhbw.rahmlab.casadi.impl.casadi.Function.getCPtr(f), f, with_jac_sparsity);
  }

  /**
   *  Add a function (name generated)
   */
  public void add(de.dhbw.rahmlab.casadi.impl.casadi.Function f) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_CodeGenerator_add__SWIG_1(swigCPtr, this, de.dhbw.rahmlab.casadi.impl.casadi.Function.getCPtr(f), f);
  }

  /**
   *  Generate a file, return code as string
   */
  public String dump() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_CodeGenerator_dump(swigCPtr, this);
  }

  /**
   *  Generate file(s)<br>
   * <br>
   *       The "prefix" argument will be prepended to the generated files and may<br>
   *       be a directory or a file prefix.<br>
   *       returns the filename<br>
   * <br>
   *         
   */
  public String generate(String prefix) {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_CodeGenerator_generate__SWIG_0(swigCPtr, this, prefix);
  }

  /**
   *  Generate file(s)<br>
   * <br>
   *       The "prefix" argument will be prepended to the generated files and may<br>
   *       be a directory or a file prefix.<br>
   *       returns the filename<br>
   * <br>
   *         
   */
  public String generate() {
    return de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_CodeGenerator_generate__SWIG_1(swigCPtr, this);
  }

  /**
   *  Add an include file optionally using a relative path "..." instead of an absolute path &lt;...&gt;
   */
  public void add_include(String new_include, boolean relative_path, String use_ifdef) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_CodeGenerator_add_include__SWIG_0(swigCPtr, this, new_include, relative_path, use_ifdef);
  }

  /**
   *  Add an include file optionally using a relative path "..." instead of an absolute path &lt;...&gt;
   */
  public void add_include(String new_include, boolean relative_path) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_CodeGenerator_add_include__SWIG_1(swigCPtr, this, new_include, relative_path);
  }

  /**
   *  Add an include file optionally using a relative path "..." instead of an absolute path &lt;...&gt;
   */
  public void add_include(String new_include) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_CodeGenerator_add_include__SWIG_2(swigCPtr, this, new_include);
  }

}