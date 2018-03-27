package edu.cnm.deepdive.heydoc.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * The <code>Specialty</code> class defines the parameters for populating and displaying the specialty
 * data. This includes the specialty name and the specialty ID.
 */
@Entity
public class Specialty {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "specialty_name")
  private String specialtyName;

  public Specialty() {

  }

  public Specialty(String specialtyName) {
    this.specialtyName = specialtyName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSpecialtyName() {

    return specialtyName;
  }

  public void setSpecialtyName(String specialtyName) {

    this.specialtyName = specialtyName;
  }

  /**
   * The populateData method implements a series of Specialties that will display in alphabetical order.
   * The table is tied in to the database so it can be altered.
   * @return specialtyName
   */
  public static Specialty[] populateData() {
    return new Specialty[]{
        new Specialty("Allergy"),
        new Specialty("Cardiology"),
        new Specialty("Chiropractic"),
        new Specialty("Dermatology"),
        new Specialty("Dentistry"),
        new Specialty("ENT"),
        new Specialty("Endocrinology"),
        new Specialty("Gastroenterology"),
        new Specialty("Orthopaedist"),
        new Specialty("Otology"),
        new Specialty("Pain Management"),
        new Specialty("Pediatrics"),
        new Specialty("Physical Therapy"),
        new Specialty("Psychiatry"),
        new Specialty("Rheumatology"),
        new Specialty("Urology"),
    };
  }

  @Override
  public String toString() {
    return specialtyName;
  }
}
