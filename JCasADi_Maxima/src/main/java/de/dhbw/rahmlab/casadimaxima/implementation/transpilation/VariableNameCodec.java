package de.dhbw.rahmlab.casadimaxima.implementation.transpilation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encodes free CasADi symbol names for transport through Maxima.
 *
 * <p>The fixed prefix prevents Maxima from assigning a built-in meaning to a user supplied
 * symbol name. Encoding is injective because it always adds exactly one prefix.</p>
 */
public final class VariableNameCodec {

    public static final String PREFIX = "var_";
    private static final Pattern TEX_ENCODED_IDENTIFIER = Pattern.compile(
        "(?<![A-Za-z0-9_])var\\\\_(?:(?:[A-Za-z])|(?:\\\\_))(?:(?:[A-Za-z0-9])|(?:\\\\_))*(?![A-Za-z0-9]|\\\\_)"
    );

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

    /**
     * Decodes Maxima's TeX representation of all transport identifiers in {@code latex}.
     */
    public static String decodeTex(String latex) {
        Matcher matcher = TEX_ENCODED_IDENTIFIER.matcher(latex);
        StringBuffer decoded = new StringBuffer();
        while (matcher.find()) {
            String maximaName = matcher.group().replace("\\_", "_");
            String casadiName = decode(maximaName);
            String texName = casadiName.replace("_", "\\_");
            matcher.appendReplacement(decoded, Matcher.quoteReplacement(texName));
        }
        matcher.appendTail(decoded);
        return decoded.toString();
    }
}
