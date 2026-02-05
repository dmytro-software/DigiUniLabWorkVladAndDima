package Project;

public class StudentRegistry {
    private Student[] students;
    private int numberOfStudents;

    public StudentRegistry() {
        this.students = new Student[10];
    }
    public void addStudent(Student student){
        if(numberOfStudents >= students.length){
            resizeArray(numberOfStudents + 1);
        }
        students[numberOfStudents++] = student;
    }
    public void resizeArray(int i){
        Student[] newarray = new Student[i];
        System.arraycopy(students, 0, newarray, 0, students.length);
        students = newarray;
    }

    public Student[] getStudents() {
        return students.clone();
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }
}
