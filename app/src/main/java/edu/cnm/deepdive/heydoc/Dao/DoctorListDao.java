package edu.cnm.deepdive.heydoc.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Doctor;
import java.util.List;


@Dao
public interface DoctorListDao {

  @Query("SELECT * FROM Doctor")
  List<Doctor> getAll();

  @Query("SELECT * FROM Doctor WHERE doctor_name LIKE :name LIMIT 1")
  Doctor findByName(String name);

  @Insert
  long insert(Doctor doctor);

  @Insert
  void insertAll(Doctor... doctor);

}


