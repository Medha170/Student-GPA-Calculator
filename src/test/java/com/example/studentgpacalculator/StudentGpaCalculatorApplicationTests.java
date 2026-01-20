package com.example.studentgpacalculator;

import com.example.studentgpacalculator.model.Course;
import com.example.studentgpacalculator.service.GradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentGpaCalculatorApplicationTests {

    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        gradeService = new GradeService();
    }

    // ✅ Test 1: Add Course & GPA Calculation
    @Test
    void shouldCalculateGpaCorrectly() {

        gradeService.addCourse(new Course(101L, 4, 85));
        gradeService.addCourse(new Course(102L, 3, 78));

        double gpa = gradeService.calculateGpa();

        assertEquals(8.2, gpa, 0.01);
    }

    // ✅ Test 2: GPA with No Courses
    @Test
    void shouldReturnZeroGpaWhenNoCoursesPresent() {
        double gpa = gradeService.calculateGpa();
        assertEquals(0.0, gpa);
    }

    // ✅ Test 3: Grade Calculation
    @Test
    void shouldReturnCorrectGrade() {
        assertEquals("A+", gradeService.calculateGrade(9.2));
        assertEquals("A", gradeService.calculateGrade(8.5));
        assertEquals("B+", gradeService.calculateGrade(7.5));
        assertEquals("B", gradeService.calculateGrade(6.5));
        assertEquals("C", gradeService.calculateGrade(5.2));
        assertEquals("D", gradeService.calculateGrade(4.1));
        assertEquals("F", gradeService.calculateGrade(3.9));
    }

    // ✅ Test 4: Update Course
    @Test
    void shouldUpdateExistingCourse() {

        gradeService.addCourse(new Course(101L, 4, 70));

        boolean updated = gradeService.updateCourse(
                101L,
                new Course(101L, 4, 90)
        );

        double gpa = gradeService.calculateGpa();

        assertTrue(updated);
        assertEquals(9.0, gpa);
    }

    // ✅ Test 5: Update Non-Existing Course
    @Test
    void shouldFailWhenUpdatingNonExistingCourse() {

        boolean updated = gradeService.updateCourse(
                999L,
                new Course(999L, 3, 80)
        );

        assertFalse(updated);
    }

    // ✅ Test 6: Delete Course
    @Test
    void shouldDeleteCourseSuccessfully() {

        gradeService.addCourse(new Course(101L, 4, 85));

        boolean deleted = gradeService.deleteCourse(101L);

        assertTrue(deleted);
        assertEquals(0.0, gradeService.calculateGpa());
    }

    // ✅ Test 7: Delete Non-Existing Course
    @Test
    void shouldReturnFalseWhenDeletingNonExistingCourse() {

        boolean deleted = gradeService.deleteCourse(999L);

        assertFalse(deleted);
    }

    // ✅ Test 8: GPA Boundary Grade Test
    @Test
    void shouldAssignCorrectGradeAtBoundary() {

        gradeService.addCourse(new Course(101L, 4, 80)); // 8.0 GPA

        double gpa = gradeService.calculateGpa();
        String grade = gradeService.calculateGrade(gpa);

        assertEquals(8.0, gpa);
        assertEquals("A", grade);
    }

    @Test
    void contextLoads() {
    }

}
