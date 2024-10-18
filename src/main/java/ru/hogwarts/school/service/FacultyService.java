package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.exceptions.StudentListIsEmptyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(
                FacultyNotFoundException::new);
    }

    public Faculty updateFacultyInfo(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);

    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findByColor(color);
    }

//                .findAll()
//                .stream()
//                .filter(faculty -> faculty.getColor().equals(color))
//                .collect(Collectors.toList());


    public List<Student> findAllStudentsOfFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(
                FacultyNotFoundException::new);

        return faculty.getStudents();
    }

    public Collection<Faculty> getFacultyByName(String name) {
        return facultyRepository.findByName(name);
    }

}
