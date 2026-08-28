package de.dhbw.rahmlab.casadimaxima.implementation.transpilation;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.CasadiLexer;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.CasadiParser;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Phase;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class CasadiToMaximaTranspilerService {

    /**
     * Low-level CasADi-text entry point for diagnostics and exploration.
     */
    public String casadiToMaxima(String casadiString) {
        var charStream = CharStreams.fromString(casadiString);
        var lexer = new CasadiLexer(charStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new FailFastErrorListener(Direction.CASADI_TO_MAXIMA, Phase.LEXER, casadiString));
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new CasadiParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new FailFastErrorListener(Direction.CASADI_TO_MAXIMA, Phase.PARSER, casadiString));

        var parseTree = parser.file();
        CasadiToMaximaTranspiler maximaTranspiler = new CasadiToMaximaTranspiler(casadiString);
        String maximaString = maximaTranspiler.visit(parseTree);

        return maximaString;
    }

    public String casadiToMaxima(SX sx) {
        if (sx.columns() != 1) {
            throw new IllegalArgumentException("Only column vectors supported.");
        }

        // SX.set_precision(0);
        String casadiString = sx.toString();

        return casadiToMaxima(casadiString);
    }
}
