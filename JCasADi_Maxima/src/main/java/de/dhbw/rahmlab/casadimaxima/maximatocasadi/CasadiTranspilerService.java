package de.dhbw.rahmlab.casadimaxima.maximatocasadi;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.CasadiLexer;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.CasadiParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class CasadiTranspilerService {

    private final CasadiTranspiler casadiTranspiler = new CasadiTranspiler();

    /**
     * Only for debugging.
     */
    @Deprecated
    public SX maximaToCasadi(String maximaString) {
        var charStream = CharStreams.fromString(maximaString);
        var lexer = new CasadiLexer(charStream);
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new CasadiParser(tokenStream);

        var parseTree = parser.file();
        SX sx = this.casadiTranspiler.visit(parseTree);

        return sx;
    }
}
