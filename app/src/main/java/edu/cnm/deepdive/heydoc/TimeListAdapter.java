package edu.cnm.deepdive.heydoc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class TimeListAdapter extends ArrayAdapter<ScheduleItem> {

  public TimeListAdapter(Context context, int resource, List<ScheduleItem> strings) {
    super(context, resource, strings);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ScheduleItem scheduleItem = getItem(position);
    TextView view = (TextView) LayoutInflater.from(getContext())
        .inflate(android.R.layout.simple_list_item_1, parent, false);
    view.setText(scheduleItem.getTime());
    if (scheduleItem.isBooked()) {
      view.setBackgroundColor(Color.RED);
    }
    return view;
  }
}