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

/**
 * The instamine mode.
 *
 * @see GanacheContainer#withInstamine(Instamine)
 */
public enum Instamine {

	/**
	 * Eager instamine mode.
	 */
	EAGER("eager"),
	/**
	 * Strict instamine mode.
	 */
	STRICT("strict");

	@Getter
	private final String modeName;

	Instamine(final String modeName) {
		this.modeName = modeName;
	}

}
