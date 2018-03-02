package edu.cnm.deepdive.heydoc.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Practitioner.class,
    parentColumns = "id", childColumns = "practitioner_Id"))
public class Appointment {

 @PrimaryKey(autoGenerate = true)
  private long id;

 @ColumnInfo(name = "date")
  private Date date;

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

