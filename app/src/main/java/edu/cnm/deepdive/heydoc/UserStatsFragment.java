package edu.cnm.deepdive.heydoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import edu.cnm.deepdive.heydoc.models.Account;


public class UserStatsFragment extends Fragment implements TextWatcher{

  private EditText ageEdit;
  private EditText heightEdit;
  private EditText weightEdit;
  private EditText bpEdit;
  private EditText rrEdit;
  private EditText hrEdit;
  private int age;
  private double height;
  private double weight;
  private int bp;
  private int rr;
  private int hr;
  private Account account;

  public UserStatsFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_stats, container, false);
    ageEdit = view.findViewById(R.id.age_edit);
    heightEdit = view.findViewById(R.id.height_edit);
    weightEdit = view.findViewById(R.id.weight_edit);
    bpEdit = view.findViewById(R.id.bp_edit);
    rrEdit = view.findViewById(R.id.rr_edit);
    hrEdit = view.findViewById(R.id.hr_edit);
    ageEdit.addTextChangedListener(this);
    heightEdit.addTextChangedListener(this);
    weightEdit.addTextChangedListener(this);
    bpEdit.addTextChangedListener(this);
    rrEdit.addTextChangedListener(this);
    hrEdit.addTextChangedListener(this);
    Button button = view.findViewById(R.id.update_button);

    updateDisplay();

    button.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new Thread(new Runnable() {
          @Override
          public void run() {
            account.setAge(age);
            account.setHeight(height);
            account.setWeight(weight);
            account.setBloodPressure(bp);
            account.setRespiratoryRate(rr);
            account.setHeartRate(hr);
            UniDatabase.getInstance(getContext()).accountDao().updateStats(account);
          }
        }).start();
        updateDisplay();
      }
    });

    return view;
  }

  private void updateDisplay() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        account = UniDatabase.getInstance(getContext()).accountDao().findById();
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            ageEdit.setText(String.format("%d", account.getAge()));
            heightEdit.setText(String.format("%.2f", account.getHeight()));
            weightEdit.setText(String.format("%.2f", account.getWeight()));
            bpEdit.setText(String.format("%d/80", account.getBloodPressure()));
            rrEdit.setText(String.format("%d", account.getRespiratoryRate()));
            hrEdit.setText(String.format("%d", account.getHeartRate()));
          }
        });
      }
    }).start();
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable s) {

    try {
      age = Integer.parseInt(ageEdit.getText().toString());
      height = Double.parseDouble(heightEdit.getText().toString());
      weight = Double.parseDouble(weightEdit.getText().toString());
      bp = Integer.parseInt(bpEdit.getText().toString());
      rr = Integer.parseInt(rrEdit.getText().toString());
      hr = Integer.parseInt(hrEdit.getText().toString());
    } catch (NumberFormatException e) {

    }
  }
}
