package com.example.mobileappdevpa.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseActivity extends AppCompatActivity {

    private InventoryManagementRepository inventoryManagementRepository;
    public static final String TAG = "CourseActivity";

    public static int numAlert;

    TextView mDisplayEndDate;
    TextView mDisplayStartDate;

    DatePickerDialog.OnDateSetListener mDateEndSetListener;

    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener mDateSetListener;
    Date myDate;


    static int id2;
    int Id;
    String title;
    String startDate;
    String endDate;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String optionalNote;
    String status;

    Spinner spinner;

    EditText editTitle;

    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editNote;
    long statusPos;


    int termId;

    long date;


    CourseEntity currentCourse;
    List<AssessmentEntity> associatedAssessments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        inventoryManagementRepository = new InventoryManagementRepository(getApplication());

        mDisplayEndDate = findViewById(R.id.courseEnd);
        mDisplayStartDate = findViewById(R.id.courseStart);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        String[] statusOptions = {"In Progress", "Completed", "Dropped", "Plan To Take"};
        spinner = (Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        Id = getIntent().getIntExtra("courseId", -1);
        if (Id == -1) Id = AssessmentActivity.id3;

        title = getIntent().getStringExtra("courseTitle");
        startDate = getIntent().getStringExtra("courseStartDate");
        endDate = getIntent().getStringExtra("courseEndDate");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        optionalNote = getIntent().getStringExtra("optionalNote");
        status = getIntent().getStringExtra("status");

        termId = getIntent().getIntExtra("termId", -1);


        editTitle = findViewById(R.id.courseNameEditText);
        editInstructorName = findViewById(R.id.instructorNameEditText);
        editInstructorPhone = findViewById(R.id.instructorPhoneEditText);
        editInstructorEmail = findViewById(R.id.instructorEmailEditText);
        editNote = findViewById(R.id.courseNotesEditText);
        spinner = findViewById(R.id.statusSpinner);


        List<CourseEntity> allCourses = inventoryManagementRepository.getAllCourses();

        for (CourseEntity c : allCourses) {
            if (c.getCourseId() == Id) currentCourse = c;
        }


        if (currentCourse != null) {
            title = currentCourse.getCourseTitle();
            startDate = currentCourse.getCourseStartDate();
            endDate = currentCourse.getCourseEndDate();
            instructorName = currentCourse.getInstructorName();
            instructorEmail = currentCourse.getInstructorEmail();
            instructorPhone = currentCourse.getInstructorPhone();
            optionalNote = currentCourse.getOptionalNote();
            status = currentCourse.getStatus();
            termId = currentCourse.getTermId();


        }

        id2 = termId;


        if (Id != -1) {
            editTitle.setText(title);
            mDisplayStartDate.setText(startDate);
            mDisplayEndDate.setText(endDate);
            editInstructorName.setText(instructorName);
            editInstructorEmail.setText(instructorEmail);
            editInstructorPhone.setText(instructorPhone);
            editNote.setText(optionalNote);
            //  testDate = myCalendarStart.getTimeInMillis();

            for (int i = 0; i < statusOptions.length; i++) {
                if (status.equals(statusOptions[i])) {
                    spinner.setSelection(i);
                }
            }

            try {
                myDate = sdf.parse(mDisplayStartDate.getText().toString());
                assert myDate != null;
                myCalendarStart.setTime(myDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        inventoryManagementRepository = new InventoryManagementRepository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.recyclerviewAssessments);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();


        for (AssessmentEntity a : inventoryManagementRepository.getAllAssessments()) {
            if (a.getCourseId() == Id) filteredAssessments.add(a);
        }

        assessmentAdapter.setWords(filteredAssessments);

        associatedAssessments = filteredAssessments;


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
                new DatePickerDialog(CourseActivity.this, mDateSetListener, myCalendarStart
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
                new DatePickerDialog(CourseActivity.this, mDateEndSetListener, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();

            }


        });

    }


    public void toAddAssessment(View view) {
        Intent intent = new Intent(CourseActivity.this, AssessmentActivity.class);
        intent.putExtra("courseId", Id);
    //    intent.putExtra("termId", termId);
        startActivity(intent);
    }

    public void addCourseFromScreen(View view) {
        CourseEntity c;

        if (Id != -1)
            c = new CourseEntity(Id, editTitle.getText().toString(), mDisplayStartDate.getText().toString(), mDisplayEndDate.getText().toString(),
                    spinner.getSelectedItem().toString(), editNote.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                    editInstructorEmail.getText().toString(), termId);
        else {
            List<CourseEntity> allCourses = inventoryManagementRepository.getAllCourses();
            Id = allCourses.get(allCourses.size() - 1).getCourseId();
            c = new CourseEntity(++Id, editTitle.getText().toString(), mDisplayStartDate.getText().toString(), mDisplayEndDate.getText().toString(),
                    spinner.getSelectedItem().toString(), editNote.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                    editInstructorEmail.getText().toString(), termId);

        }
        inventoryManagementRepository.insert(c);

        currentCourse = c;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_delete, menu);
        getMenuInflater().inflate(R.menu.menu_share, menu);
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {

            inventoryManagementRepository.delete(currentCourse);

            for (int i = 0; i < associatedAssessments.size(); i++) {
                inventoryManagementRepository.delete(associatedAssessments.get(i));
            }

            Toast.makeText(this, "Course " + currentCourse.getCourseTitle() + " has been deleted", Toast.LENGTH_SHORT).show();
        }


        if (id == R.id.sharing) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Here are my course notes for " + currentCourse.getCourseTitle() + "\n" + currentCourse.getOptionalNote());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes for " + currentCourse.getCourseTitle());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (id == R.id.notifications) {


            Intent intent = new Intent(CourseActivity.this, MyReceiver.class);
            intent.putExtra("key", "Course " + currentCourse.getCourseTitle() + " Starting Today!");
            PendingIntent sender = PendingIntent.getBroadcast(CourseActivity.this, ++numAlert, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


            date = myCalendarStart.getTimeInMillis();

            alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);

            return true;

        }
        return super.onOptionsItemSelected(item);
    }


}