package Project;


public class DepartmentRegistry {
    private Department department;

    private Department[] departments;
    private int numOfDepartments;


    public DepartmentRegistry() {
        this.departments = new Department[1];
        this.numOfDepartments = 0;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    private void resizeArray() {
        Department[] newArray = new Department[departments.length * 2];
        System.arraycopy(departments, 0, newArray, 0, numOfDepartments);
        departments = newArray;
    }

    public void addDepartment(Department department) {
        if (numOfDepartments >= departments.length) {
            resizeArray();
        }
        departments[numOfDepartments++] = department;
    }

    public void removeDepartment(int id) {
        for (int i = 0; i < numOfDepartments; i++) {
            if (departments[i].getIdDepartment() == id) {
                for (int j = i; j < numOfDepartments - 1; j++) {
                    departments[j] = departments[j + 1];
                }
                departments[numOfDepartments - 1] = null;
                numOfDepartments--;
                return;
            }
        }
        throw new IllegalArgumentException("Department not found");
    }

    public Department findDepartmentById(int id) {
        for (int i = 0; i < numOfDepartments; i++) {
            if (departments[i].getIdDepartment() == id) {
                return departments[i];
            }
        }
        throw new IllegalArgumentException("Department not found with ID " + id);
    }

    @Override
    public String toString() {
        if (numOfDepartments == 0) {
            return "No departments available.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfDepartments; i++) {
            sb.append(departments[i].toString()).append("\n");
        }
        return sb.toString();
    }
}
