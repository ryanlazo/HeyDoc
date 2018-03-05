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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialtyFragment extends Fragment {

  private ListView specialty;
  private ArrayAdapter listAdapter;

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
        final ArrayAdapter<Specialty> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, records);
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
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


      }
    });

    return view;
  }


  }

