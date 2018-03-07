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
import edu.cnm.deepdive.heydoc.models.Practitioner;
import edu.cnm.deepdive.heydoc.models.Specialty;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

  public static final String SPECIALTY_ID_KEY = "specialty_id_key";
  private ListView profile;


  public ProfileFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View view = inflater.inflate(R.layout.fragment_profile, container, false);







//    new Thread(new Runnable() {
//      @Override
//      public void run() {
//
//        List<Practitioner> records = (specialtyId != 0) ?
//            UniDatabase.getInstance(getContext()).practitionerDao().findBySpecialty(specialtyId)
//            : UniDatabase.getInstance(getContext()).practitionerDao().getAll();
//        final ArrayAdapter<Practitioner> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, records);
//        getActivity().runOnUiThread(new Runnable() {
//          @Override
//          public void run() {
//            practitioner.setAdapter(adapter);
//          }
//        });
//      }
//    }).start();

//     = view.findViewById(R.id.d);
//    specialty.setOnItemClickListener(new OnItemClickListener() {
//      @Override
//      public void onItemClick(AdapterView parent, View view, int position, long id) {
//        //
//        Specialty specialty = (Specialty) parent.getItemAtPosition(position);
//        long specialtyId = specialty.getId();
//      }
//    });

    return view;

  }




}
