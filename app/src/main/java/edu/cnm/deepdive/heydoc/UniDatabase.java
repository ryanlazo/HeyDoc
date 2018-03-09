package edu.cnm.deepdive.heydoc;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.heydoc.dao.AccountDao;
import edu.cnm.deepdive.heydoc.dao.AppointmentDao;
import edu.cnm.deepdive.heydoc.dao.PractitionerDao;
import edu.cnm.deepdive.heydoc.dao.ScheduleDao;
import edu.cnm.deepdive.heydoc.dao.SpecialtyDao;
import edu.cnm.deepdive.heydoc.models.Account;
import edu.cnm.deepdive.heydoc.models.Appointment;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import edu.cnm.deepdive.heydoc.models.Schedule;
import edu.cnm.deepdive.heydoc.models.Specialty;
import java.util.concurrent.Executors;

@Database(entities = {Account.class, Appointment.class, Practitioner.class,
    Schedule.class, Specialty.class}, version = 1)
public abstract class UniDatabase extends RoomDatabase {

  private static UniDatabase INSTANCE;

  public abstract AccountDao accountDao();

  public abstract AppointmentDao appointmentDao();

  public abstract PractitionerDao practitionerDao();

  public abstract ScheduleDao scheduleDao();

  public abstract SpecialtyDao specialtyDao();

  public synchronized static UniDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = buildDatabase(context);
    }
    return INSTANCE;
  }

  private static UniDatabase buildDatabase(final Context context) {
    return Room.databaseBuilder(context,
        UniDatabase.class,
        "unidatabase")
        .addCallback(new Callback() {
          @Override
          public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
              @Override
              public void run() {
                getInstance(context).specialtyDao().insertAll(Specialty.populateData());
                getInstance(context).practitionerDao().insertAll(Practitioner.populateData());
              }
            });
          }
        })
        .build();
  }
}





