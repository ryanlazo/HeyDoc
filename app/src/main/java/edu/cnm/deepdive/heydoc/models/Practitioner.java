package edu.cnm.deepdive.heydoc.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Practitioner {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "Location")
    private String location;


}
