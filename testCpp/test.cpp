#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <stdexcept>
#include <casadi/casadi.hpp>

using namespace casadi;

int main(){
	// Symbolic
    // symbolic variables
    casadi::SX posi = casadi::SX::sym("p", 2);
    casadi::SX posi_des = casadi::SX::sym("p_des", 2);

    // objective function
	// casadi::SX::get()
    casadi::SX fun_obj = pow(posi(0)-posi_des(0), 2) +
                         pow(posi(1)-posi_des(1), 2);
    // gradients
    casadi::SX fun_obj_grad = jacobian(fun_obj, posi);  // 1 by 2 jacobian matrix

    // functions to be generated
    casadi::Function f_fun_obj("fun_obj", {posi, posi_des}, {fun_obj});
    casadi::Function f_fun_obj_grad("fun_obj_grad", {posi, posi_des}, {fun_obj_grad});

	// Numeric
	// input
	std::vector<double> posi_val(2, 0);
	posi_val[0] = 10.0;
	posi_val[1] = 5.0;

	std::vector<double> posi_des_val(2, 0);
	posi_des_val[0] = 5.0;
	posi_des_val[1] = 3.0;

	std::vector<casadi::DM> arg_1 = {casadi::DM(posi_val), casadi::DM(posi_des_val)};

	// compute objective function
	std::vector<casadi::DM> result_1 = f_fun_obj(arg_1);
	// the first result
	std::cout << "objective function: " << result_1.at(0) << std::endl;


	// another way to input
	std::vector<casadi::DM> arg_2 = {posi_val, posi_des_val};

	// compute objective function jacobian
	std::vector<casadi::DM> result_2 = f_fun_obj_grad(arg_2);
	// the first result
	std::cout << "objective function jacobian: " << result_2.at(0) << std::endl;

	///////
	casadi::DaeBuilder* b = new casadi::DaeBuilder;
	casadi::SharedObjectInternal* soi = (static_cast<casadi::SharedObject*>(b))->get();
	///////
	casadi::MX mx = casadi::MX::sym("x",2,2);
	casadi::StringVector sv;
	std::string printed = casadi::MX::print_operator(mx, sv);
	std::cout << "blub1: " << printed << std::endl;
	std::cout << "blub2: " << mx << std::endl;
	///////
	MX x = MX::sym("x",2);
	MX A = MX(2,2);
	A(0,0) = x(0);
	A(1,1) = x(0)+x(1);
	std::cout << "foo: " << A << std::endl;
	// Throws exception:
	// std::cout << "foo: " << casadi::MX::print_operator(A, sv) << std::endl;

    return 0;
}
