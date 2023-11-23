package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.FileDeserializer;
import de.dhbw.rahmlab.casadi.impl.casadi.FileSerializer;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import java.io.FileDescriptor;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Demo4SerializationSolution {
    
    public static void main(String[] args) {
        
        // Ex 1.1
        SX x = SX.sym("x");
        Function f = new Function("f", new StdVectorSX(new SX[]{x}), 
                new StdVectorSX(new SX[]{SX.pow(x, new SX(2)) }));
        f.save("f.casadi");
        Function f_loaded = Function.load("f.casadi");
        //TODO
        // test ob ich die gespeicherte Funktion auch in ein casadi-python programm laden kann
        // leider scheint das Laden mit Python so nicht zu funktionieren
        // im Forum nachfragen
        
        //TODO
        // print(f)
        // print(f_loaded)
        // via System.out.println implementieren
        //System.out.println(f.);
        
        //TODO
        // print(f(3))
        // print(f_loaded(3))
        // implementieren
        
        // Ex 1.2
        
        FileSerializer fs = new FileSerializer("foo.casadi");
        fs.pack("foo");
        fs.pack(x);
        fs.pack(new StdVectorSX(new SX[]{SX.pow(x, new SX(2)),SX.sin(x)}));
        fs.pack(Sparsity.lower(5));
        fs.pack(f);
        
        // fs = None
        fs = null;
        
        
        // Ex 1.3
        
        FileDeserializer fd  = new FileDeserializer("foo.casadi");
        // in phython reicht fd.unpack() ohne den Typ mit im Methodenname
        //TODO
        System.out.println(fd.unpack_string());
		System.out.println(fd.unpack_sx().toString());
        //fd.unpack_sx_vector().
        //TODO toString method fehlt in sx_vector
        // sollte so aussehen: [SX(sq(x)), SX(sin(x))]
        System.out.println(fd.unpack_sparsity().dim());
        //TODO sollte so aussehen: 5x5,15nz
        fd.unpack_function();
        //TODO
        // für functions fehlt noch eine toString()-Methoden die die Struktur ausgibt
        // sollte so aussehen: f:(i0)->(o0) SXFunction
        
        // # Ex 1.4

        fd = new FileDeserializer("foo.casadi");

        // _ = fd.unpack()
        SX x_saved = fd.unpack_sx();
        // y  = vcat(fd.unpack())

        // print("jacobian wrt to original x:", jacobian(y,x))
        // print("jacobian wrt to saved x:", jacobian(y,x_saved))

        // # Loaded-back expressions graphs are completely uncoupled from the original expression graph.
        // # Hence, Jacobian detects no dependence on the original symbol.

        // # Ex 1.5

        fd = new FileDeserializer("f.casadi");
        // print(fd.unpack())

        // # Function.save is just doing FileSerializer + pack in the background

        // # Ex 2.1

        // x = SX.sym("x")

        //f = new Function("f",new StdVectorSX(new SX[]{x}),new StdVectorSX(new SX[]{SX.pow(x, new SX(2)})));
        //FIXME
        
        //foo = {"a": DM(Sparsity.lower(5)), "f": f}
        //TODO
        
        //import pickle
        //pickle.dump(foo,open("foo.dat","wb"))
        //bar = pickle.load(open("foo.dat","rb"))
        //TODO
        
        //print(foo)
        //print(bar)
    }
}
