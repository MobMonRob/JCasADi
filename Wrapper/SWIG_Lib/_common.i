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
%}

//Unknown Doxygen command
#pragma SWIG nowarn=560

%pragma(java) moduleimports=%{
import java.lang.ref.Cleaner;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.function.LongConsumer;
%}

// Fix JVM crash by use-after-free if cpp functions return references to members.

%pragma(java) modulecode=%{
public static final Cleaner CLEANER = Cleaner.create();
public static final LifeTimeExtender LIFE_TIME_EXTENDER = new LifeTimeExtender(CLEANER);

public static class LifeTimeExtender {

	private final Cleaner cleaner;

	public LifeTimeExtender(Cleaner cleaner) {
		this.cleaner = cleaner;
	}

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


// Fix JVM crashes due to CasADi not being thread-safe.

%pragma(java) modulecode=%{
	/**
	 * Not thread-safe.
	 */
	public static class ManualCleaner<T> {

		private static class CleaningPhantomReference<T> extends PhantomReference<T> {

			private final Runnable cleanupAction;

			public CleaningPhantomReference(T referent, ReferenceQueue<? super T> q, Runnable cleanupAction) {
				super(referent, q);
				this.cleanupAction = cleanupAction;
			}

			public void cleanUp() {
				this.cleanupAction.run();
			}
		}

		private final ReferenceQueue<T> referenceQueue = new ReferenceQueue<>();
		/**
		 * Needed to prevent CleaningPhantomReference from being garbage collected before enqueued.
		 */
		private final Set<CleaningPhantomReference<T>> registeredRefs = new HashSet<>(1024, 0.5f);

		public void register(T referent, Runnable cleanupAction) {
			CleaningPhantomReference<T> ref = new CleaningPhantomReference<>(referent, this.referenceQueue, cleanupAction);
			this.registeredRefs.add(ref);
		}

		public void cleanupUnreachable() {
			CleaningPhantomReference<T> ref;
			for (;;) {
				ref = (CleaningPhantomReference<T>) this.referenceQueue.poll();
				if (ref == null) {
					return;
				}
				try {
					ref.cleanUp();
				} finally {
					this.registeredRefs.remove(ref);
				}
			}
		}
	}

	public static final ManualCleaner<Object> MANUAL_CLEANER = new ManualCleaner<>();

	public static void REGISTER_DELETION(Object obj, long swigCPtr, LongConsumer deleteFunction) {
		MANUAL_CLEANER.register(obj, () -> deleteFunction.accept(swigCPtr));
	}
%}

// Adjusted definition from https://github.com/swig/swig/blob/v4.0.1/Lib/java/java.swg#L1204
// Change if used SWIG version is different.
%define SWIG_JAVABODY_PROXY_OWN(PTRCTOR_VISIBILITY, CPTR_VISIBILITY, TYPE...)
// Base proxy classes
%typemap(javabody) TYPE %{
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  PTRCTOR_VISIBILITY $javaclassname(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
	if (cMemoryOwn) {
		REGISTER_DELETION(this, this.swigCPtr, $javaclassname::delete);
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
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
	if (cMemoryOwn) {
		REGISTER_DELETION(this, subtype_cPtr, subtype_deleteFunction);
	}
  }

  CPTR_VISIBILITY static long getCPtr($javaclassname obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        $javaclassname.delete(swigCPtr);
      }
      swigCPtr = 0;
    }
  }
%}

// Derived proxy classes
%typemap(javabody_derived) TYPE %{
  private transient long swigCPtr;

  PTRCTOR_VISIBILITY $javaclassname(long cPtr, boolean cMemoryOwn) {
    super($imclassname.$javaclazznameSWIGUpcast(cPtr), cMemoryOwn, cPtr, $javaclassname::delete);
    swigCPtr = cPtr;
  }
  
  CPTR_VISIBILITY static long getCPtr($javaclassname obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @Override
  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (super.swigCMemOwn) {
        super.swigCMemOwn = false;
        $javaclassname.delete(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }
%}
%enddef
SWIG_JAVABODY_PROXY_OWN(public, public, SWIGTYPE)

%typemap(javafinalize) SWIGTYPE %{
  @SuppressWarnings("deprecation")
  @Override
  protected void finalize() {
  }
%}

// GLOBAL_DESTRUCTOR_LOCK not needed if single-threaded deletion is ensured.
%pragma(java) modulecode=%{
// public static final Object GLOBAL_DESTRUCTOR_LOCK = new Object();
%}

%typemap(javadestruct, methodname="delete", methodmodifiers="private static", parameters="long swigCPtr") SWIGTYPE {
	// synchronized (GLOBAL_DESTRUCTOR_LOCK) {
        $jnicall;
	// }
}

%typemap(javadestruct_derived, methodname="delete", methodmodifiers="private static", parameters="long swigCPtr") SWIGTYPE {
	// synchronized (GLOBAL_DESTRUCTOR_LOCK) {
        $jnicall;
	// }
}
