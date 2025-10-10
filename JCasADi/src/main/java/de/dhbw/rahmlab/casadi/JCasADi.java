package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.delegating.annotation.api.GenerateDelegate;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.casadi.DmGenericExpression;
import de.dhbw.rahmlab.casadi.impl.casadi.DmGenericMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.DmSparsityInterface;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.casadi.GlobalOptions;
import de.dhbw.rahmlab.casadi.impl.casadi.IM;
import de.dhbw.rahmlab.casadi.impl.casadi.ImGenericExpression;
import de.dhbw.rahmlab.casadi.impl.casadi.ImGenericMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.ImSparsityInterface;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.MxGenericExpression;
import de.dhbw.rahmlab.casadi.impl.casadi.MxGenericMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.MxSparsityInterface;
import de.dhbw.rahmlab.casadi.impl.casadi.MxSubMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.casadi.Slice;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.casadi.SparsitySparsityInterface;
import de.dhbw.rahmlab.casadi.impl.casadi.SxGenericExpression;
import de.dhbw.rahmlab.casadi.impl.casadi.SxGenericMatrix;
import de.dhbw.rahmlab.casadi.impl.casadi.SxSparsityInterface;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorStdString;
import de.dhbw.rahmlab.nativelibloader.api.NativeLibLoader;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@GenerateDelegate(name = "SparsityStatic", of = {Sparsity.class, SparsitySparsityInterface.class})
@GenerateDelegate(name = "DmStatic", of = {DM.class, DmGenericExpression.class, DmGenericMatrix.class, DmSparsityInterface.class})
@GenerateDelegate(name = "ImStatic", of = {IM.class, ImGenericExpression.class, ImGenericMatrix.class, ImSparsityInterface.class})
@GenerateDelegate(name = "MxStatic", of = {MX.class, MxGenericExpression.class, MxGenericMatrix.class, MxSparsityInterface.class})
@GenerateDelegate(name = "SxStatic", of = {SX.class, SxGenericExpression.class, SxGenericMatrix.class, SxSparsityInterface.class})
public class JCasADi {

	public static void main(String[] args) {
		System.out.println("------------------");
		// test1();
		System.out.println("------------------");
		// simple_function_call();
		System.out.println("------------------");
		// composed_function_call();
		System.out.println("------------------");
		// dmtest();
		System.out.println("------------------");
		// printtest();
		System.out.println("------------------");
		// mxTest();
		System.out.println("------------------");
		// mulTest();
		System.out.println("------------------");
        NativeLibLoader.setDebug(true);
		casadiSharedLibLoadTest();
	}

	public static void casadiSharedLibLoadTest() {
		System.out.println("getCasadiPath: " + GlobalOptions.getCasadiPath());
		System.out.println("getCasadiIncludePath: " + GlobalOptions.getCasadiIncludePath());
		System.out.println("System.getenv(\"CASADIPATH\"): " + System.getenv("CASADIPATH"));
		System.out.println("System.getProperty(\"CASADIPATH\"): " + System.getProperty("CASADIPATH"));
		System.out.println("userDir: " + System.getProperty("user.dir"));

		System.out.println("Waiting until enter...");
		(new Scanner(System.in)).nextLine();

		try {
			var mv = MxStatic.sym("mv", 2, 1);
			System.out.println(mv.dim_(true));
			Thread.sleep(100);
			// MxStatic.inv() of 2x1 matrix requires libcasadi_linsol_qr.so.
			var inv = MxStatic.inv(mv);
			System.out.println(inv);
		} catch (RuntimeException | InterruptedException ex) {
			ex.printStackTrace();
			System.out.println("Waiting until enter...");
			(new Scanner(System.in)).nextLine();
		}
	}

	public static void mulTest() {
		int za = 2;
		int sa = za; // Quadratisch
		int zb = sa; // Bedingung für Multiplikation
		int sb = 1;
		int zc = za; // Folge aus Multiplikation
		int sc = sb; // Folge aus Multipklikation

		var a = MxStatic.sym("a", Sparsity.lower(za));
		System.out.println("a: " + a.dim_(true));

		var b = MxStatic.sym("b", zb, sb);
		System.out.println("b: " + b.dim_(true));

		var c = MxStatic.mtimes(a, b);
		System.out.println("c: " + c.toString(true) + " | " + c.dim_(true));

		for (int zeile = 0; zeile < c.rows(); ++zeile) {
			for (int spalte = 0; spalte < c.columns(); ++spalte) {
				System.out.println(String.format("z: %s, s: %s, v: %s", zeile, spalte, c.at(zeile, spalte)));
			}
		}
	}

	public static void mxTest() {
		var sp = Sparsity.diag(2, 2);
		var mx = new MX(sp);
		mx.at(0, 0).assign(new MX(11));
		mx.at(1, 1).assign(new MX(3.14));
		System.out.println(mx);
	}

	public static void printtest() {
		var mx = new MX(Sparsity.diag(2, 2));
		// mx.set(new MX(3.14), false, new Slice(0, 1), new Slice(0, 1));
		mx.at(0, 0).assign(new MX(3.14));
		mx.at(1, 1).assign(new MX(11));
		System.out.println(mx.at(0, 0).toString(true)); // MX
		System.out.println(mx.at(0, 0)); // MXSubMatrix
		System.out.println("--1....................\n");

		var a = MxStatic.sym("a", Sparsity.diag(2, 2));
		System.out.println(a.at(1, 1).toString(true)); // MX
		System.out.println(a.at(1, 1)); // MXSubMatrix
		System.out.println("--2....................\n");

		var mx2 = MxStatic.sym("mx2", Sparsity.diag(2, 2));
		mx2.at(0, 0).assign(MxStatic.plus(a.at(1, 1), new MX(3.14)));
		mx2.at(1, 1).assign(new MX(22));
		System.out.println(mx2.at(0, 0).toString(true)); // MX
		System.out.println(mx2.at(0, 0)); // MXSubMatrix
		System.out.println("--3....................\n");

		var mx3 = MxStatic.sym("mx3", Sparsity.diag(2, 2));
		mx3.at(0, 0).assign(MxStatic.plus(mx2.at(0, 0), new MX(13)));
		mx3.at(1, 1).assign(new MX(33));
		System.out.println(mx3.at(0, 0).toString(true)); // MX
		System.out.println(mx3.at(0, 0)); // MXSubMatrix
		System.out.println("--4....................\n");

		var b = SxStatic.sym("b", Sparsity.diag(2, 2));

		var sx = SxStatic.sym("sx", Sparsity.diag(2, 2));
		sx.at(0, 0).assign(SxStatic.plus(b.at(1, 1), new SX(3.14)));
		sx.at(1, 1).assign(new SX(2.7));
		System.out.println(sx);
		System.out.println(sx.at(0, 0));
		System.out.println("--5....................\n");

		var sx2 = SxStatic.sym("sx2", Sparsity.diag(2, 2));
		sx2.at(0, 0).assign(SxStatic.plus(sx.at(0, 0), new SX(13)));
		System.out.println(sx2);
		System.out.println(sx2.at(0, 0));
		System.out.println("--6....................\n");
	}

	public static void dmtest() {
		var sp = Sparsity.dense(2, 2);
		var dd = new StdVectorDouble(new double[]{1.0, 2.0, 3.0, 4.0});
		var dm = new DM(sp, dd, false);
		System.out.println(dm);
	}

	public static void test1() {
		DM test;
//		// Symbolic
//		// symbolic variables
//		casadi::SX posi = casadi::SX::sym("p", 2);
		SX posi = SxStatic.sym("p", 2);
		System.out.println("posi: " + posi);
//		casadi::SX posi_des = casadi::SX::sym("p_des", 2);
		SX posi_des = SxStatic.sym("p_des", 2);
//
//		// objective function
//		casadi::SX fun_obj = pow(posi(0)-posi_des(0), 2) +
//							 pow(posi(1)-posi_des(1), 2);
		SX fun_obj = SxStatic.plus(
			SxStatic.pow(SxStatic.minus(posi.at(0), posi_des.at(0)), new SX(2)),
			SxStatic.pow(SxStatic.minus(posi.at(1), posi_des.at(1)), new SX(2))
		);
//
//		// gradients
//		casadi::SX fun_obj_grad = jacobian(fun_obj, posi);  // 1 by 2 jacobian matrix
		SX fun_obj_grad = SxStatic.jacobian(fun_obj, posi);
//
//		// functions to be generated
//		casadi::Function f_fun_obj("fun_obj", {posi, posi_des}, {fun_obj});
//		casadi::Function f_fun_obj_grad("fun_obj_grad", {posi, posi_des}, {fun_obj_grad});
		Function f_fun_obj = new Function("fun_obj", new StdVectorSX(new SX[]{posi, posi_des}), new StdVectorSX(new SX[]{fun_obj}));
		Function f_fun_obj_grad = new Function("fun_obj_grad", new StdVectorSX(new SX[]{posi, posi_des}), new StdVectorSX(new SX[]{fun_obj_grad}));
		System.out.println("f_fun_obj: " + f_fun_obj);

//
//		// Numeric
//		// input
//		std::vector<double> posi_val(2, 0);
//		posi_val[0] = 10.0;
//		posi_val[1] = 5.0;
		StdVectorDouble posi_val = new StdVectorDouble(new double[]{10.0, 5.0});
//
//		std::vector<double> posi_des_val(2, 0);
//		posi_des_val[0] = 5.0;
//		posi_des_val[1] = 3.0;
		StdVectorDouble posi_des_val = new StdVectorDouble(new double[]{5.0, 3.0});
//
//		std::vector<casadi::DM> arg_1 = {casadi::DM(posi_val), casadi::DM(posi_des_val)};
		StdVectorDM arg_1 = new StdVectorDM(new DM[]{new DM(posi_val), new DM(posi_des_val)});
//
//		// compute objective function
//		std::vector<casadi::DM> result_1 = f_fun_obj(arg_1);
		StdVectorDM result_1 = new StdVectorDM();
		f_fun_obj.call(arg_1, result_1);
//
//		// the first result
//		std::cout << "objective function: " << result_1.at(0) << std::endl;
		System.out.println("objective function: " + result_1.get(0));
//
//
//		// another way to input
//		std::vector<casadi::DM> arg_2 = {posi_val, posi_des_val};
		StdVectorDM arg_2 = new StdVectorDM(new DM[]{new DM(posi_val), new DM(posi_des_val)});
//
//		// compute objective function jacobian
//		std::vector<casadi::DM> result_2 = f_fun_obj_grad(arg_2);
		StdVectorDM result_2 = new StdVectorDM();
		f_fun_obj_grad.call(arg_2, result_2);
//
//		// the first result
//		std::cout << "objective function jacobian: " << result_2.at(0) << std::endl;
		System.out.println("objective function jacobian: " + result_2.get(0));

		MX mx = MxStatic.sym("x", 2, 2);
		StdVectorStdString vecStr = new StdVectorStdString();
		String theStr = MxStatic.print_operator(mx, vecStr);
		// mx 1.: x
		// mx 2.: x
		System.out.println("mx 1.: " + theStr);
		System.out.println("mx 2.: " + mx);

//		MX x = MX::sym("x",2);
//		MX A = MX(2,2);
//		A(0,0) = x(0);
//		A(1,1) = x(0)+x(1);
//		std::cout << "foo: " << A << std::endl;
		// Beispiel aus: https://web.casadi.org/docs/#the-mx-symbolics
		// Achtung: Indizes fangen in der Casadi Doku bei 1 an und bei uns bei 0!
		MX x = MxStatic.sym("x", 2);
		MX A = new MX(2, 2);
		A.at(0, 0).assign(x.at(0));
		A.at(1, 1).assign(MxStatic.plus(x.at(0), x.at(1)));

		// A:(project((zeros(2x2,1nz)[0] = x[0]))[1] = (x[0]+x[1]))
		System.out.println("A:" + A);
	}

	public static Function f_sym_java(int dim) {
		// DM ist nicht symbolisch!

		// Input symbolic
		MX in_sym_1 = MxStatic.sym("in_sym_1", dim, dim);
		System.out.println("in_sym_1: " + in_sym_1);
		MX in_sym_2 = MxStatic.sym("in_sym_2", dim, dim);
		System.out.println("in_sym_2: " + in_sym_2);

		// Output symbolic
		MX out_sym_1 = MxStatic.plus(in_sym_1, in_sym_2);
		System.out.println("out_sym_1: " + out_sym_1);

		// Function symbolic
		var f_sym_in = new StdVectorMX(new MX[]{in_sym_1, in_sym_2});
		var f_sym_out = new StdVectorMX(new MX[]{out_sym_1});
		Function f_sym_casadi = new Function("f_sym_casadi", f_sym_in, f_sym_out);
		System.out.println("f_sym_casadi: " + f_sym_casadi);

		return f_sym_casadi;
	}

	public static void simple_function_call() {
		System.out.println("-> simple_function_call()");

		Function f_sym_casadi = f_sym_java(2);

		DM in_num_1 = new DM(2, 2);
		in_num_1.at(0, 0).assign(new DM(0));
		in_num_1.at(1, 1).assign(new DM(11));
		System.out.println("in_num_1: " + in_num_1);

		DM in_num_2 = new DM(in_num_1);
		in_num_2.at(0, 1).assign(new DM(314));
		System.out.println("in_num_2: " + in_num_2);

		StdVectorDM f_num_in = new StdVectorDM(new DM[]{in_num_1, in_num_2});
		StdVectorDM f_num_out = new StdVectorDM();
		// In Doku: "Evaluate the function symbolically or numerically."
		// Call mit DM ist numerische Evaluierung.
		// Call mit SX oder MX ist symbolische Evaluierung.
		f_sym_casadi.call(f_num_in, f_num_out);

		DM out_num_1 = f_num_out.get(0);
		System.out.println("out_num_1: " + out_num_1);
	}

	public static Function f_sym_composed_java(int dim) {
		Function f_sym_casadi = f_sym_java(dim);

		// Input symbolic
		MX in_sym_1 = MxStatic.sym("in_sym_1", dim, dim);
		System.out.println("in_sym_1: " + in_sym_1);
		MX in_sym_2 = MxStatic.sym("in_sym_2", dim, dim);
		System.out.println("in_sym_2: " + in_sym_2);
		MX in_sym_com_1 = MxStatic.sym("in_sym_com_1", dim, dim);
		System.out.println("in_sym_com_1: " + in_sym_com_1);

		// Output symbolic
		var f_sym_in = new StdVectorMX(new MX[]{in_sym_1, in_sym_2});
		var f_sym_out = new StdVectorMX();
		f_sym_casadi.call(f_sym_in, f_sym_out);
		MX f_sym_out_1 = f_sym_out.get(0);
		MX out_sym_com_1 = MxStatic.minus(f_sym_out_1, in_sym_com_1);
		System.out.println("out_sym_com_1: " + out_sym_com_1);

		// Function symbolic
		var f_sym_com_in = new StdVectorMX(new MX[]{in_sym_1, in_sym_2, in_sym_com_1});
		var f_sym_com_out = new StdVectorMX(new MX[]{out_sym_com_1});
		Function f_sym_com_casadi = new Function("f_sym_com_casadi", f_sym_com_in, f_sym_com_out);
		System.out.println("f_sym_com_casadi: " + f_sym_com_casadi);

		return f_sym_com_casadi;
	}

	public static void composed_function_call() {
		System.out.println("-> composed_function_call()");

		Function f_sym_com_casadi = f_sym_composed_java(2);

		DM in_num_1 = new DM(2, 2);
		in_num_1.at(0, 0).assign(new DM(0));
		in_num_1.at(1, 1).assign(new DM(11));
		System.out.println("in_num_1: " + in_num_1);

		DM in_num_2 = new DM(in_num_1);
		in_num_2.at(0, 1).assign(new DM(314));
		System.out.println("in_num_2: " + in_num_2);

		DM in_num_3 = new DM(2, 2);
		in_num_3.at(1, 0).assign(new DM(15));
		System.out.println("in_num_3: " + in_num_3);

		StdVectorDM f_num_in = new StdVectorDM(new DM[]{in_num_1, in_num_2, in_num_3});
		StdVectorDM f_num_out = new StdVectorDM();
		// In Doku: "Evaluate the function symbolically or numerically."
		// Call mit DM ist numerische Evaluierung.
		// Call mit SX oder MX ist symbolische Evaluierung.
		f_sym_com_casadi.call(f_num_in, f_num_out);

		DM out_num_1 = f_num_out.get(0);
		System.out.println("out_num_1: " + out_num_1);
	}
}
