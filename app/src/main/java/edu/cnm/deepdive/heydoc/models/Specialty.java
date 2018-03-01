package edu.cnm.deepdive.heydoc.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Specialty {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "specialtyName")
    private String specialtyName;

}
