package in.codehex.office;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner roleSpinner;
    private static String name, emailId, cUrl, roleBit, companyContact, street, city, state, country, zipcode, responsecompanyId, responseName, responseRoleId;
    private ProgressDialog pDialog;
    ImageButton submitImageButton;
    private static String TAG = MainActivity.class.getSimpleName();
    EditText nameEditText, emailIdEditText, curlEditText, companyContactEditText, streetEditText, cityEditText, stateEditText, countryEditText, zipCodeEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/latoregular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        roleSpinner =(Spinner) findViewById(R.id.role_spinner);
        roleSpinner.setOnItemSelectedListener(this);
        zipCodeEditText = (EditText) findViewById(R.id.zip_code_edit_text);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        emailIdEditText = (EditText) findViewById(R.id.email_id_edit_text);
        curlEditText = (EditText) findViewById(R.id.curl_edit_text);
        companyContactEditText = (EditText) findViewById(R.id.company_contact_edit_text);
        streetEditText = (EditText) findViewById(R.id.street_edit_text);
        cityEditText = (EditText) findViewById(R.id.city_edit_text);
        stateEditText = (EditText) findViewById(R.id.state_edit_text);
        countryEditText = (EditText) findViewById(R.id.country_edit_text);
        submitImageButton = (ImageButton) findViewById(R.id.submit_image_button);
        submitImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                //startActivity(i);

                if((nameEditText.getText().toString()).equals("")||
                        (curlEditText.getText().toString()).equals("")||
                        (companyContactEditText.getText().toString()).equals("")||
                        (streetEditText.getText().toString()).equals("")||
                        (cityEditText.getText().toString()).equals("")||
                        (stateEditText.getText().toString()).equals("")||
                        (countryEditText.getText().toString()).equals("")||
                        (zipCodeEditText.getText().toString()).equals("")||
                        (nameEditText.getText().toString()).equals("")){
                    Toast.makeText(getApplicationContext(),"All fields are mandatory!!!",Toast.LENGTH_LONG).show();
                }else{
                    if((companyContactEditText.getText().toString()).length()<10) {
                        Toast.makeText(getApplicationContext(),"Enter valid contact number!!!",Toast.LENGTH_LONG).show();}
                    else if((zipCodeEditText.getText().toString()).length()<6){
                        Toast.makeText(getApplicationContext(),"Enter valid zip code!!!",Toast.LENGTH_LONG).show();
                    }
                    else{uploadDetails();}
                }

            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.edit_text_color));
        //((TextView) parent.getChildAt(0)).setTextSize(R.dimen.primary_text);

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
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void uploadDetails() {
        showpDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            name = nameEditText.getText().toString();
            cUrl = curlEditText.getText().toString();
            companyContact = companyContactEditText.getText().toString();
            zipcode = zipCodeEditText.getText().toString();
            state = stateEditText.getText().toString();
            street = streetEditText.getText().toString();
            city = cityEditText.getText().toString();
            country = countryEditText.getText().toString();
            emailId = emailIdEditText.getText().toString();


            jsonObject.put("email",emailId);
            jsonObject.put("name", name);
            jsonObject.put("url", cUrl);
            jsonObject.put("street", companyContact);
            jsonObject.put("city", city);
            jsonObject.put("state", state);
            jsonObject.put("country", country);
            jsonObject.put("zipcode", zipcode);
            jsonObject.put("company_contact", companyContact);
            jsonObject.put("added_by", "1");
            jsonObject.put("role",roleBit);

        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,
                "http://codehex.officebuy.in/company/add_company/", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                    responsecompanyId  = response.getString("company_id");
                    responseName =response.getString("name");

                    SharedPreferences userDetailPrefs = getSharedPreferences("company_detail", MODE_PRIVATE);
                    SharedPreferences.Editor userDetailPrefsEditor = userDetailPrefs.edit();
                    userDetailPrefsEditor.putString("company_id",responsecompanyId).apply();
                    userDetailPrefsEditor.putString("name",responseName).apply();
                    userDetailPrefsEditor.commit();


                    Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                    startActivity(i);

                }
                catch(Exception e){

                    e.printStackTrace();}
                hidepDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


}
