package de.dhbw.rahmlab.casadi.implUtil;

import java.lang.ref.ReferenceQueue;
import java.util.function.Function;

/**
 * Not thread-safe.
 */
public class ManualCleaner<T> {

	private final ReferenceQueue<T> referenceQueue = new ReferenceQueue<>();
	/**
	 * Needed to prevent CleaneablePhantomReference from being garbage collected before enqueued.
	 */
	private final FixedIndexList<CleaneablePhantomReference<T>> registeredRefs = new FixedIndexList<>(1024);

	private static <T> Function<Integer, CleaneablePhantomReference<T>> createRef(T referent, ReferenceQueue<T> referenceQueue, Runnable cleanupAction) {
		return (Integer index) -> {
			return new CleaneablePhantomReference<>(referent, referenceQueue, cleanupAction, index);
		};
	}

	public void register(T referent, Runnable cleanupAction) {
		var ref = createRef(referent, this.referenceQueue, cleanupAction);
		this.registeredRefs.put(ref);
	}

	public void cleanupUnreachable() {
		CleaneablePhantomReference<T> ref;
		for (;;) {
			ref = (CleaneablePhantomReference<T>) this.referenceQueue.poll();
			if (ref == null) {
				return;
			}
			try {
				ref.cleanup();
			} finally {
				this.registeredRefs.remove(ref.index);
			}
		}
	}
}
