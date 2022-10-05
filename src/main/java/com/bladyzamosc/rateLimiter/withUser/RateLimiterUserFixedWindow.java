package com.bladyzamosc.rateLimiter.withUser;

/**
 * User: Bladyzamosc
 * Date: 04.10.2022
 */
public class RateLimiterUserFixedWindow extends RateLimiterUser
{
  long time;
  int count;
  protected volatile Object mutex;

  protected RateLimiterUserFixedWindow(int limit, long timeLimit)
  {
    super(limit, timeLimit);
    this.time = System.currentTimeMillis() - (1+timeLimit);
    this.count = 0;
    this.mutex = new Object();
  }

  @Override
  public int acquire()
  {
    int result;
    synchronized (mutex)
    {
      if (System.currentTimeMillis() - time > timeLimit)
      {
        time = System.currentTimeMillis();
        count = 0;
      }

      if (count >= limit)
      {
        throw new RuntimeException("Limit reached");
      }

      count++;
      result = limit - count;
    }
    return result;
  }
}
