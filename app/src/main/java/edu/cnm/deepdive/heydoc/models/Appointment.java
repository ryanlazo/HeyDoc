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

  public long getPractionerId() {
    return practionerId;
  }

  public void setPractionerId(long practionerId) {
    this.practionerId = practionerId;
  }

  @ColumnInfo(name = "practitioner_id")

  private long practionerId;

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

