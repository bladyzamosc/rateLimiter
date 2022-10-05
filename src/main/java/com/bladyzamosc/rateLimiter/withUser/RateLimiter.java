package com.bladyzamosc.rateLimiter.withUser;

import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Bladyzamosc
 * Date: 04.10.2022
 */
public class RateLimiter
{
  private final int limit;
  private final long time;
  private final Strategy strategy;
  private ConcurrentHashMap<String, RateLimiterUser> map;

  public RateLimiter(int limit, long time, Strategy strategy)
  {
    this.limit = limit;
    this.time = time;
    this.strategy = strategy;
    this.map = new ConcurrentHashMap<>();
  }

  public int acquire(String user)
  {
    RateLimiterUser rateLimiterUser = resolveRateLimiterUser(user);
    return rateLimiterUser.acquire();
  }

  private RateLimiterUser resolveRateLimiterUser(String user)
  {
    return map.computeIfAbsent(user, (x) -> generateRateLimiter());
  }

  private RateLimiterUser generateRateLimiter()
  {
    switch (strategy)
    {
      case SLIDING_LOG -> {
        return new RateLimiterUserSlidingLog(limit, time);
      }
      case FIXED_WINDOW -> {
        return new RateLimiterUserFixedWindow(limit, time);
      }
      case TOKEN_BUCKET -> {
        return new RateLimiterUserTokenBucket(limit, time);
      }
    }
    throw new RuntimeException("No strategy");
  }
}
