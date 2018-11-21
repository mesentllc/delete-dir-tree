package com.mesentllc.utilities;

import org.junit.Assert;
import org.junit.Test;

public class Tests {
	@Test
	public void explode_2Hours_0Minutes_0Seconds_0Millis() {
		Assert.assertEquals("[02:00:00.000]", Utilities.explode(7200000));
	}

	@Test
	public void explode_0Hours_7Minutes_0Seconds_0Millis() {
		Assert.assertEquals("[00:07:00.000]", Utilities.explode(420000));
	}

	@Test
	public void explode_0Hours_0Minutes_30Seconds_0Millis() {
		Assert.assertEquals("[00:00:30.000]", Utilities.explode(30000));
	}

	@Test
	public void explode_0Hours_0Minutes_0Seconds_20Millis() {
		Assert.assertEquals("[00:00:00.020]", Utilities.explode(20));
	}

	@Test
	public void explode_2Hours_7Minutes_15Seconds_450Millis() {
		Assert.assertEquals("[02:07:15.450]", Utilities.explode(7635450));
	}
}
