package com.azhar.rvtoexcel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.azhar.rvtoexcel.R;
import com.azhar.rvtoexcel.model.ModelMain;
import com.azhar.rvtoexcel.network.ApiClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by Azhar Rivaldi on 07-09-2022
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

   private final Context context;
   private ArrayList<ModelMain> modelMainArrayList;

   public MainAdapter(Context context) {
      this.context = context;
      modelMainArrayList = new ArrayList<>();
   }

   private ArrayList<ModelMain> getListDataMain() {
      return modelMainArrayList;
   }

   public void setListDataMain(ArrayList<ModelMain> mainArrayList) {
      this.modelMainArrayList = mainArrayList;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
      return new ViewHolder(itemRow);
   }

   @Override
   public void onBindViewHolder(ViewHolder holder, int position) {
      holder.tvName.setText(getListDataMain().get(position).getStrName());
      holder.tvRelease.setText(getListDataMain().get(position).getStrRelease());
      holder.tvDesc.setText(getListDataMain().get(position).getStrDesc());

      Glide.with(context)
              .load(ApiClient.URLIMAGE + getListDataMain().get(position).getStrPhoto())
              .apply(new RequestOptions().transform(new RoundedCorners(16)))
              .into(holder.imagePhoto);
   }

   @Override
   public int getItemCount() {
      return getListDataMain().size();
   }

   class ViewHolder extends RecyclerView.ViewHolder {
      final TextView tvName;
      final TextView tvRelease;
      final TextView tvDesc;
      final ImageView imagePhoto;

      ViewHolder(View itemView) {
         super(itemView);
         tvName = itemView.findViewById(R.id.tvName);
         tvRelease = itemView.findViewById(R.id.tvDate);
         tvDesc = itemView.findViewById(R.id.tvDesc);
         imagePhoto = itemView.findViewById(R.id.imagePhoto);
      }
   }

}