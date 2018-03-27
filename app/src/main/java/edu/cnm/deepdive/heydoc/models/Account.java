package edu.cnm.deepdive.heydoc.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import edu.cnm.deepdive.heydoc.models.Appointment.Converters;
import java.util.Date;

/**
 * The <code>Account</code> class is used for the users account information. It displays the lastname,
 * firstname of the user, and displays whether they have a scheduled appointment or if they recently
 * cancelled any. In the floating action button it displays the users basic health statistics
 */
@Entity
@TypeConverters(Converters.class)
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

  @ColumnInfo(name = "last_physical")
  private Date lastPhysical;

  @ColumnInfo(name = "age")
  private int age;

  @ColumnInfo(name = "height")
  private double height;

  @ColumnInfo(name = "weight")
  private double weight;

  @ColumnInfo(name = "blood_pressure")
  private int bloodPressure;

  @ColumnInfo(name = "respiratory_rate")
  private int respiratoryRate;

  @ColumnInfo(name = "heart_rate")
  private int heartRate;

  public static class Converters {

    @TypeConverter
    public Date fromTimestamp(Long value) {
      return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
      if (date == null) {
        return null;
      } else {
        return date.getTime();
      }
    }
  }

  public Account() {

  }

  /**
   * This is the users basic health statistics data. It can be changed and populated by any Doctor
   * and displayed for user to review.
   * @param age in int
   * @param height as a double
   * @param weight as a double
   * @param bp as an int
   * @param rr as an int
   * @param hr as an int
   */
  public Account(int age, double height, double weight, int bp, int rr, int hr) {
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.bloodPressure = bp;
    this.respiratoryRate = rr;
    this.heartRate = hr;
  }

  public Date getLastPhysical() {
    return lastPhysical;
  }

  public void setLastPhysical(Date lastPhysical) {
    this.lastPhysical = lastPhysical;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public int getBloodPressure() {
    return bloodPressure;
  }

  public void setBloodPressure(int bloodPressure) {
    this.bloodPressure = bloodPressure;
  }

  public int getRespiratoryRate() {
    return respiratoryRate;
  }

  public void setRespiratoryRate(int respiratoryRate) {
    this.respiratoryRate = respiratoryRate;
  }

  public int getHeartRate() {
    return heartRate;
  }

  public void setHeartRate(int heartRate) {
    this.heartRate = heartRate;
  }

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

  /**
   * This is a sample of user statistics that is hard-coded for the presentation
   * @return user statistics.
   */
  public static Account[] populateData() {
    return new Account[]{
      new Account(30, 5.10, 150.0, 117, 13, 86)
    };
  }
}

