%module(directors="1") core__;

// Own generic .i files
%include "_common.i"
%include "template_interface.i"

// SWIG lib .i fles
%include <swiginterface.i>
%include <std_string.i>

////////////////

%{
#include "casadi/core/casadi_common.hpp"
#include "casadi/core/core.hpp"

using namespace casadi;
%}

#define CASADI_EXPORT

%import "casadi/core/casadi_common.hpp" // #define SWIG_IF_ELSE
#undef SWIG_IF_ELSE
#define SWIG_IF_ELSE(is_swig, not_swig) not_swig
%import "casadi/core/casadi_types.hpp"

/// Data structure in the target language holding data
#define GUESTOBJECT void

// #pragma SWIG nowarn=509,303,302

// Incude cmath early on, see #622
%begin %{
	#include <cmath>
	#ifdef _XOPEN_SOURCE
	#undef _XOPEN_SOURCE
	#endif
	#ifdef _POSIX_C_SOURCE
	#undef _POSIX_C_SOURCE
	#endif
	%}

	%ignore *::operator->;

	%rename(str) get_str;

	// append works for strings
	%naturalvar;

	// Make data members read-only
	%immutable;

	// Make sure that a copy constructor is created
	%copyctor;

	%include "doc.i"

	// Note: Only from 3.0.0 onwards,
	// DirectorException inherits from std::exception
	#if SWIG_VERSION >= 0x030000
	// Exceptions handling
	%include "exception.i"
	%exception {
		try {
			$action
		} catch(const std::exception& e) {
			SWIG_exception(SWIG_RuntimeError, e.what());
		}
	}
	#else
	// Exceptions handling
	%include "exception.i"
	%exception {
		try {
			$action
		} catch(const std::exception& e) {
			SWIG_exception(SWIG_RuntimeError, e.what());
		} catch (const Swig::DirectorException& e) {
			SWIG_exception(SWIG_TypeError, e.getMessage());
		}
	}
	#endif

	%{
		#define SWIG_Error_return(code, msg)  { std::cerr << "Error occured in CasADi SWIG interface code:" << std::endl << "  "<< msg << std::endl;SWIG_Error(code, msg); return 0; }
	%}


//////

//using namespace casadi;

//////

/*
%import "casadi/core/calculus.hpp"
%import "casadi/core/callback.hpp"
%import "casadi/core/casadi_common.hpp"
%import "casadi/core/casadi_enum.hpp"
%import "casadi/core/casadi_export.h"
%import "casadi/core/casadi_interrupt.hpp"
%import "casadi/core/casadi_limits.hpp"
%import "casadi/core/casadi_logger.hpp"
%import "casadi/core/casadi_meta.hpp"
%import "casadi/core/casadi_misc.hpp"
%import "casadi/core/casadi_types.hpp"
%import "casadi/core/code_generator.hpp"
%import "casadi/core/conic.hpp"
%import "casadi/core/core.hpp"
%import "casadi/core/dae_builder.hpp"
%import "casadi/core/dm.hpp"
%import "casadi/core/dm_fwd.hpp"
%import "casadi/core/dple.hpp"
%import "casadi/core/exception.hpp"
%import "casadi/core/expm.hpp"
%import "casadi/core/external.hpp"
%import "casadi/core/fmu.hpp"
%import "casadi/core/function.hpp"
%import "casadi/core/generic_expression.hpp"
%import "casadi/core/generic_matrix.hpp"
%import "casadi/core/generic_type.hpp"
%import "casadi/core/global_options.hpp"
%import "casadi/core/im.hpp"
%import "casadi/core/im_fwd.hpp"
%import "casadi/core/importer.hpp"
%import "casadi/core/integration_tools.hpp"
%import "casadi/core/integrator.hpp"
%import "casadi/core/interpolant.hpp"
%import "casadi/core/linsol.hpp"
%import "casadi/core/matrix_decl.hpp"
%import "casadi/core/matrix_fwd.hpp"
%import "casadi/core/mx.hpp"
%import "casadi/core/nlp_builder.hpp"
%import "casadi/core/nlp_tools.hpp"
%import "casadi/core/nlpsol.hpp"
%import "casadi/core/nonzeros.hpp"
%import "casadi/core/options.hpp"
%import "casadi/core/optistack.hpp"
%import "casadi/core/polynomial.hpp"
%import "casadi/core/printable.hpp"
%import "casadi/core/rootfinder.hpp"
%import "casadi/core/serializer.hpp"
%import "casadi/core/serializing_stream.hpp"
%import "casadi/core/shared_object.hpp"
%import "casadi/core/slice.hpp"
%import "casadi/core/sparsity.hpp"
%import "casadi/core/sparsity_interface.hpp"
%import "casadi/core/submatrix.hpp"
//%import "casadi/core/sx.hpp"
%import "casadi/core/sx_elem.hpp"
//%import "casadi/core/sx_fwd.hpp"
%import "casadi/core/timing.hpp"
%import "casadi/core/xml_file.hpp"
%import "casadi/core/xml_node.hpp"
*/

//////
// Im Folgenden etwa die gleiche Reihenfolge wie  in "casadi/core/core.hpp"
//////

// Generic tools
//%include "casadi/core/polynomial.hpp"
//%include "casadi/core/casadi_misc.hpp"
//%include "casadi/core/global_options.hpp"
//%include "casadi/core/casadi_meta.hpp"

// Matrices

//%import "casadi/core/core.hpp"

// %interface_custom("MatrixCommon_Proxy", "MatrixCommon_Interface", casadi::MatrixCommon)

//// Start: SX

// #pragma SWIG nowarn=503

// %interface(casadi::GenericExpressionCommon)
// class casadi::GenericExpressionCommon {};
%ignore casadi::GenericExpressionCommon;
%ignore casadi::GenericExpression::printme;

// %interface(casadi::MatrixCommon)
// %interface(casadi::SparsityInterfaceCommon)
// %interface(casadi::GenericMatrixCommon)
// %interface(casadi::PrintableCommon)
%ignore casadi::MatrixCommon;
%ignore casadi::SparsityInterfaceCommon;
%ignore casadi::GenericMatrixCommon;
%ignore casadi::PrintableCommon;

// %import "casadi/core/sx_elem.hpp"
class casadi::SXElem;
#undef SWIG
%import "casadi/core/sparsity_interface.hpp"
%import "casadi/core/generic_expression.hpp"
%import "casadi/core/printable.hpp"
#define SWIG
#define DOXYGEN
%import "casadi/core/generic_matrix.hpp"
#undef DOXYGEN
%import "casadi/core/matrix_decl.hpp"
#undef SWIG
// %import "casadi/core/matrix_decl.hpp"
// %import "casadi/core/sx_fwd.hpp"
%include "casadi/core/sx.hpp"
#define SWIG

// Kommt ursprünglich aus GenericMatrix, das eine Superklasse von Matrix ist.
%extend casadi::Matrix<casadi::SXElem> {
	// casadi::SubIndex<casadi::Matrix<casadi::SXElem>, int> at(const int &rr) {return  (*($self))(rr);}
	// Don't need to wrap SubIndex because use of baseclass Matrix is sufficient.
	casadi::Matrix<casadi::SXElem> at(const int &rr) {return (*($self))(rr);}
}

%template_interface("SxSparsity", casadi::SparsityInterface< casadi::Matrix< casadi::SXElem > >)
%template_interface("SxGenericMatrix", casadi::GenericMatrix< casadi::Matrix< casadi::SXElem > >)
%template_interface("SxGenericExpression", casadi::GenericExpression< casadi::Matrix< casadi::SXElem > >)
%template_interface("SxPrintable", casadi::Printable< casadi::Matrix< casadi::SXElem > >)
%template(SX) casadi::Matrix<casadi::SXElem>;

// %import "casadi/core/submatrix.hpp"
// %template(SxSubIndex) casadi::SubIndex<casadi::Matrix<casadi::SXElem>, int>;

//#pragma SWIG nowarn=+503

//%template(SXVector) std::vector<SX>;
//%template(SXIList) std::initializer_list<SX>;
//%template(SXVectorVector) std::vector<SXVector> SXVectorVector;
//%template(SXDict) std::map<std::string, SX>;

//// Stop: SX

//%include "casadi/core/dm.hpp"
//%include "casadi/core/im.hpp"

// Matrix expressions
// %include "casadi/core/mx.hpp"

// Functions
//%include "casadi/core/code_generator.hpp"
//%include "casadi/core/includeer.hpp"
//%include "casadi/core/callback.hpp"
//%include "casadi/core/integrator.hpp"
//%include "casadi/core/conic.hpp"
//%include "casadi/core/nlpsol.hpp"
//%include "casadi/core/rootfinder.hpp"
//%include "casadi/core/linsol.hpp"
//%include "casadi/core/dple.hpp"
//%include "casadi/core/expm.hpp"
//%include "casadi/core/interpolant.hpp"
//%include "casadi/core/external.hpp"

// Misc
//%include "casadi/core/integration_tools.hpp"
//%include "casadi/core/nlp_tools.hpp"
//%include "casadi/core/nlp_builder.hpp"
//%include "casadi/core/dae_builder.hpp"
//%include "casadi/core/xml_file.hpp"
//%include "casadi/core/optistack.hpp"
//%include "casadi/core/serializer.hpp"


//"Submodules"

