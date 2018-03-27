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

/**
 * The <code>FavoritesFragment</code> inflates the fragment_favorites layout. It utilizes an adapter
 * to help set a Doctor to your favorites.
 */
public class FavoritesFragment extends Fragment {

  private ListView fav;



  public FavoritesFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_favorites, container, false);

    updateDisplay(view);

    fav.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
            .beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();
        transaction.replace(R.id.container1, profileFragment).addToBackStack("home").commit();
      }
    });

    return view;
  }

  private void updateDisplay(View view) {
    fav = view.findViewById(R.id.fav_list);
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<Practitioner> practitioners = UniDatabase.getInstance(getContext()).practitionerDao()
            .getFavorites();
        final ArrayAdapter<Practitioner> adapter = new ArrayAdapter<>(getActivity(),
            android.R.layout.simple_list_item_1, practitioners);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            fav.setAdapter(adapter);
          }
        });
      }
    }).start();
  }
}