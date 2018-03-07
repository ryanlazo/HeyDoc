package edu.cnm.deepdive.heydoc.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.heydoc.models.Account;
import java.util.List;

@Dao
public interface AccountDao {

  @Query("SELECT * FROM Account")
  List<Account> getAll();

  @Query("SELECT * FROM Account WHERE last_name LIKE :name LIMIT 1")
  Account findByName(String name);

  @Insert
  long insert(Account account);


}
