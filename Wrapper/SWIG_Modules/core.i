%module(directors="1") core__;

// Own generic .i files
%include "_common.i"
%include "template_interface.i"
%include "my_std_map.i"

// SWIG lib .i fles
%include <swiginterface.i>
// don't include std_map.i because it conflicts with my_std_map.i.
%include <std_string.i>
%include <std_vector.i>

////////////////

%{
#include "casadi/core/casadi_common.hpp"
#include "casadi/core/core.hpp"

using namespace casadi;
%}

#define CASADI_EXPORT

%include "casadi/core/casadi_types.hpp"
%import "casadi/core/casadi_common.hpp" // #define SWIG_IF_ELSE
#undef SWIG_IF_ELSE
#define SWIG_IF_ELSE(is_swig, not_swig) not_swig

/// Data structure in the target language holding data
// #define GUESTOBJECT void

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

//// Start: SWIGTYPE's removal

typedef casadi::Slice Slice;

%template(StdVectorStdString) std::vector<std::string>;

class casadi::GenericType;
typedef casadi::GenericType GenericType;
// From: generic_type.hpp
// Weird error with dict::Iterator if namespace "casadi::" is missing!
%template(Dict) std::map< std::string, casadi::GenericType >;

//// Start: SWIGTYPE's removal


//// Start: SX

#pragma SWIG nowarn=503

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

%ignore sparsity;
%ignore mtimes;
%ignore vertsplit;
%ignore nnz;
%ignore numel;
%ignore diagsplit;
%ignore horzsplit;
%ignore horzsplit_n;
%ignore jtimes;

%ignore nnz_lower;
%ignore nnz_upper;
%ignore size1;
%ignore size2;
%ignore is_dense;
%ignore is_vector;
%ignore is_row;
%ignore is_column;
%ignore is_triu;
%ignore is_tril;

// Start: Überladungen, die in Netbeans Probleme machen.
%ignore dim;
%ignore size;
%ignore is_empty;
%ignore is_scalar;
%ignore row;
%ignore colind;
// Stop: Überladungen, die in Netbeans Probleme machen.

// %import "casadi/core/sx_elem.hpp"
class casadi::SXElem {

};
#undef SWIG
%import "casadi/core/sparsity_interface.hpp"
%import "casadi/core/generic_expression.hpp"
%import "casadi/core/printable.hpp"
%import "casadi/core/generic_matrix.hpp"
%import "casadi/core/matrix_decl.hpp"
#define SWIG
#undef SWIG
// %import "casadi/core/sx_fwd.hpp"
%include "casadi/core/sx.hpp"
#define SWIG

// Kommt ursprünglich aus GenericMatrix, das eine Superklasse von Matrix ist.
%extend casadi::Matrix<casadi::SXElem> {
	// casadi::SubIndex<casadi::Matrix<casadi::SXElem>, int> at(const int &rr) {return  (*($self))(rr);}
	// Don't need to wrap SubIndex because use of baseclass Matrix is sufficient.
	casadi::Matrix<casadi::SXElem> at(const int &rr) {return (*($self))(rr);}
}

%template_interface("SxSparsityInterface", casadi::SparsityInterface< casadi::Matrix< casadi::SXElem > >) // Needed for GenericMatrix
%template_interface("SxGenericMatrix", casadi::GenericMatrix< casadi::Matrix< casadi::SXElem > >)
%template_interface("SxGenericExpression", casadi::GenericExpression< casadi::Matrix< casadi::SXElem > >)
// %template_interface("SxPrintable", casadi::Printable< casadi::Matrix< casadi::SXElem > >)
%template(SX) casadi::Matrix<casadi::SXElem>;

// %import "casadi/core/submatrix.hpp"
// %template(SxSubIndex) casadi::SubIndex<casadi::Matrix<casadi::SXElem>, int>;

//#pragma SWIG nowarn=+503

//%template(SXVector) std::vector<SX>;
//%template(SXIList) std::initializer_list<SX>;
//%template(SXVectorVector) std::vector<SXVector> SXVectorVector;
//%template(SXDict) std::map<std::string, SX>;

//// Stop: SX

//// Start: Function
%ignore SharedObject;
// %template_interface("FunctionPrintable", casadi::Printable< casadi::Function >)
// %interface_custom("SharedObject", "ISharedObject", casadi::SharedObject)

// %interface_custom("SharedObjectInternal", "ISharedObjectInternal", casadi::SharedObjectInternal)
// %interface_custom("FunctionInternal", "IFunctionInternal", casadi::FunctionInternal)

// %inline %{
// // To trick the cpp compiler. Unsure, if will lead to errors.
// class casadi::SharedObjectInternal {

// };
// class casadi::FunctionInternal : public casadi::SharedObjectInternal {

// };
// /////
// %}

// Muss womöglich vor function.hpp stehen, wo das verwendet wird.
// Beim MX scheint es nicht notwendig zu sein...
typedef casadi::Matrix<casadi::SXElem> SX;
%template(StdVectorSx) std::vector<SX>;

#undef SWIG
// %include "casadi/core/shared_object.hpp"
%include "casadi/core/function.hpp"
#define SWIG

//// Stop: Function

//// Start: std::vector<double>

%template(StdVectorDouble) std::vector<double>;

//// Stop: std::vector<double>

//// Start: DM
// dm_fwd.hpp
typedef casadi::Matrix<double> DM;

// Kommt ursprünglich aus GenericMatrix, das eine Superklasse von Matrix ist.
%extend casadi::Matrix<double> {
	// casadi::SubIndex<casadi::Matrix<double>, int> at(const int &rr) {return  (*($self))(rr);}
	// Don't need to wrap SubIndex because use of baseclass Matrix is sufficient.
	casadi::Matrix<double> at(const int &rr) {return (*($self))(rr);}
}

%template_interface("DmSparsityInterface", casadi::SparsityInterface< casadi::Matrix< double > >)
%template_interface("DmGenericMatrix", casadi::GenericMatrix< casadi::Matrix< double > >)
%template_interface("DmGenericExpression", casadi::GenericExpression< casadi::Matrix< double > >)
// %template_interface("DmPrintable", casadi::Printable< casadi::Matrix< double > >)
%template(DM) casadi::Matrix<double>;

// dm_fwd.hpp
typedef std::vector<DM> DMVector;
%template(StdVectorDM) std::vector<DM>;
//// Stop: DM

//// Start: MX
// Avoid SWIG Error
%ignore repmat;

class casadi::MX; // Forward declaration needed for Template instantiation in SWIG.
%template_interface("MxGenericExpression", casadi::GenericExpression< casadi::MX >)
// %template_interface("MxPrintable", casadi::Printable< casadi::MX >)
%template_interface("MxSparsityInterface", casadi::SparsityInterface< casadi::MX >) // Needed for GenericMatrix
%template_interface("MxGenericMatrix", casadi::GenericMatrix< casadi::MX >)

// %inline %{
// // To trick the cpp compiler. Unsure, if will lead to errors.
// class casadi::MXNode : public casadi::SharedObjectInternal {

// };
// /////
// %}

%ignore casadi::ConvexifyData;
#undef SWIG
// %import "casadi/core/sx_fwd.hpp"
%include "casadi/core/mx.hpp"
#define SWIG

// Wichtig: Namespace "casadi::" vor "MX".
typedef std::vector<casadi::MX> MXVector;
%template(StdVectorMX) std::vector<casadi::MX>;
//// Stop: MX

//// Start: Sparsity
class casadi::Sparsity;
%template_interface("SparsitySparsityInterface", casadi::SparsityInterface< casadi::Sparsity >)
// %template_interface("SparsityPrintable", casadi::Printable< casadi::Sparsity >)

// %inline %{
// // To trick the cpp compiler. Unsure, if will lead to errors.
// class casadi::SparsityInternal : public casadi::SharedObjectInternal {

// };
// /////
// %}

#undef SWIG
%include <casadi/core/sparsity.hpp>
#define SWIG
//// Stop: Sparsity

//// Start: Various
%include <casadi/core/generic_type.hpp>

// class casadi::Slice;
// %template_interface("SlicePrintable", casadi::Printable< casadi::Slice >)
%ignore casadi::Slice::Slice(casadi_int, bool);
%include <casadi/core/slice.hpp>

%include <casadi/core/calculus.hpp>
//// Stop: Various

%include <casadi/core/external.hpp>
%include <casadi/core/integrator.hpp>
%include <casadi/core/conic.hpp>
%include <casadi/core/nlpsol.hpp>
%include <casadi/core/rootfinder.hpp>
%include <casadi/core/linsol.hpp>
%include <casadi/core/dple.hpp>
%include <casadi/core/expm.hpp>
%include <casadi/core/interpolant.hpp>

%feature("copyctor", "0") casadi::CodeGenerator;
%include <casadi/core/code_generator.hpp>

%include <casadi/core/importer.hpp>

// %feature("director") casadi::Callback;
%rename (close) casadi::Callback::finalize();
%include <casadi/core/callback.hpp>

%include <casadi/core/global_options.hpp>
%include <casadi/core/casadi_meta.hpp>
%include <casadi/core/integration_tools.hpp>
%include <casadi/core/nlp_tools.hpp>
%include <casadi/core/nlp_builder.hpp>

// %inline %{
// // To trick the cpp compiler. Unsure, if will lead to errors.
// class casadi::DaeBuilderInternal : public casadi::SharedObjectInternal {

// };
// /////
// %
%include <casadi/core/dae_builder.hpp>

%include <casadi/core/xml_file.hpp>

%feature("copyctor", "0") casadi::SerializerBase;
%feature("copyctor", "0") casadi::DeserializerBase;
%feature("copyctor", "0") casadi::StringSerializer;
%feature("copyctor", "0") casadi::StringDeserializer;
%feature("copyctor", "0") casadi::FileSerializer;
%feature("copyctor", "0") casadi::FileDeserializer;
%nodefaultctor casadi::SerializerBase;
%nodefaultctor casadi::DeserializerBase;
%include <casadi/core/serializer.hpp>

%feature("director") casadi::OptiCallback;
%include <casadi/core/optistack.hpp>

//"Submodules"

