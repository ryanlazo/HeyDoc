package edu.cnm.deepdive.heydoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import edu.cnm.deepdive.heydoc.models.Profile;
import java.util.List;


@Dao
public interface ProfileDao {

  @Query("SELECT * FROM Profile")
  List<Practitioner> getAll();

  @Insert
  long insert(Profile profile);

}


