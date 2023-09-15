package com.prakruthi.ui;
import static android.content.ContentValues.TAG;

import static com.prakruthi.ui.utils.Constants.CART;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.prakruthi.R;
import com.prakruthi.ui.misc.Loading;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Login extends AppCompatActivity {
    TextView register,forget_password;
    EditText username,password;
    AppCompatButton login;
    RelativeLayout LoginLayout;

    CheckBox RememberMe;


    // Get SharedPreferences object
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.register_an_account_login);
        forget_password = findViewById(R.id.forget_password_login);
        username = findViewById(R.id.edittext_user_name);
        password = findViewById(R.id.edittext_login_password);
        RememberMe = findViewById(R.id.RememberMe);
        login = findViewById(R.id.login_btn);

        LoginLayout = findViewById(R.id.LoginLayout);


        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        if (isRemembered())
        {
            String username = sharedPreferences.getString("username","");
            String password = sharedPreferences.getString("password","");

            LoginLayout.setVisibility(View.GONE);
            Loading.show(this);

            Api(username,password);
            return;
        }

        forget_password.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, ForgetPassword.class));
        });

        login.setOnClickListener(view -> {
            if (username.getText().toString().isEmpty())
            {
                username.setError("Username is Required");
            }
            else if (password.getText().toString().isEmpty())
            {
                password.setError("Password is Required");
            }
            if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
            {
                Api(username.getText().toString(),password.getText().toString());
            }
        });

        register.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, RegistrationForm.class));
        });

    }
    @SuppressLint("StaticFieldLeak")
    public void Api(String usernameString, String passwordString)
    {
        Loading.show(Login.this);
        login.setVisibility(View.INVISIBLE);
        // Execute the AsyncTask
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "user_name";
                field[1] = "password";
                //Creating array for data
                String[] data = new String[2];
                data[0] = usernameString;
                data[1] = passwordString;
                PutData putData = new PutData(Variables.BaseUrl+"login", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        // result = Api Result
                        String result = putData.getResult();
                        return result;
                    }
                }
                return null;
            }
            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    try {
                        Log.e(TAG, result );
                        JSONObject json = new JSONObject(result);
                        boolean statusCode = json.getBoolean("status_code");
                        String message = json.getString("message");
                        if (statusCode)
                        {
                            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                            getUserData(json);
                        }
                        else
                        {
                            Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                            username.setError("Invalid");
                            password.setError("Invalid");
                            ObjectAnimator.ofFloat(username, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                            username.requestFocus();
                            ObjectAnimator.ofFloat(password, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                            password.requestFocus();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(Login.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
                login.setVisibility(View.VISIBLE);
                Loading.hide();
            }
        }.execute();
    }
    public void getUserData(JSONObject ResultJson)
    {
        try {
            String token = ResultJson.getString("token");
            JSONArray userDetailsArray = ResultJson.getJSONArray("user_details");
            JSONObject userDetails = userDetailsArray.getJSONObject(0);
            int id = userDetails.getInt("id");
            int departmentId = userDetails.getInt("department_id");
            String userCode = userDetails.optString("user_code", "");
            String name = userDetails.optString("name", "");
            String lastName = userDetails.optString("last_name", "");
            String email = userDetails.optString("email", "");
            String password = userDetails.optString("password", "");
            String mobile = userDetails.optString("mobile", "");
            String gender = userDetails.optString("gender", "");
            String dob = userDetails.optString("dob", "");
            String attachment = userDetails.optString("attachment", "");
            String city = userDetails.optString("city", "");
            String postCode = userDetails.optString("postCode", "");
            String address = userDetails.optString("address", "");
            String state = userDetails.optString("state", "");
            String country = userDetails.optString("country", "");
            String district = userDetails.optString("district", "");
            String street = userDetails.optString("street", "");
            String about = userDetails.optString("about", "");
            String status = userDetails.optString("status", "");
            String mobileVerified = userDetails.optString("mobile_verified", "");
            String isVerified = userDetails.optString("is_verified", "");
            String logDateCreated = userDetails.optString("log_date_created", "");
            String createdBy = userDetails.optString("created_by", "");
            String logDateModified = userDetails.optString("log_date_modified", "");
            String modifiedBy = userDetails.optString("modified_by", "");
            String fcmToken = userDetails.optString("fcm_token", "");
            String deviceId = userDetails.optString("device_id", "");
            String allowEmail = userDetails.optString("allow_email", "");
            String allowSms = userDetails.optString("allow_sms", "");
            String allowPush = userDetails.optString("allow_push", "");

            // Store values in static variables
            Variables.clear();
            Variables.token = token;
            Variables.id = id;
            Variables.departmentId = departmentId;
            Variables.userCode = userCode;
            Variables.name = name;
            Variables.lastName = lastName;
            Variables.email = email;
            Variables.password = password;
            Variables.mobile = mobile;
            Variables.gender = gender;
            Variables.dob = dob;
            Variables.attachment = attachment;
            Variables.city = city;
            Variables.postCode = postCode;
            Variables.address = address;
            Variables.state = state;
            Variables.country = country;
            Variables.district = district;
            Variables.street = street;
            Variables.about = about;
            Variables.status = status;
            Variables.mobileVerified = mobileVerified;
            Variables.isVerified = isVerified;
            Variables.logDateCreated = logDateCreated;
            Variables.createdBy = createdBy;
            Variables.logDateModified = logDateModified;
            Variables.modifiedBy = modifiedBy;
            Variables.fcmToken = fcmToken;
            Variables.deviceId = deviceId;
            Variables.allowEmail = allowEmail;
            Variables.allowSms = allowSms;
            Variables.allowPush = allowPush;

//            Log.e(TAG, Variables.id+Variables.token );

            login.setVisibility(View.VISIBLE);
            Loading.hide();
            if (RememberMe.isChecked())
                rememberMe();
            CART="0";
            startActivity(new Intent(Login.this, HomeActivity.class).putExtra("cart","0"));
            finish();
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString() );
            Toast.makeText(this, "Internal Error", Toast.LENGTH_SHORT).show();
            login.setVisibility(View.VISIBLE);
            LoginLayout.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onBackPressed() {
    }
    public void rememberMe()
    {
        // Get SharedPreferences.Editor object
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Set String value
        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.putBoolean("rememberMe",true);
        // Apply changes
        editor.apply();
    }
    public boolean isRemembered()
    {
        return sharedPreferences.getBoolean("rememberMe",false);
    }

}