package com.example.deliveryboy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryboy.utils.Global;
import com.example.deliveryboy.utils.SessionManager;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tab1Fragment extends Fragment {

    TextView pickup,delivery,shopname,productname,address,order_status;
    SessionManager sessionManager;
    CardView cardView;
    ImageView imageView;
    public String shop,pick,addres,product,delvery_charge,cancellation_charge,total_amount,payment_method,split,ids;
    SlideToActView sta,sta1;
    private String status;
    private String data;


    private String URLline = Global.BASE_URL+"api_delivery_boy/upcoming_delivery/";
    private String URLlin = Global.BASE_URL+"api_delivery_boy/del_boy_req_accepted/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tab1_fragment, container, false);

        pickup=view.findViewById(R.id.pickTv);
        shopname=view.findViewById(R.id.shop_name);

        productname=view.findViewById(R.id.dateee);
        cardView=view.findViewById(R.id.card);
        cardView.setVisibility(View.GONE);

        address=view.findViewById(R.id.orderType);
        imageView=view.findViewById(R.id.profile_image);
        order_status=view.findViewById(R.id.order_status);
        sessionManager = new SessionManager(view.getContext());
        sta  = (SlideToActView) view.findViewById(R.id.example);
        sta1  = (SlideToActView) view.findViewById(R.id.examplen);


        Log.d("cfffffff","mm"+sessionManager.isCard());

        if (Global.flag){
            cardView.setVisibility(View.VISIBLE);
        //    order_status.setText("Your are now Online");
           callup();
        }
        if (!Global.flag){
            cardView.setVisibility(View.GONE);
        }
        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull final SlideToActView view) {
                Toast.makeText(getContext(),"Accepted the request",Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Do you want to accept this order??");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                status="1";
                                accept();
                                sta.setVisibility(View.GONE);
                                sta1.setVisibility(View.GONE);
                                Intent i=new Intent(getContext(),upcomingdetails.class);
                                i.putExtra("shopname",shop);
                                i.putExtra("pick",pick);
                                i.putExtra("delivery",addres);
                                i.putExtra("product",product);
                                i.putExtra("delcharge",delvery_charge);
                                i.putExtra("concharge",cancellation_charge);
                                i.putExtra("totcharge",total_amount);
                                i.putExtra("payment",payment_method);
                                i.putExtra("image",split);
                                i.putExtra("id",ids);
                                i.putExtra("data",data);
                                Log.d("datttttt","mm"+data);
                                startActivity(i);



                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                view.resetSlider();

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });

     sta1.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(final SlideToActView slideToActView) {

                Toast.makeText(getContext(),"Rejected the request",Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Do you want to reject this order??");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                status="0";
                                accept();
                                sta.setVisibility(View.GONE);
                                sta1.setVisibility(View.GONE);
                                Toast.makeText(getContext(),"Rejected the order",Toast.LENGTH_SHORT).show();

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                slideToActView.resetSlider();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"image"+split,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getContext(),upcomingdetails.class);
                i.putExtra("shopname",shop);
                i.putExtra("pick",pick);
                i.putExtra("delivery",addres);
                i.putExtra("product",product);
                i.putExtra("delcharge",delvery_charge);
                i.putExtra("concharge",cancellation_charge);
                i.putExtra("totcharge",total_amount);
                i.putExtra("payment",payment_method);
                i.putExtra("image",split);
                i.putExtra("id",ids);
                startActivity(i);

            }
        });

        /*if(sessionManager.card=true){

        }

        if(sessionManager.card=false){

        }*/




        return view;
    }

    private void callup(){
        Toast.makeText(getContext(),"hjhkjhjjjjjjjhhhhj",Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  dialog.dismiss();
                        Log.d("TestingResponse","mmm"+response);
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");

                            if(status.equals("203")){
                                Toast.makeText(getContext(),"Currently there is no upcoming deliveries",Toast.LENGTH_SHORT).show();
                                cardView.setVisibility(View.GONE);
                            }

                          //  String token=jsonObject.optString("data");

                           // JSONObject data=jsonObject.getJSONObject("data");

                          //  String dar=jsonObject.getString("data");
                            else{



                            JSONArray dar=jsonObject.getJSONArray("data");
                            if(dar.equals("")){
                                Toast.makeText(getContext(),"Currently there is no upcoming deliveries",Toast.LENGTH_SHORT).show();
                                cardView.setVisibility(View.GONE);
                            }

                            if(dar.length()==0){
                                cardView.setVisibility(View.GONE);
                                Toast.makeText(getContext(),"Currently there is no upcoming deliveries",Toast.LENGTH_SHORT).show();
                            }
                            if(dar.length()!=0){




                           JSONObject jsonObject1=dar.optJSONObject(0);

                           ids=jsonObject1.getString("id");

                            Log.d("DATA","mm"+ids);

                            shop=jsonObject1.getString("shop_name");

                           shopname.setText(shop);

                            pick=jsonObject1.getString("pick_up");
                           pickup.setText(pick);

                             addres=jsonObject1.getString("address");
                             String[]sep=addres.split(",");
                             String sp=sep[0];
                            address.setText(sp);

                             product=jsonObject1.getString("product_name");
                            productname.setText(product);

                             delvery_charge=jsonObject1.getString("delvery_charge");
                             cancellation_charge=jsonObject1.getString("cancellation_charge");
                             total_amount=jsonObject1.getString("total_amount");
                            payment_method=jsonObject1.getString("payment_method");
                            String images1 = jsonObject1.getString("product_image");
                            String[] seperated = images1.split(",");

                            ArrayList<String>images = new ArrayList<>();
                            for (int i =0;i<seperated.length;i++){
                                images.add(Global.BASE_URL+seperated[i].replace("[","").replace("[","").replace("]",""));
                            }
                            split=images.get(0);

                            Picasso.with(getContext()).load(split).into(imageView);


                            Log.d("DATA","mm"+dar);
                            Log.d("code","mm"+status);
                            if(status.equals("200")){
                                Toast.makeText(getContext(), "Successful"+response, Toast.LENGTH_LONG).show();
                               /* Intent intent = new Intent(Login.this, MainUI.class);
                                startActivity(intent);*/
                            }
                            else{
                                Toast.makeText(getContext(), "Failed."+ot, Toast.LENGTH_LONG).show();


                            }
                            }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //   Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("online_status",sessionManager.getID());


                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization","Token "+sessionManager.getTokens());
                Log.d("Tokenccccc","mm"+sessionManager.getTokens());
                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }
    private void accept(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String token=jsonObject.optString("Token");
                             data=jsonObject.optString("data");
                             Global.data=data;
                             Log.d("dataattaatats","mm"+Global.data);
                            //    sessionManager.setTokens(token);




                            Log.d("otp","mm"+token);
                            Log.d("datttttt","mm"+data);
                            Log.d("code","mm"+status);
                            if(status.equals("200")&&(!(ot.equals("verify")))){
                                Toast.makeText(getContext(), "Successful"+response, Toast.LENGTH_LONG).show();



                            }


                            if(!(status.equals("200"))){
                                Toast.makeText(getContext(), "Failed."+ot, Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("delivery_id",ids);
                params.put("status",status);
                Log.d("idlllll","mm"+status);

                return params;
            }

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
