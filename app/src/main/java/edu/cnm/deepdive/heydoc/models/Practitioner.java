package edu.cnm.deepdive.heydoc.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Specialty.class,
    parentColumns = "id", childColumns = "specialty_Id"))
public class Practitioner {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "lastName")
  private String lastName;

  @ColumnInfo(name = "firstName")
  private String firstName;

  @ColumnInfo(name = "Location")
  private String location;

  @ColumnInfo(name = "specialty_id")
  private long specialtyId;


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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
