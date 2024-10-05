package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.CheckIDException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return service.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = service.getFaculty(id);
        if (id < 0) {
            throw new CheckIDException();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> updateFacultyInfo(@PathVariable long id, @RequestBody Faculty faculty) {
        Faculty foundFaculty = service.updateFacultyInfo(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        service.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color")
    public Collection<Faculty> filteredByColor(@RequestParam String color) {
        return service.getFacultyByColor(color);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity findAllStudentsOfFaculty(@PathVariable long id) {
        Faculty faculty = service.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.getFaculty(id).getStudents());
    }

}
