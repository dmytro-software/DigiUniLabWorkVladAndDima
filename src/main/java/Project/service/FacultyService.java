package Project.service;

import Project.Models.Faculty;

import java.io.IOException;
import java.util.List;

public interface FacultyService {

    void addFaculty(Faculty faculty) throws IOException;

    boolean removeFaculty(int id) throws IOException;

    void editFaculty(int id,
                     String name,
                     String shortName,
                     String head,
                     String contacts) throws IOException;

    Faculty findById(int id) throws IOException;

    List<Faculty> findAll();

}

