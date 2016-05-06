package in.codehex.office;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PasscodeActivity extends AppCompatActivity {

    EditText passcodeEditText;
    Button submitButton;
    String passCode,dateString,tempPassCode,codehexPasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/latoregular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
        setContentView(R.layout.activity_passcode);
        passcodeEditText = (EditText) findViewById(R.id.passcode_edit_text);
        submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codehexPasscode = "codehex@123";
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                dateString = dateFormat.format(Calendar.getInstance().getTime());
                tempPassCode = codehexPasscode.concat(dateString);
                passCode = passcodeEditText.getText().toString();
                if(passCode.equals(tempPassCode)){
                    Toast.makeText(getApplicationContext(),"Login Successful!!",Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(PasscodeActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"You have entered wrong passcode",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
