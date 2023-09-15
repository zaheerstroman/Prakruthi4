package com.prakruthi.ui.ui.profile.myaddress;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.DeleteDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetDeliveryAddressDetails;
import com.prakruthi.ui.APIs.SaveDeliveryAddressDetails;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.AddressUserDetails;
import com.prakruthi.ui.ui.UserDetails;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAddresses extends AppCompatActivity implements GetDeliveryAddressDetails.DeliveryAddressListener, SaveDeliveryAddressDetails.OnSaveDeliveryAddressApiHits, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits {

    //    RecyclerView myAddresses_personal_address_recyclerview_List;
    ShimmerRecyclerView myAddresses_personal_address_recyclerview_List;


    AppCompatButton btn_add_new_address, txtRemove;

    public static int id;

    EditText nameEditText ,countryEditText ;
    EditText cityEditText, stateEditText,addressEditText,postalCodeEditText  ;



    int stateId = 0;

    //    ArrayList<String> districtNames = new ArrayList<>();
    ArrayList<String> cityNames = new ArrayList<>();


    @SuppressLint({"WrongViewCast", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

//        View root = binding.getRoot();
//        id = root.getId();


        myAddresses_personal_address_recyclerview_List = findViewById(R.id.myAddresses_personal_address_recyclerview_List);

        // Ensure stateEditText and cityEditText are initialized properly


//        PowerSpinnerView powerSpinnerView = findViewById(R.id.powerSpinnerView); // Make sure the ID matches your XML layout
//        cityEditText = findViewById(R.id.cityEditText); // Make sure the ID matches your XML layout
//        stateEditText = findViewById(R.id.stateEditText); // Make sure the ID matches your XML layout


        //  // Getting DropDown Arrays
        //        getDropDownData();
//        getDropDownData(stateEditText);
//        getDropDownData(stateEditText, cityEditText);

//        if (stateEditText != null) {
//            stateEditText.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
//                stateId = newIndex + 1;
////            getDropDownData(stateEditText);
//                getDropDownData(stateEditText, cityEditText);
//                stateEditText.setError(null);
//            });
//        } else {
//            // Handle the case where stateEditText is null
//            Toast.makeText(this, "Oops! Something went wrong with state selection.", Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "stateEditText is null");
//
//            //            stateId = DEFAULT_STATE_ID;
//
//        }
//
//        if (cityEditText != null) {
//            cityEditText.setOnClickListener(v -> {
//                if (stateId == 0) {
//                    stateEditText.setError("Select State");
//                } else cityEditText.showOrDismiss();
//            });
//        } else {
//            // Handle the case where stateEditText is null
//            Toast.makeText(this, "Oops! Something went wrong with state selection.", Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "stateEditText is null");
//
//            //            stateId = DEFAULT_STATE_ID;
//
//        }

        // set an OnTouchListener to the root view
        View root = findViewById(android.R.id.content);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // check if the touch is outside of the state view
                    int[] location = new int[2];
                    stateEditText.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = stateEditText.getWidth();
                    int height = stateEditText.getHeight();
                    if (!(event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height)) {
                        // dismiss the state view
//                        stateEditText.dismiss();
////                        district.dismiss();
//                        cityEditText.dismiss();

//                        type.dismiss();
                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });

        btn_add_new_address = findViewById(R.id.btn_add_new_address);


        btn_add_new_address.setOnClickListener(v -> {
            // Create an AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAddresses.this);
            builder.setTitle("Add New Address");

            // Set the custom layout for the dialog
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_address, null);
            builder.setView(dialogView);

//            cityEditText = findViewById(R.id.edittext_city); // Make sure the ID matches your XML layout
//            stateEditText = findViewById(R.id.editTextState); // Make sure the ID matches your XML layout

            // Find the EditText fields and checkbox in the dialog layout
//             nameEditText = dialogView.findViewById(R.id.edittext_name);
            EditText nameEditText = dialogView.findViewById(R.id.edittext_name);

//            EditText stateEditText = dialogView.findViewById(R.id.edittext_state);
//            editTextState = dialogView.findViewById(R.id.editTextState);
//             stateEditText = dialogView.findViewById(R.id.editTextState);
             stateEditText = dialogView.findViewById(R.id.editTextState);

//            EditText cityEditText = dialogView.findViewById(R.id.edittext_city);
             cityEditText = dialogView.findViewById(R.id.edittext_city);
//            PowerSpinnerView cityEditText = dialogView.findViewById(R.id.edittext_city);


//             countryEditText = dialogView.findViewById(R.id.edittext_country);
            EditText countryEditText = dialogView.findViewById(R.id.edittext_country);

//            addressEditText = dialogView.findViewById(R.id.edittext_address);
           EditText addressEditText = dialogView.findViewById(R.id.edittext_address);

//            postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code);
           EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code);

            CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default);

//            getDropDownData(editTextState, editTextDistrict);
//            getDropDownData(stateEditText, cityEditText);

            // Add click listener to the Submit button
            nameEditText.setText(AddressUserDetails.name);
//            editTextState.setText(UserDetails.state);
            stateEditText.setText(AddressUserDetails.state);
            cityEditText.setText(AddressUserDetails.city);
            countryEditText.setText(AddressUserDetails.country);
            addressEditText.setText(AddressUserDetails.address);
            addressEditText.setText(AddressUserDetails.postal_code);
            addressEditText.setText(AddressUserDetails.is_default);


            // Set the positive and negative buttons
            builder.setPositiveButton("Save", null); // Set null initially

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // Handle the cancel button click
                // Dismiss the dialog
                dialog.dismiss();
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();

            // Override the onClickListener for the positive button
            dialog.setOnShowListener(dialogInterface -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(view -> {

//                    editTextState.setError(null);
                    stateEditText.setError(null);
                    cityEditText.setError(null);


                    // Handle the save button click
                    String name = nameEditText.getText().toString();

                    String city = cityEditText.getText().toString();
//                    String city = String.valueOf(cityEditText.getSelectedIndex() + 1);

                    String state = stateEditText.getText().toString();
//                    String state = String.valueOf(stateEditText.getSelectedIndex() + 1);

                    String country = countryEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String postalCode = postalCodeEditText.getText().toString();
                    boolean isDefault = defaultCheckBox.isChecked();


                    // Check if any of the fields are empty
                    boolean hasError = false;
                    if (name.isEmpty()) {
                        nameEditText.setError("Name is required");
                        hasError = true;
                    }
                    if (city.isEmpty()) {
                        cityEditText.setError("City is required");
                        hasError = true;
                    }
                    if (state.isEmpty()) {
                        stateEditText.setError("State is required");
//                        ObjectAnimator.ofFloat(state, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
//                        state.requestFocus();
                        hasError = true;
                    }
                    if (country.isEmpty()) {
                        countryEditText.setError("Country is required");
                        hasError = true;
                    }
                    if (address.isEmpty()) {
                        addressEditText.setError("Address is required");
                        hasError = true;
                    }
                    if (postalCode.isEmpty()) {
                        postalCodeEditText.setError("Postal Code is required");
                        hasError = true;
                    }

                    if (!hasError) {
                        // All fields are properly filled, perform the save operation
//                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, "city", country, address, postalCode, boolToInt(isDefault));
                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode, boolToInt(isDefault));

                        saveDeliveryAddressDetails.HitApi();
                        Loading.show(this);
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
//                    else{
//                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(
//                                nameEditText.getText().toString(),
//                                cityEditText.getSelectedIndex() + 1,
//                                stateEditText.getSelectedIndex() + 1,
//                                countryEditText.getText().toString(),
//                                addressEditText.getText().toString(),
//                                postalCodeEditText.getText().toString(),
//                                defaultCheckBox.getText().toString(),
//                        )
//                    }
                });
            });

            dialog.show();
        });


        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
        getDeliveryAddressDetails.execute();
    }

    @SuppressLint("StaticFieldLeak")
//    public void getDropDownData(PowerSpinnerView State) {
    public void getDropDownData(PowerSpinnerView State, PowerSpinnerView City) {

        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
//                FetchData fetchData = new FetchData("https://houseofspiritshyd.in/prakruthi/admin/api/getDropdownData");
                FetchData fetchData = new FetchData("https://prakruthiepl.com/admin/api/getDropdownData");

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
                            stateNames.add(state.getString("state"));
                        }
//                        State.setItems(stateNames);
//                        stateEditText.setItems(stateNames);

                        //----------
                        JSONArray districts = jsonObj.getJSONArray("city");
//                        districtNames.clear();
                        cityNames.clear();
                        for (int i = 0; i < districts.length(); i++) {
                            JSONObject districtt = districts.getJSONObject(i);

                            if (districtt.getInt("state_id") == stateId) {

//                                districtNames.add(districtt.getString("name"));
                                cityNames.add(districtt.getString("city"));

//                                City.setItems(cityNames);
//                                cityEditText.setItems(cityNames);

                            }


                            cityNames.add(districtt.getString("city"));
                        }
                        City.setItems(cityNames);


                    } catch (JSONException e) {
                        Toast.makeText(MyAddresses.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyAddresses.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    public int boolToInt(boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList) {
        try {
            MyAddresses.this.runOnUiThread(() -> {
                myAddresses_personal_address_recyclerview_List.hideShimmerAdapter();
                myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(this));
                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(addressList, this));
            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    @Override
    public void onSaveDeliveryAddress(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
            getDeliveryAddressDetails.execute();
            PopupDialog.getInstance(this)
                    .setStyle(Styles.SUCCESS)
                    .setHeading("Well Done")
                    .setDescription("Successfully" +
                            " Added New Address")
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

    @Override
    public void onSaveDeliveryAddressApiError(String error) {
        runOnUiThread(() -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());
    }


    @Override
    public void OnDeleteDeliveryAddress(String message) {
        MyAddresses.this.runOnUiThread(() -> {
            Loading.hide();

//            getCartDetails();
            myAddresses_personal_address_recyclerview_List.showShimmerAdapter();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
            getDeliveryAddressDetails.execute();
        });
    }

    @Override
    public void OnOnDeleteDeliveryAddressApiGivesError(String error) {
        MyAddresses.this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(MyAddresses.this, error, Toast.LENGTH_SHORT).show();

//            getCartDetails();
            myAddresses_personal_address_recyclerview_List.showShimmerAdapter();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
            getDeliveryAddressDetails.execute();

        });
    }
}