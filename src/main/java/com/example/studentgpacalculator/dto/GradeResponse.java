package com.example.studentgpacalculator.dto;

public class GradeResponse {

    private double gpa;
    private String grade;

    public GradeResponse(double gpa, String grade) {
        this.gpa = gpa;
        this.grade = grade;
    }

    public double getGpa() {
        return gpa;
    }

    public String getGrade() {
        return grade;
    }
}
