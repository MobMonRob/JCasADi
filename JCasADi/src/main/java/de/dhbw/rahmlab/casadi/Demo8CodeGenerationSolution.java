package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.GlobalOptions;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
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

        SX x = SX.sym("x", 2);
        SX y = SX.sym("y");

        Function f = new Function("f", new StdVectorSX(new SX[]{x,y}), 
                                           new StdVectorSX(new SX[]{SX.minus(SX.sqrt(x),y)}));
        f.generate("fun");


        // Ex 1.2

        Dict options = new Dict();
        options.put("with_header", new GenericType(true));

        f.generate("fun2",options);
    
        
        // %% Ex 1.3
        
        MX xx = MX.sym("x", 2);
        MX yy = MX.sym("y");

        f = new Function("f", new StdVectorMX(new MX[]{xx, yy}), 
                              new StdVectorMX(new MX[]{MX.minus(MX.sqrt(xx),yy)}));

        f.generate("fun3",options);

        // % The function body (casadi_f0) changed a lot
        // % f_work changed
        
        
        // %% Ex 2.1

        //TODO
        /*qp = struct;
        qp.h = Sparsity.dense(2, 2);
        qp.a = Sparsity.dense(1,2);
        
        f = conic("f","qrqp",qp);*/
        f.generate("fun21", options);

        //TODO
        //StdVectorDM args_1 = new StdVectorDM(new DM[]{new DM(posi_val), new DM(posi_des_val)});

        StdVectorMX result = new StdVectorMX();
        // Funktionaufruf mit symbolischem Argument
        //TODO
	//f.call(args1, result);
        
        //f("h",[1,0.3;0.3,7],"g",[2;3],"a",[1;2],"lba",-1,"uba",1)

        
        // %% Ex 2.2
        
        //TODO
        //f = conic("f","osqp",qp);
        f.generate("fun22",options);

        //TODO
        // das sollte sich mit java-board-Mitteln implementieren lassen
        
        /*fileID = fopen("common.sh","w");
        fprintf(fileID,"LIBDIR=''%s''\n",GlobalOptions.getCasadiPath());
        fprintf(fileID,"INCDIR=''%s''\n",GlobalOptions.getCasadiIncludePath());
        fclose(fileID);

        fileID = fopen("common.bat","w");
        fprintf(fileID,"set LIBDIR=%s\n",GlobalOptions.getCasadiPath());
        fprintf(fileID,"set INCDIR=%s\n",GlobalOptions.getCasadiIncludePath());
        fclose(fileID);*/
    }
}