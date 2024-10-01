package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class FacultyControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testPostFacultyIsNotNull() throws Exception{
        Faculty faculty = new Faculty();
        faculty.setId(1);
        faculty.setName("super");
        faculty.setColor("blue");

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull();
    }

    @Test
    public void testGetFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    void updateFacultyInfo() throws Exception{
        Faculty faculty = new Faculty();
        faculty.setName("super");
        faculty.setColor("blue");

        facultyController.createFaculty(faculty);
        Faculty faculty1 = new Faculty();
        faculty1.setId(faculty1.getId());
        faculty1.setName("puper");
        faculty1.setColor("green");

        ResponseEntity<Faculty> response = restTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.PUT, new HttpEntity<>(faculty1), Faculty.class);

        Assertions
                .assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("puper");

        studentController.deleteStudent(faculty.getId());
        studentController.deleteStudent(faculty1.getId());
    }

    @Test
    void deleteFaculty() {
    }

    @Test
    void filteredByColor() {
    }

    @Test
    void findAllStudentsOfFaculty() {
    }
}