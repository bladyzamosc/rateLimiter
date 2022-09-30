package com.bladyzamosc.tokenBucket;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Bladyzamosc
 * Date: 30.09.2022
 */
public class MultithreadedTokenBucketFilterTest
{

  @Test
  public void testMulti() throws InterruptedException
  {
    Set<Thread> allThreads = new HashSet<>();
    MultithreadedTokenBucketFilter tokenBucketFilter = new MultithreadedTokenBucketFilter(5);
    tokenBucketFilter.initialize();

    for (int i = 0; i < 10; i++)
    {
      allThreads.add(new Thread(() -> {
        try
        {
          tokenBucketFilter.getToken();
        }
        catch (InterruptedException ie)
        {
          System.out.println("We have a problem");
        }
      }, "T" + i));
    }
    allThreads.forEach(Thread::start);

    for (Thread t : allThreads)
      t.join();
  }
}
