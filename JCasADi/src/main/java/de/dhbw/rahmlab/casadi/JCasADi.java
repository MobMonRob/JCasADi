package de.dhbw.rahmlab.casadi;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;

public class JCasADi {

	public static void main(String[] args) {
//		// Symbolic
//		// symbolic variables
//		casadi::SX posi = casadi::SX::sym("p", 2);
		SX posi = SX.sym("p", 2);
		System.out.println("test: " + posi.str());
//		casadi::SX posi_des = casadi::SX::sym("p_des", 2);
		SX posi_des = SX.sym("p_des", 2);
//
//		// objective function
//		casadi::SX fun_obj = pow(posi(0)-posi_des(0), 2) +
//							 pow(posi(1)-posi_des(1), 2);
		SX fun_obj = SX.plus(
			SX.pow(SX.minus(posi.at(0), posi_des.at(0)), new SX(2)),
			SX.pow(SX.minus(posi.at(1), posi_des.at(1)), new SX(2))
		);

//		// gradients
//		casadi::SX fun_obj_grad = jacobian(fun_obj, posi);  // 1 by 2 jacobian matrix
		SX fun_obj_grad = SX.jacobian(fun_obj, posi);
//
//		// functions to be generated
//		casadi::Function f_fun_obj("fun_obj", {posi, posi_des}, {fun_obj});
//		casadi::Function f_fun_obj_grad("fun_obj_grad", {posi, posi_des}, {fun_obj_grad});
//
//		// Numeric
//		// input
//		std::vector<double> posi_val(2, 0);
//		posi_val[0] = 10.0;
//		posi_val[1] = 5.0;
//
//		std::vector<double> posi_des_val(2, 0);
//		posi_des_val[0] = 5.0;
//		posi_des_val[1] = 3.0;
//
//		std::vector<casadi::DM> arg_1 = {casadi::DM(posi_val), casadi::DM(posi_des_val)};
//
//		// compute objective function
//		std::vector<casadi::DM> result_1 = f_fun_obj(arg_1);
//		// the first result
//		std::cout << "objective function: " << result_1.at(0) << std::endl;
//
//
//		// another way to input
//		std::vector<casadi::DM> arg_2 = {posi_val, posi_des_val};
//
//		// compute objective function jacobian
//		std::vector<casadi::DM> result_2 = f_fun_obj_grad(arg_2);
//		// the first result
//		std::cout << "objective function jacobian: " << result_2.at(0) << std::endl;
	}
}
