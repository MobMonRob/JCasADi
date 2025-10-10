package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.DmStatic;
import de.dhbw.rahmlab.casadi.MxStatic;
import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXVectorCollection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.casadi.SparsitySparsityInterface;

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
     * Vertically concatenates an array of MXWrapper objects into a single MXWrapper.
     *
     * This method takes multiple MXWrapper instances and combines them into a single
     * MXWrapper by creating an MXVector, adding all the provided MXWrapper instances
     * to it, and then performing the vertical concatenation using the CasADi library.
     *
     * @param vector An array of MXWrapper objects to be concatenated.
     * @return A new MXWrapper containing the vertically concatenated result of the input MXWrapper objects.
     */
    public static MXWrapper vertcat(MXWrapper... vector) {
        MXVector mxVector = new MXVector();
        mxVector.addAll(Arrays.asList(vector));
        return new MXWrapper(MxStatic.vertcat(mxVector.getCasADiObject()));
    }

    /**
     * Vertically concatenates an array of DMWrapper objects into a single DMWrapper.
     *
     * This method takes multiple DMWrapper instances and combines them into a single
     * DMWrapper by creating a DMVector, adding all the provided DMWrapper instances
     * to it, and then performing the vertical concatenation using the CasADi library.
     *
     * @param vector An array of DMWrapper objects to be concatenated.
     * @return A new DMWrapper containing the vertically concatenated result of the input DMWrapper objects.
     */
    public static DMWrapper vertcat(DMWrapper... vector) {
        DMVector dmVector = new DMVector();
        dmVector.addAll(Arrays.asList(vector));
        return new DMWrapper(DmStatic.vertcat(dmVector.getCasADiObject()));
    }

    /**
     * Vertically concatenates an array of SXWrapper objects into a single SXWrapper.
     *
     * This method takes multiple SXWrapper instances and combines them into a single
     * SXWrapper by creating an SXVector, adding all the provided SXWrapper instances
     * to it, and then performing the vertical concatenation using the CasADi library.
     *
     * @param vector An array of SXWrapper objects to be concatenated.
     * @return A new SXWrapper containing the vertically concatenated result of the input SXWrapper objects.
     */
    public static SXWrapper vertcat(SXWrapper... vector) {
        SXVector sxVector = new SXVector();
        sxVector.addAll(Arrays.asList(vector));
        return new SXWrapper(SxStatic.vertcat(sxVector.getCasADiObject()));
    }

    /**
     * Concatenates sparsity patterns vertically.
     *
     * @param vector an array of SparsityWrapper objects to concatenate.
     * @return a SparsityWrapper representing the vertically concatenated sparsity pattern.
     */
    public static SparsityWrapper vertcat(SparsityWrapper... vector) {
        SparsityVector sparsityVector = new SparsityVector();
        sparsityVector.addAll(Arrays.asList(vector));
        return new SparsityWrapper(Sparsity.vertcat(sparsityVector.getCasADiObject()));
    }

    /**
     * Horizontally concatenates an array of MXWrapper objects into a single MXWrapper.
     *
     * This method takes multiple MXWrapper instances and combines them into a single
     * MXWrapper by creating an MXVector, adding all the provided MXWrapper instances
     * to it, and then performing the horizontal concatenation using the CasADi library.
     *
     * @param vector An array of MXWrapper objects to be concatenated.
     * @return A new MXWrapper containing the horizontally concatenated result of the input MXWrapper objects.
     */
    public static MXWrapper veccat(MXWrapper... vector) {
        MXVector mxVector = new MXVector();
        mxVector.addAll(Arrays.asList(vector));
        return new MXWrapper(MxStatic.veccat(mxVector.getCasADiObject()));
    }

    /**
     * Horizontally concatenates an array of DMWrapper objects into a single DMWrapper.
     *
     * This method takes multiple DMWrapper instances and combines them into a single
     * DMWrapper by creating a DMVector, adding all the provided DMWrapper instances
     * to it, and then performing the horizontal concatenation using the CasADi library.
     *
     * @param vector An array of DMWrapper objects to be concatenated.
     * @return A new DMWrapper containing the horizontally concatenated result of the input DMWrapper objects.
     */
    public static DMWrapper veccat(DMWrapper... vector) {
        DMVector dmVector = new DMVector();
        dmVector.addAll(Arrays.asList(vector));
        return new DMWrapper(DmStatic.veccat(dmVector.getCasADiObject()));
    }

    /**
     * Horizontally concatenates an array of SXWrapper objects into a single SXWrapper.
     *
     * This method takes multiple SXWrapper instances and combines them into a single
     * SXWrapper by creating an SXVector, adding all the provided SXWrapper instances
     * to it, and then performing the horizontal concatenation using the CasADi library.
     *
     * @param vector An array of SXWrapper objects to be concatenated.
     * @return A new SXWrapper containing the horizontally concatenated result of the input SXWrapper objects.
     */
    public static SXWrapper veccat(SXWrapper... vector) {
        SXVector sxVector = new SXVector();
        sxVector.addAll(Arrays.asList(vector));
        return new SXWrapper(SxStatic.veccat(sxVector.getCasADiObject()));
    }

    /**
     * Concatenates sparsity patterns into a single vector.
     *
     * @param vector an array of SparsityWrapper objects to concatenate.
     * @return a SparsityWrapper representing the vector concatenated sparsity pattern.
     */
    public static SparsityWrapper veccat(SparsityWrapper... vector) {
        SparsityVector sparsityVector = new SparsityVector();
        sparsityVector.addAll(Arrays.asList(vector));
        return new SparsityWrapper(SparsitySparsityInterface.veccat(sparsityVector.getCasADiObject()));
    }

    /**
     * Horizontally concatenates an array of MXWrapper objects into a single MXWrapper.
     *
     * This method takes multiple MXWrapper instances and combines them into a single
     * MXWrapper by creating an MXVector, adding all the provided MXWrapper instances
     * to it, and then performing the horizontal concatenation using the CasADi library.
     *
     * @param vector An array of MXWrapper objects to be concatenated.
     * @return A new MXWrapper containing the horizontally concatenated result of the input MXWrapper objects.
     */
    public static MXWrapper horzcat(MXWrapper... vector) {
        MXVector mxVector = new MXVector();
        mxVector.addAll(Arrays.asList(vector));
        return new MXWrapper(MxStatic.horzcat(mxVector.getCasADiObject()));
    }

    /**
     * Horizontally concatenates an array of DMWrapper objects into a single DMWrapper.
     *
     * This method takes multiple DMWrapper instances and combines them into a single
     * DMWrapper by creating a DMVector, adding all the provided DMWrapper instances
     * to it, and then performing the horizontal concatenation using the CasADi library.
     *
     * @param vector An array of DMWrapper objects to be concatenated.
     * @return A new DMWrapper containing the horizontally concatenated result of the input DMWrapper objects.
     */
    public static DMWrapper horzcat(DMWrapper... vector) {
        DMVector dmVector = new DMVector();
        dmVector.addAll(Arrays.asList(vector));
        return new DMWrapper(DmStatic.horzcat(dmVector.getCasADiObject()));
    }

    /**
     * Horizontally concatenates an array of SXWrapper objects into a single SXWrapper.
     *
     * This method takes multiple SXWrapper instances and combines them into a single
     * SXWrapper by creating an SXVector, adding all the provided SXWrapper instances
     * to it, and then performing the horizontal concatenation using the CasADi library.
     *
     * @param vector An array of SXWrapper objects to be concatenated.
     * @return A new SXWrapper containing the horizontally concatenated result of the input SXWrapper objects.
     */
    public static SXWrapper horzcat(SXWrapper... vector) {
        SXVector sxVector = new SXVector();
        sxVector.addAll(Arrays.asList(vector));
        return new SXWrapper(SxStatic.horzcat(sxVector.getCasADiObject()));
    }

    /**
     * Concatenates sparsity patterns horizontally.
     *
     * @param vector an array of SparsityWrapper objects to concatenate.
     * @return a SparsityWrapper representing the horizontally concatenated sparsity pattern.
     */
    public static SparsityWrapper horzcat(SparsityWrapper... vector) {
        SparsityVector sparsityVector = new SparsityVector();
        sparsityVector.addAll(Arrays.asList(vector));
        return new SparsityWrapper(Sparsity.horzcat(sparsityVector.getCasADiObject()));

    }

    /**
     * Diagonally concatenates an array of MXWrapper objects into a single MXWrapper.
     *
     * This method takes multiple MXWrapper instances and combines them into a single
     * MXWrapper by creating an MXVector, adding all the provided MXWrapper instances
     * to it, and then performing the diagonal concatenation using the CasADi library.
     *
     * @param vector An array of MXWrapper objects to be concatenated diagonally.
     * @return A new MXWrapper containing the diagonally concatenated result of the input MXWrapper objects.
     */
    public static MXWrapper diagcat(MXWrapper... vector) {
        MXVector mxVector = new MXVector();
        mxVector.addAll(Arrays.asList(vector));
        return new MXWrapper(MxStatic.diagcat(mxVector.getCasADiObject()));
    }

    /**
     * Diagonally concatenates an array of DMWrapper objects into a single DMWrapper.
     *
     * This method takes multiple DMWrapper instances and combines them into a single
     * DMWrapper by creating a DMVector, adding all the provided DMWrapper instances
     * to it, and then performing the diagonal concatenation using the CasADi library.
     *
     * @param vector An array of DMWrapper objects to be concatenated diagonally.
     * @return A new DMWrapper containing the diagonally concatenated result of the input DMWrapper objects.
     */
    public static DMWrapper diagcat(DMWrapper... vector) {
        DMVector dmVector = new DMVector();
        dmVector.addAll(Arrays.asList(vector));
        return new DMWrapper(DmStatic.diagcat(dmVector.getCasADiObject()));
    }

    /**
     * Diagonally concatenates an array of SXWrapper objects into a single SXWrapper.
     *
     * This method takes multiple SXWrapper instances and combines them into a single
     * SXWrapper by creating an SXVector, adding all the provided SXWrapper instances
     * to it, and then performing the diagonal concatenation using the CasADi library.
     *
     * @param vector An array of SXWrapper objects to be concatenated diagonally.
     * @return A new SXWrapper containing the diagonally concatenated result of the input SXWrapper objects.
     */
    public static SXWrapper diagcat(SXWrapper... vector) {
        SXVector sxVector = new SXVector();
        sxVector.addAll(Arrays.asList(vector));
        return new SXWrapper(SxStatic.diagcat(sxVector.getCasADiObject()));
    }

    /**
     * Concatenates sparsity patterns diagonally.
     *
     * @param vector an array of SparsityWrapper objects to concatenate.
     * @return a SparsityWrapper representing the diagonally concatenated sparsity pattern.
     */
    public static SparsityWrapper diagcat(SparsityWrapper... vector) {
        SparsityVector sparsityVector = new SparsityVector();
        sparsityVector.addAll(Arrays.asList(vector));
        return new SparsityWrapper(Sparsity.diagcat(sparsityVector.getCasADiObject()));
    }

    /**
     * Concatenates MX vectors into a block structure.
     *
     * @param collection an MXVectorCollection containing the vectors to concatenate.
     * @return an MXWrapper representing the block concatenated MX vectors.
     */
    public static MXWrapper blockcat(MXVectorCollection collection) {
        MXVectorCollection mxVectorCollection = new MXVectorCollection();
        mxVectorCollection.addAll(collection);
        return new MXWrapper(MxStatic.blockcat(mxVectorCollection.getCasADiObject()));
    }

    /**
     * Concatenates DM vectors into a block structure.
     *
     * @param collection a DMVectorCollection containing the vectors to concatenate.
     * @return a DMWrapper representing the block concatenated DM vectors.
     */
    public static DMWrapper blockcat(DMVectorCollection collection) {
        DMVectorCollection dmVectorCollection = new DMVectorCollection();
        dmVectorCollection.addAll(collection);
        return new DMWrapper(DmStatic.blockcat(dmVectorCollection.getCasADiObject()));
    }

    /**
     * Concatenates SX vectors into a block structure.
     *
     * @param collection an SXVectorCollection containing the vectors to concatenate.
     * @return an SXWrapper representing the block concatenated SX vectors.
     */
    public static SXWrapper blockcat(SXVectorCollection collection) {
        SXVectorCollection sxVectorCollection = new SXVectorCollection();
        sxVectorCollection.addAll(collection);
        return new SXWrapper(SxStatic.blockcat(sxVectorCollection.getCasADiObject()));
    }

    /**
     * Concatenates sparsity vectors into a block structure.
     *
     * @param collection a SparsityVectorCollection containing the vectors to concatenate.
     * @return a SparsityWrapper representing the block concatenated sparsity vectors.
     */
    public static SparsityWrapper blockcat(SparsityVectorCollection collection) {
        SparsityVectorCollection sparsityVectorCollection = new SparsityVectorCollection();
        sparsityVectorCollection.addAll(collection);
        return new SparsityWrapper(Sparsity.blockcat(sparsityVectorCollection.getCasADiObject()));
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
            } else if (obj instanceof SparsityWrapper) {
                if (type == null) {
                    type = 4; // SparsityWrapper
                } else if (type != 4) {
                    throw new Exception("Error: Mixed object types or other types found.");
                }
            } else {
                throw new Exception("Error: Unknown object type found.");
            }
        }

        return type != null ? type : 0; // 0 if array is Empty
    }

}
