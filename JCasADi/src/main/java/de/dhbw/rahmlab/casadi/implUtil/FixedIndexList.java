package de.dhbw.rahmlab.casadi.implUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * The operations in this list do not move the objects once inserted.
 */
public class FixedIndexList<T> {

	private final List<T> payload;
	private final List<Integer> gaps;

	public FixedIndexList(int initialCapacity) {
		this.payload = new ArrayList<>(initialCapacity);
		this.gaps = new ArrayList<>(initialCapacity);
	}

	public void put(Function<Integer, T> tBuilder) {
		int index = acquireFreeIndex();
		T t = tBuilder.apply(index);
		payload.set(index, t);
	}

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
