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
import ru.hogwarts.school.controller.FacultyController;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerTest {
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
    private FacultyController facultyController;

    @Test
    void createFacultyTest() throws Exception {
        long id = 1;
        String name = "A";
        String color = "green";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void editFacultyTest() throws Exception {
        long id = 1;
        String name = "A";
        String color = "green";
        Faculty faculty = new Faculty(id, name, color);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id",id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

    }

    @Test
    void deleteFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getFacultyByColorTest() throws Exception {
        Faculty f1 = new Faculty(1L, "A", "green");
        Faculty f2 = new Faculty(2L, "B", "green");

        List<Faculty> list = List.of(f1, f2);

        when(facultyRepository.findAllByColor(anyString())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color/green")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(f1.getId()))
                .andExpect(jsonPath("$.[0].name").value(f1.getName()))
                .andExpect(jsonPath("$.[0].color").value(f1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(f2.getId()))
                .andExpect(jsonPath("$.[1].name").value(f2.getName()))
                .andExpect(jsonPath("$.[1].color").value(f2.getColor()));
    }

    @Test
    void findFacultyByColorOrNameTest() throws Exception {
        Faculty f1 = new Faculty(1L, "A", "green");
        Faculty f2 = new Faculty(2L, "B", "green");

        List<Faculty> list = List.of(f1, f2);

        when(facultyRepository.findByColorContainsIgnoreCase(anyString()))
                .thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/nameOrColor?color=green")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(f1.getId()))
                .andExpect(jsonPath("$.[0].name").value(f1.getName()))
                .andExpect(jsonPath("$.[0].color").value(f1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(f2.getId()))
                .andExpect(jsonPath("$.[1].name").value(f2.getName()))
                .andExpect(jsonPath("$.[1].color").value(f2.getColor()));

        when(facultyRepository.findByNameContainsIgnoreCase(anyString()))
                .thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/nameOrColor?name=name1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(f1.getId()))
                .andExpect(jsonPath("$.[0].name").value(f1.getName()))
                .andExpect(jsonPath("$.[0].color").value(f1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(f2.getId()))
                .andExpect(jsonPath("$.[1].name").value(f2.getName()))
                .andExpect(jsonPath("$.[1].color").value(f2.getColor()));
    }

    @Test
    void getStudentsTest() throws Exception {
        Faculty f1 = new Faculty(1L, "Faculty1", "green");
        Faculty f2 = new Faculty(2L, "Faculty2", "red");

        Student s1 = new Student(1L, "A", 10);
        Student s2 = new Student(2L, "B", 20);
        Student s3 = new Student(3L, "B", 20);

        s1.setFaculty(f1);
        s2.setFaculty(f1);
        s3.setFaculty(f2);

        List<Student> list = List.of(s1, s2, s3);

        when(studentRepository.findAllByFaculty_Id(any(Long.class))).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1/students")
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
    void getAllFacultyTest() throws Exception {
        Faculty f1 = new Faculty(1L, "Faculty1", "green");
        Faculty f2 = new Faculty(2L, "Faculty2", "red");

        List<Faculty> list = List.of(f1, f2);

        when(facultyRepository.findAll()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(f1.getId()))
                .andExpect(jsonPath("$.[0].name").value(f1.getName()))
                .andExpect(jsonPath("$.[0].color").value(f1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(f2.getId()))
                .andExpect(jsonPath("$.[1].name").value(f2.getName()))
                .andExpect(jsonPath("$.[1].color").value(f2.getColor()));
    }
}
