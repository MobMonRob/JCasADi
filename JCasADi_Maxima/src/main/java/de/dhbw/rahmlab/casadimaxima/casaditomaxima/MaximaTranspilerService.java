package de.dhbw.rahmlab.casadimaxima.casaditomaxima;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class MaximaTranspilerService {

    private final MaximaTranspiler maximaTranspiler = new MaximaTranspiler();

    /**
     * Only for debugging.
     */
    @Deprecated
    public String casadiToMaxima(String casadiString) {
        var charStream = CharStreams.fromString(casadiString);
        var lexer = new CasadiLexer(charStream);
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new CasadiParser(tokenStream);

        var parseTree = parser.file();
        String maximaString = this.maximaTranspiler.visit(parseTree);

        return maximaString;
    }

    public String casadiToMaxima(SX sx) {
        if (sx.columns() != 1) {
            throw new IllegalArgumentException("Only column vectors supported.");
        }

        String casadiString = sx.toString();
        return casadiToMaxima(casadiString);
    }
}
