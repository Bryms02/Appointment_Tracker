package com.example.mobileappdevpa.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mobileappdevpa.Entity.AssessmentEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Query("SELECT * FROM assessment_table")
    List<AssessmentEntity> getAllAssessments();

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Delete
    void delete(AssessmentEntity assessmentEntity);


}
