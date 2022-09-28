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
      RateLimiterStrategy create()
      {
        return new FixedTimeWindowStrategy();
      }
    },
  ROLLING_WINDOW
    {
      @Override
      RateLimiterStrategy create()
      {
        throw new RateLimiterException("Not implemented yet");
      }
    },
  SLIDING_WINDOW
    {
      @Override
      RateLimiterStrategy create()
      {
        throw new RateLimiterException("Not implemented yet");
      }
    },
  SLIDING_WINDOW_WITH_COUNTERS
    {
      @Override
      RateLimiterStrategy create()
      {
        throw new RateLimiterException("Not implemented yet");
      }
    };


  abstract RateLimiterStrategy create();
}
