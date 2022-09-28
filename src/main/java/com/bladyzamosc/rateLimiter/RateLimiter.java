package com.bladyzamosc.rateLimiter;

import com.bladyzamosc.rateLimiter.configuration.RateLimiterConfiguration;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public class RateLimiter
{
  private RateLimiterConfiguration rateLimiterConfiguration;

  public RateLimiter(RateLimiterConfiguration rateLimiterConfiguration)
  {
    this.rateLimiterConfiguration = rateLimiterConfiguration;
  }

  public static RateLimiter create(RateLimiterConfiguration rateLimiterConfiguration) {
    RateLimiter instance = new RateLimiter(rateLimiterConfiguration);
    return instance;
  }

  public RateLimiterConfiguration getConfiguration()
  {
    return rateLimiterConfiguration;
  }
}
