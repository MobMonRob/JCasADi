package de.dhbw.rahmlab.casadi.implUtil;

/**
 * Not thread-safe.
 */
public class CleanupPreventer {

	private CleaneablePhantomReference ref;

	CleanupPreventer(CleaneablePhantomReference ref) {
		this.ref = ref;
	}

	public void prevent() {
		if (ref == null) {
			return;
		}
		this.ref.clear();
		this.ref = null;
	}
}
