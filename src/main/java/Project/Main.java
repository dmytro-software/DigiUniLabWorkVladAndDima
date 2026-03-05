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
                            ====================================
                            |               Commands:          |
                            |==================================|
                            | 0       | - Show university      |
                            |==================================|
                            | 1       | - Add a faculty        |
                            | 2       | - Edit a faculty       |
                            | 3       | - Remove a faculty     |
                            | 4       | - Show all faculties   |
                            |==================================|
                            | 5       | - Add a department     |
                            | 6       | - Remove a department  |
                            | 7       | - Edit a department    |
                            | 8       | - Show all departments |
                            |==================================|
                            | 9       | - Add a student        |
                            | 10      | - Edit a student       |
                            | 11      | - Remove a student     |
                            | 12      | - Show all students    |
                            |==================================|
                            | 13      | - Add a teacher        |
                            | 14      | - Remove a teacher     |
                            | 15      | - Edit a teacher       |
                            | 16      | - Show all teachers    |
                            |==================================|
                            | exit    | - Exit program         |
                            ===================================
                            """);
                    break;

                case "0":
                   System.out.println(myUniversity);
                    break;

                case "1":
                    facultyHandler.handleAddFaculty(reader);
                    break;

                case "2":
                    facultyHandler.handleEditFaculty(reader);
                    break;

                case "3":
                    facultyHandler.handleRemoveFaculty(reader);
                    break;

                case "4":
                    facultyHandler.handleShowAllFaculties();
                    break;

                case "5":
                    deptHandler.handleAddDepartment(reader);
                    break;

                case "7":
                    deptHandler.handleRemoveDepartment(reader);
                    break;
                case "8":
                    deptHandler.handleEditDepartment(reader);
                    break;

                case "9":
                    deptHandler.handleShowAllDepartments();
                    break;

                case "10":
                    studentHandler.handleAddStudent(reader);
                    break;

                case "11":
                    studentHandler.handleEditStudent(reader);
                    break;

                case "12":
                    studentHandler.handleRemoveStudent(reader);
                    break;

                case "13":
                    studentHandler.handleShowAllStudents();
                    break;

                case "14":
                    teacherHandler.handleAddTeacher(reader);
                    break;

                case "15":
                    //teacherHandler.handleEditTeacher(reader);
                    break;

                case "16":
                    teacherHandler.handleRemoveTeacher(reader);
                    break;

                case "17":
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