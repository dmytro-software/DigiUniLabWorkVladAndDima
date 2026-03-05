package Project.service.Impl;

import Project.Models.Faculty;
import Project.Models.University;
import Project.service.FacultyService;
import java.util.ArrayList;
import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    private final List<Faculty> faculties = new ArrayList<>();
    private final University university;


    public FacultyServiceImpl(University university) {
        this.university = university;
    }

    @Override
    public void addFaculty(Faculty faculty) {
        if (faculty == null)
            throw new IllegalArgumentException("Faculty cannot be null");
        faculties.add(faculty);
    }

    @Override
    public boolean removeFaculty(int id) {
        for(Faculty fac : faculties)
            if(fac.getIdFaculty() == id){
               return faculties.remove(fac);
            }
        return false;
    }

    @Override
    public void editFaculty(int id, String name, String shortName, String head, String contacts) {
        Faculty faculty = findById(id);

        if (name != null && !name.isBlank())
            faculty.setFacultyName(name);
        if (shortName != null && !shortName.isBlank())
            faculty.setFacultyShortName(shortName);
        if (head != null && !head.isBlank())
            faculty.setHeadOfFaculty(head);
        if (contacts != null && !contacts.isBlank())
            faculty.setContactsOfFaculty(contacts);
    }

    @Override
    public Faculty findById(int id) {
       for(Faculty faculty : faculties)
           if(faculty.getIdFaculty() == id) {
               return faculty;
           }
        throw new IllegalArgumentException("Faculty with ID " + id + " not found");
    }

    @Override
    public List<Faculty> findAll() {
        return new ArrayList<>(faculties);
    }
}