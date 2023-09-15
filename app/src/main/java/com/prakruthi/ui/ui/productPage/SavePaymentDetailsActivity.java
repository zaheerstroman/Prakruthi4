package com.prakruthi.ui.ui.productPage;

import static com.prakruthi.ui.Variables.productId;
import static com.prakruthi.ui.ui.productPage.ProductPage.total_amount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonElement;
import com.prakruthi.R;
import com.prakruthi.databinding.ActivitySavePaymentDetailsBinding;
import com.prakruthi.databinding.FragmentHomeBinding;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.DeleteCartDetails;
import com.prakruthi.ui.APIs.GetCartDetails;
import com.prakruthi.ui.APIs.GetDeliveryAddressDetails;
import com.prakruthi.ui.APIs.RemoveAllCartDetails;
import com.prakruthi.ui.APIs.SavePaymentDetailsApi;
import com.prakruthi.ui.Api.API_class;
import com.prakruthi.ui.Api.Retrofit_funtion_class;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.WebViewActivityPayment;
import com.prakruthi.ui.ui.cart.CartFragment;
import com.prakruthi.ui.ui.cart.CartModal;
import com.prakruthi.ui.ui.cart.CartRecyclerAdaptor;
import com.prakruthi.ui.ui.home.HomeFragment;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
import com.prakruthi.ui.ui.profile.myaddress.MyAddresses;
import com.prakruthi.ui.ui.search.SearchPage;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavePaymentDetailsActivity extends AppCompatActivity implements GetCartDetails.OnDataFetchedListener, GetDeliveryAddressDetails.DeliveryAddressListener, AddToCart.OnDataFetchedListner, DeleteCartDetails.OnCartItemDeleteAPiHit, SavePaymentDetailsApi.OnSavePaymentDetailsApiHitListner, RemoveAllCartDetails.OnCartItemRemoveAllAPiHit {

    static final String TAG = SavePaymentDetailsActivity.class.getSimpleName();

    public static RecyclerView addressRecyclerView_SavePaymentDetails;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    public static TextView HomeProductPageDetailsBuyNowSavePaymentDetailsAddress;

    private ActivitySavePaymentDetailsBinding binding;

    private final long DELAY_TIME = 4000; // in milliseconds


    @BindView(R.id.MakepaymentButton)
    AppCompatButton MakepaymentButton;

    @BindView(R.id.txt_removeall)
    TextView txtRemoveall;


    ArrayList<ProductModel> productModals;

    StringBuilder arrayList = new StringBuilder();

    private CartInteractionListener mListener;

    public static String address, productIds, totalAmount;

    public static String invoiceId = String.valueOf(113); // Replace with the actual invoice_id you want to save

    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_payment_details);

        binding = ActivitySavePaymentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final RecyclerView recyclerviewList = binding.cartRecyclerviewList;

        String invoiceId = String.valueOf(113);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("invoice_id", Integer.parseInt(invoiceId));
        editor.apply();

        int retrievedInvoiceId = sharedPreferences.getInt("invoice_id", -1);

        if (retrievedInvoiceId != -1) {
        }


        GetDeliveryAddressDetails();

        SetScreenViews();

        getCartDetails();

        txtRemoveall = findViewById(R.id.txt_removeall);


        binding.txtRemoveall.setOnClickListener(v -> {
            RemoveAllCartDetails removeAllCartDetails = new RemoveAllCartDetails(this);
            removeAllCartDetails.RemoveAllHitApi();
        });


        MakepaymentButton = findViewById(R.id.MakepaymentButton);

        MakepaymentButton.setOnClickListener(v -> {

            proceddutocheckout();

//            startActivity(new Intent(SavePaymentDetailsActivity.this, CartFragment.class));
//            savePaymentDetailsApi();

        });


    }

    private void proceddutocheckout() {
        progressDoalog = new ProgressDialog(SavePaymentDetailsActivity.this);
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String ContentType = "application/json";
        String Accept = "application/json";
        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);
        Call<JsonElement> callRetrofit = null;
        callRetrofit = service.AKRUTHTEST(String.valueOf(Variables.id), Variables.token, Variables.addressID, arrayList.toString(), String.valueOf(CartModal.cartAmount));
        callRetrofit.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                System.out.println("----------------------------------------------------");
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                System.out.println("----------------------------------------------------");

                if (response.isSuccessful()) {
                    progressDoalog.dismiss();
                    String searchResponse = response.body().toString();
                    Log.d("Regisigup", searchResponse);
                    try {
//                        JSONObject lObj = new JSONObject(searchResponse);
                        try {
                            JSONObject lObj = new JSONObject(searchResponse);
                            String status_code = lObj.getString("status_code");
                            if (status_code.equalsIgnoreCase("true")) {
                                String payment_url = lObj.getString("payment_url");
                                String invoice_id = lObj.getString("invoice_id");
                                String invoice_num = lObj.getString("invoice_num");

                                Intent intent = new Intent(SavePaymentDetailsActivity.this, WebViewActivityPayment.class);
                                intent.putExtra("payment_url", payment_url);
                                intent.putExtra("invoice_id", invoice_id);
                                intent.putExtra("invoice_num", invoice_num);
                                startActivity(intent);

                            } else {
                                String message = lObj.getString("message");
                                //status true/false
                                if (message.equalsIgnoreCase("No AddressFound.")) {
                                    Intent intent = new Intent(SavePaymentDetailsActivity.this, MyAddresses.class);
                                    startActivity(intent);
                                }
                                Toast.makeText(SavePaymentDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            }

//
                        }
                        catch (Exception e) {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    progressDoalog.dismiss();
                    if (!response.isSuccessful()) {
                        InputStream i = response.errorBody().byteStream();
                        BufferedReader r = new BufferedReader(new InputStreamReader(i));
                        StringBuilder errorResult = new StringBuilder();
                        String line;
                        try {
                            while ((line = r.readLine()) != null) {

                                errorResult.append(line).append('\n');
                                try {
                                    JSONObject jsonObject = new JSONObject(line);
                                    jsonObject.getString("message");
                                    Log.d("lineappende >>>>  ", "lineapends  >>> " + jsonObject.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("line", "line" + errorResult.append("message" + line));
                                Log.d("searchResponse", "searchResponse" + errorResult.append(line).append('\n'));


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Error Call", ">>>>" + call.toString());
                Log.d("Error", ">>>>" + t.toString());
            }
        });

    }


    //payment_gateway.php
    public void savePaymentDetailsApi() {
        SavePaymentDetailsApi savePaymentDetailsApi = new SavePaymentDetailsApi(this, address, productIds, totalAmount);
        savePaymentDetailsApi.HitSavePaymentDetailsApi();
    }


    public void getCartDetails() {
        binding.cartRecyclerviewList.showShimmerAdapter();
        GetCartDetails getCartDetails = new GetCartDetails(this);
        getCartDetails.fetchData();
    }


    public void onStart() {
        super.onStart();
    }


    private final Handler handler = new Handler();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void SetScreenViews() {

        HomeProductPageDetailsBuyNowSavePaymentDetailsAddress = binding.DeleverHomeLocationPurchasesDateTime;

        HomeProductPageDetailsBuyNowSavePaymentDetailsAddress.setSelected(true);

        binding.DeleverHomeLocationPurchasesDateTime.setOnClickListener(v -> {
            if (GetPermission()) {
                chooseLocationDialog();
            }
        });
    }

    public void setPayButtonVisibility() {

        if (productModals.size() == 0) {
            binding.MakepaymentButton.setVisibility(View.GONE);
        } else {
            binding.MakepaymentButton.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onCartListFetched(ArrayList<CartModal> cartModals) {
        try {
            for (int i = 0; i < cartModals.size(); i++) {

                Log.d("getProductId", String.valueOf(cartModals.get(i).getProductId()));

                arrayList.append(String.valueOf(cartModals.get(i).getProductId())).append(",");

            }

            SavePaymentDetailsActivity.this.runOnUiThread(() -> {
                binding.cartRecyclerviewList.hideShimmerAdapter();
                binding.cartRecyclerviewList.setLayoutManager(new LinearLayoutManager(SavePaymentDetailsActivity.this));
                binding.cartRecyclerviewList.setAdapter(new CartRecyclerAdaptor(SavePaymentDetailsActivity.this, cartModals, this, this));

//                binding.SubtotalPrice.setText(String.valueOf(CartModal.cartAmount));

                binding.SubTotalPrice.setText("₹  ");
//                binding.SubtotalPrice.append(String.valueOf(CartModal.getCartAmount()));
                binding.SubTotalPrice.append(String.valueOf(CartModal.getSubTotal()));

                binding.subTotal.setSelected(true);

//                binding.TaxesPrice.setText("₹  ");
//                binding.TaxesPrice.append(String.valueOf(CartModal.getTax()));


//                binding.TaxesPrice.setText("₹  ");
//                binding.TaxesPrice.append(String.valueOf(CartModal.getSubTotal()));

                if (CartModal.getCgst() != 0) {
                    binding.cgstPrice.setText("₹ " + String.valueOf(CartModal.getCgst()));
                } else {
                    // If the value is 0, hide the view or set it to an empty string
                    binding.cgstPrice.setVisibility(View.GONE); // Hide the view
                    binding.Cgst.setVisibility(View.GONE); // Hide the view


                }

                if (CartModal.getCgst_percentage() != 0) {
                    binding.CgstPercentage.setText(String.valueOf(CartModal.getCgst_percentage()) + " %");
                } else {
                    // If the value is 0, hide the view or set it to an empty string
                    binding.CgstPercentage.setVisibility(View.GONE); // Hide the view
                    binding.Cgst.setVisibility(View.GONE); // Hide the view

                }

                binding.sgstPrice.setText("₹  ");
                binding.sgstPrice.append(String.valueOf(CartModal.getSgst()));
                binding.SgstPercentage.setText(String.valueOf(CartModal.getSgst_percentage()) + " %");


                binding.igstPrice.setText("₹  ");
                binding.igstPrice.append(String.valueOf(CartModal.getIgst()));

                binding.IgstPercentage.setText(String.valueOf(CartModal.getIgst_percentage()) + " %");


                binding.totalAmountPrice.setText("₹  ");
                binding.totalAmountPrice.append(String.valueOf(CartModal.getCartAmount()));

                binding.MakepaymentButton.setVisibility(View.VISIBLE);
            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }

    }

    @Override
    public void onDataFetchError(String error) {
        try {
            SavePaymentDetailsActivity.this.runOnUiThread(() -> {
                try {
                    binding.cartRecyclerviewList.hideShimmerAdapter();
                    binding.NesterScrollViewCart.setVisibility(View.GONE);
                    binding.MakepaymentButton.setVisibility(View.GONE);
                    Toast.makeText(SavePaymentDetailsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean GetPermission() {
        if (ContextCompat.checkSelfPermission(SavePaymentDetailsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SavePaymentDetailsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted, do something
            } else {
                Toast.makeText(SavePaymentDetailsActivity.this, "Location Permission Required", Toast.LENGTH_SHORT).show();
                // permission denied, do something else
            }
        }
    }

    public boolean IsGpsEnabled() {
        LocationManager locationManager = (LocationManager) SavePaymentDetailsActivity.this.getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsEnabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SavePaymentDetailsActivity.this);
            builder.setTitle("Enable GPS");
            builder.setMessage("Please enable GPS to use this feature.");
            builder.setPositiveButton("Settings", (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return isGpsEnabled;
    }


    @SuppressLint("MissingPermission")
    public void getCurrentLocation(Consumer<String> callback) {
        Loading.show(SavePaymentDetailsActivity.this);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SavePaymentDetailsActivity.this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Geocoder geocoder = new Geocoder(SavePaymentDetailsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String area = address.getSubLocality();
                        String city = address.getLocality();
                        String pincode = address.getPostalCode();
                        String locationString = "Deliver to " + Variables.name + " - " + area + ", " + city + " " + pincode;
                        callback.accept(locationString);
                        Loading.hide();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Loading.hide();
                }
            } else {
                Log.d(TAG, "Location is null");
                Toast.makeText(SavePaymentDetailsActivity.this, "Error Fetching Location", Toast.LENGTH_SHORT).show();
                Loading.hide();
            }
        });
    }


    private void chooseLocationDialog() {
        // Create the bottom sheet dialog
        BottomSheetDialog dialog = new BottomSheetDialog(SavePaymentDetailsActivity.this);
        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(SavePaymentDetailsActivity.this).inflate(R.layout.choose_location_bottom_dialog, null);

        addressRecyclerView_SavePaymentDetails = dialogView.findViewById(R.id.choose_location_bottom_dialog_recycler);
        GetDeliveryAddressDetails();

        TextView CurrentLocation = dialogView.findViewById(R.id.choose_location_bottom_dialog_choose_current_location);

        CurrentLocation.setOnClickListener(v -> {
            if (IsGpsEnabled()) {
                getCurrentLocation(locationString -> {
                    binding.DeleverHomeLocationPurchasesDateTime.setText(locationString);
                    dialog.dismiss();
                });
            }
        });

        dialog.setContentView(dialogView);
        // Show the dialog
        dialog.show();
    }

    public void GetDeliveryAddressDetails() {
        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
        getDeliveryAddressDetails.execute();
    }

    @Override
    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> address_bottomSheet_recycler_adaptor_models) {

        if (addressRecyclerView_SavePaymentDetails != null) {
            addressRecyclerView_SavePaymentDetails.setLayoutManager(new LinearLayoutManager(SavePaymentDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            addressRecyclerView_SavePaymentDetails.setAdapter(new SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor(address_bottomSheet_recycler_adaptor_models, SavePaymentDetailsActivity.this));
        }
        if (Variables.address.isEmpty() || Variables.address.matches("") || Variables.address.equals("null") || Variables.address.equals(null)) {
            binding.DeleverHomeLocationPurchasesDateTime.setText("Choose Location");
        } else {
            binding.DeleverHomeLocationPurchasesDateTime.setText(Variables.address);
        }
    }


    @Override
    public void OnCarteditDataFetched(String Message) {
        SavePaymentDetailsActivity.this.runOnUiThread(() -> {
            Loading.hide();
            getCartDetails();
        });

    }

    @Override
    public void OnAddtoCartDataFetched(String Message) {

        SavePaymentDetailsActivity.this.runOnUiThread(Loading::hide);

        SavePaymentDetailsActivity.this.runOnUiThread(() -> {
            Loading.hide();
            getCartDetails();
        });

    }

    @Override
    public void OnErrorFetched(String Error) {
        SavePaymentDetailsActivity.this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(SavePaymentDetailsActivity.this, Error, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void OnCartItemDeleted(String message) {
        SavePaymentDetailsActivity.this.runOnUiThread(() -> {
            Loading.hide();
            getCartDetails();
        });
    }

    @Override
    public void OnCartItemDeleteAPiGivesError(String error) {
        SavePaymentDetailsActivity.this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(SavePaymentDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
            getCartDetails();
        });
    }

    @Override
    public void OnSavePaymentDtailsResult(String product) {

        runOnUiThread(() -> {

//            GetApiData();
            Loading.hide();
            PopupDialog.getInstance(this).setStyle(Styles.SUCCESS).setHeading("Well Done").setDescription("Successfully" + " Added Into The Cart").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    super.onDismissClicked(dialog);
                }
            });


        });


        SavePaymentDetailsActivity.this.runOnUiThread(Loading::hide);

//        payment_url
//        hit

        try {
            SavePaymentDetailsActivity.this.runOnUiThread(() -> {
                Toast.makeText(SavePaymentDetailsActivity.this, product, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnOnSavePaymentDtailsResultApiGivesError(String error) {
        try {
            SavePaymentDetailsActivity.this.runOnUiThread(() -> {
                Toast.makeText(SavePaymentDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnCartItemRemoveAll(String message) {
        SavePaymentDetailsActivity.this.runOnUiThread(this::getCartDetails);
    }

    @Override
    public void OnCartItemRemoveAllAPiGivesError(String error) {

    }


    public interface CartInteractionListener {

        void ProductAddedToCart(ProductModel product);

        void ProductRemovedFromCart(ProductModel product);

    }

}