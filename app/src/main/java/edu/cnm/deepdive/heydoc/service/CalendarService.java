package edu.cnm.deepdive.heydoc.service;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import edu.cnm.deepdive.heydoc.adapter.EventAdapter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class CalendarService extends Fragment implements EasyPermissions.PermissionCallbacks {

  private static final int REQUEST_ACCOUNT_PICKER = 1000;
  private static final int REQUEST_AUTHORIZATION = 1001;
  private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
  private final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

  private static final String PREF_ACCOUNT_NAME = "accountName";
  private static final String[] SCOPES = {CalendarScopes.CALENDAR };



  private static GoogleAccountCredential credential;


  public CalendarService() {

    }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    synchronized (this.getClass()) {
      if (credential == null) {
        credential = GoogleAccountCredential.usingOAuth2(
            getContext().getApplicationContext(), Arrays.asList(SCOPES))
            .setBackOff(new ExponentialBackOff());
      }
    }
    prepareAPI();
  }

  public GetEventsTask getEventsTask(Callback callback) {
    return new GetEventsTask(callback);
  }


  public GetDayTasks getDayTasks(Callback callback, Date date) {
    return new GetDayTasks(callback, date);
  }

  public CreateEventTask createEventTask(Callback callback) {
    return new CreateEventTask(callback);
  }
  /**
   * Attempt to call the API, after verifying that all the preconditions are
   * satisfied. The preconditions are: Google Play Services installed, an
   * account was selected and the device currently has online access. If any
   * of the preconditions are not satisfied, the app will prompt the user as
   * appropriate.
   */
  private void prepareAPI() {
    if (! isGooglePlayServicesAvailable()) {
      acquireGooglePlayServices();
    } else if (credential.getSelectedAccountName() == null) {
      chooseAccount();
    } else if (! isDeviceOnline()) {
//TODO throw exception.
    }
  }

  /**
   * Attempts to set the account used with the API credentials. If an account
   * name was previously saved it will use that one; otherwise an account
   * picker dialog will be shown to the user. Note that the setting the
   * account to use with the credentials object requires the app to have the
   * GET_ACCOUNTS permission, which is requested here if it is not already
   * present. The AfterPermissionGranted annotation indicates that this
   * function will be rerun automatically whenever the GET_ACCOUNTS permission
   * is granted.
   */
  @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
  private void chooseAccount() {
    if (EasyPermissions.hasPermissions(
        getContext(), Manifest.permission.GET_ACCOUNTS)) {
      String accountName = getActivity().getPreferences(Context.MODE_PRIVATE)
          .getString(PREF_ACCOUNT_NAME, null);
      if (accountName != null) {
        credential.setSelectedAccountName(accountName);
        prepareAPI();
      } else {
        // Start a dialog from which the user can choose an account
        startActivityForResult(
            credential.newChooseAccountIntent(),
            REQUEST_ACCOUNT_PICKER);
      }
    } else {
      // Request the GET_ACCOUNTS permission via a user dialog
      EasyPermissions.requestPermissions(
          getActivity(),
          "This app needs to access your Google account (via Contacts).",
          REQUEST_PERMISSION_GET_ACCOUNTS,
          Manifest.permission.GET_ACCOUNTS);
    }
  }

  /**
   * Called when an activity launched here (specifically, AccountPicker
   * and authorization) exits, giving you the requestCode you started it with,
   * the resultCode it returned, and any additional data from it.
   * @param requestCode code indicating which activity result is incoming.
   * @param resultCode code indicating the result of the incoming
   *     activity result.
   * @param data Intent (containing result data) returned by incoming
   *     activity result.
   */
  @Override
  public void onActivityResult(
      int requestCode, int resultCode, Intent data) {
    switch(requestCode) {
      case REQUEST_GOOGLE_PLAY_SERVICES:
        if (resultCode != RESULT_OK) {
//          mOutputText.setText(
//              "This app requires Google Play Services. Please install " +
//                  "Google Play Services on your device and relaunch this app.");
        } else {
          prepareAPI();
        }
        break;
      case REQUEST_ACCOUNT_PICKER:
        if (resultCode == RESULT_OK && data != null &&
            data.getExtras() != null) {
          String accountName =
              data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
          if (accountName != null) {
            SharedPreferences settings =
                getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_ACCOUNT_NAME, accountName);
            editor.apply();
            credential.setSelectedAccountName(accountName);
            prepareAPI();
          }
        }
        break;
      case REQUEST_AUTHORIZATION:
        if (resultCode == RESULT_OK) {
          prepareAPI();
        }
        break;
    }
  }

  /**
   * Respond to requests for permissions at runtime for API 23 and above.
   * @param requestCode The request code passed in
   *     requestPermissions(android.app.Activity, String, int, String[])
   * @param permissions The requested permissions. Never null.
   * @param grantResults The grant results for the corresponding permissions
   *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
   */
  @Override
  public void onRequestPermissionsResult(int requestCode,
      @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    EasyPermissions.onRequestPermissionsResult(
        requestCode, permissions, grantResults, this);
  }

  /**
   * Callback for when a permission is granted using the EasyPermissions
   * library.
   * @param requestCode The request code associated with the requested
   *         permission
   * @param list The requested permission list. Never null.
   */
  @Override
  public void onPermissionsGranted(int requestCode, List<String> list) {
    // Do nothing.
  }

  /**
   * Callback for when a permission is denied using the EasyPermissions
   * library.
   * @param requestCode The request code associated with the requested
   *         permission
   * @param list The requested permission list. Never null.
   */
  @Override
  public void onPermissionsDenied(int requestCode, List<String> list) {
    // Do nothing.
  }

  /**
   * Checks whether the device currently has a network connection.
   * @return true if the device has a network connection, false otherwise.
   */
  private boolean isDeviceOnline() {
    ConnectivityManager connMgr =
        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    return (networkInfo != null && networkInfo.isConnected());
  }

  /**
   * Check that Google Play services APK is installed and up to date.
   * @return true if Google Play Services is available and up to
   *     date on this device; false otherwise.
   */
  private boolean isGooglePlayServicesAvailable() {
    GoogleApiAvailability apiAvailability =
        GoogleApiAvailability.getInstance();
    final int connectionStatusCode =
        apiAvailability.isGooglePlayServicesAvailable(getActivity());
    return connectionStatusCode == ConnectionResult.SUCCESS;
  }

  /**
   * Attempt to resolve a missing, out-of-date, invalid or disabled Google
   * Play Services installation via a user dialog, if possible.
   */
  private void acquireGooglePlayServices() {
    GoogleApiAvailability apiAvailability =
        GoogleApiAvailability.getInstance();
    final int connectionStatusCode =
        apiAvailability.isGooglePlayServicesAvailable(getActivity());
    if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
      showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
    }
  }


  /**
   * Display an error dialog showing that Google Play Services is missing
   * or out of date.
   * @param connectionStatusCode code describing the presence (or lack of)
   *     Google Play Services on this device.
   */
  void showGooglePlayServicesAvailabilityErrorDialog(
      final int connectionStatusCode) {
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    Dialog dialog = apiAvailability.getErrorDialog(
        getActivity(),
        connectionStatusCode,
        REQUEST_GOOGLE_PLAY_SERVICES);
    dialog.show();
  }

  /**
   * An asynchronous task that handles the Google Calendar API call.
   * Placing the API calls in their own task ensures the UI stays responsive.
   */
  public class GetEventsTask extends AsyncTask<Void, Void, List<Event>> {

    private com.google.api.services.calendar.Calendar service = null;
    private Exception lastException = null;
    private Callback callback;
    private Calendar calendarId;
    private Event eventId;


    public GetEventsTask(Callback callback) {
      this.callback = callback;
      HttpTransport transport = AndroidHttp.newCompatibleTransport();
      JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
      service = new com.google.api.services.calendar.Calendar.Builder(
          transport, jsonFactory, credential)
          .setApplicationName("Google Calendar API Android Quickstart")
          .build();
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected List<Event> doInBackground(Void... params) {
      try {
        return getDataFromApi();
      } catch (Exception e) {
        lastException = e;
        cancel(true);
        return null;
      }
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private List<Event> getDataFromApi() throws IOException {
      // List the next 10 events from the primary calendar.
      DateTime now = new DateTime(System.currentTimeMillis());

      Events events = service.events().list("primary")
          .setMaxResults(10)
          .setTimeMin(now)
          .setOrderBy("startTime")
          .setSingleEvents(true)
          .execute();
      List<Event> items = events.getItems();

      return items;
    }


    @Override
    protected void onPostExecute(List<Event> events) {
      callback.handle(events);

    }

    @Override
    protected void onCancelled()  {
      callback.cancel(lastException);
    }

  }

  public class GetDayTasks extends AsyncTask<Void, Void, List<Event>> {

    private com.google.api.services.calendar.Calendar service = null;
    private Exception lastException = null;
    private Callback callback;
    private Calendar calendarId;
    private Event eventId;
    private Date date;


    public GetDayTasks(Callback callback, Date date) {
      this.callback = callback;
      this.date = date;
      HttpTransport transport = AndroidHttp.newCompatibleTransport();
      JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
      service = new com.google.api.services.calendar.Calendar.Builder(
          transport, jsonFactory, credential)
          .setApplicationName("Google Calendar API Android Quickstart")
          .build();
    }

    /**
     * Background task to call Google Calendar API.
     * @param params no parameters needed for this task.
     */
    @Override
    protected List<Event> doInBackground(Void... params) {
      try {
        return getDataFromApi();
      } catch (Exception e) {
        lastException = e;
        cancel(true);
        return null;
      }
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private List<Event> getDataFromApi() throws IOException {
      // List the next 10 events from the primary calendar.
      Date today = new Date(date.getTime());
      today.setHours(0);
      today.setMinutes(0);
      today.setSeconds(0);
      DateTime start = new DateTime(today.getTime());
      DateTime end = new DateTime(today.getTime() + 24 * 60 * 60 * 1000);

      Events events = service.events().list("primary")
          .setMaxResults(99)
          .setTimeMin(start)
          .setTimeMax(end)
          .setOrderBy("startTime")
          .setSingleEvents(true)
          .execute();
      List<Event> items = events.getItems();

      return items;
    }


    @Override
    protected void onPostExecute(List<Event> events) {
      callback.handle(events);

    }


    @Override
    protected void onCancelled()  {
      callback.cancel(lastException);
    }

  }

  public class CreateEventTask extends AsyncTask<Date, Void, Void> {

    private com.google.api.services.calendar.Calendar service = null;
    private Exception lastException = null;
    private Callback callback;
    private Calendar calendarId;
    private Event eventId;
    private Date date;


    public CreateEventTask(Callback callback) {
      this.callback = callback;
      this.date = date;
      HttpTransport transport = AndroidHttp.newCompatibleTransport();
      JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
      service = new com.google.api.services.calendar.Calendar.Builder(
          transport, jsonFactory, credential)
          .setApplicationName("Google Calendar API Android Quickstart")
          .build();
    }

    /**
     * Background task to call Google Calendar API.
     */
    @Override
    protected Void doInBackground(Date... dates) {
      try {
        sendEventToAPI(dates[0], dates[1]);
      } catch (Exception e) {
        lastException = e;
        cancel(true);

      }
      return null;
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     * @return List of Strings describing returned events.
     * @throws IOException
     */
    private void sendEventToAPI(Date startDate, Date endDate) throws IOException {
      Event event = new Event();
      EventDateTime eventStart = new EventDateTime();
      EventDateTime eventEnd = new EventDateTime();
      eventStart.setDateTime(new DateTime(startDate));
      eventEnd.setDateTime(new DateTime(endDate));

      event.setStart(eventStart);
      event.setEnd(eventEnd);
      event.setSummary("test");
//      event.setStatus("request");
     Event execEvent = service.events().insert("primary", event).execute();

    }


    @Override
    protected void onPostExecute(Void ignore) {
      callback.handle(null);

    }


    @Override
    protected void onCancelled()  {
      callback.cancel(lastException);
    }

  }



  public interface Callback {
    void handle(List <Event> events);
    void cancel(Exception exception);
  }



}
