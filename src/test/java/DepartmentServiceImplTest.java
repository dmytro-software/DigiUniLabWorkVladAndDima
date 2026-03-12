package Project.service.Impl;

import Project.Exceptions.EntityNotEmptyException;
import Project.Exceptions.EntityNotFoundException;
import Project.Models.Department;
import Project.Models.Faculty;
import Project.Models.Student;
import Project.Models.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentServiceImplTest {
    private DepartmentServiceImpl departmentService;
    private FacultyServiceImpl facultyService;

    @BeforeEach
    void setUp() {
        University university = new University("Ukma", "Ukma", "Kyiv","address");
        facultyService = new FacultyServiceImpl(university);
        departmentService = new DepartmentServiceImpl(facultyService);

        // Створюємо базовий факультет для кафедр
        facultyService.addFaculty(new Faculty(10, "ФІ", "ФІ", "Декан", "123"));
    }

    @Test
    void addDepartment_ValidData_ShouldCreateLinkedDepartment() {
        departmentService.addDepartment(100, "Програмування", 10, "Зав", 301);
        assertEquals(1, departmentService.findAll().size());
        assertEquals(10, departmentService.findById(100).getFacultyId());
    }

    @Test
    void addDepartment_InvalidFacultyId_ShouldThrowException() {
        assertThrows(EntityNotFoundException.class,
                () -> departmentService.addDepartment(101, "Математика", 999, "Зав", 302));
    }

    @Test
    void removeDepartment_EmptyDepartment_ShouldRemoveSuccessfully() {
        departmentService.addDepartment(102, "Фізика", 10, "Зав", 303);
        boolean isRemoved = departmentService.removeDepartment(102);
        assertTrue(isRemoved);
        assertEquals(0, departmentService.findAll().size());
    }

    @Test
    void removeDepartment_WithStudents_ShouldThrowException() {
        departmentService.addDepartment(103, "Хімія", 10, "Зав", 304);
        Department dep = departmentService.findById(103);
        if (dep.getStudents() == null) dep.setStudents(new ArrayList<>());

        // Додаємо фейкового студента
        dep.getStudents().add(new Student(1, "Іванов", LocalDate.now(), "em@il", 123, 111, 1, 1, 2023, "Денна", "Активний"));

        assertThrows(EntityNotEmptyException.class, () -> departmentService.removeDepartment(103));
    }

    @Test
    void editDepartment_ValidData_ShouldUpdateFields() {
        departmentService.addDepartment(104, "Стара Назва", 10, "Старий Зав", 100);
        departmentService.editDepartment(104, "Нова Назва", 10, null, 200);

        Department updated = departmentService.findById(104);
        assertEquals("Нова Назва", updated.getDepartmentName());
        assertEquals(200, updated.getRoomNumber());
        assertEquals("Старий Зав", updated.getHeadOfDepartment());
    }
}