package edu.cnm.deepdive.heydoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Specialty;
import java.util.List;

@Dao
public interface SpecialtyDao {

  @Query("SELECT * FROM Specialty")
  List<Specialty> getAll();

  @Query("SELECT * FROM Specialty WHERE specialty_name LIKE :name LIMIT 1")
  Specialty findByName(String name);

  @Insert
  long insert(Specialty specialty);

  @Insert
  void insertAll(Specialty... specialties);

}
