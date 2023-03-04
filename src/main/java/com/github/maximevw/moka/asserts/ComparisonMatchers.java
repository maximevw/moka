/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.asserts;

import java.math.BigInteger;
import java.util.function.Function;

/**
 * Provides comparison matchers for assertions using {@link ComparisonMatcher} interface.
 */
public final class ComparisonMatchers {

	private ComparisonMatchers() {
		// Private constructor hiding the public default one.
	}

	private static <T extends Comparable<T>> ComparisonMatcher<T> buildMatcher(
		final Function<T, Boolean> comparableFunction) {
		return new ComparisonMatcher<>() {
			@Override
			public boolean comparesTo(final T value) {
				return comparableFunction.apply(value);
			}
		};
	}

	/**
	 * The matched value is greater or equal to the given {@code value}.
	 *
	 * @param value The comparator value.
	 * @return {@code true} if the matched value is greater or equal to {@code value}, {@code false} otherwise.
	 */
	public static ComparisonMatcher<BigInteger> atLeast(final BigInteger value) {
		return buildMatcher(comparedToValue -> comparedToValue.compareTo(value) >= 0);
	}

	/**
	 * The matched value is less or equal to the given {@code value}.
	 *
	 * @param value The comparator value.
	 * @return {@code true} if the matched value is less or equal to {@code value}, {@code false} otherwise.
	 */
	public static ComparisonMatcher<BigInteger> atMost(final BigInteger value) {
		return buildMatcher(comparedToValue -> comparedToValue.compareTo(value) <= 0);
	}

}
