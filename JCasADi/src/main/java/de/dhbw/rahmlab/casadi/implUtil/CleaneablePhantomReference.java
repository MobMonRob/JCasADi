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

	public static CleaneablePhantomReference create(Object referent, ReferenceQueue<Object> referenceQueue, Runnable cleanupAction) {
		var refBuilder = createRef(referent, referenceQueue, cleanupAction);
		int index = registeredRefs.put(refBuilder);
		return registeredRefs.get(index);
	}

	private final Runnable cleanupAction;
	private final int index;

	private CleaneablePhantomReference(Object referent, ReferenceQueue<Object> q, Runnable cleanupAction, int index) {
		super(referent, q);
		this.cleanupAction = cleanupAction;
		this.index = index;
	}

	/**
	 * At most invoked once by ManualCleaner.
	 */
	public void cleanup() {
		try {
			this.cleanupAction.run();
		} finally {
			registeredRefs.remove(this.index);
		}
	}

	/**
	 * <pre>
	 * At most invoked once by CleanupPreventer.prevent() when deleting proxy object.
	 * Prevents enqueing and thus invokation by cleanup.
	 * </pre>
	 */
	@Override
	public void clear() {
		super.clear();
		registeredRefs.remove(this.index);
	}
}
