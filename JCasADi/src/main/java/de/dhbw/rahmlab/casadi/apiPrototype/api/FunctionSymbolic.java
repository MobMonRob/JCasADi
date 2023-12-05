package de.dhbw.rahmlab.casadi.apiPrototype.api;

import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import java.util.List;

public class FunctionSymbolic {

	protected Function f_sym_casadi;
	protected String name;
	protected int arity;

	protected static StdVectorMX transform(List<MultivectorSymbolic> mvs) {
		List<MX> mxs = mvs.stream().map(mv -> mv.mx).toList();
		return new StdVectorMX(mxs);
	}

	public FunctionSymbolic(String name, List<MultivectorSymbolic> parameters, List<MultivectorSymbolic> returns) {
		var f_sym_in = FunctionSymbolic.transform(parameters);
		var f_sym_out = FunctionSymbolic.transform(returns);
		this.f_sym_casadi = new Function(name, f_sym_in, f_sym_out);
		this.name = name;
		this.arity = parameters.size();
	}

	public String getName() {
		return this.name;
	}

	public int getArity() {
		return this.arity;
	}

	public List<MultivectorSymbolic> callSymbolic(List<MultivectorSymbolic> arguments) {
		var f_sym_in = FunctionSymbolic.transform(arguments);
		var f_sym_out = new StdVectorMX();
		this.f_sym_casadi.call(f_sym_in, f_sym_out);
		return f_sym_out.stream().map(mx -> new MultivectorSymbolic(mx)).toList();
	}

	public List<MultivectorNumeric> callNumeric(List<MultivectorNumeric> arguments) {
		var f_num_in = new StdVectorDM(arguments.stream().map(num -> num.dm).toList());
		var f_num_out = new StdVectorDM();
		this.f_sym_casadi.call(f_num_in, f_num_out);
		return f_num_out.stream().map(dm -> new MultivectorNumeric(dm)).toList();
	}

	@Override
	public String toString() {
		return this.f_sym_casadi.toString();
	}
}
