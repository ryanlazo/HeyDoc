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

  @Insert
  long insert(Account account);


}
