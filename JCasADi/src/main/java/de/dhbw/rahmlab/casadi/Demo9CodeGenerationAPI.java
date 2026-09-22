package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.api.core.wrapper.CoreWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.CodeGeneratorWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.Importer;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

/**
 * @author Florian Stöckl (florian.stoeckl@dhbw-karlsruhe.de)
 */
public class Demo9CodeGenerationAPI {

    public static void main(String[] args) {

        SX x = SxStatic.sym("x");
        SX y = SxStatic.sym("y");

        Function f = new Function("f", new StdVectorSX(new SX[] { x, y }),
                new StdVectorSX(new SX[] { SxStatic.minus(SxStatic.sqrt(x), y) }));
        Function g = new Function("g", new StdVectorSX(new SX[] { y }),
                new StdVectorSX(new SX[] { SxStatic.cos(y) }));
        // 1. Pack CasADi Function in FunctionWrapper
        FunctionWrapper func = new FunctionWrapper(f);
        func.generate("", "gen.c");

        // 2. Compile and reimport the generated code, then call it
        Importer importer = new Importer("gen.c", "shell");
        FunctionWrapper fJit = CoreWrapper.external("f", importer);
        DMVector result = fJit.call(new DMWrapper(4.0), new DMWrapper(1.0));
        System.out.println("fJit(4.0, 1.0)=" + result.toString()); // f(4.0, 1.0)=[1]

        // 3. Multi-function code generation with CodeGeneratorWrapper
        CodeGeneratorWrapper generator = CodeGeneratorWrapper.withHeader("gen2.c");
        generator.add(new FunctionWrapper(f));
        generator.add(new FunctionWrapper(g));
        generator.generate("");

        FunctionWrapper gJit = CoreWrapper.external("g", new Importer("gen2.c", "shell"));
        DMVector gResult = gJit.call(new DMWrapper(0.0));
        System.out.println("gJit(0.0)=" + gResult.toString()); // g(0.0)=[1]
    }
}