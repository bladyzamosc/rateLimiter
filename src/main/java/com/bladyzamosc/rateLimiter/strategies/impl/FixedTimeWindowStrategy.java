package com.bladyzamosc.rateLimiter.strategies.impl;

import com.bladyzamosc.rateLimiter.configuration.RateLimiterConfiguration;
import com.bladyzamosc.rateLimiter.exceptions.RateLimiterException;

/**
 * User: Z6EKI
 * Date: 28.09.2022
 */
public class FixedTimeWindowStrategy extends BaseStrategy
{
  long startTime;
  long count;

  public FixedTimeWindowStrategy(RateLimiterConfiguration rateLimiterConfiguration)
  {
    super(rateLimiterConfiguration);
    this.count = 0;
    this.startTime = Long.MIN_VALUE;
  }

  @Override
  protected long reserve(int length)
  {
    boolean reset = System.currentTimeMillis() - rateLimiterConfiguration.getTimeInMillis() > startTime;
    if (reset)
    {
      startTime = System.currentTimeMillis();
      count = 0;
    }
    if (count + length > rateLimiterConfiguration.getLimit())
    {
      throw new RateLimiterException("Exceeded maximum limit of " + rateLimiterConfiguration.getLimit());
    }
    count += length;
    return rateLimiterConfiguration.getLimit() - count;
  }

}
