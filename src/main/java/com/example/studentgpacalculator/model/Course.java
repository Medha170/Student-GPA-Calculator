package com.example.studentgpacalculator.model;

public class Course {

    private Long courseId;
    private int credits;
    private double marksObtained;

    public Course() {}

    public Course(Long courseId, int credits, double marksObtained) {
        this.courseId = courseId;
        this.credits = credits;
        this.marksObtained = marksObtained;
    }

    public Long getCourseId() {
        return courseId;
    }

    public int getCredits() {
        return credits;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public double getPoints() {
        return marksObtained / 10;
    }
}
