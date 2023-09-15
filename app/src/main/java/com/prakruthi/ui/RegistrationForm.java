package com.prakruthi.ui;

import static android.content.ContentValues.TAG;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prakruthi.R;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RegistrationForm extends AppCompatActivity {

    EditText fullname,phone_number,email,password,city;

    PowerSpinnerView state, district, type;


    int stateId = 0;

    CheckBox terms;

    AppCompatButton sendotp, backbtn;
    ArrayList<String> districtNames = new ArrayList<>();
    int value =0;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_from);

        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);

        fullname = findViewById(R.id.edittext_full_name);
        phone_number = findViewById(R.id.edittext_phone_number);
        email = findViewById(R.id.edittext_email_address);
        password = findViewById(R.id.edittext_pass_word);
        state = findViewById(R.id.State_DropDown);
        district = findViewById(R.id.District_DropDown);
        city = findViewById(R.id.Edittext_City);
        type = findViewById(R.id.Type_DropDown);
        terms = findViewById(R.id.checkbox);
        sendotp = findViewById(R.id.send_OTP_btn);
        backbtn = findViewById(R.id.registration_back_btn);

        // Getting DropDown Arrays
        getDropDownData();

        state.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            stateId = newIndex+1;
            getDropDownData();
            district.setText("District");

            state.setError(null);
        });

        district.setOnClickListener(v -> {
            if (stateId == 0)
            {
                state.setError("Select State");
            }
            else district.showOrDismiss();
        });

        getFCMToken();



        // set an OnTouchListener to the root view
        View root = findViewById(android.R.id.content);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // check if the touch is outside of the state view
                    int[] location = new int[2];
                    state.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = state.getWidth();
                    int height = state.getHeight();
                    if (!(event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height)) {
                        // dismiss the state view
                        state.dismiss();
                        district.dismiss();
                        type.dismiss();
                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });

        backbtn.setOnClickListener(view -> {
            super.onBackPressed();
        });

        sendotp.setOnClickListener(view -> {
            state.setError(null);
            district.setError(null);
            type.setError(null);
            if (fullname.getText().toString().trim().isEmpty()) {
                fullname.setError("Full name is required");
                ObjectAnimator.ofFloat(fullname, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                fullname.requestFocus();
                return;
            } else if (phone_number.getText().toString().trim().isEmpty() || phone_number.getText().length() < 10) {
                phone_number.setError("Phone number is required");
                ObjectAnimator.ofFloat(phone_number, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                phone_number.requestFocus();
                return;
            } else if (email.getText().toString().trim().isEmpty()) {
                email.setError("Email is required");
                ObjectAnimator.ofFloat(email, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                email.requestFocus();
                return;
            } else if (password.getText().toString().trim().isEmpty()) {
                password.setError("Password is required");
                ObjectAnimator.ofFloat(password, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                password.requestFocus();
                return;
            } else if (city.getText().toString().trim().isEmpty()) {
                city.setError("City is required");
                ObjectAnimator.ofFloat(city, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                city.requestFocus();
                return;
            } else if (state.getText().toString().isEmpty()) {
                state.setError("State is required");
                ObjectAnimator.ofFloat(state, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                state.requestFocus();
                return;
            } else if (district.getText().toString().isEmpty()) {
                district.setError("District is required");
                ObjectAnimator.ofFloat(district, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                district.requestFocus();
                return;
            } else if (type.getText().toString().isEmpty()) {
                type.setError("Type is required");
                ObjectAnimator.ofFloat(type, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                type.requestFocus();
                return;
            } else if (Variables.fcmToken == null || Variables.fcmToken.isEmpty()) {
                Toast.makeText(this,"Internal Error", Toast.LENGTH_SHORT).show();
                return;
            } else if (!terms.isChecked()) {
                Toast.makeText(this, "Please Accept The Terms And Conditions", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                String fullnameStr = fullname.getText().toString().trim();
                String phoneStr = phone_number.getText().toString().trim();
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
//                String cityStr = city.getText().toString().trim();
                String cityStr = String.valueOf(city.getText().toString().trim());
                String stateStr = String.valueOf(state.getSelectedIndex()+1);
                String districtStr = String.valueOf(district.getSelectedIndex()+1);
                String typeStr = String.valueOf(GetDepartmentId(type.getSelectedIndex()));
               new ApiTask().execute(fullnameStr,phoneStr,emailStr,passwordStr,cityStr,typeStr,stateStr,districtStr,Variables.fcmToken);
            }
        });
    }
    public void getUserData(JSONObject ResultJson)
    {
        try {
            JSONArray resultArray = ResultJson.getJSONArray("result");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject resultObject = resultArray.getJSONObject(i);
                int userId = resultObject.getInt("user_id");
                String userMobile = resultObject.getString("user_mobile");
                String apiToken = resultObject.getString("api_token");
            }
            sendotp.setVisibility(View.VISIBLE);
            Variables.phoneNumber = phone_number.getText().toString();
            Intent intent=new Intent(RegistrationForm.this,OTP_Verification.class);
            intent.putExtra("fullname",phone_number.getText().toString().trim());
            intent.putExtra("password",password.getText().toString().trim());
            startActivity(intent);
        }
        catch (JSONException e) {
            Log.e(TAG, e.toString() );
            Toast.makeText(this, "System Error", Toast.LENGTH_SHORT).show();
            sendotp.setVisibility(View.VISIBLE);
        }

    }
    @SuppressLint("StaticFieldLeak")
    public void getDropDownData() {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                FetchData fetchData = new FetchData("https://prakruthiepl.com/admin/api/getDropdownData");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        String result = fetchData.getResult();
                        Log.i("FetchData", result);
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            return jsonObj;
                        } catch (JSONException e) {
                            Log.e(TAG, e.toString() );
                            return null;
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObj) {
                if (jsonObj != null) {
                    try {
                        JSONArray departments = jsonObj.getJSONArray("departments");
                        ArrayList<String> departmentNames = new ArrayList<>();
                        for(int i = 0; i < departments.length(); i++) {
                            JSONObject department = departments.getJSONObject(i);
                            departmentNames.add(department.getString("name"));
                        }
                        type.setItems(departmentNames);

                        JSONArray states = jsonObj.getJSONArray("state");
                        ArrayList<String> stateNames = new ArrayList<>();
                        for(int i = 0; i < states.length(); i++) {
                            JSONObject state = states.getJSONObject(i);
                            stateNames.add(state.getString("name"));
                        }
                        state.setItems(stateNames);


                        JSONArray districts = jsonObj.getJSONArray("district");
                        districtNames.clear();
                        district.setText("District");
                        //old spinner
                        //ArrayList<String> districtNames = new ArrayList<>();
//                        for(int i = 0; i < districts.length(); i++) {
//                            JSONObject district = districts.getJSONObject(i);
//                            districtNames.add(district.getString("name"));
//                        }
//                        district.setItems(districtNames);

                        for(int i = 0; i < districts.length(); i++) {
                            JSONObject districtt = districts.getJSONObject(i);
                            if (districtt.getInt("state_id") == stateId)
                            {
                                districtNames.add(districtt.getString("name"));
                                district.setItems(districtNames);
                            }
                        }

                    } catch (JSONException e) {
                        Log.e(TAG, e.toString() );
                        Toast.makeText(RegistrationForm.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegistrationForm.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

    }

    public int GetDepartmentId(int DepartmentName)
    {

        if (DepartmentName==0)
            value= 2;
        if (DepartmentName==1)
            value= 3;
        if (DepartmentName==2)
            value= 4;
        return value;
    }
    public void getFCMToken() {
        FirebaseApp.initializeApp(RegistrationForm.this);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("firebase", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("firebase", "token" + token);
                        Variables.fcmToken = token;
                    }
                });
    }
    private class ApiTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //Creating array for parameters
            String[] field = new String[9];
            field[0] = "name";
            field[1] = "mobile";
            field[2] = "email";
            field[3] = "password";
            field[4] = "city";
            field[5] = "type";
            field[6] = "state";
            field[7] = "district";
            field[8] = "fcm_token";
            //Creating array for data
            String[] data = new String[9];
            data[0] = params[0];
            data[1] = params[1];
            data[2] = params[2];
            data[3] = params[3];
            data[4] = params[4];
            data[5] = params[5];
            data[6] = params[6];
            data[7] = params[7];
            data[8] = params[8];

            Log.e(TAG, Arrays.toString(data));
            PutData putData = new PutData(Variables.BaseUrl + "registration", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    // result = Api Result
                    Log.e(TAG, putData.getResult() );
                    return putData.getResult();
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

                    if (statusCode) {
                        Toast.makeText(RegistrationForm.this, message, Toast.LENGTH_SHORT).show();
                        getUserData(json);
                    } else {
                        Toast.makeText(RegistrationForm.this, message, Toast.LENGTH_SHORT).show();
                        sendotp.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    sendotp.setVisibility(View.VISIBLE);
                    Toast.makeText(RegistrationForm.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                sendotp.setVisibility(View.VISIBLE);
                Toast.makeText(RegistrationForm.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
