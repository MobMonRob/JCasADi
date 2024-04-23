package de.dhbw.rahmlab.casadi.implUtil;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class CleaningPhantomReference<T> extends PhantomReference<T> {

	private final Runnable cleanupAction;

	public CleaningPhantomReference(T referent, ReferenceQueue<? super T> q, Runnable cleanupAction) {
		super(referent, q);
		this.cleanupAction = cleanupAction;
	}

	public void cleanUp() {
		this.cleanupAction.run();
	}
}
