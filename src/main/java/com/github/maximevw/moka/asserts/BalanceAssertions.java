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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Assertions for account balances.
 */
public final class BalanceAssertions {

	private BalanceAssertions() {
		// Private constructor hiding the public default one.
	}

    public static void assertBalanceEquals(final TestingAccount account, final BigInteger expectedBalance) {
        Assertions.assertEquals(expectedBalance, account.getBalance().setScale(0, RoundingMode.HALF_UP).toBigInteger());
    }

	public static void assertBalanceDecreased(final TestingAccount account) {
		final BigDecimal currentBalance = account.getBalance();
		Assertions.assertTrue(currentBalance.compareTo(account.getLastBalance()) < 0);
	}

	public static void assertBalanceIncreased(final TestingAccount account) {
		final BigDecimal currentBalance = account.getBalance();
		Assertions.assertTrue(currentBalance.compareTo(account.getLastBalance()) > 0);
	}

	public static void assertBalanceStable(final TestingAccount account) {
		final BigDecimal currentBalance = account.getBalance();
		Assertions.assertEquals(0, currentBalance.compareTo(account.getLastBalance()));
	}
}
