package Project;

public class UniversityRegistry {

    private University university;

    public void setUniversity(University university) {
        this.university = university;
    }

    public University getUniversity() {
        return university;
    }

    @Override
    public String toString() {
        return university.toString();
    }
}
