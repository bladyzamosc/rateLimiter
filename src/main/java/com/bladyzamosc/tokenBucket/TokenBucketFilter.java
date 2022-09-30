package com.bladyzamosc.tokenBucket;

import java.util.concurrent.TimeUnit;

/**
 * User: Bladyzamosc
 * Date: 30.09.2022
 */
public class TokenBucketFilter
{
  protected static final int ONE_SECOND = 1000;
  private int maxTokens;
  private long possibleTokens;
  private long lastRequestTime = System.currentTimeMillis();

  TokenBucketFilter(int maxTokens)
  {
    this.maxTokens = maxTokens;

  }

  public synchronized void token() throws InterruptedException
  {
    possibleTokens += (System.currentTimeMillis() - lastRequestTime) / ONE_SECOND;
    if (possibleTokens > maxTokens)
    {
      possibleTokens = maxTokens;
    }
    if (possibleTokens == 0)
    {
      TimeUnit.MILLISECONDS.sleep(ONE_SECOND);
    }
    else
    {
      possibleTokens--;
    }
    lastRequestTime = System.currentTimeMillis();
    System.out.println("Granting " + Thread.currentThread().getName() + " token at " + (System.currentTimeMillis() / ONE_SECOND));
  }
}
