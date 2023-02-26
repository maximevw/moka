/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.entities;

import com.github.maximevw.moka.GanacheContainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;

/**
 * Data used to instantiate accounts with some predefined values.
 *
 * @see GanacheContainer#withAccounts(List)
 */
@Getter
@Builder
@AllArgsConstructor
public class InitAccountData {

	/**
	 * The private key.
	 */
	private String privateKey;

	/**
	 * The account balance in WEI.
	 */
	private BigInteger balanceInWei;

}
