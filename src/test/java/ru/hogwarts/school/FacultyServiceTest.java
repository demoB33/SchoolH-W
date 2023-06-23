package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.List;

public class FacultyServiceTest {
    FacultyService out;

    @BeforeEach
    void setupFaculties() {
        out = new FacultyService();
        out.createFaculty(new Faculty(1L,"A", "a"));
        out.createFaculty(new Faculty(2L,"B", "b"));
        out.createFaculty(new Faculty(3L,"C", "c"));
    }

    @Test
    void createFacultyTest() {
        int size = out.getAllFaculty().size();
        Faculty f = new Faculty(4L, "D", "d");
        Assertions.assertEquals(f, out.createFaculty(f));
        Assertions.assertEquals(size + 1, out.getAllFaculty().size());
    }

    @Test
    void findFacultyPositiveTest() {
        Assertions.assertEquals(new Faculty(3L,"C","c"), out.findFaculty(3L));
    }

    @Test
    void findFacultyNegativeTest() {
        Assertions.assertNull(out.findFaculty(4L));
    }

    @Test
    void editFacultyPositiveTest() {
        Faculty f = new Faculty(3L, "C", "c");
        int size = out.getAllFaculty().size();
        Assertions.assertEquals(f, out.editFaculty(f));
        Assertions.assertEquals(size, out.getAllFaculty().size());
    }

    @Test
    void editFacultyNegativeTest() {
        Faculty f = new Faculty(4L, "C", "D");
        int size = out.getAllFaculty().size();
        Assertions.assertNull(out.editFaculty(f));
        Assertions.assertEquals(size, out.getAllFaculty().size());
    }

    @Test
    void deleteFacultyPositiveTest() {
        Faculty f = new Faculty(3L, "C", "c");
        int size = out.getAllFaculty().size();
        Assertions.assertEquals(f, out.deleteFaculty(3L));
        Assertions.assertEquals(size-1, out.getAllFaculty().size());
    }

    @Test
    void deleteFacultyNegativeTest() {
        int size = out.getAllFaculty().size();
        Assertions.assertNull(out.deleteFaculty(4L));
        Assertions.assertEquals(size, out.getAllFaculty().size());
    }

    @Test
    void getFacultyByColorPositiveTest() {
        Faculty f = new Faculty(4L, "D", "c");
        out.createFaculty(f);
        List<Faculty> test = List.of(new Faculty(3L, "C", "c"), f);
        Assertions.assertIterableEquals(test, out.getFacultyByColor("c"));

    }
    @Test
    void getFacultyByColorNegativeTest() {
        List<Faculty> test = Collections.emptyList();
        Assertions.assertIterableEquals(test, out.getFacultyByColor("g"));
    }



}
