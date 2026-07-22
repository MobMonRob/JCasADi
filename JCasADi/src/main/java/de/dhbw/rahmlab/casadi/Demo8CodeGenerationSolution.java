package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.api.core.wrapper.CoreWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.CodeGenerator;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.Importer;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.Dict;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo8CodeGenerationSolution {

    public static void main(String[] args) {

        // Ex 1.1

        SX x = SxStatic.sym("x", 2);
        SX y = SxStatic.sym("y");

        Function f = new Function("f", new StdVectorSX(new SX[] { x, y }),
                new StdVectorSX(new SX[] { SxStatic.minus(SxStatic.sqrt(x), y) }));
        f.generate("fun");

        // Ex 1.2

        Dict options = new Dict();
        options.put("with_header", new GenericType(true));

        f.generate("fun2", options);

        // %% Ex 1.3

        MX xx = MxStatic.sym("x", 2);
        MX yy = MxStatic.sym("y");

        f = new Function("f", new StdVectorMX(new MX[] { xx, yy }),
                new StdVectorMX(new MX[] { MxStatic.minus(MxStatic.sqrt(xx), yy) }));

        f.generate("fun3", options);

        // % The function body (casadi_f0) changed a lot
        // % f_work changed

        // %% Ex 2.1

        // TODO
        /*
         * qp = struct;
         * qp.h = Sparsity.dense(2, 2);
         * qp.a = Sparsity.dense(1,2);
         * 
         * f = conic("f","qrqp",qp);
         */
        f.generate("fun21", options);

        // TODO
        // StdVectorDM args_1 = new StdVectorDM(new DM[]{new DM(posi_val), new
        // DM(posi_des_val)});

        StdVectorMX result = new StdVectorMX();
        // Funktionaufruf mit symbolischem Argument
        // TODO
        // f.call(args1, result);

        // f("h",[1,0.3;0.3,7],"g",[2;3],"a",[1;2],"lba",-1,"uba",1)

        // %% Ex 2.2

        // TODO
        // f = conic("f","osqp",qp);
        f.generate("fun22", options);

        // TODO
        // das sollte sich mit java-board-Mitteln implementieren lassen

        /*
         * fileID = fopen("common.sh","w");
         * fprintf(fileID,"LIBDIR=''%s''\n",GlobalOptions.getCasadiPath());
         * fprintf(fileID,"INCDIR=''%s''\n",GlobalOptions.getCasadiIncludePath());
         * fclose(fileID);
         * 
         * fileID = fopen("common.bat","w");
         * fprintf(fileID,"set LIBDIR=%s\n",GlobalOptions.getCasadiPath());
         * fprintf(fileID,"set INCDIR=%s\n",GlobalOptions.getCasadiIncludePath());
         * fclose(fileID);
         */

        // Examples from https://web.casadi.org/docs/#generating-c-code
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
        StdVectorDM arg = new StdVectorDM(new DM[]{new DM(3.14)});
        StdVectorDM res = new StdVectorDM();
	    fLoaded.call(arg, res);
        long endTime = System.nanoTime();
        System.out.println("Time taken to load and call f: " + (endTime - startTime) + " nanoseconds");
        System.out.println("fLoaded(3.14)="+res.toString()); // f(3.14)=[0.00159265]

        Function gLoaded = CoreWrapper.external("g", "./gen.so").getCasADiObject();
        gLoaded.call(arg, res);
        System.out.println("gLoaded(3.14)="+res.toString()); // g(3.14)=[-0.999999]

        startTime = System.nanoTime();
        Importer cImporter = new Importer("gen.c", "shell"); // compiler can be "shell" or "clang" (CasADi distributed, but not working for JCasADi yet (2026-05-13))
        Function fJIT = CoreWrapper.external("f", cImporter).getCasADiObject();
        fJIT.call(arg, res);
        endTime = System.nanoTime();
        System.out.println("Time taken to JIT compile and call f: " + (endTime - startTime) + " nanoseconds");
        System.out.println("fJIT(3.14)="+res.toString()); // f(3.14)=[0.00159265]

        // Use another optimization for JIT compilation from (https://github.com/casadi/casadi/issues/1941#issuecomment-274793064)
        Dict jitOptions = new Dict();
        jitOptions.put("flags", new GenericType("-O3"));
        startTime = System.nanoTime();
        Importer cImporter2 = new Importer("gen.c", "shell", jitOptions);
        Function fJIT2 = CoreWrapper.external("f", cImporter2).getCasADiObject();
        fJIT2.call(arg, res);
        endTime = System.nanoTime();
        System.out.println("Time taken to JIT compile with -O3 and call f: " + (endTime - startTime) + " nanoseconds");
        System.out.println("fJIT2(3.14)="+res.toString()); // f(3.14)=[0.00159265]
    }
}