package Project;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class Main {

    private static FacultyRegistry facultyRegistry = new FacultyRegistry();
    private static DepartmentRegistry departmentRegistry = new DepartmentRegistry();
    private static StudentRegistry studentRegistry = new StudentRegistry();
    private static TeacherRegistry teacherRegistry = new TeacherRegistry();
    private static UniversityRegistry universityRegistry = new UniversityRegistry();

    public static void main(String[] args) throws Exception {

        Terminal terminal = TerminalBuilder.builder().system(true).build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

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
        System.out.println("Type 'help' to see commands");

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
                    case "help -menager":
                        System.out.println("""
                                ==============================================
                                |               Commands:                    |
                                ==============================================
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

                 facultyRegistry.addFaculty(new Faculty(id, name, shortName, head, contacts));
                 System.out.println("Faculty added.");
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
            if (name.isBlank()) name = faculty.getFacultyName();

            String shortName = reader.readLine("Short name (" + faculty.getFacultyShortName() + "): ");
            if (shortName.isBlank()) shortName = faculty.getFacultyShortName();

            String head = reader.readLine("Head (" + faculty.getHeadOfFaculty() + "): ");
            if (head.isBlank()) head = faculty.getHeadOfFaculty();

            String contacts = reader.readLine("Contacts (" + faculty.getContactsOfFaculty() + "): ");
            if (contacts.isBlank()) contacts = faculty.getContactsOfFaculty();

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

            int facultyId = Integer.parseInt(reader.readLine("Enter Faculty ID for this department: "));
            Faculty faculty = findFaculty(facultyId);

            String headOfDepartment = reader.readLine("Head of department: ");
            int roomNumber = Integer.parseInt(reader.readLine("Room number: "));

            departmentRegistry.addDepartment(new Department(id, name, faculty, headOfDepartment, roomNumber));
            System.out.println("Department added successfully.");
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
            if (!name.isBlank()) dept.setDepartmentName(name);

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

            studentRegistry.addStudent(new Student(idPerson, pib, birthDate, email, phoneNumber,
                    gradeBookId, course, group, enrollmentYear, formOfEducation, studentStatus));
            System.out.println("Student added successfully.");
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

    private static void addTeacher(LineReader reader, String role){
        if(!role.equals("manager")){
            System.out.println("No permission");
        }

        try {

            int idPerson = Integer.parseInt(reader.readLine("Person ID: "));
            String pib = reader.readLine("Full Name (PIB): ");
            String birthDate = reader.readLine("Birth Date: ");
            String email = reader.readLine("Email: ");
            int phoneNumber = Integer.parseInt(reader.readLine("Phone Number: "));

            int teacherId = Integer.parseInt(reader.readLine("Teacher ID: "));
            String position = reader.readLine("Position: ");
            String academicDegree = reader.readLine("Academic Degree: ");
            String academicRank = reader.readLine("Academic Rank: ");
            String hireDate = reader.readLine("Hire date: ");
            double fullTimeEquivalent = Integer.parseInt(reader.readLine("Full time equivalent: "));

            teacherRegistry.addTeacher(new Teacher(idPerson,pib,birthDate,email,phoneNumber,teacherId,position,academicDegree,academicRank,
                   hireDate,fullTimeEquivalent));
            System.out.println("Teacher added successfully.");
        } catch (Exception e) {
            System.out.println("Error:"+ e.getMessage());
        }
    }

    private static void editTeacher(LineReader reader,String role){
        if(!role.equals("manager")){
            System.out.println("No permission");
            return;
        }

        try {

            int teacherId = Integer.parseInt(reader.readLine("Enter TeacherID: "));

            Teacher teacher = teacherRegistry.findByTeacherId(teacherId);

            System.out.println("Editing teacher. Press Enter to keep current value.");

            String positionEdt = reader.readLine("Position (" + teacher.getPosition() + "): ");
            if(!positionEdt.isBlank()) teacher.setPosition(positionEdt);

            String academicDegreeEdt = reader.readLine("Academic degree("+ teacher.getAcademicDegree()+"): ");
            if(!academicDegreeEdt.isBlank()) teacher.setAcademicDegree(academicDegreeEdt);

            String acacdemicRankEdt = reader.readLine("Acacdemic Rank("+teacher.getAcademicRank()+"): ");
            if(!acacdemicRankEdt.isBlank()) teacher.setAcademicRank(acacdemicRankEdt);

            String fullTimeEquivalentEdt = reader.readLine("Full time equivalent:("+teacher.getFullTimeEquivalent()+"): ");
            if (!fullTimeEquivalentEdt.isBlank()) teacher.setFullTimeEquivalent(Integer.parseInt(fullTimeEquivalentEdt));

            System.out.println("Teacher record updated.");


        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    public static void removeTeacher(LineReader reader,String role ){
        if (!role.equals("manager")) {
            System.out.println("No permission");
            return;
        }

        try {
            int id = Integer.parseInt(reader.readLine("Enter TeacherId to remove: "));
            teacherRegistry.removeTeacher(id);
            System.out.println("Teacher removed successfully");
        }catch (Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }


//    private static void searchStudent(LineReader reader) {
//        String pib = reader.readLine("Enter your pib for search: ");
//
//        studentRegistry.findByPib(pib)
//                .ifPresentOrElse(
//                        student -> System.out.println("Знайдено: " + student),
//                        () -> System.out.println("Student not found.")
//                );
//    }

    private static Faculty findFaculty(int id) {
        for (Faculty f : facultyRegistry.getFaculties()) {
            if (f != null && f.getIdFaculty() == id)
                return f;
        }
        throw new IllegalArgumentException("Faculty not found");
    }
}
