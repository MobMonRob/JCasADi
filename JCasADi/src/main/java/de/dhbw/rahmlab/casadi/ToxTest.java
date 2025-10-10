package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.SXElem;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiSXElem;

public class ToxTest {
    public static void main(String[] args) {
        // init a
        var a = SxStatic.sym("a", 2, 3);
        a.at(0, 0).assign(new SX(42));
        System.out.println(a.toString(true));

        // init b with Element from a
        var num = a.at(0, 0).scalar();
        var b = new SX(new StdVectorCasadiSXElem(new SXElem[]{num}));
        System.out.println(b.toString(true));

        // change a at same position
        a.at(0, 0).assign(new SX(55));
        System.out.println(a.toString(true));
        System.out.println(b.toString(true));
        // Good: Change of previous matrix does not change current matrix.
    }
}
