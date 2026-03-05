package Project.service;

import Project.Models.Faculty;

import java.util.List;

public interface FacultyService {

    void addFaculty(Faculty faculty);

    boolean removeFaculty(int id);

    void editFaculty(int id,
                     String name,
                     String shortName,
                     String head,
                     String contacts);

    Faculty findById(int id);

    List<Faculty> findAll();

}

