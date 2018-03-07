package edu.cnm.deepdive.heydoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import java.util.List;


  @Dao
  public interface PractitionerDao {

    @Query("SELECT * FROM Practitioner")
    List<Practitioner> getAll();

    @Query("SELECT * FROM Practitioner WHERE last_name LIKE :name LIMIT 1")
    Practitioner findByName(String name);

    @Query("SELECT * FROM Practitioner WHERE id = :id")
    Practitioner getById (long id);

    @Query("SELECT * FROM Practitioner WHERE specialty_id = :specialtyId")
    List <Practitioner> findBySpecialty (long specialtyId);

    @Insert
    long insert(Practitioner practitioner);

    @Insert
    void insertAll(Practitioner... practitioner);


  }


