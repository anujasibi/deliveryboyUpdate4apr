package com.example.deliveryboy;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.deliveryboy.utils.SessionManager;


public class splashscreen extends AppCompatActivity {
    Activity activity = this;
    SessionManager sessionManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        sessionManager = new SessionManager(this);
        Toast.makeText(splashscreen.this,"token"+sessionManager.getTokens().length(),Toast.LENGTH_SHORT).show();


// finally change the color
        window.setStatusBarColor(activity.getResources().getColor(R.color.black));

        Handler handler;




        if (sessionManager.getTokens().length() == 0){

            handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(splashscreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);

        }
        if (!(sessionManager.getTokens().length() == 0)){
            handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(splashscreen.this,MainUI.class);
                    startActivity(intent);
                    finish();
                }
            },3000);
        }

    }

}