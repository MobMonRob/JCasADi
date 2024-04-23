package de.dhbw.rahmlab.casadi.implUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * <pre>
 * The operations in this list do not move the objects once inserted.
 * Not thread-safe.
 * </pre>
 */
public class FixedIndexList<T> {

	private final List<T> payload;
	private final List<Integer> gaps;

	public FixedIndexList(int initialCapacity) {
		this.payload = new ArrayList<>(initialCapacity);
		this.gaps = new ArrayList<>(initialCapacity);
	}

	/**
	 *
	 * @return index of the inserted element.
	 */
	public int put(Function<Integer, T> tBuilder) {
		int index = acquireFreeIndex();
		T t = tBuilder.apply(index);
		payload.set(index, t);
		return index;
	}

	/**
	 *
	 * @return index of the inserted element.
	 */
	public int put(T t) {
		int index = acquireFreeIndex();
		payload.set(index, t);
		return index;
	}

	/**
	 * Payload at returned index must be set afterwards.
	 */
	private int acquireFreeIndex() {
		if (gaps.isEmpty()) {
			payload.add(null);
			return payload.size() - 1;
		} else {
			int payloadGapIndex = gaps.remove(gaps.size() - 1);
			return payloadGapIndex;
		}
	}

	/**
	 *
	 * @return the element at the specified position in this list
	 */
	public T get(int index) {
		return payload.get(index);
	}

	/**
	 *
	 * @return the element previously at the specified position
	 */
	public T remove(int index) {
		// payload.remove is incorrect. Moves other indices.
		T previous = payload.set(index, null);
		gaps.add(index);
		return previous;
	}
}
