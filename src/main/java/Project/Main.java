package Project;

import Project.Exceptions.EntityNotFoundException;
import Project.Models.*;
import Project.Repository.*;
import Project.service.*;
import Project.service.Impl.*;
import Project.ui.*;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import java.io.IOException;
import java.util.Optional;

import static Project.Models.ConsoleColors.*;


public class Main {

    private static final University myUniversity = new University(
            "Kyiv-Mohyla Academy", "NaUKMA", "Kyiv", "Volodymyrska 60"
    );
    private static final AuthService authService = new AuthServiceImpl();
    private static final UserConsoleHandler userHandler = new UserConsoleHandler(authService);

    private static final LoadStateManager loadStateManager = new LoadStateManager();

    private static UniversityRepository universityRepository = new UniversityRepository();
    private static FacultyRepository  facultyRepository = new FacultyRepository(universityRepository);
    private static DepartmentRepository departmentRepository = new DepartmentRepository(universityRepository);
    private static StudentRepository  studentRepository = new StudentRepository(universityRepository);

    private static final FacultyService facultyService = new FacultyServiceImpl(myUniversity, facultyRepository, departmentRepository, universityRepository);
    public static final DepartmentService deptService = new DepartmentServiceImpl(universityRepository);
    private static final StudentService studentService = new StudentServiceImpl(deptService, studentRepository, departmentRepository, universityRepository);
    private static final TeacherService teacherService = new TeacherServiceImpl(deptService, departmentRepository, universityRepository);

    private static final FacultyConsoleHandler facultyHandler = new FacultyConsoleHandler(facultyService);
    private static final DepartmentConsoleHandler deptHandler = new DepartmentConsoleHandler(deptService, facultyService, studentService);
    private static final StudentConsoleHangler studentHandler = new StudentConsoleHangler(studentService);
    private static final TeacherConsoleHangler teacherHandler = new TeacherConsoleHangler(teacherService);

    public static void main(String[] args) throws Exception {

        Terminal terminal = TerminalBuilder.terminal();

        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .parser(new DefaultParser())
                .history(new DefaultHistory())
                .build();

        System.out.println("//  ____  _       _ _   _       _   ____            _     _              \n" +
                "// |  _ \\(_) __ _(_) | | |_ __ (_) |  _ \\ ___  __ _(_)___| |_ _ __ _   _ \n" +
                "// | | | | |/ _` | | | | | '_ \\| | | |_) / _ \\/ _` | / __| __| '__| | | |\n" +
                "// | |_| | | (_| | | |_| | | | | | |  _ <  __/ (_| | \\__ \\ |_| |  | |_| |\n" +
                "// |____/|_|\\__, |_|\\___/|_| |_|_| |_| \\_\\___|\\__, |_|___/\\__|_|   \\__, |\n" +
                "//          |___/                             |___/                |___/ ");
        System.out.println("v 3.0");
        System.out.println("© made by Vlad and Dima");
    while (true) {

        System.out.print(YELLOW + " ❯ " + RESET + "Login: ");
        String login = reader.readLine();

        System.out.print(YELLOW + " ❯ " + RESET + "Password: ");
        String password = reader.readLine();

        User currentUser = null;
        try {
            currentUser = authService.login(login, password);
        } catch (Exception e) {
            System.out.println(RED + " ✗ " + e.getMessage() + "\n" + RESET);
            continue;
        }
        String role = currentUser.getRole().name().toLowerCase();

        System.out.println(GREEN + " ✓ Logged as: " + role + RESET);
        System.out.println(CYAN + " ℹ Type 'help' to see commands\n" + RESET);

        if (role.equals("manager")) {
            managerMenu(reader, role);
        } else if (role.equals("admin")) {
            adminMenu(reader, role);
        } else {
            userMenu(reader, role);
        }
    }
    }

    private static void adminMenu(LineReader reader, String role) throws Exception {

        while (true) {
            String line = reader.readLine("Admin>> ").trim();
            if (line.isEmpty()) continue;

            switch (line) {
                case "help":
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
| repo   | stu -f   | Report students by faculty    |
| repo   | tch -f   | Report teachers by faculty    |
|--------|----------|-------------------------------|
| add    | dep      | Add a department              |
| edit   | dep      | Edit a department             |
| rm     | dep      | Remove a department           |
| ls     | dep      | Show all departments          |
| repo   | dep -s -c| Report students grouped by crs|
| repo   | dep -s -a| Report students by alphabet   |
| repo   | dep -s -cc| Report students by exact crs |
| repo   | dep -t -a| Report teachers by alphabet   |
| update | dep -s   | Change student's department   |
|--------|----------|-------------------------------|
| add    | stu      | Add a student                 |
| edit   | stu      | Edit a student                |
| rm     | stu      | Remove a student              |
| ls     | stu      | Show all students             |
| find   | stu -p   | Find student by PIB           |
| find   | stu -g   | Find student by Group         |
| find   | stu -c   | Find student by Course        |
| repo   | stu -c   | Report students by course     |
|--------|----------|-------------------------------|
| add    | tch      | Add a teacher                 |
| edit   | tch      | Edit a teacher                |
| rm     | tch      | Remove a teacher              |
| ls     | tch      | Show all teachers             |
| find   | tch -id  | Find teacher by ID            |
| find   | tch -p   | Find teacher by PIB           |
|--------|----------|-------------------------------|
| load   | uni      | Load data from files          |
| load   | fac      | Load faculties from file      |
| load   | dep      | Load departments from file    |
| load   | stu      | Load students from file       |
| load   | tch      | Load teachers from file       |
|--------|----------|-------------------------------|
| ls     | user     | Show all users                |
| add    | user     | Create a new user account     |
| rm     | user     | Delete a user account         |
| block  | user     | Block a user account          |
| unblock| user     | Unblock a user account        |
| edit   | role     | Change user's role            |\n""" + CYAN_BOLD + """
=====================================================
| exit              | Exit program                  |
=====================================================""" + RESET);
                    break;
                case "ls user":
                    userHandler.handelShowAllUsers();
                    break;

                case "add user":
                    userHandler.handleAddUser(reader);
                    break;

                case "block user":
                    userHandler.handleBlockUser(reader);
                    break;

                case "unblock user":
                    userHandler.handleUnblockUser(reader);
                    break;

                case "edit role":
                    userHandler.handleEditRole(reader);
                    break;
                case "rm user":
                    userHandler.handleDeleteUser(reader);

                case "ls uni":
                    myUniversity.printInfo();
                    universityRepository.saveUniversity(myUniversity);
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
                case "update dep -s":
                    deptHandler.changeStudentDepartment(reader);
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
                case "load dep": {

                    if (!loadStateManager.canLoadDepartments()) {
                        System.out.println("⚠ Load faculties first");
                        break;
                    }

                    Optional<University> uniOpt = universityRepository.loadUniversity();

                    if (uniOpt.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded = uniOpt.get();

                    for (Faculty fLoaded : loaded.faculties()) {

                        for (Faculty fCurrent : myUniversity.faculties()) {

                            if (fCurrent.getIdFaculty() == fLoaded.getIdFaculty()) {

                                fCurrent.getDepartments().clear();

                                if (fLoaded.getDepartments() != null) {
                                    fCurrent.getDepartments().addAll(fLoaded.getDepartments());
                                }
                            }
                        }
                    }

                    System.out.println(GREEN + "✓ Departments loaded successfully" + RESET);

                    for (Faculty f : myUniversity.faculties()) {
                        System.out.println("\nFaculty: " + f.getFacultyName());

                        if (f.getDepartments() == null || f.getDepartments().isEmpty()) {
                            System.out.println("  (no departments)");
                            continue;
                        }

                        for (Department d : f.getDepartments()) {
                            System.out.println("  ------------------------");
                            System.out.println("  Dept ID: " + d.getIdDepartment());
                            System.out.println("  Name: " + d.getDepartmentName());
                            System.out.println("  Faculty ID: " + d.getFacultyId());
                            System.out.println("  Head: " + d.getHeadOfDepartment());
                            System.out.println("  Room Number: " + d.getRoomNumber());
                            System.out.println("  ------------------------");
                        }
                    }
                    loadStateManager.setState(LoadState.DEPARTMENTS_LOADED);
                    break;
                }

                case "load uni":
                    Optional<University> uniOpt = universityRepository.loadUniversity();

                    if (uniOpt.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded = uniOpt.get();

                    myUniversity.faculties().clear();

                    myUniversity.faculties().addAll(loaded.faculties());

                    System.out.println(GREEN + "✓ University loaded successfully" + RESET);

                    System.out.println("\n========== UNIVERSITY ==========");

                    System.out.println("Name: " + myUniversity.universityName());
                    System.out.println("Short Name: " + myUniversity.universityShortName());
                    System.out.println("City: " + myUniversity.city());
                    System.out.println("Address: " + myUniversity.universityAddress());

                    System.out.println("\n========== FACULTIES ==========");

                    for (Faculty f : myUniversity.faculties()) {

                        System.out.println("================================");
                        System.out.println("Faculty ID: " + f.getIdFaculty());
                        System.out.println("Name: " + f.getFacultyName());
                        System.out.println("Short Name: " + f.getFacultyShortName());
                        System.out.println("Head: " + f.getHeadOfFaculty());
                        System.out.println("Contacts: " + f.getContactsOfFaculty());
                        System.out.println("================================");

                        for (Department d : f.getDepartments()) {

                            System.out.println("  Department: " + d.getDepartmentName());

                            System.out.println("    Students: " +
                                    (d.getStudents() == null ? 0 : d.getStudents().size()));

                            System.out.println("    Teachers: " +
                                    (d.getTeachers() == null ? 0 : d.getTeachers().size()));
                        }
                    }
                    loadStateManager.setState(LoadState.UNIVERSITY_LOADED);
                    break;

                case "load fac": {

                    if (!loadStateManager.canLoadFaculties()) {
                        System.out.println("⚠ Load university first");
                        break;
                    }

                    Optional<University> uniOpt1 = universityRepository.loadUniversity();

                    if (uniOpt1.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded1 = uniOpt1.get();

                    myUniversity.faculties().clear();

                    if (loaded1.faculties() != null) {
                        myUniversity.faculties().addAll(loaded1.faculties());
                    }

                    System.out.println(GREEN + "✓ Faculties loaded successfully" + RESET);

                    System.out.println("\n========== FACULTIES ==========");

                    for (Faculty f : myUniversity.faculties()) {

                        System.out.println("================================");
                        System.out.println("Faculty ID: " + f.getIdFaculty());
                        System.out.println("Name: " + f.getFacultyName());
                        System.out.println("Short Name: " + f.getFacultyShortName());
                        System.out.println("Head: " + f.getHeadOfFaculty());
                        System.out.println("Contacts: " + f.getContactsOfFaculty());

                        System.out.println("Departments count: " +
                                (f.getDepartments() == null ? 0 : f.getDepartments().size()));
                    }

                    System.out.println("================================");
                    loadStateManager.setState(LoadState.FACULTIES_LOADED);
                    break;
                }
                case "load stu": {

                    if (!loadStateManager.canLoadStudents()) {
                        System.out.println("⚠ Load departments first");
                        break;
                    }
                    Optional<University> uniOpt2 = universityRepository.loadUniversity();

                    if (uniOpt2.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded2 = uniOpt2.get();

                    for (Faculty f : myUniversity.faculties()) {

                        if (f.getDepartments() == null) continue;

                        for (Department d : f.getDepartments()) {

                            if (d.getStudents() != null) {
                                d.getStudents().clear();
                            }
                        }
                    }

                    for (Faculty fLoaded : loaded2.faculties()) {

                        for (Faculty fCurrent : myUniversity.faculties()) {

                            if (fCurrent.getIdFaculty() == fLoaded.getIdFaculty()) {

                                for (Department dLoaded : fLoaded.getDepartments()) {

                                    for (Department dCurrent : fCurrent.getDepartments()) {

                                        if (dCurrent.getIdDepartment() == dLoaded.getIdDepartment()) {

                                            if (dLoaded.getStudents() != null) {

                                                dCurrent.getStudents().addAll(dLoaded.getStudents());

                                                for (var s : dLoaded.getStudents()) {

                                                    System.out.println("✓ STUDENT LOADED");
                                                    System.out.println("ID Person: " + s.getIdPerson());
                                                    System.out.println("PIB: " + s.getPib());
                                                    System.out.println("Birth Date: " + s.getBirthDate());
                                                    System.out.println("Email: " + s.getEmail());
                                                    System.out.println("Phone: " + s.getPhoneNumber());
                                                    System.out.println("GradeBook ID: " + s.getGradeBookId());
                                                    System.out.println("Course: " + s.getCourse());
                                                    System.out.println("Group: " + s.getGroup());
                                                    System.out.println("Enrollment Year: " + s.getEnrollmentYear());
                                                    System.out.println("Form of Education: " + s.getFormOfEducation());
                                                    System.out.println("Status: " + s.getStudentStatus());
                                                    System.out.println("Department ID: " + s.getDepartmentId());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    System.out.println(GREEN + "✓ Students loaded successfully" + RESET);

                    for (Faculty f : myUniversity.faculties()) {

                        System.out.println("\nFaculty: " + f.getFacultyName());

                        if (f.getDepartments() == null) continue;

                        for (Department d : f.getDepartments()) {

                            System.out.println("  Department: " + d.getDepartmentName());
                            System.out.println("    Students: " +
                                    (d.getStudents() == null ? 0 : d.getStudents().size()));
                        }
                    }
                    loadStateManager.setState(LoadState.STUDENTS_LOADED);
                    break;
                }


                case "load tch": {
                    if (!loadStateManager.canLoadStudents()) {
                        System.out.println("⚠ Load departments first");
                        break;
                    }

                    Optional<University> uniOpt3 = universityRepository.loadUniversity();

                    if (uniOpt3.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded3 = uniOpt3.get();

                    for (Faculty f : myUniversity.faculties()) {

                        if (f.getDepartments() == null) continue;

                        for (Department d : f.getDepartments()) {

                            if (d.getTeachers() != null) {
                                d.getTeachers().clear();
                            }
                        }
                    }

                    for (Faculty fLoaded : loaded3.faculties()) {

                        for (Faculty fCurrent : myUniversity.faculties()) {

                            if (fCurrent.getIdFaculty() == fLoaded.getIdFaculty()) {

                                for (Department dLoaded : fLoaded.getDepartments()) {

                                    for (Department dCurrent : fCurrent.getDepartments()) {

                                        if (dCurrent.getIdDepartment() == dLoaded.getIdDepartment()) {

                                            if (dLoaded.getTeachers() != null) {

                                                dCurrent.getTeachers().addAll(dLoaded.getTeachers());

                                                for (var t : dLoaded.getTeachers()) {

                                                    System.out.println("\n================================");
                                                    System.out.println("✓ TEACHER LOADED");
                                                    System.out.println("ID Person: " + t.getIdPerson());
                                                    System.out.println("PIB: " + t.getPib());
                                                    System.out.println("Birth Date: " + t.getBirthDate());
                                                    System.out.println("Email: " + t.getEmail());
                                                    System.out.println("Phone: " + t.getPhoneNumber());
                                                    System.out.println("Teacher ID: " + t.getTeacherId());
                                                    System.out.println("Position: " + t.getPosition());
                                                    System.out.println("Department ID: " + t.getDepartmentId());
                                                    System.out.println("Academic Degree: " + t.getAcademicDegree());
                                                    System.out.println("Academic Rank: " + t.getAcademicRank());
                                                    System.out.println("Hire Date: " + t.getHireDate());
                                                    System.out.println("FTE: " + t.getFullTimeEquivalent());
                                                    System.out.println("================================");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    System.out.println(GREEN + "✓ Teachers loaded successfully" + RESET);

                    for (Faculty f : myUniversity.faculties()) {

                        System.out.println("\nFaculty: " + f.getFacultyName());

                        if (f.getDepartments() == null)
                            continue;

                        for (Department d : f.getDepartments()) {

                            System.out.println("  Department: " + d.getDepartmentName());
                            System.out.println("    Teachers: " +
                                    (d.getTeachers() == null ? 0 : d.getTeachers().size()));
                        }
                    }
                    loadStateManager.setState(LoadState.TEACHERS_LOADED);
                    break;
                }

                case "exit":
                    universityRepository.saveUniversity(myUniversity);
                    System.out.println(GREEN + "✓ Saved. Bye!" + RESET);
                    return;

                default:
                    System.out.println(RED + " ✗ Unknown command." + RESET);
                    break;
            }
        }
    }

    private static void managerMenu(LineReader reader, String role) throws Exception {
        while (true) {
            String line = reader.readLine("Manager>> ").trim();
            if (line.isEmpty()) continue;

            switch (line) {
                case "help":
                    System.out.println(CYAN_BOLD + """
=====================================================""" + RESET + """
\n|               DigiUni MANAGER CLI                 |\n""" + CYAN_BOLD + """
=====================================================""" + RESET + """
\n| Action | Target   | Description                   |
|--------|----------|-------------------------------|
| ls     | uni      | Show university               |
|--------|----------|-------------------------------|
| add    | fac      | Add a new faculty             |
| edit   | fac      | Edit a faculty                |
| rm     | fac      | Remove a faculty              |
| ls     | fac      | Show all faculties            |
| repo   | stu -f   | Report students by faculty    |
| repo   | tch -f   | Report teachers by faculty    |
|--------|----------|-------------------------------|
| add    | dep      | Add a department              |
| edit   | dep      | Edit a department             |
| rm     | dep      | Remove a department           |
| ls     | dep      | Show all departments          |
| repo   | dep -s -c| Report students grouped by crs|
| repo   | dep -s -a| Report students by alphabet   |
| repo   | dep -s -cc| Report students by exact crs |
| repo   | dep -t -a| Report teachers by alphabet   |
| update | dep -s   | Change student's department   |
|--------|----------|-------------------------------|
| add    | stu      | Add a student                 |
| edit   | stu      | Edit a student                |
| rm     | stu      | Remove a student              |
| ls     | stu      | Show all students             |
| find   | stu -p   | Find student by PIB           |
| find   | stu -g   | Find student by Group         |
| find   | stu -c   | Find student by Course        |
| repo   | stu -c   | Report students by course     |
|--------|----------|-------------------------------|
| add    | tch      | Add a teacher                 |
| edit   | tch      | Edit a teacher                |
| rm     | tch      | Remove a teacher              |
| ls     | tch      | Show all teachers             |
| find   | tch -id  | Find teacher by ID            |
| find   | tch -p   | Find teacher by PIB           |
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
                    universityRepository.saveUniversity(myUniversity);
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
                case "update dep -s":
                    deptHandler.changeStudentDepartment(reader);
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

                case "load dep": {

                    if (!loadStateManager.canLoadDepartments()) {
                        System.out.println("⚠ Load faculties first");
                        break;
                    }

                    Optional<University> uniOpt = universityRepository.loadUniversity();

                    if (uniOpt.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded = uniOpt.get();

                    for (Faculty fLoaded : loaded.faculties()) {

                        for (Faculty fCurrent : myUniversity.faculties()) {

                            if (fCurrent.getIdFaculty() == fLoaded.getIdFaculty()) {

                                fCurrent.getDepartments().clear();

                                if (fLoaded.getDepartments() != null) {
                                    fCurrent.getDepartments().addAll(fLoaded.getDepartments());
                                }
                            }
                        }
                    }

                    System.out.println(GREEN + "✓ Departments loaded successfully" + RESET);

                    for (Faculty f : myUniversity.faculties()) {
                        System.out.println("\nFaculty: " + f.getFacultyName());

                        if (f.getDepartments() == null || f.getDepartments().isEmpty()) {
                            System.out.println("  (no departments)");
                            continue;
                        }

                        for (Department d : f.getDepartments()) {
                            System.out.println("  ------------------------");
                            System.out.println("  Dept ID: " + d.getIdDepartment());
                            System.out.println("  Name: " + d.getDepartmentName());
                            System.out.println("  Faculty ID: " + d.getFacultyId());
                            System.out.println("  Head: " + d.getHeadOfDepartment());
                            System.out.println("  Room Number: " + d.getRoomNumber());
                            System.out.println("  ------------------------");
                        }
                    }
                    loadStateManager.setState(LoadState.DEPARTMENTS_LOADED);
                    break;
                }

                case "load uni":
                    Optional<University> uniOpt = universityRepository.loadUniversity();

                    if (uniOpt.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded = uniOpt.get();

                    myUniversity.faculties().clear();

                    myUniversity.faculties().addAll(loaded.faculties());

                    System.out.println(GREEN + "✓ University loaded successfully" + RESET);

                    System.out.println("\n========== UNIVERSITY ==========");

                    System.out.println("Name: " + myUniversity.universityName());
                    System.out.println("Short Name: " + myUniversity.universityShortName());
                    System.out.println("City: " + myUniversity.city());
                    System.out.println("Address: " + myUniversity.universityAddress());

                    System.out.println("\n========== FACULTIES ==========");

                    for (Faculty f : myUniversity.faculties()) {

                        System.out.println("================================");
                        System.out.println("Faculty ID: " + f.getIdFaculty());
                        System.out.println("Name: " + f.getFacultyName());
                        System.out.println("Short Name: " + f.getFacultyShortName());
                        System.out.println("Head: " + f.getHeadOfFaculty());
                        System.out.println("Contacts: " + f.getContactsOfFaculty());
                        System.out.println("================================");

                        for (Department d : f.getDepartments()) {

                            System.out.println("  Department: " + d.getDepartmentName());

                            System.out.println("    Students: " +
                                    (d.getStudents() == null ? 0 : d.getStudents().size()));

                            System.out.println("    Teachers: " +
                                    (d.getTeachers() == null ? 0 : d.getTeachers().size()));
                        }
                    }
                    loadStateManager.setState(LoadState.UNIVERSITY_LOADED);
                    break;

                case "load fac": {

                    if (!loadStateManager.canLoadFaculties()) {
                        System.out.println("⚠ Load university first");
                        break;
                    }

                    Optional<University> uniOpt1 = universityRepository.loadUniversity();

                    if (uniOpt1.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded1 = uniOpt1.get();

                    myUniversity.faculties().clear();

                    if (loaded1.faculties() != null) {
                        myUniversity.faculties().addAll(loaded1.faculties());
                    }

                    System.out.println(GREEN + "✓ Faculties loaded successfully" + RESET);

                    System.out.println("\n========== FACULTIES ==========");

                    for (Faculty f : myUniversity.faculties()) {

                        System.out.println("================================");
                        System.out.println("Faculty ID: " + f.getIdFaculty());
                        System.out.println("Name: " + f.getFacultyName());
                        System.out.println("Short Name: " + f.getFacultyShortName());
                        System.out.println("Head: " + f.getHeadOfFaculty());
                        System.out.println("Contacts: " + f.getContactsOfFaculty());

                        System.out.println("Departments count: " +
                                (f.getDepartments() == null ? 0 : f.getDepartments().size()));
                    }

                    System.out.println("================================");
                    loadStateManager.setState(LoadState.FACULTIES_LOADED);
                    break;
                }
                case "load stu": {

                    if (!loadStateManager.canLoadStudents()) {
                        System.out.println("⚠ Load departments first");
                        break;
                    }
                    Optional<University> uniOpt2 = universityRepository.loadUniversity();

                    if (uniOpt2.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded2 = uniOpt2.get();

                    for (Faculty f : myUniversity.faculties()) {

                        if (f.getDepartments() == null) continue;

                        for (Department d : f.getDepartments()) {

                            if (d.getStudents() != null) {
                                d.getStudents().clear();
                            }
                        }
                    }

                    for (Faculty fLoaded : loaded2.faculties()) {

                        for (Faculty fCurrent : myUniversity.faculties()) {

                            if (fCurrent.getIdFaculty() == fLoaded.getIdFaculty()) {

                                for (Department dLoaded : fLoaded.getDepartments()) {

                                    for (Department dCurrent : fCurrent.getDepartments()) {

                                        if (dCurrent.getIdDepartment() == dLoaded.getIdDepartment()) {

                                            if (dLoaded.getStudents() != null) {

                                                dCurrent.getStudents().addAll(dLoaded.getStudents());

                                                for (var s : dLoaded.getStudents()) {

                                                    System.out.println("✓ STUDENT LOADED");
                                                    System.out.println("ID Person: " + s.getIdPerson());
                                                    System.out.println("PIB: " + s.getPib());
                                                    System.out.println("Birth Date: " + s.getBirthDate());
                                                    System.out.println("Email: " + s.getEmail());
                                                    System.out.println("Phone: " + s.getPhoneNumber());
                                                    System.out.println("GradeBook ID: " + s.getGradeBookId());
                                                    System.out.println("Course: " + s.getCourse());
                                                    System.out.println("Group: " + s.getGroup());
                                                    System.out.println("Enrollment Year: " + s.getEnrollmentYear());
                                                    System.out.println("Form of Education: " + s.getFormOfEducation());
                                                    System.out.println("Status: " + s.getStudentStatus());
                                                    System.out.println("Department ID: " + s.getDepartmentId());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    System.out.println(GREEN + "✓ Students loaded successfully" + RESET);

                    for (Faculty f : myUniversity.faculties()) {

                        System.out.println("\nFaculty: " + f.getFacultyName());

                        if (f.getDepartments() == null) continue;

                        for (Department d : f.getDepartments()) {

                            System.out.println("  Department: " + d.getDepartmentName());
                            System.out.println("    Students: " +
                                    (d.getStudents() == null ? 0 : d.getStudents().size()));
                        }
                    }
                    loadStateManager.setState(LoadState.STUDENTS_LOADED);
                    break;
                }

                case "load tch": {
                    if (!loadStateManager.canLoadStudents()) {
                        System.out.println("⚠ Load departments first");
                        break;
                    }

                    Optional<University> uniOpt3 = universityRepository.loadUniversity();

                    if (uniOpt3.isEmpty()) {
                        System.out.println("⚠ university.json not found");
                        break;
                    }

                    University loaded3 = uniOpt3.get();

                    for (Faculty f : myUniversity.faculties()) {

                        if (f.getDepartments() == null) continue;

                        for (Department d : f.getDepartments()) {

                            if (d.getTeachers() != null) {
                                d.getTeachers().clear();
                            }
                        }
                    }

                    for (Faculty fLoaded : loaded3.faculties()) {

                        for (Faculty fCurrent : myUniversity.faculties()) {

                            if (fCurrent.getIdFaculty() == fLoaded.getIdFaculty()) {

                                for (Department dLoaded : fLoaded.getDepartments()) {

                                    for (Department dCurrent : fCurrent.getDepartments()) {

                                        if (dCurrent.getIdDepartment() == dLoaded.getIdDepartment()) {

                                            if (dLoaded.getTeachers() != null) {

                                                dCurrent.getTeachers().addAll(dLoaded.getTeachers());

                                                for (var t : dLoaded.getTeachers()) {

                                                    System.out.println("\n================================");
                                                    System.out.println("✓ TEACHER LOADED");
                                                    System.out.println("ID Person: " + t.getIdPerson());
                                                    System.out.println("PIB: " + t.getPib());
                                                    System.out.println("Birth Date: " + t.getBirthDate());
                                                    System.out.println("Email: " + t.getEmail());
                                                    System.out.println("Phone: " + t.getPhoneNumber());
                                                    System.out.println("Teacher ID: " + t.getTeacherId());
                                                    System.out.println("Position: " + t.getPosition());
                                                    System.out.println("Department ID: " + t.getDepartmentId());
                                                    System.out.println("Academic Degree: " + t.getAcademicDegree());
                                                    System.out.println("Academic Rank: " + t.getAcademicRank());
                                                    System.out.println("Hire Date: " + t.getHireDate());
                                                    System.out.println("FTE: " + t.getFullTimeEquivalent());
                                                    System.out.println("================================");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    System.out.println(GREEN + "✓ Teachers loaded successfully" + RESET);

                    for (Faculty f : myUniversity.faculties()) {

                        System.out.println("\nFaculty: " + f.getFacultyName());

                        if (f.getDepartments() == null)
                            continue;

                        for (Department d : f.getDepartments()) {

                            System.out.println("  Department: " + d.getDepartmentName());
                            System.out.println("    Teachers: " +
                                    (d.getTeachers() == null ? 0 : d.getTeachers().size()));
                        }
                    }
                    loadStateManager.setState(LoadState.TEACHERS_LOADED);
                    break;
                }

                case "exit":
                    universityRepository.saveUniversity(myUniversity);
                    System.out.println(GREEN + "✓ Saved. Bye!" + RESET);
                    return;

                default:
                    System.out.println(RED + " ✗ Unknown command." + RESET);
                    break;
            }
        }
    }


    private static void userMenu(LineReader reader, String role) throws IOException {
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

