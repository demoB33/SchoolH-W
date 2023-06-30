package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
            private StudentRepository studentRepositoryMock;
    @InjectMocks
        StudentService out;

       /* @BeforeEach
        void setupStudent() {
            out = new StudentService();
            out.createStudent(new Student(1L,"A", 1));
            out.createStudent(new Student(2L,"B", 2));
            out.createStudent(new Student(3L,"C", 3));
        }*/

        @Test
        public void createStudentTest() {
            Student studentTest = new Student(1L,"AA",20);
            when(studentRepositoryMock.save(studentTest)).thenReturn(studentTest);
            Assertions.assertEquals(studentTest, out.createStudent(studentTest));
        }

        @Test
        void findStudentPositiveTest() {
            Student studentTest = new Student(1L,"AA",20);
            when(studentRepositoryMock.findById(1L)).thenReturn(Optional.of(studentTest));
            Assertions.assertEquals(studentTest, out.findStudent(1L));

        }

        @Test
        void findStudentNegativeTest() {
            Student studentTest = new Student(1L,"AA",20);
            when(studentRepositoryMock.findById(4L)).thenReturn(Optional.empty());
            Assertions.assertThrows(NoSuchElementException.class, ()-> out.findStudent(4L));
        }

        @Test
        void editStudentPositiveTest() {
            Student studentTest = new Student(1L,"AA",20);
            when(studentRepositoryMock.save(studentTest)).thenReturn(studentTest);
            Assertions.assertEquals(studentTest, out.editStudent(studentTest));
        }

        @Test
        void editStudentNegativeTest() {
            Student f = new Student(4L, "C", 4);
            int size = out.getAllStudent().size();
            Assertions.assertNull(out.editStudent(f));
            Assertions.assertEquals(size, out.getAllStudent().size());
        }

       /* @Test
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

*/


}
