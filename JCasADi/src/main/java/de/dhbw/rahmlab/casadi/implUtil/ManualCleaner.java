package de.dhbw.rahmlab.casadi.implUtil;

import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;

/**
 * Not thread-safe.
 */
public class ManualCleaner<T> {

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
