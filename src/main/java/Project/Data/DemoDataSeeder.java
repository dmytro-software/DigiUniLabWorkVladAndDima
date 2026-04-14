package Project.Data;

import Project.Models.Faculty;
import Project.service.DepartmentService;
import Project.service.FacultyService;
import Project.service.StudentService;
import Project.service.TeacherService;

import java.time.LocalDate;

public class DemoDataSeeder {
    public static void initDummyData(
            FacultyService facultyService,
            DepartmentService departmentService,
            TeacherService teacherService,
            StudentService studentService) {

        try {
            facultyService.addFaculty(new Faculty(1, "Факультет Інформатики", "ФІ", "Глибовець", "0441112233"));
            facultyService.addFaculty(new Faculty(2, "Правничий Факультет", "ФПрН", "Азаров", "0442223344"));
            departmentService.addDepartment(101, "Інженерія програмного забезпечення", 1, "Бублик", 320);
            departmentService.addDepartment(102, "Комп'ютерні науки", 1, "Франчук", 115);
            departmentService.addDepartment(201, "Загальнотеоретичне право", 2, "Козюбра", 410);
            teacherService.addTeacher(101, 5001, "Бублик Володимир", LocalDate.of(1975, 5, 10),
                    "bublyk@ukma.edu.ua", 981234567, 7001, "Завідувач", "Кандидат", "Доцент", "2010.09.01", 1.0);

            teacherService.addTeacher(102, 5002, "Глибовець Андрій", LocalDate.of(1968, 11, 20),
                    "hlybovets@ukma.edu.ua", 981234568, 7002, "Декан", "Доктор", "Професор", "2005.09.01", 1.0);

            studentService.addStudent(101, 1001, "Резанов Влад", LocalDate.of(2005, 8, 15),
                    "vlad@ukma.edu.ua", 980000001, 200001, 2, 1, 2024, "Денна", "Активний");
            studentService.addStudent(101, 1001, "Алабай", LocalDate.of(2005, 8, 15),
                    "vlad@ukma.edu.ua", 980000001, 200001, 2, 1, 2024, "Денна", "Активний");
            studentService.addStudent(101, 1002, "Діма Поплавський", LocalDate.of(2004, 10, 12),
                    "dima@ukma.edu.ua", 980000002, 200002, 2, 1, 2024, "Денна", "Активний");

            studentService.addStudent(101, 1003, "Шевченко Ганна", LocalDate.of(2006, 5, 20),
                    "anna@ukma.edu.ua", 980000003, 200003, 1, 2, 2025, "Денна", "Активний");

            studentService.addStudent(102, 1004, "Лисенко Марія", LocalDate.of(2005, 2, 28),
                    "maria@ukma.edu.ua", 980000004, 200004, 3, 3, 2023, "Денна", "Активний");

            studentService.addStudent(201, 1005, "Мельник Софія", LocalDate.of(2003, 12, 5),
                    "sofia@ukma.edu.ua", 980000005, 200005, 4, 1, 2022, "Денна", "Активний");

            System.out.println("Base has been created");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
