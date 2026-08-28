package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspiler;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService;
import static de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService.checkMapVars;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.MaximaLexer;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.MaximaParser;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class OutputDifferenceTest {

    public static void main1(String[] args) throws Exception {
        String maximaOutput = "\"block([%1,%2,%3,%4,%5,%6,%7,%8],%1:arg0_5^2+arg0_4^2+arg0_3^2,%2:sqrt(%1),%3:cos(%2),%4:1/%2,%5:sin(%2),%6:arg0_0*arg0_5-arg0_1*arg0_4-arg0_2*arg0_3,%7:1/%1,%8:%3-%4*%5,[%3,0,0,0,0,arg0_5*%6*%7*%8+arg0_0*%4*%5,arg0_1*%4*%5-arg0_4*%6*%7*%8,arg0_3*%6*%7*%8+arg0_2*%4*%5,arg0_3*%4*%5,-(arg0_4*%4*%5),arg0_5*%4*%5,0,0,0,0,%6*%4*%5])\"";

        var charStream = CharStreams.fromString(maximaOutput);
        var lexer = new MaximaLexer(charStream);
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new MaximaParser(tokenStream);

        var testRig = new AntlrTestRig();
        testRig.process(lexer, parser, charStream, "root");
    }

    public static void main(String[] args) throws Exception {
        String maximaOutput = "\"block([%1,%2,%3,%4,%5,%6,%7,%8],%1:arg0_5^2+arg0_4^2+arg0_3^2,%2:sqrt(%1),%3:cos(%2),%4:1/%2,%5:sin(%2),%6:arg0_0*arg0_5-arg0_1*arg0_4-arg0_2*arg0_3,%7:1/%1,%8:%3-%4*%5,[%3,0,0,0,0,arg0_5*%6*%7*%8+arg0_0*%4*%5,arg0_1*%4*%5-arg0_4*%6*%7*%8,arg0_3*%6*%7*%8+arg0_2*%4*%5,arg0_3*%4*%5,-(arg0_4*%4*%5),arg0_5*%4*%5,0,0,0,0,%6*%4*%5])\"";
        SX sx = new ToCasadiTranspilerService().maximaToCasadi(maximaOutput, List.of(SxStatic.sym("arg0", 32, 1)));
        System.out.println("\nmaximaOut:\n" + maximaOutput);
        System.out.println("\ncasadiOut:\n" + sx.toString());
    }
}
