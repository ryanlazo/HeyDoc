package edu.cnm.deepdive.heydoc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;
import android.widget.Toast;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import edu.cnm.deepdive.heydoc.models.Specialty;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
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
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        AppointmentFragment appointmentFragment = new AppointmentFragment();
        transaction.replace(R.id.container1, appointmentFragment).addToBackStack("home").commit();
      }
    });

    return view;
  }




}
