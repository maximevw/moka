/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.enums;

import com.github.maximevw.moka.GanacheContainer;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.github.maximevw.moka.enums.GanacheVersionLevel.V6;
import static com.github.maximevw.moka.enums.GanacheVersionLevel.V7;

/**
 * The chain hardfork rules.
 *
 * @see GanacheContainer#withHardfork(ChainHardForkRule)
 */
public enum ChainHardForkRule {

	/**
	 * ArrowGlacier rule.
	 */
	ARROW_GLACIER("arrowGlacier", V7),
	/**
	 * Berlin rule.
	 */
	BERLIN("berlin", V7),
	/**
	 * Byzantium rule.
	 */
	BYZANTIUM("byzantium", V6, V7),
	/**
	 * Constantinople rule.
	 */
	CONSTANTINOPLE("constantinople", V6, V7),
	/**
	 * GrayGlacier rule.
	 */
	GRAY_GLACIER("grayGlacier", V6, V7),
	/**
	 * Istanbul rule.
	 */
	ISTANBUL("istanbul", V6, V7),
	/**
	 * London rule.
	 */
	LONDON("london", V7),
	/**
	 * Merge rule.
	 */
	MERGE("merge", V7),
	/**
	 * MuirGlacier rule.
	 */
	MUIR_GLACIER("muirGlacier", V6, V7),
	/**
	 * Petersburg rule.
	 */
	PETERSBURG("petersburg", V6, V7);

	@Getter
	private final String ruleName;
	private final List<GanacheVersionLevel> supportedVersions;

	ChainHardForkRule(final String ruleName, final GanacheVersionLevel... supportedVersions) {
		this.ruleName = ruleName;
		this.supportedVersions = Arrays.asList(supportedVersions);
	}

	/**
	 * Whether the specified Ganache version level supports the hardfork rule.
	 *
	 * @param versionLevel The Ganache version level.
	 * @return {@code true} if the hardfork rule is supported, {@code true} otherwise.
	 */
	public boolean supports(final GanacheVersionLevel versionLevel) {
		return this.supportedVersions.contains(versionLevel);
	}
}
