package Project;
import Project.Models.*;
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
    private static final DepartmentConsoleHandler deptHandler = new DepartmentConsoleHandler(deptService);
    private static final StudentConsoleHangler studentHandler = new StudentConsoleHangler(studentService);
    private static final TeacherConsoleHangler teacherHandler = new TeacherConsoleHangler(teacherService);

    public static void main(String[] args) throws Exception {

        Terminal terminal = TerminalBuilder.terminal();

        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .parser(new DefaultParser())
                .history(new DefaultHistory())
                .build();

        System.out.print("Login: ");
        String login = reader.readLine();

        System.out.print("Password: ");
        String password = reader.readLine();

        String role = authService.authorize(login, password);

        if (role == null) {
            System.out.println("Access denied");
            return;
        }

        System.out.println("Logged as: " + role);
        System.out.println("Type 'help -manager' to see commands");

        if (role.equals("manager")) {
            managerMenu(reader, role);
        } else {
            userMenu(reader, role);
        }
    }

    private static void managerMenu(LineReader reader, String role) {
        while (true) {
            String line = reader.readLine("Manager>> ").trim();
            if (line.isEmpty()) continue;

            switch (line) {
                case "help -manager":
                    System.out.println("""
                ===================================================
                |                DigiUni Manager                  |
                ===================================================
                | Action | Target | Description                   |
                |--------|--------|-------------------------------|
                | ls     | uni    | Show university               |
                |--------|--------|-------------------------------|
                | add    | fac    | Add a new faculty             |
                | edit   | fac    | Edit a faculty                |
                | rm     | fac    | Remove a faculty              |
                | ls     | fac    | Show all faculties            |
                |--------|--------|-------------------------------|
                | add    | dep    | Add a department              |
                | edit   | dep    | Edit a department             |
                | rm     | dep    | Remove a department           |
                | ls     | dep    | Show all departments          |
                |--------|--------|-------------------------------|
                | add    | stu    | Add a student                 |
                | edit   | stu    | Edit a student                |
                | rm     | stu    | Remove a student              |
                | ls     | stu    | Show all students             |
                |--------|--------|-------------------------------|
                | add    | tch    | Add a teacher                 |
                | edit   | tch    | Edit a teacher                |
                | rm     | tch    | Remove a teacher              |
                | ls     | tch    | Show all teachers             |
                ===================================================
                | exit            | Exit program                  |
                ===================================================
                * Tip: Type commands like 'add stu' or 'ls fac'
                """);
                    break;

                case "ls uni":
                    System.out.println(myUniversity);
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

                case "add tch":
                    teacherHandler.handleAddTeacher(reader);
                    break;

                case "edit tch":
                    //teacherHandler.handleEditTeacher(reader);
                    break;

                case "rm tch":
                    teacherHandler.handleRemoveTeacher(reader);
                    break;

                case "ls tch":
                    teacherHandler.handleShowAllTeachers();
                    break;

                case "exit":
                    System.out.println("Bye!");
                    return;

                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }


    private static void userMenu(LineReader reader, String role) {
        while (true) {
            String line = reader.readLine("User>> ").trim();
            if (line.isEmpty()) continue;

            switch (line) {
                case "help":
                    System.out.println("""
                            ==============================================
                            |               Commands:                    |
                            ==============================================
                            | faculty show      | - Show all faculties   |
                            | department show   | - Show all departments |
                            | student show      | - Show all students    |
                            | teacher show      | - Show all teachers    |
                            | exit              | - Exit program         |
                            ==============================================
                            """);
                    break;

                case "faculty show":
                    // facultyHandler.handleShowAll();
                    break;
                case "department show":
                    // System.out.println(departmentRegistry);
                    break;
                case "student show":
                    // System.out.println(studentRegistry);
                    break;
                case "teacher show":
                    System.out.println("Teachers list");
                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }
}