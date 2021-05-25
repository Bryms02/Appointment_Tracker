package com.example.mobileappdevpa.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(tableName = "term_table")
public class TermEntity {

    @PrimaryKey
    private int termId;

    private String termName;
    private String termStartDate;
    private String termEndDate;


    public TermEntity(int termId, String termName, String termStartDate, String termEndDate) {
        this.termId = termId;
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }
}
