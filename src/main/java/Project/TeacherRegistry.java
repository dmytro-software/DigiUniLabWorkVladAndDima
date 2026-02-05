package Project;

public class TeacherRegistry {
    private Teacher[] teachers;
    private int numberOfTeachers;

    public TeacherRegistry() {
        this.teachers = new Teacher[10];
    }
    public void addStudent(Teacher teacher){
        if(numberOfTeachers >= teachers.length){
            resizeArray(numberOfTeachers + 1);
        }
        teachers[numberOfTeachers++] = teacher;
    }
    public void resizeArray(int i){
        Teacher[] newarray = new Teacher[i];
        System.arraycopy(teachers, 0, newarray, 0, teachers.length);
        teachers = newarray;
    }

    public Teacher[] getTeachers() {
        return teachers.clone();
    }

    public int getNumberOfTeachers() {
        return numberOfTeachers;
    }
}
