package com.bladyzamosc.rateLimiter.withUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * User: Bladyzamosc
 * Date: 04.10.2022
 */
public class RateLimiterTest
{

  @Test
  public void testRateLimit_SLIDING_LOG() throws InterruptedException
  {
    RateLimiter rateLimiter = new RateLimiter(3, 1000l, Strategy.SLIDING_LOG);
    Assertions.assertEquals(2, rateLimiter.acquire("1"));
    Assertions.assertEquals(1, rateLimiter.acquire("1"));
    Assertions.assertEquals(0, rateLimiter.acquire("1"));
    Assertions.assertThrows(RuntimeException.class, () -> rateLimiter.acquire("1"));
    Assertions.assertEquals(2, rateLimiter.acquire("2"));
    Assertions.assertEquals(1, rateLimiter.acquire("2"));
    Assertions.assertEquals(0, rateLimiter.acquire("2"));
    TimeUnit.SECONDS.sleep(1);
    Assertions.assertEquals(2, rateLimiter.acquire("1"));
  }

  @Test
  public void testRateLimitConcurrent_SLIDING_LOG() throws InterruptedException
  {
    int limit = 100;
    RateLimiter rateLimiter = new RateLimiter(limit, 100000000000l, Strategy.SLIDING_LOG);

    int threadNo = limit - 1;
    ExecutorService executor = Executors.newFixedThreadPool(10);
    CountDownLatch countDownLatch = new CountDownLatch(threadNo * 2);
    for (int i = 0; i < threadNo; i++)
    {
      executor.submit(() -> {
        rateLimiter.acquire("1");
        countDownLatch.countDown();
      });
    }

    for (int i = 0; i < threadNo; i++)
    {
      executor.submit(() -> {
        rateLimiter.acquire("2");
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    executor.shutdown();
    Assertions.assertEquals(0, rateLimiter.acquire("1"));
    Assertions.assertEquals(0, rateLimiter.acquire("2"));
  }

  @Test
  public void testRateLimit_FIXED_WINDOW() throws InterruptedException
  {
    RateLimiter rateLimiter = new RateLimiter(3, 1000l, Strategy.FIXED_WINDOW);
    Assertions.assertEquals(2, rateLimiter.acquire("1"));
    Assertions.assertEquals(1, rateLimiter.acquire("1"));
    Assertions.assertEquals(0, rateLimiter.acquire("1"));
    Assertions.assertThrows(RuntimeException.class, () -> rateLimiter.acquire("1"));
    Assertions.assertEquals(2, rateLimiter.acquire("2"));
    Assertions.assertEquals(1, rateLimiter.acquire("2"));
    Assertions.assertEquals(0, rateLimiter.acquire("2"));
    TimeUnit.SECONDS.sleep(1);
    Assertions.assertEquals(2, rateLimiter.acquire("1"));
  }

  @Test
  public void testRateLimitConcurrent_FIXED_WINDOW() throws InterruptedException
  {
    int limit = 100;
    RateLimiter rateLimiter = new RateLimiter(limit, 100000000000l, Strategy.FIXED_WINDOW);

    int threadNo = limit - 1;
    ExecutorService executor = Executors.newFixedThreadPool(10);
    CountDownLatch countDownLatch = new CountDownLatch(2 * threadNo);
    for (int i = 0; i < threadNo; i++)
    {
      executor.submit(new Thread(() -> {
        rateLimiter.acquire("1");
        countDownLatch.countDown();
      }, "t" + i));
    }

    for (int i = 0; i < threadNo; i++)
    {
      executor.submit(() -> {
        rateLimiter.acquire("2");
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    Assertions.assertEquals(0, rateLimiter.acquire("1"));
    Assertions.assertEquals(0, rateLimiter.acquire("2"));
    TimeUnit.SECONDS.sleep(1);
    executor.shutdown();
  }

  @Test
  public void testRateLimit_TOKEN_BUCKET() throws InterruptedException
  {
    RateLimiter rateLimiter = new RateLimiter(3, 1000l, Strategy.TOKEN_BUCKET);
    rateLimiter.acquire("1");
    rateLimiter.acquire("1");
    rateLimiter.acquire("1");
    rateLimiter.acquire("1");
    Assertions.assertEquals(3, rateLimiter.acquire("1"));
  }
}
