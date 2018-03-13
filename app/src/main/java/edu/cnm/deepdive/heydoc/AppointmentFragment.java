package edu.cnm.deepdive.heydoc;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import edu.cnm.deepdive.heydoc.models.Appointment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentFragment extends Fragment {

  private ListView timeList;
  private Spinner apptSpinner;
  private TextView apptLength;
  private Button yesButton;
  private Button noButton;
  private ArrayAdapter<ScheduleItem> adapter;

  public static AppointmentFragment newInstance(int year, int month, int day) {
    Bundle args = new Bundle();
    args.putInt("year", year);
    args.putInt("month", month);
    args.putInt("day", day);
    AppointmentFragment fragment = new AppointmentFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public AppointmentFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_appointment, container, false);
    timeList = view.findViewById(R.id.time_list);
    List<ScheduleItem> times = new ArrayList<>();
    for (int i = 8; i < 18; i++) {
      for (int j = 0; j < 60; j = j + 15) {
        ScheduleItem time = new ScheduleItem();
        if (j == 0) {
          time.setTime(String.format("%d:%d0", i, j));
        } else {
          time.setTime(String.format("%d:%d", i, j));
        }
        times.add(time);
      }
      adapter = new TimeListAdapter(getActivity(),
          android.R.layout.simple_list_item_1, times);
      timeList.setAdapter(adapter);
    }
    timeList.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AppointmentDialog appointmentDialog = new AppointmentDialog();
        appointmentDialog.appointmentDialog(position);
      }
    });
    return view;
  }

  private class AppointmentDialog {

    public void appointmentDialog(final int position) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

      LayoutInflater inflater = getLayoutInflater();
      @SuppressLint("InflateParams") final View dialogView = inflater
          .inflate(R.layout.appointment_dialog, null);

      builder.setView(dialogView);

      apptSpinner = dialogView.findViewById(R.id.appt_spinner);
      apptLength = dialogView.findViewById(R.id.appt_length);
      yesButton = dialogView.findViewById(R.id.yes_button);
      noButton = dialogView.findViewById(R.id.no_button);

      String[] options = {"Regular cleaning - 30 minutes",
      "Cavity Repair - 60 minutes",
      "New Patient - 90 minutes",
      "Root Canal - 90 minutes"};
      final ArrayAdapter<String> optionAdapter = new ArrayAdapter<>(getActivity(),
          android.R.layout.simple_spinner_dropdown_item, options);
      apptSpinner.setAdapter(optionAdapter);

      final AlertDialog dialog = builder.create();

      yesButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          adapter.getItem(position).setBooked(true);
          adapter.notifyDataSetChanged();
          new Thread(new Runnable() {
            @Override
            public void run() {
              Bundle args = getArguments();
              Calendar calendar = Calendar.getInstance();
              calendar.set(args.getInt("year"), args.getInt("month"), args.getInt("day"), 0, 0, 0);
              SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
              try {
                Date time = sdf.parse(adapter.getItem(position).getTime());
                calendar.add(Calendar.MILLISECOND, (int) time.getTime());
              } catch (ParseException e) {
                throw new RuntimeException(e);
              }
              Appointment appointment = new Appointment();
              appointment.setDate(calendar.getTime());
              appointment.setDuration(15);
              appointment.setPractionerId(1); //TODO CHANGE THIS YOU LAZY ***
              UniDatabase.getInstance(getContext()).appointmentDao().insert(appointment);
            }
          }).start();
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
}
