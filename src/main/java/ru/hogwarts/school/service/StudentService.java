package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentByCategory;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student with student = {}", student);
       return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student with id = {}", id);
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student with student = {}", student);
        return studentRepository.save(student);
    }
    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student with id = {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method for findByAge student with age = {}", age);
        return studentRepository.findAllByAge(age);
    }
    public Collection<Student> getAllStudent() {
        logger.info("Was invoked method for getAllStudent student");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        logger.info("Was invoked method for findByAgeBetween with ageMin = {} and ageMax = {}", ageMin, ageMax);
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public Student getStudents(long id) {
        logger.info("Was invoked method for getStudents student with id = {}", id);
        return studentRepository.findById(id).get();
    }

    public int getAllStudentByCategory() {
        logger.info("Was invoked method for getAllStudentByCategory student");
        return studentRepository.getAllStudentByCategory();
    }

    public double getAVGByAge() {
        logger.info("Was invoked method for getAVGByAge student");
        return studentRepository.getAVGByAge();
    }

    public List<Student> getLastStudent(int count) {
        logger.info("Was invoked method for getLastStudent student with count = {}", count);
        return studentRepository.getLastStudent(count);
    }

}
