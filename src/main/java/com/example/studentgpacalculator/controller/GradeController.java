package com.example.studentgpacalculator.controller;
import com.example.studentgpacalculator.dto.GradeResponse;
import com.example.studentgpacalculator.model.Course;
import com.example.studentgpacalculator.service.GradeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    // 1️⃣ POST – Add course
    @PostMapping("/courses")
    public String addCourse(@RequestBody Course course) {
        gradeService.addCourse(course);
        return "Course added successfully";
    }

    // 2️⃣ PUT – Update course
    @PutMapping("/courses/{courseId}")
    public String updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course) {

        boolean updated = gradeService.updateCourse(courseId, course);
        return updated ? "Course updated" : "Course not found";
    }

    // 3️⃣ DELETE – Delete course
    @DeleteMapping("/courses/{courseId}")
    public String deleteCourse(@PathVariable Long courseId) {
        return gradeService.deleteCourse(courseId)
                ? "Course deleted"
                : "Course not found";
    }

    // 4️⃣ GET – GPA
    @GetMapping("/gpa")
    public double getGpa() {
        return gradeService.calculateGpa();
    }

    // 5️⃣ GET – Grade
    @GetMapping("/grade")
    public GradeResponse getGrade() {
        double gpa = gradeService.calculateGpa();
        String grade = gradeService.calculateGrade(gpa);
        return new GradeResponse(gpa, grade);
    }
}
