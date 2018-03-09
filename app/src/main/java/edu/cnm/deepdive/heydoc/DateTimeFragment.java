package edu.cnm.deepdive.heydoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import edu.cnm.deepdive.heydoc.DateTimePickerFragment.Mode;
import edu.cnm.deepdive.heydoc.DateTimePickerFragment.OnChangeListener;
import java.util.Calendar;

public class DateTimeFragment extends Fragment {

  private Calendar calendar;
  private EditText dateControl;
  private EditText timeControl;


  public DateTimeFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_date_time, container, false);
    dateControl = view.findViewById(R.id.date_control);
    timeControl = view.findViewById(R.id.time_control);
    calendar = Calendar.getInstance();
    setupListeners();
    updateDisplay();

    return view;
  }

  private void updateDisplay() {
    dateControl.setText(DateFormat.getDateFormat(getActivity()).format(calendar.getTime()));
    timeControl.setText(DateFormat.getTimeFormat(getActivity()).format(calendar.getTime()));
  }

  private void showDatePicker() {
    DateTimePickerFragment fragment = new DateTimePickerFragment();
    fragment.setMode(Mode.DATE);
    fragment.setPassthrough(false);
    fragment.setCalendar(calendar);
    fragment.setOnChangeListener(new OnChangeListener() {
      @Override
      public void onChange(Calendar calendar) {
        //TODO
        DateTimeFragment.this.calendar = calendar;
        updateDisplay();
      }
    });
    fragment.show(getFragmentManager(), fragment.getClass().getName());
  }

  private void showTimePicker() {
    DateTimePickerFragment fragment = new DateTimePickerFragment();
    fragment.setMode(Mode.TIME);
    fragment.setPassthrough(true);
    fragment.setCalendar(calendar);
    fragment.setOnChangeListener(new OnChangeListener() {
      @Override
      public void onChange(Calendar calendar) {
        updateDisplay();
      }
    });
    fragment.show(getFragmentManager(), fragment.getClass().getName());
  }

  private void setupListeners() {
    dateControl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        showDatePicker();
      }
    });
    timeControl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        showTimePicker();

      }

    });
  }
}


