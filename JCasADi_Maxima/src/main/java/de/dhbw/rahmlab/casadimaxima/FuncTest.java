package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import java.util.List;

public class FuncTest {
    public static void main(String[] args) {
        var a = SxStatic.sym("a", 16, 1);
        System.out.println(a.toString());
        var b = new SX(2, 1);
        b.at(0, 0).assign(SxStatic.plus(new SX(2), a.at(5, 0)));
        System.out.println(b.toString());
        var func = new Function("func", new StdVectorSX(List.of(a)), new StdVectorSX(List.of(b)));
        func.sx_in().forEach(System.out::println);
        func.sx_out().forEach(System.out::println);
        var out = new StdVectorSX();
        func.call(new StdVectorSX(List.of(a)), out);
        out.forEach(System.out::println);
    }
}
