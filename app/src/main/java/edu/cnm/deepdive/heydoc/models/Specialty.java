package edu.cnm.deepdive.heydoc.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Specialty {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "specialtyName")
    private String specialtyName;

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
}
