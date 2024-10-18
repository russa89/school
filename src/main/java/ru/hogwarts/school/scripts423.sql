
SELECT student.name, student.age, faculty.name
FROM student
LEFT JOIN faculty ON student.faculty_id = faculty.id

SELECT student.name, student.age
FROM student
LEFT JOIN avatar ON student.id = avatar.student_id