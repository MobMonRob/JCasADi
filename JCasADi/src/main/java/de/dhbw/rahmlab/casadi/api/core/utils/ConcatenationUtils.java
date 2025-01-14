package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;

/**
 * Utility class for performing various concatenation operations on CasADi vector objects.
 *
 * This class provides static methods to concatenate instances of MXVector, DMVector, and SXVector
 * using different concatenation strategies, including vertical, horizontal, diagonal, and block concatenation.
 *
 * Each method accepts a vector of a specific type and returns a new Wrapper containing the result
 * of the concatenation. Unsupported vector types will throw an IllegalArgumentException.
 */
public class ConcatenationUtils {

    /**
     * Vertically concatenates the given vector.
     *
     * @param vector The vector to concatenate.
     * @return A new Wrapper containing the result of the vertical concatenation.
     * @throws IllegalArgumentException if the vector type is unsupported.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T vertcat(Vector vector) {
        if (vector instanceof MXVector) {
            return (T) new MXWrapper(MX.vertcat(((MXVector) vector).getCasADiObject()));
        } else if (vector instanceof DMVector) {
            return (T) new DMWrapper(DM.vertcat(((DMVector) vector).getCasADiObject()));
        } else if (vector instanceof SXVector) {
            return (T) new SXWrapper(SX.vertcat(((SXVector) vector).getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + vector.getClass());
        }
    }

    /**
     * Concatenates the given vector into a single vector.
     *
     * @param vector The vector to concatenate.
     * @return A new Wrapper containing the result of the vertical concatenation.
     * @throws IllegalArgumentException if the vector type is unsupported.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T veccat(Vector vector) {
        if (vector instanceof MXVector) {
            return (T) new MXWrapper(MX.veccat(((MXVector) vector).getCasADiObject()));
        } else if (vector instanceof DMVector) {
            return (T) new DMWrapper(DM.veccat(((DMVector) vector).getCasADiObject()));
        } else if (vector instanceof SXVector) {
            return (T) new SXWrapper(SX.veccat(((SXVector) vector).getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + vector.getClass());
        }
    }

    /**
     * Horizontally concatenates the given vector.
     *
     * @param vector The vector to concatenate.
     * @return A new Wrapper containing the result of the horizontal concatenation.
     * @throws IllegalArgumentException if the vector type is unsupported.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T horzcat(Vector vector) {
        if (vector instanceof MXVector) {
            return (T) new MXWrapper(MX.horzcat(((MXVector) vector).getCasADiObject()));
        } else if (vector instanceof DMVector) {
            return (T) new DMWrapper(DM.horzcat(((DMVector) vector).getCasADiObject()));
        } else if (vector instanceof SXVector) {
            return (T) new SXWrapper(SX.horzcat(((SXVector) vector).getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + vector.getClass());
        }
    }

    /**
     * Creates a diagonal concatenation of the given vector.
     *
     * @param vector The vector to concatenate.
     * @return A new Wrapper containing the result of the diagonal concatenation.
     * @throws IllegalArgumentException if the vector type is unsupported.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T diagcat(Vector vector) {
        if (vector instanceof MXVector) {
            return (T) new MXWrapper(MX.diagcat(((MXVector) vector).getCasADiObject()));
        } else if (vector instanceof DMVector) {
            return (T) new DMWrapper(DM.diagcat(((DMVector) vector).getCasADiObject()));
        } else if (vector instanceof SXVector) {
            return (T) new SXWrapper(SX.diagcat(((SXVector) vector).getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + vector.getClass());
        }
    }

    // TODO: Updated method
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T blockcat(Object collection) {
        if (collection instanceof MXVectorCollection) {
            return (T) new MXWrapper(MX.blockcat(((MXVectorCollection) collection).getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + collection.getClass());
        }
    }

}
