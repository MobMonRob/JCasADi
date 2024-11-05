%module core__;
// To use directors, typemaps for them must be defined in _common.i to properly handle deletion.
// %module(directors="1") core__;

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

#pragma SWIG nowarn=503 // cant't wrap unless renamed to a valid identifier
#pragma SWIG nowarn=476 // Initialization using std::initializer_list.
#pragma SWIG nowarn=516 // Overloaded method const ignored
#pragma SWIG nowarn=320 // Explicit template instantiation ignored.
#pragma SWIG nowarn=401 // Nothing known about base class, ignored. Maybe you forgot to instantiate using %template.
#pragma SWIG nowarn=315 // IM: Nothing known about 'B::horzsplit' ...

// append works for strings
%naturalvar;

// Make data members read-only
%immutable;

// Make sure that a copy constructor is created
%copyctor;

%ignore *::operator->;

%{
	#include "casadi/core/casadi_common.hpp"
	#include "casadi/core/core.hpp"

	using namespace casadi;
%}

#define CASADI_EXPORT

%include "casadi/core/casadi_types.hpp" // UnifiedReturnStatus for solvers
%import "casadi/core/casadi_common.hpp" // #define SWIG_IF_ELSE
#undef SWIG_IF_ELSE
#define SWIG_IF_ELSE(is_swig, not_swig) not_swig

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

//// Start: Fix memory safety issues

// Prevent use-after-free for many cases where methods return memory-unsafe output types.
// Especially references to members.
%typemap(javaout) SWIGTYPE & {
	final long cPtr = $jnicall;
	if (cPtr == 0) return null;
	// false here indicates no ownership transfer to java
	$javaclassname proxy = new $javaclassname(cPtr, $owner);
	// public void extend(final Object toBeExtendedLifeTime, final Object extendedToLifeTime)
	LIFE_TIME_EXTENDER.extend(this, proxy);
	return proxy;
}
%typemap(javaout) SWIGTYPE * {throw new UnsupportedOperationException();}
%ignore casadi::Sparsity::getCache;
%ignore casadi::Sparsity::getScalar;
%ignore casadi::Sparsity::getScalarSparse;
%ignore casadi::Sparsity::getEmpty;
%ignore casadi::Sparsity::file_formats; // Field

%ignore casadi::Matrix::ptr;

// Leads to use-after-free without LIFE_TIME_EXTENDER. Better use casadi::Matrix::get_sparsity.
%ignore casadi::Matrix::sparsity;
// Convenience to avoid renaming.
%rename(sparsity) casadi::Matrix::get_sparsity;

// SWIGTYPE &: C++ Container types will copy the values. Should be safe usually.
// SWIGTYPE *: Pointer types are not wrapped.
/*
%typemap(javain) SWIGTYPE &, SWIGTYPE * "
	longCall(() -> {
		throw new UnsupportedOperationException()
		return $javaclassname.getCPtr($javainput);
	})
"
*/

//// Stop: Fix memory safety issues

//// Start: print fix

%rename(toString) get_str;

// %rename(toString) casadi::Matrix::get_str;

class casadi::SharedObject;
typedef casadi::SharedObject SharedObject;
%interface_custom("SharedObject", "ISharedObject", casadi::SharedObject)
%ignore casadi::WeakRef;

// %rename(toString) casadi::SharedObject::get_str;

#ifndef SWIG
	#define SWIG
#endif
%include "casadi/core/shared_object.hpp"

//// Stop: print fix

//// Start: SWIGTYPE's removal

typedef casadi::Slice Slice;

%template(StdVectorStdString) std::vector<std::string>;

// Needed, so that all occurrences of dict are correctly matched.
typedef std::map< std::string, casadi::GenericType > casadi::Dict;
typedef casadi::Dict Dict;
// From: generic_type.hpp
%rename(DictIter) std::map< std::string, casadi::GenericType >::iterator;
// Weird error with dict::Iterator if namespace "casadi::" is missing!
%template(Dict) std::map< std::string, casadi::GenericType >;

%define MAP_WRAP(prefix, K, T)
%rename("StdMapIter" ## prefix) std::map< K, T >::iterator;
%template("StdMap" ## prefix) std::map< K, T >;
%enddef

MAP_WRAP("StringToMX", std::string, casadi::MX)
typedef casadi::Matrix<casadi::SXElem> casadi::SX;
MAP_WRAP("StringToSX", std::string, casadi::Matrix<casadi::SXElem>)
typedef casadi::Matrix<double> casadi::DM;
MAP_WRAP("StringToDM", std::string, casadi::Matrix<double>)
MAP_WRAP("StringToSparsity", std::string, casadi::Sparsity)
MAP_WRAP("StringToVectorDouble", std::string, std::vector<double>)
MAP_WRAP("StringToVectorString", std::string, std::vector<std::string>)

typedef casadi::GenericType GenericType;

// From: casadi_misc.hpp
typedef std::vector<std::string> StringVector;

%template(StdVectorBool) std::vector<bool>;
%template(StdVectorCasadiFunction) std::vector<casadi::Function>;
%template(StdVectorCasadiGenericType) std::vector<casadi::GenericType>;
%template(StdVectorCasadiLinsol) std::vector<casadi::Linsol>;
%template(StdVectorCasadiSXElem) std::vector<casadi::SXElem>;

%template(StdVectorVectorCasadiSparsity) std::vector<std::vector<casadi::Sparsity>>;
%template(StdVectorCasadiSparsity) std::vector<casadi::Sparsity>;

%template(StdVectorCasadiInt) std::vector<casadi_int>;


//// Stop: SWIGTYPE's removal

// Needed for assignment of matrix elements.
%import "casadi/core/submatrix.hpp"
// If CTYPE is a template instance, %template must be before %extendAt. Otherwise SubIndex and SubMatrix won't be subclasses of CTYPE.
// Example:
// %template(SX) casadi::Matrix<casadi::SXElem>;
// %extendAt("Sx", casadi::Matrix<casadi::SXElem>)
%define %extendAt(PREFIX, CTYPE...)
	%template(PREFIX##"SubIndex") casadi::SubIndex<CTYPE, int>;
	%template(PREFIX##"SubMatrix") casadi::SubMatrix<CTYPE, int, int>;
	%extend CTYPE {
		// Kommt ursprünglich aus GenericMatrix, das eine Superklasse von Matrix ist.
		casadi::SubIndex<CTYPE, int> at(const int &rr) {return (*($self))(rr);}
		casadi::SubMatrix<CTYPE, int, int> at(const int &rr, const int &cc) {return (*($self))(rr, cc);}
		void assign(const CTYPE& other) {(*$self).operator=(other);}
	}
	%extend casadi::SubMatrix<CTYPE, int, int> {
		void assign(const casadi::SubMatrix<CTYPE, int, int>& other) {(*$self).operator=(other);}
		void assign(const CTYPE& other) {(*$self).operator=(other);}
	}
	%extend casadi::SubIndex<CTYPE, int> {
		void assign(const casadi::SubIndex<CTYPE, int>& other) {(*$self).operator=(other);}
		void assign(const CTYPE& other) {(*$self).operator=(other);}
	}
%enddef

//// Start: SX

%ignore casadi::GenericExpressionCommon;
%ignore casadi::GenericExpression::printme;

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
// %ignore casadi::SXElem;
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

%template_interface("SxSparsityInterface", casadi::SparsityInterface< casadi::Matrix< casadi::SXElem > >) // Needed for GenericMatrix
%template_interface("SxGenericMatrix", casadi::GenericMatrix< casadi::Matrix< casadi::SXElem > >)
%template_interface("SxGenericExpression", casadi::GenericExpression< casadi::Matrix< casadi::SXElem > >)
%template(SX) casadi::Matrix<casadi::SXElem>;

// Needs to be after %template(SX)
%extendAt("Sx", casadi::Matrix<casadi::SXElem>)

// From here: typedef std::vector<SX> SXVector;
%import "casadi/core/sx_fwd.hpp"

// Muss womöglich vor function.hpp stehen, wo das verwendet wird.
// Beim MX scheint es nicht notwendig zu sein...
typedef casadi::Matrix<casadi::SXElem> SX;
%template(StdVectorSX) std::vector<SX>;
%template(StdVectorVectorSX) std::vector<std::vector<SX>>;

//// Stop: SX

//// Start: Function

%ignore casadi::Function::buf_in;
%ignore casadi::Function::buf_out;

#undef SWIG
	%include "casadi/core/function.hpp"
#define SWIG

//// Stop: Function

//// Start: std::vector<double>

%template(StdVectorDouble) std::vector<double>;
%template(StdVectorVectorDouble) std::vector<std::vector<double>>;

//// Stop: std::vector<double>

//// Start: IM
// typedef long long int casadi_int;
typedef casadi::Matrix<casadi_int> IM;

%template_interface("ImSparsityInterface", casadi::SparsityInterface< casadi::Matrix< casadi_int > >)
%template_interface("ImGenericMatrix", casadi::GenericMatrix< casadi::Matrix< casadi_int > >)
%template_interface("ImGenericExpression", casadi::GenericExpression< casadi::Matrix< casadi_int > >)
// %template_interface("ImPrintable", casadi::Printable< casadi::Matrix< casadi_int > >)
%template(IM) casadi::Matrix<casadi_int>;

// Needs to be after %template(IM)
%extendAt("Im", casadi::Matrix<casadi_int>)
//// Stop: IM

//// Start: DM
// dm_fwd.hpp
typedef casadi::Matrix<double> DM;

%template_interface("DmSparsityInterface", casadi::SparsityInterface< casadi::Matrix< double > >)
%template_interface("DmGenericMatrix", casadi::GenericMatrix< casadi::Matrix< double > >)
%template_interface("DmGenericExpression", casadi::GenericExpression< casadi::Matrix< double > >)
// %template_interface("DmPrintable", casadi::Printable< casadi::Matrix< double > >)
%template(DM) casadi::Matrix<double>;

// Needs to be after %template(DM)
%extendAt("Dm", casadi::Matrix<double>)

%import "casadi/core/dm_fwd.hpp"
typedef casadi::DMDict DMDict;

// dm_fwd.hpp
typedef std::vector<DM> DMVector;
%template(StdVectorDM) std::vector<DM>;
%template(StdVectorVectorDM) std::vector<std::vector<DM>>;
//// Stop: DM

//// Start: MX
%ignore casadi::MX::repmat; // Still in SparsityInterface which is superclass.

class casadi::MX; // Forward declaration needed for Template instantiation in SWIG.
%template_interface("MxGenericExpression", casadi::GenericExpression< casadi::MX >)
%template_interface("MxSparsityInterface", casadi::SparsityInterface< casadi::MX >) // Needed for GenericMatrix
%template_interface("MxGenericMatrix", casadi::GenericMatrix< casadi::MX >)

%ignore casadi::ConvexifyData; // Used only in CodeGenerator within "#ifndef SWIG".
#undef SWIG
	// %import "casadi/core/sx_fwd.hpp"
	%include "casadi/core/mx.hpp"
#define SWIG

%extendAt("Mx", casadi::MX)
%extend casadi::SubMatrix<casadi::MX, int, int> {
%proxycode %{
	/**
	 * Shows the value if MX is a 1x1 Matrix. Works with MX created via MX.sym(). Be cautious: Will resolve MX dependencies as far as it can. The output is basically MX transformed to SX.
	 */
	@Override
	public String toString() {
		if ((super.columns() != 1) || (super.rows() != 1)) {
			return super.toString();
		}

		var freeMX = $typemap(jstype, casadi::MX).symvar(this);
		var freeSX = freeMX.stream().map(freeVar -> $typemap(jstype, casadi::SX).sym(freeVar.name(), freeVar.rows(), freeVar.columns())).toList();
		
		var inSym = new $typemap(jstype, std::vector<casadi::MX>)(freeMX);
		var outSym = new $typemap(jstype, std::vector<casadi::MX>)(java.util.List.of(this));

		// Allows MX created with MX.sym();
		var options = new $typemap(jstype, casadi::Dict)();
		options.put("allow_free", new $typemap(jstype, std::GenericType)(true));

		var f = new $typemap(jstype, casadi::Function)("f", inSym, outSym, options);

		var inVal = new $typemap(jstype, std::vector<casadi::SX>)(freeSX);
		var outVal = new $typemap(jstype, std::vector<casadi::SX>)();

		f.call(inVal, outVal);
		return outVal.get(0).toString();
	}
%}
}

// typedef casadi::MXVector;
typedef casadi::MXIList MXIList;
typedef casadi::MXVectorVector MXVectorVector;
typedef casadi::MXDict MXDict;

// Wichtig: Namespace "casadi::" vor "MX".
typedef std::vector<casadi::MX> MXVector;
%template(StdVectorMX) std::vector<casadi::MX>;
%template(StdVectorVectorMX) std::vector<std::vector<casadi::MX>>;
//// Stop: MX

//// Start: Sparsity
class casadi::Sparsity;
%template_interface("SparsitySparsityInterface", casadi::SparsityInterface< casadi::Sparsity >)

#undef SWIG
	%include <casadi/core/sparsity.hpp>
#define SWIG
//// Stop: Sparsity

//// Start: Various
#undef SWIG
	%include <casadi/core/generic_type.hpp>
#define SWIG

// class casadi::Slice;
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

