package de.dhbw.rahmlab.casadimaxima.maximatocasadi;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class CasadiTranspilerService {

    private final CasadiTranspiler casadiTranspiler = new CasadiTranspiler();

    public SX maximaToCasadi(String maximaString) {
        var charStream = CharStreams.fromString(maximaString);
        var lexer = new MaximaLexer(charStream);
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new MaximaParser(tokenStream);

        var parseTree = parser.root();
        SX sx = this.casadiTranspiler.visit(parseTree);

        sx = SxStatic.sparsify(sx);

        return sx;
    }
}
