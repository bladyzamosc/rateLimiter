package com.bladyzamosc.elevator;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * User: Z6EKI
 * Date: 03.10.2022
 */
public class ElevatorTest
{
  @Test
  public void testElevator() throws InterruptedException
  {
    Elevator elevator = new Elevator();
    new Thread(elevator).start();
    elevator.request(5, 3);
    elevator.request(5, 1);
    elevator.request(1, 6);
    TimeUnit.SECONDS.sleep(2);
    elevator.request(1, 5);
    TimeUnit.SECONDS.sleep(2);
    elevator.request(10, 2);
    TimeUnit.SECONDS.sleep(2);
    elevator.request(10, 2);
    TimeUnit.SECONDS.sleep(2);
    elevator.request(9, 2);
    TimeUnit.SECONDS.sleep(2);
    elevator.request(4, 6);
    TimeUnit.SECONDS.sleep(200);
  }


}
