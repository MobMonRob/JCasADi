package de.dhbw.rahmlab.casadi.apiPrototype.test;

import de.dhbw.rahmlab.casadi.apiPrototype.api.FunctionSymbolic;
import de.dhbw.rahmlab.casadi.apiPrototype.api.MultivectorNumeric;
import de.dhbw.rahmlab.casadi.apiPrototype.api.MultivectorSymbolic;
import java.util.List;

/**
 * Das ist dann, was der Transformator nach dem Parser ausführt.
 */
public class ApiTest {

	public static void main(String[] args) {
// func(a, b) {
//   a + b
// }
//
// main() {
//   a := ??
//   b := ??
//   c := func(a, b)
// }
//
// // main()
		simple_function_call();
	}

	public static FunctionSymbolic f_sym_java() {
		MultivectorSymbolic in_sym_1 = new MultivectorSymbolic("in_sym_1");
		System.out.println("in_sym_1" + in_sym_1);
		MultivectorSymbolic in_sym_2 = new MultivectorSymbolic("in_sym_2");
		System.out.println("in_sym_2" + in_sym_2);

		MultivectorSymbolic out_sym_1 = in_sym_1.plus(in_sym_2);
		System.out.println("out_sym_1" + out_sym_1);

		var f_sym_in = List.of(in_sym_1, in_sym_2);
		var f_sym_out = List.of(out_sym_1);
		FunctionSymbolic f_sym = new FunctionSymbolic("func", f_sym_in, f_sym_out);
		System.out.println("f_sym" + f_sym);

		return f_sym;
	}

	public static void simple_function_call() {
		System.out.println("-> simple_function_call()");
		FunctionSymbolic f_sym = f_sym_java();

		MultivectorNumeric in_num_1 = new MultivectorNumeric();
		in_num_1.set(0, 0, 0);
		in_num_1.set(1, 1, 1);
		System.out.println("in_num_1" + in_num_1);

		MultivectorNumeric in_num_2 = new MultivectorNumeric(in_num_1);
		in_num_2.set(314, 0, 1);
		System.out.println("in_num_2" + in_num_2);

		List<MultivectorNumeric> f_num_out = f_sym.callNumeric(List.of(in_num_1, in_num_2));
		MultivectorNumeric out_num_1 = f_num_out.get(0);
		System.out.println("out_num_1" + out_num_1);
	}
}
