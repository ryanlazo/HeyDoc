package edu.cnm.deepdive.heydoc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.cnm.deepdive.heydoc.models.Appointment;
import java.util.List;

public class HomeFragment extends Fragment {

  private ListView appt;

  public HomeFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);

    updateDisplay(view);

    FloatingActionButton spendingAdd = view.findViewById(R.id.stats_button);
    spendingAdd.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        UserStatsFragment userStatsFragment = new UserStatsFragment();
        transaction.replace(R.id.container1, userStatsFragment).addToBackStack("home").commit();
      }
    });

    return view;
  }

  private void updateDisplay(View view) {
    appt = view.findViewById(R.id.appt_list);
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<Appointment> appointments = UniDatabase.getInstance(getContext()).appointmentDao()
            .getAll();
        final ArrayAdapter<Appointment> adapter = new ArrayAdapter<>(getActivity(),
            android.R.layout.simple_list_item_1, appointments);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            appt.setAdapter(adapter);
          }
        });
      }
    }).start();
  }
}
