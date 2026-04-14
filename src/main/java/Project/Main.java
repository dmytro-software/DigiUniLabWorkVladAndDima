package Project;

import Project.Models.*;
import Project.Data.DemoDataSeeder;
import Project.Repository.*;
import Project.service.*;
import Project.service.Impl.*;
import Project.ui.DepartmentConsoleHandler;
import Project.ui.FacultyConsoleHandler;
import Project.ui.StudentConsoleHangler;
import Project.ui.TeacherConsoleHangler;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static Project.Models.ConsoleColors.*;


public class Main {

    private static final University myUniversity = new University(
            "Kyiv-Mohyla Academy", "NaUKMA", "Kyiv", "Volodymyrska 60"
    );
    private static final AuthService authService = new AuthServiceImpl();

    private static final FacultyService facultyService = new FacultyServiceImpl(myUniversity);
    private static final DepartmentService deptService = new DepartmentServiceImpl(facultyService);
    private static final StudentService studentService = new StudentServiceImpl(deptService);
    private static final TeacherService teacherService = new TeacherServiceImpl(deptService);


    private static final FacultyConsoleHandler facultyHandler = new FacultyConsoleHandler(facultyService);
    private static final DepartmentConsoleHandler deptHandler = new DepartmentConsoleHandler(deptService, facultyService);
    private static final StudentConsoleHangler studentHandler = new StudentConsoleHangler(studentService);
    private static final TeacherConsoleHangler teacherHandler = new TeacherConsoleHangler(teacherService);

    private static final StudentRepository studentRepository = new StudentRepository();
    private static final TeacherRepository teacherRepository = new TeacherRepository();
    private static final FacultyRepository facultyRepository = new FacultyRepository();
    private static final DepartmentRepository departmentRepository = new DepartmentRepository(studentRepository, teacherRepository);
    private static final UniversityRepository universityRepository = new UniversityRepository(facultyRepository, departmentRepository);

    public static void main(String[] args) throws Exception {

        DemoDataSeeder.initDummyData(facultyService,deptService,teacherService,studentService);

        Terminal terminal = TerminalBuilder.terminal();

        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .parser(new DefaultParser())
                .history(new DefaultHistory())
                .build();
    while (true) {

        System.out.print(YELLOW + " ❯ " + RESET + "Login: ");
        String login = reader.readLine();

        System.out.print(YELLOW + " ❯ " + RESET + "Password: ");
        String password = reader.readLine();

        String role = authService.authorize(login, password);

        if (role == null) {
            System.out.println(RED + " ✗ Access denied\n" + RESET);
            continue;
        }

        System.out.println(GREEN + " ✓ Logged as: " + role + RESET);
        System.out.println(CYAN + " ℹ Type 'help -m' to see commands\n" + RESET);

        if (role.equals("manager")) {
            managerMenu(reader, role);
        }
        if (role.equals("admin")) {
            adminMenu(reader, role);
        } else {
            userMenu(reader, role);
        }
    }
    }

    private static void adminMenu(LineReader reader, String role) throws IOException {

        while (true) {
            String line = reader.readLine("Admin>> ").trim();
            if (line.isEmpty()) continue;

            switch (line) {
                case "help -m":
                    System.out.println(CYAN_BOLD + """
=====================================================""" + RESET + """
\n|               DigiUni ADMIN CLI                   |\n""" + CYAN_BOLD + """
=====================================================""" + RESET + """
\n| Action | Target   | Description                   |
|--------|----------|-------------------------------|
| ls     | uni      | Show university               |
|--------|----------|-------------------------------|
| add    | fac      | Add a new faculty             |
| edit   | fac      | Edit a faculty                |
| rm     | fac      | Remove a faculty              |
| ls     | fac      | Show all faculties            |
|--------|----------|-------------------------------|
| add    | dep      | Add a department              |
| edit   | dep      | Edit a department             |
| rm     | dep      | Remove a department           |
| ls     | dep      | Show all departments          |
|--------|----------|-------------------------------|
| add    | stu      | Add a student                 |
| edit   | stu      | Edit a student                |
| rm     | stu      | Remove a student              |
| ls     | stu      | Show all students             |
| find   | stu -p   | Find student by PIB           |
| find   | stu -g   | Find student by Group         |
| find   | stu -c   | Find student by Course        |
|--------|----------|-------------------------------|
| add    | tch      | Add a teacher                 |
| edit   | tch      | Edit a teacher                |
| rm     | tch      | Remove a teacher              |
| ls     | tch      | Show all teachers             |
| find   | tch -id  | Find teacher by ID            |
| find   | tch -n   | Find teacher by PIB           |
|--------|----------|-------------------------------|
| load   | uni      | Load data from files          |
| load   | fac      | Load faculties from file      |
| load   | dep      | Load departments from file    |
| load   | stu      | Load students from file       |
| load   | tch      | Load teachers from file       |\n""" + CYAN_BOLD + """
=====================================================
| exit              | Exit program                  |
=====================================================""" + RESET);
                    break;

                case "ls uni":
                    myUniversity.printInfo();
                    break;

                case "add fac":
                    facultyHandler.handleAddFaculty(reader);
                    break;

                case "edit fac":
                    facultyHandler.handleEditFaculty(reader);
                    break;

                case "rm fac":
                    facultyHandler.handleRemoveFaculty(reader);
                    break;

                case "ls fac":
                    facultyHandler.handleShowAllFaculties();
                    break;
                case "repo stu -f":
                    facultyHandler.handelShowStudentsReportByPibFromFaculty(reader);
                    break;
                case "repo tch -f":
                    facultyHandler.handelShowTeacherReportByPibFromFaculty(reader);
                    break;
                case "add dep":
                    deptHandler.handleAddDepartment(reader);
                    break;

                case "rm dep":
                    deptHandler.handleRemoveDepartment(reader);
                    break;
                case "edit dep":
                    deptHandler.handleEditDepartment(reader);
                    break;

                case "ls dep":
                    deptHandler.handleShowAllDepartments();
                    break;
                case "repo dep -s -c":
                    deptHandler.handleShowReportOfStudentGroupingByCourse(reader);
                    break;
                case "repo dep -s -a":
                    deptHandler.handleShowReportOfStudentsByAlphabet(reader);
                    break;
                case "repo dep -t -a":
                    deptHandler.handleShowReportOfTeachersByAlphabet(reader);
                    break;
                case "repo dep -s -cc":
                    deptHandler.handleShowReportOfStudentsByChoosedCourse(reader);
                    break;

                case "add stu":
                    studentHandler.handleAddStudent(reader);
                    break;

                case "edit stu":
                    studentHandler.handleEditStudent(reader);
                    break;

                case "rm stu":
                    studentHandler.handleRemoveStudent(reader);
                    break;

                case "ls stu":
                    studentHandler.handleShowAllStudents();
                    break;

                case "find stu -p":
                    studentHandler.handleFindStudentByPib(reader);
                    break;

                case "find stu -c":
                    studentHandler.handleFindStudentByCourse(reader);
                    break;

                case "find stu -g":
                    studentHandler.handleFindStudentsByGroup(reader);
                    break;
                case "repo stu -c":
                    studentHandler.handleShowStudentsReportByCourse(reader);
                    break;
                case "add tch":
                    teacherHandler.handleAddTeacher(reader);
                    break;

                case "edit tch":
                    teacherHandler.handleEditTeacher(reader);
                    break;

                case "rm tch":
                    teacherHandler.handleRemoveTeacher(reader);
                    break;

                case "ls tch":
                    teacherHandler.handleShowAllTeachers();
                    break;

                case "find tch -id":
                    teacherHandler.handleFindTeacherById(reader);
                    break;

                case "find tch -p":
                    teacherHandler.handelFindTeacherByPib(reader);
                    break;

                case "load uni":
                    UniversityRepository repo = new UniversityRepository(facultyRepository, departmentRepository);

                    Optional<University> optionalUniversity = repo.loadUniversity();

                    if (optionalUniversity.isPresent()) {
                        University university = optionalUniversity.get();
                        university.printInfo();
                    } else {
                        System.out.println(RED + " ✗ Університет не знайдено у файлі." + RESET);
                    }
                    break;

                case "load stu":
                    StudentRepository stuRepo = new StudentRepository();
                    Optional<List<Student>> optionalStudents = stuRepo.loadAll();
                    break;

                case "load tch":
                    TeacherRepository tchRepo = new TeacherRepository();
                    Optional<List<Teacher>> optionalTeachers = tchRepo.loadAll();
                    break;

                case "load dep":
                    DepartmentRepository dep = new DepartmentRepository(studentRepository, teacherRepository);
                    Optional<List<Department>> optionalDepartments = dep.loadAll();
                    break;

                case "load fac":
                    FacultyRepository fac = new FacultyRepository();
                    Optional<List<Faculty>> optionalFaculties = fac.loadAll();
                    break;

                case "exit":
                    departmentRepository.saveAll(deptService.findAll());
                    facultyRepository.saveAll(facultyService.findAll());
                    universityRepository.saveUniversity(myUniversity);
                    studentRepository.saveAll(studentService.findAll());
                    teacherRepository.saveAll(teacherService.findAll());
                    System.out.println(GREEN + " ✓ Saved. Bye!" + RESET);
                    return;

                default:
                    System.out.println(RED + " ✗ Unknown command." + RESET);
                    break;
            }
        }

    }

    private static void managerMenu(LineReader reader, String role) {
        while (true) {
            String line = reader.readLine("Manager>> ").trim();
            if (line.isEmpty()) continue;

            switch (line) {
                case "help -m":
                    System.out.println(CYAN_BOLD + """
=====================================================""" + RESET + """
\n|               DigiUni Manager CLI                 |\n""" + CYAN_BOLD + """
=====================================================""" + RESET + """
\n| Action | Target   | Description                   |
|--------|----------|-------------------------------|
| ls     | uni      | Show university               |
|--------|----------|-------------------------------|
| add    | fac      | Add a new faculty             |
| edit   | fac      | Edit a faculty                |
| ls     | fac      | Show all faculties            |
|--------|----------|-------------------------------|
| add    | dep      | Add a department              |
| edit   | dep      | Edit a department             |
| ls     | dep      | Show all departments          |
|--------|----------|-------------------------------|
| add    | stu      | Add a student                 |
| edit   | stu      | Edit a student                |
| ls     | stu      | Show all students             |
| find   | stu -n   | Find student by PIB           |
| find   | stu -g   | Find student by Group         |
| find   | stu -c   | Find student by Course        |
|--------|----------|-------------------------------|
| add    | tch      | Add a teacher                 |
| edit   | tch      | Edit a teacher                |
| ls     | tch      | Show all teachers             |
| find   | tch -id  | Find teacher by ID            |
| find   | tch -n   | Find teacher by PIB           |\n""" + CYAN_BOLD + """
=====================================================
| exit              | Exit program                  |
=====================================================""" + RESET);
                    break;

                case "ls uni":
                    myUniversity.printInfo();
                    break;

                case "add fac":
                    facultyHandler.handleAddFaculty(reader);
                    break;

                case "edit fac":
                    facultyHandler.handleEditFaculty(reader);
                    break;

                case "ls fac":
                    facultyHandler.handleShowAllFaculties();
                    break;

                case "add dep":
                    deptHandler.handleAddDepartment(reader);
                    break;

                case "edit dep":
                    deptHandler.handleEditDepartment(reader);
                    break;

                case "ls dep":
                    deptHandler.handleShowAllDepartments();
                    break;

                case "add stu":
                    studentHandler.handleAddStudent(reader);
                    break;

                case "edit stu":
                    studentHandler.handleEditStudent(reader);
                    break;

                case "ls stu":
                    studentHandler.handleShowAllStudents();
                    break;

                case "find stu -p":
                    studentHandler.handleFindStudentByPib(reader);
                    break;

                case "find stu -c":
                    studentHandler.handleFindStudentByCourse(reader);
                    break;

                case "find stu -g":
                    studentHandler.handleFindStudentsByGroup(reader);
                    break;

                case "add tch":
                    teacherHandler.handleAddTeacher(reader);
                    break;

                case "edit tch":
                    teacherHandler.handleEditTeacher(reader);
                    break;

                case "ls tch":
                    teacherHandler.handleShowAllTeachers();
                    break;

                case "find tch -id":
                    teacherHandler.handleFindTeacherById(reader);
                    break;

                case "find tch -p":
                    teacherHandler.handelFindTeacherByPib(reader);
                    break;

                case "exit":
                    System.out.println(GREEN + " ✓ Bye!" + RESET);
                    return;

                default:
                    System.out.println(RED + " ✗ Unknown command." + RESET);
                    break;
            }
        }
    }


    private static void userMenu(LineReader reader, String role) {
        while (true) {
            String line = reader.readLine("User>> ").trim();
            if (line.isEmpty()) continue;

            switch (line) {
                case "h":
                    System.out.println(CYAN_BOLD + """
============================================================""" + RESET + """
\n|                     DIGIUNI USER CLI                     |\n""" + CYAN_BOLD + """
============================================================""" + RESET + """
\n| COMMAND    | DESCRIPTION                                 |
|------------|---------------------------------------------|
| 1          | List all faculties                          |
| 2          | List all departments                        |
| 3          | List all students                           |
| 4          | List all teachers                           |
|------------|---------------------------------------------|
| SEARCH STUDENTS                                          |
| f s -p     | Search student by name                      |
| f s -c     | Search student by course                    |
| f s -g     | Search student by group                     |
|------------|---------------------------------------------|
| SEARCH TEACHERS                                          |
| f t -id    | Search teacher by ID                        |
| f t -p     | Search teacher by name                      |
|------------|---------------------------------------------|
| h          | Show this menu                              |\n""" + CYAN_BOLD + """
| exit       | Exit program                                |
============================================================""" + RESET);
                    break;

                case "1":
                    facultyHandler.handleShowAllFaculties();
                    break;
                case "2":
                    deptHandler.handleShowAllDepartments();
                    break;
                case "3":
                    studentHandler.handleShowAllStudents();
                    break;
                case "4":
                    teacherHandler.handleShowAllTeachers();
                    break;
                case "f s -p":
                    studentHandler.handleFindStudentByPib(reader);
                    break;
                case"f s -c":
                    studentHandler.handleFindStudentByCourse(reader);
                    break;
                case"f s -g":
                    studentHandler.handleFindStudentsByGroup(reader);
                    break;
                case "f t -id":
                    teacherHandler.handleFindTeacherById(reader);
                    break;
                case "f t -p":
                    teacherHandler.handelFindTeacherByPib(reader);
                    break;
                case "exit":
                    System.out.println(GREEN + " ✓ Bye!" + RESET);
                    return;
                default:
                    System.out.println(RED + " ✗ Unknown command." + RESET);
                    break;
            }
        }
    }
}

