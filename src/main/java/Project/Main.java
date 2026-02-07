package Project;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class Main {

    private static FacultyRegistry facultyRegistry = new FacultyRegistry();
    private static DepartmentRegistry departmentRegistry = new DepartmentRegistry();
    private static StudentRegistry studentRegistry = new StudentRegistry();

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
                        editFaculty(reader, role);
                        break;

                    case "student remove":
                        removeFaculty(reader, role);
                        break;

                    case "student show":
                        System.out.println(facultyRegistry);
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
                        break; // додати реальний список
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
            String name = reader.readLine("Name: ");
            int facultyId = Integer.parseInt(reader.readLine("Faculty id: "));
            Faculty faculty = findFaculty(facultyId);
            String head = reader.readLine("Head: ");
            int room = Integer.parseInt(reader.readLine("Room: "));

            departmentRegistry.addDepartment(new Department(id, name, faculty, head, room));
            System.out.println("Department added.");
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


    }

    private static Faculty findFaculty(int id) {
        for (Faculty f : facultyRegistry.getFaculties()) {
            if (f != null && f.getIdFaculty() == id)
                return f;
        }
        throw new IllegalArgumentException("Faculty not found");
    }
    private static void addStudent(LineReader reader, String role) {
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
}
