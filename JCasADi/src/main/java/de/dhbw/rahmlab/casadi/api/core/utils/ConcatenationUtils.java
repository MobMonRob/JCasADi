package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Collection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;

import java.util.Arrays;

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
     * Vertically concatenates the given array of Wrapper objects.
     *
     * <p>This method checks the types of the provided Wrapper objects and performs a vertical
     * concatenation based on their type. It supports MXWrapper, SXWrapper, and DMWrapper types.</p>
     *
     * @param vector The array of Wrapper objects to concatenate.
     * @return A new Wrapper containing the result of the vertical concatenation.
     * @throws IllegalArgumentException if the array contains unsupported or mixed Wrapper types.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T vertcat(T... vector) throws Exception {
        int arrayType = checkArrayTypes(vector);
        if (arrayType == 1) {
            MXVector mxVector = new MXVector();
            Arrays.stream(vector).forEach(element -> mxVector.add((MXWrapper) element));
            return (T) new MXWrapper(MX.vertcat(mxVector.getCasADiObject()));
        } else if (arrayType == 2) {
            DMVector dmVector = new DMVector();
            Arrays.stream(vector).forEach(element -> dmVector.add((DMWrapper) element));
            return (T) new DMWrapper(DM.vertcat(dmVector.getCasADiObject()));
        } else if (arrayType == 3) {
            SXVector sxVector = new SXVector();
            Arrays.stream(vector).forEach(element -> sxVector.add((SXWrapper) element));
            return (T) new SXWrapper(SX.vertcat(sxVector.getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + vector.getClass());
        }
    }

    /**
     * Concatenates the given array of Wrapper objects into a single vector.
     *
     * <p>This method checks the types of the provided Wrapper objects and performs a concatenation
     * based on their type. It supports MXWrapper, SXWrapper, and DMWrapper types.</p>
     *
     * @param vector The array of Wrapper objects to concatenate.
     * @return A new Wrapper containing the result of the concatenation.
     * @throws IllegalArgumentException if the array contains unsupported or mixed Wrapper types.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T veccat(T... vector) throws Exception {
        int arrayType = checkArrayTypes(vector);
        if (arrayType == 1) {
            MXVector mxVector = new MXVector();
            Arrays.stream(vector).forEach(element -> mxVector.add((MXWrapper) element));
            return (T) new MXWrapper(MX.veccat(mxVector.getCasADiObject()));
        } else if (arrayType == 2) {
            DMVector dmVector = new DMVector();
            Arrays.stream(vector).forEach(element -> dmVector.add((DMWrapper) element));
            return (T) new DMWrapper(DM.veccat(dmVector.getCasADiObject()));
        } else if (arrayType == 3) {
            SXVector sxVector = new SXVector();
            Arrays.stream(vector).forEach(element -> sxVector.add((SXWrapper) element));
            return (T) new SXWrapper(SX.veccat(sxVector.getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + vector.getClass());
        }
    }

    /**
     * Horizontally concatenates the given array of Wrapper objects.
     *
     * <p>This method checks the types of the provided Wrapper objects and performs a horizontal
     * concatenation based on their type. It supports MXWrapper, SXWrapper, and DMWrapper types.</p>
     *
     * @param vector The array of Wrapper objects to concatenate.
     * @return A new Wrapper containing the result of the horizontal concatenation.
     * @throws IllegalArgumentException if the array contains unsupported or mixed Wrapper types.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T horzcat(T... vector) throws Exception {
        int arrayType = checkArrayTypes(vector);
        if (arrayType == 1) {
            MXVector mxVector = new MXVector();
            Arrays.stream(vector).forEach(element -> mxVector.add((MXWrapper) element));
            return (T) new MXWrapper(MX.horzcat(mxVector.getCasADiObject()));
        } else if (arrayType == 2) {
            DMVector dmVector = new DMVector();
            Arrays.stream(vector).forEach(element -> dmVector.add((DMWrapper) element));
            return (T) new DMWrapper(DM.horzcat(dmVector.getCasADiObject()));
        } else if (arrayType == 3) {
            SXVector sxVector = new SXVector();
            Arrays.stream(vector).forEach(element -> sxVector.add((SXWrapper) element));
            return (T) new SXWrapper(SX.horzcat(sxVector.getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + vector.getClass());
        }
    }

    /**
     * Creates a diagonal concatenation of the given array of Wrapper objects.
     *
     * <p>This method checks the types of the provided Wrapper objects and performs a diagonal
     * concatenation based on their type. It supports MXWrapper, SXWrapper, and DMWrapper types.</p>
     *
     * @param vector The array of Wrapper objects to concatenate.
     * @return A new Wrapper containing the result of the diagonal concatenation.
     * @throws IllegalArgumentException if the array contains unsupported or mixed Wrapper types.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper> T diagcat(T... vector) throws Exception {
        int arrayType = checkArrayTypes(vector);
        if (arrayType == 1) {
            MXVector mxVector = new MXVector();
            Arrays.stream(vector).forEach(element -> mxVector.add((MXWrapper) element));
            return (T) new MXWrapper(MX.diagcat(mxVector.getCasADiObject()));
        } else if (arrayType == 2) {
            DMVector dmVector = new DMVector();
            Arrays.stream(vector).forEach(element -> dmVector.add((DMWrapper) element));
            return (T) new DMWrapper(DM.diagcat(dmVector.getCasADiObject()));
        } else if (arrayType == 3) {
            SXVector sxVector = new SXVector();
            Arrays.stream(vector).forEach(element -> sxVector.add((SXWrapper) element));
            return (T) new SXWrapper(SX.diagcat(sxVector.getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + vector.getClass());
        }
    }

    /**
     * Creates a block concatenation of the given collection.
     *
     * @param collection The collection of CasADi objects to concatenate.
     * @return A new Wrapper containing the result of the block concatenation.
     * @throws IllegalArgumentException if the collection type is unsupported.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Wrapper, U extends Collection> T blockcat(U collection) {
        if (collection instanceof MXVectorCollection) {
            return (T) new MXWrapper(MX.blockcat(((MXVectorCollection) collection).getCasADiObject()));
        } else if (collection instanceof DMVectorCollection) {
            return (T) new DMWrapper(DM.blockcat(((DMVectorCollection) collection).getCasADiObject()));
        } else if (collection instanceof SXVectorCollection) {
            return (T) new SXWrapper(SX.blockcat(((SXVectorCollection) collection).getCasADiObject()));
        } else {
            throw new IllegalArgumentException("Unsupported CasADi object type: " + collection.getClass());
        }
    }

    /**
     * Checks the types of objects in the provided array to ensure that all objects
     * are of the same specified type: MXWrapper, SXWrapper, or DMWrapper.
     *
     * <p>This method iterates through the array and determines the type of the first
     * object it encounters. If it finds any object of a different type or an unknown
     * type, it throws an exception. If the array is empty, it returns 0.</p>
     *
     * @param arr an array of objects to be checked
     * @return an integer representing the type of objects in the array:
     *         <ul>
     *         <li>1 if all objects are of type MXWrapper</li>
     *         <li>2 if all objects are of type SXWrapper</li>
     *         <li>3 if all objects are of type DMWrapper</li>
     *         <li>0 if the array is empty</li>
     *         </ul>
     * @throws Exception if the array contains mixed object types or unknown object types
     */
    private static int checkArrayTypes(Object[] arr) throws Exception {
        Integer type = null;

        for (Object obj : arr) {
            if (obj instanceof MXWrapper) {
                if (type == null) {
                    type = 1; // MXWrapper
                } else if (type != 1) {
                    throw new Exception("Error: Mixed object types or other types found.");
                }
            } else if (obj instanceof SXWrapper) {
                if (type == null) {
                    type = 2; // SXWrapper
                } else if (type != 2) {
                    throw new Exception("Error: Mixed object types or other types found.");
                }
            } else if (obj instanceof DMWrapper) {
                if (type == null) {
                    type = 3; // DMWrapper
                } else if (type != 3) {
                    throw new Exception("Error: Mixed object types or other types found.");
                }
            } else {
                throw new Exception("Error: Unknown object type found.");
            }
        }

        return type != null ? type : 0; // 0 if array is Empty
    }

}
