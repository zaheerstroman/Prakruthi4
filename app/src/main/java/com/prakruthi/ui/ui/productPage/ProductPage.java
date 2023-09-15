package com.prakruthi.ui.ui.productPage;

import static com.google.firebase.messaging.Constants.TAG;
import static com.prakruthi.ui.ui.AddReviewsUserDetails.rating;
import static com.prakruthi.ui.ui.AddReviewsUserDetails.review;
import static com.prakruthi.ui.utils.Constants.CART;

import com.prakruthi.databinding.ActivityProductPageBinding;
import com.prakruthi.databinding.FragmentHomeBinding;
import com.prakruthi.databinding.FragmentWishlistBinding;
import com.prakruthi.ui.APIs.FeedBackApi;
import com.prakruthi.ui.APIs.GetUserDataApi;
import com.prakruthi.ui.APIs.SavePaymentDetailsApi;
import com.prakruthi.ui.HomeActivity;
import com.prakruthi.ui.Utility.AvenuesParams;
import com.prakruthi.ui.Utility.ServiceUtility;
import com.prakruthi.ui.ui.cart.CartFragment;
import com.prakruthi.ui.ui.profile.AboutUsWebViewActivity;
import com.prakruthi.ui.ui.profile.EditProfileFragmentHttpURLConnection;
import com.prakruthi.ui.ui.profile.ProfileGetUserDataResponse;
import com.prakruthi.ui.utils.Constants;
import com.willy.ratingbar.ScaleRatingBar;

//import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddMyOrdersProductReviews;
import com.prakruthi.ui.APIs.AddRecentViewProductsAPI;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.GetProductDetails;
import com.prakruthi.ui.APIs.GetProductReviews;
import com.prakruthi.ui.APIs.SaveWishList;
//import com.prakruthi.ui.Utility.Constants;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.AddReviewsUserDetails;
import com.prakruthi.ui.ui.UserDetails;
import com.prakruthi.ui.ui.productPage.productReviews.ProductReviewsAdaptor;
import com.prakruthi.ui.ui.productPage.productReviews.ProductReviewsModel;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.willy.ratingbar.RotationRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductPage extends AppCompatActivity implements GetProductDetails.OnProductDataFetched, AddToCart.OnDataFetchedListner, SaveWishList.OnSaveWishListDataFetchedListener, GetProductReviews.OnGetProductReviewsHits, SavePaymentDetailsApi.OnSavePaymentDetailsApiHitListner {

    String categoryId, category_id;

    String productId;

    public SharedPreferences sharedPreferences;

    GetProductReviews.OnGetProductReviewsHits mListner;

    public static String address;

    public static String product_ids;
    public static String total_amount;

    ArrayList<ProductModel> productModals;
    TextView ProductName, ProductDescription, CurrentPrice, MRPPrice, ProductDeleveryAddress,
            Avalable, Ratingcount, VoewAllRating;

    PowerSpinnerView Qty;
    AppCompatButton AddtoCart, BuyNow;
    DotsIndicator dotsIndicator;
    AppCompatButton Wishlist, productPage_back_btn;

    AppCompatButton Write;

    RelativeLayout ll_site_visit_req_LastUpdated4_http_details;
    ViewPager2 ProductImagePager;
    RotationRatingBar ratingBar;
    ShimmerRecyclerView ReviewsrecyclerView, ReviewsrecyclerView_add;

    String amount, order_ID;

    boolean Reviewable;
    boolean in_wishlist;

    private ActivityProductPageBinding binding;

    public static int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        binding = ActivityProductPageBinding.inflate(getLayoutInflater());


        // Retrieve the product ID from the intent
        productId = getIntent().getStringExtra("productId");

//        BuyNow/SavePaymentDetailsActivity
        SetViews();

        //GetProductDetails+AddRecentViewProductsAPI
        GetApiData();

        //Wishlist(SaveWishList)in_wishlist/VoewAllRating(GetProductReviews)/AddtoCart(Qty)/BuyNow(Qty)AddToCart:--
        Clicks();
//        SetTextViews();

        View root = binding.getRoot();

        id = root.getId();
        address = String.valueOf(root.getId());
        product_ids = String.valueOf(root.getId());
        total_amount = String.valueOf(root.getId());

    }

    private void SetViews() {
        productPage_back_btn = findViewById(R.id.productPage_back_btn);
        ProductImagePager = findViewById(R.id.ProductImagePager);
        dotsIndicator = findViewById(R.id.dots_indicator);
        ProductName = findViewById(R.id.ProductName);
        ProductDescription = findViewById(R.id.ProductDescription);
        CurrentPrice = findViewById(R.id.CurrentPrice);
        MRPPrice = findViewById(R.id.MRPPrice);
        ProductDeleveryAddress = findViewById(R.id.ProductDeleveryAddress);
        Avalable = findViewById(R.id.Avalable);
        Qty = findViewById(R.id.Qty);
        AddtoCart = findViewById(R.id.AddtoCart);
        BuyNow = findViewById(R.id.BuyNow);
        Wishlist = findViewById(R.id.Product_Save_Wishlist);

        ratingBar = findViewById(R.id.simpleRatingBar);
        Ratingcount = findViewById(R.id.RatingCount);
        VoewAllRating = findViewById(R.id.ViewAllRatimg);


        if (BuyNow != null) {
            BuyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(ProductPage.this, CCAvenueBuyNowPageActivity.class));
//                    startActivity(new Intent(ProductPage.this, CartFragment.class));
                    startActivity(new Intent(ProductPage.this, SavePaymentDetailsActivity.class));


                }
            });
        }

    }


    private int getTotal(List<ProductModel> products) {

        int TotalPrice = 0;
        for (ProductModel product : products) {
            TotalPrice = Integer.parseInt(TotalPrice + product.getCustomerPrice());
        }
        return TotalPrice;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //generating new order number for every transaction
        Integer randomNum = ServiceUtility.randInt(0, 9999999);
        order_ID = randomNum.toString();
    }

    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }

    private void Clicks() {

        productPage_back_btn.setOnClickListener(v -> super.onBackPressed());

        Wishlist.setOnClickListener(v -> {

            if (in_wishlist) {
                SaveWishList saveWishList = new SaveWishList(this, productId);
                saveWishList.HitSaveWishListApi("No");
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.like_outline));
            } else if (!in_wishlist) {
                SaveWishList saveWishList = new SaveWishList(this, productId);
                saveWishList.HitSaveWishListApi("Yes");
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.like_filled));
            }

        });

        AddtoCart.setOnClickListener(v -> {
            Qty.setError(null);
            if (Qty.getText().toString().isEmpty()) {
                Qty.setError("Select Quantity");
                ObjectAnimator.ofFloat(Qty, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                Qty.requestFocus();
                return;
            }
            Loading.show(this);
            AddToCart addToCart = new AddToCart(productId, String.valueOf(Qty.getSelectedIndex() + 1), String.valueOf(Qty.getSelectedIndex() + 1), false, this);
            addToCart.fetchData();
        });


        BuyNow.setOnClickListener(v -> {
            Qty.setError(null);
            if (Qty.getText().toString().isEmpty()) {
                Qty.setError("Select Quantity");
                ObjectAnimator.ofFloat(Qty, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                Qty.requestFocus();
                return;
            }
            Intent intent=new Intent(ProductPage.this, SavePaymentDetailsActivity.class);

//            Intent intent=new Intent(ProductPage.this, HomeActivity.class);
            intent.putExtra("cart","1");
            startActivity(intent);


            Loading.show(this);

            AddToCart addToCart = new AddToCart(productId, String.valueOf(Qty.getSelectedIndex() + 1), String.valueOf(Qty.getSelectedIndex() + 1), false, this);
            addToCart.fetchData();

        });

        //----------
        VoewAllRating.setOnClickListener(v -> {

            {

                ArrayList<AddMyOrdersProductReviews> reviews = new ArrayList<>(); // Initialize your reviews list

                //Get:--------
                // Inflate the custom layout
                View bottomSheetView = getLayoutInflater().inflate(R.layout.product_reviews_bottom_popup, null);

                AppCompatButton Write = bottomSheetView.findViewById(R.id.Write);

                Write.setOnClickListener(view -> {
                    if (Reviewable) {
                        WriteAReview();
//                        AddRatingReviewFeedBackDialog();
                    } else {
                        Toast.makeText(this, "Purchase Product To Review", Toast.LENGTH_SHORT).show();
                    }
                });


                // Find the RecyclerView in the layout
                ReviewsrecyclerView = bottomSheetView.findViewById(R.id.recyclerView);
                // Set up your RecyclerView (e.g., set adapter, layout manager, etc.)
                ReviewsrecyclerView.showShimmerAdapter();

                Log.e(TAG, "OnDataFetched: ");

                // Create the bottom sheet dialog
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProductPage.this);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                GetProductReviews getProductReviews = new GetProductReviews(productId, this);
                getProductReviews.HitReviewsApi();

            }
        });
    }


    //ALI:--
    //------------------------

    public void WriteAReview() {
        // Create a custom dialog instance
        Dialog dialog = new Dialog(ProductPage.this);
        dialog.setContentView(R.layout.custom_dialog_layout);

        Button post = dialog.findViewById(R.id.Post);
        RotationRatingBar rotationRatingBar = dialog.findViewById(R.id.ReviewRatingBar);
        EditText review = dialog.findViewById(R.id.review);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (review.getText().toString().isEmpty()) {
                    review.setError("Cannnot Be Empty");
                } else {
                    AddMyOrdersProductReviews addProductReviews = new AddMyOrdersProductReviews(productId, String.valueOf(rotationRatingBar.getRating()), review.getText().toString(), new AddMyOrdersProductReviews.OnAddMyOrdersProductReviewsHitsListener() {

                        @Override
                        public void OnAddProductReviewsResult() {
                            runOnUiThread(() -> {
                                GetProductReviews getProductReviews = new GetProductReviews(productId, ProductPage.this);

                                getProductReviews.HitReviewsApi();
                                Toast.makeText(ProductPage.this, "Review Added", Toast.LENGTH_SHORT).show();
                                dialog.dismiss(); // Close the dialog
                            });

                        }

                        @Override
//                        public void OnAddProductReviewsError(String error) {
                        public void OnGetProductReviewsError(String error) {

                            runOnUiThread(() -> {
                                GetProductReviews getProductReviews = new GetProductReviews(productId, ProductPage.this);
                                getProductReviews.HitReviewsApi();
                                Toast.makeText(ProductPage.this, error, Toast.LENGTH_SHORT).show();
                                dialog.dismiss(); // Close the dialog
                            });

                        }
                    });
                    addProductReviews.HitAddMyOrdersProductReviewsApi();
                }

            }
        });
        dialog.show(); // Show the dialog

    }


//--------


    public void GetApiData() {
        GetProductDetails getProductDetails = new GetProductDetails(this, productId);
        getProductDetails.fetchData();
        AddRecentViewProductsAPI addRecentViewProductsAPI = new AddRecentViewProductsAPI(productId);
        addRecentViewProductsAPI.HitRecentApi();
    }


    @Override
    public void OnDataFetched(ProductModel productModel) {
        this.runOnUiThread(() -> {

            ProductImagePager.setAdapter(new ProductPagerAdaptor(this, productModel.getAttachments()));
            dotsIndicator.attachTo(ProductImagePager);
            ProductName.setText(productModel.getName());
            ProductDescription.setText(productModel.getDescription());

            if (Variables.departmentId.equals(2)) {
//                CurrentPrice.setText("₹ : ");
                CurrentPrice.setText("₹  ");

                CurrentPrice.append(productModel.getCustomerPrice());
            } else if (Variables.departmentId.equals(3)) {

                CurrentPrice.setText("₹  ");
                CurrentPrice.append(productModel.getDealerPrice());
            } else if (Variables.departmentId.equals(4)) {

                CurrentPrice.setText("₹  ");
                CurrentPrice.append(productModel.getMartPrice());
            }

            MRPPrice.setText(" M.R.P ₹  ");
            MRPPrice.append(productModel.getActualPrice());
            MRPPrice.setPaintFlags(MRPPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ProductDeleveryAddress.setText(Variables.address);

            in_wishlist = productModel.isIn_wishlist();
            if (in_wishlist)
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.like_filled));


            ratingBar.setRating(Float.parseFloat(productModel.getRating()));
//            Ratingcount.setText(productModel.getCount_rating());
            Ratingcount.setText(productModel.getRating());


        });
        Reviewable = productModel.getIs_review();

    }

    @Override
    public void OnDataFetchError(String message) {
        this.runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            PopupDialog.getInstance(this).setStyle(Styles.FAILED).setHeading("Uh-Oh").setDescription("Unexpected error occurred." + " Try again later.").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    super.onDismissClicked(dialog);
                }
            });
        });
    }

    @Override
    public void OnCarteditDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
        });

    }

    @Override
    public void OnAddtoCartDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
            PopupDialog.getInstance(this).setStyle(Styles.SUCCESS).setHeading("Well Done").setDescription("Successfully" + " Added Into The Cart").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    super.onDismissClicked(dialog);
                }
            });
        });
    }

    @Override
    public void OnErrorFetched(String Error) {
        this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(this, Error, Toast.LENGTH_SHORT).show();
            PopupDialog.getInstance(this).setStyle(Styles.FAILED).setHeading("Uh-Oh").setDescription("Unexpected error occurred." + " Try again later.").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    super.onDismissClicked(dialog);
                }
            });
        });
    }

    @Override
    public void OnItemSavedToWishlist(String message) {
        runOnUiThread(() -> {
            GetApiData();
        });

    }

    @Override
    public void OnSaveWishlistApiGivesError(String error) {
        runOnUiThread(() -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void OnGetProductReviewsResult
            (ArrayList<ProductReviewsModel> productReviewsModels) {
        try {
            runOnUiThread(() -> {
                ReviewsrecyclerView.setLayoutManager(new LinearLayoutManager(ProductPage.this));
                ReviewsrecyclerView.setAdapter(new ProductReviewsAdaptor(productReviewsModels));
                ReviewsrecyclerView.hideShimmerAdapter();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnGetProductReviewsError(String error) {
        ProductPage.this.runOnUiThread( () -> {
            Loading.hide();
            Toast.makeText(ProductPage.this, error, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void OnSavePaymentDtailsResult(String product) {
        ProductPage.this.runOnUiThread(Loading::hide);

    }

    @Override
    public void OnOnSavePaymentDtailsResultApiGivesError(String error) {
        ProductPage.this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(ProductPage.this, error, Toast.LENGTH_SHORT).show();
        });

    }

}