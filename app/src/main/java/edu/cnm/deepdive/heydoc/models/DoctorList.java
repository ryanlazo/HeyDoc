package edu.cnm.deepdive.heydoc.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DoctorList {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "doctorList_name")
  private String doctorListName;

  public DoctorList() {

  }

  public DoctorList(String doctorListName) {
    this.doctorListName = doctorListName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDoctorListName() {
    return doctorListName;
  }

  public void setDoctorListName(String doctorListName) {
    this.doctorListName = doctorListName;
  }

  public static DoctorList[] populateData() {
    return new DoctorList[] {
        new DoctorList(""),

    };
  }

  @Override
  public String toString() {
    return doctorListName;
  }
}

