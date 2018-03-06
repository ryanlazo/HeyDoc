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
import edu.cnm.deepdive.heydoc.models.DoctorList;
import edu.cnm.deepdive.heydoc.models.Specialty;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorListFragment extends Specialty {

  private ListView doctorList;
  private ArrayAdapter listAdapter;

  public DoctorListFragment() {

  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);

    new Thread(new Runnable() {
      @Override
      public void run() {

        List<DoctorList> records = UniDatabase.getInstance(getContext()).DoctorListDao().getAll();
        final ArrayAdapter<DoctorList> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, records);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ((ListView) view).setAdapter(adapter);
          }
        });
      }
    }).start();

    doctorList = view.findViewById(R.id.doctorList);
    doctorList.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


      }
    });

    return view;
  }


}