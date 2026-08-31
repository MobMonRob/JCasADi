package de.dhbw.rahmlab.casadimaxima.implementation.transpilation;

/**
 * Encodes free CasADi symbol names for transport through Maxima.
 *
 * <p>The fixed prefix prevents Maxima from assigning a built-in meaning to a user supplied
 * symbol name. Encoding is injective because it always adds exactly one prefix.</p>
 */
public final class VariableNameCodec {

    public static final String PREFIX = "var_";

    private VariableNameCodec() {
    }

    public static String encode(String casadiName) {
        return PREFIX + casadiName;
    }

    public static String decode(String maximaName) {
        if (!maximaName.startsWith(PREFIX)) {
            throw new IllegalArgumentException("Not an encoded Maxima variable name: " + maximaName);
        }
        return maximaName.substring(PREFIX.length());
    }

    public static boolean isEncoded(String maximaName) {
        return maximaName.startsWith(PREFIX);
    }
}
