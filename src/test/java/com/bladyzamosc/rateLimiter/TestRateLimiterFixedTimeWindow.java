package com.bladyzamosc.rateLimiter;

import com.bladyzamosc.rateLimiter.configuration.RateLimiterConfiguration;
import com.bladyzamosc.rateLimiter.configuration.RateLimiterStrategyType;
import com.bladyzamosc.rateLimiter.exceptions.RateLimiterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * User: Bladyzamosc
 * Date: 28.09.2022
 */
public class TestRateLimiterFixedTimeWindow
{

  @Test
  public void testRateLimiterCreation()
  {
    RateLimiter rateLimiter = RateLimiter.create(RateLimiterConfiguration.RateLimiterConfigurationBuilder.aRateLimiterConfiguration().build());
    Assertions.assertEquals(1000, rateLimiter.getConfiguration().getTimeInMillis());
    Assertions.assertEquals(RateLimiterStrategyType.FIXED_TIME_WINDOW, rateLimiter.getConfiguration().getType());
    Assertions.assertEquals(5, rateLimiter.getConfiguration().getLimit());
  }

  @Test
  public void testRateLimiterAcquire_1()
  {
    int timeInMillis = 100;
    RateLimiter rateLimiter = RateLimiter.create(RateLimiterConfiguration.RateLimiterConfigurationBuilder.aRateLimiterConfiguration()
      .withLimit(1)
      .withTimeInMillis(timeInMillis)
      .build());

    Assertions.assertEquals(0, rateLimiter.acquire(1));
  }

  @Test
  public void testRateLimiterAcquire_1_withWait() throws InterruptedException
  {
    int timeInMillis = 100;
    RateLimiter rateLimiter = RateLimiter.create(RateLimiterConfiguration.RateLimiterConfigurationBuilder.aRateLimiterConfiguration()
      .withLimit(1)
      .withTimeInMillis(timeInMillis)
      .build());
    Assertions.assertEquals(0, rateLimiter.acquire(1));
    TimeUnit.MILLISECONDS.sleep(timeInMillis + 1);
    Assertions.assertEquals(0, rateLimiter.acquire(1));
  }

  @Test
  public void testRateLimiterAcquire_multiThread() throws InterruptedException
  {
    int limit = 1000;
    int diff = 200;
    int noThreads = limit - diff;
    RateLimiter rateLimiter = RateLimiter.create(RateLimiterConfiguration.RateLimiterConfigurationBuilder.aRateLimiterConfiguration()
      .withLimit(limit)
      .withTimeInMillis(100000000)
      .build());

    ExecutorService service = Executors.newFixedThreadPool(200);
    CountDownLatch latch = new CountDownLatch(noThreads);
    for (int i = 0; i < noThreads; i++)
    {
      service.execute(() -> {
        System.out.println(rateLimiter.acquire());
        latch.countDown();
      });
    }
    latch.await();
    Assertions.assertEquals(0, rateLimiter.acquire(diff));
  }

  @Test
  public void testRateLimiterAcquire_exception()
  {
    RateLimiter rateLimiter = RateLimiter.create(RateLimiterConfiguration.RateLimiterConfigurationBuilder.aRateLimiterConfiguration()
      .withLimit(1)
      .withTimeInMillis(10000)
      .build());
    Assertions.assertThrows(RateLimiterException.class, () -> rateLimiter.acquire(2));
  }

  @Test
  public void testRateLimiterAcquire_exceptionMinusAcquire()
  {
    RateLimiter rateLimiter = RateLimiter.create(RateLimiterConfiguration.RateLimiterConfigurationBuilder.aRateLimiterConfiguration()
      .withLimit(1)
      .withTimeInMillis(10000)
      .build());

    Assertions.assertThrows(RateLimiterException.class, () -> rateLimiter.acquire(-2));
  }

}
