package edu.cnm.deepdive.heydoc.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Specialty.class,
    parentColumns = "id", childColumns = "specialty_id"))

public class Profile {

  public Profile() {

  }

  /**
   * The <code>Profile</code> class sets several parameters for displaying a Doctor's profile. Id,
   * name and location are all used to define specific profile pages for each Doctor.
   * @param Name String of names
   * @param specialtyId defined as a long in this method.
   */
  public Profile(String Name, long specialtyId) {
    this.specialtyId = specialtyId;
    this.practitionerName = practitionerName;
  }

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "practitioner_name")
  private String practitionerName;

  @ColumnInfo(name = "last_name")
  private String lastName;

  @ColumnInfo(name = "first_name")
  private String firstName;

  @ColumnInfo(name = "location")
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

  public long getSpecialtyId() {
    return specialtyId;
  }

  public void setSpecialtyId(long specialtyId) {

    this.specialtyId = specialtyId;
  }

  public String getPractitionerName() {
    return practitionerName;
  }

  public void setPractitionerName(String practitionerName) {
    this.practitionerName = practitionerName;
  }

  /**
   * The <code>Practitioner</code> method links the profile data to the practitioner data
   * @return practitionerName
   */
  public static Practitioner[] populateData() {
    return new Practitioner[]{
        new Practitioner("Abernathy, Katherine", 1),

    };
  }

  @Override
  public String toString() {
    return practitionerName;
  }
}
