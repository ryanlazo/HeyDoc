package edu.cnm.deepdive.heydoc;

import android.content.Context;
import android.text.format.DateFormat;
import java.util.Date;

/**
 * The ScheduleItem class establishes the parameters for an appointment. It sets whether the appointment
 * has been booked or not and then returns the date in a string format based on the results.
 */
public class ScheduleItem {

  private Context context;
  private long time;
  private boolean isBooked;

  public ScheduleItem(Context context, long time) {
    this.context = context;
    this.time = time;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public boolean isBooked() {
    return isBooked;
  }

  public void setBooked(boolean booked) {
    isBooked = booked;
  }

  @Override
  public String toString() {
    return DateFormat.getTimeFormat(context).format(new Date(time));
  }
}
