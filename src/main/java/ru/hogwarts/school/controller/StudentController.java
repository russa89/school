package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.CheckIDException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    @Autowired
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
        if (id < 0){
            throw new CheckIDException();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public ResponseEntity<Student> updateStudentInfo(@RequestBody Student student) {
        Student foundStudent = service.updateStudentInfo(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        if (id < 0){
            throw new CheckIDException();
        }
        service.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> filteredByAge(@PathVariable int age) {
        if (age > 0) {
            return ResponseEntity.ok(service.getStudentByAge(age));
        }
        return (ResponseEntity<Collection<Student>>) ResponseEntity.badRequest();
    }
}
