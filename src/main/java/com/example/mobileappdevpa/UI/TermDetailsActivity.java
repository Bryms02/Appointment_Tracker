package com.example.mobileappdevpa.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileappdevpa.Database.InventoryManagementRepository;
import com.example.mobileappdevpa.Entity.CourseEntity;
import com.example.mobileappdevpa.Entity.TermEntity;
import com.example.mobileappdevpa.R;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetailsActivity extends AppCompatActivity {

    private InventoryManagementRepository inventoryManagementRepository;

    public static final String TAG = "TermDetailsActivity";
    TextView mDisplayEndDate;
    TextView mDisplayStartDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    DatePickerDialog.OnDateSetListener mDateEndSetListener;

    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();





    String name;
    String startDate;
    String endDate;

    EditText editName;
    TermEntity currentTerm;

    public static int numCourses;

    long date;




    int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        mDisplayEndDate = (TextView) findViewById(R.id.termEnd);
        mDisplayStartDate = (TextView) findViewById(R.id.termStart);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
;


        Id = getIntent().getIntExtra("termId", -1);
        if (Id==-1)Id= CourseActivity.id2;
        inventoryManagementRepository = new InventoryManagementRepository(getApplication());

        List<TermEntity> allTerms = inventoryManagementRepository.getAllTerms();

        for(TermEntity t:allTerms){
            if(t.getTermId()==Id)currentTerm = t;
        }

        AssessmentActivity.id3 = -1;



        editName = findViewById(R.id.termNameEditText);
        mDisplayStartDate = findViewById(R.id.termStart);
        mDisplayEndDate = findViewById(R.id.termEnd);
        if(currentTerm != null)
        {
            name = currentTerm.getTermName();
            startDate = currentTerm.getTermStartDate();
            endDate = currentTerm.getTermEndDate();

        }

        if(Id != -1){
            editName.setText(name);
            mDisplayStartDate.setText(startDate);
            mDisplayEndDate.setText(endDate);
        }

        inventoryManagementRepository = new InventoryManagementRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.recyclerviewCourses);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CourseEntity> filteredCourses = new ArrayList<>();
        //OrderBy ID here

        for(CourseEntity c:inventoryManagementRepository.getAllCourses()){
            if(c.getTermId()==Id)filteredCourses.add(c);
        }

        numCourses = filteredCourses.size();

        courseAdapter.setCourse(filteredCourses);






        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                mDisplayStartDate.setText(sdf.format(myCalendarStart.getTime()));

            }
        };


        mDisplayStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermDetailsActivity.this, mDateSetListener, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();

            }


        });


        mDateEndSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                mDisplayEndDate.setText(sdf.format(myCalendarEnd.getTime()));

            }
        };

        mDisplayEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermDetailsActivity.this, mDateEndSetListener, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

            }


        });


    }

    public void toCourseDetailsScreen(View view) {
        Intent intent = new Intent(TermDetailsActivity.this, CourseActivity.class);
        intent.putExtra("termId", Id);

        startActivity(intent);
    }

    public void addTermFromScreen(View view)
    {
        TermEntity t;

        if(Id != -1) t = new TermEntity(Id, editName.getText().toString(), mDisplayStartDate.getText().toString(), mDisplayEndDate.getText().toString());
        else {
            List<TermEntity> allTerms = inventoryManagementRepository.getAllTerms();
            Id = allTerms.get(allTerms.size()-1).getTermId();
            t = new TermEntity(++Id, editName.getText().toString(), mDisplayStartDate.getText().toString(), mDisplayEndDate.getText().toString());


        }
        inventoryManagementRepository.insert(t);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_delete, menu);
        //getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.delete){
            if(numCourses == 0)
            {
                inventoryManagementRepository.delete(currentTerm);
                Toast.makeText(this, "Term " + currentTerm.getTermName() +" has been deleted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Unable to delete Terms that have Courses", Toast.LENGTH_SHORT).show();
            }
        }


        return super.onOptionsItemSelected(item);
    }




}