/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka.asserts;

import com.github.maximevw.moka.entities.TestingAccount;
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
	 * Asserts that the balance of the given account has decreased in a given proportion since the last account
	 * checkpoint.
	 *
	 * @param account The tested account.
	 * @param matcher The comparison matcher.
	 * @see TestingAccount#checkpoint()
	 */
	public static void assertBalanceDecreased(final TestingAccount account,
											  final ComparisonMatcher<BigInteger> matcher) {
		final BigInteger currentBalance = account.getBalanceInWei();
		final BigInteger lastBalance = account.getLastBalance();
		if (!matcher.comparesTo(lastBalance.subtract(currentBalance))) {
			assertionFailure().message("The current balance has not decreased since the last checkpoint.")
				.expected("Less than " + lastBalance + WEI.name())
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
	 * Asserts that the balance of the given account has increased in a given proportion since the last account
	 * checkpoint.
	 *
	 * @param account The tested account.
	 * @param matcher The comparison matcher.
	 * @see TestingAccount#checkpoint()
	 */
	public static void assertBalanceIncreased(final TestingAccount account,
											  final ComparisonMatcher<BigInteger> matcher) {
		final BigInteger currentBalance = account.getBalanceInWei();
		final BigInteger lastBalance = account.getLastBalance();
		if (!matcher.comparesTo(currentBalance.subtract(account.getLastBalance()))) {
			assertionFailure().message("The current balance has not increased since the last checkpoint.")
				.expected("More than " + lastBalance + WEI.name())
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
