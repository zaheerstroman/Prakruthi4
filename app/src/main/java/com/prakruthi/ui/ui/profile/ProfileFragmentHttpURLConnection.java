package com.prakruthi.ui.ui.profile;

import static android.content.ContentValues.TAG;
import static com.prakruthi.ui.Variables.city;
import static com.prakruthi.ui.Variables.district;
import static com.prakruthi.ui.Variables.email;
import static com.prakruthi.ui.Variables.name;
import static com.prakruthi.ui.Variables.state;
import static com.prakruthi.ui.Variables.token;
import static com.prakruthi.ui.Variables.userId;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.prakruthi.R;
import com.prakruthi.databinding.FragmentProfileHttpURLConnectionBinding;
import com.prakruthi.ui.APIs.FeedBackApi;
import com.prakruthi.ui.APIs.GetRecentViewProductsAPI;
import com.prakruthi.ui.APIs.GetUserData;
import com.prakruthi.ui.APIs.GetUserDataApi;
import com.prakruthi.ui.APIs.UserDetailsUpdate;
import com.prakruthi.ui.APIs.UserDetailsUpdateApi;
import com.prakruthi.ui.Login;
import com.prakruthi.ui.SplashScreen;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.UserDetails;
import com.prakruthi.ui.ui.profile.myaddress.MyAddresses;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersActivity;
import com.prakruthi.ui.ui.profile.mypayments.MyPaymentsActivity;
import com.prakruthi.ui.ui.profile.order_qty.OrderQtyActivity;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductAdaptor;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductModel;
import com.prakruthi.ui.utils.Constants;
import com.prakruthi.ui.utils.SharedPrefs;
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

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
//public class ProfileFragmentHttpURLConnection extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit, GetRecentViewProductsAPI.OnGetRecentViewProductsAPIHit, GetUserDataApi.OnGetUserDataApiHitFetchedListner, UserDetailsUpdateApi.OnUserDetailsUpdateApiGivesFetchedListner {
//public class ProfileFragmentHttpURLConnection extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit, GetRecentViewProductsAPI.OnGetRecentViewProductsAPIHit, GetUserDataApi.OnGetUserDataApiHitFetchedListner, UserDetailsUpdateApi.OnUserDetailsUpdateApiGivesFetchedListner {
public class ProfileFragmentHttpURLConnection extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit, GetRecentViewProductsAPI.OnGetRecentViewProductsAPIHit {

    GetUserDataApi.OnGetUserDataApiHitFetchedListner mListner;

    AppCompatButton sendotp, backbtn;

    ArrayList<String> districtNames = new ArrayList<>();

    //    PowerSpinnerView spinner_city, spinner_state, spinner_district;
//PowerSpinnerView spinner_city, state, district;
    PowerSpinnerView spinner_city, editTextState, editTextDistrict;


    int stateId = 0;

    public ShimmerRecyclerView myAddresses_personal_address_recyclerview_List;
    private FragmentProfileHttpURLConnectionBinding binding;
    public SharedPreferences sharedPreferences;

    AppCompatTextView tvRecentProducts, tvViewAll;

    AppCompatButton btn_add_new_address, login_http, Logout_http;

    public YourAdapter yourAdapter;

    ImageView iv_edit_http, iv_close;

    Context context;


    LinearLayout ll_site_visit_req_LastUpdated4_http;

    //    public ShimmerRecyclerView ProfileHomeProductsRecycler;
    public ShimmerRecyclerView ProfileHomeProductsRecycler_http;

    TextView tvQtyHttp,tvProfileNameHttp, tvEmailHttp, tvPhoneHttp, tvRoleHttp, tvMyOrdersHttp, tvMyAddressHttp, tvMyWishlistHttp, tvPaymentsHttp, tvFeedbackHttp, tvSupportHttp, tvAboutUsHttp, tvTermsConditionsHttp, tvPrivacyPolicyHttp, tvRRPHttp, tvSecurityHttp;
    private ProfileGetUserDataResponse responseProfile_http;

    private ProfileGetUserDataResponseHttpURLConnectionAll responseProfileHttpUTLConnection;

    private ProfileGetUserDataResponseAli profileGetUserDataResponseAli;
    public EditProfileFragmentHttpURLConnection editProfileFragmentHttpURLConnection;


    public ProfileFragmentHttpURLConnection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_http_u_r_l_connection, container, false);

        iv_edit_http = rootView.findViewById(R.id.iv_edit_http);
        tvQtyHttp=rootView.findViewById(R.id.tv_qty_http);
        tvProfileNameHttp = rootView.findViewById(R.id.tv_profile_name_http);
        tvEmailHttp = rootView.findViewById(R.id.tv_email_http);
        tvPhoneHttp = rootView.findViewById(R.id.tv_phone_http);
        tvRoleHttp = rootView.findViewById(R.id.tv_role_http);

        Logout_http = rootView.findViewById(R.id.Logout_http);

        tvMyOrdersHttp = rootView.findViewById(R.id.tv_my_orders_http);

        tvMyAddressHttp = rootView.findViewById(R.id.tv_my_address_http);

        tvMyWishlistHttp = rootView.findViewById(R.id.tv_my_wishlist_http);

        tvPaymentsHttp = rootView.findViewById(R.id.tv_payments_http);

        tvFeedbackHttp = rootView.findViewById(R.id.tv_feedback_http);

        tvSupportHttp = rootView.findViewById(R.id.tv_support_http);

        tvAboutUsHttp = rootView.findViewById(R.id.tv_About_us_http);

        tvPrivacyPolicyHttp = rootView.findViewById(R.id.tv_privacy_policy_http);

        tvTermsConditionsHttp = rootView.findViewById(R.id.tv_Terms_Conditions_http);

        tvRRPHttp = rootView.findViewById(R.id.tv_returnRefundPolicy_http);

        tvSecurityHttp = rootView.findViewById(R.id.tv_security_http);

        Logout_http = rootView.findViewById(R.id.Logout_http);

        ProfileHomeProductsRecycler_http = rootView.findViewById(R.id.ProfileHomeProductsRecycler_http);
        ProfileHomeProductsRecycler_http.showShimmerAdapter();

        iv_close = rootView.findViewById(R.id.iv_close);

        backbtn = rootView.findViewById(R.id.backbtn);

        editTextState  = rootView.findViewById(R.id.editTextState);

        editTextDistrict = rootView.findViewById(R.id.editTextDistrict);


//        setdata();
        SetTextViews();
//        SetClickListeners();
        UserDetailsUpdateApi();


        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();


        sharedPreferences = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        SetTextViews();
//        SetClickListeners();



        //---------------------------------------------------------------------------------

        iv_edit_http.setOnClickListener(v -> {
            GetUserData getUserData = new GetUserData(new GetUserData.OnGetUserDataFetchedListener() {

                @Override
                public void onUserDataFetched() {

                    try {
                        requireActivity().runOnUiThread(() -> {
                            iv_edit_http.setClickable(true);
                            showDialog();

                        });
                    } catch (Exception ignore) {
                        requireActivity().runOnUiThread(() -> {
                            iv_edit_http.setClickable(true);

                        });

                    }

                }

                @Override
                public void onUserDataFetchError(String error) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                            iv_edit_http.setClickable(true);


                        } catch (Exception ignore) {

                        }
                    });

                }
            });
            getUserData.HitGetUserDataApi();

            iv_edit_http.setClickable(false);


        });



        Logout_http.setOnClickListener(v ->


        {
            //Gasaver Retrofit
            SharedPrefs.getInstance(requireContext()).clearSharedPrefs();

            //
            Variables.clear();
            // Get SharedPreferences.Editor object
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("rememberMe", false);
            // Apply changes
            editor.apply();
            startActivity(new Intent(requireContext(), Login.class));
            requireActivity().finish();

            //Gasaver
            Intent intent1 = new Intent(requireContext(), Login.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
        });

        tvMyOrdersHttp.setOnClickListener(v ->


                startActivity(new Intent(requireContext(), MyOrdersActivity.class)));



        if (tvMyAddressHttp != null) {
            tvMyAddressHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(requireContext(), MyAddresses.class));
                }
            });
        }

        if (Variables.departmentId != 2) {
//            tvMyWishlistHttp.setText("quantity");
            tvQtyHttp.setVisibility(View.VISIBLE);
            tvMyWishlistHttp.setVisibility(View.GONE);
//            Drawable top = getResources().getDrawable(R.drawable.baseline_warehouse_24);
//            tvMyWishlistHttp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.quantity_icon1, 0, 0, 0);
//            Drawable top = getResources().getDrawable(R.drawable.quantity_icon1);
//            tvMyWishlistHttp.setCompoundDrawables(null, top, null, null);

            tvQtyHttp.setOnClickListener(v -> {

                startActivity(new Intent(requireContext(), OrderQtyActivity.class));
            });

        } else {
            tvMyWishlistHttp.setVisibility(View.VISIBLE);
            tvQtyHttp.setVisibility(View.GONE);
            tvMyWishlistHttp.setOnClickListener(v -> {

                BottomNavigationView bottomNavigationView;
                bottomNavigationView = (BottomNavigationView) requireActivity().findViewById(R.id.nav_view);
                bottomNavigationView.setSelectedItemId(R.id.navigation_wishlist);

                startActivity(new Intent(requireContext(), OrderQtyActivity.class));
            });
        }

        if (tvPaymentsHttp != null) {
            tvPaymentsHttp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), MyPaymentsActivity.class));
                }
            });
        }

        if (tvFeedbackHttp != null) {
            tvFeedbackHttp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FeedBackDialog();
                }
            });
        }


        if (tvPrivacyPolicyHttp != null) {
            tvPrivacyPolicyHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), PrivacyPolicyActivity.class));

                }
            });
        }

        if (tvTermsConditionsHttp != null) {
            tvTermsConditionsHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), TermNConditionWebViewActivity.class));

                }
            });
        }

        if (tvAboutUsHttp != null) {
            tvAboutUsHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), AboutUsWebViewActivity.class));

                }
            });
        }

        if (tvSupportHttp != null) {
            tvSupportHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(requireContext(), SupportActivity.class));
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    startActivity(i);
                }

            });
        }

        if (tvRRPHttp != null) {
            tvRRPHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), ReturnRefundPolicyActivity.class));

                }
            });
        }


        if (tvSecurityHttp != null) {
            tvSecurityHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(requireContext(), SecurityActivity.class));

                }
            });
        }

        if (ll_site_visit_req_LastUpdated4_http != null) {
            ll_site_visit_req_LastUpdated4_http.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPrefs.getInstance(getActivity()).clearSharedPrefs();
                    Intent intent1 = new Intent(getActivity(), SplashScreen.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }
            });
        }

        return rootView;

    }

    private void showDialog() {


        // Create a custom dialog
        final Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.profile_edit_dialog);
        dialog.setCancelable(true);


        // Set the dialog's window width to match_parent
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);

        // Get references to the UI elements in the custom dialog
        EditText editTextName = dialog.findViewById(R.id.editTextName);
        EditText editTextEmail = dialog.findViewById(R.id.editTextEmail);
        EditText editTextCity = dialog.findViewById(R.id.editTextCity);

        editTextState = dialog.findViewById(R.id.editTextState);
        editTextDistrict = dialog.findViewById(R.id.editTextDistrict);

        Button buttonSubmit = dialog.findViewById(R.id.btn_save);
        editTextState.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            stateId = newIndex+1;

            getDropDownData(editTextState, editTextDistrict);

        });
        getDropDownData(editTextState, editTextDistrict);
        // Add click listener to the Submit button
        editTextName.setText(UserDetails.name);
        editTextEmail.setText(UserDetails.email);
        editTextCity.setText(UserDetails.city);
        editTextState.setText(UserDetails.state);
        editTextDistrict.setText(UserDetails.district);

        buttonSubmit.setOnClickListener(v -> {

            editTextState.setError(null);
            editTextDistrict.setError(null);

            // Check if any of the EditText fields are empty
            boolean hasEmptyFields = false;
            if (TextUtils.isEmpty(editTextName.getText())) {
                editTextName.setError("Name cannot be empty");
                hasEmptyFields = true;
            }
            if (TextUtils.isEmpty(editTextEmail.getText())) {
                editTextEmail.setError("Email cannot be empty");
                hasEmptyFields = true;
            }
            if (TextUtils.isEmpty(editTextCity.getText())) {
                editTextCity.setError("City cannot be empty");
                hasEmptyFields = true;
            }
            if (TextUtils.isEmpty(editTextState.getText())) {
                editTextState.setError("State cannot be empty");

                ObjectAnimator.ofFloat(editTextState, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                editTextState.requestFocus();

                hasEmptyFields = true;
            }
            if (TextUtils.isEmpty(editTextDistrict.getText())) {
                editTextDistrict.setError("District cannot be empty");

                ObjectAnimator.ofFloat(editTextDistrict, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                editTextDistrict.requestFocus();

                hasEmptyFields = true;
            }

            // If any EditText field is empty, return without dismissing the dialog
            if (hasEmptyFields) {
                return;
            } else {
                UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(

                        editTextName.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextCity.getText().toString(),
                        editTextState.getText().toString(),
                        editTextDistrict.getText().toString(),

                        new UserDetailsUpdate.OnUserDetailsUpdateListener() {

                            @Override
                            public void onUserDetailsUpdate(String messaage) {

                                requireActivity().runOnUiThread(() -> {
                                    try {
                                        Variables.name = editTextName.getText().toString();
                                        tvProfileNameHttp.setText(Variables.name);

                                        email = editTextEmail.getText().toString();
                                        tvEmailHttp.setText(email);

                                        Toast.makeText(requireContext(), messaage, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Loading.hide();
                                    } catch (Exception ignore) {
                                        Loading.hide();
                                    }

                                });

                            }

                            @Override
                            public void onUserDetailsError(String error) {

                                requireActivity().runOnUiThread(() -> {
                                    try {
                                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Loading.hide();
                                    } catch (Exception ignore) {
                                        Loading.hide();
                                    }
                                });

                            }
                        });

                userDetailsUpdate.HitUserDetailsUpdateApi();
                Loading.show(requireContext());
            }

            dialog.dismiss(); // Close the dialog after processing
        });


        dialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    public void getDropDownData(PowerSpinnerView State, PowerSpinnerView District) {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
                FetchData fetchData = new FetchData("https://houseofspiritshyd.in/prakruthi/admin/api/getDropdownData");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        String result = fetchData.getResult();
                        Log.i("getDropDownData", result);
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            return jsonObj;
//                            return new JSONObject(result);
                        } catch (JSONException e) {
                            return null;
                        }
                    }
                    return null;
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
                        State.setItems(stateNames);

                        JSONArray districts = jsonObj.getJSONArray("district");
                        districtNames.clear();

//                        ArrayList<String> districtNames = new ArrayList<>();
                        for (int i = 0; i < districts.length(); i++) {
                            JSONObject districtt = districts.getJSONObject(i);

                            if (districtt.getInt("state_id") == stateId) {

//                                districtNames.add(districtt.getString("name"));
//                                spinner_district.setItems(districtNames);
//                                District.setItems(districtNames);
                                districtNames.add(districtt.getString("name"));

                            }


                        }
                        District.setItems(districtNames);


                    } catch (JSONException e) {
                        Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    //HttpURLConnection:---

    public void SetTextViews() {

        tvProfileNameHttp.setText("");
        tvProfileNameHttp.append(String.valueOf(name));


        tvEmailHttp.setText("");
        tvEmailHttp.append(String.valueOf(Variables.email));


        tvPhoneHttp.setText("");
        tvPhoneHttp.append(String.valueOf(Variables.mobile));


        tvRoleHttp.setText("");
        tvRoleHttp.append(String.valueOf(Variables.userCode));



        ProfileHomeProductsRecycler_http.showShimmerAdapter();
        tvTermsConditionsHttp.setSelected(true);


        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();


        //Retrofit:-------
        if (editProfileFragmentHttpURLConnection != null && editProfileFragmentHttpURLConnection.isVisible()) {
            editProfileFragmentHttpURLConnection.updateDetails(responseProfile_http.getData());

        }


    }

    @SuppressLint("StaticFieldLeak")
    public void ApiTaskProHttp() {
        Loading.show(requireContext());
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "user_id";
                field[1] = "token";

                //Creating array for data
                String[] data = new String[2];
                data[0] = String.valueOf(Variables.id);
                data[1] = token;
                PutData putData = new PutData(Variables.BaseUrl + "getUserData", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();

                        try {
                            // Parse JSON response
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            ArrayList<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels = new ArrayList<>();

                            // Loop through JSON array and create Address objects
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                boolean statusCode = jsonObject.getBoolean("status_code");
                                String message = jsonObject.getString("message");
                                String privacyPolicy = jsonObject.getString("privacyPolicy");
                                String termsAndConditions = jsonObject.getString("termsAndConditions");

                                String id = obj.getString("id");
                                String department_id = obj.getString("department_id");
                                String user_code = obj.getString("user_code");

                                String name = obj.getString("name");
                                String last_name = obj.getString("last_name");
                                String email = obj.getString("email");
                                String password = obj.getString("password");
                                String mobile = obj.getString("mobile");
                                String gender = obj.getString("gender");
                                String dob = obj.getString("dob");
                                String attachment = obj.getString("attachment");
                                String city = obj.getString("city");
                                String postCode = obj.getString("postCode");
                                String address = obj.getString("address");
                                String state = obj.getString("state");
                                String country = obj.getString("country");
                                String district = obj.getString("district");
                                String street = obj.getString("street");
                                String about = obj.getString("about");
                                String status = obj.getString("status");
                                String mobile_verified = obj.getString("mobile_verified");
                                String is_verified = obj.getString("is_verified");
                                String log_date_created = obj.getString("log_date_created");
                                String created_by = obj.getString("created_by");
                                String log_date_modified = obj.getString("log_date_modified");
                                String modified_by = obj.getString("modified_by");
                                String fcm_token = obj.getString("fcm_token");
                                String device_id = obj.getString("device_id");
                                String allow_email = obj.getString("allow_email");
                                String allow_sms = obj.getString("allow_sms");
                                String allow_push = obj.getString("allow_push");


                                profileGetUserDataModels.add(new ProfileGetUserDataResponse.ProfileGetUserDataModel(id, department_id, user_code, name, last_name, email, password, mobile, gender, dob, attachment, city, postCode, address, state, country, district, street, about, status, mobile_verified, is_verified, log_date_created, created_by, log_date_modified, modified_by, fcm_token, device_id, allow_email, allow_sms, allow_push));

                            }
                            mListner.OnGetUserDataResultApiGivesSuccess(profileGetUserDataModels);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
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
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        login_http.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    login_http.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
                login_http.setVisibility(View.VISIBLE);
                Loading.hide();
            }
        }.execute();

        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();

    }

    public void refreshFragment() {

        yourAdapter.notifyDataSetChanged();


    }

    // Example usage: call this method to refresh the fragment
    public void refreshButtonClicked() {
        refreshFragment();
    }


    public void UserDetailsUpdateApi() {

        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();

    }

    public void FeedBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Feedback");

        // Create the EditText
        final EditText editText = new EditText(requireContext());
        builder.setView(editText);

        // Add the submit button
        builder.setPositiveButton("Submit", null); // Set the click listener to null for now

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Get the button from the dialog's view
        Button submitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(view -> {
            // Handle the submit button click
            String feedback = editText.getText().toString();

            if (feedback.isEmpty()) {
                editText.setError("Feedback cannot be empty");
            } else {
                // Do something with the feedback
                FeedBackApi feedBackApi = new FeedBackApi(this, feedback);
                feedBackApi.FeedbackHitApi();
                dialog.dismiss(); // Dismiss the dialog after handling the click
            }
        });
    }


    private FragmentManager getSupportFragmentManager() {
//        return null;
        return null;

    }

    @Override
    public void OnProfileItemFeedback(String description) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnProfileItemFeedbackAPiGivesError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnGetRecentViewProductsAPIGivesResult
            (ArrayList<RecentProductModel> recentProductModels) {
        try {
            requireActivity().runOnUiThread(() -> {

                ProfileHomeProductsRecycler_http.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                ProfileHomeProductsRecycler_http.setAdapter(new RecentProductAdaptor(recentProductModels));
                ProfileHomeProductsRecycler_http.hideShimmerAdapter();
            });
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    @Override
    public void OnGetRecentViewProductsAPIGivesError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();

                ProfileHomeProductsRecycler_http.hideShimmerAdapter();
                tvRecentProducts.setVisibility(View.GONE);
                tvViewAll.setVisibility(View.GONE);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


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

            Variables.id = Integer.valueOf(String.valueOf(id));
            Variables.departmentId = Integer.valueOf(String.valueOf(departmentId));
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


            iv_edit_http.setVisibility(View.VISIBLE);

            Variables.name = tvProfileNameHttp.getText().toString();
            Variables.email = tvEmailHttp.getText().toString();
            Variables.mobile = tvPhoneHttp.getText().toString();
            Variables.userCode = tvRoleHttp.getText().toString();

            Loading.hide();

            startActivity(new Intent(requireContext(), EditProfileFragmentHttpURLConnection.class));


        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            Toast.makeText(requireContext(), "Internal Error", Toast.LENGTH_SHORT).show();
            iv_edit_http.setVisibility(View.VISIBLE);
        }

    }


}