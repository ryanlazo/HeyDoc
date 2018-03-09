package edu.cnm.deepdive.heydoc.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import edu.cnm.deepdive.heydoc.models.Appointment.Converters;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Practitioner.class,
    parentColumns = "id", childColumns = "practitioner_id"))
@TypeConverters(Converters.class)
public class Appointment {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "date")
  private Date date;

  @ColumnInfo(name = "duration")
  private int duration;

  @ColumnInfo(name = "practitioner_id")
  private long practionerId;

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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public long getPractionerId() {
    return practionerId;
  }

  public void setPractionerId(long practionerId) {
    this.practionerId = practionerId;
  }

  @Override
  public String toString() {
    return date + " for " + duration + " minutes";
  }
}

