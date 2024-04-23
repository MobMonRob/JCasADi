package de.dhbw.rahmlab.casadi.implUtil;

import java.lang.ref.ReferenceQueue;

/**
 * Not thread-safe.
 */
public class ManualCleaner {

	private final ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

	public void register(Object referent, Runnable cleanupAction) {
		CleaneablePhantomReference.create(referent, this.referenceQueue, cleanupAction);
	}

	public CleanupPreventer registerGetPreventer(Object referent, Runnable cleanupAction) {
		CleaneablePhantomReference ref = CleaneablePhantomReference.create(referent, this.referenceQueue, cleanupAction);
		return new CleanupPreventer(ref);
	}

	public void cleanupUnreachable() {
		CleaneablePhantomReference ref;
		for (;;) {
			ref = (CleaneablePhantomReference) this.referenceQueue.poll();
			if (ref == null) {
				return;
			}
			ref.cleanup();
		}
	}
}
