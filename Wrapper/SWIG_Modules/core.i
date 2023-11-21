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

#pragma SWIG nowarn=503,401,320,476,516

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

// Needed, so that all occurrences of dict are correctly matched.
typedef std::map< std::string, casadi::GenericType > casadi::Dict;
typedef casadi::Dict Dict;
// From: generic_type.hpp
// Weird error with dict::Iterator if namespace "casadi::" is missing!
%template(Dict) std::map< std::string, casadi::GenericType >;

typedef casadi::GenericType GenericType;

// From: casadi_misc.hpp
typedef std::vector<std::string> StringVector;

%template(StdVectorBool) std::vector<bool>;
%template(StdVectorCasadiFunction) std::vector<casadi::Function>;
%template(StdVectorCasadiGenericType) std::vector<casadi::GenericType>;
%template(StdVectorCasadiLinsol) std::vector<casadi::Linsol>;
%template(StdVectorCasadiSXElem) std::vector<casadi::SXElem>;
%template(StdVectorCasadiSparsity) std::vector<casadi::Sparsity>;

//// Stop: SWIGTYPE's removal


//// Start: SX

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

%ignore casadi::GenericMatrix::sparsity; // %ignore casadi::Matrix::sparsity;

%ignore casadi::SparsityInterface::mtimes; // %ignore casadi::Matrix::mtimes;
%ignore casadi::SparsityInterface::vertsplit;
%ignore casadi::SparsityInterface::diagsplit;
%ignore casadi::SparsityInterface::horzsplit;
%ignore casadi::SparsityInterface::horzsplit_n;

%rename (nnz_) casadi::GenericMatrix::nnz; // %ignore casadi::GenericMatrix::nnz;
%rename (numel_) casadi::GenericMatrix::numel;
%rename (jtimes_) casadi::GenericMatrix::jtimes;
%rename (nnz_lower_) casadi::GenericMatrix::nnz_lower;
%rename (nnz_upper_) casadi::GenericMatrix::nnz_upper;
%rename (size1_) casadi::GenericMatrix::size1;
%rename (size2_) casadi::GenericMatrix::size2;
%rename (is_dense_) casadi::GenericMatrix::is_dense;
%rename (is_vector_) casadi::GenericMatrix::is_vector;
%rename (is_row_) casadi::GenericMatrix::is_row;
%rename (is_column_) casadi::GenericMatrix::is_column;
%rename (is_triu_) casadi::GenericMatrix::is_triu;
%rename (is_tril_) casadi::GenericMatrix::is_tril;

// Start: Überladungen, die in Netbeans Probleme machen.
%rename (dim_) casadi::GenericMatrix::dim;
%rename (size_) casadi::GenericMatrix::size;
%rename (is_empty_) casadi::GenericMatrix::is_empty;
%rename (is_scalar_) casadi::GenericMatrix::is_scalar;
%rename (row_) casadi::GenericMatrix::row;
%rename (colind_) casadi::GenericMatrix::colind;
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

// From here: typedef std::vector<SX> SXVector;
%import "casadi/core/sx_fwd.hpp"

// Muss womöglich vor function.hpp stehen, wo das verwendet wird.
// Beim MX scheint es nicht notwendig zu sein...
typedef casadi::Matrix<casadi::SXElem> SX;
%template(StdVectorSX) std::vector<SX>;

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

%import "casadi/core/dm_fwd.hpp"
typedef casadi::DMDict DMDict;

// dm_fwd.hpp
typedef std::vector<DM> DMVector;
%template(StdVectorDM) std::vector<DM>;
//// Stop: DM

//// Start: MX
%ignore casadi::MX::repmat; // Still in SparsityInterface which is superclass.

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

%ignore casadi::ConvexifyData; // Used only in CodeGenerator within "#ifndef SWIG".
#undef SWIG
// %import "casadi/core/sx_fwd.hpp"
%include "casadi/core/mx.hpp"
#define SWIG

// typedef casadi::MXVector;
typedef casadi::MXIList MXIList;
typedef casadi::MXVectorVector MXVectorVector;
typedef casadi::MXDict MXDict;

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
#undef SWIG
%include <casadi/core/generic_type.hpp>
#define SWIG

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
// #undef SWIG
%include <casadi/core/code_generator.hpp>
// #define SWIG

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

