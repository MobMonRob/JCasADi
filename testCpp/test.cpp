#include <casadi/casadi.hpp>

int main(){
	casadi::DM dm = casadi::DM(3.141592653589793239);
	std::cout << "dm1: " << dm << std::endl;
	std::cout << "dm2: " << std::setprecision(12) << dm << std::endl;
	casadi::DM::set_precision(12);
	std::cout << "dm3: " << dm << std::endl;

	casadi::SX sx = casadi::SX(3.141592653589793239);
	std::cout << "sx1: " << sx << std::endl;
	std::cout << "sx2: " << std::setprecision(12) << sx << std::endl;
	casadi::SX::set_precision(12);
	std::cout << "sx3: " << sx << std::endl;

	/*
	// Output:
	dm1: 3.14159
	dm2: 3.14159
	dm3: 3.14159265359
	sx1: 3.14159
	sx2: 3.14159
	sx3: 3.14159
	*/

    return 0;
}
