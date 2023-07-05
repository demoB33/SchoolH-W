package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private FacultyRepository facultyRepository;
    private StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }
    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findAllByColor(color);
       // return faculties.values().stream()
       //         .filter(faculty -> faculty.getColor().equals(color))
       //         .collect(Collectors.toList());
    }
    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findFacultyByName(String name) {
        return facultyRepository.findByNameContainsIgnoreCase(name);
    }
    public Collection<Faculty> findFacultyByColor(String color) {
        return facultyRepository.findByColorContainsIgnoreCase(color);
    }

    public Collection<Student> getStudents(Long facultyId) {
        return studentRepository.findAllByFaculty_Id(facultyId).stream()
                .filter(s -> s.getFaculty().getId().equals(facultyId))
                .collect(Collectors.toList());
    }
}

