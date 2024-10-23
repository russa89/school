package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        logger.info("Was invoked method for get student");
        return studentRepository.findById(id).orElseGet(() -> {
            logger.error("There is not student with id = " + id);
            throw new StudentDoesNotExistException();
        });

    }


    public Student updateStudentInfo(Student student) {
        logger.warn("Was invoked method for update student");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.warn("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentByAge(int age) {
        logger.info("Was invoked method for get student by age");
        return studentRepository
                .findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> getStudentBetweenAge(int minAge, int maxAge) {
        logger.info("Was invoked method for get student between ages");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        List<Student> students = new ArrayList<>(studentRepository
                .findAll());
        return students;
    }

    public Faculty findFacultyByStudent(long id) {
        logger.info("Was invoked method for get faculty by student");
        logger.error("There is not student with id = " + id);
        Student student = studentRepository.findById(id).orElseThrow(
                StudentListIsEmptyException::new);
        return student.getFaculty();
    }

    public Integer getAmountOfStudents() {
        logger.info("Was invoked method for get amount of students");
        return studentRepository.getAmountOfStudents();
    }

    public Integer getAverageAgeOfStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> getLast5Students() {
        logger.info("Was invoked method for get last 5 students");
        return studentRepository.getLast5Students();
    }

    public List<Student> getStudentByName(String name) {
        logger.info("Was invoked method for get student by name");
        return studentRepository.getStudentByName(name);
    }

    public List<Student> getStudentsByLetter(String letter) {

        return studentRepository
                .findAll()
                .stream()
                .filter(n -> n.getName()
                        .toUpperCase()
                        .startsWith(letter))
                .sorted(Comparator.comparing(Student::getName))
                .toList();
    }

    public Integer getAverageAgeOfStudentsByStream() {
        return (int) studentRepository
                .findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(Double.NaN);
    }

    public void printAllStudentsInParallel() {
        List<Student> students = studentRepository.findAll();
        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        new Thread(() -> {
            System.out.println(students.get(3).getName());
            System.out.println(students.get(4).getName());
        }).start();

        new Thread(() -> {
            System.out.println(students.get(5).getName());
            System.out.println(students.get(6).getName());
        }).start();
    }

    public void printAllStudentsSynchronized() {
        List<Student> students = studentRepository.findAll();
        synchronizedCount(students);
        synchronizedCount(students);

        new Thread(() -> {
            synchronizedCount(students);
            synchronizedCount(students);
        }).start();

        new Thread(() -> {
            synchronizedCount(students);
            synchronizedCount(students);
        }).start();
    }

    private Integer count = 1;

    public void synchronizedCount(List<Student> students) {
        System.out.println(count + " " + students.get(count).getName());
        count++;
    }
}

