package edu.cnm.deepdive.heydoc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import edu.cnm.deepdive.heydoc.models.Practitioner;

/**
 * The ProfileFragment class has several elements to it including the calendar and the rating bar for
 * each one of the practitioners. The practitioner list is called from the database and populated in
 * the fragment and each populated profile has a rating bar consisting of 5 stars that are ratable to
 * the .5. The floating action button wired in here allows the user to favorite a doctor which then
 * populates in the navigation drawer menu option favorites.
 */

public class ProfileFragment extends Fragment {

  CalendarView calendar;
  RatingBar ratingBar;

  public ProfileFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_profile, container, false);
    calendar = view.findViewById(R.id.calendar);
    ratingBar = view.findViewById(R.id.rating);
    new Thread(new Runnable() {
      @Override
      public void run() {
        Practitioner doc = UniDatabase.getInstance(getContext()).practitionerDao().findByName();
        ratingBar.setRating(doc.getRating());
      }
    }).start();
    calendar.setOnDateChangeListener(new OnDateChangeListener() {
      @Override
      public void onSelectedDayChange(CalendarView view, int year, int month,
          int dayOfMonth) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
            .beginTransaction();
        AppointmentFragment appointmentFragment = AppointmentFragment
            .newInstance(year, month, dayOfMonth);
        transaction.replace(R.id.container1, appointmentFragment).addToBackStack("home").commit();
      }
    });

    FloatingActionButton favAdd = view.findViewById(R.id.fav_add);
    favAdd.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new Thread(new Runnable() {
          @Override
          public void run() {
            Practitioner doc = UniDatabase.getInstance(getContext()).practitionerDao().findByName();
            doc.setIsFavorite(1);
            UniDatabase.getInstance(getContext()).practitionerDao().update(doc);
          }
        }).start();
      }
    });

    ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(final RatingBar ratingBar, float rating, boolean fromUser) {
        new Thread(new Runnable() {
          @Override
          public void run() {
            Practitioner doc = UniDatabase.getInstance(getContext()).practitionerDao().findByName();
            doc.setRating(ratingBar.getNumStars());
            UniDatabase.getInstance(getContext()).practitionerDao().update(doc);
          }
        }).start();
      }
    });

    return view;
  }
}
