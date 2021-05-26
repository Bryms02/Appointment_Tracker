package com.example.mobileappdevpa.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")
public class AssessmentEntity {

    @PrimaryKey
    private int id;
    private String assessmentType;
    private String dateOfAssessment;
    private String assessmentTitle;
    private int courseId;

    public AssessmentEntity(int id, String assessmentType, String dateOfAssessment, String assessmentTitle, int courseId) {
        this.id = id;
        this.assessmentType = assessmentType;
        this.dateOfAssessment = dateOfAssessment;
        this.assessmentTitle = assessmentTitle;
        this.courseId = courseId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getDateOfAssessment() {
        return dateOfAssessment;
    }

    public void setDateOfAssessment(String dateOfAssessment) {
        this.dateOfAssessment = dateOfAssessment;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
