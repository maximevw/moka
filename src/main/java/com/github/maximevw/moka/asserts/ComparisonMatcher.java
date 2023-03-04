/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.asserts;

/**
 * A matcher to compare two {@link Comparable}s.
 *
 * @param <T> A comparable type.
 */
public interface ComparisonMatcher<T extends Comparable<T>> {

	/**
	 * Compares the matched value to a given one.
	 *
	 * @param value The value to which the matched one is compared.
	 * @return {@code true} if the expected comparison is verified, {@code false} otherwise.
	 */
	boolean comparesTo(T value);

}
