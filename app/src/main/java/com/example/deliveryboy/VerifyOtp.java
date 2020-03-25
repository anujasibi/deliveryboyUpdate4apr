package com.example.deliveryboy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class VerifyOtp extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Activity activity = this;
    String phone_no = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
    }
}
