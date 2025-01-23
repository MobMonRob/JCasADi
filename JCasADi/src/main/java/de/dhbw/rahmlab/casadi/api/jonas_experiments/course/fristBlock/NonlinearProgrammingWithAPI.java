package de.dhbw.rahmlab.casadi.api.jonas_experiments.course.fristBlock;

import de.dhbw.rahmlab.casadi.api.core.builder.MXBuilder;
import de.dhbw.rahmlab.casadi.api.core.utils.ConcatenationUtils;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;

public class NonlinearProgrammingWithAPI {

    public static void main(String[] args) {
        var x = new MXBuilder().setName("x").setRows(2).build();
        var f = x.at(0).sq().add(x.at( 1).tanh().sq());
        var g = x.at(0).add(x.at(1)).cos().add(0.5);
        var h = x.at(0).sin().add(0.5);

        var lambd = MXWrapper.sym("lambd");
        // var lambd = new MXBuilder().setName("lambd").build();

        var nu = new MXBuilder().setName("nu").build();
        // var nu = MXWrapper.sym("nu");

        var lag = f.add(lambd.multiply(g)).add(nu.multiply(h));
        System.out.println("Option 1: " + lag);

        var lagf = new Function("lagf", new MXVector(x, lambd, nu).getCasADiObject(), new MXVector(lag).getCasADiObject());

        var result = new MXVector();
        MXWrapper x0 = ConcatenationUtils.vertcat(new MXVector(-0.5, -1.8));
        lagf.call(new MXVector(x0, new MXWrapper(new MX(2)), new MXWrapper(new MX(3))).getCasADiObject(), result.getCasADiObject());
        System.out.println(result.get(0).at(0,0));
    }

}
