package de.dhbw.rahmlab.casadi.implUtil;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class CleaneablePhantomReference<T> extends PhantomReference<T> {

	private final Runnable cleanupAction;
	public final int index;

	public CleaneablePhantomReference(T referent, ReferenceQueue<? super T> q, Runnable cleanupAction, int index) {
		super(referent, q);
		this.cleanupAction = cleanupAction;
		this.index = index;
	}

	public void cleanup() {
		this.cleanupAction.run();
	}
}
