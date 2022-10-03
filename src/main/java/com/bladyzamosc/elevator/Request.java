package com.bladyzamosc.elevator;

/**
 * User: Bladyzamosc
 * Date: 03.10.2022
 */
public class Request
{
  private int destination;
  private int source;
  private State state = State.IDLE;

  public Request( int source,int destination)
  {
    this.destination = destination;
    this.source = source;
  }

  public void goInside() {
    this.state = State.MOVE;
  }

  public State getState()
  {
    return state;
  }

  public int getDestination()
  {
    return destination;
  }

  public int getSource()
  {
    return source;
  }
}
