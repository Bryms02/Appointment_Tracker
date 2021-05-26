package com.example.mobileappdevpa.Database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mobileappdevpa.DAO.AssessmentDAO;
import com.example.mobileappdevpa.DAO.CourseDAO;
import com.example.mobileappdevpa.DAO.TermDAO;
import com.example.mobileappdevpa.Entity.AssessmentEntity;
import com.example.mobileappdevpa.Entity.CourseEntity;
import com.example.mobileappdevpa.Entity.TermEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 1)

public abstract class InventoryManagementDatabase extends RoomDatabase {

    public abstract AssessmentDAO assessmentDAO();

    public abstract TermDAO termDAO();

    public abstract CourseDAO courseDAO();

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile InventoryManagementDatabase INSTANCE;

    static InventoryManagementDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (InventoryManagementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), InventoryManagementDatabase.class, "mobile_app_pa.db")
                            .fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;

    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                TermDAO mTermDao = INSTANCE.termDAO();
                CourseDAO mCourseDAO = INSTANCE.courseDAO();

                AssessmentDAO mAssessmentDAO = INSTANCE.assessmentDAO();


                mTermDao.deleteAllTerms();
                mCourseDAO.deleteAllCourses();
                mAssessmentDAO.deleteAllAssessments();


                AssessmentEntity assessment = new AssessmentEntity(0, "Performance", "01/02/21", "Assessment 1", 2);
                mAssessmentDAO.insert(assessment);
                AssessmentEntity assessment1 = new AssessmentEntity(1, "Objective", "02/03/21", "Assessment 2", 1);
                mAssessmentDAO.insert(assessment1);



                CourseEntity course = new CourseEntity(1, "Course 1", "05/09/2021", "06/07/2021", "In Progress", "difficult course, study", "Mr. Jones", "555-555-5555", "jones@gmail.com", 1);
                mCourseDAO.insert(course);

                course = new CourseEntity(2, "Course 2", "07/08/2021", "09/01/2021", "Completed", "easy course", "Mr.Smith", "555-555-4444", "smith@gmail.com", 2);
                mCourseDAO.insert(course);



                TermEntity term = new TermEntity(1, "Spring", "02/05/2020", "04/05/2020");
                mTermDao.insert(term);

                term = new TermEntity(2, "Fall", "06/02/2020", "08/02/2020");
                mTermDao.insert(term);


            });
        }


    };
}
