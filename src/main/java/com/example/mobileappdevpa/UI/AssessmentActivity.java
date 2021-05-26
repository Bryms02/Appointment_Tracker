package com.example.mobileappdevpa.UI;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileappdevpa.Database.InventoryManagementRepository;
import com.example.mobileappdevpa.Entity.AssessmentEntity;
import com.example.mobileappdevpa.Entity.CourseEntity;
import com.example.mobileappdevpa.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentActivity extends AppCompatActivity {


    private InventoryManagementRepository inventoryManagementRepository;

    private static final String TAG = "AssessmentActivity";

    TextView mDisplayDateNew;


    Calendar myCalendarStartDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener mDateSetNewListener;
    Date myStartDate;

    static int id3;

    int Id;
    String title;
    String date;
    String type;
    String courseName;

    Spinner spinner;

    EditText editTitle;
    TextView courseNameTv;

    int termId;

    int courseId;

    long dateStart;

    CourseEntity currentCourse;
    AssessmentEntity currentAssessment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        inventoryManagementRepository = new InventoryManagementRepository(getApplication());

        mDisplayDateNew = findViewById(R.id.assessmentDateTv);



        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String[] statusOptions = {"Objective", "Performance"};
        spinner = (Spinner) findViewById(R.id.assessmentsSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("assessmentTitle");
        date = getIntent().getStringExtra("dateOfAssessment");
        type = getIntent().getStringExtra("assessmentType");

        courseId = getIntent().getIntExtra("courseId", -1);
     //   termId = getIntent().getIntExtra("termId", -1);

        List<CourseEntity> allCourses = inventoryManagementRepository.getAllCourses();
        List<AssessmentEntity> allAssessments = inventoryManagementRepository.getAllAssessments();

        for(AssessmentEntity a: allAssessments){
            if(a.getId()== Id) currentAssessment = a;
        }
        for(CourseEntity c: allCourses){
            if(c.getCourseId()== courseId) currentCourse = c;
        }

        if(currentAssessment != null)
        {
            title = currentAssessment.getAssessmentTitle();
            date = currentAssessment.getDateOfAssessment();
            type = currentAssessment.getAssessmentType();
            courseId = currentAssessment.getCourseId();
          //  termId = currentCourse.getTermId();
            courseName = currentCourse.getCourseTitle();
        }


/**
       if(Id != -1)
       {
           CourseActivity.id2 = termId;
      }
 **/


        id3 = courseId;

        editTitle = findViewById(R.id.assessmentTitleEditText);
        courseNameTv = findViewById(R.id.courseTextPlaceHolder);
        mDisplayDateNew = findViewById(R.id.assessmentDateTv);






        if (Id != -1) {



            courseNameTv.setText(courseName);
            editTitle.setText(title);
            mDisplayDateNew.setText(date);

            try {
                myStartDate = sdf.parse(date);
                assert myStartDate != null;
                myCalendarStartDate.setTime(myStartDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < statusOptions.length; i++) {
                if (type.equals(statusOptions[i])) {
                    spinner.setSelection(i);
                }
            }



        }


        inventoryManagementRepository = new InventoryManagementRepository(getApplication());



        mDateSetNewListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendarStartDate.set(Calendar.YEAR, year);
                myCalendarStartDate.set(Calendar.MONTH, month);
                myCalendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                mDisplayDateNew.setText(sdf.format(myCalendarStartDate.getTime()));

            }
        };


        mDisplayDateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AssessmentActivity.this, mDateSetNewListener, myCalendarStartDate
                        .get(Calendar.YEAR), myCalendarStartDate.get(Calendar.MONTH),
                        myCalendarStartDate.get(Calendar.DAY_OF_MONTH)).show();

            }


        });


    }


    public void saveAssessmentClick(View view) {

        AssessmentEntity a;

        if(Id != -1)  a = new AssessmentEntity(Id, spinner.getSelectedItem().toString(), mDisplayDateNew.getText().toString(), editTitle.getText().toString(), courseId);

        else{
            List<AssessmentEntity> allAssessments = inventoryManagementRepository.getAllAssessments();
            Id = allAssessments.get(allAssessments.size() - 1).getId();
            a = new AssessmentEntity(++Id, spinner.getSelectedItem().toString(), mDisplayDateNew.getText().toString(), editTitle.getText().toString(), courseId);
        }
        inventoryManagementRepository.insert(a);

        currentAssessment = a;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_delete, menu);
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {

            inventoryManagementRepository.delete(currentAssessment);
            Toast.makeText(this, "Assessment " + currentAssessment.getAssessmentTitle() + " has been deleted", Toast.LENGTH_SHORT).show();
        }



        if (id == R.id.notifications) {

            Intent intent = new Intent(AssessmentActivity.this, MyReceiver.class);
            intent.putExtra("key", "Assessment " + currentAssessment.getAssessmentTitle() + " Starting Today!");
            PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this, ++CourseActivity.numAlert, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            dateStart = myCalendarStartDate.getTimeInMillis();

            alarmManager.set(AlarmManager.RTC_WAKEUP, dateStart, sender);

            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    }