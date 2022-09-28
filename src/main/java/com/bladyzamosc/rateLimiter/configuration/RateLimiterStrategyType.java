package com.bladyzamosc.rateLimiter.configuration;

import com.bladyzamosc.rateLimiter.strategies.RateLimiterStrategy;
import com.bladyzamosc.rateLimiter.strategies.impl.FixedTimeWindowStrategy;
import com.bladyzamosc.rateLimiter.strategies.impl.RateLimiterException;

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
        throw new RateLimiterException("Not implemented yet");
      }
    };


  public abstract RateLimiterStrategy create(RateLimiterConfiguration rateLimiterConfiguration);
}
