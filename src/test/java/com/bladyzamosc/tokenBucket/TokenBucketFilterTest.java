package com.bladyzamosc.tokenBucket;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Bladyzamosc
 * Date: 30.09.2022
 */
public class TokenBucketFilterTest
{
  @Test
  public void testTokenBucket()
  {
    int noThreads = 10;
    int maxTokens = 1;

    Set<Thread> threads = new HashSet<>(noThreads);
    final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(maxTokens);
    startThreads(noThreads, threads, tokenBucketFilter);
  }

  @Test
  public void testTokenBucket_max5()
  {
    int noThreads = 10;
    int maxTokens = 5;

    Set<Thread> threads = new HashSet<>(noThreads);
    final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(maxTokens);
    startThreads(noThreads, threads, tokenBucketFilter);
  }

  private void startThreads(int noThreads, Set<Thread> threads, TokenBucketFilter tokenBucketFilter)
  {
    try
    {
      Thread.sleep(10000);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    for (int i = 0; i < noThreads; i++)
    {
      Thread thread = new Thread(() -> {
        try
        {
          tokenBucketFilter.token();
        }
        catch (InterruptedException e)
        {
          System.out.println("Something wrong in");
        }
      }, "T-" + i);
      threads.add(thread);
    }

    threads.forEach(a -> a.start());
    threads.forEach(a -> {
      try
      {
        a.join();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    });
  }
}
