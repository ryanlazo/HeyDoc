package edu.cnm.deepdive.heydoc.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import edu.cnm.deepdive.heydoc.models.Appointment.Converters;
import java.util.Date;

/**
 * The <code>Schedule</code> class utilizes the Date class to populate data necessary for allowing
 * the user to schedule an appointment. The parameters are established here by the date and id.
 */
@Entity
@TypeConverters(Converters.class)
public class Schedule {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "date")
  private Date date;

  /**
   * The <code>Converters</code> class takes the values from above and uses them to set a dateTimeStamp.
   * It returns a date object.
   */
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
}


