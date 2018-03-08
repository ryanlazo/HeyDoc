package edu.cnm.deepdive.heydoc;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import edu.cnm.deepdive.heydoc.DateTimePickerFragment.Mode;
import edu.cnm.deepdive.heydoc.DateTimePickerFragment.OnChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentFragment extends Fragment {

  ListView timeList;
  private Calendar calendar;
  private EditText dateControl;
  private EditText timeControl;
  private TextView confirmText;
  private Button yesButton;
  private Button noButton;

  public AppointmentFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_appointment, container, false);
    timeList = view.findViewById(R.id.time_list);
    List<String> times = new ArrayList<>();
    String time;
    for (int i = 8; i < 18; i++) {
      for (int j = 0; j < 60; j = j + 15) {
        if (j == 0) {
          time = String.format("%d:%d0", i, j);
        } else {
          time = String.format("%d:%d", i, j);
        }
        times.add(time);
      }
      ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
          android.R.layout.simple_list_item_1, times);
      timeList.setAdapter(adapter);
    }
    timeList.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AppointmentDialog appointmentDialog = new AppointmentDialog();
        appointmentDialog.appointmentDialog();
      }
    });
    return view;
  }

  private class AppointmentDialog {

    public void appointmentDialog() {
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

      LayoutInflater inflater = getLayoutInflater();
      @SuppressLint("InflateParams") final View dialogView = inflater
          .inflate(R.layout.appointment_dialog, null);

      builder.setView(dialogView);

      confirmText = dialogView.findViewById(R.id.confirm_text);
      yesButton = dialogView.findViewById(R.id.yes_button);
      noButton = dialogView.findViewById(R.id.no_button);

      final AlertDialog dialog = builder.create();

      yesButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

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
