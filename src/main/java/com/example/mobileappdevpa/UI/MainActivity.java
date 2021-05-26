package com.example.mobileappdevpa.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobileappdevpa.R;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void termList(View view) {
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);
        startActivity(intent);
    }
}