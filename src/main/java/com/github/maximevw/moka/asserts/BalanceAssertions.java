/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.asserts;

import com.github.maximevw.moka.entities.TestingAccount;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;

import java.math.BigInteger;

import static org.junit.jupiter.api.AssertionFailureBuilder.assertionFailure;
import static org.web3j.utils.Convert.Unit.WEI;

/**
 * Assertions for account balances.
 */
public final class BalanceAssertions {

	private BalanceAssertions() {
		// Private constructor hiding the public default one.
	}

	/**
	 * Asserts that the given account has the expected balance.
	 *
	 * @param account			The tested account.
	 * @param expectedBalance	The expected balance in WEI.
	 */
	public static void assertBalanceEquals(final TestingAccount account, final BigInteger expectedBalance) {
		Assertions.assertEquals(expectedBalance, account.getBalanceInWei());
	}

	/**
	 * Asserts that the balance of the given account has decreased since the last account checkpoint.
	 *
	 * @param account The tested account.
	 * @see TestingAccount#checkpoint()
	 */
	public static void assertBalanceDecreased(final TestingAccount account) {
		final BigInteger currentBalance = account.getBalanceInWei();
		final BigInteger lastBalance = account.getLastBalance();
		if (!(currentBalance.compareTo(lastBalance) < 0)) {
			assertionFailure().message("The current balance has not decreased since the last checkpoint.")
				.expected("Less than " + lastBalance + WEI.name())
				.actual(currentBalance + WEI.name())
				.buildAndThrow();
		}
	}

	/**
	 * Asserts that the balance of the given account has decreased in a given proportion (in WEI) since the last account
	 * checkpoint.
	 *
	 * @param account The tested account.
	 * @param matcher The comparison matcher using a value in WEI.
	 * @see TestingAccount#checkpoint()
	 */
	public static void assertBalanceDecreased(final TestingAccount account,
											  final VariationMatcher<BigInteger> matcher) {
		final BigInteger currentBalance = account.getBalanceInWei();
		final BigInteger lastBalance = account.getLastBalance();
		final BigInteger variation = lastBalance.subtract(currentBalance);
		if (variation.signum() < 0 || !matcher.comparesTo(variation)) {
			String expectedComplement = StringUtils.EMPTY;
			if (matcher.maximalVariation().isPresent()) {
				expectedComplement = " and more than " + lastBalance.subtract(matcher.maximalVariation().get())
					+ WEI.name();
			}
			assertionFailure().message("The current balance has not decreased of " + matcher.describeMismatch()
					+ "WEI since the last checkpoint.")
				.expected("Less than " + lastBalance.subtract(matcher.minimalVariation().orElse(BigInteger.ZERO))
					+ WEI.name() + expectedComplement)
				.actual(currentBalance + WEI.name())
				.buildAndThrow();
		}
	}

	/**
	 * Asserts that the balance of the given account has increased since the last account checkpoint.
	 *
	 * @param account The tested account.
	 * @see TestingAccount#checkpoint()
	 */
	public static void assertBalanceIncreased(final TestingAccount account) {
		final BigInteger currentBalance = account.getBalanceInWei();
		final BigInteger lastBalance = account.getLastBalance();
		if (!(currentBalance.compareTo(lastBalance) > 0)) {
			assertionFailure().message("The current balance has not increased since the last checkpoint.")
				.expected("More than " + lastBalance + WEI.name())
				.actual(currentBalance + WEI.name())
				.buildAndThrow();
		}
	}

	/**
	 * Asserts that the balance of the given account has increased in a given proportion (in WEI) since the last account
	 * checkpoint.
	 *
	 * @param account The tested account.
	 * @param matcher The comparison matcher using a value in WEI.
	 * @see TestingAccount#checkpoint()
	 */
	public static void assertBalanceIncreased(final TestingAccount account,
											  final VariationMatcher<BigInteger> matcher) {
		final BigInteger currentBalance = account.getBalanceInWei();
		final BigInteger lastBalance = account.getLastBalance();
		final BigInteger variation = currentBalance.subtract(lastBalance);
		if (variation.signum() < 0 || !matcher.comparesTo(variation)) {
			String expectedComplement = StringUtils.EMPTY;
			if (matcher.maximalVariation().isPresent()) {
				expectedComplement = " and less than " + lastBalance.add(matcher.maximalVariation().get()) + WEI.name();
			}
			assertionFailure().message("The current balance has not increased of " + matcher.describeMismatch()
					+ "WEI since the last checkpoint.")
				.expected("More than " + lastBalance.add(matcher.minimalVariation().orElse(BigInteger.ZERO))
					+ WEI.name() + expectedComplement)
				.actual(currentBalance + WEI.name())
				.buildAndThrow();
		}
	}

	/**
	 * Asserts that the balance of the given account is stable since the last account checkpoint.
	 *
	 * @param account The tested account.
	 * @see TestingAccount#checkpoint()
	 */
	public static void assertBalanceStable(final TestingAccount account) {
		final BigInteger currentBalance = account.getBalanceInWei();
		final BigInteger lastBalance = account.getLastBalance();
		if (!(currentBalance.compareTo(lastBalance) == 0)) {
			assertionFailure().message("The current balance has changed since the last checkpoint.")
				.expected(lastBalance + WEI.name())
				.actual(currentBalance + WEI.name())
				.buildAndThrow();
		}
	}

}
