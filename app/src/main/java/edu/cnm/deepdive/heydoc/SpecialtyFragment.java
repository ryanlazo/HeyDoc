package edu.cnm.deepdive.heydoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialtyFragment extends Fragment {

  private ListView specialty;
  private ArrayAdapter listAdapter;

  public SpecialtyFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_specialty, container, false);

    specialty = view.findViewById(R.id.Specialty);
    String[] specialtyArray = {"Allergy", "Chiropractic", "Dentist", "Dermatologist"};
    ArrayList<String> fieldsList = new ArrayList<>();
    fieldsList.addAll(Arrays.asList(specialtyArray));
    listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, fieldsList);
    specialty.setAdapter(listAdapter);

    return view;
  }

}