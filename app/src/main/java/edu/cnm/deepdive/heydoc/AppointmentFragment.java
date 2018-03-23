package edu.cnm.deepdive.heydoc;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import edu.cnm.deepdive.heydoc.models.Appointment;
import edu.cnm.deepdive.heydoc.service.CalendarService;
import edu.cnm.deepdive.heydoc.service.CalendarService.Callback;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * This is the AppointmentFragment class that extends Fragment. It contains several private fields
 * that help set the parameters for each appointment time and what happens when it is clicked.
 */
public class AppointmentFragment extends Fragment {

  private List<ScheduleItem> times;
  private ListView timeList;
  private Spinner apptSpinner;
  private TextView apptLength;
  private Button yesButton;
  private Button noButton;
  private ArrayAdapter<ScheduleItem> adapter;
  private CalendarService calendarService;
  private int year;
  private int month;
  private int day;
  private Calendar calendar;

  /**
   * establishes argument parameters for day, month and year and assigns it to an appointment
   * @param year sets the year
   * @param month sets the month
   * @param day sets the day
   * @return fragment
   */
  public static AppointmentFragment newInstance(int year, int month, int day) {
    Bundle args = new Bundle();
    args.putInt("year", year);
    args.putInt("month", month);
    args.putInt("day", day);
    AppointmentFragment fragment = new AppointmentFragment();
    fragment.setArguments(args);
    return fragment;
  }

  /**
   * empty parameter constructor for AppointmentFragment
   */
  public AppointmentFragment() {

  }

  /**
   * the onCreate method passes a bundle of arguments assigned to a day, month and year. Then it sets
   * a calendar object and sets the calendar to whatever the current day is. Whenever onCreate is
   * invoked the day will either change or stay the same based on the what day it is.
   * @param savedInstanceState saves the instance based on the day
   */
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle args = getArguments();
    year = args.getInt("year");
    month = args.getInt("month");
    day = args.getInt("day");
    calendar = Calendar.getInstance();
    calendar.clear();
    calendar.set(year, month, day);
    calendar.setTimeZone(TimeZone.getDefault());
    calendarService = new CalendarService();
    getFragmentManager().beginTransaction().add(calendarService, "Calendar Service Today").commit();
  }

  /**
   * the onCreateView method sets a view object and when it gets invoked it inflates the appointment
   * fragment layout. the timeList finds the view by ID and returns a view.
   * @param inflater inflates the layout
   * @param container class of ViewGroup
   * @param savedInstanceState saves the bundle
   * @return view
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_appointment, container, false);
    timeList = view.findViewById(R.id.time_list);
    return view;
  }

  /**
   * sets the hours, seconds and minutes for the date object and calls the getDayTasks method to
   * retrieve the events listed for that day.
   */
  @Override
  public void onStart() {
    super.onStart();
    final Date date = calendar.getTime();

    date.setHours(8);
    date.setMinutes(0);
    date.setSeconds(0);
    calendarService.getDayTasks(new Callback() {

      /**
       * uses a for loop to iterate through the scheduled items based on the number of seconds in
       * millis, minutes, and hours. Assigns a value to determine the start and end times for an
       * event. An if statement is used to determine whether the appointment time has already been
       * booked or not. Finally, the handle method utilizes the TimeListAdapter and the onItemClick
       * Listener.
       * @param events list of events for that day.
       */
      @Override
      public void handle(List<Event> events) {
        times = new ArrayList<>();
        for (int i = 0; i < 40; i++) {

          ScheduleItem item = new ScheduleItem(getContext(), date.getTime() + i * 15 * 60 * 1000);
          for (Event event : events) {
            long eventStart = ((DateTime) event.getStart().get("dateTime")).getValue();
            long eventEnd = ((DateTime) event.getEnd().get("dateTime")).getValue();
            long itemStart = item.getTime();
            long itemEnd = itemStart + 15 * 60 * 1000;
            if (itemStart < eventEnd && itemEnd > eventStart) {
              item.setBooked(true);
            }

          }
          times.add(item);

        }

        adapter = new TimeListAdapter(getActivity(),
            android.R.layout.simple_list_item_1, times);
        timeList.setAdapter(adapter);
        timeList.setOnItemClickListener(new OnItemClickListener() {

          /**
           * this is the onItemClick used during the onItemClickListener. It sets the appointmentDialog
           * and passes a position parameter.
           * @param parent parent
           * @param view view for the onItemClick
           * @param position position represented as an int
           * @param id id displayed as a long
           */
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppointmentDialog appointmentDialog = new AppointmentDialog();
            appointmentDialog.appointmentDialog(position);
          }
        });
      }

      @Override
      public void cancel(Exception exception) {

      }
    }, date).execute();
  }

  private class AppointmentDialog {

    public void appointmentDialog(final int position) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

      LayoutInflater inflater = getLayoutInflater();
      @SuppressLint("InflateParams") final View dialogView = inflater
          .inflate(R.layout.appointment_dialog, null);

      builder.setView(dialogView);

      apptSpinner = dialogView.findViewById(R.id.appt_spinner);
      yesButton = dialogView.findViewById(R.id.yes_button);
      noButton = dialogView.findViewById(R.id.no_button);

      String[] options = {"Regular cleaning - 30 minutes",
          "Cavity Repair - 1 hour",
          "New Patient - 1 hour 30 minutes",
          "Root Canal - 1 hour 30 minutes"};
      final ArrayAdapter<String> optionAdapter = new ArrayAdapter<>(getActivity(),
          android.R.layout.simple_spinner_dropdown_item, options);
      apptSpinner.setAdapter(optionAdapter);

      final AlertDialog dialog = builder.create();

      yesButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          switch (apptSpinner.getSelectedItem().toString()) {
            case "Regular cleaning - 30 minutes":
              adapter.getItem(position).setBooked(true);
              adapter.getItem(position + 1).setBooked(true);
              adapter.notifyDataSetChanged();
              break;
            case "Cavity Repair - 1 hour":
              adapter.getItem(position).setBooked(true);
              adapter.getItem(position + 1).setBooked(true);
              adapter.getItem(position + 2).setBooked(true);
              adapter.getItem(position + 3).setBooked(true);
              adapter.notifyDataSetChanged();
              break;
            case "New Patient - 1 hour 30 minutes":
              adapter.getItem(position).setBooked(true);
              adapter.getItem(position + 1).setBooked(true);
              adapter.getItem(position + 2).setBooked(true);
              adapter.getItem(position + 3).setBooked(true);
              adapter.getItem(position + 4).setBooked(true);
              adapter.getItem(position + 5).setBooked(true);
              adapter.notifyDataSetChanged();
              break;
            case "Root Canal - 1 hour 30 minutes":
              adapter.getItem(position).setBooked(true);
              adapter.getItem(position + 1).setBooked(true);
              adapter.getItem(position + 2).setBooked(true);
              adapter.getItem(position + 3).setBooked(true);
              adapter.getItem(position + 4).setBooked(true);
              adapter.getItem(position + 5).setBooked(true);
              adapter.notifyDataSetChanged();
              break;
          }
          new Thread(new Runnable() {
            @Override
            public void run() {
              Bundle args = getArguments();
              Calendar calendar = Calendar.getInstance();
              calendar.setTimeInMillis(adapter.getItem(position).getTime());
              Date startDate = new Date(calendar.getTimeInMillis());
              calendar.add(Calendar.MINUTE, 30);
              Date endDate = new Date(calendar.getTimeInMillis());
//              appointment.setPractionerId(1); //TODO CHANGE THIS YOU LAZY ***
//              switch (apptSpinner.getSelectedItem().toString()) {
//                case "Regular cleaning - 30 minutes":
//                  appointment.setDuration(30);
//                  break;
//                case "Cavity Repair - 1 hour":
//                  appointment.setDuration(60);
//                  break;
//                case "New Patient - 1 hour 30 minutes":
//                  appointment.setDuration(90);
//                  break;
//                case "Root Canal - 1 hour 30 minutes":
//                  appointment.setDuration(90);
//                  break;
//              }
//              UniDatabase.getInstance(getContext()).appointmentDao().insert(appointment);
              calendarService.createEventTask(new Callback() {
                @Override
                public void handle(List<Event> events) {
                  Log.d("calendar service", "event created");
                }

                @Override
                public void cancel(Exception exception) {

                }
              }).execute(startDate, endDate);
            }
          }).start();
          showToast();
          dialog.dismiss();
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

  public void showToast() {
    Toast.makeText(getContext(), "You will be contacted when your appointment has been confirmed.",
        Toast.LENGTH_LONG).show();
  }
}
