package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    List<Student> findAll();
    List<Student> findAllByAge(int age);

    List<Student> getStudentByName(String name);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    Integer getAmountOfStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getAverageAgeOfStudents();

    @Query(value = "SELECT * FROM student ORDER BY id desc LIMIT 5", nativeQuery = true)
    List<Student> getLast5Students();
}
