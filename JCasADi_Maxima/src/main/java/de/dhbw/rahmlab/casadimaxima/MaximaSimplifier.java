package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.casaditomaxima.MaximaTranspilerService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MaximaSimplifier {

    public static void main(String[] args) {
        String casadiString = "@1=-0.0996, @2=(arg0_0+(@1*arg1_0)), @3=0.0996, @4=((@3*arg1_0)-arg0_0), @5=0.5, @6=sq((arg0_0+(@1*arg1_0))), @7=((@5*@6)+-0.508884), @8=-0.5, @9=(-0.491116+(@8*@6)), @10=((@5*@6)+0.491116), @11=(0.508884+(@8*@6)), @12=((@3*arg1_0)-arg0_0), [(((@2*@4)-(@7*@9))+(@10*@11)), 00, 00, 00, (((@2*@9)-(@7*@4))-(@10*@12)), (((@2*@11)-(@7*@12))-(@10*@4)), 00, 00, 00, 00, 00, 00, 00, 00, 00, (((@2*@12)-(@7*@11))+(@10*@9)), 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00]";

        // String casadiString = "@1=0.5, @2=sq(arg0_0), [arg0_0, 00, 00, 00, ((@1*@2)+-0.5), ((@1*@2)+@1), 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00]";
        String maximaInput = new MaximaTranspilerService().casadiToMaxima(casadiString);
        String maximaOutput = new MaximaSimplifier().simplify(maximaInput);
        System.out.println("casadiString: " + casadiString);
        System.out.println("\nmaximaInput:\n" + maximaInput);
        System.out.println("\nmaximaOutput:\n" + maximaOutput);
    }

    public String simplify(String expr) throws RuntimeException {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "maxima",
                "--very-quiet",
                "--batch-string",
                String.format("display2d:false$\n%s\nvs : (ev(vn, infeval, trigrat, radscan))$\nstring(vs);", expr)
            );
            Process p = pb.start();
            String out = new BufferedReader(new InputStreamReader(p.getInputStream()))
                .lines().toList().getLast();
            //.lines().collect(Collectors.joining("\n"));
            String err = new BufferedReader(new InputStreamReader(p.getErrorStream()))
                .lines().collect(Collectors.joining("\n"));
            if (!err.isBlank()) {
                throw new RuntimeException("Maxima error: " + err);
            }
            int ret = p.waitFor();
            if (ret != 0) {
                throw new RuntimeException("Maxima error");
            }
            return out;
        } catch (Exception e) {
            throw new RuntimeException("Failed to run Maxima", e);
        }
    }
}
