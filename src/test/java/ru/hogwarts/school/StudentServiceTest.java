package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;

public class StudentServiceTest {
        StudentService out;

        @BeforeEach
        void setupStudent() {
            out = new StudentService();
            out.createStudent(new Student(1L,"A", 1));
            out.createStudent(new Student(2L,"B", 2));
            out.createStudent(new Student(3L,"C", 3));
        }

        @Test
        void createStudentTest() {
            int size = out.getAllStudent().size();
            Student f = new Student(4L, "D", 4);
            Assertions.assertEquals(f, out.createStudent(f));
            Assertions.assertEquals(size + 1, out.getAllStudent().size());
        }

        @Test
        void findStudentPositiveTest() {
            Assertions.assertEquals(new Student(3L,"C",3), out.findStudent(3L));
        }

        @Test
        void findStudentNegativeTest() {
            Assertions.assertNull(out.findStudent(4L));
        }

        @Test
        void editStudentPositiveTest() {
            Student f = new Student(3L, "C", 3);
            int size = out.getAllStudent().size();
            Assertions.assertEquals(f, out.editStudent(f));
            Assertions.assertEquals(size, out.getAllStudent().size());
        }

        @Test
        void editStudentNegativeTest() {
            Student f = new Student(4L, "C", 4);
            int size = out.getAllStudent().size();
            Assertions.assertNull(out.editStudent(f));
            Assertions.assertEquals(size, out.getAllStudent().size());
        }

        @Test
        void deleteStudentPositiveTest() {
            Student f = new Student(3L, "C", 3);
            int size = out.getAllStudent().size();
            Assertions.assertEquals(f, out.deleteStudent(3L));
            Assertions.assertEquals(size-1, out.getAllStudent().size());
        }

        @Test
        void deleteStudentNegativeTest() {
            int size = out.getAllStudent().size();
            Assertions.assertNull(out.deleteStudent(4L));
            Assertions.assertEquals(size, out.getAllStudent().size());
        }

        @Test
        void findByAgePositiveTest() {
            Student f = new Student(4L, "D", 3);
            out.createStudent(f);
            List<Student> test = List.of(new Student(3L, "C", 3), f);
            Assertions.assertIterableEquals(test, out.findByAge(3));

        }
        @Test
        void findByAgeNegativeTest() {
            List<Student> test = Collections.emptyList();
            Assertions.assertIterableEquals(test, out.findByAge(8));
        }




}
