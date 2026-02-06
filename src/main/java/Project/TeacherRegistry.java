package Project;

public class TeacherRegistry {
    private Teacher[] teachers;
    private int numberOfTeachers;

    public TeacherRegistry() {
        this.teachers = new Teacher[10];
    }
    public void addTeacher(Teacher teacher){
        if(numberOfTeachers >= teachers.length){
            resizeArray(numberOfTeachers + 1);
        }
        teachers[numberOfTeachers++] = teacher;
    }

    public void removeTeacher(int id) {
        for (int i = 0; i < numberOfTeachers; i++) {
            //шукаємо за айді
            if (teachers[i].getIdPerson() == id) {
                Teacher[] newArray = new Teacher[numberOfTeachers - 1];
                // пояснення для себе
                // копіювання все до вибраного елементу
                System.arraycopy(teachers, 0, newArray, 0, i);
                // копіювання все після вибраного елементу
                System.arraycopy(teachers, i + 1, newArray, i, numberOfTeachers - i - 1);

                teachers = newArray;
                numberOfTeachers--;
                return;
            }
        }
        throw new IllegalArgumentException("КАФЕДРУ НЕ ЗНАЙДЕНО");
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
