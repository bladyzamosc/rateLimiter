package com.bladyzamosc.rateLimiter.strategies.impl;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public class RateLimiterException extends RuntimeException
{
  private final String message;

  public RateLimiterException(String message)
  {
    this.message = message;
  }
}
