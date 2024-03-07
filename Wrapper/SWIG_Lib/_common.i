%feature("nspace");
//Muss unbedingt vor den Template Instanzierungen stehen, damit die zugreifbar bleiben.
//Refer to end of section of http://www.swig.org/Doc4.0/Java.html#Java_code_typemaps
//Nur notwendig, wenn nspace Feature aktiv
SWIG_JAVABODY_PROXY(public, public, SWIGTYPE)
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
%}

//Unknown Doxygen command
#pragma SWIG nowarn=560

%pragma(java) moduleimports=%{
import java.lang.ref.Cleaner;
import java.lang.ref.Reference;
%}

%pragma(java) modulecode=%{
public static final LifeTimeExtender LIFE_TIME_EXTENDER = new LifeTimeExtender();

public static final class LifeTimeExtender {

	private final Cleaner cleaner = Cleaner.create();

	private static Runnable reachabilityCleanup(final Object o) {
		return () -> {
			Reference.reachabilityFence(o);
		};
	}

	public void extend(final Object toBeExtendedLifeTime, final Object extendedToLifeTime) {
		this.cleaner.register(extendedToLifeTime, LifeTimeExtender.reachabilityCleanup(toBeExtendedLifeTime));
	}
}

public static interface LongCall {
	long call();
}

public static long longCall(LongCall cl) {
	return cl.call();
}

%}

