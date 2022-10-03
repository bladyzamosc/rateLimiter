package com.bladyzamosc.elevator;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * User: Bladyzamosc
 * Date: 03.10.2022
 */
public class Elevator implements Runnable
{

  private int currentFloor = 0;
  private Request destinationRequest = null;

  private Deque<Request> requests = new LinkedList<>();

  public void request(int from, int to)
  {
    if (from == to)
    {
      return;
    }

    System.out.println("Request from " + from + "  to " + to);
    Request request = new Request(from, to);
    requests.add(request);
  }

  private void waitPeriod()
  {
    try
    {
      TimeUnit.MILLISECONDS.sleep(1000l);
    }
    catch (Exception e)
    {

    }
  }


  @Override
  public void run()
  {

    while (true)
    {
      System.out.println("------------ " + this.requests.size());
      determineNextDestination();
      releaseAllOnThisFloor();
      waitPeriod();
      changeFloor();
    }
  }

  private void releaseAllOnThisFloor()
  {
    Iterator<Request> iterator = requests.iterator();
    while (iterator.hasNext())
    {
      Request request = iterator.next();
      if (request.getDestination() == currentFloor && request.getState().equals(State.MOVE))
      {
        System.out.println("Pasanger unpacked from " + request.getSource() + " on floor " + this.currentFloor);
        iterator.remove();
      }

      if (request.getSource() == currentFloor && request.getState().equals(State.IDLE))
      {
        request.goInside();
        System.out.println("Pasanger goes inside request "
          + request.getSource()
          + " to "
          + request.getDestination());
      }
    }
  }

  private void changeFloor()
  {
    if (this.destinationRequest != null)
    {
      if (destinationRequest.getState().equals(State.MOVE))
      {
        if (currentFloor > destinationRequest.getDestination())
        {
          currentFloor--;
          System.out.println((currentFloor + 1) + " - " + currentFloor);
        }
        else
        {
          currentFloor++;
          System.out.println((currentFloor - 1) + " - " + currentFloor);
        }
      }
      else
      {
        if (currentFloor > destinationRequest.getSource())
        {
          currentFloor--;
          System.out.println((currentFloor + 1) + " - " + currentFloor);
        }
        else
        {
          currentFloor++;
          System.out.println((currentFloor - 1) + " - " + currentFloor);
        }
      }
    }
    else
    {
      System.out.println("Elevator still in the same position = " + currentFloor);
    }
  }

  private void determineNextDestination()
  {
    if (this.destinationRequest == null && !this.requests.isEmpty())
    {
      this.destinationRequest = this.requests.getFirst();
      System.out.println("A new destination from: "
        + this.destinationRequest.getSource()
        + " to "
        + this.destinationRequest.getDestination());
    }

    if (this.destinationRequest != null
      && this.destinationRequest.getDestination() == this.currentFloor
      && this.destinationRequest.getState().equals(State.MOVE)
    )
    {
      System.out.println("End of journey of request from "
        + this.destinationRequest.getSource()
        + " to "
        + this.destinationRequest.getDestination());
      this.destinationRequest = null;
    }

    if (this.destinationRequest != null
      && this.destinationRequest.getSource() == this.currentFloor
      && this.destinationRequest.getState().equals(State.IDLE))
    {
      this.destinationRequest.goInside();
      System.out.println("Go Inside request "
        + this.destinationRequest.getSource()
        + " to "
        + this.destinationRequest.getDestination());
    }
  }
}
