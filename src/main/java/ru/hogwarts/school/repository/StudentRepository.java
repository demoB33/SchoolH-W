package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentByCategory;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student> findAllByAge(int age);

    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    Collection<Student> findAllByFaculty_Id(long facultyId);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int getAllStudentByCategory();

    @Query(value = "SELECT AVG(age) FROM student ", nativeQuery = true)
    double getAVGByAge();

    @Query(value = "SELECT * FROM student ORDER BY student.id DESC LIMIT 5 ", nativeQuery = true)
    List<Student> getLastStudent(@Param("count") int count);
}
