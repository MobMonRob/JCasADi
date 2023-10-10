package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;

public class JCasADi {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		SX posi = SX.sym("p", 2);
		System.out.println("test: " + posi.str());
	}
}
