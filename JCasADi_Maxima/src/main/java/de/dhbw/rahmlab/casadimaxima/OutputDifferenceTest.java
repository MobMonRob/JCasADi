package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.MaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.CasadiTranspiler;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.CasadiTranspilerService;
import static de.dhbw.rahmlab.casadimaxima.maximatocasadi.CasadiTranspilerService.checkMapVars;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.MaximaLexer;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.MaximaParser;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class OutputDifferenceTest {

    public static void main1(String[] args) throws Exception {
        String maximaOutput = "\"block([%1,%2,%3,%4,%5,%6,%7,%8],%1:a0_5^2+a0_4^2+a0_3^2,%2:sqrt(%1),%3:cos(%2),%4:1/%2,%5:sin(%2),%6:a0_0*a0_5-a0_1*a0_4-a0_2*a0_3,%7:1/%1,%8:%3-%4*%5,[%3,0,0,0,0,a0_5*%6*%7*%8+a0_0*%4*%5,a0_1*%4*%5-a0_4*%6*%7*%8,a0_3*%6*%7*%8+a0_2*%4*%5,a0_3*%4*%5,-(a0_4*%4*%5),a0_5*%4*%5,0,0,0,0,%6*%4*%5])\"";

        var charStream = CharStreams.fromString(maximaOutput);
        var lexer = new MaximaLexer(charStream);
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new MaximaParser(tokenStream);

        var testRig = new AntlrTestRig();
        testRig.process(lexer, parser, charStream, "root");
    }

    public static void main(String[] args) throws Exception {
        String maximaOutput = "\"block([%1,%2,%3,%4,%5,%6,%7,%8],%1:a0_5^2+a0_4^2+a0_3^2,%2:sqrt(%1),%3:cos(%2),%4:1/%2,%5:sin(%2),%6:a0_0*a0_5-a0_1*a0_4-a0_2*a0_3,%7:1/%1,%8:%3-%4*%5,[%3,0,0,0,0,a0_5*%6*%7*%8+a0_0*%4*%5,a0_1*%4*%5-a0_4*%6*%7*%8,a0_3*%6*%7*%8+a0_2*%4*%5,a0_3*%4*%5,-(a0_4*%4*%5),a0_5*%4*%5,0,0,0,0,%6*%4*%5])\"";
        SX sx = new CasadiTranspilerService().maximaToCasadi(maximaOutput, List.of(SxStatic.sym("a0", 32, 1)));
        System.out.println("\nmaximaOut:\n" + maximaOutput);
        System.out.println("\ncasadiOut:\n" + sx.toString());
    }
}
