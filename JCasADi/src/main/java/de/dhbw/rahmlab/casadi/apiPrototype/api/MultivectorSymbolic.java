package de.dhbw.rahmlab.casadi.apiPrototype.api;

import de.dhbw.rahmlab.casadi.impl.casadi.MX;

public class MultivectorSymbolic {

	protected MX mx;

	protected MultivectorSymbolic(MX mx) {
		this.mx = mx;
	}

	public MultivectorSymbolic(String name) {
		this(MX.sym(name, 2, 2));
	}

	public MultivectorSymbolic plus(MultivectorSymbolic rhs) {
		MX out = MX.plus(this.mx, rhs.mx);
		return new MultivectorSymbolic(out);
	}

	@Override
	public String toString() {
		return this.mx.toString();
	}
}
