package com.bladyzamosc.rateLimiter.strategies.impl;

import com.bladyzamosc.rateLimiter.configuration.RateLimiterConfiguration;
import com.bladyzamosc.rateLimiter.exceptions.RateLimiterException;

import java.util.Deque;
import java.util.LinkedList;

/**
 * User: Z6EKI
 * Date: 29.09.2022
 */
public class RollingWindowStrategy extends BaseStrategy
{
  Deque<Long> queue;

  public RollingWindowStrategy(RateLimiterConfiguration rateLimiterConfiguration)
  {
    super(rateLimiterConfiguration);
    queue = new LinkedList<>();
  }

  @Override
  protected long reserve(int length)
  {
    ensureQueue();
    long available = rateLimiterConfiguration.getLimit() - queue.size();

    if (available < length)
    {
      throw new RateLimiterException("Exceeded maximum limit of " + rateLimiterConfiguration.getLimit());
    }

    for (int i = 0; i < length; i++)
    {
      queue.offerFirst(System.currentTimeMillis());
    }

    return rateLimiterConfiguration.getLimit() - queue.size();
  }

  private void ensureQueue()
  {
    Long expiredTime = System.currentTimeMillis() - rateLimiterConfiguration.getTimeInMillis();
    while (queue.size() > 0 && queue.getLast() < expiredTime)
    {
      queue.removeLast();
    }
  }
}
