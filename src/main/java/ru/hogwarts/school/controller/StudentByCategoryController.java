package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentByCategory;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class StudentByCategoryController {

    private final StudentService studentService;

    public StudentByCategoryController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student-all")
    public int getAllStudentByCategory() {
        return studentService.getAllStudentByCategory();
    }

    @GetMapping("/student-avg-age")
    public double getAVGByAge() {
        return studentService.getAVGByAge();
    }

    @GetMapping("/get-last-student")
    public List<Student> getLastStudent(@RequestParam(value = "count", defaultValue = "5") int count) {
        return studentService.getLastStudent(Math.abs(count));
    }

}
