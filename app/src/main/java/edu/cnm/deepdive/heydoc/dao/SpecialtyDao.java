package edu.cnm.deepdive.heydoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Specialty;
import java.util.List;

/**
 * Data access object (Dao) of the <code>Specialty</code> entity.
 *
 * @author Ryan Lazo
 */

@Dao
public interface SpecialtyDao {

  /**
   * Query that retrieves data from the <code>Specialty</code>
   * @return a list of the Specialty
   */

  @Query("SELECT * FROM Specialty")
  List<Specialty> getAll();

  @Insert
  long insert(Specialty specialty);

  @Insert
  void insertAll(Specialty... specialties);

}
