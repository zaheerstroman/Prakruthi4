package com.prakruthi.ui.ui.profile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.prakruthi.R;
import com.prakruthi.databinding.FragmentEditProfile2Binding;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.network.APIClient;
import com.prakruthi.ui.network.ApiInterface;
import com.prakruthi.ui.utils.CommonUtils;
import com.prakruthi.ui.utils.Constants;
import com.prakruthi.ui.utils.SharedPrefs;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileFragment2 extends BottomSheetDialogFragment {
    int view = R.layout.fragment_edit_profile2;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    EditText et_first_name, et_email, et_city;
    public PowerSpinnerView spinner_state, spinner_district;

    int stateId = 0;
    ImageView iv_close;
    AppCompatButton sendotp, backbtn;

    SharedPreferences sharedPreferences;
    Button btn_save;

    ArrayList<String> districtNames = new ArrayList<>();

    //Use for Get Method only

    CheckBox RememberMe;

    ProfileGetUserDataResponseRetrofit.ProfileGetUserDataModelRetrofit data;
    EditProfileUpdateDrailsUpdateModels editProfileUpdateDrailsUpdateModels;

    private DismissListener dismissListener;

    private Uri imageUri;
    private String selectedPath = " ";


    private FragmentEditProfile2Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile2, container, false);


        data = new Gson().fromJson(getArguments().getString("data"), ProfileGetUserDataResponseRetrofit.ProfileGetUserDataModelRetrofit.class);

        init(view);

        //POST Method
        fetchStates();
        fetchDistrict();
        //GET Method
//        getDropDownData();

        spinner_state = view.findViewById(R.id.spinner_state);

        spinner_state.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            stateId = newIndex + 1;

//            getDropDownData();
            fetchStates();

            spinner_state.setError(null);
        });

        //--------------------------------------------------

        spinner_district = view.findViewById(R.id.spinner_district);

        spinner_district.setOnClickListener(v -> {
            if (stateId == 0) {
                spinner_state.setError("Select State");

                fetchDistrict();

            } else spinner_district.showOrDismiss();
        });


        //-------------------------------------------------------------


        //----------------------------------------------------------------


        getFCMToken();

        //Fragment
        View root = view.getRootView();
//        Activity
//        View root = view.findViewById(android.R.id.content);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // check if the touch is outside of the state view
                    int[] location = new int[2];
                    spinner_state.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = spinner_state.getWidth();
                    int height = spinner_state.getHeight();
                    if (!(event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height)) {
                        // dismiss the state view
                        spinner_state.dismiss();
                        spinner_district.dismiss();
//                        type.dismiss();
                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });



        return view;
    }



    private void init(View view) {
        iv_close = view.findViewById(R.id.iv_close);
        backbtn = view.findViewById(R.id.backbtn);

        et_first_name = view.findViewById(R.id.et_first_name);
        et_email = view.findViewById(R.id.et_email);
        et_city = view.findViewById(R.id.et_city);
        spinner_state = view.findViewById(R.id.spinner_state);
        spinner_district = view.findViewById(R.id.spinner_district);

        btn_save = view.findViewById(R.id.btn_save);

        //Retrofit:---
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllFieldsValidated())

                    updateProfileDetails();
//                    getDropDownData();

            }
        });

        setdata();


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }


    private void setdata() {

        try {

            et_first_name.setText((CharSequence) data.getName());

            et_email.setText((CharSequence) data.getEmail());

            et_city.setText((CharSequence) data.getCity());

            spinner_state.setText((CharSequence) data.getState());

            spinner_district.setText((CharSequence) data.getDistrict());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Retrofit:-----
    private void updateProfileDetails() {

        CommonUtils.showLoading(requireContext(), "Please Wait", false);

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);


         if (Variables.userId == null) {
            Toast.makeText(requireContext(), "Internal Error", Toast.LENGTH_SHORT).show();
            return;

        }

         else if (Variables.token == null || Variables.token.isEmpty()) {
            Toast.makeText(requireContext(), "Internal Error", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (et_first_name.getText().toString().trim().isEmpty()) {
            et_first_name.setError("Full name is required");
            ObjectAnimator.ofFloat(et_first_name, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            et_first_name.requestFocus();
            return;
        } else if (et_email.getText().toString().trim().isEmpty()) {
            et_email.setError("Email is required");
            ObjectAnimator.ofFloat(et_email, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            et_email.requestFocus();
            return;
        } else if (et_city.getText().toString().trim().isEmpty()) {
            et_city.setError("City is required");
            ObjectAnimator.ofFloat(et_city, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            et_city.requestFocus();
            return;
        } else if (spinner_state.getText().toString().isEmpty()) {
            spinner_state.setError("State is required");
            ObjectAnimator.ofFloat(spinner_state, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            spinner_state.requestFocus();
            return;

        } else if (spinner_district.getText().toString().isEmpty()) {
            spinner_district.setError("District is required");
            ObjectAnimator.ofFloat(spinner_district, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            spinner_district.requestFocus();
            return;
        }

        else {
            String fullnameStr = et_first_name.getText().toString().trim();
            String emailStr = et_email.getText().toString().trim();
            String cityStr = String.valueOf(et_city.getText().toString().trim());
            String stateStr = String.valueOf(spinner_state.getSelectedIndex() + 1);
            String districtStr = String.valueOf(spinner_district.getSelectedIndex() + 1);

            new ApiTask2().execute((String) Variables.userId, Variables.token, fullnameStr, emailStr, cityStr, stateStr, districtStr);

        }


        String state = "";
        String district = "";

        String country = "";

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        builder
                .addFormDataPart("user_id", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_ID))
                .addFormDataPart("token", SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN))
                .addFormDataPart("name", et_first_name.getText().toString())

                .addFormDataPart("email", et_email.getText().toString())
                .addFormDataPart("city", et_city.getText().toString())
                .addFormDataPart("state", state)
                .addFormDataPart("district", district)
                .build();


        Call<EditProfileUpdateDrailsUpdateModels> call = apiInterface.userDetailsUpdate(builder.build());
        call.enqueue(new Callback<EditProfileUpdateDrailsUpdateModels>() {
            @Override
            public void onResponse(Call<EditProfileUpdateDrailsUpdateModels> call, Response<EditProfileUpdateDrailsUpdateModels> response) {
                CommonUtils.hideLoading();
                if (response.body() != null && response.body().getStatusCode()) {
                    if (response.body().getStatusCode()) {
                        Toast.makeText(requireContext(), "Profile Details Updated successfully", Toast.LENGTH_SHORT).show();
                        dismissListener.onDismiss();
                        dismiss();
                    } else {
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EditProfileUpdateDrailsUpdateModels> call, Throwable t) {
                CommonUtils.hideLoading();
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }


    //    //HttpURLConnection:--
    @SuppressLint("StaticFieldLeak")
    private class ApiTask2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //Creating array for parameters
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
                    String result = putData.getResult();
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
//                        sendotp.setVisibility(View.VISIBLE);
                        btn_save.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    btn_save.setVisibility(View.VISIBLE);

                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                btn_save.setVisibility(View.VISIBLE);

                Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //HttpURLConnection:--
    public void getUserData(JSONObject ResultJson) {
        try {
            String status_code = ResultJson.getString("status_code");
            String privacyPolicy = ResultJson.getString("privacyPolicy");
            String termsAndConditions = ResultJson.getString("termsAndConditions");
            String returnRefundPolicy = ResultJson.getString("returnRefundPolicy");
            String security = ResultJson.getString("security");
            String aboutUs = ResultJson.getString("aboutUs");
            String message = ResultJson.getString("message");

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
            Variables.id = Integer.valueOf(id);
            Variables.departmentId = Integer.valueOf(departmentId);


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


            btn_save.setVisibility(View.VISIBLE);
            Loading.hide();

            startActivity(new Intent(requireContext(), EditProfileFragment2.class));
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            Toast.makeText(requireContext(), "Internal Error", Toast.LENGTH_SHORT).show();
            btn_save.setVisibility(View.VISIBLE);
        }

    }



    //Retrofit:--
    private boolean isAllFieldsValidated() {

        if (TextUtils.isEmpty(et_first_name.getText().toString().trim())) {
            et_first_name.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(et_email.getText().toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {
            et_email.setError("Not Valid");
            return false;
        }

        if (TextUtils.isEmpty(et_city.getText().toString().trim())) {
            et_city.setError("Required");
            return false;
        }

//        if (TextUtils.isEmpty(spinner_state.getText().toString().isEmpty())) {
        if (spinner_state.getText().toString().isEmpty()) {
            spinner_state.setError("State is required");
            ObjectAnimator.ofFloat(spinner_state, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            spinner_state.requestFocus();
            return false;

        }
        if (spinner_district.getText().toString().isEmpty()) {
            spinner_district.setError("District is required");
            ObjectAnimator.ofFloat(spinner_district, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            spinner_district.requestFocus();
            return false;
        }

        return true;
    }


//Retrofit:---
    public void updateDetails(ProfileGetUserDataResponseRetrofit.ProfileGetUserDataModelRetrofit dat) {

        this.data = dat;
        et_first_name.setText((CharSequence) data.getName());
        et_email.setText((CharSequence) data.getEmail());
        et_city.setText((CharSequence) data.getCity());

        spinner_state.setText((CharSequence) data.getState());
        spinner_district.setText((CharSequence) data.getDistrict());

    }

    //Retrofit
    public void setDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }


    //Retrofit
    public interface DismissListener {
        public void onDismiss();
    }


    //
    public void rememberMe() {
        // Get SharedPreferences.Editor object
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Set String value
        editor.putString("name", et_first_name.getText().toString());
        editor.putString("email", et_email.getText().toString());
        editor.putString("city", et_city.getText().toString());

        editor.putBoolean("rememberMe", true);
        // Apply changes
        editor.apply();
    }


    //
    public void getFCMToken() {
        FirebaseApp.initializeApp(requireContext());
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("firebase", "Fetching FCM Edit token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("firebase", "token" + token);
                        Variables.token = token;
                    }
                });
    }

    ArrayList<GetDefaultDataPrakruthiStates.States> statess = new ArrayList<>();

    //getDefaultData POST Method

    //------
    ArrayList<GetDefaultDataPrakruthiDistrict.District> districts = new ArrayList<>();


    private void fetchStates() {
        CommonUtils.showLoading(requireContext(), "Please Wait", false);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("table_name", "states");
        Log.e("state", jsonObject.getAsString());

        Call<GetDefaultDataPrakruthiStates> call = apiInterface.getDropdownData(jsonObject);

        call.enqueue(new Callback<GetDefaultDataPrakruthiStates>() {
            @Override
            public void onResponse(Call<GetDefaultDataPrakruthiStates> call, Response<GetDefaultDataPrakruthiStates> response) {

                GetDefaultDataPrakruthiStates getDropdownDataReponsePrakruthiMs = response.body();

                statess = new ArrayList<>();

                if (getDropdownDataReponsePrakruthiMs != null && getDropdownDataReponsePrakruthiMs.getData() != null) {

                    statess.addAll(getDropdownDataReponsePrakruthiMs.getData());


                    if (jsonObject != null) {

                        JsonArray states = jsonObject.get("state").getAsJsonArray();
                        Log.e("state", states.getAsString());
                        ArrayList<String> stateNames = new ArrayList<>();


                        for (int i = 0; i < states.size(); i++) {
                            JsonObject state = states.get(i).getAsJsonObject();
                            stateNames.add(state.get("name").getAsString());
                        }

                        spinner_state.setItems(stateNames);

                        JsonArray districts = jsonObject.get("district").getAsJsonArray();
                        districtNames.clear();

                        for (int i = 0; i < districts.size(); i++) {
                            JsonObject districtt = districts.get(i).getAsJsonObject();
                            if (districtt.get("state_id").getAsInt() == stateId) {

                                districtNames.add(districtt.get("name").getAsString());

                                spinner_district.setItems(districtNames);
                            }
                        }


                    }


                    else {
                        Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }

                }
                ArrayList<String> stateNames = new ArrayList<>();
                stateNames.add("Select");


                int selPos = 0;

                for (GetDefaultDataPrakruthiStates.States locationTable : statess) {
                    stateNames.add(locationTable.getName());
                    if (data.getState() != null && data.getState().equals(locationTable.getName()))

                        selPos = statess.indexOf(locationTable) + 1;
                }


                spinner_state.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
                    stateId = newIndex + 1;

                    fetchStates();

                    spinner_state.setError(null);
//
                });

            }

            @Override
            public void onFailure(Call<GetDefaultDataPrakruthiStates> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.hideLoading();
            }
        });

    }

    private void fetchDistrict() {
        CommonUtils.showLoading(requireContext(), "Please Wait", false);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", SharedPrefs.getInstance(requireContext()).getString(Constants.ID));
        jsonObject.addProperty("reference_id", "state_id");
        jsonObject.addProperty("table_name", "district");

        Call<GetDefaultDataPrakruthiDistrict> call = apiInterface.getDropdownData1(jsonObject);

        call.enqueue(new Callback<GetDefaultDataPrakruthiDistrict>() {
            @Override
            public void onResponse(Call<GetDefaultDataPrakruthiDistrict> call, Response<GetDefaultDataPrakruthiDistrict> response) {
                districts = new ArrayList<>();
                GetDefaultDataPrakruthiDistrict getDropdownDataReponsePrakruthiMs = response.body();
                if (getDropdownDataReponsePrakruthiMs != null && getDropdownDataReponsePrakruthiMs.getData() != null) {

                    districts.addAll(getDropdownDataReponsePrakruthiMs.getData());

                    if (jsonObject != null) {


                        JsonArray states = jsonObject.get("state").getAsJsonArray();
                        ArrayList<String> stateNames = new ArrayList<>();


                        for (int i = 0; i < states.size(); i++) {
                            JsonObject state = states.get(i).getAsJsonObject();
                            stateNames.add(state.get("name").getAsString());
                        }

                        spinner_state.setItems(stateNames);


                        JsonArray districts = jsonObject.get("district").getAsJsonArray();
                        districtNames.clear();

                        for (int i = 0; i < districts.size(); i++) {
                            JsonObject districtt = districts.get(i).getAsJsonObject();
                            if (districtt.get("state_id").getAsInt() == stateId) {

                                districtNames.add(districtt.get("name").getAsString());

                                spinner_district.setItems(districtNames);
                            }
                        }

                    }

                }

                ArrayList<String> districtNames = new ArrayList<>();
                districtNames.add("Medhak");


                int selPos = 0;
                for (GetDefaultDataPrakruthiDistrict.District locationTable : districts) {
                    districtNames.add(locationTable.getName());
                    if (data.getCountry() != null && data.getCountry().equals(locationTable.getName()))
                        selPos = districts.indexOf(locationTable) + 1;
                }


                spinner_district.setOnClickListener(v -> {
                    if (stateId == 0) {
                        spinner_state.setError("Select State");
                    } else spinner_district.showOrDismiss();
                });


                CommonUtils.hideLoading();
            }

            @Override
            public void onFailure(Call<GetDefaultDataPrakruthiDistrict> call, Throwable t) {
                t.printStackTrace();
                CommonUtils.hideLoading();
            }
        });

    }

}