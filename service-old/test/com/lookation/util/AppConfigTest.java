package com.lookation.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AppConfigTest
{
	@Test
	public void missingRequiredSettingFailsFast()
	{
		System.clearProperty("LOOKATION_TEST_MISSING");
		try
		{
			AppConfig.require("LOOKATION_TEST_MISSING");
			fail("누락된 설정은 예외여야 한다");
		}
		catch (IllegalStateException expected)
		{
			assertThat(expected.getMessage().contains("LOOKATION_TEST_MISSING"), is(true));
		}
	}

	@Test
	public void systemPropertyOverridesEnvironment()
	{
		System.setProperty("LOOKATION_TEST_VALUE", "from-property");
		try
		{
			assertThat(AppConfig.get("LOOKATION_TEST_VALUE"), is("from-property"));
		}
		finally
		{
			System.clearProperty("LOOKATION_TEST_VALUE");
		}
	}
}
