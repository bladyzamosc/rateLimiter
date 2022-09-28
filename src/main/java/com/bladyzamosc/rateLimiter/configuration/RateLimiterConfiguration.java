package com.bladyzamosc.rateLimiter.configuration;

import java.util.concurrent.TimeUnit;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public class RateLimiterConfiguration
{
  RateLimiterStrategyType type;
  int limit;
  long timeInMillis;

  public RateLimiterStrategyType getType()
  {
    return type;
  }

  public int getLimit()
  {
    return limit;
  }

  public long getTimeInMillis()
  {
    return timeInMillis;
  }

  public static final class RateLimiterConfigurationBuilder
  {
    private RateLimiterStrategyType type = RateLimiterStrategyType.FIXED_TIME_WINDOW;
    private int limit = 5;
    private long timeInMillis = TimeUnit.SECONDS.toMillis(1);

    private RateLimiterConfigurationBuilder()
    {
    }

    public static RateLimiterConfigurationBuilder aRateLimiterConfiguration()
    {
      return new RateLimiterConfigurationBuilder();
    }

    public RateLimiterConfigurationBuilder withType(RateLimiterStrategyType type)
    {
      this.type = type;
      return this;
    }

    public RateLimiterConfigurationBuilder withLimit(int limit)
    {
      this.limit = limit;
      return this;
    }

    public RateLimiterConfigurationBuilder withTimeInMillis(long timeInMillis)
    {
      this.timeInMillis = timeInMillis;
      return this;
    }

    public RateLimiterConfiguration build()
    {
      RateLimiterConfiguration rateLimiterConfiguration = new RateLimiterConfiguration();
      rateLimiterConfiguration.type = this.type;
      rateLimiterConfiguration.limit = this.limit;
      rateLimiterConfiguration.timeInMillis = this.timeInMillis;
      return rateLimiterConfiguration;
    }
  }
}
