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
import android.widget.ListView;
import edu.cnm.deepdive.heydoc.models.Practitioner;
import java.util.List;

public class PractitionerListFragment extends Fragment {

  public static final String SPECIALTY_ID_KEY = "specialty_id_key";
  private ListView practitioner;

  public PractitionerListFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    final View view = inflater.inflate(R.layout.fragment_practitioner_list, container, false);
    final Bundle args = getArguments();
    final long specialtyId = (args != null) ? args.getLong(SPECIALTY_ID_KEY, 0) : 0;

    new Thread(new Runnable() {
      @Override
      public void run() {
        List<Practitioner> records = (specialtyId != 0) ?
            UniDatabase.getInstance(getContext()).practitionerDao().findBySpecialty(specialtyId)
            : UniDatabase.getInstance(getContext()).practitionerDao().getAll();
        final ArrayAdapter<Practitioner> adapter = new ArrayAdapter<>(getActivity(),
            android.R.layout.simple_list_item_1, records);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            practitioner.setAdapter(adapter);
          }
        });
      }
    }).start();
    practitioner = view.findViewById(R.id.practitioner_list);
    practitioner.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView parent, View view, int position, long id) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
            .beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        transaction.replace(R.id.container1, profileFragment).addToBackStack("home").commit();
      }
    });
    return view;
  }
}