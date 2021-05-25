package com.example.mobileappdevpa.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mobileappdevpa.Database.InventoryManagementRepository;
import com.example.mobileappdevpa.R;

public class TermsActivity extends AppCompatActivity {

    private InventoryManagementRepository inventoryManagementRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        inventoryManagementRepository = new InventoryManagementRepository(getApplication());
        inventoryManagementRepository.getAllTerms();

        CourseActivity.id2 = -1;


        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(inventoryManagementRepository.getAllTerms());

    }


    public void toAddTerm(View view) {
        Intent intent = new Intent(TermsActivity.this, TermDetailsActivity.class);
        startActivity(intent);
    }


}