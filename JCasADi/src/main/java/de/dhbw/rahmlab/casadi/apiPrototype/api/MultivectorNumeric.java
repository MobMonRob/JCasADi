package de.dhbw.rahmlab.casadi.apiPrototype.api;

import de.dhbw.rahmlab.casadi.impl.casadi.DM;

public class MultivectorNumeric {

	protected DM dm;

	protected MultivectorNumeric(DM dm) {
		this.dm = dm;
	}

	public MultivectorNumeric() {
		this.dm = new DM(2, 2);
	}

	public MultivectorNumeric(MultivectorNumeric mn) {
		this.dm = new DM(mn.dm);
	}

	public void set(double num, int x, int y) {
		this.dm.at(x, y).assign(new DM(num));
	}

	@Override
	public String toString() {
		return this.dm.toString();
	}
}
