package edu.cnm.deepdive.heydoc.dao;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import java.util.List;


@Dao
public interface PractitionerDao {

  @Query("SELECT * FROM Practitioner")
  List<Practitioner> getAll();

  @Query("SELECT * FROM Practitioner WHERE id = 14")
  Practitioner findByName();

  @Query("SELECT * FROM Practitioner WHERE specialty_id = :specialtyId")
  List<Practitioner> findBySpecialty(long specialtyId);

  @Query("Select * FROM Practitioner Where is_favorite = 1")
  List<Practitioner> getFavorites();

  @Update(onConflict = REPLACE)
  void update(Practitioner practitioner);

  @Insert
  long insert(Practitioner practitioner);

  @Insert
  void insertAll(Practitioner... practitioner);


}


