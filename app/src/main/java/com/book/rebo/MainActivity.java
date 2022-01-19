package com.book.rebo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private BroadcastReceiver MyReceiver = null;

    public static final String IMAGE_URL = "https://api.learn2crack.com/android/images/donut.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huawei_quickstart);
//        MyReceiver = new MyReceiver();

//        broadcastIntent();
//        if (isConnected()) {
//            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
//            mImageView = (ImageView) findViewById(R.id.image);
//            new loadImage(this).execute(IMAGE_URL);
//        } else {
//            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//        }
    }



    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//
//            case R.id.btn_load_image:
//
//                break;
//        }
//    }


}