package com.example.deliveryboy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryboy.utils.Global;
import com.example.deliveryboy.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tab3Fragment extends Fragment {
    RecyclerView recyclerView;
    String token = null;

    ArrayList<PendingPojo> dataModelArrayList;
    private PendingAdapter orderAdapter;
    SessionManager sessionManager;
    private String URLline = Global.BASE_URL+"api_delivery_boy/delivery_boy_pendings/";
    private ProgressDialog dialog ;
    boolean doubleBackToExitPressedOnce = false;
    TextView pickup,delivery,shopname,productname,address,order_status;
    CardView cardView;
    public String shop,pick,addres,product,delvery_charge,cancellation_charge,total_amount,payment_method,split,ids;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tab3_fragment, container, false);

        pickup=view.findViewById(R.id.pickTv);
        shopname=view.findViewById(R.id.shop_name);

        productname=view.findViewById(R.id.dateee);
        cardView=view.findViewById(R.id.orderCardview);

        address=view.findViewById(R.id.orderType);
        order_status=view.findViewById(R.id.order_status);
        sessionManager = new SessionManager(view.getContext());
        recyclerView = view.findViewById(R.id.re);

        fetchingJSON();





        return view;


    }
    private void fetchingJSON() {


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // dialog.dismiss();

                        try {

                            JSONObject obj = new JSONObject(response);
                            Log.d("ssssd", "resp" + obj);
                            // amount.setText(obj.optString("total"));
                            dataModelArrayList = new ArrayList<>();
                            String total=obj.optString("total");
                            JSONArray dataArray  = obj.getJSONArray("data");

                            if(dataArray.length() == 0){
                                Toast.makeText(getContext(),"Nothing to display",Toast.LENGTH_SHORT).show();
                            }

                            for (int i = 0; i < dataArray.length(); i++) {

                                PendingPojo playerModel = new PendingPojo();
                                JSONObject dataobj = dataArray.getJSONObject(i);
                                //   playerModel.setProductname(dataobj.optSt ring("name"));
                                // ApiClient.productids.add(dataobj.optString("id"));
                                playerModel.setPname(dataobj.optString("pdt_name"));
                                playerModel.setLocation(dataobj.optString("address"));
                                playerModel.setStatus(dataobj.optString("status"));
                                String images1 = dataobj.getString("pdt_image");
                                String[] seperated = images1.split(",");

                                ArrayList<String>images = new ArrayList<>();
                                for (int j =0;j<seperated.length;j++){
                                    images.add(Global.BASE_URL+seperated[j].replace("[","").replace("[","").replace("]",""));
                                }
                                split=images.get(0);
                                playerModel.setImage(split);
                                //  playerModel.setStatus("");

                                playerModel.setId(dataobj.getString("pas_id"));
                                Global.id=dataobj.getString("pas_id");
                                playerModel.setShopname(dataobj.getString("shop_name"));
                                playerModel.setPickup(dataobj.getString("pick_up"));



                                playerModel.setDc(dataobj.getString("delvery_charge"));
                                playerModel.setCc(dataobj.getString("cancellation_charge"));
                               playerModel.setTotal(dataobj.getString("total_amount"));
                                playerModel.setPay(dataobj.getString("payment_method"));


                                dataModelArrayList.add(playerModel);

                            }

                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Internal Server Error",Toast.LENGTH_LONG).show();


            }
        }) {

            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + sessionManager.getTokens());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void setupRecycler(){

        orderAdapter = new PendingAdapter(dataModelArrayList, getContext());
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

}

