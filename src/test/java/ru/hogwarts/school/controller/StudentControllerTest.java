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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class StudentControllerTest {

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
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudentIsNotNull() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentFilteredByAgeIsNotNull() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/age", String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentFilteredByRangAgeIsNotNull() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/filter", String.class))
                .isNotNull();
    }

    @Test
    public void testFindFacultyByStudentIsNotNull() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    public void testPostStudentIsNotNull() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("Ivan");
        student.setAge(17);

        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }

    @Test
    public void testUpdateStudentInfo() throws Exception {
        Student student = new Student();
        student.setAge(16);
        student.setName("Oleg");
        student.setFaculty(null);
        studentController.createStudent(student);
        Student student2 = new Student();
        student2.setId(student2.getId());
        student2.setAge(20);
        student2.setName("Ivan");
        student2.setFaculty(null);

        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.PUT, new HttpEntity<>(student2), Student.class);

        Assertions
                .assertThat(Objects.requireNonNull(response.getBody()).getName()).isEqualTo("Ivan");

        studentController.deleteStudent(student2.getId());

    }

    @Test
    void deleteStudent() throws Exception {

        Student student = new Student();
        student.setId(16);
        student.setName("Ivan");
        student.setAge(17);
        studentController.createStudent(student);

        restTemplate.delete("http://localhost:" + port + "/student" + student.getId());

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student",
                String.class).isEmpty());
    }

    @Test
    void filteredByAge() {

        Student student = new Student();
        student.setName("Ivan");
        student.setAge(17);

        studentController.createStudent(student);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age/" + student.getAge(),
                String.class)).isNotNull();
        assertThat(student.getName()).isEqualTo("Ivan");

        studentController.deleteStudent(student.getId());
    }

    @Test
    void testFilteredByAgeRang() {

        Student studentAnn = new Student(15, "Ann", 17);
        Student studentMary = new Student(16, "Mary", 22);
        Student studentDenis = new Student(17, "Denis", 20);

        studentController.createStudent(studentAnn);
        studentController.createStudent(studentMary);
        studentController.createStudent(studentDenis);

        String result = restTemplate.getForObject("http://localhost:" + port + "/student/filter?min=12&max=25", String.class);
        assertThat(result).isNotNull();

        studentController.deleteStudent(studentAnn.getId());
        studentController.deleteStudent(studentMary.getId());
        studentController.deleteStudent(studentDenis.getId());
    }

    @Test
    void findFacultyByStudent() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(10);
        faculty.setName("super");
        faculty.setColor("blue");
        facultyController.createFaculty(faculty);
        Student student = new Student(20, "Petr", 21);
        studentController.createStudent(student);
        student.setFaculty(faculty);

        Faculty actual = this.restTemplate.getForObject("http://localhost:"
                + port + "/student" + student.getId() + "/faculty", Faculty.class);

        assertThat(actual.getId()).isEqualTo(faculty.getId());

        //        Faculty faculty1 = response.getBody();
//////        assert faculty1 != null;
//        assertThat(faculty.getName().equals(faculty1.getName())).isTrue();


    }
}
