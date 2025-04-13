package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dict.Dictionary;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.MapStringToMXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.MapStringToSXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.StringVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXVector;

/**
 * Builder class for creating FunctionWrapper objects.
 * <p>
 * This builder allows fluent configuration of a FunctionWrapper by specifying:
 * <ul>
 *   <li>A function name.</li>
 *   <li>Input and output expressions as SXVector or MXVector.</li>
 *   <li>Input/Output names (via StringVector).</li>
 *   <li>Additional options (Dict).</li>
 *   <li>Mappings from strings to SX/MX (MapStringToSXWrapper / MapStringToMXWrapper) if needed.</li>
 * </ul>
 * The build() method selects the appropriate constructor based on the provided parameters.
 */
public class FunctionBuilder {
    private String functionName = null;
    private SXVector sxExIn = null;
    private SXVector sxExOut = null;
    private MXVector mxExIn = null;
    private MXVector mxExOut = null;
    private StringVector nameIn = null;
    private StringVector nameOut = null;
    private Dictionary opts = null;
    private MapStringToSXWrapper mapSX = null;
    private MapStringToMXWrapper mapMX = null;

    public FunctionBuilder setFunctionName(String functionName) {
        this.functionName = functionName;
        return this;
    }

    public FunctionBuilder setSXExIn(SXVector sxExIn) {
        this.sxExIn = sxExIn;
        return this;
    }

    public FunctionBuilder setSXExOut(SXVector sxExOut) {
        this.sxExOut = sxExOut;
        return this;
    }

    public FunctionBuilder setMXExIn(MXVector mxExIn) {
        this.mxExIn = mxExIn;
        return this;
    }

    public FunctionBuilder setMXExOut(MXVector mxExOut) {
        this.mxExOut = mxExOut;
        return this;
    }

    public FunctionBuilder setNameIn(StringVector nameIn) {
        this.nameIn = nameIn;
        return this;
    }

    public FunctionBuilder setNameOut(StringVector nameOut) {
        this.nameOut = nameOut;
        return this;
    }

    public FunctionBuilder setOptions(Dictionary opts) {
        this.opts = opts;
        return this;
    }

    public FunctionBuilder setMapSX(MapStringToSXWrapper mapSX) {
        this.mapSX = mapSX;
        return this;
    }

    public FunctionBuilder setMapMX(MapStringToMXWrapper mapMX) {
        this.mapMX = mapMX;
        return this;
    }

    /**
     * Builds a FunctionWrapper based on the current configuration.
     * The builder selects the constructor according to the set parameters in the following order:
     * <ol>
     *   <li>If a MapStringToSXWrapper is provided, that constructor is used (with or without options and names).</li>
     *   <li>If a MapStringToMXWrapper is provided, that constructor is used similarly.</li>
     *   <li>If SX vectors are provided for inputs and outputs, the corresponding SX constructor is used.</li>
     *   <li>If MX vectors are provided for inputs and outputs, the corresponding MX constructor is used.</li>
     *   <li>If only a function name is set, the simplest constructor is invoked.</li>
     *   <li>Otherwise, the default constructor is used.</li>
     * </ol>
     *
     * @return a FunctionWrapper constructed with the specified settings.
     */
    public FunctionWrapper build() {
        // Case 1: Using MapStringToSXWrapper
        if (mapSX != null) {
            if (nameIn != null && nameOut != null) {
                if (opts != null) {
                    return new FunctionWrapper(functionName, mapSX, nameIn, nameOut, opts);
                } else {
                    return new FunctionWrapper(functionName, mapSX, nameIn, nameOut);
                }
            }
        }
        // Case 2: Using MapStringToMXWrapper
        if (mapMX != null) {
            if (nameIn != null && nameOut != null) {
                if (opts != null) {
                    return new FunctionWrapper(functionName, mapMX, nameIn, nameOut, opts);
                } else {
                    return new FunctionWrapper(functionName, mapMX, nameIn, nameOut);
                }
            }
        }
        // Case 3: Using SX vectors
        if (sxExIn != null && sxExOut != null) {
            if (nameIn != null && nameOut != null) {
                if (opts != null) {
                    return new FunctionWrapper(functionName, sxExIn, sxExOut, nameIn, nameOut, opts);
                } else {
                    return new FunctionWrapper(functionName, sxExIn, sxExOut, nameIn, nameOut);
                }
            } else {
                if (opts != null) {
                    return new FunctionWrapper(functionName, sxExIn, sxExOut, opts);
                } else {
                    return new FunctionWrapper(functionName, sxExIn, sxExOut);
                }
            }
        }
        // Case 4: Using MX vectors
        if (mxExIn != null && mxExOut != null) {
            if (nameIn != null && nameOut != null) {
                if (opts != null) {
                    return new FunctionWrapper(functionName, mxExIn, mxExOut, nameIn, nameOut, opts);
                } else {
                    return new FunctionWrapper(functionName, mxExIn, mxExOut, nameIn, nameOut);
                }
            } else {
                if (opts != null) {
                    return new FunctionWrapper(functionName, mxExIn, mxExOut, opts);
                } else {
                    return new FunctionWrapper(functionName, mxExIn, mxExOut);
                }
            }
        }
        // Case 5: Only function name provided
        if (functionName != null && !functionName.isEmpty()) {
            return new FunctionWrapper(functionName);
        }
        // Default: call the parameterless constructor
        return new FunctionWrapper();
    }
}