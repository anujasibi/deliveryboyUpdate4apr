package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryboy.utils.Global;
import com.example.deliveryboy.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class pendingdetails extends AppCompatActivity {

    TextView pick,delivery,product,deliveryamount,canamount,totalamo,shop,payment,pickup,deli,submit,deliverysub,paysub;
    ImageView imageView,back;
    Context context=this;
    public String source_lat,source_lng,id,ids,pays;
    SessionManager sessionManager;
    public String status="0";
    private String URLline = Global.BASE_URL+"api_delivery_boy/del_boy_accept/";
    private String URLli = Global.BASE_URL+"api_delivery_boy/del_boy_deliver/";
    private String URLl = Global.BASE_URL+"api_delivery_boy/del_boy_pay_received/";
    private String newur = Global.BASE_URL+"api_delivery_boy/delivery_boy_status/";
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcomingdetails);
        stat();



        sessionManager = new SessionManager(this);

        pick=findViewById(R.id.pick);
        delivery=findViewById(R.id.del);

        deliverysub=findViewById(R.id.deliversub);
        paysub=findViewById(R.id.paysub);

        shop=findViewById(R.id.shopname);
        product=findViewById(R.id.product);
        deli=findViewById(R.id.delivery);

        back=findViewById(R.id.back);

        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*status="1";
                submitnew();*/
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are you sure to pickup the product");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                status="1";
                                submitnew();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                status="0";
                                submitnew();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        deliverysub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*status="1";
                submitnew();*/
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are you sure to deliver the product");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                status="1";
                                delivernew();

                                //  submit.setVisibility(View.GONE);
                                //    deliverysub.setVisibility(View.VISIBLE);
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                status="0";
                                delivernew();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        paysub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*status="1";
                submitnew();*/
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are you sure to deliver the product");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                status="1";
                                paysu();

                                //  submit.setVisibility(View.GONE);
                                //    deliverysub.setVisibility(View.VISIBLE);
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                paysub.setText("Please complete the payment process");
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        deliveryamount=findViewById(R.id.deliveryp);

        totalamo=findViewById(R.id.totalamount);

        payment=findViewById(R.id.payment);

        imageView=findViewById(R.id.img);

        pickup=findViewById(R.id.pickup);

        Bundle bundle = getIntent().getExtras();

        String pickn = bundle.getString("pick");

        pick.setText(pickn);

        String del=bundle.getString("delivery");
        delivery.setText(del);

        String sh=bundle.getString("shopname");
        shop.setText(sh);

        String pro=bundle.getString("product");
        product.setText(pro);

        String delc=bundle.getString("delcharge");
        deliveryamount.setText("₹"+delc);

        String tot=bundle.getString("totcharge");
        totalamo.setText("₹"+tot);

        String pay=bundle.getString("payment");
        pays=bundle.getString("payment");
        payment.setText(pay);

        String sp=bundle.getString("image");
        Picasso.with(context).load(sp).into(imageView);

        id=bundle.getString("id");

        data=bundle.getString("data");
        Log.d("hhhhhhhhhhhhhhhh","mm"+data);


        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(pick.getText().toString(),
                getApplicationContext(), new pendingdetails.GeocoderHandler());

        GeocodingLocation delivry = new GeocodingLocation();
        delivry.getAddressFromLocation(delivery.getText().toString(),
                getApplicationContext(), new pendingdetails.GeocoderHandler1());



        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnMap(source_lat,source_lng);
            }
        });

        deli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOnMap(source_lat,source_lng);
            }
        });



    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String lat,lonh;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    lat= bundle.getString("lat");
                    lonh=bundle.getString("long");
                    Log.d("source","mm"+lat);
                    Log.d("longitude","mm"+lonh);
                    source_lat = lat;
                    source_lng = lonh;
                    Toast.makeText(pendingdetails.this,source_lat+source_lng,Toast.LENGTH_SHORT).show();
                    //  sessionManager.setDestLong(lonh);
                    break;
                default:
                    locationAddress = null;
            }
            //  latLongTV.setText(locationAddress);
            Log.d("locationaddress","mm"+locationAddress);
        }

    }

    private class GeocoderHandler1 extends Handler {
        @Override
        public void handleMessage(Message message) {
            String delivry;
            String lat,lonh;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    delivry = bundle.getString("address");
                    lat= bundle.getString("lat");
                    lonh=bundle.getString("long");
                    Log.d("source","mm"+lat);
                    Log.d("longitude","mm"+lonh);
                    source_lat = lat;
                    source_lng = lonh;
                    Toast.makeText(pendingdetails.this,source_lat+source_lng,Toast.LENGTH_SHORT).show();
                    //  sessionManager.setDestLong(lonh);
                    break;
                default:
                    delivry = null;
            }
            //  latLongTV.setText(locationAddress);
            Log.d("locationaddress","mm"+delivry);
        }

    }
    public void viewOnMap(String ulat,String ulan){
        Uri.Builder directionsBuilder = new Uri.Builder()
                .scheme("https")
                .authority("www.google.com")
                .appendPath("maps")
                .appendPath("dir")
                .appendPath("")
                .appendQueryParameter("api", "1")
                .appendQueryParameter("destination", ulat + "," + ulan);

        startActivity(new Intent(Intent.ACTION_VIEW, directionsBuilder.build()));
    }
    private void submitnew(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
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
                            ids=jsonObject.optString("data");






                            Log.d("code","mm"+status);
                            if(status.equals("200")){
                                Toast.makeText(pendingdetails.this, "Successfully Picked Up", Toast.LENGTH_LONG).show();
                                submit.setVisibility(View.GONE);
                                deliverysub.setVisibility(View.VISIBLE);

                            }
                            if(status.equals("203")){
                                Toast.makeText(pendingdetails.this, "Failed to Pickup."+ot, Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(pendingdetails.this, "Failed."+ot, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(pendingdetails.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("delivery_id",Global.id);
                params.put("status",status);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

    private void delivernew(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLli,
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






                            Log.d("code","mm"+status);
                            if(status.equals("200")){
                                Toast.makeText(pendingdetails.this, "Successful", Toast.LENGTH_LONG).show();
                                if(pays.equals("COD")){
                                    paysub.setVisibility(View.VISIBLE);
                                }
                                if((!pays.equals("COD"))){
                                    deliverysub.setText("COMPLETED");
                                }


                            }
                            else{
                                Toast.makeText(pendingdetails.this, "Failed."+ot, Toast.LENGTH_SHORT).show();


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
                        Toast.makeText(pendingdetails.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("delivery_id",Global.id);
                params.put("deliver_status",status);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    private void paysu(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLl,
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






                            Log.d("code","mm"+status);
                            if(status.equals("200")){
                                Toast.makeText(pendingdetails.this, "Successful", Toast.LENGTH_SHORT).show();
                                paysub.setText("COMPLETED");


                            }
                            else{
                                Toast.makeText(pendingdetails.this, "Failed."+ot, Toast.LENGTH_SHORT).show();


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
                        Toast.makeText(pendingdetails.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("delivery_id",Global.id);
                params.put("payment_status",status);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
    private void stat(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, newur,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("status");
                            String status=jsonObject.optString("code");






                            Log.d("code","mm"+status);
                            if(status.equals("200")){
                                Toast.makeText(pendingdetails.this, "Successfuldfghjkldfghjkl;sdfghjk", Toast.LENGTH_SHORT).show();
                                if(ot.equals("Delivered")){
                                    submit.setVisibility(View.GONE);
                                    deliverysub.setVisibility(View.GONE);
                                    paysub.setVisibility(View.VISIBLE);
                                }


                            }
                            else{
                                Toast.makeText(pendingdetails.this, "Failed."+ot, Toast.LENGTH_SHORT).show();


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
                        Toast.makeText(pendingdetails.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("order_id",id);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token "+sessionManager.getTokens());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }
}
