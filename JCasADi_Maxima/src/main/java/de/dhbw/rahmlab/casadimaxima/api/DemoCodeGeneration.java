package de.dhbw.rahmlab.casadimaxima.api;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.api.core.wrapper.CoreWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.CodeGenerator;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.Importer;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

public class DemoCodeGeneration {

    public static void main(String[] args) {

        // 5.1 Syntax for generating code
        SX x1 = SxStatic.sym("x");
        SX y1 = SxStatic.sym("y");

        Function f1 = new Function("f", new StdVectorSX(new SX[] { x1 }),
                new StdVectorSX(new SX[] { SxStatic.sin(x1) }));
        Function g1 = new Function("g", new StdVectorSX(new SX[] { y1 }),
                new StdVectorSX(new SX[] { SxStatic.cos(y1) }));
        Dict options1 = new Dict();
        options1.put("with_header", new GenericType(true));
        CodeGenerator C = new CodeGenerator("gen.c", options1);
        C.add(f1);
        C.add(g1);
        C.generate();

        // Compile c code to shared library (gen.so) using gcc
        try {
            ProcessBuilder pb = new ProcessBuilder("gcc", "-shared", "-fPIC", "-o", "gen.so", "gen.c");
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5.2 Using the generated code (external function and JIT compilation)
        long startTime = System.nanoTime();
        Function fLoaded = CoreWrapper.external("f", "./gen.so").getCasADiObject();
        StdVectorDM arg = new StdVectorDM(new DM[] { new DM(3.14) });
        StdVectorDM res = new StdVectorDM();
        fLoaded.call(arg, res);
        long endTime = System.nanoTime();
        System.out.println("Time taken to load and call f: " + (endTime - startTime) + " nanoseconds");
        System.out.println("fLoaded(3.14)=" + res.toString()); // f(3.14)=[0.00159265]

        Function gLoaded = CoreWrapper.external("g", "./gen.so").getCasADiObject();
        gLoaded.call(arg, res);
        System.out.println("gLoaded(3.14)=" + res.toString()); // g(3.14)=[-0.999999]

        startTime = System.nanoTime();
        Importer cImporter = new Importer("gen.c", "shell");
        Function fJIT = CoreWrapper.external("f", cImporter).getCasADiObject();
        fJIT.call(arg, res);
        endTime = System.nanoTime();
        System.out.println("Time taken to JIT compile and call f: " + (endTime - startTime) + " nanoseconds");
        System.out.println("fJIT(3.14)=" + res.toString()); // f(3.14)=[0.00159265]

        Dict jitOptions = new Dict();
        jitOptions.put("flags", new GenericType("-O3"));
        startTime = System.nanoTime();
        Importer cImporter2 = new Importer("gen.c", "shell", jitOptions);
        Function fJIT2 = CoreWrapper.external("f", cImporter2).getCasADiObject();
        fJIT2.call(arg, res);
        endTime = System.nanoTime();
        System.out.println("Time taken to JIT compile with -O3 and call f: " + (endTime - startTime) + " nanoseconds");
        System.out.println("fJIT2(3.14)=" + res.toString()); // f(3.14)=[0.00159265]

        // LaTeXify the symbolic expressions
        long numberOfInputs = f1.n_in();
        long numberOfOutputs = f1.n_out();
        System.out.println("Function f has " + numberOfInputs + " input(s) and " + numberOfOutputs + " output(s).");
        StdVectorSX fInSyms = f1.sx_in();
        StdVectorSX fOutSyms = f1.sx_out();
        SX[] fIns = new SX[10];
        SX[] fOuts = new SX[10];
        for (int i=0; i<f1.n_in() && i<10; i++) {
            fIns[i] = fInSyms.get(0);
            fOuts[i] = fOutSyms.get(0);
        }

        SX x = fIns[0];
        SX sin = fOuts[0];

        // TODO: Fix, because sin is only filled with placeholder by CasADi
        // Function t = new Function("t", new StdVectorSX(new SX[]{x}), new StdVectorSX(new SX[]{sin}));
        // t.call(arg, res);
        // System.out.println(res.toString());

        SX b1 = new SX(5,1);

        System.out.println("LaTeX sin(x): " + MaximaLaTeXifier.LaTeXify(b1));
        
    }
}
