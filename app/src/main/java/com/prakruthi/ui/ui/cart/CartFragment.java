package com.prakruthi.ui.ui.cart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonElement;
import com.prakruthi.R;
import com.prakruthi.databinding.FragmentCartBinding;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.DeleteCartDetails;
import com.prakruthi.ui.APIs.GetCartDetails;
import com.prakruthi.ui.APIs.GetDeliveryAddressDetails;
import com.prakruthi.ui.APIs.RemoveAllCartDetails;
import com.prakruthi.ui.Api.API_class;
import com.prakruthi.ui.Api.Retrofit_funtion_class;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.WebViewActivityPayment;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
import com.prakruthi.ui.ui.productPage.ProductModel;
import com.prakruthi.ui.ui.productPage.SavePaymentDetailsActivity;
import com.prakruthi.ui.ui.productPage.SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor;
import com.prakruthi.ui.ui.productPage.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements GetCartDetails.OnDataFetchedListener, GetDeliveryAddressDetails.DeliveryAddressListener,AddToCart.OnDataFetchedListner, DeleteCartDetails.OnCartItemDeleteAPiHit, RemoveAllCartDetails.OnCartItemRemoveAllAPiHit {

    static final String TAG = CartFragment.class.getSimpleName();

    private FragmentCartBinding binding;

    public static RecyclerView addressRecyclerView_SavePaymentDetails;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;


    public static TextView HomeProductPageDetailsBuyNowSavePaymentDetailsAddress;

    private final long DELAY_TIME = 4000; // in milliseconds

    @BindView(R.id.MakepaymentButton)
    AppCompatButton MakepaymentButton;

//    ArrayList<ProductModel> productModals;


    private SavePaymentDetailsActivity.CartInteractionListener mListener;

    public static String address, productIds, totalAmount;
    public static String invoiceId = String.valueOf(113); // Replace with the actual invoice_id you want to save



    List<ProductModel> productModals;

    ArrayList<CartModal> cartModals;
    StringBuilder arrayList = new StringBuilder();

    ProgressDialog progressDoalog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentCartBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        final RecyclerView recyclerviewList = binding.cartRecyclerviewList;

        String invoiceId = String.valueOf(113);

        GetDeliveryAddressDetails();

        SetScreenViews();

        getCartDetails();

        binding.txtRemoveall.setOnClickListener(v -> {
            RemoveAllCartDetails removeAllCartDetails = new RemoveAllCartDetails(this);
            removeAllCartDetails.RemoveAllHitApi();
        });

        binding.CheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceddutocheckout();
            }
        });
        return root;
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

    public boolean GetPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        }
        else return true;
    }


    public boolean IsGpsEnabled() {
        LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsEnabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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
        Loading.show(requireContext());
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
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
                Toast.makeText(requireContext(), "Error Fetching Location", Toast.LENGTH_SHORT).show();
                Loading.hide();
            }
        });
    }


    private void chooseLocationDialog() {
        // Create the bottom sheet dialog
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.choose_location_bottom_dialog, null);

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
            addressRecyclerView_SavePaymentDetails.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            addressRecyclerView_SavePaymentDetails.setAdapter(new SavePaymentDetails_Address_BottomSheet_Recycler_Adaptor(address_bottomSheet_recycler_adaptor_models, requireContext()));
        }
        if (Variables.address.isEmpty() || Variables.address.matches("") || Variables.address.equals("null") || Variables.address.equals(null)) {
            binding.DeleverHomeLocationPurchasesDateTime.setText("Choose Location");
        } else {
            binding.DeleverHomeLocationPurchasesDateTime.setText(Variables.address);
        }
    }




    private void proceddutocheckout() {
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String ContentType = "application/json";
        String Accept = "application/json";
        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);
        Call<JsonElement> callRetrofit = null;
        callRetrofit = service.AKRUTHTEST(String.valueOf(Variables.id),Variables.token,Variables.addressID,arrayList.toString(), String.valueOf(CartModal.cartAmount));
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
                            String payment_url = lObj.getString("payment_url");
                            String invoice_num = lObj.getString("invoice_num");

                            Intent intent=new Intent(getActivity(), WebViewActivityPayment.class);
                            intent.putExtra("payment_url",payment_url);
                            intent.putExtra("invoice_num",invoice_num);
                            startActivity(intent);

//
                        } catch (Exception e) {
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


    public void getCartDetails() {
        binding.cartRecyclerviewList.showShimmerAdapter();
        GetCartDetails getCartDetails = new GetCartDetails(this);
        getCartDetails.fetchData();
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCartListFetched(ArrayList<CartModal> cartModals) {
        try {
            requireActivity().runOnUiThread(() -> {
                for (int i=0;i<cartModals.size();i++){
                    Log.d("getProductId", String.valueOf(cartModals.get(i).getProductId()));
                    arrayList.append(String.valueOf(cartModals.get(i).getProductId())).append(",");
                }

                binding.cartRecyclerviewList.hideShimmerAdapter();
                binding.cartRecyclerviewList.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.cartRecyclerviewList.setAdapter(new CartRecyclerAdaptor(requireContext(), cartModals, this, this));

//                binding.SubtotalPrice.setText(String.valueOf(CartModal.cartAmount));

                binding.SubTotalPrice.setText("₹  ");
//                binding.SubtotalPrice.append(String.valueOf(CartModal.cartAmount());
                binding.SubTotalPrice.append(String.valueOf(CartModal.getSubTotal()));

                binding.subTotal.setSelected(true);

//                binding.TaxesPrice.setText("₹  ");
//                binding.TaxesPrice.append(String.valueOf(CartModal.getTax()));

//                binding.cgstPrice.setText("₹  ");
//                binding.cgstPrice.append(String.valueOf(CartModal.getCgst()));
//                binding.CgstPercentage.setText(String.valueOf(CartModal.getCgst_percentage()) + " %");



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

//                binding.CgstPercentage.setText("₹  ");
//                binding.CgstPercentage.append(String.valueOf(CartModal.getCgst_percentage()));
//                binding.CgstPercentage.setText("  %");



                binding.sgstPrice.setText("₹  ");
                binding.sgstPrice.append(String.valueOf(CartModal.getSgst()));
                binding.SgstPercentage.setText(String.valueOf(CartModal.getSgst_percentage()) + " %");




                binding.igstPrice.setText("₹  ");
                binding.igstPrice.append(String.valueOf(CartModal.getIgst()));

                binding.IgstPercentage.setText(String.valueOf(CartModal.getIgst_percentage()) + " %");


                binding.totalAmountPrice.setText("₹  ");
                binding.totalAmountPrice.append(String.valueOf(CartModal.getCartAmount()));


                binding.CheckoutButton.setVisibility(View.VISIBLE);

            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }

    }

    @Override
    public void onDataFetchError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {
                try {
                    binding.cartRecyclerviewList.hideShimmerAdapter();
                    binding.NesterScrollViewCart.setVisibility(View.GONE);
                    binding.CheckoutButton.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnCarteditDataFetched(String Message) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            getCartDetails();
        });

    }

    @Override
    public void OnAddtoCartDataFetched(String Message) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            getCartDetails();
        });
    }

    @Override
    public void OnErrorFetched(String Error) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(requireContext(), Error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void OnCartItemDeleted(String message) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            getCartDetails();
        });
    }

    @Override
    public void OnCartItemDeleteAPiGivesError(String error) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            getCartDetails();
        });
    }

    @Override
    public void OnCartItemRemoveAll(String message) {
        requireActivity().runOnUiThread(this::getCartDetails);
    }

    @Override
    public void OnCartItemRemoveAllAPiGivesError(String error) {

    }

}