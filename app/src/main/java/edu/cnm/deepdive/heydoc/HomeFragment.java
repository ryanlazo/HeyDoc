package edu.cnm.deepdive.heydoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
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
