package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long generatedFaculty = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++generatedFaculty);
        faculties.put(generatedFaculty, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if (faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(),faculty);
            return faculty;
        }
        return null;
    }
    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }
    public Collection<Faculty> getFacultyByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
    public Collection<Faculty> getAllFaculty() {
        return faculties.values();
    }
}

