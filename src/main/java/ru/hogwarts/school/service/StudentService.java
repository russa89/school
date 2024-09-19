package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;

@Service

public class StudentService {

    private Map<Long, Student> students = new HashMap<>();
    private long idCounter;

    public Student createStudent(Student student) {
        student.setId(++idCounter);
        students.put(student.getId(), student);
        return student;
    }

    public Student getStudent(long id) {
        return students.get(id);
    }

    public Student updateStudentInfo(Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentByAge(int age) {
        ArrayList<Student> studentsByAge = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                studentsByAge.add(student);
            }
        }
        return studentsByAge;
    }
}
