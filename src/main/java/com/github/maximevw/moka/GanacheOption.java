/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka;

import com.github.maximevw.moka.enums.GanacheVersionLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Alternatives for a Ganache CLI option depending on the targeted version level of Ganache.
 */
@NoArgsConstructor
public class GanacheOption {

	final Map<GanacheVersionLevel, String> optionsByVersionLevel = new HashMap<>();

	/**
	 * Adds an option.
	 *
	 * @param versionLevel The targeted Ganache version level.
	 * @param optionName The option name.
	 * @return The instance of {@code GanacheOption} including the added option.
	 */
	public GanacheOption addOption(final GanacheVersionLevel versionLevel, final String optionName) {
		this.optionsByVersionLevel.put(versionLevel, optionName);
		return this;
	}

	/**
	 * Gets the appropriate option for the given Ganache version level.
	 *
	 * @param versionLevel The version level.
	 * @return The option name.
	 */
	public String getOptionNameForVersion(final GanacheVersionLevel versionLevel) {
		return this.optionsByVersionLevel.get(versionLevel);
	}

}
