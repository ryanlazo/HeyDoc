package edu.cnm.deepdive.heydoc.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Account {

 @PrimaryKey(autoGenerate = true)
  private int id;

 @ColumnInfo(name = "lastName")
  private String lastName;

 @ColumnInfo(name = "firstName")
  private String firstName;

  @ColumnInfo(name = "scheduleAppointments")
  private String scheduledAppointments;

  @ColumnInfo(name = "cancelledAppointments")
  private String cancelledAppointments;

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

