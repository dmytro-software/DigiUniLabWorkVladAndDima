package Project;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Main {

    private static FacultyRegistry facultyRegistry = new FacultyRegistry();
    private static DepartmentRegistry departmentRegistry = new DepartmentRegistry();
    private static StudentRegistry studentRegistry = new StudentRegistry();
    private static TeacherRegistry teacherRegistry = new TeacherRegistry();
    private static UniversityRegistry universityRegistry = new UniversityRegistry();

    public static void main(String[] args) throws Exception {

            universityRegistry.setUniversity(
                    new University("Kyiv-Mohyla Academy або просто Mohyla Academy",
                            "NaUkma",
                            "Kyiv",
                            "Volodymyrska 60")
            );

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

        String role = authorize(login, password);

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
    private static void managerMenu (LineReader reader, String role){
        while (true) {
            String line = reader.readLine("Manager>> ").trim();
            if (line.isEmpty()) continue;

            switch (line) {
                case "help -manager":
                    System.out.println("""
                                ==============================================
                                |               Commands:                    |
                                ==============================================
                                | university show   | - Show university      |
                                |============================================|
                                | faculty add       | - Add a faculty        |
                                | faculty edit      | - Edit a faculty       |
                                | faculty remove    | - Remove a faculty     |
                                | faculty show      | - Show all faculties   |
                                |============================================|
                                | department add    | - Add a department     |
                                | department remove | - Remove a department  |
                                | department edit   | - Edit a department    |
                                | department show   | - Show all departments |
                                |============================================|
                                | student add       | - Add a student        |
                                | student edit      | - Edit a student       |
                                | student remove    | - Remove a student     |
                                | student show      | - Show all students    |
                                | student transfer  | - Show all students    |
                                |============================================|
                                | teacher add       | - Add a teacher        |
                                | teacher remove    | - Remove a teacher     |
                                | teacher edit      | - Edit a teacher       |
                                | teacher show      | - Show all teachers    |
                                |============================================|
                                | exit              | - Exit program         |
                                ==============================================
                                """);
                    break;

                case "university show":
                    System.out.println(universityRegistry.getUniversity());
                    break;

                case "faculty add":
                    addFaculty(reader, role);
                    break;

                case "faculty edit":
                    editFaculty(reader, role);
                    break;

                case "faculty remove":
                    removeFaculty(reader, role);
                    break;

                case "faculty show":
                    System.out.println(facultyRegistry);
                    break;

                case "department add":
                    addDepartment(reader, role);
                    break;

                case "department remove":
                    removeDepartment(reader, role);
                    break;

                case "department edit":
                    editDepartment(reader, role);

                case "department show":
                    System.out.println(departmentRegistry);
                    break;

                case "student add":
                    addStudent(reader, role);
                    break;

                case "student edit":
                    editStudent(reader, role);
                    break;

                case "student remove":
                    removeStudent(reader, role);
                    break;

                case "student show":
                    System.out.println(studentRegistry);
                    break;

                case "student transfer":
                    transferStudent(reader);
                    break;
                case "teacher add":
                    addTeacher(reader, role);
                    break;
                case "teacher show":
                    System.out.println(teacherRegistry);
                case "exit":
                    System.out.println("Bye!");
                    return;

                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }


    private static void userMenu (LineReader reader, String role){
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
                    System.out.println(facultyRegistry);
                    break;
                case "department show":
                    System.out.println(departmentRegistry);
                    break;
                case "student show":
                    System.out.println(studentRegistry);
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

    private static String authorize(String login, String password) {
        if (login.equals("admin") && password.equals("123"))
            return "manager";
        if (login.equals("user") && password.equals("123"))
            return "user";
        return null;
    }

    private static void addFaculty(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Faculty id: "));
            String name = reader.readLine("Name: ");
            String shortName = reader.readLine("Short name: ");
            String head = reader.readLine("Head: ");
            String contacts = reader.readLine("Contacts: ");

            Faculty faculty = new Faculty(id, name, shortName, head, contacts);

            universityRegistry.getUniversity().addFaculty(faculty);
            facultyRegistry.addFaculty(faculty);

            System.out.println("Faculty added to university.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void editFaculty(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Faculty id: "));
            Faculty faculty = findFaculty(id);

            String name = reader.readLine("Name (" + faculty.getFacultyName() + "): ");
            if (name.isBlank())
                name = faculty.getFacultyName();

            String shortName = reader.readLine("Short name (" + faculty.getFacultyShortName() + "): ");
            if (shortName.isBlank())
                shortName = faculty.getFacultyShortName();

            String head = reader.readLine("Head (" + faculty.getHeadOfFaculty() + "): ");
            if (head.isBlank())
                head = faculty.getHeadOfFaculty();

            String contacts = reader.readLine("Contacts (" + faculty.getContactsOfFaculty() + "): ");
            if (contacts.isBlank())
                contacts = faculty.getContactsOfFaculty();

            facultyRegistry.editFaculty(id, name, shortName, head, contacts);

            System.out.println("Faculty updated.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeFaculty(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Faculty id to remove: "));
            facultyRegistry.removeFaculty(id);
            System.out.println("Faculty removed.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addDepartment(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Department id: "));
            String name = reader.readLine("Department name: ");

            int facultyName = Integer.parseInt(reader.readLine("Enter Faculty name for this department: "));
            Faculty faculty = findFaculty(facultyName);

            String headOfDepartment = reader.readLine("Head of department: ");

            int roomNumber = Integer.parseInt(reader.readLine("Room number: "));

            Department department = new Department(id, name, faculty, headOfDepartment, roomNumber);

            departmentRegistry.addDepartment(department);

            universityRegistry.getUniversity().addDepartment(department);

            System.out.println("Department added successfully and attached to university.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void removeDepartment(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Department id to remove: "));
            departmentRegistry.removeDepartment(id);
            System.out.println("Department removed.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editDepartment(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Enter Department ID to edit: "));

            Department dept = departmentRegistry.findDepartmentById(id);

            System.out.println("Editing department. Press Enter to keep current value.");

            String name = reader.readLine("Name (" + dept.getDepartmentName() + "): ");
            if (!name.isBlank())
                dept.setDepartmentName(name);

            String facultyIdStr = reader.readLine("New Faculty ID (Current: " + dept.getFaculty().getFacultyName() + "): ");
            if (!facultyIdStr.isBlank()) {
                Faculty newFaculty = findFaculty(Integer.parseInt(facultyIdStr));
                dept.setFaculty(newFaculty);
            }

            String head = reader.readLine("Head (" + dept.getHeadOfDepartment() + "): ");
            if (!head.isBlank()) dept.setHeadOfDepartment(head);

            String roomStr = reader.readLine("Room (" + dept.getRoomNumberOfDepartment() + "): ");
            if (!roomStr.isBlank()) dept.setRoomNumberOfDepartment(Integer.parseInt(roomStr));

            System.out.println("Department updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addStudent(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int idPerson = Integer.parseInt(reader.readLine("Person ID: "));
            String pib = reader.readLine("Full Name (PIB): ");
            String birthDate = reader.readLine("Birth Date: ");
            String email = reader.readLine("Email: ");
            int phoneNumber = Integer.parseInt(reader.readLine("Phone Number: "));

            int gradeBookId = Integer.parseInt(reader.readLine("GradeBook ID: "));
            int course = Integer.parseInt(reader.readLine("Course: "));
            int group = Integer.parseInt(reader.readLine("Group: "));
            int enrollmentYear = Integer.parseInt(reader.readLine("Enrollment year: "));
            String formOfEducation = reader.readLine("Form of Education: ");
            String studentStatus = reader.readLine("Student status: ");

            int facultyName = Integer.parseInt(reader.readLine("Faculty name: "));
            Faculty faculty = findFaculty(facultyName);

            Student student = new Student(idPerson, pib, birthDate, email, phoneNumber,
                    gradeBookId, course, faculty, group, enrollmentYear,
                    formOfEducation, studentStatus);

            studentRegistry.addStudent(student);

            faculty.addStudent(student);

            System.out.println("Student added and attached to faculty.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void transferStudent(LineReader reader) {
        try {
            int gradeBookId = Integer.parseInt(reader.readLine("Enter GradeBook ID of student: "));
            Student student = studentRegistry.findStudentByGradeBook(gradeBookId);

            System.out.println("Current Faculty: " + student.getFaculty().getFacultyName());
            System.out.println("Current Group: " + student.getGroup());
            System.out.println("Current Course: " + student.getCourse());

            String transferDeptStr = reader.readLine("Enter new Department name to transfer: ");
            if (!transferDeptStr.isBlank()) {
                int newDeptName = Integer.parseInt(transferDeptStr);
                Department newDept = departmentRegistry.findDepartmentById(newDeptName);

                student.getFaculty().removeStudent(student);

                // Прив'язуємо студента до нового факультету кафедри
                student.setFaculty(newDept.getFaculty());
                newDept.getFaculty().addStudent(student);

                System.out.println("Student transferred to department: " + newDept.getDepartmentName() +
                        " | Faculty: " + newDept.getFaculty().getFacultyName());
            }

            // Зміна групи
            String groupStr = reader.readLine("Enter new group number (or Enter to skip): ");
            if (!groupStr.isBlank()) {
                int newGroup = Integer.parseInt(groupStr);
                student.setGroup(newGroup);
                System.out.println("Student group changed to: " + newGroup);
            }

            // Зміна курсу
            String courseStr = reader.readLine("Enter new course (or Enter to skip): ");
            if (!courseStr.isBlank()) {
                int newCourse = Integer.parseInt(courseStr);
                student.setCourse(newCourse);
                System.out.println("Student course changed to: " + newCourse);
            }

            System.out.println("Transfer and updates completed successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void editStudent(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Enter GradeBook ID of student to edit: "));
            Student student = studentRegistry.findStudentByGradeBook(id);

            System.out.println("Editing student. Press Enter to keep current value.");

            String courseStr = reader.readLine("Course (" + student.getCourse() + "): ");
            if (!courseStr.isBlank()) student.setCourse(Integer.parseInt(courseStr));

            String groupStr = reader.readLine("Group (" + student.getGroup() + "): ");
            if (!groupStr.isBlank()) student.setGroup(Integer.parseInt(groupStr));

            String form = reader.readLine("Form of Education (" + student.getFormOfEducation() + "): ");
            if (!form.isBlank()) student.setFormOfEducation(form);

            String status = reader.readLine("Status (" + student.getStudentStatus() + "): ");
            if (!status.isBlank()) student.setStudentStatus(status);

            System.out.println("Student record updated.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void addTeacher(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int idPerson = Integer.parseInt(reader.readLine("Person ID: "));
            String pib = reader.readLine("Full Name: ");
            String birthDate = reader.readLine("Birth Date (YYYYMMDD): ");
            String email = reader.readLine("Email: ");
            int phoneNumber = Integer.parseInt(reader.readLine("Phone Number: "));
            int teacherId = Integer.parseInt(reader.readLine("Teacher ID: "));
            String position = reader.readLine("Position: ");
            String academicDegree = reader.readLine("Academic Degree: ");
            String academicRank = reader.readLine("Academic Rank: ");
            String hireDate = reader.readLine("Hire Date (YYYYMMDD): ");

            System.out.print("Full Time Equivalent: ");
            String input = reader.readLine().replace(',', '.');
            double fullTimeEquivalent = Double.parseDouble(input);

            int deptId = Integer.parseInt(reader.readLine("Department ID: "));
            Department department = departmentRegistry.findDepartmentById(deptId);


            Teacher teacher = new Teacher(idPerson, pib, birthDate, email, phoneNumber,
                    teacherId, position, department, academicDegree, academicRank, hireDate, fullTimeEquivalent);

            teacherRegistry.addTeacher(teacher);
            department.addTeacher(teacher);

            System.out.println("Teacher added and attached to department: " + department.getDepartmentName());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeStudent(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Enter GradeBook ID to remove: "));
            studentRegistry.removeStudent(id);
            System.out.println("Student removed successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Faculty findFaculty(int id) {
        for (Faculty f : facultyRegistry.getFaculties()) {
            if (f != null && f.getIdFaculty() == id)
                return f;
        }
        throw new IllegalArgumentException("Faculty not found");
    }
}
