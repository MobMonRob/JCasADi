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

public class StringDeserializer extends de.dhbw.rahmlab.casadi.impl.casadi.DeserializerBase {
  private transient long swigCPtr;

  public StringDeserializer(long cPtr, boolean cMemoryOwn) {
    super(de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_StringDeserializer_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  public static long getCPtr(StringDeserializer obj) {
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
        de.dhbw.rahmlab.casadi.impl.core__JNI.delete_casadi_StringDeserializer(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  /**
   *  Advanced deserialization of CasADi objects<br>
   * <br>
   * @see StringDeserializer<br>
   * <br>
   *         
   */
  public StringDeserializer(String string) {
    this(de.dhbw.rahmlab.casadi.impl.core__JNI.new_casadi_StringDeserializer(string), true);
  }

  /**
   *  Sets the string to deserialize objects from<br>
   * <br>
   *         
   */
  public void decode(String string) {
    de.dhbw.rahmlab.casadi.impl.core__JNI.casadi_StringDeserializer_decode(swigCPtr, this, string);
  }

}