/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.asserts;

import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;

/**
 * Provides variation matchers for assertions using {@link VariationMatcher} interface.
 */
public final class VariationMatchers {

	private VariationMatchers() {
		// Private constructor hiding the public default one.
	}

	private static <T extends Comparable<T>> VariationMatcher<T> buildMatcher(
		final Function<T, Boolean> comparableFunction, final String mismatchDescription,
		final T minimalVariation, final T maximalVariation) {
		return new VariationMatcher<>() {
			@Override
			public boolean comparesTo(final T value) {
				return comparableFunction.apply(value);
			}

			@Override
			public String describeMismatch() {
				return mismatchDescription;
			}

			@Override
			public Optional<T> minimalVariation() {
				return Optional.ofNullable(minimalVariation);
			}

			@Override
			public Optional<T> maximalVariation() {
				return Optional.ofNullable(maximalVariation);
			}
		};
	}

	/**
	 * The effective variation (matched value) is greater or equal to the given {@code value} (the expected variation).
	 * <p>
	 *     Calling {@link VariationMatcher#minimalVariation()} will return {@code value} and
	 *     {@link VariationMatcher#maximalVariation()} will return an empty {@link Optional}.
	 * </p>
	 * <p>
	 *     Considering an initial value <b>N</b>,
	 *     <ul>
	 *         <li>if <b>N</b> should increase of at least <b>V</b>, the new value expected for <b>N</b> should be more
	 *         than <b>N</b> + <b>V</b></li>
	 *         <li>if <b>N</b> should decrease of at least <b>V</b>, the new value expected for <b>N</b> should be less
	 *         than <b>N</b> - <b>V</b></li>
	 *     </ul>
	 * </p>
	 *
	 * @param value The minimal expected variation.
	 * @return {@code true} if the effective variation is greater or equal to {@code value}, {@code false} otherwise.
	 */
	public static VariationMatcher<BigInteger> atLeast(final BigInteger value) {
		return buildMatcher(comparedToValue -> comparedToValue.compareTo(value) >= 0,
			String.format("at least %d", value), value, null);
	}

	/**
	 * The effective variation (matched value) is less or equal to the given {@code value} (the expected variation).
	 * <p>
	 *     Calling {@link VariationMatcher#minimalVariation()} will return an empty {@link Optional} and
	 *     {@link VariationMatcher#maximalVariation()} will return {@code value}.
	 * </p>
	 * <p>
	 *     Considering an initial value <b>N</b>,
	 *     <ul>
	 *         <li>if <b>N</b> should increase of at most <b>V</b>, the new value expected for <b>N</b> should be more
	 *         than <b>N</b> and less than <b>N</b> + <b>V</b></li>
	 *         <li>if <b>N</b> should decrease of at most <b>V</b>, the new value expected for <b>N</b> should be less
	 *         than <b>N</b> and more than <b>N</b> - <b>V</b></li>
	 *     </ul>
	 * </p>
	 *
	 * @param value The maximal expected variation.
	 * @return {@code true} if the effective variation is less or equal to {@code value}, {@code false} otherwise.
	 */
	public static VariationMatcher<BigInteger> atMost(final BigInteger value) {
		return buildMatcher(comparedToValue -> comparedToValue.compareTo(value) <= 0,
			String.format("at most %d", value), null, value);
	}

}
