package edu.cnm.deepdive.heydoc.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import edu.cnm.deepdive.heydoc.R;
import java.util.Date;
import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

  private static final int RESOURCE_ID = R.layout.event_item;
  private Context context;

  /**
   * Adapter that allows us to use context and objects as parameters for an Event on the calendar.
   */
  public EventAdapter(Context context, List<Event> objects) {
    super(context, RESOURCE_ID, objects);
    this.context = context;
  }

  /**
   * this method gets the view for the event adapter. The startDate, endDate, startTime,
   * summaryEvent and stateEvent are retrieved from the XML and displayed in a TextView.
   *
   * @param position as an int
   * @param convertView converts the view
   * @param parent from the the ViewGroup class
   * @return item
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View item = LayoutInflater.from(context).inflate(RESOURCE_ID, null);
    Event event = getItem(position);
    TextView startDate = item.findViewById(R.id.start_date);
    TextView endTime = item.findViewById(R.id.end_time);
    TextView startTime = item.findViewById(R.id.start_time);
    TextView summaryEvent = item.findViewById(R.id.summary_event);
    TextView stateEvent = item.findViewById(R.id.state_event);

    Date start = new Date(((DateTime) event.getStart().get("dateTime")).getValue());
    Date end = new Date(((DateTime) event.getEnd().get("dateTime")).getValue());
    startDate.setText(
        DateFormat.getDateFormat(context).format(start));
    startTime.setText(DateFormat.getTimeFormat(context).format(start));
    endTime.setText(
        DateFormat.getTimeFormat(context).format(end));
    summaryEvent.setText(event.getSummary());
    stateEvent.setText(event.getStatus());

//    Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credentials)
//      .setApplicationName("applicationName").build();
//  service.events().delete(calendarID, "eventID").execute();
    return item;
//  }

  }
}
