package com.example.deliveryboy;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    public List<PendingPojo> downloadPojos;
    Context context1 ;

    public PendingAdapter(List<PendingPojo> productPojo, Context context) {
        this.downloadPojos = productPojo;
        this.context1 = context;
    }

    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.pending, parent, false);
        PendingAdapter.ViewHolder viewHolder = new PendingAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PendingAdapter.ViewHolder holder, final int position) {


        holder.pname.setText(downloadPojos.get(position).getPname());
        holder.loca.setText(downloadPojos.get(position).getLocation());
        holder.status.setText(downloadPojos.get(position).getStatus());
        Picasso.with(context1).load(downloadPojos.get(position).getImage()).into(holder.image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context1,pendingdetails.class);
                i.putExtra("shopname",downloadPojos.get(position).getShopname());
                i.putExtra("pick",downloadPojos.get(position).getPickup());
                i.putExtra("delivery",downloadPojos.get(position).getLocation());
                i.putExtra("product",downloadPojos.get(position).getPname());
                i.putExtra("delcharge",downloadPojos.get(position).getDc());
                i.putExtra("concharge",downloadPojos.get(position).getCc());
                i.putExtra("totcharge",downloadPojos.get(position).getTotal());
                i.putExtra("payment",downloadPojos.get(position).getPay());
                i.putExtra("image",downloadPojos.get(position).getImage());
                i.putExtra("id",downloadPojos.get(position).getId());
                 context1.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return downloadPojos.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pname,loca,status;
        public ImageView image;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image=itemView.findViewById(R.id.profile_image);
            this.pname=itemView.findViewById(R.id.title);

            this.loca=itemView.findViewById(R.id.desc);


            this.status=itemView.findViewById(R.id.statuse);
            this.cardView=itemView.findViewById(R.id.card);
        }

    }

}
