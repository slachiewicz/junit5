/*
 * Copyright 2015-2019 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.jupiter.api.condition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for {@link EnabledIfEnvironmentVariable}.
 *
 * @since 5.1
 */
@Disabled("Disabled since the required environment variable is not set")
// Tests will pass if you set the following environment variables:
//EnabledIfEnvironmentVariableTests.key1 = EnabledIfEnvironmentVariableTests.enigma
//EnabledIfEnvironmentVariableTests.key2 = EnabledIfEnvironmentVariableTests.enigma
class EnabledIfEnvironmentVariableIntegrationTests {

	static final String KEY1 = "EnabledIfEnvironmentVariableTests.key1";
	static final String KEY2 = "EnabledIfEnvironmentVariableTests.key2";
	static final String ENIGMA = "EnabledIfEnvironmentVariableTests.enigma";
	static final String BOGUS = "EnabledIfEnvironmentVariableTests.bogus";

	@Test
	@Disabled("Only used in a unit test via reflection")
	void enabledBecauseAnnotationIsNotPresent() {
	}

	@Test
	@Disabled("Only used in a unit test via reflection")
	@EnabledIfEnvironmentVariable(named = "  ", matches = ENIGMA)
	void blankNamedAttribute() {
	}

	@Test
	@Disabled("Only used in a unit test via reflection")
	@EnabledIfEnvironmentVariable(named = KEY1, matches = "  ")
	void blankMatchesAttribute() {
	}

	@Test
	@EnabledIfEnvironmentVariable(named = KEY1, matches = ENIGMA)
	void enabledBecauseEnvironmentVariableMatchesExactly() {
		assertEquals(ENIGMA, System.getenv(KEY1));
	}

	@Test
	@EnabledIfEnvironmentVariable(named = KEY1, matches = ENIGMA)
	@EnabledIfEnvironmentVariable(named = KEY2, matches = ENIGMA)
	void enabledBecauseBothEnvironmentVariablesMatchExactly() {
		assertEquals(ENIGMA, System.getProperty(KEY1));
		assertEquals(ENIGMA, System.getProperty(KEY2));
	}

	@Test
	@EnabledIfEnvironmentVariable(named = KEY1, matches = ".+enigma$")
	void enabledBecauseEnvironmentVariableMatchesPattern() {
		assertEquals(ENIGMA, System.getenv(KEY1));
	}

	@Test
	@EnabledIfEnvironmentVariable(named = KEY1, matches = BOGUS)
	void disabledBecauseEnvironmentVariableDoesNotMatch() {
		fail("should be disabled");
	}

	@Test
	@EnabledIfEnvironmentVariable(named = BOGUS, matches = "doesn't matter")
	void disabledBecauseEnvironmentVariableDoesNotExist() {
		fail("should be disabled");
	}

}
