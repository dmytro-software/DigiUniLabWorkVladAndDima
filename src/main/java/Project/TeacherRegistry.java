package Project;

public class TeacherRegistry {
    private Teacher[] teachers;
    private int numberOfTeachers;

    public TeacherRegistry() {
        this.teachers = new Teacher[1];
    }

    public Teacher[] getTeachers() {
        return teachers.clone();
    }

    public int getNumberOfTeachers() {
        return numberOfTeachers;
    }

    public void addTeacher(Teacher teacher){
        if(numberOfTeachers >= teachers.length){
            resizeArray(numberOfTeachers + 1);
        }
        teachers[numberOfTeachers++] = teacher;
    }

    public void removeTeacher(int teacherId) {
        for (int i = 0; i < numberOfTeachers; i++) {
            if (teachers[i].getTeacherId() == teacherId) {
                System.arraycopy(teachers, i + 1, teachers, i, numberOfTeachers - i - 1);
                teachers[--numberOfTeachers] = null;
                return;
            }
        }
        throw new IllegalArgumentException("Teacher not found with ID: " + teacherId);
    }

    public void editTeacher(int teacherId, String position, String academicDegree,
                            String academicRank, String hireDate, Double fullTimeEquivalent,
                            Department department) {
        Teacher teacher = findTeacherById(teacherId);
        if (position != null) teacher.setPosition(position);
        if (academicDegree != null) teacher.setAcademicDegree(academicDegree);
        if (academicRank != null) teacher.setAcademicRank(academicRank);
        if (hireDate != null) teacher.setHireDate(hireDate);
        if (fullTimeEquivalent != null) teacher.setFullTimeEquivalent(fullTimeEquivalent);
        if (department != null) teacher.setDepartment(department);
    }
    public Teacher findTeacherById(int teacherId) {
        for (int i = 0; i < numberOfTeachers; i++) {
            if (teachers[i].getTeacherId() == teacherId) {
                return teachers[i];
            }
        }
        throw new IllegalArgumentException("Teacher not found with ID: " + teacherId);
    }

    public Teacher findByPib(String pib){
        for(int i = 0; i < numberOfTeachers; i++) {
            if (teachers[i] != null && teachers[i].getPib().equals(pib)){
                return teachers[i];
            }
        }
        return null;
    }

    public void resizeArray(int i){
        Teacher[] newarray = new Teacher[i];
        System.arraycopy(teachers, 0, newarray, 0, teachers.length);
        teachers = newarray;
    }

    @Override
    public String toString() {

        String result = " РЕЄСТР ВЧИТЕЛІВ (Всього: " + numberOfTeachers + ") \n";
        for (int i = 0; i < numberOfTeachers; i++) {
            result = result + (i + 1) + ". " + teachers[i].toString() + "\n";
            result = result + "--------------------------------------------------\n";
        }
        return result;
    }
}
