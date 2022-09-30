package com.bladyzamosc.tokenBucket;

/**
 * User: Bladyzamosc
 * Date: 30.09.2022
 */
public class MultithreadedTokenBucketFilter
{
  private long possibleTokens = 0;
  private final int MAX_TOKENS;
  private final int ONE_SECOND = 1000;

  MultithreadedTokenBucketFilter(int maxTokens)
  {
    MAX_TOKENS = maxTokens;
  }

  void initialize()
  {
    Thread dt = new Thread(() -> daemonThread());
    dt.setDaemon(true);
    dt.start();
  }

  private void daemonThread()
  {
    while (true)
    {
      synchronized (this)
      {
        if (possibleTokens < MAX_TOKENS)
        {
          possibleTokens++;
        }
        this.notify();
      }
      try
      {
        Thread.sleep(ONE_SECOND);
      }
      catch (InterruptedException ie)
      {
      }
    }
  }

  public void getToken() throws InterruptedException
  {
    synchronized (this)
    {
      while (possibleTokens == 0)
      {
        this.wait();
      }
      possibleTokens--;
    }
    System.out.println(
      "Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis() / 1000);
  }
}
