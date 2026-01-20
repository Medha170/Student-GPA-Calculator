package com.example.studentgpacalculator.service;

import org.springframework.stereotype.Service;

import com.example.studentgpacalculator.model.Course;
import java.util.*;

@Service
public class GradeService {

    // ✅ In-memory DB (courseId → Course)
    private final Map<Long, Course> courseStore = new HashMap<>();

    public void addCourse(Course course) {
        courseStore.put(course.getCourseId(), course);
    }

    public boolean updateCourse(Long courseId, Course updatedCourse) {
        Course existing = courseStore.get(courseId);
        if (existing == null) return false;

        existing.setCredits(updatedCourse.getCredits());
        existing.setMarksObtained(updatedCourse.getMarksObtained());
        return true;
    }

    public boolean deleteCourse(Long courseId) {
        return courseStore.remove(courseId) != null;
    }

    public double calculateGpa() {

        double totalPoints = 0;
        int totalCredits = 0;

        for (Course course : courseStore.values()) {
            totalPoints += course.getCredits() * course.getPoints();
            totalCredits += course.getCredits();
        }

        if (totalCredits == 0) {
            return 0.0;
        }

        double gpa = totalPoints / totalCredits;

        // ✅ Round to 2 decimal places
        return Math.round(gpa * 100.0) / 100.0;
    }

    public String calculateGrade(double gpa) {
        if (gpa >= 9.0) return "A+";
        if (gpa >= 8.0) return "A";
        if (gpa >= 7.0) return "B+";
        if (gpa >= 6.0) return "B";
        if (gpa >= 5.0) return "C";
        if (gpa >= 4.0) return "D";
        return "F";
    }
}
