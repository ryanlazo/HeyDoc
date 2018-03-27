package edu.cnm.deepdive.heydoc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.api.services.calendar.model.Event;
import edu.cnm.deepdive.heydoc.adapter.EventAdapter;
import edu.cnm.deepdive.heydoc.models.Appointment;
import edu.cnm.deepdive.heydoc.service.CalendarService;
import edu.cnm.deepdive.heydoc.service.CalendarService.Callback;
import java.util.List;

/**
 * The <code>HomeFragment</code> class extends the Fragment class and inflates the elements of the
 * home page including the list of scheduled appointments. It also contains a FloatingActionButton
 * that populates the users basic health statistics. The onClick and onItemClick methods of this
 * class contain the information of the FloatingActionButton from the onCreateView method. the
 * onItemClick method calls the HomeFragment.
 */
public class HomeFragment extends Fragment {

  private ListView appt;
  private ListView cancel;
  private Button yesButton;
  private Button noButton;
  private CalendarService calendarService;

  public HomeFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    appt = view.findViewById(R.id.appt_list);
    cancel = view.findViewById(R.id.cancel_list);

//    updateDisplay(view);

    FloatingActionButton spendingAdd = view.findViewById(R.id.stats_button);
    spendingAdd.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
            .beginTransaction();
        UserStatsFragment userStatsFragment = new UserStatsFragment();
        transaction.replace(R.id.container1, userStatsFragment).addToBackStack("home").commit();
      }
    });

    appt.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HomeFragment.AppointmentDialog appointmentDialog = new HomeFragment.AppointmentDialog();
        appointmentDialog.appointmentDialog(position, view);
      }
    });

    calendarService = new CalendarService();
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.add(calendarService, calendarService.getClass().getSimpleName()).commit();


    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    calendarService.getEventsTask(new Callback() {
      @Override
      public void handle(List<Event> events) {
        EventAdapter adapter = new EventAdapter(getActivity(),events);
        appt.setAdapter(adapter);
      }

      @Override
      public void cancel(Exception exception) {

      }
    }).execute();
  }

  private void updateDisplay(View view) {

//    new Thread(new Runnable() {
//      @Override
//      public void run() {
//        List<Appointment> appointments = UniDatabase.getInstance(getContext()).appointmentDao()
//            .getSet();
//        List<Appointment> cancelledAppointments = UniDatabase.getInstance(getContext())
//            .appointmentDao()
//            .getCancelled();
//        final ArrayAdapter<Appointment> setAdapter = new ArrayAdapter<>(getActivity(),
//            android.R.layout.simple_list_item_1, appointments);
//        final ArrayAdapter<Appointment> cancelledAdapter = new ArrayAdapter<>(getActivity(),
//            android.R.layout.simple_list_item_1, cancelledAppointments);
//        getActivity().runOnUiThread(new Runnable() {
//          @Override
//          public void run() {
//            appt.setAdapter(setAdapter);
//            cancel.setAdapter(cancelledAdapter);
//          }
//        });
//      }
//    }).start();
  }

  private class AppointmentDialog {

    public void appointmentDialog(final int position, final View view) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

      LayoutInflater inflater = getLayoutInflater();
      @SuppressLint("InflateParams") final View dialogView = inflater
          .inflate(R.layout.cancel_dialog, null);

      builder.setView(dialogView);

      yesButton = dialogView.findViewById(R.id.yes_button);
      noButton = dialogView.findViewById(R.id.no_button);

      final AlertDialog dialog = builder.create();

      yesButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              Appointment appointment = UniDatabase.getInstance(getContext()).appointmentDao()
                  .getSelected(position + 1);
              appointment.setIsCancelled(1);
              UniDatabase.getInstance(getContext()).appointmentDao().updateSelected(appointment);
            }
          }).start();
          dialog.dismiss();
          updateDisplay(view);
        }
      });

      noButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          dialog.dismiss();
        }
      });
      dialog.show();
    }
  }
}
