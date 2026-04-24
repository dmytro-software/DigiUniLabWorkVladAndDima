//package Project.service.Impl;
//
//import Project.Exceptions.EntityNotEmptyException;
//import Project.Exceptions.EntityNotFoundException;
//import Project.Models.Department;
//import Project.Models.Faculty;
//import Project.Models.University;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////class FacultyServiceImplTest {
//    private FacultyServiceImpl facultyService;
//
//    @BeforeEach
//    void setUp() {
//        University university = new University("Ukma", "Ukma", "Kyiv","address");
//        facultyService = new FacultyServiceImpl(university);
//    }
//
//    @Test
//    void addFaculty_ValidData_ShouldIncreaseSize() {
//        Faculty faculty = new Faculty(1, "ФІ", "ФІ", "Гданськ", "123");
//        facultyService.addFaculty(faculty);
//        assertEquals(1, facultyService.findAll().size());
//    }
//
//    @Test
//    void findById_ExistingId_ShouldReturnFaculty() {
//        facultyService.addFaculty(new Faculty(2, "ФПрН", "ФПрН", "Декан", "321"));
//        Faculty found = facultyService.findById(2);
//        assertNotNull(found);
//        assertEquals("ФПрН", found.getFacultyName());
//    }
//
//    @Test
//    void findById_NonExistingId_ShouldThrowException() {
//        assertThrows(EntityNotFoundException.class, () -> facultyService.findById(999));
//    }
//
//    @Test
//    void editFaculty_ValidData_ShouldUpdateFields() {
//        facultyService.addFaculty(new Faculty(3, "Стара", "СТ", "Хтось", "000"));
//        facultyService.editFaculty(3, "Нова", null, null, null);
//
//        Faculty updated = facultyService.findById(3);
//        assertEquals("Нова", updated.getFacultyName());
//        assertEquals("СТ", updated.getFacultyShortName()); // Мало залишитись старим
//    }
//
//}