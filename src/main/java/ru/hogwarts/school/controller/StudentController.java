package ru.hogwarts.school.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.CheckIDException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping()
    public Student createStudent(@RequestBody Student student) {
        return service.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = service.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public ResponseEntity updateStudentInfo(@RequestBody Student student) {
        Student foundStudent = service.updateStudentInfo(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity <Student> deleteStudent(@PathVariable Long id) {
        if (id < 0){
            throw new CheckIDException();
        }
        service.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<Collection<Student>> filteredByAge(@PathVariable int age) {
        if (age > 0){
            return ResponseEntity.ok(service.getStudentByAge(age));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/filter")
    public Collection<Student> filteredByAge(@RequestParam int minAge,
                                             @RequestParam int maxAge) {
        return service
                .getAllStudents()
                .stream()
                .filter(student -> (student.getAge() > minAge && student.getAge() < maxAge))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<?> findFacultyByStudent(@PathVariable Long id) {
        Student student = service.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getFaculty());
    }
}

