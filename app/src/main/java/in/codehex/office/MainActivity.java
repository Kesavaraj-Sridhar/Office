package in.codehex.office;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner roleSpinner;
    String roleBit;
    EditText zipCodeEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/latoregular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);
        roleSpinner =(Spinner) findViewById(R.id.role_spinner);
        roleSpinner.setOnItemSelectedListener(this);
        zipCodeEditText = (EditText) findViewById(R.id.zip_code_edit_text);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.edit_text_color));

        if(parent.getItemAtPosition(position).toString().equals("Buyer")){
            //Toast.makeText(getApplicationContext(),"Admin",Toast.LENGTH_LONG).show();
            roleBit="0";
        }
        if(parent.getItemAtPosition(position).toString().equals("Seller")){
            roleBit="1";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
