package de.dhbw.rahmlab.casadi.implUtil;

import java.lang.ref.Cleaner;
import java.util.function.LongConsumer;

public class WrapUtil {

	// Fix JVM crash by use-after-free if cpp functions return references to members.
	public static final Cleaner CLEANER = Cleaner.create();
	public static final LifeTimeExtender LIFE_TIME_EXTENDER = new LifeTimeExtender(CLEANER);

	// Fix JVM crashes due to CasADi not being thread-safe.
	public static final ManualCleaner MANUAL_CLEANER = new ManualCleaner();

	private static Runnable createDeletionRunnable(long swigCPtr, LongConsumer deleteFunction) {
		return () -> deleteFunction.accept(swigCPtr);
	}

	public static void REGISTER_DELETION(Object obj, long swigCPtr, LongConsumer deleteFunction) {
		MANUAL_CLEANER.register(obj, createDeletionRunnable(swigCPtr, deleteFunction));
	}
}
