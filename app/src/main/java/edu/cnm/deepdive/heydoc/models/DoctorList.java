package edu.cnm.deepdive.heydoc.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DoctorList {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "doctor_name")
  private String doctorName;

  public DoctorList() {

  }

  public DoctorList(String doctorName) {
    this.doctorName = doctorName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDoctorName() {
    return doctorName;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
  }

  public static DoctorList[] populateData() {
    return new DoctorList[] {
        new DoctorList(""),

    };
  }

  @Override
  public String toString() {
    return doctorName;
  }
}

