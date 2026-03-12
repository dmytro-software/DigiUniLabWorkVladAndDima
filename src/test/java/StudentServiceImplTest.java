package Project.service.Impl;

import Project.Exceptions.EntityNotFoundException;
import Project.Models.Faculty;
import Project.Models.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        University university = new University("Ukma", "Ukma", "Kyiv","address");
        FacultyServiceImpl facultyService = new FacultyServiceImpl(university);
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(facultyService);
        studentService = new StudentServiceImpl(departmentService);

        // Підготовка бази: Факультет -> Кафедра
        facultyService.addFaculty(new Faculty(1, "ФІ", "ФІ", "Декан", "123"));
        departmentService.addDepartment(10, "Інженерія", 1, "Зав", 101);
    }

    @Test
    void addStudent_ValidData_ShouldAddStudentToDepartment() {
        studentService.addStudent(10, 1001, "Шевченко Т.Г.", LocalDate.of(2000, 1, 1),
                "shev@ukma.edu.ua", 1234567890, 5050, 2, 4, 2024, "Денна", "Навчається");

        assertEquals(1, studentService.findAll().size());
    }

    @Test
    void editStudent_NonExistingId_ShouldThrowException() {
        assertThrows(EntityNotFoundException.class,
                () -> studentService.editStudent(9999, "Новий ПІБ", "email", 123, 1, 1, "Заочна", "Відрахований"));
    }

    @Test
    void removeStudent_ExistingId_ShouldReturnTrue() {
        studentService.addStudent(10, 1003, "Леся Українка", LocalDate.of(1999, 3, 3),
                "lesya@ukma.edu.ua", 222333444, 7070, 4, 2, 2022, "Денна", "Навчається");

        boolean removed = studentService.removeStudent(7070);
        assertTrue(removed);
        assertEquals(0, studentService.findAll().size());
    }

    @Test
    void findByGroup_ExistingGroup_ShouldReturnList() {
        studentService.addStudent(10, 1004, "Студент 1", LocalDate.now(), "e1", 1, 11, 1, 99, 2025, "Д", "А");
        studentService.addStudent(10, 1005, "Студент 2", LocalDate.now(), "e2", 2, 22, 1, 99, 2025, "Д", "А");

        assertEquals(2, studentService.findByGroup(99).size());
    }
}
