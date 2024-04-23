package de.dhbw.rahmlab.casadi.implUtil;

import java.lang.ref.Cleaner;
import java.lang.ref.Reference;

public class LifeTimeExtender {

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
