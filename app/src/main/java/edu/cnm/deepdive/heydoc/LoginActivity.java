package edu.cnm.deepdive.heydoc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * the <code>LoginActivity</code> class sets the parameters for the log in page which requires a
 * username and a password. If the user types in the wrong username and password they receive a
 * Toast "Wrong Credentials"
 */
public class LoginActivity extends Activity {

  Button b1, b2;
  EditText ed1, ed2;
  TextView tx1;
  int counter = 3;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    b1 = (Button) findViewById(R.id.button);
    ed1 = (EditText) findViewById(R.id.editText);
    ed2 = (EditText) findViewById(R.id.editText2);
    b2 = (Button) findViewById(R.id.button2);


    b1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (ed1.getText().toString().equals("admin") &&
            ed2.getText().toString().equals("admin")) {
          Toast.makeText(getApplicationContext(),
              "Redirecting...", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
        } else {
          Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
          if (counter == 0) {
            b1.setEnabled(false);
          }
        }
      }
    });
    b2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}