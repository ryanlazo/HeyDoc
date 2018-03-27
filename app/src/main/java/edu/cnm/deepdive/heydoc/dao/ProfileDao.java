package edu.cnm.deepdive.heydoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import edu.cnm.deepdive.heydoc.models.Profile;
import java.util.List;

/**
 * Data access object (Dao) of the <code>Profile</code> entity.
 *
 * @author Ryan Lazo
 */

@Dao
public interface ProfileDao {

  /**
   * Query that retrieves data from the <code>Profile</code>
   * @return a list of the Profile
   */

  @Query("SELECT * FROM Profile")
  List<Profile> getAll();

  @Insert
  long insert(Profile profile);

}


