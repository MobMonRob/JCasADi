package de.dhbw.rahmlab.casadimaxima.maximatocasadi;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Phase;
import de.dhbw.rahmlab.casadimaxima.parsing.FailFastErrorListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class ToCasadiTranspilerService {

    public SX maximaToCasadi(String maximaString, List<SX> variables) {
        Map<String, SX> variablesMap = checkMapVars(variables);

        var charStream = CharStreams.fromString(maximaString);
        var lexer = new MaximaLexer(charStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new FailFastErrorListener(Direction.MAXIMA_TO_CASADI, Phase.LEXER, maximaString));
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new MaximaParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new FailFastErrorListener(Direction.MAXIMA_TO_CASADI, Phase.PARSER, maximaString));

        var parseTree = parser.root();
        ToCasadiTranspiler casadiTranspiler = new ToCasadiTranspiler(variablesMap);
        SX sx;
        try {
            sx = casadiTranspiler.visit(parseTree);
        } catch (RuntimeException ex) {
            throw new RuntimeException(String.format("Exception \"%s\" with Maxima Output: \"%s\".", ex.getMessage(), maximaString), ex);
        }

        sx = SxStatic.sparsify(sx);

        return sx;
    }

    public static Map<String, SX> checkMapVars(List<SX> variables) throws IllegalArgumentException {
        Set<SX> uniqueVars = Collections.newSetFromMap(new IdentityHashMap<>(variables.size()));
        Map<String, SX> variablesMap = new HashMap<>();
        for (SX var : variables) {
            // plus(a, a) is allowed.
            boolean newVar = uniqueVars.add(var);
            if (!newVar) {
                continue;
            }

            if (!var.is_valid_input()) {
                throw new IllegalArgumentException("Variable is not a purely symbolic.");
            }

            if (var.columns() != 1) {
                throw new IllegalArgumentException("Only column vectors supported.");
            }

            StdVectorSX rows = SxStatic.vertsplit_n(var, var.rows());
            for (SX row : rows) {
                String rowString = row.toString();
                if (rowString.equals("00")) {
                    continue;
                }
                Object mapped = variablesMap.put(rowString, row);
                if (mapped != null) {
                    // // Not allowed:
                    // SX a1 = SxStatic.sym("a");
                    // SX a2 = SxStatic.sym("a");
                    // SX b = SxStatic.plus(a1, a2);
                    throw new IllegalArgumentException(String.format("Name only allowed once, but found twice: %s", mapped));
                }

            }
            // Map<String, SX> currentVarMap = rows.stream().collect(Collectors.toMap(SX::toString, java.util.function.Function.identity()));
        }
        return variablesMap;
    }
}
