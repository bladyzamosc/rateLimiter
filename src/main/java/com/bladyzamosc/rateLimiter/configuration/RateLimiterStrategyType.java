package com.bladyzamosc.rateLimiter.configuration;

import com.bladyzamosc.rateLimiter.strategies.RateLimiterStrategy;
import com.bladyzamosc.rateLimiter.strategies.impl.FixedTimeWindowStrategy;
import com.bladyzamosc.rateLimiter.strategies.impl.RollingWindowStrategy;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public enum RateLimiterStrategyType
{
  FIXED_TIME_WINDOW
    {
      @Override
      public RateLimiterStrategy create(RateLimiterConfiguration rateLimiterConfiguration)
      {
        return new FixedTimeWindowStrategy(rateLimiterConfiguration);
      }
    },
  ROLLING_WINDOW
    {
      @Override
      public RateLimiterStrategy create(RateLimiterConfiguration rateLimiterConfiguration)
      {
        return new RollingWindowStrategy(rateLimiterConfiguration);
      }
    };

  public abstract RateLimiterStrategy create(RateLimiterConfiguration rateLimiterConfiguration);
}
