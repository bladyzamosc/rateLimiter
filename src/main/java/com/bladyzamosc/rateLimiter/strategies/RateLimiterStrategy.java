package com.bladyzamosc.rateLimiter.strategies;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public interface RateLimiterStrategy
{
  long acquire(int length);
}
