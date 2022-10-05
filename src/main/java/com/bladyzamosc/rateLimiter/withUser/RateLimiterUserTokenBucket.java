package com.bladyzamosc.rateLimiter.withUser;

import java.util.concurrent.TimeUnit;

/**
 * User: Bladyzamosc
 * Date: 04.10.2022
 */
public class RateLimiterUserTokenBucket extends RateLimiterUser
{
  private int count;

  protected RateLimiterUserTokenBucket(int limit, long timeLimit)
  {
    super(limit, timeLimit);
    this.count = limit;
    initialize();
  }

  private void initialize()
  {
    Thread t = new Thread(() -> daemon());
    t.setDaemon(true);
    t.start();
  }

  private void daemon()
  {
    while (true)
    {
      synchronized (this)
      {
        if (count < limit)
        {
          count++;
        }
        notify();
      }
      try
      {
        TimeUnit.MILLISECONDS.sleep(timeLimit / limit);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }

  @Override
  public int acquire()
  {
    int result;
    synchronized (this)
    {
      while (count == 0)
      {
        try
        {
          wait();
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
      count--;
      result = limit - count;
    }
    return result;
  }
}
