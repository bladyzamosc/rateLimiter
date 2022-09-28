package com.bladyzamosc.rateLimiter.strategies.impl;

import com.bladyzamosc.rateLimiter.configuration.RateLimiterConfiguration;
import com.bladyzamosc.rateLimiter.strategies.RateLimiterStrategy;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public abstract class BaseStrategy implements RateLimiterStrategy
{
  protected RateLimiterConfiguration rateLimiterConfiguration;
  protected volatile Object mutex;

  protected BaseStrategy(RateLimiterConfiguration rateLimiterConfiguration)
  {
    this.mutex = new Object();
    this.rateLimiterConfiguration = rateLimiterConfiguration;
  }

  @Override
  public long acquire(int length)
  {
    if (length < 1)
    {
      throw new RateLimiterException("Length should be higher than 0");
    }

    synchronized (mutex)
    {
      return reserve(length);
    }
  }

  protected abstract long reserve(int length);
}
