package com.prakruthi.ui.ui.profile;

import static android.content.ContentValues.TAG;

import static com.prakruthi.ui.Variables.city;
import static com.prakruthi.ui.Variables.district;
import static com.prakruthi.ui.Variables.email;
import static com.prakruthi.ui.Variables.name;
import static com.prakruthi.ui.Variables.token;
import static com.prakruthi.ui.Variables.userId;
import static com.prakruthi.ui.ui.UserDetails.state;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.prakruthi.R;
import com.prakruthi.databinding.FragmentEditProfileHttpURLConnectionBinding;
import com.prakruthi.ui.APIs.UserDetailsUpdateApi;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class EditProfileFragmentHttpURLConnection extends BottomSheetDialogFragment implements UserDetailsUpdateApi.OnUserDetailsUpdateApiGivesFetchedListner {

    int view = R.layout.fragment_edit_profile_http_u_r_l_connection;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    EditText et_first_name_http, et_email_http, et_city_http;

    PowerSpinnerView spinner_city, spinner_state_http, spinner_district_http;
    int stateId = 0;

    Button btn_save_http;
    AppCompatButton sendotp, backbtn_http, ivBack;
    SharedPreferences sharedPreferences;

    ArrayList<String> districtNames = new ArrayList<>();

    ImageView iv_close_http, iv_back;
    ShapeableImageView iv_profile;

    private Uri imageUri;
    private String selectedPath = " ";

    CheckBox RememberMe;

    private DismissListener dismissListener;


    ProfileGetUserDataResponse.ProfileGetUserDataModel profileGetUserDataModel_http;
    EditProfileUpdateDrailsUpdateModels editProfileUpdateDrailsUpdateModels_http;

    private FragmentEditProfileHttpURLConnectionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile_http_u_r_l_connection, container, false);

        profileGetUserDataModel_http = new Gson().fromJson(getArguments().getString("data"), ProfileGetUserDataResponse.ProfileGetUserDataModel.class);

        iv_close_http = view.findViewById(R.id.iv_close_http);
        backbtn_http = view.findViewById(R.id.backbtn_http);

        et_first_name_http = view.findViewById(R.id.et_first_name_http);
        et_email_http = view.findViewById(R.id.et_email_http);
        et_city_http = view.findViewById(R.id.et_city_http);
        spinner_state_http = view.findViewById(R.id.spinner_state_http);
        spinner_district_http = view.findViewById(R.id.spinner_district_http);

        btn_save_http = view.findViewById(R.id.btn_save_http);

        setdata();
        // Getting DropDown Arrays
        getDropDownData();

        spinner_state_http.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            stateId = newIndex + 1;
            getDropDownData();
            spinner_state_http.setError(null);
        });

        spinner_district_http.setOnClickListener(v -> {
            if (stateId == 0) {
                spinner_state_http.setError("Select State");
            } else spinner_district_http.showOrDismiss();
        });

        getFCMToken();

        // Access the root view of the fragment
        View root = view.getRootView();

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // check if the touch is outside of the state view
                    int[] location = new int[2];
                    spinner_state_http.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = spinner_state_http.getWidth();
                    int height = spinner_state_http.getHeight();
                    if (!(event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height)) {

                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });


        iv_close_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        //HttpURLConnection:---
        btn_save_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinner_state_http.setError(null);
                spinner_district_http.setError(null);

                if (userId == null) {
                    Toast.makeText(requireContext(), "Internal Error", Toast.LENGTH_SHORT).show();
                    return;

                }

                else if (token == null || token.isEmpty()) {
                    Toast.makeText(requireContext(), "Internal Error", Toast.LENGTH_SHORT).show();
                    return;
                }


                else if (et_first_name_http.getText().toString().trim().isEmpty()) {
                    et_first_name_http.setError("Full name is required");
                    ObjectAnimator.ofFloat(et_first_name_http, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                    et_first_name_http.requestFocus();
                    return;
                }

                else if (et_email_http.getText().toString().trim().isEmpty()) {
                    et_email_http.setError("Email is required");
                    ObjectAnimator.ofFloat(et_email_http, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                    et_email_http.requestFocus();
                    return;
                }


                else if (et_city_http.getText().toString().trim().isEmpty()) {
                    et_city_http.setError("City is required");
                    ObjectAnimator.ofFloat(et_city_http, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                    et_city_http.requestFocus();
                    return;
                }

                else if (spinner_state_http.getText().toString().isEmpty()) {
                    spinner_state_http.setError("State is required");
                    ObjectAnimator.ofFloat(spinner_state_http, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                    spinner_state_http.requestFocus();
                    return;

                } else if (spinner_district_http.getText().toString().isEmpty()) {
                    spinner_district_http.setError("District is required");
                    ObjectAnimator.ofFloat(spinner_district_http, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                    spinner_district_http.requestFocus();
                    return;
                }


                else {
                    String fullnameStr = et_first_name_http.getText().toString().trim();
                    String emailStr = et_email_http.getText().toString().trim();
                    String cityStr = String.valueOf(et_city_http.getText().toString().trim());
                    String stateStr = String.valueOf(spinner_state_http.getSelectedIndex() + 1);
                    String districtStr = String.valueOf(spinner_district_http.getSelectedIndex() + 1);

                    new ApiTask1Http().execute(userId, token, fullnameStr, emailStr, cityStr, stateStr, districtStr);


                }
            }
        });


        return view;


    }

    public void getFCMToken() {
        FirebaseApp.initializeApp(requireContext());
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


    //HttpURLConnection:-----
    public void getUserData(JSONObject ResultJson) {
        try {
//            String token = ResultJson.getString("token");
            String status_code = ResultJson.getString("status_code");
            String privacyPolicy = ResultJson.getString("privacyPolicy");
            String termsAndConditions = ResultJson.getString("termsAndConditions");
            String returnRefundPolicy = ResultJson.getString("returnRefundPolicy");
            String security = ResultJson.getString("security");
            String aboutUs = ResultJson.getString("aboutUs");
            String message = ResultJson.getString("message");

//            JSONArray userDetailsArray = ResultJson.getJSONArray("user_details");
            JSONArray userDetailsArray = ResultJson.getJSONArray("data");


            JSONObject userDetails = userDetailsArray.getJSONObject(0);

            String id = String.valueOf(userDetails.getInt("id"));
            String departmentId = String.valueOf(userDetails.getInt("department_id"));
            String userCode = userDetails.getString("user_code");
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

            Variables.status_code = status_code;
            Variables.privacyPolicy = privacyPolicy;
            Variables.termsAndConditions = termsAndConditions;
            Variables.returnRefundPolicy = returnRefundPolicy;
            Variables.security = security;
            Variables.aboutUs = aboutUs;
            Variables.message = message;


            Variables.id = Integer.valueOf(String.valueOf(Integer.valueOf(id)));
            Variables.departmentId = Integer.valueOf(String.valueOf(Integer.valueOf(departmentId)));

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


            btn_save_http.setVisibility(View.VISIBLE);
            Loading.hide();

            startActivity(new Intent(requireContext(), EditProfileFragmentHttpURLConnection.class));
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            Toast.makeText(requireContext(), "Internal Error", Toast.LENGTH_SHORT).show();
            btn_save_http.setVisibility(View.VISIBLE);
        }

    }

    //HttpURLConnection
    @SuppressLint("StaticFieldLeak")
    public void getDropDownData() {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                FetchData fetchData = new FetchData("https://houseofspiritshyd.in/prakruthi/admin/api/getDropdownData");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        String result = fetchData.getResult();
                        Log.i("FetchData", result);
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            return jsonObj;
                        } catch (JSONException e) {
                            Log.e(TAG, e.toString());
                            return null;
//                            return "";
                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObj) {
                super.onPostExecute(jsonObj);

                if (jsonObj != null) {
                    try {

                        JSONArray states = jsonObj.getJSONArray("state");
                        ArrayList<String> stateNames = new ArrayList<>();
                        for (int i = 0; i < states.length(); i++) {
                            JSONObject state = states.getJSONObject(i);
                            stateNames.add(state.getString("name"));
                        }
                        spinner_state_http.setItems(stateNames);


                        JSONArray districts = jsonObj.getJSONArray("district");
                        districtNames.clear();

                        for (int i = 0; i < districts.length(); i++) {
                            JSONObject districtt = districts.getJSONObject(i);
                            if (districtt.getInt("state_id") == stateId) {

                                districtNames.add(districtt.getString("name"));
                                spinner_district_http.setItems(districtNames);
                            }
                        }



                    } catch (JSONException e) {
                        Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    @Override
    public void OnUserDetailsUpdateApiGivesFetched(String message) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();


            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();


            PopupDialog.getInstance(requireContext())
                    .setStyle(Styles.SUCCESS)
                    .setHeading("Well Done")
                    .setDescription("Successfully" +
                            " Edit Profile")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
            Loading.hide();
        });

    }



    public int boolToInt(boolean value) {
        return value ? 1 : 0;
    }


    @Override
    public void OnUserDetailsUpdateApiGivesError(String error) {
        requireActivity().runOnUiThread(() -> {
            requireActivity().runOnUiThread(() ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show());
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();

            PopupDialog.getInstance(requireContext())
                    .setStyle(Styles.FAILED)
                    .setHeading("Uh-Oh")
                    .setDescription("Unexpected error occurred." +
                            " Try again later.")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
        });

    }


    @SuppressLint("StaticFieldLeak")
//    private class ApiTask1 extends AsyncTask<String, Void, String> {
    private class ApiTask1Http extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //Creating array for parameters

            //------------------------------

            String[] field = new String[7];
            field[0] = "user_id";
            field[1] = "token";

            field[2] = "name";
            field[3] = "email";
            field[4] = "city";
            field[5] = "state";
            field[6] = "district";
            //Creating array for data
            String[] data = new String[7];
            data[0] = params[0];
            data[1] = params[1];
            data[2] = params[2];
            data[3] = params[3];
            data[4] = params[4];
            data[5] = params[5];
            data[6] = params[6];


            Log.e(TAG, Arrays.toString(data));
            PutData putData = new PutData(Variables.BaseUrl + "userDetailsUpdate", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    // result = Api Result
                    Log.e(TAG, putData.getResult());
                    return putData.getResult();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result != null) {
                try {
                    Log.e(TAG, result);
                    JSONObject json = new JSONObject(result);
                    boolean statusCode = json.getBoolean("status_code");
                    String message = json.getString("message");

                    if (statusCode) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        getUserData(json);
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        btn_save_http.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    btn_save_http.setVisibility(View.VISIBLE);

                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                btn_save_http.setVisibility(View.VISIBLE);

                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setdata() {

        try {

            et_first_name_http.setText((CharSequence) profileGetUserDataModel_http.getName());

            et_email_http.setText((CharSequence) profileGetUserDataModel_http.getEmail());

            et_city_http.setText((CharSequence) profileGetUserDataModel_http.getCity());

            spinner_state_http.setText((CharSequence) profileGetUserDataModel_http.getState());

            spinner_district_http.setText((CharSequence) profileGetUserDataModel_http.getDistrict());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAllFieldsValidated() {

        if (TextUtils.isEmpty(et_first_name_http.getText().toString().trim())) {
            et_first_name_http.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(et_email_http.getText().toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(et_email_http.getText().toString()).matches()) {
            et_email_http.setError("Not Valid");
            return false;
        }

        if (TextUtils.isEmpty(et_city_http.getText().toString().trim())) {
            et_city_http.setError("Not Valid");
            return false;
        }

        if (spinner_state_http.getText().toString().isEmpty()) {
            spinner_state_http.setError("State is required");
            ObjectAnimator.ofFloat(spinner_state_http, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            spinner_state_http.requestFocus();
            return false;

        }
        if (spinner_district_http.getText().toString().isEmpty()) {
            spinner_district_http.setError("District is required");
            ObjectAnimator.ofFloat(spinner_district_http, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            spinner_district_http.requestFocus();
            return false;
        }

        return true;
    }

    //Retrofit:---
    public void updateDetails(ProfileGetUserDataResponse.ProfileGetUserDataModel dat) {

        this.profileGetUserDataModel_http = dat;

        et_first_name_http.setText((CharSequence) profileGetUserDataModel_http.getName());
        et_email_http.setText((CharSequence) profileGetUserDataModel_http.getEmail());
        et_city_http.setText((CharSequence) profileGetUserDataModel_http.getCity());

        spinner_state_http.setText((CharSequence) profileGetUserDataModel_http.getState());

        spinner_district_http.setText((CharSequence) profileGetUserDataModel_http.getDistrict());
    }

    public void setDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public interface DismissListener {
        public void onDismiss();
    }


}