/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package de.dhbw.rahmlab.casadi.impl.casadi;

/**
 *  Unified return status for solvers 
 */
public final class UnifiedReturnStatus {
  public final static de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus SOLVER_RET_SUCCESS = new de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus("SOLVER_RET_SUCCESS");
  public final static de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus SOLVER_RET_UNKNOWN = new de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus("SOLVER_RET_UNKNOWN");
  public final static de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus SOLVER_RET_LIMITED = new de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus("SOLVER_RET_LIMITED");
  public final static de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus SOLVER_RET_NAN = new de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus("SOLVER_RET_NAN");
  public final static de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus SOLVER_RET_INFEASIBLE = new de.dhbw.rahmlab.casadi.impl.casadi.UnifiedReturnStatus("SOLVER_RET_INFEASIBLE");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static UnifiedReturnStatus swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + UnifiedReturnStatus.class + " with value " + swigValue);
  }

  private UnifiedReturnStatus(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private UnifiedReturnStatus(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private UnifiedReturnStatus(String swigName, UnifiedReturnStatus swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static UnifiedReturnStatus[] swigValues = { SOLVER_RET_SUCCESS, SOLVER_RET_UNKNOWN, SOLVER_RET_LIMITED, SOLVER_RET_NAN, SOLVER_RET_INFEASIBLE };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}
