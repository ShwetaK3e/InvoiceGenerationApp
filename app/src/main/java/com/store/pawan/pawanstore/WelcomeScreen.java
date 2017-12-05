package com.store.pawan.pawanstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amitshekhar.DebugDB;
import com.store.pawan.pawanstore.model.EntryItem;

import java.util.LinkedList;
import java.util.List;

public class WelcomeScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

    // Log.i("ADDRESS", DebugDB.getAddressLog());

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
            }
        },2000);
    }
}
