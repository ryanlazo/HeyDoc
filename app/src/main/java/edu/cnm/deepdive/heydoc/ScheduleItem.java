package edu.cnm.deepdive.heydoc;

public class ScheduleItem {

  private String time;
  private boolean isBooked;

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public boolean isBooked() {
    return isBooked;
  }

  public void setBooked(boolean booked) {
    isBooked = booked;
  }
}
