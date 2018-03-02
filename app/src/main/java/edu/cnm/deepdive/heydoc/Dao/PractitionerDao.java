package edu.cnm.deepdive.heydoc.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import java.util.List;


  @Dao
  public interface PractitionerDao {

    @Query("SELECT * FROM Practitioner")
    List<Practitioner> getAll();

    @Query("SELECT * FROM Practitioner WHERE name LIKE :name LIMIT 1")
    Practitioner findByName(String name);

    @Insert
    long insert(Practitioner practitioner);


  }


