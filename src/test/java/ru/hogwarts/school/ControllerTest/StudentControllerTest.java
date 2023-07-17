package ru.hogwarts.school.ControllerTest;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void saveStudentTest() throws Exception{
        final String name = "Harry Potter";
        final int age = 15;
        final long id = 1;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);


        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/student")
                .content(studentObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }


    @Test
    public void editStudentTest() throws Exception {
        final String name = "Harry Potter";
        final int age = 15;
        final long id = 1;

        JSONObject studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);


        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        final String name = "Harry Potter";
        final int age = 15;
        final long id = 1;

        JSONObject studentObject = new JSONObject();

        studentObject.put("name", name);
        studentObject.put("age", age);


        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findStudentByAgeTest() throws Exception{
        Student s1 = new Student(1L, "Harry", 10);
        Student s2 = new Student(2L, "!Harry", 10);

        List<Student> list = List.of(s1, s2);

        when(studentRepository.findAllByAge(anyInt())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/student")
                        .param("age", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$.[1].age").value(s2.getAge()));
    }

    @Test
    void findAgeByMinAndMaxTest() throws Exception {
        Student s1 = new Student(1L, "A", 10);
        Student s2 = new Student(2L, "B", 20);

        List<Student> list = List.of(s1, s2);

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/minMaxAge")
                        .param("ageMin", "10")
                        .param("ageMax", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(s1.getId()))
                .andExpect(jsonPath("$.[0].name").value(s1.getName()))
                .andExpect(jsonPath("$.[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(s2.getId()))
                .andExpect(jsonPath("$.[1].name").value(s2.getName()))
                .andExpect(jsonPath("$.[1].age").value(s2.getAge()));
    }

    @Test
    public void findFacultyTest() throws Exception{
        final String name = "Harry Potter";
        final int age = 15;
        final long id = 1;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        Faculty faculty = new Faculty(1L, "qwerty", "ytrewq");
        student.setFaculty(faculty);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id + "/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getAllStudentTest() throws Exception {
        Student s1 = new Student(1L, "Harry", 10);
        Student s2 = new Student(2L, "!Harry", 10);


        List<Student> list = List.of(s1, s2);

        when(studentRepository.findAll()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(s1.getId()))
                .andExpect(jsonPath("$.[0].name").value(s1.getName()))
                .andExpect(jsonPath("$.[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(s2.getId()))
                .andExpect(jsonPath("$.[1].name").value(s2.getName()))
                .andExpect(jsonPath("$.[1].age").value(s2.getAge()));
    }


}
