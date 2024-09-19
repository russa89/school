package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

@Service
public class FacultyService {

    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private long idCounter;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++idCounter);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getFaculty(long id) {
        return facultyMap.get(id);
    }

    public Faculty updateFacultyInfo(Faculty faculty) {
        if (!facultyMap.containsKey(faculty.getId())) {
            return null;
        }
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return facultyMap.remove(id);

    }

    public Collection<Faculty> getFacultyByColor(String color) {
        ArrayList<Faculty> facultyByColor = new ArrayList<>();
        for (Faculty faculty : facultyMap.values()) {
            if (Objects.equals(faculty.getColor(), color)) {
                facultyByColor.add(faculty);
            }
        }
        return facultyByColor;
    }
}
