package com.bladyzamosc.rateLimiter.withUser;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * User: Bladyzamosc
 * Date: 04.10.2022
 */
public class RateLimiterUserSlidingLog extends RateLimiterUser
{
  private final ConcurrentLinkedDeque<Long> queue;

  public RateLimiterUserSlidingLog(int limit, long timeLimit)
  {
    super(limit, timeLimit);
    this.queue = new ConcurrentLinkedDeque<>();
  }

  public int acquire()
  {
    removeOlderElements();
    if (queue.size() >= limit)
    {
      throw new RuntimeException("Limit reached");
    }
    queue.add(System.currentTimeMillis());
    return limit - queue.size();
  }

  private void removeOlderElements()
  {
    long now = System.currentTimeMillis();
    while (!queue.isEmpty() && now - queue.peek() > timeLimit)
    {
      queue.remove();
    }
  }
}
