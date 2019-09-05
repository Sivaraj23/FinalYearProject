package com.example.user.finalf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    String status="";

    EditText _aadharText;
    EditText _nameText;
    EditText _vehiText;
    EditText _emailText;
    EditText _mobileText;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    Button _signupButton;
    TextView _loginLink;




    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _aadharText=(EditText)findViewById(R.id.edit1);
        _nameText=(EditText)findViewById(R.id.input_name);
        _vehiText=(EditText)findViewById(R.id.input_vehi);
        _emailText=(EditText)findViewById(R.id.input_email);
        _mobileText=(EditText)findViewById(R.id.input_mobile);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _reEnterPasswordText=(EditText)findViewById(R.id.input_reEnterPassword);
        _signupButton=(Button) findViewById(R.id.btn_signup);
        _loginLink=(TextView) findViewById(R.id.link_login);
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String vehi = _vehiText.getText().toString();
        String name = _nameText.getText().toString();
        String aadhar = _aadharText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.
        Toast.makeText(getApplicationContext(),email+" "+password, Toast.LENGTH_LONG).show();
        String urlString = "http://www.ezeelearn.com/checkusers.php?vehicle1="+vehi+"&name="+name+"&num="+aadhar;
        // pass username and password to checkuser.php code
        // and wait for response from the webserver ie response send by the login.php code
        // read response using JSONObject reader
        try{
            HTTPDataHandler hh = new HTTPDataHandler(getBaseContext());
            String stream = hh.GetHTTPData(urlString);


            if(stream !=null){
                try{
                    // Get the full HTTP Data as JSONObject
                    JSONObject reader= new JSONObject(stream);

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray = reader.optJSONArray("login");

                    //Iterate the jsonArray and print the info of JSONObjects

                    if(jsonArray.length()>0) // check for data from remote server
                    {
                        for(int i=0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            status = jsonObject.optString("status").toString();

                        }
                    }
                    else
                    {
                        status="Record Not Found";
                        Toast.makeText(getBaseContext(), status, Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(getBaseContext(), status, Toast.LENGTH_LONG).show();

                }catch(JSONException e){
                    Toast.makeText(getBaseContext(), "JSONError"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } // if statement end

        }catch(Exception e)
        {
            Toast.makeText(getBaseContext(), "Error2="+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        _signupButton.setEnabled(true);
        String url="http://ezeelearn.com/storeuser.php?username="+email+"&password="+password;

        HTTPDataHandler hh = new HTTPDataHandler(this);
        String stream = hh.GetHTTPData(url);
        String status="";
        try{

            JSONObject reader= new JSONObject(stream);
            JSONArray jsonArray = reader.optJSONArray("store");

            if(jsonArray.length()>0) // check for data from remote server
            {
                for(int i=0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    status = jsonObject.optString("status").toString();
                }
            }
            Toast.makeText(getBaseContext(), "Sever Response :"+status , Toast.LENGTH_LONG).show();
        }catch(JSONException ee)
        {
            Toast.makeText(getBaseContext(), "Error GetResponse "+ee.getMessage() , Toast.LENGTH_LONG).show();
        }
        //tv1.setText(status); // display server response


        Toast.makeText(getBaseContext(), "Signup sucess", Toast.LENGTH_LONG).show();


        Intent i=new Intent (getApplicationContext(),LoginActivity.class);
        startActivity(i);
        //finish();
        setResult(RESULT_OK, null);
    }



    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String vehi = _vehiText.getText().toString();
        String name = _nameText.getText().toString();
        String aadhar = _aadharText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (aadhar.isEmpty()) {
            _aadharText.setError("Enter Valid Address");
            valid = false;
        } else {
            _aadharText.setError(null);
        }

        if (vehi.isEmpty()) {
            _vehiText.setError("Enter Valid Address");
            valid = false;
        } else {
            _vehiText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}