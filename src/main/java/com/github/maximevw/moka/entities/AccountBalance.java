/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.entities;

import lombok.Getter;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.web3j.utils.Convert.fromWei;
import static org.web3j.utils.Convert.toWei;

/**
 * The account balance.
 */
@Getter
public class AccountBalance {

	/**
	 * The balance in WEI.
	 */
	private final BigInteger valueInWei;

	/**
	 * The balance in the unit specified in {@link #getUnit()}.
	 */
	private final BigDecimal valueInUnit;

	/**
	 * The balance unit.
	 */
	private final Convert.Unit unit;

	/**
	 * Instantiates a new balance account.
	 *
	 * @param value The value in the given unit.
	 * @param unit The unit.
	 */
	public AccountBalance(final BigDecimal value, final Convert.Unit unit) {
		if (unit != Convert.Unit.WEI) {
			this.valueInWei = toWei(value, Convert.Unit.WEI).toBigInteger();
		} else {
			this.valueInWei = value.toBigInteger();
		}
		this.valueInUnit = value;
		this.unit = unit;
	}

	/**
	 * Gets the balance value in the specified unit.
	 *
	 * @param unit The unit.
	 * @return The balance value in the specified unit.
	 */
	public BigDecimal getValueIn(final Convert.Unit unit) {
		return fromWei(this.valueInWei.toString(), unit);
	}

	/**
	 * Constructs an account balance with the initial given value in the specified unit.
	 *
	 * @param value The decimal value of the balance.
	 * @param unit 	The unit.
	 * @return An account balance instance.
	 */
	public static AccountBalance of(final BigDecimal value, final Convert.Unit unit) {
		return new AccountBalance(value, unit);
	}

	/**
	 * Constructs an account balance with the initial given value in the specified unit.
	 *
	 * @param value The integer value of the balance.
	 * @param unit 	The unit.
	 * @return An account balance instance.
	 */
	public static AccountBalance of(final BigInteger value, final Convert.Unit unit) {
		return AccountBalance.of(new BigDecimal(value), unit);
	}
}
