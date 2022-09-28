package com.bladyzamosc.rateLimiter;

import com.bladyzamosc.rateLimiter.configuration.RateLimiterConfiguration;
import com.bladyzamosc.rateLimiter.strategies.RateLimiterStrategy;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public class RateLimiter
{
  private RateLimiterConfiguration rateLimiterConfiguration;
  private RateLimiterStrategy strategy;

  public RateLimiter(RateLimiterConfiguration rateLimiterConfiguration)
  {
    this.rateLimiterConfiguration = rateLimiterConfiguration;
    this.strategy = rateLimiterConfiguration.getType().create(rateLimiterConfiguration);
  }

  public static RateLimiter create(RateLimiterConfiguration rateLimiterConfiguration)
  {
    RateLimiter instance = new RateLimiter(rateLimiterConfiguration);
    return instance;
  }

  public RateLimiterConfiguration getConfiguration()
  {
    return rateLimiterConfiguration;
  }

  public long acquire()
  {
    return strategy.acquire(1);
  }

  public long acquire(int i)
  {
    return strategy.acquire(i);
  }
}
