package com.bladyzamosc.rateLimiter;

import com.bladyzamosc.rateLimiter.configuration.RateLimiterConfiguration;
import com.bladyzamosc.rateLimiter.configuration.RateLimiterStrategyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public class TestRateLimiter
{

  @Test
  public void testRateLimiterCreation()
  {
    RateLimiter rateLimiter = RateLimiter.create(RateLimiterConfiguration.RateLimiterConfigurationBuilder.aRateLimiterConfiguration().build());
    Assertions.assertEquals(1000, rateLimiter.getConfiguration().getTimeInMillis());
    Assertions.assertEquals(RateLimiterStrategyType.FIXED_TIME_WINDOW, rateLimiter.getConfiguration().getType());
    Assertions.assertEquals(5, rateLimiter.getConfiguration().getLimit());
  }

}
