package ru.hogwarts.school.ServiceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {
    @Mock
            private FacultyRepository facultyRepositoryMock;
    @InjectMocks
    FacultyService out;
/*
    @BeforeEach
    void setupFaculties() {
        out = new FacultyService();
        out.createFaculty(new Faculty(1L,"A", "a"));
        out.createFaculty(new Faculty(2L,"B", "b"));
        out.createFaculty(new Faculty(3L,"C", "c"));
    }*/

    @Test
    void createFacultyTest() {
        Faculty f = new Faculty(4L, "D", "d");
        when(facultyRepositoryMock.save(f)).thenReturn(f);
        Assertions.assertEquals(f, out.createFaculty(f));
    }

    @Test
    void findFacultyPositiveTest() {
        Faculty f = new Faculty(4L, "D", "d");
        when(facultyRepositoryMock.findById(1L)).thenReturn(Optional.of(f));
        Assertions.assertEquals(f, out.findFaculty(1L));
    }

    @Test
    void findFacultyNegativeTest() {
        Faculty f = new Faculty(1L, "D", "d");
        when(facultyRepositoryMock.findById(4L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, ()-> out.findFaculty(4L));
    }

    @Test
    void editFacultyPositiveTest() {
        Faculty f = new Faculty(3L, "C", "c");
        when(facultyRepositoryMock.save(f)).thenReturn(f);
        Assertions.assertEquals(f, out.editFaculty(f));
    }

    @Test
    void editFacultyNegativeTest() {
        Faculty f = new Faculty(4L, "C", "D");
        int size = out.getAllFaculty().size();
        Assertions.assertNull(out.editFaculty(f));
        Assertions.assertEquals(size, out.getAllFaculty().size());
    }
/*
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


*/
}
