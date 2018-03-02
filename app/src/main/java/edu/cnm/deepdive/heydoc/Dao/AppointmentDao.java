package edu.cnm.deepdive.heydoc.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Appointment;
import java.util.List;

@Dao
public interface AppointmentDao {

  @Query("SELECT * FROM Appointment")
  List<Appointment> getAll();

  @Insert
  long insert(Appointment Appointment);


}
