package de.dhbw.rahmlab.casadi.implUtil;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.function.Function;

/**
 * <pre>
 * Not thread-safe.
 * Package-private.
 * </pre>
 */
final class CleaneablePhantomReference extends PhantomReference<Object> {

	/**
	 * Prevents CleaneablePhantomReference from being garbage collected before enqueued.
	 */
	private static final FixedIndexList<CleaneablePhantomReference> registeredRefs = new FixedIndexList<>(1024);

	private static Function<Integer, CleaneablePhantomReference> createRef(Object referent, ReferenceQueue<Object> referenceQueue, Runnable cleanupAction) {
		return (Integer index) -> {
			return new CleaneablePhantomReference(referent, referenceQueue, cleanupAction, index);
		};
	}

	public static void createRegister(Object referent, ReferenceQueue<Object> referenceQueue, Runnable cleanupAction) {
		var refBuilder = createRef(referent, referenceQueue, cleanupAction);
		registeredRefs.put(refBuilder);
	}

	private final Runnable cleanupAction;
	private final int index;

	private CleaneablePhantomReference(Object referent, ReferenceQueue<Object> q, Runnable cleanupAction, int index) {
		super(referent, q);
		this.cleanupAction = cleanupAction;
		this.index = index;
	}

	/**
	 * <pre>
	 * At most invoked once by ManualCleaner.
	 * A second invokation is wrong and must be avoided.
	 * </pre>
	 */
	public void cleanup() {
		try {
			this.cleanupAction.run();
		} finally {
			registeredRefs.remove(this.index);
		}
	}
}
