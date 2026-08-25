package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.DmStatic;
import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.GlobalOptions;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import de.dhbw.rahmlab.casadimaxima.api.MaximaSimplifier;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MaximaSimplifierTest {

    public static void main(String[] args) throws InterruptedException {
        String casadiString = "@1=-0.0996, @2=(arg0_0+(@1*arg1_0)), @3=0.0996, @4=((@3*arg1_0)-arg0_0), @5=0.5, @6=sq((arg0_0+(@1*arg1_0))), @7=((@5*@6)+-0.508884), @8=-0.5, @9=(-0.491116+(@8*@6)), @10=((@5*@6)+0.491116), @11=(0.508884+(@8*@6)), @12=((@3*arg1_0)-arg0_0), [(((@2*@4)-(@7*@9))+(@10*@11)), 00, 00, 00, (((@2*@9)-(@7*@4))-(@10*@12)), (((@2*@11)-(@7*@12))-(@10*@4)), 00, 00, 00, 00, 00, 00, 00, 00, 00, (((@2*@12)-(@7*@11))+(@10*@9)), 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00]";

        // String casadiString = "@1=0.5, @2=sq(arg0_0), [arg0_0, 00, 00, 00, ((@1*@2)+-0.5), ((@1*@2)+@1), 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00]";
        String maximaInput = new ToMaximaTranspilerService().casadiToMaxima(casadiString);
        String maximaOutput = MaximaSimplifier.simplify(maximaInput);
        // SX sx = new ToCasadiTranspilerService().maximaToCasadi(maximaOutput, List.of(SxStatic.sym("arg0", 32, 1), SxStatic.sym("arg1", 32, 1)));
        System.out.println("casadiIn: " + casadiString);
        System.out.println("\nmaximaIn:\n" + maximaInput);
        System.out.println("\nmaximaOut:\n" + maximaOutput);
        SX sx = new ToCasadiTranspilerService().maximaToCasadi(maximaOutput, List.of(SxStatic.sym("arg0", 32, 1), SxStatic.sym("arg1", 32, 1)));
        System.out.println("\ncasadiOut:\n" + sx.toString());
        var func = new Function("testfunc", new StdVectorSX(), new StdVectorSX(new SX[]{sx}), new Dict(Map.of("allow_free", new GenericType(true))));
        func.free_sx().forEach(System.out::println);

        SX a = new SX(1);
        SX b = SxStatic.sym("b", 1, 1);
        SX c = SxStatic.rdivide(a, b);
        System.out.println("\nc:\n" + c.toString());

        GlobalOptions.setSimplificationOnTheFly(false);

        // SxStatic.set_scientific(true);
        DmStatic.set_precision(10);
        System.out.println(new DM(3.141592653589793239).toString());

        SxStatic.set_precision(10);
        SxStatic.set_width(10);
        SxStatic.set_scientific(true);
        System.out.println(new SX(3.141592653589793239));

        System.out.println(new MX(3.141592653589793239));

        // System.out.println(SxStatic.exp(new SX(1)).toString());
    }
}
