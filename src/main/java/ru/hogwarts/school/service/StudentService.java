package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hogwarts.school.exceptions.StudentDoesNotExistException;
import ru.hogwarts.school.exceptions.StudentListIsEmptyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        return studentRepository.findById(id).orElseThrow(StudentDoesNotExistException::new);
    }

    public Student updateStudentInfo(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentByAge(int age) {
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> getStudentBetweenAge(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getAllStudents() {
        List<Student> students = new ArrayList<>(studentRepository
                .findAll());
        if (students.isEmpty()) {
            throw new StudentListIsEmptyException();
        }
        return students;
    }

    public Faculty findFacultyByStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                StudentListIsEmptyException::new);
        return student.getFaculty();
    }
}