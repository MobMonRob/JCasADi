%module(directors="1") core__;

// Own generic .i files
%include "_common.i"


// SWIG lib .i fles


%{
#include "casadi/core/casadi_common.hpp"
#include "casadi/core/core.hpp"

using namespace casadi;
%}

%import "casadi/core/casadi_common.hpp" // #define SWIG_IF_ELSE
%import "casadi/core/casadi_types.hpp"

/// Data structure in the target language holding data
#define GUESTOBJECT void

// #pragma SWIG nowarn=509,303,302

#define CASADI_EXPORT

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

// Scalar expressions (why do I need to put it up here?)
//%include "sx_elem.hpp"

// Generic tools
//%include "polynomial.hpp"
//%include "casadi_misc.hpp"
//%include "global_options.hpp"
//%include "casadi_meta.hpp"

// Matrices
//%include "sx.hpp"
//%include "dm.hpp"
//%include "im.hpp"

// Matrix expressions
%include "casadi/core/mx.hpp"

// Functions
//%include "code_generator.hpp"
//%include "includeer.hpp"
//%include "callback.hpp"
//%include "integrator.hpp"
//%include "conic.hpp"
//%include "nlpsol.hpp"
//%include "rootfinder.hpp"
//%include "linsol.hpp"
//%include "dple.hpp"
//%include "expm.hpp"
//%include "interpolant.hpp"
//%include "external.hpp"

// Misc
//%include "integration_tools.hpp"
//%include "nlp_tools.hpp"
//%include "nlp_builder.hpp"
//%include "dae_builder.hpp"
//%include "xml_file.hpp"
//%include "optistack.hpp"
//%include "serializer.hpp"


//"Submodules"

