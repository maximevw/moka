/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.asserts;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AssertionFailureBuilder;

import java.util.Optional;

/**
 * A matcher to compare two {@link Comparable}s representing a variation between two values. The matched value <b>V</b>
 * represents the effective variation and is compared to the expected variation <b>E</b>.
 *
 * @param <T> A comparable type.
 */
public interface VariationMatcher<T extends Comparable<T>> {

	/**
	 * Compares the matched value (the effective variation) to the expected variation.
	 *
	 * @param value The expected value to which the matched one is compared.
	 * @return {@code true} if the expected comparison is verified, {@code false} otherwise.
	 */
	boolean comparesTo(T value);

	/**
	 * Describes the mismatch for usage in {@link AssertionFailureBuilder} methods.
	 *
	 * @return The mismatch description.
	 */
	default String describeMismatch() {
		return StringUtils.EMPTY;
	}

	/**
	 * The minimal expected variation (i.e. a variation of at least the given value).
	 *
	 * @return The minimal variation or an empty {@link Optional} if not applicable.
	 */
	default Optional<T> minimalVariation() {
		return Optional.empty();
	}

	/**
	 * The maximal expected variation (i.e. a variation of at most the given value).
	 *
	 * @return The maximal variation or an empty {@link Optional} if not applicable.
	 */
	default Optional<T> maximalVariation() {
		return Optional.empty();
	}

}
