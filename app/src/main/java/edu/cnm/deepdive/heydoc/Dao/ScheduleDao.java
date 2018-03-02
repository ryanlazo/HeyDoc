package edu.cnm.deepdive.heydoc.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Schedule;
import java.util.List;

@Dao
public interface ScheduleDao {

  @Query("SELECT * FROM Schedule")
  List<Schedule> getAll();

  @Insert
  long insert(Schedule schedule);


}
