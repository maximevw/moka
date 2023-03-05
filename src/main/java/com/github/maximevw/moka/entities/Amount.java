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
 * An amount in WEI or another valid unit.
 *
 * @see Convert.Unit
 */
@Getter
public class Amount implements Comparable<Amount> {

	/**
	 * The amount in WEI.
	 */
	private final BigInteger valueInWei;

	/**
	 * The amount in the unit specified in {@link #getUnit()}.
	 */
	private final BigDecimal valueInUnit;

	/**
	 * The amount unit.
	 */
	private final Convert.Unit unit;

	/**
	 * Instantiates a new amount.
	 *
	 * @param value The value in the given unit.
	 * @param unit 	The unit.
	 */
	public Amount(final BigDecimal value, final Convert.Unit unit) {
		if (unit != Convert.Unit.WEI) {
			this.valueInWei = toWei(value, Convert.Unit.WEI).toBigInteger();
		} else {
			this.valueInWei = value.toBigInteger();
		}
		this.valueInUnit = value;
		this.unit = unit;
	}

	/**
	 * Gets the amount value in the specified unit.
	 *
	 * @param unit The unit.
	 * @return The amount value in the specified unit.
	 */
	public BigDecimal getValueIn(final Convert.Unit unit) {
		return fromWei(this.valueInWei.toString(), unit);
	}

	/**
	 * Constructs an amount with the initial given value in the specified unit.
	 *
	 * @param value The decimal value of the amount.
	 * @param unit 	The unit.
	 * @return A new amount instance.
	 */
	public static Amount of(final BigDecimal value, final Convert.Unit unit) {
		return new Amount(value, unit);
	}

	/**
	 * Constructs an amount with the initial given value in the specified unit.
	 *
	 * @param value The integer value of the amount.
	 * @param unit 	The unit.
	 * @return A new amount instance.
	 */
	public static Amount of(final BigInteger value, final Convert.Unit unit) {
		return Amount.of(new BigDecimal(value), unit);
	}

	@Override
	public int compareTo(final Amount amount) {
		return this.getValueInWei().compareTo(amount.getValueInWei());
	}

}
