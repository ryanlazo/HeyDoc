package edu.cnm.deepdive.heydoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.cnm.deepdive.heydoc.models.Specialty;
import java.util.List;

/**
 * The SpecialtyFragment sets the parameters for the list of Specialists and inflates that fragment.
 * Each Specialist has designated ID from the database so they are easily and exclusively selected by
 * the user.
 */

public class SpecialtyFragment extends Fragment {

  private ListView specialty;

  public SpecialtyFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_specialty, container, false);
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<Specialty> records = UniDatabase.getInstance(getContext()).specialtyDao().getAll();
        final ArrayAdapter<Specialty> adapter = new ArrayAdapter<>(getActivity(),
            android.R.layout.simple_list_item_1, records);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ((ListView) view).setAdapter(adapter);
          }
        });
      }
    }).start();

    specialty = view.findViewById(R.id.Specialty);
    specialty.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView parent, View view, int position, long id) {
        Specialty specialty = (Specialty) parent.getItemAtPosition(position);
        long specialtyId = specialty.getId();
        Bundle bundle = new Bundle();
        bundle.putLong(PractitionerListFragment.SPECIALTY_ID_KEY, specialtyId);
        ((MainActivity) getActivity()).loadFragment(new PractitionerListFragment(), bundle);
      }
    });
    return view;
  }
}

