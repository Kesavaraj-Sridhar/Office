package in.codehex.office;

import android.app.ProgressDialog;
import android.content.Context;
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

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText regEmailEditText, regFirstNameEditText, regLastNameEditText;
    Spinner regRoleSpinner;
    private static String regEmail, regFirstName, regLastName, regRoleId, regCompanyId, responseEmail, toastMessage;
    private ProgressDialog pDialog;
    ImageButton regSubmitImageButton;
    private static String TAG = RegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/latoregular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_registration);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        regEmailEditText = (EditText) findViewById(R.id.reg_email_edit_text);
        regFirstNameEditText = (EditText) findViewById(R.id.reg_first_name_edit_text);
        regLastNameEditText = (EditText) findViewById(R.id.reg_last_name_edit_text);
        regRoleSpinner = (Spinner) findViewById(R.id.reg_role_spinner);
        regRoleSpinner.setOnItemSelectedListener(this);
        regSubmitImageButton = (ImageButton) findViewById(R.id.reg_submit_image_button);
        regSubmitImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regEmailEditText.getText().toString().equals("")||
                        regFirstNameEditText.getText().toString().equals("")||
                        regLastNameEditText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"All fields are mandatory!!!",Toast.LENGTH_LONG).show();
                }else{
                    uploadRegDetails();
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
            regRoleId="2";
        }
        if(parent.getItemAtPosition(position).toString().equals("Seller")){
            regRoleId="3";
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



    private void uploadRegDetails() {
        showpDialog();
        JSONObject jsonObject=new JSONObject();
        try {
            regFirstName = regFirstNameEditText.getText().toString();
            regLastName = regLastNameEditText.getText().toString();
            regEmail = regEmailEditText.getText().toString();
            final SharedPreferences datapref = getApplicationContext().getSharedPreferences("company_detail", MODE_PRIVATE);
            regCompanyId = datapref.getString("company_id", null);

            jsonObject.put("email",regEmail);
            jsonObject.put("username", regEmail);
            jsonObject.put("company_id", regCompanyId);
            jsonObject.put("role_id", regRoleId);
            jsonObject.put("first_name", regFirstName);
            jsonObject.put("last_name", regLastName);

        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,
                "http://codehex.officebuy.in/auth/register/", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    responseEmail = response.getString("email");
                    toastMessage = responseEmail.concat(" is registered Successfully!!");


                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();

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
