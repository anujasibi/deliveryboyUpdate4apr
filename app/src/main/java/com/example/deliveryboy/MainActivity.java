package com.example.deliveryboy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryboy.utils.Global;
import com.example.deliveryboy.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAnalytics firebaseAnalytics;
    private String token_firebase;
    Context context=this;

    TextView Login;
    TextInputEditText phoneno,password;

    private String URLline = Global.BASE_URL+"api_delivery_boy/delvry_boy_login/";
    private ProgressDialog dialog ;
    SessionManager sessionManager;
    boolean doubleBackToExitPressedOnce = false;
    String device_id = null;
    Activity activity = this;
    ImageView imageView;
    TextView forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        token_firebase = FirebaseInstanceId.getInstance().getToken();
        Log.d("tokkkken","lhykhiyh"+token_firebase);
        imageView=findViewById(R.id.img);


        forget=findViewById(R.id.forgot);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,ForgotPassword.class));
            }
        });

        dialog=new ProgressDialog(MainActivity.this,R.style.MyAlertDialogStyle);
        sessionManager = new SessionManager(this);
        // Toast.makeText(Login.this,"hvjhvv"+sessionManager.getTokens(),Toast.LENGTH_SHORT).show();
    /*  if (sessionManager.getTokens() == null){

       }       if (!(sessionManager.getTokens() == null)){
        dialog.setMessage("Logging in ...");
        dialog.show();
            startActivity(new Intent(Login.this,MainUI.class));
          dialog.dismiss();
       }*/
        Picasso.with(context).load(Global.BASE_URL+"media/files/events_add/extra_pic/haahoo_logo1.png").into(imageView);

        Login=findViewById(R.id.login);


        phoneno=findViewById(R.id.name);
        password=findViewById(R.id.namee);
        device_id =  Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals("")||phoneno.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"All Field Required",Toast.LENGTH_LONG).show();

                }
                else {
                    dialog.setMessage("Loading..");
                    dialog.show();
                    loginuser();

                }
            }
        });

    }

    private void loginuser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        //  Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String token=jsonObject.optString("Token");
                            sessionManager.setTokens(token);




                            Log.d("otp","mm"+token);
                            Log.d("code","mm"+status);
                            if(status.equals("200")){
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                               Intent intent = new Intent(MainActivity.this, MainUI.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Login Failed."+ot, Toast.LENGTH_LONG).show();


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
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phoneno.getText().toString());
                params.put("password",password.getText().toString());
                params.put("fire_token",token_firebase);
                params.put("device_id",device_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(MainActivity.this,"Press again to exit",Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
