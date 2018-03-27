package edu.cnm.deepdive.heydoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Schedule;
import java.util.List;

/**
 * Data access object (Dao) of the <code>Schedule</code> entity.
 *
 * @author Ryan Lazo
 */
@Dao
public interface ScheduleDao {

  /**
   * Query that retrieves data from the <code>Schedule</code>
   * @return a list of the Schedule
   */

  @Query("SELECT * FROM Schedule")
  List<Schedule> getAll();

  @Insert
  long insert(Schedule schedule);


}
