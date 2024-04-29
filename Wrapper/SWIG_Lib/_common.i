%feature("nspace");
//Muss unbedingt vor den Template Instanzierungen stehen, damit die zugreifbar bleiben.
//Refer to end of section of http://www.swig.org/Doc4.0/Java.html#Java_code_typemaps
//Nur notwendig, wenn nspace Feature aktiv
// SWIG_JAVABODY_PROXY(public, public, SWIGTYPE)
SWIG_JAVABODY_TYPEWRAPPER(public, public, public, SWIGTYPE)
SWIG_JAVABODY_METHODS(public, public, SWIGTYPE)

//Fixes [...]SwigJNI class to invoke NativeLibLoader
%pragma(java) jniclassimports=%{
import de.dhbw.rahmlab.casadi.nativelib.NativeLibLoader;
%}

%pragma(java) jniclasscode=%{
static {
	NativeLibLoader.load();
}
%}

// Import in all Proxy Classes.
// Um trotz "nspace" herauszufinden, wo die "SWIGTYPE_p_[...]" Stummel Proxy Klassen benutzt werden via netbeans "find usages".
%typemap(javaimports) SWIGTYPE
%{
import de.dhbw.rahmlab.casadi.impl.*;
import static de.dhbw.rahmlab.casadi.impl.$module.*;
import java.util.function.LongConsumer;
import static de.dhbw.rahmlab.casadi.implUtil.WrapUtil.*;
%}

//Unknown Doxygen command
#pragma SWIG nowarn=560

// %pragma(java) moduleimports=%{
// %}

// Fix JVM crash by use-after-free if cpp functions return references to members.

// %pragma(java) modulecode=%{

// public static interface LongCall {
// 	long call();
// }

// public static long longCall(LongCall cl) {
// 	return cl.call();
// }

// %}


// Fix JVM crashes due to CasADi not being thread-safe.

// Adjusted definition from https://github.com/swig/swig/blob/v4.0.1/Lib/java/java.swg#L1204
// Change if used SWIG version is different.
%define SWIG_JAVABODY_PROXY_OWN(PTRCTOR_VISIBILITY, CPTR_VISIBILITY, TYPE...)
// Base proxy classes
%typemap(javabody) TYPE %{
  private final long swigCPtr;

  PTRCTOR_VISIBILITY $javaclassname(long cPtr, boolean cMemoryOwn) {
    this.swigCPtr = cPtr;
	  if (cMemoryOwn) {
		  REGISTER_DELETION(this, cPtr, $javaclassname::delete);
  	}
  }

  /**
  * <pre>
  * In C++, deleting a pointer twice is undefined behavior!
  * In C++, deleting an object polymorphically is undefined behavior if the base class does not declare it's constructor as virtual!
  * Using this baseclass constructor for subtypes prevents that.
  * </pre>
  */
  protected $javaclassname(long cPtr, boolean cMemoryOwn, long subtype_cPtr, LongConsumer subtype_deleteFunction) {
    this.swigCPtr = cPtr;
	  if (cMemoryOwn) {
		  REGISTER_DELETION(this, subtype_cPtr, subtype_deleteFunction);
	  }
  }

  CPTR_VISIBILITY static long getCPtr($javaclassname obj) {
    return obj.swigCPtr;
  }
%}

// Derived proxy classes
%typemap(javabody_derived) TYPE %{
  private final long swigCPtr;

  PTRCTOR_VISIBILITY $javaclassname(long cPtr, boolean cMemoryOwn) {
    super($imclassname.$javaclazznameSWIGUpcast(cPtr), cMemoryOwn, cPtr, $javaclassname::delete);
    this.swigCPtr = cPtr;
  }
  
  CPTR_VISIBILITY static long getCPtr($javaclassname obj) {
    return obj.swigCPtr;
  }
%}
%enddef
SWIG_JAVABODY_PROXY_OWN(public, public, SWIGTYPE)

%typemap(javafinalize) SWIGTYPE "";

// %typemap(javafinalize) SWIGTYPE %{
//   @SuppressWarnings("deprecation")
//   @Override
//   protected void finalize() throws Throwable {
// 	  super.finalize();
//   }
// %}

%typemap(javadestruct, methodname="delete", methodmodifiers="private static", parameters="long swigCPtr") SWIGTYPE {
  $jnicall;
}

%typemap(javadestruct_derived, methodname="delete", methodmodifiers="private static", parameters="long swigCPtr") SWIGTYPE {
  $jnicall;
}
