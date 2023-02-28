/*
 *  Copyright (c) 2023 Maxime Wiewiora
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 */

package com.github.maximevw.moka;

import com.github.maximevw.moka.entities.TestingAccount;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.OutputFrame;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Log consumer extracting the data generated by Ganache at the startup.
 */
@Slf4j(topic = "GanacheContainer")
public class GanacheLogConsumer implements Consumer<OutputFrame> {

	private static final Pattern ACCOUNT_ADDRESS = Pattern.compile("^\\((\\d+)\\) (0x[0-9a-zA-Z]{40}) \\(\\d+ ETH\\)$");
	private static final Pattern ACCOUNT_PRIVATE_KEY = Pattern.compile("^\\((\\d+)\\) (0x[0-9a-zA-Z]*)$");
	private static final Pattern GANACHE_VERSION = Pattern.compile("^ganache( CLI)? v(\\d+\\.\\d+\\.?\\d*) \\(.*\\)$",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern RPC_LISTENING = Pattern.compile("^RPC Listening on .*:\\d+$");

	private final GanacheContainer<?> ganacheContainer;

	/**
	 * Constructor.
	 *
	 * @param ganacheContainer The Ganache container.
	 */
	public GanacheLogConsumer(final GanacheContainer<?> ganacheContainer) {
		this.ganacheContainer = ganacheContainer;
	}

	@Override
	public void accept(final OutputFrame outputFrame) {
		final String outputMessage = StringUtils.defaultString(outputFrame.getUtf8String(), StringUtils.EMPTY).trim();

		final Matcher ganacheVersionMatcher = GANACHE_VERSION.matcher(outputMessage);
		if (ganacheVersionMatcher.matches()) {
			final String version = ganacheVersionMatcher.group(2);
			log.info("Running Ganache v{}", version);
		}

		final Matcher accountMatcher = ACCOUNT_ADDRESS.matcher(outputMessage);
		if (accountMatcher.matches()) {
			final int index = Integer.parseInt(accountMatcher.group(1));
			final String accountAddress = accountMatcher.group(2);
			this.ganacheContainer.mapGanacheAccount(index, accountAddress);
		}

		final Matcher privateKeyMatcher = ACCOUNT_PRIVATE_KEY.matcher(outputMessage);
		if (privateKeyMatcher.matches()) {
			final int index = Integer.parseInt(privateKeyMatcher.group(1));
			final String privateKey = privateKeyMatcher.group(2);
			this.ganacheContainer.mapGanachePrivateKey(index, privateKey);
		}

		// As soon as Ganache is ready to listen to RPC calls, create an initial checkpoint for each generated account.
		final Matcher rpcListeningMatcher = RPC_LISTENING.matcher(outputMessage);
		if (rpcListeningMatcher.matches()) {
			this.ganacheContainer.getTestingAccounts().values().forEach(TestingAccount::checkpoint);
		}

		final OutputFrame.OutputType outputType = outputFrame.getType();
		final Logger loggerChain = LoggerFactory.getLogger("🔗 [Ganache]");
		switch (outputType) {
			case END:
				break;
			case STDOUT:
				if (StringUtils.isNotBlank(outputMessage)) {
					loggerChain.debug(outputMessage);
				}
				break;
			case STDERR:
				loggerChain.error(outputMessage);
				break;
			default:
				throw new IllegalArgumentException("Unexpected output type: " + outputType);
		}
	}
}
