package edu.cnm.deepdive.heydoc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import edu.cnm.deepdive.heydoc.models.Practitioner;

public class ProfileFragment extends Fragment {

  CalendarView calendar;

  public ProfileFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_profile, container, false);
    calendar = view.findViewById(R.id.calendar);
    calendar.setOnDateChangeListener(new OnDateChangeListener() {
      @Override
      public void onSelectedDayChange(CalendarView view, int year, int month,
          int dayOfMonth) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
            .beginTransaction();
        AppointmentFragment appointmentFragment = AppointmentFragment
            .newInstance(year, month, dayOfMonth);
        transaction.replace(R.id.container1, appointmentFragment).addToBackStack("home").commit();
      }
    });

    FloatingActionButton favAdd = view.findViewById(R.id.fav_add);
    favAdd.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new Thread(new Runnable() {
          @Override
          public void run() {
            Practitioner doc = UniDatabase.getInstance(getContext()).practitionerDao().findByName();
            doc.setIsFavorite(1);
            UniDatabase.getInstance(getContext()).practitionerDao().update(doc);
          }
        }).start();
      }
    });
    return view;
  }
}
