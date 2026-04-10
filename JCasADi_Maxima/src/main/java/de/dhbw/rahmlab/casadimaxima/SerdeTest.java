package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.StringDeserializer;
import de.dhbw.rahmlab.casadi.impl.casadi.StringSerializer;

public class SerdeTest {

    public static void main(String[] args) {
        var a = SxStatic.sym("a", 2, 1);
        var b = SxStatic.sym("b", 2, 1);
        var c = SxStatic.plus(a, b);
        System.out.println(c.toString());
        var ser = new StringSerializer();
        ser.pack(c);
        String cString = ser.encode();
        System.out.println(cString);
        var de = new StringDeserializer();
        de.decode(cString);
        var cDe = de.unpack_sx();
        System.out.println(cDe.toString());
    }
}
