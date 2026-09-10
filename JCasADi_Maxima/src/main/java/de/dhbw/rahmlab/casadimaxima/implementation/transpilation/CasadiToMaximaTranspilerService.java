package de.dhbw.rahmlab.casadimaxima.implementation.transpilation;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.CasadiLexer;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.CasadiParser;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Phase;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class CasadiToMaximaTranspilerService {

    /**
     * Low-level CasADi-text entry point with an explicit variables allow-list.
     */
    public String casadiToMaxima(String casadiString, Set<String> variables) {
        var charStream = CharStreams.fromString(casadiString);
        var lexer = new CasadiLexer(charStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new FailFastErrorListener(Direction.CASADI_TO_MAXIMA, Phase.LEXER, casadiString));
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new CasadiParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new FailFastErrorListener(Direction.CASADI_TO_MAXIMA, Phase.PARSER, casadiString));

        var parseTree = parser.file();
        CasadiToMaximaTranspiler maximaTranspiler = new CasadiToMaximaTranspiler(casadiString, variables);
        String maximaString = maximaTranspiler.visit(parseTree);

        return maximaString;
    }

    public String casadiToMaxima(SX sx) {
        if (sx.columns() != 1) {
            throw new IllegalArgumentException("Only column vectors supported.");
        }

        // SX.set_precision(0);
        String casadiString = sx.toString();
        if (!casadiString.contains("[")) {
            casadiString = "[" + casadiString + "]";
        }

        HashSet<String> variables = SxStatic.symvar(sx).stream().unordered().map(SX::toString).collect(Collectors.toCollection(HashSet::new));

        return casadiToMaxima(casadiString, variables);
    }
}
