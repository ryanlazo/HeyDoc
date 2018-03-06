package edu.cnm.deepdive.heydoc.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.DoctorList;
import java.util.List;


@Dao
  public interface DoctorListDao {

    @Query("SELECT * FROM DoctorList")
    List<DoctorList> getAll();

    @Query("SELECT * FROM Specialty WHERE doctor_name LIKE :name LIMIT 1")
    DoctorList findByName(String name);

    @Insert
    long insert(DoctorList doctorList);

    @Insert
    void insertAll(DoctorList... doctorList);

  }


