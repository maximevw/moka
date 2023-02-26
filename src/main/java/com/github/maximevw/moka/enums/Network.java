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
 * The network name used for forks.
 *
 * @see GanacheContainer#withForkNetwork(Network)
 */
public enum Network {

	/**
	 * Mainnet network.
	 */
	MAINNET("mainnet"),
	/**
	 * Görli test network.
	 */
	GOERLI("goerli"),
	/**
	 * Sepolia network.
	 */
	SEPOLIA("sepolia");

	@Getter
	private final String networkName;

	Network(final String networkName) {
		this.networkName = networkName;
	}

}
