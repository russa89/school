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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    void testPostFacultyIsNotNull() throws Exception {
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
    void updateFacultyInfo() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setName("white");
        faculty.setColor("white");

        facultyRepository.save(faculty);

        Faculty faculty1 = new Faculty();
        faculty1.setId(faculty1.getId());
        faculty1.setName("puper");
        faculty1.setColor("green");

        ResponseEntity<Faculty> response = restTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.PUT, new HttpEntity<>(faculty1), Faculty.class);


        Assertions
                .assertThat(response.getStatusCode().is2xxSuccessful());

        facultyRepository.deleteById(faculty1.getId());
    }

    @Test
    void deleteFaculty() {
        Faculty faculty = new Faculty();

        faculty.setName("black");
        faculty.setColor("black");
        facultyController.createFaculty(faculty);

        restTemplate.delete("http://localhost:" + port + "/faculty" + faculty.getId());

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty",
                String.class).isEmpty());

        facultyController.deleteFaculty(faculty.getId());
    }

    @Test
    void filteredByColor() {
        Faculty faculty = new Faculty();
        faculty.setName("black");
        faculty.setColor("black");
        facultyController.createFaculty(faculty);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/color/" + faculty.getColor(),
                String.class)).isNotNull();
        assertThat(faculty.getName()).isEqualTo("black");

        facultyController.deleteFaculty(faculty.getId());
    }

    @Test
    void findAllStudentsOfFaculty() {

        Faculty faculty = new Faculty();
        faculty.setName("2");
        faculty.setColor("2");
        facultyController.createFaculty(faculty);

        Student student1 = new Student();
        student1.setName("1");
        student1.setAge(15);
        student1.setFaculty(faculty);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + faculty.getId() + "/students",
                String.class)).isNotNull();

        facultyController.deleteFaculty(faculty.getId());
    }
}
