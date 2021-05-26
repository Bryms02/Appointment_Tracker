package com.example.mobileappdevpa.Database;

import android.app.Application;

import com.example.mobileappdevpa.DAO.AssessmentDAO;
import com.example.mobileappdevpa.DAO.CourseDAO;
import com.example.mobileappdevpa.DAO.TermDAO;
import com.example.mobileappdevpa.Entity.AssessmentEntity;
import com.example.mobileappdevpa.Entity.CourseEntity;
import com.example.mobileappdevpa.Entity.TermEntity;

import java.util.List;

public class InventoryManagementRepository {

    private AssessmentDAO mAssessmentDao;
    private TermDAO mTermDao;
    private CourseDAO mCourseDao;
    private List<AssessmentEntity> mAllAssessments;
    private List<TermEntity> mAllTerms;
    private List<CourseEntity> mAllCourses;
    private int termId;

    public InventoryManagementRepository(Application application) {

        InventoryManagementDatabase db = InventoryManagementDatabase.getDatabase(application);
        mAssessmentDao = db.assessmentDAO();
        mTermDao = db.termDAO();
        mCourseDao = db.courseDAO();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public List<TermEntity> getAllTerms() {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mAllTerms = mTermDao.getAllTerms();

        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<CourseEntity> getAllCourses() {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mAllCourses = mCourseDao.getAllCourses();

        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public List<AssessmentEntity> getAllAssessments() {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mAllAssessments = mAssessmentDao.getAllAssessments();

        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(CourseEntity courseEntity) {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.insert(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void insert(TermEntity termEntity) {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.insert(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(AssessmentEntity assessmentEntity) {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.insert(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(TermEntity termEntity) {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.delete(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(CourseEntity courseEntity) {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.delete(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllTerms() {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.deleteAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(AssessmentEntity assessmentEntity) {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.delete(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllAssessments() {
        InventoryManagementDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.deleteAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
