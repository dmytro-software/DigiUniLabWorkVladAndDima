package Project;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

public class Main {

    private static FacultyRegistry facultyRegistry = new FacultyRegistry();
    private static DepartmentRegistry departmentRegistry = new DepartmentRegistry();
    private static StudentRegistry studentRegistry = new StudentRegistry();
    private static TeacherRegistry teacherRegistry = new TeacherRegistry();
    private static UniversityRegistry universityRegistry = new UniversityRegistry();

    public static int generateSixDigitId() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

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

    private static void managerMenu(LineReader reader, String role) {
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

//                case "student transfer":
//                    transferStudent(reader);
//                    break;

                case "teacher add":
                    addTeacher(reader, role);
                    break;

                case "teacher edit":
                    editTeacher(reader, role);
                    break;

                case "teacher remove":
                    removeTeacher(reader, role);
                    break;

                case "teacher show":
                    System.out.println(teacherRegistry);
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
            int id = generateSixDigitId();
            System.out.println("Faculty id: " + id);
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

            Faculty faculty = universityRegistry
                    .getUniversity()
                    .findFacultyById(id);

            System.out.println("Press Enter to keep current value.");

            String name = reader.readLine("Name (" + faculty.getFacultyName() + "): ");
            String shortName = reader.readLine("Short name (" + faculty.getFacultyShortName() + "): ");
            String head = reader.readLine("Head of faculty(" + faculty.getHeadOfFaculty() + "): ");
            String contacts = reader.readLine("Contacts (" + faculty.getContactsOfFaculty() + "): ");

            universityRegistry.getUniversity()
                    .editFaculty(id, name, shortName, head, contacts);

            System.out.println("Faculty updated successfully.");

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

            Faculty faculty = universityRegistry.getUniversity().findFacultyById(id);

            if (faculty.getDepartments().length > 0) {
                System.out.println("Cannot remove faculty with departments.");
                return;
            }

            boolean removedFromUniversity = universityRegistry.getUniversity().removeFaculty(id);

            facultyRegistry.removeFaculty(id);

            if (removedFromUniversity) {
                System.out.println("Faculty removed successfully.");
            } else {
                System.out.println("Faculty not found.");
            }

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
            // Генерація або введення унікального ID департаменту
            int id = generateSixDigitId();
            System.out.println("Department ID: " + id);

            String name = reader.readLine("Department name: ");
            if (name.isBlank()) {
                System.out.println("Department name cannot be empty.");
                return;
            }

            String facultyIdStr = reader.readLine("Faculty ID: ");
            if (facultyIdStr.isBlank() || !facultyIdStr.matches("\\d+")) {
                System.out.println("Faculty ID must be a number.");
                return;
            }
            int facultyId = Integer.parseInt(facultyIdStr);

            Faculty faculty = universityRegistry.getUniversity().findFacultyById(facultyId);
            if (faculty == null) {
                System.out.println("Faculty not found.");
                return;
            }

            String head = reader.readLine("Head of department: ");
            if (head.isBlank()) {
                System.out.println("Head of department cannot be empty.");
                return;
            }

            String roomStr = reader.readLine("Room number: ");
            if (roomStr.isBlank() || !roomStr.matches("\\d+")) {
                System.out.println("Room number must be a positive integer.");
                return;
            }
            int room = Integer.parseInt(roomStr);
            if (room <= 0) {
                System.out.println("Room number must be greater than 0.");
                return;
            }

            Department department = new Department(id, name, faculty, head, room);

            faculty.addDepartment(department);

            departmentRegistry.addDepartment(department);


            System.out.println("Department '" + name + "' added to faculty: " + faculty.getFacultyName());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    private static void removeDepartment(LineReader reader, String role) {

        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }
        int facultyId = Integer.parseInt(reader.readLine("Faculty ID: "));
        Faculty faculty = universityRegistry.getUniversity().findFacultyById(facultyId);

        int depId = Integer.parseInt(reader.readLine("Department ID to remove: "));

        boolean removed = faculty.removeDepartment(depId);

        if (removed) {
            departmentRegistry.removeDepartment(depId);
            System.out.println("Department removed from faculty: " + faculty.getFacultyName());
        } else {
            System.out.println("Department not found in faculty.");
        }
    }

    private static void editDepartment(LineReader reader, String role) {

        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            String facultyIdStr = reader.readLine("Faculty ID: ");
            if (facultyIdStr.isBlank() || !facultyIdStr.matches("\\d+")) {
                System.out.println("Faculty ID must be a number.");
                return;
            }
            int facultyId = Integer.parseInt(facultyIdStr);
            Faculty faculty = universityRegistry.getUniversity().findFacultyById(facultyId);
            if (faculty == null) {
                System.out.println("Faculty not found.");
                return;
            }

            String depIdStr = reader.readLine("Department ID to edit: ");
            if (depIdStr.isBlank() || !depIdStr.matches("\\d+")) {
                System.out.println("Department ID must be a number.");
                return;
            }
            int depId = Integer.parseInt(depIdStr);
            Department dept = null;
            try {
                dept = faculty.findDepartmentById(depId);
            } catch (Exception ex) {
                System.out.println("Department not found in this faculty.");
                return;
            }

            System.out.println("Press Enter to keep current value.");

            String name = reader.readLine("Name (" + dept.getDepartmentName() + "): ");
            if (!name.isBlank()) {
                dept.setDepartmentName(name);
            }

            String head = reader.readLine("Head (" + dept.getHeadOfDepartment() + "): ");
            if (!head.isBlank()) {
                dept.setHeadOfDepartment(head);
            }

            String roomStr = reader.readLine("Room (" + dept.getRoomNumberOfDepartment() + "): ");
            if (!roomStr.isBlank()) {
                if (!roomStr.matches("\\d+")) {
                    System.out.println("Room number must be a positive integer.");
                    return;
                }
                int room = Integer.parseInt(roomStr);
                if (room <= 0) {
                    System.out.println("Room number must be greater than 0.");
                    return;
                }
                dept.setRoomNumberOfDepartment(room);
            }

            System.out.println("Department updated in faculty: " + faculty.getFacultyName());

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
            int idPerson = generateSixDigitId();
            System.out.println("Person ID: " + idPerson);

            String pib = reader.readLine("Full Name (PIB): ");
            if (pib.isBlank() || !pib.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
                System.out.println("Full Name must contain only letters and spaces.");
                return;
            }

            String birthDate = reader.readLine("Birth Date (YYYYMMDD): ");
            if (!birthDate.matches("\\d{8}")) {
                System.out.println("Birth Date must be in format YYYYMMDD and 8 digits.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthLocalDate;
            try {
                birthLocalDate = LocalDate.parse(birthDate, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid birth date format.");
                return;
            }

            LocalDate today = LocalDate.now();
            if (birthLocalDate.isAfter(today)) {
                System.out.println("Birth date cannot be in the future.");
                return;
            }

            int age = Period.between(birthLocalDate, today).getYears();
            if (age < 16 || age > 60) {
                System.out.println("Student age must be between 16 and 60 years. Current age: " + age);
                return;
            }

            String email = reader.readLine("Email: ");
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("Invalid email format.");
                return;
            }

            String phoneStr = reader.readLine("Phone Number: ");
            if (!phoneStr.matches("\\d{10}")) {
                System.out.println("Phone number must be exactly 10 digits.");
                return;
            }
            int phoneNumber = Integer.parseInt(phoneStr);

            int gradeBookId = generateSixDigitId();
            System.out.println("GradeBook ID: " + gradeBookId);

            int course = Integer.parseInt(reader.readLine("Course (1-4): "));
            int group = Integer.parseInt(reader.readLine("Group (1-6): "));
            int enrollmentYear = Integer.parseInt(reader.readLine("Enrollment year (2000-2026): "));
            String formOfEducation = reader.readLine("Form of Education: ");
            String studentStatus = reader.readLine("Student status: ");

            int facultyId = Integer.parseInt(reader.readLine("Faculty ID: "));
            Faculty faculty = universityRegistry.getUniversity().findFacultyById(facultyId);
            if (faculty == null) {
                System.out.println("Faculty not found.");
                return;
            }

            int departmentId = Integer.parseInt(reader.readLine("Department ID: "));
            Department department = faculty.findDepartmentById(departmentId);
            if (department == null) {
                System.out.println("Department not found in this faculty.");
                return;
            }

            // Створення студента
            Student student = new Student(idPerson, pib, birthDate, email, phoneNumber, gradeBookId,
                    course, faculty, group, enrollmentYear, formOfEducation, studentStatus);

            // Додаємо студента до департаменту та глобального реєстру
            department.addStudent(student);
            studentRegistry.addStudent(student);

            System.out.println("Student added successfully to department '" + department.getDepartmentName()
                    + "' in faculty '" + faculty.getFacultyName() + "'.");

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
            if (!courseStr.isBlank())
                student.setCourse(Integer.parseInt(courseStr));

            String groupStr = reader.readLine("Group (" + student.getGroup() + "): ");
            if (!groupStr.isBlank())
                student.setGroup(Integer.parseInt(groupStr));

            String form = reader.readLine("Form of Education (" + student.getFormOfEducation() + "): ");
            if (!form.isBlank())
                student.setFormOfEducation(form);

            String status = reader.readLine("Status (" + student.getStudentStatus() + "): ");
            if (!status.isBlank())
                student.setStudentStatus(status);

            System.out.println("Student record updated.");
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
            int gradeBookId = Integer.parseInt(reader.readLine("Enter GradeBook ID to remove: "));

            // Знаходимо кафедру, в якій є студент
            Department department = departmentRegistry.getDepartment();
            if (department == null) {
                System.out.println("Department not found.");
                return;
            }

            // Перевіряємо чи студент існує
            Student student = null;
            try {
                student = department.findStudentByGradeBook(gradeBookId);
            } catch (IllegalArgumentException e) {
                System.out.println("Student not found.");
                return;
            }

            // Видаляємо студента з кафедри
            boolean removedFromDepartment = department.removeStudent(gradeBookId);

            // Видаляємо студента з реєстру університету, якщо потрібен глобальний реєстр
            if (removedFromDepartment) {
                departmentRegistry.getDepartment().removeStudent(gradeBookId);
                System.out.println("Student removed successfully from department: " + department.getDepartmentName());
            } else {
                System.out.println("Student not found in department.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid GradeBook ID.");
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
            String idPersonStr = reader.readLine("Person ID: ");
            if (idPersonStr.isBlank() || !idPersonStr.matches("\\d+")) {
                System.out.println("Person ID must be a number and not empty.");
                return;
            }
            int idPerson = Integer.parseInt(idPersonStr);

            String pib = reader.readLine("Full Name: ");
            if (pib.trim().isEmpty() || !pib.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
                System.out.println("Name cannot be empty.");
                return;
            }
            String birthDate = reader.readLine("Birth Date (YYYYMMDD): ");
            if (!birthDate.matches("\\d{8}")) {
                System.out.println("Birth date must be exactly 8 digits.");
                return;
            }
            String email = reader.readLine("Email: ");
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("Invalid email format.");
                return;
            }
            String phoneStr = reader.readLine("Phone Number: ");
            if (phoneStr.isBlank() || !phoneStr.matches("\\d{10}") || phoneStr.matches(".*[a-zA-Zа-яА-ЯіїєґІЇЄҐ].*")) {
                System.out.println("Phone number must contain only digits.");
                return;
            }
            int phoneNumber = Integer.parseInt(phoneStr);

            String teacherIdStr = reader.readLine("Teacher ID: ");
            if (teacherIdStr.isBlank() || !teacherIdStr.matches("\\d+")) {
                System.out.println("Teacher ID must be a number.");
                return;
            }
            int teacherId = Integer.parseInt(teacherIdStr);

            String position = reader.readLine("Position: ");

            if (position.isBlank() || !position.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
                System.out.println("Position is required and must contain only letters.");
                return;
            }
            String academicDegree = reader.readLine("Academic Degree: ");

            if (academicDegree.isBlank() || !academicDegree.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
                System.out.println("Validation Error.");
                return;
            }
            String academicRank = reader.readLine("Academic Rank: ");

            String hireDate = reader.readLine("Hire Date (YYYYMMDD): ");
            if (hireDate.isBlank() || !hireDate.matches("\\d{8}")) {
                System.out.println("Validation Error: Hire Date must be exactly 8 digits (format: YYYYMMDD).");
                return;
            }

            System.out.print("Full Time Equivalent: ");
            String input = reader.readLine().replace(',', '.');
            double fullTimeEquivalent = Double.parseDouble(input);

            if (fullTimeEquivalent <= 0 || fullTimeEquivalent > 1.0) {
                System.out.println("FTE should be between 0.1 and 1.0.");
                return;
            }

            String departmentIdStr = reader.readLine("Department ID: ");
            if (departmentIdStr.isBlank() || !departmentIdStr.matches("\\d+")) {
                System.out.println("Department ID must be a number.");
                return;
            }
            int deptId = Integer.parseInt(departmentIdStr);
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
    private static void editTeacher(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int teacherId = Integer.parseInt(reader.readLine("Enter Teacher ID to edit: "));
            Teacher teacher = teacherRegistry.findTeacherById(teacherId);

            System.out.println("Press Enter to keep current value.");

            String position = reader.readLine("Position (" + teacher.getPosition() + "): ");
            String degree = reader.readLine("Academic Degree (" + teacher.getAcademicDegree() + "): ");
            String rank = reader.readLine("Academic Rank (" + teacher.getAcademicRank() + "): ");
            String hireDate = reader.readLine("Hire Date (" + teacher.getHireDate() + "): ");

            String fteStr = reader.readLine("Full Time Equivalent (" + teacher.getFullTimeEquivalent() + "): ");
            Double fte = fteStr.isBlank() ? null : Double.parseDouble(fteStr.replace(',', '.'));

            String department = reader.readLine("Department ID (" + teacher.getDepartment().getIdDepartment() + "): ");
            Department dept;
            if (department.isBlank()) {
                dept = null;
            } else {
                dept = departmentRegistry.findDepartmentById(Integer.parseInt(department));
            }


            System.out.println("Teacher updated successfully.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeTeacher(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int teacherId = Integer.parseInt(reader.readLine("Enter Teacher ID to remove: "));
            Teacher teacher = teacherRegistry.findTeacherById(teacherId);

            Department dept = teacher.getDepartment();
            if (dept != null) {
                dept.removeTeacher(teacher);
            }

            teacherRegistry.removeTeacher(teacherId);
            System.out.println("Teacher removed successfully from department and registry.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
