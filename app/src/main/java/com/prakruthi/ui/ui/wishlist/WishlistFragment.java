package com.prakruthi.ui.ui.wishlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.prakruthi.R;
import com.prakruthi.databinding.FragmentWishlistBinding;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.GetWishlistDetails;
import com.prakruthi.ui.APIs.SaveWishList;
import com.prakruthi.ui.misc.Loading;

import java.util.ArrayList;

public class WishlistFragment extends Fragment implements GetWishlistDetails.OnWishlistDataFetchedListener , AddToCart.OnDataFetchedListner , SaveWishList.OnSaveWishListDataFetchedListener {

    private FragmentWishlistBinding binding;

    public static int id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWishlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        id = root.getId();
        getWishlistDetails();

        return root;
    }

    public void getWishlistDetails() {
        Loading.hide();
        binding.wishlistRecyclerviewList.showShimmerAdapter();
        GetWishlistDetails getWishlistDetails = new GetWishlistDetails(this);
        getWishlistDetails.HitWishlistApi();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onWishListFetched(ArrayList<WishListModal> wishListModals) {
        try {
            requireActivity().runOnUiThread(() -> {
                try {
                    binding.wishlistRecyclerviewList.hideShimmerAdapter();
                    binding.wishlistRecyclerviewList.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.wishlistRecyclerviewList.setAdapter(new WishListRecyclerAdaptor(wishListModals , this,this));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            });
        }catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void onDataFetchError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {
                binding.wishlistRecyclerviewList.hideShimmerAdapter();
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void OnCarteditDataFetched(String Message) {

    }

    @Override
    public void OnAddtoCartDataFetched(String Message) {
        requireActivity().runOnUiThread(Loading::hide);
    }

    @Override
    public void OnErrorFetched(String Error) {
        requireActivity().runOnUiThread( () -> {
            Loading.hide();
            Toast.makeText(requireContext(), Error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void OnItemSavedToWishlist(String message) {
        requireActivity().runOnUiThread(this::getWishlistDetails);
    }

    @Override
    public void OnSaveWishlistApiGivesError(String error) {
        requireActivity().runOnUiThread( () -> {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        } );
    }
}