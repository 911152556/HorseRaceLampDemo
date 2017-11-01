package com.zhuwb.horseracelampdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class ModelActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

       textView=findViewById(R.id.model_IMEI);
       textView.setText("Product Model: " + android.os.Build.MODEL + ","
               + android.os.Build.VERSION.SDK + ","
               + android.os.Build.VERSION.RELEASE);
    }




}
