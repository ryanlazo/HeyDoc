package edu.cnm.deepdive.heydoc.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Account {

 @PrimaryKey(autoGenerate = true)
  private long id;

 @ColumnInfo(name = "last_name")
  private String lastName;

 @ColumnInfo(name = "first_name")
  private String firstName;

  @ColumnInfo(name = "schedule_appointments")
  private String scheduledAppointments;

  @ColumnInfo(name = "cancelled_appointments")
  private String cancelledAppointments;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getScheduledAppointments() {
    return scheduledAppointments;
  }

  public void setScheduledAppointments(String scheduledAppointments) {
    this.scheduledAppointments = scheduledAppointments;
  }

  public String getCancelledAppointments() {
    return cancelledAppointments;
  }

  public void setCancelledAppointments(String cancelledAppointments) {
    this.cancelledAppointments = cancelledAppointments;
  }




}

