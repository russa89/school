package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        logger.info("Was invoked method for get faculty");
        logger.debug("Requesting info for get faculty with id: {}", id);
        logger.error("There is not faculty with id = " + id);
        return facultyRepository.findById(id).orElseThrow(
                FacultyNotFoundException::new);
    }

    public Faculty updateFacultyInfo(Faculty faculty) {
        logger.warn("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.warn("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);

    }

    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Was invoked method for get faculty by color");
        return facultyRepository.findByColor(color);
    }

    public List<Student> findAllStudentsOfFaculty(long id) {
        logger.info("Was invoked method for get all students of faculty");
        logger.error("There is not faculty with id = " + id);
        Faculty faculty = facultyRepository.findById(id).orElseThrow(
                FacultyNotFoundException::new);

        return faculty.getStudents();
    }

    public Collection<Faculty> getFacultyByName(String name) {
        logger.info("Was invoked method for find get faculty by name");
        return facultyRepository.findByName(name);
    }
}
