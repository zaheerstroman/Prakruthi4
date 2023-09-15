package com.prakruthi.ui.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prakruthi.R;

import java.util.List;

public class YourAdapter extends RecyclerView.Adapter<YourAdapter.ViewHolder> {

    private List<ProfileGetUserDataResponse.ProfileGetUserDataModel> dataItems; // Replace DataItem with your actual data model
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    // Constructor
    public YourAdapter(List<ProfileGetUserDataResponse.ProfileGetUserDataModel> dataItems) {
        this.dataItems = dataItems;
    }

    // Create ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pro_edi_layout, parent, false);
        return new ViewHolder(view);
    }

    // Bind data to ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProfileGetUserDataResponse.ProfileGetUserDataModel item = dataItems.get(position);
        holder.myName.setText(item.getName());
        holder.txtEmail.setText(item.getEmail());
        holder.txtNumber.setText(item.getMobile());
    }

    // Get total item count
    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    // Set click listener for item
    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }



    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myName;
        TextView txtEmail;
        TextView txtNumber;

        ViewHolder(View itemView) {
            super(itemView);
            myName = itemView.findViewById(R.id.my_Name);
            txtEmail = itemView.findViewById(R.id.txt_email);
            txtNumber = itemView.findViewById(R.id.txt_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    // ItemClickListener interface
    public interface ItemClickListener {
        void onItemClick(int position);
    }
}