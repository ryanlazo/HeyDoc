package edu.cnm.deepdive.heydoc.dao;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import edu.cnm.deepdive.heydoc.models.Account;
import edu.cnm.deepdive.heydoc.models.Specialty;
import java.util.List;

@Dao
public interface AccountDao {

  @Query("SELECT * FROM Account")
  List<Account> getAll();

  @Query("SELECT * FROM account WHERE id = 1")
  Account findById();

  @Insert
  long insert(Account account);

  @Insert
  void insertAll(Account... accounts);

  @Update(onConflict = REPLACE)
  void updateStats(Account account);

}
