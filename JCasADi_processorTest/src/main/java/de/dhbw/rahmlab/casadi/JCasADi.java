package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.delegating.annotation.api.GenerateDelegate;

@GenerateDelegate(name = "MxStatic", of = {MX1.class, MX2.class})
public class JCasADi {

    public static void main(String[] args) {
        System.out.println("test");
    }
}
