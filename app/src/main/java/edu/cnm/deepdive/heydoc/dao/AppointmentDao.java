package edu.cnm.deepdive.heydoc.dao;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import edu.cnm.deepdive.heydoc.models.Appointment;
import java.util.List;

@Dao
public interface AppointmentDao {

  @Query("SELECT * FROM Appointment")
  List<Appointment> getAll();

  @Query("SELECT * FROM Appointment WHERE is_cancelled = 0")
  List<Appointment> getSet();

  @Query("SELECT * FROM Appointment WHERE is_cancelled = 1")
  List<Appointment> getCancelled();

  @Query("SELECT * FROM Appointment WHERE id = :id")
  Appointment getSelected(int id);

  @Update(onConflict = REPLACE)
  void updateSelected(Appointment appointment);

  @Insert
  long insert(Appointment Appointment);


}
