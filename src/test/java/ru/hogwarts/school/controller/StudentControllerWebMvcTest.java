package ru.hogwarts.school.controller;

import org.json.JSONException;
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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void createStudent() throws Exception {
        Long id = 1L;
        String name = "Sasha";

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);

        Student student = new Student();
        student.setId(id);
        student.setName(name);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getStudent() throws Exception {
        Long id = 1L;
        String name = "Sasha";

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);

        Student student = new Student();
        student.setId(id);
        student.setName(name);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void updateStudentInfo() throws Exception {
        Long id = 1L;
        String name = "Sasha";
        String newName = "Petr";

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);

        Student student = new Student();
        student.setId(id);
        student.setName(newName);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName));
    }

    @Test
    void deleteStudent() throws Exception {
        long id = 1L;
        String name = "Viktor";
        int age = 32;
        Student student = new Student(id, name, age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void filteredByAge() throws Exception {
        int age = 20;
        List<Student> students = new ArrayList<>(List.of(
                new Student(1L, "Kirill", 20),
                new Student(2L, "Ivan", 27),
                new Student(3L, "Oleg", 23)));
        when(studentRepository.findAllByAge(age)).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/" + age)
                        .param("age", "20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFilteredByAgeRange() throws Exception {
        int minAge = 20;
        int maxAge = 25;
        List<Student> students = new ArrayList<>();

        Student student1 = new Student(1L, "Oleg", 22);
        Student student2 = new Student(2L, "Petr", 24);

        students.add(0, student1);
        students.add(1, student2);

        when(studentRepository.findByAgeBetween(minAge, maxAge)).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/filter")
                        .param("minAge", "20")
                        .param("maxAge", "25")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findFacultyByStudent() throws Exception {
        Long facultyId = 5L;
        Faculty faculty = new Faculty(5L, "Booomb", "Yellow");
        Student student = new Student(2L, "Sasha", 21);
        student.setFaculty(faculty);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + facultyId + "/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(5L))
                .andExpect(jsonPath("name").value("Booomb"))
                .andExpect(jsonPath("color").value("Yellow"));
    }
}