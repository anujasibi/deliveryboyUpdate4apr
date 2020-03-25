package com.example.deliveryboy;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public List<HistoryPojo> downloadPojos;
    Context context1 ;

    public HistoryAdapter(List<HistoryPojo> productPojo, Context context) {
        this.downloadPojos = productPojo;
        this.context1 = context;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.history_row, parent, false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {

        holder.pickup.setText(downloadPojos.get(position).getPickup());
        holder.shopname.setText(downloadPojos.get(position).getShopname());
        holder.productname.setText(downloadPojos.get(position).getProduct());
        holder.address.setText(downloadPojos.get(position).getDelivery());
    }

    @Override
    public int getItemCount() {
        return downloadPojos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pickup,shopname,productname,address;

        public ViewHolder(View itemView) {
            super(itemView);
            this.pickup=itemView.findViewById(R.id.pickTv);
            this.shopname=itemView.findViewById(R.id.shop_name);

            this.productname=itemView.findViewById(R.id.dateee);


           this.address=itemView.findViewById(R.id.orderType);
        }

    }

}
