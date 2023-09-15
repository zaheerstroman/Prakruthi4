package com.prakruthi.ui.ui.search;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.GetFilterApi;
import com.prakruthi.ui.APIs.SearchProductApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class SearchPage extends AppCompatActivity implements SearchProductApi.OnSearchResultApiHit, GetFilterApi.OnFilterAPiHit {

    EditText editText;

    AppCompatButton back;
    TextView SortBy, filters , ResetFilters;
    ShimmerRecyclerView searchRecyclerView;
    String order = "";
    String type = "";
    String color = "";

    RadioGroup typegroup;
    RadioGroup colorgroup;
    SearchAdaptor searchAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        SetViews();
        ClickListners();
        SearchLogic();

    }

    private void SetViews() {
        back = findViewById(R.id.search_back_btn);
        searchRecyclerView = findViewById(R.id.SearchRecyclerView);
        editText = findViewById(R.id.Search);
        SortBy = findViewById(R.id.SortBy);
        filters = findViewById(R.id.filters);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void ClickListners() {
        back.setOnClickListener(v -> super.onBackPressed());
        SortBy.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchPage.this);
            View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);


            RadioButton radioRecommended = bottomSheetView.findViewById(R.id.radioRecommended);
            RadioButton radioAscending = bottomSheetView.findViewById(R.id.radioAscending);
            RadioButton radioDescending = bottomSheetView.findViewById(R.id.radioDescending);

            radioAscending.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle the "Sort by Ascending" radio button selection
                    // Perform sorting operation in ascending order
                    order = "asc";
                    performSearch();
                    bottomSheetDialog.dismiss();
                }
            });

            radioDescending.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle the "Sort by Descending" radio button selection
                    // Perform sorting operation in descending order
                    order = "desc";
                    performSearch();
                    bottomSheetDialog.dismiss();
                }
            });

            radioRecommended.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Handle the "Sort by Descending" radio button selection
                    // Perform sorting operation in descending order
                    order = "";
                    performSearch();
                    bottomSheetDialog.dismiss();
                }
            });

            //Zaheer /Ali
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            // //Sriniwas
            //            radioRecommended.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //                if (isChecked) {
            //                    // Handle the "Sort by Descending" radio button selection
            //                    // Perform sorting operation in descending order
            //                    order = "";
            //                    performSearch();
            //                    bottomSheetDialog.dismiss();
            //                }
            //            });
        });
        filters.setOnClickListener(v -> {
            GetFilterApi getFilterApi = new GetFilterApi(this);
            getFilterApi.HitApi();
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(SearchPage.this);
            View bottomSheetView = getLayoutInflater().inflate(R.layout.filters_bottom_sheet, null);

            ResetFilters = bottomSheetView.findViewById(R.id.ResetFilter);
            typegroup = bottomSheetView.findViewById(R.id.type_group);
            colorgroup = bottomSheetView.findViewById(R.id.color_group);

            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

        });
    }

    private void SearchLogic() {
        editText.requestFocus();
        if (editText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        editText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            performSearch();
            return false;
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void performSearch() {
        if (editText.getText().toString().isEmpty()) {
            ObjectAnimator.ofFloat(editText, "translationX", 0, -20, 20, -10, 10, -20, 10, -20, 20, 0).start();
            editText.requestFocus();
        } else {
            searchRecyclerView.setVisibility(View.VISIBLE);
            searchRecyclerView.showShimmerAdapter();
            SearchProductApi searchProductApi = new SearchProductApi(this, editText.getText().toString(), order , type , color);
            searchProductApi.HitSearchApi();
        }
    }

    @Override
    public void OnSearchResult(List<SearchModle> product) {
        runOnUiThread(() -> {
            searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            searchRecyclerView.hideShimmerAdapter();
            searchAdaptor = new SearchAdaptor(product);
            searchRecyclerView.setAdapter(searchAdaptor);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void OnSearchResultApiGivesError(String error) {
        runOnUiThread(() -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            searchRecyclerView.hideShimmerAdapter();
            if (searchAdaptor.searchModles != null)
            {
                searchAdaptor.searchModles.clear();
                searchRecyclerView.getActualAdapter().notifyDataSetChanged();
                searchRecyclerView.scrollToPosition(0);
            }

        });
    }

    public void ShowFiltrType(JSONObject result, RadioGroup group) {
        try
        {
            JSONArray types = result.getJSONArray("product_type_details");
            for (int i = 0; i < types.length(); i++) {
                JSONObject type = types.getJSONObject(i);
                ToggleButton toggleButton = new ToggleButton(this);
                toggleButton.setId(View.generateViewId());
                toggleButton.setTextOn(type.getString("type")); // Set text for checked state
                toggleButton.setTextOff(type.getString("type")); // Set text for unchecked state
                toggleButton.setChecked(false); // Set the initial state to unchecked

                toggleButton.setButtonDrawable(null);

                // Create LayoutParams with desired width and height
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // Set margins
                int leftMargin = 16; // Example value, adjust as needed
                int topMargin = 8; // Example value, adjust as needed
                int rightMargin = 16; // Example value, adjust as needed
                int bottomMargin = 8; // Example value, adjust as needed

                // Create MarginLayoutParams and set margins
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
                marginLayoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                toggleButton.setLayoutParams(marginLayoutParams);

                // Set the selector drawable as the background
                toggleButton.setBackgroundResource(R.drawable.custom_button_selector);
                toggleButton.setTextSize(20);

                toggleButton.setGravity(Gravity.CENTER_HORIZONTAL);

                // Customize background tint for selected state
                int selectedTint = ContextCompat.getColor(this, R.color.Secondary_less);
                toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        buttonView.setBackgroundTintList(ColorStateList.valueOf(selectedTint));
                        this.type = String.valueOf(toggleButton.getText());
                        performSearch();
                    } else {
                        this.type = "";
                        performSearch();
                        buttonView.setBackgroundTintList(null);
                    }
                });

                // Add the ToggleButton to the RadioGroup
                group.addView(toggleButton, marginLayoutParams);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void ShowFiltrClr(JSONObject result, RadioGroup group) {
        try
        {
            JSONArray types = result.getJSONArray("product_color_details");
            for (int i = 0; i < types.length(); i++) {
                JSONObject type = types.getJSONObject(i);
                ToggleButton toggleButton = new ToggleButton(this);
                toggleButton.setId(View.generateViewId());
                toggleButton.setTextOn(type.getString("color")); // Set text for checked state
                toggleButton.setTextOff(type.getString("color")); // Set text for unchecked state
                toggleButton.setChecked(false); // Set the initial state to unchecked

                toggleButton.setButtonDrawable(null);

                // Create LayoutParams with desired width and height
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                // Set margins
                int leftMargin = 16; // Example value, adjust as needed
                int topMargin = 8; // Example value, adjust as needed
                int rightMargin = 16; // Example value, adjust as needed
                int bottomMargin = 8; // Example value, adjust as needed

                // Create MarginLayoutParams and set margins
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
                marginLayoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                toggleButton.setLayoutParams(marginLayoutParams);

                // Set the selector drawable as the background
                toggleButton.setBackgroundResource(R.drawable.custom_button_selector);
                toggleButton.setTextSize(20);

                toggleButton.setGravity(Gravity.CENTER_HORIZONTAL);

                // Customize background tint for selected state
                int selectedTint = ContextCompat.getColor(this, R.color.Secondary_less);
                toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        buttonView.setBackgroundTintList(ColorStateList.valueOf(selectedTint));
                        this.color = String.valueOf(toggleButton.getText());
                        performSearch();
                    } else {
                        this.color = "";
                        performSearch();
                        buttonView.setBackgroundTintList(null);
                    }
                });

                // Add the ToggleButton to the RadioGroup
                group.addView(toggleButton, marginLayoutParams);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void OnFilterApiSuccess(JSONObject result) {
        runOnUiThread(() -> {
            ShowFiltrType(result, typegroup);
            ShowFiltrClr(result, colorgroup);
            ResetFilters.setOnClickListener(v -> {
                order = "";
                type = "";
                color = "";
                typegroup.clearCheck();
                colorgroup.clearCheck();

                // Clear all toggle buttons in typegroup
                for (int i = 0; i < typegroup.getChildCount(); i++) {
                    View child = typegroup.getChildAt(i);
                    if (child instanceof ToggleButton) {
                        ToggleButton toggleButton = (ToggleButton) child;
                        toggleButton.setChecked(false);
                        toggleButton.setBackgroundTintList(null);
                    }
                }

                // Clear all toggle buttons in colorgroup
                for (int i = 0; i < colorgroup.getChildCount(); i++) {
                    View child = colorgroup.getChildAt(i);
                    if (child instanceof ToggleButton) {
                        ToggleButton toggleButton = (ToggleButton) child;
                        toggleButton.setChecked(false);
                        toggleButton.setBackgroundTintList(null);
                    }
                }

                performSearch();
            });
        });
    }
    @Override
    public void OnFilterApiFailed(String result) {

    }
}