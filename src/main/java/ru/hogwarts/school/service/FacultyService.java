package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.lang.model.element.Name;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private FacultyRepository facultyRepository;
    private StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Was invoked method for createFaculty with faculty = {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.debug("Was invoked method for findFaculty with id = {}", id);
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Was invoked method for editFaculty with faculty = {}", faculty);
        return facultyRepository.save(faculty);
    }
    public void deleteFaculty(long id) {
        logger.debug("Was invoked method for deleteFaculty with id = {}", id);
        facultyRepository.deleteById(id);
    }
    public Collection<Faculty> getFacultyByColor(String color) {
        logger.debug("Was invoked method for getFacultyByColor with color = {}", color);
        return facultyRepository.findAllByColor(color);
       // return faculties.values().stream()
       //         .filter(faculty -> faculty.getColor().equals(color))
       //         .collect(Collectors.toList());
    }
    public Collection<Faculty> getAllFaculty() {
        logger.debug("Was invoked method for getAllFaculty");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findFacultyByName(String name) {
        logger.debug("Was invoked method for findFacultyByName with name = {}", name);
        return facultyRepository.findByNameContainsIgnoreCase(name);
    }
    public Collection<Faculty> findFacultyByColor(String color) {
        logger.debug("Was invoked method for findFacultyByColor with color = {}", color);
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    public Collection<Student> getStudents(Long facultyId) {
        logger.debug("Was invoked method for getStudents with facultyId= {}",facultyId);
        return studentRepository.findAllByFaculty_Id(facultyId).stream()
                .filter(s -> s.getFaculty().getId().equals(facultyId))
                .collect(Collectors.toList());
    }

    // Создать эндпоинт, который будет возвращать самое длинное название факультета.
    public String longestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .sorted(Comparator.comparing(String::length, Comparator.reverseOrder()))
                .findFirst().get();
    }

    //Создать эндпоинт (не важно в каком контроллере),
    // который будет возвращать целочисленное значение.
    // Это значение вычисляется следующей формулой:
    // int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000)
    // .reduce(0, (a, b) -> a + b );
    public Integer computedIntegerValue() {
        logger.info("Was invoked method for computedIntegerValue");
        return Stream.iterate(1, a -> a +1)
                //.parallel()//параллельный выполняется за 65,
                // последовательный выполняется за 22
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
    }

}

