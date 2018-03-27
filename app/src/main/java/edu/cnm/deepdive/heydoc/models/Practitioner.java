package edu.cnm.deepdive.heydoc.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Specialty.class,
    parentColumns = "id", childColumns = "specialty_id"))
public class Practitioner {

  public Practitioner() {

  }

  /**
   * The <code>Practitioner</code> class establishes the parameters for the practitioner data. It is
   * displayed by the Id, lastname, firstname and location. You can also display the practitioner
   * rating and whether or not you want to favorite this Doctor.
   * @param practitionerName String of names
   * @param specialtyId Id established by position in the array
   */
  public Practitioner(String practitionerName, long specialtyId) {
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

  @ColumnInfo(name = "is_favorite")
  private int isFavorite;

  @ColumnInfo(name = "rating")
  private int rating;


  public int getIsFavorite() {
    return isFavorite;
  }

  public void setIsFavorite(int isFavorite) {
    this.isFavorite = isFavorite;
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

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  /**
   * The <code>Practitioner</code> array establishes how to display the practitioner data. The data
   * is populated by lastname, firstname and specialty id which is taken from the specialty class. The
   * id number is determined by its position in specialty array.
   * @return practitionerName
   */
  public static Practitioner[] populateData() {
    return new Practitioner[]{
        new Practitioner("Abernathy, Katherine", 1),
        new Practitioner("Clayton, Michael", 1),
        new Practitioner("Feldman, Bruce", 1),
        new Practitioner("Honsinger, Richard", 1),
        new Practitioner("Jenkins, Teresa", 1),
        new Practitioner("Keslin, Michael", 1),
        new Practitioner("Rosandich, Ronald", 1),
        new Practitioner("Tolber, Steven", 1),

        new Practitioner("Allan, Grant", 5),
        new Practitioner("Anderson, Steven", 5),
        new Practitioner("Chodosh, David", 5),
        new Practitioner("Gomez, Terry", 5),
        new Practitioner("Haight, Michael", 5),
        new Practitioner("Henry, Kyle", 5),
        new Practitioner("Jaime, Lillian", 5),
        new Practitioner("Ku, James", 5),
        new Practitioner("Lee, Monique", 5),
        new Practitioner("Lines, Derek", 5),
        new Practitioner("LoPour, Greg", 5),
        new Practitioner("Mink, Kevin", 5),
        new Practitioner("Newsome, Boyd", 5),
        new Practitioner("Onuora, Obiajulu", 5),
        new Practitioner("Rogers, Jacob", 5),
        new Practitioner("Sanchez, Gary", 5),
        new Practitioner("Sanchez, Greg", 5),

        new Practitioner("Bateman, Tim", 3),
        new Practitioner("Berlin, John", 3),
        new Practitioner("Blessing, Lindsey", 3),
        new Practitioner("Blair, Emily", 3),
        new Practitioner("Blair, Randy", 3),
        new Practitioner("Cecil, Christopher", 3),
        new Practitioner("Coogan, Kelly", 3),
        new Practitioner("Dehoogh, Austin", 3),
        new Practitioner("Del Angel, Tony", 3),
        new Practitioner("Emberger, Michelle", 3),
        new Practitioner("Freeburg, Thomas", 3),
        new Practitioner("Gardner, Stephen", 3),
        new Practitioner("Hightower, Beau", 3),
        new Practitioner("Lovin, Bill", 3),
        new Practitioner("Manson, Kirk", 3),
        new Practitioner("Marcus, Rion", 3),
        new Practitioner("Ramirez, Ruben", 3),
        new Practitioner("Valenti, Helene", 3),
        new Practitioner("Vawter, John", 3),


    };
  }

  @Override
  public String toString() {
    return practitionerName;
  }
}
