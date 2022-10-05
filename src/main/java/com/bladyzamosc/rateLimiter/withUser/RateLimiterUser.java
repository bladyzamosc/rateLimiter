package com.bladyzamosc.rateLimiter.withUser;

/**
 * User: Bladyzamosc
 * Date: 04.10.2022
 */
public abstract class RateLimiterUser
{
  protected final int limit;
  protected final long timeLimit;

  protected RateLimiterUser(int limit, long timeLimit)
  {
    this.limit = limit;
    this.timeLimit = timeLimit;
  }

  public abstract int acquire();
}
