package edu.cnm.deepdive.heydoc;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import edu.cnm.deepdive.heydoc.Dao.AccountDao;
import edu.cnm.deepdive.heydoc.Dao.AppointmentDao;
import edu.cnm.deepdive.heydoc.Dao.ScheduleDao;
import edu.cnm.deepdive.heydoc.Dao.SpecialtyDao;
import edu.cnm.deepdive.heydoc.models.Account;
import edu.cnm.deepdive.heydoc.models.Appointment;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import edu.cnm.deepdive.heydoc.models.Schedule;
import edu.cnm.deepdive.heydoc.models.Specialty;
import edu.cnm.deepdive.roomapp.dao.ProductDao;
import edu.cnm.deepdive.roomapp.models.Product;

@Database(entities = {Account.class, Appointment.class, Practitioner.class,
Schedule.class, Specialty.class}, version = 1)
public abstract class UniDatabase extends RoomDatabase {

  public abstract AccountDao accountDao();
  public abstract AppointmentDao appointmentsDao();
  public abstract Practitioner practitionerDao();
  public abstract ScheduleDao scheduleDao();
  public abstract SpecialtyDao specialtyDao();


}
