package com.example.user.finalf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class LoginActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    String status="";

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        String session_name = sharedpreferences.getString("namekey", null);
        Toast.makeText(getApplicationContext(),session_name, Toast.LENGTH_LONG).show();

        if (session_name != null) {
            Toast.makeText(getBaseContext(), "Session Found - Call Home :"+session_name, Toast.LENGTH_LONG).show();
            // the Session Value Found - Call Home Screen
            Intent in=new Intent(this,Bluetooth.class);
            startActivity(in);
        }

            // the Session Value Found - Call Login Screen
       
        _emailText=(EditText)findViewById(R.id.input_email);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _loginButton=(Button)findViewById(R.id.btn_login);
        _signupLink=(TextView)findViewById(R.id.link_signup);



        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");


        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        Toast.makeText(getApplicationContext(),email+" "+password, Toast.LENGTH_LONG).show();
        String urlString = "http://www.ezeelearn.com/checkuser.php?username="+email+"&password="+password;
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
        //check status value from webserverdb


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if(status.equalsIgnoreCase("true"))
                        {

                            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            String email=_emailText.getText().toString();
                            String pass=_passwordText.getText().toString();
                            Toast.makeText(getBaseContext(), " Login Success", Toast.LENGTH_LONG).show();
                            editor.putString("namekey", email);
                            editor.putString("passkey", pass);
                            editor.commit();

                            Intent i = new Intent(getApplicationContext(), Bluetooth.class);
                            startActivity(i);





                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), " check username and password", Toast.LENGTH_LONG).show();
                            onLoginFailed();
                        }

                        // On complete call either onLoginSuccess or onLoginFailed

                        //
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
               // this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        //finish();

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
