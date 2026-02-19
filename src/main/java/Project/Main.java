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

        System.out.println(" /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\ \n" +
                        "( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )\n" +
                        " > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ < \n" +
                        " /\\_/\\                                                                        /\\_/\\ \n" +
                        "( o.o ) ██╗   ██╗██╗      █████╗ ██████╗      █████╗ ███╗   ██╗██████╗       ( o.o )\n" +
                        " > ^ <  ██║   ██║██║     ██╔══██╗██╔══██╗    ██╔══██╗████╗  ██║██╔══██╗       > ^ < \n" +
                        " /\\_/\\  ██║   ██║██║     ███████║██║  ██║    ███████║██╔██╗ ██║██║  ██║       /\\_/\\ \n" +
                        "( o.o ) ╚██╗ ██╔╝██║     ██╔══██║██║  ██║    ██╔══██║██║╚██╗██║██║  ██║      ( o.o )\n" +
                        " > ^ <   ╚████╔╝ ███████╗██║  ██║██████╔╝    ██║  ██║██║ ╚████║██████╔╝       > ^ < \n" +
                        " /\\_/\\    ╚═══╝  ╚══════╝╚═╝  ╚═╝╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝        /\\_/\\ \n" +
                        "( o.o )                                                                      ( o.o )\n" +
                        " > ^ <  ██████╗ ██╗███╗   ███╗ █████╗      ██████╗ ██████╗ ██████╗ ██████╗    > ^ < \n" +
                        " /\\_/\\  ██╔══██╗██║████╗ ████║██╔══██╗    ██╔════╝██╔═══██╗██╔══██╗██╔══██╗   /\\_/\\ \n" +
                        "( o.o ) ██║  ██║██║██╔████╔██║███████║    ██║     ██║   ██║██████╔╝██████╔╝  ( o.o )\n" +
                        " > ^ <  ██║  ██║██║██║╚██╔╝██║██╔══██║    ██║     ██║   ██║██╔══██╗██╔═══╝    > ^ < \n" +
                        " /\\_/\\  ██████╔╝██║██║ ╚═╝ ██║██║  ██║    ╚██████╗╚██████╔╝██║  ██║██║██╗     /\\_/\\ \n" +
                        "( o.o ) ╚═════╝ ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝    ( o.o )\n" +
                        " > ^ <" + "  DIGI UNI Systems v1.0                                                  > ^ < \n" +
                        " /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\  /\\_/\\ \n" +
                        "( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )( o.o )\n" +
                        " > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  > ^ <  "
                );

        System.out.println("==================================================");

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
        System.out.println("Type 'help'(or 6 for user menu) to see commands");

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
                case "help":
                    System.out.println("""
                            ============================================================
                            |                      COMMANDS                            |
                            ============================================================
                            | university show          | - Show university             |
                            |--------------------------|-------------------------------|
                            | faculty add              | - Add a faculty               |
                            | faculty edit             | - Edit a faculty              |
                            | faculty remove           | - Remove a faculty            |
                            | faculty show             | - Show all faculties          |
                            |--------------------------|-------------------------------|
                            | department add           | - Add a department            |
                            | department edit          | - Edit a department           |
                            | department remove        | - Remove a department         |
                            | department show          | - Show all departments        |
                            |--------------------------|-------------------------------|
                            | student add              | - Add a student               |
                            | student edit             | - Edit a student              |
                            | student remove           | - Remove a student            |
                            | student show             | - Show all students           |
                            | student transfer         | - Transfer student to another |
                            | student search pib       | - Search student by PIIB      |
                            | student search group     | - Search student by group     |
                            | student search course    | - Search student by course    |
                            |--------------------------|-------------------------------|
                            | teacher add              | - Add a teacher               |
                            | teacher edit             | - Edit a teacher              |
                            | teacher remove           | - Remove a teacher            |
                            | teacher show             | - Show all teachers           |
                            | teacher search pib       | - Search teacher by PIIB      |
                            | teacher search id        | - Search teacher by ID        |
                            |--------------------------|-------------------------------|
                            | exit                     | - Exit program                |
                            ============================================================
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

                case "student search pib":
                    searchStudentsByPib(reader, role);
                    break;

                case "student search course":
                    searchStudentsByCourse(reader, role);

                case "student search group":
                    searchStudentsByGroup(reader, role);

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

                case "teacher search pib":
                    searchTeachersByPib(reader, role);

                case "teacher search id":
                    searchTeacherById(reader, role);

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
            int line = Integer.parseInt(reader.readLine("User>> ").trim());
            if (line < 0 || line > 6) continue;


            switch (line) {
                case 6:
                    System.out.println("""
                            ==================================================
                            |                   Commands:                    |
                            ==================================================
                            | faculty show (1)      | - Show all faculties   |
                            | department show (2)   | - Show all departments |
                            | student show (3)      | - Show all students    |
                            | teacher show (4)      | - Show all teachers    |
                            | exit (5)              | - Exit program         |
                            ==================================================
                            """);
                    break;

                case 1:
                    System.out.println(facultyRegistry);
                    break;
                case 2:
                    System.out.println(departmentRegistry);
                    break;
                case 3:
                    System.out.println(studentRegistry);
                    break;
                case 4:
                    System.out.println(teacherRegistry);
                    break;
                case 5:
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
            while(name == null || name.isBlank()){
                System.out.println("ПОМИЛКА: ПОВНА НАЗВА ФАКУЛЬТЕТУ ПУСТА");
                name = reader.readLine("Name: ");
            }
            String shortName = reader.readLine("Short name: ");
            while(shortName == null || shortName.isBlank()){
                System.out.println("ПОМИЛКА: КОРОТКЕ ІМЯ ПУСТЕ");
                shortName = reader.readLine("Short name: ");
            }
            String head = reader.readLine("Head: ");
            while (head == null || head.isBlank()) {
                System.out.println(" ПОМИЛКА: ПІБ ДЕКАНА ПУСТЕ");
                head = reader.readLine("Head: ");
            }
            String email = reader.readLine("Email: ");
            while (email == null || !email.contains("@") || !email.contains(".")) {
                System.out.println(" ПОМИЛКА: НЕКОРЕКТНИЙ EMAIL (має містити @ та крапку)");
                email = reader.readLine("Email: ");
            }
            String phoneNumber = reader.readLine("Phone number:");
            while (phoneNumber == null || !phoneNumber.matches("\\+?\\d{10,13}")) {
                System.out.println(" ПОМИЛКА: ТЕЛЕФОН МАЄ МІСТИТИ ТІЛЬКИ ЦИФРИ(10-13 цифр)");
                phoneNumber = reader.readLine("Phone number: ");
            }

            Faculty faculty = new Faculty(id, name, shortName, head, email, phoneNumber);

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
            String email = reader.readLine("Email(" + faculty.getEmail() +"): ");
            String phoneNumber = reader.readLine("Phone number("+ faculty.getPhoneNumber()+"): ");

            universityRegistry.getUniversity()
                    .editFaculty(id, name, shortName, head, email, phoneNumber);

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
            while (name == null || name.isBlank()) {
                System.out.println("ПОМИЛКА: НАЗВА КАФЕДРИ ПУСТА");
                name = reader.readLine("Department name: ");
            }

            String facultyIdStr = reader.readLine("Faculty ID: ");
            Faculty faculty = null;
            if (facultyIdStr.matches("\\d+")) {
                faculty = universityRegistry.getUniversity().findFacultyById(Integer.parseInt(facultyIdStr));
            }

            while (faculty == null) {
                System.out.println("ПОМИЛКА: ФАКУЛЬТЕТ НЕ ЗНАЙДЕНО (введіть існуючий ID)");
                facultyIdStr = reader.readLine("Faculty ID: ");
                if (facultyIdStr.matches("\\d+")) {
                    faculty = universityRegistry.getUniversity().findFacultyById(Integer.parseInt(facultyIdStr));
                }
            }

            String head = reader.readLine("Head of department: ");
            while (head == null || head.isBlank()) {
                System.out.println("ПОМИЛКА: ПІБ ЗАВІДУВАЧА ПУСТЕ");
                head = reader.readLine("Head of department: ");
            }

            String roomStr = reader.readLine("Room number: ");
            int room = (roomStr.matches("\\d+")) ? Integer.parseInt(roomStr) : 0;
            while (room <= 0) {
                System.out.println("ПОМИЛКА: НОМЕР АУДИТОРІЇ МАЄ БУТИ ДОДАТНИМ ЧИСЛОМ");
                roomStr = reader.readLine("Room number: ");
                if (roomStr.matches("\\d+")) {
                    room = Integer.parseInt(roomStr);
                }
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
            while (pib == null || pib.isBlank() || !pib.matches("^[a-zA-Zа-яА-ЯіїєґІЇЄҐ'\\s]+$")) {
                System.out.println("ПОМИЛКА: ПІБ має містити тільки літери та не бути порожнім");
                pib = reader.readLine("Full Name (PIB): ");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthLocalDate = null;
            while (birthLocalDate == null) {
                String birthDateStr = reader.readLine("Birth Date (YYYYMMDD): ");
                try {
                    if (birthDateStr.matches("\\d{8}")) {
                        birthLocalDate = LocalDate.parse(birthDateStr, formatter);
                        int age = Period.between(birthLocalDate, LocalDate.now()).getYears();
                        if (age < 16 || age > 60) {
                            System.out.println("ПОМИЛКА: Вік студента має бути від 16 до 60 років (Зараз: " + age + ")");
                            birthLocalDate = null;
                        }
                    } else {
                        System.out.println("ПОМИЛКА: Формат має бути 8 цифр (YYYYMMDD)");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("ПОМИЛКА: Некоректна дата");
                }
            }
            String birthDate = birthLocalDate.format(formatter);

            String email = reader.readLine("Email: ");
            while (email == null || !email.contains("@") || !email.contains(".")) {
                System.out.println("ПОМИЛКА: Некоректний формат Email");
                email = reader.readLine("Email: ");
            }

            String phoneInput = reader.readLine("Phone Number (10-13 digits): ");
            while (phoneInput == null || !phoneInput.matches("\\d{10,13}")) {
                System.out.println("ПОМИЛКА: ТЕЛЕФОН МАЄ МІСТИТИ ТІЛЬКИ ЦИФРИ (10-13 цифр)");
                phoneInput = reader.readLine("Phone Number: ");
            }
            long phoneNumber = Long.parseLong(phoneInput);
            String gradeBookInput = reader.readLine("GradeBook ID: ");
            while (gradeBookInput == null || !gradeBookInput.matches("\\d+")) {
                System.out.println("ПОМИЛКА: GradeBook ID має бути числом");
                gradeBookInput = reader.readLine("GradeBook ID: ");
            }
            int gradeBookId = Integer.parseInt(gradeBookInput);

            String courseInput = reader.readLine("Course (1-4): ");
            while (courseInput == null || !courseInput.matches("[1-4]")) {
                System.out.println("ПОМИЛКА: КУРС МАЄ БУТИ ЦИФРОЮ ВІД 1 ДО 4");
                courseInput = reader.readLine("Course (1-4): ");
            }
            int course = Integer.parseInt(courseInput);
            String groupInput = reader.readLine("Group (1-6): ");
            while (groupInput == null || !groupInput.matches("[1-6]")) {
                System.out.println("ПОМИЛКА: ГРУПА МАЄ БУТИ ЦИФРОЮ ВІД 1 ДО 6");
                groupInput = reader.readLine("Group (1-6): ");
            }
            int group = Integer.parseInt(groupInput);

            String yearInput = reader.readLine("Enrollment year (2000-2026): ");
            while (yearInput == null || !yearInput.matches("\\d{4}")) {
                System.out.println("ПОМИЛКА: ВВЕДІТЬ РІК ЧИСЛОМ (наприклад, 2024)");
                yearInput = reader.readLine("Enrollment year (2000-2026): ");
            }
            int enrollmentYear = Integer.parseInt(yearInput);
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

            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.println("Editing student. Press Enter to keep current value.");

            String pib = reader.readLine("Full Name (" + student.getPib() + "): ");
            if (!pib.isBlank())
                student.setPib(pib);

            String email = reader.readLine("Email (" + student.getEmail() + "): ");
            if (!email.isBlank())
                student.setEmail(email);

            String phoneStr = reader.readLine("Phone Number (" + student.getPhoneNumber() + "): ");
            if (!phoneStr.isBlank())
                student.setPhoneNumber(Integer.parseInt(phoneStr));

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
            int gradeBookId = Integer.parseInt(reader.readLine("Enter Grade book ID to remove: "));
            Student student = studentRegistry.findStudentByGradeBook(gradeBookId);

            if (student == null) {
                System.out.println("Student not found");
                return;
            }

            Department deptm = student.getDepartment();

            if (deptm != null) {
                deptm.removeStudent(student);
            }

            studentRegistry.removeStudent(gradeBookId);
            System.out.println("Student removed successfully from department and registry.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchStudentsByPib(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            String pib = reader.readLine("Введіть ПІБ для пошуку: ");
            while(pib == null || pib.isBlank()){
                System.out.println("! ПІБ пустий !");
                pib = reader.readLine("Введіть ПІБ для пошуку: ");
            }
            Student[] results = studentRegistry.findByPib(pib);
            printSearchResults(results);
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void searchTeachersByPib(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }
        try {
            String pib = reader.readLine("Введіть ПІБ викладача для пошуку: ");
            while (pib == null || pib.isBlank()) {
                System.out.println("! ПІБ пустий !");
                pib = reader.readLine("Введіть ПІБ викладача для пошуку: ");
            }

            Teacher[] results = teacherRegistry.findByPib(pib);

            if (results.length == 0) {
                System.out.println("Викладачів не знайдено.");
            } else {
                System.out.println("Знайдено викладачів: " + results.length);
                for (Teacher t : results) {
                    System.out.println(t);
                    System.out.println("-----------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void searchTeacherById(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }
        try {
            String input = reader.readLine("Введіть ID викладача: ");
            while (input == null || input.isBlank() || !input.matches("\\d+")) {
                System.out.println("! ID має бути числом і не може бути порожнім !");
                input = reader.readLine("Введіть ID викладача: ");
            }

            int id = Integer.parseInt(input);
            Teacher teacher = teacherRegistry.findByTeacherId(id);

            System.out.println("Викладача знайдено:");
            System.out.println(teacher);

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void searchStudentsByCourse(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }
        try {
            int course = Integer.parseInt(reader.readLine("Введіть курс (1-4): "));
            while(course < 1 || course > 4){
                System.out.println("! Курс має бути в проміжку від 1-4 !");
                course = Integer.parseInt(reader.readLine("Введіть курс (1-4): "));
            }
            Student[] results = studentRegistry.findByCourse(course);
            printSearchResults(results);
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

    private static void searchStudentsByGroup(LineReader reader, String role) {
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }
        try {
            int group = Integer.parseInt(reader.readLine("Введіть номер групи: "));
            while(group < 1 || group > 6){
                System.out.println("! Група має бути в проміжку від 1-6 !");
                group = Integer.parseInt(reader.readLine("Введіть номер групи: "));
            }
            Student[] results = studentRegistry.findByGroup(group);
            printSearchResults(results);
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }


    private static void printSearchResults(Student[] results) {
        if (results.length == 0) {
            System.out.println("Студентів не знайдено.");
        } else {
            System.out.println("Знайдено студентів: " + results.length);
            for (int i = 0; i < results.length; i++) {
                System.out.println((i + 1) + ". " + results[i]);
                System.out.println("-----------------------------------");
            }
        }
    }



    private static void addTeacher(LineReader reader, String role) {

        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int idPerson = generateSixDigitId();
            System.out.println("Person ID: " + idPerson);

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

            int teacherId = generateSixDigitId();
            System.out.println("Teacher id: " + teacherId);

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
            int id = Integer.parseInt(reader.readLine("Enter Teacher ID to edit: "));
            Teacher teacher = teacherRegistry.findTeacherById(id);

            if (teacher == null) {
                System.out.println("Teacher not found.");
                return;
            }

            System.out.println("Editing teacher. Press Enter to keep current value.");

            String pib = reader.readLine("Full Name (" + teacher.getPib() + "): ");
            if (!pib.isBlank())
                teacher.setPib(pib);

            String email = reader.readLine("Email (" + teacher.getEmail() + "): ");
            if (!email.isBlank())
                teacher.setEmail(email);

            String phoneStr = reader.readLine("Phone Number (" + teacher.getPhoneNumber() + "): ");
            if (!phoneStr.isBlank())
                teacher.setPhoneNumber(Integer.parseInt(phoneStr));

            String position = reader.readLine("Position (" + teacher.getPosition() + "): ");
            if (!position.isBlank())
                teacher.setPosition(position);

            String degree = reader.readLine("Academic Degree (" + teacher.getAcademicDegree() + "): ");
            if (!degree.isBlank())
                teacher.setAcademicDegree(degree);

            String rank = reader.readLine("Academic Rank (" + teacher.getAcademicRank() + "): ");
            if (!rank.isBlank())
                teacher.setAcademicRank(rank);

            String hireDate = reader.readLine("Hire Date (" + teacher.getHireDate() + "): ");
            if (!hireDate.isBlank())
                teacher.setHireDate(hireDate);

            String fteStr = reader.readLine("Full Time Equivalent (" + teacher.getFullTimeEquivalent() + "): ");
            if (!fteStr.isBlank())
                teacher.setFullTimeEquivalent(Double.parseDouble(fteStr.replace(',', '.')));

            String deptStr = reader.readLine("Department ID (" + teacher.getDepartment().getIdDepartment() + "): ");
            if (!deptStr.isBlank()) {
                int deptId = Integer.parseInt(deptStr);
                Department dept = departmentRegistry.findDepartmentById(deptId);
                if (dept != null) {
                    // переміщаємо вчителя до нового департаменту
                    teacher.getDepartment().removeTeacher(teacher);
                    dept.addTeacher(teacher);
                    teacher.setDepartment(dept);
                } else {
                    System.out.println("Department not found, keeping current.");
                }
            }

            System.out.println("Teacher record updated.");
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
